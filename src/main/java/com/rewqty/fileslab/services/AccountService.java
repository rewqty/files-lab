package com.rewqty.fileslab.services;

import com.rewqty.fileslab.models.UserProfileModel;
import java.util.HashMap;
import java.util.Map;
import com.rewqty.fileslab.database.DataBaseService;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {
    private final Map<String, UserProfileModel> loginToProfile;
    private final Map<String, UserProfileModel> sessionIdToProfile;
    private final DataBaseService _dataBaseService;

    private static AccountService instance;
    public static AccountService getInstance() {
        if(instance == null) {
            instance = new AccountService();
        }

        return instance;
    }
    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        _dataBaseService = new DataBaseService();
        initializeDataBaseService();
    }

    private void initializeDataBaseService() {
        try (ResultSet rs = _dataBaseService.getStatement().executeQuery("select * from users")) {
            while (rs.next()) {
                loginToProfile.put(
                        rs.getString(1),
                        new UserProfileModel(rs.getString(1),rs.getString(2),rs.getString(3))
                );
                String login = rs.getString(1);
                System.out.println("Login: " + login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNewUser(String login, String email, String pass) {
        try {
            String sql = String.format(
                    "insert into users (login, email, pass) values ('%s', '%s', '%s');", login, email, pass);
            _dataBaseService.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addNewUser(UserProfileModel userProfile) {
        insertNewUser(userProfile.getLogin(), userProfile.getEmail(), userProfile.getPass());
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }
    public boolean loginExist(String login) {
        return _dataBaseService.columnValueExist("login", login);
    }
    public boolean emailExist(String email) {
        return _dataBaseService.columnValueExist("email", email);
    }

    public UserProfileModel getUserByLogin(String login) {
        return loginToProfile.get(login);
    }
    public UserProfileModel getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }
    public void addSession(String sessionId, UserProfileModel userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
package com.rewqty.fileslab.services;

import com.rewqty.fileslab.models.UserProfileModel;
import java.util.HashMap;
import java.util.Map;
import com.rewqty.fileslab.database.DataBaseService;

public class AccountService {
    private final Map<String, UserProfileModel> sessionIdToProfile;
    private final DataBaseService _dataBaseService;
    private static AccountService instance;
    public static AccountService getInstance() {
        if(instance == null) {
            instance = new AccountService();
        }

        return instance;
    }
    private AccountService() {
        sessionIdToProfile = new HashMap<>();
        _dataBaseService = new DataBaseService();
    }
    public void addNewUser(UserProfileModel userProfile) {
        _dataBaseService.addUser(userProfile.getLogin(), userProfile.getEmail(), userProfile.getPass());
    }
    public boolean loginExist(String login) {
        UserProfileModel user = _dataBaseService.getUserByLogin(login);
        return user != null;
    }
    public boolean emailExist(String email) {
        UserProfileModel user = _dataBaseService.getUserByEmail(email);
        return user != null;
    }
    public UserProfileModel getUserByLogin(String login) {
        return _dataBaseService.getUserByLogin(login);
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
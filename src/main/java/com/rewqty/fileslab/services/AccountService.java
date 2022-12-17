package com.rewqty.fileslab.services;

import com.rewqty.fileslab.models.UserProfileModel;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfileModel> loginToProfile;
    private final Map<String, UserProfileModel> sessionIdToProfile;

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
    }

    public void addNewUser(UserProfileModel userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
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
package com.rewqty.fileslab.models;

public class UserProfileModel {
    private final String login;
    private final String pass;
    private final String email;

    public UserProfileModel(String login, String email, String pass) {
        this.login = login;
        this.email = email;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }
}

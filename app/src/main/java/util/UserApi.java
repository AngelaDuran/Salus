package util;

import android.app.Application;

public class UserApi extends Application {

    private String username;
    private String userId;
    private static UserApi instance;

    public static UserApi getInstance() {
        if (instance == null)
            instance = new UserApi();
        return instance;

    }

    public UserApi(){}


    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        username = username;
    }

    public String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) { userId = userId;}
}

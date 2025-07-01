package com.tap.schoolplatform.services.auth;

import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.Service;

public class LoginService extends Service {

    private static User CURRENT_USER;
    public static User getCurrentUser() {
        return CURRENT_USER;
    }
    public static void setCurrentUser(User user) {
        CURRENT_USER = user;
    }
    public static void logout() {
        CURRENT_USER = null;
    }
}

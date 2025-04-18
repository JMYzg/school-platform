package com.tap.schoolplatfom.services.auth;

import com.tap.schoolplatfom.models.users.User;
import com.tap.schoolplatfom.models.users.enums.Role;
import com.tap.schoolplatfom.services.Service;

import java.util.Optional;

public class AuthService extends Service {
    public static User login(String email, String password) {
        Optional<User> match = Optional.empty();
        for (Role role : Role.values()) {
            match = data.getUsers(role).stream()
                    .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                    .findFirst();
        }
        return match.orElse(null);
    }
}

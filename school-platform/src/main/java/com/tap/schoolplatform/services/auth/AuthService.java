package com.tap.schoolplatform.services.auth;

import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.utils.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public class AuthService extends Service {
    public static User login(String email, String password) throws UserNotFoundException {
        List<User> users = getEvery(User.class);
        Optional<User> match = users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        }
        throw new UserNotFoundException("Check out your credentials");
    }
}
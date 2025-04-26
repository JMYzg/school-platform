package com.tap.schoolplatform.controllers.login;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.auth.AuthService;
import com.tap.schoolplatform.services.auth.LoginService;
import com.tap.schoolplatform.utils.Validation;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import com.tap.schoolplatform.utils.exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController extends ViewController {

    @FXML private TextField
            emailField,
            passwordField;

    @FXML private Button loginButton;

    @FXML private void initialize() {
        LoginService.logout();
    }

    @FXML
    private void onLoginClick() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        try {
            if (!Validation.ofEmail(email)) throw new NotValidFormatException("Not a valid email");
//            if (!Validation.ofPassword(password)) throw new NotValidFormatException("Not a valid password");
            User user = AuthService.login(email, password);
            LoginService.setCurrentUser(user);
            String view = switch(user.getRole()) {
                case ADMIN -> ADMIN_VIEW;
                case TEACHER -> TEACHER_VIEW;
                default -> STUDENT_VIEW;
            };
            toView(view, user.getRole().toString(), loginButton);
        } catch (NotValidFormatException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Not a valid format",
                    e.getMessage()
            );
        } catch (UserNotFoundException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "User not found",
                    e.getMessage()
            );
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        } catch (Exception e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Unexpected error",
                    e.getMessage()
            );
        }
    }
}

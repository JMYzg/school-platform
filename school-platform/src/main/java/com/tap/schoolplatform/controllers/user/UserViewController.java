package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;


public class UserViewController extends ViewController {

    @FXML public Label adminNameLabel;
    @FXML public Button logoutButton, createButton, joinButton, backButton;

    public void onLogoutClick(ActionEvent actionEvent) {
        try {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Performing logout",
                            "Are you sure you want to log out?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK)
                toView(LOGIN_VIEW, "Log in", logoutButton);
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void back(ActionEvent actionEvent) {

    }

    public void createAction(ActionEvent actionEvent) {

    }

    public void joinAction(ActionEvent actionEvent) {

    }
}

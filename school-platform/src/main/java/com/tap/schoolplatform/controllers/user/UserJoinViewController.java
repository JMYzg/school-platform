package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class UserJoinViewController extends UserViewController {

    @FXML
    private TextField idField;

    @FXML
    private Button
            joinButton,
            cancelButton;

    private UserDataViewController mainController;

    @FXML
    public void initialize() {
    }

    @FXML
    private void joinGroupAction(ActionEvent actionEvent) {
        if (idField != null && !idField.getText().isEmpty()) {
            if (!isValidId(idField.getText())) {
                AlertHandler.showAlert(
                        Alert.AlertType.ERROR,
                        "Error joining group",
                        "The id you are trying to join is not valid",
                        "Please try again"
                );
            } else {
                if (Service.find(Integer.parseInt(idField.getText()), Group.class) == null) {
                    AlertHandler.showAlert(
                            Alert.AlertType.ERROR,
                            "Error joining group",
                            "The group you are trying to join does not exist",
                            "Please try again"
                    );
                } else {
                    int groupId = Integer.parseInt(idField.getText());
                    if (isMember()) {
                        AlertHandler.showAlert(
                                Alert.AlertType.ERROR,
                                "Error joining group",
                                "You are already a member of this group",
                                "Please try again"
                        );
                    } else {
                        Group group = Service.find(groupId, Group.class);
                        Membership membership = new Membership(LoginService.getCurrentUser(), group, Role.MEMBER);
                        Service.add(membership);
                        mainController.generateGroupStack();
                        AlertHandler.showAlert(
                                Alert.AlertType.INFORMATION,
                                "Successfully joined group",
                                "You have successfully joined the group",
                                "Please reload your current groups to see " + group.getName() + " in your list of groups"
                        );
                        Stage stage = (Stage) joinButton.getScene().getWindow();
                        stage.close();
                    }
                }
            }
        } else {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error joining group",
                    "An error occurred while joining the group",
                    "Make sure you filled all fields"
            );
        }
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Cancelling joining group",
                        "Are you sure you want to cancel this operation?"
                );
        if (response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isMember() {
        return LoginService.getCurrentUser().getMemberships()
                .stream()
                .anyMatch(membership -> membership.getGroup()
                        .getId() == Integer.parseInt(idField.getText())
                        && membership.getRole() == Role.MEMBER);
    }

    public void setMainController(UserDataViewController controller) {
        this.mainController = controller;
    }


}
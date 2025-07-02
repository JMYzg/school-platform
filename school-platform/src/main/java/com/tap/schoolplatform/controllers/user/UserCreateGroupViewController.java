package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class UserCreateGroupViewController {


    @FXML TextField groupNameField;
    @FXML TextArea descriptionField;
    @FXML ColorPicker colorPicker;
    @FXML Button createButton, cancelButton;

    public void createGroup(ActionEvent actionEvent) {
        if (groupNameField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error creating the group",
                    "An error occurred while creating the group",
                    "Make sure you filled all fields"
            );
        } else {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Creation of group",
                            "Are you sure you want to create this group?"
                    );
            if (response.get() == ButtonType.OK) {
                Group group = new Group(groupNameField.getText(), descriptionField.getText());
                group.setColor(colorPicker.toString());
                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Succesfully",
                        "Group created succesfully",
                        "The group has been created successfully"
                );
                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void cancelCreation(ActionEvent actionEvent) {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Cancelling creation of group",
                        "Are you sure you want to cancel this operation?"
                );
        if (response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }
}

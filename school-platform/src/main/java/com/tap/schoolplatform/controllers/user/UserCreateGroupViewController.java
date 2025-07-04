package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.interfaces.GroupCreatedListener;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class UserCreateGroupViewController {

    @FXML TextField groupNameField;
    @FXML TextArea descriptionField;
    @FXML ColorPicker colorPicker;

    @FXML Button createButton, cancelButton;
    private Group group;

    public UserViewController userViewController;
    private GroupCreatedListener listener;
    private UserDataViewController mainController;

    public void setMainController(UserDataViewController controller) {
        this.mainController = controller;
    }

    public void initialize() {
        createButton.setOnAction(event -> {
            //agregar alertas de confirmación
            handleCreateGroup();
        });
        //falta el de cancel y clear
    }
    public void handleCreateGroup() {
        String groupName = groupNameField.getText();
        String description = descriptionField.getText();
        Color color = colorPicker.getValue();

        if (color == null || groupName == null || description == null) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Empty fields",
                    "All the fields must be required",
                    "Please enter all the fields correctly"
            );
        }else{
            group = new Group(groupName, description);
            Service.add(group);
            Membership membership = new Membership(LoginService.getCurrentUser(), group, Role.OWNER);
            Service.add(membership);
            if(mainController != null){
                mainController.generateGroupStack();
            }
            if (listener != null) {
                listener.onGroupCreated(group);
            }

            mainController.generateGroupStack();
        }

        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }
}

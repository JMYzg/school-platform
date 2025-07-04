package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class UserAssignmentController {
    @FXML Button exitButton1, attachButton, studentEditButton, submmitButton;
    @FXML Label homeworkTitleLabel, deadlineLabel, hourLabel;

    private UserGroupBorderPaneViewController userGroupBorderPaneViewController;
//    private UserListAssignmentsController userListAssignmentsController;

    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.userGroupBorderPaneViewController = userGroupBorderPaneViewController;
    }

//    //defino el controlador que quiero obtener
//    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
//        this.userListAssignmentsController = userListAssignmentsController;
//    }


    @FXML
    public void initialize() {
        exitButton1.setOnAction(e -> {
            try {
                userGroupBorderPaneViewController.setloadCenter("/views/new-interface/user-homework-list-view.fxml");

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    public void closeSubmitHomework(ActionEvent actionEvent) {
    }

    public void studentEditButtonHandler(ActionEvent actionEvent) {
    }

    public void submitHomework(ActionEvent actionEvent) {
    }

    public void attachFiles(ActionEvent actionEvent) {
    }
}

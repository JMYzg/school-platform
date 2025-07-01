package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.subs.UserListAssignmentsController;
import com.tap.schoolplatform.controllers.user.subs.UserNew_EditAssignmentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class UserGroupBorderPaneViewController {

    @FXML public Button membersButton;
    @FXML public Button assignmentsButton;
    @FXML private BorderPane groupBorderPaneContainer;


    @FXML
    public void initialize() {
        try {
            setloadCenter("/views/new-interface/user-list-view.fxml");
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }

    }
    @FXML
    public void loadMembers(ActionEvent actionEvent) {
        try {
            setloadCenter("/views/new-interface/user-list-view.fxml");
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    @FXML
    public void loadAssignments(ActionEvent actionEvent) {
        try {
            setloadCenter("/views/new-interface/user-homework-list-view.fxml");
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void setloadCenter(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserGroupBorderPaneViewController.class.getResource(fxmlPath));
        Parent view = fxmlLoader.load();
        Object controller = fxmlLoader.getController();
        if (controller instanceof UserListAssignmentsController userListAssignmentsController) {
            userListAssignmentsController.setMainController(this);
        } else if (controller instanceof UserNew_EditAssignmentController userNew_EditAssignmentControlle) {
            userNew_EditAssignmentControlle.setMainController(this);
        }
        groupBorderPaneContainer.setCenter(view);
    }

}

package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.subs.UserAssignmentController;
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

    @FXML public Button membersButton, assignmentsButton;
    @FXML private BorderPane groupBorderPaneContainer;

    private UserAssignmentController userAssignmentController;

    private UserNew_EditAssignmentController userNew_EditAssignmentController;

    public void setCreateEditAssignmentController(UserNew_EditAssignmentController userNew_EditAssignmentController) {
        this.userNew_EditAssignmentController = userNew_EditAssignmentController;
    }

    private UserListAssignmentsController userListAssignmentsController;

    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
        this.userListAssignmentsController = userListAssignmentsController;
    }

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
        membersButton.setOnAction(event -> {
            try {
                setloadCenter("/views/new-interface/user-list-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assignmentsButton.setOnAction(event -> {
            try {
                setloadCenter("/views/new-interface/user-homework-list-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    @FXML
    public void loadMembers(ActionEvent actionEvent) {
//        try {
//            setloadCenter("/views/new-interface/user-list-view.fxml");
//        } catch (IOException e) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "Resource not found",
//                    e.getMessage()
//            );
//        }
    }

    @FXML
    public void loadAssignments(ActionEvent actionEvent) {
//        try {
//            setloadCenter("/views/new-interface/user-homework-list-view.fxml");
//        } catch (IOException e) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "Resource not found",
//                    e.getMessage()
//            );
//        }
    }

    public void setloadCenter(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserGroupBorderPaneViewController.class.getResource(fxmlPath));
        Parent view = fxmlLoader.load();
        Object controller = fxmlLoader.getController();

        if (controller instanceof UserListAssignmentsController userListAssignmentsController) {
            userListAssignmentsController.generateAssignmentStack();
            userListAssignmentsController.setMainController(this);
            this.userListAssignmentsController = userListAssignmentsController;

        } else if (controller instanceof UserNew_EditAssignmentController userNew_EditAssignmentControlle) {
            userNew_EditAssignmentControlle.setMainController(this);
            userNew_EditAssignmentControlle.setUserListAssignmentsController(userListAssignmentsController);
        }
        else if (controller instanceof UserAssignmentController userAssignmentController) {
//            userAssignmentController.setUserListAssignmentsController(userListAssignmentsController);
            userAssignmentController.setBorderPaneController(this);
            this.userAssignmentController = userAssignmentController;

        }
        groupBorderPaneContainer.setCenter(view);
    }

}

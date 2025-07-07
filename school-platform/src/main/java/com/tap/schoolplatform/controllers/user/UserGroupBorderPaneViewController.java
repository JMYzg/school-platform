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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class UserGroupBorderPaneViewController {

    @FXML
    private Button membersButton, assignmentsButton;

    @FXML
    private Label
            groupName,
            groupID;

    @FXML
    private TextFlow groupDescription;

    @FXML
    private BorderPane groupBorderPaneContainer;

    private VBox groupVBox;

    public void setAssignmentsContainer(VBox assignmentsContainer) {
        this.groupVBox = assignmentsContainer;
    }

    private UserAssignmentController userAssignmentController;

    private UserNew_EditAssignmentController userNew_EditAssignmentController;

    private UserListAssignmentsController userListAssignmentsController;

    public void setCreateEditAssignmentController(UserNew_EditAssignmentController userNew_EditAssignmentController) {
        this.userNew_EditAssignmentController = userNew_EditAssignmentController;
    }


    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
        this.userListAssignmentsController = userListAssignmentsController;
    }

    public UserListAssignmentsController getUserListAssignmentsController() {
        return userListAssignmentsController;
    }

    @FXML
    public void initialize() {
        groupName.setText(UserViewController.getCurrentGroup().getName());
        groupDescription.getChildren().add(new Text(UserViewController.getCurrentGroup().getDescription()));
        groupID.setText(Integer.toString(UserViewController.getCurrentGroup().getId()));
        try {
            setLoadCenter("/views/new-interface/user-list-view.fxml");
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
                setLoadCenter("/views/new-interface/user-list-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assignmentsButton.setOnAction(event -> {
            try {
                setLoadCenter("/views/new-interface/user-homework-list-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    public void loadMembers(ActionEvent actionEvent) {

    }

    @FXML
    public void loadAssignments(ActionEvent actionEvent) {

    }

    public void setLoadCenter(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserGroupBorderPaneViewController.class.getResource(fxmlPath));
        Parent view = fxmlLoader.load();
        Object controller = fxmlLoader.getController();

        if (controller instanceof UserListAssignmentsController userListAssignmentsController) {
            userListAssignmentsController.generateAssignmentStack();
            userListAssignmentsController.setMainController(this);
            this.userListAssignmentsController = userListAssignmentsController;

        } else if (controller instanceof UserNew_EditAssignmentController userNew_EditAssignmentController) {
            userNew_EditAssignmentController.setMainController(this);
            userNew_EditAssignmentController.setUserListAssignmentsController(userListAssignmentsController);
//            userNew_EditAssignmentController.setUserViewController(userViewController);
        } else if (controller instanceof UserAssignmentController userAssignmentController) {
//            userAssignmentController.setUserListAssignmentsController(userListAssignmentsController);
            userAssignmentController.setBorderPaneController(this);
            this.userAssignmentController = userAssignmentController;
        }
        groupBorderPaneContainer.setCenter(view);
    }
}
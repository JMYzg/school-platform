package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.MainApplication;
import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserAssignmentController {
    public Button gradeButton;
    @FXML Button exitButton1, attachButton, editButton, submmitButton;
    @FXML Label homeworkTitleLabel, deadlineLabel, hourLabel;
    private UserListAssignmentsController userListAssignmentsController;

    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
        this.userListAssignmentsController = userListAssignmentsController;
    }

    private UserGroupBorderPaneViewController mainController;
    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.mainController = userGroupBorderPaneViewController;
    }

    @FXML
    public void initialize() {
        loadAssignment();
        //cuando presiono este botón y regreso a la lista de tareas desaparecen los botones de tareas
        exitButton1.setOnAction(e -> {
            try {
                mainController.setloadCenter("/views/new-interface/user-homework-list-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        attachButton.setOnAction(e -> {
            //función para abrir el explorador de archivos
        });
        if (false) {
            gradeButton.setVisible(false);
            editButton.setVisible(false);
        }



        editButton.setOnAction(e -> {
            try {
                UserNew_EditAssignmentController controller =
                        ViewController.loadNewView(e,"/views/new-interface/user-homework-edit_new.fxml","Edit");
                controller.setMainController(mainController);
                controller.setGroup(UserViewController.getCurrentGroup());
                controller.setUserListAssignmentsController(userListAssignmentsController);
                controller.setAssignmentCreatedListener(updateAssignment ->{
                            mainController.getUserListAssignmentsController().generateAssignmentStack();
                        });
//                controller.setAss
//                openEditAssignment("");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    public void loadAssignment() {
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().toString());
    }

    public void closeSubmitHomework(ActionEvent actionEvent) {
    }


    public void submitHomework(ActionEvent actionEvent) {
    }

    public void attachFiles(ActionEvent actionEvent) {
    }
    public void openEditAssignment(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent root = fxmlLoader.load();
        UserNew_EditAssignmentController controller = fxmlLoader.getController();
    }

    public void editButtonHandler(ActionEvent actionEvent) {
    }

//    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
//        UserViewController.loadNewView(actionEvent,"/views/new-interface/user-homework-edit_new.fxml","");
//    }
}

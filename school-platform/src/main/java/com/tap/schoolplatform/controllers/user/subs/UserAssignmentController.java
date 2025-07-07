package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UserAssignmentController {
    public Button gradeButton;
    public TextFlow instructions;
    public Button deleteButton;
    @FXML
    Button
            exitButton,
            attachButton,
            editButton,
            submmitButton;
    @FXML
    Label
            homeworkTitleLabel,
            deadlineLabel,
            hourLabel;

    private UserViewController userViewController;

//    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
//        this.userListAssignmentsController = userListAssignmentsController;
//    }

    private UserGroupBorderPaneViewController mainController;
    private UserGroupBorderPaneViewController userGroupBorderPaneViewController;

    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.mainController = userGroupBorderPaneViewController;
    }

    @FXML
    public void initialize() {
        setAssigmentData();

        //cuando presiono este botón y regreso a la lista de tareas desaparecen los botones de tareas
        exitButton.setOnAction(e -> {
            try {
                mainController.setLoadCenter("/views/new-interface/user-homework-list-view.fxml");
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
                        ViewController.loadNewView(e, "/views/new-interface/user-homework-edit_new.fxml", "Edit");
                controller.setMainController(mainController);
                controller.setGroup(UserViewController.getCurrentGroup());
//                controller.setUserListAssignmentsController(userListAssignmentsController);
                controller.setAssignmentCreatedListener(updateAssignment -> {
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

    private void setAssigmentData() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        instructions.getChildren().add(new Text(UserViewController.getCurrentAssignment().getDescription()));
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hourLabel.setText(UserViewController.getCurrentAssignment().getDeadline().getHour() + ":" + UserViewController.getCurrentAssignment().getDeadline().getMinute());
    }

    public void closeSubmitHomework(ActionEvent actionEvent) {
    }


    public void submitHomework(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-grade.fxml", "");
    }

    public void attachFiles(ActionEvent actionEvent) {
    }

    public void openEditAssignment(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent root = fxmlLoader.load();
        UserNew_EditAssignmentController controller = fxmlLoader.getController();
    }

    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-edit_new.fxml", "");
        updateAssigmentData();
    }

    public void updateAssigmentData() {
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().toString());
        instructions.getChildren().clear();
        instructions.getChildren().add(new Text(UserViewController.getCurrentAssignment().getDescription()));
    }

    public void deleteAssignment(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Deleting assignment",
                "Are you sure you want to delete this assignment?"
        );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            UserViewController.getCurrentGroup().getAssignments().remove(UserViewController.getCurrentAssignment());
            Service.delete(UserViewController.getCurrentAssignment());
            UserViewController.setCurrentAssignment(null);
            try {
                mainController.getUserListAssignmentsController().generateAssignmentStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AlertHandler.showAlert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Assignment deleted",
                "The assignment has been deleted successfully."
        );
        try {
            mainController.setLoadCenter("/views/new-interface/user-homework-list-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
//        UserViewController.loadNewView(actionEvent,"/views/new-interface/user-homework-edit_new.fxml","");
//    }

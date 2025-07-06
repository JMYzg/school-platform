package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class UserListAssignmentsController {

    @FXML Button addHomeworkButton;
    @FXML VBox homeworkViewsContainer;

    public VBox getHomeworkViewsContainer() {
        return homeworkViewsContainer;
    }

    public UserGroupBorderPaneViewController mainController;

    public void setMainController(UserGroupBorderPaneViewController mainController) {
        this.mainController = mainController;
    }
    public void initialize() {
        generateAssignmentStack();
        addHomeworkButton.setOnAction(event ->
                openCreateAssignView("views/new-interface/user-homework-edit_new.fxml", "Create a Assignment"));
    }
    public void generateAssignmentStack() {
        homeworkViewsContainer.getChildren().clear();
        Group currentGroup = UserViewController.getCurrentGroup();
            List<Assignment> assignments = currentGroup.getAssignments();
            for (Assignment assignment : assignments) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/new-interface/button-assignment.fxml"));
                    Parent button = loader.load();
                    UserButtonAssignmentController controller = loader.getController();
                    controller.setAssigment(assignment);
                    controller.setHomeworkTitle(assignment.getTitle());

                    controller.setOnClick(() -> {
                        UserViewController.setCurrentAssignment(assignment);
                        assignment.setButtonController(controller);
                        try{
                            //esto debe estar para que funcione ver la tarea
                            mainController.setloadCenter("/views/new-interface/user-homework-view.fxml");
//                            mainController.setUserListAssignmentsController(this);

                        } catch (Exception e) {
                            AlertHandler.showAlert(
                                    Alert.AlertType.ERROR,
                                    "Error",
                                    "Resource not found",
                                    e.getMessage()
                            );
                        }
                    });

                    homeworkViewsContainer.getChildren().add(button);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

    }

    private void openCreateAssignView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();
            UserNew_EditAssignmentController controller = loader.getController();
//            controller.setUserListAssignmentsController(this);
            controller.setMainController(mainController);
            //pass current group
            controller.setGroup(UserViewController.getCurrentGroup());
            controller.setAssignmentContainer(this.homeworkViewsContainer);


            //pass Listener
            controller.setAssignmentCreatedListener(newAssignment -> {
                addAssigmentToVBox(newAssignment);
            });

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
            stage.setResizable(false);
//            stage.showAndWait();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addAssigmentToVBox(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/views/new-interface/button-assignment.fxml"));
            Parent assignmentNode = loader.load();

            UserButtonAssignmentController controller = loader.getController();
            controller.setAssigment(assignment);
            controller.setHomeworkTitle(assignment.getTitle());

            homeworkViewsContainer.getChildren().add(assignmentNode);
        }
        catch (Exception e) {

        }
    }

    public void addHomework(ActionEvent actionEvent) {
    }
}

package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
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

    //por que en privado no se puede acceder
    public UserGroupBorderPaneViewController mainController;

    public void setMainController(UserGroupBorderPaneViewController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        //get Group to optain their assignment
        Group currentGroup = UserViewController.getCurrentGroup();
        if (currentGroup != null) {
            List<Assignment> assignments = currentGroup.getAssignments();

            for (Assignment assignment : assignments) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/new-interface/user-homework-container.fxml"));
                    Parent button = loader.load();
                    UserButtonAssignmentController controller = loader.getController();
                    controller.setAssigment(assignment);

                    controller.setOnClick(() -> {
                        UserViewController.setCurrentAssignment(assignment);
                        try{
                            mainController.setloadCenter("/views/new-interface/user-homework-view.fxml");
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

        addHomeworkButton.setOnAction(event ->
                openInNewWindow("views/new-interface/user-homework-edit_new.fxml", "Create a Assignment"));
    }



    private void openInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();

            UserNew_EditAssignmentController controller = loader.getController();
            controller.setMainController(mainController);
            //pass current group
            controller.setGroup(UserViewController.getCurrentGroup());
            controller.setAssignmentContainer(homeworkViewsContainer);

            //pass Listener
            controller.setAssignmentCreatedListener(newAssignment -> {
                addAssigmentToVBox(newAssignment);
            });


            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
//            stage.showAndWait();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addAssigmentToVBox(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/views/new-interface/user-homework-container.fxml"));
            Parent assignmentNode = loader.load();

            UserButtonAssignmentController controller = loader.getController();
            controller.setAssigment(assignment);

            homeworkViewsContainer.getChildren().add(assignmentNode);
        }
        catch (Exception e) {

        }
    }

    public void addHomework(ActionEvent actionEvent) {
    }
}

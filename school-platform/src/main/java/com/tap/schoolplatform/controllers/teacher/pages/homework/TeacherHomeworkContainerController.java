package com.tap.schoolplatform.controllers.teacher.pages.homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class TeacherHomeworkContainerController {

    public static final String CONTAINER_PATH = "/views/teacher-views/teacher-option-homework-container-view.fxml";

    public Button editButton;
    public Button gradeButton;
    public Label homeworkTitle;
    public Label creationDateLabel;
    public Label deadLineLabel;
    public Label deadLineTimeLabel;
    private Assignment assignment;

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setTitle(String title) {
        homeworkTitle.setText(title);
    }

    public void setDueDate(LocalDateTime deadline) {
        deadLineLabel.setText(deadline.toString());
    }

    public void setCreationDate(LocalDateTime creationDate) {
        creationDateLabel.setText(creationDate.toString());
    }


    public void editHomework(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(TeacherHomeworkNewController.PATH));
            Parent root = loader.load();
            TeacherHomeworkNewController controller = loader.getController();
            controller.setAssignment(assignment);

            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            //
             VBox homeworkContainer = (VBox) currentStage.getScene().lookup("#homework-container");
             if(homeworkContainer != null) {
                 TeacherHomeworkController parentController = (TeacherHomeworkController) homeworkContainer.getProperties().get("parentController");
                    if(parentController != null) {
                        // Pasar el containerMap al controlador de edición
                        controller.setContainerMap(parentController.getContainerMap());

                    } else {
                        System.err.println("Parent controller not found");
                    }
             }
            //


            Stage stage = new Stage();
            stage.setTitle("Edit Homework");
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Este On action esta pendiente
    public void openHomeworkGrades(ActionEvent actionEvent) {
    }
}

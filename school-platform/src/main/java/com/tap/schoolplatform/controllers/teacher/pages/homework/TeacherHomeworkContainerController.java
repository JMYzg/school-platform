package com.tap.schoolplatform.controllers.teacher.pages.homework;

import com.tap.schoolplatform.models.academic.tasks.Assignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class TeacherHomeworkContainerController extends VBox {

    public static final String CONTAINER_PATH = "/views/teacher-views/teacher-option-homework-container-view.fxml";

    public Button editButton;
    public Button gradeButton;
    public Label homeworkTitle;
    public Label creationDateLabel;
    public Label deadLineLabel;
    private Assignment assignment;

//    private final VBox homeworkViewsContainer;
//
//    public TeacherHomeworkContainerController(VBox homeworkViewsContainer) {
//        this.homeworkViewsContainer = homeworkViewsContainer;
//    }
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

//            controller.setHomeworkViewContainer(homeworkViewsContainer);

            Stage stage = new Stage();
            stage.setTitle("Edit Homework");
            stage.setScene(new Scene(root));

//          stage.getScene().setUserData(this.getParentController());
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private TeacherHomeworkController getParentController() {
//        return (TeacherHomeworkController) homeworkViewsContainer.getScene().getWindow().getUserData();
//    }

//    public void updateAssignmentView(Assignment assignment) {
//        setTitle(assignment.getTitle());
//        setDueDate(assignment.getDeadline());
//        setCreationDate(assignment.getCreationDate());
//    }

    public void openHomeworkGrades(ActionEvent actionEvent) {
    }
}

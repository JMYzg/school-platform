package com.tap.schoolplatform.controllers.student.pages.homework;

import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.academic.tasks.Task;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class StudentHomeworkViewController {
    @FXML private Button homeworkContainerButton;

    @FXML private AnchorPane homeworkContainerAnchorPane;

    @FXML private VBox homeworkdsContainerVBox;

    Student currentStudent = (Student) LoginService.getCurrentUser();

    private void initialize() {
//        loadTasks();
    }

//    private void loadTasks() {
//        for (Integer unit : currentStudent.getSemester().getDegree().getClass()./*currentSubject.getTaskListMap().keySet()*/) {
//            for (Task task : currentSubject.getTaskListMap().get(unit)) {
//                if (task instanceof Assignment assignment) {
//                    try {
//                        // Load the FXML for each evaluation
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/student-views/student-homework-container-view.fxml"));
//                        Parent examView = loader.load();
//
//                        // Get the controller and set the evaluation
//                        HomeworkContainerController controller = loader.getController();
//                        controller.setAssignment(assignment);
//
//                        // Create a container with a remove button
//                        AnchorPane assignmentContainer = new AnchorPane();
//                        assignmentContainer.getChildren().add(examView);
//
////                        Button removeButton = new Button("Remove");
////                        removeButton.setOnAction(e -> homeworkViewsContainer.getChildren().remove(assignmentContainer));
////                        assignmentContainer.getChildren().add(removeButton);
////
////                        AnchorPane.setTopAnchor(removeButton, 5.0);
////                        AnchorPane.setRightAnchor(removeButton, 5.0);
//
//                        // Add to the container
//                        homeworkViewsContainer.getChildren().add(assignmentContainer);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    private void openHomework(ActionEvent actionEvent) throws IOException {
        loadNewView(actionEvent, "/views/student-views/student-homework-summit-view.fxml", "Homework");
    }
}

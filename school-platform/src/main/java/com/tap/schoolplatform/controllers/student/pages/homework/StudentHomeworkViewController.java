package com.tap.schoolplatform.controllers.student.pages.homework;

import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.academic.tasks.Exam;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    @FXML private void initialize() {
        loadTasks();
    }

    private void loadTasks() {
        loadAssignments();
        loadExams();

//        for (Subject subject : currentStudent.getSemester().getSubjects()) {
//            for (Assignment assignment : subject.getAllAssignments()) {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource(HomeworkContainerViewController.PATH));
//                    Node node = loader.load();
//                    HomeworkContainerViewController controller = loader.getController();
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    private void loadAssignments() {
        for (Subject subject : currentStudent.getSemester().getSubjects()) {
            for (Assignment assignment : subject.getAllAssignments()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HomeworkContainerViewController.PATH));
                    Node node = fxmlLoader.load();
                    HomeworkContainerViewController controller = fxmlLoader.getController();
                    controller.homeworkTitle.setText(assignment.getTitle());
                    controller.homeworkDeadline.setText(assignment.getDeadline().toString());
                    controller.homeworkSubject.setText(assignment.getUnit().getSubject().getName());
                    homeworkdsContainerVBox.getChildren().add(node);
                    String description = assignment.getDescription();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadExams() {
        for (Subject subject : currentStudent.getSemester().getSubjects()) {
            for (Exam exam : subject.getAllExams()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HomeworkContainerViewController.PATH));
                    Node node = fxmlLoader.load();
                    HomeworkContainerViewController controller = fxmlLoader.getController();
                    controller.homeworkTitle.setText(exam.getTitle());
                    controller.homeworkDeadline.setText(exam.getDeadline().toString());
                    controller.homeworkSubject.setText(exam.getUnit().getSubject().getName());
                    homeworkdsContainerVBox.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void openHomework(ActionEvent actionEvent) throws IOException {
        loadNewView(actionEvent, "/views/student-views/student-homework-summit-view.fxml", "Homework");

    }
}

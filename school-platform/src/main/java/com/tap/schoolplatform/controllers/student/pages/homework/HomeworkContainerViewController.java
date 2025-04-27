package com.tap.schoolplatform.controllers.student.pages.homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class HomeworkContainerViewController {
    @FXML private Button homeworkButton;

    @FXML private Label
            homeworkTitle,
            homeworkDeadline,
            homeworkSubject;

    @FXML private Button openHomeworkButton;

    public void openHomework(ActionEvent actionEvent) throws IOException {
        loadNewView(actionEvent, "/views/student-views/student-homework-summit-view.fxml", "Homework");
    }
}

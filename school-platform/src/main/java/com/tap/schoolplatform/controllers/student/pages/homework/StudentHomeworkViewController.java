package com.tap.schoolplatform.controllers.student.pages.homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class StudentHomeworkViewController {
    @FXML private Button homeworkContainerButton;

    @FXML private AnchorPane homeworkContainerAnchorPane;

    @FXML private VBox homeworkdsContainerVBox;

    public void openHomework(ActionEvent actionEvent) throws IOException {
        loadNewView(actionEvent, "/views/student-views/student-homework-summit-view.fxml", "Homework");

    }
}

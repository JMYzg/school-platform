package com.tap.schoolplatform.controllers.teacher.pages.homework;


import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class TeacherHomeworkController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-homework-view.fxml";

    @FXML private Button addHomeworkButton;

    @FXML private VBox homeworkViewsContainer;

    @FXML private AnchorPane anchorPaneHomeworkContainer;

    public Subject subject;

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    @FXML private void addHomework(ActionEvent event) throws IOException {
        loadNewView(event, TeacherHomeworkNewController.PATH, "Create new exam");
    }
}

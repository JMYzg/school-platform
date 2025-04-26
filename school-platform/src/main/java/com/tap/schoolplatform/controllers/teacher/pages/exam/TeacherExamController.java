package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TeacherExamController extends ViewController {

    @FXML Button createNewExamButton;

    @FXML VBox examViewsContainer;

    @FXML AnchorPane anchorPaneExamContainer;

    private Subject subject;

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    @FXML private void createNewExam(ActionEvent event) throws IOException {
        loadNewView(event, "/views/teacher-views/teacher-option-exam-new-view.fxml", "Create new exam");
    }
}

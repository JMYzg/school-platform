package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.models.academic.tasks.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TeacherExamContainerController {

    public static String PATH = "/views/teacher-views/teacher-option-exam-container-view.fxml";

    private Unit unit;

    @FXML Button
            editButton,
            gradeButton;
    public Label
            examTitleLabel,
            statusLabel,
            dayOfAplicationLabel,
            timeLabel,
            durationLabel;

    public void editExam(ActionEvent actionEvent) {
        TeacherExamNewController.exam = unit.getExam();

    }

    public void openExamGrades(ActionEvent actionEvent) {

    }


}

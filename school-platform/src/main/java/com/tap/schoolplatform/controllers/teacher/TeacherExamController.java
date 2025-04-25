package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.models.academic.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TeacherExamController {


    @FXML Label groupNameLabel, semesterLabel,shiftLabel;
    @FXML Button createNewExamButton;
    @FXML VBox examViewsContainer;
    @FXML AnchorPane anchorPaneExamContainer;


    private Group group;

    public void setGroup(Group group){
        this.group = group;

        updateUI();
    }

    private void updateUI(){
        String nameGroup = group.getID();
        groupNameLabel.setText(nameGroup);
        String semester = group.getSemester().toString();
        semesterLabel.setText(semester);
        String shift = group.getShift().toString();
        shiftLabel.setText(shift);

    }

    public void createNewExam() {
    }
}

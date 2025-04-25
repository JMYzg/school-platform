package com.tap.schoolplatform.controllers.teacher;


import com.tap.schoolplatform.models.academic.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TeacherHomeworkController {

    @FXML public Label groupName, semesterName, shiftName;
    @FXML Button addHomeworkButton;
    @FXML VBox homeworkViewsContainer;
    @FXML AnchorPane anchorPaneHomeworkContainer;

    private Group group;
    public void setGroup(Group group){
        this.group = group;

        updateUI();
    }
    private void updateUI(){
        String nameGroup = group.getID();
        groupName.setText(nameGroup);
        String semester = group.getSemester().toString();
        semesterName.setText(semester);
        String shift = group.getShift().toString();
        shiftName.setText(shift);

    }

    public void addHomework() {
    }
}

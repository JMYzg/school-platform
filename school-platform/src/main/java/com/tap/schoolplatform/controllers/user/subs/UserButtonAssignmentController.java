package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.models.academic.tasks.Assignment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserButtonAssignmentController {
    @FXML public Button assignmentButton;
    @FXML public Label
            homeworkTitle,
            homeworkDeadline,
            points,
            creationDate;
    private Assignment assignment;

    public void setAssigment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle.setText(homeworkTitle);
    }
    public String getHomeworkTitle() {
        return homeworkTitle.getText();
    }

    public void setHomeworkDeadline(String homeworkDeadline) {
        this.homeworkDeadline.setText(homeworkDeadline);
    }

    public String getHomeworkDeadline() {
        return homeworkDeadline.getText();
    }

    public void setPoints(String points) {
        this.points.setText(points);
    }
    public String getPoints() {
        return points.getText();
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.setText(creationDate);
    }

    public String getCreationDate() {
        return creationDate.getText();
    }

}

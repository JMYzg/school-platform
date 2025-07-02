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
    private Runnable OnClick;

    public void setAssigment(Assignment assignment) {
        this.assignment = assignment;
        homeworkTitle.setText(assignment.getTitle());
        homeworkDeadline.setText(assignment.getDeadline().toString());

        assignmentButton.setOnAction(event -> {
            if(OnClick != null) OnClick.run();
        });
        //agregar resto
        //creationDate.setText(assignment.getCreationDate().toString());
    }

    public void setHomeworkTitle(String title) {
        homeworkTitle.setText(title);
    }

    public void setOnClick(Runnable onClick) {
        this.OnClick = onClick;
    }

    public Assignment getAssignment() {
        return assignment;
    }

}

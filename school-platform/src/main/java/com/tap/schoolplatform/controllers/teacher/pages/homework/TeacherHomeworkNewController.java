package com.tap.schoolplatform.controllers.teacher.pages.homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TeacherHomeworkNewController {

    @FXML private TextField titleTF;

    @FXML private TextArea descriptionTF;

    @FXML private DatePicker datePicker;

    @FXML private Spinner spinnerHour;
    @FXML private Spinner spinnerMinute;

    @FXML private Button
            cleanButton,
            cancelButton,
            acceptButton;

    @FXML private void cancelHomework(ActionEvent actionEvent) {
    }

    @FXML private void createHomework(ActionEvent actionEvent) {
    }

    @FXML private void clearAll(ActionEvent actionEvent) {
    }

}

package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class TeacherExamNewController {

    @FXML private Button
            submitExamButton,
            btnSetTittleExam,
            addQuestionButton;

    @FXML private TextField
            titleTextField,
            newExamUnit,
            titleQuestion,
            examOption1TF,
            examOption2TF,
            examOption3TF,
            examOption4TF;

    @FXML private Label titleLabel;

    @FXML private DatePicker datePicker;
    @FXML private Spinner spinnerHourDte;
    @FXML private Spinner spinnerMinuteDte;
    @FXML private ToggleGroup radioGroup;
    @FXML private RadioButton
            rightAnswer1RadioButton,
            rightAnswer2RadioButton,
            rightAnswer3RadioButton,
            rightAnswer4RadioButton;

    @FXML private TableView tableQuestions;
    @FXML private TableColumn questionNumberTableColumn;
    @FXML private TableColumn questionTableColumn;

    public void handleSubmitExam(ActionEvent actionEvent) {
        Optional<ButtonType> response =
        AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "",
                ""
        );
        if (response.isPresent() && response.get() == ButtonType.OK ) {
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "",
                    "",
                    ""
            );
        }
    }

    public void setExamTitle(ActionEvent actionEvent) {
    }

    public void addQuestion(ActionEvent actionEvent) {
    }
}

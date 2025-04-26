package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.tasks.Question;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.Optional;

public class TeacherExamNewController {

    public static final String PATH = "/views/teacher-views/teacher-option-exam-new-view.fxml";

    private boolean isEditing = true;

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
    @FXML private Spinner<Integer> spinnerHourDte;
    @FXML private Spinner<Integer> spinnerMinuteDte;
    @FXML private ToggleGroup radioGroup;
    @FXML private RadioButton
            rightAnswer1RadioButton,
            rightAnswer2RadioButton,
            rightAnswer3RadioButton,
            rightAnswer4RadioButton;

    @FXML private TableView<Question> tableQuestions;
    @FXML private TableColumn<Question, Integer> questionNumberTableColumn;
    @FXML private TableColumn<Question, String> questionTableColumn;

    @FXML private void initialize() {
        spinnerConfiguration(spinnerHourDte, 23);
        spinnerConfiguration(spinnerMinuteDte, 59);
    }

    public void handleSubmitExam() {
        Optional<ButtonType> response =
        AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Are you sure you want to submit exam?",
                "You'll be able to edit the exam details later"
        );
        if (response.isPresent() && response.get() == ButtonType.OK ) {
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Exam created",
                    "The exam has been submitted",
                    "Click OK to continue"
            );
        }
    }

    public void setExamTitle() {
        if (isEditing) {
            titleTextField.setVisible(false);
            titleLabel.setText(titleTextField.getText());
            titleLabel.setVisible(true);
            btnSetTittleExam.setText("EDIT");
        } else {
            titleTextField.setVisible(true);
            titleLabel.setVisible(false);
            btnSetTittleExam.setText("OK");
        }
        isEditing = !isEditing;
    }

    public void addQuestion() {
    }

    private void spinnerConfiguration(Spinner<Integer> spinner, int max) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max);
        valueFactory.setValue(0);

        valueFactory.setConverter(new StringConverter<Integer>() {

            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });
        spinner.setValueFactory(valueFactory);
    }

}

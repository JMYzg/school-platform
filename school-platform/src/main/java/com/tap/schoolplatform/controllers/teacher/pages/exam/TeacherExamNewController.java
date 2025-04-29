package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.admin.AdminViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.models.academic.tasks.Answer;
import com.tap.schoolplatform.models.academic.tasks.Exam;
import com.tap.schoolplatform.models.academic.tasks.Question;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TeacherExamNewController extends TeacherViewPage {

    public static final String PATH = "/views/teacher-views/teacher-option-exam-new-view.fxml";

    private Exam exam;

    @FXML private Button
            submitButton,
            editButton,
            addButton;

    @FXML private TextField
            titleField,
            unitField,

            questionField,
            answer1Field,
            answer2Field,
            answer3Field,
            answer4Field;

    @FXML private Label titleLabel;

    @FXML private DatePicker datePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private ToggleGroup toggleGroup;
    @FXML private RadioButton
            radio1Button,
            radio2Button,
            radio3Button,
            radio4Button;

    @FXML private TableView<Question> tableView;
    @FXML private TableColumn<Question, Integer> numberColumn;
    @FXML private TableColumn<Question, String> questionColumn;

    private VBox vBox;

    private final TextField[] answerFields = {
            answer1Field,
            answer2Field,
            answer3Field,
            answer4Field
    };

    private final RadioButton[] radioButtons = {
            radio1Button,
            radio2Button,
            radio3Button,
            radio4Button
    };

    @FXML private void initialize() {
        spinnerConfiguration(hourSpinner, 23);
        spinnerConfiguration(minuteSpinner, 59);
        if (exam != null) {
            fillExamForm();
            tableView.getItems().setAll(exam.getQuestions());
            unitField.setDisable(true);
            unitField.setEditable(true);
            tableView.refresh();
        }
        bindColumns();
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    private void fillExamForm() {
        titleField.setText(exam.getTitle());
        unitField.setText(exam.getUnit().toString());
        datePicker.setValue(exam.getDeadline().toLocalDate());
        hourSpinner.getValueFactory().setValue(exam.getDeadline().getHour());
        minuteSpinner.getValueFactory().setValue(exam.getDeadline().getMinute());
    }

    private void fillQuestionForm() {
        Question question = tableView.getSelectionModel().getSelectedItem();
        if (question != null) {
            questionField.setText(question.getDescription());
            for (int i = 0; i < answerFields.length; i++) {
                answerFields[i].setText(question.getAnswers().get(i).getText());
            }
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    toggleGroup.selectToggle(radioButtons[question.getAnswers().indexOf(answer)]);
                    break;
                }
            }
        }
    }

    private void clearQuestionForm() {
        questionField.clear();
        for (TextField textField : answerFields) textField.clear();
        toggleGroup.getSelectedToggle().setSelected(false);
    }

    private void disableQuestionForm(boolean toggle) {
        questionField.setDisable(toggle);
        for (TextField textField : answerFields) textField.setDisable(toggle);
        for (RadioButton radioButton : radioButtons) radioButton.setDisable(toggle);
    }

    public void setVBox(VBox vBox) {
        this.vBox = vBox;
    }

    public void handleSubmitExam() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Are you sure you want to submit the exam?",
                        "You'll be able to edit the exam details later"
                );
        if (response.isPresent() && response.get() == ButtonType.OK ) {
            if (exam == null) {
                Exam exam = new Exam(
                        subject.getUnit(Integer.parseInt(unitField.getText().trim())),
                        titleField.getText().trim(),
                        "",
                        LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue()))
                );

                for(Question question : tableView.getItems()) {
                    if (!exam.getQuestions().contains(question)) {
                        exam.addQuestion(question);
                        question.setExam(exam);
                    }
                }

                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Exam created",
                        "The exam has been submitted",
                        "Click OK to continue"
                );
                addExamContainer(exam);
            } else {
                addExamContainer(exam);
            }
            exam = null;
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setExamTitle() {
//        if (isEditing) {
//            titleField.setVisible(false);
//            titleLabel.setText(titleField.getText());
//            titleLabel.setVisible(true);
//            datePicker.setDisable(true);
//            hourSpinner.setDisable(true);
//            minuteSpinner.setDisable(true);
//            editButton.setText("EDIT");
//        } else {
//            titleField.setVisible(true);
//            titleLabel.setVisible(false);
//            editButton.setText("OK");
//            if (exam == null) datePicker.setDisable(false);
//            hourSpinner.setDisable(false);
//            minuteSpinner.setDisable(false);
//            if (exam != null) updateExam();
//        }
//        isEditing = !isEditing;
    }

    public void addQuestion() {
        switch(addButton.getText()) {
            case "Add New Question":
                try {
                    validateForm();
                    Question question =
                            new Question(
                                    null,
                                    tableView.getItems().size(),
                                    questionField.getText().trim()
                            );

                    question.setAnswers(
                            FXCollections.observableArrayList(
                                    List.of(
                                            new Answer(
                                                    question,
                                                    answer1Field.getText().trim(),
                                                    radio1Button.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    answer2Field.getText().trim(),
                                                    radio2Button.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    answer3Field.getText().trim(),
                                                    radio3Button.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    answer4Field.getText().trim(),
                                                    radio4Button.isSelected()
                                            )
                                    )
                            )
                    );
                    tableView.getItems().add(question);
                    tableView.refresh();
                    clearQuestionForm();
                } catch (NotValidFormatException e) {
                    AlertHandler.showAlert(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Not a valid format",
                            e.getMessage()
                    );
                }
                break;
            case "Edit Question":
                disableQuestionForm(false);
                tableView.setDisable(true);
                addButton.setText("OK");
                break;
            case "OK":
                updateQuestion(tableView.getSelectionModel().getSelectedItem());
                clearQuestionForm();
                tableView.setDisable(false);
                addButton.setText("Add New Question");
                break;
        }
    }

    private void updateQuestion(Question question) {
        if (!question.getDescription().equals(questionField.getText().trim())) question.setDescription(questionField.getText().trim());
//        if (!question.getAnswers().get(0).getText().equals(examOption1TF.getText().trim())) question.getAnswers().get(0).setText(examOption1TF.getText().trim());
//        if (!question.getAnswers().get(1).getText().equals(examOption2TF.getText().trim())) question.getAnswers().get(1).setText(examOption2TF.getText().trim());
//        if (!question.getAnswers().get(2).getText().equals(examOption3TF.getText().trim())) question.getAnswers().get(2).setText(examOption3TF.getText().trim());
//        if (!question.getAnswers().get(3).getText().equals(examOption4TF.getText().trim())) question.getAnswers().get(3).setText(examOption4TF.getText().trim());
        RadioButton[] radioButtons = {
                radio1Button,
                radio2Button,
                radio3Button,
                radio4Button
        };
        for (int i = 0; i < radioButtons.length; i++) {
            if (!question.getAnswers().get(i).getText().equals(radioButtons[i].getText().trim())) question.getAnswers().get(i).setText(radioButtons[i].getText().trim());
            question.getAnswers().get(i).setCorrect(radioButtons[i].isSelected());
        }
        tableView.refresh();
    }

    private void updateExam() {
        if (!exam.getDescription().equals(titleField.getText().trim())) exam.setDescription(titleField.getText().trim());
        if (!exam.getDeadline().equals(LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue()))))
            exam.setDeadline(LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue())));
    }

    @FXML private void questionSelectionHandler() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            fillQuestionForm();
            disableQuestionForm(true);
            addButton.setText("Edit Question");
        }
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

    private void validateForm() throws NotValidFormatException {
        validateText(questionField, "Question is required");
        validateText(answer1Field, "Option 1 is required");
        validateText(answer2Field, "Option 2 is required");
        validateText(answer3Field, "Option 3 is required");
        validateText(answer4Field, "Option 4 is required");
        if (toggleGroup.getSelectedToggle() == null) throw new NotValidFormatException("You must select one option as correct");
    }

    private void validateText(TextField field, String message) throws NotValidFormatException {
        if (field.getText().trim().isEmpty()) throw new NotValidFormatException(message);
    }

    private void addExamContainer(Exam exam) {
        try{
            FXMLLoader Loader = new FXMLLoader(getClass().getResource(TeacherExamContainerController.PATH));

            Node view = Loader.load();

            TeacherExamContainerController controller = Loader.getController();
            controller.examTitleLabel.setText(exam.getTitle());
            controller.dayOfAplicationLabel.setText(exam.getDeadline().toLocalDate().toString());
            controller.timeLabel.setText(exam.getDeadline().toLocalTime().toString());
            controller.durationLabel.setText("-");
            controller.asociatedExam = exam;
//            if (TeacherExamNewController.exam == null) {
//                vBox.getChildren().add(view);
//                TeacherExamController.exams.add(exam);
//            } else {
//                controller.asociatedExam = TeacherExamNewController.exam;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editContainer() {

    }

    private void bindColumns() {
        TableColumn<?, ?>[] tableColumns = {
                numberColumn,
                questionColumn
        };

        String[] properties = {
                "number",
                "description"
        };
        AdminViewController.injectCellValues(tableColumns, properties);
    }
}

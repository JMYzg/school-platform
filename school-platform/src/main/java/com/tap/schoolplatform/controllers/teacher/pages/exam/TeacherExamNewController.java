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

    @FXML private TextField
            titleField,
            unitField,

            questionField,

            answer1Field,
            answer2Field,
            answer3Field,
            answer4Field;

    @FXML private Label titleLabel;

    @FXML private Button
            submitButton,
            editButton,
            addButton;

    @FXML private DatePicker datePicker;

    @FXML private Spinner<Integer>
            hourSpinner,
            minuteSpinner;

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
    private Exam exam;

    private TeacherExamContainerController controller;

    private TextField[] answerFields;

    private RadioButton[] radioButtons;

    @FXML private void initialize() {

        answerFields = new TextField[]{
                answer1Field,
                answer2Field,
                answer3Field,
                answer4Field
        };

        radioButtons = new RadioButton[]{
                radio1Button,
                radio2Button,
                radio3Button,
                radio4Button
        };

        spinnerConfiguration(hourSpinner, 23);
        spinnerConfiguration(minuteSpinner, 59);
        bindColumns();
    }

    @FXML private void submitButtonHandler() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Are you sure you want to submit the exam?",
                        "You'll be able to edit the exam details later"
                );
        if (response.isPresent() && response.get() == ButtonType.OK ) {
            if (this.exam == null) {
                Exam exam = new Exam(
                        subject.getUnit(Integer.parseInt(unitField.getText().trim())),
                        titleField.getText().trim(),
                        "-",
                        LocalDateTime.of(
                                datePicker.getValue(),
                                LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue())
                        )
                );

                updateQuestions(exam);

                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Successful submission",
                        "The exam has been submitted",
                        "Click OK to continue"
                );
                createContainer(exam);
            } else {
                updateExam();
                updateQuestions(exam);
                updateContainer();
            }

//            exam = null;
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void editButtonHandler() {
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
        switch (editButton.getText()) {
            case "OK":
                disableExamForm(true);
                editButton.setText("Edit");
                break;
            case "Edit":
                disableExamForm(false);
                updateExam();
                editButton.setText("OK");
                break;
            default:
                break;
        }
    }

    @FXML private void addQuestion() {
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

    @FXML private void questionSelectionHandler() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            fillQuestionForm();
            disableQuestionForm(true);
            addButton.setText("Edit Question");
        }
    }

    public void setVBox(VBox vBox) {
        this.vBox = vBox;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        fillExamForm();
        tableView.getItems().setAll(exam.getQuestions());
        unitField.setDisable(true);
        unitField.setEditable(true);
        tableView.refresh();
    }

    public void setController(TeacherExamContainerController controller) {
        this.controller = controller;
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

    private void createContainer(Exam exam) {
        try{
            FXMLLoader Loader = new FXMLLoader(getClass().getResource(TeacherExamContainerController.PATH));

            Node view = Loader.load();

            TeacherExamContainerController controller = Loader.getController();
            controller.titleLabel.setText(exam.getTitle());
            controller.deadLineLabel.setText(exam.getDeadline().toLocalDate().toString());
            controller.hourLabel.setText(exam.getDeadline().toLocalTime().toString());
            controller.durationLabel.setText("-");
            controller.setAttachedExam(exam);

            vBox.getChildren().add(view);
            TeacherExamController.exams.add(exam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateContainer() {
        if (controller == null) return;
        controller.setAttachedExam(exam);
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

    private void updateExam() {
        if (exam == null) return;
        if (!exam.getDescription().equals(titleField.getText().trim())) exam.setTitle(titleField.getText().trim());
        if (!exam.getDeadline().equals(LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue())))) {
            exam.setDeadline(LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue())));
        }
    }

    private void updateQuestion(Question question) {
        if (question == null) return;
        if (!question.getDescription().equals(questionField.getText().trim())) question.setDescription(questionField.getText().trim());
        for (int i = 0; i < radioButtons.length; i++) {
            if (!question.getAnswers().get(i).getText().equals(radioButtons[i].getText().trim())) question.getAnswers().get(i).setText(radioButtons[i].getText().trim());
            question.getAnswers().get(i).setCorrect(radioButtons[i].isSelected());
        }
        tableView.refresh();
    }

    private void updateQuestions(Exam exam) {
        for(Question question : tableView.getItems()) {
            if (!exam.getQuestions().contains(question)) {
                exam.addQuestion(question);
                question.setExam(exam);
            }
        }
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

    private void disableExamForm(boolean toggle) {
        titleField.setDisable(toggle);
        if (exam == null) unitField.setDisable(toggle);
        datePicker.setDisable(toggle);
        hourSpinner.setDisable(toggle);
        minuteSpinner.setDisable(toggle);
    }

    private void disableQuestionForm(boolean toggle) {
        questionField.setDisable(toggle);
        for (TextField textField : answerFields) textField.setDisable(toggle);
        for (RadioButton radioButton : radioButtons) radioButton.setDisable(toggle);
    }

    private void clearQuestionForm() {
        questionField.clear();
        for (TextField textField : answerFields) textField.clear();
        toggleGroup.getSelectedToggle().setSelected(false);
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
}

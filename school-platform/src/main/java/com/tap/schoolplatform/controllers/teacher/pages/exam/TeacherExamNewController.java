package com.tap.schoolplatform.controllers.teacher.pages.exam;

import com.tap.schoolplatform.controllers.admin.AdminViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.controllers.teacher.pages.homework.TeacherHomeworkContainerController;
import com.tap.schoolplatform.models.academic.tasks.Answer;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.academic.tasks.Exam;
import com.tap.schoolplatform.models.academic.tasks.Question;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    public static Exam exam;

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

    private VBox examContainer;

    @FXML private void initialize() {
        spinnerConfiguration(spinnerHourDte, 23);
        spinnerConfiguration(spinnerMinuteDte, 59);

        if (exam != null) {
            fillExamForm();
            tableQuestions.getItems().setAll(exam.getQuestions());
            newExamUnit.setDisable(true);
            newExamUnit.setEditable(true);
        }

        bindColumns();

//        tableQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//           if (newValue != null) {
//               fillQuestionForm();
//           }
//        });
    }

    private void fillExamForm() {
        titleTextField.setText(exam.getTitle());
        newExamUnit.setText(exam.getUnit().toString());
        datePicker.setValue(exam.getDeadline().toLocalDate());
        spinnerHourDte.getValueFactory().setValue(exam.getDeadline().getHour());
        spinnerMinuteDte.getValueFactory().setValue(exam.getDeadline().getMinute());
    }

    private void fillQuestionForm() {
        Question question = tableQuestions.getSelectionModel().getSelectedItem();
        if (question != null) {
            titleQuestion.setText(question.getDescription());
            examOption1TF.setText(question.getAnswers().get(0).getText());
            examOption2TF.setText(question.getAnswers().get(1).getText());
            examOption3TF.setText(question.getAnswers().get(2).getText());
            examOption4TF.setText(question.getAnswers().get(3).getText());
            RadioButton[] radioButtons = {
                    rightAnswer1RadioButton,
                    rightAnswer2RadioButton,
                    rightAnswer3RadioButton,
                    rightAnswer4RadioButton
            };
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    radioGroup.selectToggle(radioButtons[question.getAnswers().indexOf(answer)]);
                    break;
                }
            }
        }
    }

    private void clearQuestionForm() {
        titleQuestion.clear();
        examOption1TF.clear();
        examOption2TF.clear();
        examOption3TF.clear();
        examOption4TF.clear();

//        RadioButton[] radioButtons = {
//                rightAnswer1RadioButton,
//                rightAnswer2RadioButton,
//                rightAnswer3RadioButton,
//                rightAnswer4RadioButton
//        };
//
//        for (RadioButton radioButton : radioButtons) {
//            ;
//        }
        radioGroup.getSelectedToggle().setSelected(false);
    }

    private void disableQuestionForm(boolean toggle) {
        titleQuestion.setDisable(toggle);
        examOption1TF.setDisable(toggle);
        examOption2TF.setDisable(toggle);
        examOption3TF.setDisable(toggle);
        examOption4TF.setDisable(toggle);

        RadioButton[] radioButtons = {
                rightAnswer1RadioButton,
                rightAnswer2RadioButton,
                rightAnswer3RadioButton,
                rightAnswer4RadioButton
        };

        for (RadioButton radioButton : radioButtons) {
            radioButton.setDisable(toggle);
        }
    }

    public void setExamContainer(VBox container) {
        examContainer = container;
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
            if (exam == null) {
                Exam exam = new Exam(
                        subject.getUnit(Integer.parseInt(newExamUnit.getText().trim())),
                        titleTextField.getText().trim(),
                        "",
                        LocalDateTime.of(datePicker.getValue(), LocalTime.of(spinnerHourDte.getValue(), spinnerMinuteDte.getValue()))
                );

                for(Question question : tableQuestions.getSelectionModel().getSelectedItems()) {
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
            }
            TeacherExamNewController.exam = null;
            Stage stage = (Stage) submitExamButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setExamTitle() {
        if (isEditing) {
            titleTextField.setVisible(false);
            titleLabel.setText(titleTextField.getText());
            titleLabel.setVisible(true);
            datePicker.setDisable(true);
            spinnerHourDte.setDisable(true);
            spinnerMinuteDte.setDisable(true);
            btnSetTittleExam.setText("EDIT");
        } else {
            titleTextField.setVisible(true);
            titleLabel.setVisible(false);
            btnSetTittleExam.setText("OK");
            if (exam == null) datePicker.setDisable(false);
            spinnerHourDte.setDisable(false);
            spinnerMinuteDte.setDisable(false);
            if (exam != null) updateExam();
        }
        isEditing = !isEditing;
    }

    public void addQuestion() {
        switch(addQuestionButton.getText()) {
            case "Add New Question":
                try {
                    validateForm();
                    Question question =
                            new Question(
                                    null,
                                    tableQuestions.getItems().size(),
                                    titleQuestion.getText().trim()
                            );

                    question.setAnswers(
                            FXCollections.observableArrayList(
                                    List.of(
                                            new Answer(
                                                    question,
                                                    examOption1TF.getText().trim(),
                                                    rightAnswer1RadioButton.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    examOption2TF.getText().trim(),
                                                    rightAnswer2RadioButton.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    examOption3TF.getText().trim(),
                                                    rightAnswer3RadioButton.isSelected()
                                            ),
                                            new Answer(
                                                    question,
                                                    examOption4TF.getText().trim(),
                                                    rightAnswer4RadioButton.isSelected()
                                            )
                                    )
                            )
                    );
                    tableQuestions.getItems().add(question);
                    tableQuestions.refresh();
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
                tableQuestions.setDisable(true);
                addQuestionButton.setText("OK");
                break;
            case "OK":
                updateQuestion(tableQuestions.getSelectionModel().getSelectedItem());
                clearQuestionForm();
                tableQuestions.setDisable(false);
                addQuestionButton.setText("Add New Question");
                break;
        }
    }

    private void updateQuestion(Question question) {
        if (!question.getDescription().equals(titleQuestion.getText().trim())) question.setDescription(titleQuestion.getText().trim());
        if (!question.getAnswers().get(0).getText().equals(examOption1TF.getText().trim())) question.getAnswers().get(0).setText(examOption1TF.getText().trim());
        if (!question.getAnswers().get(1).getText().equals(examOption2TF.getText().trim())) question.getAnswers().get(1).setText(examOption2TF.getText().trim());
        if (!question.getAnswers().get(2).getText().equals(examOption3TF.getText().trim())) question.getAnswers().get(2).setText(examOption3TF.getText().trim());
        if (!question.getAnswers().get(3).getText().equals(examOption4TF.getText().trim())) question.getAnswers().get(3).setText(examOption4TF.getText().trim());
        tableQuestions.refresh();
    }

    private void updateExam() {
        if (!exam.getDescription().equals(titleTextField.getText().trim())) exam.setDescription(titleTextField.getText().trim());
        if (!exam.getDeadline().equals(LocalDateTime.of(datePicker.getValue(), LocalTime.of(spinnerHourDte.getValue(), spinnerMinuteDte.getValue()))))
            exam.setDeadline(LocalDateTime.of(datePicker.getValue(), LocalTime.of(spinnerHourDte.getValue(), spinnerMinuteDte.getValue())));
    }

    @FXML private void questionSelectionHandler() {
        if (tableQuestions.getSelectionModel().getSelectedItem() != null) {
            fillQuestionForm();
            disableQuestionForm(true);
            addQuestionButton.setText("Edit Question");
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
        validateText(titleQuestion, "Question is required");
        validateText(examOption1TF, "Option 1 is required");
        validateText(examOption2TF, "Option 2 is required");
        validateText(examOption3TF, "Option 3 is required");
        validateText(examOption4TF, "Option 4 is required");
        if (radioGroup.getSelectedToggle() == null) throw new NotValidFormatException("You must select one option as correct");
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
            examContainer.getChildren().add(view);
            TeacherExamController.exams.add(exam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindColumns() {
        TableColumn<?, ?>[] tableColumns = {
                questionNumberTableColumn,
                questionTableColumn
        };

        String[] properties = {
                "number",
                "description"
        };
        AdminViewController.injectCellValues(tableColumns, properties);
    }
}

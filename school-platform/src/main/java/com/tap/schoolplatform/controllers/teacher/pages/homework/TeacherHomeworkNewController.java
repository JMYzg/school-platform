package com.tap.schoolplatform.controllers.teacher.pages.homework;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Optional;

public class TeacherHomeworkNewController {

    public static final String PATH = "/views/teacher-views/teacher-option-homework-new-view.fxml";

    @FXML private TextField titleTF;

    @FXML private TextArea descriptionTF;

    @FXML private DatePicker datePicker;

    @FXML private Spinner<Integer> spinnerHour;
    @FXML private Spinner<Integer> spinnerMinute;

    @FXML private Button
            cleanButton,
            cancelButton,
            acceptButton;

    @FXML private void initialize() {
        spinnerConfiguration(spinnerHour, 23);
        spinnerConfiguration(spinnerMinute, 59);
    }

    @FXML private void cancelHomework() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Cancel homework",
                        "Are you sure you want to cancel this homework?",
                        "All content will be lost"
                );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void createHomework() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Confirm new homework",
                        "Are you sure you want to create this homework",
                        "You'll be able to modify this homework later on"
                );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            // code to create the new homework
        }
    }

    @FXML private void clearAll() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Clear all",
                        "Are you sure you want to clear this homework?",
                        "Content will be lost"
                );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            titleTF.clear();
            descriptionTF.clear();
            datePicker.setValue(null);
            // spinner hours
            // spinner minutes
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
}
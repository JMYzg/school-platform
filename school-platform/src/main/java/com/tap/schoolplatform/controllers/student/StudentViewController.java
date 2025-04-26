package com.tap.schoolplatform.controllers.student;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class StudentViewController extends ViewController {
    @FXML private Button
            studentDataButton,
            classesTab,
            homeworkTab,
            gradesButton,
            logOutButton;

    @FXML private BorderPane borderPane;

    @FXML private void openStudentDataTab() {
        loadPageView("/views/student-views/student-data-view.fxml", borderPane);
    }

    @FXML private void openClassTab() {
        loadPageView("/views/student-views/classes-view.fxml", borderPane);
    }

    @FXML private void openHomeworkTab() {
        loadPageView("/views/student-views/homework-view.fxml", borderPane);
    }

    @FXML private void openGradesTab() {
        loadPageView("/views/student-views/grades-view.fxml", borderPane);
    }

    @FXML private void exitStudentView() {
        try {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Performing logout",
                            "Are you sure you want to log out?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK)
                toView(LOGIN_VIEW, "Log in", logOutButton);
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
        }

    private void loadPageView(String pageName, BorderPane borderPane) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(pageName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setCenter(root);
    }
}
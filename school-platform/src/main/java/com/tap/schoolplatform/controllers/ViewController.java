package com.tap.schoolplatform.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class ViewController {

    protected static final String LOGIN_VIEW = "/views/admin-views/login-view.fxml";
    protected static final String ADMIN_VIEW = "/views/admin-views/admin-view.fxml";
    protected static final String TEACHER_VIEW = "/views/teacher-views/teacher-view.fxml";
    protected static final String STUDENT_VIEW = "/views/student-views/student-view.fxml";

    public void toView(String view, String title, Control control) throws IOException {
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        newStage.setTitle(title);
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
    }
}

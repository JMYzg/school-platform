package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.teacher.pages.exam.TeacherExamController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherGradeController;
import com.tap.schoolplatform.controllers.teacher.pages.homework.TeacherHomeworkController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherStudentListController;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class TeacherViewController extends ViewController {

    @FXML public TreeView<Subject> treeView;//aqui comienzan los cambios
    @FXML private Label
            subjectNameLabel,
            teacherName,
            semesterNameLabel;
    @FXML private Button
            optionstudentsButton,
            optionexamsButton,
            optionhomeworkButton,
            optiongradesButton,
            logoutButton;
    @FXML private BorderPane optionBorderPane;
    @FXML private Button previousButton, nextButton;

    public Subject lastSelectedSubject;

    public void initialize() {
        teacherName.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");

        setTreeView();
        setListener();
    }

    private void setListener(){
        // Listener
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Subject selectedSubject = newValue.getValue();
                System.out.println(selectedSubject);

                lastSelectedSubject = selectedSubject;
                subjectNameLabel.setText(selectedSubject.toString());
                semesterNameLabel.setText(selectedSubject.getSemester().toString()); //Solo aplicaba al principio
            }
        });
    }

    public void setTreeView() {
        Teacher currentTeacher = (Teacher) LoginService.getCurrentUser();
        TreeItem<Subject> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);

        ObservableList<Subject> subjects = currentTeacher.getSubjects();
        for (Subject subject : subjects) {
            TreeItem<Subject> subjectItem = new TreeItem<>(subject);
            subjectItem.setExpanded(true);
            rootItem.getChildren().add(subjectItem);
        }
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
    }

//    public void setLastSelectedSubject(Subject actualSubject) {
//        this.lastSelectedSubject = actualSubject;
//    }

    public void goPrevious(ActionEvent actionEvent) {

    }

    public void goNext(ActionEvent actionEvent) {

    }

    //utils
    // Clase auxiliar para almacenar objetos en los TreeItems
    public class TreeItemWrapper {
        private final String displayText;
        private final Object associatedObject;

        public TreeItemWrapper(String displayText, Object associatedObject) {
            this.displayText = displayText;
            this.associatedObject = associatedObject;
        }

        @Override
        public String toString() {
            return displayText;
        }

        public Object getAssociatedObject() {
            return associatedObject;
        }
    }

    public void logOut() {
        try {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Performing logout",
                            "Are you sure you want to log out?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK)
                toView(LOGIN_VIEW, "Log in", logoutButton);
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void loadPageStudentList(String pageName, BorderPane borderPane, Subject subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherStudentListController controller = loader.getController();
            controller.setSubject(subject);
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPageHomeworks(String pageName, BorderPane borderPane, Subject subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherHomeworkController controller = loader.getController();
            controller.setSubject(subject);
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPageExams(String pageName, BorderPane borderPane, Subject subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherExamController controller = loader.getController();
            controller.setSubject(subject);
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPageGrades(String pageName, BorderPane borderPane, Subject subject) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherGradeController controller = loader.getController();
            controller.setSubject(subject);
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openStudentList() {
        loadPageStudentList("/views/teacher-views/teacher-option-student-list-view.fxml", optionBorderPane, lastSelectedSubject);
    }
    public void openHomework() {
        loadPageHomeworks("/views/teacher-views/teacher-option-homework-view.fxml", optionBorderPane, lastSelectedSubject);
    }

    public void openExams() {
        loadPageExams("/views/teacher-views/teacher-option-exam-view.fxml", optionBorderPane, lastSelectedSubject);
    }

    public void openGradesList() {
        loadPageGrades("/views/teacher-views/teacher-option-grade-view.fxml", optionBorderPane, lastSelectedSubject);
    }

    public void selectItemTreeView(){
        Object item = treeView.getSelectionModel().getSelectedItem();
    }
}

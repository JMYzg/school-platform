package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
import com.tap.schoolplatform.controllers.teacher.pages.exam.TeacherExamController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherGradeController;
import com.tap.schoolplatform.controllers.teacher.pages.homework.TeacherHomeworkController;
import com.tap.schoolplatform.controllers.teacher.pages.TeacherStudentListController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;

public class TeacherViewController extends ViewController {

    @FXML public TreeView<Group> treeView;

    @FXML private Label
            subjectNameLabel,
            teacherName,
            groupName,
            semesterGroup,
            groupShift;

    @FXML private Button
            optionstudentsButton,
            optionexamsButton,
            optionhomeworkButton,
            optiongradesButton,
            logoutButton;

    @FXML private BorderPane optionBorderPane;

    @FXML private Button
            previousButton,
            nextButton;

    public TeacherStudentListController currentStudentList;
    public static Group currentSubject;
    public LinkedList<Group> currentGroups;
    ListIterator<Group> iterator;

    public void initialize() {
        teacherName.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");

        setTreeView();
        setListener();
//        setCurrentSubject();
    }

    private void setListener(){
        // Listener
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Group selectedSubject = (Group) newValue.getValue();
                System.out.println(selectedSubject);

                TeacherViewPage.subject = selectedSubject;
                currentGroups = new LinkedList<>(TeacherViewPage.subject.getSemester().getAllGroups());
                iterator = currentGroups.listIterator();
                subjectNameLabel.setText(selectedSubject.toString());
            }
        });
    }

    public void setTreeView() {
        Teacher currentTeacher = (Teacher) LoginService.getCurrentUser();
        TreeItem<Group> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);

        ObservableList<Group> subjects = currentTeacher.getSubjects();
        for (Group subject : subjects) {
            TreeItem<Group> subjectItem = new TreeItem<>(subject);
            subjectItem.setExpanded(true);
            rootItem.getChildren().add(subjectItem);
        }
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
    }

//    public void setCurrentSubject(Subject actualSubject) {
//        currentSubject = actualSubject;
//    }

    public void goPrevious(ActionEvent actionEvent) {
        if (this.iterator.hasPrevious()) {
            Group previous = this.iterator.previous();
            currentStudentList.table.setItems(previous.getStudents());
            groupName.setText(previous.getId());
            semesterGroup.setText(previous.getSemester().toString());
            groupShift.setText(previous.getShift().toString());
            currentStudentList.table.refresh();
        }
    }

    public void goNext(ActionEvent actionEvent) {
        if (this.iterator.hasNext()) {
            Group next = this.iterator.next();
            currentStudentList.table.setItems(next.getStudents());
            groupName.setText(next.getId());
            semesterGroup.setText(next.getSemester().toString());
            groupShift.setText(next.getShift().toString());
            currentStudentList.table.refresh();
        }
    }

    //utils
    // Clase auxiliar para almacenar objetos en los TreeItems
    public static class TreeItemWrapper {
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

    public void loadPageStudentList(String pageName, BorderPane borderPane, Group subject) {
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

//    public void loadPageHomeworks(String pageName, BorderPane borderPane, Subject subject) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
//            Parent root = loader.load();
//            TeacherHomeworkController controller = loader.getController();
//            controller.setSubject(subject);
//            borderPane.setCenter(root);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void loadPageExams(String pageName, BorderPane borderPane, Subject subject) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
//            Parent root = loader.load();
//            TeacherExamController controller = loader.getController();
//            controller.setSubject(subject);
//            borderPane.setCenter(root);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void loadPageGrades(String pageName, BorderPane borderPane, Subject subject) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
//            Parent root = loader.load();
//            TeacherGradeController controller = loader.getController();
//            controller.setGroup(subject);
//            borderPane.setCenter(root);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void loadPage(String path, BorderPane pane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof TeacherViewPage teacherViewPage) {
//                ((TeacherViewPage) controller).setSubject(subject);
                if (teacherViewPage instanceof TeacherStudentListController teacherStudentListController) {
                    currentStudentList = teacherStudentListController;
                    currentStudentList.table.setItems(currentGroups.getFirst().getStudents());
                }
            }
            pane.setCenter(root);
        }
        catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void loadPage(String path, BorderPane pane, Group subject, Group group) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Object controller = loader.getController();
            if (controller instanceof TeacherViewPage) {
                ((TeacherViewPage) controller).setSubject(subject);
                ((TeacherViewPage) controller).setGroup(group);
            }
            Parent root = loader.load();
            pane.setCenter(root);
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void openStudentList() {
//        loadPageStudentList("/views/teacher-views/teacher-option-student-list-view.fxml", optionBorderPane, currentSubject);
        loadPage(TeacherStudentListController.PATH, optionBorderPane);
    }

    public void openHomework() {
//        loadPageHomeworks("/views/teacher-views/teacher-option-homework-view.fxml", optionBorderPane, currentSubject);
        loadPage(TeacherHomeworkController.PATH, optionBorderPane);
    }

    public void openExams() {
//        loadPageExams("/views/teacher-views/teacher-option-exam-view.fxml", optionBorderPane, currentSubject);
        loadPage(TeacherExamController.PATH, optionBorderPane);
    }

    public void openGradesList() {
//        loadPageGrades("/views/teacher-views/teacher-option-grade-view.fxml", optionBorderPane, currentSubject);
        loadPage(TeacherGradeController.PATH, optionBorderPane);
    }

    public void selectItemTreeView(){
        Object item = treeView.getSelectionModel().getSelectedItem();
    }
}

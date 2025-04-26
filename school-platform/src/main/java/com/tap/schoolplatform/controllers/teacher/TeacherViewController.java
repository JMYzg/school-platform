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

    @FXML public TreeView<Object> treeView;

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

    public Subject lastSelectedSubject;

    public void initialize() {
        teacherName.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");

        setTreeView();
        listener();
    }

    public void listener(){
        // Listener
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Object selectedObject = newValue.getValue();
                System.out.println(selectedObject);

                if(selectedObject instanceof Subject x){
                    lastSelectedSubject = x;

                    TreeItem<Object> parent = newValue.getParent();
                    subjectNameLabel.setText(selectedObject.toString());

                    Subject subject = (Subject) selectedObject;
                    semesterGroup.setText(subject.getSemester().toString());
                }


//No funcionan las listas

//                if (selectedObject instanceof Group selectedGroup) {
//                    lastSelectedGroup = selectedGroup;
//
//                    // Aquí puedes usar el nombre y la lista observable de alumnos del grupo seleccionado
//                    String groupName = selectedGroup.getID();
//                    ObservableList<Student> students = selectedGroup.getStudents();
//
//                    // Realiza las acciones necesarias con el grupo seleccionado
//                    System.out.println("Grupo seleccionado: " + groupName);
//                    System.out.println("Lista de alumnos: " + students);
//
//                    //Optains Parent Name
//                    TreeItem<Object> parentItem = newValue.getParent();
//                    System.out.println("Parent seleccionado: " + parentItem);
//                    subjectNameLabel.setText(parentItem.getValue().toString());
//
//                } else if (selectedObject instanceof Subject) {
//                    if (lastSelectedGroup != null) {
//                        System.out.println("Selected Group: " + lastSelectedGroup.getID());
//                    } else {
//                        System.out.println("No select Group yet");
//                    }
//                }
            }
        });
    }

    public void setTreeView() {
//        TreeItem<Object> rootItem = new TreeItem<>(LoginService.getCurrentUser());
        TreeItem<Object> rootItem = new TreeItem<>(LoginService.getCurrentUser());
        rootItem.setExpanded(true);

        Teacher teacher = (Teacher) LoginService.getCurrentUser();
        ObservableList<Subject> subjects = teacher.getSubjects();

        for (Subject subject : subjects) {
//            TreeItem<Object> SubjectItem = new TreeItem<>(subject);
            TreeItem<Object> SubjectItem = new TreeItem<>(subject);

            SubjectItem.setExpanded(true);
            rootItem.getChildren().add(SubjectItem);
        }
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
    }

    public void setLastSelectedSubject(Subject actualSubject) {
        this.lastSelectedSubject = actualSubject;
    }

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
//        loadPageGrades("/views/teacher-views/teacher-option-grade-view.fxml", optionBorderPane, lastSelectedSubject);
    }

    public void selectItemTreeView(){
        Object item = treeView.getSelectionModel().getSelectedItem();
    }
}

package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @FXML public Label
            subjectNameLabel,
            teacherName;
    @FXML public Button
            optionstudentsButton,
            optionexamsButton,
            optionhomeworkButton,
            optiongradesButton,
            logoutButton;

    public Group lastSelectedGroup;
    @FXML public BorderPane optionborderPane;

    public void initialize() {
        teacherName.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");

        setTreeView();
        listener();
    }

    public void listener(){
        // Listener
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
                if (newValue != null) {
                    Object selectedObject = newValue.getValue();
                    System.out.println(selectedObject);
                    if (selectedObject instanceof Group) {
                        Group selectedGroup = (Group) selectedObject;
                        lastSelectedGroup = selectedGroup;

                        // Aquí puedes usar el nombre y la lista observable de alumnos del grupo seleccionado
                        String groupName = selectedGroup.getID();
                        ObservableList<Student> students = selectedGroup.getStudents();

                        // Realiza las acciones necesarias con el grupo seleccionado
                        System.out.println("Grupo seleccionado: " + groupName);
                        System.out.println("Lista de alumnos: " + students);

                        //Optains Parent Name
                        TreeItem<Object> parentItem = newValue.getParent();
                        System.out.println("Parent seleccionado: " + parentItem);
                        subjectNameLabel.setText(parentItem.getValue().toString());

                    } else if (selectedObject instanceof Subject) {
                        if (lastSelectedGroup != null) {
                            System.out.println("Selected Group: " + lastSelectedGroup.getID());
                        }
                        else{
                            System.out.println("No select Group yet");
                        }

                    }
                }
            }
        });
    }

    public void setTreeView() {
        TreeItem<Object> rootItem = new TreeItem<>("Subjects");
        rootItem.setExpanded(true);

        Teacher teacher = (Teacher) LoginService.getCurrentUser();
        ObservableList<Subject> subjects = teacher.getSubjects();

        for (Subject subject : subjects) {
            TreeItem<Object> SubjectItem = new TreeItem<>(subject);

            SubjectItem.setExpanded(true);
            rootItem.getChildren().add(SubjectItem);

            Semester semester = subject.getSemester();

            ObservableList<Group> groups = semester.getAllGroups();

            for (Group group : groups) {
                TreeItem<Object> groupItem = new TreeItem<>(group);
                groupItem.setExpanded(true);
                SubjectItem.getChildren().add(groupItem);
            }
        }
        treeView.setRoot(rootItem);


    }

    public void setLastSelectedGroup(Group actualGroup) {
        this.lastSelectedGroup = actualGroup;

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

    public void logOut(ActionEvent actionEvent) {
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

    public void loadPageViewA(String pageName, BorderPane borderPane, Group group) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherStudentListController controller = loader.getController();
            controller.setGroup(group);
            borderPane.setCenter(root);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadPageViewB(String pageName, BorderPane borderPane, Group group) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherHomeworkController controller = loader.getController();
            controller.setGroup(group);
            borderPane.setCenter(root);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadPageViewC(String pageName, BorderPane borderPane, Group group) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherExamController controller = loader.getController();
            controller.setGroup(group);
            borderPane.setCenter(root);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadPageViewD(String pageName, BorderPane borderPane, Group group) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            TeacherGradeController controller = loader.getController();
            controller.setGroup(group);
            borderPane.setCenter(root);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void openStudentList() {
        loadPageViewA("/views/teacher-views/teacher-option-student-list-view.fxml",optionborderPane, lastSelectedGroup);
    }
    public void openHomework() {
        loadPageViewB("/views/teacher-views/teacher-option-homework-view.fxml",optionborderPane, lastSelectedGroup);
    }

    public void openExams() {
        loadPageViewC("/views/teacher-views/teacher-option-exam-view.fxml",optionborderPane, lastSelectedGroup);
    }

    public void openGradesList() {
        loadPageViewD("/views/teacher-views/teacher-option-grade-view.fxml",optionborderPane, lastSelectedGroup);
    }

    public void selectItemTreeView(){
        Object item = treeView.getSelectionModel().getSelectedItem();
    }


}

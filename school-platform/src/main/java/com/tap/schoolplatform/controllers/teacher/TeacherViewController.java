package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class TeacherViewController extends ViewController {

    @FXML public TreeView treeView;
    @FXML public BorderPane optionborderPane;
    @FXML public Label
            subjectNameLabel,
            teacherName;
    @FXML public Button
            optionstudentsButton,
            optionexamsButton,
            optionhomeworkButton,
            optiongradesButton,
            logoutButton;


    public void initialize() {
        teacherName.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");
        //Quiero una TreeView Dinamica que solo tendra dos niveles roots (Subjects) y Leafs (Groups),
        //Necesito que se creen los roots a partir de una lista de observable de materias llamada "getSubjects() de la clase "Teacher"
        TreeItem<String> rootItem = new TreeItem<>("Subjects");
        rootItem.setExpanded(true);

        Teacher teacher = (Teacher) LoginService.getCurrentUser();
        ObservableList<Subject> subjects = teacher.getSubjects();

        for (Subject subject : subjects) {
            TreeItem<String> item = new TreeItem<>(subject.getName());
            item.setExpanded(true);
            rootItem.getChildren().add(item);
        }

        //Se crearian las hojas a partir de los "groups" de cada "Subject" estos dos se vinculan a traves del semestre.
        //El flujo seria: Se jalan las subjects de maestro, de cada una de ellas se obtiene el semestre y el semestre proporciona un mapa de grupos, esos grupos serian las Leafs (Hojas).

        //TreeItem<String> leaftItem = new TreeItem<>("Groups");
        treeView.setRoot(rootItem);
    }

    //utils
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

    public void openStudentList() {
    }

    public void openExams() {
    }

    public void openHomework() {
    }

    public void openGradesList() {
    }

    public void selectItemTreeView() {
    }
}

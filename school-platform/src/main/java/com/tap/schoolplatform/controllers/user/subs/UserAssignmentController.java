package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class UserAssignmentController {
    public Button gradeButton;
    public TextFlow instructions;
    @FXML
    Button
            exitButton,
            attachButton,
            editButton,
            submmitButton;
    @FXML
    Label
            homeworkTitleLabel,
            deadlineLabel,
            hourLabel;

    private UserViewController userViewController;

    private UserGroupBorderPaneViewController userGroupBorderPaneViewController;

    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.userGroupBorderPaneViewController = userGroupBorderPaneViewController;
    }

    @FXML
    public void initialize() {
        setAssigmentData();

        //cuando presiono este botón y regreso a la lista de tareas desaparecen los botones de tareas
        exitButton.setOnAction(e -> {
            try {
                userGroupBorderPaneViewController.setloadCenter("/views/new-interface/user-homework-list-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        attachButton.setOnAction(e -> {
            //función para abrir el explorador de archivos
        });
    }

    private void setAssigmentData() {
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().toString());
        instructions.getChildren().add(new Text(UserViewController.getCurrentAssignment().getDescription()));
    }

    public void closeSubmitHomework(ActionEvent actionEvent) {
    }


    public void submitHomework(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-grade.fxml", "");
    }

    public void attachFiles(ActionEvent actionEvent) {
    }

    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-edit_new.fxml", "");
    }
}
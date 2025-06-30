package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class UserListAssignmentsController {

    @FXML VBox homeworkViewsContainer;
    @FXML Button addHomeworkButton;

    public void initialize() {

        Group g = UserViewController.currentGroup;


        addHomeworkButton.setOnAction(event ->
                openInNewWindow("views/new-interface/user-homework-edit_new.fxml", "Create a Assignment"));
    }

    private void openInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHomework(ActionEvent actionEvent) {
    }
}

package com.tap.schoolplatform.controllers.student.pages.homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static com.tap.schoolplatform.controllers.ViewController.loadNewView;

public class HomeworkContainerViewController {

    public static String PATH = "/views/student-views/student-homework-container-view.fxml";

    @FXML private Button homeworkButton;

    public Label
            homeworkTitle,
            homeworkDeadline,
            homeworkSubject;

    @FXML private Button openHomeworkButton;

//    public void initialize() throws IOException {
//        setData();
//    }

/*    public void setData () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HomeworkSubmitViewController.PATH));
        fxmlLoader.load();
        HomeworkSubmitViewController controller = fxmlLoader.getController();

        controller.homeworkTitleLabel.setText(homeworkTitle.getText());
        controller.deadlineLabel.setText(homeworkDeadline.getText());
        controller.hourLabel.setText("99 AM - 12 PM");

        FXMLLoader fxmlLoaderDescription = new FXMLLoader(getClass().getResource(HomeworkSubmitViewController.PATH));
        fxmlLoaderDescription.load();
        HomeworkSubmitViewController controllerDescription = fxmlLoaderDescription.getController();

        controller.homeworkDescription.setTextAlignment(controllerDescription.homeworkDescription.getTextAlignment());
    }

    public void openHomework(ActionEvent actionEvent) throws IOException {
        loadNewView(actionEvent, HomeworkSubmitViewController.PATH, "Homework");
        setData();
    }*/

    public void openHomework(ActionEvent actionEvent) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(HomeworkSubmitViewController.PATH));
        Parent root = loader.load();

        // Get the controller
        HomeworkSubmitViewController submitController = loader.getController();

        // Pass the data to the new controller
        if (homeworkTitle != null && homeworkTitle.getText() != null) {
            submitController.homeworkTitleLabel.setText(homeworkTitle.getText());
        }

        if (homeworkDeadline != null && homeworkDeadline.getText() != null) {
            submitController.deadlineLabel.setText(homeworkDeadline.getText());
        }

        submitController.homeworkDescription.getChildren().clear();

        FXMLLoader fxmlLoaderDescription = new FXMLLoader(getClass().getResource(HomeworkSubmitViewController.PATH));
        fxmlLoaderDescription.load();
        HomeworkSubmitViewController controllerDescription = fxmlLoaderDescription.getController();

        submitController.homeworkDescription.setTextAlignment(controllerDescription.homeworkDescription.getTextAlignment());
        javafx.scene.text.Text descriptionText = new javafx.scene.text.Text(
                controllerDescription.homeworkDescription.getTextAlignment().toString()
        );

        // Set static text for the hour
        submitController.hourLabel.setText("99 AM - 12 PM");

        // Create and show the new scene
        Stage stage = new Stage();
        stage.setTitle("Homework");
        stage.setScene(new Scene(root));
        stage.show();
    }

}

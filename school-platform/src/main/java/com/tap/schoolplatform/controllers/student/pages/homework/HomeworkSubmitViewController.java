package com.tap.schoolplatform.controllers.student.pages.homework;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class HomeworkSubmitViewController {
    @FXML private Button
            exitButton,
            attachButton,
            submmitButton;

    @FXML private Label
            homeworkTitleLabel,
            deadlineLabel,
            hourLabel;

    @FXML private void initialize() {
        homeworkTitleLabel.setText("Homework title");
        deadlineLabel.setText("Due date");
        hourLabel.setText("Hour");
    }

    @FXML private void closeSubmitHomework() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Closing window",
                        "Are you sure you want to close this window?"
                        );
        if (response.isPresent() && response.get() == ButtonType.OK){
            Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        }
    }

    @FXML private void submitHomework() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Submitting homework",
                        "Are you sure you want to submit this homework?"
                );
        if (response.isPresent() && response.get() == ButtonType.OK){
            // Submit code shit
        }
    }

    @FXML private void attachFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg")
        );
        File image = fileChooser.showOpenDialog(attachButton.getScene().getWindow());
        if (image != null) {
            try {
//                String imagePath = image.toURI().toString();
//                Image pfp = new Image(imagePath);
//                attachButton.setImage(pfp);
//                return pfp;
                System.out.println("Image loaded");
            } catch (IllegalArgumentException e) {
                AlertHandler.showAlert(
                        Alert.AlertType.ERROR,
                        "Error",
                        "Error loading image",
                        "Could not load image, please try again.\n" + e.getMessage()
                );
            }
        }
//        return null;
    }
}

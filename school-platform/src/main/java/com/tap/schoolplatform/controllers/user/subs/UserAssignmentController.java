package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.tasks.Submission;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class UserAssignmentController {
    @FXML Button exitButton1, attachButton, editButton, submmitButton;
    @FXML Label homeworkTitleLabel, deadlineLabel, hourLabel;
    @FXML private TextFlow textFlow;
    private Submission submission;
    private Role role;

    private UserGroupBorderPaneViewController userGroupBorderPaneViewController;
    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.userGroupBorderPaneViewController = userGroupBorderPaneViewController;
    }

    @FXML
    public void initialize() {
        setup();
        exitButton1.setOnAction(e -> {
            try {
                userGroupBorderPaneViewController.setloadCenter("/views/new-interface/user-homework-list-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        attachButton.setOnAction(e -> {
            if (attachButton.getText().equals("Attach")) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose a PDF file to attach the assignment");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PDF", "*.pdf")
                );
                File pdf = fileChooser.showOpenDialog(submmitButton.getScene().getWindow());
                if (pdf != null) {
                    try (InputStream inputStream = new FileInputStream(pdf)) {
                        submission = new Submission(
                                LoginService.getCurrentUser(),
                                UserViewController.CURRENT_ASSIGNMENT,
                                UserViewController.CURRENT_GROUP,
                                inputStream.readAllBytes()
                        );
//                return pdf.getName();
                        submmitButton.setDisable(false);
                        attachButton.setText("Detach");
                    } catch (IllegalArgumentException | IOException ex) {
                        AlertHandler.showAlert(
                                Alert.AlertType.ERROR,
                                "Error",
                                "Error uploading file",
                                "Could not upload file, please try again.\n" + ex.getMessage()
                        );
                    }
                }
            } else {
                submission = null;
                submmitButton.setDisable(true);
                attachButton.setText("Attach");
            }
        });
    }

    public void closeSubmitHomework(ActionEvent actionEvent) {
    }

    public void submitHomework(ActionEvent actionEvent) {
        if (submmitButton.getText().equals("Submit")) {
            Service.add(submission);
        } else {
            try {
                UserViewController.loadNewView(actionEvent,"/views/new-interface/user-homework-grade.fxml","");
            } catch (IOException ex) {
                AlertHandler.showAlert(
                        Alert.AlertType.ERROR,
                        "Error",
                        "Resource not found",
                        ex.getMessage()
                );
            }
        }
    }

    public void attachFiles(ActionEvent actionEvent) {}

    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent,"/views/new-interface/user-homework-edit_new.fxml","");
    }

    private void setup() {
        submmitButton.setDisable(true);
        Membership membership = UserViewController.CURRENT_GROUP.getMemberships()
                .stream()
                .filter(mem -> mem.getUser().equals(LoginService.getCurrentUser())).findFirst()
                .orElse(null);
        assert membership != null;
        role = membership.getRole();
        homeworkTitleLabel.setText(UserViewController.CURRENT_ASSIGNMENT.getTitle());
        deadlineLabel.setText(UserViewController.CURRENT_ASSIGNMENT.getDeadline().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)));
        hourLabel.setText(UserViewController.CURRENT_ASSIGNMENT.getCreationDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)));
        Text text = new Text(UserViewController.CURRENT_ASSIGNMENT.getDescription());
        textFlow.getChildren().add(text);
        if (role == Role.MEMBER) {
            editButton.setVisible(false);
        } else {
            submmitButton.setVisible(true);
            submmitButton.setText("Grade");
            submmitButton.setDisable(false);
        }
    }
}

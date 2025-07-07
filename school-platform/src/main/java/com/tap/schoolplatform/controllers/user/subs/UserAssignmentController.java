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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class UserAssignmentController {
    public Button gradeButton;
    public TextFlow textFlow;
    public Button deleteButton;
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

    private Role role;
    private Submission submission;

//    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
//        this.userListAssignmentsController = userListAssignmentsController;
//    }

    private UserGroupBorderPaneViewController mainController;
    private UserGroupBorderPaneViewController userGroupBorderPaneViewController;

    public void setBorderPaneController(UserGroupBorderPaneViewController userGroupBorderPaneViewController) {
        this.mainController = userGroupBorderPaneViewController;
    }

    @FXML
    public void initialize() {
        if (isMember()) {
            editButton.setVisible(false);
            gradeButton.setVisible(false);
            deleteButton.setVisible(false);
            submmitButton.setVisible(true);
//            submmitButton.setText("Submit");
            submmitButton.setDisable(false);
        } else {
            editButton.setVisible(true);
            gradeButton.setVisible(true);
            deleteButton.setVisible(true);
            submmitButton.setVisible(false);
            submmitButton.setDisable(true);
        }
//        setAssigmentData();
        setup();
        //cuando presiono este botón y regreso a la lista de tareas desaparecen los botones de tareas
        exitButton.setOnAction(e -> {
            try {
                UserViewController.setCurrentAssignment(null);
                mainController.setLoadCenter("/views/new-interface/user-homework-list-view.fxml");
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

        editButton.setOnAction(e -> {
            try {
                UserNew_EditAssignmentController controller =
                        ViewController.loadNewView(e, "/views/new-interface/user-homework-edit_new.fxml", "Edit");
                controller.setMainController(mainController);
                controller.setGroup(UserViewController.getCurrentGroup());
//                controller.setUserListAssignmentsController(userListAssignmentsController);
                controller.setAssignmentCreatedListener(updateAssignment -> {
                    mainController.getUserListAssignmentsController().generateAssignmentStack();
                });
//                controller.setAss
//                openEditAssignment("");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void loadAssignment() {
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().toString());
    }

//    private void setAssigmentData() {

    /// /        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
//        instructions.getChildren().add(new Text(UserViewController.getCurrentAssignment().getDescription()));
//        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        hourLabel.setText(UserViewController.getCurrentAssignment().getDeadline().getHour() + ":" + UserViewController.getCurrentAssignment().getDeadline().getMinute());
//    }
    public void closeSubmitHomework(ActionEvent actionEvent) {
    }

    public void submitHomework(ActionEvent actionEvent) {
        if (submmitButton.getText().equals("Submit")) {
            if (submission == null) {
                AlertHandler.showAlert(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No file attached",
                        "Please attach a file to submit this assignment."
                );
            } else {
                Optional<ButtonType> response = AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Submitting assignment",
                        "Are you sure you want to submit this assignment?"
                );
                if (response.isPresent() && response.get() == ButtonType.OK) {
                    Service.add(submission);
                    submmitButton.setDisable(false);
                    submmitButton.setText("Unsubmit");
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Success",
                            "Assignment submitted",
                            "The assignment has been submitted successfully."
                    );
                }
            }
        } else if (submmitButton.getText().equals("Unsubmit")) {
            Optional<ButtonType> response = AlertHandler.showAlert(
                    Alert.AlertType.CONFIRMATION,
                    "Please confirm",
                    "Unsubmitting assignment",
                    "Are you sure you want to unsubmit this assignment?"
            );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                Service.delete(submission);
                submmitButton.setDisable(false);
                submmitButton.setText("Submit");
            }
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Assignment unsubmitted",
                    "The assignment has been unsubmitted successfully."
            );
        } else {
            try {
                UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-grade.fxml", "");
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

//    public void submitHomework(ActionEvent actionEvent) throws IOException {
//        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-grade.fxml", "");
//    }

    public void attachFiles(ActionEvent actionEvent) {
    }

    public void openEditAssignment(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent root = fxmlLoader.load();
        UserNew_EditAssignmentController controller = fxmlLoader.getController();
    }

    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
        UserViewController.loadNewView(actionEvent, "/views/new-interface/user-homework-edit_new.fxml", "");
        updateAssigmentData();
    }

    public void updateAssigmentData() {
        homeworkTitleLabel.setText(UserViewController.getCurrentAssignment().getTitle());
        deadlineLabel.setText(UserViewController.getCurrentAssignment().getDeadline().toString());
        textFlow.getChildren().clear();
        textFlow.getChildren().add(new Text(UserViewController.getCurrentAssignment().getDescription()));
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
            attachButton.setVisible(false);
        }
    }

    public void deleteAssignment(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Deleting assignment",
                "Are you sure you want to delete this assignment?"
        );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            UserViewController.getCurrentGroup().getAssignments().remove(UserViewController.getCurrentAssignment());
            Service.delete(UserViewController.getCurrentAssignment());
            UserViewController.setCurrentAssignment(null);
            try {
                mainController.getUserListAssignmentsController().generateAssignmentStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AlertHandler.showAlert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Assignment deleted",
                "The assignment has been deleted successfully."
        );
        try {
            mainController.setLoadCenter("/views/new-interface/user-homework-list-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMember() {
        Membership currentGroupMembership = LoginService.getCurrentUser().getMemberships().stream()
                .filter(mem -> mem.getGroup().equals(UserViewController.getCurrentGroup()))
                .findFirst()
                .orElse(null);

        return currentGroupMembership != null && (currentGroupMembership.getRole() == Role.MEMBER || currentGroupMembership.getRole() == Role.MEMBER);
    }

}

//    public void editButtonHandler(ActionEvent actionEvent) throws IOException {
//        UserViewController.loadNewView(actionEvent,"/views/new-interface/user-homework-edit_new.fxml","");
//    }

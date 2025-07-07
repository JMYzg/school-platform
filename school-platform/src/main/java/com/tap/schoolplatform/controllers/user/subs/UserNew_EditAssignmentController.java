package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserGroupBorderPaneViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.interfaces.AssignmentCreatedListener;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.tap.schoolplatform.controllers.user.UserViewController.CURRENT_ASSIGNMENT;

public class UserNew_EditAssignmentController {
    @FXML
    TextField titleTF;
    @FXML
    TextArea descriptionTF;
    @FXML
    DatePicker datePicker;
    @FXML
    Spinner<Integer> spinnerHour;
    @FXML
    Spinner<Integer> spinnerMinute;
    @FXML
    public Button cleanButton, cancelButton, acceptButton;

    private Assignment assignment;
    private Group group;
    private VBox AssignmentContainer;
    private UserViewController userViewController;
    private AssignmentCreatedListener assignmentCreatedListener;
    private UserListAssignmentsController userListAssignmentsController;
    public UserGroupBorderPaneViewController mainController;

    public void setUserViewController(UserViewController userViewController) {
        this.userViewController = userViewController;
    }

    public void setUserListAssignmentsController(UserListAssignmentsController userListAssignmentsController) {
        this.userListAssignmentsController = userListAssignmentsController;
    }

    public void setMainController(UserGroupBorderPaneViewController mainController) {
        this.mainController = mainController;
    }

    public void setAssignmentCreatedListener(AssignmentCreatedListener Listener) {
        this.assignmentCreatedListener = Listener;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setAssignmentContainer(VBox AssignmentContainer) {
        this.AssignmentContainer = AssignmentContainer;
    }

    @FXML
    public void initialize() {
        spinnerConfiguration(spinnerHour, 23);
        spinnerConfiguration(spinnerMinute, 59);
        datePicker.setEditable(false);
        clearAssigmentData();
        if (UserViewController.getCurrentAssignment() != null) {
            loadAssignmentData();
        }

        if (userViewController.getCurrentAssignment() != null) {
            setAssignmentData();
        }

        acceptButton.setOnAction(event ->
                //pendiente agregar alerta de confirmación
                {
                    try {
                        handleCreateAssignment();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public void handleCreateAssignment() throws IOException {
        String title = titleTF.getText();
        String description = descriptionTF.getText();
        LocalDate date = datePicker.getValue();
        int hour = spinnerHour.getValue();
        int minute = spinnerMinute.getValue();

        if (title.isEmpty() || description.isEmpty() || hour < 0 || hour > 23 || minute < 0 || minute > 59 || date == null) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Empty fields",
                    "All the fields must be required",
                    "Please enter all the fields correctly"
            );
        } else {
            LocalDateTime dueDateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            if (UserViewController.getCurrentAssignment() == null) {
                //If the assign is new
                assignment = new Assignment(
                        title,
                        description,
                        dueDateTime,
                        group
                );
                //Asegurarme que se agreguen al grupo actual
                UserViewController.CURRENT_GROUP.getAssignments().add(assignment);
                //aqui estaba
                Service.add(assignment);

                if (assignmentCreatedListener != null) {
                    assignmentCreatedListener.onAssignmentCreated(assignment);
                }

                if (userListAssignmentsController != null) {
                    userListAssignmentsController.generateAssignmentStack();
                }

                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Assignment created",
                        "The assignment has been created successfully",
                        "Changes were saved"
                );
                UserViewController.setCurrentAssignment(null);
            } else {
//                CURRENT_ASSIGNMENT = assignment;
//                Service.update(assignment);

                //If you are just editing
                UserViewController.getCurrentAssignment().setTitle(title);
                UserViewController.getCurrentAssignment().setDescription(description);
                UserViewController.getCurrentAssignment().setDeadline(dueDateTime);

                UserButtonAssignmentController btnController = UserViewController.getCurrentAssignment().getButtonController();
                if (btnController != null) {
                    btnController.setHomeworkTitle(title);
                }

                Service.update(UserViewController.getCurrentAssignment());
                mainController.setLoadCenter("/views/new-interface/user-homework-list-view.fxml");

                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Assignment updated",
                        "The assignment has been updated successfully",
                        "Changes were saved"
                );
                UserViewController.setCurrentAssignment(null);
            }

            if (AssignmentContainer != null) {
                addAssignmentView(assignment); //Cuando es nuevo lo añade, pero  debemos hacer que cuando no es nuevo lo actualice
            }
        }
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        stage.close();
    }

    private void addAssignmentView(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/new-interface/button-assignment.fxml"));
            Parent taskView = loader.load();

            UserButtonAssignmentController controller = loader.getController();
            assignment.setButtonController(controller);
            controller.setAssigment(assignment);
            controller.setHomeworkTitle(assignment.getTitle());
//            controller.setAssigmentContainer(AssignmentContainer);

            controller.setOnClick(() -> {
                UserViewController.setCurrentAssignment(assignment);
//                assignment.setButtonController(controller);
                try {
                    UserViewController.setCurrentAssignment(assignment);
                    //aqui
                    loadAssignmentData();
                    mainController.setLoadCenter("/views/new-interface/user-homework-view.fxml");
//                    mainController.setUserListAssignmentsController(userListAssignmentsController);
                } catch (Exception e) {
                    AlertHandler.showAlert(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Resource not found",
                            e.getMessage()
                    );
                }
            });
            AssignmentContainer.getChildren().add(taskView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAssignmentData() {
        Assignment current = UserViewController.getCurrentAssignment();
        if (current != null) {
            titleTF.setText(current.getTitle());
            descriptionTF.setText(current.getDescription());

            LocalDateTime deadline = current.getDeadline();
            datePicker.setValue(deadline.toLocalDate());
            spinnerHour.getValueFactory().setValue(deadline.getHour());
            spinnerMinute.getValueFactory().setValue(deadline.getMinute());
        }
    }

    public void clearAssigmentData() {
        titleTF.clear();
        descriptionTF.clear();
        datePicker.setValue(null);
        spinnerHour.getValueFactory().setValue(0);
        spinnerMinute.getValueFactory().setValue(0);
    }

    public void clearAll(ActionEvent actionEvent) {
        Optional<ButtonType> result = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Clearing all fields",
                "Are you sure you want to clear all the fields?"
        );
        if (result.get() == ButtonType.OK) {
            titleTF.clear();
            descriptionTF.clear();
            datePicker.setValue(null);
        }
    }

    public void cancelHomework(ActionEvent actionEvent) {
        Optional<ButtonType> result = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Cancelling modifying homework",
                "Are you sure you want to cancel this operation?"
        );
        if (result.get() == ButtonType.OK) {
            UserViewController.setCurrentAssignment(null);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void createHomework(ActionEvent actionEvent) {
    }

    private void spinnerConfiguration(Spinner<Integer> spinner, int max) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max);
        valueFactory.setValue(0);

        valueFactory.setConverter(new StringConverter<Integer>() {

            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        });
        spinner.setValueFactory(valueFactory);
    }

    private void setAssignmentData() {
        titleTF.setText(CURRENT_ASSIGNMENT.getTitle() == null ? "" : CURRENT_ASSIGNMENT.getTitle());
        descriptionTF.setText(CURRENT_ASSIGNMENT.getDescription());
        datePicker.setValue(CURRENT_ASSIGNMENT.getDeadline().toLocalDate());
        spinnerHour.getValueFactory().setValue(CURRENT_ASSIGNMENT.getDeadline().getHour() % 24);
        spinnerMinute.getValueFactory().setValue(CURRENT_ASSIGNMENT.getDeadline().getMinute());
    }
}
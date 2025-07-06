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

public class UserNew_EditAssignmentController {
    @FXML TextField titleTF;
    @FXML TextArea descriptionTF;
    @FXML DatePicker datePicker;
    @FXML Spinner<Integer> spinnerHour;
    @FXML Spinner<Integer> spinnerMinute;
    @FXML public Button cleanButton, cancelButton, acceptButton;

    private Assignment assignment;
    private Group group;
    private VBox AssignmentContainer;
    private AssignmentCreatedListener assignmentCreatedListener;
    private UserListAssignmentsController userListAssignmentsController;
    public UserGroupBorderPaneViewController mainController;

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

        loadAssignmentData();
        spinnerConfiguration(spinnerHour, 23);
        spinnerConfiguration(spinnerMinute, 59);
        acceptButton.setOnAction(event ->
                //pendiente agregar alerta de confirmación
                handleCreateAssignment()
                );
    }

    public void handleCreateAssignment() {
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
            if (assignment == null) {
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
            }
            else{
                //If you are just editing
                UserViewController.getCurrentAssignment().setTitle(title);
                UserViewController.getCurrentAssignment().setDescription(description);
                UserViewController.getCurrentAssignment().setDeadline(dueDateTime);

                UserButtonAssignmentController btnController = UserViewController.getCurrentAssignment().getButtonController();
                if (btnController != null) {
                    btnController.setHomeworkTitle(title);
                }

                AlertHandler.showAlert(
                        Alert.AlertType.INFORMATION,
                        "Assignment updated",
                        "the assignment has been updated successfully",
                        "Changes were saved"
                );
            }

            if (AssignmentContainer != null) {
                addAssignmentView(assignment); //Cuando es nuevo lo añade, pero  debemos hacer que cuando no es nuevo lo actualice
            }

            Stage stage = (Stage) acceptButton.getScene().getWindow();
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Create assignment",
                    "Successfully created assignment",
                    "The assignment was successfully created"
            );
            stage.close();
        }
    }

    private void addAssignmentView(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/new-interface/button-assignment.fxml"));
            Parent taskView = loader.load();

            UserButtonAssignmentController controller = loader.getController();
            assignment.setButtonController(controller);
            controller.setAssigment(assignment);
            controller.setHomeworkTitle(assignment.getTitle());
            controller.setAssigmentContainer(AssignmentContainer);

            controller.setOnClick(()->{
                UserViewController.setCurrentAssignment(assignment);
                assignment.setButtonController(controller);
                try{
                    mainController.setloadCenter("/views/new-interface/user-homework-view.fxml");
                    mainController.setUserListAssignmentsController(userListAssignmentsController);
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
        } catch (IOException e){
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


    public void clearAll(ActionEvent actionEvent) {

    }

    public void cancelHomework(ActionEvent actionEvent) {
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
}

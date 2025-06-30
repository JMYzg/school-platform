package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class UserNew_EditAssignmentController {

    @FXML
    TextField titleTF;
    @FXML TextArea descriptionTF;
    @FXML DatePicker datePicker;
    @FXML Spinner<Integer> spinnerHour;
    @FXML Spinner<Integer>  spinnerMinute;
    @FXML public Button cleanButton, cancelButton, acceptButton;

    private VBox AssignmentContainer;
    private Assignment assignment;
    private Group group;

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

            LocalDateTime dueDateTime = LocalDateTime.of(date, LocalTime.of(hour,minute));
            if (assignment == null) {
                //If the assign is new
                assignment = new Assignment(title,description,dueDateTime, group);
                addAssignmentView(assignment); //Cuando es nuevo lo añade, pero  debemos hacer que cuando no es nuevo lo actualice
            }else{
                //If you are just editing
                assignment.setTitle(title);
                assignment.setDescription(description);
                assignment.setDeadline(dueDateTime);
                //Origen del problema
                updateAssignmentView(assignment);
            }
            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
        }
    }

    private void addAssignmentView(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/new-interface/user-homework-container.fxml"));
            Node taskView = loader.load();

            UserButtonAssignmentController controller = loader.getController();
            controller.setAssigment(assignment);

            controller.setHomeworkTitle(assignment.getTitle());
            controller.setCreationDate(assignment.getCreationDate().toString());
            controller.setHomeworkDeadline(assignment.getDeadline().toString());
            //falta agregar el dia de creación, color y puntos

            AssignmentContainer.getChildren().add(taskView);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateAssignmentView(Assignment assignment) {

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

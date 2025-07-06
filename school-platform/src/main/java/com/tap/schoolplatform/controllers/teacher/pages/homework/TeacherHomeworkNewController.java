//package com.tap.schoolplatform.controllers.teacher.pages.homework;
//
//import com.tap.schoolplatform.controllers.alerts.AlertHandler;
//import com.tap.schoolplatform.controllers.teacher.pages.TeacherViewPage;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.util.StringConverter;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Map;
//import java.util.Optional;
//
//public class TeacherHomeworkNewController extends TeacherViewPage {
//
//    public static final String PATH = "/views/teacher-views/teacher-option-homework-new-view.fxml";
//
//    @FXML private TextField titleTF, unitTextField;
//    @FXML private TextArea descriptionTF;
//    @FXML private DatePicker datePicker;
//    @FXML private Spinner<Integer> spinnerHour;
//    @FXML private Spinner<Integer> spinnerMinute;
//    @FXML private Button cleanButton, cancelButton, acceptButton;
//
//    private VBox homeworkViewContainer;
//    private Assignment assignment;
//    //
//    private Map<Assignment, TeacherHomeworkContainerController> containerMap;
//    //
//    public void setHomeworkViewContainer(VBox homeworkViewContainer) {
//        this.homeworkViewContainer = homeworkViewContainer;
//    }
//    public void setAssignment(Assignment assignment) {
//        this.assignment = assignment;
//    }
//
//    //
//    public void setContainerMap(Map<Assignment, TeacherHomeworkContainerController> containerMap) {
//        this.containerMap = containerMap;
//    }
//    //
//
//    @FXML private void initialize() {
//        spinnerConfiguration(spinnerHour, 23);
//        spinnerConfiguration(spinnerMinute, 59);
//    }
//
//    @FXML private void handleCreateAssignment() throws IOException {
//        String title = titleTF.getText();
//        String description = descriptionTF.getText();
//        LocalDate dueDate = datePicker.getValue();
//        int hour = spinnerHour.getValue();
//        int minute = spinnerMinute.getValue();
//        Unit unit = subject.getUnit(Integer.parseInt(unitTextField.getText().trim()));
//
//
//        if (title.isEmpty() || description.isEmpty() || hour == 0 || dueDate == null) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Empty fields",
//                    "All the fields must be required",
//                    "Please enter all the fields correctly");
//        } else {
//
//
//            LocalDateTime dueDateTime = LocalDateTime.of(dueDate, LocalTime.of(hour,minute));
//            if (assignment == null) {
//                //If the assign is new
//                assignment = new Assignment(unit,title,description,dueDateTime);
//                unit.addAssignment(assignment);
//                addAssignmentView(assignment); //Cuando es nuevo lo añade, pero  debemos hacer que cuando no es nuevo lo actualice
//
//            }else{
//                //If you are just editing
//                assignment.setTitle(title);
//                assignment.setDescription(description);
//                assignment.setDeadline(dueDateTime);
//                updateAssignmentView(assignment);
//            }
//
//        }
//    }
//
//    @FXML private void cancelHomework() {
//        Optional<ButtonType> response =
//                AlertHandler.showAlert(
//                        Alert.AlertType.CONFIRMATION,
//                        "Cancel homework",
//                        "Are you sure you want to cancel this homework?",
//                        "All content will be lost"
//                );
//        if (response.isPresent() && response.get() == ButtonType.OK) {
//            Stage stage = (Stage) cancelButton.getScene().getWindow();
//            stage.close();
//        }
//    }
//
//    @FXML private void createHomework() throws IOException {
//
//        Optional<ButtonType> response =
//                AlertHandler.showAlert(
//                        Alert.AlertType.CONFIRMATION,
//                        "Confirm new homework",
//                        "Are you sure you want to create this homework",
//                        "You'll be able to modify this homework later on"
//                );
//        if (response.isPresent() && response.get() == ButtonType.OK) {
//            // code to create the new homework
//            handleCreateAssignment();
//            Stage stage = (Stage) acceptButton.getScene().getWindow();
//            stage.close();
//        }
//    }
//
//    @FXML private void clearAll() {
//        Optional<ButtonType> response =
//                AlertHandler.showAlert(
//                        Alert.AlertType.CONFIRMATION,
//                        "Clear all",
//                        "Are you sure you want to clear this homework?",
//                        "Content will be lost"
//                );
//        if (response.isPresent() && response.get() == ButtonType.OK) {
//            titleTF.clear();
//            descriptionTF.clear();
//            datePicker.setValue(null);
//            // spinner hours
//            // spinner minutes
//        }
//    }
//
//    private void addAssignmentView(Assignment assignment) {
//        try{
//            FXMLLoader Loader = new FXMLLoader(getClass().getResource(TeacherHomeworkContainerController.CONTAINER_PATH));
//            Node taskView = Loader.load();
//
//            TeacherHomeworkContainerController controller = Loader.getController();
//            controller.setAssignment(assignment);
//
//            controller.setTitle(assignment.getTitle());
//            controller.setDueDate(assignment.getDeadline());
//            controller.setCreationDate(assignment.getCreationDate());
//            homeworkViewContainer.getChildren().add(taskView);
//            //
//            containerMap.put(assignment, controller);
//            //
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Este metodo genera los problemas
//    private void updateAssignmentView(Assignment assignment) {
//        TeacherHomeworkContainerController controller = containerMap.get(assignment);
//        if (controller != null) {
//            controller.setTitle(assignment.getTitle());
//            controller.setDueDate(assignment.getDeadline());
//            controller.setCreationDate(assignment.getCreationDate());
//        }
//    }
//    //
//
//    //Esta información que se carga en automatico se debe actualizar en el Container
//    private void loadAssignmentData() {
//        if(assignment != null){
//            titleTF.setText(assignment.getTitle());
//            descriptionTF.setText(assignment.getDescription());
//            LocalDateTime deadline = assignment.getDeadline();
//            datePicker.setValue(deadline.toLocalDate());
//            spinnerHour.getValueFactory().setValue(deadline.getHour());
//            spinnerMinute.getValueFactory().setValue(deadline.getMinute());
//            unitTextField.setText(assignment.getUnit().toString());
//        }
//    }
//
//
//    private void spinnerConfiguration(Spinner<Integer> spinner, int max) {
//        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max);
//        valueFactory.setValue(0);
//
//        valueFactory.setConverter(new StringConverter<Integer>() {
//
//            public String toString(Integer value) {
//                return String.format("%02d", value);
//            }
//
//            public Integer fromString(String string) {
//                return Integer.parseInt(string);
//            }
//        });
//        spinner.setValueFactory(valueFactory);
//    }
//}
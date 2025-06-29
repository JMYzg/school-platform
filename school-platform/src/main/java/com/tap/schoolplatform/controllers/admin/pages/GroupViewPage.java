//package com.tap.schoolplatform.controllers.admin.pages;
//
//import com.tap.schoolplatform.controllers.alerts.AlertHandler;
//import com.tap.schoolplatform.models.academic.enums.Shift;
//import com.tap.schoolplatform.utils.SharedData;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.ComboBox;
//import javafx.stage.Stage;
//
//import java.util.Optional;
//
//public class GroupViewPage {
//
//    public static String PATH = "/views/admin-views/group-view.fxml";
//
//    public ComboBox<Degree> degreeComboBox;
//    public ComboBox<Semester> semesterComboBox;
//    public ComboBox<Shift> shiftComboBox;
//
//    public Button
//            addButton,
//            clearAllButton,
//            cancelButton;
//
//    SharedData sharedData = SharedData.getInstance();
//
//    public void initialize() {
//        setListeners();
//        degreeComboBox.setItems(sharedData.getDegrees());
////        refreshDegreeCB();
//        shiftComboBox.getItems().addAll(Shift.values());
//        shiftComboBox.setEditable(false);
//        shiftComboBox.setDisable(true);
//        degreeComboBox.setEditable(false);
//        semesterComboBox.setEditable(false);
//        semesterComboBox.setDisable(true);
//    }
//
//    private void setListeners() {
//        degreeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                semesterComboBox.setDisable(false);
//                semesterComboBox.setItems(newValue.getSemesters());
//            } else {
//                semesterComboBox.setDisable(true);
//                semesterComboBox.setItems(null);
//                shiftComboBox.setDisable(true);
//                shiftComboBox.setItems(null);
//            }
//        });
//
//        semesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            shiftComboBox.setDisable(newValue == null);
//        });
//
//        shiftComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            addButton.setDisable(newValue == null);
//        });
//
//    }
//
//    public void addGroup() {
////        Degree selectedDegree = degreeComboBox.getSelectionModel().getSelectedItem();
//        Shift selectedShift = shiftComboBox.getSelectionModel().getSelectedItem();
//        Semester selectedSemester = semesterComboBox.getSelectionModel().getSelectedItem();
////        if (selectedDegree == null || selectedSemester == null || selectedShift == null) {
////            AlertHandler.showAlert(
////                    Alert.AlertType.ERROR,
////                    "Error",
////                    "Not a valid format",
////                    "Please select a valid degree, semester and shift"
////            );
////        } else {
////            selectedSemester.
////
////        }
//        if (selectedShift != null) {
//            new Group(selectedSemester, selectedShift);
//            Stage stage = (Stage) addButton.getScene().getWindow();
//            Optional<ButtonType> response = AlertHandler.showAlert(
//                    Alert.AlertType.INFORMATION,
//                    "Success",
//                    "Group added",
//                    "The group has been added successfully"
//            );
//            if (response.isPresent() && response.get() == ButtonType.OK) {
//                stage.close();
//            }
//        } else {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "Not a valid format",
//                    "Please select a valid shift"
//            );
//        }
//    }
//
//    public void clearAll() {
//        Optional<ButtonType> response =
//                AlertHandler.showAlert(
//                Alert.AlertType.CONFIRMATION,
//                        "Clear all",
//                        "Clear all fields",
//                        "Are you sure you want to clear all the fields?"
//        );
//        if (response.isPresent() && response.get() == ButtonType.OK) {
//            degreeComboBox.setItems(null);
//        }
//    }
//
//    public void cancelGroup() {
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
//        stage.close();
//    }
//
//    private void refreshDegreeCB() {
//        degreeComboBox.getItems().setAll(sharedData.getDegrees());
//    }
//
//}

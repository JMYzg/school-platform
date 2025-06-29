//package com.tap.schoolplatform.controllers.admin.pages;
//
//import com.tap.schoolplatform.controllers.alerts.AlertHandler;
//import com.tap.schoolplatform.utils.SharedData;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import javafx.util.StringConverter;
//
//import java.util.Optional;
//
//public class DegreeViewPage {
//
//    @FXML private static String PATH = "/views/admin-views/degree-view.fxml";
//
//    @FXML private TextField newDegreeField;
//
//    @FXML private ComboBox<Degree> degreeComboBox;
//
//    @FXML private Button
//            addButton,
//            clearAllButton,
//            cancelButton;
//
//    SharedData sharedData = SharedData.getInstance();
//
//    @FXML private void initialize() {
//        degreeComboBox.setEditable(false);
//        refreshCBDegree();
//    }
//
//    @FXML private void addDegree() {
//        if (newDegreeField.getText().isEmpty()) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "Not a valid format",
//                    "Please enter a valid degree name"
//            );
//        } else if (degreeComboBox.getItems().stream().anyMatch(degree -> degree.getName().equals(newDegreeField.getText()))) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "There is already a degree with this name",
//                    "Please write a non existing degree"
//            );
//        } else {
//            Optional<ButtonType> response =
//                    AlertHandler.showAlert(
//                            Alert.AlertType.CONFIRMATION,
//                            "Please confirm",
//                            "Adding new degree",
//                            "Are you sure you want to add this degree?"
//                    );
//
//            if (response.isPresent() && response.get() == ButtonType.OK) {
//                Degree newDegree = new Degree(newDegreeField.getText(), 9); // change the numbers of semesters using the upcoming combobox
//                sharedData.addDegree(newDegree);
//                AlertHandler.showAlert(
//                        Alert.AlertType.INFORMATION,
//                        "Success",
//                        "Degree added",
//                        "The degree has been added successfully"
//                );
//                Stage stage = (Stage) addButton.getScene().getWindow();
//                stage.close();
//            }
//        }
//    }
//
//    @FXML private void clearAll() {
//        degreeComboBox.valueProperty().set(null);
//        newDegreeField.clear();
//    }
//
//    @FXML private void cancelDegree() {
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
//        stage.close();
//    }
//
//    private void refreshCBDegree() {
//        degreeComboBox.getItems().addAll(sharedData.getDegrees());
//        degreeComboBox.setConverter(new StringConverter<Degree>() {
//                                        @Override
//                                        public String toString(Degree degree) {
//                                            if(degree != null) return degree.getName();
//                                            else return null;
//                                        }
//                                        @Override
//                                        public Degree fromString(String s) {
//                                            return null;
//                                        }
//                                    }
//        );
//    }
//
//}

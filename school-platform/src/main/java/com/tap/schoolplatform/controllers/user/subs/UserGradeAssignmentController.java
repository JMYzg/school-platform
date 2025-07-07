package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.services.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

import static com.tap.schoolplatform.controllers.admin.AdminViewController.injectCellValues;


public class UserGradeAssignmentController {

    @FXML
    private Button
            cancelButton,
            acceptButton;
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> points;

    @FXML
    private TableColumn<User, String>
            id,
            lastName,
            name,
            status,
            file;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    public void initialize() {
        setTable();
    }

    public void setTable() {
        users.clear();
        users.setAll(
                Service.getEvery(User.class)
                        .stream()
                        .filter(user -> user.getType() != Type.ADMIN)
                        .toList()
        );
        tableView.setItems(users);

        TableColumn<?, ?>[] tableColumns = {
                id,
                name,
                lastName,
                status,
                file
        };

        String[] properties = {
                "id",
                "name",
                "lastName",
                "status",
                "file"
        };
        injectCellValues(tableColumns, properties);
    }

    public void cancelGrading(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Cancelling grading",
                "Are you sure you want to cancel this operation?"
        );
        if (response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void acceptGrading(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(
                Alert.AlertType.CONFIRMATION,
                "Please confirm",
                "Accepting grading",
                "Are you sure you want to accept this operation?"
        );
        if (response.get() == ButtonType.OK) {
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Confirmed",
                    "Grading completed",
                    "Grading completed successfully"
            );
            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
        }
    }
}
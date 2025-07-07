package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.academic.tasks.Submission;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class UserGradeAssgnmentController {

    public TableView<Submission> tableView;
    public TableColumn<Submission, String> idColumn;
    public TableColumn<Submission, String> lastNameColumn;
    public TableColumn<Submission, String> nameColumn;
    public TableColumn<Submission, Void> fileColumn;
    public TableColumn<Submission, Void> scoreColumn;
    public Button cancelButton;
    public Button acceptButton;

    public void initialize() {
        bindTable();
    }

    public void bindTable() {
        ObservableList<Submission> items = FXCollections.observableArrayList();
        items.setAll(Service.getEvery(Submission.class).stream().filter(submission -> submission.getAssignment().equals(UserViewController.CURRENT_ASSIGNMENT)).toList());

        tableView.setItems(items);

        bindColumns(idColumn, nameColumn, lastNameColumn);

        fileColumn.setCellFactory(cell -> new TableCell<Submission, Void>() {
            private final Hyperlink hyperLink = new Hyperlink("Download file");

            {
                hyperLink.setOnAction(event -> {
                    Submission submission = getTableView().getItems().get(getIndex());
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Choose a location to save the file");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

                    File pdf = fileChooser.showSaveDialog(hyperLink.getScene().getWindow());

                    if (pdf != null) {
                        try (OutputStream outputStream = new FileOutputStream(pdf)) {
                            // Aquí escribes el contenido que quieres guardar, por ejemplo, un PDF
                            // Supongamos que ya tienes los datos en un arreglo de bytes llamado `byte[] pdfData`

                            outputStream.write(submission.getContent()); // Escribe los datos en el archivo
                            outputStream.flush();

                        } catch (IllegalArgumentException | IOException ex) {
                            AlertHandler.showAlert(
                                    Alert.AlertType.ERROR,
                                    "Error",
                                    "Error saving file",
                                    "Could not save the file, please try again.\n" + ex.getMessage());
                        }
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hyperLink);
            }
        });

        scoreColumn.setCellFactory(cell -> new TableCell<Submission, Void>() {
            private final TextField textField = new TextField();
            {
                textField.setOnAction(event -> {
                    Submission submission = getTableView().getItems().get(getIndex());
                    try {
                        Double score = Double.parseDouble(textField.getText());
                        if (score < Assignment.MIN_SCORE || score > Assignment.MAX_SCORE) {
                            throw new IllegalArgumentException("The score must be between 0 and 10");
                        }
                        submission.setScore(score);
                        Service.update(submission);
                        AlertHandler.showAlert(
                                Alert.AlertType.INFORMATION,
                                "Correct",
                                "Score updated",
                                "Score updated successfully");
                    } catch (IllegalArgumentException e) {
                        AlertHandler.showAlert(
                                Alert.AlertType.ERROR,
                                "Error",
                                "Error updating score",
                                "The score must be between 0 and 10");
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Submission submission = getTableView().getItems().get(getIndex());
                    textField.setText(submission.getScore() != Assignment.MIN_SCORE ? Double.toString(submission.getScore()) : "Not yet qualified");
                    setGraphic(textField);
                }
            }
        });

    }

    public void cancelGrading(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(Alert.AlertType.CONFIRMATION, "Please confirm", "Cancelling grading", "Are you sure you want to cancel this operation?");
        if (response.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void acceptGrading(ActionEvent actionEvent) {
        Optional<ButtonType> response = AlertHandler.showAlert(Alert.AlertType.CONFIRMATION, "Please confirm", "Accepting grading", "Are you sure you want to accept this operation?");
        if (response.get() == ButtonType.OK) {
            AlertHandler.showAlert(Alert.AlertType.INFORMATION, "Confirmed", "Grading completed", "Grading completed successfully");
            Stage stage = (Stage) acceptButton.getScene().getWindow();
            stage.close();
        }
    }

    public void bindColumns(TableColumn<Submission, String> id, TableColumn<Submission, String> name, TableColumn<Submission, String> lastName) {
        id.setCellValueFactory(cell -> new ReadOnlyStringWrapper(Integer.toString(cell.getValue().getUser().getId())));
        name.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getUser().getName()));
        lastName.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getUser().getLastName()));
    }

}

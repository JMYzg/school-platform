package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.tasks.Submission;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

public class UserGradeAssgnmentController {

    public TableView<Submission> tableView;
    public TableColumn<Submission, String> idColumn;
    public TableColumn<Submission, String> lastNameColumn;
    public TableColumn<Submission, String> nameColumn;
    public TableColumn<Submission, String> statusColumn;
    public TableColumn<Submission, Void> fileColumn;
    public TableColumn<Double, Void> scoreColumn;
    public Button cancelButton;
    public Button acceptButton;

    public void initialize() {
        bindTable();
    }

    public void bindTable() {
        ObservableList<Submission> items = FXCollections.observableArrayList();
        items.setAll(
                Service.getEvery(Submission.class)
                        .stream()
                        .filter(submission -> submission.getAssignment().equals(UserViewController.CURRENT_ASSIGNMENT))
                        .toList()
        );
        tableView.setItems(items);
        TableColumn<?, ?>[] columns = {
                idColumn,
                lastNameColumn,
                nameColumn,
                statusColumn
        };
        for (int i = 0; i < columns.length; i++) {

        }
    }
}

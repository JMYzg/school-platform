package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class UserViewController extends ViewController {

    @FXML public Label adminNameLabel;
    @FXML public Button logoutButton, createButton, joinButton, backButton;
    @FXML public BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        // Cargar user-data-view.fxml en el centro al iniciar
        loadCenter("views/new-interface/user-data-view.fxml");

        backButton.setVisible(false); // Por ahora no se usa

        // JOIN → nueva ventana
        joinButton.setOnAction(event ->
                openInNewWindow("views/new-interface/user-join-view.fxml", "Join a Group"));

        // CREATE → nueva ventana
        createButton.setOnAction(event ->
                openInNewWindow("views/new-interface/user-group-create-view.fxml", "Create a Group"));
    }

    private void loadCenter(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogoutClick(ActionEvent actionEvent) {
        try {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Performing logout",
                            "Are you sure you want to log out?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK)
                toView(LOGIN_VIEW, "Log in", logoutButton);
        } catch (IOException e) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Resource not found",
                    e.getMessage()
            );
        }
    }

    public void back(ActionEvent actionEvent) {

    }

    public void createAction(ActionEvent actionEvent) {

    }

    public void joinAction(ActionEvent actionEvent) {

    }
}

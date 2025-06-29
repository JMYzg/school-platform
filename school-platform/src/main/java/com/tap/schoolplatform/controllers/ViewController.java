package com.tap.schoolplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class ViewController {

    protected static final String LOGIN_VIEW = "/views/new-interface/login-view.fxml";
    protected static final String ADMIN_VIEW = "/views/admin-views/admin-view.fxml";
    protected static final String USER_VIEW = "/views/new-interface/user-view.fxml";

    public void toView(String view, String title, Control control) throws IOException {
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        newStage.setTitle(title);
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
    }

    public static void loadNewView(ActionEvent event, String view, String title) throws IOException {
        Stage ownerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.initOwner(ownerStage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(ViewController.class.getResource(view)));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

}

package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;


public class UserViewController extends ViewController {

    @FXML
    private Label
            adminNameLabel,
            name;
    @FXML
    private Button logoutButton;
    @FXML
    private Button createButton;
    @FXML
    private Button joinButton;
    @FXML
    private Button backButton;
    @FXML
    private BorderPane mainBorderPane;

    static public Group CURRENT_GROUP;
    static public Assignment CURRENT_ASSIGNMENT;
    private UserViewController userViewController;
    private UserDataViewController userDataViewController;

    public static Group getCurrentGroup() {
        return CURRENT_GROUP;
    }

    public static void setCurrentGroup(Group group) {
        CURRENT_GROUP = group;
    }

    public static void clearCurrentGroup() {
        CURRENT_GROUP = null;
    }

    public static Assignment getCurrentAssignment() {
        return CURRENT_ASSIGNMENT;
    }

    public static void setCurrentAssignment(Assignment assignment) {
        CURRENT_ASSIGNMENT = assignment;
    }

    public static void clearCurrentaAssignment() {
        CURRENT_ASSIGNMENT = null;
    }


    @FXML
    public void initialize() throws IOException {
        // Cargar user-data-view.fxml en el centro al iniciar
        loadCenter("views/new-interface/user-data-view.fxml");

        name.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");

        backButton.setVisible(false); // Por ahora no se usa

        // JOIN → nueva ventana
        joinButton.setOnAction(event ->
                openInNewWindow("views/new-interface/user-join-view.fxml", "Join a Group"));

        // CREATE → nueva ventana
        createButton.setOnAction(event ->
                openCreateGroupView("views/new-interface/user-group-create-view.fxml", "Create a Group"));

        backButton.setOnAction(e -> {
            try {
                loadCenter("views/new-interface/user-data-view.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showBackButton(false);
        });
    }

    public void loadCenter(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(UserViewController.class.getClassLoader().getResource(fxmlPath));
        Parent view = loader.load();
        Object controller = loader.getController();

        if (controller instanceof UserDataViewController userDataViewController) {
            userDataViewController.setMainController(this);
            this.userDataViewController = userDataViewController;
        }

        Node OldView = mainBorderPane.getCenter();

        if (OldView != null) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), OldView);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                view.setOpacity(0.0);
                mainBorderPane.setCenter(view);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), view);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        } else {
            view.setOpacity(0.0);
            mainBorderPane.setCenter(view);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), view);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }


    }

    private void openCreateGroupView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
            Parent root = loader.load();

            UserCreateGroupViewController controller = loader.getController();
            controller.setMainController(userDataViewController);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
            stage.show();
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
            UserJoinViewController controller = loader.getController();
            controller.setMainController(userDataViewController);
            stage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal hasta que se cierre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBackButton(boolean show) {
        backButton.setVisible(show);
        //quise hacer esto pero no funciono
        joinButton.setVisible(!show);
        createButton.setVisible(!show);
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
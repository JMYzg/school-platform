package com.tap.schoolplatform.controllers.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

public class AlertHandler {

    private static final ImageView INFO_IMAGE =
            new ImageView(Objects.requireNonNull(AlertHandler.class
                    .getResource("/images/info.png")).toExternalForm());
    private static final ImageView WARNING_IMAGE =
            new ImageView(Objects.requireNonNull(AlertHandler.class
                    .getResource("/images/warning.png")).toExternalForm());
    private static final ImageView ERROR_IMAGE =
            new ImageView(Objects.requireNonNull(AlertHandler.class
                    .getResource("/images/error.png")).toExternalForm());
    private static final ImageView CONFIRM_IMAGE =
            new ImageView(Objects.requireNonNull(AlertHandler.class
                    .getResource("/images/confirm.png")).toExternalForm());

    public static Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets()
                .add(Objects.requireNonNull(AlertHandler.class.getResource("/styles/alerts.css")).toExternalForm());
        alert.setGraphic(
                switch (type) {
                    case INFORMATION -> INFO_IMAGE;
                    case WARNING -> WARNING_IMAGE;
                    case ERROR -> ERROR_IMAGE;
                    default -> CONFIRM_IMAGE;
                }
        );
        return alert.showAndWait();
    }
}

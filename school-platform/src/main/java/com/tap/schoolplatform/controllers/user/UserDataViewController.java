package com.tap.schoolplatform.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserDataViewController {
    @FXML public ImageView userPhoto;
    @FXML public Label
            lastName,
            name,
            id,
            degree,
            gender,
            telephone,
            email,
            street,
            colony,
            pc,
            city,
            state,
            country;
    @FXML public AnchorPane groupIcon;

    void initialize() {

    }
}

package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

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
    @FXML public VBox vboxGroups;

    List<Group> userGroups = Service.find(LoginService.getCurrentUser().getId(), User.class)
            .getMemberships().stream()
            .map(Membership::getGroup)
            .collect(Collectors.toList()):;

    void initialize() {

    }
}

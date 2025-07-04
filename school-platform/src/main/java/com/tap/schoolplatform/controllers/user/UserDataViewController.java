package com.tap.schoolplatform.controllers.user;

import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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

    private UserViewController mainController;
    public void setMainController(UserViewController mainController) {
        this.mainController = mainController;
    }


    public void initialize() {
        generateGroupStack();
    }

    public void generateGroupStack() {
        List<Group> userGroups = Service.find(LoginService.getCurrentUser().getId(), User.class)
                .getMemberships().stream()
                .map(Membership::getGroup)
                .toList();
        vboxGroups.getChildren().clear();
        for (Group group : userGroups) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/new-interface/button-group.fxml"));
                Parent button = fxmlLoader.load();
                UserButtonGroupController controller = fxmlLoader.getController();
                controller.setGroup(group);

                controller.setOnClick(() -> {
                    UserViewController.setCurrentGroup(group);
                    try {
                        mainController.loadCenter("views/new-interface/user-group-borderPane-view.fxml");
                    } catch (IOException e) {
                        AlertHandler.showAlert(
                                Alert.AlertType.ERROR,
                                "Error",
                                "Resource not found",
                                e.getMessage()
                        );
                    }
                    mainController.showBackButton(true);
                });
                vboxGroups.getChildren().add(button);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

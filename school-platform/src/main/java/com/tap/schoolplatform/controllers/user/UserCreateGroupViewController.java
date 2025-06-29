package com.tap.schoolplatform.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UserCreateGroupViewController {


    @FXML TextField groupNameField;
    @FXML TextArea descriptionField;
    @FXML ColorPicker colorPicker;
    @FXML Button createButton, cancelButton;

    public void createGroup(ActionEvent actionEvent) {
    }

    public void cancelCreation(ActionEvent actionEvent) {
    }
}

package com.tap.schoolplatform.controllers.user;
import com.tap.schoolplatform.models.academic.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UserButtonGroupController {
    @FXML Button groupButton;
    @FXML AnchorPane colorGroup;
    @FXML Label groupName;

    private Group group;
    private Runnable onClick;

    public void setGroup(Group group) {
        this.group = group;
        groupName.setText(group.getName());

        groupButton.setOnAction(e -> {
            if (onClick != null) onClick.run();
        });
    }
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }
    public Group getGroup() {
        return group;
    }

    public void enterGroupView(ActionEvent actionEvent) {
        System.out.println("enterGroupView :D");
    }
}

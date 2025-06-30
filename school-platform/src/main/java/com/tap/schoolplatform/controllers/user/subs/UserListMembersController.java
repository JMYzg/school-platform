package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.models.academic.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserListMembersController {
    public TableView table;
    public TableColumn
            idTableColumn,
            lastNameTableColumn,
            nameTableColumn,
            emailTableColumn,
            roleTabbleColumn;

    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }
}

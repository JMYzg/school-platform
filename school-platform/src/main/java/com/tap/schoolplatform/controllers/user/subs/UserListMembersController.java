package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.user.UserDataViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.stream.Collectors;

import static com.tap.schoolplatform.controllers.admin.AdminViewController.injectCellValues;
import static com.tap.schoolplatform.controllers.user.UserViewController.CURRENT_GROUP;

public class UserListMembersController extends ViewController {
    @FXML public TableView table;
    @FXML public TableColumn
            id,
            lastName,
            name,
            email,
            role;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    private Group group;

    @FXML public void initialize() {
        bindTable();
        bindTableColumns();
    }

    private void bindTable() {
        users.clear();
        users.setAll(
                Service.find(CURRENT_GROUP.getId(), Group.class).getMemberships()
                        .stream()
                        .map(Membership::getUser)
                        .collect(Collectors.toList())
        );

        table.setItems(users);
    }

    private void bindTableColumns() {
        TableColumn<?, ?>[] tableColumns = {
                id,
                name,
                lastName,
                email,
                role
        };

        String[] properties = {
                "id",
                "name",
                "lastName",
                "email",
                "memberships"
        };

        injectCellValues(tableColumns, properties);
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

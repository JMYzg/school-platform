package com.tap.schoolplatform.controllers.user.subs;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.user.UserDataViewController;
import com.tap.schoolplatform.controllers.user.UserViewController;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.enums.Type;
import com.tap.schoolplatform.models.users.shared.Membership;
import com.tap.schoolplatform.services.Service;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.tap.schoolplatform.controllers.admin.AdminViewController.injectCellValues;
import static com.tap.schoolplatform.controllers.user.UserViewController.CURRENT_GROUP;

public class UserListMembersController extends ViewController {
    @FXML public TableView<User> table;
    @FXML public TableColumn<User, String>
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
        // Make the role column to just add the group's role and not all the users roles
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
                "email"
        };

        bindColumns(id, name, lastName, email);

        role.setCellValueFactory(cell -> {
            Role role = cell.getValue().getMemberships()
                    .stream()
                    .map(Membership::getRole)
                    .findFirst().orElse(null);
            assert role != null;
            return new ReadOnlyStringWrapper(role.toString());
        });
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void bindColumns (
            TableColumn<User, String>  id,
            TableColumn<User, String> name,
            TableColumn<User, String> lastName,
            TableColumn<User, String> email
    ) {
        id.setCellValueFactory(cell -> new ReadOnlyStringWrapper(Integer.toString(cell.getValue().getId())));
        name.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getName()));
        lastName.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getLastName()));
        email.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getEmail()));
    }

}

package com.tap.schoolplatform.controllers.admin;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Optional;

public class AdminViewController extends ViewController {

    // Attributes
    @FXML private Label adminNameLabel;

    @FXML private Button logoutButton;

    // StudentTab
    @FXML private Button
            studentNewButton,

            studentUploadImageButton,
            studentManageDegreeButton,
            studentManageGroupButton,

            studentEditButton,
            studentAcceptButton,
            studentCancelButton,

            studentFilterButton;

    @FXML private TextField
            studentNameField,
            studentLastNameField,
            studentPhoneField,
            studentEmailField,
            studentStreetField,
            studentPCField,
            studentColonyField,
            studentCityField,
            studentStateField,
            studentCountryField,

            studentSearchField;

    @FXML private ComboBox<Gender> studentGenderComboBox;
    @FXML private ComboBox<Degree> studentDegreeComboBox;
    @FXML private ComboBox<Group> studentGroupComboBox;

    @FXML private DatePicker studentDatePicker;

    @FXML private ImageView studentImageView;

    @FXML private TableView<Student> studentTableView;

    @FXML private TableColumn<Student, String>
            studentIDTableColumn,
            studentNameTableColumn,
            studentLastNameTableColumn,
            studentEmailTableColumn,
            studentPhoneTableColumn;
    @FXML private TableColumn<Student, Degree> studentDegreeTableColumn;
    @FXML private TableColumn<Student, Group> studentGroupTableColumn;
    @FXML private TableColumn<Student, Gender> studentGenderTableColumn;
    @FXML private TableColumn<Student, String>
            studentStreetTableColumn,
            studentPCTableColumn,
            studentColonyTableColumn,
            studentCityTableColumn,
            studentStateTableColumn,
            studentCountryTableColumn;
    @FXML private TableColumn<Student, Integer> studentAgeTableColumn;

    // TeacherTab
    @FXML private Button
            teacherNewButton,

            teacherManageDegreeButton,
            teacherCreateSubjectButton,
            teacherAssignSubjectButton,
            teacherUnassignSubjectButton,

            teacherEditButton,
            teacherAcceptButton,
            teacherCancelButton,

            teacherFilterButton;

    @FXML private TextField
            teacherNameField,
            teacherLastNameField,
            teacherLicenseField,
            teacherSpecializationField,
            teacherPhoneField,
            teacherEmailField,
            teacherStreetField,
            teacherPCField,
            teacherColonyField,
            teacherCityField,
            teacherStateField,
            teacherCountryField,

            teacherSubjectField,

            teacherSearchField;

    @FXML private ComboBox<Gender> teacherGenderComboBox;
    @FXML private ComboBox<Degree> teacherDegreeComboBox;
    @FXML private ComboBox<Subject>
            teacherAssignSubjectComboBox,
            teacherUnassignSubjectComboBox;
    @FXML private ComboBox<Semester>
            teacherSubjectSemesterComboBox,
            teacherAssignSubjectSemesterComboBox,
            teacherUnassignSubjectSemesterComboBox;

    @FXML private DatePicker teacherDatePicker;

    @FXML private TableView<Teacher> teacherTableView;

    @FXML private TableColumn<Teacher, String>
            teacherLicenseTableColumn,
            teacherNameTableColumn,
            teacherLastNameTableColumn,
            teacherSpecializationTableColumn,
            teacherEmailTableColumn,
            teacherPhoneTableColumn;
    @FXML private TableColumn<Teacher, Degree> teacherDegreeTableColumn;
    @FXML private TableColumn<Teacher, Gender> teacherGenderTableColumn;
    @FXML private TableColumn<Teacher, String>
            teacherStreetTableColumn,
            teacherPCTableColumn,
            teacherColonyTableColumn,
            teacherCityTableColumn,
            teacherStateTableColumn,
            teacherCountryTableColumn;
    @FXML private TableColumn<Teacher, Integer> teacherAgeTableColumn;

    // Methods
    @FXML private void initialize() {
        adminNameLabel.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");
        bindTableViews();
        disableStudentForm(true);
        disableTeacherForm(true);
        bindStudentTableColumns();
        bindTeacherTableColumns();
    }

    @FXML private void onLogoutClick() {
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

    // StudentTab
    @FXML private void studentNewButtonHandler() {

    }

    @FXML private void onStudentUploadImageClick() {

    }
    @FXML private void onStudentManageDegreeClick() {

    }
    @FXML private void onStudentManageGroupClick() {

    }

    @FXML private void studentEditButtonHandler() {

    }
    @FXML private void studentAcceptButtonHandler() {

    }
    @FXML private void studentCancelButtonHandler() {

    }

    @FXML private void onStudentFilterClick() {

    }

    @FXML private void studentSelectionHandler() {

    }

    // TeacherTab
    @FXML private void teacherNewButtonHandler() {

    }

    @FXML private void onTeacherManageDegreeClick() {

    }

    @FXML private void onTeacherCreateSubjectClick() {

    }

    @FXML private void onTeacherAssignSubjectClick() {

    }
    @FXML private void onTeacherUnassignSubjectClick() {

    }

    @FXML private void teacherEditButtonHandler() {

    }
    @FXML private void teacherAcceptButtonHandler() {

    }
    @FXML private void teacherCancelButtonHandler() {

    }

    @FXML private void onTeacherFilterClick() {

    }

    @FXML private void teacherSelectionHandler() {

    }

    // Utils
    private void bindTableViews() {
        studentTableView.setItems(data.getUsers(Role.STUDENT));
        teacherTableView.setItems(data.getUsers(Role.TEACHER));
    }

    private void bindStudentTableColumns() {
        TableColumn<?, ?>[] studentTableColumns = {
                studentIDTableColumn,
                studentNameTableColumn,
                studentLastNameTableColumn,
                studentEmailTableColumn,
                studentPhoneTableColumn,
                studentDegreeTableColumn,
                studentGroupTableColumn,
                studentGenderTableColumn,
                studentAgeTableColumn
        };

        String[] properties = {
                "ID",
                "name",
                "lastName",
                "email",
                "phone",
                "degree",
                "group",
                "gender",
                "age"
        };
        injectCellValues(studentTableColumns, properties);

        bindAddressTableColumns(
                studentStreetTableColumn,
                studentPCTableColumn,
                studentColonyTableColumn,
                studentCityTableColumn,
                studentStateTableColumn,
                studentCountryTableColumn
        );
    }

    private void bindTeacherTableColumns() {
        TableColumn<?, ?>[] TeacherTableColumns = {
                teacherLicenseTableColumn,
                teacherNameTableColumn,
                teacherLastNameTableColumn,
                teacherSpecializationTableColumn,
                teacherEmailTableColumn,
                teacherPhoneTableColumn,
                teacherDegreeTableColumn,
                teacherGenderTableColumn,
                teacherAgeTableColumn
        };

        String[] properties = {
                "license",
                "name",
                "lastName",
                "specialization",
                "email",
                "phone",
                "degree",
                "gender",
                "age"
        };
        injectCellValues(TeacherTableColumns, properties);
        bindAddressTableColumns(
                teacherStreetTableColumn,
                teacherPCTableColumn,
                teacherColonyTableColumn,
                teacherCityTableColumn,
                teacherStateTableColumn,
                teacherCountryTableColumn
        );
}

    private void injectCellValues(TableColumn<?, ?>[] columns, String[] properties) {
        for (int i = 0; i < columns.length; i++) {
            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
    }

    private <T extends User> void bindAddressTableColumns(
            TableColumn<T, String> streetTableColumn,
            TableColumn<T, String> PCTableColumn,
            TableColumn<T, String> colonyTableColumn,
            TableColumn<T, String> cityTableColumn,
            TableColumn<T, String> stateTableColumn,
            TableColumn<T, String> countryTableColumn) {

        streetTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().streetProperty());
        PCTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().postalCodeProperty());
        colonyTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().colonyProperty());
        cityTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().cityProperty());
        stateTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().stateProperty());
        countryTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().countryProperty());
    }

    private void disableStudentForm(boolean toggle) {
        disableTextFields(toggle,
                studentNameField,
                studentLastNameField,
                studentPhoneField,
                studentEmailField,
                studentStreetField,
                studentPCField,
                studentColonyField,
                studentCityField,
                studentStateField,
                studentCountryField
        );
        disableComboBoxes(toggle,
                studentGenderComboBox,
                studentDegreeComboBox,
                studentGroupComboBox
        );
        disableButtons(toggle,
                studentManageDegreeButton,
                studentManageGroupButton,
                studentUploadImageButton
        );
        studentDatePicker.setDisable(toggle);
        studentImageView.setDisable(toggle);
    }

    private void disableTeacherForm(boolean toggle) {
        disableTextFields(toggle,
                teacherNameField,
                teacherLastNameField,
                teacherLicenseField,
                teacherSpecializationField,
                teacherPhoneField,
                teacherEmailField,
                teacherStreetField,
                teacherPCField,
                teacherColonyField,
                teacherCityField,
                teacherStateField,
                teacherCountryField
        );
        disableComboBoxes(toggle,
                teacherGenderComboBox,
                teacherDegreeComboBox,

                teacherAssignSubjectComboBox,
                teacherUnassignSubjectComboBox,

                teacherSubjectSemesterComboBox,
                teacherAssignSubjectSemesterComboBox,
                teacherUnassignSubjectSemesterComboBox
        );
        disableButtons(toggle,
                teacherManageDegreeButton,
                teacherCreateSubjectButton,
                teacherAssignSubjectButton,
                teacherUnassignSubjectButton
        );
        teacherDatePicker.setDisable(toggle);
    }

    private void disableTextFields(boolean value, TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setDisable(value);
        }
    }

    private void disableComboBoxes(boolean value, ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setDisable(value);
        }
    }

    private void disableButtons(boolean value, Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(value);
        }
    }
}

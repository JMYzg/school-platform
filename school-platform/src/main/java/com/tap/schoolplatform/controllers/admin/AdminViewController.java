package com.tap.schoolplatform.controllers.admin;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.academic.enums.Shift;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.services.auth.LoginService;
import com.tap.schoolplatform.utils.Validation;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

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
    @FXML private ComboBox<Semester> studentSemesterComboBox;
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
    @FXML private TableColumn<Student, Semester> studentSemesterTableColumn;
    @FXML private TableColumn<Student, Group> studentGroupTableColumn;
    @FXML private TableColumn<Student, Gender> studentGenderTableColumn;
    @FXML private TableColumn<Student, String>
            studentStreetTableColumn,
            studentPCTableColumn,
            studentColonyTableColumn,
            studentCityTableColumn,
            studentStateTableColumn,
            studentCountryTableColumn;
    @FXML private TableColumn<Student, LocalDate> studentBirthDateTableColumn;
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
    @FXML private TableColumn<Teacher, LocalDate> teacherBirthDateTableColumn;
    @FXML private TableColumn<Teacher, Integer> teacherAgeTableColumn;

    // Methods
    @FXML private void initialize() {
        adminNameLabel.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");
        bindTableViews();
        bindStudentTableColumns();
        bindTeacherTableColumns();
        disableComboBoxes(true,
                studentSemesterComboBox,
                studentGroupComboBox
                // ...
        );
        notEditableComboBoxes();
        bindComboBoxes();
        createDependencies(studentDegreeComboBox, studentSemesterComboBox);

        studentSemesterComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (studentSemesterComboBox.isDisabled()) {
                studentGroupComboBox.setDisable(true);
            }
            if (newValue != null) {
                studentGroupComboBox.getSelectionModel().clearSelection();
                studentGroupComboBox.setItems(FXCollections.observableArrayList());
                studentGroupComboBox.setDisable(false);
                for (Shift shift : newValue.getGroups().keySet()) {
                    studentGroupComboBox.getItems().setAll(newValue.getGroups(shift));
                    studentGroupComboBox.setConverter(new StringConverter<>() {
                        @Override
                        public String toString(Group group) {
                            return (group == null) ? "" : group.toString();
                        }
                        @Override
                        public Group fromString(String string) {
                            return null;
                        }
                    });
                }
                if (studentGroupComboBox.getItems().isEmpty()) studentGroupComboBox.setDisable(true);
            } else {
                studentGroupComboBox.getSelectionModel().clearSelection();
                studentGroupComboBox.setDisable(true);
            }
        });

        createDependencies(teacherDegreeComboBox, teacherSubjectSemesterComboBox);

        disableButtons(true,
                studentNewButton,

                studentEditButton,
                studentCancelButton,

                teacherNewButton,

                teacherEditButton,
                teacherCancelButton
        );
        studentAcceptButton.setText("Create");
        teacherAcceptButton.setText("Create");
    }

    private void createDependencies(ComboBox<Degree> studentDegreeComboBox, ComboBox<Semester> studentSemesterComboBox) {
        studentDegreeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (studentDegreeComboBox.isDisabled()) {
                studentSemesterComboBox.setDisable(true);
            }
            if (newValue != null) {
                studentSemesterComboBox.getSelectionModel().clearSelection();
                studentSemesterComboBox.setItems(FXCollections.observableArrayList());
                studentSemesterComboBox.setDisable(false);
                studentSemesterComboBox.setItems(newValue.getSemesters());
                studentSemesterComboBox.setConverter(new StringConverter<>() {
                    @Override
                    public String toString(Semester semester) {
                        return (semester == null) ? "" : semester.toString();
                    }
                    @Override
                    public Semester fromString(String string) {
                        return null;
                    }
                });
            } else {
                studentSemesterComboBox.getSelectionModel().clearSelection();
                studentSemesterComboBox.setDisable(true);
            }
        });
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
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Clear form",
                        "This action will clear the form, do you want to continue?"
                );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            if (studentTableView.getSelectionModel().getSelectedItem() != null) {
                Student current = studentTableView.getSelectionModel().getSelectedItem();
                unbindStudentForm(current);
            }
            clearStudentForm();
            disableStudentForm(false);
            disableButtons(true, studentNewButton, studentEditButton, studentCancelButton);
            disableButtons(false, studentAcceptButton);
            studentAcceptButton.setText("Create");
        }
    }

    @FXML private Image onStudentUploadImageClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg")
        );
        File image = fileChooser.showOpenDialog(studentUploadImageButton.getScene().getWindow());
        if (image != null) {
            try {
                String imagePath = image.toURI().toString();
                Image pfp = new Image(imagePath);
                studentImageView.setImage(pfp);
                return pfp;
            } catch (IllegalArgumentException e) {
                AlertHandler.showAlert(
                  Alert.AlertType.ERROR,
                  "Error",
                  "Error loading image",
                  "Could not load image, please try again.\n" + e.getMessage()
                );
            }
        }
        return null;
    }
    @FXML private void onStudentManageDegreeClick() {

    }
    @FXML private void onStudentManageGroupClick() {

    }

    @FXML private void studentEditButtonHandler() {
        bindStudentForm(studentTableView.getSelectionModel().getSelectedItem());
        disableStudentForm(false);
        disableComboBoxes(false,
                studentSemesterComboBox,
                studentGroupComboBox
        );
        disableButtons(false, studentAcceptButton);
        disableButtons(true, studentEditButton);
    }
    @FXML private void studentAcceptButtonHandler() {
        switch (studentAcceptButton.getText()) {
            case "Create":
                try {
                    validateStudentForm();
                    Student student = new Student(
                            studentNameField.getText(),
                            studentLastNameField.getText(),
                            studentEmailField.getText(),
                            "789",
                            studentPhoneField.getText(),
                            new Address(
                                    studentStreetField.getText(),
                                    studentPCField.getText(),
                                    studentColonyField.getText(),
                                    studentCityField.getText(),
                                    studentStateField.getText(),
                                    studentCountryField.getText()
                            ),
                            studentDatePicker.getValue(),
                            studentGenderComboBox.getValue()
                    );
                    studentGroupComboBox.getSelectionModel().getSelectedItem().addStudent(student);
                    student.setProfilePicture(studentImageView.getImage());
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Create student",
                            "Successful operation",
                            "The student has been created!"
                    );
                } catch (NotValidFormatException e) {
                    AlertHandler.showAlert(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Not a valid format",
                            e.getMessage()
                    );
                }
                break;
            case "Update":
                try {
                    validateStudentForm();
                    Student student = studentTableView.getSelectionModel().getSelectedItem();
                    studentGroupComboBox.getSelectionModel().getSelectedItem().addStudent(student);
                    student.setGender(studentGenderComboBox.getValue());

                    student.setProfilePicture(studentImageView.getImage());
                    student.setBirthDate(studentDatePicker.getValue());
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Update student",
                            "Successful operation",
                            "The student has been updated!"
                    );
                    bindStudentTableColumns();
                } catch (NotValidFormatException e) {
                    AlertHandler.showAlert(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Not a valid format",
                            e.getMessage()
                    );
                }
                break;
            default:
                break;
        }
    }
    @FXML private void studentCancelButtonHandler() {
        switch (studentCancelButton.getText()) {
            case "Cancel":
                disableStudentForm(true);
                break;
            case "Unselect":
                if (studentTableView.getSelectionModel().getSelectedItem() != null) {
                    Student current = studentTableView.getSelectionModel().getSelectedItem();
                    unbindStudentForm(current);
                }
                clearStudentForm();
                disableStudentForm(false);
                disableButtons(true, studentNewButton, studentEditButton, studentCancelButton);
                disableButtons(false, studentAcceptButton);
                studentAcceptButton.setText("Create");
                break;
            default:
                break;
        }
    }

    @FXML private void onStudentFilterClick() {

    }

    @FXML private void studentSelectionHandler() {
        fillStudentForm(studentTableView.getSelectionModel().getSelectedIndex());
        disableStudentForm(true);
        disableButtons(false, studentNewButton, studentEditButton, studentCancelButton);
        studentCancelButton.setText("Unselect");
        studentAcceptButton.setText("Update");
        disableButtons(true, studentAcceptButton);
    }

    // TeacherTab
    @FXML private void teacherNewButtonHandler() {
        Optional<ButtonType> response =
                AlertHandler.showAlert(
                        Alert.AlertType.CONFIRMATION,
                        "Please confirm",
                        "Clear form",
                        "This action will clear the form, do you want to continue?"
                );
        if (response.isPresent() && response.get() == ButtonType.OK) {
            clearTeacherForm();
            disableTeacherForm(false);
            disableButtons(true, teacherNewButton, teacherEditButton, teacherCancelButton);
            disableButtons(false, teacherAcceptButton);
        }
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
        switch (teacherAcceptButton.getText()) {
            case "Create":
                Teacher teacher = new Teacher(
                        teacherNameField.getText(),
                        teacherLastNameField.getText(),
                        teacherEmailField.getText(),
                        "456",
                        teacherPhoneField.getText(),
                        new Address(
                                teacherStreetField.getText(),
                                teacherPCField.getText(),
                                teacherColonyField.getText(),
                                teacherCityField.getText(),
                                teacherStateField.getText(),
                                teacherCountryField.getText()
                        ),
                        teacherDatePicker.getValue(),
                        teacherGenderComboBox.getValue()
                );
                teacherDegreeComboBox.getSelectionModel().getSelectedItem().addTeacher(teacher);
                break;
            case "Update":
                break;
            default:
                break;
        }
    }

    @FXML private void teacherCancelButtonHandler() {
        switch (teacherCancelButton.getText()) {
            case "Cancel":
                teacherNewButton.fire();
                break;
            case "Unselect":
                clearTeacherForm();
                disableTeacherForm(false);
                disableButtons(true, teacherEditButton, teacherCancelButton);
                disableButtons(false, teacherAcceptButton);
                break;
            default:
                break;
        }
    }

    @FXML private void onTeacherFilterClick() {

    }

    @FXML private void teacherSelectionHandler() {
        int index = teacherTableView.getSelectionModel().getSelectedIndex();
        fillTeacherForm(index);
        disableTeacherForm(true);
        disableButtons(false, teacherNewButton, teacherEditButton, teacherCancelButton);
        teacherCancelButton.setText("Unselect");
        teacherAcceptButton.setText("Update");
        disableButtons(true, teacherAcceptButton);
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
                studentSemesterTableColumn,
                studentGroupTableColumn,
                studentGenderTableColumn,
                studentBirthDateTableColumn,
                studentAgeTableColumn
        };

        String[] properties = {
                "ID",
                "name",
                "lastName",
                "email",
                "phone",
                "degree",
                "semester",
                "group",
                "gender",
                "birthDate",
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
                teacherBirthDateTableColumn,
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
                "birthDate",
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
                studentDegreeComboBox
        );
        if (toggle) {
            disableComboBoxes(true,
                    studentSemesterComboBox,
                    studentGroupComboBox
            );
        }
        disableButtons(toggle,
                studentManageDegreeButton,
                studentManageGroupButton,
                studentUploadImageButton
        );
        studentDatePicker.setDisable(toggle);
        studentImageView.setDisable(toggle);
    }

    private void clearStudentForm() {
        clearTextFields(
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
        studentDegreeComboBox.getSelectionModel().clearSelection();
        studentGenderComboBox.getSelectionModel().clearSelection();
        studentDatePicker.setValue(null);
    }

    private void validateStudentForm() throws NotValidFormatException {
        if (studentNameField.textProperty() == null
                || studentLastNameField.getText() == null
                || studentPhoneField.getText() == null
                || studentEmailField.getText() == null
                || studentStreetField.getText() == null
                || studentPCField.getText() == null
                || studentColonyField == null
                || studentCityField == null
                || studentStateField == null
                || studentCountryField == null
                || studentDegreeComboBox.getSelectionModel().getSelectedItem() == null
                || studentGenderComboBox.getSelectionModel().getSelectedItem() == null
                || studentGroupComboBox.getSelectionModel().getSelectedItem() == null
                || studentImageView.getImage() == null
                || studentDatePicker.getValue() == null
        ) throw new NotValidFormatException("Make sure every field is filled.");
        if (!Validation.ofName(studentNameField.getText())) throw new NotValidFormatException("Not a valid name.");
        if (!Validation.ofName(studentLastNameField.getText())) throw new NotValidFormatException("Not a valid Last name.");
        if (!Validation.ofPhone(studentPhoneField.getText())) throw new NotValidFormatException("Not a valid phone number.");
        if (!Validation.ofEmail(studentEmailField.getText())) throw new NotValidFormatException("Not a valid email address.");
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

    private void clearTeacherForm() {
        clearTextFields(
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
                teacherSubjectField
        );
        clearComboBoxes(
                teacherDegreeComboBox,
                teacherAssignSubjectComboBox,
                teacherUnassignSubjectComboBox,
                teacherSubjectSemesterComboBox,
                teacherAssignSubjectSemesterComboBox,
                teacherUnassignSubjectSemesterComboBox
        );
    }

    private void validateTeacherForm() throws NotValidFormatException {
        if (teacherNameField.textProperty() == null
                || teacherLastNameField.getText() == null
                || teacherPhoneField.getText() == null
                || teacherEmailField.getText() == null
                || teacherStreetField.getText() == null
                || teacherPCField.getText() == null
                || teacherColonyField == null
                || teacherCityField == null
                || teacherStateField == null
                || teacherCountryField == null
        ) throw new NotValidFormatException("Make sure every field is filled.");
        if (!Validation.ofName(teacherNameField.getText())) throw new NotValidFormatException("Not a valid name.");
        if (!Validation.ofName(teacherLastNameField.getText())) throw new NotValidFormatException("Not a valid Last name.");
        if (!Validation.ofPhone(teacherPhoneField.getText())) throw new NotValidFormatException("Not a valid phone number.");
        if (!Validation.ofEmail(teacherEmailField.getText())) throw new NotValidFormatException("Not a valid email address.");
    }

    private void disableTextFields(boolean toggle, TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setDisable(toggle);
        }
    }

    private void clearTextFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.clear();
        }
    }

    private void disableComboBoxes(boolean toggle, ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setDisable(toggle);
        }
    }

    private void clearComboBoxes(ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.getSelectionModel().clearSelection();
        }
    }

    private void disableButtons(boolean toggle, Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(toggle);
        }
    }

    private void notEditableComboBoxes() {
        ComboBox<?>[] comboBoxes = {
                studentGenderComboBox,
                studentDegreeComboBox,
                studentSemesterComboBox,
                studentGroupComboBox,

                teacherGenderComboBox,
                teacherDegreeComboBox,

                teacherAssignSubjectComboBox,
                teacherUnassignSubjectComboBox,

                teacherSubjectSemesterComboBox,
                teacherAssignSubjectSemesterComboBox,
                teacherUnassignSubjectSemesterComboBox
        };
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setEditable(false);
        }
    }

//    private void bindComboBoxes() {
//        studentDegreeComboBox.setItems(data.getDegrees());
//        bindComboBoxes(studentDegreeComboBox, studentSemesterComboBox);
//
//        studentDegreeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            studentSemesterComboBox.setDisable(newValue == null);
//            studentSemesterComboBox.getSelectionModel().clearSelection();
//            studentGroupComboBox.setDisable(true);
//        });
//
//        studentSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                studentGroupComboBox.setDisable(false);
//                for (Shift shift : newValue.getGroups().keySet()) {
//                    studentGroupComboBox.getItems().setAll(newValue.getGroups(shift));
//                    studentGroupComboBox.setConverter(new StringConverter<>() {
//                        @Override
//                        public String toString(Group group) {
//                            return (group == null) ? "" : group.toString();
//                        }
//
//                        @Override
//                        public Group fromString(String string) {
//                            return null;
//                        }
//                    });
//                }
//            } else {
//                studentGroupComboBox.getItems().clear();
//                studentGroupComboBox.setDisable(true);
//            }
//        });
//        teacherDegreeComboBox.setItems(data.getDegrees());
//        bindComboBoxes(teacherDegreeComboBox, teacherSubjectSemesterComboBox);
//    }
//
//    private void bindComboBoxes(ComboBox<Degree> degreeComboBox, ComboBox<Semester> semesterComboBox) {
//        degreeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                semesterComboBox.setItems(newValue.getSemesters());
//                semesterComboBox.setConverter(new StringConverter<>() {
//                    @Override
//                    public String toString(Semester semester) {
//                        return (semester == null) ? "" : semester.toString();
//                    }
//
//                    @Override
//                    public Semester fromString(String string) {
//                        return null;
//                    }
//                });
//            } else {
//                semesterComboBox.getItems().clear();
//                semesterComboBox.setDisable(Boolean.TRUE);
//            }
//        });
//    }

    private void bindComboBoxes() {
        studentDegreeComboBox.setItems(data.getDegrees());
        studentGenderComboBox.getItems().setAll(Gender.values());
        teacherDegreeComboBox.setItems(data.getDegrees());
        teacherGenderComboBox.getItems().setAll(Gender.values());
    }

    private void fillStudentForm(int index) {
        fillForm(
                index,
                Map.ofEntries(
                        entry(studentNameField, studentNameTableColumn),
                        entry(studentLastNameField, studentLastNameTableColumn),
                        entry(studentPhoneField, studentPhoneTableColumn),
                        entry(studentEmailField, studentEmailTableColumn),
                        entry(studentStreetField, studentStreetTableColumn),
                        entry(studentPCField, studentPCTableColumn),
                        entry(studentColonyField, studentColonyTableColumn),
                        entry(studentCityField, studentCityTableColumn),
                        entry(studentStateField, studentStateTableColumn),
                        entry(studentCountryField, studentCountryTableColumn)
                ),
                Map.ofEntries(
                        entry(studentGenderComboBox, studentGenderTableColumn),
                        entry(studentDegreeComboBox, studentDegreeTableColumn),
                        entry(studentSemesterComboBox, studentSemesterTableColumn),
                        entry(studentGroupComboBox, studentGroupTableColumn)
                )
        );
        studentDatePicker.setValue(studentBirthDateTableColumn.getCellObservableValue(index).getValue());
    }

    private void bindStudentForm(Student current) {
        studentNameField.textProperty().bindBidirectional(current.nameProperty());
        studentLastNameField.textProperty().bindBidirectional(current.lastNameProperty());
        studentPhoneField.textProperty().bindBidirectional(current.phoneProperty());
        studentEmailField.textProperty().bindBidirectional(current.emailProperty());
        studentStreetField.textProperty().bindBidirectional(current.getAddress().streetProperty());
        studentPCField.textProperty().bindBidirectional(current.getAddress().postalCodeProperty());
        studentColonyField.textProperty().bindBidirectional(current.getAddress().colonyProperty());
        studentCityField.textProperty().bindBidirectional(current.getAddress().cityProperty());
        studentStateField.textProperty().bindBidirectional(current.getAddress().stateProperty());
        studentCountryField.textProperty().bindBidirectional(current.getAddress().countryProperty());

        studentGenderComboBox.setValue(current.getGender());
        studentDegreeComboBox.setValue(current.getDegree());

        studentSemesterComboBox.setItems(current.getDegree().getSemesters());
        studentSemesterComboBox.setValue(current.getSemester());

        studentGroupComboBox.setItems(current.getSemester().getAllGroups());
        studentGroupComboBox.setValue(current.getGroup());

        studentDatePicker.setValue(current.getBirthDate());
        studentImageView.setImage(current.getProfilePicture());
    }

    private void unbindStudentForm(Student current) {
        studentNameField.textProperty().unbindBidirectional(current.nameProperty());
        studentLastNameField.textProperty().unbindBidirectional(current.lastNameProperty());
        studentPhoneField.textProperty().unbindBidirectional(current.phoneProperty());
        studentEmailField.textProperty().unbindBidirectional(current.emailProperty());
        studentStreetField.textProperty().unbindBidirectional(current.getAddress().streetProperty());
        studentPCField.textProperty().unbindBidirectional(current.getAddress().postalCodeProperty());
        studentColonyField.textProperty().unbindBidirectional(current.getAddress().colonyProperty());
        studentCityField.textProperty().unbindBidirectional(current.getAddress().cityProperty());
        studentStateField.textProperty().unbindBidirectional(current.getAddress().stateProperty());
        studentCountryField.textProperty().unbindBidirectional(current.getAddress().countryProperty());

        clearComboBoxes(
                studentGenderComboBox,
                studentSemesterComboBox,
                studentGroupComboBox
        );

        studentDatePicker.setValue(null);
        studentImageView.setImage(null);
    }

    private void fillTeacherForm(int index) {
        fillForm(
                index,
                Map.ofEntries(
                        entry(teacherNameField, teacherNameTableColumn),
                        entry(teacherLastNameField, teacherLastNameTableColumn),
                        entry(teacherLicenseField, teacherLicenseTableColumn),
                        entry(teacherSpecializationField, teacherSpecializationTableColumn),
                        entry(teacherPhoneField, teacherPhoneTableColumn),
                        entry(teacherEmailField, teacherEmailTableColumn),
                        entry(teacherStreetField, teacherStreetTableColumn),
                        entry(teacherPCField, teacherPCTableColumn),
                        entry(teacherColonyField, teacherColonyTableColumn),
                        entry(teacherCityField, teacherCityTableColumn),
                        entry(teacherStateField, teacherStateTableColumn),
                        entry(teacherCountryField, teacherCountryTableColumn)
                ),
                Map.ofEntries(
                        entry(teacherGenderComboBox, teacherGenderTableColumn),
                        entry(teacherDegreeComboBox, teacherDegreeTableColumn)
                )
        );
        studentDatePicker.setValue(studentBirthDateTableColumn.getCellObservableValue(index).getValue());
    }

    @SuppressWarnings("unchecked")
    private <T> void fillForm(int index,
                              Map<TextField, TableColumn<T, String>> textFields,
                              Map<ComboBox<?>, TableColumn<T, ?>> comboBoxes) {

        textFields.forEach((field, column) ->
                field.setText(column.getCellObservableValue(index).getValue())
        );

        comboBoxes.forEach((combo, column) -> {
            Object value = column.getCellObservableValue(index).getValue();
            ((ComboBox<Object>) combo).setValue(value);
        });
    }
}

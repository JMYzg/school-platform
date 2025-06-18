package com.tap.schoolplatform.controllers.admin;

import com.tap.schoolplatform.controllers.ViewController;
import com.tap.schoolplatform.controllers.alerts.AlertHandler;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.services.auth.LoginService;
import com.tap.schoolplatform.utils.Validation;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    @FXML private Label studentSearchLabel;

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
    @FXML private ComboBox<Group>
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
    @FXML private Label teacherSearchLabel;

    // Methods

    @FXML private void initialize() {
        adminNameLabel.setText("Welcome " + LoginService.getCurrentUser().toString() + "!");
        initializeTabs();
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
        if (studentNewButton.getText().equals("New")) {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Clear form",
                            "This action will clear the form, do you want to continue?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                studentTableView.getSelectionModel().clearSelection();
                clearStudentForm();
                disableStudentForm(false);
                studentTableView.setDisable(false);
                disableButtons(true, studentNewButton, studentEditButton, studentCancelButton);
                disableButtons(false, studentAcceptButton, studentFilterButton);
                disableTextFields(false, studentSearchField);
                studentSearchLabel.setText("Please enter any word");
                studentAcceptButton.setText("Create");
            }
        } else {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Delete user",
                            "This action will delete the user, do you want to continue?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                Student student = studentTableView.getSelectionModel().getSelectedItem();
                if (student != null) {
                    clearStudentForm();
                    Group group = student.getGroup();
                    group.removeStudent(student);
                    studentTableView.refresh();
                    studentTableView.getSelectionModel().clearSelection();
                    studentTableView.setDisable(false);
                    disableButtons(false, studentFilterButton, studentAcceptButton);
                    disableButtons(true, studentEditButton, studentCancelButton, studentNewButton);
                    disableTextFields(false, studentSearchField);
                    disableStudentForm(false);
                    studentSearchLabel.setText("Please enter any word");
                    studentAcceptButton.setText("Create");
                    studentNewButton.setText("New");
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Delete student",
                            "Successful operation",
                            "The student has been deleted!"
                    );
                }
            }
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

    @FXML private void onStudentManageDegreeClick(ActionEvent event) throws IOException {
        loadNewView(event, "/views/admin-views/degree-view.fxml", "Degree management");
    }

    @FXML private void onStudentManageGroupClick(ActionEvent event) throws IOException {
        loadNewView(event, "/views/admin-views/group-view.fxml", "Group management");
    }

    @FXML private void studentEditButtonHandler() {
        fillStudentForm(studentTableView.getSelectionModel().getSelectedItem());
        disableStudentForm(false);
        disableComboBoxes(false,
                studentSemesterComboBox,
                studentGroupComboBox
        );
        disableButtons(false, studentNewButton, studentAcceptButton);
        disableButtons(true, studentEditButton, studentFilterButton);
        disableTextFields(true, studentSearchField);
        studentTableView.setDisable(true);
        studentSearchLabel.setText("Please update the user to continue");
        studentCancelButton.setText("Cancel");
    }

    // Move this

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
                    clearStudentForm();
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

                    updateStudent(student);
                    clearStudentForm();
                    disableStudentForm(true);
                    studentTableView.refresh();

                    disableButtons(true, studentNewButton, studentAcceptButton);
                    disableButtons(false, studentEditButton);

                    studentTableView.setDisable(false);

                    disableTextFields(false, studentSearchField);
                    disableButtons(false, studentFilterButton);

                    studentSearchLabel.setText("Please enter any word");

                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Update student",
                            "Successful operation",
                            "The student has been updated!"
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
            default:
                break;
        }
    }
    // Move this

    private void updateStudent(Student student) {
        updateUser(student,
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
                studentGenderComboBox,
                studentDatePicker
        );
        if (!student.getGroup().equals(studentGroupComboBox.getValue())) student.setGroup(studentGroupComboBox.getValue());
        if (studentImageView.getImage() != null) {
            if (student.getProfilePicture() == null || !student.getProfilePicture().equals(studentImageView.getImage())) student.setProfilePicture(studentImageView.getImage());
        }
    }
    @FXML private void studentCancelButtonHandler() {
        switch (studentCancelButton.getText()) {
            case "Cancel":
                studentSelectionHandler();
                break;
            case "Unselect":
                clearStudentForm();
                disableStudentForm(false);
                disableButtons(true, studentNewButton,
                        studentEditButton,
                        studentCancelButton
                );
                disableButtons(false, studentAcceptButton, studentFilterButton);
                disableTextFields(false, studentSearchField);
                studentNewButton.setText("New");
                studentSearchLabel.setText("Please enter any word");
                studentAcceptButton.setText("Create");
                studentCancelButton.setText("Cancel");
                break;
            default:
                break;
        }
        studentTableView.setDisable(false);
    }

    @FXML private void onStudentFilterClick() {
        studentTableView.setItems(findStudent(studentSearchField.getText()));
    }

    @FXML private void studentSelectionHandler() {
//        if (!studentAcceptButton.isDisabled() && studentAcceptButton.getText().equals("Update")) {
//            AlertHandler.showAlert(
//                    Alert.AlertType.ERROR,
//                    "Error",
//                    "You must unselect the current user",
//                    "Please unselect or update the current user to continue"
//            );
//        } else {
//
//        }
        fillStudentForm(studentTableView.getSelectionModel().getSelectedItem());
        disableStudentForm(true);
        disableButtons(false,
                studentNewButton,
                studentEditButton,
                studentCancelButton,
                studentFilterButton
        );
        disableButtons(true, studentNewButton, studentAcceptButton);
        disableTextFields(false, studentSearchField);
        studentNewButton.setText("Delete");
        studentCancelButton.setText("Unselect");
        studentAcceptButton.setText("Update");
        studentSearchLabel.setText("Please enter any word");
    }

    // TeacherTab
    @FXML private void teacherNewButtonHandler() {
        if (teacherNewButton.getText().equals("New")) {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Clear form",
                            "This action will clear the form, do you want to continue?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                teacherTableView.getSelectionModel().clearSelection();
                clearTeacherForm();
                disableTeacherForm(false);
                teacherTableView.setDisable(false);
                disableButtons(true, teacherNewButton, teacherEditButton, teacherCancelButton);
                disableButtons(false, teacherAcceptButton, teacherFilterButton);
                disableTextFields(false, teacherSearchField);
                teacherSearchLabel.setText("Please enter any word");
                teacherAcceptButton.setText("Create");
            }
        } else {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Delete user",
                            "This action will delete the user, do you want to continue?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
                if (teacher != null) {
                    clearTeacherForm();
                    Degree degree = teacher.getDegree();
                    degree.removeTeacher(teacher);
                    teacherTableView.refresh();
                    teacherTableView.getSelectionModel().clearSelection();
                    teacherTableView.setDisable(false);
                    disableButtons(false, teacherFilterButton, teacherAcceptButton);
                    disableButtons(true, teacherEditButton, teacherCancelButton, teacherNewButton);
                    disableTextFields(false, teacherSearchField);
                    disableTeacherForm(false);
                    teacherSearchLabel.setText("Please enter any word");
                    teacherAcceptButton.setText("Create");
                    teacherNewButton.setText("New");
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Delete teacher",
                            "Successful operation",
                            "The teacher has been deleted!"
                    );
                }
            }
        }
    }

    @FXML private void onTeacherManageDegreeClick(ActionEvent event) throws IOException {
        loadNewView(event, "/views/admin-views/degree-view.fxml", "Degree management");
    }

    @FXML private void onTeacherCreateSubjectClick() {
        if (teacherSubjectField.getText().isEmpty()) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Please enter a subject",
                    "Please enter a subject to continue"
            );
        } else {
            new Group(teacherSubjectSemesterComboBox.getSelectionModel().getSelectedItem(), teacherSubjectField.getText(), 5);
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Create subject",
                    "Successful operation",
                    "The subject has been created!"
            );
            teacherSubjectSemesterComboBox.getSelectionModel().clearSelection();
            teacherSubjectField.clear();
            teacherAssignSubjectSemesterComboBox.getSelectionModel().clearSelection();
            teacherAssignSubjectComboBox.getSelectionModel().clearSelection();
            teacherAssignSubjectComboBox.setDisable(true);
            teacherAssignSubjectButton.setDisable(true);
            loadTeacherAssignSubjectSemesterComboBox();
            loadTeacherUnassignSubjectSemesterComboBox();
//            teacherUnassignSubjectSemesterComboBox.getSelectionModel().clearSelection();
//            teacherUnassignSubjectComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML private void onTeacherAssignSubjectClick() {
        if (teacherAssignSubjectComboBox.getValue() == null) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Please select a subject",
                    "Please select a subject to continue"
            );
        } else {
            Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
            teacher.addSubject(teacherAssignSubjectComboBox.getValue());
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Assign subject",
                    "Successful operation",
                    "The subject has been assigned to the teacher!"
            );
            teacherAssignSubjectSemesterComboBox.getSelectionModel().clearSelection();
            teacherAssignSubjectComboBox.getSelectionModel().clearSelection();
            loadTeacherAssignSubjectSemesterComboBox();
            loadTeacherUnassignSubjectSemesterComboBox();
//            teacherCancelButton.fire();
//            teacherSelectionHandler();
//            teacherEditButton.fire();
        }
    }

    @FXML private void onTeacherUnassignSubjectClick() {
        if (teacherUnassignSubjectComboBox.getValue() == null) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Please select a subject",
                    "Please select a subject to continue"
            );
        } else {
            Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
            teacher.removeSubject(teacherUnassignSubjectComboBox.getValue());
            AlertHandler.showAlert(
                    Alert.AlertType.INFORMATION,
                    "Unassign subject",
                    "Successful operation",
                    "The subject has been unassigned from the teacher!"
            );
            teacherUnassignSubjectSemesterComboBox.getSelectionModel().clearSelection();
            teacherUnassignSubjectComboBox.getSelectionModel().clearSelection();
            loadTeacherAssignSubjectSemesterComboBox();
            loadTeacherUnassignSubjectSemesterComboBox();
//            teacherCancelButton.fire();
//            teacherSelectionHandler();
//            teacherEditButton.fire();
        }
    }

    @FXML private void teacherEditButtonHandler() {
        fillTeacherForm(teacherTableView.getSelectionModel().getSelectedItem());
        disableTeacherForm(false);
        disableComboBoxes(false,
                teacherSubjectSemesterComboBox

//                teacherAssignSubjectSemesterComboBox,
//                teacherUnassignSubjectSemesterComboBox
        );
        disableButtons(false, teacherNewButton, teacherAcceptButton);
        disableButtons(true, teacherEditButton, teacherFilterButton);
        disableTextFields(true, teacherSearchField);
        teacherTableView.setDisable(true);
        teacherSearchLabel.setText("Please update the user to continue");
        teacherCancelButton.setText("Cancel");
        loadTeacherAssignSubjectSemesterComboBox();
        loadTeacherUnassignSubjectSemesterComboBox();
        boolean isEmpty = teacherAssignSubjectSemesterComboBox.getItems().isEmpty();
        disableComboBoxes(isEmpty, teacherAssignSubjectSemesterComboBox);
        isEmpty = teacherUnassignSubjectSemesterComboBox.getItems().isEmpty();
        disableComboBoxes(isEmpty, teacherUnassignSubjectSemesterComboBox);
    }

    @FXML private void teacherAcceptButtonHandler() {
        switch (teacherAcceptButton.getText()) {
            case "Create":
                try {
                    validateTeacherForm();
                    Teacher teacher = getTeacher();
                    teacherDegreeComboBox.getSelectionModel().getSelectedItem().addTeacher(teacher);
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Create teacher",
                            "Successful operation",
                            "The teacher has been created!"
                    );
                    clearTeacherForm();
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
                    validateTeacherForm();
                    Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();

                    updateTeacher(teacher);
                    clearTeacherForm();
                    disableTeacherForm(true);
                    teacherTableView.refresh();

                    disableButtons(true, teacherNewButton, teacherAcceptButton);
                    disableButtons(false, teacherEditButton);

                    teacherTableView.setDisable(false);

                    disableTextFields(false, teacherSearchField);
                    disableButtons(false, teacherFilterButton);

                    teacherSearchLabel.setText("Please enter any word");

                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Update teacher",
                            "Successful operation",
                            "The teacher has been updated!"
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
            default:
                break;
        }
    }

    @FXML private void teacherCancelButtonHandler() {
        switch (teacherCancelButton.getText()) {
            case "Cancel":
                teacherSelectionHandler();
                break;
            case "Unselect":
                clearTeacherForm();
                disableTeacherForm(false);
                disableButtons(true,
                        teacherNewButton,
                        teacherEditButton,
                        teacherCancelButton
                );
                disableButtons(false, teacherAcceptButton, studentFilterButton);
                disableTextFields(false, teacherSearchField);
                teacherSearchLabel.setText("Please enter any word");
                teacherAcceptButton.setText("Create");
                teacherCancelButton.setText("Cancel");
                teacherNewButton.setText("New");
                teacherTableView.getSelectionModel().clearSelection();
                break;
            default:
                break;
        }
        teacherTableView.setDisable(false);
    }

    @FXML private void onTeacherFilterClick() {
        teacherTableView.setItems(findTeacher(teacherSearchField.getText()));
    }

    @FXML private void teacherSelectionHandler() {
        fillTeacherForm(teacherTableView.getSelectionModel().getSelectedItem());
        disableTeacherForm(true);
        clearComboBoxes(
                teacherSubjectSemesterComboBox,
                teacherAssignSubjectSemesterComboBox,
                teacherAssignSubjectComboBox,
                teacherUnassignSubjectSemesterComboBox,
                teacherSubjectSemesterComboBox
        );
        clearTextFields(teacherSubjectField);
        disableButtons(false,
                teacherEditButton,
                teacherCancelButton,
                teacherFilterButton
        );
        disableButtons(true, teacherNewButton, teacherAcceptButton);
        disableTextFields(false, teacherSearchField);
        teacherCancelButton.setText("Unselect");
        teacherAcceptButton.setText("Update");
        teacherSearchLabel.setText("Please enter any word");
        teacherNewButton.setText("Delete");
    }
    // Utils

    private void updateUser(
            User user,
            TextField nameField,
            TextField lastNameField,
            TextField phoneField,
            TextField emailField,
            TextField streetField,
            TextField PCField,
            TextField colonyField,
            TextField cityField,
            TextField stateField,
            TextField countryField,
            ComboBox<Gender> genderComboBox,
            DatePicker datePicker
    ) {
        if (!user.getName().equals(nameField.getText())) user.setName(nameField.getText());
        if (!user.getLastName().equals(lastNameField.getText())) user.setLastName(lastNameField.getText());
        if (!user.getPhone().equals(phoneField.getText())) user.setPhone(phoneField.getText());
        if (!user.getEmail().equals(emailField.getText())) user.setEmail(emailField.getText());
        if (!user.getAddress().getStreet().equals(streetField.getText()))
            user.getAddress().setStreet(streetField.getText());
        if (!user.getAddress().getPostalCode().equals(PCField.getText()))
            user.getAddress().setPostalCode(PCField.getText());
        if (!user.getAddress().getColony().equals(colonyField.getText()))
            user.getAddress().setColony(colonyField.getText());
        if (!user.getAddress().getCity().equals(cityField.getText())) user.getAddress().setCity(cityField.getText());
        if (!user.getAddress().getState().equals(stateField.getText()))
            user.getAddress().setState(stateField.getText());
        if (!user.getAddress().getCountry().equals(countryField.getText()))
            user.getAddress().setCountry(countryField.getText());
        if (!user.getGender().equals(genderComboBox.getValue())) user.setGender(genderComboBox.getValue());
        if (!user.getBirthDate().equals(datePicker.getValue())) user.setBirthDate(datePicker.getValue());
    }

    private void bindTableViews() {
        studentTableView.setItems(data.getUsers(Role.STUDENT));
        teacherTableView.setItems(data.getUsers(Role.TEACHER));
    }

    private void bindTableColumns() {
        bindStudentTableColumns();
        bindTeacherTableColumns();
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

    public static void injectCellValues(TableColumn<?, ?>[] columns, String[] properties) {
        for (int i = 0; i < columns.length; i++) {
            columns[i].setCellValueFactory(new PropertyValueFactory<>(properties[i]));
        }
    }

    private void initializeTabs() {
        bindTableViews();
        bindTableColumns();
        bindComboBoxes();
        makeComboBoxesNotEditable();

        disableComboBoxes(true,
                studentSemesterComboBox,
                studentGroupComboBox,

                teacherSubjectSemesterComboBox,
                teacherAssignSubjectSemesterComboBox,
                teacherAssignSubjectComboBox,
                teacherUnassignSubjectSemesterComboBox,
                teacherUnassignSubjectComboBox
        );

        setListeners();

        disableButtons(true,
                studentNewButton,

                studentEditButton,
                studentCancelButton,

                teacherNewButton,

                teacherEditButton,
                teacherCancelButton
        );

        disableTextFields(true, teacherSubjectField);

        disableButtons(true,
                teacherCreateSubjectButton,
                teacherAssignSubjectButton,
                teacherUnassignSubjectButton
        );

        studentDatePicker.setEditable(false);
        teacherDatePicker.setEditable(false);

        studentAcceptButton.setText("Create");
        teacherAcceptButton.setText("Create");
    }

    private void setListeners() {
        setStudentListeners();
        setTeacherListeners();
    }

    private void setTeacherListeners() {
        teacherDegreeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableComboBoxes(false,
                        teacherSubjectSemesterComboBox
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
                );
                ObservableList<Semester> semesters = newValue.getSemesters();
                teacherSubjectSemesterComboBox.setItems(semesters);

//                if (teacherTableView.getSelectionModel().getSelectedItem() == null || teacherAssignSubjectSemesterComboBox.getItems().isEmpty()) {
//
//                }

                if (teacherTableView.getSelectionModel().getSelectedItem() != null) {
                    Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
                    teacherAssignSubjectSemesterComboBox.setItems(semesters.stream()
                            .filter(semester -> !semester.getSubjects().isEmpty() &&
                                    semester.getSubjects().stream()
                                            .anyMatch(subject -> subject.getTeacher() == null))
                            .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    teacherUnassignSubjectSemesterComboBox.setItems(teacher.getSemesters());
                } else {
                    disableComboBoxes(true,
                            teacherAssignSubjectSemesterComboBox,
                            teacherUnassignSubjectSemesterComboBox
                    );
                }
            } else {
                disableTextFields(true, teacherSubjectField);
                disableComboBoxes(true,
                        teacherSubjectSemesterComboBox,
                        teacherAssignSubjectSemesterComboBox,
                        teacherAssignSubjectComboBox,
                        teacherUnassignSubjectSemesterComboBox,
                        teacherUnassignSubjectComboBox
                );
                disableButtons(true,
                        teacherCreateSubjectButton,
                        teacherAssignSubjectButton,
                        teacherUnassignSubjectButton
                );
            }
        });

        teacherSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableTextFields(false, teacherSubjectField);
                disableButtons(false, teacherCreateSubjectButton);
            } else {
                disableTextFields(true, teacherSubjectField);
                disableButtons(true, teacherCreateSubjectButton);
            }
        });

        teacherAssignSubjectSemesterComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            disableComboBoxes(observable.getValue().isEmpty(), teacherAssignSubjectSemesterComboBox);
        });

        teacherAssignSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableComboBoxes(false, teacherAssignSubjectComboBox);
                disableButtons(false, teacherAssignSubjectButton);

                teacherAssignSubjectComboBox.setItems(newValue.getSubjects().stream()
                        .filter(subject -> subject.getTeacher() == null)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
                );

            } else {
                disableComboBoxes(true, teacherAssignSubjectComboBox);
                disableButtons(true, teacherAssignSubjectButton);
            }
        });

/*        teacherTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
//                teacherUnassignSubjectSemesterComboBox.setItems(teacher.getSemesters());
*//*                disableComboBoxes(false,
                        teacherAssignSubjectSemesterComboBox,
                        teacherUnassignSubjectSemesterComboBox
                );*//*
            } else {
                disableComboBoxes(true,
                        teacherAssignSubjectSemesterComboBox,
                        teacherUnassignSubjectSemesterComboBox
                );
            }
        });*/

        teacherUnassignSubjectSemesterComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
            disableComboBoxes(observable.getValue().isEmpty(), teacherUnassignSubjectSemesterComboBox);
        });

        teacherUnassignSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableComboBoxes(false, teacherUnassignSubjectComboBox);
                disableButtons(false, teacherUnassignSubjectButton);
                if (teacherTableView.getSelectionModel().getSelectedItem() != null) {
                    Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
                    teacherUnassignSubjectComboBox.setItems(newValue.getSubjects().stream()
                            .filter(subject -> subject.getTeacher() == teacher)
                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                    );
                }
            } else {
                disableComboBoxes(true, teacherUnassignSubjectComboBox);
                disableButtons(true, teacherUnassignSubjectButton);
            }
        });
    }

    private void setStudentListeners() {
        studentDegreeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (studentDegreeComboBox.isDisabled()) {
                studentSemesterComboBox.setDisable(true);
            }
            if (newValue != null) {
                studentSemesterComboBox.getSelectionModel().clearSelection();
                studentSemesterComboBox.setItems(FXCollections.observableArrayList());
                studentSemesterComboBox.setDisable(false);
                studentSemesterComboBox.setItems(newValue.getSemesters());
            } else {
                studentSemesterComboBox.getSelectionModel().clearSelection();
                studentSemesterComboBox.setDisable(true);
            }
        });

        studentSemesterComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (studentSemesterComboBox.isDisabled()) {
                studentGroupComboBox.setDisable(true);
            }
            if (newValue != null) {
                studentGroupComboBox.getSelectionModel().clearSelection();
                studentGroupComboBox.setItems(FXCollections.observableArrayList());
                studentGroupComboBox.setDisable(false);
                studentGroupComboBox.setItems(newValue.getAllGroups());
                if (studentGroupComboBox.getItems().isEmpty()) studentGroupComboBox.setDisable(true);
            } else {
                studentGroupComboBox.getSelectionModel().clearSelection();
                studentGroupComboBox.setDisable(true);
            }
        });
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

        nullComboBoxes(
                studentDegreeComboBox,
                studentSemesterComboBox,
                studentGroupComboBox,

                studentGenderComboBox
        );

        studentDatePicker.setValue(null);
        studentImageView.setImage(null);
    }

    private void validateStudentForm() throws NotValidFormatException {
        checkRequirements(
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
        requireNotNull(studentGenderComboBox.getSelectionModel().getSelectedItem(), "Gender is required.");
        requireNotNull(studentDatePicker.getValue(), "Date of birth is required.");
        requireNotNull(studentDegreeComboBox.getSelectionModel().getSelectedItem(), "Degree is required.");
        requireNotNull(studentSemesterComboBox.getSelectionModel().getSelectedItem(), "Semester is required.");
        requireNotNull(studentGroupComboBox.getSelectionModel().getSelectedItem(), "Group is required.");
        requireNotNull(studentImageView.getImage(), "Image is required.");

        validateFieldFormat(Validation.ofName(studentNameField.getText()), "Not a valid name.");
        validateFieldFormat(Validation.ofName(studentLastNameField.getText()), "Not a valid last name.");
        validateFieldFormat(Validation.ofPhone(studentPhoneField.getText()), "Not a valid phone number.");
        validateFieldFormat(Validation.ofEmail(studentEmailField.getText()), "Not a valid email address.");
    }

    private void checkRequirements(
            TextField studentNameField,
            TextField studentLastNameField,
            TextField studentPhoneField,
            TextField studentEmailField,
            TextField studentStreetField,
            TextField studentPCField,
            TextField studentColonyField,
            TextField studentCityField,
            TextField studentStateField,
            TextField studentCountryField) throws NotValidFormatException {
        requireText(studentNameField.getText(), "Name is required.");
        requireText(studentLastNameField.getText(), "Last name is required.");
        requireText(studentPhoneField.getText(), "Phone number is required.");
        requireText(studentEmailField.getText(), "Email is required.");
        requireText(studentStreetField.getText(), "Street is required.");
        requireText(studentPCField.getText(), "Postal code is required.");
        requireText(studentColonyField.getText(), "Colony is required.");
        requireText(studentCityField.getText(), "City is required.");
        requireText(studentStateField.getText(), "State is required.");
        requireText(studentCountryField.getText(), "Country is required.");
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
                teacherDegreeComboBox
        );
        if(toggle) {
            disableComboBoxes(true,
                    teacherSubjectSemesterComboBox,

                    teacherAssignSubjectSemesterComboBox,
                    teacherAssignSubjectComboBox,
                    teacherUnassignSubjectSemesterComboBox,
                    teacherUnassignSubjectComboBox
            );

            disableTextFields(true, teacherSubjectField);
            disableButtons(true,
                    teacherCreateSubjectButton,
                    teacherAssignSubjectButton,
                    teacherUnassignSubjectButton
                    );
        }
        disableButtons(toggle, teacherManageDegreeButton);
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

        nullComboBoxes(
                teacherDegreeComboBox,
                teacherSubjectSemesterComboBox,

                teacherAssignSubjectSemesterComboBox,
                teacherAssignSubjectComboBox,
                teacherUnassignSubjectSemesterComboBox,
                teacherUnassignSubjectComboBox,

                teacherGenderComboBox
                );

        teacherDatePicker.setValue(null);
    }

    private void updateTeacher(Teacher teacher) {
        updateUser(
                teacher,
                teacherNameField,
                teacherLastNameField,
                teacherPhoneField,
                teacherEmailField,
                teacherStreetField,
                teacherPCField,
                teacherColonyField,
                teacherCityField,
                teacherStateField,
                teacherCountryField,
                teacherGenderComboBox,
                teacherDatePicker
        );
        if(!teacher.getLicense().equals(teacherLicenseField.getText())) teacher.setLicense(teacherLicenseField.getText());
        if(!teacher.getSpecialization().equals(teacherSpecializationField.getText())) teacher.setSpecialization(teacherSpecializationField.getText());
        if(!teacher.getDegree().equals(teacherDegreeComboBox.getValue())) teacher.setDegree(teacherDegreeComboBox.getValue());
    }

    private Teacher getTeacher() {
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
        teacher.setLicense(teacherLicenseField.getText());
        teacher.setSpecialization(teacherSpecializationField.getText());
        return teacher;
    }

    private void validateTeacherForm() throws NotValidFormatException {
        checkRequirements(
                teacherNameField,
                teacherLastNameField,
                teacherPhoneField,
                teacherEmailField,

                teacherStreetField,
                teacherPCField,
                teacherColonyField,
                teacherCityField,
                teacherStateField,
                teacherCountryField
        );

        requireText(teacherLicenseField.getText(), "License is required.");
        requireText(teacherSpecializationField.getText(), "Specialization is required.");

        requireNotNull(teacherGenderComboBox.getSelectionModel().getSelectedItem(), "Gender is required.");
        requireNotNull(teacherDatePicker.getValue(), "Birthdate is required.");
        requireNotNull(teacherDegreeComboBox.getSelectionModel().getSelectedItem(), "Degree is required.");

        validateFieldFormat(Validation.ofName(teacherNameField.getText()), "Not a valid name.");
        validateFieldFormat(Validation.ofName(teacherLastNameField.getText()), "Not a valid last name.");
        validateFieldFormat(Validation.ofName(teacherSpecializationField.getText()), "Not a valid specialization name.");
        validateFieldFormat(Validation.ofPhone(teacherPhoneField.getText()), "Not a valid phone number.");
        validateFieldFormat(Validation.ofEmail(teacherEmailField.getText()), "Not a valid email address.");
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

    private void nullComboBoxes(ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setValue(null);
        }
    }

    private void disableButtons(boolean toggle, Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(toggle);
        }
    }

    private void makeComboBoxesNotEditable() {
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

    private void bindComboBoxes() {
        bindStudentComboBoxes();
        bindTeacherComboBoxes();
    }

    private void bindStudentComboBoxes() {
        studentDegreeComboBox.setItems(data.getDegrees());
        studentGenderComboBox.getItems().setAll(Gender.values());
    }

    private void bindTeacherComboBoxes() {
        teacherDegreeComboBox.setItems(data.getDegrees());
        teacherGenderComboBox.getItems().setAll(Gender.values());
    }

    private void fillStudentForm(Student student) {
        FormFiller.create()
                .with(studentNameField, student.getName())
                .with(studentLastNameField, student.getLastName())
                .with(studentPhoneField, student.getPhone())
                .with(studentEmailField, student.getEmail())

                .with(studentStreetField, student.getAddress().getStreet())
                .with(studentPCField, student.getAddress().getPostalCode())
                .with(studentColonyField, student.getAddress().getColony())
                .with(studentCityField, student.getAddress().getCity())
                .with(studentStateField, student.getAddress().getState())
                .with(studentCountryField, student.getAddress().getCountry())

                .with(studentDegreeComboBox, student.getDegree())
                .with(studentSemesterComboBox, student.getSemester())
                .with(studentGroupComboBox, student.getGroup())

                .with(studentGenderComboBox, student.getGender())
                .with(studentDatePicker, student.getBirthDate())
                .fill();

        studentImageView.setImage(student.getProfilePicture());
    }

    private void fillTeacherForm(Teacher teacher) {
        FormFiller.create()
                .with(teacherNameField, teacher.getName())
                .with(teacherLastNameField, teacher.getLastName())
                .with(teacherLicenseField, teacher.getLicense())
                .with(teacherSpecializationField, teacher.getSpecialization())
                .with(teacherPhoneField, teacher.getPhone())
                .with(teacherEmailField, teacher.getEmail())

                .with(teacherStreetField, teacher.getAddress().getStreet())
                .with(teacherPCField, teacher.getAddress().getPostalCode())
                .with(teacherColonyField, teacher.getAddress().getColony())
                .with(teacherCityField, teacher.getAddress().getCity())
                .with(teacherStateField, teacher.getAddress().getState())
                .with(teacherCountryField, teacher.getAddress().getCountry())

                .with(teacherGenderComboBox, teacher.getGender())
                .with(teacherDegreeComboBox, teacher.getDegree())
                .with(teacherDatePicker, teacher.getBirthDate())
                .fill();
    }

    private void loadTeacherAssignSubjectSemesterComboBox() {
        ObservableList<Semester> semesters = teacherDegreeComboBox.getValue().getSemesters();
        teacherAssignSubjectSemesterComboBox.setItems(semesters.stream()
                .filter(semester -> !semester.getSubjects().isEmpty() &&
                        semester.getSubjects().stream()
                                .anyMatch(subject -> subject.getTeacher() == null))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    private void loadTeacherUnassignSubjectSemesterComboBox() {
        Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
        teacherUnassignSubjectSemesterComboBox.setItems(teacher.getSemesters());
    }

    private static class FormFiller {
        private final Map<TextField, String> textMap = new LinkedHashMap<>();
        private final Map<DatePicker, LocalDate> dateMap = new LinkedHashMap<>();
        private final List<Runnable> orderedOperations = new ArrayList<>();

        public static FormFiller create() {
            return new FormFiller();
        }

        public FormFiller with(TextField field, String value) {
            textMap.put(field, value);
            return this;
        }

        public <T> FormFiller with(ComboBox<T> combo, T value) {
            orderedOperations.add(() -> combo.setValue(value));
            return this;
        }

        public FormFiller with(DatePicker date, LocalDate value) {
            dateMap.put(date, value);
            return this;
        }

        public void fill() {
            textMap.forEach(TextField::setText);
            dateMap.forEach(DatePicker::setValue);
            orderedOperations.forEach(Runnable::run);
        }
    }

    private void requireText(String text, String errorMessage) throws NotValidFormatException {
        if (text == null || text.trim().isEmpty()) throw new NotValidFormatException(errorMessage);
    }

    private void requireNotNull(Object obj, String errorMessage) throws NotValidFormatException {
        if (obj == null) throw new NotValidFormatException(errorMessage);
    }

    private void validateFieldFormat(boolean condition, String errorMessage) throws NotValidFormatException {
        if (!condition) throw new NotValidFormatException(errorMessage);
    }

    private ObservableList<Student> findStudent(String query) {
        return findPersons(query, data.getUsers(Role.STUDENT), student -> Arrays.asList(
                student.getID(),
                student.getName(),
                student.getLastName(),
                student.getDegree().toString(),
                student.getSemester().toString(),
                student.getGroup().toString(),
                student.getEmail(),
                student.getPhone(),
                safeGet(() -> student.getAddress().getStreet()),
                safeGet(() -> student.getAddress().getPostalCode()),
                safeGet(() -> student.getAddress().getColony()),
                safeGet(() -> student.getAddress().getCity()),
                safeGet(() -> student.getAddress().getState()),
                safeGet(() -> student.getAddress().getCountry()),
                student.getGender().toString(),
                student.getBirthDate().toString(),
                Integer.toString(student.getAge())
                ));
    }

    public ObservableList<Teacher> findTeacher(String query) {
        return findPersons(query, data.getUsers(Role.TEACHER), teacher -> Arrays.asList(
                teacher.getLicense(),
                teacher.getName(),
                teacher.getLastName(),
                teacher.getSpecialization(),
                teacher.getDegree().toString(),
                teacher.getEmail(),
                teacher.getPhone(),
                safeGet(() -> teacher.getAddress().getStreet()),
                safeGet(() -> teacher.getAddress().getPostalCode()),
                safeGet(() -> teacher.getAddress().getColony()),
                safeGet(() -> teacher.getAddress().getCity()),
                safeGet(() -> teacher.getAddress().getState()),
                safeGet(() -> teacher.getAddress().getCountry()),
                teacher.getGender().toString(),
                teacher.getBirthDate().toString(),
                Integer.toString(teacher.getAge())
        ));
    }

    private <T> ObservableList<T> findPersons(String attribute, List<T> persons, Function<T, List<String>> fieldExtractor) {
        String toLowerCase = attribute.toLowerCase();
        return persons.stream()
                .filter(person -> extractFields(person, fieldExtractor).stream()
                        .anyMatch(field -> field.toLowerCase().contains(toLowerCase)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    private <T> List<String> extractFields(T person, Function<T, List<String>> fieldExtractor) {
        return fieldExtractor.apply(person).stream()
                .map(field -> field != null ? field : "")
                .collect(Collectors.toList());
    }

    private static String safeGet(Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
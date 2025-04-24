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
import com.tap.schoolplatform.models.users.shared.Address;
import com.tap.schoolplatform.services.auth.LoginService;
import com.tap.schoolplatform.utils.Validation;
import com.tap.schoolplatform.utils.exceptions.NotValidFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.*;
import java.util.stream.Collectors;

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
        initializeTabs();
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
                        teacherSubjectSemesterComboBox,
                        teacherAssignSubjectSemesterComboBox,
                        teacherUnassignSubjectSemesterComboBox
                );
                ObservableList<Semester> semesters = newValue.getSemesters();
                teacherSubjectSemesterComboBox.setItems(semesters);
//                setStringConverter(teacherSubjectSemesterComboBox);
                teacherAssignSubjectSemesterComboBox.setItems(semesters.stream()
                        .filter(semester -> !semester.getSubjects().isEmpty() &&
                                semester.getSubjects().stream()
                                        .anyMatch(subject -> subject.getTeacher() == null))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                setStringConverter(teacherAssignSubjectSemesterComboBox);
                if (teacherAssignSubjectSemesterComboBox.getItems().isEmpty()) {
                    disableComboBoxes(true, teacherAssignSubjectSemesterComboBox);
                }
                if (teacherTableView.getSelectionModel().getSelectedItem() != null) {
                    Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
                    teacherUnassignSubjectSemesterComboBox.setItems(teacher.getSemesters());
//                    setStringConverter(teacherUnassignSubjectSemesterComboBox);
                } else {
                    disableComboBoxes(true, teacherUnassignSubjectSemesterComboBox);
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

        teacherAssignSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableComboBoxes(false, teacherAssignSubjectComboBox);
                disableButtons(false, teacherAssignSubjectButton);

                teacherAssignSubjectComboBox.setItems(newValue.getSubjects().stream()
                        .filter(subject -> subject.getTeacher() == null)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
                );
//                setStringConverter(teacherAssignSubjectComboBox);

            } else {
                disableComboBoxes(true, teacherAssignSubjectComboBox);
                disableButtons(true, teacherAssignSubjectButton);
            }
        });

        teacherTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                disableComboBoxes(false, teacherUnassignSubjectSemesterComboBox);
                Teacher teacher = teacherTableView.getSelectionModel().getSelectedItem();
                teacherUnassignSubjectSemesterComboBox.setItems(teacher.getSemesters());
//                setStringConverter(teacherUnassignSubjectSemesterComboBox);
            } else {
                disableComboBoxes(true, teacherUnassignSubjectSemesterComboBox);
            }
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
//                    setStringConverter(teacherUnassignSubjectComboBox);
                }
            } else {
                disableComboBoxes(true, teacherUnassignSubjectComboBox);
                disableButtons(true, teacherUnassignSubjectButton);
            }
        });
    }

//    private void setTeacherListeners() {
//        teacherDegreeComboBox.disabledProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//            } else {
//                disableComboBoxes(false,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox
//                );
//            }
//        });
//
//        teacherDegreeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                clearComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                        );
//            } else {
//                disableComboBoxes(false,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//            }
//        });
//
//        teacherDegreeComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                emptyComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//            }
//        });
//
//        teacherSubjectSemesterComboBox.disabledProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                disableTextFields(true, teacherSubjectField);
//                disableButtons(true, teacherCreateSubjectButton);
//            } else {
//                disableTextFields(false, teacherSubjectField);
//                disableButtons(false, teacherCreateSubjectButton);
//            }
//        });
//
//        teacherSubjectSemesterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                clearTextFields(teacherSubjectField);
//                disableTextFields(true, teacherSubjectField);
//                disableButtons(true, teacherCreateSubjectButton);
//            } else {
//                disableTextFields(false, teacherSubjectField);
//                disableButtons(false, teacherCreateSubjectButton);
//            }
//        });
//
//        teacherSubjectSemesterComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) teacherSubjectField.clear();
//        });
//
//        teacherAssignSubjectSemesterComboBox.disabledProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                disableComboBoxes(true, teacherAssignSubjectComboBox);
//                disableButtons(true, teacherAssignSubjectButton);
//            } else {
//                disableComboBoxes(false, teacherAssignSubjectComboBox);
//                disableButtons( false, teacherAssignSubjectButton);
//            }
//        });
//
//        teacherAssignSubjectSemesterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                clearComboBoxes(teacherAssignSubjectComboBox);
//                disableComboBoxes(true, teacherAssignSubjectComboBox);
//                disableButtons(true, teacherAssignSubjectButton);
//            } else {
//                disableComboBoxes(false, teacherAssignSubjectComboBox);
//                disableButtons(false, teacherAssignSubjectButton);
//            }
//        });
//
//        teacherAssignSubjectSemesterComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) emptyComboBoxes(teacherAssignSubjectComboBox);
//        });
//
//        teacherUnassignSubjectComboBox.disabledProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                disableComboBoxes(true, teacherUnassignSubjectComboBox);
//                disableButtons(true, teacherUnassignSubjectButton);
//            } else {
//                disableComboBoxes(false, teacherUnassignSubjectComboBox);
//                disableButtons(false, teacherUnassignSubjectButton);
//            }
//        });
//
//        teacherUnassignSubjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                clearComboBoxes(teacherUnassignSubjectComboBox);
//                disableComboBoxes(true, teacherUnassignSubjectComboBox);
//                disableButtons(true, teacherUnassignSubjectButton);
//            } else {
//                disableComboBoxes(false, teacherUnassignSubjectComboBox);
//                disableButtons(false, teacherUnassignSubjectButton);
//            }
//        });
//
//        teacherUnassignSubjectComboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) emptyComboBoxes(teacherUnassignSubjectComboBox);
//        });
//
//        teacherDegreeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                disableComboBoxes(false,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//                ObservableList<Semester> semesters = newValue.getSemesters();
//                teacherSubjectSemesterComboBox.setItems(semesters);
//                setStringConverter(teacherSubjectSemesterComboBox);
//
//                teacherAssignSubjectSemesterComboBox.setItems(semesters.stream()
//                        .filter(semester -> !semester.getSubjects().isEmpty() &&
//                                semester.getSubjects().stream()
//                                        .anyMatch(subject -> subject.getTeacher() == null))
//                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                setStringConverter(teacherAssignSubjectSemesterComboBox);
//                if (teacherAssignSubjectSemesterComboBox.getItems().isEmpty()) {
//                    clearComboBoxes(teacherAssignSubjectSemesterComboBox);
//                    emptyComboBoxes(teacherAssignSubjectSemesterComboBox);
//                    disableComboBoxes(true, teacherAssignSubjectSemesterComboBox);
//                }
//            } else {
//                teacherSubjectSemesterComboBox.setItems(null);
//                clearComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
//            }
//        });
//
//        teacherAssignSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                List<Subject> subjects = newValue.getSubjects();
//                teacherAssignSubjectComboBox.setItems(
//                        subjects.stream()
//                                .filter(subject -> subject.getTeacher() == null)
//                                .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                );
//                setStringConverter(teacherAssignSubjectComboBox);
//            }
//        });
//
//        teacherTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                teacherUnassignSubjectSemesterComboBox.setItems(newValue.getSemesters());
//                setStringConverter(teacherUnassignSubjectSemesterComboBox);
//            } else {
//                clearComboBoxes(teacherUnassignSubjectSemesterComboBox);
//                emptyComboBoxes(teacherUnassignSubjectSemesterComboBox);
//                disableComboBoxes(true, teacherUnassignSubjectSemesterComboBox);
//            }
//        });
//
//        teacherUnassignSubjectSemesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                teacherUnassignSubjectComboBox.setItems(
//                        newValue.getSubjects().stream()
//                                .filter(subject -> subject.getTeacher().equals(teacherTableView.getSelectionModel().getSelectedItem()))
//                                .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                );
//                setStringConverter(teacherUnassignSubjectComboBox);
//            }
//        });
//    }

    private <T> void setStringConverter(ComboBox<T> comboBox) {
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(T object) {
                return (object == null) ? "" : object.toString();
            }

            @Override
            public T fromString(String s) {
                return null;
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

//        teacherDegreeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
//            if (teacherDegreeComboBox.isDisabled()) {
//                disableTextFields(true, teacherSubjectField);
//                disableButtons(true,
//                        teacherCreateSubjectButton,
//                        teacherAssignSubjectButton,
//                        teacherUnassignSubjectButton
//                );
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//            }
//            if (newValue != null) {
//                clearComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//
//                emptyComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//
//                disableComboBoxes(false,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//                createDependencies(teacherSubjectSemesterComboBox, newValue);
//            } else {
//                clearComboBoxes(
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox,
//                        teacherUnassignSubjectComboBox
//                );
//            }
//        });
    }

    private void emptyComboBoxes(ComboBox<?>... comboBoxes) {
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setItems(null);
        }
    }

//    private void setListeners(ComboBox<Degree> degreeComboBox, ComboBox<Semester> semesterComboBox) {
//        degreeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
//            if (degreeComboBox.isDisabled()) {
//                semesterComboBox.setDisable(true);
//            }
//            if (newValue != null) {
//                semesterComboBox.getSelectionModel().clearSelection();
//                semesterComboBox.setItems(FXCollections.observableArrayList());
//                semesterComboBox.setDisable(false);
//                semesterComboBox.setItems(newValue.getSemesters());
//            } else {
//                semesterComboBox.getSelectionModel().clearSelection();
//                semesterComboBox.setDisable(true);
//            }
//        });
//    }

    private void setStudentListeners(ComboBox<Semester> semesterComboBox, Degree degree) {
        semesterComboBox.setItems(degree.getSemesters());
        semesterComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Semester semester) {
                return (semester == null) ? "" : semester.toString();
            }
            @Override
            public Semester fromString(String string) {
                return null;
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
            studentTableView.getSelectionModel().clearSelection();
//            if (studentTableView.getSelectionModel().getSelectedItem() != null) {
//                Student current = studentTableView.getSelectionModel().getSelectedItem();
//                unbindStudentForm(current);
//            }
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
        fillStudentForm(studentTableView.getSelectionModel().getSelectedItem());
//        bindStudentForm(studentTableView.getSelectionModel().getSelectedItem());
        disableStudentForm(false);
        disableComboBoxes(false,
                studentSemesterComboBox,
                studentGroupComboBox
        );
        disableButtons(false, studentAcceptButton);
        disableButtons(true, studentEditButton);
        studentTableView.setDisable(true);
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
                    studentTableView.refresh();

                    disableButtons(true, studentAcceptButton);
                    disableButtons(false, studentEditButton);

                    studentTableView.setDisable(false);
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
        if (!user.getAddress().getStreet().equals(streetField.getText())) user.getAddress().setStreet(streetField.getText());
        if (!user.getAddress().getPostalCode().equals(PCField.getText())) user.getAddress().setPostalCode(PCField.getText());
        if (!user.getAddress().getColony().equals(colonyField.getText())) user.getAddress().setColony(colonyField.getText());
        if (!user.getAddress().getCity().equals(cityField.getText())) user.getAddress().setCity(cityField.getText());
        if (!user.getAddress().getState().equals(stateField.getText())) user.getAddress().setState(stateField.getText());
        if (!user.getAddress().getCountry().equals(countryField.getText())) user.getAddress().setCountry(countryField.getText());
        if (!user.getGender().equals(genderComboBox.getValue())) user.setGender(genderComboBox.getValue());
        if (!user.getBirthDate().equals(datePicker.getValue())) user.setBirthDate(datePicker.getValue());
    }


    @FXML private void studentCancelButtonHandler() {
        switch (studentCancelButton.getText()) {
            case "Cancel":
                disableStudentForm(true);
                break;
            case "Unselect":
                clearStudentForm();
                disableStudentForm(false);
                disableButtons(true, studentNewButton, studentEditButton, studentCancelButton);
                disableButtons(false, studentAcceptButton);
                studentAcceptButton.setText("Create");
                studentTableView.setDisable(false);
                break;
            default:
                break;
        }
    }

    @FXML private void onStudentFilterClick() {

    }

    @FXML private void studentSelectionHandler() {
        if (!studentAcceptButton.isDisabled() && studentAcceptButton.getText().equals("Update")) {
            AlertHandler.showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "You must unselect the current user",
                    "Please unselect or update the current user to continue"
            );
        } else {
            fillStudentForm(studentTableView.getSelectionModel().getSelectedItem());
            disableStudentForm(true);
            disableButtons(false, studentNewButton, studentEditButton, studentCancelButton);
            studentCancelButton.setText("Unselect");
            studentAcceptButton.setText("Update");
            disableButtons(true, studentAcceptButton);
        }
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
            teacherTableView.getSelectionModel().clearSelection();
            clearTeacherForm();
            disableTeacherForm(false);
            disableButtons(true, teacherNewButton, teacherEditButton, teacherCancelButton);
            disableButtons(false, teacherAcceptButton);
            teacherAcceptButton.setText("Create");
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
//        bindTeacherForm(teacherTableView.getSelectionModel().getSelectedItem());
        fillTeacherForm(teacherTableView.getSelectionModel().getSelectedItem());
        disableTeacherForm(false);
        disableComboBoxes(false,
                teacherSubjectSemesterComboBox,

                teacherAssignSubjectSemesterComboBox,
                teacherUnassignSubjectSemesterComboBox
        );
        disableButtons(false, teacherAcceptButton);
        disableButtons(true, teacherEditButton);
        teacherTableView.setDisable(true);
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
                    teacherTableView.refresh();

                    disableButtons(true, teacherAcceptButton);
                    disableButtons(false, teacherEditButton);

                    teacherTableView.setDisable(false);
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

    @FXML private void teacherCancelButtonHandler() {
        switch (teacherCancelButton.getText()) {
            case "Cancel":
                teacherNewButton.fire();
                break;
            case "Unselect":
                clearTeacherForm();
                disableTeacherForm(false);
//                disableButtons(true, teacherEditButton, teacherCancelButton);
//                disableButtons(false, teacherAcceptButton);
//                disableButtons(true,
//                        teacherCreateSubjectButton,
//                        teacherAssignSubjectButton,
//                        teacherUnassignSubjectButton
//                );
//                disableTextFields(true, teacherSubjectField);
//                disableComboBoxes(true,
//                        teacherSubjectSemesterComboBox,
//                        teacherAssignSubjectSemesterComboBox,
//                        teacherAssignSubjectComboBox,
//                        teacherUnassignSubjectComboBox,
//                        teacherUnassignSubjectSemesterComboBox
//                );
                disableButtons(true, teacherNewButton, teacherEditButton, teacherCancelButton);
                disableButtons(false, teacherAcceptButton);
                teacherAcceptButton.setText("Create");
                teacherTableView.setDisable(false);
                break;
            default:
                break;
        }
    }

    @FXML private void onTeacherFilterClick() {

    }

    @FXML private void teacherSelectionHandler() {
        fillTeacherForm(teacherTableView.getSelectionModel().getSelectedItem());
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

        nullComboBoxes(
                studentDegreeComboBox,
                studentSemesterComboBox,
                studentGroupComboBox,

                studentGenderComboBox
        );

        studentDatePicker.setValue(null);
        studentImageView.setImage(null);
    }

/*    private void validateStudentForm() throws NotValidFormatException {
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
    }*/

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
                teacherCountryField,
                teacherSubjectField
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
        }
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

/*    private void validateTeacherForm() throws NotValidFormatException {
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
        if (!Validation.ofName(teacherSpecializationField.getText())) throw new NotValidFormatException("Not a valid Specialization name.");
        if (!Validation.ofPhone(teacherPhoneField.getText())) throw new NotValidFormatException("Not a valid phone number.");
        if (!Validation.ofEmail(teacherEmailField.getText())) throw new NotValidFormatException("Not a valid email address.");
        if (teacherGenderComboBox.getSelectionModel().getSelectedItem() == null) throw new NotValidFormatException("Not a valid gender.");
        if (teacherDegreeComboBox.getSelectionModel().getSelectedItem() == null) throw new NotValidFormatException("Not a valid degree.");
    }*/

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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    private void bindTeacherForm(Teacher current) {
        teacherNameField.textProperty().bindBidirectional(current.nameProperty());
        teacherLastNameField.textProperty().bindBidirectional(current.lastNameProperty());
        teacherLicenseField.textProperty().bindBidirectional(current.licenseProperty());
        teacherSpecializationField.textProperty().bindBidirectional(current.specializationProperty());
        teacherPhoneField.textProperty().bindBidirectional(current.phoneProperty());
        teacherEmailField.textProperty().bindBidirectional(current.emailProperty());
        teacherStreetField.textProperty().bindBidirectional(current.getAddress().streetProperty());
        teacherPCField.textProperty().bindBidirectional(current.getAddress().postalCodeProperty());
        teacherColonyField.textProperty().bindBidirectional(current.getAddress().colonyProperty());
        teacherCityField.textProperty().bindBidirectional(current.getAddress().cityProperty());
        teacherStateField.textProperty().bindBidirectional(current.getAddress().stateProperty());
        teacherCountryField.textProperty().bindBidirectional(current.getAddress().countryProperty());

        teacherGenderComboBox.setValue(current.getGender());
        teacherDegreeComboBox.setValue(current.getDegree());

        studentDatePicker.setValue(current.getBirthDate());
    }

    @Deprecated
    private void unbindTeacherForm(Teacher current) {
        teacherNameField.textProperty().unbindBidirectional(current.nameProperty());
        teacherLastNameField.textProperty().unbindBidirectional(current.lastNameProperty());
        teacherLicenseField.textProperty().unbindBidirectional(current.licenseProperty());
        teacherSpecializationField.textProperty().unbindBidirectional(current.specializationProperty());
        teacherPhoneField.textProperty().unbindBidirectional(current.phoneProperty());
        teacherEmailField.textProperty().unbindBidirectional(current.emailProperty());
        teacherStreetField.textProperty().unbindBidirectional(current.getAddress().streetProperty());
        teacherPCField.textProperty().unbindBidirectional(current.getAddress().postalCodeProperty());
        teacherColonyField.textProperty().unbindBidirectional(current.getAddress().colonyProperty());
        teacherCityField.textProperty().unbindBidirectional(current.getAddress().cityProperty());
        teacherStateField.textProperty().unbindBidirectional(current.getAddress().stateProperty());
        teacherCountryField.textProperty().unbindBidirectional(current.getAddress().countryProperty());

        clearComboBoxes(
                teacherGenderComboBox,
                teacherDegreeComboBox
        );

        teacherDatePicker.setValue(null);
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

    @Deprecated
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
        teacherDatePicker.setValue(teacherBirthDateTableColumn.getCellObservableValue(index).getValue());
    }

    @Deprecated
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
}
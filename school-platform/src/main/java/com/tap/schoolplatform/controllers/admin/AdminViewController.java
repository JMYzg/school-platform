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
    newButton,
    editButton,
    acceptButton,
    cancelButton,
    filterButton;

    @FXML private TextField
    nameField,
    lastNameField,
    phoneField,
    emailField,
    streetField,
    pcField,
    colonyField,
    cityField,
    stateField,
    countryField,

    searchField;

    @FXML private ComboBox<Gender> genderComboBox;

    @FXML private DatePicker datePicker;
    @FXML private ImageView imageView;

    @FXML private TableView<User> searchTableView;
    @FXML private TableColumn<User, String>
    idTableColumn,
    nameTableColumn,
    lastNameTableColumn,
    emailTableColumn,
    phoneTableColumn,
    streetTableColumn,
    pcTableColumn,
    colonyTableColumn,
    cityTableColumn,
    stateTableColumn,
    countryTableColumn;

    @FXML private TableColumn<User, Gender> genderTableColumn;

    @FXML private TableColumn<User, Integer> ageTableColumn;
    @FXML private TableColumn<User, LocalDate> birthDateTableColumn;

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
    @FXML private void newButtonHandler() {
        if (newButton.getText().equals("New")) {
            Optional<ButtonType> response =
                    AlertHandler.showAlert(
                            Alert.AlertType.CONFIRMATION,
                            "Please confirm",
                            "Clear form",
                            "This action will clear the form, do you want to continue?"
                    );
            if (response.isPresent() && response.get() == ButtonType.OK) {
                searchTableView.getSelectionModel().clearSelection();
                clearForm();
                disableForm(false);
                searchTableView.setDisable(false);
                disableButtons(true, newButton, editButton, cancelButton);
                disableButtons(false, acceptButton, filterButton);
                disableTextFields(false, searchField);
                acceptButton.setText("Create");
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
                User user = searchTableView.getSelectionModel().getSelectedItem();
                if (user != null) {
                    clearForm();
                    searchTableView.refresh();
                    searchTableView.getSelectionModel().clearSelection();
                    searchTableView.setDisable(false);
                    disableButtons(false, filterButton, acceptButton);
                    disableButtons(true, editButton, cancelButton, newButton);
                    disableTextFields(false, searchField);
                    disableForm(false);
                    acceptButton.setText("Create");
                    newButton.setText("New");
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

    @FXML private Image uploadImageClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg", "*.jpeg")
        );
        File image = fileChooser.showOpenDialog(newButton.getScene().getWindow());
        if (image != null) {
            try {
                String imagePath = image.toURI().toString();
                Image pfp = new Image(imagePath);
                imageView.setImage(pfp);
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
        fillForm(searchTableView.getSelectionModel().getSelectedItem());
        disableForm(false);
        disableButtons(false, newButton, acceptButton);
        disableButtons(true, editButton, filterButton);
        disableTextFields(true, searchField);
        searchTableView.setDisable(true);
        cancelButton.setText("Cancel");
    }

    // Move this

    @FXML private void acceptButtonHandler() {
        switch (acceptButton.getText()) {
            case "Create":
                try {
                    validateForm();
                    User user = new User (
                            nameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            "789",
                            phoneField.getText(),
                            new Address(
                                    streetField.getText(),
                                    pcField.getText(),
                                    colonyField.getText(),
                                    cityField.getText(),
                                    stateField.getText(),
                                    countryField.getText()
                            ),
                            datePicker.getValue(),
                            genderComboBox.getValue()
                    );
                    //user.setProfilePicture(imageView.getImage());
                    AlertHandler.showAlert(
                            Alert.AlertType.INFORMATION,
                            "Create student",
                            "Successful operation",
                            "The student has been created!"
                    );
                    clearForm();
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
                    validateForm();
                    User user = searchTableView.getSelectionModel().getSelectedItem();

                    updateUser(user);
                    clearForm();
                    disableForm(true);
                    searchTableView.refresh();

                    disableButtons(true, newButton, acceptButton);
                    disableButtons(false, editButton);

                    searchTableView.setDisable(false);

                    disableTextFields(false, searchField);
                    disableButtons(false, filterButton);

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

    private void updateUser(User user) {
        updateUser(user,
                nameField,
                lastNameField,
                phoneField,
                emailField,
                streetField,
                pcField,
                colonyField,
                cityField,
                stateField,
                countryField,
                genderComboBox,
                datePicker
        );
        if (imageView.getImage() != null) {
            //if (user.getProfilePicture() == null || !user.getProfilePicture().equals(imageView.getImage())) user.setProfilePicture(imageView.getImage());
        }
    }
    @FXML private void studentCancelButtonHandler() {
        switch (cancelButton.getText()) {
            case "Cancel":
                studentSelectionHandler();
                break;
            case "Unselect":
                clearForm();
                disableForm(false);
                disableButtons(true, newButton,
                        editButton,
                        cancelButton
                );
                disableButtons(false, acceptButton, filterButton);
                disableTextFields(false, searchField);
                newButton.setText("New");
                acceptButton.setText("Create");
                cancelButton.setText("Cancel");
                break;
            default:
                break;
        }
        searchTableView.setDisable(false);
    }

    @FXML private void onStudentFilterClick() {
//        searchTableView.setItems(findUsers(searchField.getText(), ));
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
        fillForm(searchTableView.getSelectionModel().getSelectedItem());
        disableForm(true);
        disableButtons(false,
                newButton,
                editButton,
                cancelButton,
                filterButton
        );
        disableButtons(true, newButton, acceptButton);
        disableTextFields(false, searchField);
        newButton.setText("Delete");
        cancelButton.setText("Unselect");
        acceptButton.setText("Update");
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
        //tableView.setItems(data.getUsers()); LOAD DATA FROM DATA BASE
    }

    private void bindTableColumns() {
        TableColumn<?, ?>[] studentTableColumns = {
                idTableColumn,
                nameTableColumn,
                lastNameTableColumn,
                emailTableColumn,
                phoneTableColumn,
                genderTableColumn,
                birthDateTableColumn,
                ageTableColumn
        };

        String[] properties = {
                "ID",
                "name",
                "lastName",
                "email",
                "phone",
                "gender",
                "birthDate",
                "age"
        };
        injectCellValues(studentTableColumns, properties);

        bindAddressTableColumns(
                streetTableColumn,
                pcTableColumn,
                colonyTableColumn,
                cityTableColumn,
                stateTableColumn,
                countryTableColumn
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

        setListeners();

        disableButtons(true,
                newButton,
                editButton,
                cancelButton
        );


        datePicker.setEditable(false);

        acceptButton.setText("Create");
    }

    private void setListeners() {
        //setStudentListeners();
        //setTeacherListeners();
    }

    /*private void setTeacherListeners() {
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
        });

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
    } */

    private <T extends User> void bindAddressTableColumns(
            TableColumn<T, String> streetTableColumn,
            TableColumn<T, String> PCTableColumn,
            TableColumn<T, String> colonyTableColumn,
            TableColumn<T, String> cityTableColumn,
            TableColumn<T, String> stateTableColumn,
            TableColumn<T, String> countryTableColumn) {

/*        streetTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress());
        PCTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().postalCodeProperty());
        colonyTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().colonyProperty());
        cityTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().cityProperty());
        stateTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().stateProperty());
        countryTableColumn.setCellValueFactory(cell -> cell.getValue().getAddress().countryProperty());*/
    }

    private void disableForm(boolean toggle) {
        disableTextFields(toggle,
                nameField,
                lastNameField,
                phoneField,
                emailField,
                streetField,
                pcField,
                colonyField,
                cityField,
                stateField,
                countryField
        );
        disableComboBoxes(toggle,
                genderComboBox
        );
        /*disableButtons(toggle,
                imageView
        );*/
        datePicker.setDisable(toggle);
        imageView.setDisable(toggle);
    }

    private void clearForm() {
        clearTextFields(
                nameField,
                lastNameField,
                phoneField,
                emailField,
                streetField,
                pcField,
                colonyField,
                cityField,
                stateField,
                countryField
        );

        nullComboBoxes(
                genderComboBox
        );

        datePicker.setValue(null);
        imageView.setImage(null);
    }

    private void validateForm() throws NotValidFormatException {
        checkRequirements(
                nameField,
                lastNameField,
                phoneField,
                emailField,
                streetField,
                pcField,
                colonyField,
                cityField,
                stateField,
                countryField
        );
        requireNotNull(genderComboBox.getSelectionModel().getSelectedItem(), "Gender is required.");
        requireNotNull(datePicker.getValue(), "Date of birth is required.");
        requireNotNull(imageView.getImage(), "Image is required.");

        validateFieldFormat(Validation.ofName(nameField.getText()), "Not a valid name.");
        validateFieldFormat(Validation.ofName(lastNameField.getText()), "Not a valid last name.");
        validateFieldFormat(Validation.ofPhone(phoneField.getText()), "Not a valid phone number.");
        validateFieldFormat(Validation.ofEmail(emailField.getText()), "Not a valid email address.");
    }

    private void checkRequirements(
            TextField nameField,
            TextField lastNameField,
            TextField phoneField,
            TextField emailField,
            TextField streetField,
            TextField pcField,
            TextField colonyField,
            TextField cityField,
            TextField stateField,
            TextField countryField) throws NotValidFormatException {
        requireText(nameField.getText(), "Name is required.");
        requireText(lastNameField.getText(), "Last name is required.");
        requireText(phoneField.getText(), "Phone number is required.");
        requireText(emailField.getText(), "Email is required.");
        requireText(streetField.getText(), "Street is required.");
        requireText(pcField.getText(), "Postal code is required.");
        requireText(colonyField.getText(), "Colony is required.");
        requireText(cityField.getText(), "City is required.");
        requireText(stateField.getText(), "State is required.");
        requireText(countryField.getText(), "Country is required.");
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
                genderComboBox,
        };
        for (ComboBox<?> comboBox : comboBoxes) {
            comboBox.setEditable(false);
        }
    }

    private void bindComboBoxes() {
        genderComboBox.getItems().setAll(Gender.values());
    }

    private void fillForm(User user) {
        FormFiller.create()
                .with(nameField, user.getName())
                .with(lastNameField, user.getLastName())
                .with(phoneField, user.getPhone())
                .with(emailField, user.getEmail())

                .with(streetField, user.getAddress().getStreet())
                .with(pcField, user.getAddress().getPostalCode())
                .with(colonyField, user.getAddress().getColony())
                .with(cityField, user.getAddress().getCity())
                .with(stateField, user.getAddress().getState())
                .with(countryField, user.getAddress().getCountry())

                .with(genderComboBox, user.getGender())
                .with(datePicker, user.getBirthDate())
                .fill();

        // imageView.setImage(user.getProfilePicture()); User getProfilePicture method needed
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

    private <T> ObservableList<T> findUsers(String attribute, List<T> persons, Function<T, List<String>> fieldExtractor) {
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
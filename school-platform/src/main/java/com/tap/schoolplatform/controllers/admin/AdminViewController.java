package com.tap.schoolplatform.controllers.admin;

import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.shared.Address;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class AdminViewController {

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
            studentLastNameColumn,
            studentEmailTableColumn,
            studentPhoneTableColumn;
    @FXML private TableColumn<Student, Degree> studentDegreeTableColumn;
    @FXML private TableColumn<Student, Group> studentGroupTableColumn;
    @FXML private TableColumn<Student, Gender> studentGenderTableColumn;
    @FXML private TableColumn<Address, String>
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
    @FXML private TableColumn<Address, String>
            teacherStreetTableColumn,
            teacherPCTableColumn,
            teacherColonyTableColumn,
            teacherCityTableColumn,
            teacherStateTableColumn,
            teacherCountryTableColumn;
    @FXML private TableColumn<Teacher, Integer> teacherAgeTableColumn;
}

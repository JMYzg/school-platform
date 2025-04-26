package com.tap.schoolplatform.controllers.teacher.pages;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeacherGradeController {

    @FXML private Label
            groupNameLabel,
            groupSemesterLabel,
            groupShiftLabel;

    @FXML private TableView<Student> gradesTable;

    @FXML private TableColumn<Student, String> idTableColumn;
    @FXML private TableColumn<Student, String> lastNameTableColumn;
    @FXML private TableColumn<Student, String> unit1TableColumn;
    @FXML private TableColumn<Student, String> unit2TableColumn;
    @FXML private TableColumn<Student, String> unit3TableColumn;
    @FXML private TableColumn<Student, String> unit4TableColumn;
    @FXML private TableColumn<Student, String> unit5TableColumn;
    @FXML private TableColumn<Student, String> unit6TableColumn;
    @FXML private TableColumn<Student, String> unit7TableColumn;

    private Group group;

    public void setGroup(Group group) {
        this.group = group;

        updateUI();
    }

    @FXML private void updateUI(){
        String nameGroup = group.getID();
        groupNameLabel.setText(nameGroup);
        String semester = group.getSemester().toString();
        groupSemesterLabel.setText(semester);
        String shift = group.getShift().toString();
        groupShiftLabel.setText(shift);
    }
}

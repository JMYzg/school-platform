package com.tap.schoolplatform.controllers.teacher;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.Student;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeacherGradeController {


    public Label groupNameLabel;
    public Label groupSemesterLabel;
    public Label groupShiftLabel;
    public TableView<Student> gradesTable;
    public TableColumn<Student, String> idTableColumn;
    public TableColumn<Student, String> lastNameTableColumn;
    public TableColumn<Student, String> unit1TableColumn;
    public TableColumn<Student, String> unit2TableColumn;
    public TableColumn<Student, String> unit3TableColumn;
    public TableColumn<Student, String> unit4TableColumn;
    public TableColumn<Student, String> unit5TableColumn;
    public TableColumn<Student, String> unit6TableColumn;
    public TableColumn<Student, String> unit7TableColumn;

    private Group group;

    public void setGroup(Group group){
        this.group = group;

        updateUI();
    }
    private void updateUI(){
        String nameGroup = group.getID();
        groupNameLabel.setText(nameGroup);
        String semester = group.getSemester().toString();
        groupSemesterLabel.setText(semester);
        String shift = group.getShift().toString();
        groupShiftLabel.setText(shift);

    }
}

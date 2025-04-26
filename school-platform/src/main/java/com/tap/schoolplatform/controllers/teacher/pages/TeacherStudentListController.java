package com.tap.schoolplatform.controllers.teacher.pages;

import com.tap.schoolplatform.controllers.admin.AdminViewController;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TeacherStudentListController {

    @FXML public TableView<Student> table;
    @FXML public TableColumn<Student, String>
            idTableColumn,
            lastNameTableColumn,
            nameTableColumn,
            emailTableColumn;

    public Subject subject;

    public void setSubject(Subject subject){
        this.subject = subject;
//        updateUI();
    }

//    private void updateUI(){
////        String nameGroup = group.getID();
////        groupName.setText(nameGroup);
////        String semester = group.getSemester().toString();
////        semesterGroup.setText(semester);
////        String shift = group.getShift().toString();
////        groupShift.setText(shift);
//
////        table.setItems(subject.getSemester());
////        bindTable();
//    }

    private void bindTable() {
        TableColumn<?, ?>[] tableColumns = {
                idTableColumn,
                lastNameTableColumn,
                nameTableColumn,
                emailTableColumn
        };

        String[] attributes = {
                "ID",
                "lastName",
                "name",
                "email"
        };
        AdminViewController.injectCellValues(tableColumns, attributes);
    }
}

package com.tap.schoolplatform.controllers.teacher.pages;
//
//import com.tap.schoolplatform.controllers.admin.AdminViewController;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//public class TeacherStudentListController extends TeacherViewPage {
//
//    public static String PATH = "/views/teacher-views/teacher-option-student-list-view.fxml";
//
//    @FXML public TableView<Student> table;
//    @FXML private TableColumn<Student, String>
//            idTableColumn,
//            lastNameTableColumn,
//            nameTableColumn,
//            emailTableColumn;
//
//    @FXML private void initialize() {
//        bindTable();
//    }
////    private void updateUI(){
//////        String nameGroup = group.getID();
//////        groupName.setText(nameGroup);
//////        String semester = group.getSemester().toString();
//////        semesterGroup.setText(semester);
//////        String shift = group.getShift().toString();
//////        groupShift.setText(shift);
////
//////        table.setItems(subject.getSemester());
//////        bindTable();
////    }
//
//    private void bindTable() {
//
//        TableColumn<?, ?>[] tableColumns = {
//                idTableColumn,
//                lastNameTableColumn,
//                nameTableColumn,
//                emailTableColumn
//        };
//
//        String[] attributes = {
//                "ID",
//                "lastName",
//                "name",
//                "email"
//        };
//        AdminViewController.injectCellValues(tableColumns, attributes);
//    }
//}

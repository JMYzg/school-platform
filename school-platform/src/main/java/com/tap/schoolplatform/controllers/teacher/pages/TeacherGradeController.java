//package com.tap.schoolplatform.controllers.teacher.pages;
//
//import com.tap.schoolplatform.models.academic.Group;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//public class TeacherGradeController extends TeacherViewPage {
//
//    public static final String PATH = "/views/teacher-views/teacher-option-grade-view.fxml";
//
//    @FXML private Label
//            groupNameLabel,
//            groupSemesterLabel,
//            groupShiftLabel;
//
//    @FXML private TableView<Student> gradesTable;
//
//    @FXML private TableColumn<Student, String> idTableColumn;
//    @FXML private TableColumn<Student, String> lastNameTableColumn;
//    @FXML private TableColumn<Student, String> unit1TableColumn;
//    @FXML private TableColumn<Student, String> unit2TableColumn;
//    @FXML private TableColumn<Student, String> unit3TableColumn;
//    @FXML private TableColumn<Student, String> unit4TableColumn;
//    @FXML private TableColumn<Student, String> unit5TableColumn;
//    @FXML private TableColumn<Student, String> unit6TableColumn;
//    @FXML private TableColumn<Student, String> unit7TableColumn;
//
//    private Group subject;
//
//    public void setSubject(Group subject) {
//        this.subject = subject;
//        updateUI();
//    }
//
//    @FXML private void updateUI(){
////        String nameGroup = subject.getID();
////        groupNameLabel.setText(nameGroup);
////        String semester = subject.getSemester().toString();
////        groupSemesterLabel.setText(semester);
////        String shift = subject.getShift().toString();
////        groupShiftLabel.setText(shift);
//    }
//}

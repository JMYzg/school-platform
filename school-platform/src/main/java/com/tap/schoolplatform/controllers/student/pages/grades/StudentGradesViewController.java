package com.tap.schoolplatform.controllers.student.pages.grades;

import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.services.auth.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentGradesViewController {
    @FXML private TableView<Subject> gradesTable;
    @FXML private  TableColumn<Subject, String> subjectsTableColumn;
    @FXML private  TableColumn<Subject, String> unit1TableColumn;
    @FXML private  TableColumn<Subject, String> unit2TableColumn;
    @FXML private  TableColumn<Subject, String> unit3TableColumn;
    @FXML private  TableColumn<Subject, String> unit4TableColumn;
    @FXML private  TableColumn<Subject, String> unit5TableColumn;
    @FXML private  TableColumn<Subject, String> unit6TableColumn;
    @FXML private  TableColumn<Subject, String> unit7TableColumn;

    Student currentStudent = (Student) LoginService.getCurrentUser();

    public void initialize() {
        subjectsTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unit1TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit1"));
        unit2TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit2"));
        unit3TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit3"));
        unit4TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit4"));
        unit5TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit5"));
        unit6TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit6"));
        unit7TableColumn.setCellValueFactory(new PropertyValueFactory<>("unit7"));

        gradesTable.setItems(currentStudent.getGroup().getSemester().getSubjects());
    }

}

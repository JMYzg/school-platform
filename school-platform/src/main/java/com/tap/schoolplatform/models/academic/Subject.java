package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.academic.tasks.Assignment;
import com.tap.schoolplatform.models.academic.tasks.Exam;
import com.tap.schoolplatform.models.academic.tasks.Task;
import com.tap.schoolplatform.models.academic.tasks.Unit;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Subject {

    private final Semester semester;
    private final StringProperty name;
    private Teacher teacher;
    private final ObservableList<Unit> units = FXCollections.observableArrayList();

    public Subject(Semester semester, String name, int units) {
        for (int i = 1; i <= units; i++) {
            this.units.add(new Unit(this, i));
        }
        this.semester = semester;
        this.semester.addSubject(this);
        this.name = new SimpleStringProperty(name);
    }

    public Semester getSemester() {
        return semester;
    }
//    public void setSemester(Semester semester) {
//        this.semester = semester;
//    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) { // Only used by Teacher in addSubject() and removeSubject()
        this.teacher = teacher;
    }

    public Unit getUnit(int number) {
        return units.get(number - 1);
    }

    public ObservableList<Assignment> getAllAssignments() {
        ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        for (Unit unit : units) {
            if (!unit.getAssignments().isEmpty()) {
                assignments.addAll(unit.getAssignments());
            }
        }
        return assignments;
    }

    public ObservableList<Exam> getAllExams() {
        ObservableList<Exam> exams = FXCollections.observableArrayList();
        for (Unit unit : units) {
            if (unit.getExam() != null) {
                exams.add(unit.getExam());
            }
        }
        return exams;
    }

    @Override
    public String toString() {
        return name.get();
    }
}

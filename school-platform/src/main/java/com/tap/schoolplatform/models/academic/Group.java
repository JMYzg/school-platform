package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.academic.enums.Shift;
import com.tap.schoolplatform.models.users.Student;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {

    private StringProperty ID;
    private Semester semester;
    private Shift shift;
    private final ObservableList<Student> students = FXCollections.observableArrayList();

    public Group(Semester semester, Shift shift) {
        this.semester = semester;
        this.shift = shift;
    }

    public String getID() {
        return ID.get();
    }
    public StringProperty IDProperty() {
        return ID;
    }
    public void setID(String ID) {
        this.ID.set(ID);
    }

    public Semester getSemester() {
        return semester;
    }
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Shift getShift() {
        return shift;
    }
    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
    public void removeStudent(Student student) {
        students.remove(student);
    }
    public ObservableList<Student> getStudents() {
        return FXCollections.unmodifiableObservableList(students);
    }
}

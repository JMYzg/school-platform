/**
 * TODO
 * 1. Check if name can be modifiable.
 * */

package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.users.Teacher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Degree {

    private final StringProperty name;
    private final ObservableList<Semester> semesters = FXCollections.observableArrayList();
    private final ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    public Degree(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public void addSemester(Semester semester) {
        this.semesters.add(semester);
    }
    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
    }
    public ObservableList<Semester> getSemesters() {
        return FXCollections.unmodifiableObservableList(semesters);
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }
    public ObservableList<Teacher> getTeachers() {
        return FXCollections.unmodifiableObservableList(teachers);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Degree degree = (Degree) o;
        return name.equals(degree.name);
    }
}

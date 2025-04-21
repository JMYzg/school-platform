/**
 * TODO
 * 1. Check if name can be modifiable.
 * */

package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.utils.SharedData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Degree {

    private final StringProperty name;
    private final ObservableList<Semester> semesters = FXCollections.observableArrayList();
    private final ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    public Degree(String name, int numberOfSemesters) {
        this.name = new SimpleStringProperty(name);
        for (int i = 1; i <= numberOfSemesters; i++ ) {
            addSemester(new Semester(this, i));
        }
        teachers.addListener((ListChangeListener<Teacher>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Teacher teacher : change.getAddedSubList()) {
                        SharedData.getInstance().addUser(teacher);
                    }
                }
                if (change.wasRemoved()) {
                    for (Teacher teacher : change.getRemoved()) {
                        SharedData.getInstance().removeUser(teacher);
                    }
                }
            }
        });
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

    private void addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.setDegree(this);
    }
    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
    }
    public Semester getSemester(int number) {
        return semesters.get(number);
    }
    public ObservableList<Semester> getSemesters() {
        return FXCollections.unmodifiableObservableList(semesters);
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.setDegree(this);
    }
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.setDegree(null);
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

package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.academic.enums.Shift;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.utils.SharedData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Group {

    private final StringProperty ID;
    private Semester semester;
    private final Shift shift;
    private final ObservableList<Student> students = FXCollections.observableArrayList();

    public Group(Semester semester, Shift shift) {
        this.semester = semester;
        this.shift = shift;
        this.ID = new SimpleStringProperty();
        this.semester.addGroup(this);
        this.ID.set(generateID());
        students.addListener((ListChangeListener<Student>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Student student : change.getAddedSubList()) {
                        SharedData.getInstance().addUser(student);
                    }
                }
                if (change.wasRemoved()) {
                    for (Student student : change.getRemoved()) {
                        SharedData.getInstance().removeUser(student);
                    }
                }
            }
        });
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
        this.ID.set(this.semester != null ? generateID() : null);
    }

    public Shift getShift() {
        return shift;
    }
//    public void setShift(Shift shift) {
//        this.shift = shift;
//    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            student.setGroup(this);
        }
    }
    public void removeStudent(Student student) {
        students.remove(student);
        student.setGroup(null);
    }
    public ObservableList<Student> getStudents() {
        return FXCollections.unmodifiableObservableList(students);
    }

    private String generateID() {
        int semester = this.semester.getNumber();
        String initials = this.semester.getDegree().getName().chars()
                .filter(Character::isUpperCase)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int index;
        if (this.semester.getGroups(this.shift) != null && this.semester.getGroups(this.shift).contains(this)) {
            index = this.semester.getGroups(this.shift).indexOf(this) + 1;
        }
        else throw new IllegalArgumentException("The semester does not contain this group");
        String shift = this.shift == Shift.MORNINGS ? "M" : "E";
        return String.format(
                "%d%s-%d%s",
                semester,
                initials,
                index,
                shift
        );
    }

    @Override
    public String toString() {
        return ID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return ID.equals(group.ID);
    }
}

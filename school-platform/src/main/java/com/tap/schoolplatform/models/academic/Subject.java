package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.users.Teacher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private final Semester semester;
    private final StringProperty name;
    private Teacher teacher;

    public Subject(Semester semester, String name) {
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
}

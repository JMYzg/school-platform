package com.tap.schoolplatfom.models.academic;

import com.tap.schoolplatfom.models.users.Teacher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private Semester semester;
    private StringProperty name;
    private Teacher teacher;

    public Subject(Semester semester, String name) {
        this.semester = semester;
        this.name = new SimpleStringProperty(name);
    }

    public Semester getSemester() {
        return semester;
    }
    public void setSemester(Semester semester) {
        this.semester = semester;
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
}

package com.tap.schoolplatform.models.users;

import com.tap.schoolplatform.models.academic.Degree;
import com.tap.schoolplatform.models.academic.Semester;
import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.users.enums.Gender;
import com.tap.schoolplatform.models.users.enums.Role;
import com.tap.schoolplatform.models.users.shared.Address;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Teacher extends User {

    private final StringProperty license;
    private final StringProperty specialization;
    private Degree degree;

    private final ObservableList<Subject> subjects = FXCollections.observableArrayList();

    public Teacher(String name, String lastName, String email, String password, String phone, Address address, LocalDate birthDate, Gender gender) {
        super(name, lastName, email, password, phone, address, birthDate, gender, Role.TEACHER);
        license = new SimpleStringProperty();
        specialization = new SimpleStringProperty();
    }

    public String getLicense() {
        return license.get();
    }
    public StringProperty licenseProperty() {
        return license;
    }
    public void setLicense(String license) {
        this.license.set(license);
    }

    public String getSpecialization() {
        return specialization.get();
    }
    public StringProperty specializationProperty() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization.set(specialization);
    }

    public Degree getDegree() {
        return degree;
    }
    public void setDegree(Degree degree) { // Only used by Degree in addTeacher() and removeTeacher()
        this.degree = degree;
    }

    //Make Modifications find last one down
    public void addSubject(Subject... subject) {
        for (Subject s : subject) {
            subjects.add(s);
            s.setTeacher(this);
        }
    }
    //Last one, before one
//    public void addSubject(Subject subject) {
//        subjects.add(subject);
//        subject.setTeacher(this);
//    }
    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subject.setTeacher(null);
    }

    public ObservableList<Subject> getSubjects() {

        return FXCollections.unmodifiableObservableList(subjects);
    }

    public ObservableList<Semester> getSemesters() {
        ObservableList<Semester> semesters = FXCollections.observableArrayList();
        subjects.forEach(subject -> {
            if (!semesters.contains(subject.getSemester())) semesters.add(subject.getSemester());
        });
        return semesters;
    }
}

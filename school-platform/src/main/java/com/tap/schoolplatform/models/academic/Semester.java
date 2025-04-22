/**
 * TODO
 * 1. check if computeIfAbsent is the best way to add a Group.
 * 2. check how to get a specific Group or Subject.
 * */

package com.tap.schoolplatform.models.academic;

import com.tap.schoolplatform.models.academic.enums.Shift;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Semester {

    private final IntegerProperty number;
    private Degree degree;

    private final Map<Shift, ObservableList<Group>> groups = new HashMap<>();
    private final ObservableList<Subject> subjects = FXCollections.observableArrayList();

    protected Semester(Degree degree, int number) { // Only used by Degree in Constructor
        this.degree = degree;
        this.number = new SimpleIntegerProperty(number);
    }

    public int getNumber() {
        return number.get();
    }

    public Degree getDegree() {
        return degree;
    }
    protected void setDegree(Degree degree) {
        this.degree = degree;
    }

    protected void addGroup(Group group) { // Only used by Group in Constructor
        groups.computeIfAbsent(group.getShift(), s ->
                FXCollections.observableArrayList()).add(group);
    }
    public void removeGroup(Group group) {
        groups.remove(group.getShift());
        group.setSemester(null);
    }

    public ObservableList<Group> getGroups(Shift shift) {
        return FXCollections.unmodifiableObservableList(groups.get(shift));
    }

    public Map<Shift, ObservableList<Group>> getGroups() {
        return Collections.unmodifiableMap(groups);
    }

    protected void addSubject(Subject subject) { // Only used by Subject in Constructor
        subjects.add(subject);
    }
    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public ObservableList<Subject> getSubjects() {
        return FXCollections.unmodifiableObservableList(subjects);
    }

    @Override
    public String toString() {
        return String.valueOf(number.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return number.equals(semester.number) && degree.equals(semester.degree);
    }
}

/**
 * TODO
 * 1. check if computeIfAbsent is the best way to add a Group.
 * 2. check how to get a specific Group or Subject.
 * */

package com.tap.schoolplatfom.models.academic;

import com.tap.schoolplatfom.models.academic.enums.Shift;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Semester {

    private final IntegerProperty number;
    private Degree degree;

    private final Map<Shift, ObservableList<Group>> groups = new HashMap<>();
    private final ObservableList<Subject> subjects = FXCollections.observableArrayList();

    public Semester(Degree degree, int number) {
        this.degree = degree;
        this.number = new SimpleIntegerProperty(number);
    }

    public int getNumber() {
        return number.get();
    }

    public Degree getDegree() {
        return degree;
    }
    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void addGroup(Group group) {
        groups.computeIfAbsent(group.getShift(), s ->
                FXCollections.observableArrayList()).add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group.getShift());
    }

    public ObservableList<Group> getGroups(Shift shift) {
        return FXCollections.unmodifiableObservableList(groups.get(shift));
    }

    public void addSubject(Subject subject) {
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
        return number.toString();
    }
}

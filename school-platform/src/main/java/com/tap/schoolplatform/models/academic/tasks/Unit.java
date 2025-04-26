package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.Subject;
import com.tap.schoolplatform.models.academic.tasks.keys.Submission;
import com.tap.schoolplatform.models.users.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Unit {

    private final int number;
    private final Subject subject;
    private final ObservableMap<Assignment, ObservableList<Submission>> assignments = FXCollections.observableHashMap();
    private final ObservableMap<Exam, ObservableMap<Student, Grade>> exam = FXCollections.observableHashMap();

    public Unit(Subject subject, int number) {
        this.subject = subject;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Subject getSubject() {
        return subject;
    }
    
    public void addAssignment(Assignment assignment) {
        assignments.put(assignment, FXCollections.observableArrayList());
    }
    
    public void addSubmission(Assignment assignment, Submission submission) {
        assignments.computeIfAbsent(assignment, k -> FXCollections.observableArrayList()).add(submission);
    }

    public ObservableList<Submission> getSubmission(Assignment assignment) {
        return FXCollections.unmodifiableObservableList(assignments.get(assignment));
    }
    
    public void setExam(Exam exam) {
        this.exam.put(exam, FXCollections.observableHashMap());
    }

    public void addSubmission(Exam exam, Student student, Grade grade) {
        this.exam.computeIfAbsent(exam, k -> FXCollections.observableHashMap()).put(student, grade);
    }

    public ObservableMap<Student, Grade> getSubmission(Exam exam) {
        return FXCollections.unmodifiableObservableMap(this.exam.get(exam));
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return number == unit.number && subject.equals(unit.subject);
    }
}

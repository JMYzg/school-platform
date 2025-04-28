package com.tap.schoolplatform.models.academic.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Question {

    private int number;
    private String description;
    private Exam exam;
    private final ObservableList<Answer> answers;

    public Question(Exam exam, int number, String description) {
        this.exam = exam;
        this.number = number;
        this.description = description;
        this.answers = FXCollections.observableArrayList();
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getNumber() {
        return number + 1;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public ObservableList<Answer> getAnswers() {
        return FXCollections.unmodifiableObservableList(answers);
    }

    public void setAnswers(ObservableList<Answer> answers) {
        this.answers.clear();
        this.answers.addAll(answers);
    }
}

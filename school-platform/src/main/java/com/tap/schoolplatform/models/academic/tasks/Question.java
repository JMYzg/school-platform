package com.tap.schoolplatform.models.academic.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Question {

    private int number;
    private String description;
    private final ObservableList<Answer> answers;

    public Question(int number, String description, List<Answer> answers) {
        this.number = number;
        this.description = description;
        this.answers = FXCollections.observableArrayList(answers);
    }

    public int getNumber() {
        return number;
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
}

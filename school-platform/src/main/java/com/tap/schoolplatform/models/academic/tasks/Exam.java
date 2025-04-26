package com.tap.schoolplatform.models.academic.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

public class Exam extends Task {

    private LocalDateTime startDate;
    private Timer timer;

    private final ObservableList<Question> questions = FXCollections.observableArrayList();

    public Exam(String title, String description, LocalDateTime deadline) {
        super(title, description, deadline);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Timer getTimer() {
        return timer;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
    public void removeQuestion(Question question) {
        questions.remove(question);
    }
    public ObservableList<Question> getQuestions() {
        return FXCollections.unmodifiableObservableList(questions);
    }
}

package com.tap.schoolplatfom.models.academic.tasks;

import com.tap.schoolplatfom.models.academic.tasks.enums.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public abstract class Task {

    public static final double MAX_SCORE = 10;
    public static final double MIN_SCORE = 0;

    private StringProperty ID;
    private final LocalDateTime creationDate;
    private final StringProperty title;
    private final StringProperty description;
    private LocalDateTime deadline;
    private Status status;

    public Task(String title, String description, LocalDateTime deadline) {
        creationDate = LocalDateTime.now();
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.deadline = deadline;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}

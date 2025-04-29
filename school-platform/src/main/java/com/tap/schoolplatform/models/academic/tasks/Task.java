package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.tasks.enums.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {

    public static final double MAX_SCORE = 10;
    public static final double MIN_SCORE = 0;

    private final StringProperty ID;
    private final Unit unit;
    private final LocalDateTime creationDate;
    private final StringProperty title;
    private final StringProperty description;
    private LocalDateTime deadline;
    private Status status;

    public Task(Unit unit, String title, String description, LocalDateTime deadline) {
        this.unit = unit;
        ID = new SimpleStringProperty();
        creationDate = LocalDateTime.now();
        creationDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.deadline = deadline;
        this.status = deadline.isAfter(creationDate) ? Status.ACTIVE : Status.INACTIVE;
        ID.set(generateID());
    }

    public String getID() {
        return ID.get();
    }
    public StringProperty IDProperty() {
        return ID;
    }
    public void setID(String ID) {
        this.ID.set(ID);
    }

    public Unit getUnit() {
        return unit;
    }
//    public void setUnit(Unit unit) {
//        this.unit.set(unit);
//    }

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

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    private String generateID() {
        return creationDate.format(DateTimeFormatter.ISO_ORDINAL_DATE);
    }

    @Override
    public String toString() {
        return ID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return ID.equals(task.ID);
    }
}

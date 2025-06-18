package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.tasks.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    public static final double MAX_SCORE = 10;
    public static final double MIN_SCORE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime creationDate;
    private String title;
    private String description;
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Assignment() {}

    public Assignment(String title, String description, LocalDateTime deadline) {
        this.creationDate = LocalDateTime.now();
        this.title = title;
        this.description = description;
        this.status = deadline.isAfter(creationDate) ? Status.ACTIVE : Status.INACTIVE;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        this.status = deadline.isAfter(LocalDateTime.now()) ? Status.ACTIVE : Status.INACTIVE;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate(){
        creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment task = (Assignment) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
    return Integer.hashCode(id);
    }
}

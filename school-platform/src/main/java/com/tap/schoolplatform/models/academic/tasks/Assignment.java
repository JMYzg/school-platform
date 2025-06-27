package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.tasks.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @Future
    private LocalDateTime deadline;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public Assignment() {}

    public Assignment(String title, String desc, LocalDateTime deadline, Group group) {
        this.title = title;
        this.description = desc;
        this.deadline = deadline;
        this.group = group;
        this.status = deadline.isAfter(LocalDateTime.now()) ? Status.ACTIVE : Status.INACTIVE;
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

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    public Status getStatus() {
        return status;
    }
    protected void setStatus(Status status) {
        this.status = deadline.isAfter(LocalDateTime.now()) ? Status.ACTIVE : Status.INACTIVE;
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

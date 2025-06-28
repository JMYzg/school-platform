package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.Instant;

@Entity
@Table(name = "submissions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "assignment_id"})
})
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @Past
    private Instant submittedAt;

    @NotNull
    private double score;

    @NotNull
    @Lob
    private byte[] content;

    @NotBlank
    private String feedback;

    public Submission() {
    }

    public Submission(User user, Assignment assignment, Group group, byte[] content) {
        this.user = user;
        this.assignment = assignment;
        this.group = group;
        this.content = content;
        this.submittedAt = Instant.now();
        this.score = -1;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Group getGroup() {
        return group;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
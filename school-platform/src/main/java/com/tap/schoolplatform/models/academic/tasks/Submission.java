package com.tap.schoolplatform.models.academic.tasks;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import jakarta.persistence.*;

import java.io.File;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "assignment_id"})
})
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    private LocalDateTime submittedAt;

    private double score;

    @Lob
    private File content;

    private String feedback;

    public Submission() {
    }

    public Submission(User user, Assignment assignment, Group group, File content) {
        this.user = user;
        this.assignment = assignment;
        this.group = group;
        this.content = content;
        this.submittedAt = LocalDateTime.now();
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

    public File getContent() {
        return content;
    }
    public void setContent(File content) {
        this.content = content;
    }

    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
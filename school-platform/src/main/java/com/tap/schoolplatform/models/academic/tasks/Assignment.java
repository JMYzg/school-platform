package com.tap.schoolplatform.models.academic.tasks;

import java.time.LocalDateTime;

public class Assignment extends Task {
    public Assignment(String title, String description, LocalDateTime deadline) {
        super(title, description, deadline);
    }
}

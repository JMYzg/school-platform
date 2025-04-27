package com.tap.schoolplatform.models.academic.tasks;

import java.time.LocalDateTime;

public class Assignment extends Task {
    public Assignment(Unit unit, String title, String description, LocalDateTime deadline) {
        super(unit, title, description, deadline);
        unit.addAssignment(this);
    }
}

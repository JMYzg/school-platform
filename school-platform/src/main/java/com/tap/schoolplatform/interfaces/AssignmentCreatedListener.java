package com.tap.schoolplatform.interfaces;
import com.tap.schoolplatform.models.academic.tasks.Assignment;

public interface AssignmentCreatedListener {
    void onAssignmentCreated(Assignment newAssignment);
}

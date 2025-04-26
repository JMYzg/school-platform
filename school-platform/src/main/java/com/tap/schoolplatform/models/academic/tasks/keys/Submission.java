package com.tap.schoolplatform.models.academic.tasks.keys;

import com.tap.schoolplatform.models.academic.tasks.Grade;
import com.tap.schoolplatform.models.users.Student;

import java.io.File;

public record Submission(Student student, File file, Grade grade) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission submission = (Submission) o;
        return student.equals(submission.student) && file.equals(submission.file) && grade.equals(submission.grade);
    }

}

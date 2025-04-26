package com.tap.schoolplatform.controllers.teacher.pages;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.academic.Subject;

public abstract class TeacherViewPage {

    private Subject subject;
    private Group group;

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
}

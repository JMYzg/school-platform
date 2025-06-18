package com.tap.schoolplatform.controllers.teacher.pages;

import com.tap.schoolplatform.models.academic.Group;

public abstract class TeacherViewPage {

    public static Group subject;
    private Group group;

    public Group getSubject() {
        return subject;
    }
    public void setSubject(Group subject) {
        TeacherViewPage.subject = subject;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
}

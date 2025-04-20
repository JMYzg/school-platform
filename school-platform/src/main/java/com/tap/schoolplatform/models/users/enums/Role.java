package com.tap.schoolplatform.models.users.enums;

import com.tap.schoolplatform.models.users.Admin;
import com.tap.schoolplatform.models.users.Student;
import com.tap.schoolplatform.models.users.Teacher;
import com.tap.schoolplatform.models.users.User;

public enum Role {
    ADMIN(Admin.class),
    TEACHER(Teacher.class),
    STUDENT(Student.class);

    private final Class<? extends User> UserClass;

    Role(Class<? extends User> userClass) {
        this.UserClass = userClass;
    }

    public Class<? extends User> getUserClass() {
        return UserClass;
    }
}

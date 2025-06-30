module com.tap.schoolplatform {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.management;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires java.sql;
    requires java.desktop;

    opens com.tap.schoolplatform to javafx.fxml;
    exports com.tap.schoolplatform;

    // Controllers
    opens com.tap.schoolplatform.controllers to javafx.fxml;
    exports com.tap.schoolplatform.controllers;

    opens com.tap.schoolplatform.controllers.alerts to javafx.fxml;
    exports com.tap.schoolplatform.controllers.alerts;

    opens com.tap.schoolplatform.controllers.login to javafx.fxml;
    exports com.tap.schoolplatform.controllers.login;

    opens com.tap.schoolplatform.controllers.user to javafx.fxml;
    exports com.tap.schoolplatform.controllers.user;

    opens com.tap.schoolplatform.controllers.admin to javafx.fxml;
    exports com.tap.schoolplatform.controllers.admin;

    opens com.tap.schoolplatform.controllers.user.subs to javafx.fxml;
    exports com.tap.schoolplatform.controllers.user.subs;

    // Models
    opens com.tap.schoolplatform.models.academic to javafx.fxml, org.hibernate.orm.core, org.hibernate.validator;
    exports com.tap.schoolplatform.models.academic;

    opens com.tap.schoolplatform.models.academic.tasks to javafx.fxml, org.hibernate.orm.core, org.hibernate.validator;
    exports com.tap.schoolplatform.models.academic.tasks;

    opens com.tap.schoolplatform.models.academic.tasks.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.academic.tasks.enums;

    opens com.tap.schoolplatform.models.users to javafx.fxml, org.hibernate.orm.core, org.hibernate.validator;
    exports com.tap.schoolplatform.models.users;

    opens com.tap.schoolplatform.models.users.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.users.enums;

    opens com.tap.schoolplatform.models.users.shared to javafx.fxml, org.hibernate.orm.core, org.hibernate.validator;
    exports com.tap.schoolplatform.models.users.shared;

    // SharedData
    opens com.tap.schoolplatform.utils to javafx.fxml;
    exports com.tap.schoolplatform.utils;

    opens com.tap.schoolplatform.controllers.teacher.pages to javafx.fxml;
    exports com.tap.schoolplatform.controllers.teacher.pages;

    opens com.tap.schoolplatform.controllers.student to javafx.fxml;
    exports com.tap.schoolplatform.controllers.student;

    opens com.tap.schoolplatform.controllers.student.pages.classes to javafx.fxml;
    exports com.tap.schoolplatform.controllers.student.pages.classes;

    exports com.tap.schoolplatform.controllers.student.pages.homework;
    opens com.tap.schoolplatform.controllers.student.pages.homework to javafx.fxml;

}
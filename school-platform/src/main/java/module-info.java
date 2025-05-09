module com.tap.schoolplatform {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.management;
    requires java.xml;

    opens com.tap.schoolplatform to javafx.fxml;
    exports com.tap.schoolplatform;

    // Controllers
    opens com.tap.schoolplatform.controllers to javafx.fxml;
    exports com.tap.schoolplatform.controllers;

    opens com.tap.schoolplatform.controllers.admin to javafx.fxml;
    exports com.tap.schoolplatform.controllers.admin;

    opens com.tap.schoolplatform.controllers.alerts to javafx.fxml;
    exports com.tap.schoolplatform.controllers.alerts;

    opens com.tap.schoolplatform.controllers.login to javafx.fxml;
    exports com.tap.schoolplatform.controllers.login;

    opens com.tap.schoolplatform.controllers.teacher to javafx.fxml;
    exports com.tap.schoolplatform.controllers.teacher;

    // Models
    opens com.tap.schoolplatform.models.academic to javafx.fxml;
    exports com.tap.schoolplatform.models.academic;

    opens com.tap.schoolplatform.models.academic.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.academic.enums;

    opens com.tap.schoolplatform.models.academic.tasks to javafx.fxml;
    exports com.tap.schoolplatform.models.academic.tasks;

    opens com.tap.schoolplatform.models.academic.tasks.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.academic.tasks.enums;

    opens com.tap.schoolplatform.models.academic.tasks.keys to javafx.fxml;
    exports com.tap.schoolplatform.models.academic.tasks.keys;

    opens com.tap.schoolplatform.models.users to javafx.fxml;
    exports com.tap.schoolplatform.models.users;

    opens com.tap.schoolplatform.models.users.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.users.enums;

    opens com.tap.schoolplatform.models.users.shared to javafx.fxml;
    exports com.tap.schoolplatform.models.users.shared;

    // SharedData
    opens com.tap.schoolplatform.utils to javafx.fxml;
    exports com.tap.schoolplatform.utils;

    opens com.tap.schoolplatform.controllers.admin.pages to javafx.fxml;
    exports com.tap.schoolplatform.controllers.admin.pages;

    opens com.tap.schoolplatform.controllers.teacher.pages to javafx.fxml;
    exports com.tap.schoolplatform.controllers.teacher.pages;

    opens com.tap.schoolplatform.controllers.teacher.pages.exam to javafx.fxml;
    exports com.tap.schoolplatform.controllers.teacher.pages.exam;

    opens com.tap.schoolplatform.controllers.teacher.pages.homework to javafx.fxml;
    exports com.tap.schoolplatform.controllers.teacher.pages.homework;

    opens com.tap.schoolplatform.controllers.student to javafx.fxml;
    exports com.tap.schoolplatform.controllers.student;

    opens com.tap.schoolplatform.controllers.student.pages.classes to javafx.fxml;
    exports com.tap.schoolplatform.controllers.student.pages.classes;

    exports com.tap.schoolplatform.controllers.student.pages.data;
    opens com.tap.schoolplatform.controllers.student.pages.data to javafx.fxml;

    exports com.tap.schoolplatform.controllers.student.pages.grades;
    opens com.tap.schoolplatform.controllers.student.pages.grades to javafx.fxml;

    exports com.tap.schoolplatform.controllers.student.pages.homework;
    opens com.tap.schoolplatform.controllers.student.pages.homework to javafx.fxml;
}
module com.tap.schoolplatform {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.management;

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

    opens com.tap.schoolplatform.models.users to javafx.fxml;
    exports com.tap.schoolplatform.models.users;

    opens com.tap.schoolplatform.models.users.enums to javafx.fxml;
    exports com.tap.schoolplatform.models.users.enums;

    opens com.tap.schoolplatform.models.users.shared to javafx.fxml;
    exports com.tap.schoolplatform.models.users.shared;

    // SharedData
    opens com.tap.schoolplatform.utils to javafx.fxml;
    exports com.tap.schoolplatform.utils;
    exports com.tap.schoolplatform.controllers.admin.pages;
    opens com.tap.schoolplatform.controllers.admin.pages to javafx.fxml;
}
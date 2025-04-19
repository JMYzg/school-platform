module com.tap.schoolplatform {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.management;

    opens com.tap.schoolplatform to javafx.fxml;
    exports com.tap.schoolplatform;

    opens com.tap.schoolplatform.controllers to javafx.fxml;
    exports com.tap.schoolplatform.controllers;

    opens com.tap.schoolplatform.controllers.admin to javafx.fxml;
    exports com.tap.schoolplatform.controllers.admin;

    opens com.tap.schoolplatform.controllers.alerts to javafx.fxml;
    exports com.tap.schoolplatform.controllers.alerts;

    opens com.tap.schoolplatform.controllers.login to javafx.fxml;
    exports com.tap.schoolplatform.controllers.login;
}
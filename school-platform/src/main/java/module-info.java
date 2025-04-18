module com.tap.schoolplatform {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.tap.schoolplatform to javafx.fxml;
    exports com.tap.schoolplatform;
}
module com.tap.schoolplatfom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.tap.schoolplatfom to javafx.fxml;
    exports com.tap.schoolplatfom;
}
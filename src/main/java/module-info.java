module CloudFileManagment {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports cloud.file.management.app to javafx.graphics;
    opens cloud.file.management.controller to javafx.fxml;
}
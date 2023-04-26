module Demo {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens demo to javafx.fxml;
    exports demo;

    exports demo.controller;
    opens demo.controller to javafx.fxml;

    exports demo.model;
    opens demo.model to javafx.base;
}
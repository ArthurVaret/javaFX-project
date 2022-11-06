module com.example.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens app to javafx.fxml;
    exports app;
    opens controllers to javafx.fxml;
    exports controllers;
    opens service to javafx.fxml;
    exports service;
    opens models to javafx.fxml;
    exports models;

}
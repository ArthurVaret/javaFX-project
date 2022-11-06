module com.example.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens com.example.javafxproject to javafx.fxml;
    exports com.example.javafxproject;
    opens controllers to javafx.fxml;
    exports controllers;
    opens database to javafx.fxml;
    exports database;
    opens models to javafx.fxml;
    exports models;

}
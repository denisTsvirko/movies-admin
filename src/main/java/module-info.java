module com.movies.admin {
    requires javafx.fxml;
    requires javafx.swing;
    requires com.google.gson;
    requires static lombok;
    requires org.controlsfx.controls;
    requires java.net.http;


    opens com.movies.admin to javafx.fxml;
    opens com.movies.admin.controller to javafx.fxml;
    opens com.movies.admin.service.api.dto to com.google.gson, javafx.fxml;
    opens com.movies.admin.service.api.request to com.google.gson, javafx.fxml;
    opens com.movies.admin.helper to com.google.gson;

    exports com.movies.admin;
    exports com.movies.admin.controller;
    exports com.movies.admin.model;
    exports com.movies.admin.service.api.enums;
    opens com.movies.admin.model to javafx.fxml;
}

package com.movies.admin.helper;


import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class AlertHelper {

    public static boolean result = false;

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UTILITY);
        
        alert.showAndWait();
    }
}

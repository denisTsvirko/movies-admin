package com.movies.admin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    @Getter
    private static Stage currentStage;
    @Getter
    @Setter
    private static String token;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        currentStage = stage;
        stage.setScene(scene);
        stage.setTitle("Admin panel");
        stage.getIcons().add(new Image("file:asset/icon.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        currentStage.setResizable(false);
        currentStage.setWidth(616);
        currentStage.setHeight(439);
    }

    public static void setRootForAdminPage(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        currentStage.setResizable(true);
        currentStage.setWidth(1366);
        currentStage.setHeight(768);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
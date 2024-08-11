package com.movies.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.movies.admin.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainPanelController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFXML("genres");
    }


    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private void loadFXML(String fileName) {
        Parent parent;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("view/" + fileName + ".fxml")));
            borderPane.setCenter(parent);

        } catch (IOException ignored) {
        }
    }

    @FXML
    private void close() throws IOException {
        App.setRoot("login");
        App.setToken(null);
    }

    @FXML
    private void loadHomeView(ActionEvent e) {
        loadFXML("HomeView");
    }

    
    @FXML
    private void loadPageUsers(ActionEvent e) {
        loadFXML("users");
    }

    @FXML
    private void loadPageGenres(ActionEvent e) {
        loadFXML("genres");
    }

    @FXML
    private void loadPageSerials(ActionEvent e) {
        loadFXML("serials");
    }
}
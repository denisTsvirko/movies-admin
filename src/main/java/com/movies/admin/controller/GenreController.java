package com.movies.admin.controller;

import com.movies.admin.App;
import com.movies.admin.helper.AlertHelper;
import com.movies.admin.model.Genre;
import com.movies.admin.service.api.GenreService;
import com.movies.admin.service.api.GenreServiceImpl;
import com.movies.admin.service.api.request.GenreRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GenreController implements Initializable {


    private final ObservableList<Genre> genresData = FXCollections.observableArrayList();

    private final GenreService genreService = new GenreServiceImpl();

    @FXML
    private TableView<Genre> tableGenres;

    @FXML
    private TableColumn<Genre, Integer> id;

    @FXML
    private TableColumn<Genre, String> name;

    @FXML
    private TableColumn<Genre, Boolean> isActive;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox isActiveField;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();

        id.setCellValueFactory(new PropertyValueFactory<Genre, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        isActive.setCellValueFactory(new PropertyValueFactory<Genre, Boolean>("isActive"));

        tableGenres.setItems(genresData);

        tableGenres.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String selectedName = newSelection.getName();
                int selectedId = newSelection.getId();
                Boolean selectedIsActive= newSelection.getIsActive();

                idField.setText(String.valueOf(selectedId));
                nameField.setText(selectedName);
                isActiveField.setSelected(selectedIsActive);
            }
        });
    }

    private void initData() {
        try {
            List<Genre> genres = genreService.list();
            genresData.addAll(genres);

        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void save() {
        if (isValidated()) {
            GenreRequest genreRequest = new GenreRequest(
                    nameField.getText(),
                    isActiveField.isSelected()
            );

            try {
                String message = "Genre updated successfully!";
                if (idField.getText().isEmpty()) {
                    genreService.create(genreRequest);
                    message = "Genre created successfully!";
                } else {
                    genreService.update(genreRequest, Integer.parseInt(idField.getText()));
                }

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success", message);
                App.setRootForAdminPage("mainPanel");
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    @FXML
    private void delete() {
        if (idField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Need id for delete genre");
            idField.requestFocus();
            return;
        }

        try {
            genreService.delete(Integer.parseInt(idField.getText()));
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "Genre deleted successfully");
            App.setRootForAdminPage("mainPanel");
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private boolean isValidated() {
        if (nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Name text field cannot be blank.");
            nameField.requestFocus();
        } else if (nameField.getText().length() < 5 || nameField.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Name text field cannot be less than 5 and greater than 25 characters.");
            nameField.requestFocus();
        } else {
            return true;
        }
        return false;
    }
}
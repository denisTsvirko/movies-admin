package com.movies.admin.controller;

import com.movies.admin.helper.AlertHelper;
import com.movies.admin.model.Genre;
import com.movies.admin.model.Series;
import com.movies.admin.service.api.SeriesService;
import com.movies.admin.service.api.SeriesServiceImpl;
import com.movies.admin.service.api.enums.Country;
import com.movies.admin.service.api.enums.Status;
import com.movies.admin.service.api.request.SeriesRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSeriesController implements Initializable {

    private final SeriesService seriesService = new SeriesServiceImpl();
    private final Series currentSeries = SeriesController.getCurrentSeries();
    @FXML
    private ChoiceBox<Status> status;
    @FXML
    private TextField nameRu;
    @FXML
    private TextField nameEng;
    @FXML
    private ChoiceBox<Country> country;
    @FXML
    private DatePicker premiereDate;
    @FXML
    private TextField rating;
    @FXML
    private TextField genres;
    @FXML
    private TextArea description;
    @FXML
    private TextArea story;
    @FXML
    private TextField img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (isValidated()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            List<Genre> genreList = new ArrayList<>();

            if (!genres.getText().isEmpty()) {
                String[] genresIds = genres.getText().split(",");
                for (String genresId : genresIds) {
                    Genre genre = new Genre();
                    genre.id = Integer.parseInt(genresId.strip());
                    genreList.add(genre);
                }
            }

            String desc = null;
            if (description.getText() !=null && !description.getText().isEmpty()){
                desc = description.getText();
            }

            String newStory = null;
            if (story.getText() !=null && !story.getText().isEmpty()){
                newStory = story.getText();
            }

            SeriesRequest seriesRequest = new SeriesRequest(
                    img.getText(),
                    nameRu.getText(),
                    nameEng.getText(),
                    status.getSelectionModel().getSelectedItem(),
                    formatter.format(premiereDate.getValue()),
                    country.getSelectionModel().getSelectedItem(),
                    Float.parseFloat(rating.getText()),
                    desc,
                    newStory,
                    genreList
            );

            try {
                if (currentSeries == null) {
                    seriesService.create(seriesRequest);
                }else {
                    seriesService.update(seriesRequest, currentSeries.id);
                }
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }

        }
    }

    private void initData() {
        status.getItems().setAll(Status.values());
        country.getItems().setAll(Country.values());
        if (currentSeries != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate lDate = LocalDate.parse(currentSeries.getPremiereDate(),formatter);
            StringBuilder gens = new StringBuilder();
            for (Genre genre : currentSeries.genres) {
                gens.append(genre.id).append(", ");
            }
            img.setText(currentSeries.getImg());
            nameRu.setText(currentSeries.getNameRu());
            nameEng.setText(currentSeries.getNameEng());
            if (description != null) {
                description.setText(currentSeries.getDescription());
            }
            if (story != null) {
                story.setText(currentSeries.getStory());
            }
            status.getSelectionModel().select(currentSeries.getStatus());
            country.getSelectionModel().select(currentSeries.getCountry());
            rating.setText(Float.toString(currentSeries.ratingIMDb));
            premiereDate.setValue(lDate);
            genres.setText(gens.toString());
        }
    }


    @FXML
    private void clear() {
        img.clear();
        nameRu.clear();
        nameEng.clear();
        rating.clear();
        description.clear();
        story.clear();
        genres.clear();
        premiereDate.setValue(null);
    }

    private boolean isValidated() {

        if (img.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be blank.");
            img.requestFocus();
        } else if (img.getText().length() < 3 || img.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be less than 3 and greater than 255 characters.");
            img.requestFocus();
        } else if (nameRu.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR,  "Error",
                    "NameRu text field cannot be blank.");
            nameRu.requestFocus();
        } else if (nameRu.getText().length() < 3 || nameRu.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameRu text field cannot be less than 3 and greater than 255 characters.");
            nameRu.requestFocus();
        } else if (nameEng.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameEng text field cannot be blank.");
            nameEng.requestFocus();
        } else if (nameEng.getText().length() < 3 || nameEng.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameEng text field cannot be less than 3 and greater than 255 characters.");
            nameEng.requestFocus();
        } else if (status.getSelectionModel().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Status field cannot be blank.");
            status.requestFocus();
        } else if (country.getSelectionModel().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Country field cannot be blank.");
            country.requestFocus();
        } else if (rating.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Rating field cannot be blank.");
            rating.requestFocus();
        } else if (premiereDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Premiere Date cannot be blank.");
            premiereDate.requestFocus();
        } else {
            return true;
        }
        return false;
    }
}
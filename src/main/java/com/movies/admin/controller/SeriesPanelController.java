package com.movies.admin.controller;

import com.movies.admin.helper.AlertHelper;
import com.movies.admin.model.Episode;
import com.movies.admin.model.Genre;
import com.movies.admin.model.Season;
import com.movies.admin.model.Series;
import com.movies.admin.service.api.*;
import com.movies.admin.service.api.enums.Country;
import com.movies.admin.service.api.enums.Status;
import com.movies.admin.service.api.request.EpisodeRequest;
import com.movies.admin.service.api.request.SeasonRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class SeriesPanelController implements Initializable {

    private final SeasonService seasonService = new SeasonServiceImpl();
    private final EpisodeService episodeService = new EpisodeServiceImpl();
    private final Series currentSeries = SeriesController.getCurrentSeries();

    private Season currentSeason;

    private final ObservableList<Season> seasonData = FXCollections.observableArrayList();
    private final ObservableList<Episode> episodeData = FXCollections.observableArrayList();

    private Boolean showEpisodes = false;

    @FXML
    private ChoiceBox<Status> status;
    @FXML
    private TextField seriesId;
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

    private Window window;

    @FXML
    private TableView<Season> tableSeason;
    @FXML
    private TableColumn<Season, Integer> tableSeasonId;
    @FXML
    private TableColumn<Season, Status>  tableSeasonStatus;
    @FXML
    private TableColumn<Season, Integer>  tableSeasonYear;
    @FXML
    private TableColumn<Season, Integer>  tableSeasonNumber;
    @FXML
    private TableColumn<Season, String>  tableSeasonImg;
    @FXML
    private TableColumn<Season, Integer>  tableSeasonTotal;
    @FXML
    private TextField seasonYear;
    @FXML
    private TextField seasonId;
    @FXML
    private ChoiceBox<Status> seasonStatus;
    @FXML
    private TextField seasonNumber;
    @FXML
    private TextField seasonImg;
    @FXML
    private TextField seasonTotal;

    @FXML
    public TableView<Episode> tableEpisodes;
    @FXML
    public TableColumn<Episode, Integer>  tableEpisodesId;
    @FXML
    public TableColumn<Episode, Integer>  tableEpisodesNumber;
    @FXML
    public TableColumn<Episode, String>  tableEpisodesNameRu;
    @FXML
    public TableColumn<Episode, String>  tableEpisodesNameEng;
    @FXML
    public TableColumn<Episode, String>  tableEpisodesReleaseDateRu;
    @FXML
    public TableColumn<Episode, String>  tableEpisodesReleaseDateEng;
    @FXML
    public TableColumn<Episode, Float>  tableEpisodesIMDb;
    @FXML
    public TextField episodeRating;
    @FXML
    public TextField episodeId;
    @FXML
    public TextField episodeNumber;
    @FXML
    public TextField episodeNameRu;
    @FXML
    public TextField episodeNameEng;
    @FXML
    public DatePicker episodeReleaseDateRu;
    @FXML
    public DatePicker episodeReleaseDateEng;
    @FXML
    public TextField episodeImg;
    @FXML
    public TextArea episodeDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
        initSeasons();
    }


    private void initData() {
        status.getItems().setAll(Status.values());
        country.getItems().setAll(Country.values());
        if (currentSeries == null) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate lDate = LocalDate.parse(currentSeries.getPremiereDate(), formatter);
        StringBuilder gens = new StringBuilder();
        for (Genre genre : currentSeries.genres) {
            gens.append(genre.name).append(", ");
        }

        seriesId.setText(Integer.toString(currentSeries.getId()));
        img.setText(currentSeries.getImg());
        nameRu.setText(currentSeries.getNameRu());
        nameEng.setText(currentSeries.getNameEng());
        status.getSelectionModel().select(currentSeries.getStatus());
        country.getSelectionModel().select(currentSeries.getCountry());
        rating.setText(Float.toString(currentSeries.ratingIMDb));
        premiereDate.setValue(lDate);
        genres.setText(gens.toString());
        story.setText(currentSeries.getStory());
        description.setText(currentSeries.getDescription());
    }

    private void initSeasons() {
        if (currentSeries == null) {
            return;
        }
        try {
            getSeasonsData();
            tableSeasonId.setCellValueFactory(new PropertyValueFactory<Season, Integer>("id"));
            tableSeasonStatus.setCellValueFactory(new PropertyValueFactory<Season, Status>("status"));
            tableSeasonYear.setCellValueFactory(new PropertyValueFactory<Season, Integer>("year"));
            tableSeasonNumber.setCellValueFactory(new PropertyValueFactory<Season, Integer>("number"));
            tableSeasonImg.setCellValueFactory(new PropertyValueFactory<Season, String>("img"));
            tableSeasonTotal.setCellValueFactory(new PropertyValueFactory<Season, Integer>("totalEpisodes"));
            tableSeason.setItems(seasonData);

            seasonStatus.getItems().setAll(Status.values());
            tableSeason.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    seasonId.setText(String.valueOf(newSelection.getId()));
                    seasonYear.setText(String.valueOf(newSelection.getYear()));
                    seasonNumber.setText(String.valueOf(newSelection.getNumber()));
                    seasonImg.setText(newSelection.getImg());
                    seasonTotal.setText(String.valueOf(newSelection.getTotalEpisodes()));
                    seasonStatus.getSelectionModel().select(newSelection.getStatus());
                    currentSeason = newSelection;
                    showEpisodes = true;
                }
            });

        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void getSeasonsData() {
        try {
            seasonData.clear();
            List<Season> seasons = seasonService.list(currentSeries.id);
            seasonData.addAll(seasons);
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void deleteSeason() {
        if (seasonId.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Choice Season for delete");
            seasonId.requestFocus();
            return;
        }

        try {
            seasonService.delete(Integer.parseInt(seasonId.getText()));
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "Season deleted successfully");
            seasonData.remove(tableSeason.getSelectionModel().getSelectedItem());
            clearSeason();
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    @FXML
    private void saveSeason() {
        if (isValidatedSeason()) {
            SeasonRequest seasonRequest = new SeasonRequest(
                    seasonImg.getText(),
                    seasonStatus.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(seasonYear.getText()),
                    Integer.parseInt(seasonNumber.getText()),
                    Integer.parseInt(seasonTotal.getText()),
                    currentSeries
            );
            try {
                if (seasonId.getText().isEmpty()) {
                    seasonService.create(seasonRequest);
                }else {
                    seasonService.update(seasonRequest, Integer.parseInt(seasonId.getText()));
                }
                clearSeason();
                getSeasonsData();
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }
    @FXML
    private void clearSeason() {
        seasonId.clear();
        seasonYear.clear();
        seasonNumber.clear();
        seasonImg.clear();
        seasonTotal.clear();
        seasonStatus.getItems().setAll(Status.values());
        currentSeason = null;
    }

    private boolean isValidatedSeason() {

        if (img.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be blank.");
            img.requestFocus();
        } else if (img.getText().length() < 3 || img.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be less than 3 and greater than 255 characters.");
            img.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void clickOnEpisode() {
        if (currentSeason == null && !showEpisodes) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, "Episode",
                    "Episode not selected");
            showEpisodes = true;
        }
        initEpisodes();
    }

    @FXML
    private void clickOnSeason() {
        showEpisodes = false;
    }

    private void initEpisodes() {
        if (currentSeason == null) {
            return;
        }
        getEpisodesData();
        tableEpisodesId.setCellValueFactory(new PropertyValueFactory<Episode, Integer>("id"));
        tableEpisodesNumber.setCellValueFactory(new PropertyValueFactory<Episode, Integer>("number"));
        tableEpisodesNameRu.setCellValueFactory(new PropertyValueFactory<Episode, String>("nameRu"));
        tableEpisodesNameEng.setCellValueFactory(new PropertyValueFactory<Episode, String>("nameEng"));
        tableEpisodesReleaseDateRu.setCellValueFactory(new PropertyValueFactory<Episode, String>("releaseDateRu"));
        tableEpisodesReleaseDateEng.setCellValueFactory(new PropertyValueFactory<Episode, String>("releaseDateEng"));
        tableEpisodesIMDb.setCellValueFactory(new PropertyValueFactory<Episode, Float>("ratingIMDb"));
        tableEpisodes.setItems(episodeData);

        tableEpisodes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate lDateRu = LocalDate.parse(newSelection.getReleaseDateRu(),formatter);
                LocalDate lDateEng = LocalDate.parse(newSelection.getReleaseDateEng(),formatter);
                episodeId.setText(String.valueOf(newSelection.getId()));
                episodeRating.setText(Float.toString(newSelection.getRatingIMDb()));
                episodeNumber.setText(String.valueOf(newSelection.getNumber()));
                episodeNameRu.setText(newSelection.getNameRu());
                episodeNameEng.setText(newSelection.getNameEng());
                episodeReleaseDateRu.setValue(lDateRu);
                episodeReleaseDateEng.setValue(lDateEng);
                episodeImg.setText(newSelection.getImg());
                episodeDescription.setText(newSelection.getDescription());
            }
        });
    }

    private void getEpisodesData() {
        try {
            episodeData.clear();
            List<Episode> episodes = episodeService.list(currentSeason.id);
            episodeData.addAll(episodes);
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void deleteEpisode() {
        if (episodeId.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Choice Episode for delete");
            episodeId.requestFocus();
            return;
        }

        try {
            episodeService.delete(Integer.parseInt(episodeId.getText()));
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "Episode deleted successfully");
            episodeData.remove(tableEpisodes.getSelectionModel().getSelectedItem());
            clearEpisode();
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    @FXML
    private void saveEpisode() {
        if (isValidatedEpisode()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            EpisodeRequest episodeRequest = new EpisodeRequest(
                    episodeImg.getText(),
                    Integer.parseInt(episodeNumber.getText()),
                    episodeNameRu.getText(),
                    episodeNameEng.getText(),
                    formatter.format(episodeReleaseDateRu.getValue()),
                    formatter.format(episodeReleaseDateEng.getValue()),
                    Float.parseFloat(episodeRating.getText()),
                    episodeDescription.getText(),
                    currentSeason
            );
            try {
                if (episodeId.getText().isEmpty()) {
                    episodeService.create(episodeRequest);
                }else {
                    episodeService.update(episodeRequest, Integer.parseInt(episodeId.getText()));
                }
                clearEpisode();
                getEpisodesData();
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    private boolean isValidatedEpisode() {

        if (episodeImg.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be blank.");
            episodeImg.requestFocus();
        } else if (episodeImg.getText().length() < 3 || episodeImg.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Img text field cannot be less than 3 and greater than 255 characters.");
            episodeImg.requestFocus();
        } else if (episodeNameRu.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR,  "Error",
                    "NameRu text field cannot be blank.");
            episodeNameRu.requestFocus();
        } else if (episodeNameRu.getText().length() < 3 || episodeNameRu.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameRu text field cannot be less than 3 and greater than 255 characters.");
            episodeNameRu.requestFocus();
        } else if (episodeNameEng.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameEng text field cannot be blank.");
            episodeNameEng.requestFocus();
        } else if (episodeNameEng.getText().length() < 3 || episodeNameEng.getText().length() > 255) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "NameEng text field cannot be less than 3 and greater than 255 characters.");
            episodeNameEng.requestFocus();
        } else if (episodeRating.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Rating field cannot be blank.");
            episodeRating.requestFocus();
        } else if (episodeReleaseDateRu.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Release date ru cannot be blank.");
            episodeReleaseDateRu.requestFocus();
        } else if (episodeReleaseDateEng.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Release date eng cannot be blank.");
            episodeReleaseDateEng.requestFocus();
        } else if (episodeNumber.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Number cannot be blank.");
            episodeNumber.requestFocus();
        } else if (episodeDescription.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Description cannot be blank.");
            episodeDescription.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    private void clearEpisode() {
        episodeRating.clear();
        episodeNumber.clear();
        episodeNameRu.clear();
        episodeId.clear();
        episodeNameEng.clear();
        episodeReleaseDateRu.setValue(null);
        episodeReleaseDateEng.setValue(null);
        episodeImg.clear();
        episodeDescription.clear();
    }
}
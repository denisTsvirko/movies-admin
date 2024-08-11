package com.movies.admin.controller;

import com.movies.admin.App;
import com.movies.admin.helper.AlertHelper;
import com.movies.admin.model.Series;
import com.movies.admin.service.api.SeriesService;
import com.movies.admin.service.api.SeriesServiceImpl;
import com.movies.admin.service.api.enums.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SeriesController implements Initializable {

    @Getter
    @Setter
    private static Series currentSeries;

    private final ObservableList<Series> seriesData = FXCollections.observableArrayList();

    private final SeriesService seriesService = new SeriesServiceImpl();

    @FXML
    private TableView<Series> tableSeries;

    @FXML
    private TableColumn<Series, Integer> id;

    @FXML
    private TableColumn<Series, String> nameRu;

    @FXML
    private TableColumn<Series, String> nameEng;

    @FXML
    private TableColumn<Series, Status> status;

    @FXML
    private TableColumn<Series, String> premiereDate;

    @FXML
    private TableColumn<Series, String> country;

    @FXML
    private TableColumn<Series, Float> imdb;

    @FXML
    private TextField idDeleteField;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();

        id.setCellValueFactory(new PropertyValueFactory<Series, Integer>("id"));
        nameRu.setCellValueFactory(new PropertyValueFactory<Series, String>("nameRu"));
        nameEng.setCellValueFactory(new PropertyValueFactory<Series, String>("nameEng"));
        status.setCellValueFactory(new PropertyValueFactory<Series, Status>("status"));
        premiereDate.setCellValueFactory(new PropertyValueFactory<Series, String>("premiereDate"));
        country.setCellValueFactory(new PropertyValueFactory<Series, String>("country"));
        imdb.setCellValueFactory(new PropertyValueFactory<Series, Float>("ratingIMDb"));

        addButtonToTable();

        tableSeries.setItems(seriesData);

        tableSeries.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int selectedId = newSelection.getId();
                setCurrentSeries(newSelection);
                idDeleteField.setText(String.valueOf(selectedId));
            }
        });
    }

    private void addButtonToTable() {
        TableColumn<Series, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Series, Void>, TableCell<Series, Void>> cellFactory = new Callback<TableColumn<Series, Void>, TableCell<Series, Void>>() {
            @Override
            public TableCell<Series, Void> call(final TableColumn<Series, Void> param) {
                final TableCell<Series, Void> cell = new TableCell<Series, Void>() {

                    private final Button btn = new Button("show"); {
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Series series = getTableView().getItems().get(getIndex());
                                createMainSeriesPopup();
                            }
                        });
                        btn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#2EE6E0"), CornerRadii.EMPTY, Insets.EMPTY)));

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        tableSeries.getColumns().add(colBtn);
    }

    private void initData() {
        try {
            List<Series> series = seriesService.list();
            seriesData.clear();
            seriesData.addAll(series);

        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void deleteSeries() {
        if (idDeleteField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Choice Series for delete");
            idDeleteField.requestFocus();
            return;
        }

        try {
            seriesService.delete(Integer.parseInt(idDeleteField.getText()));
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "Series deleted successfully");
            seriesData.remove(tableSeries.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void addSeries(ActionEvent eventA) {
        setCurrentSeries(null);
        idDeleteField.setText("");
        createPopup("Create series");
    }

    @FXML
    private void updateSeries() {
        if (idDeleteField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Choice Series for update");
            idDeleteField.requestFocus();
            return;
        }

        createPopup("Update series");
    }

    private void createPopup(String title) {
        Parent root;
        try {
            root =  FXMLLoader.load(Objects.requireNonNull(App.class.getResource("view/addSeries.fxml")));;
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:asset/icon.png"));
            stage.setScene(new Scene(root, 575, 635));

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                }
            });
            stage.show();

        } catch (IOException ignored) {
        }
    }

    private void createMainSeriesPopup() {
        Parent root;
        try {
            root =  FXMLLoader.load(Objects.requireNonNull(App.class.getResource("view/seriesPanel.fxml")));;
            Stage stage = new Stage();
            stage.setTitle("Serial Panel");
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:asset/icon.png"));
            stage.setScene(new Scene(root, 1366, 768));

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                }
            });
            stage.show();

        } catch (IOException ignored) {
        }
    }

    private void update() {
        initData();
    }
}
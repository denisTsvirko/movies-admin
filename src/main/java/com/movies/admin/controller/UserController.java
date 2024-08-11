package com.movies.admin.controller;

import com.movies.admin.helper.AlertHelper;
import com.movies.admin.model.Role;
import com.movies.admin.model.User;
import com.movies.admin.service.api.UserService;
import com.movies.admin.service.api.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    private final ObservableList<User> usersData = FXCollections.observableArrayList();

    private final UserService userService = new UserServiceImpl();

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, List<Role>> roles;

    @FXML
    private TextField idField;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();

        id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        roles.setCellValueFactory(new PropertyValueFactory<User, List<Role>>("roles"));

        tableUsers.setItems(usersData);

        tableUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int selectedId = newSelection.getId();
                idField.setText(String.valueOf(selectedId));
            }
        });
    }

    private void initData() {
        try {
            List<User> users = userService.list();
            usersData.addAll(users);

        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void delete() {
        if (idField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Choice User for delete");
            idField.requestFocus();
            return;
        }

        try {
            userService.delete(Integer.parseInt(idField.getText()));
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "User deleted successfully");
            usersData.remove(tableUsers.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
}
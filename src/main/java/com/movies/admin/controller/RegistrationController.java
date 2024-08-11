package com.movies.admin.controller;

import java.io.IOException;

import com.movies.admin.App;

import com.movies.admin.helper.AlertHelper;
import com.movies.admin.service.api.RegistrationService;
import com.movies.admin.service.api.RegistrationServiceImpl;
import com.movies.admin.service.api.enums.Role;
import com.movies.admin.service.api.request.RegistrationRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    public TextField name;
    @FXML
    public TextField email;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public Button registerButton;

    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void register() {
        if (this.isValidated()) {
            try {
                String[] roles = {Role.ROLE_ADMIN.name()};

                RegistrationRequest registrationRequest = new RegistrationRequest(
                        username.getText(),
                        password.getText(),
                        email.getText(),
                        password.getText(),
                        roles
                );
                registrationService.registration(registrationRequest);

                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, "Success", "Registration Successful");
                App.setRoot("login");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    private boolean isValidated() {

        if (name.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Name text field cannot be blank.");
            name.requestFocus();
        } else if (name.getText().length() < 2 || name.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Name text field cannot be less than 2 and greater than 25 characters.");
            name.requestFocus();
        } else if (email.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR,  "Error",
                    "Email text field cannot be blank.");
            email.requestFocus();
        } else if (email.getText().length() < 5 || email.getText().length() > 45) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Email text field cannot be less than 5 and greater than 45 characters.");
            email.requestFocus();
        } else if (username.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Username text field cannot be less than 5 and greater than 25 characters.");
            username.requestFocus();
        } else if (password.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Password text field cannot be less than 5 and greater than 25 characters.");
            password.requestFocus();
        } else if (confirmPassword.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Confirm password text field cannot be blank.");
            confirmPassword.requestFocus();
        } else if (confirmPassword.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Confirm password text field cannot be less than 5 and greater than 25 characters.");
            confirmPassword.requestFocus();
        } else if (!password.getText().equals(confirmPassword.getText())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Password and confirm password text fields does not match.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }
}
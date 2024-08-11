package com.movies.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.movies.admin.App;
import com.movies.admin.helper.AlertHelper;
import com.movies.admin.service.api.LoginService;
import com.movies.admin.service.api.LoginServiceImpl;

import com.movies.admin.service.api.dto.Token;
import com.movies.admin.service.api.request.LoginRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameOrEmail;

    @FXML
    private TextField password;


    private final LoginService loginService = new LoginServiceImpl();

     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


    @FXML
    private void switchToRegistration() throws IOException {
         App.setRoot("registration");
    }

    @FXML
    private void login() {
         if (this.isValidated()) {
            try {
                LoginRequest loginRequest = new LoginRequest(
                        usernameOrEmail.getText(),
                        password.getText()
                );

                Token token = loginService.login(loginRequest);
                App.setToken(token.accessToken);
                App.setRootForAdminPage("mainPanel");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
         }
    }

    private boolean isValidated() {
        if (usernameOrEmail.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Username text field cannot be blank.");
            usernameOrEmail.requestFocus();
        } else if (usernameOrEmail.getText().length() < 5 || usernameOrEmail.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Username text field cannot be less than 5 and greater than 25 characters.");
                usernameOrEmail.requestFocus();
        } else if (password.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Error",
                    "Password text field cannot be less than 5 and greater than 25 characters.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }
}

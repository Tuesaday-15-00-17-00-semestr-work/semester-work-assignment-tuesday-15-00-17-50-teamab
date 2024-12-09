package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class UserPopupApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        showUserRegistrationPopup(primaryStage);
    }

    private void showUserRegistrationPopup(Stage primaryStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("User Registration");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        Label roleIdLabel = new Label("Role ID:");
        ChoiceBox<String> roleIdChoiceBox = new ChoiceBox<>();
        roleIdChoiceBox.getItems().addAll("Admin", "User");
        grid.add(roleIdLabel, 0, 2);
        grid.add(roleIdChoiceBox, 1, 2);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 1, 4);

        Button backButton = new Button("Back");
        grid.add(backButton, 0, 4);

        saveButton.setOnAction(event -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                Integer roleId = "Admin".equals(roleIdChoiceBox.getValue()) ? 1 : 2;

                // Fetch the next User ID
                Integer userId = getNextUserId();

                saveUserToDatabase(userId, username, password, email, roleId);

            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Invalid Input", "Please ensure all fields are filled correctly.");
            }
        });

        backButton.setOnAction(event -> {
            popupStage.close();
            openLoginWindow();
        });

        Scene scene = new Scene(grid, 300, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    private Integer getNextUserId() {
        Integer maxUserId = 0;
        try {
            URL url = new URL("http://localhost:8080/api/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONArray users = new JSONArray(response.toString());

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                int userId = user.getInt("userId");
                if (userId > maxUserId) {
                    maxUserId = userId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxUserId + 1;
    }

    private void saveUserToDatabase(Integer userId, String username, String password, String email, Integer roleId) {
        try {
            URL url = new URL("http://localhost:8080/api/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String userJson = String.format(
                    "{\"userId\":%d, \"username\":\"%s\", \"password\":\"%s\", \"email\":\"%s\", \"roleId\":%d}",
                    userId, username, password, email, roleId);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(userJson.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                showInformationAlert("Success", "User registered successfully!");
            } else {
                showErrorAlert("Registration Failed", "The registration process failed. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Error during registration process.");
        }
    }

    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openLoginWindow() {
        LoginWindow loginWindow = new LoginWindow();
        Stage loginStage = new Stage();
        loginWindow.start(loginStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
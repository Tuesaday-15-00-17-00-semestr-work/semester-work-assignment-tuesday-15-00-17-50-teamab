package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

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

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 3);

        final Label message = new Label();
        grid.add(message, 1, 4);

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Integer roleId = authenticateUser(username, password);
            if (roleId != null) {
                message.setText("Login successful!");
                if (roleId == 1) {
                    showAdminMenu();
                } else if (roleId == 2) {
                    showUserMenu();
                }
                primaryStage.close();
            } else {
                message.setText("Login failed. Incorrect username or password.");
            }
        });

        registerButton.setOnAction(event -> {
            primaryStage.close();
            openUserRegistration();
        });

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit(); // Exit the application on window close
            System.exit(0);  // Ensure the process terminates
        });

        primaryStage.show();
    }

    private Integer authenticateUser(String username, String password) {
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
                String existingUsername = user.getString("username");
                String existingPassword = user.getString("password");
                int roleId = user.getInt("roleId");

                if (existingUsername.equals(username) && existingPassword.equals(password)) {
                    return roleId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAdminMenu() {
        Stage adminMenuStage = new Stage();
        MenuAdmin menuAdmin = new MenuAdmin();
        menuAdmin.start(adminMenuStage);
    }

    private void showUserMenu() {
        Stage userMenuStage = new Stage();
        MenuUser menuUser = new MenuUser();
        menuUser.start(userMenuStage);
    }

    private void openUserRegistration() {
        UserPopupApplication userPopup = new UserPopupApplication();
        Stage registrationStage = new Stage();
        userPopup.start(registrationStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
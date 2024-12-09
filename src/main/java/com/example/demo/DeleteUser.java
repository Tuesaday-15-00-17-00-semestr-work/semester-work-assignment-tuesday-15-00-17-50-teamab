package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteUser extends Application {

    private TableView<User> table = new TableView<>();
    private ObservableList<User> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Delete Users");

        TableColumn<User, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setMinWidth(100);
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        table.setItems(data);
        table.getColumns().addAll(userIdColumn, usernameColumn);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                String selectedUsername = selectedUser.getUsername();
                deleteUserByUsername(selectedUsername);
                data.remove(selectedUser); // Remove from Table View after deletion
            } else {
                showInformationAlert("No Selection", "Please select a user to delete.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            primaryStage.close();
            openMenuAdminWindow();
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, deleteButton, backButton);

        Scene scene = new Scene(vbox, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        fetchUsers();
    }

    private void fetchUsers() {
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
                String username = user.getString("username");

                data.add(new User(userId, username));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUserByUsername(String username) {
        try {
            URL url = new URL("http://localhost:8080/api/users/" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                showInformationAlert("Success", "User with username '" + username + "' deleted successfully!");
            } else {
                showErrorAlert("Deletion Failed", "Failed to delete the user. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Error during the deletion process.");
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

    private void openMenuAdminWindow() {
        MenuAdmin menuAdmin = new MenuAdmin();
        Stage menuAdminStage = new Stage();
        menuAdmin.start(menuAdminStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class User {
        private Integer userId;
        private String username;

        public User(Integer userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }
    }
}
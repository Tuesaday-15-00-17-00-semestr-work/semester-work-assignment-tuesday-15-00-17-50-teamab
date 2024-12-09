package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteBookUser extends Application {

    private TableView<Book> table = new TableView<>();
    private ObservableList<Book> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Delete Books");

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        table.setItems(data);
        table.getColumns().addAll(titleColumn);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String selectedTitle = selectedBook.getTitle();
                deleteBookByTitle(selectedTitle);
                data.remove(selectedBook); // Remove from Table View after deletion
            } else {
                showInformationAlert("No Selection", "Please select a book to delete.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            primaryStage.close();
            openMenuUserWindow();
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, deleteButton, backButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        fetchBooks();
    }

    private void fetchBooks() {
        try {
            URL url = new URL("http://localhost:8080/api/books");
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

            JSONArray books = new JSONArray(response.toString());

            for (int i = 0; i < books.length(); i++) {
                JSONObject book = books.getJSONObject(i);
                String title = book.getString("title");

                data.add(new Book(title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteBookByTitle(String title) {
        try {
            URL url = new URL("http://localhost:8080/api/books/" + title);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                showInformationAlert("Success", "Book titled '" + title + "' deleted successfully!");
            } else {
                showErrorAlert("Deletion Failed", "Failed to delete the book. Response code: " + responseCode);
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

    private void openMenuUserWindow() {
        MenuUser menuUser = new MenuUser();
        Stage menuUserStage = new Stage();
        menuUser.start(menuUserStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Book {
        private String title;

        public Book(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
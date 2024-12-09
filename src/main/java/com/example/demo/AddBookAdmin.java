package com.example.demo;

import javafx.application.Application;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBookAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Add Book");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Title:");
        grid.add(titleLabel, 0, 0);

        TextField titleField = new TextField();
        grid.add(titleField, 1, 0);

        Label authorLabel = new Label("Author:");
        grid.add(authorLabel, 0, 1);

        TextField authorField = new TextField();
        grid.add(authorField, 1, 1);

        Label isbnLabel = new Label("ISBN:");
        grid.add(isbnLabel, 0, 2);

        TextField isbnField = new TextField();
        grid.add(isbnField, 1, 2);

        Label availableCopiesLabel = new Label("Available Copies:");
        grid.add(availableCopiesLabel, 0, 3);

        TextField availableCopiesField = new TextField();
        grid.add(availableCopiesField, 1, 3);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 1, 4);

        Button backButton = new Button("Back");
        grid.add(backButton, 0, 4);

        final Label messageLabel = new Label();
        grid.add(messageLabel, 1, 5);

        saveButton.setOnAction(event -> {
            try {
                int bookId = getNextBookId();
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                int availableCopies = Integer.parseInt(availableCopiesField.getText());

                boolean success = saveBook(bookId, title, author, isbn, availableCopies);

                if (success) {
                    messageLabel.setText("Book saved successfully!");
                } else {
                    messageLabel.setText("Failed to save book. Please try again.");
                }
            } catch (Exception e) {
                messageLabel.setText("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });

        backButton.setOnAction(event -> {
            primaryStage.close();
            openMenuAdminWindow();
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int getNextBookId() {
        int maxBookId = 0;
        try {
            URL url = new URL("http://localhost:8080/api/books");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
                int bookId = book.getInt("book_id");
                if (bookId > maxBookId) {
                    maxBookId = bookId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxBookId + 1;
    }

    private boolean saveBook(int bookId, String title, String author, String isbn, int availableCopies) {
        try {
            URL url = new URL("http://localhost:8080/api/books");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JSONObject book = new JSONObject();
            book.put("book_id", bookId);
            book.put("title", title);
            book.put("author", author);
            book.put("isbn", isbn);
            book.put("available_copies", availableCopies);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = book.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openMenuAdminWindow() {
        MenuAdmin menuAdmin = new MenuAdmin();
        Stage menuAdminStage = new Stage();
        menuAdmin.start(menuAdminStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
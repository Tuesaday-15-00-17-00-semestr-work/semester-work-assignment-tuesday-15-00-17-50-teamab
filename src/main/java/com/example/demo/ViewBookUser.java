package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class ViewBookUser extends Application {

    private TableView<Book> table = new TableView<>();
    private ObservableList<Book> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("View Books");

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(150);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setMinWidth(150);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, Integer> copiesColumn = new TableColumn<>("Available Copies");
        copiesColumn.setMinWidth(100);
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));

        table.setItems(data);
        table.getColumns().addAll(titleColumn, authorColumn, isbnColumn, copiesColumn);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            primaryStage.close();
            openMenuUserWindow(); // Always open MenuAdmin
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, backButton);

        Scene scene = new Scene(vbox, 600, 400); // Adjusted width for new columns
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
                String author = book.getString("author");
                String isbn = book.getString("isbn");
                int availableCopies = book.getInt("available_copies");

                data.add(new Book(title, author, isbn, availableCopies));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMenuUserWindow() {
        MenuUser menuUser = new MenuUser();
        Stage menuAdminStage = new Stage();
        menuUser.start(menuAdminStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Book {
        private String title;
        private String author;
        private String isbn;
        private int availableCopies;

        public Book(String title, String author, String isbn, int availableCopies) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.availableCopies = availableCopies;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getAvailableCopies() {
            return availableCopies;
        }
    }
}
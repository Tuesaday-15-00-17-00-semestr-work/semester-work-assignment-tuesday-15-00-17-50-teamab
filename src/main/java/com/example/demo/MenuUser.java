package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuUser extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Menu");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Button addBookButton = new Button("Add Book");
        grid.add(addBookButton, 0, 0);

        Button findAllBooksButton = new Button("View Books");
        grid.add(findAllBooksButton, 1, 0);

        Button deleteBookButton = new Button("Delete Book");
        grid.add(deleteBookButton, 0, 1);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 1, 1);

        // Set actions for buttons
        addBookButton.setOnAction(event -> {
            primaryStage.close();
            openAddBookWindow();
        });

        findAllBooksButton.setOnAction(event -> {
            primaryStage.close();
            openViewBookWindow();
        });

        deleteBookButton.setOnAction(event -> {
            primaryStage.close();
            openDeleteBookWindow();
        });

        exitButton.setOnAction(event -> {
            primaryStage.close();
            openLoginWindow();
        });

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            openLoginWindow();
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddBookWindow() {
        AddBookUser addBookUser = new AddBookUser();
        Stage addBookStage = new Stage();
        addBookUser.start(addBookStage);
    }

    private void openViewBookWindow() {
        ViewBookUser viewBook = new ViewBookUser();
        Stage viewBookStage = new Stage();
        viewBook.start(viewBookStage);
    }

    private void openDeleteBookWindow() {
        DeleteBookUser deleteBookUser = new DeleteBookUser();
        Stage deleteBookStage = new Stage();
        deleteBookUser.start(deleteBookStage);
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
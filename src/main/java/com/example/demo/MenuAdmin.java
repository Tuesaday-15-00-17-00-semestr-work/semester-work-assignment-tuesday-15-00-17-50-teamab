package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Menu");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Button addBookButton = new Button("Add Book");
        grid.add(addBookButton, 0, 0);

        Button findAllBooksButton = new Button("View books");
        grid.add(findAllBooksButton, 1, 0);

        Button deleteBookButton = new Button("Delete Book");
        grid.add(deleteBookButton, 0, 1);

        Button addUserButton = new Button("Add User");
        grid.add(addUserButton, 1, 1);

        Button deleteUserButton = new Button("Delete User");
        grid.add(deleteUserButton, 0, 2);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 1, 2);

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
            openDeleteBookAdminWindow();
        });

        addUserButton.setOnAction(event -> {
            primaryStage.close();
            openAddUser();
        });

        deleteUserButton.setOnAction(event -> {
            primaryStage.close();
            openDeleteUserWindow();
        });

        exitButton.setOnAction(event -> {
            primaryStage.close();
            openLoginWindow();
        });

        // Event handler for manual window close
        primaryStage.setOnCloseRequest(event -> {
            openLoginWindow();
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddBookWindow() {
        AddBookAdmin addBookAdmin = new AddBookAdmin();
        Stage addBookStage = new Stage();
        addBookAdmin.start(addBookStage);
    }

    private void openViewBookWindow() {
        ViewBookAdmin viewBook = new ViewBookAdmin();
        Stage viewBookStage = new Stage();
        viewBook.start(viewBookStage);
    }

    private void openDeleteBookAdminWindow() {
        DeleteBookAdmin deleteBookAdmin = new DeleteBookAdmin();
        Stage deleteBookAdminStage = new Stage();
        deleteBookAdmin.start(deleteBookAdminStage);
    }

    private void openAddUser() {
        AddUser addUser = new AddUser();
        Stage addUserStage = new Stage();
        addUser.start(addUserStage);
    }

    private void openDeleteUserWindow() {
        DeleteUser deleteUser = new DeleteUser();
        Stage deleteUserStage = new Stage();
        deleteUser.start(deleteUserStage);
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
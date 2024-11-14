package org.example;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection conn;
    private ArrayList<User> users;
    private ArrayList<String> usernames;
    private ArrayList<Book> books;
    private ArrayList<String> booknames;

    public Database() {
        // Definovanie priečinka a názvu súboru pre databázu
        String dbDirectory = "database_files";
        String dbName = "studentlibrary.db";
        String fullDbPath = "jdbc:sqlite:" + dbDirectory + "/" + dbName;

        // Kontrola a vytvorenie priečinka, ak neexistuje
        File dbFolder = new File(dbDirectory);
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }

        try {
            // Pripojenie k SQLite databáze (vytvorí súbor v priečinku database_files, ak neexistuje)
            conn = DriverManager.getConnection(fullDbPath);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }

        users = new ArrayList<>();
        usernames = new ArrayList<>();
        books = new ArrayList<>();
        booknames = new ArrayList<>();

        getUsers();
        getBooks();
    }

    private void createTables() {
        // SQL príkazy na vytvorenie tabuliek
        String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "name TEXT, nickname TEXT, password TEXT, role TEXT)";
        String createBooksTable = "CREATE TABLE IF NOT EXISTS Books (" +
                "name TEXT, author TEXT)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO Users (name, nickname, password, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user instanceof Admin ? "Admin" : "NormalUser");
            pstmt.executeUpdate();

            users.add(user);
            usernames.add(user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login(String nickname, String password) {
        int n = -1;
        for (User user : users) {
            if (user.getNickname().equals(nickname) && user.getPassword().equals(password)) {
                n = users.indexOf(user);
                break;
            }
        }
        return n;
    }

    private void getUsers() {
        String sql = "SELECT * FROM Users";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String nickname = rs.getString("nickname");
                String password = rs.getString("password");
                String role = rs.getString("role");

                User user = role.equals("Admin") ? new Admin(name, nickname, password)
                        : new NormalUser(name, nickname, password);
                users.add(user);
                usernames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(int n) {
        return users.get(n);
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO Books (name, author) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());

            pstmt.executeUpdate();

            books.add(book);
            booknames.add(book.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBooks() {
        String sql = "SELECT * FROM Books";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book();
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));


                books.add(book);
                booknames.add(book.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public int getBook(String bookname) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equals(bookname)) {
                return i;
            }
        }
        return -1;
    }

    public void deleteBook(int index) {
        Book book = books.get(index);
        String sql = "DELETE FROM Books WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getName());
            pstmt.executeUpdate();

            books.remove(index);
            booknames.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllData() {
        String deleteUsers = "DELETE FROM Users";
        String deleteBooks = "DELETE FROM Books";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(deleteUsers);
            stmt.execute(deleteBooks);

            users.clear();
            usernames.clear();
            books.clear();
            booknames.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }
}

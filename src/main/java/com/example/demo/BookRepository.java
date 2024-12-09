package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import jakarta.annotation.*;
import org.springframework.util.Assert;
import java.util.*;

@Repository
public class BookRepository {

    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
    private final JdbcClient jdbcClient;

    public BookRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Book> findAllBooks() {
        return jdbcClient.sql("select * from book")
                .query(Book.class)
                .list();
    }

    public Optional<Book> findBookByTitle(String title){
        return jdbcClient.sql("SELECT book_id,title,author,isbn,available_copies FROM Book WHERE title = :title")
                .param("title", title)
                .query(Book.class)
                .optional();
    }

    public void addBook (Book book) {
        var updated = jdbcClient.sql("INSERT INTO Book (book_id,title,author,isbn,available_copies ) values(?,?,?,?,?)")
                .params(List.of(book.book_id(),book.title(),book.author(),book.isbn(),book.available_copies().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create book " + book.title());
    }

    public void deleteBook (String title) {
        var updated = jdbcClient.sql("delete from book where title = :title")
                .param("title", title)
                .update();

        Assert.state(updated == 1, "Failed to delete book " + title);
    }

    public int count(){ return jdbcClient.sql("select * from book").query().listOfRows().size();}
    public void saveAll(List<Book> books) {
        books.stream().forEach(this::addBook);
    }
}
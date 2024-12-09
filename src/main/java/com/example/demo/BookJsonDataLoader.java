package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class BookJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BookJsonDataLoader.class);
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;

    public BookJsonDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) throws Exception {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json")) {
                Books allBooks = objectMapper.readValue(inputStream, Books.class);
                log.info("Reading {} Books from JSON data and saving to in-memory collection.", allBooks.books().size());
                bookRepository.saveAll(allBooks.books());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }


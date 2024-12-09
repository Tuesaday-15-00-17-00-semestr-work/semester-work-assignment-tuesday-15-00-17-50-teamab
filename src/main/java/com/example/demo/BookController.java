package com.example.demo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    @GetMapping("/{title}")
    Book findBookByTitle(@PathVariable String title) {
        Optional<Book> book = bookRepository.findBookByTitle(title);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return book.get();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void addBook(@Valid @RequestBody Book book) {
        bookRepository.addBook(book);
    }


    @DeleteMapping("/{title}")
    void deleteBook(@PathVariable String title) {
        bookRepository.deleteBook(title);
    };
}
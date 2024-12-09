package com.example.demo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record Book(
        Integer book_id,
        @NotEmpty
        String title,
        @NotEmpty
        String author,
        @NotEmpty
        String isbn,
        @Positive
        Integer available_copies) {


}


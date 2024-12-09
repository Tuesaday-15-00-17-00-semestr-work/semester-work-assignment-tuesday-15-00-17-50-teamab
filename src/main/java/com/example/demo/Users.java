package com.example.demo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record Users (
        @Positive
        Integer userId,
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @Positive
        Integer roleId,
        @NotEmpty
        String email

        ){}


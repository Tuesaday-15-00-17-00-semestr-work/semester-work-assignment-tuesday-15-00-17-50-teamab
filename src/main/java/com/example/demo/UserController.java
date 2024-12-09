package com.example.demo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    List<Users> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @GetMapping("/{userId}")
    Users findUserById(@PathVariable String username) {
        Optional<Users> users = userRepository.findUserByUsername(username);
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users.get();
    }


    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void addUser(@Valid @RequestBody Users users) {
        userRepository.addUser(users);
    }

    @DeleteMapping("/{nickname}")
    void deleteUser(@PathVariable String nickname) {
        userRepository.deleteUser(nickname);
    };
}
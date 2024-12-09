package com.example.demo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/api/log")
public class LoginLogController {

    private final LoginLogRepository loginLogRepository;

    public LoginLogController(LoginLogRepository loginLogRepository) {
        this.loginLogRepository = loginLogRepository;
    }

    @GetMapping
    List<LoginLog> findAllLoginLogs() {
        return loginLogRepository.findAllLoginLogs();
    }

    @PostMapping("")
    void addLoginLog(@Valid @RequestBody LoginLog loginLog) {
        loginLogRepository.addLoginLog(loginLog);
    }


    @DeleteMapping("/{loggedUserId}")
    void deleteLoginLog(@PathVariable Integer loggedUserId) {
        loginLogRepository.deleteLoginLog(loggedUserId);
    };
}
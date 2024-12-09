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
public class UserJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserJsonDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) throws Exception {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
                Users_ allUsers_ = objectMapper.readValue(inputStream, Users_.class);
                log.info("Reading {} Users from JSON data and saving to in-memory collection.", allUsers_.users_().size());
                userRepository.saveAll(allUsers_.users_());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }


    }
}

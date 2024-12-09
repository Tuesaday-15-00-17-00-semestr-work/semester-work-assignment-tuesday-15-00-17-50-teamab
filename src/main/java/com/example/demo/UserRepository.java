package com.example.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.*;
@Repository
public class UserRepository  {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Users> findAllUsers() {
        return jdbcClient.sql("select * from users")
                .query(Users.class)
                .list();
    }

    public Optional<Users> findUserByUsername(String username){
        return jdbcClient.sql("SELECT userId,nickname,password,roleId,email FROM Book WHERE username = :username")
                .param("username", username)
                .query(Users.class)
                .optional();
    }

    public void addUser (Users users) {
        var updated = jdbcClient.sql("INSERT INTO Users (userId,username,password,roleId,email ) values(?,?,?,?,?)")
                .params(List.of(users.userId(),users.username(),users.password(),users.roleId(),users.email().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create user " + users.username());
    }

    public void deleteUser (String username) {
        var updated = jdbcClient.sql("delete from users where username = :username")
                .param("username", username)
                .update();

        Assert.state(updated == 1, "Failed to delete user " + username);
    }

    public int count(){ return jdbcClient.sql("select * from users").query().listOfRows().size();}
    public void saveAll(List<Users> users_) {
        users_.stream().forEach(this::addUser);
    }
}
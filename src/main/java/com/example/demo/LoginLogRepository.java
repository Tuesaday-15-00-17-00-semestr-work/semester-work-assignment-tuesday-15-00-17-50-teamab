package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.*;

@Repository
public class LoginLogRepository {

    private static final Logger log = LoggerFactory.getLogger(LoginLogRepository.class);
    private final JdbcClient jdbcClient;

    public LoginLogRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<LoginLog> findAllLoginLogs() {
        return jdbcClient.sql("select * from loginLog")
                .query(LoginLog.class)
                .list();
    }


    public void addLoginLog (LoginLog loginLog) {
        var updated = jdbcClient.sql("INSERT INTO LoginLog (loggedUserId, loggedUsername ) values(?,?)")
                .params(List.of(loginLog.loggedUserId(),loginLog.loggedUsername().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create log " + loginLog.loggedUsername());
    }

    public void deleteLoginLog (Integer loggedUserId) {
        var updated = jdbcClient.sql("delete from loginLog where loggedUserId = :loggedUserId")
                .param("loggedUserId", loggedUserId)
                .update();

        Assert.state(updated == 1, "Failed to delete log " + loggedUserId);
    }

    public int count(){ return jdbcClient.sql("select * from loginLog").query().listOfRows().size();}
    public void saveAll(List<LoginLog> loginLogs) {
        loginLogs.stream().forEach(this::addLoginLog);
    }
}

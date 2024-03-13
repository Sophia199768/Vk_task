package org.itmo.vk_task.service;

import org.itmo.vk_task.pojo.Log;
import org.itmo.vk_task.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {
    @Autowired
    private LogRepository repository;
    @Autowired
    private DateTimeFormatter formatter;

    public void createLog(String jwt, String statusCode) {
        var log = new Log();
        log.setDate(LocalDateTime.now().format(formatter));
        log.setStatusCode(statusCode);
        log.setUserToken(jwt);
        repository.save(log);
    }
}

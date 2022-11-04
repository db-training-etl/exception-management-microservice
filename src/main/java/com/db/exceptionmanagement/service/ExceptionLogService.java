package com.db.exceptionmanagement.service;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

@Service
public class ExceptionLogService {

    ExceptionLogRepository exceptionLogRepository;

    public ExceptionLogService(ExceptionLogRepository exceptionLogRepository) {
        this.exceptionLogRepository = exceptionLogRepository;
    }

    public List<ExceptionLog> findAll() {
        return exceptionLogRepository.findAll();
    }

    public Optional<ExceptionLog> findById(int id) { return exceptionLogRepository.findById(id); }

    public List<ExceptionLog> findByCobDate(String filter) throws ParseException, JsonProcessingException {

        JsonObject jsonDate = JsonParser.parseString(filter).getAsJsonObject();

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date cobDate = format.parse(jsonDate.get("cobDate").getAsString());

        return exceptionLogRepository.findByCobDate(cobDate);
    }

    public ExceptionLog save(ExceptionLog newExceptionLog) { return exceptionLogRepository.save(newExceptionLog); }



}

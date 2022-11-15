package com.db.exceptionmanagement.controller;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.db.exceptionmanagement.service.ExceptionLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/exceptions")
public class ExceptionLogController {

    private final ExceptionLogService exceptionLogService;
    private final ExceptionLogRepository exceptionLogRepository;

    public ExceptionLogController(ExceptionLogService exceptionLogService, ExceptionLogRepository exceptionLogRepository) {
        this.exceptionLogService = exceptionLogService;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    @PostMapping(path="")
    public @ResponseBody ResponseEntity<ExceptionLog> addNewExceptionLog (@RequestBody ExceptionLog newExceptionLog) {

        exceptionLogRepository.save(newExceptionLog);

        return new ResponseEntity(newExceptionLog, HttpStatus.OK);

    }

    @GetMapping(path="")
    public @ResponseBody List<ExceptionLog> getAllExceptionLogs() {
        return exceptionLogService.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<ExceptionLog> getExceptionLogById(@PathVariable int id) {
        return exceptionLogService.findById(id);
    }

    @PostMapping(path="/filter")
    public @ResponseBody List<ExceptionLog> getExceptionLogsByDate(@RequestBody String cobDate) throws ParseException, JsonProcessingException {
        return exceptionLogService.findByCobDate(cobDate);
    }

}

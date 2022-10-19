package com.db.exceptionmanagement.controller;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.db.exceptionmanagement.service.ExceptionLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path="/exceptions")
public class ExceptionController {

    private final ExceptionLogService exceptionLogService;
    private final ExceptionLogRepository exceptionLogRepository;

    public ExceptionController(ExceptionLogService exceptionLogService, ExceptionLogRepository exceptionLogRepository) {
        this.exceptionLogService = exceptionLogService;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<ExceptionLog> addNewExceptionLog (@RequestBody ExceptionLog newExceptionLog) {

        exceptionLogRepository.save(newExceptionLog);

        return new ResponseEntity<ExceptionLog>(newExceptionLog, HttpStatus.OK);

    }

    @GetMapping(path="/all")
    public @ResponseBody List<ExceptionLog> getAllExceptionLogs() {
        // This returns a JSON or XML with the exceptions
        return exceptionLogService.findAll();
    }

}

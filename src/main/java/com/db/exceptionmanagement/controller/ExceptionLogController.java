package com.db.exceptionmanagement.controller;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.db.exceptionmanagement.service.ExceptionLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    //displayExceptionsForCobDate is missing until further information about data format is achieved

}

package com.db.exceptionmanagement.service;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ExceptionLog save(ExceptionLog newExceptionLog) { return exceptionLogRepository.save(newExceptionLog); }



}

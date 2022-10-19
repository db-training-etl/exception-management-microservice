package com.db.exceptionmanagement.service;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ExceptionLogServiceTest {

    ExceptionLogRepository exceptionLogRepository;

    ExceptionLogService exceptionLogService;

    @BeforeEach
    public void setup(){
        exceptionLogRepository = mock(ExceptionLogRepository.class);
        exceptionLogService = new ExceptionLogService(exceptionLogRepository);
    }

    @Test
    public void findAll_Test(){

        List<ExceptionLog> mockableLogs = new ArrayList<>();

        mockableLogs.add(new ExceptionLog());

        given(exceptionLogRepository.findAll()).willReturn(mockableLogs);

        assertEquals(mockableLogs, exceptionLogService.findAll());
    }

    @Test
    public void save_Test(){

        ExceptionLog exceptionLogMock = new ExceptionLog();

        ResponseEntity<ExceptionLog> response = new ResponseEntity<ExceptionLog>(exceptionLogMock, HttpStatus.OK);

        given(exceptionLogRepository.save(exceptionLogMock)).willAnswer((invocation) -> invocation.getArgument(0));

        assertEquals(exceptionLogMock, exceptionLogService.save(exceptionLogMock));
    }

}

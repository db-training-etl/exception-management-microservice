package com.db.exceptionmanagement.controller;

import static org.mockito.Mockito.mock;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.db.exceptionmanagement.service.ExceptionLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ExceptionLogControllerTest {

    ExceptionController exceptionController;

    ExceptionLogService exceptionLogService;

    ExceptionLogRepository exceptionLogRepository;

    @BeforeEach
    void setUp() {
        exceptionLogService = mock(ExceptionLogService.class);
        exceptionLogRepository = mock(ExceptionLogRepository.class);
        exceptionController = new ExceptionController(exceptionLogService, exceptionLogRepository);
    }

    @Test
    public void findAllReturnNormally(){

        ExceptionLog exceptionLogMock = new ExceptionLog();
        exceptionLogMock.setId(232);
        exceptionLogMock.setName("nombre");
        exceptionLogMock.setType("tipo");
        exceptionLogMock.setMessage("bachata");
        exceptionLogMock.setTrace("trasado");
        exceptionLogMock.setCobDate(new Date(1999,1,9));

        List<ExceptionLog> resultMock = new ArrayList<>();

        resultMock.add(exceptionLogMock);

        given(exceptionLogService.findAll()).willReturn(resultMock);

        assertEquals(resultMock, exceptionController.getAllExceptionLogs());
    }

    @Test
    public void AddNewExceptionLogToDB(){

        ExceptionLog exceptionLogMock = new ExceptionLog();

        ResponseEntity<ExceptionLog> response = new ResponseEntity<ExceptionLog>(exceptionLogMock, HttpStatus.OK);

        given(exceptionLogRepository.save(exceptionLogMock)).willAnswer((invocation) -> invocation.getArgument(0));

        assertEquals(response, exceptionController.addNewExceptionLog(exceptionLogMock));
    }
}

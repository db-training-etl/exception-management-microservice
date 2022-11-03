package com.db.exceptionmanagement.controller;

import static org.mockito.Mockito.mock;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.db.exceptionmanagement.service.ExceptionLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExceptionLogControllerUT {

    ExceptionLogController exceptionLogController;

    ExceptionLogService exceptionLogService;

    ExceptionLogRepository exceptionLogRepository;

    List<ExceptionLog> exceptionLogs;


    @BeforeEach
    void setUp() {
        exceptionLogService = mock(ExceptionLogService.class);
        exceptionLogRepository = mock(ExceptionLogRepository.class);
        exceptionLogController = new ExceptionLogController(exceptionLogService, exceptionLogRepository);
    }

    @Test
    public void findAllLogsTest(){

        ExceptionLog exceptionLogMock = new ExceptionLog();
        exceptionLogMock.setId(232);
        exceptionLogMock.setName("nombre");
        exceptionLogMock.setType("tipo");
        exceptionLogMock.setMessage("bachata");
        exceptionLogMock.setTrace("trasado");
        exceptionLogMock.setCobDate(LocalDateTime.now());

        List<ExceptionLog> resultMock = new ArrayList<>();

        resultMock.add(exceptionLogMock);

        given(exceptionLogService.findAll()).willReturn(resultMock);

        assertEquals(resultMock, exceptionLogController.getAllExceptionLogs());
    }

    @Test
    public void findLogByIdTest(){
        ExceptionLog exceptionLog = getExampleLogs(1,"AAA", "type1", "message1", "trace1", LocalDateTime.now());

        given(exceptionLogService.findById(1)).willReturn(Optional.ofNullable(exceptionLog));

        assertEquals(exceptionLog, exceptionLogController.getExceptionLogById(1).get());
    }
    @Test
    public void findLogByCobDateTest() throws ParseException, JsonProcessingException {
        ExceptionLog exceptionLogMock = new ExceptionLog();
        exceptionLogMock.setId(232);
        exceptionLogMock.setName("nombre");
        exceptionLogMock.setType("tipo");
        exceptionLogMock.setMessage("bachata");
        exceptionLogMock.setTrace("trasado");
        exceptionLogMock.setCobDate(LocalDateTime.now());

        List<ExceptionLog> resultMock = new ArrayList<>();

        resultMock.add(exceptionLogMock);
        given(exceptionLogService.findByCobDate(Date.from(Instant.now()).toString())).willReturn(resultMock);

        assertEquals(resultMock, exceptionLogController.getExceptionLogsByDate(Date.from(Instant.now()).toString()));
    }

    @Test
    public void postNewLogToExceptionDB(){

        ExceptionLog exceptionLogMock = new ExceptionLog();

        ResponseEntity<ExceptionLog> response = new ResponseEntity<ExceptionLog>(exceptionLogMock, HttpStatus.OK);

        given(exceptionLogRepository.save(exceptionLogMock)).willAnswer((invocation) -> invocation.getArgument(0));

        assertEquals(response, exceptionLogController.addNewExceptionLog(exceptionLogMock));
    }

    public void setExampleLogs(){
        exceptionLogs = new ArrayList<>();
        exceptionLogs.add(getExampleLogs(null,"AAA", "type1", "message1", "trace1", LocalDateTime.now()));
        exceptionLogs.add(getExampleLogs(null,"BBB", "type2", "message2", "trace1", LocalDateTime.now()));
        exceptionLogs.add(getExampleLogs(null,"CCC", "type3", "message3", "trace1", LocalDateTime.now()));
    }

    public ExceptionLog getExampleLogs(Integer id, String name, String type, String message, String trace, LocalDateTime cobDate){
        ExceptionLog excp = new ExceptionLog();
        excp.setId(id);
        excp.setName(name);
        excp.setType(type);
        excp.setMessage(message);
        excp.setTrace(trace);
        excp.setCobDate(cobDate);

        return excp;
    }

    public ExceptionLog getPostExampleLogs(String name, String type, String message, String trace, LocalDateTime cobDate){
        ExceptionLog excp = new ExceptionLog();
        excp.setName(name);
        excp.setType(type);
        excp.setMessage(message);
        excp.setTrace(trace);
        excp.setCobDate(cobDate);

        return excp;
    }
}

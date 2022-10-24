package com.db.exceptionmanagement.service;

import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class ExceptionLogServiceUT {

    ExceptionLogRepository exceptionLogRepository;

    ExceptionLogService exceptionLogService;

    List<ExceptionLog> exceptionLogs;

    @BeforeEach
    public void setup(){
        exceptionLogRepository = mock(ExceptionLogRepository.class);
        exceptionLogService = new ExceptionLogService(exceptionLogRepository);
    }

    @Test
    public void findAllTest(){

        List<ExceptionLog> mockableLogs = new ArrayList<>();

        mockableLogs.add(new ExceptionLog());

        given(exceptionLogRepository.findAll()).willReturn(mockableLogs);

        assertEquals(mockableLogs, exceptionLogService.findAll());
    }

    @Test
    public void findByIdTest(){
        ExceptionLog exceptionLog = getExampleLogs(1,"AAA", "type1", "message1", "trace1", new Date());

        given(exceptionLogRepository.findById(1)).willReturn(Optional.ofNullable(exceptionLog));

        assertEquals(exceptionLogService.findById(1).get(),exceptionLog);
    }

    @Test
    public void saveTest(){

        ExceptionLog exceptionLogMock = new ExceptionLog();

        ResponseEntity<ExceptionLog> response = new ResponseEntity<ExceptionLog>(exceptionLogMock, HttpStatus.OK);

        given(exceptionLogRepository.save(exceptionLogMock)).willAnswer((invocation) -> invocation.getArgument(0));

        assertEquals(exceptionLogMock, exceptionLogService.save(exceptionLogMock));
    }

    public void setExampleLogs(){
        exceptionLogs = new ArrayList<>();
        exceptionLogs.add(getExampleLogs(null,"AAA", "type1", "message1", "trace1", Date.from(Instant.now())));
        exceptionLogs.add(getExampleLogs(null,"BBB", "type2", "message2", "trace1", Date.from(Instant.now())));
        exceptionLogs.add(getExampleLogs(null,"CCC", "type3", "message3", "trace1", Date.from(Instant.now())));
    }

    public ExceptionLog getExampleLogs(Integer id, String name, String type, String message, String trace, Date cobDate){
        ExceptionLog excp = new ExceptionLog();
        excp.setId(id);
        excp.setName(name);
        excp.setType(type);
        excp.setMessage(message);
        excp.setTrace(trace);
        excp.setCobDate(cobDate);

        return excp;
    }

    public ExceptionLog getPostExampleLogs(String name, String type, String message, String trace, Date cobDate){
        ExceptionLog excp = new ExceptionLog();
        excp.setName(name);
        excp.setType(type);
        excp.setMessage(message);
        excp.setTrace(trace);
        excp.setCobDate(cobDate);

        return excp;
    }

}

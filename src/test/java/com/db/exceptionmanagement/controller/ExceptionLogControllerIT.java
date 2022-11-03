package com.db.exceptionmanagement.controller;

import com.db.exceptionmanagement.ExceptionManagementApplication;
import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.service.ExceptionLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = ExceptionManagementApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExceptionLogControllerIT {

    @MockBean
    ExceptionLogService exceptionLogService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    List<ExceptionLog> exceptionLogs;

    @Test
    public void findOneLogByIdTest() throws Exception {
        ExceptionLog exceptionLog = getExampleLogs(1,"AAA", "type1", "message1", "trace1", LocalDateTime.now());

        given(exceptionLogService.findById(1)).willReturn(Optional.ofNullable(exceptionLog));

        ResultActions response = mockMvc.perform(get("/exceptions/1"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is("AAA")));

    }

    @Test
    public void findAllLogsTest() throws Exception {

        setExampleLogs();

        given(exceptionLogService.findAll()).willReturn(exceptionLogs);

        ResultActions response = mockMvc.perform(get("/exceptions"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$[0].name", is("AAA")));

    }

    @Test
    public void saveOneLogToDB() throws Exception{
        ExceptionLog exceptionLog = getPostExampleLogs("AAA", "type1", "message1", "trace1", LocalDateTime.now());

        given(exceptionLogService.save(exceptionLog)).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/exceptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exceptionLog)));

        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void findLogByCobDate() throws Exception {
        setExampleLogs();

        given(exceptionLogService.findByCobDate(any())).willReturn(exceptionLogs);

        ResultActions response = mockMvc.perform(post("/exceptions/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("{'cobDate' : "+exceptionLogs.get(0).getCobDate()+"}")));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$[0].name", is("AAA")));

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

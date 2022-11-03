package com.db.exceptionmanagement.service;


import com.db.exceptionmanagement.ExceptionManagementApplication;
import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest(classes = ExceptionManagementApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExceptionLogServiceIT {

    @MockBean
    ExceptionLogRepository exceptionLogRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    List<ExceptionLog> exceptionLogs;

    @Test
    public void findOneExceptionByIdTest() throws Exception{
        ExceptionLog excp = getExampleLogs(null,"AAA", "type1", "message1", "trace1", LocalDateTime.now());

        given(exceptionLogRepository.findById(1)).willReturn(Optional.ofNullable(excp));

        ResultActions response = mockMvc.perform(get("/exceptions/1"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is("AAA")));

    }

    @Test
    public void findAllExceptionLogsTest() throws Exception{

        setExampleLogs();

        given(exceptionLogRepository.findAll()).willReturn(exceptionLogs);

        ResultActions response = mockMvc.perform(get("/exceptions"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$[0].name", is("AAA")));

    }

    @Test
    public void saveOneExceptionLogTest() throws Exception{
        ExceptionLog exceptionLog = getPostExampleLogs("AAA", "type1", "message1", "trace1", LocalDateTime.now());

        given(exceptionLogRepository.save(exceptionLog)).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/exceptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exceptionLog)));

        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void findAllExceptionLogsByCobDateTest() throws Exception {

        setExampleLogs();

        given(exceptionLogRepository.findByCobDate(new Date())).willReturn(exceptionLogs);

        ResultActions response = mockMvc.perform(post("/exceptions/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'cobDate' : '"+exceptionLogs.get(0).getCobDate()+"'}"));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$[0].name", is("AAA")));

    }

    public void setExampleLogs() throws ParseException {

        /*
        Date cobDate = new Date();
        String stringOfCobDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(cobDate);
        cobDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringOfCobDate);
        */
        //TODO
        //LocalDateTime cobDate = LocalDateTime.parse(LocalDateTime.now().format(ISO_DATE_TIME));
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        //cobDate = LocalDateTime.parse(String.valueOf(cobDate), formatter);

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

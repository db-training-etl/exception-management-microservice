package com.db.exceptionmanagement.service;


import com.db.exceptionmanagement.ExceptionManagementApplication;
import com.db.exceptionmanagement.entity.ExceptionLog;
import com.db.exceptionmanagement.repository.ExceptionLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        ExceptionLog excp = getExampleLogs(null,"AAA", "type1", "message1", "trace1", Date.from(Instant.now()));

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
        ExceptionLog exceptionLog = getPostExampleLogs("AAA", "type1", "message1", "trace1", Date.from(Instant.now()));

        given(exceptionLogRepository.save(exceptionLog)).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/exceptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exceptionLog)));

        response.andExpect(status().isOk()).andDo(print());
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

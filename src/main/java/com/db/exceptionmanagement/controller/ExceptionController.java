package com.db.exceptionmanagement.controller;

import com.db.exceptionmanagement.entity.Exception;
import com.db.exceptionmanagement.repository.ExceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
@RequestMapping(path="/exceptions")
public class ExceptionController {
    @Autowired
    private ExceptionRepository exceptionRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewExceptionLog (@RequestParam String name,
                                                    @RequestParam String type,
                                                    @RequestParam String message,
                                                    @RequestParam String trace,
                                                    @RequestParam Date cobDate) {
        // @ResponseBody means the returned String is the response, not a view name

        Exception newException = new Exception();
        newException.setName(name);
        newException.setType(type);
        newException.setMessage(message);
        newException.setTrace(trace);
        newException.setCobDate(cobDate);
        exceptionRepository.save(newException);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Exception> getAllExceptionLogs() {
        // This returns a JSON or XML with the users
        return exceptionRepository.findAll();
    }

}

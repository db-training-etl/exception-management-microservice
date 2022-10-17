package com.db.exceptionmanagement.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Exception {

    Integer id;
    String exceptionName;
    String type;
    String message;
    String trace;
    Date cobDate;

}

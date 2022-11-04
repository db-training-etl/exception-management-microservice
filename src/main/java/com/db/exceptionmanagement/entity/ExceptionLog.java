package com.db.exceptionmanagement.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class ExceptionLog {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    String name;
    String type;
    String message;
    String trace;
    Date cobDate;

}

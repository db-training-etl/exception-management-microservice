package com.db.exceptionmanagement.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.db.exceptionmanagement.entity.ExceptionLog;

import java.util.List;
import java.util.Date;


public interface ExceptionLogRepository extends JpaRepository<ExceptionLog, Integer>{

    List<ExceptionLog> findByCobDate(Date cobDate);

}

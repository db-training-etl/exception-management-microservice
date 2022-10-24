package com.db.exceptionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.db.exceptionmanagement.entity.ExceptionLog;


public interface ExceptionLogRepository extends JpaRepository<ExceptionLog, Integer>{

}

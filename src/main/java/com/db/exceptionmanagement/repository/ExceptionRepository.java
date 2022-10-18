package com.db.exceptionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.db.exceptionmanagement.entity.Exception;


public interface ExceptionRepository extends JpaRepository<Exception, Integer>{

}

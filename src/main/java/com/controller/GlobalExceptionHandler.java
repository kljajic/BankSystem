package com.controller;

import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(DateTimeParseException.class)
	public void handleDateTimeParseException(HttpServletRequest req, Exception e){
		log.error("ERROR: "+req.getRequestURI()+", MESSAGE: "+e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletRequest req, Exception e){
		log.error("ERROR: "+req.getRequestURI()+", MESSAGE: "+e.getMessage());
	}
	
}

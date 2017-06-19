package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong date and time format.")
	@ExceptionHandler(DateTimeParseException.class)
	public void handleDateTimeParseException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong data format.")
	@ExceptionHandler(ParseException.class)
	public void handleParseException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "NullPointerException occured - unauthorized access.")
	@ExceptionHandler(NullPointerException.class)
	public void handleNullPointerException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not able to write to pdf.")
	@ExceptionHandler(JRException.class)
	public void handleJRException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Check database connection.")
	@ExceptionHandler(SQLException.class)
	public void handleSQLException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Input/output error occurred.")
	@ExceptionHandler(IOException.class)
	public void handleIOException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Exception occured.")
	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletRequest req, Exception e) {
		log.error("ERROR: " + req.getRequestURI() + ", MESSAGE: " + e.getMessage());
	}
	
}

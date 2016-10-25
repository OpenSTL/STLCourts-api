package svc.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	/* 
	 * This handler was created for SQL queries that are out of bounds, could be used for other purposes later
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFoundException(NotFoundException e){
		String msg = (e.getMessage() != "")?e.getMessage():"Result Not Found";
		return (msg);
	}
	
	
	/*
	 *  Catch all for unhandled exceptions.
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String handleGeneralExceptions(Exception e){
		return ("500: General Unhandled Exception");
	}
	
}
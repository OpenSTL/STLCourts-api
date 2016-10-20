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
	 * This handler was created for SQL queries that are out of bounds using 
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFoundException(NotFoundException e){
		return ("Result Not Found");
	}
	
	
	/*
	 * The GlobalExceptionHandler will catch all exceptions not caught in a Try/Catch
	 * Thus this is here to be a general catch all for unhandled exceptions.
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String handleGeneralExceptions(Exception e){
		return ("500: General Unhandled Exception");
	}
	
}
	

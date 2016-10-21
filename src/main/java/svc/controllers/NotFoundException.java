package svc.controllers;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;
	
	public NotFoundException(){
		super();
	}
	
	public NotFoundException(String message){
		super(message);
		this.message = message;
	}
	
	public NotFoundException(Throwable cause){
		super(cause);
	}
	
	public NotFoundException(String message, Throwable cause){
		super(message,cause);
		this.message = message;
	}
	
	@Override
	public String toString(){
		return message;
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
}

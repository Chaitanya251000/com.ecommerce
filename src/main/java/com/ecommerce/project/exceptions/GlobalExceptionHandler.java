package com.ecommerce.project.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> methodArgNotValid(MethodArgumentNotValidException ex){
		
		Map<String,String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(err ->{
			 String fieldName = ((FieldError)err).getField();
			 String msg = err.getDefaultMessage();
			 response.put(fieldName, msg);
		});
		
		return response;
	}

}

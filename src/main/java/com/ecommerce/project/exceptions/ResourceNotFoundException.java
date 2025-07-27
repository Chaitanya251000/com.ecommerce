package com.ecommerce.project.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	String resource;
	String field;
	String fieldName;
	Long fieldId;
	
	public ResourceNotFoundException(String resource, String field, String fieldName) {
		super(String.format("%s not found with %s : %s", resource,field,fieldName));
		this.resource = resource;
		this.field = field;
		this.fieldName = fieldName;
	}

	public ResourceNotFoundException(String resource, String field, Long fieldId) {
		super(String.format("%s not found with %s : %d", resource,field,fieldId));
		this.resource = resource;
		this.field = field;
		this.fieldId = fieldId;
	}
	
	public ResourceNotFoundException() {
		
	}
	
	
	

}

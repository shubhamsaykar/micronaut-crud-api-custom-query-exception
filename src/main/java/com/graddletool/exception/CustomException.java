package com.graddletool.exception;

public class CustomException extends RuntimeException {
	
	String name;
	String fieldName;
	int fieldValue;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}

	public CustomException(String name,String fieldname, int fieldValue) {
		super(String.format("%s not found with %s : %s",name,fieldname,fieldValue));
		this.fieldName=fieldname;
		this.name=name;
		this.fieldValue=fieldValue;
		
	}
	

}

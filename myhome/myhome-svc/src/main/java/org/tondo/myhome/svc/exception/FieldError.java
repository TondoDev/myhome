package org.tondo.myhome.svc.exception;

/**
 * 
 * @author TondoDev
 *
 */
public final class FieldError {

	private String field;
	private String errorCode;
	
	
	public FieldError(String field, String code) {
		this.field = field;
		this.errorCode = code;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getField() {
		return field;
	}
}

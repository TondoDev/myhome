package org.tondo.myhome.svc.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author TondoDev
 *
 */
public class BusinessValidationexception extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<FieldError> fieldErrors;
	
//	public BusinessValidationexception() {
//		this.fieldErrors = Collections.emptyList();
//	}
	
	public BusinessValidationexception(Collection<FieldError> errors) {
		this.fieldErrors = Collections.unmodifiableList(new ArrayList<>(errors));
	}
	
	
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
}

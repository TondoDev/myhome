package org.tondo.myhome.svc.exception;

import java.util.Collection;

/**
 * 
 * @author TondoDev
 *
 */
public class WaterValidationException extends BusinessValidationexception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WaterValidationException(Collection<FieldError> errors) {
		super(errors);
	}
}

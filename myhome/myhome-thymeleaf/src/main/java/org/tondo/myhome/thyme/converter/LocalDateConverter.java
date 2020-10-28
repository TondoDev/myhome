package org.tondo.myhome.thyme.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

/**
 * For parsing text from thymeleaf templates
 * @author TondoDev
 *
 */
public class LocalDateConverter implements Converter<String, LocalDate> {
	
	// this should be thread safe
	private DateTimeFormatter formatter;
	
	public LocalDateConverter(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public LocalDate convert(String source) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		
		
		return LocalDate.parse(source, this.formatter);
	}

}

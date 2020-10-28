package org.tondo.myhome.thyme.converter;

import java.text.DecimalFormat;

import org.springframework.core.convert.converter.Converter;

public class DoubleConverter implements Converter<Double, String> {
	

	@Override
	public String convert(Double source) {
		if (source == null) {
			return null;
		}
		
		return new DecimalFormat("#.###").format(source);
	}
	

}

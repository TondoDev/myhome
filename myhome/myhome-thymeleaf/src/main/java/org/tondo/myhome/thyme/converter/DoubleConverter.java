package org.tondo.myhome.thyme.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.core.convert.converter.Converter;

public class DoubleConverter implements Converter<Double, String> {
	

	@Override
	public String convert(Double source) {
		if (source == null) {
			return null;
		}
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		
		return new DecimalFormat("#.###", symbols).format(source);
	}
	

}

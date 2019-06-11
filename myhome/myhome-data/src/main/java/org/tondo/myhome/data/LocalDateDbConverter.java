package org.tondo.myhome.data;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// autoamticaly used for all LocalDate types
@Converter(autoApply=true)
public class LocalDateDbConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : Date.valueOf(attribute);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		return dbData == null ? null : dbData.toLocalDate();
	}

}

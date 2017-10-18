package org.tondo.myhome.enumsdom;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
@Table(name="LOCALIZEDTEXT")
public class LocalizedEnumText {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer letId;
	
	
	@ManyToOne
	private EnumVal enumValue;
	
	@Column
	private String text;

	public Integer getLetId() {
		return letId;
	}

	public void setLetId(Integer letId) {
		this.letId = letId;
	}

	public EnumVal getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(EnumVal enumValue) {
		this.enumValue = enumValue;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}

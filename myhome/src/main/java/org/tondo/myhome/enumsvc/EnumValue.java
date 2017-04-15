package org.tondo.myhome.enumsvc;

public class EnumValue {

	private Integer valueId;
	private Integer enumId;
	private String value;
	private String label;
	
	
	public EnumValue(Integer id, Integer enumId, String val, String lbl) {
		this.valueId = id;
		this.enumId = enumId;
		this.value = val;
		this.label = lbl;
	}
	
	public Integer getValueId() {
		return valueId;
	}
	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}
	public Integer getEnumId() {
		return enumId;
	}
	public void setEnumId(Integer enumId) {
		this.enumId = enumId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}

package org.tondo.myhome.enumsvc;

public class EnumName {

	private Integer enumId;
	private String name;
	private String valueType;
	
	
	public EnumName(Integer id, String name, String type) {
		this.enumId = id;
		this.name = name;
		this.valueType = type;
	}
	
	
	public Integer getEnumId() {
		return enumId;
	}
	
	public void setEnumId(Integer enumId) {
		this.enumId = enumId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValueType() {
		return valueType;
	}
	
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}

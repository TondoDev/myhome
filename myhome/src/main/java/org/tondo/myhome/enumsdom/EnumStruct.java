package org.tondo.myhome.enumsdom;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entity
@Table(name = "ENUMERATION")
public class EnumStruct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer eid;
	
	@Column(nullable = false)
	private String name;
	
	//************************//
	
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

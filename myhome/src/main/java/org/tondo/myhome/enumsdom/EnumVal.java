package org.tondo.myhome.enumsdom;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

//@Entity
@SecondaryTable(name = "LOCALIZEDTEXT",
		pkJoinColumns=@PrimaryKeyJoinColumn(name="enumValue")
		)
public class EnumVal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer evid;
	
	@ManyToOne
	private EnumStruct e;
	
	@Column(nullable = false)
	private Integer ordering;

	public Integer getEvid() {
		return evid;
	}

	public void setEvid(Integer evid) {
		this.evid = evid;
	}

	public EnumStruct getE() {
		return e;
	}

	public void setE(EnumStruct e) {
		this.e = e;
	}

	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
}

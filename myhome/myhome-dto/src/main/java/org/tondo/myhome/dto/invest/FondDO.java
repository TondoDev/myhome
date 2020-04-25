package org.tondo.myhome.dto.invest;
import javax.validation.constraints.NotNull;

import org.tondo.myhome.dto.invest.InvestmentBaseDO;

public class FondDO extends InvestmentBaseDO {

	@NotNull
	private String isin;
	
	
	public String getIsin() {
		return isin;
	}
	
	public void setIsin(String isin) {
		this.isin = isin;
	}
}

package org.tondo.myhome.dto.invest;

import java.util.List;

public class PortfolioSummaryDO {

	private List<FondValueDO> fonds;
	
	private FondValueDO total;
	
	
	public List<FondValueDO> getFonds() {
		return fonds;
	}
	
	public void setFonds(List<FondValueDO> fonds) {
		this.fonds = fonds;
	}
	
	public FondValueDO getTotal() {
		return total;
	}
	
	public void setTotal(FondValueDO total) {
		this.total = total;
	}
}

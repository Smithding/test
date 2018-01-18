package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

public class DemandTeamVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String demandNo;
	
	private String createType;

	public String getDemandNo() {
		return demandNo;
	}

	public void setDemandNo(String demandNo) {
		this.demandNo = demandNo;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	
}
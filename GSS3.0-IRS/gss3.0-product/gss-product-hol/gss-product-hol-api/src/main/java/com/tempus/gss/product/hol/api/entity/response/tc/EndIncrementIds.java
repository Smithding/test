package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.Date;

public class EndIncrementIds implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long endIncrementId;
	
	private Date incrTime;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEndIncrementId() {
		return endIncrementId;
	}

	public void setEndIncrementId(Long endIncrementId) {
		this.endIncrementId = endIncrementId;
	}

	public Date getIncrTime() {
		return incrTime;
	}

	public void setIncrTime(Date incrTime) {
		this.incrTime = incrTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

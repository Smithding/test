package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class QueryPnrAndTimeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long pnrNo;
	
	private Date start;
	
	private Date end;

	public Long getPnrNo() {
		return pnrNo;
	}

	public void setPnrNo(Long pnrNo) {
		this.pnrNo = pnrNo;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}

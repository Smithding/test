package com.tempus.gss.product.ift.api.entity.policy;

import java.io.Serializable;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;

public class IftOrderPrice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1460326585751479766L;
	private String policyId;
	private String openTicketFee;
	private String ticketDate;
	private List<PassengerTypePricesTotal> psgPolicyTotal;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getOpenTicketFee() {
		return openTicketFee;
	}

	public void setOpenTicketFee(String openTicketFee) {
		this.openTicketFee = openTicketFee;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}

	public List<PassengerTypePricesTotal> getPsgPolicyTotal() {
		return psgPolicyTotal;
	}

	public void setPsgPolicyTotal(List<PassengerTypePricesTotal> psgPolicyTotal) {
		this.psgPolicyTotal = psgPolicyTotal;
	}

}

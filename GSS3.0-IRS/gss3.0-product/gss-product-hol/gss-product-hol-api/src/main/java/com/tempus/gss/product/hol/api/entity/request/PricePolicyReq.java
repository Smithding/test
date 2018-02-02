package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.entity.PricePolicy;
import com.tempus.gss.product.hol.api.entity.request.BaseRequest;

public class PricePolicyReq extends BaseRequest implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * UBP编号
	 */
	@JSONField(name = "UBPCode")
	private String UBPCode;
	/**
	 * 全量供应商信息
	 */
	@JSONField(name = "policys")
	private List<PricePolicy> policys; 
	
	public String getUBPCode() {
		return UBPCode;
	}

	public void setUBPCode(String uBPCode) {
		UBPCode = uBPCode;
	}

	public List<PricePolicy> getPolicys() {
		return policys;
	}

	public void setPolicys(List<PricePolicy> policys) {
		this.policys = policys;
	}

	
}

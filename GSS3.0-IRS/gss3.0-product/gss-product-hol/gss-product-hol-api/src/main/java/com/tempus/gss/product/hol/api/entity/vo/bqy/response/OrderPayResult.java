package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderPayResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JSONField(name="Reseult")
	private boolean reseult;			//String	操作结果
	
	@JSONField(name="Msg")
	private String msg;				//String	消息
	
	@JSONField(name="IsChangePrice")
	private boolean isChangePrice;	//bool	是否变价
	
	@JSONField(name="PayPrice")
	private String payPrice;		//decimal	支付金额

	public boolean getReseult() {
		return reseult;
	}

	public void setReseult(boolean reseult) {
		this.reseult = reseult;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getIsChangePrice() {
		return isChangePrice;
	}

	public void setIsChangePrice(boolean isChangePrice) {
		this.isChangePrice = isChangePrice;
	}

	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}

}

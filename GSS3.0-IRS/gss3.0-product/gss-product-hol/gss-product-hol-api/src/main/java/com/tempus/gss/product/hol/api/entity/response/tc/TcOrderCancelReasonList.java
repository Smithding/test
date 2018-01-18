package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 同程订单取消原因集合
 * @author kai.yang
 *
 */
public class TcOrderCancelReasonList implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 取消原因
	 */
	@JSONField(name = "CancelReasonList")
	private List<CancelReasonModel> cancelReasonList;
	/**
	 * 提示信息
	 */
	@JSONField(name = "Msg")
	private String msg;
	public List<CancelReasonModel> getCancelReasonList() {
		return cancelReasonList;
	}
	public void setCancelReasonList(List<CancelReasonModel> cancelReasonList) {
		this.cancelReasonList = cancelReasonList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}

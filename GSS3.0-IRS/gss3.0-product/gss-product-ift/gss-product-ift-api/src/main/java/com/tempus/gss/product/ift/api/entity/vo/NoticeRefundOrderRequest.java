package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;
/**
 * 
 * ClassName:NoticeRefundOrderRequest
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 * @author   liuyulong
 * @version  
 * @since    Ver 1.1
 * @Date	 2017	2017年8月11日		上午10:20:10
 * @see 	 
 *
 */
public class NoticeRefundOrderRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String orderId;//退票订单号
	
	

	private String errorinfo;//挂起原因	
	
	

	private String status;//状态


	public String getErrorinfo() {
		return errorinfo;
	}


	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}


	public String getStatus() {
		return status;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "NoticeRefundOrderRequest [orderId=" + orderId + ", errorinfo=" + errorinfo + ", status=" + status + "]";
	}


	
}

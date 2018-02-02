package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 可定检查响应参数
 * @author kai.yang
 *
 */
public class IsBookOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 是否可订,  1：可订,  0：不可订
	 */
	@JSONField(name = "CanBooking")
	private Integer canBooking;
	
	/**
	 * 可订检查错误消息
	 */
	@JSONField(name = "ErrorMessage")
	private String errorMessage;
	/**
	 * 可订检查接口返回的数据
	 */
	@JSONField(name = "BookableMessage")
	private String bookableMessage;
	
	/**
	 * 真实返回对象数据
	 */
	@JSONField(name = "BookableMessageTarget")
	private BookableMessage bookableMessageTarget;
	
	/**
	 * 可订检查失败原因
	 */
	@JSONField(name = "BookableFailure")
	private List<BookableFailure> bookableFailure;
	/**
	 * 详细的银行卡信息
	 */
	@JSONField(name = "PaymentWayList")
	private List<PaymentWay> paymentWayList;
	/**
	 * 最晚到店时间验证
	 */
	@JSONField(name = "LastestArriveTimeConfirm")
	private Boolean lastestArriveTimeConfirm;
	/**
	 * 自定义返回可定说明信息
	 */
	private String selfMessage;
	
	public Integer getCanBooking() {
		return canBooking;
	}
	public void setCanBooking(Integer canBooking) {
		this.canBooking = canBooking;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<BookableFailure> getBookableFailure() {
		return bookableFailure;
	}
	public void setBookableFailure(List<BookableFailure> bookableFailure) {
		this.bookableFailure = bookableFailure;
	}
	/*public BookableMessage getBookableMessage() {
		return bookableMessage;
	}
	public void setBookableMessage(BookableMessage bookableMessage) {
		this.bookableMessage = bookableMessage;
	}*/
	public String getBookableMessage() {
		return bookableMessage;
	}
	public void setBookableMessage(String bookableMessage) {
		this.bookableMessage = bookableMessage;
	}
	public BookableMessage getBookableMessageTarget() {
		return bookableMessageTarget;
	}
	
	public void setBookableMessageTarget(BookableMessage bookableMessageTarget) {
		this.bookableMessageTarget = bookableMessageTarget;
	}
	public List<PaymentWay> getPaymentWayList() {
		return paymentWayList;
	}
	public void setPaymentWayList(List<PaymentWay> paymentWayList) {
		this.paymentWayList = paymentWayList;
	}
	public Boolean getLastestArriveTimeConfirm() {
		return lastestArriveTimeConfirm;
	}
	public void setLastestArriveTimeConfirm(Boolean lastestArriveTimeConfirm) {
		this.lastestArriveTimeConfirm = lastestArriveTimeConfirm;
	}
	public String getSelfMessage() {
		return selfMessage;
	}
	@JSONField(deserialize=false)
	public void setSelfMessage(String selfMessage) {
		this.selfMessage = selfMessage;
	}
	
}

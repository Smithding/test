package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 可订检查接口返回的数据
 * @author kai.yang
 *
 */
public class BookableMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 可定接口里的每天卖价(币种为外币时，是外币价格)
	 */
	@JSONField(name = "AmountArr")
	private String[] amountArr;
	
	/**
	 * 可定接口里的每天卖价(人民币价格)
	 */
	@JSONField(name = "AmountArrOther")
	private String[] amountArrOther;
	
	/**
	 * 封装担保，罚金，取消时间信息
	 */
	@JSONField(name = "CtripGuaranteeInfo")
	private CtripGuaranteeInfo ctripGuaranteeInfo;
	
	/**
	 * 房间剩余数量
	 */
	@JSONField(name = "ExcessRooms")
	private Integer excessRooms;
	
	/**
	 * 是否接受自定义下单备注
	 */
	@JSONField(name = "ReceiveTextRemark")
	private Boolean receiveTextRemark;
	
	/**
	 * 最早到店时间
	 */
	@JSONField(name = "LastArraiveDateTime")
	private Date lastArraiveDateTime;
	
	/**
	 * 最早到店限制时间（东八区北京时间）
	 */
	@JSONField(name = "LimitEarliestArrivalTime")
	private Date limitEarliestArrivalTime;
	/**
	 * 最晚到店时间限制（东八区北京时间）
	 */
	@JSONField(name = "LimitLatestArrivalTime")
	private Date limitLatestArrivalTime;
	/**
	 * 特殊备注列表
	 */
	@JSONField(name = "SpecialRequestOptions")
	private List<SpecialRequestOptions> specialRequestOptions;
	public CtripGuaranteeInfo getCtripGuaranteeInfo() {
		return ctripGuaranteeInfo;
	}
	public void setCtripGuaranteeInfo(CtripGuaranteeInfo ctripGuaranteeInfo) {
		this.ctripGuaranteeInfo = ctripGuaranteeInfo;
	}
	public Integer getExcessRooms() {
		return excessRooms;
	}
	public void setExcessRooms(Integer excessRooms) {
		this.excessRooms = excessRooms;
	}
	public Boolean getReceiveTextRemark() {
		return receiveTextRemark;
	}
	public void setReceiveTextRemark(Boolean receiveTextRemark) {
		this.receiveTextRemark = receiveTextRemark;
	}
	public Date getLastArraiveDateTime() {
		return lastArraiveDateTime;
	}
	public void setLastArraiveDateTime(Date lastArraiveDateTime) {
		this.lastArraiveDateTime = lastArraiveDateTime;
	}
	public Date getLimitEarliestArrivalTime() {
		return limitEarliestArrivalTime;
	}
	public void setLimitEarliestArrivalTime(Date limitEarliestArrivalTime) {
		this.limitEarliestArrivalTime = limitEarliestArrivalTime;
	}
	public Date getLimitLatestArrivalTime() {
		return limitLatestArrivalTime;
	}
	public void setLimitLatestArrivalTime(Date limitLatestArrivalTime) {
		this.limitLatestArrivalTime = limitLatestArrivalTime;
	}
	public List<SpecialRequestOptions> getSpecialRequestOptions() {
		return specialRequestOptions;
	}
	public void setSpecialRequestOptions(List<SpecialRequestOptions> specialRequestOptions) {
		this.specialRequestOptions = specialRequestOptions;
	}	
	
}

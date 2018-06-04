package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
	@JSONField(name = "LastArraiveDateTime"/*, format="yyyy-MM-dd HH:mm:ss"*/)
	private String lastArraiveDateTime;
	
	/**
	 * 最早到店限制时间（东八区北京时间）
	 */
	@JSONField(name = "LimitEarliestArrivalTime"/*, format="yyyy-MM-dd HH:mm:ss"*/)
	private String limitEarliestArrivalTime;
	/**
	 * 最晚到店时间限制（东八区北京时间）
	 */
	@JSONField(name = "LimitLatestArrivalTime"/*, format="yyyy-MM-dd HH:mm:ss"*/)
	private String limitLatestArrivalTime;
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
	public List<SpecialRequestOptions> getSpecialRequestOptions() {
		return specialRequestOptions;
	}
	public void setSpecialRequestOptions(List<SpecialRequestOptions> specialRequestOptions) {
		this.specialRequestOptions = specialRequestOptions;
	}
	public String[] getAmountArr() {
		return amountArr;
	}
	public void setAmountArr(String[] amountArr) {
		this.amountArr = amountArr;
	}
	public String[] getAmountArrOther() {
		return amountArrOther;
	}
	public void setAmountArrOther(String[] amountArrOther) {
		this.amountArrOther = amountArrOther;
	}
	public String getLastArraiveDateTime() {
		return lastArraiveDateTime;
	}
	public void setLastArraiveDateTime(String lastArraiveDateTime) {
		if(StringUtils.isNotEmpty(lastArraiveDateTime)) {
			lastArraiveDateTime = lastArraiveDateTime.replace("T", " ").substring(0, 19);
		}
		this.lastArraiveDateTime = lastArraiveDateTime;
	}
	public String getLimitEarliestArrivalTime() {
		return limitEarliestArrivalTime;
	}
	public void setLimitEarliestArrivalTime(String limitEarliestArrivalTime) {
		if(StringUtils.isNotEmpty(limitEarliestArrivalTime)) {
			limitEarliestArrivalTime = limitEarliestArrivalTime.replace("T", " ").substring(0, 19);
		}
		this.limitEarliestArrivalTime = limitEarliestArrivalTime;
	}
	public String getLimitLatestArrivalTime() {
		return limitLatestArrivalTime;
	}
	public void setLimitLatestArrivalTime(String limitLatestArrivalTime) {
		if(StringUtils.isNotEmpty(limitLatestArrivalTime)) {
			limitLatestArrivalTime = limitLatestArrivalTime.replace("T", " ").substring(0, 19);
		}
		this.limitLatestArrivalTime = limitLatestArrivalTime;
	}	
	
	
	
	
}

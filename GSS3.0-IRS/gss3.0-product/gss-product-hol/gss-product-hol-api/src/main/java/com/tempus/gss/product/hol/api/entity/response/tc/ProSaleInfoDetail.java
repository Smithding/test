package com.tempus.gss.product.hol.api.entity.response.tc;

import java.io.Serializable;
import java.util.List;


import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.util.DateUtil;
/**
 * 日期所对应的房型剩余库存量,价格详情
 * @author kai.yang
 *
 */
public class ProSaleInfoDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 分销商卖价
	 */
	@JSONField(name = "DistributionSalePrice")
	private Integer distributionSalePrice;
	/**
	 * 库存剩余数量
	 */
	@JSONField(name = "InventoryRemainder")
	private Integer inventoryRemainder;
	/**
	 * 库存状态（0：即时，1：正常，4：不可售）
	 */
	@JSONField(name = "InventoryStats")
	private Integer inventoryStats;
	/**
	 * 是否开放售卖：true，开放售卖；false，限量售卖
	 */
	@JSONField(name = "OpeningSale")
	private Boolean openingSale;
	/**
	 * 同程卖价
	 */
	@JSONField(name = "TcDirectPrice")
	private Integer tcDirectPrice;
	/**
	 * 早餐名称
	 */
	@JSONField(name = "BreakfastName")
	private String breakfastName;
	/**
	 * 早餐数量
	 */
	@JSONField(name = "BreakfastNum")
	private Integer breakfastNum;
	/**
	 * 加床价
	 */
	@JSONField(name = "AddBedAmount")
	private Integer addBedAmount;
	/**
	 * 最 晚 预 订 时 间 （ 默 认 时 间1900-01-01）
	 */
	@JSONField(name = "LastReserveTime")
	private String lastReserveTime;
	/**
	 * 担保类型：0-不担保，1-峰时担保；2-全额担保；4-超时担保；8-一律担保；
	 */
	@JSONField(name = "GuaranteeCode")
	private Integer guaranteeCode;
	/**
	 * GuaranteeCode 中有“4”时，在此时 间 之 前 不 需 要 担 保 ， 类 似“22:00:00”
	 */
	@JSONField(name = "GuaranteeHoldTime")
	private String guaranteeHoldTime;
	/**
	 * 取消规则
	 */
	@JSONField(name = "CancelRules")
	private List<CancelRule> cancelRules;
	public Integer getDistributionSalePrice() {
		return distributionSalePrice;
	}
	public void setDistributionSalePrice(Integer distributionSalePrice) {
		this.distributionSalePrice = distributionSalePrice;
	}
	public Integer getInventoryRemainder() {
		return inventoryRemainder;
	}
	public void setInventoryRemainder(Integer inventoryRemainder) {
		this.inventoryRemainder = inventoryRemainder;
	}
	public Integer getInventoryStats() {
		return inventoryStats;
	}
	public void setInventoryStats(Integer inventoryStats) {
		this.inventoryStats = inventoryStats;
	}
	public Boolean getOpeningSale() {
		return openingSale;
	}
	public void setOpeningSale(Boolean openingSale) {
		this.openingSale = openingSale;
	}
	public Integer getTcDirectPrice() {
		return tcDirectPrice;
	}
	public void setTcDirectPrice(Integer tcDirectPrice) {
		this.tcDirectPrice = tcDirectPrice;
	}
	public String getBreakfastName() {
		return breakfastName;
	}
	public void setBreakfastName(String breakfastName) {
		this.breakfastName = breakfastName;
	}
	public Integer getBreakfastNum() {
		return breakfastNum;
	}
	public void setBreakfastNum(Integer breakfastNum) {
		this.breakfastNum = breakfastNum;
	}
	public Integer getAddBedAmount() {
		return addBedAmount;
	}
	public void setAddBedAmount(Integer addBedAmount) {
		this.addBedAmount = addBedAmount;
	}
	public String getLastReserveTime() {
		return lastReserveTime;
	}
	public void setLastReserveTime(String lastReserveTime) {
		lastReserveTime= DateUtil.stringToStrDate(lastReserveTime);
		this.lastReserveTime = lastReserveTime;
	}
	public Integer getGuaranteeCode() {
		return guaranteeCode;
	}
	public void setGuaranteeCode(Integer guaranteeCode) {
		this.guaranteeCode = guaranteeCode;
	}
	public String getGuaranteeHoldTime() {
		return guaranteeHoldTime;
	}
	public void setGuaranteeHoldTime(String guaranteeHoldTime) {
		this.guaranteeHoldTime = guaranteeHoldTime;
	}
	public List<CancelRule> getCancelRules() {
		return cancelRules;
	}
	public void setCancelRules(List<CancelRule> cancelRules) {
		this.cancelRules = cancelRules;
	}
	
	
}

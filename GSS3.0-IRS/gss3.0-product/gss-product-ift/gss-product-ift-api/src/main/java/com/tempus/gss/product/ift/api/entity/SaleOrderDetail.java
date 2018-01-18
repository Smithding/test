package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售营销，表人+航段的组合.
 */
public class SaleOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 销售明细编号 销售明细编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderDetailNo;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 数据归属单位
	 */
	private Integer owner;
	/**
	 * 销售订单编号 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 乘客编号 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;
	/**
	 * 乘客
	 */
	private Passenger passenger;
	/**
	 * 航程编号 航程编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long legNo;
	/**
	 * 航程
	 */
	private Leg leg;
	/**
	 * 票号
	 */
	private String ticketNo;
	/**
	 * 票价
	 */
	private BigDecimal fare;
	/**
	 * 税费
	 */
	private BigDecimal tax;
	/**
	 * 手续费
	 */
	private BigDecimal brokerage;
	/**
	 * 代理费
	 */
	private BigDecimal agencyFee;
	/**
	 * 后返
	 */
	private BigDecimal rebate;
	/**
	 * 舱位: A/B/C/D/E.
	 */
	private String cabin;
	/**
	 * 计奖价
	 */
	private BigDecimal awardPrice;
	/**
	 * 父航段序号 从01开始
	 */
	private String parentSection;
	/**
	 * 子航段序号 从01开始 保存格式为 复航段序号+子航段序号，即：0101.
	 */
	private String childSection;
	/**
	 * 是否改签新增的明细。
	 * true：表示改签新增信息。
	 * false：表示原始信息。
	 */
	private Boolean isChange;
	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 销售明细状态 1（待核价），2（已核价），3（出票中），4（已出票）5（退票中），6（废票中），7（改签中） 8（已退），9（已废），10（已改签）.11(已取消).12（已挂起)
	 */
	private String status;
	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 销售单拓展
	 */
	private SaleOrderExt saleOrderExt;
	
	/**
	 * 挂起原因
	 */
	private String rejectreason;
	/***
	 * 拒单原因
	 */
	private String refuseReason;
	/**
	 *改签订单编号
	 */
	@Transient
	private Long changeOrderNo;

	/**
	 * 部门毛利
	 */
	private BigDecimal deptProfit;

	/**
	 * 毛利
	 */
	private BigDecimal profit;

	@Override
	public String toString() {
		return "SaleOrderDetail{" +
				"saleOrderDetailNo=" + saleOrderDetailNo +
				", id=" + id +
				", owner=" + owner +
				", saleOrderNo=" + saleOrderNo +
				", passengerNo=" + passengerNo +
				", passenger=" + passenger +
				", legNo=" + legNo +
				", leg=" + leg +
				", ticketNo='" + ticketNo + '\'' +
				", fare=" + fare +
				", tax=" + tax +
				", brokerage=" + brokerage +
				", agencyFee=" + agencyFee +
				", rebate=" + rebate +
				", cabin='" + cabin + '\'' +
				", awardPrice=" + awardPrice +
				", parentSection='" + parentSection + '\'' +
				", childSection='" + childSection + '\'' +
				", isChange=" + isChange +
				", modifier='" + modifier + '\'' +
				", modifyTime=" + modifyTime +
				", creator='" + creator + '\'' +
				", createTime=" + createTime +
				", status='" + status + '\'' +
				", valid=" + valid +
				", saleOrderExt=" + saleOrderExt +
				", rejectreason='" + rejectreason + '\'' +
				", refuseReason='" + refuseReason + '\'' +
				", changeOrderNo=" + changeOrderNo +
				", deptProfit=" + deptProfit +
				", profit=" + profit +
				'}';
	}

	public Long getSaleOrderDetailNo() {

		return saleOrderDetailNo;
	}

	public void setSaleOrderDetailNo(Long saleOrderDetailNo) {

		this.saleOrderDetailNo = saleOrderDetailNo;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Integer getOwner() {

		return owner;
	}

	public void setOwner(Integer owner) {

		this.owner = owner;
	}

	public Long getSaleOrderNo() {

		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {

		this.saleOrderNo = saleOrderNo;
	}

	public Long getPassengerNo() {

		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {

		this.passengerNo = passengerNo;
	}

	public Long getLegNo() {

		return legNo;
	}

	public void setLegNo(Long legNo) {

		this.legNo = legNo;
	}

	public String getTicketNo() {

		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {

		this.ticketNo = ticketNo;
	}

	public Boolean getChange() {

		return isChange;
	}



	public BigDecimal getFare() {

		return fare;
	}

	public void setFare(BigDecimal fare) {

		this.fare = fare;
	}

	public BigDecimal getTax() {

		return tax;
	}

	public void setTax(BigDecimal tax) {

		this.tax = tax;
	}

	public BigDecimal getBrokerage() {

		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {

		this.brokerage = brokerage;
	}

	public BigDecimal getAgencyFee() {

		return agencyFee;
	}

	public void setAgencyFee(BigDecimal agencyFee) {

		this.agencyFee = agencyFee;
	}

	public BigDecimal getRebate() {

		return rebate;
	}

	public void setRebate(BigDecimal rebate) {

		this.rebate = rebate;
	}

	public BigDecimal getAwardPrice() {

		return awardPrice;
	}

	public void setAwardPrice(BigDecimal awardPrice) {

		this.awardPrice = awardPrice;
	}

	public String getParentSection() {

		return parentSection;
	}

	public void setParentSection(String parentSection) {

		this.parentSection = parentSection;
	}

	public String getChildSection() {

		return childSection;
	}

	public void setChildSection(String childSection) {

		this.childSection = childSection;
	}

	public Boolean getIsChange() {

		return isChange;
	}

	public void setIsChange(Boolean isChange) {

		this.isChange = isChange;
	}

	public String getModifier() {

		return modifier;
	}

	public void setModifier(String modifier) {

		this.modifier = modifier;
	}

	public Date getModifyTime() {

		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {

		this.modifyTime = modifyTime;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public Passenger getPassenger() {

		return passenger;
	}

	public void setPassenger(Passenger passenger) {

		this.passenger = passenger;
	}

	public Leg getLeg() {

		return leg;
	}

	public void setLeg(Leg leg) {

		this.leg = leg;
	}
	
	public String getCabin() {
	
		return cabin;
	}

	public void setCabin(String cabin) {
	
		this.cabin = cabin;
	}

	public SaleOrderExt getSaleOrderExt() {

		return saleOrderExt;
	}

	public void setSaleOrderExt(SaleOrderExt saleOrderExt) {

		this.saleOrderExt = saleOrderExt;
	}

	public String getRejectreason() {
		return rejectreason;
	}

	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Long getChangeOrderNo() {
		return changeOrderNo;
	}

	public void setChangeOrderNo(Long changeOrderNo) {
		this.changeOrderNo = changeOrderNo;
	}

	public void setChange(Boolean change) {
		isChange = change;
	}

	public BigDecimal getDeptProfit() {
		return deptProfit;
	}

	public void setDeptProfit(BigDecimal deptProfit) {
		this.deptProfit = deptProfit;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
}
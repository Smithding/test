package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Alias("iftPassenger")
public class Passenger implements Serializable {
	/**
	 * 乘客编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long passengerNo;

	/**
	 * 数据归属单位
	 */
	private Integer owner;
	/**
	 * 是否设置为常旅客（0 否 1 是）
	 */
	private String historyPassenger;
	/**
	 * ID
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 销售单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 乘客类型 1：ADT:2：CHD，3：INF
	 */
	private String passengerType;

	/**
	 * 姓
	 */
	private String surname;

	/**
	 * 名
	 */
	private String name;

	/**
	 * 证件类型 PP:护照 NI：身份证 ID:其他证件
	 */
	private String certType;

	/**
	 * 证件编号
	 */
	private String certNo;

	/**
	 * 证件有效期
	 */
	private Date certValid;

	/**
	 * 乘客出生日期
	 */
	private Date passengerBirth;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 出票配置
	 */
	private String ticketConfig;

	/**
	 * 出票航司
	 */
	private String ticketAirline;

	/**
	 * 出票类型
	 */
	private String ticketType;

	/**
	 * 采购票价
	 */
	private BigDecimal buyFare;

	/**
	 * 采购税费
	 */
	private BigDecimal buyTax;

	/**
	 * 采购手续费
	 */
	private BigDecimal buyBrokerage;

	/**
	 * 采购代理费
	 */
	private BigDecimal buyAgencyFee;

	/**
	 * 采购后返
	 */
	private BigDecimal buyRebate;

	/**
	 * 采购计奖价
	 */
	private BigDecimal buyAwardPrice;

	/**
	 * 采购结算价
	 */
	private BigDecimal buyPrice;

	/**
	 * 销售票价
	 */
	private BigDecimal saleFare;

	/**
	 * 销售税费
	 */
	private BigDecimal saleTax;

	/**
	 * 销售手续费
	 */
	private BigDecimal saleBrokerage;

	/**
	 * 销售代理费
	 */
	private BigDecimal saleAgencyFee;

	/**
	 * 销售后返
	 */
	private BigDecimal saleRebate;

	/**
	 * 销售计奖价
	 */
	private BigDecimal saleAwardPrice;
	
	/**
	 * 服务费
	 */
	private BigDecimal serviceCharge;

	/**
	 * 销售结算价
	 */
	private BigDecimal salePrice;

	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 启用状态 1：启用，
	 2：停用
	 */
	private String status;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Byte valid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 政策编号.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private long policyNo;

	//部门毛利
	private BigDecimal deptProfit;
	//毛利
	private BigDecimal profit;
	//采购币种 BUY_CURRENCY
	private String buyCurrency;
	//采购汇率 BUY_EXCHANGE_RATE
	private BigDecimal buyExchangeRate;

	public String getBuyCurrency() {
		return buyCurrency;
	}

	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	public BigDecimal getBuyExchangeRate() {
		return buyExchangeRate;
	}

	public void setBuyExchangeRate(BigDecimal buyExchangeRate) {
		this.buyExchangeRate = buyExchangeRate;
	}

	@Override
	public String toString() {
		return "Passenger{" +
				"passengerNo=" + passengerNo +
				", owner=" + owner +
				", id=" + id +
				", saleOrderNo=" + saleOrderNo +
				", passengerType='" + passengerType + '\'' +
				", surname='" + surname + '\'' +
				", name='" + name + '\'' +
				", certType='" + certType + '\'' +
				", certNo='" + certNo + '\'' +
				", certValid=" + certValid +
				", passengerBirth=" + passengerBirth +
				", nationality='" + nationality + '\'' +
				", gender='" + gender + '\'' +
				", ticketConfig='" + ticketConfig + '\'' +
				", ticketAirline='" + ticketAirline + '\'' +
				", ticketType='" + ticketType + '\'' +
				", buyFare=" + buyFare +
				", buyTax=" + buyTax +
				", buyBrokerage=" + buyBrokerage +
				", buyAgencyFee=" + buyAgencyFee +
				", buyRebate=" + buyRebate +
				", buyAwardPrice=" + buyAwardPrice +
				", buyPrice=" + buyPrice +
				", saleFare=" + saleFare +
				", saleTax=" + saleTax +
				", saleBrokerage=" + saleBrokerage +
				", saleAgencyFee=" + saleAgencyFee +
				", saleRebate=" + saleRebate +
				", saleAwardPrice=" + saleAwardPrice +
				", serviceCharge=" + serviceCharge +
				", salePrice=" + salePrice +
				", modifier='" + modifier + '\'' +
				", status='" + status + '\'' +
				", modifyTime=" + modifyTime +
				", valid=" + valid +
				", createTime=" + createTime +
				", creator='" + creator + '\'' +
				", ticketNo='" + ticketNo + '\'' +
				", phone='" + phone + '\'' +
				", policyNo=" + policyNo +
				", deptProfit=" + deptProfit +
				", profit=" + profit +
				'}';
	}

	public long getPolicyNo() {

		return policyNo;
	}

	public void setPolicyNo(long policyNo) {

		this.policyNo = policyNo;
	}

	private static final long serialVersionUID = 1L;

	public Long getPassengerNo() {
		return passengerNo;
	}

	public void setPassengerNo(Long passengerNo) {
		this.passengerNo = passengerNo;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Date getCertValid() {
		return certValid;
	}

	public void setCertValid(Date certValid) {
		this.certValid = certValid;
	}

	public Date getPassengerBirth() {
		return passengerBirth;
	}

	public void setPassengerBirth(Date passengerBirth) {
		this.passengerBirth = passengerBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTicketConfig() {
		return ticketConfig;
	}

	public void setTicketConfig(String ticketConfig) {
		this.ticketConfig = ticketConfig;
	}

	public String getTicketAirline() {
		return ticketAirline;
	}

	public void setTicketAirline(String ticketAirline) {
		this.ticketAirline = ticketAirline;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public BigDecimal getBuyFare() {
		return buyFare;
	}

	public void setBuyFare(BigDecimal buyFare) {
		this.buyFare = buyFare;
	}

	public BigDecimal getBuyTax() {
		return buyTax;
	}

	public void setBuyTax(BigDecimal buyTax) {
		this.buyTax = buyTax;
	}

	public BigDecimal getBuyBrokerage() {
		return buyBrokerage;
	}

	public void setBuyBrokerage(BigDecimal buyBrokerage) {
		this.buyBrokerage = buyBrokerage;
	}

	public BigDecimal getBuyAgencyFee() {
		return buyAgencyFee;
	}

	public void setBuyAgencyFee(BigDecimal buyAgencyFee) {
		this.buyAgencyFee = buyAgencyFee;
	}

	public BigDecimal getBuyRebate() {
		return buyRebate;
	}

	public void setBuyRebate(BigDecimal buyRebate) {
		this.buyRebate = buyRebate;
	}

	public BigDecimal getBuyAwardPrice() {
		return buyAwardPrice;
	}

	public void setBuyAwardPrice(BigDecimal buyAwardPrice) {
		this.buyAwardPrice = buyAwardPrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSaleFare() {
		return saleFare;
	}

	public void setSaleFare(BigDecimal saleFare) {
		this.saleFare = saleFare;
	}

	public BigDecimal getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(BigDecimal saleTax) {
		this.saleTax = saleTax;
	}

	public BigDecimal getSaleBrokerage() {
		return saleBrokerage;
	}

	public void setSaleBrokerage(BigDecimal saleBrokerage) {
		this.saleBrokerage = saleBrokerage;
	}

	public BigDecimal getSaleAgencyFee() {
		return saleAgencyFee;
	}

	public void setSaleAgencyFee(BigDecimal saleAgencyFee) {
		this.saleAgencyFee = saleAgencyFee;
	}

	public BigDecimal getSaleRebate() {
		return saleRebate;
	}

	public void setSaleRebate(BigDecimal saleRebate) {
		this.saleRebate = saleRebate;
	}

	public BigDecimal getSaleAwardPrice() {
		return saleAwardPrice;
	}

	public void setSaleAwardPrice(BigDecimal saleAwardPrice) {
		this.saleAwardPrice = saleAwardPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getHistoryPassenger() {
		return historyPassenger;
	}

	public void setHistoryPassenger(String historyPassenger) {
		this.historyPassenger = historyPassenger;
	}
}
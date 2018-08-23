package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.product.ift.api.entity.Demand;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class SaleOrderExtVo implements Serializable {
	
	@JsonSerialize(using = LongSerializer.class)
	private Long id;
	
	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;
	/** 供应商编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long supplierNo;
	/*销售单的字段封装到扩展Vo*/
	/** 交易单号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long transationOrderNo;

	/** 业务批次号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long businessSignNo;

	/** 所属部门 */
	private String deptCode;

	/** 订单类型 */
	private Integer orderType;

	/** 接入方式(订单来源) */
	private String sourceChannelNo;

	/** 客户类型 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerTypeNo;
	/**
	 * office
	 */
	private String office;
	
	/** 客户编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerNo;

	/** 下单用户登录名 */
	private String orderingLoginName;

	/** 主订单编号 */
	@JsonSerialize(using = LongSerializer.class)
	private Long mainOrderNo;

	/** 附属类型 */
	private String attachedType;

	/** 变更类型 */
	private String orderChangeType;

	/** 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 */
	private Integer goodsType;

	/** 商品小类 */
	private Integer goodsSubType;

	/** 商品名称 如：PEK-CKG-SHA,航空意外险等 */
	private String goodsName;

	/** 金额 */
	private BigDecimal amount;

	/** 优惠金额 */
	private BigDecimal discountAmount;

	/** 应收 */
	private BigDecimal receivable;

	/** 实收 */
	private BigDecimal received;

	/** 采购价格 */
	private BigDecimal buyorderPrice;

	/** 数量 */
	private Integer pcount;

	/** 下单时间 */
	private Date orderingTime;

	/** 支付状态 1 为待支付 2 支付中 3 为已支付，4 为挂帐支付 */
	private Integer payStatus;

	/** 订单状态 */
	private Integer orderStatus;

	/** 订单子状态 */
	private Integer[] orderChildStatusArray;
	/*销售单的字段封装到扩展Vo  end*/
	/*PNR的字段封装到扩展Vo  start*/
	/**
	 * 订单来源编号 可能为采购单编号，也可能为销售单编号，具体根据pnrSource确定
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long sourceNo;

	/**
	 * 航司PNR编号
	 */
	private String pnr;

	/**
	 * pnr编码类型.
	 * 1：ETERM，2：ABACUS，3：GALILEO，4：SABRE，5：AMADEUS，6：WORLDSPAN
	 */
	private Integer pnrType;

	/**
	 * PNR内容
	 */
	private String pnrContent;

	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * Pnr大编码 6位字符串.
	 */
	private String bigPnr;

	/**
	 * RT时间
	 */
	private Date rtTime;

	/**
	 * PNR来源 1：导入，2：采购，3：改签，4：手动生成
	 */
	private Integer pnrSource;
	/*PNR的字段封装到扩展Vo  end*/
	/**
	 * 数据归属
	 */
	private Integer owner;

	/**
	 * 需求单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long demandNo;

	/**
	 * 航程类型 ：1:单程; 2:往返.
	 */
	private Integer legType;

	/**
	 * 联系人名称
	 */
	private String contactName;

	/**
	 * 联系人电话
	 */
	private String contactPhone;

	/**
	 * 联系人手机号
	 */
	private String contactMobile;

	/**
	 * 联系人邮箱
	 */
	private String contactMail;

	/**
	 * 销售备注
	 */
	private String saleRemark;

	/**
	 * 删除标记
	 */
	private Byte valid;

	/**
	 * 版本号，每次更新时需判断是否同一版本号，再+1.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long version;

	/**
	 * 将销售单锁定的用户的Id 有大于0的值，表示已被用户锁定，是该用户的Id.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long locker;
	@JsonSerialize(using = LongSerializer.class)
	private Long buyLocker;

	/**
	 * 锁定时间.
	 */
	private Date lockTime;

	/**
	 * 订单创建类型. 1：PNR,2：白屏，3：手工补单.
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long createType;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 修改日期
	 */
	private Date modifyTime;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String modifier;
	
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
	* 航程集合
	* */
	private List<Leg> legList;
	
	/**
	 * 采购办理状态 待处理（1），处理中（2），已出票（3），已拒单（4）
	 */
	private Integer buyStatus;
	
	/**
	 * 客户办理状态 待核价（1），已核价（2），出票中（3），已出票（4），已取消（5）
	 */
	private Integer saleStatus;

	/**
	* 乘客集合
	* */
	private List<Passenger> passengerList;
	
	public  List<OrderPriceVo> orderPriceVoList;
	

	/*
	* 销售单明细
	* */
	private List<SaleOrderDetail> saleOrderDetailList;
	
	/**
	 * 乘机人
	 */
	private String name;
	
	/**
	 * 航程
	 */
	private String leg;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 起飞时间
	 */
	private String startTime;
	
	/**
	 * 舱位
	 */
	private String serviceClass;
	
	/**
	 * 销售结算价
	 */
	private String salePrice;
	
	/**
	 * 采购结算价
	 */
	private String buyPrice;
	/**
	 * 票面价
	 */
	private String saleFare;
	
	/**
	 * 税费
	 */
	private String saleTax;
	
	/**
	 * 政策类型
	 */
	private String policyType;
	
	/**
	 *
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long agentId;
	
	/**
	 * 出票时间
	 */
	private Date issueTime;
	
	private Demand demand;
	/*
	部门毛利 页面展示为利润
	* */
	private BigDecimal deptProfit;

	public BigDecimal getDeptProfit() {
		return deptProfit;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

    public void setDeptProfit(BigDecimal deptProfit) {
        this.deptProfit = deptProfit;
    }

    /**
	 * 12（已挂起).13.(已解挂)
	 */
	private String status;
	private BigDecimal exchangeRate;//采购汇率
	private String currency;//采购币种
	private BigDecimal saleExchangeRate;//销售汇率
	private String saleCurrency;//销售币种
	private String handlers;//当前订单操作人
	private Long originalOrderNo;//原订单号
	//客户名称
	private String customerName;

	public BigDecimal getSaleExchangeRate() {
		return saleExchangeRate;
	}

	public void setSaleExchangeRate(BigDecimal saleExchangeRate) {
		this.saleExchangeRate = saleExchangeRate;
	}

	private static final long serialVersionUID = 1L;

	

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



	public Long getTransationOrderNo() {
		return transationOrderNo;
	}



	public void setTransationOrderNo(Long transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}



	public Long getBusinessSignNo() {
		return businessSignNo;
	}



	public void setBusinessSignNo(Long businessSignNo) {
		this.businessSignNo = businessSignNo;
	}



	public String getDeptCode() {
		return deptCode;
	}



	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}



	public Integer getOrderType() {
		return orderType;
	}



	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}



	public String getSourceChannelNo() {
		return sourceChannelNo;
	}



	public void setSourceChannelNo(String sourceChannelNo) {
		this.sourceChannelNo = sourceChannelNo;
	}



	public Long getCustomerTypeNo() {
		return customerTypeNo;
	}



	public void setCustomerTypeNo(Long customerTypeNo) {
		this.customerTypeNo = customerTypeNo;
	}



	public Long getCustomerNo() {
		return customerNo;
	}



	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}



	public String getOrderingLoginName() {
		return orderingLoginName;
	}



	public void setOrderingLoginName(String orderingLoginName) {
		this.orderingLoginName = orderingLoginName;
	}



	public Long getMainOrderNo() {
		return mainOrderNo;
	}



	public void setMainOrderNo(Long mainOrderNo) {
		this.mainOrderNo = mainOrderNo;
	}



	public String getAttachedType() {
		return attachedType;
	}



	public void setAttachedType(String attachedType) {
		this.attachedType = attachedType;
	}



	public String getOrderChangeType() {
		return orderChangeType;
	}



	public void setOrderChangeType(String orderChangeType) {
		this.orderChangeType = orderChangeType;
	}



	public Integer getGoodsType() {
		return goodsType;
	}



	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}



	public Integer getGoodsSubType() {
		return goodsSubType;
	}



	public void setGoodsSubType(Integer goodsSubType) {
		this.goodsSubType = goodsSubType;
	}



	public String getGoodsName() {
		return goodsName;
	}



	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}



	public BigDecimal getAmount() {
		return amount;
	}



	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}



	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}



	public BigDecimal getReceivable() {
		return receivable;
	}



	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}



	public BigDecimal getReceived() {
		return received;
	}



	public void setReceived(BigDecimal received) {
		this.received = received;
	}



	public BigDecimal getBuyorderPrice() {
		return buyorderPrice;
	}



	public void setBuyorderPrice(BigDecimal buyorderPrice) {
		this.buyorderPrice = buyorderPrice;
	}



	public Integer getPcount() {
		return pcount;
	}



	public void setPcount(Integer pcount) {
		this.pcount = pcount;
	}



	public Date getOrderingTime() {
		return orderingTime;
	}



	public void setOrderingTime(Date orderingTime) {
		this.orderingTime = orderingTime;
	}



	public Integer getPayStatus() {
		return payStatus;
	}



	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}



	public Integer getOrderStatus() {
		return orderStatus;
	}



	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer[] getOrderChildStatusArray() {

		return orderChildStatusArray;
	}

	public void setOrderChildStatusArray(Integer[] orderChildStatusArray) {

		this.orderChildStatusArray = orderChildStatusArray;
	}

	public Long getSourceNo() {
		return sourceNo;
	}

	

	public Integer getBuyStatus() {
		return buyStatus;
	}



	public void setBuyStatus(Integer buyStatus) {
		this.buyStatus = buyStatus;
	}



	public Integer getSaleStatus() {
		return saleStatus;
	}



	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}



	public void setSourceNo(Long sourceNo) {
		this.sourceNo = sourceNo;
	}



	public String getPnr() {
		return pnr;
	}



	public void setPnr(String pnr) {
		this.pnr = pnr;
	}



	public Integer getPnrType() {
		return pnrType;
	}



	public void setPnrType(Integer pnrType) {
		this.pnrType = pnrType;
	}



	public String getPnrContent() {
		return pnrContent;
	}



	public void setPnrContent(String pnrContent) {
		this.pnrContent = pnrContent;
	}



	public String getBigPnr() {
		return bigPnr;
	}



	public void setBigPnr(String bigPnr) {
		this.bigPnr = bigPnr;
	}



	public Date getRtTime() {
		return rtTime;
	}



	public void setRtTime(Date rtTime) {
		this.rtTime = rtTime;
	}



	public Integer getPnrSource() {
		return pnrSource;
	}



	public void setPnrSource(Integer pnrSource) {
		this.pnrSource = pnrSource;
	}



	public Integer getOwner() {
		return owner;
	}



	public void setOwner(Integer owner) {
		this.owner = owner;
	}



	public Long getDemandNo() {
		return demandNo;
	}



	public void setDemandNo(Long demandNo) {
		this.demandNo = demandNo;
	}



	public Integer getLegType() {
		return legType;
	}



	public void setLegType(Integer legType) {
		this.legType = legType;
	}



	public String getContactName() {
		return contactName;
	}



	public void setContactName(String contactName) {
		this.contactName = contactName;
	}



	public String getContactPhone() {
		return contactPhone;
	}



	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}



	public String getContactMobile() {
		return contactMobile;
	}



	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}



	public String getContactMail() {
		return contactMail;
	}



	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}



	public String getSaleRemark() {
		return saleRemark;
	}



	public void setSaleRemark(String saleRemark) {
		this.saleRemark = saleRemark;
	}

	public Byte getValid() {

		return valid;
	}

	public void setValid(Byte valid) {

		this.valid = valid;
	}

	public Long getVersion() {
		return version;
	}



	public void setVersion(Long version) {
		this.version = version;
	}



	public Long getLocker() {
		return locker;
	}



	public void setLocker(Long locker) {
		this.locker = locker;
	}



	public Date getLockTime() {
		return lockTime;
	}



	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}



	public Long getCreateType() {
		return createType;
	}



	public void setCreateType(Long createType) {
		this.createType = createType;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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



	public String getModifier() {
		return modifier;
	}



	public void setModifier(String modifier) {
		this.modifier = modifier;
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



	public List<Leg> getLegList() {
		return legList;
	}



	public void setLegList(List<Leg> legList) {
		this.legList = legList;
	}



	public List<Passenger> getPassengerList() {
		return passengerList;
	}



	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}



	public List<OrderPriceVo> getOrderPriceVoList() {
		return orderPriceVoList;
	}



	public void setOrderPriceVoList(List<OrderPriceVo> orderPriceVoList) {
		this.orderPriceVoList = orderPriceVoList;
	}

	public List<SaleOrderDetail> getSaleOrderDetailList() {
		return saleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {
		this.saleOrderDetailList = saleOrderDetailList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeg() {
		return leg;
	}

	public void setLeg(String leg) {
		this.leg = leg;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getSaleFare() {
		return saleFare;
	}

	public void setSaleFare(String saleFare) {
		this.saleFare = saleFare;
	}

	public String getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(String saleTax) {
		this.saleTax = saleTax;
	}

	public Long getSupplierNo() {
	
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
	
		this.supplierNo = supplierNo;
	}
	
	

	public String getPolicyType() {
		return policyType;
	}



	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	


	public Long getAgentId() {
		return agentId;
	}



	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	

	public Date getIssueTime() {
		return issueTime;
	}



	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}


	public Demand getDemand() {
		return demand;
	}



	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public String getHandlers() {
		return handlers;
	}

	public void setHandlers(String handlers) {
		this.handlers = handlers;
	}

	public Long getOriginalOrderNo() {
		return originalOrderNo;
	}

	public void setOriginalOrderNo(Long originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
	
	public String getOffice() {
		return office;
	}
	
	public void setOffice(String office) {
		this.office = office;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getBuyLocker() {
		return buyLocker;
	}

	public void setBuyLocker(Long buyLocker) {
		this.buyLocker = buyLocker;
	}

	@Override
	public String toString() {
		return "SaleOrderExtVo{" +
				"id=" + id +
				", saleOrderNo=" + saleOrderNo +
				", supplierNo=" + supplierNo +
				", transationOrderNo=" + transationOrderNo +
				", businessSignNo=" + businessSignNo +
				", deptCode='" + deptCode + '\'' +
				", orderType=" + orderType +
				", sourceChannelNo='" + sourceChannelNo + '\'' +
				", customerTypeNo=" + customerTypeNo +
				", customerNo=" + customerNo +
				", orderingLoginName='" + orderingLoginName + '\'' +
				", mainOrderNo=" + mainOrderNo +
				", attachedType='" + attachedType + '\'' +
				", orderChangeType='" + orderChangeType + '\'' +
				", goodsType=" + goodsType +
				", goodsSubType=" + goodsSubType +
				", goodsName='" + goodsName + '\'' +
				", amount=" + amount +
				", discountAmount=" + discountAmount +
				", receivable=" + receivable +
				", received=" + received +
				", buyorderPrice=" + buyorderPrice +
				", pcount=" + pcount +
				", orderingTime=" + orderingTime +
				", payStatus=" + payStatus +
				", orderStatus=" + orderStatus +
				", orderChildStatusArray=" + Arrays.toString(orderChildStatusArray) +
				", sourceNo=" + sourceNo +
				", pnr='" + pnr + '\'' +
				", pnrType=" + pnrType +
				", pnrContent='" + pnrContent + '\'' +
				", ticketNo='" + ticketNo + '\'' +
				", bigPnr='" + bigPnr + '\'' +
				", rtTime=" + rtTime +
				", pnrSource=" + pnrSource +
				", owner=" + owner +
				", demandNo=" + demandNo +
				", legType=" + legType +
				", contactName='" + contactName + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				", contactMobile='" + contactMobile + '\'' +
				", contactMail='" + contactMail + '\'' +
				", saleRemark='" + saleRemark + '\'' +
				", valid=" + valid +
				", version=" + version +
				", locker=" + locker +
				", lockTime=" + lockTime +
				", createType=" + createType +
				", createTime=" + createTime +
				", modifyTime=" + modifyTime +
				", creator='" + creator + '\'' +
				", modifier='" + modifier + '\'' +
				", ticketConfig='" + ticketConfig + '\'' +
				", ticketAirline='" + ticketAirline + '\'' +
				", ticketType='" + ticketType + '\'' +
				", legList=" + legList +
				", buyStatus=" + buyStatus +
				", saleStatus=" + saleStatus +
				", passengerList=" + passengerList +
				", orderPriceVoList=" + orderPriceVoList +
				", saleOrderDetailList=" + saleOrderDetailList +
				", name='" + name + '\'' +
				", leg='" + leg + '\'' +
				", flightNo='" + flightNo + '\'' +
				", startTime='" + startTime + '\'' +
				", serviceClass='" + serviceClass + '\'' +
				", salePrice='" + salePrice + '\'' +
				", buyPrice='" + buyPrice + '\'' +
				", saleFare='" + saleFare + '\'' +
				", saleTax='" + saleTax + '\'' +
				", policyType='" + policyType + '\'' +
				", agentId=" + agentId +
				", issueTime=" + issueTime +
				", demand=" + demand +
				", status='" + status + '\'' +
				", exchangeRate=" + exchangeRate +
				", currency='" + currency + '\'' +
				", saleCurrency='" + saleCurrency + '\'' +
				'}';
	}
}
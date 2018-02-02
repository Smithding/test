package com.tempus.gss.product.ins.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.ins.api.entity.vo.InsureExtVo;
import com.tempus.gss.serializer.LongSerializer;

@JsonInclude(Include.NON_NULL)
@Alias("insSaleOrderExt")
public class SaleOrderExt implements Serializable {
	/**
	 * 订单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long saleOrderNo;

	/**
	 * 编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long id;

	/**
	 * 数据归属单位
	 */
	private Integer owner;

	/**
	 * 保险销售单
	 */
	private SaleOrder saleOrder;

	/**
	 * 采购单编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long buyOrderNo;

	/**
	 * 保险采购单
	 */
	private BuyOrder buyOrder;

	/**
	 * 保险产品编号
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long insuranceNo;

	/**
	 * 保险产品
	 */
	private Insurance insurance;

	/**
	 * 投保单号
	 */
	private String proposalNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单类型 1：线上 2：线下
	 */
	private Integer orderType;

	/**
	 * 保单下载地址
	 */
	private String policyUrl;

	/**
	 * 保单号
	 */
	private String policyNo;

	/**
	 * 交易流水号
	 */
	private String transactionId;

	/**
	 * 产品code
	 */
	private String productKey;

	/**
	 * 目的地
	 */
	private String destination;

	/**
	 * 保单生效日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date effectDate;

	/**
	 * 保单失效日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date expireDate;

	/**
	 * 投保日期 yyyy-MM-dd HH:mm:ss
	 */
	private Date issueDate;

	/**
	 * 投保人类型 1-个人 2-企业
	 */
	private Integer holderType;

	/**
	 * 投保人姓名
	 */
	private String holderName;

	/**
	 * 投保人证件类型 1:身份证,2:护照,3:出生证,4:驾照,5:军官证,6:其他
	 */
	private Integer holderCertiType;

	/**
	 * 投保人证件号码
	 */
	private String holderCertiNo;

	/**
	 * 投保人性别 1-男 2-女
	 */
	private Integer holderSex;

	/**
	 * yyyy-MM-dd 投保人出生日
	 */
	private Date holderBirthday;

	/**
	 * 投保人电子邮箱地址
	 */
	private String holderEmail;

	/**
	 * 投保人手机号码
	 */
	private String holderPhone;

	/**
	 * 销售价
	 */
	private BigDecimal salePrice;

	/**
	 * 采购价
	 */
	private BigDecimal buyPrice;


	/**
	 * 总保费
	 */
	private BigDecimal totalPremium;

	/**
	 * 旅行天数
	 */
	private Integer travelDay;

	/**
	 * 被保险人个数
	 */
	private Integer insuredCount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否团单 1-是 2-否
	 */
	private Short isTeam;

	/**
	 * 渠道名称
	 */
	private String sourceName;

	/**
	 * 拓展字段json串
	 */
	private String extendedFieldsJson;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 创建者
	 */
	private String creator;
	/**
	 * 保险类别 1为国内 2为国际
	 */
    private int internatOrcivil;


	/**
	 * 操作人 默认为：sys
	 */
	private String modifier;

	/**
	 * 删除标志 0 无效 已删除 1 有效
	 */
	private Boolean valid;
	/**
	 * 保险子订单集合
	 */
	private List<SaleOrderDetail> saleOrderDetailList;
	/**
	 * 结算公司
	 */
    private String ownerName;

    private String customerType;
    public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	private String customerName;
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 保险扩展字段对象
	 */
	private InsureExtVo insureExtVo;

	/**
	 * 控润渠道类型，目前可用范围：301（分销商），302（集团客户）,303（散客会员）,306（体内销售）
	 */
	@JsonSerialize(using = LongSerializer.class)
	private Long customerTypeNo;
 
	/**
	 * 子账户集合
	 * @return
	 */
	private List<Customer> lowerCustomers;


	public List<Customer> getLowerCustomers() {
		return lowerCustomers;
	}

	public void setLowerCustomers(List<Customer> lowerCustomers) {
		this.lowerCustomers = lowerCustomers;
	}

	/**
	 * PNR,关联机票信息
	 */
	private String pnr;
	/**
	 * 1代表b2b下单  2代表呼叫中心下单 3代表api下单
	 */
	private int insuranceSource;
	/**
	 * 1代表单独下单 2代表与其它产品一起下单
	 */
	private int isBind;
	/**
	 * 该订单总保险份数（一个订单可以多个 一人可以多份）
	 */
	private int sumCopies;


	/**
	 * 面价
	 */
	private BigDecimal facePrice;
	private static final long serialVersionUID = 1L;
	public String getPnr() {

		return pnr;
	}
	public int getInsuranceSource() {
		return insuranceSource;
	}

	public void setInsuranceSource(int insuranceSource) {
		this.insuranceSource = insuranceSource;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

	public int getSumCopies() {
		return sumCopies;
	}

	public void setSumCopies(int sumCopies) {
		this.sumCopies = sumCopies;
	}

	public void setPnr(String pnr) {

		this.pnr = pnr;
	}
	public BigDecimal getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(BigDecimal facePrice) {
		this.facePrice = facePrice;
	}

	public int getInternatOrcivil() {
		return internatOrcivil;
	}

	public void setInternatOrcivil(int internatOrcivil) {
		this.internatOrcivil = internatOrcivil;
	}
	public Long getCustomerTypeNo() {

		return customerTypeNo;
	}

	public void setCustomerTypeNo(Long customerTypeNo) {

		this.customerTypeNo = customerTypeNo;
	}

	public Long getSaleOrderNo() {
		return saleOrderNo;
	}

	public InsureExtVo getInsureExtVo() {

		return insureExtVo;
	}

	public void setInsureExtVo(InsureExtVo insureExtVo) {

		this.insureExtVo = insureExtVo;
	}


	public void setSaleOrderNo(Long saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
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

	public Long getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(Long buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	public Long getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(Long insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getPolicyUrl() {
		return policyUrl;
	}

	public void setPolicyUrl(String policyUrl) {
		this.policyUrl = policyUrl;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Integer getHolderType() {
		return holderType;
	}

	public void setHolderType(Integer holderType) {
		this.holderType = holderType;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public Integer getHolderCertiType() {
		return holderCertiType;
	}

	public void setHolderCertiType(Integer holderCertiType) {
		this.holderCertiType = holderCertiType;
	}

	public String getHolderCertiNo() {
		return holderCertiNo;
	}

	public void setHolderCertiNo(String holderCertiNo) {
		this.holderCertiNo = holderCertiNo;
	}

	public Integer getHolderSex() {
		return holderSex;
	}

	public void setHolderSex(Integer holderSex) {
		this.holderSex = holderSex;
	}

	public Date getHolderBirthday() {
		return holderBirthday;
	}

	public void setHolderBirthday(Date holderBirthday) {
		this.holderBirthday = holderBirthday;
	}

	public String getHolderEmail() {
		return holderEmail;
	}

	public void setHolderEmail(String holderEmail) {
		this.holderEmail = holderEmail;
	}

	public String getHolderPhone() {
		return holderPhone;
	}

	public void setHolderPhone(String holderPhone) {
		this.holderPhone = holderPhone;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}

	public Integer getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(Integer travelDay) {
		this.travelDay = travelDay;
	}

	public Integer getInsuredCount() {
		return insuredCount;
	}

	public void setInsuredCount(Integer insuredCount) {
		this.insuredCount = insuredCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsTeam() {

		return isTeam;
	}

	public void setIsTeam(Short isTeam) {

		this.isTeam = isTeam;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getExtendedFieldsJson() {
		return extendedFieldsJson;
	}

	public void setExtendedFieldsJson(String extendedFieldsJson) {
		this.extendedFieldsJson = extendedFieldsJson;
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

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public List<SaleOrderDetail> getSaleOrderDetailList() {

		return saleOrderDetailList;
	}

	public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {

		this.saleOrderDetailList = saleOrderDetailList;
	}

	public Insurance getInsurance() {

		return insurance;
	}

	public void setInsurance(Insurance insurance) {

		this.insurance = insurance;
	}

	public SaleOrder getSaleOrder() {

		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {

		this.saleOrder = saleOrder;
	}

	public BuyOrder getBuyOrder() {

		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {

		this.buyOrder = buyOrder;
	}

	public BigDecimal getSalePrice() {

		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {

		this.salePrice = salePrice;
	}

	public BigDecimal getBuyPrice() {

		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {

		this.buyPrice = buyPrice;
	}
}
package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfoModel;
import com.tempus.gss.serializer.LongSerializer;

/**
 * 酒店订单mysql数据库保存信息
 */
@TableName("HOL_HOTEL_ORDER")
public class HotelOrder implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据归属单位
     */
    //@TableField(exist = false)
    private Integer owner;
    /**
     * 交易单号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long transationOrderNo;
    /**
     * 订单编号
     */
    private String hotelOrderNo;

    /**
     * 销售订单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleOrderNo;

    /**
     * 采购订单编号
     */
    //@TableField(exist = false)
    @JsonSerialize(using = LongSerializer.class)
    private Long buyOrderNo;

    /**
     * 酒店编号
     */
    private String hotelCode;
    /**
     * 房型编号
     */
    private String proId;
    
    /**
     * 酒店名字
     */
    private String hotelName;

    /**
     * 订单类型 1:内 2:国际
     */
    private Integer orderType;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactNumber;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 到住日期
     */
    private Date arrivalDate;

    /**
     * 离店日期
     */
    private Date departureDate;

    /**
     * 价格合计
     */
    private BigDecimal totalPrice;

    /**
     * 返现合计
     */
    private BigDecimal totalRefund;

    /**
     * 入住人姓名
     */
    private String guestName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 当前状态
     */
    private String orderStatus;

    /**
     * 锁定的用户的Id 有大于0的值，表示已被用户锁定
     */
    private Long locker;

    /**
     * 锁定时间
     */
    private Date lockTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createOrderTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private SaleOrder saleOrder;
    
   /**
    * 晚数
    */
    private Integer nightCount;

    /**
	 * 销售策略 Id
	 */
    @JsonSerialize(using = LongSerializer.class)
	private Long productUniqueId;
	/**
	 * 预定份数
	 */
	private Integer bookCount;
    /**
     * 支付模式 1.预付 2.现付
     */
   private Integer paymentSign;
   /**
    * 银行id
    */
   private Long bankId;
   /**
    * 银行名称
    */
   private String bankName;
   /**
    * 信用卡卡号
    */
   private String cardNumber;
   /**
    * 持卡人姓名
    */
   private String cardUserName;
   
   /**
    * 信用卡有效期
    */
   private String cardValueDate;
   /**
    * 证件名称
    */
   private String credentialName;
   /**
    * 证件号码
    */
   private String credentialNumber;
   /**
    * 持卡人预留手机号码
    */
   private String cardMobile;
   /**
    * 下单返回的信息
    */
   private String msg;
   /**
    * 下单完成后订单的金额
    */
//   private BigDecimal orderMoney;
   /**
    * 订单是否可以及时确认,即库存确认
    */
   private Boolean isAffirm;
   /**
    * 返回码，0=>下单失败，1=>下单成功，2=>下单成功，支付失败，3=>下单成功，支付成功
    */
   private String resultCode;
   /**
	 * 酒店住址
	 */
	private String hotelAddress;
	/**
	 * 酒店电话
	 */
	private String hotelPhone;
	/**
	 * 房型名称
	 */
	private String proName;
	/**
	 *同程订单状态
	 */
	private String tcOrderStatus;
	/**
     * 入住人电话
     */
    private String guestMobile;
    /**
     * 担保类型
     */
    private String dbOrderType;
    /**
     * 担保金额
     */
    private BigDecimal dbOrderMoney;
    /**
     * 担保取消规则
     */
    private String dbCancelRule;
    /**
     * 最迟的取消时间，在这个时间前取消不需要扣除罚金
     */
    private Date cancelPenalty;
    /**
     * 最早到店限制时间（东八区北京时间）
     */
    private Date earlyArriveTime;
    /**
     * 最晚到店时间限制（东八区北京时间）
     */
    private Date latestArriveTime;
    /**
     * 分销商编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long customerNo;
    /**
     * 供应商编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long supplierNo;
    /**
     * 信用卡验证码
     */
    private String cardValueCode;
    /**
     * 最晚到店时间 yyyy-MM-dd HH:mm:ss
     */
    private String arriveHotelTime;
    /**
     * 特殊要求代码
     */
    private String requestCode;
   
    /**
     * 特殊要求文本
     */
    private String requestText;
   
    /**
     * 特殊要求名称
     */
    private String requestName;
    /**
     * 特殊要求名称以集合返回前台
     */
    @TableField(exist = false)
    private List<String> requestNameList;
    
    /**
     * 实际到店时间
     */
    private Date factArriveTime;
    /**
     * 实际离店时间
     */
    private Date factLeaveTime;
    /**
     * 实际入住人
     */
    private String factGuestName;
    
    /**
     * 实际房间数
     */
    private Integer factProCount;
    /**
     * 实际晚数
     */
    private Integer factNightCount;
    /**
     * 预定时每晚的价格
     */
    private String eachNightPrice;
    
    /**
     * 实际总价
     */
    private BigDecimal factTotalPrice;
    
    /**
	 * 早餐分数
	 */
	private String breakfastCount;
	/**
	 * 携程订单号
	 */
	private String supplierNumber;
	/**
	 * 产品名称
	 */
	private String supPriceName;
	/**
	 * 床型
	 */
	private String bedTypeName;
	
	private BigDecimal factTotalRefund;
	/**
	 * 补单信息出现的关联订单号
	 */
	private String relateOrderNo;
	/**
	 * SM_USER表中的name
	 */
	@TableField(exist = false)
	private String name;
    
	public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelOrderNo() {
        return hotelOrderNo;
    }

    public void setHotelOrderNo(String hotelOrderNo) {
        this.hotelOrderNo = hotelOrderNo;
    }

    public Long getSaleOrderNo() {
        return this.saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public Long getBuyOrderNo() {
        return this.buyOrderNo;
    }

    public void setBuyOrderNo(Long buyOrderNo) {
        this.buyOrderNo = buyOrderNo;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getOrderType() {
        return this.orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getCancelTime() {
        return this.cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(BigDecimal totalRefund) {
        this.totalRefund = totalRefund;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

	public Long getProductUniqueId() {
		return productUniqueId;
	}

	public void setProductUniqueId(Long productUniqueId) {
		this.productUniqueId = productUniqueId;
	}

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public Integer getPaymentSign() {
		return paymentSign;
	}

	public void setPaymentSign(Integer paymentSign) {
		this.paymentSign = paymentSign;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	public String getCardValueDate() {
		return cardValueDate;
	}

	public void setCardValueDate(String cardValueDate) {
		this.cardValueDate = cardValueDate;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public String getCardMobile() {
		return cardMobile;
	}

	public void setCardMobile(String cardMobile) {
		this.cardMobile = cardMobile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getIsAffirm() {
		return isAffirm;
	}

	public void setIsAffirm(Boolean isAffirm) {
		this.isAffirm = isAffirm;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getNightCount() {
		return nightCount;
	}

	public void setNightCount(Integer nightCount) {
		this.nightCount = nightCount;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public String getHotelPhone() {
		return hotelPhone;
	}

	public void setHotelPhone(String hotelPhone) {
		this.hotelPhone = hotelPhone;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getTcOrderStatus() {
		return tcOrderStatus;
	}

	public void setTcOrderStatus(String tcOrderStatus) {
		this.tcOrderStatus = tcOrderStatus;
	}

	public Long getTransationOrderNo() {
		return transationOrderNo;
	}

	public void setTransationOrderNo(Long transationOrderNo) {
		this.transationOrderNo = transationOrderNo;
	}

	public String getGuestMobile() {
		return guestMobile;
	}

	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}

	public String getDbOrderType() {
		return dbOrderType;
	}

	public void setDbOrderType(String dbOrderType) {
		this.dbOrderType = dbOrderType;
	}

	public BigDecimal getDbOrderMoney() {
		return dbOrderMoney;
	}

	public void setDbOrderMoney(BigDecimal dbOrderMoney) {
		this.dbOrderMoney = dbOrderMoney;
	}

	public String getDbCancelRule() {
		return dbCancelRule;
	}

	public void setDbCancelRule(String dbCancelRule) {
		this.dbCancelRule = dbCancelRule;
	}

	public Date getCancelPenalty() {
		return cancelPenalty;
	}

	public void setCancelPenalty(Date cancelPenalty) {
		this.cancelPenalty = cancelPenalty;
	}

	public Date getEarlyArriveTime() {
		return earlyArriveTime;
	}

	public void setEarlyArriveTime(Date earlyArriveTime) {
		this.earlyArriveTime = earlyArriveTime;
	}

	public Date getLatestArriveTime() {
		return latestArriveTime;
	}

	public void setLatestArriveTime(Date latestArriveTime) {
		this.latestArriveTime = latestArriveTime;
	}

	public Long getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	public Long getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(Long supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getCardValueCode() {
		return cardValueCode;
	}

	public void setCardValueCode(String cardValueCode) {
		this.cardValueCode = cardValueCode;
	}

	public String getArriveHotelTime() {
		return arriveHotelTime;
	}

	public void setArriveHotelTime(String arriveHotelTime) {
		this.arriveHotelTime = arriveHotelTime;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getRequestText() {
		return requestText;
	}

	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public Date getFactArriveTime() {
		return factArriveTime;
	}

	public void setFactArriveTime(Date factArriveTime) {
		this.factArriveTime = factArriveTime;
	}

	public Date getFactLeaveTime() {
		return factLeaveTime;
	}

	public void setFactLeaveTime(Date factLeaveTime) {
		this.factLeaveTime = factLeaveTime;
	}

	public String getFactGuestName() {
		return factGuestName;
	}

	public void setFactGuestName(String factGuestName) {
		this.factGuestName = factGuestName;
	}

	public Integer getFactProCount() {
		return factProCount;
	}

	public void setFactProCount(Integer factProCount) {
		this.factProCount = factProCount;
	}
	public Integer getFactNightCount() {
		return factNightCount;
	}

	public void setFactNightCount(Integer factNightCount) {
		this.factNightCount = factNightCount;
	}

	public String getEachNightPrice() {
		return eachNightPrice;
	}

	public void setEachNightPrice(String eachNightPrice) {
		this.eachNightPrice = eachNightPrice;
	}

	public BigDecimal getFactTotalPrice() {
		return factTotalPrice;
	}

	public void setFactTotalPrice(BigDecimal factTotalPrice) {
		this.factTotalPrice = factTotalPrice;
	}

	public List<String> getRequestNameList() {
		return requestNameList;
	}

	public void setRequestNameList(List<String> requestNameList) {
		this.requestNameList = requestNameList;
	}

	public String getBreakfastCount() {
		return breakfastCount;
	}

	public void setBreakfastCount(String breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
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

	public String getSupPriceName() {
		return supPriceName;
	}

	public void setSupPriceName(String supPriceName) {
		this.supPriceName = supPriceName;
	}

	public String getBedTypeName() {
		return bedTypeName;
	}

	public void setBedTypeName(String bedTypeName) {
		this.bedTypeName = bedTypeName;
	}

	public BigDecimal getFactTotalRefund() {
		return factTotalRefund;
	}

	public void setFactTotalRefund(BigDecimal factTotalRefund) {
		this.factTotalRefund = factTotalRefund;
	}

	public String getRelateOrderNo() {
		return relateOrderNo;
	}

	public void setRelateOrderNo(String relateOrderNo) {
		this.relateOrderNo = relateOrderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

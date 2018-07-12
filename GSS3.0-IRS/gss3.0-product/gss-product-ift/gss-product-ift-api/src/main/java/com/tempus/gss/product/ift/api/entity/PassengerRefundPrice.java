package com.tempus.gss.product.ift.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 乘客废退明细，表示乘客废退是的销售价格和采购价格。
 */
public class PassengerRefundPrice implements Serializable {

    /**
     * 退票编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long passengerRefundPriceNo;

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
     * 销售单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleOrderNo;

    /**
     * 销售改签单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleChangeNo;

    /**
     * 乘客编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long passengerNo;

    /**
     * 乘客
     */
    private Passenger passenger;

    /**
     * 销售票面价.
     */
    private BigDecimal salePrice;

    /**
     * 销售税费
     */
    private BigDecimal saleTax;

    /**
     * 退票手续费
     */
    private BigDecimal saleRefundPrice;

    /**
     * 销售机票结算价
     */
    private BigDecimal saleTicketAccount;

    /**
     * 销售回收奖励
     */
    private BigDecimal saleRecoveryAward;

    /**
     * 销售退票结算价
     */
    private BigDecimal saleRefundAccount;

    /**
     * 采购机票结算价
     */
    private BigDecimal buyTicketAccount;

    /**
     * 采购回收奖励
     */
    private BigDecimal buyRecoveryAward;

    /**
     * 采购退票结算价
     */
    private BigDecimal buyFefundAccount;
    /**
     * 采购票面价
     */
    private BigDecimal buyPrice;

    /**
     * 采购税费
     */
    private BigDecimal buyTax;

    /**
     * 采购退票费
     */
    private BigDecimal buyRefundPrice;


    /**
     * 1:成人 2：儿童 3：婴儿
     */
    private String passengerType;

    /**
     * 1:销售 2：采购
     */
    private String orderType;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Byte valid;

    /**
     * 启用状态 1：启用，2：停用
     */
    private String status;

    /**
     * 操作人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private String modifier;

    /**
     * 票号
     */
    private String ticketNo;

    /**
     * 操作时间
     */
    private Date modifyTime;

    /**
     * 销售合计
     */
    private BigDecimal saleCount;
    /**
     * 采购合计
     */
    private BigDecimal buyCount;
    /**
     * 销售服务费
     */
    private BigDecimal saleBrokerage;
    /**
     * 采购服务费
     */
    private BigDecimal buyBrokerage;


    /**
     * 冲抵营业毛利
     */
    private BigDecimal chargeProfit;

    //币种
    private String currency;
    //销售币种
    private String saleCurrency;
    //汇率
    private BigDecimal exchangeRate;
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
        return "PassengerRefundPrice{" +
                "passengerRefundPriceNo=" + passengerRefundPriceNo +
                ", id=" + id +
                ", owner=" + owner +
                ", saleOrderNo=" + saleOrderNo +
                ", saleChangeNo=" + saleChangeNo +
                ", passengerNo=" + passengerNo +
                ", passenger=" + passenger +
                ", salePrice=" + salePrice +
                ", saleTax=" + saleTax +
                ", saleRefundPrice=" + saleRefundPrice +
                ", saleTicketAccount=" + saleTicketAccount +
                ", saleRecoveryAward=" + saleRecoveryAward +
                ", saleRefundAccount=" + saleRefundAccount +
                ", buyTicketAccount=" + buyTicketAccount +
                ", buyRecoveryAward=" + buyRecoveryAward +
                ", buyFefundAccount=" + buyFefundAccount +
                ", buyPrice=" + buyPrice +
                ", buyTax=" + buyTax +
                ", buyRefundPrice=" + buyRefundPrice +
                ", passengerType='" + passengerType + '\'' +
                ", orderType='" + orderType + '\'' +
                ", valid=" + valid +
                ", status='" + status + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", modifier='" + modifier + '\'' +
                ", ticketNo='" + ticketNo + '\'' +
                ", modifyTime=" + modifyTime +
                ", saleCount=" + saleCount +
                ", buyCount=" + buyCount +
                ", saleBrokerage=" + saleBrokerage +
                ", buyBrokerage=" + buyBrokerage +
                ", chargeProfit=" + chargeProfit +
                ", currency='" + currency + '\'' +
                ", saleCurrency='" + saleCurrency + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
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

    private static final long serialVersionUID = 1L;

    public Long getPassengerRefundPriceNo() {
        return passengerRefundPriceNo;
    }

    public void setPassengerRefundPriceNo(Long passengerRefundPriceNo) {
        this.passengerRefundPriceNo = passengerRefundPriceNo;
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

    public Long getSaleChangeNo() {
        return saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public Long getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(Long passengerNo) {
        this.passengerNo = passengerNo;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(BigDecimal saleTax) {
        this.saleTax = saleTax;
    }

    public BigDecimal getSaleRefundPrice() {
        return saleRefundPrice;
    }

    public void setSaleRefundPrice(BigDecimal saleRefundPrice) {
        this.saleRefundPrice = saleRefundPrice;
    }

    public BigDecimal getSaleTicketAccount() {
        return saleTicketAccount;
    }

    public void setSaleTicketAccount(BigDecimal saleTicketAccount) {
        this.saleTicketAccount = saleTicketAccount;
    }

    public BigDecimal getSaleRecoveryAward() {
        return saleRecoveryAward;
    }

    public void setSaleRecoveryAward(BigDecimal saleRecoveryAward) {
        this.saleRecoveryAward = saleRecoveryAward;
    }

    public BigDecimal getSaleRefundAccount() {
        return saleRefundAccount;
    }

    public void setSaleRefundAccount(BigDecimal saleRefundAccount) {
        this.saleRefundAccount = saleRefundAccount;
    }

    public BigDecimal getBuyTicketAccount() {
        return buyTicketAccount;
    }

    public void setBuyTicketAccount(BigDecimal buyTicketAccount) {
        this.buyTicketAccount = buyTicketAccount;
    }

    public BigDecimal getBuyRecoveryAward() {
        return buyRecoveryAward;
    }

    public void setBuyRecoveryAward(BigDecimal buyRecoveryAward) {
        this.buyRecoveryAward = buyRecoveryAward;
    }

    public BigDecimal getBuyFefundAccount() {
        return buyFefundAccount;
    }

    public void setBuyFefundAccount(BigDecimal buyFefundAccount) {
        this.buyFefundAccount = buyFefundAccount;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getBuyTax() {
        return buyTax;
    }

    public void setBuyTax(BigDecimal buyTax) {
        this.buyTax = buyTax;
    }

    public BigDecimal getBuyRefundPrice() {
        return buyRefundPrice;
    }

    public void setBuyRefundPrice(BigDecimal buyRefundPrice) {
        this.buyRefundPrice = buyRefundPrice;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public BigDecimal getSaleBrokerage() {
        return saleBrokerage;
    }

    public void setSaleBrokerage(BigDecimal saleBrokerage) {
        this.saleBrokerage = saleBrokerage;
    }

    public BigDecimal getBuyBrokerage() {
        return buyBrokerage;
    }

    public void setBuyBrokerage(BigDecimal buyBrokerage) {
        this.buyBrokerage = buyBrokerage;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public BigDecimal getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(BigDecimal saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(BigDecimal buyCount) {
        this.buyCount = buyCount;
    }

    public Passenger getPassenger() {

        return passenger;
    }

    public void setPassenger(Passenger passenger) {

        this.passenger = passenger;
    }

    public BigDecimal getChargeProfit() {
        return chargeProfit;
    }

    public void setChargeProfit(BigDecimal chargeProfit) {
        this.chargeProfit = chargeProfit;
    }

    public String getSaleCurrency() {
        return saleCurrency;
    }

    public void setSaleCurrency(String saleCurrency) {
        this.saleCurrency = saleCurrency;
    }
}
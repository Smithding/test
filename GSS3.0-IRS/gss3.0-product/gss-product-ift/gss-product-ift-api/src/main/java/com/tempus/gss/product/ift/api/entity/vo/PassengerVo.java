package com.tempus.gss.product.ift.api.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.cps.entity.Account;
import com.tempus.gss.pay.entity.CapitalAccount;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PassengerVo implements Serializable {
    /**
     * 乘客编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long passengerNo;
    
    /**
     * ID
     */
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
     * 销售单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleOrderDetailNo;
    
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
     * 证件类型
     */
    private String certType;
    
    /**
     * 证件编号
     */
    private String certNo;
    
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
     * 票号
     */
    private String ticketNo;
    
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
     * 销售其他手续费
     */
    private BigDecimal saleRest;
    /**
     * 采购其他手续费
     */
    private BigDecimal buyRest;
    /**
     * 销售改签结算价
     */
    private BigDecimal countPrice;
    /**
     * 采购改签结算价
     */
    private BigDecimal buyCountPrice;
    
    /**
     * 结算价
     */
    private BigDecimal salePrice;
    
    private BigDecimal buyPrice;
    /**
     * 采购手续费
     */
    private BigDecimal brokerage;
    
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
     * 销售票价/税费
     */
    private String saleFareTax;
    /**
     * 销售票价/税费
     */
    private String buyFareTax;
    private BigDecimal saleTax;
    
    /**
     * 销售手续费
     */
    private BigDecimal saleBrokerage;
    /**
     * 销售手续费
     */
    private BigDecimal buyBrokerage;
    
    /**
     * 销售代理费/后返
     */
    private String saleAgencyFeeRebate;
    private String buyAgencyFeeRebate;
    
    /**
     * 销售计奖价
     */
    private BigDecimal saleAwardPrice;
    
    /**
     * 操作人 默认为：sys
     */
    private String modifier;
    
    /**
     * 启用状态 1：启用，
     * 2：停用
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
     * 证件有效期
     */
    private Date certValid;
    
    //票号集合
    private List<SaleOrderDetailVo> detailList;
    
    /**
     * 创建人
     */
    private String creator;
    //航程航段信息
    private String legValue;
    //航程集合
    private List<LegVo> legList;
    private List<LegVo> oldLegList;
    private List<LegVo> newLegList;
    
    //供应商编号
    @JsonSerialize(using = LongSerializer.class)
    private Long supplierNo;
    
    /**
     * 机票结算价
     */
    private BigDecimal saleAirlineCount;
    /**
     * 机票结算价
     */
    private BigDecimal buyAirlineCount;
    
    private List<Account> accountList;
    private List<CapitalAccount> capitalAccountList;
    
    private BigDecimal saleAgencyFee;
    private BigDecimal saleRebate;
    private String exchangeRate;
    private String currency;
    private String profit;
    
    private List<Map<String, Object>> currencyList;
    
    public List<Map<String, Object>> getCurrencyList() {
        return currencyList;
    }
    
    public void setCurrencyList(List<Map<String, Object>> currencyList) {
        this.currencyList = currencyList;
    }
    
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
        return "PassengerVo{" + "passengerNo=" + passengerNo + ", id=" + id + ", owner=" + owner + ", saleOrderNo=" + saleOrderNo + ", saleOrderDetailNo=" + saleOrderDetailNo + ", passengerType='" + passengerType + '\'' + ", surname='" + surname + '\'' + ", name='" + name + '\'' + ", certType='" + certType + '\'' + ", certNo='" + certNo + '\'' + ", nationality='" + nationality + '\'' + ", gender='" + gender + '\'' + ", ticketConfig='" + ticketConfig + '\'' + ", ticketAirline='" + ticketAirline + '\'' + ", ticketNo='" + ticketNo + '\'' + ", ticketType='" + ticketType + '\'' + ", buyFare=" + buyFare + ", buyTax=" + buyTax + ", saleRest=" + saleRest + ", buyRest=" + buyRest + ", countPrice=" + countPrice + ", buyCountPrice=" + buyCountPrice + ", salePrice=" + salePrice + ", buyPrice=" + buyPrice + ", brokerage=" + brokerage + ", buyAgencyFee=" + buyAgencyFee + ", buyRebate=" + buyRebate + ", buyAwardPrice=" + buyAwardPrice + ", saleFareTax='" + saleFareTax + '\'' + ", buyFareTax='" + buyFareTax + '\'' + ", saleTax=" + saleTax + ", saleBrokerage=" + saleBrokerage + ", buyBrokerage=" + buyBrokerage + ", saleAgencyFeeRebate='" + saleAgencyFeeRebate + '\'' + ", buyAgencyFeeRebate='" + buyAgencyFeeRebate + '\'' + ", saleAwardPrice=" + saleAwardPrice + ", modifier='" + modifier + '\'' + ", status='" + status + '\'' + ", modifyTime=" + modifyTime + ", valid=" + valid + ", createTime=" + createTime + ", certValid=" + certValid + ", detailList=" + detailList + ", creator='" + creator + '\'' + ", legValue='" + legValue + '\'' + ", supplierNo=" + supplierNo + ", saleAirlineCount=" + saleAirlineCount + ", buyAirlineCount=" + buyAirlineCount + ", accountList=" + accountList + ", saleAgencyFee=" + saleAgencyFee + ", saleRebate=" + saleRebate + ", exchangeRate=" + exchangeRate + ", currency=" + currency + '}';
    }
    
    public String getLegValue() {
        
        return legValue;
    }
    
    public void setLegValue(String legValue) {
        
        this.legValue = legValue;
    }
    
    private static final long serialVersionUID = 1L;
    
    public Long getPassengerNo() {
        
        return passengerNo;
    }
    
    public void setPassengerNo(Long passengerNo) {
        
        this.passengerNo = passengerNo;
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
    
    public BigDecimal getBrokerage() {
        
        return brokerage;
    }
    
    public void setBrokerage(BigDecimal brokerage) {
        
        this.brokerage = brokerage;
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
    
    public BigDecimal getSaleBrokerage() {
        
        return saleBrokerage;
    }
    
    public List<SaleOrderDetailVo> getDetailList() {
        
        return detailList;
    }
    
    public void setDetailList(List<SaleOrderDetailVo> detailList) {
        
        this.detailList = detailList;
    }
    
    public void setSaleBrokerage(BigDecimal saleBrokerage) {
        
        this.saleBrokerage = saleBrokerage;
    }
    
    public BigDecimal getSaleAwardPrice() {
        
        return saleAwardPrice;
    }
    
    public void setSaleAwardPrice(BigDecimal saleAwardPrice) {
        
        this.saleAwardPrice = saleAwardPrice;
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
    
    public String getSaleFareTax() {
        
        return saleFareTax;
    }
    
    public void setSaleFareTax(String saleFareTax) {
        
        this.saleFareTax = saleFareTax;
    }
    
    public String getSaleAgencyFeeRebate() {
        
        return saleAgencyFeeRebate;
    }
    
    public void setSaleAgencyFeeRebate(String saleAgencyFeeRebate) {
        
        this.saleAgencyFeeRebate = saleAgencyFeeRebate;
    }
    
    public BigDecimal getSalePrice() {
        
        return salePrice;
    }
    
    public void setSalePrice(BigDecimal salePrice) {
        
        this.salePrice = salePrice;
    }
    
    public String getTicketNo() {
        
        return ticketNo;
    }
    
    public void setTicketNo(String ticketNo) {
        
        this.ticketNo = ticketNo;
    }
    
    public List<LegVo> getLegList() {
        return legList;
    }
    
    public void setLegList(List<LegVo> legList) {
        this.legList = legList;
    }
    
    public List<LegVo> getOldLegList() {
        
        return oldLegList;
    }
    
    public void setOldLegList(List<LegVo> oldLegList) {
        
        this.oldLegList = oldLegList;
    }
    
    public List<LegVo> getNewLegList() {
        
        return newLegList;
    }
    
    public void setNewLegList(List<LegVo> newLegList) {
        
        this.newLegList = newLegList;
    }
    
    public Long getSaleOrderDetailNo() {
        
        return saleOrderDetailNo;
    }
    
    public void setSaleOrderDetailNo(Long saleOrderDetailNo) {
        
        this.saleOrderDetailNo = saleOrderDetailNo;
    }
    
    public BigDecimal getSaleTax() {
        
        return saleTax;
    }
    
    public void setSaleTax(BigDecimal saleTax) {
        
        this.saleTax = saleTax;
    }
    
    public BigDecimal getSaleRest() {
        
        return saleRest;
    }
    
    public void setSaleRest(BigDecimal saleRest) {
        
        this.saleRest = saleRest;
    }
    
    public BigDecimal getCountPrice() {
        
        return countPrice;
    }
    
    public void setCountPrice(BigDecimal countPrice) {
        
        this.countPrice = countPrice;
    }
    
    public String getBuyFareTax() {
        return buyFareTax;
    }
    
    public void setBuyFareTax(String buyFareTax) {
        this.buyFareTax = buyFareTax;
    }
    
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }
    
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }
    
    public BigDecimal getBuyBrokerage() {
        return buyBrokerage;
    }
    
    public void setBuyBrokerage(BigDecimal buyBrokerage) {
        this.buyBrokerage = buyBrokerage;
    }
    
    public String getBuyAgencyFeeRebate() {
        return buyAgencyFeeRebate;
    }
    
    public void setBuyAgencyFeeRebate(String buyAgencyFeeRebate) {
        this.buyAgencyFeeRebate = buyAgencyFeeRebate;
    }
    
    public BigDecimal getBuyCountPrice() {
        return buyCountPrice;
    }
    
    public void setBuyCountPrice(BigDecimal buyCountPrice) {
        this.buyCountPrice = buyCountPrice;
    }
    
    public BigDecimal getBuyRest() {
        return buyRest;
    }
    
    public void setBuyRest(BigDecimal buyRest) {
        this.buyRest = buyRest;
    }
    
    public Long getSupplierNo() {
        
        return supplierNo;
    }
    
    public void setSupplierNo(Long supplierNo) {
        
        this.supplierNo = supplierNo;
    }
    
    public BigDecimal getSaleAirlineCount() {
        return saleAirlineCount;
    }
    
    public void setSaleAirlineCount(BigDecimal saleAirlineCount) {
        this.saleAirlineCount = saleAirlineCount;
    }
    
    public BigDecimal getBuyAirlineCount() {
        return buyAirlineCount;
    }
    
    public void setBuyAirlineCount(BigDecimal buyAirlineCount) {
        this.buyAirlineCount = buyAirlineCount;
    }
    
    public Date getCertValid() {
        return certValid;
    }
    
    public void setCertValid(Date certValid) {
        this.certValid = certValid;
    }
    
    public List<Account> getAccountList() {
        return accountList;
    }
    
    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
    
    public List<CapitalAccount> getCapitalAccountList() {
        return capitalAccountList;
    }
    
    public void setCapitalAccountList(List<CapitalAccount> capitalAccountList) {
        this.capitalAccountList = capitalAccountList;
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
    
    public String getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getProfit() {
        return profit;
    }
    
    public void setProfit(String profit) {
        this.profit = profit;
    }
}
package com.tempus.gss.product.ift.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.order.entity.DifferenceOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.serializer.LongSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售订单扩展.
 */
public class SaleOrderExt implements Serializable {
    
    @JsonSerialize(using = LongSerializer.class)
    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long saleOrderNo;
    /**
     * 销售单.
     */
    private SaleOrder saleOrder;
    
    @JsonSerialize(using = LongSerializer.class)
    private Long id;
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
     * 需求单团队编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long teamNo;
    
    /**
     * 需求单
     */
    private Demand demand;
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
    /**
     * 锁定时间.
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lockTime;
    /**
     * 订单创建类型. 1：PNR,2：白屏，3：手工补单.4:需求单.5:押金单.6:PNR后台下单
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long createType;
    
    /**
     * pnr编号
     */
    @JsonSerialize(using = LongSerializer.class)
    private Long pnrNo;
    /**
     * 导入Pnr建单时的pnr数据。
     */
    private Pnr importPnr;
    /**
     * 创建日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
     * 出票时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date issueTime;
    /**
     * 原始订单号
     */
    private Long originalOrderNo;
    /**
     * 航程集合
     */
    private List<Leg> legList;
    /**
     * 乘客集合
     */
    private List<Passenger> passengerList;
    
    private List<Legpassenger> LegpassengerList;
    
    //订单状态：待核价（1），已核价（2），出票中（3），已出票（4），已取消（5）  属于主订单的状态
    /**
     * 销售单明细
     */
    private List<SaleOrderDetail> saleOrderDetailList;
    
    //订单状态：12（已挂起).13.(已解挂)  专门供挂起、解挂列表使用
    private String status;
    
    /** 关联类型: 1:主单; 2:补儿童; 3:补婴儿 */
    protected Integer linkType;
    
    private String linkNo;
    private String currency;//采购币种
    private String saleCurrency;//销售币种
    private BigDecimal exchangeRate;//汇率
    private String handlers;//操作人姓名
    private String drawerLoginName;//出票员登录名
    
    /**
     * office
     */
    private String office;
    
    /**
     * 差异单明细
     *
     * @return
     */
    private List<DifferenceOrder> differenceOrderList;
    
    public String getOffice() {
        return office;
    }
    
    public void setOffice(String office) {
        this.office = office;
    }
    
    public Integer getLinkType() {
        return linkType;
    }
    
    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }
    
    public Long getSaleOrderNo() {
        
        return saleOrderNo;
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
    
    public SaleOrder getSaleOrder() {
        
        return saleOrder;
    }
    
    public void setSaleOrder(SaleOrder saleOrder) {
        
        this.saleOrder = saleOrder;
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
    
    public List<SaleOrderDetail> getSaleOrderDetailList() {
        
        return saleOrderDetailList;
    }
    
    public void setSaleOrderDetailList(List<SaleOrderDetail> saleOrderDetailList) {
        
        this.saleOrderDetailList = saleOrderDetailList;
    }
    
    public Pnr getImportPnr() {
        
        return importPnr;
    }
    
    public void setImportPnr(Pnr importPnr) {
        
        this.importPnr = importPnr;
    }
    
    public Demand getDemand() {
        
        return demand;
    }
    
    public void setDemand(Demand demand) {
        
        this.demand = demand;
    }
    
    public Long getPnrNo() {
        
        return pnrNo;
    }
    
    public void setPnrNo(Long pnrNo) {
        
        this.pnrNo = pnrNo;
    }
    
    public Long getOriginalOrderNo() {
        return originalOrderNo;
    }
    
    public void setOriginalOrderNo(Long originalOrderNo) {
        this.originalOrderNo = originalOrderNo;
    }
    
    public Date getIssueTime() {
        return issueTime;
    }
    
    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }
    
    public List<Legpassenger> getLegpassengerList() {
        return LegpassengerList;
    }
    
    public void setLegpassengerList(List<Legpassenger> legpassengerList) {
        LegpassengerList = legpassengerList;
    }
    
    public Long getTeamNo() {
        return teamNo;
    }
    
    public void setTeamNo(Long teamNo) {
        this.teamNo = teamNo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLinkNo() {
        return linkNo;
    }
    
    public void setLinkNo(String linkNo) {
        this.linkNo = linkNo;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public List<DifferenceOrder> getDifferenceOrderList() {
        return differenceOrderList;
    }
    
    public void setDifferenceOrderList(List<DifferenceOrder> differenceOrderList) {
        this.differenceOrderList = differenceOrderList;
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
    
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getHandlers() {
        return handlers;
    }
    
    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }
    
    public String getDrawerLoginName() {
        return drawerLoginName;
    }
    
    public void setDrawerLoginName(String drawerLoginName) {
        this.drawerLoginName = drawerLoginName;
    }
    
    @Override
    public String toString() {
        return "SaleOrderExt{" + "saleOrderNo=" + saleOrderNo + ", saleOrder=" + saleOrder + ", id=" + id + ", owner=" + owner + ", demandNo=" + demandNo + ", teamNo=" + teamNo + ", demand=" + demand + ", legType=" + legType + ", contactName='" + contactName + '\'' + ", contactPhone='" + contactPhone + '\'' + ", contactMobile='" + contactMobile + '\'' + ", contactMail='" + contactMail + '\'' + ", saleRemark='" + saleRemark + '\'' + ", valid=" + valid + ", version=" + version + ", locker=" + locker + ", lockTime=" + lockTime + ", createType=" + createType + ", pnrNo=" + pnrNo + ", importPnr=" + importPnr + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", creator='" + creator + '\'' + ", modifier='" + modifier + '\'' + ", issueTime=" + issueTime + ", originalOrderNo=" + originalOrderNo + ", legList=" + legList + ", status='" + status + '\'' + ", linkType=" + linkType + ", linkNo='" + linkNo + '\'' + ", currency='" + currency + '\'' + ", saleCurrency='" + saleCurrency + '\'' + ", exchangeRate=" + exchangeRate + '}';
    }
}

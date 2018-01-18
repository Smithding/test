package com.tempus.gss.product.ift.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tempus.gss.serializer.LongSerializer;

/**
 * @author 
 */
@Alias("iftProfit")
public class Profit implements Serializable {
    /**
     * 控润规则编号
     */
	@JsonSerialize(using = LongSerializer.class)
    private Long profitNo;

    /**
     * 编号
     */
	@JsonSerialize(using = LongSerializer.class)
    private Long id;

    /**
     * 客户编号
     */
	@JsonSerialize(using = LongSerializer.class)
    private Long customerNo;

    /**
     * 返点
     */
    private BigDecimal rebate;

    /**
     * 加价
     */
    private BigDecimal raisePrice;

    /**
     * 账号
     */
    private String account;

    /**
     * 控润渠道类型
     */
    private Long customerTypeNo;

    /**
     * 控润渠道名称
     */
    private String customerTypeName;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;


    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Boolean valid;

    /**
     * 归集单位
     */
    private Integer owner;

    /**
     * 控润渠道名称
     */
    private String customerName;

    /**
     * 备注
     */
    private String remark;
    /**
     * 启用状态
     */
    private String status;
    /**
     * 价格方式
     */
    private Integer priceType;
    private String priceTypeString;

    private static final long serialVersionUID = 1L;

    public String getPriceTypeString() {
        return priceTypeString;
    }

    public void setPriceTypeString(String priceTypeString) {
        this.priceTypeString = priceTypeString;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Long getProfitNo() {
        return profitNo;
    }

    public void setProfitNo(Long profitNo) {
        this.profitNo = profitNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getRaisePrice() {
        return raisePrice;
    }

    public void setRaisePrice(BigDecimal raisePrice) {
        this.raisePrice = raisePrice;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getCustomerTypeNo() {
        return customerTypeNo;
    }

    public void setCustomerTypeNo(Long customerTypeNo) {
        this.customerTypeNo = customerTypeNo;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Profit{" +
                "profitNo=" + profitNo +
                ", id=" + id +
                ", customerNo=" + customerNo +
                ", rebate=" + rebate +
                ", raisePrice=" + raisePrice +
                ", account='" + account + '\'' +
                ", customerTypeNo=" + customerTypeNo +
                ", customerTypeName='" + customerTypeName + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", modifier='" + modifier + '\'' +
                ", modifyTime=" + modifyTime +
                ", valid=" + valid +
                ", owner=" + owner +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
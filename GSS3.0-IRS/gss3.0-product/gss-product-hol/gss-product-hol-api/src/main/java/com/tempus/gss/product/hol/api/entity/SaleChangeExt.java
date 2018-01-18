package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.order.entity.SaleOrder;

/**
 * 酒店变更拓展单
 */
@TableName("HOL_SALE_CHANGE_EXT")
public class SaleChangeExt implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据归属单位
     */
    private Integer owner;
    /**
     * 销售单号
     */
    private Long saleOrderNo;
    /**
     * 销售单废退改编号
     */
    private Long saleChangeNo;

    /**
     * 采购单废退改编号
     */
    private Long buyChangeNo;

    /**
     * 操作人 默认为：sys
     */
    private String modifier;

    /**
     * 启用状态 1：待处理；2：已处理
     */
    private String status;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 锁定时间
     */
    private Date lockTime;

    /**
     * 业务类型 1废 2退(酒店退房) 3改(同SaleChange的 orderChangeType 属性)
     */
    private Integer changeType;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 审核人员
     */
    private String auditPerson;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 客商编号
     */
    private Long customerNo;

    /**
     * 客商类型编号
     */
    private Long customerTypeNo;

    /**
     * 销售变更单实体
     */
    @TableField(exist = false)
    private SaleChange saleChange;
    
    /**
     * 销售单实体
     * @return
     */
    @TableField(exist = false)
    private SaleOrder saleOrder;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOwner() {
        return this.owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Long getSaleChangeNo() {
        return this.saleChangeNo;
    }

    public void setSaleChangeNo(Long saleChangeNo) {
        this.saleChangeNo = saleChangeNo;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getValid() {
        return this.valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getChangeType() {
        return this.changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuditPerson() {
        return this.auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public Date getAuditTime() {
        return this.auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getCustomerNo() {
        return this.customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public Long getCustomerTypeNo() {
        return this.customerTypeNo;
    }

    public void setCustomerTypeNo(Long customerTypeNo) {
        this.customerTypeNo = customerTypeNo;
    }

    public Long getSaleOrderNo() {
        return saleOrderNo;
    }

    public void setSaleOrderNo(Long saleOrderNo) {
        this.saleOrderNo = saleOrderNo;
    }

    public SaleChange getSaleChange() {
        return saleChange;
    }

    public void setSaleChange(SaleChange saleChange) {
        this.saleChange = saleChange;
    }

    public Long getBuyChangeNo() {
        return buyChangeNo;
    }

    public void setBuyChangeNo(Long buyChangeNo) {
        this.buyChangeNo = buyChangeNo;
    }

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
    
}

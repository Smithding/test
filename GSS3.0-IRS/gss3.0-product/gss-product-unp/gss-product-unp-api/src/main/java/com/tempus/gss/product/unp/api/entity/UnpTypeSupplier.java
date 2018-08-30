package com.tempus.gss.product.unp.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpTypeSupplier implements Serializable {
    /**
     * ID
     */
    private Long no;
    /**
     * CPS表供应商编号
     */
    private Long supplierNo;
    /**
     * UNP_ITEM_TYPE通用产品小类编号
     */
    private Long itemTypeNo;
    /**
     * 是否供应整个大类 默认false(针对小类)
     */
    private Boolean parentType;
    /**
     * 执行创建操作的用户
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态（0，停用；1，启用）
     */
    private Integer valid;
    
    private static final long serialVersionUID = -3046006014224447608L;
    
    public Boolean getParentType() {
        return parentType;
    }
    
    public void setParentType(Boolean parentType) {
        this.parentType = parentType;
    }
    
    public Long getNo() {
        return no;
    }
    
    public void setNo(Long no) {
        this.no = no;
    }
    
    public Long getSupplierNo() {
        return supplierNo;
    }
    
    public void setSupplierNo(Long supplierNo) {
        this.supplierNo = supplierNo;
    }
    
    public Long getItemTypeNo() {
        return itemTypeNo;
    }
    
    public void setItemTypeNo(Long itemTypeNo) {
        this.itemTypeNo = itemTypeNo;
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
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
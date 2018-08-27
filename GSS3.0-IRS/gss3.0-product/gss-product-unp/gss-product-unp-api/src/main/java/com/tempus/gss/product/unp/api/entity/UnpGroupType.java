package com.tempus.gss.product.unp.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpGroupType implements Serializable {
    private static final long serialVersionUID = -1807482867107972503L;
    /**
     * 大类ID
     */
    private Long typeNo;
    /**
     * 数据归属
     */
    private Integer owner;
    /**
     * 通用产品-类型代码
     */
    private String code;
    /**
     * 通用产品-类型名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 执行创建操作的用户
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeStart;
    private String createTimeEnd;
    /**
     * 执行更新操作的用户
     */
    private String modifier;
    
    /**
     * 更新时间
     */
    private Date modifyTime;
    private String modifyTimeEnd;
    private String modifyTimeStart;
    /**
     * 删除标志 0 无效 已删除 1 有效 （0，停用；1，启用）
     */
    private Integer valid;
    
    public Long getTypeNo() {
        return typeNo;
    }
    
    public String getCreateTimeStart() {
        return createTimeStart;
    }
    
    public UnpGroupType setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
        return this;
    }
    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }
    
    public UnpGroupType setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
        return this;
    }
    
    public String getModifyTimeEnd() {
        return modifyTimeEnd;
    }
    
    public UnpGroupType setModifyTimeEnd(String modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
        return this;
    }
    
    public String getModifyTimeStart() {
        return modifyTimeStart;
    }
    
    public UnpGroupType setModifyTimeStart(String modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
        return this;
    }
    
    public void setTypeNo(Long typeNo) {
        this.typeNo = typeNo;
    }
    
    public Integer getOwner() {
        return owner;
    }
    
    public void setOwner(Integer owner) {
        this.owner = owner;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
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
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
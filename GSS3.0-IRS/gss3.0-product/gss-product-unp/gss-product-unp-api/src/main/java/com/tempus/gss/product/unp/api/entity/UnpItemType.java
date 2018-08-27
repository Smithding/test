package com.tempus.gss.product.unp.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpItemType implements Serializable {
    /**
     * 主键
     */
    private Long itemTypeNo;
    /**
     * 数据归属单位
     */
    private Integer owner;
    /**
     * 通用产品类型
     */
    private String groupCode;
    /**
     * 通用产品类型名称
     */
    private String groupName;
    /**
     * 通用产品代号
     */
    private String code;
    /**
     * 通用产品名称
     */
    private String name;
    /**
     * 图片
     */
    private String img;
    /**
     * 推荐价格
     */
    private BigDecimal baseAmount;
    /**
     * 排序级别 1级最高，往后递减，若遇到同样的级别，后创建的优先
     */
    private Integer sortNo;
    /**
     * 状态（0，停用；1，启用）
     */
    private Integer valid;
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
    
    private static final long serialVersionUID = 4891123559980662463L;
    
    public String getCreateTimeStart() {
        return createTimeStart;
    }
    
    public UnpItemType setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
        return this;
    }
    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }
    
    public UnpItemType setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
        return this;
    }
    
    public String getModifyTimeEnd() {
        return modifyTimeEnd;
    }
    
    public UnpItemType setModifyTimeEnd(String modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
        return this;
    }
    
    public String getModifyTimeStart() {
        return modifyTimeStart;
    }
    
    public UnpItemType setModifyTimeStart(String modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
        return this;
    }
    
    public Long getItemTypeNo() {
        return itemTypeNo;
    }
    
    public void setItemTypeNo(Long itemTypeNo) {
        this.itemTypeNo = itemTypeNo;
    }
    
    public Integer getOwner() {
        return owner;
    }
    
    public void setOwner(Integer owner) {
        this.owner = owner;
    }
    
    public String getGroupCode() {
        return groupCode;
    }
    
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
    
    public String getImg() {
        return img;
    }
    
    public void setImg(String img) {
        this.img = img;
    }
    
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
    
    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }
    
    public Integer getSortNo() {
        return sortNo;
    }
    
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
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
}
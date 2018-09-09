package com.tempus.gss.product.unp.api.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpGroupItemVo implements Serializable {
    
    private static final long serialVersionUID = -6971622398851019127L;
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
     * 状态（0，停用；1，启用）
     */
    private Integer status;
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
    private String modifyTimeStart;
    private String modifyTimeEnd;
    /**
     * 删除标志 0 无效 已删除 1 有效
     */
    private Integer valid;
    
    private String remark;
    //-------------------------below is from Item-------------//
    
    /**
     * 通用产品代号
     */
    private String itemCode;
    /**
     * 通用产品名称
     */
    private String itemName;
    /**
     * 小类 状态
     */
    private Integer itemValid;
    
    /**
     * 推荐价格
     */
    private BigDecimal baseAmount;
    /**
     * 图片路径
     */
    private String img;
    /**
     * 排序级别 1级最高，往后递减，若遇到同样的级别，后创建的优先
     */
    private Integer sortNo;
    
    //-------------------------below is for query-------------//
    /**
     * 查询类型 true 查询大类列表
     */
    private Boolean group;
    
    public Boolean getGroup() {
        if (this.group == null) {
            return true;
        }
        return group;
    }
    
    public UnpGroupItemVo setGroup(Boolean group) {
        this.group = group;
        return this;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public UnpGroupItemVo setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    
    public String getImg() {
        return img;
    }
    
    public UnpGroupItemVo setImg(String img) {
        this.img = img;
        return this;
    }
    
    public Integer getSortNo() {
        return sortNo;
    }
    
    public UnpGroupItemVo setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
        return this;
    }
    
    public Long getTypeNo() {
        return typeNo;
    }
    
    public UnpGroupItemVo setTypeNo(Long typeNo) {
        this.typeNo = typeNo;
        return this;
    }
    
    public Integer getOwner() {
        return owner;
    }
    
    public UnpGroupItemVo setOwner(Integer owner) {
        this.owner = owner;
        return this;
    }
    
    public String getCode() {
        return code;
    }
    
    public UnpGroupItemVo setCode(String code) {
        this.code = code;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public UnpGroupItemVo setName(String name) {
        this.name = name;
        return this;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public UnpGroupItemVo setStatus(Integer status) {
        this.status = status;
        return this;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public UnpGroupItemVo setCreator(String creator) {
        this.creator = creator;
        return this;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public UnpGroupItemVo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
    public String getCreateTimeStart() {
        return createTimeStart;
    }
    
    public UnpGroupItemVo setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
        return this;
    }
    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }
    
    public UnpGroupItemVo setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
        return this;
    }
    
    public String getModifier() {
        return modifier;
    }
    
    public UnpGroupItemVo setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }
    
    public Date getModifyTime() {
        return modifyTime;
    }
    
    public UnpGroupItemVo setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }
    
    public String getModifyTimeStart() {
        return modifyTimeStart;
    }
    
    public UnpGroupItemVo setModifyTimeStart(String modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
        return this;
    }
    
    public String getModifyTimeEnd() {
        return modifyTimeEnd;
    }
    
    public UnpGroupItemVo setModifyTimeEnd(String modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
        return this;
    }
    
    public Integer getValid() {
        return valid;
    }
    
    public UnpGroupItemVo setValid(Integer valid) {
        this.valid = valid;
        return this;
    }
    
    public String getItemCode() {
        return itemCode;
    }
    
    public UnpGroupItemVo setItemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public UnpGroupItemVo setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
    
    public Integer getItemValid() {
        return itemValid;
    }
    
    public UnpGroupItemVo setItemValid(Integer itemValid) {
        this.itemValid = itemValid;
        return this;
    }
    
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
    
    public UnpGroupItemVo setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }
}

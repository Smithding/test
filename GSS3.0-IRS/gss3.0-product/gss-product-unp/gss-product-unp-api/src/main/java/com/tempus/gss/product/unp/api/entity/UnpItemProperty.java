package com.tempus.gss.product.unp.api.entity;

import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpItemProperty implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 特定的产品ID
     */
    private Long itemNo;
    /**
     * 此字段是,0为大类准备(ITEM_NO填大类的ID)还是,1为小类准备(ITEM_NO填小类ID)的
     *
     * @see EUnpConstant.ForType
     */
    private Integer forType;
    /**
     * 英文名
     */
    private String eName;
    /**
     * 中文名
     */
    private String cName;
    /**
     * 数据模式或者提示信息
     */
    private String pattern;
    /**
     * 排序级别 1级最高，往后递减，若遇到同样的级别，后创建的优先
     */
    private Integer sortNo;
    /**
     * 数据类型:1文本;2数字```
     *
     * @see EUnpConstant.DataType
     */
    private Integer type;
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
    
    private static final long serialVersionUID = 4154205820747421247L;
    
    public Integer getForType() {
        return forType;
    }
    
    public void setForType(Integer forType) {
        this.forType = forType;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getItemNo() {
        return itemNo;
    }
    
    public void setItemNo(Long itemNo) {
        this.itemNo = itemNo;
    }
    
    public String geteName() {
        return eName;
    }
    
    public void seteName(String eName) {
        this.eName = eName;
    }
    
    public String getcName() {
        return cName;
    }
    
    public void setcName(String cName) {
        this.cName = cName;
    }
    
    public String getPattern() {
        return pattern;
    }
    
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    
    public Integer getSortNo() {
        return sortNo;
    }
    
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
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
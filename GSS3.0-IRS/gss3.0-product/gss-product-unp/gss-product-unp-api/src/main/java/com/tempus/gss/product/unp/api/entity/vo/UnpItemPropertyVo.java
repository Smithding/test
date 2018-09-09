package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangBro
 */
public class UnpItemPropertyVo implements Serializable {
    
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
    private String createTimeStart;
    private String createTimeEnd;
    /**
     * 状态（0，停用；1，启用）
     */
    private Integer valid;
    /**
     * 通用产品-类型代码
     */
    private String code;
    /**
     * 通用产品-类型名称
     */
    private String name;
    
    private static final long serialVersionUID = 770152541861231962L;
    
    public String getCode() {
        return code;
    }
    
    public UnpItemPropertyVo setCode(String code) {
        this.code = code;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public UnpItemPropertyVo setName(String name) {
        this.name = name;
        return this;
    }
    
    public Integer getForType() {
        return forType;
    }
    
    public String getCreateTimeStart() {
        return createTimeStart;
    }
    
    public UnpItemPropertyVo setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
        return this;
    }
    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }
    
    public UnpItemPropertyVo setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
        return this;
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
    
    public Integer getValid() {
        return valid;
    }
    
    public void setValid(Integer valid) {
        this.valid = valid;
    }
    
}
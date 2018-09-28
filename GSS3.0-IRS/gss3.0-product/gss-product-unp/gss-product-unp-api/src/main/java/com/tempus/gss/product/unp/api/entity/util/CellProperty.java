package com.tempus.gss.product.unp.api.entity.util;

import java.io.Serializable;

/**
 * @author ZhangBro
 * <p>
 * 报表工具 单元格属性
 */
public class CellProperty implements Serializable {
    private static final long serialVersionUID = -331512110586480823L;
    private Integer index;
    private String name;
    private String value;
    private Boolean isConvert;
    
    public Integer getIndex() {
        return index;
    }
    
    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Boolean getConvert() {
        if (this.isConvert == null) {
            return false;
        }
        return isConvert;
    }
    
    public void setConvert(Boolean convert) {
        isConvert = convert;
    }
}

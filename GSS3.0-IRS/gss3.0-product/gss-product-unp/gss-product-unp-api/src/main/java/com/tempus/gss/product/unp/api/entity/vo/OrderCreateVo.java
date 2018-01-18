/**
 * OrderCreateVo.java
 * com.tempus.gss.product.unp.api.entity.vo
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2017年2月24日 		niepeng
 * <p>
 * Copyright (c) 2017, TNT All Rights Reserved.
 */

package com.tempus.gss.product.unp.api.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tempus.gss.product.unp.api.entity.UnpOrder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * ClassName:OrderCreateVo
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author niepeng
 * @Date 2017年2月24日        上午9:18:23
 * @see
 * @since Ver 1.1
 */
@JsonInclude(Include.NON_NULL)
@Alias("unpOrderCreateVo")
public class OrderCreateVo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public UnpOrder unpOrder;
    
    public Integer unpType;
    
    public String unpTypeName;
    
    public String getUnpTypeName() {
        return unpTypeName;
    }
    
    public void setUnpTypeName(String unpTypeName) {
        this.unpTypeName = unpTypeName;
    }
    
    public Integer getUnpType() {
        return unpType;
    }
    
    public void setUnpType(Integer unpType) {
        this.unpType = unpType;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public UnpOrder getUnpOrder() {
        
        return unpOrder;
    }
    
    public void setUnpOrder(UnpOrder unpOrder) {
        
        this.unpOrder = unpOrder;
    }
}


package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;

import java.io.Serializable;
import java.util.List;

public class UnpOrderCreateVo implements Serializable {
    private UnpBuy unpBuy;
    private List<UnpSaleItem> saleItems;
    private List<UnpBuyItem> buyItems;
    
    /**
     * 交易单号
     */
    private Long traNo;
    /**
     * 客户ID, 外键
     */
    private Long customerNo;
    /**
     * 客户类型, 外键
     */
    private Long customerType;
    
    /**
     * 供应商ID
     */
    private Long supplierId;
    /**
     * 供应商类型
     */
    private Long supplierType;
    /**
     * 备注
     */
    private String saleRemark;
    /**
     * 备注
     */
    private String buyRemark;
}

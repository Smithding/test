package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import java.io.Serializable;
import java.util.List;

/**
 * @author ZhangBro
 */
public class UnpOrderUpdateVo implements Serializable {
    private static final long serialVersionUID = 4261705459327729774L;
    private UnpSale unpSale;
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
    /**
     * 此字段是判断当前操作是;支付 退 取消
     *
     * @see EUnpConstant.Opertion
     */
    private  Integer operationType;

    public UnpBuy getUnpBuy() {
        return unpBuy;
    }
    
    public UnpSale getUnpSale() {
        return unpSale;
    }
    
    public void setUnpSale(UnpSale unpSale) {
        this.unpSale = unpSale;
    }
    
    public void setUnpBuy(UnpBuy unpBuy) {
        this.unpBuy = unpBuy;
    }
    
    public List<UnpSaleItem> getSaleItems() {
        return saleItems;
    }
    
    public void setSaleItems(List<UnpSaleItem> saleItems) {
        this.saleItems = saleItems;
    }
    
    public List<UnpBuyItem> getBuyItems() {
        return buyItems;
    }
    
    public void setBuyItems(List<UnpBuyItem> buyItems) {
        this.buyItems = buyItems;
    }
    
    public Long getTraNo() {
        return traNo;
    }

    public void setTraNo(Long traNo) {
        this.traNo = traNo;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public Long getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Long customerType) {
        this.customerType = customerType;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Long supplierType) {
        this.supplierType = supplierType;
    }

    public String getSaleRemark() {
        return saleRemark;
    }
    
    public void setSaleRemark(String saleRemark) {
        this.saleRemark = saleRemark;
    }
    
    public String getBuyRemark() {
        return buyRemark;
    }
    
    public void setBuyRemark(String buyRemark) {
        this.buyRemark = buyRemark;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}

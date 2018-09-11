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
public class UnpOrderVo implements Serializable {
    private static final long serialVersionUID = 4261705459327729774L;
    private UnpSale unpSale;
    private UnpBuy unpBuy;
    private List<UnpSaleItem> saleItems;
    private List<UnpBuyItem> buyItems;
    
    /**
     * 此字段是判断当前操作是;支付 退 取消
     *
     * @see EUnpConstant.Opertion
     */
    private Integer operationType;
    
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
    
    public Integer getOperationType() {
        return operationType;
    }
    
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}

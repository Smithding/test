package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhangBro
 */
public class UnpOrderCreateVo implements Serializable {
    private static final long serialVersionUID = 4261705459327729774L;
    private UnpSale unpSale;
    private UnpBuy unpBuy;
    private List<UnpSaleItem> saleItems;
    private List<UnpBuyItem> buyItems;
    
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
}

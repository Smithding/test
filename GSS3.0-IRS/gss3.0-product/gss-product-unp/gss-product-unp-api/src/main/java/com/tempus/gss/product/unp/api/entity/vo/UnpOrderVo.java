package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.vo.Agent;

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
    private Agent agent;
    private Integer payWayCode;
    private String payAccount;
    private String payRemark;
    
    public UnpOrderVo(UnpSale unpSale, UnpBuy unpBuy, List<UnpSaleItem> saleItems, List<UnpBuyItem> buyItems, Integer operationType) {
        this.unpSale = unpSale;
        this.unpBuy = unpBuy;
        this.saleItems = saleItems;
        this.buyItems = buyItems;
        this.operationType = operationType;
    }
    
    public UnpOrderVo(UnpSale unpSale, UnpBuy unpBuy) {
        this.unpSale = unpSale;
        this.unpBuy = unpBuy;
    }
    
    public UnpOrderVo(UnpSale unpSale, UnpBuy unpBuy, List<UnpSaleItem> saleItems, List<UnpBuyItem> buyItems) {
        this.unpSale = unpSale;
        this.unpBuy = unpBuy;
        this.saleItems = saleItems;
        this.buyItems = buyItems;
    }
    
    public UnpOrderVo() {
    
    }
    
    /**
     * 此字段是判断当前操作是;支付 退 取消
     *
     * @see EUnpConstant.Opertion
     */
    private Integer operationType;
    
    public Integer getPayWayCode() {
        return payWayCode;
    }
    
    public void setPayWayCode(Integer payWayCode) {
        this.payWayCode = payWayCode;
    }
    
    public String getPayAccount() {
        return payAccount;
    }
    
    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }
    
    public String getPayRemark() {
        return payRemark;
    }
    
    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }
    
    public Agent getAgent() {
        return agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    
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

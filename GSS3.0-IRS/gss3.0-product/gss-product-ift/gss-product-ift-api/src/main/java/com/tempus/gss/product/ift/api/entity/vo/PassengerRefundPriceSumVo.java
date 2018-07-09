package com.tempus.gss.product.ift.api.entity.vo;/*
 * 改签分单任务
 *
 * @author fangzhuangzhan
 * @create 14:25 2018/7/6
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class PassengerRefundPriceSumVo implements Serializable {

    private static final long serialVersionUID = -2506506835500761276L;

    //销售机票结算价
    private BigDecimal allSalePrice;
    //退款费
    private BigDecimal allRefundPrice;
    //销售退款（实际退款）
    private BigDecimal allFactRefundAccount;
    //采购退款
    private BigDecimal allBuyRefundAccount;
    //税费
    private BigDecimal allTax;
    //净结算价
    private BigDecimal allSettlePrice;
    //CHARGE_PROFIT
    private BigDecimal chargeProfit;

    public BigDecimal getAllSalePrice() {
        return allSalePrice;
    }

    public void setAllSalePrice(BigDecimal allSalePrice) {
        this.allSalePrice = allSalePrice;
    }

    public BigDecimal getAllRefundPrice() {
        return allRefundPrice;
    }

    public void setAllRefundPrice(BigDecimal allRefundPrice) {
        this.allRefundPrice = allRefundPrice;
    }

    public BigDecimal getAllFactRefundAccount() {
        return allFactRefundAccount;
    }

    public void setAllFactRefundAccount(BigDecimal allFactRefundAccount) {
        this.allFactRefundAccount = allFactRefundAccount;
    }

    public BigDecimal getAllBuyRefundAccount() {
        return allBuyRefundAccount;
    }

    public void setAllBuyRefundAccount(BigDecimal allBuyRefundAccount) {
        this.allBuyRefundAccount = allBuyRefundAccount;
    }

    public BigDecimal getAllTax() {
        return allTax;
    }

    public void setAllTax(BigDecimal allTax) {
        this.allTax = allTax;
    }

    public BigDecimal getAllSettlePrice() {
        return allSettlePrice;
    }

    public void setAllSettlePrice(BigDecimal allSettlePrice) {
        this.allSettlePrice = allSettlePrice;
    }

    public BigDecimal getChargeProfit() {
        return chargeProfit;
    }

    public void setChargeProfit(BigDecimal chargeProfit) {
        this.chargeProfit = chargeProfit;
    }
}


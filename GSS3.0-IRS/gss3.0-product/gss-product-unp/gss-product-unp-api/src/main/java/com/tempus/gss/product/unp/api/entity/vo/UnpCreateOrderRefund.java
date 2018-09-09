package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuyRefund;
import com.tempus.gss.product.unp.api.entity.UnpBuyRefundItem;
import com.tempus.gss.product.unp.api.entity.UnpSaleRefund;
import com.tempus.gss.product.unp.api.entity.UnpSaleRefundItem;

import java.io.Serializable;
import java.util.List;

public class UnpCreateOrderRefund implements Serializable {

    private UnpSaleRefund unpSaleRefund;
    private List<UnpSaleRefundItem> unpSaleRefundItemList;
    private UnpBuyRefund unpBuyRefund;
    private List<UnpBuyRefundItem> unpBuyRefundItemList;

    public UnpSaleRefund getUnpSaleRefund() {
        return unpSaleRefund;
    }

    public void setUnpSaleRefund(UnpSaleRefund unpSaleRefund) {
        this.unpSaleRefund = unpSaleRefund;
    }

    public List<UnpSaleRefundItem> getUnpSaleRefundItemList() {
        return unpSaleRefundItemList;
    }

    public void setUnpSaleRefundItemList(List<UnpSaleRefundItem> unpSaleRefundItemList) {
        this.unpSaleRefundItemList = unpSaleRefundItemList;
    }

    public UnpBuyRefund getUnpBuyRefund() {
        return unpBuyRefund;
    }

    public void setUnpBuyRefund(UnpBuyRefund unpBuyRefund) {
        this.unpBuyRefund = unpBuyRefund;
    }

    public List<UnpBuyRefundItem> getUnpBuyRefundItemList() {
        return unpBuyRefundItemList;
    }

    public void setUnpBuyRefundItemList(List<UnpBuyRefundItem> unpBuyRefundItemList) {
        this.unpBuyRefundItemList = unpBuyRefundItemList;
    }
}

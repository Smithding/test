package com.tempus.gss.product.unp.api.entity.vo;

import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;

import java.util.List;

public class UnpOrderCreateVo {
    private UnpSale unpSale;
    private UnpBuy unpBuy;
    private List<UnpSaleItem> saleItems;
    private List<UnpBuyItem> buyItems;
    
}

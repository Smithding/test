package com.tempus.gss.product.ift.dao;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.BuyChangeExt;

@Component
public interface BuyChangeExtDao extends BaseDao<BuyChangeExt, Object> {

    BuyChangeExt selectBySaleChangeNoFindOne(Long saleChangeNo);

    void  updateBuyRemarkBySelectBuyChangeNo(BuyChangeExt buyChangeExt);
}


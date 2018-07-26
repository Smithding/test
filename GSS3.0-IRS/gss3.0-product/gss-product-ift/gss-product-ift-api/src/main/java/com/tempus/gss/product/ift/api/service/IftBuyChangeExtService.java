package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.BuyChangeExt;

/**
 * 国际采购变更单服务接口
 */
public interface IftBuyChangeExtService {
    //获得采购变更单扩展信息
    public BuyChangeExt queryBuyChgeExtByNo(Long saleChangeNo);

    public void updateBuyChangeExt(BuyChangeExt buyChangeExt);

    public void updateBuyChangeByChangeNo(RequestWithActor<Long> requestWithActor, String status);
}

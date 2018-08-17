package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.iftVo.IftRepayVo;

/**
 * 销售，改签订单重审后重新支付接口
 */
public interface IIftOrderRePayService {
    public void rePay(RequestWithActor<IftRepayVo> requestWithActor);
}

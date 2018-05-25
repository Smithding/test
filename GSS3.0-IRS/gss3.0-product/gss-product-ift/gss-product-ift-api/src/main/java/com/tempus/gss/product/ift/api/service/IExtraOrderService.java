package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;
import com.tempus.gss.vo.Agent;

public interface IExtraOrderService {
    /**
     * 创建杂费单
     * @param agent
     * @param requestWithActor
     */
    public void createExtraOrder(Agent agent, RequestWithActor<OrderCreateVo> requestWithActor);

    /**
     * 更新已经支付完的杂费单状态为已完结
     *
     */
    public void updateExtraOrderStatus();


}

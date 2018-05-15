package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;
import com.tempus.gss.vo.Agent;

public interface IExtraOrderService {
    public void createExtraOrder(Agent agent, RequestWithActor<OrderCreateVo> requestWithActor);
}

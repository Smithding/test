package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.controller.Result;
import com.tempus.gss.order.entity.DifferenceOrder;
import com.tempus.gss.product.common.entity.RequestWithActor;
public interface IExtraOrderService {
    /**
     * 创建杂费单
     * @param requestWithActor
     */
    public Result createExtraOrder(RequestWithActor<DifferenceOrder> requestWithActor);

    /**
     * 更新已经支付完的杂费单状态为已完结
     *
     */
    public void updateExtraOrderStatus(RequestWithActor<DifferenceOrder> requestWithActor) throws Exception;

    /**
     * 杂费冲单时修改原单的状态为审核未通过已冲单  2
     * @param originDifferenceOrderNo
     * @param differenceType
     * @throws Exception
     */
    public void updateExtraAuditStatus(Long originDifferenceOrderNo, Integer differenceType) throws Exception;

    public DifferenceOrder getDifferenceOrderByDifferenceOrderNo(Long differenceOrderNo) throws Exception;

}

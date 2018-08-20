package com.tempus.gss.product.unp.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.vo.Agent;

/**
 * @author ZhangBro
 * 此service的方法不会抛异常，所有异常信息以消息方式传递
 * 但不排除调用超时的情况
 * @see UnpResult
 */
public interface UnpOrderService {
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createOrder(Agent agent, RequestWithActor<UnpOrderCreateVo> request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createSale(Agent agent, RequestWithActor<UnpOrderCreateVo> request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createBuy(Agent agent, RequestWithActor<UnpOrderCreateVo> request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createSaleRefund(Agent agent, RequestWithActor<UnpOrderCreateVo> request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createBuyRefund(Agent agent, RequestWithActor<UnpOrderCreateVo> request);
    
}

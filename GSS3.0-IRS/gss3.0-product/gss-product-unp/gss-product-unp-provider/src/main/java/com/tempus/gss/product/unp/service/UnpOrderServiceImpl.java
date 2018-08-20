package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.vo.Agent;

/**
 * @author ZhangBro
 */
@Service
public class UnpOrderServiceImpl extends BaseUnpService implements UnpOrderService {
    @Override
    public UnpResult<UnpSale> createOrder(Agent agent, RequestWithActor<UnpOrderCreateVo> request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createSale(Agent agent, RequestWithActor<UnpOrderCreateVo> request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createBuy(Agent agent, RequestWithActor<UnpOrderCreateVo> request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createSaleRefund(Agent agent, RequestWithActor<UnpOrderCreateVo> request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createBuyRefund(Agent agent, RequestWithActor<UnpOrderCreateVo> request) {
        return null;
    }
}

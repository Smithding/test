package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyRefund;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpCreateOrderRefund;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderUpdateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
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
    UnpResult<UnpSale> createOrder(Agent agent, UnpOrderCreateVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createSale(Agent agent, UnpOrderCreateVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpBuy> createBuy(Agent agent, UnpOrderCreateVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createSaleRefund(Agent agent, UnpOrderCreateVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderCreateVo
     * @return UnpResult
     */
    UnpResult<UnpBuyRefund> createBuyRefund(Agent agent, UnpCreateOrderRefund request);
    
    /**
     * @param wrapper
     *         wrapper
     * @param param
     *         param
     * @return Page
     */
    Page<UnpSale> querySaleOrderList(Page<UnpSale> wrapper, UnpOrderVo param);
    /*
    * @param agent
    * @param request
    * */
    UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderUpdateVo request);
}

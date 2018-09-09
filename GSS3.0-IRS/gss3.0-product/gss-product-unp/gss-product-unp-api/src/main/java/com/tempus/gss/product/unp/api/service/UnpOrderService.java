package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyRefund;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.UnpSaleItem;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpCreateOrderRefund;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderUpdateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * @author ZhangBro
 * 此service的方法不会抛异常，所有异常信息以消息方式传递
 * 但不排除调用超时的情况
 * @see UnpResult
 */
public interface UnpOrderService {
    /** <<<<<<<<<<<<<<<<<<<<<<<【CREATE】>>>>>>>>>>>>>>>>>>>>>>>*/
    
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
    
    /**<<<<<<<<<<<<<<<<<<<<<<<【CREATE】【UPDATE】>>>>>>>>>>>>>>>>>>>>>>>*/
    
    /**
     * @param agent
     * @param request
     */
    UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderUpdateVo request);
    /**<<<<<<<<<<<<<<<<<<<<<<<【UPDATE】【SELECT】>>>>>>>>>>>>>>>>>>>>>>>*/
    
    /**
     * 查询一个销售订单详细信息，包括销售单下的明细信息
     *
     * @param params
     *         UnpOrderCreateVo
     * @param params
     *         saleOrderNo     销售单号
     * @param params
     *         buyOrderNo     采购单号
     * @param params
     *         traNo        交易单号
     * @param params
     *         thirdBusNo    第三方业务单号
     * @param params
     *         changeType    变更类型
     * @return UnpResult
     * @see EUnpConstant.ChangeType
     */
    UnpSale getSaleOrderInfo(UnpOrderVo params);
    
    /**
     * 根据条件查询明细表集合
     *
     * @param params
     *         saleOrderNo     销售单号
     * @param params
     *         buyOrderNo     采购单号
     * @param params
     *         traNo        交易单号
     * @param params
     *         thirdBusNo    第三方业务单号
     * @return UnpResult
     */
    List<UnpSaleItem> getItems(UnpOrderVo params);
    
    /**
     * @param itemNo
     *         itemNo
     *         根据明细单编号查询明细单
     * @return UnpResult
     */
    UnpSaleItem getItem(Long itemNo);
    
    /**
     * @param wrapper
     * @param param
     * @return Page
     */
    Page<UnpSale> querySaleOrderList(Page<UnpSale> wrapper, UnpOrderVo param);
    
    /**<<<<<<<<<<<<<<<<<<<<<<<【SELECT】【】>>>>>>>>>>>>>>>>>>>>>>>*/
    
}

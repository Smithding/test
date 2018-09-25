package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.unp.api.entity.*;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpRefundVo;
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
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createOrder(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpSale> createSale(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpBuy> createBuy(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     *         agent
     * @param saleOrderNo
     * @return UnpResult
     */
    UnpResult<Object> createRefund(Agent agent, Long saleOrderNo);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpSaleRefund> createSaleRefund(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpBuyRefund> createBuyRefund(Agent agent, UnpRefundVo request, UnpSaleRefund unpSaleRefund);
    
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @param unpSaleRefund
     * @return UnpResult
     */
    UnpResult<UnpBuyRefund> createBuyRefund(Agent agent, UnpOrderVo request, UnpSaleRefund unpSaleRefund);
    
    /** <<<<<<<<<<<<<<<<<<<<<<<【CREATE】【UPDATE】>>>>>>>>>>>>>>>>>>>>>>> */
    /**
     * @param agent
     *         agent
     * @param request
     *         UnpOrderVo
     * @return UnpResult
     */
    UnpResult<UnpSale> updateSale(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     * @param request
     */
    UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderVo request);
    
    /**
     * @param agent
     * @param request
     *         1、 参数传进来的都是需要修改的目标状态
     *         2、 增删改查的SQL尽量只各写一条
     */
    UnpResult<UnpSaleRefund> updateSale(Agent agent, UnpRefundVo request);
    
    /**
     * @param agent
     * @param request
     */
    UnpResult<UnpBuyRefund> updateBuy(Agent agent, UnpRefundVo request);
    
    /**
     * @param agent
     * @param saleOrderNo
     * @return
     */
    UnpResult<Object> cancel(Agent agent, Long... saleOrderNo);
    /**<<<<<<<<<<<<<<<<<<<<<<<【UPDATE】【SELECT】>>>>>>>>>>>>>>>>>>>>>>>*/
    
    /**
     * 查询一个销售订单详细信息，包括销售单下的明细信息
     *
     * @param params
     *         UnpOrderVo
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
    UnpSale getSaleOrderInfo(UnpOrderQueryVo params);
    
    UnpSaleRefund getSaleRefundOrderInfo(UnpOrderQueryVo params);
    
    UnpBuy getBuyOrderInfo(UnpOrderQueryVo params);
    
    UnpBuyRefund getBuyRefundOrderInfo(UnpOrderQueryVo params);
    
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
    List<UnpSaleItem> getItems(UnpOrderQueryVo params);
    
    List<UnpBuyItem> getBuyItems(UnpOrderQueryVo params);
    
    List<UnpSaleRefundItem> getSaleRefundItems(UnpOrderQueryVo params);
    
    List<UnpBuyRefundItem> getBuyRefundItems(UnpOrderQueryVo params);
    
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
    Page<UnpSale> querySaleOrderList(Page<UnpSale> wrapper, UnpOrderQueryVo param);
    
    /**
     * @param wrapper
     * @param param
     * @return Page
     */
    Page<UnpBuy> queryBuyOrderList(Page<UnpBuy> wrapper, UnpOrderQueryVo param);
    
    /**
     * @param param
     * @return Page
     */
    Page<UnpSaleRefund> querySaleOrderRefundList(Page<UnpSaleRefund> wrapper, UnpOrderQueryVo param);
    
    /**
     * @param param
     * @return Page
     */
    Page<UnpBuyRefund> queryBuyOrderRefundList(Page<UnpBuyRefund> wrapper, UnpOrderQueryVo param);
    
    /**
     * @param param
     * @return Page
     * SaleChangeNo 销售变更单号
     * BuyChangeNo 采购变更单号
     */
    UnpSaleRefund querySaleOrderRefund(UnpOrderQueryVo param);
    
    /**
     * @param param
     * @return Page
     */
    UnpBuyRefund queryBuyOrderRefund(UnpOrderQueryVo param);
    
    /** <<<<<<<<<<<<<<<<<<<<<<<【SELECT】【PAY】>>>>>>>>>>>>>>>>>>>>>>> */
    
    /**
     * @return Page
     */
    void payBuy(Agent agent, UnpSale unpSale, Integer payWay, String paymentAccount, String receivableAccount) throws Exception;
    
    /**
     * @param saleRefundNo
     *         销售退单号
     * @return Page
     */
    UnpResult<Object> saleRefund(Agent agent, Long saleRefundNo) throws GSSException;
    
    /**
     * @param buyRefundNo
     *         退单号
     * @return Page
     */
    UnpResult<Object> buyRefund(Agent agent, Long buyRefundNo) throws GSSException;
}

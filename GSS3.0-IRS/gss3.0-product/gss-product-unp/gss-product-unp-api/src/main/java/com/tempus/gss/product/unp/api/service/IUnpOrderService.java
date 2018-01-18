/**
 * IUnpOrderService.java
 * com.tempus.gss.product.unp.api.service
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2017年3月10日 		niepeng
 * <p>
 * Copyright (c) 2017, TNT All Rights Reserved.
 */

package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpOrder;
import com.tempus.gss.product.unp.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:IUnpOrderService
 * Function: 通用产品订单服务
 *
 * @author niepeng
 * @version
 * @since Ver 1.1
 * @Date 2017年3月10日        上午8:55:47
 *
 * @see
 *
 */
public interface IUnpOrderService {
    /**
     *
     * createOrder:创建订单
     *
     * @param @param
     *            createOrderVo
     * @param @return
     *            设定文件
     * @return 销售单编号
     * @throws @since
     *             CodingExample Ver 1.1
     */
    Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception;
    
    /**
     * 根据订单编号查询订单
     *
     * @param requestWithActor
     * @return
     */
    UnpOrder queryOrderByNo(RequestWithActor<Long> requestWithActor);
    
    /**
     * queryOrderList:分页查询订单列表
     * @param  @param page
     * @param  @param pageRequest
     * @param  @return    设定文件
     * @return Page<UnpOrder>    DOM对象
     * @throws
     * @since CodingExample　Ver 1.1
     */
    Page<UnpOrder> queryOrderList(Page<UnpOrder> page, RequestWithActor<UnpOrderVo> pageRequest);
    
    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteOrder(RequestWithActor<Long> id) throws Exception;
    
    /**
     *
     * cancelOrder:取消订单
     *
     * @param      订单号
     * @return void    DOM对象
     * @throws
     * @since CodingExample　Ver 1.1
     */
    boolean cancelOrder(RequestWithActor<Long> orderNo);
    
    /**
     * updateOrderStatus:根据订单号修改订单状态
     *
     * @param  @param orderNo
     * @param  @param status
     * @param  @return    设定文件
     * @return boolean    DOM对象
     * @throws
     * @since CodingExample　Ver 1.1
     */
    boolean updateOrderStatus(RequestWithActor<Long> orderNo, Integer status);
    
    /**
     * 根据订单号修改支付状态
     * @param requestWithActor
     * @param status
     * @return
     */
    boolean updateOrderPayStatus(RequestWithActor<UnpOrderVo> requestWithActor);
    
    /**
     * pay:订单支付
     *
     * @param  @param requestWithActor
     * @param  @return
     * @param  @throws Exception    设定文件
     * @return boolean    DOM对象
     * @throws
     * @since CodingExample　Ver 1.1
     */
    boolean pay(RequestWithActor<UnpOrderVo> requestWithActor) throws Exception;
}


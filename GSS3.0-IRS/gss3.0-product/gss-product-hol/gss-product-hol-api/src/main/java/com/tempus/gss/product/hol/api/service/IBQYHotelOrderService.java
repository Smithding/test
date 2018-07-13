package com.tempus.gss.product.hol.api.service;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelOrderRes;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BQYPushOrderInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.vo.Agent;

/**
 * BQY酒店订单接口
 */
public interface IBQYHotelOrderService {

	/**
	 * 创建订单
	 * @param agent
	 * @param orderReq
	 * @return
	 */
	//HotelOrder createOrder(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq); 
	
	/**
	 * 创建酒店订单
	 * @param agent
	 * @param orderReq
	 * @param orderCreateReq
	 * @return
	 */
	OrderCreateReq hotelReserve(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq);
	
	/**
	 * 取消订单
	 * @param agent
	 * @param orderCancelBeforePayReq
	 * @return
	 */
	CancelOrderRes cancelOrder(Agent agent, CancelOrderBeforePayReq orderCancelBeforePayReq); 
	
	/**
	 * 酒店订单推送
	 * @param agent
	 * @param bqyPushOrderInfo
	 * @return
	 */
	Boolean bqyPushOrderInfo(Agent agent, BQYPushOrderInfo bqyPushOrderInfo);
}

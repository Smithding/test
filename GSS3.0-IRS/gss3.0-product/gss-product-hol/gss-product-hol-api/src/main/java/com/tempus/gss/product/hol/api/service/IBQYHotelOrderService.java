package com.tempus.gss.product.hol.api.service;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.tc.CancelOrderBeforePayReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderCreateReq;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelOrderRes;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfomationDetail;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BQYPushOrderInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.vo.Agent;

import java.util.concurrent.Future;

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
	HotelOrder createOrder(Agent agent, CreateOrderReq orderReq, OrderCreateReq orderCreateReq, RoomInfo roomInfo) throws GSSException;
	
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
	Boolean bqyPushOrderInfo(Agent agent, BQYPushOrderInfo bqyPushOrderInfo) throws GSSException;

	/**
	 * 异步获取bqy酒店详情
	 * @param agent
	 * @param hotelOrderNo
	 * @return
	 */
    Future<OrderInfomationDetail> futureOrderDetailInfo(Agent agent, String hotelOrderNo);

	/**
	 * 更新订单状态
	 * @param agent
	 * @param orderNo
	 * @return
	 */
    Boolean updateOrderStatus(Agent agent, String orderNo);
}

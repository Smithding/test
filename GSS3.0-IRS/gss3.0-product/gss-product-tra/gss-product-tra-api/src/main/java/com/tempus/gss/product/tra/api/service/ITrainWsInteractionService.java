package com.tempus.gss.product.tra.api.service;

import com.tempus.gss.product.tra.api.entity.*;

/**
 *
 * @ClassName ITicketWsInteraction.java
 * @Description
 * @Author Administrator
 * @CreateDate 2014年11月7日
 * @Version
 * 
 */
public interface ITrainWsInteractionService {

	/**
	 * 方法描述:  查询火车票
	 * 作    者： Administrator
	 * 日    期： 2016年7月5日-上午9:26:55
	 * @param request
	 * @return
	 * @throws Exception 
	 * 返回类型： Future<TrainSearchResponse>
	*/
	public TrainResponse TrainSearch(QueryTrainRequest request) throws Exception;

	/**
	 * 方法描述:  火车票下单
	 * 作    者： Administrator
	 * 日    期： 2016年7月11日-上午11:04:23
	 * @param request
	 * @return
	 * @throws Exception 
	 * 返回类型： OrderResponse
	*/
	public BookTicketsResponse orderAdd(String request) throws Exception;
	
	
	/**
	 * 火车票退票
	 */
	public CyOrderResponse orderReturn(OrderReturnReq request) throws Exception;



	/**
	 * 火车票取消
	 */
	public CyOrderResponse cancelOrder(String orderNumber)throws Exception;

	/**
	 * 根据车次查详情
	 */
	public OrderDetailResponse orderDetailForCy(String orderId)throws Exception;
	/**
	 * 申请出票
	 */
	public CyOrderResponse applyOrder(String orderId)throws Exception;
	/**
	 * 查询车次
	 */
	public TrainNoResponse queryCheci(QueryCheciRequest request)throws Exception;


}

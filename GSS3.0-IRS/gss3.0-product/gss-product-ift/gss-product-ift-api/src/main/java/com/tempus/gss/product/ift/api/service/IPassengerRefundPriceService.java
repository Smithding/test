package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;

/**
 * 
 */
public interface IPassengerRefundPriceService  {
	/**
	 * 
	 * 创建
	 * @param 
	 * @return
	 */
	int createPassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice);
	
	/**
	 * 根据saleChangeNo查询
	 * @param passengerChangerPrice
	 * @return
	 */
	PassengerRefundPrice getPassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice);
	
	/**
	 * 修改
	 */
	int updatePassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice);
	
	/**
	 * 列表查询
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	List<PassengerRefundPrice> queryPassengerRefundPriceList(RequestWithActor<PassengerRefundPrice> requestWithActor);
}

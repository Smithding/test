package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.vo.ChangePriceRequest;

/**
 * 
 */
public interface IPassengerChangePriceService  {
	/**
	 * 
	 * 创建
	 * @param 
	 * @return
	 */
	int createPassengerChangePrice(RequestWithActor<ChangePriceRequest> requestWithActor);
	
	/**
	 * 根据saleChangeNo查询
	 * @param passengerChangerPrice
	 * @return
	 */
	List<PassengerChangePrice> getChangePriceList(RequestWithActor<Long> ChangePriceNo);
	/**
	 * 修改
	 */
	int updatePassengerChangePrice(RequestWithActor<PassengerChangePrice> passengerChangePrice);
}

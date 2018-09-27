package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.product.ift.api.entity.vo.PassengerRefundPriceVo;

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
	 * @param passengerRefundPrice
	 * @return
	 */
	PassengerRefundPrice getPassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice);
	
	/**
	 * 修改
	 */
	int updatePassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice);
	
	/**
	 * 列表查询
	 * @param requestWithActor
	 * @return
	 */
	List<PassengerRefundPrice> queryPassengerRefundPriceList(RequestWithActor<PassengerRefundPrice> requestWithActor);

	/**
	 * 批量更新乘客变更价格
	 * @param requestWithActor
	 */
	void batchUpdatePassengerRefundPrice(RequestWithActor<PassengerRefundPriceVo> requestWithActor);
}

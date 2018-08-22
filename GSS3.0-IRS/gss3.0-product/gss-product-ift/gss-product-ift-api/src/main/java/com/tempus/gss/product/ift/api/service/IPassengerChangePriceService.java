package com.tempus.gss.product.ift.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

	PassengerChangePrice getPassengerChangePriceBypassengerNoAndSaleChangeNo(Long saleChangeNo,Long passengerNo);

    PassengerChangePrice getLastChangeByPgerNoAndSaleOrderNo(Long passengerNo, Long saleOrderNo);

	/**
	 * 获取该乘客最新的已改签成功的信息(payStatus in 3，4)
	 * @param passengerNo
	 * @param saleOrderNo
	 * @return
	 */
	PassengerChangePrice getChangePassgerByPgerNo(Long passengerNo, Long saleOrderNo);

	/**
	 * 获取该乘客所有的改签差价和改签税的累积总和（多次改签的总和）
	 * @param passengerNo
	 * @param saleOrderNo
	 * @return
	 */
    Map<String, BigDecimal> getAllChangePriceAndChangeTax(Long passengerNo, Long saleOrderNo);
}

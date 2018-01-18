package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Passenger;

import java.util.List;

/**
 * 国际机票需求单服务接口.
 */
public interface IPassengerService  {

	/**
	 * 修改
	 * @param requestWithActor
	 * @return
	 */
	int updatePassenger(RequestWithActor<Passenger> requestWithActor);

	/**
	 * 根据乘客编号获取乘客信息
	 * @param passengerNo
	 * @return
	 */
	public Passenger getPassengerByPassengerNo(String passengerNo);

	/**
	 * 根据销售单号获取乘客信息
	 * @param saleOrderNo
	 * @return
	 */
	public List<Passenger> getPassengerBySaleOrderNo(String saleOrderNo);
}

package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.vo.PassengerVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PassengerDao extends BaseDao<Passenger, PassengerVo> {

	/*
   * 通过订单编号获取乘客集合
   * */
	public List<Passenger> selectPassengerBySaleOrderNo(Long saleOrderNo);
	
	int updateByOrderNo(Passenger passenger);

	/**
	 *  根据乘客编号获取乘客信息
	 * @param passengerNo
	 * @return
	 */
	public Passenger getPassengerByPassengerNo(String passengerNo);

	public List<Passenger> getPassengerBySaleOrderNo(String saleOrderNo);
}
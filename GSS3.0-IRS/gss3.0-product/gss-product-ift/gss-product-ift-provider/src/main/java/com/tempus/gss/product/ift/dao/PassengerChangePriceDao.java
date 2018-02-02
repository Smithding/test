package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.vo.PassengerChangePriceVo;

@Component
public interface PassengerChangePriceDao extends BaseDao<PassengerChangePrice,PassengerChangePriceVo> {
	/**
	 * 查询
	 * @param passengerChangePrice
	 * @return
	 */
	List<PassengerChangePrice> selectPricerByChangeNo(Long saleChangeNo);


	int updateByOrderNo(PassengerChangePrice passengerChangePrice);

	/***
	 * 根据改签订单编号修改数据
	 * @return
	 */
	int updateByChangeOrderNo(PassengerChangePrice passengerChangePrice);
}
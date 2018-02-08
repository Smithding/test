package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface PassengerRefundPriceDao extends BaseDao<PassengerRefundPrice, Object> {
	
	/**
	 * 查询
	 * @param passengerChangePrice
	 * @return
	 */
	PassengerRefundPrice getPassengerRefundPrice(PassengerRefundPrice passengerChangePrice);
	
	/**
	 * 列表查询
	 * @param record
	 * @return
	 */
	List<PassengerRefundPrice> queryObjByKeyList(PassengerRefundPrice record);

	/**
	 * 根据销售订单id修改数据
	 * @param passengerRefundPrice
	 * @return
	 */
	int updateByChangeOrderNo(PassengerRefundPrice passengerRefundPrice);

	/***
	 * 根据变更单号获取乘客退费明细
	 * @param saleChangeNo
	 * @return
	 */
	List<PassengerRefundPrice> selectPassengerRefundPriceBySaleOrderNo(Long saleChangeNo);
}
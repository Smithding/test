package com.tempus.gss.product.ift.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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

	/***
	 * 根据乘客号修改数据
	 * @return
	 */
	int updateByPassengerNo(PassengerChangePrice passengerChangePrice);

	PassengerChangePrice selectPricerByChangeNoAndPassengerNo(@Param("saleChangeNo") Long saleChangeNo, @Param("passengerNo") Long passengerNo);

    PassengerChangePrice selectByPgerNoAndSaleOrderNo(@Param("passengerNo")Long passengerNo, @Param("saleOrderNo")Long saleOrderNo);

	/**
	 * 获取该乘客最新的已改签成功的信息
	 * @param passengerNo
	 * @param saleOrderNo
	 * @return
	 */
	PassengerChangePrice getChangePassgerByPgerNo(@Param("passengerNo")Long passengerNo, @Param("saleOrderNo")Long saleOrderNo);

	/**
	 * 获取该乘客所有的改签差价和改签税的累积总和（多次改签的总和）
	 * @param passengerNo
	 * @param saleOrderNo
	 * @return
	 */
    Map<String,BigDecimal> getAllChangePriceAndChangeTax(@Param("passengerNo")Long passengerNo, @Param("saleOrderNo")Long saleOrderNo);
}
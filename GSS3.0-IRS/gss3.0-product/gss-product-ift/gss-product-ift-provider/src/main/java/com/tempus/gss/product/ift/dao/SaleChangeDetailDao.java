package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.SaleChangeDetail;

@Component
public interface SaleChangeDetailDao extends BaseDao<SaleChangeDetail, Object> {

	public List<SaleChangeDetail> selectBySaleChangeNo(Long saleChangeNo);

	/**
	 * 通过saleOrderDetailNo来获取  (通过saleOrderDetailNo获取的SaleChangeDetail不唯一可能报错,请使用selectBySaleOrderDetailNoAndSaleChangeNo)
	 * @param saleOrderDetailNo
	 * @return
	 */
	@Deprecated
	SaleChangeDetail selectBySaleOrderDetailNo(Long saleOrderDetailNo);

	/***
	 * 通过改签订单主表修改数据
	 * @param saleChangeDetail
	 * @return
	 */
	int updateByChangeOrderNo(SaleChangeDetail saleChangeDetail);

	/**
	 * 
	 * @param saleOrderDetailNo
	 * @param saleChangeNo
	 * @return
	 */
    SaleChangeDetail selectBySaleOrderDetailNoAndSaleChangeNo(@Param("saleOrderDetailNo") Long saleOrderDetailNo, @Param("saleChangeNo") Long saleChangeNo);

}
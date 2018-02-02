package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.SaleChangeDetail;

@Component
public interface SaleChangeDetailDao extends BaseDao<SaleChangeDetail, Object> {

	public List<SaleChangeDetail> selectBySaleChangeNo(Long saleChangeNo);

	/**
	 * 通过saleOrderDetailNo来获取
	 * @param saleOrderDetailNo
	 * @return
	 */
	SaleChangeDetail selectBySaleOrderDetailNo(Long saleOrderDetailNo);

	/***
	 * 通过改签订单主表修改数据
	 * @param saleChangeDetail
	 * @return
	 */
	int updateByChangeOrderNo(SaleChangeDetail saleChangeDetail);
}
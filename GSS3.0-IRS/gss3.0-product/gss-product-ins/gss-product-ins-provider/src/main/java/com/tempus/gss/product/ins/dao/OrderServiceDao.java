package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderExtVo;

@Component("insOrderServiceDao")
public interface OrderServiceDao extends InsBaseDao<SaleOrderExt, SaleOrderExtVo> {

	/**
	 * 通过保单号获取订单
	 * @param policyNo
	 * @return
	 */
	SaleOrderExt selectByPolicyNo(String policyNo);
	/**
	 * 通过交易单号获取订单
	 * @param policyNo
	 * @return
	 */
	List<SaleOrderExt> selectByTransaction(String transactionNo);
	/**
	 * 根据条件查询保险销售单
	 * @param saleOrderExtVo
	 * @return
	 */
	List<SaleOrderExtVo> selectBySaleOrderExtVo(SaleOrderExtVo saleOrderExtVo);
}

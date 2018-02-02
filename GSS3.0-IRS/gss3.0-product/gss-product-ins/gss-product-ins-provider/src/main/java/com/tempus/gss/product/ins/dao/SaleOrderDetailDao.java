package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderDetailVo;

@Component("insSaleOrderDetailDao")
public interface SaleOrderDetailDao extends InsBaseDao<SaleOrderDetail,SaleOrderDetailVo>{
	/*
	* 通过订单编号获取子订单集合
	* */
	public List<SaleOrderDetail> selectBySaleOrderNo(Long saleOrderNo);

	/**
	 * 
	 * selectDetailByPolicyNo:根据保单号查询子订单
	 *
	 * @param  @param policyNo
	 * @param  @return    设定文件
	 * @return List<SaleOrderDetail>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<SaleOrderDetail> selectDetailByPolicyNo(String policyNo);
	
}

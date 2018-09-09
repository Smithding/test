package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import java.util.List;


public interface ISaleOrderDetailService {

	/**
	* 根据销售编号查询销售单明细
	* */
	public List<SaleOrderDetail> selectBySaleOrderNo(Long saleOrderNo);

	/**
	 * 更具saleChangeNoList查询明细
	 * @param saleOrderNo
	 * @return
	 */
	public List<SaleOrderDetail> selectBySaleChangeNoList(List<Long> saleChangeNoList);

	/**
	 * 根据乘客编号，销售单编号详情信息
	 * @param passengerNo
	 * @param saleOrderNo
	 * @return
	 */
	public List<SaleOrderDetail> selectByPassengerAndSaleOrder(Long passengerNo,Long saleOrderNo);

	/**
	* 根据销售编号更新销售单状态
	* */
	public int upateSaleOrder(RequestWithActor<SaleOrderDetail> saleOrderDetail);

	int batchUpdate(List<SaleOrderDetail> list);

	/*
	* 将订单中已核价未支付的订单状态改为未核价
	* */
	int updateSaleOrderDetailStatus();

	/*
	* 根据订单号将订单中已核价未支付的订单状态改为未核价
	* */
	int updateSaleOrderDetailStatusByNo(Long saleOrderNo);

	/**
	 * 根据订单号修改明细指定状态
	 * @param saleOrderNo
	 * @param status
	 * @return
	 */
	int updateSaleOrderDetailStatusByNo(Long saleOrderNo,int status);

	/**
	 * 通过销售订单号修改订单详情数据
	 * @param saleOrderDetail
	 * @return
	 */
	public int updateBySaleOrderNo(RequestWithActor<SaleOrderDetail> saleOrderDetail);

    SaleOrderDetail selectLastDetailByPgerNo(Long passengerNo);

}
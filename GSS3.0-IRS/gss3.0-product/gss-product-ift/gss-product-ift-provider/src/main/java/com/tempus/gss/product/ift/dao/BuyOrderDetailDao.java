package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.BuyOrderDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BuyOrderDetailDao extends BaseDao<BuyOrderDetail, Object> {

	/*根据采购单编号查询订单详情信息*/
	public List<BuyOrderDetail> selectByOrderNo(Long buyOrderNo);

	/*根据销售单查询最新的采购单*/
	public BuyOrderDetail selectBySaleNoDesc(Long saleDetailNo);

	/*根据销售单查询采购单*/
	public List<BuyOrderDetail> selectBySaleNo(Long saleDetailNo);

}
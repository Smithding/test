package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderExtVo;

@Component("OrderServiceReportDao")
public interface OrderServiceReportDao extends InsBaseDao<SaleOrderExt, SaleOrderExtVo> {

	/**
	 * 报表功能查询所有订单数据(包括付款和退款)
	 */
	List<SaleOrderExt> queryReportObjByKey(SaleOrderExtVo saleOrderExtVo);
}

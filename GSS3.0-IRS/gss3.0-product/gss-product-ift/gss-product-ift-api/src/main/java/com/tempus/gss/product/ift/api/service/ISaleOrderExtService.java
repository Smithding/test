package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.vo.Agent;

/**
 * 国际机票销售订单拓展服务接口.
 */
public interface ISaleOrderExtService{

	/**
	 * 根据销售单编号获取销售单拓展（包含SaleOrder对象）
	 * @param agent
	 * @return
	 */
	SaleOrderExt selectBySaleOrderNo(Agent agent,Long saleOrderNo) throws Exception;

}

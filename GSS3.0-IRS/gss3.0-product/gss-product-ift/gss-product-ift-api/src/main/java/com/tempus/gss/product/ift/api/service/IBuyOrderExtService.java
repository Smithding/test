package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.BuyOrderExt;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * 国际机票销售订单拓展服务接口.
 */
public interface IBuyOrderExtService {

	/**
	 * 根据采购单编号获取采购单拓展（包含BuyOrder对象）
	 * @param agent
	 * @return
	 */
	BuyOrderExt selectByBuyOrderNo(Agent agent, Long buyOrderNo) throws Exception;


	/**
	 * 根据销售单编号获取采购单拓展（包含BuyOrder对象,默认第一个）
	 * @param agent
	 * @return
	 */
	BuyOrderExt selectBySaleOrderNo(Agent agent, Long saleOrderNo) throws Exception;


	List<BuyOrderExt> selectListBySaleOrderNo(Agent agent, Long saleOrderNo)throws Exception;
	

}

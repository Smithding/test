package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.order.entity.UpdatePlanAmountVO;
import com.tempus.gss.vo.Agent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface IAccountService {

	/**
	 * 调整采购单结算价
	 * @param agent
	 * @param sb key 票号   bigDecimal 价格
	 * @return
	 */
	public List<UpdatePlanAmountVO> adjustAccountPrice(Agent agent, Map<String,BigDecimal> sb);



}

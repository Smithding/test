package com.tempus.gss.product.ins.api.service;

import java.util.List;

import com.tempus.gss.product.ins.api.entity.InsuranceProfit;
import com.tempus.gss.vo.Agent;

public interface IInsuranceProfitService {
	/**
	 * 根据保险的编号获取所有保险的二级控润
	 * @param agent
	 * @param insuranceNo
	 * @return
	 */
public List<InsuranceProfit> queryInsuranceProfitByNo(Agent agent,Long insuranceNo);
}

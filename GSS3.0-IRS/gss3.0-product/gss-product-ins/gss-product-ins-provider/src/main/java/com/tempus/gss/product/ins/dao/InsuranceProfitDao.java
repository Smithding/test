package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.InsuranceProfit;
@Component("InsuranceProfitDao")
public interface InsuranceProfitDao {
	//根据保险编号查询二级保险控润
public List<InsuranceProfit> queryInsuranceProfitByNo(Long insuranceNo);
}
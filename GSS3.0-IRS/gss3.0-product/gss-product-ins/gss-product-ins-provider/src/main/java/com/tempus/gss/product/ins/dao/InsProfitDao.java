package com.tempus.gss.product.ins.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ins.api.entity.InsProfit;
import com.tempus.gss.product.ins.api.entity.vo.InsProfitVo;

public interface InsProfitDao extends AutoMapper<InsProfit> {
	
	
	/**
	 * 分页查询
	 * @param page
	 * @param vo
	 * @return
	 */
	List<InsProfit> queryProfitList(Page<InsProfit> page,InsProfitVo vo);
	
	List<InsProfit> queryProfitListAll(InsProfitVo vo);
	
	InsProfit selectByProfitNo(Long profitNo);
	
	int updateByProfitNo(InsProfitVo vo);
	
	InsProfit queryProfitList(InsProfitVo vo);
	
	List<InsProfit> queryProfitResList(InsProfitVo vo);
	
	List<InsProfit> queryProfitByParentNo(Long customerTypeNo);
}

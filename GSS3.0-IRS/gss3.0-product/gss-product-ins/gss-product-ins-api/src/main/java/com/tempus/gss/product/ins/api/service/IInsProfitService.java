package com.tempus.gss.product.ins.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ins.api.entity.InsProfit;
import com.tempus.gss.product.ins.api.entity.vo.InsProfitVo;
import com.tempus.gss.vo.Agent;

/**
 * 保险控润服务
 * @author kai.yang
 *
 */
public interface IInsProfitService {
	
	/**
	 * 查询保险控润分页
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public Page<InsProfit> queryProfitList(Page<InsProfit> page,Agent agent,InsProfitVo vo);
	/**
	 * 查询保险的所有控润
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public List<InsProfit> queryProfitListAll(InsProfitVo vo);
	/**
	 * 创建控润
	 */
	public int createProfit(Agent agent,InsProfitVo vo);
	
	public InsProfit selectByProfitNo(Long profitNo ,Agent agent);
	
	public InsProfit updateByProfitNo(InsProfitVo vo ,Agent agent);
	
	public InsProfit selectByProfitNoAndCustomerTypeNo(InsProfitVo vo, Agent agent);
	
	public int canceByInsProfitNo(Agent agent, Long id);
	
	public Boolean queryProfitResList(Agent agent, InsProfitVo vo);
}

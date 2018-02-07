package com.tempus.gss.product.hol.api.service;

import java.math.BigDecimal;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.Profit;
import com.tempus.gss.product.hol.api.entity.response.QTPrice;
import com.tempus.gss.product.hol.api.entity.vo.ProfitVo;
import com.tempus.gss.vo.Agent;

/**
 *
 * Profit 表数据服务层接口
 *
 */
public interface IHolProfitService extends ISuperService<Profit> {

	/**
	 * 查询酒店控润分页
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public Page<Profit> queryProfitList(Page<Profit> page,Agent agent,ProfitVo vo);
	
	/**
	 * 创建控润
	 */
	public int createProfit(Agent agent,ProfitVo vo);
	
	/**
	 * 根据customerTypeNo获取政策
	 * @param customerTypeNo
	 * @return
	 */
	public Profit selectByProfitNo(Long profitNo,Agent agent);
	
	/**
	 * 根据customerTypeNo获取政策
	 * @param customerTypeNo
	 * @return
	 */
	public Profit selectByProfitNoAndCustomerTypeNo(ProfitVo vo,Agent agent);
	
	/**
	 * 根据customerTypeNo获取所有政策，计算控润
	 * @param customerTypeNo
	 * @param agent
	 * @return
	 */
	public BigDecimal computeProfitPrice(Long customerTypeNo,QTPrice qtPrice,Agent agent);
	/**
	 * 修改
	 * @param agent
	 * @param vo
	 * @return
	 */
	public Profit updateByProfitNo(Agent agent,ProfitVo vo);
	/**
	 * 查询TC酒店控润
	 * @param agent
	 * @param price
	 * @return
	 */
	public BigDecimal computeTcProfitPrice(Agent agent, Integer price, Long customerTypeNo);
	
	public int cancelByProfitNo(Agent agent, Long id);
	
}
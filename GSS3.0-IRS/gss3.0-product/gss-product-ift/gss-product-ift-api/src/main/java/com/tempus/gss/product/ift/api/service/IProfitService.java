/**
 * IProfitService.java
 * com.tempus.gss.product.ift.api.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月7日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.vo.ProfitVO;
import com.tempus.gss.vo.Agent;

/**
 * ClassName:IProfitService
 * Function: 国际控润接口类
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年9月7日		上午10:03:33
 *
 * @see 	 
 *  
 */
public interface IProfitService {

	/**
	 * 查询国际控润分页
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public Page<Profit> queryProfitList(Page<Profit> page, Agent agent, ProfitVO vo);

	/**
	 * 
	 * createProfit:添加控润
	 *
	 * @param  @param agent
	 * @param  @param profitVo
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public int createProfit(Agent agent, ProfitVO profitVo);

	/**
	 * 
	 * updateByProfitNo:更新控润
	 *
	 * @param  @param agent
	 * @param  @param profit
	 * @param  @return    设定文件
	 * @return Profit    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public int updateByProfitNo(Agent agent, Profit profit);

	/**
	 * 
	 * selectByProfitNo:根据控润编号查询控润
	 *
	 * @param  @param profitNo
	 * @param  @param agent
	 * @param  @return    设定文件
	 * @return Profit    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public Profit selectByProfitNo(Long profitNo, Agent agent);

	/**
	 *
	 * deleteByProfitNo:根据控润编号删除控润
	 *
	 * @param  @param profitNo
	 * @return int  删除条数
	 * @throws
	 * @since  CodingExample　Ver 1.1
	 */
	public int deleteByProfitNo(Long profitNo, Agent agent);
}


package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Demand;
import com.tempus.gss.product.ift.api.entity.vo.DemandVo;

/**
 * 国际机票需求单服务接口.
 */
public interface IDemandService  {

	/**
	 * 创建需求单.
	 *
	 * @param demandRequest 创建请求.
	 * @return
	 */
	int create(RequestWithActor<Demand> demandRequest)  throws Exception;

	/**
	 * 取消需求单
	 *
	 * @param demandNo 需求单编号.
	 * @return
	 */
	boolean cancel(RequestWithActor<Long> demandNo);
	
	/**
	 * 锁定 解锁
	 *
	 * @param demandNo 需求单编号.
	 * @return
	 */
	boolean locker(RequestWithActor<Long> demandNo);

	/**
	 * 需求单查询.
	 *
	 * @param requestWithActor 查询请求
	 * @return
	 */
	public Page<Demand> queryDemandList(Page<Demand> page,RequestWithActor<DemandVo> requestWithActor) ;

	/**
	 * 需求单查询.
	 *
	 * @param demandNo 查询请求
	 * @return
	 */
	Demand getDemand(RequestWithActor<Long> demandNo);

	/**
	 * 需求单核价.
	 *
	 * @param demandNo 需求单核价请求.
	 * @return
	 */
	boolean confirmPrice(RequestWithActor<Long> demandNo);

	/**
	 * 修改需求单
	 *
	 * @param demand
	 * @return
	 */
	int updateDemand(RequestWithActor<Demand> demand);
	
	
	/**
	 * 更新需求单状态
	 *
	 * @param demand
	 * @return
	 */
	int updateDemandStatus(RequestWithActor<Demand> demand);

	/**
	 * 根据条件查询需求单的数量
	 * @param demandVo
	 * @return
	 */
	Long countDemand(RequestWithActor<DemandVo> demandVo);

}

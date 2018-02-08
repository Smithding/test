/**
 * IUnpOrderService.java
 * com.tempus.gss.product.unp.api.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年3月10日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpOrderChange;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderChangeVo;

/**
 * ClassName:IUnpOrderService
 * Function: 通用产品订单服务
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年3月10日		上午8:55:47
 *
 * @see 	 
 *  
 */
public interface IUnpOrderChangeService {
	/**
	 * 
	 * createOrderChange:创建订单
	 *
	 * @param @return
	 *            设定文件
	 * @return 销售单编号
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	boolean createOrderChange(RequestWithActor<Long> requestWithActor) throws Exception;

	/**
	 * 根据订单编号查询订单
	 * 
	 * @param requestWithActor
	 * @return
	 */
	UnpOrderChange queryOrderChangeByNo(RequestWithActor<Long> requestWithActor);
	
	/**
	 * 根据订单编号查询订单
	 * 
	 * @param requestWithActor
	 * @return
	 */
	UnpOrderChange queryBuyChangeByNo(RequestWithActor<Long> requestWithActor);
	/**
	 * queryOrderList:分页查询订单列表
	 * @param  @param page
	 * @param  @param pageRequest
	 * @param  @return    设定文件
	 * @return Page<UnpOrder>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	Page<UnpOrderChange> queryChangeOrderList(Page<UnpOrderChange> page, RequestWithActor<UnpOrderChangeVo> pageRequest);
	
	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteOrderChagne(RequestWithActor<Long> orderChangeNo) throws Exception;

	/**
	 * 
	 * orderChangeNo:取消订单
	 *
	 * @param      订单号
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	boolean cancelOrderChange(RequestWithActor<Long> orderChangeNo);
	
	/**
	 * pay:订单支付
	 *
	 * @param  @param requestWithActor
	 * @param  @return
	 * @param  @throws Exception    设定文件
	 * @return boolean    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	boolean changePay(RequestWithActor<UnpOrderChangeVo> requestWithActor) throws Exception;
	
	
}


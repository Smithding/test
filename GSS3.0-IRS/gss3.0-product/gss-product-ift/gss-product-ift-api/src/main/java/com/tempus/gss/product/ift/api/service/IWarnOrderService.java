/**
 * IWarnOrderService.java
 * com.tempus.gss.product.ift.api.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月5日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.WarnOrder;
import com.tempus.gss.product.ift.api.entity.vo.WarnOrderVO;

/**
 * ClassName:IWarnOrderService Function: 调整单提醒接口类
 *
 * @author shuo.cheng
 * @version
 * @since Ver 1.1
 * @Date 2017年9月5日 下午2:53:56
 *
 * @see
 * 
 */
public interface IWarnOrderService {

	/**
	 * 
	 * listTodoWarnOrder:查询调整单提醒列表
	 *
	 * @param @param
	 *            page
	 * @param @param
	 *            warnOrderRequest
	 * @param @return
	 *            设定文件
	 * @return Page<WarnOrder> DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public Page<WarnOrder> listWarnOrder(Page<WarnOrder> page, RequestWithActor<WarnOrderVO> warnOrderRequest);

	public int addWarnOrder(WarnOrder warnOrder);

	public List<WarnOrder> findWarnOrders(WarnOrder warnOrder);

	public int updateWarnOrder(WarnOrder warnOrder);

}


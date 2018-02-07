/**
 * OrderDao.java
 * com.tempus.gss.product.unp.dao
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年3月10日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpOrder;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;

/**
 * ClassName:OrderDao
 * Function: 通用产品订单dao
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年3月10日		下午2:29:00
 *
 * @see 	 
 *  
 */
@Component("unpOrderDao")
public interface OrderDao extends AutoMapper<UnpOrder>{

	/**
	 * getOrderByNo:根据订单号查询订单信息
	 *
	 * @param  @param orderNo
	 * @param  @return    设定文件
	 * @return UnpOrder    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	UnpOrder getOrderByNo(Long orderNo);
	
	/**
	 * queryOrderList:分页查询订单信息
	 *
	 * @param  @param page
	 * @param  @param unpOrder
	 * @param  @return    设定文件
	 * @return List<UnpOrder>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	List<UnpOrder> queryOrderList(Page<UnpOrder> page,UnpOrderVo unpOrder);
	
	/**
	 * 根据OrderNo修改订单
	 * @param unpOrder
	 * @return
	 */
	boolean updateByPrimaryKeySelective(UnpOrder unpOrder);
}


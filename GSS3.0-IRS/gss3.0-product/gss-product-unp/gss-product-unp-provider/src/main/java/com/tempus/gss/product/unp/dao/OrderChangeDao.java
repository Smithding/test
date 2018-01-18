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
import com.tempus.gss.product.unp.api.entity.UnpOrderChange;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderChangeVo;

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
@Component("OrderChangeDao")
public interface OrderChangeDao extends AutoMapper<UnpOrderChange>{

	/**
	 * getOrderByNo:根据订单号查询订单信息
	 *
	 * @param  @param orderNo
	 * @param  @return    设定文件
	 * @return UnpOrder    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	UnpOrderChange getOrderChangeByNo(Long orderChangeNo);
	
	/**
	 * getOrderByNo:根据订单号查询订单信息
	 *
	 * @param  @param orderNo
	 * @param  @return    设定文件
	 * @return UnpOrder    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	UnpOrderChange queryBuyChangeByNo(Long buyChangeNo);
	
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
	List<UnpOrderChange> queryOrderChangeList(Page<UnpOrderChange> page,UnpOrderChangeVo unpOrderChange);
	
	int updateByPrimaryKeySelective(UnpOrderChange unpOrderChange);
}


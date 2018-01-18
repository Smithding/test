/**
 * SaleChangeExtDao.java
 * com.tempus.gss.product.ins.dao
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2016年11月26日 		shuo.cheng
 *
 * Copyright (c) 2016, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.SaleChangeExt;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.vo.SaleChangeExtVo;

/**
 * ClassName:SaleChangeExtDao
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2016年11月26日		上午10:18:49
 *
 * @see 	 
 *  
 */
@Component("insSaleChangeExtDao")
public interface SaleChangeExtDao extends InsBaseDao<SaleChangeExt, SaleChangeExtVo> {
	
	/**
	 * 
	 * selectByInsuredNo:根据被保人查询
	 *
	 * @param  @param insuredNo
	 * @param  @return    设定文件
	 * @return List<SaleChangeExt>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<SaleChangeExt> selectByInsuredNo(Long insuredNo);
}


/**
 * SupplierDao.java
 * com.tempus.gss.product.unp.dao
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年2月24日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.unp.api.entity.UnpSupplier;
import com.tempus.gss.product.unp.api.entity.vo.UnpSupplierVo;

/**
 * ClassName:SupplierDao
 * Function: 通用产品供应商
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年2月24日		下午3:47:00
 *
 * @see 	 
 *  
 */
@Component("unpSupplierDao")
public interface SupplierDao extends BaseDao<UnpSupplier,UnpSupplierVo> {

	/**
	 * 
	 * selectSupplierList:获取供应商列表
	 *
	 * @param  @return    设定文件
	 * @return List<UnpSupplier>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<UnpSupplier> selectSupplierList();
	
	/**
	 * 根据供应商编号查询最大的排序号
	 * @return
	 */
	public Long selectBySupplierNo();
	
	/**
	 *查询是否存在同样的编号
	 * @param supplierNo
	 * @return
	 */
	public UnpSupplier selectExistSupplierNo(Long supplierNo);
	
}


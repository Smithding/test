/**
 * IUnpSupplierService.java
 * com.tempus.gss.product.unp.api.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年2月24日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpSupplier;
import com.tempus.gss.product.unp.api.entity.vo.UnpSupplierVo;
import com.tempus.gss.vo.Agent;

/**
 * ClassName:IUnpSupplierService
 * Function: 供应商服务
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年2月24日		下午3:38:40
 *
 * @see 	 
 *  
 */
public interface IUnpSupplierService {

	/**
	 * 
	 * getSupplierList:获取供应商列表
	 *
	 * @param  @return    设定文件
	 * @return List<Supplier>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<Supplier> getSupplierList(Agent agent);
	
	/**
	 * 创建供应商列表
	 * @param requestWithActor
	 * @return
	 */
	int createSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception;
	
	/**
	 * 查询供应商列表  
	 * @param page,pageRequest
	 * @return
	 */
	public Page<UnpSupplier> selectSupplier(Page<UnpSupplier> page,RequestWithActor<UnpSupplierVo> pageRequest);
	
	/**
	 * 删除供应商.
	 * @param supplierNo
	 * @return
	 */
	int deleteSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception;
	
	/**
	 * 修改供应商.
	 * @param requestWithActor
	 * @return
	 */
	int updateSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception;
	
	/**
	 * 判断供应商编号是否唯一
	 * @param supplierNo
	 */
	public UnpSupplier getSupplierNo(RequestWithActor<UnpSupplier> requestWithActor);

	/**
	 * 
	 * selectSupplierByPrimaryKey:根据供应商编号查询保险供应商
	 *
	 * @param  @param requestWithActor
	 * @param  @return    设定文件
	 * @return UnpSupplier    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	UnpSupplier selectSupplierByPrimaryKey(RequestWithActor<Long> requestWithActor);
}


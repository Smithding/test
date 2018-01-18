package com.tempus.gss.product.ins.api.service;

import com.baomidou.mybatisplus.plugins.Page;

import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.InsSupplier;
import com.tempus.gss.product.ins.api.entity.vo.InsSupplierVo;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 *  供应商服务。
 */
public interface IInsSupplierService {
	
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
	int createSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception;
	
	/**
	 * 查询供应商列表  
	 * @param page,pageRequest
	 * @return
	 */
	public Page<InsSupplier> selectSupplier(Page<InsSupplier> page,RequestWithActor<InsSupplierVo> pageRequest);
	
	/**
	 * 删除供应商.
	 * @param supplierNo
	 * @return
	 */
	int deleteSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception;
	
	/**
	 * 修改供应商.
	 * @param requestWithActor
	 * @return
	 */
	int updateSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception;
	
	/**
	 * 判断供应商编号是否唯一
	 * @param supplierNo
	 */
	public InsSupplier getSupplierNo(RequestWithActor<InsSupplier> requestWithActor);


	/**
	 * 
	 * selectSupplierByPrimaryKey:根据供应商编号查询保险供应商
	 *
	 * @param  @param requestWithActor
	 * @param  @return    设定文件
	 * @return InsSupplier    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	InsSupplier selectSupplierByPrimaryKey(RequestWithActor<Long> requestWithActor);
}

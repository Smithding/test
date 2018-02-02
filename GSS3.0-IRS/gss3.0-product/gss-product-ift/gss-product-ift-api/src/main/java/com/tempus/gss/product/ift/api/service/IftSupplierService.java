package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.IftSupplier;
import com.tempus.gss.product.ift.api.entity.vo.IftSupplierVo;
import com.tempus.gss.vo.Agent;


public interface IftSupplierService {
	
	public List<Supplier> getSupplierList(Agent agent);
	/**
	 * 创建供应商列表
	 * @param requestWithActor
	 * @return
	 */
	int createSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception;
	
	/**
	 * 查询供应商列表  
	 * @param page,pageRequest
	 * @return
	 */
	public Page<IftSupplier> selectSupplier(Page<IftSupplier> page,RequestWithActor<IftSupplierVo> pageRequest);
	
	/**
	 * 删除供应商.
	 * @param supplierNo
	 * @return
	 */
	int deleteSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception;
	
	/**
	 * 修改供应商.
	 * @param requestWithActor
	 * @return
	 */
	int updateSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception;
	
	/**
	 * 判断供应商编号是否唯一
	 * @param supplierNo
	 */
	 public IftSupplier getSupplierNo(RequestWithActor<IftSupplier> requestWithActor);
}

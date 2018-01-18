package com.tempus.gss.product.hol.api.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.HolSupplier;
import com.tempus.gss.product.hol.api.entity.vo.SupplierVo;
import com.tempus.gss.vo.Agent;

/**
 *
 * Supplier 表数据服务层接口
 *
 */
public interface IHolSupplierService extends ISuperService<HolSupplier> {
	
	/**
	 * 查询酒店供应商分页
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public Page<HolSupplier> querySupplierList(Page<HolSupplier> page,RequestWithActor<SupplierVo> requestWithActor) ;

	/**
	 * 插入数据
	 * @param agent
	 * @param supplier
	 * @return
	 */
	public int insertSupplier(Agent agent,HolSupplier supplier);
	
	/**
	 * 根据ID查询
	 * @param agent
	 * @param id
	 * @return
	 */
	public HolSupplier queryById(Agent agent,Long id);
	
	/**
	 * 根据ID修改
	 * @param agent
	 * @param supplier
	 * @return
	 */
	public int updateSupplierById(Agent agent,HolSupplier supplier);
	
	/**
	 * 取消
	 * @param agent
	 * @param id
	 * @return
	 */
	public int cancelById(Agent agent,Long id);
	
	public List<HolSupplier> selectSupplierList();
	
	public HolSupplier queryBySupplierCode(String supplierCode);
}
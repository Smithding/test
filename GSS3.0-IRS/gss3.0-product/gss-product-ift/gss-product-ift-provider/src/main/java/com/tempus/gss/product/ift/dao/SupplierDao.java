package com.tempus.gss.product.ift.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ift.api.entity.IftSupplier;
import com.tempus.gss.product.ift.api.entity.vo.IftSupplierVo;

@Component
public interface SupplierDao extends BaseDao<IftSupplier, IftSupplierVo> {
	public List<IftSupplier> selectSupplierList();
	
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
	public IftSupplier selectExistSupplierNo(Long supplierNo);
}
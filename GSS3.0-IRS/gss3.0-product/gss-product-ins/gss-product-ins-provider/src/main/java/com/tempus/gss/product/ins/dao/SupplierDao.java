package com.tempus.gss.product.ins.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tempus.gss.product.ins.api.entity.InsSupplier;
import com.tempus.gss.product.ins.api.entity.vo.InsSupplierVo;

@Component("insSupplierDao")
public interface SupplierDao extends InsBaseDao<InsSupplier, InsSupplierVo> {

	/**
	 * 
	 * selectSupplierList:获取供应商列表
	 *
	 * @param  @return    设定文件
	 * @return List<InsSupplier>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public List<InsSupplier> selectSupplierList();
	
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
	public InsSupplier selectExistSupplierNo(Long supplierNo);
}
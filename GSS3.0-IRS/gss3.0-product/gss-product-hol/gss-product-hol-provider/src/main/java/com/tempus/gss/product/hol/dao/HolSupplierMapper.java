package com.tempus.gss.product.hol.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HolSupplier;
import com.tempus.gss.product.hol.api.entity.vo.SupplierVo;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

/**
 *
 * Supplier 表数据库控制层接口
 *
 */
@Component("HolSupplierMapper")
public interface HolSupplierMapper extends AutoMapper<HolSupplier> {

	/**
	 * 分页查询
	 * @param page
	 * @param vo
	 * @return
	 */
	List<HolSupplier> querySupplierList(Page<HolSupplier> page,SupplierVo vo);
	
	/**
	 * 插入
	 * @param supplier
	 * @return
	 */
	int insertSupplier(HolSupplier supplier);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	HolSupplier queryById(Long id);
	
	/**
	 * 根据ID修改
	 * @param supplier
	 * @return
	 */
	int updateSupplierById(HolSupplier supplier);
	
	/**
	 * 取消
	 * @param supplier
	 * @return
	 */
	int cancelById(HolSupplier supplier);
	
	/**
	 * 查询全部
	 * @return
	 */
	List<HolSupplier> selectSupplierList();
	
	/**
	 * 根据名字查询
	 */
	HolSupplier queryBySupplierCode(String supplierCode);
}
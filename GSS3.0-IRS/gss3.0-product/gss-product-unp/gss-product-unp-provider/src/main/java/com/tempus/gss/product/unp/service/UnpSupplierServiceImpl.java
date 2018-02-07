/**
 * UnpSupplierServiceImpl.java
 * com.tempus.gss.product.unp.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年2月24日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpSupplier;
import com.tempus.gss.product.unp.api.entity.vo.UnpSupplierVo;
import com.tempus.gss.product.unp.api.service.IUnpSupplierService;
import com.tempus.gss.product.unp.dao.SupplierDao;


/**
 * ClassName:UnpSupplierServiceImpl
 * Function: 通用产品供应商管理
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年2月24日		下午3:58:54
 *
 * @see 	 
 *  
 */
@Service
@EnableAutoConfiguration
public class UnpSupplierServiceImpl implements IUnpSupplierService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Reference
	ISupplierService supplierService;
	
	@Autowired
	SupplierDao supplierDao;

	@Override
	public List<Supplier> getSupplierList(Agent agent) {
		List<Supplier> supplierList = new ArrayList<>();
		log.info("获取通用产品供应商开始======");
		List<UnpSupplier> unpSupplierList = supplierDao.selectSupplierList();// 获取通用产品供应商
		if(null!=unpSupplierList && unpSupplierList.size()>0){
			for(UnpSupplier unpSupplier:unpSupplierList){
				Supplier cpsSupplier = supplierService.getSupplierByNo(agent,unpSupplier.getSupplierNo());
				if(null!=cpsSupplier && cpsSupplier.getIsOpen()==1){
					supplierList.add(cpsSupplier);
				}
			}
		}
		log.info("获取通用产品供应商结束======");
		return supplierList;
	}

	@Override
	public int createSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception {
		log.info("创建供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("创建供应商信息条件为空");
			throw new GSSException("创建供应商信息条件为空", "0011", "创建供应商信息失败");
		}
		int flag = 0;
		try {
			UnpSupplier supplier = requestWithActor.getEntity();
			supplier.setValid(1);
			supplier.setStatus(1);
			Long supplierNo = supplierDao.selectBySupplierNo();
			if (supplierNo != null) {
				supplier.setSort(supplierNo + 1l);
			} else {
				supplier.setSort(1L);
			}
			supplier.setCreator(requestWithActor.getAgent().getAccount());
			supplier.setCreateTime(new Date());
			UnpSupplier unpSupplier = supplierDao.selectByPrimaryKey(supplier.getSupplierNo());// 查询编号是否存在在数据库，所有
			if (unpSupplier != null) {
				flag = supplierDao.updateByPrimaryKeySelective(supplier);
			} else {
				flag = supplierDao.insert(supplier);
			}
		} catch (Exception e) {
			log.error("supplier创建失败", e);
			throw new GSSException("创建供应商信息模块", "0012", "创建信息异常");
		}
		log.info("创建供应商结束========");
		return flag;
	}

	@Override
	public Page<UnpSupplier> selectSupplier(Page<UnpSupplier> page, RequestWithActor<UnpSupplierVo> pageRequest) {
		log.info("查询供应商信息开始");
		try {
			List<Supplier> list = this.getSupplierList(pageRequest.getAgent());
			List<UnpSupplier> supplierList = supplierDao.queryObjByKey(page, pageRequest.getEntity());
			for (UnpSupplier unpSupplier : supplierList) {
				for (Supplier sup : list) {
					if (unpSupplier.getSupplierNo().equals(sup.getSupplierNo())) {
						unpSupplier.setName(sup.getName());
					}
				}
			}
			page.setRecords(supplierList);
		} catch (Exception e) {
			log.error("供应商信息表中未查询出相应的结果集", e);
			throw new GSSException("查询供应商信息模块", "0022", "查询供应商信息失败");
		}
		log.info("查询供应商信息结束");
		return page;
	}

	@Override
	public int deleteSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception {
		log.info("删除供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("删除供应商信息条件为空");
			throw new GSSException("删除供应商信息条件为空", "0031", "删除供应商信息失败");
		}
		int flag = 0;
		try {
			requestWithActor.getEntity().setValid(0);
			flag = supplierDao.updateByPrimaryKeySelective(requestWithActor.getEntity());
		} catch (Exception e) {
			log.error("supplier删除失败", e);
			throw new GSSException("删除供应商信息模块", "0032", "删除信息异常");
		}
		log.info("删除供应商信息结束========");
		return flag;
	}

	@Override
	public int updateSupplier(RequestWithActor<UnpSupplier> requestWithActor) throws Exception {
		log.info("修改供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("修改供应商信息条件为空");
			throw new GSSException("修改供应商信息条件为空", "0041", "修改供应商信息失败");
		}
		int flag = 0;
		try {
			UnpSupplier supplier = requestWithActor.getEntity();
			supplier.setValid(1);
			flag = supplierDao.updateByPrimaryKeySelective(supplier);
		} catch (Exception e) {
			log.error("supplier修改失败", e);
			throw new GSSException("修改供应商信息模块", "0042", "修改信息异常");
		}
		log.info("修改供应商信息结束========");
		return flag;
	}

	@Override
	public UnpSupplier getSupplierNo(RequestWithActor<UnpSupplier> requestWithActor) {
		log.info("查询供应商编号是否存在开始====");
		UnpSupplier supplier;
		try {
			supplier = supplierDao.selectExistSupplierNo(requestWithActor.getEntity().getSupplierNo());
		} catch (Exception e) {
			log.error("供应商信息表中未查询出相应的结果集", e);
			throw new GSSException("查询供应商编号是否存在模块", "0052", "查询供应商编号是否存在失败");
		}
		log.info("查询供应商编号是否存在结束====");
		return supplier;
	}

	@Override
	public UnpSupplier selectSupplierByPrimaryKey(RequestWithActor<Long> requestWithActor) {
		log.info("根据供应商编号查询通用产品供应商开始======");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("查询条件为空");
			throw new GSSException("查询条件为空", "0031", "查询供应商信息失败");
		}
		Long supplierNo = requestWithActor.getEntity();
		UnpSupplier unpSupplier = supplierDao.selectByPrimaryKey(supplierNo);
		log.info("根据供应商编号查询通用产品供应商结束======");
		return unpSupplier;
	}
}


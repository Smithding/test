package com.tempus.gss.product.ins.service;

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
import com.tempus.gss.product.ins.api.entity.InsSupplier;
import com.tempus.gss.product.ins.api.entity.vo.InsSupplierVo;
import com.tempus.gss.product.ins.api.service.IInsSupplierService;
import com.tempus.gss.product.ins.dao.SupplierDao;

@Service
@EnableAutoConfiguration
public class InsSupplierServiceImpl implements IInsSupplierService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Reference
	ISupplierService supplierService;

	@Autowired
	SupplierDao supplierDao;

	@Override
	public int createSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception {

		log.info("创建供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("创建供应商信息条件为空");
			throw new GSSException("创建供应商信息条件为空", "0011", "创建供应商信息失败");
		}
		int flag = 0;
		try {
			InsSupplier supplier = requestWithActor.getEntity();
			supplier.setValid((byte) 1);
			supplier.setStatus(1);
			Long supplierNo = supplierDao.selectBySupplierNo();
			if (supplierNo != null) {
				supplier.setSort(supplierNo + 1l);
			} else {
				supplier.setSort(1L);
			}
			supplier.setCreator(requestWithActor.getAgent().getAccount());
			supplier.setCreateTime(new Date());
			InsSupplier inssupplier = supplierDao.selectByPrimaryKey(supplier.getSupplierNo());// 查询编号是否存在在数据库，所有
			if (inssupplier != null) {
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
	public Page<InsSupplier> selectSupplier(Page<InsSupplier> page, RequestWithActor<InsSupplierVo> pageRequest) {

		log.info("查询供应商信息开始");
		try {
			List<Supplier> list = this.getSupplierList(pageRequest.getAgent());
			List<InsSupplier> supplierList = supplierDao.queryObjByKey(page, pageRequest.getEntity());
			for (InsSupplier inssup : supplierList) {
				for (Supplier sup : list) {
					if (inssup.getSupplierNo().equals(sup.getSupplierNo())) {
						inssup.setName(sup.getName());
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
	public int deleteSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception {

		log.info("删除供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("删除供应商信息条件为空");
			throw new GSSException("删除供应商信息条件为空", "0031", "删除供应商信息失败");
		}
		int flag = 0;
		try {
			requestWithActor.getEntity().setValid((byte) 0);
			flag = supplierDao.updateByPrimaryKeySelective(requestWithActor.getEntity());
		} catch (Exception e) {
			log.error("supplier删除失败", e);
			throw new GSSException("删除供应商信息模块", "0032", "删除信息异常");
		}
		log.info("删除供应商信息结束========");
		return flag;
	}

	@Override
	public int updateSupplier(RequestWithActor<InsSupplier> requestWithActor) throws Exception {

		log.info("修改供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("修改供应商信息条件为空");
			throw new GSSException("修改供应商信息条件为空", "0041", "修改供应商信息失败");
		}
		int flag = 0;
		try {
			InsSupplier supplier = requestWithActor.getEntity();
			supplier.setValid((byte) 1);
			flag = supplierDao.updateByPrimaryKeySelective(supplier);
		} catch (Exception e) {
			log.error("supplier修改失败", e);
			throw new GSSException("修改供应商信息模块", "0042", "修改信息异常");
		}
		log.info("修改供应商信息结束========");
		return flag;
	}

	@Override
	public InsSupplier getSupplierNo(RequestWithActor<InsSupplier> requestWithActor) {

		log.info("查询供应商编号是否存在开始====");
		InsSupplier supplier;
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
	public List<Supplier> getSupplierList(Agent agent) {

		log.info("获取保险供应商开始======");
		List<InsSupplier> supplierList = supplierDao.selectSupplierList();// 获取保险供应商
		List<Supplier> cpsSupplierList = new ArrayList<>();
		if (supplierList != null && supplierList.size() != 0) {
			for (InsSupplier supplier : supplierList) {
				Supplier cpsSupplier = supplierService.getSupplierByNo(agent,supplier.getSupplierNo());
				if (cpsSupplier != null) {
					cpsSupplierList.add(cpsSupplier);
				}
			}
		}

		log.info("获取保险供应商结束======");
		return cpsSupplierList;
	}

	@Override
	public InsSupplier selectSupplierByPrimaryKey(RequestWithActor<Long> requestWithActor) {

		log.info("根据供应商编号查询保险供应商开始======");

		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("根据供应商编号查询保险供应商条件为空");
			throw new GSSException("根据供应商编号查询保险供应商条件为空", "0031", "查询供应商信息失败");
		}

		Long supplierNo = requestWithActor.getEntity();
		InsSupplier insSupplier = supplierDao.selectByPrimaryKey(supplierNo);
		log.info("根据供应商编号查询保险供应商结束======");

		return insSupplier;

	}

}

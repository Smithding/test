package com.tempus.gss.product.ift.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;

import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.IftSupplier;
import com.tempus.gss.product.ift.api.entity.vo.IftSupplierVo;
import com.tempus.gss.product.ift.api.service.IftSupplierService;
import com.tempus.gss.product.ift.dao.SupplierDao;

@Service
@EnableAutoConfiguration
public class IftSupplierServiceImpl implements IftSupplierService{
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SupplierDao supplierDao;
	@Reference
	ISupplierService supplierService;
	@Override
	public List<Supplier> getSupplierList(Agent agent) {
		log.info("获取国际供应商开始======");
		List<IftSupplier> supplierList = supplierDao.selectSupplierList();//获取国际机票供应商
		List<Supplier> cpsSupplierList = new ArrayList<>();
		if(supplierList!=null && supplierList.size()!=0){
			for(IftSupplier supplier :supplierList) {
				Supplier cpsSupplier =  supplierService.getSupplierByNo(agent,supplier.getSupplierNo());
				if(cpsSupplier!=null){
					cpsSupplierList.add(cpsSupplier);
				}
			}
		}
		return cpsSupplierList;
	}
	
	@Override
	public int createSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception {

		log.info("创建供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("创建供应商信息条件为空");
			throw new GSSException("创建供应商信息条件为空", "0011", "创建供应商信息失败");
		}
		int flag = 0;
		try {
			IftSupplier supplier = requestWithActor.getEntity();
			supplier.setValid((byte) 1);
			supplier.setStatus(1);
			if(supplierDao.selectBySupplierNo()!=null){
				supplier.setSort(supplierDao.selectBySupplierNo()+1l);
			}else{
				supplier.setSort(1l);
			}
			supplier.setCreator(requestWithActor.getAgent().getAccount());
			supplier.setCreateTime(new Date());
			IftSupplier iftsupplier = supplierDao.selectByPrimaryKey(supplier.getSupplierNo());//查询编号是否存在在数据库，所有
			if(iftsupplier!=null){
				flag = supplierDao.updateByPrimaryKeySelective(supplier);
			}else{
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
	public Page<IftSupplier> selectSupplier(Page<IftSupplier> page, RequestWithActor<IftSupplierVo> pageRequest) {

		log.info("查询供应商信息开始");
		try {
			List<Supplier> list=this.getSupplierList(pageRequest.getAgent());
			List<IftSupplier> policyList = supplierDao.queryObjByKey(page, pageRequest.getEntity());
			for(IftSupplier iftsup:policyList){
				for(Supplier sup:list){
					if(iftsup.getSupplierNo().equals(sup.getSupplierNo())){
						iftsup.setChannelType(sup.getGroupNo());
						iftsup.setOfficeNo(sup.getOfficeNo());
						iftsup.setName(sup.getName());
					}
				}
			}
			page.setRecords(policyList);
		} catch (Exception e) {
			log.error("供应商信息表中未查询出相应的结果集", e);
			throw new GSSException("查询供应商信息模块", "0022", "查询供应商信息失败");
		}
		log.info("查询供应商信息结束");
		return page;
	}

	@Override
	public int deleteSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception {

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
	public int updateSupplier(RequestWithActor<IftSupplier> requestWithActor) throws Exception {

		log.info("修改供应商信息开始========");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.error("修改供应商信息条件为空");
			throw new GSSException("修改供应商信息条件为空", "0041", "修改供应商信息失败");
		}
		int flag = 0;
		try {
			IftSupplier supplier = requestWithActor.getEntity();
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
	public IftSupplier getSupplierNo(RequestWithActor<IftSupplier> requestWithActor) {
		
		log.info("查询供应商编号是否存在开始====");
		IftSupplier supplier;
		try {
			 requestWithActor.getEntity().setValid((byte) 1);
			 supplier = supplierDao.selectExistSupplierNo(requestWithActor.getEntity().getSupplierNo());
		} catch (Exception e) {
			log.error("供应商信息表中未查询出相应的结果集", e);
			throw new GSSException("查询供应商编号是否存在模块", "0052", "查询供应商编号是否存在失败");
		}
		log.info("查询供应商编号是否存在结束====");
		return supplier;
	}

}

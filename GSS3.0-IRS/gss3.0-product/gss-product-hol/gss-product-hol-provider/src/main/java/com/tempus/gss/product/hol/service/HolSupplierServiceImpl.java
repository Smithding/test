package com.tempus.gss.product.hol.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.HolSupplier;
import com.tempus.gss.product.hol.api.entity.vo.SupplierVo;
import com.tempus.gss.product.hol.api.service.IHolSupplierService;
import com.tempus.gss.product.hol.dao.HolSupplierMapper;
import com.tempus.gss.vo.Agent;

/**
 *
 * Supplier 表数据服务层接口实现类
 *
 */
@Service
@EnableAutoConfiguration
public class HolSupplierServiceImpl extends SuperServiceImpl<HolSupplierMapper, HolSupplier> implements IHolSupplierService {
	
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	HolSupplierMapper supplierMapper;
	
	@Override
	public Page<HolSupplier> querySupplierList(Page<HolSupplier> page, RequestWithActor<SupplierVo> requestWithActor) {
		log.info("酒店供应商查询开始");
		try {
			if(requestWithActor.getAgent()==null){
				log.error("agent为空");
	            throw new GSSException("酒店供应商查询失败", "0103", "agent为空");
			}
			requestWithActor.getEntity().setOwner(requestWithActor.getAgent().getOwner().longValue());
			List<HolSupplier> supplierList = supplierMapper.querySupplierList(page, requestWithActor.getEntity());
			page.setRecords(supplierList);
		} catch (Exception e) {
			log.error("酒店供应商查询失败", e);
			throw new GSSException("酒店供应商查询模块", "0302", "查询失败");
		}
		log.info("需求查询结束");
		return page;
	}
	
	@Override
	public int insertSupplier(Agent agent,HolSupplier supplier){
		int flag=0;
		try{
			supplier.setOwner(agent.getOwner().longValue());
			supplier.setValid(1);
			supplier.setCreator(agent.getAccount());
			supplier.setCreateTime(new Date());
			flag=supplierMapper.insertSupplier(supplier);
			/*if(flag == 1){
				int pushCode = pushJson(agent);
				if(pushCode == 0){
					log.info("推送失败");
				}else if(pushCode == 1){
					log.info("推送成功");
				}
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 进行数据推送
	 * @param agent
	 * @return 0：推送失败，1推送成功
	 */
	/*public int pushJson(Agent agent) throws Exception{
		int pushCode = 0;
		List<PcUBPSupplier> pcUBPSupplier=new ArrayList<PcUBPSupplier>();
		List<HolSupplier> supplierList=supplierMapper.selectSupplierList();
		for(HolSupplier su:supplierList){
			//只推送同程
			if(su.getStatus() == 1 && "tc,411709261204150108".contains(su.getSupplierCode())){
				PcUBPSupplier pcSupplier=new PcUBPSupplier();
				pcSupplier.setUBPCode(su.getUBPCode());
				pcSupplier.setRate(su.getRate());
				pcSupplier.setSupplierCode(su.getSupplierCode());
				pcSupplier.setContext(su.getContext());
				pcSupplier.setPayType(su.getPayType().toString());
				pcUBPSupplier.add(pcSupplier);
			}
		}
		PcUBPSupplierReq pcSupplier=new PcUBPSupplierReq();
		pcSupplier.setPcUBPSupplier(pcUBPSupplier);
		pcSupplier.setUBPCode(agent.getOwner().toString());
		QTCommonRequest qtCommonRequest = new QTCommonRequest();
		qtCommonRequest.setAction(ActionType.USBSupplier.toString());
		qtCommonRequest.setRequestJson(JSONObject.toJSONString(pcSupplier));
		String reqJson = JSONObject.toJSONString(qtCommonRequest);
		BaseResponse baseResponse = HttpClientUtil.doJsonPost(QianTaoConfig.SUPPLIER_URL, reqJson, BaseResponse.class);
		if (baseResponse != null) {
		    if (baseResponse.getIsSuccess()) {
		        logger.info("推送成功！"+qtCommonRequest.getRequestJson());
		        pushCode = 1;
		    } else {
		    	pushCode = 0;
		        logger.error("推送失败");
		        throw new GSSException("推送供应商信息失败", "0110", "推送供应商信息失败:" + baseResponse.getErrorMessage());
		    }
		} else {
			pushCode = 0;
		    logger.error("推送供应商信息返回空值");
		    throw new GSSException("推送供应商信息请求", "0111", "推送供应商信息返回空值");
		}
		return pushCode;
	}*/
	
	@Override
	public HolSupplier queryById(Agent agent, Long id) {
		if(agent == null ){
			log.error("agent为空");
            throw new GSSException("酒店供应商查询失败", "0103", "agent为空");
		}
		if(id ==null){
			log.error("id为空");
            throw new GSSException("酒店供应商查询失败", "0103", "id为空");
		}
		HolSupplier supplier=supplierMapper.queryById(id);
		return supplier;
	}

	@Override
	public int updateSupplierById(Agent agent, HolSupplier supplier) {
		int flag=0;
		try {
			if(agent == null ){
				log.error("agent为空");
			    throw new GSSException("酒店供应商修改失败", "0103", "agent为空");
			}
			if(supplier == null || supplier.getId()==null){
				log.error("agent为空");
			    throw new GSSException("酒店供应商修改失败", "0103", "传入参数为空");
			}
			supplier.setModifier(agent.getAccount());
			supplier.setModifierTime(new Date());
			flag = supplierMapper.updateSupplierById(supplier);
			/*if(flag == 1){
				int pushCode = pushJson(agent);
				if(pushCode == 0){
					log.info("推送失败");
				}else if(pushCode == 1){
					log.info("推送成功");
				}
			}*/
		} catch (Exception e) {
			log.error("更新酒店供应商失败", e);
			throw new GSSException("更新酒店供应商失败", "0302", "更新酒店供应商失败");
		}
		return flag;
	}

	@Override
	public int cancelById(Agent agent, Long id) {
		int flag=0;
		try {
			if(agent == null){
				log.error("agent为空");
			    throw new GSSException("取消酒店供应商失败", "0103", "agent为空");
			}
			if(id ==null){
				log.error("id为空");
			    throw new GSSException("取消酒店供应商失败", "0103", "id为空");
			}
			HolSupplier supplier = new HolSupplier();
			supplier.setModifier(agent.getAccount());
			supplier.setModifierTime(new Date());
			supplier.setValid(0);
			supplier.setId(id);
			flag = supplierMapper.cancelById(supplier);
			/*if(flag == 1){
				int pushCode = pushJson(agent);
				if(pushCode == 0){
					log.info("推送失败");
				}else if(pushCode == 1){
					log.info("推送成功");
				}
			}*/
		} catch (Exception e) {
			log.error("取消酒店供应商失败", e);
			throw new GSSException("取消酒店供应商失败", "0302", "取消酒店供应商失败");
		}
		return flag;
	}

	@Override
	public List<HolSupplier> selectSupplierList() {
		return supplierMapper.selectSupplierList();
	}

	@Override
	public HolSupplier queryBySupplierCode(String supplierCode) {
		
		return supplierMapper.queryBySupplierCode(supplierCode);
	}
	
}
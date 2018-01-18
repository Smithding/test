package com.tempus.gss.product.ins.service;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ins.api.entity.InsProfit;
import com.tempus.gss.product.ins.api.entity.InsuranceProfit;
import com.tempus.gss.product.ins.api.service.IInsProfitService;
import com.tempus.gss.product.ins.api.service.IInsuranceProfitService;
import com.tempus.gss.product.ins.dao.InsProfitDao;
import com.tempus.gss.product.ins.dao.InsuranceProfitDao;
import com.tempus.gss.vo.Agent;
@Service
@EnableAutoConfiguration
public class InsuranceProfitServiceImpl implements IInsuranceProfitService{

	@Autowired
	InsuranceProfitDao insuranceProfitDao;
	@Autowired
	InsProfitDao insProfitDao;
	@Reference
	IInsProfitService insProfitService;
	@Override
	public List<InsuranceProfit> queryInsuranceProfitByNo(Agent agent, Long insuranceNo) {
		List<InsuranceProfit> InsuranceProfitList = null;
		List<InsuranceProfit> Result = new ArrayList<InsuranceProfit>();
		List<Long> customerList = new ArrayList<Long>();
		InsuranceProfit insuranceProfit = new InsuranceProfit();
		List<InsProfit> parentNo = null;
		List<InsProfit> supperNo = null;
		List<InsProfit> rootNo = null;
		try{
			parentNo = insProfitDao.queryProfitByParentNo(agent.getNum());
			if(parentNo.size()!=0){
				supperNo = insProfitDao.queryProfitByParentNo(parentNo.get(0).getParentId());
				if(supperNo.size()!=0){
					rootNo = insProfitDao.queryProfitByParentNo(supperNo.get(0).getParentId());
				}
				customerList.add(agent.getNum());
				if(supperNo.size()!=0){
					customerList.add(parentNo.get(0).getParentId());
					if(rootNo.size()!=0){
						customerList.add(supperNo.get(0).getParentId());
					}
			
				}
			}
			InsuranceProfitList = insuranceProfitDao.queryInsuranceProfitByNo(insuranceNo);
			if(InsuranceProfitList.size()!=0&&customerList.size()!=0){
				for(Long no:customerList){
					for(InsuranceProfit ins:InsuranceProfitList){
						if(ins.getCustomerTypeNo()==no){
							insuranceProfit = ins;
							Result.add(insuranceProfit);
						}
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			Log.error("查询二级控润错误!!!");
		}
		return Result;
	}

}

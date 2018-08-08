package com.tempus.gss.product.ins.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.InsuranceProfit;
import com.tempus.gss.product.ins.api.entity.ProfitControl;
import com.tempus.gss.product.ins.api.entity.vo.InsuranceVo;
import com.tempus.gss.product.ins.api.service.IInsuranceProfitService;
import com.tempus.gss.product.ins.api.service.IInsuranceService;
import com.tempus.gss.product.ins.api.service.IOrderService;
import com.tempus.gss.product.ins.dao.InsuranceDao;
import com.tempus.gss.product.ins.dao.ProfitControlDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;

@Service
@EnableAutoConfiguration
public class InsuranceServiceImpl  implements IInsuranceService {
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	InsuranceDao insuranceDao;
	@Reference
	IMaxNoService maxNoService;
    @Reference
    IOrderService orderService;
	@Autowired
	ProfitControlDao profitControlDao;
	@Reference
    IInsuranceProfitService insuranceProfitService;
	@Override
	public List<Insurance> queryList(RequestWithActor<InsuranceVo> requestWithActor) {
		log.info("查询保险产品开始=====");
		if(requestWithActor.getEntity()==null){
			log.error(" 查询参数条件为空");
			throw new GSSException("查询参数条件为空","0101","查询保险产品失败");
		}
		/*不分页*/
		List<Insurance> insuranceList = insuranceDao.queryObjByKey(requestWithActor.getEntity());

		log.info("查询保险产品结束=====");
		return insuranceList;
	}
	@Override
	public Insurance getDefault(RequestWithActor<String>  requestWithActor,Integer insuranceKindType) {

		log.info("获取默认的保险产品开始=====");
		if (requestWithActor.getEntity() == null) {
			log.error("获取默认的保险产品参数条件为空");
			throw new GSSException("参数条件为空", "0101", "获取默认的保险产品失败");
		}
		String insureType = requestWithActor.getEntity();
		List<Insurance> insuranceList = null;
		try{
			 insuranceList = insuranceDao.selectDefaultInsurance(insureType,insuranceKindType);
			if(insuranceList==null||insuranceList.size()==0){
				insuranceList = insuranceDao.chooseDefaultInsurance(insureType, insuranceKindType);
			}
			if(insuranceList.size()==0){
				return null;
			}
			//一级控润处理
			//截取客户类型前三个字符
	        String customerTypeNo = (requestWithActor.getAgent().getType()+"").substring(0, 3);
			 List<ProfitControl> profitList = orderService.selectByInsuranceNo(insuranceList.get(0).getInsuranceNo());
	    	 for(ProfitControl pf : profitList) {
	    		 if(pf.getCustomerTypeNo() !=null ) {	
	    			 if(customerTypeNo.equals(pf.getCustomerTypeNo().toString())) {
	    				 insuranceList.get(0).setBuyPrice(pf.getSalePrice());
	        		 } 
	    		 }
	    	 }
			//存储一级控润后的保险
			List<Insurance> newinsuranceList = new ArrayList<Insurance>();
			
			for(Insurance insurance:insuranceList){
				 //二级控润
		        List<InsuranceProfit> insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(requestWithActor.getAgent(), insurance.getInsuranceNo());
		        BigDecimal a = new BigDecimal(100);
		        insurance.setProfitPrice(insurance.getBuyPrice());
		        for(InsuranceProfit insuranceProfit:insuranceProfitList){
		        	 if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
		            	  //判断是控点还是固定控  1为控点    2为固定控          
		                if(insuranceProfit.getProfitMode()==1){
		                	/* if(insuranceProfit.getProfitCount()!=null){
		                		 insurance.setBuyPrice((insurance.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(insurance.getBuyPrice()));    
		              	   }else{
		              		   Log.error("保险控点为空");
		              	   }*/
		               }else if(insuranceProfit.getProfitMode()==2){
		            	   if(insuranceProfit.getProfitMoney()!=null){
		            		   insurance.setBuyPrice(insurance.getBuyPrice().add(insuranceProfit.getProfitMoney()));    
		            	   }else{
		            		   Log.error("保险控现为空");
		            	   }
		            		   
		               }
		            }
		        }
	           
		           newinsuranceList.add(insurance);
			}
			if (newinsuranceList.size() <= 0) {
				log.info("查询到的默认的保险产品为空=====");
			}
			log.info("获取默认的保险产品结束=====");
			return newinsuranceList.get(0);
		}catch(Exception e){
			throw new GSSException("获取空润失败", "003", "计算控润出现错误!") ;
		}	
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
	public Long createInsurance(RequestWithActor<Insurance> requestWithActor) {
		log.info("创建保险产品开始=======");
		if(requestWithActor.getAgent()==null||requestWithActor.getEntity()==null){
			log.error("创建产品参数条件为空");
			throw new GSSException("创建产品参数条件为空","0101","创建保险产品失败");
		}

		Insurance insurance = requestWithActor.getEntity();
		insurance.setInsuranceNo(maxNoService.generateBizNo("INS_INSURANCE_NO",39));
		insurance.setCreator(requestWithActor.getAgent().getAccount());
		insurance.setCreateTime(new Date());

		Boolean isDefault = insurance.getIsDefault();
		// 如果新创建的产品isDefault属性为1(是默认),则更新其他同险种类型的产品isDefault属性为0(非默认)
		if (Boolean.TRUE.equals(isDefault)) {
			String insureType = insurance.getInsureType();
			InsuranceVo vo = new InsuranceVo();
			vo.setInsureType(insureType);
			/* 不分页 */
			List<Insurance> insuranceList = insuranceDao.queryObjByKey(vo);
			if (insuranceList == null) {
				log.info("根据险种类型查询保险产品为空!");
			}
			for (Insurance ins : insuranceList) {
				ins.setIsDefault(new Boolean(false));
				insuranceDao.updateByPrimaryKeySelective(ins);
			}
		}
		try{
			/*创建保险产品*/
			insuranceDao.insertSelective(insurance);
			/*创建控润信息*/
			if(insurance.getProfitControlList()!=null){
				for(ProfitControl profitControl:insurance.getProfitControlList()){
					profitControl.setProfitControlNo(maxNoService.generateBizNo("IFT_PROFIT_CONTROL_NO",35));
					profitControl.setInsuranceNo(insurance.getInsuranceNo());
					profitControlDao.insertSelective(profitControl);
				}
			}
		}catch(Exception e){
			log.error("插入保险数据失败"+e.getMessage());
		}
		
		

		log.info("创建保险产品结束=======");
		/*返回主键*/
		return insurance.getInsuranceNo();
	}

	
	
	@Override
	@Transactional
	public Long updateInsurance(RequestWithActor<Insurance> requestWithActor) throws GSSException{
		log.info("更新保险产品开始==============");
		if(requestWithActor.getAgent()==null||requestWithActor.getEntity()==null){ 
			log.error("更新产品参数条件为空");
			throw new GSSException("更新产品参数条件为空","0101","更新保险产品失败");

		}
		Insurance insurance = requestWithActor.getEntity();
		if (insurance.getInsuranceNo() == null || insurance.getInsuranceNo() == 0) {
			log.error("更新产品参数条件为空");
			throw new GSSException("更新产品参数条件为空","0101","更新保险产品失败");
		}

		Boolean isDefault = insurance.getIsDefault();
		int internatOrcivil = insurance.getInternatOrcivil();
		// 如果要更新的产品isDefault属性为1(是默认),则更新其他同险种类型的产品isDefault属性为0(非默认)
		if (Boolean.TRUE.equals(isDefault)) {
			String insureType = insurance.getInsureType();
			InsuranceVo vo = new InsuranceVo();
			vo.setInsureType(insureType);
		    vo.setInternatOrcivil(internatOrcivil);
			/* 不分页 */
			List<Insurance> insuranceList = insuranceDao.queryObjByKey(vo);
			if (insuranceList == null) {
				log.info("根据险种类型查询保险产品为空!");
			}

			for (Insurance ins : insuranceList) {
				ins.setIsDefault(new Boolean(false));
				insuranceDao.updateByPrimaryKeySelective(ins);
			}
		}
		insuranceDao.updateByPrimaryKeySelective(insurance);
		
		if(requestWithActor.getEntity().getInsuranceNo()!=null){
			profitControlDao.deleteByInsuranceNo(requestWithActor.getEntity().getInsuranceNo());
		}
		/*创建控润信息*/
		if(requestWithActor.getEntity().getProfitControlList()!=null){
			for(ProfitControl profitControl:requestWithActor.getEntity().getProfitControlList()){
				profitControl.setProfitControlNo(maxNoService.generateBizNo("IFT_PROFIT_CONTROL_NO",35));
				profitControl.setInsuranceNo(requestWithActor.getEntity().getInsuranceNo());
				profitControlDao.insertSelective(profitControl);
			}
		}
		log.info("更新保险产品结束==============");
		return requestWithActor.getEntity().getInsuranceNo();
	}

	@Override
	public Insurance getInsurance(RequestWithActor<Long> requestWithActor) {
		log.info("获取保险产品开始=======");
		if(requestWithActor.getEntity()==null||requestWithActor.getEntity()==0){
			log.error("获取产品参数条件为空");
			throw new GSSException("获取产品参数条件为空","0101","获取保险产品失败");

		}
		Insurance insurance = insuranceDao.selectByPrimaryKey(requestWithActor.getEntity());
		log.info("获取保险产品结束=======");
		return insurance;
	}

	@Override
	public Page<Insurance> queryInsuranceList(Page<Insurance> page,RequestWithActor<InsuranceVo> pageRequest) {
		log.info("查询保险分页产品开始=====");
		if (pageRequest.getEntity() == null) {
			log.error("查询参数条件为空");
			throw new GSSException("查询参数条件为空", "0101", "查询保险产品失败");
		}
		/* 分页 */
		List<Insurance> insuranceList = insuranceDao.queryObjByKey(page,pageRequest.getEntity());
		 Long customerTypeNo = Long.parseLong((pageRequest.getAgent().getType()+"").substring(0, 3));
		 for(Insurance ins : insuranceList) {
			 for(ProfitControl ProfitControl:ins.getProfitControlList()){
				   if(ProfitControl.getCustomerTypeNo().equals(customerTypeNo)){
					   //二级控润查询
					   List<InsuranceProfit> insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(pageRequest.getAgent(), ins.getInsuranceNo());
					   for(InsuranceProfit insuranceProfit:insuranceProfitList){
						   if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
				              	  //判断是控点还是固定控  1为控点   2为固定控          
				                  if(insuranceProfit.getProfitMode()==1){
				                  	/* if(insuranceProfit.getProfitCount()!=null){
				                  		 //二级控点暂时停用
				                  		 ins.setBuyPrice((ins.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(ins.getBuyPrice()));    
				                	   }else{
				                		   Log.error("保险控点为空");
				                	   }*/
				                
				                 }else if(insuranceProfit.getProfitMode()==2){
				              	   if(insuranceProfit.getProfitMoney()!=null){
				              		 ProfitControl.setSalePrice(ProfitControl.getSalePrice().add(insuranceProfit.getProfitMoney()));    
				              	   }else{
				              		   Log.error("保险控现为空");
				              	   }
				                 }
				                 }
					   }
					   
				   }
			 }
		 }
		/*//二级控润
        for(Insurance ins : insuranceList) {
        	  InsuranceProfit insuranceProfit = insuranceProfitService.queryInsuranceProfitByNo(pageRequest.getAgent(), ins.getInsuranceNo());
              BigDecimal a = new BigDecimal(100);
              if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
              	  //判断是控点还是固定控  1为控点    2为固定控          
                  if(insuranceProfit.getProfitMode()==1){
                  	 if(insuranceProfit.getProfitCount()!=null){
                  		ins.setBuyPrice((ins.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(ins.getBuyPrice()));    
                	   }else{
                		   Log.error("保险控点为空");
                	   }
                
                 }else if(insuranceProfit.getProfitMode()==2){
              	   if(insuranceProfit.getProfitMoney()!=null){
              		 ins.setBuyPrice(ins.getBuyPrice().add(insuranceProfit.getProfitMoney()));    
              	   }else{
              		   Log.error("保险控现为空");
              	   }
              		   
                 }
              }
        }*/
		/*设置分页*/
		page.setRecords(insuranceList);
		log.info("查询保险分页产品结束=====");
		return page;
	}

	@Override
	@Transactional
	public Long delInsurance(RequestWithActor<Insurance> requestWithActor) {
		log.info("删除保险产品开始==============");
		if(requestWithActor.getAgent()==null||requestWithActor.getEntity()==null){
			log.error("删除产品参数条件为空");
			throw new GSSException("更新产品参数条件为空","0101","更新保险产品失败");

		}
		if(requestWithActor.getEntity().getInsuranceNo()==null||requestWithActor.getEntity().getInsuranceNo()==0){
			log.error("删除产品参数条件为空");
			throw new GSSException("更新产品参数条件为空","0101","更新保险产品失败");
		}
		//修改valid状态
		requestWithActor.getEntity().setValid(false);
		insuranceDao.updateByPrimaryKeySelective(requestWithActor.getEntity());
		log.info("删除保险产品结束==============");
		return requestWithActor.getEntity().getInsuranceNo();
	
		
	}

	@Override
	public List<Insurance> selectInsurance(RequestWithActor<Insurance> requestWithActor) {
		log.info("查询保险产品开始=====");
		List<Insurance> insuranceList = insuranceDao.selectInsurance(requestWithActor.getEntity());
		log.info("查询保险产品结束=====");
		return insuranceList;
	}

	@Override
	public List<Insurance> getDefaultList(RequestWithActor<List<String>> requestWithActor,Integer insuranceKindType) {

		log.info("获取默认的保险产品列表开始=====");
		if (requestWithActor.getEntity() == null) {
			log.error("获取默认的保险产品参数条件为空");
			throw new GSSException("参数条件为空", "0101", "获取默认的保险产品列表失败");
		}

		List<Insurance> insuranceList = new ArrayList<>();
		List<String> list = requestWithActor.getEntity();
		for (String insureType : list) {
			List<Insurance> insurances = insuranceDao.selectDefaultInsurance(insureType,insuranceKindType);
			if (insurances == null || insurances.size() <= 0) {
				log.info("查询到险种类型:" + insureType + "对应的默认保险产品为空=====");
			}
			else
			{
				//存储二级控润后的保险
				List<Insurance> newinsuranceList = new ArrayList<Insurance>();
				
				for(Insurance insurance:insurances){
					 //二级控润
			        List<InsuranceProfit> insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(requestWithActor.getAgent(), insurance.getInsuranceNo());
			        BigDecimal a = new BigDecimal(100);
			        for(InsuranceProfit insuranceProfit:insuranceProfitList){
			            if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
			            	  //判断是控点还是固定控  1为控点    2为固定控          
			                if(insuranceProfit.getProfitMode()==1){
			                	/* if(insuranceProfit.getProfitCount()!=null){
			                		 insurance.setBuyPrice((insurance.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(insurance.getBuyPrice()));    
			              	   }else{
			              		   Log.error("保险控点为空");
			              	   }*/
			               }else if(insuranceProfit.getProfitMode()==2){
			            	   if(insuranceProfit.getProfitMoney()!=null){
			            		   insurance.setBuyPrice(insurance.getBuyPrice().add(insuranceProfit.getProfitMoney()));    
			            	   }else{
			            		   Log.error("保险控现为空");
			            	   }
			            		   
			               }
			            }
			        }

				insuranceList.addAll(insurances);
			}
			}
		}
		log.info("获取默认的保险产品列表结束=====");

		return insuranceList;

	}

	@Override
	public Page<Insurance> queryInsuranceAllList(Page<Insurance> page, RequestWithActor<InsuranceVo> pageRequest) {
		log.info("查询保险分页产品开始=====");
		if (pageRequest.getEntity() == null) {
			log.error("查询参数条件为空");
			throw new GSSException("查询参数条件为空","0101", "查询保险产品失败");
		}
		/* 分页 */
		List<Insurance> insuranceList = insuranceDao.queryAllObjByKey(page,pageRequest.getEntity());
		if(pageRequest.getAgent().getType()!=null){
			 Long customerTypeNo = Long.parseLong((pageRequest.getAgent().getType()+"").substring(0, 3));
			 for(Insurance ins : insuranceList) {
				 for(ProfitControl ProfitControl:ins.getProfitControlList()){
					   if(ProfitControl.getCustomerTypeNo().equals(customerTypeNo)){
						   //二级控润查询
						   List<InsuranceProfit> insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(pageRequest.getAgent(), ins.getInsuranceNo());
						   for(InsuranceProfit insuranceProfit:insuranceProfitList){
							   if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
					              	  //判断是控点还是固定控  1为控点    2为固定控          
					                  if(insuranceProfit.getProfitMode()==1){
					                  	 if(insuranceProfit.getProfitCount()!=null){
					                  		 //二级控点暂时停用
					                  		 /*ins.setBuyPrice((ins.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(ins.getBuyPrice()));    */
					                	   }else{
					                		   Log.error("保险控点为空");
					                	   }
					                
					                 }else if(insuranceProfit.getProfitMode()==2){
					              	   if(insuranceProfit.getProfitMoney()!=null){
					              		 ProfitControl.setSalePrice(ProfitControl.getSalePrice().add(insuranceProfit.getProfitMoney()));    
					              	   }else{
					              		   Log.error("保险控现为空");
					              	   }
					                 }
					                 }   
						   }
						   
					   }
				 }
			 }
		}
		
		/*//二级控润
        for(Insurance ins : insuranceList) {
        	  InsuranceProfit insuranceProfit = insuranceProfitService.queryInsuranceProfitByNo(pageRequest.getAgent(), ins.getInsuranceNo());
              BigDecimal a = new BigDecimal(100);
              if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
              	  //判断是控点还是固定控  1为控点    2为固定控          
                  if(insuranceProfit.getProfitMode()==1){
                  	 if(insuranceProfit.getProfitCount()!=null){
                  		ins.setBuyPrice((ins.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(ins.getBuyPrice()));    
                	   }else{
                		   Log.error("保险控点为空");
                	   }
                
                 }else if(insuranceProfit.getProfitMode()==2){
              	   if(insuranceProfit.getProfitMoney()!=null){
              		 ins.setBuyPrice(ins.getBuyPrice().add(insuranceProfit.getProfitMoney()));    
              	   }else{
              		   Log.error("保险控现为空");
              	   }
              		   
                 }
              }
        }*/
		/*设置分页*/
		page.setRecords(insuranceList);
		log.info("查询保险分页产品结束=====");
		return page;
	}
	@Override
	public Long forbitInsurance(RequestWithActor<Insurance> requestWithActor) throws GSSException {
		log.info("保险禁用启用功能开始==============");
		Insurance insurance = requestWithActor.getEntity();
/*			InsuranceVo vo = new InsuranceVo();
			vo.setInsuranceNo(insurance.getInsuranceNo());
			vo.setValid(insurance.getValid());
			 不分页 
			List<Insurance> insuranceList = insuranceDao.queryObjByKey(vo);*/
			insuranceDao.updateByPrimaryKeySelective(insurance);
		log.info("保险禁用启用功能结束==============");
		return requestWithActor.getEntity().getInsuranceNo();
	}
	@Override
	public boolean selectInsuranceCode(String code) {
		// TODO Auto-generated method stub
		List<Insurance> InsuranceList = insuranceDao.selectInsuranceCode(code);
		if(InsuranceList.size()==0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public List<Insurance> getAllInsurance(RequestWithActor<InsuranceVo> requestWithActor){
		Agent agent = requestWithActor.getAgent();
	       List<Insurance> tempInsuranceList = this.queryList(requestWithActor);
	        List<Insurance> insuranceList = new ArrayList<>();
	        //截取客户类型前三个字符
	        Long customerTypeNo = Long.parseLong((agent.getType()+"").substring(0, 3));
	        //一级控润
	        for(Insurance ins : tempInsuranceList) {
	        	//国际保险跳过
	        	if(ins.getInternatOrcivil()==2){
	        		continue;
	        	}
	        	 List<ProfitControl> profitList = orderService.selectByInsuranceNo(ins.getInsuranceNo());
	        	 for(ProfitControl pf : profitList) {
	        		 if(pf.getCustomerTypeNo() !=null ) {	
	        			 if(customerTypeNo.toString().equals(pf.getCustomerTypeNo().toString())) {
	            			 ins.setBuyPrice(pf.getSalePrice());
	            		 } 
	        		 }
	        	 }
	        	 ins.setProfitPrice(ins.getBuyPrice());
	        	 List<InsuranceProfit> insuranceProfitList = null;
	        	 //二级控润
	        	 try{
	        	 insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(agent, ins.getInsuranceNo());
	        	 }catch(Exception e){
	        		 e.printStackTrace();
	        		 log.error("二级控润查询失败！"+e);
	        	 }
	         BigDecimal a = new BigDecimal(100);
	         for(InsuranceProfit insuranceProfit:insuranceProfitList){
	        	 if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
	           	  //判断是控点还是固定控  1为控点    2为固定控          
	               if(insuranceProfit.getProfitMode()==1){
	/*               	 if(insuranceProfit.getProfitCount()!=null){
	             		   ins.setBuyPrice((ins.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(ins.getBuyPrice()));    
	             	   }else{
	             		   Log.error("保险控点为空");
	             	   }*/
	              }else if(insuranceProfit.getProfitMode()==2){
	           	   if(insuranceProfit.getProfitMoney()!=null){
	           		   ins.setBuyPrice(ins.getBuyPrice().add(insuranceProfit.getProfitMoney()));    
	           	   }else{
	           		   Log.error("保险控现为空");
	           	   }
	           		   
	              }
	           }
	        	
	         }
	         insuranceList.add(ins);
	        
	        }
	        return insuranceList;
	}

}

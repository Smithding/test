package com.tempus.gss.product.ift.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.product.ift.api.service.IPassengerRefundPriceService;
import com.tempus.gss.product.ift.dao.PassengerRefundPriceDao;
import com.tempus.gss.system.service.IMaxNoService;

//import com.tempus.gss.system.service.IMaxNoService;
/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class PassengerRefundPriceServiceImpl  implements IPassengerRefundPriceService {

	Logger logger = LoggerFactory.getLogger(PassengerRefundPriceServiceImpl.class);

	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	PassengerRefundPriceDao passengerRefundPriceDao;
	
	@Reference
	private IMaxNoService maxNoService;

	@Override
	public int createPassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice) {
		int flag=0;
		try{
			
			Long passengerRefundPriceNo = maxNoService.generateBizNo("IFT_PASSENGER_REFUND_PRICE_NO", 31);
			passengerRefundPrice.getEntity().setPassengerRefundPriceNo(passengerRefundPriceNo);
			passengerRefundPrice.getEntity().setOwner(passengerRefundPrice.getAgent().getOwner());
			passengerRefundPrice.getEntity().setCreator(passengerRefundPrice.getAgent().getAccount());
			passengerRefundPrice.getEntity().setCreateTime(simple.parse(simple.format(new Date())));
			passengerRefundPrice.getEntity().setValid((byte)1);
			flag=passengerRefundPriceDao.insertSelective(passengerRefundPrice.getEntity());
			if(flag==0){
				throw new GSSException("废退改创建失败", "0101", "创建发生异常,请检查");
			}
		}catch(Exception e){
			throw new GSSException("废退改创建失败", "0101", "创建发生异常,请检查");
		}
		
		return flag;
	}

	/**
	 * 
	 */
	@Override
	public PassengerRefundPrice getPassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice) {
		PassengerRefundPrice getPassengerRefundPrice=passengerRefundPriceDao.getPassengerRefundPrice(passengerRefundPrice.getEntity());
		return getPassengerRefundPrice;
	}
	
	/**
	 * 
	 */
	@Override
	public int updatePassengerRefundPrice(RequestWithActor<PassengerRefundPrice> passengerRefundPrice) {
		int flag=0;
		try{
			passengerRefundPrice.getEntity().setModifier(passengerRefundPrice.getAgent().getAccount());
			passengerRefundPrice.getEntity().setModifyTime(new Date());
			logger.info("更新的退废乘客价格{}",passengerRefundPrice.getEntity().toString());
			flag=passengerRefundPriceDao.updateByPrimaryKeySelective(passengerRefundPrice.getEntity());
		}catch(Exception e ){
			logger.error("废退改修改失败价格",e);
			throw new GSSException("废退改修改失败", "0301", "修改发生异常,请检查");
		}
		return flag;
	}
	
	

	@Override
	public List<PassengerRefundPrice> queryPassengerRefundPriceList(RequestWithActor<PassengerRefundPrice> requestWithActor) {
		List<PassengerRefundPrice> passengerRefundPriceList=null;
		try {
			requestWithActor.getEntity().setOwner(requestWithActor.getAgent().getOwner());
			requestWithActor.getEntity().setValid((byte)1);
			passengerRefundPriceList = passengerRefundPriceDao.queryObjByKeyList(requestWithActor.getEntity());
			
		} catch (Exception e) {
			throw new GSSException("查询需求模块", "0302", "查询失败");
		}
		return passengerRefundPriceList;
	}
}

package com.tempus.gss.product.ift.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.service.IPassengerService;
import com.tempus.gss.product.ift.dao.PassengerDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import com.tempus.gss.system.service.IMaxNoService;
/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
@org.springframework.stereotype.Service("iftPassengerService")
public class PassengerServiceImpl  implements IPassengerService {
	
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	PassengerDao passengerDao;

	@Override
	public List<Passenger> getPassengerBySaleOrderNo(String saleOrderNo) {
		return passengerDao.getPassengerBySaleOrderNo(saleOrderNo);
	}

	@Override
	public Passenger getPassengerByPassengerNo(String passengerNo) {
		return passengerDao.getPassengerByPassengerNo(passengerNo);
	}

	@Override
	@Transactional
	public int updatePassenger(RequestWithActor<Passenger> requestWithActor) {
		int flag = 0;
		try {
			int passengerUpdate =0;
			if (requestWithActor.getEntity().getPassengerNo() != null) {
				passengerUpdate = passengerDao.updateByPrimaryKeySelective(requestWithActor.getEntity());
			}
			if(passengerUpdate ==0 ){
				throw new GSSException("更新乘客模块", "0201", "更新乘客模块异常");
			}
			else{
				log.info("更新乘客成功");
			}
		} catch (Exception e) {
			throw new GSSException("更新政策模块", "0207", "更新政策、舱位、控润异常");
		}
		return flag;
	}

   

}

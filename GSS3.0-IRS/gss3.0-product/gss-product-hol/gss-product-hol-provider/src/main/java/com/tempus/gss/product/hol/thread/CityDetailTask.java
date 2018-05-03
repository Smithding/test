package com.tempus.gss.product.hol.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;

public class CityDetailTask extends Thread{
	
	private IBQYHotelInterService BQYHotelService;
	
	private Logger logger = LoggerFactory.getLogger(CityDetailTask.class);
	
	public CityDetailTask(IBQYHotelInterService BQYHotelService) {
		this.BQYHotelService = BQYHotelService;
	}
	
	@Override
    public void run() {
		logger.info("拉取城市信息开始...");
		try {
			BQYHotelService.pullCityDetail();
		} catch (Exception e) {
			logger.info("拉取城市信息失败...");
		}
		logger.info("拉取城市信息成功...");
    }

}

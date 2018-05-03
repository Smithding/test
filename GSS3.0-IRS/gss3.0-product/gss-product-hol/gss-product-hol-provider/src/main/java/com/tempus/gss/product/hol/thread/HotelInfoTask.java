package com.tempus.gss.product.hol.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;

public class HotelInfoTask extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private IBQYHotelInterService BQYHotelService;
	
	private List<HotelId> hotelIdList;

	public HotelInfoTask(IBQYHotelInterService BQYHotelService,List<HotelId> hotelIdList) {
		this.hotelIdList = hotelIdList;
		this.BQYHotelService = BQYHotelService;
	}
	
	@Override
    public void run() {
        if (hotelIdList != null && hotelIdList.size() > 0) {
        	logger.info(Thread.currentThread().getName() + "线程开始异步拉取酒店信息开始...");
        	try {
				BQYHotelService.pullHotelInfoByIdList(hotelIdList);
			} catch (Exception e) {
				logger.info(Thread.currentThread().getName() + "线程开始异步拉取酒店信息异常...");
				e.printStackTrace();
			}
        	logger.info(Thread.currentThread().getName() + "线程开始异步拉取酒店信息结束...");
        }
    }

}

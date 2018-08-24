package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.tempus.tbd.entity.Airline;

public interface NewIftPolicyDataService {
	/**
	 * 获取所有政策的城市控件，取redis缓存
	 * @return
	 */
   String getAirLineData();
   /**
	 * 政策的城市控件，加入redis缓存
	 * @return
	 */
   String setAirLineData();
   /**
    * 
    * @return
    */
   List<Airline> getAllAirCode();
}

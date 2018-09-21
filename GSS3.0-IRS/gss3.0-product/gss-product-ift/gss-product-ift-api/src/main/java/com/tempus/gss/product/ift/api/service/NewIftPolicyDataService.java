package com.tempus.gss.product.ift.api.service;

import java.util.List;

import com.tempus.tbd.entity.Airline;
import com.tempus.tbd.entity.Airport;

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
   
   /**
    * 获取所有城市三字码
    * 
    * @return
    */
   List<Airport> getAllAirport();
   /**
    * 根据 城市或者国家二字码返回；对应属于的州和国家；
    * @param key 当可以长度为2是国家，当长度为3是三字码
    * @return
    */
   String getCityDate(String key);
}

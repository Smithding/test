package com.tempus.gss.product.ift.api.service.search;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftFlightPolicy;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.vo.Agent;

public interface NewQueryService {
	  /**
	   * 航班列表查询
	   * @param page
	   * @param flightQueryRequest
	   * @return
	   */
	 public Page<QueryIBEDetail> query(Page<QueryIBEDetail> page, RequestWithActor<FlightQueryRequest> flightQueryRequest);
	 /**
	  * 获取更多舱位查询政策
	  * @param flightQueryRequest
	  * @return
	  */
	 public List<QueryIBEDetail> getCabinAll(RequestWithActor<FlightQueryRequest> flightQueryRequest);
	 
	 
}

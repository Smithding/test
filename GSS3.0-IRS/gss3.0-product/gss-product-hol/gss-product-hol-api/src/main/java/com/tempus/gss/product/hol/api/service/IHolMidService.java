package com.tempus.gss.product.hol.api.service;

import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.vo.Agent;

/**
 * 酒店中间表
 */
public interface IHolMidService {

	/**
	 * 酒店查询
	 * @param agent
	 * @param hotelSearchReq
	 * @return
	 */
	TCResponse<HolMidBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq);
}

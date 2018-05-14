package com.tempus.gss.product.hol.api.syn;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.vo.Agent;

public interface ISyncHotelInfo {
	
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	public TCResponse<ResBaseInfo> queryHotelListForBack(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	
}

package com.tempus.gss.product.hol.api.syn;

import java.util.concurrent.Future;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.vo.Agent;

public interface ISyncHotelInfo {
	
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	public TCResponse<ResBaseInfo> queryHotelListForBack(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException;
	
	/**
	 * 拉取BQY酒店信息
	 * @param hotelIdList
	 */
	void pullBQYHotelInfo();
	
	public ResBaseInfo queryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException;
	
	public <T> T queryDetailById(Long id, Class<T> clazz) throws GSSException;
	
	/**
	 * bqy酒店详细信息异步查询
	 * @param agent
	 * @param holMidId
	 * @param checkinDate
	 * @param checkoutDate
	 * @return
	 * @throws GSSException
	 */
	Future<ResBaseInfo> queryBQYHotelListForAsync(Agent agent, Long bqyHotelId, String checkinDate, String checkoutDate) throws Exception;
	
	/**
	 * tc酒店详细信息异步查询
	 * @param agent
	 * @param holMidId
	 * @param checkinDate
	 * @param checkoutDate
	 * @return
	 * @throws GSSException
	 */
	Future<ResBaseInfo> queryTCHelListForAsync(Agent agent, Long tcHotelId, String checkinDate, String checkoutDate);
	
	/**
	 * tc酒店详细信息后台异步查询
	 * @param agent
	 * @param holMidId
	 * @param checkinDate
	 * @param checkoutDate
	 * @return
	 * @throws GSSException
	 */
	Future<ResBaseInfo> queryTCHolForAsyncBack(Agent agent, Long tcHotelId);
}

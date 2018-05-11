package com.tempus.gss.product.hol.api.service;

import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.vo.Agent;

/**
 * BQY酒店查询接口
 */
public interface IBQYHotelSupplierService {
	
	/**
	 * 查询酒店列表
	 * @param hotelSearchReq
	 * @return
	 */
	TCResponse<HotelInfo> queryHotelListForBack(HotelListSearchReq hotelSearchReq);
	
	/**
	 * 查询酒店列表返回字段与同程一致
	 * @param hotelSearchReq
	 * @return
	 */
	TCResponse<ResBaseInfo> queryHotelList(HotelListSearchReq hotelSearchReq);
	
	/**
	 * 修改酒店可售状态
	 * @param agent
	 * @param hotelId
	 * @param saleStatus
	 * @return
	 */
	int saleStatusUpdate(Agent agent, Long hotelId, Integer saleStatus);
	
	/**
	 * 查询酒店详细信息
	 * @param hotelId
	 * @return
	 */
	ResBaseInfo singleHotelDetail(String hotelId);
}

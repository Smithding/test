package com.tempus.gss.product.hol.api.service;

import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;

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
}

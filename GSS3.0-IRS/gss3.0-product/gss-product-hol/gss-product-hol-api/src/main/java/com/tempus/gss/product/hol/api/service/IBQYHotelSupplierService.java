package com.tempus.gss.product.hol.api.service;

import java.util.List;
import java.util.concurrent.Future;

import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IsBookOrderReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.CityDetail;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.BookOrderResponse;
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
	Future<TCResponse<ResBaseInfo>> queryHotelList(HotelListSearchReq hotelSearchReq);
	
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
	ResBaseInfo singleHotelDetail(String hotelId, String checkinDate, String checkoutDate, String cityCode) throws Exception;
	
	/**
	 * 查询城市信息
	 * @param agent
	 * @param cityCode
	 * @return
	 */
	CityDetail getCityDetailByCityCode(Agent agent, String cityName);
	
	/**
	 * 查询酒店
	 * @param agent
	 * @param bqyHolId
	 * @return
	 */
	ResBaseInfo getById(Agent agent, Long bqyHolId);
	
	/**
	 * 酒店试预订
	 * @param agent
	 * @param isBookOrderReq
	 * @return
	 */
	BookOrderResponse isBookOrder(Agent agent, IsBookOrderReq isBookOrderReq, Integer flag);
	
	/**
	 * 查询酒店图片返回与TC图片字段一致
	 * @param agent
	 * @param hotelId
	 * @return
	 */
	List<ImgInfo> listImgByHotelId(Agent agent, long hotelId);
}

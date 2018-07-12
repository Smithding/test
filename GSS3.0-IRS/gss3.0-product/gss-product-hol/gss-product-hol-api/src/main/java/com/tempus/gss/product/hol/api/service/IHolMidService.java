package com.tempus.gss.product.hol.api.service;

import java.util.List;
import java.util.Set;

import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
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
	TCResponse<HolMidBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq, String flag);
	
	/**
	 * 查询酒店中间表信息
	 * @param agent
	 * @param holMidId
	 * @return
	 */
	HolMidBaseInfo queryHolMidById(Agent agent, String holMidId);
	
	/**
	 * 酒店详情
	 * @param agent
	 * @param holMidId
	 * @return
	 */
	ResBaseInfo hotelDetail(Agent agent, String holMidId, String checkinDate, String checkoutDate) throws Exception;
	
	/**
	 * 酒店详情后台查询
	 * @param agent
	 * @param holMidId
	 * @return
	 */
	ResBaseInfo hotelDetailForBack(Agent agent, String holMidId, String checkinDate, String checkoutDate) throws Exception;
	
	/**
	 * 查询酒店图片返回与TC图片字段一致
	 * @param agent
	 * @param holMidId
	 * @return
	 */
	List<ImgInfo> listImgByHotelId(Agent agent, String holMidId);
	
	/**
	 * 修改酒店状态
	 * @param agent
	 * @param hotelId
	 * @param saleStatus
	 * @return
	 */
	int saleStatusUpdate(Agent agent, Long holMidId, Integer saleStatus);
	
}

package com.tempus.gss.product.hol.api.service;

import java.util.List;

import com.tempus.gss.product.hol.api.entity.vo.bqy.CityInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfoListEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelRoomFacility;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryCityInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelIdParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelListParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelLocationEntity;

/**
 * 同城酒店
 */
public interface IBQYHotelInterService{
	

	/**
	 * 获取所有酒店id
	 * @param query
	 * @return
	 */
	List<HotelId> queryHotelIdList();
	

	/**
	 * 酒店列表查询
	 * @param query 酒店列表查询参数
	 * @return
	 */
	List<HotelInfoListEntity> queryHotelList(QueryHotelListParam query);
	
	/**
	 * 酒店明细查询
	 * @param query 酒店详细信息参数查询
	 * @return
	 */
	HotelEntity queryHotelDetail(QueryHotelParam query);
	
	/**
	 * 根据城市名称查询城市信息
	 * @param query
	 * @return
	 */
	CityInfo queryCityInfo(QueryCityInfoParam query);
	
	/**
	 * 根据城市id查询 酒店品牌、设施服务、酒店位置
	 * @param query
	 * @return
	 */
	HotelLocationEntity queryCityInfo2(QueryHotelIdParam query);
	
	/**
	 * 通过酒店id查询房间设施
	 * @param query
	 * @return
	 */
	List<HotelRoomFacility> queryHotelRoomFacility(QueryHotelParam query);
	
	/**
	 * 酒店图片信息
	 * @param query
	 * @return
	 */
	List<ImageList> queryHotelImage(QueryHotelParam query);
	
	/**
	 * 酒店房型图片信息
	 * @param query
	 * @return
	 */
	List<RoomImageList> queryHotelRoomImage(QueryHotelParam query);
	
	/**
	 * 根据酒店ID查询酒店信息
	 * @param query
	 * @return
	 */
	HotelInfo queryHotelInfo(QueryHotelInfoParam query);
	
	/**
	 * 将数据存储到MongoDB中
	 * @param hotelList
	 */
	void saveDataToMongoDB();
	
	/**
	 * 拉取酒店信息
	 */
	void pullHotelInfoByIdList(List<HotelId> hotelIdList);
	
	/**
	 * 拉取城市信息
	 */
	void pullCityDetail();
	
	/**
	 * 删除MongoDB数据
	 */
	void deleteMongoDBData();
	
}
package com.tempus.gss.product.hol.api.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.CityInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfoListEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelRoomFacility;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BookOrderParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.OrderCancelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.OrderPayReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryCityInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelIdParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelListParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.BookOrderResponse;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.CreateOrderRespone;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelLocationEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelOrderInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderCancelResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderPayResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceItem;

/**
 * 同城酒店
 */
public interface IBQYHotelInterService{
	
	/**
	 * 获取酒店id的总数
	 * @param param
	 * @return
	 */
	long queryHotelIdCount(Map<String, Object> param);

	/**
	 * 获取所有酒店id
	 * @param query
	 * @return
	 */
	List<HotelId> queryHotelIdList(Map<String, Object> param);
	
	/**
	 * 城市ID获取酒店ID
	 */
	List<HotelId> queryHotelIdListByCityCode(QueryHotelIdParam query);
	
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
	 * 酒店图片异步查询
	 * @param query
	 * @return
	 */
	Future<List<ImageList>> asyncHotelImage(QueryHotelParam query);
	
	/**
	 * 酒店明细异步查询
	 * @param query 酒店详细信息参数查询
	 * @return
	 */
	Future<HotelEntity> asyncHotelDetail(QueryHotelParam query);
	
	/**
	 * 根据酒店ID查询酒店信息
	 * @param query
	 * @return
	 */
	HotelInfo queryHotelInfo(QueryHotelInfoParam query);
	
	/**
	 * 酒店房间价格
	 * @param query
	 * @return
	 */
	List<RoomPriceItem> queryHotelRoomPrice(QueryHotelParam query);
	
	/**
	 * 根据酒店ID拉取酒店信息
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
	
	/**
	 * 城市ID获取酒店ID
	 */
	void pullHotelIdByCityCode();

	/**
	 * 根据纬度、经度、酒店电话查询中间表中的酒店
	 * @param lat		纬度
	 * @param lon		经度
	 * @param phone		酒店电话
	 * @return
	 */
	List<HolMidBaseInfo> searchHoltel(String lat, String lon, String phone);
	
	/**
	 * 保存中间表
	 * @param hotelId
	 * @param hotelInfo
	 */
	void saveMidHol(HotelInfo hotelInfo);
	
	/**
	 * 酒店试预订
	 * @return
	 */
	BookOrderResponse isBookOrder(BookOrderParam query);
	
	/**
	 * 酒店订单创建
	 * @param createOrderReq
	 * @return
	 */
	CreateOrderRespone createOrder(CreateOrderReq createOrderReq);
	
	/**
	 * 订单支付
	 * @param createOrderReq
	 * @return
	 */
	OrderPayResult orderPay(OrderPayReq orderPayReq);
	
	/**
	 * 取消订单
	 * @param cancelParam
	 * @return
	 */
	Boolean cancelOrder(OrderCancelParam cancelParam);
	
	/**
	 * 订单详情
	 * @param orderDetailParam
	 * @return
	 */
	HotelOrderInfo orderDetail(OrderPayReq orderDetailParam);
	
}

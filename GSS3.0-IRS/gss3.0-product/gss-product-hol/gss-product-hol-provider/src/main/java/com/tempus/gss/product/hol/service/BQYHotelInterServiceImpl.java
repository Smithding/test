package com.tempus.gss.product.hol.service;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.CityDetail;
import com.tempus.gss.product.hol.api.entity.vo.bqy.CityInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelBrand;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelFacility;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfoListEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelRoomFacility;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.InfoShowlist;
import com.tempus.gss.product.hol.api.entity.vo.bqy.Information;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ResponseResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.BookOrderParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.CreateOrderReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryCityInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelIdParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelListParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.BookOrderResponse;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelLocationEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceItem;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.util.DocumentUtil;
import com.tempus.gss.util.JsonUtil;

@Service
public class BQYHotelInterServiceImpl implements IBQYHotelInterService {

	@Value("${bqy.agentId}")
	private String BQY_AGENTID;		//代理人Id
	
	@Value("bqy.key")
	private String BQY_KEY;			
	
	@Value("${bqy.hotelId.list.url}")
	private String BQY_HOTELID_LIST_URL;		//酒店Id集合URL
	
	@Value("${bqy.hotel.info.url}")
	private String BQY_HOTEL_INFO_URL;			//酒店信息URL
	
	@Value("${bqy.all.hotel.list.url}")
	private String BQY_ALL_HOTEL_LIST; 			//酒店列表请求URL

	@Value("${bqy.hotel.detail.url}")
	private String BQY_HOTEL_DETAIL_URL; 		//酒店详情URL

	@Value("${bqy.city.info.url}")
	private String BQY_CITY_INFO_URL; 			//城市信息URL
	
	@Value("${bqy.city.info.url_2}")
	private String BQY_CITY_INFO_URL_2; 		//城市信息URL
	
	@Value("${bqy.hotel.facility.url}")
	private String BQY_HOTEL_FACILITY_URL;		//酒店设施服务详情URL

	@Value("${bqy.hotel.image.url}")
	private String BQY_HOTEL_IMAGE_URL;			//酒店图片URL
	
	@Value("${bqy.hotel.room.image.url}")
	private String BQY_HOTEL_ROOM_IMAGE_URL;	//酒店房型图片URL
	
	@Value("${bqy.hotel.room.price.url}")
	private String BQY_HOTEL_ROOM_PRICE_URL;		//酒店房间价格
	
	@Value("${bqy.hotel.book.order.url}")
	private String BQY_HOTEL_BOOK_ORDER_URL;		//酒店房间试预订
	
	@Value("${bqy.hotel.create.order.url}")
	private String BQY_HOTEL_CREATE_ORDER_URL;		//创建订单
	
	private String TOKEN;
	
	@Autowired
	private MongoTemplate mongoTemplate1;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<HotelId> queryHotelIdList() {
		logger.info("获取所有酒店ID开始...");
		String result = HttpClientUtil.doJsonPost(BQY_HOTELID_LIST_URL);
		List<HotelId> hotelIdList = null; 
		if (StringUtils.isNoneBlank(result.trim())) {
//			result = result.replace("\\", "");
			ResponseResult<String> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<String>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					String resultStr = responseResult.getResult();
					hotelIdList = JsonUtil.toBean(resultStr, new TypeReference<List<HotelId>>(){});
				}
			}
		}else {
			throw new GSSException("获取BQY酒店ID", "0111", "获取BQY所有酒店ID失败...");
		}
		logger.info("获取所有酒店ID成功...");
		return hotelIdList;
	}
	
	@Override
	public HotelInfo queryHotelInfo(QueryHotelInfoParam query) {
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		HotelInfo hotelInfo = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_INFO_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<HotelInfo> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<HotelInfo>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					hotelInfo = responseResult.getResult();
				}
			}
		}else {
			throw new GSSException("获取BQY酒店信息失败!", "0111", "BQY酒店信息返回空值");
		}
		return hotelInfo;
	}
	
	@Override
	public List<HotelInfoListEntity> queryHotelList(QueryHotelListParam query) {
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		List<HotelInfoListEntity> hotelList = null;
		logger.info("BQY酒店列表开始查询...");
		// 将请求参数转换为json格式
		String paramJson = JsonUtil.toJson(query);
		// 请求酒店列表
		String result = HttpClientUtil.doJsonPost(BQY_ALL_HOTEL_LIST, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			// 将返回数据转换成json对象
			ResponseResult<HotelList> responseResult = JsonUtil.toBean(result,
					new TypeReference<ResponseResult<HotelList>>() {
					});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					logger.info("BQY酒店列表获取成功!");
					hotelList = responseResult.getResult().getHotelList();
				} else {
					logger.info("BQY酒店列表获取失败!");
					throw new GSSException("获取酒店列表", "0112", "酒店列表请求失败!");
				}
			} else {
				logger.info("BQY酒店列表json格式转换实体失败!");
			}

		} else {
			logger.info("BQY酒店列表获取失败!");
			throw new GSSException("获取BQY酒店列表", "0111", "BQY酒店列表请求返回空值");
		}
		return hotelList;
	}

	@Override
	public HotelEntity queryHotelDetail(QueryHotelParam query) {
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		HotelEntity hotelEntity = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_DETAIL_URL, paramJson);
		System.out.println(result);
		if (StringUtils.isNoneBlank(result.trim())) {
			// 将返回数据转换成json对象
			ResponseResult<HotelEntity> responseResult = JsonUtil.toBean(result,
					new TypeReference<ResponseResult<HotelEntity>>() {});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					hotelEntity = responseResult.getResult();
				}
			} 
		} else {
			throw new GSSException("获取TC酒店详情", "0111", "BQY酒店详情请求返回空值");
		}
		return hotelEntity;
	}

	@Override
	public CityInfo queryCityInfo(QueryCityInfoParam query) {
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		CityInfo cityInfo = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_CITY_INFO_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<CityInfo> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<CityInfo>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					logger.info("BQY城市信息获取成功!");
					cityInfo = responseResult.getResult();
				} else {
					logger.info("BQY城市信息获取失败!");
					throw new GSSException("获取BQY城市信息", "0112", "城市信息为空!");
				}
			}else {
				logger.info("BQY城市信息json转换实体失败!");
			}
		}else {
			throw new GSSException("获取BQY城市信息失败!", "0111", "BQY城市信息返回空值");
		}
		return cityInfo;
	}

	@Override
	public List<HotelRoomFacility> queryHotelRoomFacility(QueryHotelParam query) {
		logger.info("BQY酒店房间设施信息获取开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		List<HotelRoomFacility> roomFacilityList = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_FACILITY_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<List<HotelRoomFacility>> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<List<HotelRoomFacility>>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					roomFacilityList = responseResult.getResult();
				}
			}
		}else {
			throw new GSSException("获取BQY酒店房间设施信息失败!", "0111", "BQY酒店房间设施信息返回空值");
		}
		logger.info("BQY酒店房间设施信息获取成功!");
		return roomFacilityList;
	}

	@Override
	public List<ImageList> queryHotelImage(QueryHotelParam query) {
		logger.info("BQY酒店图片获取开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		List<ImageList> imageList = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_IMAGE_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<List<ImageList>> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<List<ImageList>>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					imageList = responseResult.getResult();
				}
			}
		}else {
			throw new GSSException("获取BQY酒店图片失败!", "0111", "BQY酒店图片返回空值");
		}
		logger.info("BQY酒店图片获取成功!");
		return imageList;
	}
	

	@Override
	public List<RoomImageList> queryHotelRoomImage(QueryHotelParam query) {
		logger.info("BQY酒店房型图片获取开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		List<RoomImageList> imageList = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_ROOM_IMAGE_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<List<RoomImageList>> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<List<RoomImageList>>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					imageList = responseResult.getResult();
				} 
			}
		}else {
			throw new GSSException("获取BQY酒店房型图片失败!", "0111", "BQY酒店房型图片返回空值");
		}
		logger.info("BQY酒店房型图片获取成功!");
		return imageList;
	}
	
	@Override
	public List<RoomPriceItem> queryHotelRoomPrice(QueryHotelParam query) {
		// BQY_HOTEL_HOTEL_PRICE
		logger.info("BQY酒店房间获取开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		List<RoomPriceItem> roomPriceList = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_ROOM_PRICE_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<List<RoomPriceItem>> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<List<RoomPriceItem>>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					roomPriceList = responseResult.getResult();
				} 
			}
		}else {
			throw new GSSException("获取BQY酒店房型图片失败!", "0111", "BQY酒店房型图片返回空值");
		}
		logger.info("BQY酒店房型图片获取成功!");
		return roomPriceList;
	}
	
	
	@Override
	public HotelLocationEntity queryCityInfo2(QueryHotelIdParam query) {
		logger.info("BQY城市信息获取开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		HotelLocationEntity cityInfo = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_CITY_INFO_URL_2, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<HotelLocationEntity> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<HotelLocationEntity>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					cityInfo = responseResult.getResult();
				}
			}
		}else {
			throw new GSSException("获取BQY城市信息失败!", "0111", "BQY城市信息返回空值");
		}
		logger.info("BQY城市信息获取成功!");
		return cityInfo;
	}
	
	@Override
	public BookOrderResponse isBookOrder(BookOrderParam query) {
		logger.info("BQY酒店试预定开始...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		BookOrderResponse bookOrderResponse = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_BOOK_ORDER_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<BookOrderResponse> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<BookOrderResponse>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					bookOrderResponse = responseResult.getResult();
				}
			}
		}else {
			throw new GSSException("酒店试预定失败!", "0111", "酒店试预定返回空值");
		}
		logger.info("BQY酒店试预定结束!");
		return bookOrderResponse;
	}
	
	@Override
	public String createOrder(CreateOrderReq createOrderReq) {
		//BQY_HOTEL_CREATE_ORDER_URL
		logger.info("BQY酒店订单创建开始...");
		createOrderReq.setAgentId(Long.parseLong(BQY_AGENTID));
		createOrderReq.setBookingUserId(BQY_AGENTID);
		createOrderReq.setToken(md5Encryption());
		String orderNo = null;
		String paramJson = JsonUtil.toJson(createOrderReq);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_CREATE_ORDER_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			ResponseResult<String> responseResult = JsonUtil.toBean(result, new TypeReference<ResponseResult<String>>(){});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					orderNo = responseResult.getResult();
				}else {
					logger.error("BQY酒店订单创建失败!");
					throw new GSSException("酒店订单创建失败!", "0111", "酒店订单创建返回空值");
				}
			}
		}else {
			throw new GSSException("酒店订单创建失败!", "0111", "酒店订单创建返回空值");
		}
		logger.info("BQY酒店订单创建结束!");
		return orderNo;
	}
	
	/**
	 * 根据酒店ID获取酒店信息
	 */
	@Override
	@Async
	public void pullHotelInfoByIdList(List<HotelId> hotelIdList) {
		logger.info(Thread.currentThread().getName() + "线程开始拉取数据...集合大小为：" + hotelIdList.size());
		for (HotelId hotelId : hotelIdList) {
			QueryHotelInfoParam hotelInfoParam = new QueryHotelInfoParam();
			hotelInfoParam.setHotelId(hotelId.getHotelId());
			HotelInfo hotelInfo = queryHotelInfo(hotelInfoParam);
			
			hotelInfoParam.setHotelId(hotelId.getHotelId());
			hotelInfo.setId(hotelId.getHotelId());
			//酒店图片
			QueryHotelParam queryHotelParam = new QueryHotelParam();
			//queryHotelParam.setHotelId(hotelId.getHotelId());
			//TODO 酒店ID需要改
			queryHotelParam.setHotelId(hotelInfo.getHotelId());
			//List<ImageList> hotelImageList = queryHotelImage(queryHotelParam);
			//hotelInfo.setHotelImageList(hotelImageList);
			hotelInfo.setSupplierNo("411805040103290132");
			hotelInfo.setLatestUpdateTime(sdf.format(new Date()));
			hotelInfo.setSaleStatus(1);
			//TODO 随机最低价格
			hotelInfo.setLowPrice(new BigDecimal((int)((Math.random() + 1) * 100)));
			//保存酒店信息
			mongoTemplate1.save(hotelInfo);
			//保存中间表
			saveMidHol(hotelId, hotelInfo);
		}
		logger.info(Thread.currentThread().getName() + "线程完成拉取数据...");
	}

	@Override
	public void saveMidHol(HotelId hotelId, HotelInfo hotelInfo) {
		/**
		 * 判断中间表中是否有相同的酒店
		 * 通过酒店经纬度和酒店电话匹配是否为同一家酒店
		 */
		//纬度
		String latitude = hotelInfo.getLatitude().toString();
		//经度
		String longitude = hotelInfo.getLongitude().toString();
		//电话
		String mobile = hotelInfo.getMobile();
		
		List<HolMidBaseInfo> holMidList = SearchHoltel(latitude, longitude, mobile);
		if (null != holMidList && holMidList.size() > 0) {
			if (holMidList.size() == 1) {
				//合并酒店
				HolMidBaseInfo holMid = holMidList.get(0);
				holMid.setBqyResId(hotelInfo.getHotelId());
				holMid.setBqyResName(hotelInfo.getHotelName());
				int lowPrice = hotelInfo.getLowPrice().intValue();
				if (holMid.getMinPrice() > lowPrice) {
					holMid.setMinPrice(lowPrice);
				}
				mongoTemplate1.save(holMid);
				System.out.println(JsonUtil.toJson(holMid));
			}else if (holMidList.size() > 1) {
				throw new GSSException("bqy拉取酒店信息", "0111", "酒店纬度:" + latitude +"经度:" + longitude + "电话:" + mobile + "在中间表中有" + holMidList.size() + "个");
			}
		}else {
			/**
			 * 保存中间表
			 */
			HolMidBaseInfo holMidBaseInfo = new HolMidBaseInfo();
			//holMidBaseInfo.setId(BQYIDPRE + hotelId.getHotelId());
			holMidBaseInfo.setId(IdWorker.getId());
			holMidBaseInfo.setBqyResId(hotelId.getHotelId());
			holMidBaseInfo.setBqyResName(hotelInfo.getHotelName());
			holMidBaseInfo.setProvName(hotelInfo.getProvinceName());
			//holMidBaseInfo.setCityId(Integer.parseInt(hotelInfo.getCityCode()));
			holMidBaseInfo.setCityName(hotelInfo.getCityName());
			holMidBaseInfo.setIsInter(1);
			//酒店品牌
			ResBrandInfo resBrandInfo = new ResBrandInfo();
			resBrandInfo.setResBrandName(hotelInfo.getHotelBrandName());
			resBrandInfo.setId(Long.parseLong(String.valueOf(hotelInfo.getHotelBrandId())));
			holMidBaseInfo.setBrandInfo(resBrandInfo);
			
			holMidBaseInfo.setAddress(hotelInfo.getAddress());
			holMidBaseInfo.setResPhone(hotelInfo.getMobile());
			holMidBaseInfo.setIntro(hotelInfo.getDescription());
			//酒店等级
			holMidBaseInfo.setResGrade(tcHolGrade(hotelInfo.getHotelStar()));
			
			//酒店坐标
			holMidBaseInfo.setLatLonType(hotelInfo.getCoordinatesType());
			holMidBaseInfo.setLat(hotelInfo.getLatitude().toString());
			holMidBaseInfo.setLon(hotelInfo.getLongitude().toString());
			
			//holMidBaseInfo.setCountryName("中国");
			//一句话介绍...酒店描述
			//holMidBaseInfo.setShortIntro(hotelInfo.getDescription());
			
			//酒店图片
			holMidBaseInfo.setTitleImg(hotelInfo.getTitleImgUrl());
			
			holMidBaseInfo.setBookRemark(hotelInfo.getRoadCross());
			holMidBaseInfo.setMinPrice(hotelInfo.getLowPrice().intValue());
			holMidBaseInfo.setSupplierNo(hotelInfo.getSupplierNo());
			holMidBaseInfo.setLatestUpdateTime(hotelInfo.getLatestUpdateTime());
			holMidBaseInfo.setSaleStatus(1);
			holMidBaseInfo.setBookTimes(1L);
			mongoTemplate1.save(holMidBaseInfo);
		}
	}
	

	/**
	 * 移除mongoDB中的缓存信息
	 */
	@Override
	public void deleteMongoDBData() {
		mongoTemplate1.remove(new Query(), HotelInfo.class);
		//mongoTemplate1.remove(new Query(), CityDetail.class);
		//mongoTemplate1.remove(new Query(), HotelId.class);
		//TODO 需要修改中间表的清空
		mongoTemplate1.remove(new Query(), HolMidBaseInfo.class);
		//mongoTemplate1.remove(new Query(Criteria.where("supplierNo").is("411805040103290132")), HolMidBaseInfo.class);
	}
	
	@Override
	public List<HolMidBaseInfo> SearchHoltel(String lat, String lon, String phone) {
		List<HolMidBaseInfo> holMidList = null;
		int latSubLen = lat.substring(lat.indexOf(".")).length();
		int lonSubLen = lon.substring(lon.indexOf(".")).length();
		String latitude = null;
		String longitude = null;
		if (latSubLen >= 4) {
			latitude = lat.substring(0, lat.indexOf(".") + 4);
		}else {
			latitude = lat.substring(0, lat.indexOf(".") + 2);
		}
		if (lonSubLen >= 4) {
			longitude = lon.substring(0, lon.indexOf(".") + 4);
		}else {
			longitude = lon.substring(0, lon.indexOf(".") + 2);
		}
		String mobile = null;
		List<String> phoneList = new ArrayList<>();
		if (StringUtils.isNotBlank(phone) && phone.length() >= 7) {
			if (phone.contains("、")) {
				String[] phoneArr = phone.split("、");
				for (String p : phoneArr) {
					if (p.length() >= 7) {
						if (p.contains("-")) {
							String[] holPhoneArr = p.split("-");
							for (String holPhone : holPhoneArr) {
								if (holPhone.length() >= 7) {
									phoneList.add(holPhone);
								}
							}
						}else {
							if (p.contains("(")) {
								p = p.substring(0, phone.indexOf("("));
								phoneList.add(p);
							}else {
								phoneList.add(p);
							}
						}
					}
				}
			}else if (phone.contains("-")) {
				String[] holPhoneArr = phone.split("-");
				for (String holPhone : holPhoneArr) {
					if (holPhone.length() >= 7) {
						if (holPhone.contains("/")) {
							String[] phoneArr = holPhone.split("/");
							for (String p : phoneArr) {
								phoneList.add(p);
							}
						}else if (holPhone.contains("\\")) {
							String[] phoneArr = holPhone.split("\\");
							for (String p : phoneArr) {
								phoneList.add(p);
							}
						}else {
							phoneList.add(holPhone);
							}
						}
					}
				}else if (phone.contains("(")) {
					phone = phone.substring(0, phone.indexOf("("));
					phoneList.add(phone);
				}else {
					phoneList.add(phone);
				}
			if (phoneList.size() == 1) {
				holMidList = searchHol(latitude, longitude, phoneList.get(0));
			}else if (phoneList.size() > 1) {
				Criteria criteria = new Criteria();
				List<Criteria> criteriaList = new ArrayList<>();
				for (String p : phoneList) {
					if (StringUtils.isNoneBlank(p) && p.length() >= 7) {
						Criteria orCriteria = createCriteria(latitude, longitude, p);
						criteriaList.add(orCriteria);
					}
				}
				if (criteriaList.size() == 0) {
					holMidList = searchHol(latitude, longitude, null);
				}else if (criteriaList.size() == 1) {
					criteria.orOperator(criteriaList.get(0));
				}else if (criteriaList.size() == 2) {
					criteria.orOperator(criteriaList.get(0), criteriaList.get(1));
				}else {
					criteria.orOperator(criteriaList.get(0), criteriaList.get(1), criteriaList.get(2));
				}
				holMidList = mongoTemplate1.find(new Query(criteria), HolMidBaseInfo.class);
			}
		}else {
			holMidList = searchHol(latitude, longitude, mobile);
		}
		return holMidList;
	}
	
	private List<HolMidBaseInfo> searchHol(String latitude, String longitude, String mobile) {
		Criteria criteria = createCriteria(latitude, longitude, mobile);
		List<HolMidBaseInfo> holMidList = mongoTemplate1.find(new Query(criteria), HolMidBaseInfo.class);
		return holMidList;
	}

	private Criteria createCriteria(String latitude, String longitude, String mobile) {
		Criteria criteria = new Criteria();
		criteria.and("lon").regex("^" + longitude + ".*$")
					.and("lat").regex("^" + latitude + ".*$");
		if (StringUtils.isNoneBlank(mobile) && mobile.length() >= 7) {
			criteria.and("resPhone").regex("^.*" + mobile + ".*$");
		}
		return criteria;
	}

	/**
	 * 封装城市信息
	 * @param cityDetail
	 * @param cityInfo
	 * @return
	 */
	private void packingCityLocation(CityDetail cityDetail, HotelLocationEntity cityInfo) {
		//酒店品牌
		Set<HotelBrand> hotelbrandSet = new HashSet<>();
		List<HotelBrand> hotelbrandList = cityInfo.getHotelbrand();
		for (HotelBrand hotelBrand : hotelbrandList) {
			//hotelbrandSet.add(hotelBrand.getHotelBrandName());
			String brandType = hotelBrand.getHotelBrandType();
			if ("经济".equals(brandType.trim()) || "舒适".equals(brandType.trim())) {
				hotelBrand.setHotelBrandType("1");
			}else if ("高档".equals(brandType.trim())) {
				hotelBrand.setHotelBrandType("2");
			}else if ("豪华".equals(brandType.trim())) {
				hotelBrand.setHotelBrandType("3");
			}
			hotelbrandSet.add(hotelBrand);
		}
		cityDetail.setHotelBrands(hotelbrandSet);
		
		//设施服务
		Set<String> hotelFacilitySet = new HashSet<>();
		List<HotelFacility> hotelFacilityList = cityInfo.getHotelFacility();
		
		for (HotelFacility hotelFacility : hotelFacilityList) {
			hotelFacilitySet.add(hotelFacility.getParentName());
		}
		cityDetail.setHotelFacilitys(hotelFacilitySet);
		
		//位置信息
		List<InfoShowlist> infoShowlist = cityInfo.getInfolist();
		//封装城市位置信息
		for (InfoShowlist infoShow : infoShowlist) {
			String keyword = infoShow.getKeyword();
			List<Information> infoList = infoShow.getInfolist();
			Set<String> cityAirRailWaySet = cityDetail.getCityAirRailWay();
			if (null == cityAirRailWaySet) {
				cityAirRailWaySet = new HashSet<>();
			}
			switch (keyword) {
			case "商圈":
				Set<String> businessSectionInfoSet = new HashSet<>();
				for (Information info : infoList) {
					businessSectionInfoSet.add(info.getInfoName());
				}
				cityDetail.setCityBusinessSectionInfo(businessSectionInfoSet);
				break;
			case "行政区":
				Set<String> cityAreaSet = new HashSet<>();
				for (Information info : infoList) {
					cityAreaSet.add(info.getInfoName());
				}
				cityDetail.setCityArea(cityAreaSet);
				break;
			case "地铁":
				Set<String> citySubWaySet = new HashSet<>();
				for (Information info : infoList) {
					citySubWaySet.add(info.getInfoName());
				}
				cityDetail.setCitySubWay(citySubWaySet);
				break;
			case "景点/景区":
				Set<String> cityScenicSet = new HashSet<>();
				for (Information info : infoList) {
					cityScenicSet.add(info.getInfoName());
				}
				cityDetail.setCityScenic(cityScenicSet);
				break;
			case "飞机场":
				for (Information info : infoList) {
					cityAirRailWaySet.add(info.getInfoName());
				}
				cityDetail.setCityAirRailWay(cityAirRailWaySet);
				break;
			case "火车站":
				for (Information info : infoList) {
					cityAirRailWaySet.add(info.getInfoName());
				}
				cityDetail.setCityAirRailWay(cityAirRailWaySet);
				break;
			}
		}
	}
	
	/**
	 * 拉取城市信息
	 */
	@Override
	@Async
	public void pullCityDetail(){
		//城市xml文件
		Document document = DocumentUtil.getDocumentByFileName2("city.xml");
		Element root = document.getRootElement();
		//省份元素迭代器
		Iterator provinces = root.elementIterator();
		
		while (provinces.hasNext()) {
			//省份元素
			Element province = (Element) provinces.next();
			//省份编号
			String provinceCode = province.elementText("id");
			//市级元素迭代器
			Iterator citys = province.element("citys").elementIterator();
			//查询酒店id参数封装类
			QueryHotelIdParam cityInfoParam = new QueryHotelIdParam();
			
			while (citys.hasNext()) {
				Element city = (Element) citys.next();
				//城市编号
				String cityCode = city.elementText("id");
				cityInfoParam.setCityId(cityCode);
				
				CityDetail cityDetail = new CityDetail();
				//获取城市信息
				cityDetail.setProvince(provinceCode);	
				cityDetail.setProvinceName(province.elementText("ZHName"));
				cityDetail.setCity(city.elementText("id"));
				cityDetail.setCityName(city.elementText("ZHName"));
				
				HotelLocationEntity hotelLocationEntity = queryCityInfo2(cityInfoParam);
				packingCityLocation(cityDetail, hotelLocationEntity);
				mongoTemplate1.save(cityDetail);
			}
		}
	}
	
	/**
	 * bqy酒店等级转tc
	 * @param hotelStar
	 * @return
	 */
	private String tcHolGrade(String hotelStar){
		if (StringUtils.isNoneBlank(hotelStar)) {
			switch (hotelStar) {
			case "5.00":
				return "23";
			case "4.00":
				//四星级
				return "24";
			case "3.00":
				//三星级
				return "26";
			default:
				return "27";
			}
		}
		return "27";
	}
	
	/**
	 * 加密代理人id
	 * @return
	 */
	private String md5Encryption() {
		if (StringUtils.isBlank(TOKEN)) {
			TOKEN = md5Encryption((md5Encryption(BQY_AGENTID))+BQY_KEY);
		}
		return TOKEN;
	}

	/**
	 * md5加密
	 * @param encryption
	 * @return
	 */
	public String md5Encryption(String encryption) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(encryption.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	@Override
	@Async
	public Future<List<ImageList>> asyncHotelImage(QueryHotelParam query) {
		List<ImageList> imageList = queryHotelImage(query);
		return new AsyncResult<List<ImageList>>(imageList);
	}

	@Override
	@Async
	public Future<HotelEntity> asyncHotelDetail(QueryHotelParam query) {
		HotelEntity hotelEntity = queryHotelDetail(query);
		return new AsyncResult<HotelEntity>(hotelEntity);
	}

}

 
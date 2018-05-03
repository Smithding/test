package com.tempus.gss.product.hol.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.TypeReference;
import com.tempus.gss.bbp.util.DocumentUtil;
import com.tempus.gss.exception.GSSException;
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
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryCityInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelIdParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelInfoParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelListParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.HotelLocationEntity;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.thread.CityDetailTask;
import com.tempus.gss.product.hol.thread.HotelInfoTask;
import com.tempus.gss.util.JsonUtil;

import httl.spi.codecs.json.JSON;

@Service
public class BQYHotelServiceImpl implements IBQYHotelInterService {

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
	
	private String TOKEN;
	
	private static int PAGE_SIZE = 500;			//查询id数量
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private IBQYHotelInterService bqyHotelService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	

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
		logger.info("BQY酒店信息获取开始...");
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
		logger.info("BQY酒店信息获取成功...");
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
		logger.info("BQY酒店详情开始查询...");
		query.setAgentId(Long.parseLong(BQY_AGENTID));
		query.setToken(md5Encryption());
		HotelEntity hotelEntity = null;
		String paramJson = JsonUtil.toJson(query);
		String result = HttpClientUtil.doJsonPost(BQY_HOTEL_DETAIL_URL, paramJson);
		if (StringUtils.isNoneBlank(result.trim())) {
			// 将返回数据转换成json对象
			ResponseResult<HotelEntity> responseResult = JsonUtil.toBean(result,
					new TypeReference<ResponseResult<HotelEntity>>() {
					});
			if (responseResult != null) {
				if (responseResult.getResponseStatus() != null && responseResult.getResponseStatus().getAck() == 1) {
					hotelEntity = responseResult.getResult();
				}
			} 
		} else {
			throw new GSSException("获取TC酒店详情", "0111", "BQY酒店详情请求返回空值");
		}
		logger.info("BQY酒店详情获取成功!");
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
	public void saveDataToMongoDB(QueryHotelListParam query) {
		//删除Mongo中的数据
		deleteMongoDBData();
		//开始拉取城市信息
		new CityDetailTask(bqyHotelService).start();
		//获取BQY酒店ID
		List<HotelId> hotelIdList = queryHotelIdList();
		//将获取的酒店ID保存到mongoDB中
		mongoTemplate.insert(hotelIdList, HotelId.class);
		//开始拉取酒店信息
		pullHotelDetail();
	}

	/**
	 * 根据酒店ID获取酒店信息
	 */
	@Override
	public void pullHotelInfoByIdList(List<HotelId> hotelIdList) {
		for (HotelId hotelId : hotelIdList) {
			QueryHotelInfoParam hotelInfoParam = new QueryHotelInfoParam();
			hotelInfoParam.setHotelId(hotelId.getHotelId());
			HotelInfo hotelInfo = this.queryHotelInfo(hotelInfoParam);
			
			hotelInfoParam.setHotelId(hotelId.getHotelId());
			hotelInfo.setId(hotelId.getHotelId());
			//酒店图片
			QueryHotelParam queryHotelParam = new QueryHotelParam();
			queryHotelParam.setHotelId(hotelId.getHotelId());
			List<ImageList> hotelImageList = this.queryHotelImage(queryHotelParam);
			hotelInfo.setHotelImageList(hotelImageList);
			//保存酒店信息
			mongoTemplate.save(hotelInfo);
		}
	}
	

	/**
	 * 移除mongoDB中的缓存信息
	 */
	@Override
	public void deleteMongoDBData() {
		mongoTemplate.remove(new Query(), HotelInfo.class);
		mongoTemplate.remove(new Query(), CityDetail.class);
		mongoTemplate.remove(new Query(), HotelId.class);
	}

	/**
	 * 封装城市信息
	 * @param cityDetail
	 * @param cityInfo
	 * @return
	 */
	private void packingCityLocation(CityDetail cityDetail, HotelLocationEntity cityInfo) {
		//酒店品牌
		Set<String> hotelbrandSet = new HashSet<>();
		List<HotelBrand> hotelbrandList = cityInfo.getHotelbrand();
		for (HotelBrand hotelBrand : hotelbrandList) {
			hotelbrandSet.add(hotelBrand.getHotelBrandName());
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
				mongoTemplate.save(cityDetail);
			}
		}
	}

	
	/**
	 * 拉取酒店信息
	 */
	public void pullHotelDetail() {
		//获取mongoDB中存储的id数量
		long totalHotelIdNum = mongoTemplate.count(new Query(), HotelId.class);
		long count = 1;
		if ((totalHotelIdNum / PAGE_SIZE) > 1) {
			count = totalHotelIdNum % PAGE_SIZE == 0 ? totalHotelIdNum / PAGE_SIZE : totalHotelIdNum / PAGE_SIZE + 1;
		}
		for (int i = 0; i < count; i++) {
			long lastIndex = ((i * PAGE_SIZE) + PAGE_SIZE) > totalHotelIdNum ? totalHotelIdNum : (i * PAGE_SIZE) + PAGE_SIZE;
			
			Query query = new Query().skip(i * PAGE_SIZE).limit(Integer.valueOf(String.valueOf(lastIndex)));
			List<HotelId> hotelIdList = mongoTemplate.find(query, HotelId.class);
			//开启线程拉去酒店数量
			new HotelInfoTask(bqyHotelService, hotelIdList).start();
		}
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

}
 
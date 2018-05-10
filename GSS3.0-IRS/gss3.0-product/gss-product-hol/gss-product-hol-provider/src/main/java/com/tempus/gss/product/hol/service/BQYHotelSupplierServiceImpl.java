package com.tempus.gss.product.hol.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelEntity;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.entity.vo.bqy.RoomType;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.QueryHotelParam;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.BaseRoomInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.BedTypeInfoItem;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomInfoItem;
import com.tempus.gss.product.hol.api.entity.vo.bqy.room.RoomPriceItem;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.vo.Agent;

@Service
public class BQYHotelSupplierServiceImpl implements IBQYHotelSupplierService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private IBQYHotelInterService bqyHotelInterService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public TCResponse<HotelInfo> queryHotelListForBack(HotelListSearchReq hotelSearchReq) {

		logger.info("bqy查询酒店列表开始");
		if (StringUtil.isNullOrEmpty(hotelSearchReq)) {
			logger.error("hotelSearchReq查询条件为空");
			throw new GSSException("获取酒店列表", "0101", "hotelSearchReq查询条件为空");
		}

		if (StringUtil.isNullOrEmpty(hotelSearchReq.getResId())
				&& StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode())
				&& StringUtil.isNullOrEmpty(hotelSearchReq.getResGradeId())
				&& StringUtil.isNullOrEmpty(hotelSearchReq.getKeyword())) {
			hotelSearchReq.setCityCode("北京");
		}

		if (StringUtil.isNullOrEmpty(hotelSearchReq.getPageCount())) {
			logger.error("页码为空");
			throw new GSSException("获取酒店列表", "0107", "页码为空");
		}
		if (StringUtil.isNullOrEmpty(hotelSearchReq.getRowCount())) {
			logger.error("每页条数为空");
			throw new GSSException("获取酒店列表", "0108", "每页条数为空");
		}
		TCResponse<HotelInfo> response = new TCResponse<HotelInfo>();
		Query query = new Query();
		Criteria criatira = new Criteria();

		int skip = (hotelSearchReq.getPageCount() - 1) * (hotelSearchReq.getRowCount());
		query.skip(skip);
		query.limit(hotelSearchReq.getRowCount());

		//城市名称
		if (StringUtil.isNotNullOrEmpty(hotelSearchReq.getCityCode())) {
			criatira.and("cityName").is(hotelSearchReq.getCityCode());
		}

		//酒店名称关键字查询
		if (StringUtil.isNotNullOrEmpty(hotelSearchReq.getKeyword())) {
			String keyword = hotelSearchReq.getKeyword().trim();
			String escapeHtml = StringEscapeUtils.unescapeHtml(keyword);
			String[] fbsArr = { "(", ")" }; // "\\", "$", "(", ")", "*", "+",
											// ".", "[", "]", "?", "^", "{",
											// "}", "|"
			for (String key : fbsArr) {
				if (escapeHtml.contains(key)) {
					escapeHtml = escapeHtml.replace(key, "\\" + key);
				}
			}
			criatira.and("hotelName").regex("^.*" + escapeHtml + ".*$");// "^.*"+hotelName+".*$"
		}

		//酒店ID
		if (StringUtil.isNotNullOrEmpty(hotelSearchReq.getResId())) {
			criatira.and("_id").is(Long.valueOf(hotelSearchReq.getResId()));
		}
		
		//酒店等级
		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGradeId())){
			String hotelStar = hotelSearchReq.getResGradeId();
			switch (hotelStar) {
			case "23":
				//五星级
				criatira.and("hotelStar").is("5.00");
				break;
			case "24":
				//四星级
				criatira.and("hotelStar").is("4.00");
				break;
			case "26":
				//三星级
				criatira.and("hotelStar").is("3.00");
				break;
			case "27":
				//二星及二星以下
				criatira.and("hotelStar").in("2.00","1.00","0.00");
				break;
			}
		}
		
		query = query.addCriteria(criatira);
		// 获取酒店列表
		List<HotelInfo> hotelList = mongoTemplate.find(query, HotelInfo.class);
		// 查询总数
		int totalCount = (int) mongoTemplate.count(query, HotelInfo.class);
		// 总页数
		int totalPage = (int) (totalCount % hotelSearchReq.getRowCount() == 0
				? totalCount / hotelSearchReq.getRowCount() : (totalCount / hotelSearchReq.getRowCount() + 1));
		response.setTotalPatge(totalPage);
		response.setTotalCount(totalCount);
		response.setResponseResult(hotelList);
		logger.info("bqy查询酒店列表结束");
		return response;
	}

	@Override
	public TCResponse<ResBaseInfo> queryHotelList(HotelListSearchReq hotelSearchReq) {
		TCResponse<HotelInfo> hotelResult = queryHotelListForBack(hotelSearchReq);
		List<HotelInfo> hotelList = hotelResult.getResponseResult();
		List<ResBaseInfo> resList = new ArrayList<>();
		for (HotelInfo h : hotelList) {
			ResBaseInfo resBaseInfo = bqyConvertTcHotelEntity(h);
			resList.add(resBaseInfo);
		}
		TCResponse<ResBaseInfo> result = new TCResponse<>();
		result.setResponseResult(resList);
		result.setPageNumber(hotelResult.getPageNumber());
		result.setTotalCount(hotelResult.getTotalCount());
		result.setTotalPatge(hotelResult.getTotalPatge());
		return result;
	}

	@Override
	public int saleStatusUpdate(Agent agent, Long hotelId, Integer saleStatus) {
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
			throw new GSSException("修改bqy酒店可售状态", "0102", "agent对象为空");
		}
		if (StringUtil.isNullOrEmpty(hotelId)) {
			throw new GSSException("修改bqy酒店可售状态", "0118", "传入RESID为空");
		}
		try {
			SimpleDateFormat sdfupdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String updateTime = sdfupdate.format(new Date());
			Query query = Query.query(Criteria.where("_id").is(hotelId));
			Update update = Update.update("saleStatus", saleStatus).set("latestUpdateTime", updateTime);
			mongoTemplate.upsert(query, update, HotelInfo.class);
		} catch (Exception e) {
			logger.error("修改可售状态出错" + e);
			throw new GSSException("修改可售状态", "0118", "修改可售状态失败");
		}
		return 1;
	}

	@Override
	public ResBaseInfo singleHotelDetail(String hotelId) {
		Criteria criteria = Criteria.where("_id").is(Long.parseLong(hotelId));
		List<HotelInfo> hotelList = mongoTemplate.find(new Query(criteria),HotelInfo.class);
		if (null != hotelList && hotelList.size() > 0) {
			HotelInfo hotelInfo = hotelList.get(0);
			ResBaseInfo resBaseInfo = bqyConvertTcHotelEntity(hotelInfo);
			QueryHotelParam query = new QueryHotelParam();
			Calendar date = Calendar.getInstance();  
	        Calendar dateAdd = Calendar.getInstance();
	        dateAdd.add(Calendar.MONTH, 2);
	        dateAdd.add(Calendar.DAY_OF_MONTH, -1);
			query.setCityCode(hotelInfo.getCityCode());
			query.setHotelId(hotelInfo.getHotelId());
			query.setCheckInTime(sdf.format(date.getTime()));
			query.setCheckOutTime(sdf.format(dateAdd.getTime()));
			HotelEntity hotelEntity = bqyHotelInterService.queryHotelDetail(query);
			List<RoomPriceItem> roomPriceItemList = hotelEntity.getRoomPriceItem();
			
			if (null != roomPriceItemList && roomPriceItemList.size() > 0) {
				List<ProDetails> proDetails = new ArrayList<>();
				
				for (RoomPriceItem roomPriceItem : roomPriceItemList) {
					ProDetails proDetail = new ProDetails();
					BaseRoomInfo baseRoomInfo = roomPriceItem.getBaseRoomInfo();
					proDetail.setProId(baseRoomInfo.getRoomTypeID());
					proDetail.setResProName(baseRoomInfo.getRoomName());
					proDetail.setRoomFloor(baseRoomInfo.getFloorRange());
					proDetail.setRoomSize(baseRoomInfo.getAreaRange());
					List<RoomInfoItem> roomInfoList = roomPriceItem.getRoomInfo();
					List<ResProBaseInfo> resProBaseInfos = new ArrayList<>();
					for (RoomInfoItem roomInfo : roomInfoList) {
						ResProBaseInfo resProBaseInfo = new ResProBaseInfo();
						resProBaseInfo.setResId(Long.parseLong(hotelId));
						//产品名称
						resProBaseInfo.setSupPriceName(roomInfo.getRoomName());
						List<BedTypeInfoItem> roomTypeList = baseRoomInfo.getRoomTypeId();
						//床型
						if (null != roomTypeList && roomTypeList.size() > 0){
							resProBaseInfo.setBedTypeName(roomTypeList.get(0).getBedType()+"\\" + roomTypeList.get(0).getBedName());
						}
						//房间ID
						resProBaseInfo.setProductUniqueId(Integer.parseInt(roomInfo.getRoomID()));
						//TODO 	是否无烟
						//TODO	是否有宽带
						//TODO	是否有早餐
					}
					proDetails.add(proDetail);
				}
				resBaseInfo.setProDetails(proDetails);
			}
			
			return resBaseInfo;
		}
		return null;
	}

	/**
	 * bqy酒店信息转换tc实体
	 * @param hotel
	 * @return
	 */
	private ResBaseInfo bqyConvertTcHotelEntity(HotelInfo hotel) {
		ResBaseInfo resBaseInfo = new ResBaseInfo();
		resBaseInfo.setAddress(hotel.getAddress());
		// 酒店品牌
		ResBrandInfo brandInfo = new ResBrandInfo();
		brandInfo.setResBrandName(hotel.getHotelBrandName());
		brandInfo.setId(Long.parseLong(String.valueOf(hotel.getHotelBrandId())));
		resBaseInfo.setBrandInfo(brandInfo);
		
		//酒店星级
		String hotelStar = hotel.getHotelStar();
		switch (hotelStar) {
		case "5.00"://23
			//五星级
			resBaseInfo.setResGradeId("23");
			resBaseInfo.setResGrade("五星级");
			break;
		case "4.00"://24
			//四星级
			resBaseInfo.setResGradeId("24");
			resBaseInfo.setResGrade("四星级");
			break;
		case "3.00"://26
			//三星级
			resBaseInfo.setResGradeId("26");
			resBaseInfo.setResGrade("三星级");
			break;
		case "2.00":	//27
			//二星及二星以下
			resBaseInfo.setResGradeId("27");
			resBaseInfo.setResGrade("经济型");
			break;
		case "1.00":	//27
			//二星及二星以下
			resBaseInfo.setResGradeId("27");
			resBaseInfo.setResGrade("经济型");
			break;
		case "0.00":	//27
			//二星及二星以下
			resBaseInfo.setResGradeId("27");
			resBaseInfo.setResGrade("经济型");
			break;
		}

		resBaseInfo.setCityId(Integer.parseInt(hotel.getCityCode()));
		resBaseInfo.setCityName(hotel.getCityName());
		resBaseInfo.setLocation(hotel.getRoadCross());
		resBaseInfo.setId(hotel.getId());
		resBaseInfo.setResId(hotel.getId());

		// 酒店图片
		List<ImgInfo> imgInfoList = new ArrayList<>();
		List<ImageList> hotelImageList = hotel.getHotelImageList();
		if (null != hotelImageList && hotelImageList.size() > 0) {
			for (ImageList i : hotelImageList) {
				ImgInfo imgInfo = new ImgInfo();
				imgInfo.setImageName(i.getTitleName());
				imgInfo.setImageUrl(i.getImageUrl());
				imgInfo.setResId(hotel.getHotelId());
				if (StringUtils.isNotBlank(i.getImageType())) {
					String imageType = i.getImageType();
					switch (imageType) {
					case "1":
						imgInfo.setImageLabel((byte)0);
						break;
					case "2":
						imgInfo.setImageLabel((byte)9);
						break;
					case "4":
						imgInfo.setImageLabel((byte)6);
						break;
					case "5":
						imgInfo.setImageLabel((byte)11);
						break;
					case "6":
						imgInfo.setImageLabel((byte)4);
						break;
					case "20":
						imgInfo.setImageLabel((byte)20);
						break;
					case "15":
						imgInfo.setImageLabel((byte)15);
						break;
					}
				}
			}
		}
		resBaseInfo.setImgInfoList(imgInfoList);

		resBaseInfo.setLatestUpdateTime(hotel.getLatestUpdateTime());
		if (null != hotel.getLowPrice()) {
			resBaseInfo.setMinPrice(hotel.getLowPrice().intValue());
			resBaseInfo.setResCommonPrice(hotel.getLowPrice().intValue());
		}
		resBaseInfo.setResName(hotel.getHotelName());
		resBaseInfo.setSaleStatus(hotel.getSaleStatus());
		resBaseInfo.setResPhone(hotel.getMobile());
		resBaseInfo.setShortIntro(hotel.getDescription());
		resBaseInfo.setIntro(hotel.getDescription());
		resBaseInfo.setSupplierNo(hotel.getSupplierNo());
		return resBaseInfo;
	}
}

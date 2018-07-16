package com.tempus.gss.product.hol.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.service.IBQYHotelConverService;

@Service
public class BQYHotelConverServiceImpl implements IBQYHotelConverService {

	@Autowired
	private MongoTemplate mongoTemplate1;
	
	@Override
	public List<ImgInfo> convertTCImg(List<ImageList> hotelImageList) {

		List<ImgInfo> imgInfoList = new ArrayList<>();
		if (null != hotelImageList && hotelImageList.size() > 0) {
			for (ImageList i : hotelImageList) {
				ImgInfo imgInfo = new ImgInfo();
				imgInfo.setImageName(i.getTitleName());
				imgInfo.setImageUrl(i.getImageUrl());
				imgInfo.setResId(i.getHotelId().longValue());
				imgInfo.setResProId(i.getRoomTypeId());
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
						imgInfo.setIsResProDefault(1);
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
				imgInfoList.add(imgInfo);
			}
		}
		return imgInfoList;
	
	}

	@Override
	public ResBaseInfo bqyConvertTcHotelEntity(HotelInfo hotel) {

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
		
		//GPS
		List<ResGPSInfo> liGps = new ArrayList<>();
		ResGPSInfo resGPSInfo=new ResGPSInfo();
		resGPSInfo.setLat(hotel.getLatitude().toString());
		resGPSInfo.setLon(hotel.getLongitude().toString());
		liGps.add(resGPSInfo);
		resBaseInfo.setResGPS(liGps);
		
		resBaseInfo.setCityId(Integer.parseInt(hotel.getCityCode()));
		resBaseInfo.setCityName(hotel.getCityName());
		resBaseInfo.setLocation(hotel.getRoadCross());
		resBaseInfo.setId(hotel.getId());
		resBaseInfo.setResId(hotel.getId());

		// 酒店图片
		List<ImageList> hotelImageList = hotel.getHotelImageList();
		List<ImgInfo> imgInfoList = convertTCImg(hotelImageList);
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

	@Override
	@Async
	public Future<ResBaseInfo> asyncConvertTcHotelEntity(Long hotelId) {
		Criteria criteria = Criteria.where("_id").is(hotelId);
		HotelInfo hotelInfo = mongoTemplate1.findOne(new Query(criteria),HotelInfo.class);
		ResBaseInfo resBaseInfo = bqyConvertTcHotelEntity(hotelInfo);
		return new AsyncResult<ResBaseInfo>(resBaseInfo);
	}

}

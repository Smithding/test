package com.tempus.gss.product.hol.thread;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.LogRecordHol;
import com.tempus.gss.product.hol.api.entity.ResNameSum;
import com.tempus.gss.product.hol.api.entity.ResToMinPrice;
import com.tempus.gss.product.hol.api.entity.request.tc.SingleHotelDetailReq;
import com.tempus.gss.product.hol.api.entity.response.ResIdList;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfoSum;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.product.hol.api.syn.IAsyncHolDoTask;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;

public class AsyncHolDoTaskImpl implements IAsyncHolDoTask {
	
	@Autowired
	MongoTemplate mongoTemplate1;
	
	@Autowired
	ITCHotelInterService hotelInterService;
	
	SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void updateResBaseInfo(Long resId, Integer minPrice) {
		ResBaseInfo mongoRes = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(resId)), ResBaseInfo.class);
 		ResBaseInfo resBaseInfo = null;
 		TCHotelDetailResult hotelDetail=singleHolRequest(resId, "res");
		if(StringUtil.isNotNullOrEmpty(hotelDetail)) {
			List<ResBaseInfo> resBaseInfos = hotelDetail.getResBaseInfos();
			if(StringUtil.isNotNullOrEmpty(resBaseInfos)) {
				resBaseInfo = resBaseInfos.get(0);
				pushCommonRes(resBaseInfo);
				resBaseInfo.setMinPrice(minPrice);
				resBaseInfo.setResCommonPrice(minPrice);
				if(StringUtil.isNotNullOrEmpty(mongoRes)) {
					String imgUrl = mongoRes.getImgUrl();
					if(StringUtils.isNotBlank(imgUrl)) {
						resBaseInfo.setImgUrl(imgUrl);
					}else {
						TCHotelDetailResult hotelImg=singleHolRequest(resId, "rimg");
						List<ImgInfo> resImages = hotelImg.getResImages();
						String titleImg = imgUrlToImgList(resImages, resId);
						resBaseInfo.setImgUrl(titleImg);
					}
				}
				mongoTemplate1.save(resBaseInfo, "resBaseInfo");
			}
		}
	}

	@Override
	public void saveMInPriceAndMidHol(Long resId, Integer minPrice) {
		ResToMinPrice findOne = mongoTemplate1.findOne(new Query(Criteria.where("tcId").is(resId)),ResToMinPrice.class);
		if(minPrice != null) {
			if(StringUtil.isNotNullOrEmpty(findOne)) {
				if(findOne.getMinPrice() != null) {
					if(minPrice != null) {
						int compareTo = findOne.getMinPrice().compareTo(new BigDecimal(minPrice));
						if(compareTo > 0) {
							findOne.setMinPrice(new BigDecimal(minPrice));
							if(StringUtils.isNotEmpty(findOne.getNoPriceStatus())) {
								if(findOne.getNoPriceStatus().contains("1")) {
									String replaceAll = findOne.getNoPriceStatus().replaceAll("1", "");
									findOne.setNoPriceStatus(replaceAll);
								}
							}
							mongoTemplate1.save(findOne, "resToMinPrice");
						}
					}
				}
			}
		}else {
			if(StringUtil.isNotNullOrEmpty(findOne)) {
				if(findOne.getMinPrice() == null) {
					if(findOne.getNoPriceStatus()==null || "".equals(findOne.getNoPriceStatus())) {
						findOne.setNoPriceStatus("1");
					}else {
						if(!findOne.getNoPriceStatus().contains("1")) {
							findOne.setNoPriceStatus("1"+findOne.getNoPriceStatus());
						}
					}
					mongoTemplate1.save(findOne, "resToMinPrice");
				}
				HolMidBaseInfo holMidBaseInfo = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(findOne.getId())),HolMidBaseInfo.class);
				if(StringUtil.isNotNullOrEmpty(holMidBaseInfo)) {
					List<ResNameSum> resNameSum = holMidBaseInfo.getResNameSum();
					if(StringUtil.isNotNullOrEmpty(resNameSum)) {
						for(ResNameSum sum : resNameSum) {
							if(sum.getResType().equals(1)) {
								sum.setSaleStatus(0);
							}
						}
						long count = resNameSum.stream().filter(resName -> resName.getSaleStatus().equals(1)).count();
						if(count > 0L) {
							holMidBaseInfo.setSaleStatus(1);
						}else {
							holMidBaseInfo.setSaleStatus(0);
						}
					}
					
				}
				mongoTemplate1.save(holMidBaseInfo, "holMidBaseInfo");
			}
		}
	}

	@Override
	public void updateResProInfo(Long resId) {
		try {
			String request = "respro";
			TCHotelDetailResult hotelDetail = singleHolRequest(resId, request);
			if(StringUtil.isNotNullOrEmpty(hotelDetail) && StringUtil.isNotNullOrEmpty(hotelDetail.getResProBaseInfos())) {
				List<ResProBaseInfo> resProBaseInfo = hotelDetail.getResProBaseInfos();
				
				if(StringUtil.isNotNullOrEmpty(resProBaseInfo)) {
					ResProBaseInfos resProBaseInfos=new ResProBaseInfos();
					resProBaseInfos.setId(resId);
					resProBaseInfos.setResProBaseInfos(resProBaseInfo);
					resProBaseInfos.setLatestUpdateTime(sdfupdate.format(new Date()));
					mongoTemplate1.save(resProBaseInfos, "resProBaseInfos");
					
					ResIdList resIdList =new ResIdList();
					resIdList.setId(resId);
					mongoTemplate1.save(resIdList, "resIdList");
				}
			}
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-resPro");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店房间");
			logRecordHol.setDesc("同步更新酒店房间,酒店ID为："+String.valueOf(resId)+","+e.getMessage());
			logRecordHol.setResId(resId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
		}

	}
	
	public TCHotelDetailResult singleHolRequest(Long resId, String request) {
		SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
		singleHotelDetailReq.setResId(String.valueOf(resId));
		singleHotelDetailReq.setSourceForm("-1");
		singleHotelDetailReq.setRequestContent(request);
		TCHotelDetailResult hotelDetail=hotelInterService.queryTCHotelDetail(singleHotelDetailReq);
		return hotelDetail;
	}
	
	public void pushCommonRes(ResBaseInfo resBaseInfo) {
		if(StringUtil.isNotNullOrEmpty(resBaseInfo.getResGPS())) {
			Double[] resGpsLocation = new Double[2];
			for(ResGPSInfo gps : resBaseInfo.getResGPS()) {
				if(gps.getType().equals(1)) {
					resGpsLocation[0] = Double.valueOf(gps.getLon());
 					resGpsLocation[1] = Double.valueOf(gps.getLat());
 					resBaseInfo.setResGpsLocation(resGpsLocation);
 					break;
				}
			}
		}
		resBaseInfo.setSaleStatus(1);
		resBaseInfo.setId(resBaseInfo.getResId());
		resBaseInfo.setSupplierNo("411709261204150108");
		resBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
	}
	
	public String imgUrlToImgList(List<ImgInfo> resImages , Long resId) {
		String imgUrl = "";
		ImgInfoSum imgInfoSum =new ImgInfoSum();
		imgInfoSum.setId(resId);
		imgInfoSum.setImgInfoList(resImages);
		for(ImgInfo img : resImages) {
			if(img.getIsResDefault().equals(1)) {
				imgUrl = img.getImageUrl();
				break;
			}
		}
		mongoTemplate1.save(imgInfoSum, "imgInfoSum");
		return imgUrl;
	}

}

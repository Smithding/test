package com.tempus.gss.product.hol.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;

@Service
public class BQYHotelSupplierServiceImpl implements IBQYHotelSupplierService {
	
	@Autowired
	MongoTemplate mongoTemplate;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public TCResponse<HotelInfo> queryHotelListForBack(HotelListSearchReq hotelSearchReq) {

		logger.info("bqy查询酒店列表开始");
        if (StringUtil.isNullOrEmpty(hotelSearchReq)) {
            logger.error("hotelSearchReq查询条件为空");
            throw new GSSException("获取酒店列表", "0101", "hotelSearchReq查询条件为空");
        }
       
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getResId()) && StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode()) && StringUtil.isNullOrEmpty(hotelSearchReq.getResGradeId()) && StringUtil.isNullOrEmpty(hotelSearchReq.getKeyword())) {
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
		Query query=new Query();
        Criteria criatira = new Criteria();
        
        int skip= (hotelSearchReq.getPageCount()-1)* (hotelSearchReq.getRowCount());
  		query.skip(skip);
  		query.limit(hotelSearchReq.getRowCount());
  		
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getCityCode())){
			criatira.and("cityName").is(hotelSearchReq.getCityCode());
		}
  		
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getKeyword())){
			String keyword = hotelSearchReq.getKeyword().trim();
				String escapeHtml = StringEscapeUtils.unescapeHtml(keyword);
				String[] fbsArr = { "(", ")" };  // "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
		        	 for (String key : fbsArr) { 
		        		 if(escapeHtml.contains(key)){
		        			escapeHtml = escapeHtml.replace(key, "\\" + key);
		        		 }
		        	 }
				criatira.and("hotelName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
		}
  		
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResId())){
			criatira.and("_id").is(Long.valueOf(hotelSearchReq.getResId()));
		}
  		query = query.addCriteria(criatira);
  		//获取酒店列表
  		List<HotelInfo> hotelList = mongoTemplate.find(query, HotelInfo.class);
  		//查询总数
  		int totalCount = (int)mongoTemplate.count(query, HotelInfo.class);
  		//总页数
  		int totalPage= (int)(totalCount % hotelSearchReq.getRowCount() == 0 ? totalCount / hotelSearchReq.getRowCount() : (totalCount / hotelSearchReq.getRowCount() + 1));
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
			ResBaseInfo resBaseInfo = new ResBaseInfo();
			resBaseInfo.setAddress(h.getAddress());
			//酒店品牌
			ResBrandInfo brandInfo = new ResBrandInfo();
			brandInfo.setResBrandName(h.getHotelBrandName());
			brandInfo.setId(Long.parseLong(String.valueOf(h.getHotelBrandId())));
			resBaseInfo.setBrandInfo(brandInfo);
			
			resBaseInfo.setCityId(Integer.parseInt(h.getCityCode()));
			resBaseInfo.setCityName(h.getCityName());
			resBaseInfo.setLocation(h.getRoadCross());
			resBaseInfo.setId(h.getId());
			
			//酒店图片
			//TODO 酒店图片类型待定
			List<ImgInfo> imgInfoList = new ArrayList<>();
			List<ImageList> hotelImageList = h.getHotelImageList();
			if (null != hotelImageList && hotelImageList.size() > 0) {
				for (ImageList i : hotelImageList) {
					ImgInfo imgInfo = new ImgInfo();
					imgInfo.setImageName(i.getTitleName());
					imgInfo.setImageUrl(i.getImageUrl());
					imgInfo.setResId(h.getHotelId());
				}
			}  
			resBaseInfo.setImgInfoList(imgInfoList);
			
			resBaseInfo.setLatestUpdateTime(h.getLatestUpdateTime());
			if (null != h.getLowPrice()) {
				resBaseInfo.setMinPrice(h.getLowPrice().intValue());
			}
			resBaseInfo.setResName(h.getHotelName());
			resBaseInfo.setSaleStatus(h.getSaleStatus());
			resBaseInfo.setResPhone(h.getMobile());
			resBaseInfo.setShortIntro(h.getDescription());
			resBaseInfo.setIntro(h.getDescription());
			resList.add(resBaseInfo);
		}
		TCResponse<ResBaseInfo> result = new TCResponse<>();
		result.setResponseResult(resList);
		result.setPageNumber(hotelResult.getPageNumber());
		result.setTotalCount(hotelResult.getTotalCount());
		result.setTotalPatge(hotelResult.getTotalPatge());
		return result;
	}

	

}

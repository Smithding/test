package com.tempus.gss.product.hol.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
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
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.ImageList;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.vo.Agent;

@Service
public class BQYHotelSupplierServiceImpl implements IBQYHotelSupplierService {

	@Autowired
	MongoTemplate mongoTemplate;

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
			ResBaseInfo resBaseInfo = new ResBaseInfo();
			resBaseInfo.setAddress(h.getAddress());
			// 酒店品牌
			ResBrandInfo brandInfo = new ResBrandInfo();
			brandInfo.setResBrandName(h.getHotelBrandName());
			brandInfo.setId(Long.parseLong(String.valueOf(h.getHotelBrandId())));
			resBaseInfo.setBrandInfo(brandInfo);

			resBaseInfo.setCityId(Integer.parseInt(h.getCityCode()));
			resBaseInfo.setCityName(h.getCityName());
			resBaseInfo.setLocation(h.getRoadCross());
			resBaseInfo.setId(h.getId());
			resBaseInfo.setResId(h.getId());

			// 酒店图片
			// TODO 酒店图片类型待定
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
				resBaseInfo.setResCommonPrice(h.getLowPrice().intValue());
			}
			resBaseInfo.setResName(h.getHotelName());
			resBaseInfo.setSaleStatus(h.getSaleStatus());
			resBaseInfo.setResPhone(h.getMobile());
			resBaseInfo.setShortIntro(h.getDescription());
			resBaseInfo.setIntro(h.getDescription());
			resBaseInfo.setSupplierNo(h.getSupplierNo());
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

}

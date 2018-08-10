package com.tempus.gss.product.hol.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.ResNameSum;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.*;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.product.hol.api.service.IHolMidService;
import com.tempus.gss.product.hol.api.syn.ISyncHotelInfo;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.utils.TrackTime;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import httl.util.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 酒店中间表Service实现
 */
@Service
public class HolMidServiceImpl implements IHolMidService {
	
	@Autowired
	private MongoTemplate mongoTemplate1;
	
	@Autowired
	private IBQYHotelSupplierService bqyHotelSupplierService;
	
	@Autowired
	private ITCHotelSupplierService tcHotelSupplierService;
	
	@Autowired
	private ITCHotelInterService tcHotelInterService;
	
	@Autowired
	private ISyncHotelInfo syncHotelInfo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public TCResponse<HolMidBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq, String flag) {
		logger.info("查询酒店列表开始");
        if (StringUtil.isNullOrEmpty(hotelSearchReq)) {
            logger.error("hotelSearchReq查询条件为空");
            throw new GSSException("获取酒店列表", "0101", "hotelSearchReq查询条件为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("获取酒店列表", "0102", "agent对象为空");
        }
        if("before".equals(flag) && StringUtil.isNullOrEmpty(agent.getType())){
        	throw new GSSException("获取酒店列表", "0102", "agentType为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode())) {
            logger.error("城市编号为空");
            throw new GSSException("获取酒店列表", "0103", "城市编号为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getPageCount())) {
            logger.error("页码为空");
            throw new GSSException("获取酒店列表", "0107", "页码为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getRowCount())) {
            logger.error("每页条数为空");
            throw new GSSException("获取酒店列表", "0108", "每页条数为空");
        }
        if ("before".equals(flag) && StringUtil.isNullOrEmpty(hotelSearchReq.getSaleStatus())) {
            logger.error("可售状态入参为空");
            throw new GSSException("获取酒店列表", "0118", "可售状态入参为空");
        }
        TCResponse<HolMidBaseInfo> response = new TCResponse<HolMidBaseInfo>();
        Query query=new Query();
        Criteria criatira = new Criteria();
        
        //关键字
		if(StringUtils.isNotEmpty(hotelSearchReq.getKeyword())){
			String keyword = hotelSearchReq.getKeyword().trim();
			String escapeHtml = StringEscapeUtils.unescapeHtml(keyword);
			String[] fbsArr = { "(", ")" };  //  "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
	        	 for (String key : fbsArr) { 
	        		 if(escapeHtml.contains(key)){
	        			escapeHtml = escapeHtml.replace(key, "\\" + key);
	        		 }
	        	 }
	        /*Criteria bqyCriteria = new Criteria();
	        bqyCriteria.and("bqyResName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
	        addSearchCriteria(hotelSearchReq, query, bqyCriteria);*/
	        Criteria criteria = new Criteria();
	        criteria.and("resName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
	        addSearchCriteria(hotelSearchReq, query, criteria);
			//criatira.and("bqyResName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
	        criatira.orOperator(criteria);
		}else {
			addSearchCriteria(hotelSearchReq, query, criatira);
		}
		
		
  		
        int skip= (hotelSearchReq.getPageCount()-1)* (hotelSearchReq.getRowCount());
  		query.skip(skip);
  		query.limit(hotelSearchReq.getRowCount());
  		logger.info("酒店查询条件："+JsonUtil.toJson(criatira));
  		List<HolMidBaseInfo> holList = mongoTemplate1.find(query.addCriteria(criatira), HolMidBaseInfo.class);
  		//总条数
  		int count= (int)mongoTemplate1.count(query, HolMidBaseInfo.class);
  		//总页数
  		int totalPage= (int)(count/hotelSearchReq.getRowCount()+1);
  		response.setTotalPatge(Integer.valueOf(totalPage));
  		response.setTotalCount(Integer.valueOf(count));
  		response.setResponseResult(holList);
		return response;
	}

	/**
	 * 酒店条件查询
	 * @param hotelSearchReq
	 * @param query
	 * @param criatira
	 */
	private void addSearchCriteria(HotelListSearchReq hotelSearchReq, Query query, Criteria criatira) {
		//ID
		if (StringUtil.isNotBlank(hotelSearchReq.getResId())) {
			criatira.and("_id").is(hotelSearchReq.getResId());
		}
		//酒店等级
		if (StringUtil.isNotBlank(hotelSearchReq.getResGradeId())) {
			criatira.and("resGrade").is(hotelSearchReq.getResGradeId());
		}
		//城市
        if(StringUtils.isNotEmpty(hotelSearchReq.getCityCode())){
			//criatira.orOperator(Criteria.where("cityName").is(hotelSearchReq.getCityCode()), Criteria.where("sectionName").regex(".*?\\" +hotelSearchReq.getCityCode()+ ".*"));
        	criatira.and("cityName").is(hotelSearchReq.getCityCode());
        }
        //是否可售
		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSaleStatus())){
			criatira.and("saleStatus").is(hotelSearchReq.getSaleStatus());
		}
		
		//排序
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getOrderBy())){
  			if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getOrderBy().get(0))){
  				List<Order> orders = new ArrayList<Order>();
	  			for(String ss : hotelSearchReq.getOrderBy()){
	  				String[] arrs = ss.split("_");
	  				if(!arrs[0].equals("resGrade")){
	  					if(arrs[1].equals("desc")){
		  					orders.add(new Order(Direction.ASC,arrs[0]));
		  				}else if(arrs[1].equals("asc")){
		  					orders.add(new Order(Direction.DESC,arrs[0]));
		  				}
	  				}else{
	  					if(arrs[1].equals("desc")){
	  						orders.add(new Order(Direction.DESC,arrs[0]));
	  					}else if(arrs[1].equals("asc")){
	  						orders.add(new Order(Direction.ASC,arrs[0]));
	  					}
	  				}
	  			}
	  			Sort sort = new Sort(orders);
				if (null != sort) {  
		            query.with(sort);  
		        } 
  			}
  		}
        
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition())){
			if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getPriceFromToList())){
				criatira.and("minPrice").gte(hotelSearchReq.getSearchCondition().getPriceFromToList().get(0).getPriceFrom().intValue()).lte(hotelSearchReq.getSearchCondition().getPriceFromToList().get(0).getPriceTo().intValue());
			}
			if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getHotelLevelList())){
				List<String> hlevle = hotelSearchReq.getSearchCondition().getHotelLevelList(); 
 				if(!(hlevle.isEmpty()) && !(hlevle.get(0).isEmpty())){ 	 					
 					criatira.and("resGrade").in(Arrays.asList(hlevle.get(0).split(",")));
 				}
			}
			if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getBrandList())){
				List<String> brandNameList = new ArrayList<>();
				List<String> brandList = hotelSearchReq.getSearchCondition().getBrandList();
				//String[] fbsArr = { "(", ")" };  //  "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
				for (String brandName: brandList) {
					brandName = StringEscapeUtils.unescapeHtml(brandName);
		        	 /*for (String key : fbsArr) { 
		        		 if(brandName.contains(key)){
		        			 brandName = brandName.replace(key, "\\" + key);
		        		 }
		        	 }*/
		        	 brandNameList.add(brandName);
				}
				criatira.and("brandInfo.resBrandName").in(brandNameList);
			}
		}
		criatira.orOperator(Criteria.where("supplierNo").is("0"), Criteria.where("supplierNo").is("1"));
		logger.info("酒店列表查询条件:" + JsonUtil.toJson(criatira));
	}

	@Override
	public HolMidBaseInfo queryHolMidById(Agent agent, String holMidId) {
		if (StringUtil.isNullOrEmpty(holMidId)) {
            logger.error("酒店中间表ID为空!");
            throw new GSSException("获取酒店中间表信息", "0101", "酒店中间表ID为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("获取酒店列表", "0102", "agent对象为空");
        } 
       /* if(StringUtil.isNullOrEmpty(agent.getType())){
            throw new GSSException("获取酒店列表", "0102", "agentType为空");
        }*/
        
       return tcHotelSupplierService.queryMidListByResId(agent, holMidId);
		
	}
	
	@TrackTime(param = "查询所有酒店房型耗时")
	@Override
	public List<ProDetails> hotelDetail(Agent agent, String holMidId, String checkinDate, String checkoutDate) throws Exception {//, hotelDetailSearchReq.getCheckinDate(), hotelDetailSearchReq.getCheckoutDate()
		Map<String, Object> resultMap = getHotelId(agent, holMidId);
		//Long bqyResId = (Long)resultMap.get("bqyResId");
		Long bqyResId = null;
		Long tcResId = (Long)resultMap.get("tcResId");
		String bqyCityCode = (String) resultMap.get("bqyCityCode");
		ResBaseInfo bqyHotel = null;
		ResBaseInfo tcHotel = null;
		
		//tc和bqy酒店ID都不为空则开启异步查询,否则执行同步
		if (null != bqyResId && null != tcResId) {
			try {
				long start = System.currentTimeMillis();
				Future<ResBaseInfo> bqyResponse = syncHotelInfo.queryBQYHotelListForAsync(agent, bqyResId, checkinDate, checkoutDate, bqyCityCode);
				Future<ResBaseInfo> tcResponse = syncHotelInfo.queryTCHelListForAsync(agent, tcResId, checkinDate, checkoutDate);
				/*while (bqyResponse.isDone() && tcResponse.isDone()) {
						break;
					}
				if (null != bqyResponse) {*/
					bqyHotel = bqyResponse.get();
				//}
				//if (null != tcResponse) {
					tcHotel = tcResponse.get();
				//}
					
				System.out.println("111111111: " + (System.currentTimeMillis() - start));
				long start1 = System.currentTimeMillis();
				List<ProDetails> bqyProDetailList = null;
				List<ProDetails> tcProDetailList = null;
				if (null != bqyHotel) {
					bqyProDetailList = bqyHotel.getProDetails();
				}
				if (null != tcHotel) {
					tcProDetailList = tcHotel.getProDetails();
				}
				
				if (bqyProDetailList == null || bqyProDetailList.size() == 0) {
					return tcHotel.getProDetails();
				}
				if (tcProDetailList == null || tcProDetailList.size() == 0) {
					return bqyHotel.getProDetails();
				}
				List<ProDetails> newProDetailList = new ArrayList<ProDetails>();
				bqyProDetailList.addAll(tcProDetailList);
				
				/*Map<String, List<ProDetails>> collect = bqyProDetailList.stream().collect(Collectors.groupingBy(ProDetails::getResProName));
				for(Map.Entry<String, List<ProDetails>> entry : collect.entrySet()) {
					List<ProDetails> value = entry.getValue();
					if(value!= null && value.size() > 0) {
						if(value.size() >= 2) {
							for (int i = 1; i < value.size(); i++) {
								List<ResProBaseInfo> resProBaseInfoList = value.get(0).getResProBaseInfoList();
								resProBaseInfoList.addAll(value.get(i).getResProBaseInfoList());
							}
						}
						newProDetailList.add(value.get(0));
					}
				}
				bqyHotel.setProDetails(newProDetailList);*/
				
				
				Map<String, ProDetails> map = new HashMap<String, ProDetails>();
				for(ProDetails pro : bqyProDetailList) {
					if(map.containsKey(pro.getResProName())) {
						ProDetails proDetails = map.get(pro.getResProName());
						List<ResProBaseInfo> resProBaseInfoList = proDetails.getResProBaseInfoList();
						resProBaseInfoList.addAll(pro.getResProBaseInfoList());
						proDetails.setResProBaseInfoList(resProBaseInfoList);
						map.put(pro.getResProName(), proDetails);
					}else {
						map.put(pro.getResProName(), pro);
					}
				}
				newProDetailList = map.entrySet().stream().map(et ->et.getValue()).collect(Collectors.toList());
				bqyHotel.setProDetails(newProDetailList);
				System.out.println("222222: " + (System.currentTimeMillis() - start1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if (null != bqyResId) {
				Future<ResBaseInfo> hotelDetail = bqyHotelSupplierService.singleHotelDetail(agent,String.valueOf(bqyResId), checkinDate, checkoutDate, bqyCityCode);
				bqyHotel = hotelDetail.get();
			}
			if (null != tcResId) {
				bqyHotel = syncHotelInfo.queryProDetail(agent, tcResId, checkinDate, checkoutDate);
			}
		}
		List<ProDetails> proDetailList = bqyHotel.getProDetails();
		if (null != proDetailList && proDetailList.size() > 0) {
			for(int i = 0; i < proDetailList.size(); i++) {
				List<ResProBaseInfo> resProBaseInfoList = proDetailList.get(i).getResProBaseInfoList();
				resProBaseInfoList.sort(Comparator.comparingInt(ResProBaseInfo :: getFirPrice));
				proDetailList.get(i).setMinPrice(resProBaseInfoList.get(0).getFirPrice());
			}
			proDetailList.sort(Comparator.comparingInt(ProDetails :: getMinPrice));
		}
		//return bqyHotel;
		return proDetailList;
	}

	@Override
	public ResBaseInfo hotelDetailForBack(Agent agent, String holMidId, String checkinDate, String checkoutDate)
			throws Exception {
		Map<String, Object> resultMap = getHotelId(agent, holMidId);
		Long bqyResId = (Long)resultMap.get("bqyResId");
		Long tcResId = (Long)resultMap.get("tcResId");
		String bqyCityCode = (String) resultMap.get("bqyCityCode");

		ResBaseInfo bqyHotel = null;
		ResBaseInfo tcHotel = null;
		
		//tc和bqy酒店ID都不为空则开启异步查询
		if (null != bqyResId && null != tcResId) {
			try {
				Future<ResBaseInfo> bqyResponse = syncHotelInfo.queryBQYHotelListForAsync(agent, bqyResId, checkinDate, checkoutDate, bqyCityCode);
				Future<ResBaseInfo> tcResponse = syncHotelInfo.queryTCHolForAsyncBack(agent, bqyResId);
				/*while (bqyResponse.isDone() && tcResponse.isDone()) {
						break;
					}*/
				bqyHotel = bqyResponse.get();
				tcHotel = tcResponse.get();
				List<ProDetails> bqyProDetailList = bqyHotel.getProDetails();
				List<ProDetails> tcProDetailList = tcHotel.getProDetails();
				
				if (bqyProDetailList == null || bqyProDetailList.size() == 0) {
					return tcHotel;
				}
				if (tcProDetailList == null || tcProDetailList.size() == 0) {
					return bqyHotel;
				}
				
				for (int i = 0; i < bqyProDetailList.size(); i++) {
					ProDetails bqyProDetail = bqyProDetailList.get(i);
					//bqy房型名称
					String bqyProName = bqyProDetail.getResProName();
					for (int j = 0; i < tcProDetailList.size(); j++) {
						ProDetails tcProDetail = tcProDetailList.get(j);
						String tcProName = tcProDetail.getResProName();
						
						//酒店房型是否一样
						if (bqyProName.equals(tcProName)) {
							bqyProDetail.getResProBaseInfoList().addAll(tcProDetail.getResProBaseInfoList());
							continue;
						}
						
						//String reg = "[\u4e00-\u9fa5]";
						//TODO 判断酒店房型面积和酒店楼层
						//面积
						/*String bqyRoomSize = bqyProDetail.getRoomSize();
						String tcRoomSize = tcProDetail.getRoomSize();
						//楼层
						String bqyRoomFloor = bqyProDetail.getRoomFloor();
						String tcRoomFloor = tcProDetail.getRoomFloor();
						
						if (bqyRoomSize.equals(tcRoomSize) && bqyRoomFloor.equals(tcRoomFloor)) {
							bqyProDetail.getResProBaseInfoList().addAll(tcProDetail.getResProBaseInfoList());
							continue;
						}*/
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if (null != bqyResId) {
				Future<ResBaseInfo> hotelDetail = bqyHotelSupplierService.singleHotelDetail(agent,String.valueOf(bqyResId), checkinDate, checkoutDate, bqyCityCode);
				bqyHotel = hotelDetail.get();
			}
			if (null != tcResId) {
				//tcHotel = tcHotelSupplierService.queryHotelDetail(agent, tcResId, checkinDate, checkoutDate);
				bqyHotel = tcHotelInterService.updateSingleResDetail(agent, String.valueOf(tcResId));
			}
			
			//对比酒店最低价
			double minPrice = 0;
			List<ProDetails> proDetailList = bqyHotel.getProDetails();
			if (null != proDetailList && proDetailList.size() > 0) {
				if (null != proDetailList && proDetailList.size() > 0) {
					for(int i = 0; i < proDetailList.size(); i++) {
						List<ResProBaseInfo> resProBaseInfoList = proDetailList.get(i).getResProBaseInfoList();
						for (int j = 0; j < resProBaseInfoList.size(); j++) {
							Integer firPrice = resProBaseInfoList.get(j).getFirPrice();
							if (i == 0 && j == 0) {
								minPrice = firPrice;
							}else {
								if (minPrice > firPrice) {
									minPrice = firPrice;
								}
							}
						}
					}
				}
			}
			if (minPrice != 0) {
				Query query = new Query(Criteria.where("_id").is(holMidId));
				Update update = Update.update("minPrice", minPrice);
				mongoTemplate1.upsert(query, update, HolMidBaseInfo.class);
			}
		}
		return bqyHotel;
	}
	
	@Override
	public List<ImgInfo> listImgByHotelId(Agent agent, String holMidId) {
		List<ImgInfo> imgList = null;
		Map<String, Object> resultMap = getHotelId(agent, holMidId);
		Long bqyResId = (Long) resultMap.get("bqyResId");
		Long tcResId = (Long) resultMap.get("tcResId");
		if (null != bqyResId && bqyResId != 0) {
			imgList = bqyHotelSupplierService.listImgByHotelId(agent, bqyResId);
		}else {
			ImgInfoSum imgInfoSum = tcHotelSupplierService.queryImgInfoSum(agent, tcResId);
			if (null != imgInfoSum) {
				imgList = imgInfoSum.getImgInfoList();
			}
		}
		return imgList;
	}

	@Override
	public int saleStatusUpdate(Agent agent, Long holMidId, Integer saleStatus) {
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
			throw new GSSException("修改酒店可售状态", "0102", "agent对象为空");
		}
		if (StringUtil.isNullOrEmpty(holMidId)) {
			throw new GSSException("修改酒店可售状态", "0118", "传入中间表ID为空");
		}
		try {
			SimpleDateFormat sdfupdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String updateTime = sdfupdate.format(new Date());
			Query query = Query.query(Criteria.where("_id").is(holMidId));
			Update update = Update.update("saleStatus", saleStatus).set("latestUpdateTime", updateTime);
			mongoTemplate1.upsert(query, update, HolMidBaseInfo.class);
		} catch (Exception e) {
			logger.error("修改可售状态出错" + e);
			throw new GSSException("修改可售状态", "0118", "修改可售状态失败");
		}
		return 1;
	}

	@Override
	public ResBaseInfo hotelBaseInfo(Agent agent, String holMidId ,String checkInDate, String checkOutDate) throws Exception {
		if (StringUtil.isNullOrEmpty(agent)) {
			logger.error("agent对象为空");
			throw new GSSException("修改酒店可售状态", "0102", "agent对象为空");
		}
		if (StringUtil.isNullOrEmpty(holMidId)) {
			throw new GSSException("修改酒店可售状态", "0118", "传入中间表ID为空");
		}
		ResBaseInfo resBaseInfo = null;
		Map<String, Object> resultMap = getHotelId(agent, holMidId);
		//Long bqyResId = (Long)resultMap.get("bqyResId");
		Long bqyResId = null;
		Long tcResId = (Long)resultMap.get("tcResId");
		String cityCode = (String) resultMap.get("bqyCityCode");
		if (null != tcResId) {
			resBaseInfo = tcHotelSupplierService.queryListByResId(agent, tcResId);
		}else if (null != bqyResId) {
			resBaseInfo = bqyHotelSupplierService.queryHotelBaseInfo(agent, bqyResId, checkInDate, checkOutDate, cityCode);
		}
		return resBaseInfo;
	}

	@Override
	public void addClickCount(Agent agent, String holMidId) {
		Query query = new Query(Criteria.where("_id").is(holMidId));
		HolMidBaseInfo holMidBaseInfo = mongoTemplate1.findOne(query, HolMidBaseInfo.class);
		mongoTemplate1.upsert(query, new Update().set("bookTimes", null == holMidBaseInfo.getBookTimes() ? 1 : holMidBaseInfo.getBookTimes() + 1L), HolMidBaseInfo.class);
	}

	/**
	 * 酒店ID,城市编号
	 * @param agent
	 * @param holMidId
	 * @return
	 */
	private Map<String, Object> getHotelId(Agent agent, String holMidId) {
		HolMidBaseInfo holMid = queryHolMidById(agent, holMidId);
		Long bqyResId = null;
		Long tcResId = null;
		String bqyCityCode = null;
		List<ResNameSum> listHol = holMid.getResNameSum();
		if (null != listHol && listHol.size() > 0) {
			for (ResNameSum resNameSum : listHol) {
				switch (resNameSum.getResType()) {
					case 1:
						tcResId = resNameSum.getResId();
						break;
					case 2:
						bqyResId = resNameSum.getResId();
						bqyCityCode = resNameSum.getCityCode();
						break;
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("bqyResId", bqyResId);
		map.put("tcResId", tcResId);
		map.put("bqyCityCode", bqyCityCode);
		return map;
	}
}

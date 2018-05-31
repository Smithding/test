package com.tempus.gss.product.hol.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.product.hol.api.entity.FormDate;
import com.tempus.gss.product.hol.api.entity.LogRecordHol;
import com.tempus.gss.product.hol.api.entity.request.tc.AllHotelListReq;
import com.tempus.gss.product.hol.api.entity.request.tc.AssignDateHotelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.GetOrderLogInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.GetTcOrderCancelListReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IncrHotelList;
import com.tempus.gss.product.hol.api.entity.request.tc.SingleHotelDetailReq;
import com.tempus.gss.product.hol.api.entity.response.tc.AllHotelList;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelReasonModel;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfoSum;
import com.tempus.gss.product.hol.api.entity.response.tc.IncrHotelPrice;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderLogModel;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderLogModelList;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResourceProductIds;
import com.tempus.gss.product.hol.api.entity.response.tc.ResultTc;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderCancelReasonList;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.api.util.Tool;
import com.tempus.gss.product.hol.dao.HotelOrderMapper;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
/**
 * 同程酒店接对接口实现类
 * @author kai.yang
 *
 */
@Service
public class TCHotelInterServiceImpl implements ITCHotelInterService{
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	HttpClientUtil httpClientUtil;
	
	@Autowired
    HotelOrderMapper hotelOrderMapper;
	
	@Autowired
	MongoTemplate mongoTemplate1;
	
	@Reference
	ITCHotelSupplierService hotelFind;
	
	@Reference
	ILogService iLogService;
	
	@Value("${all.list.url}")
	private String ALL_LIST_URL;
	
	@Value("${single.hol.url}")
	private String SINGLE_HOL_URL;
	
	@Value("${assign.list.url}")
	private String ASSIGN_LIST_URL;
	
	@Value("${price.repo.url}")
	private String PRICE_REPO_URL;
	
	@Value("${res.order.url}")
	private String RES_ORDER_URL;
	
	@Value("${order.log.url}")
	private String ORDER_LOG_URL;
	
	SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<ResInfoList> queryTCHotelList(AllHotelListReq allHotelListReq) throws GSSException{
		//log.info("查询酒店列表开始");
		List<ResInfoList> list=null;
			try{
				String reqJson = JSONObject.toJSONString(allHotelListReq);
				String resultJson= httpClientUtil.doTCJsonPost(ALL_LIST_URL, reqJson);
			    if(resultJson != null){
			    	ResultTc<AllHotelList> res= JsonUtil.toBean(resultJson, new TypeReference<ResultTc<AllHotelList>>(){});
			 		if(StringUtil.isNotNullOrEmpty(res) && res.getRet_code().equals("200")){
			 			list= res.getResult().getResInfoList();
			 		}
			    }
			}catch(Exception e){
				e.printStackTrace();
	            throw new GSSException("获取酒店列表", "0112", "酒店列表请求出错");
			}
		return list;
	}
	
	@Override
	public TCHotelDetailResult queryTCHotelDetail(SingleHotelDetailReq singleHotelDetailReq) throws GSSException{
		//log.info("查询酒店详情开始");
		ResultTc<TCHotelDetailResult> hotelDetail= null;
		try {
			 String reqJson = JSONObject.toJSONString(singleHotelDetailReq);
	    	 String result= httpClientUtil.doTCJsonPost(SINGLE_HOL_URL, reqJson);
			 if(StringUtil.isNotNullOrEmpty(result)){
				hotelDetail= JsonUtil.toBean(result, new TypeReference<ResultTc<TCHotelDetailResult>>(){});
				if(hotelDetail.getRet_code().equals("200") && StringUtil.isNotNullOrEmpty(hotelDetail.getResult())){
					return hotelDetail.getResult();
				}else{
					
					Integer saleStatus = 0;
					SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	String updateTime = sdfupdate.format(new Date());
		        	Query query = Query.query(Criteria.where("_id").is(Long.valueOf(singleHotelDetailReq.getResId())));
		        	Update update = Update.update("saleStatus", saleStatus).set("latestUpdateTime", updateTime);
		        	mongoTemplate1.upsert(query, update, ResBaseInfo.class);
		        	log.error("酒店详情请求出错 "+result);
					//throw new GSSException("获取酒店详情", "0111", "酒店详情请求出错,"+hotelDetail.getErr_msg());
		        	return null;
				}
			 }
			  else{
		            //log.error("酒店详情请求返回空值");
		            throw new GSSException("获取酒店详情", "0111", "酒店详情请求返回空值");
		        } 
		}catch (Exception e) {
            //log.error("酒店详情请求出错");
			log.error("酒店详情请求出错 "+e.getMessage());
            throw new GSSException("获取酒店详情", "0112", "酒店详情请求出错");
		} 
	}

	@Override
	public IncrHotelPrice queryIncrHotelList(IncrHotelList incrHotelList) throws GSSException{
		try {
			String reqJson= JSONObject.toJSONString(incrHotelList);
			String result= httpClientUtil.doTCJsonPost(ASSIGN_LIST_URL, reqJson);
			if(StringUtil.isNotNullOrEmpty(result)){
				ResultTc<IncrHotelPrice> incrHotelPriceBase= JsonUtil.toBean(result, new TypeReference<ResultTc<IncrHotelPrice>>(){});
				if(StringUtil.isNotNullOrEmpty(incrHotelPriceBase) && StringUtil.isNotNullOrEmpty(incrHotelPriceBase.getResult())){
					return incrHotelPriceBase.getResult();
				}
			}else{
		            //log.error("定时器获取酒店增量数据");
		            throw new GSSException("定时器获取酒店增量数据", "0111", "定时器获取酒店增量数据请求返回空值");
		        } 
			
		} catch (Exception e) {
			throw new GSSException("定时器获取酒店增量数据", "0111", "定时器获取酒店增量数据异常");
		}
		return null;
		
	}
	
	
	@Override
	public AssignDateHotel queryAssignDateHotel(AssignDateHotelReq assignDateHotelReq) throws GSSException{
		//log.info("查询某一时间内的酒店价格和库存信息开始");
		String reqJson= JSONObject.toJSONString(assignDateHotelReq);
		//System.out.println("查询某一时间内的酒店价格的入参为：--------"+reqJson);
		try {
			String resultJson= httpClientUtil.doTCJsonPost(PRICE_REPO_URL, reqJson);
			if(StringUtil.isNotNullOrEmpty(resultJson)){
				//System.out.println("返回值为：--------"+resultJson);
				ResultTc<AssignDateHotel> ass=JsonUtil.toBean(resultJson, new TypeReference<ResultTc<AssignDateHotel>>(){} );
				if(ass.getRet_code().equals("200") && StringUtil.isNotNullOrEmpty(ass.getResult())){
					AssignDateHotel assignDateHotel= ass.getResult();
					return assignDateHotel;
				}
			}else{
				throw new GSSException("获取酒店价格库存", "0115", "酒店价格库存请求出错");
			}
		} catch (Exception e) {
			throw new GSSException("获取酒店价格库存", "0115", "酒店价格库存请求出错");
		}
		return null;
	}

	@Override
	public List<CancelReasonModel> getTcOrderCancelList() {
		GetTcOrderCancelListReq getTcOrderCancelListReq=new GetTcOrderCancelListReq();
		getTcOrderCancelListReq.setFromPlatment(6);
		getTcOrderCancelListReq.setCategory(2);
		String reqJson = JSONObject.toJSONString(getTcOrderCancelListReq);
		String result= httpClientUtil.doTCJsonPost(RES_ORDER_URL, reqJson);
		ResultTc<TcOrderCancelReasonList> tc=JsonUtil.toBean(result, new TypeReference<ResultTc<TcOrderCancelReasonList>>(){} );
		return tc.getResult().getCancelReasonList();
	}

	@Override
	public List<OrderLogModel> LogInfoOfOrderFromTc(Agent agent, GetOrderLogInfoReq getOrderLogInfoReq) {
		//log.info("查询订单日志信息开始");
		String reqJson= JSONObject.toJSONString(getOrderLogInfoReq);
		String resultJson= httpClientUtil.doTCJsonPost(ORDER_LOG_URL, reqJson);
		List<OrderLogModel> orderLogInfo = null;
		if(StringUtils.isNotEmpty(resultJson)){
			ResultTc<OrderLogModelList> tclog=JsonUtil.toBean(resultJson, new TypeReference<ResultTc<OrderLogModelList>>(){} );
			if(tclog.getRet_code().equals("200")){
				if(StringUtil.isNotNullOrEmpty(tclog.getResult())){
					 orderLogInfo = tclog.getResult().getOrderLogInfo();
				}
			}else{
				throw new GSSException("获取订单日志", "0115", tclog.getErr_msg());
			}
		}else{
			throw new GSSException("获取订单日志", "0115", "订单日志返回为空");
		}
		return orderLogInfo;
	}

	@Override
	public Boolean updateSingleHotelDetail(Agent agent, Long resId) {
		Boolean flag = doIncrInventoryWithResId(agent, resId);
		if(flag == true){
		doIncrHotelDetail(resId);
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean doIncrInventoryWithResId(Agent agent, Long resId){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();  
		Calendar calAdd = Calendar.getInstance();
		String calStartTime= sdf.format(cal.getTime());
		calAdd.add(Calendar.MONTH, 2);
		calAdd.add(Calendar.DAY_OF_MONTH, -1);
		String calEndTime= sdf.format(calAdd.getTime());
		try {
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setSourceFrom("-1");
			//assignDateHotelReq.setNeedSpecialPolicy(1);
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calEndTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			if(StringUtil.isNotNullOrEmpty(assignDateHotel)){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
					if(proInfoDetailList != null && proInfoDetailList.size() > 0){
						for(ProInfoDetail pro : proInfoDetailList){
							pro.setId(pro.getProductUniqueId());
							pro.setResId(assignDateHotel.getResId());
							pro.setUpdateInvenTime(sdfupdate.format(new Date()));
							mongoTemplate1.save(pro, "proInfoDetail");
						}
					}
			}
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-Incr");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店最新价格信息");
			logRecordHol.setDesc("同步更新酒店价格库存失败,酒店ID为："+String.valueOf(resId));
			logRecordHol.setResId(resId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
		}
		return true;
	}
	
	@Override
	public void doIncrHotelDetail(Long resId){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    		Calendar cal = Calendar.getInstance();  
    		Calendar calAdd = Calendar.getInstance();
    		//Calendar calEnd = Calendar.getInstance();
    		String calStartTime= sdf.format(cal.getTime());
    		
    		calAdd.add(Calendar.MONTH, 2);
    		calAdd.add(Calendar.DAY_OF_MONTH, -10);
    		String calAddTime= sdf.format(calAdd.getTime());
    		Integer saleStatus = 1;
    		
    		AssignDateHotelReq assignDateHotelReqFirstMonth=new AssignDateHotelReq();
			assignDateHotelReqFirstMonth.setResId(resId);
			assignDateHotelReqFirstMonth.setSourceFrom("-1");
			//assignDateHotelReqFirstMonth.setNeedSpecialPolicy(1);
			assignDateHotelReqFirstMonth.setStartTime(calStartTime);
			assignDateHotelReqFirstMonth.setEndTime(calAddTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReqFirstMonth);
			
			if(assignDateHotel !=null){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
					if(proInfoDetailList != null && proInfoDetailList.size() > 0){
						for(ProInfoDetail pro : proInfoDetailList){
							pro.setId(pro.getProductUniqueId());
							pro.setResId(assignDateHotel.getResId());
							pro.setUpdateInvenTime(sdfupdate.format(new Date()));
							mongoTemplate1.save(pro, "proInfoDetail");
						} 
						SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
		    			singleHotelDetailReq.setResId(String.valueOf(resId));
		    			singleHotelDetailReq.setSourceForm("-1");
		    			singleHotelDetailReq.setRequestContent("res,respro,rimg");
		    			TCHotelDetailResult hotelDetail=queryTCHotelDetail(singleHotelDetailReq);
		    			
		    			/*SingleHotelDetailReq singleHotelDetailReqImg=new SingleHotelDetailReq();
		    			singleHotelDetailReqImg.setResId(String.valueOf(resId));
		    			singleHotelDetailReqImg.setSourceForm("-1");
		    			singleHotelDetailReqImg.setRequestContent("rimg");
		    			TCHotelDetailResult hotelDetailImg=queryTCHotelDetail(singleHotelDetailReqImg);*/
		    			
		    			if(StringUtil.isNotNullOrEmpty(hotelDetail)){ 
		    				List<ResBaseInfo> resBaseInfoList =hotelDetail.getResBaseInfos();
		    				List<ResProBaseInfo> resProBaseInfoList= hotelDetail.getResProBaseInfos();
		    				//List<ImgInfo> imgInfoList= hotelDetailImg.getResImages();
		    				List<ImgInfo> imgInfoList= hotelDetail.getResImages();
		    				
		    				if(StringUtil.isNotNullOrEmpty(resBaseInfoList) && StringUtil.isNotNullOrEmpty(resProBaseInfoList)){
		    					ResBaseInfo tcResBaseInfo = resBaseInfoList.get(0);
		    					tcResBaseInfo.setId(resId);
		    					tcResBaseInfo.setSupplierNo("411709261204150108");
		    					if(tcResBaseInfo.getResGrade() != null){
		    						if(tcResBaseInfo.getResGrade().equals("豪华型")){
		    							tcResBaseInfo.setResGradeId("23");
		    						}else if(tcResBaseInfo.getResGrade().equals("高档型")){
		    							tcResBaseInfo.setResGradeId("24");
		    						}else if(tcResBaseInfo.getResGrade().equals("舒适型")){
		    							tcResBaseInfo.setResGradeId("26");
		    						}else if(tcResBaseInfo.getResGrade().equals("经济型")){
		    							tcResBaseInfo.setResGradeId("27");
		    						}
		    					}
		    					Map<String, List<ResProBaseInfo>> proMap=new HashMap<String, List<ResProBaseInfo>>();
		    					String key="";
		    					//List<Integer> minResPriceList=new ArrayList<Integer>();
		    					//List<Integer> commonResPriceList= new ArrayList<Integer>();
		    					//for(ResBaseInfo resList: resBaseInfoList){
		    					List<Integer> minProPrice=new ArrayList<Integer>();
    							Integer minPrice = 999999;
	    						for(ResProBaseInfo proList: resProBaseInfoList){
	    								key= proList.getResProName();
	    								if(!proMap.containsKey(key)){
	    									List<ResProBaseInfo> proBedList=new ArrayList<ResProBaseInfo>();
	    									proBedList.add(proList);
	    									proMap.put(key, proBedList); 
	    								}else{
	    									List<ResProBaseInfo> proBedList = proMap.get(key);
	    									proBedList.add(proList);
	    								}
	    							outer:
	    							for(ProInfoDetail proInfoDetail : proInfoDetailList){
	    								if(proList.getProductUniqueId().equals(proInfoDetail.getProductUniqueId())){
	    									if(proInfoDetail.getProSaleInfoDetails()!= null && proInfoDetail.getProSaleInfoDetails().size() > 0){ 
			    								TreeMap<String, ProSaleInfoDetail> map= proInfoDetail.getProSaleInfoDetails();
			    								//TreeMap<String, ProSaleInfoDetail> map1= new TreeMap<String, ProSaleInfoDetail>();
			    								//map1.putAll(map);
			    								if(StringUtil.isNotNullOrEmpty(map)){
			    									//proList.setProSaleInfoDetails(map1);
			    									for (Map.Entry<String, ProSaleInfoDetail> entry : map.entrySet()) {
			    										minProPrice.add(entry.getValue().getDistributionSalePrice());
			    										if(minProPrice.size() >= 1) {
			    											break outer;
			    										}
			    									}
			    								}
			    							}
	    								}
	    							}
	    						}
	    						if(minProPrice != null){
									if(minProPrice.size() >= 2){
										/*Integer sumPrice= 0;
										for(int k=0; k< minProPrice.size();k++){
											sumPrice += minProPrice.get(k);
										}
										Integer commonProPrice = sumPrice/minProPrice.size();*/
										minPrice= Collections.min(minProPrice);
										//minResPriceList.add(minPrice);
										//commonResPriceList.add(commonProPrice);
									}else if(minProPrice.size() == 1){
										minPrice= minProPrice.get(0);
										//minResPriceList.add(minPrice);
										//commonResPriceList.add(minPrice);
									}
								}
	    						tcResBaseInfo.setMinPrice(minPrice);
	    						tcResBaseInfo.setResCommonPrice(minPrice);
    							List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
    							if(proMap != null){
    								for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
    									ProDetails proInfoDetai=new ProDetails();
    									proInfoDetai.setProId(proMap.get(baseList.getKey()).get(0).getProId());
    									proInfoDetai.setResProName(baseList.getKey());
    									proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
    									proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
    									proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
    									proInfoDetai.setHasBroadband(proMap.get(baseList.getKey()).get(0).getHasBroadband());
    									proInfoDetai.setResProBaseInfoList(baseList.getValue());
    									ProInfoDetaisList.add(proInfoDetai);
    								}
    								tcResBaseInfo.setProDetails(ProInfoDetaisList);
    							}
    							
    							/*if(imgInfoList!=null && imgInfoList.size() > 0) {
    								List<ImgInfo> list2=new ArrayList<ImgInfo>();
	    							list2.addAll(imgInfoList);
	    							tcResBaseInfo.setImgInfoList(list2);
    							}*/
    							List<ImgInfo> list2=new ArrayList<ImgInfo>();
    							ImgInfoSum imgInfoSum = new ImgInfoSum();
    							Map<String, ImgInfo> mm = new HashMap<String, ImgInfo>();
    							if(imgInfoList!=null && imgInfoList.size() > 0) {
    								for(ImgInfo img : imgInfoList) {
    									if(img.getIsResDefault().equals(1)) {
    										mm.put("1", img);
    									}else if(img.getIsResProDefault().equals(1)){
    										mm.put(img.getResProId(), img);
    									}
    								}
    								for (Map.Entry<String, ImgInfo> entry : mm.entrySet()) {
    									list2.add(entry.getValue());
    								}
	    							//list2.addAll(imgInfoList);
	    							tcResBaseInfo.setImgInfoList(list2);
	    							imgInfoSum.setId(imgInfoList.get(0).getResId());
	    							imgInfoSum.setImgInfoList(imgInfoList);
	    							mongoTemplate1.save(imgInfoSum, "imgInfoSum");
    							}
    							
    							tcResBaseInfo.setSaleStatus(saleStatus);
	    						tcResBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
	    						mongoTemplate1.save(tcResBaseInfo, "resBaseInfo");
	    						
	    						
	    						/*List<ResGPSInfo> resGPS = tcResBaseInfo.getResGPS();
	    						QueryProperty queryProperty = new QueryProperty();
	    						for(ResGPSInfo gps : resGPS) {
	    							if(gps.getType().equals(1)) {
	    								String lat = gps.getLat();
	    								String lon = gps.getLon();
	    								int latPos = lat.length() - lat.indexOf(".") - 1;
	    								int lonPos = lon.length() - lon.indexOf(".") - 1;
	    								String latSubstring = "";
	    								String lonSubstring = "";
	    								if(latPos >= 3) {
	    									latSubstring = lat.substring(0,  lat.indexOf(".")+4);
	    								}else {
	    									latSubstring = lat;
	    								}
	    								if(lonPos >= 3) {
	    									lonSubstring = lon.substring(0,  lon.indexOf(".")+4);
	    								}else {
	    									lonSubstring = lon;
	    								}
	    								queryProperty.addQueryProperties("lat", OperateEnum.REGEX, latSubstring);
	    								queryProperty.addQueryProperties("lon", OperateEnum.REGEX, lonSubstring);
	    								break;
	    							}
	    						}
	    						if(StringUtil.isNotNullOrEmpty(tcResBaseInfo.getResPhone())) {
	    							String phone = tcResBaseInfo.getResPhone().replaceAll(" ", "");
	    							if(phone != null && !"".equals(phone)) {
	    								List<String> splitPhone = new ArrayList<String>();
	    								String realPhone = "";
	    								String[] mobileArr = null;
	    								String substring = "";
	    								String thirdEndStr = "";
	    								if(phone.contains("-")) {
	    									mobileArr = phone.split("-");
	    									if(mobileArr.length>=2) {
	    										for(String ss : mobileArr) {
	    											if(ss.length() == 7) {
	    												substring = ss.substring(0, 7);
	    												splitPhone.add(substring);
	    											}
	    											if(ss.length()>7) {
	    												substring = ss.substring(0, 7);
	    												String endString = ss.substring(7, ss.length());
	    												if(endString.length()>=8) {
	    													if(endString.contains("/")) {
	    														if(endString.length() - endString.indexOf("/") >= 8) {
	    															thirdEndStr = endString.substring(endString.indexOf("/")+1, endString.indexOf("/")+8);
	    														}
	    													}else if(endString.contains("、")) {
	    														if(endString.length() - endString.indexOf("、") >= 8) {
	    															thirdEndStr = endString.substring(endString.indexOf("、")+1, endString.indexOf("、")+8);
	    														}
	    													}else if(endString.contains("\\")) {
	    														if(endString.length() - endString.indexOf("\\") >= 8) {
	    															thirdEndStr = endString.substring(endString.indexOf("\\\\")+1, endString.indexOf("\\\\")+8);
	    														}
	    													}
	    													if(thirdEndStr.length() == 7) {
	    														splitPhone.add(thirdEndStr);
	    													}
	    												}
	    												splitPhone.add(substring);
	    											}
	    										}
	    									}
	    								}else {
	    									if(phone.length() >= 11) {
	    										substring = phone.substring(0, 11);
	    										splitPhone.add(substring);
	    									}
	    								}
	    								if(splitPhone.size() == 1) {
	    									realPhone = splitPhone.get(0);
	    									queryProperty.addQueryProperties("resPhone", OperateEnum.REGEX, realPhone);
	    								}else if(splitPhone.size() >= 2) {
	    									realPhone = splitPhone.get(0)+","+splitPhone.get(1);
	    									queryProperty.addQueryProperties("resPhone", OperateEnum.OROPERATOR, realPhone);
	    								}
	    							}
	    						}
	    						if(StringUtil.isNotNullOrEmpty(queryProperty)) {
	    							int page = 1;
	    							int pageSize = 10;
	    							Pageable pageable  = new PageRequest(page, pageSize);
	    							TCResponse<HolMidBaseInfo> findByProperty = holMongoQuery.findByProperty(queryProperty, pageable, HolMidBaseInfo.class);
	    							if(StringUtil.isNotNullOrEmpty(findByProperty) && StringUtil.isNotNullOrEmpty(findByProperty.getResponseResult())) {
	    								HolMidBaseInfo oneMidInfo = findByProperty.getResponseResult().get(0);
	    								oneMidInfo.setTcResId(tcResBaseInfo.getResId());
	    								oneMidInfo.setTcResName(tcResBaseInfo.getResName());
	    								oneMidInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
	    								oneMidInfo.setSupplierNo(oneMidInfo.getSupplierNo()+",411709261204150108");
	    								mongoTemplate.save(oneMidInfo, "holMidBaseInfo");
	    							}else {
		    							*//**
		    							 * 保存中间表
		    							 *//*
		    							HolMidBaseInfo holMidBaseInfo =new HolMidBaseInfo();
		    							holMidBaseInfo.setId(IdWorker.getId());
		    							holMidBaseInfo.setTcResId(tcResBaseInfo.getResId());
		    							holMidBaseInfo.setTcResName(tcResBaseInfo.getResName());
		    							holMidBaseInfo.setProvName(tcResBaseInfo.getProvName());
		    							holMidBaseInfo.setCityName(tcResBaseInfo.getCityName());
		    							holMidBaseInfo.setIsInter(tcResBaseInfo.getIsInter());
		    							//酒店品牌
		    							ResBrandInfo resBrandInfo = new ResBrandInfo();
		    							ResBrandInfo tcBrandInfo = tcResBaseInfo.getBrandInfo();
		    							if(StringUtil.isNotNullOrEmpty(tcBrandInfo)) {
		    								if(StringUtils.isNotEmpty(tcBrandInfo.getResBrandName())) {
		    									resBrandInfo.setResBrandName(tcBrandInfo.getResBrandName());
		    								}
		    								if(StringUtil.isNotNullOrEmpty(tcBrandInfo.getResBrandType())) {
		    									resBrandInfo.setResBrandType(tcBrandInfo.getResBrandType());
		    								}
			    							holMidBaseInfo.setBrandInfo(resBrandInfo);
		    							}
		    							
		    							holMidBaseInfo.setAddress(tcResBaseInfo.getAddress());
		    							if(StringUtil.isNotNullOrEmpty(tcResBaseInfo.getResPhone())) {
		    								String replaceAll = tcResBaseInfo.getResPhone().replaceAll(" ", "");
		    								holMidBaseInfo.setResPhone(replaceAll);
		    							}
		    							holMidBaseInfo.setIntro(tcResBaseInfo.getIntro());
		    							//酒店等级
		    							holMidBaseInfo.setResGrade(tcResBaseInfo.getResGradeId());
		    							
		    							//酒店坐标
		    							for(ResGPSInfo gps : tcResBaseInfo.getResGPS()) {
		    								if(gps.getType().equals(1)) {
		    									holMidBaseInfo.setLatLonType(gps.getType());
				    							holMidBaseInfo.setLat(gps.getLat());
				    							holMidBaseInfo.setLon(gps.getLon());
				    							break;
		    								}
		    							}
		    							
		    							
		    							//holMidBaseInfo.setCountryName("中国");
		    							//一句话介绍...酒店描述
		    							
		    							//酒店图片
		    							if(StringUtil.isNotNullOrEmpty(tcResBaseInfo.getImgInfoList())) {
		    								holMidBaseInfo.setTitleImg(tcResBaseInfo.getImgInfoList().get(0).getImageUrl());
		    							}
		    							holMidBaseInfo.setMinPrice(tcResBaseInfo.getMinPrice());
		    							holMidBaseInfo.setSupplierNo("411709261204150108");
		    							holMidBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
		    							holMidBaseInfo.setSaleStatus(1);
		    							holMidBaseInfo.setBookTimes(1L);
		    							holMidBaseInfo.setBookRemark(tcResBaseInfo.getLocation());
		    							mongoTemplate.save(holMidBaseInfo, "holMidBaseInfo");
		    						}
	    						}*/
    					}
    				}
			}
	}
			
			
		} catch (GSSException e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-IncrDetail");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店详细信息");
			logRecordHol.setDesc("同步酒店信息失败,酒店ID为："+String.valueOf(resId));
			logRecordHol.setResId(resId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
			//logger.error("同步酒店详情失败, 酒店ID为: "+resId+", "+e.getMessage());
		}
	}

	@Override
	public ResBaseInfo updateSingleResDetail(Agent agent, String resId) {
		SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
		singleHotelDetailReq.setResId(resId);
		singleHotelDetailReq.setSourceForm("-1");
		singleHotelDetailReq.setRequestContent("res,respro");
		
		TCHotelDetailResult hotelDetail=queryTCHotelDetail(singleHotelDetailReq);
		
		SingleHotelDetailReq singleHotelDetailReqImg=new SingleHotelDetailReq();
		singleHotelDetailReqImg.setResId(resId);
		singleHotelDetailReqImg.setSourceForm("-1");
		singleHotelDetailReqImg.setRequestContent("rimg");
		TCHotelDetailResult hotelDetailImg=queryTCHotelDetail(singleHotelDetailReqImg);
		List<ImgInfo> imgInfoList= hotelDetailImg.getResImages();
		
		if(StringUtil.isNotNullOrEmpty(hotelDetail)){
			List<ResBaseInfo> resBaseInfoList =hotelDetail.getResBaseInfos();
			List<ResProBaseInfo> resProBaseInfoList= hotelDetail.getResProBaseInfos();
			
			if(resBaseInfoList.size() > 0 && resProBaseInfoList.size() > 0){
				Map<String, List<ResProBaseInfo>> proMap=new HashMap<String, List<ResProBaseInfo>>();
				String key="";
					for(ResProBaseInfo proList: resProBaseInfoList){
							key= proList.getResProName();
							if(!proMap.containsKey(key)){
								List<ResProBaseInfo> proBedList=new ArrayList<ResProBaseInfo>();
								proBedList.add(proList);
								proMap.put(key, proBedList); 
							}else{
								List<ResProBaseInfo> proBedList = proMap.get(key);
								proBedList.add(proList);
							}
					}
						List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
						if(proMap != null){
							for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
								ProDetails proInfoDetai=new ProDetails();
								proInfoDetai.setProId(proMap.get(baseList.getKey()).get(0).getProId());
								proInfoDetai.setResProName(baseList.getKey());
								proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
								proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
								proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
								proInfoDetai.setHasBroadband(proMap.get(baseList.getKey()).get(0).getHasBroadband());
								proInfoDetai.setResProBaseInfoList(baseList.getValue());
								ProInfoDetaisList.add(proInfoDetai);
							}
							resBaseInfoList.get(0).setProDetails(ProInfoDetaisList);
						}
						/*if(StringUtil.isNotNullOrEmpty(hotelDetailImg)){
							List<ImgInfo> list2=new ArrayList<ImgInfo>();
							list2.addAll(imgInfoList);
							resBaseInfoList.get(0).setImgInfoList(list2);
						}*/
						List<ImgInfo> list2=new ArrayList<ImgInfo>();
						ImgInfoSum imgInfoSum = new ImgInfoSum();
						Map<String, ImgInfo> mm = new HashMap<String, ImgInfo>();
						if(imgInfoList!=null && imgInfoList.size() > 0) {
							for(ImgInfo img : imgInfoList) {
								if(img.getIsResDefault().equals(1)) {
									mm.put("1", img);
								}else if(img.getIsResProDefault().equals(1)){
									mm.put(img.getResProId(), img);
								}
							}
							for (Map.Entry<String, ImgInfo> entry : mm.entrySet()) {
								list2.add(entry.getValue());
							}
							//list2.addAll(imgInfoList);
							resBaseInfoList.get(0).setImgInfoList(list2);
							imgInfoSum.setId(imgInfoList.get(0).getResId());
							imgInfoSum.setImgInfoList(imgInfoList);
							mongoTemplate1.save(imgInfoSum, "imgInfoSum");
						}
						
						resBaseInfoList.get(0).setId(Long.valueOf(resId));
						//List<String> strs  = Tool.intToTwoPower(resBaseInfoList.get(0).getCreditCards().intValue());
						//resBaseInfoList.get(0).setCreditCardsTarget(strs);
						String updateTime = sdfupdate.format(new Date());
						mongoTemplate1.upsert(new Query(Criteria.where("_id").is(resId)), new Update().set("latestUpdateTime", updateTime), "resBaseInfo");
						return resBaseInfoList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<FormDate> updateProductunique(Agent agent, Long resId, String productUniqueId) {
		//Long resId = 883364L;	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();  
		Calendar calAdd = Calendar.getInstance();
		String calStartTime= sdf.format(cal.getTime());
		calAdd.add(Calendar.MONTH, 2);
		calAdd.add(Calendar.DAY_OF_MONTH, -1);
		String calEndTime= sdf.format(calAdd.getTime());
		int days =1;
		 try{
        	 days= DateUtil.daysBetween(calStartTime, calEndTime);
        }catch(ParseException e){
        	e.printStackTrace();
        }
		List<FormDate> fdList=new ArrayList<FormDate>();
		
		try {
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setProductUniqueId(productUniqueId);
			assignDateHotelReq.setSourceFrom("-1");
			//assignDateHotelReq.setNeedSpecialPolicy(1);
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calEndTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			ProInfoDetail proinfo = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(productUniqueId)),ProInfoDetail.class);
			
			if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(proinfo)){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				if(proInfoDetailList != null && proInfoDetailList.size() >0){
					TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proInfoDetailList.get(0).getProSaleInfoDetails();
					TreeMap<String, ProSaleInfoDetail> proinfomap = proinfo.getProSaleInfoDetails();
					
						 for (int i = 0; i <= days; i++) {
							 	Calendar calender = Calendar.getInstance();
							 	calender.add(Calendar.DAY_OF_MONTH, i);
							 	FormDate fd=new FormDate();
							 	fd.setDay(sdf.format(calender.getTime()));
							 	if(proSaleInfoDetails != null && proSaleInfoDetails.size() >0){
							 		for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
								 		if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
								 			if(entry.getValue().getInventoryStats().equals(4) || (entry.getValue().getOpeningSale().equals(false) && entry.getValue().getInventoryRemainder().equals(0))) {
								 				fd.setPrice(0);
								 			}else {
								 				fd.setPrice(entry.getValue().getDistributionSalePrice());
								 			}
								 		}
								 	}
							 	}
							 	if(proinfomap != null){
							 		for(Map.Entry<String, ProSaleInfoDetail> entry : proinfomap.entrySet()) {
								 		if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
								 			if(entry.getValue().getInventoryStats().equals(4) || (entry.getValue().getOpeningSale().equals(false) && entry.getValue().getInventoryRemainder().equals(0))) {
								 				fd.setPrice2(0);
								 			}else {
								 				fd.setPrice2(entry.getValue().getDistributionSalePrice());
								 			}
								 		}
								 	}
							 	}
							 	fdList.add(fd);
						}
				}
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return fdList;
	}

	@Override
	public Boolean doIncrInventoryWithProductUnique(Agent agent, Long resId, String productUniqueId) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();  
		Calendar calAdd = Calendar.getInstance();
		String calStartTime= sdf.format(cal.getTime());
		calAdd.add(Calendar.MONTH, 2);
		calAdd.add(Calendar.DAY_OF_MONTH, -1);
		String calEndTime= sdf.format(calAdd.getTime());
		try {
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setProductUniqueId(productUniqueId);
			assignDateHotelReq.setSourceFrom("-1");
			//assignDateHotelReq.setNeedSpecialPolicy(1);
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calEndTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			if(StringUtil.isNotNullOrEmpty(assignDateHotel)){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
					if(proInfoDetailList != null && proInfoDetailList.size() > 0){
						for(ProInfoDetail pro : proInfoDetailList){
							pro.setId(pro.getProductUniqueId());
							pro.setResId(assignDateHotel.getResId());
							pro.setUpdateInvenTime(sdfupdate.format(new Date()));
							mongoTemplate1.save(pro, "proInfoDetail");
						}
						log.info("库存插入成功");
					}
			}
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-Incr");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新政策价格库存");
			logRecordHol.setDesc("同步更新酒店政策价格失败,酒店ID为："+String.valueOf(resId));
			logRecordHol.setResId(resId);
			logRecordHol.setProductUniqueId(productUniqueId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
			//log.error("同步插入库存失败, 酒店ID为: "+resId+"政策ID为: "+productUniqueId);
		}
		return true;
	}
	
	
	
	
	
}

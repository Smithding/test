package com.tempus.gss.product.hol.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
import com.tempus.gss.product.hol.api.entity.response.ResIdList;
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
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
import com.tempus.gss.product.hol.api.entity.response.tc.ResultTc;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.product.hol.api.entity.response.tc.TcOrderCancelReasonList;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.dao.HotelOrderMapper;
import com.tempus.gss.product.hol.utils.HttpClientUtil;
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
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
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
			if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
				assignDateHotel.setId(resId);
				assignDateHotel.setLatestUpdateTime(sdfupdate.format(new Date()));
				mongoTemplate1.save(assignDateHotel, "assignDateHotel");
				
				ResIdList resIdList =new ResIdList();
				resIdList.setId(resId);
				mongoTemplate1.save(resIdList, "resIdList");
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
			SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
			singleHotelDetailReq.setResId(String.valueOf(resId));
			singleHotelDetailReq.setSourceForm("-1");
			singleHotelDetailReq.setRequestContent("res,rimg");
			TCHotelDetailResult hotelDetail=queryTCHotelDetail(singleHotelDetailReq);
			List<ResBaseInfo> resBaseInfos = hotelDetail.getResBaseInfos();
			List<ImgInfo> resImages = hotelDetail.getResImages();
			ResBaseInfo resBaseInfo = null;
			if(StringUtil.isNotNullOrEmpty(resBaseInfos)) {
				resBaseInfo = resBaseInfos.get(0);
				Integer minPrice = new Random().nextInt(800) + 100;
				resBaseInfo.setMinPrice(minPrice);
				resBaseInfo.setResCommonPrice(minPrice);
				resBaseInfo.setSaleStatus(1);
				resBaseInfo.setId(resId);
				resBaseInfo.setSupplierNo("411709261204150108");
				resBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
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
			}
			
			if(StringUtil.isNotNullOrEmpty(resImages)) {
				ImgInfoSum imgInfoSum =new ImgInfoSum();
				imgInfoSum.setId(resId);
				imgInfoSum.setImgInfoList(resImages);
				for(ImgInfo img : resImages) {
					if(img.getIsResDefault().equals(1)) {
						resBaseInfo.setImgUrl(img.getImageUrl());
						break;
					}
				}
				imgInfoSum.setLatestUpdateTime(sdfupdate.format(new Date()));
				mongoTemplate1.save(imgInfoSum, "imgInfoSum");
				mongoTemplate1.save(resBaseInfo, "resBaseInfo");
			}
			
			SingleHotelDetailReq singleHotelDetailReq2=new SingleHotelDetailReq();
			singleHotelDetailReq2.setResId(String.valueOf(resId));
			singleHotelDetailReq2.setSourceForm("-1");
			singleHotelDetailReq2.setRequestContent("respro");
			TCHotelDetailResult hotelDetail2=queryTCHotelDetail(singleHotelDetailReq2);
			if(StringUtil.isNotNullOrEmpty(hotelDetail2) && StringUtil.isNotNullOrEmpty(hotelDetail2.getResProBaseInfos())) {
				List<ResProBaseInfo> resProBaseInfo = hotelDetail2.getResProBaseInfos();
				
				if(StringUtil.isNotNullOrEmpty(resProBaseInfo)) {
					ResProBaseInfos resProBaseInfos=new ResProBaseInfos();
					resProBaseInfos.setId(resId);
					resProBaseInfos.setResProBaseInfos(resProBaseInfo);
					resProBaseInfos.setLatestUpdateTime(sdfupdate.format(new Date()));
					mongoTemplate1.save(resProBaseInfos, "resProBaseInfos");
				}
			}
			
			
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-resInfo");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店基本信息");
			logRecordHol.setDesc("同步更新酒店基本信息,酒店ID为："+String.valueOf(resId)+","+e.getMessage());
			logRecordHol.setResId(resId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
		}
	}

	@Override
	public ResBaseInfo updateSingleResDetail(Agent agent, String resId) {
		ResBaseInfo resBaseInfo = null;
		Integer minPrice = new Random().nextInt(200) + 100;
		Integer flag = 0;
		try {
			Calendar cal = Calendar.getInstance();  
			Calendar calAdd = Calendar.getInstance();
			String calStartTime= sdf.format(cal.getTime());
			
			calAdd.add(Calendar.MONTH, 2);
			calAdd.add(Calendar.DAY_OF_MONTH, -1);
			String calAddTime= sdf.format(calAdd.getTime());
			
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(Long.valueOf(resId));
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calAddTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			
			if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())){
				List<Integer> minProPrice=new ArrayList<Integer>();
				List<ProInfoDetail> proInfoDetailList = assignDateHotel.getProInfoDetailList();
				for(ProInfoDetail proInfoDetail : proInfoDetailList){
					if(proInfoDetail.getProSaleInfoDetails()!= null && proInfoDetail.getProSaleInfoDetails().size() > 0){ 
						TreeMap<String, ProSaleInfoDetail> map= proInfoDetail.getProSaleInfoDetails();
						if(StringUtil.isNotNullOrEmpty(map)){
							for (Map.Entry<String, ProSaleInfoDetail> entry : map.entrySet()) {
								minProPrice.add(entry.getValue().getDistributionSalePrice());
								if(minProPrice.size() >= 1) {
									break;
								}
							}
						}
					}
				}
				if(StringUtil.isNotNullOrEmpty(minProPrice)){
					flag = 1;
					if(minProPrice.size() >= 2){
						minPrice= Collections.min(minProPrice);
					}else if(minProPrice.size() == 1){
						minPrice= minProPrice.get(0);
					}
				}
				
				SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
				singleHotelDetailReq.setResId(String.valueOf(resId));
				singleHotelDetailReq.setSourceForm("-1");
				singleHotelDetailReq.setRequestContent("res,respro,rimg");
				TCHotelDetailResult hotelDetail=queryTCHotelDetail(singleHotelDetailReq);
				if(StringUtil.isNotNullOrEmpty(hotelDetail)) {
					List<ResBaseInfo> resBaseInfos = hotelDetail.getResBaseInfos();
					List<ResProBaseInfo> resProBaseInfoList= hotelDetail.getResProBaseInfos();
					List<ImgInfo> imgInfoList=  hotelDetail.getResImages();
					
					if(StringUtil.isNotNullOrEmpty(resBaseInfos) && StringUtil.isNotNullOrEmpty(resProBaseInfoList) && StringUtil.isNotNullOrEmpty(imgInfoList)) {
						resBaseInfo = resBaseInfos.get(0);
						
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
						resBaseInfo.setMinPrice(minPrice);
						resBaseInfo.setResCommonPrice(minPrice);
						Map<String, List<ResProBaseInfo>> proMap=new HashMap<String, List<ResProBaseInfo>>();
						String key="";
						
						for(ResProBaseInfo proList: resProBaseInfoList){
							for(ProInfoDetail de : assignDateHotel.getProInfoDetailList()) {
								if(proList.getProductUniqueId().equals(de.getProductUniqueId())) {
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
							}
						}
						
						List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
						if(StringUtil.isNotNullOrEmpty(proMap)){
							for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
								ProDetails proInfoDetai=new ProDetails();
								proInfoDetai.setProId(proMap.get(baseList.getKey()).get(0).getProId());
								proInfoDetai.setResProName(baseList.getKey());
								proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
								proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
								proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
								proInfoDetai.setResProBaseInfoList(baseList.getValue());
								ProInfoDetaisList.add(proInfoDetai);
							}
						}
						List<ImgInfo> list2=new ArrayList<ImgInfo>();
						Map<String, ImgInfo> mm = new HashMap<String, ImgInfo>();
						if(imgInfoList!=null && imgInfoList.size() > 0) {
							for(ImgInfo img : imgInfoList) {
								if(img.getIsResProDefault().equals(1)){
									mm.put(img.getResProId(), img);
								}
							}
							for (Map.Entry<String, ImgInfo> entry : mm.entrySet()) {
								list2.add(entry.getValue());
							}
						}
						
						resBaseInfo.setImgInfoList(list2);
						resBaseInfo.setProDetails(ProInfoDetaisList);
						
						if(flag.equals(0)) {
							resBaseInfo.setSaleStatus(0);
						}else {
							resBaseInfo.setSaleStatus(1);
						}
						resBaseInfo.setId(Long.valueOf(resId));
						resBaseInfo.setSupplierNo("411709261204150108");
						resBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
					}
					
					for(ImgInfo img : imgInfoList) {
						if(img.getIsResDefault().equals(1)) {
							resBaseInfo.setImgUrl(img.getImageUrl());
							break;
						}
					}
					mongoTemplate1.save(resBaseInfo, "resBaseInfo");
				}
			}
			
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-resInfo");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店基本信息");
			logRecordHol.setDesc("同步更新酒店基本信息,酒店ID为："+String.valueOf(resId)+","+e.getMessage());
			logRecordHol.setResId(Long.valueOf(resId));
			mongoTemplate1.save(logRecordHol, "logRecordHol");
		}
		return resBaseInfo;
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
			//ProInfoDetail proinfo = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(productUniqueId)),ProInfoDetail.class);
			//System.out.println(JsonUtil.toJson(assignDateHotel));
			if(StringUtil.isNotNullOrEmpty(assignDateHotel)){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				if(proInfoDetailList != null && proInfoDetailList.size() >0){
					TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proInfoDetailList.get(0).getProSaleInfoDetails();
					//TreeMap<String, ProSaleInfoDetail> proinfomap = proinfo.getProSaleInfoDetails();
					
						 for (int i = 0; i <= days; i++) {
							 	Calendar calender = Calendar.getInstance();
							 	calender.add(Calendar.DAY_OF_MONTH, i);
							 	FormDate fd=new FormDate();
							 	fd.setDay(sdf.format(calender.getTime()));
							 	if(proSaleInfoDetails != null && proSaleInfoDetails.size() >0){
							 		for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
							 			if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
							 				fd.setPrice(entry.getValue().getDistributionSalePrice());
							 				fd.setPrice2(entry.getValue().getDistributionSalePrice());
								 			if(!entry.getValue().getInventoryStats().equals(4)) {
		    	        							SimpleDateFormat newsdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		    	        							String nowTime = newsdf.format(new Date());
		    	        							int startDate = entry.getValue().getStartDate().compareTo(nowTime);
		    	        							int endDate = entry.getValue().getEndDate().compareTo(nowTime);
		    	        							if(startDate > 0 || endDate < 0) {
		    	        								fd.setPrice2(0);
		    	        							}
		    	        						}else {
		    	        							fd.setPrice2(0);
		    	        						}
							 			}
							 			
							 			
							 			
							 			
								 		/*if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
								 			if(entry.getValue().getInventoryStats().equals(4) || (entry.getValue().getOpeningSale().equals(false) && entry.getValue().getInventoryRemainder().equals(0))) {
								 				fd.setPrice(0);
								 			}else {
								 				fd.setPrice(entry.getValue().getDistributionSalePrice());
								 			}
								 		}*/
								 	}
							 	}
							 	/*if(proinfomap != null){
							 		for(Map.Entry<String, ProSaleInfoDetail> entry : proinfomap.entrySet()) {
								 		if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
								 			if(entry.getValue().getInventoryStats().equals(4) || (entry.getValue().getOpeningSale().equals(false) && entry.getValue().getInventoryRemainder().equals(0))) {
								 				fd.setPrice2(0);
								 			}else {
								 				fd.setPrice2(entry.getValue().getDistributionSalePrice());
								 			}
								 		}
								 	}
							 	}*/
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
		String calAddTime= sdf.format(calAdd.getTime());
		try {
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setProductUniqueId(productUniqueId);
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calAddTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			
			if(StringUtil.isNotNullOrEmpty(assignDateHotel)){
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				if(proInfoDetailList != null && proInfoDetailList.size() > 0){
					ProInfoDetail newProInfoDetail = proInfoDetailList.get(0);
					AssignDateHotel assignMongo = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(resId)),AssignDateHotel.class);
					if(StringUtil.isNotNullOrEmpty(assignMongo)) {
						List<ProInfoDetail> proInfoDetailMongo = assignMongo.getProInfoDetailList();
						List<ProInfoDetail> collect = proInfoDetailMongo.stream().filter(proInfoDetail -> proInfoDetail.getProductUniqueId().equals(productUniqueId)).collect(Collectors.toList());
						if(StringUtil.isNotNullOrEmpty(collect)) {
							ProInfoDetail proInfoDetail = collect.get(0);
							int position=proInfoDetailMongo.indexOf(proInfoDetail);
							if(position != -1) {
								proInfoDetailMongo.remove(position);
								proInfoDetailMongo.add(position, newProInfoDetail);
							}
							assignMongo.setLatestUpdateTime(sdfupdate.format(new Date()));
							mongoTemplate1.save(assignMongo, "assignDateHotel");
						}
					}
				}
			}
		} catch (GSSException e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-Unique");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新政策价格库存");
			logRecordHol.setDesc("同步更新酒店政策价格失败,酒店ID为："+String.valueOf(resId)+","+e.getMessage());
			logRecordHol.setResId(resId);
			logRecordHol.setProductUniqueId(productUniqueId);
			mongoTemplate1.save(logRecordHol, "logRecordHol");
			//logger.error("插入增量库存失败, 销售政策ID为: "+productUniqueId+", "+e.getMessage());
		}
		return true;
	}
	
	
	
	
}

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
	MongoTemplate mongoTemplate;
	
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
			    }else{
		            throw new GSSException("获取酒店列表", "0111", "酒店列表请求返回空值");
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
		        	mongoTemplate.upsert(query, update, ResBaseInfo.class);
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
	public List<ResourceProductIds> queryIncrHotelList(IncrHotelList incrHotelList) throws GSSException{
		String reqJson= JSONObject.toJSONString(incrHotelList);
		String result= httpClientUtil.doTCJsonPost(ASSIGN_LIST_URL, reqJson);
		if(StringUtil.isNotNullOrEmpty(result)){
			ResultTc<IncrHotelPrice> incrHotelPriceBase= JsonUtil.toBean(result, new TypeReference<ResultTc<IncrHotelPrice>>(){});
			if(StringUtil.isNotNullOrEmpty(incrHotelPriceBase) && StringUtil.isNotNullOrEmpty(incrHotelPriceBase.getResult())){
				return incrHotelPriceBase.getResult().getResourceProductIdsList();
			}
		}
		 else{
	            //log.error("定时器获取酒店增量数据");
	            throw new GSSException("定时器获取酒店增量数据", "0111", "定时器获取酒店增量数据请求返回空值");
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
			assignDateHotelReq.setNeedSpecialPolicy(1);
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
							mongoTemplate.save(pro, "proInfoDetail");
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
			mongoTemplate.save(logRecordHol, "logRecordHol");
		}
		return true;
	}
	
	@Override
	public void doIncrHotelDetail(Long resId){
		try {
			SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
			String ResId= String.valueOf(resId);
			singleHotelDetailReq.setResId(ResId);
			singleHotelDetailReq.setSourceForm("-1");
			singleHotelDetailReq.setRequestContent("res,respro");
			TCHotelDetailResult hotelDetail=queryTCHotelDetail(singleHotelDetailReq);
			
			SingleHotelDetailReq singleHotelDetailReqImg=new SingleHotelDetailReq();
			singleHotelDetailReqImg.setResId(ResId);
			singleHotelDetailReqImg.setSourceForm("-1");
			singleHotelDetailReqImg.setRequestContent("rimg");
			TCHotelDetailResult hotelDetailImg=queryTCHotelDetail(singleHotelDetailReqImg);
			
			if(StringUtil.isNotNullOrEmpty(hotelDetail)){
				List<ResBaseInfo> resBaseInfoList =hotelDetail.getResBaseInfos();
				List<ResProBaseInfo> resProBaseInfoList= hotelDetail.getResProBaseInfos();
				
				if(resBaseInfoList.size() > 0 && resProBaseInfoList.size() > 0){
					resBaseInfoList.get(0).setSupplierNo("411709261204150108");
					if(StringUtil.isNotNullOrEmpty(resBaseInfoList.get(0).getResGrade())){
						if(resBaseInfoList.get(0).getResGrade().equals("豪华型")){
							resBaseInfoList.get(0).setResGradeId("23");
						}else if(resBaseInfoList.get(0).getResGrade().equals("高档型")){
							resBaseInfoList.get(0).setResGradeId("24");
						}else if(resBaseInfoList.get(0).getResGrade().equals("舒适型")){
							resBaseInfoList.get(0).setResGradeId("26");
						}else if(resBaseInfoList.get(0).getResGrade().equals("经济型")){
							resBaseInfoList.get(0).setResGradeId("27");
						}
					}
					Map<String, List<ResProBaseInfo>> proMap=new HashMap<String, List<ResProBaseInfo>>();
					String key="";
					List<Integer> minResPriceList=new ArrayList<Integer>();
					List<Integer> commonResPriceList= new ArrayList<Integer>();
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
							List<Integer> minProPrice=new ArrayList<Integer>();
							ProInfoDetail proInfoDetail= hotelFind.queryListByProductUniqueId(proList.getProductUniqueId().longValue(), ProInfoDetail.class);
							if(proInfoDetail != null && proInfoDetail.getProSaleInfoDetails()!= null && proInfoDetail.getProSaleInfoDetails().size() > 0){
								TreeMap<String, ProSaleInfoDetail> map= proInfoDetail.getProSaleInfoDetails();
								if(map != null){
									for (Map.Entry<String, ProSaleInfoDetail> entry : map.entrySet()) {
										minProPrice.add(entry.getValue().getDistributionSalePrice());
									}
									if(StringUtil.isNotNullOrEmpty(minProPrice)){
										if(minProPrice.size() >= 2){
											Integer sumPrice= 0;
											for(int k=0; k< minProPrice.size();k++){
												sumPrice += minProPrice.get(k);
											}
											Integer commonProPrice = sumPrice/minProPrice.size();
											Integer minPrice= Collections.min(minProPrice);
											minResPriceList.add(minPrice);
											commonResPriceList.add(commonProPrice);
										}else if(minProPrice.size() == 1){
											Integer minPrice= minProPrice.get(0);
											minResPriceList.add(minPrice);
											commonResPriceList.add(minPrice);
										}else{
											Integer minPrice = 999999;
											minResPriceList.add(minPrice);
											commonResPriceList.add(minPrice);
										}
									}
								}
							}
						}
							if(StringUtil.isNotNullOrEmpty(minResPriceList)){
								if(minResPriceList.size() >=2){
									Integer minResPrice= Collections.min(minResPriceList);
									resBaseInfoList.get(0).setMinPrice(minResPrice);
								}else if(minResPriceList.size() == 1){
									Integer minResPrice= minResPriceList.get(0);
									resBaseInfoList.get(0).setMinPrice(minResPrice);
								}else{
									Integer minResPrice = 999999;
									resBaseInfoList.get(0).setMinPrice(minResPrice);
								}
							}
							if(commonResPriceList.size() > 0){
								Integer comResPrice = 0;
								for(int kk=0; kk<commonResPriceList.size();kk++){
									comResPrice += commonResPriceList.get(kk);
								}
								resBaseInfoList.get(0).setResCommonPrice(comResPrice/commonResPriceList.size());
							}else{
								resBaseInfoList.get(0).setResCommonPrice(999999);
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
									proInfoDetai.setHasBroadband(proMap.get(baseList.getKey()).get(0).getHasBroadband());
									proInfoDetai.setResProBaseInfoList(baseList.getValue());
									ProInfoDetaisList.add(proInfoDetai);
								}
								resBaseInfoList.get(0).setProDetails(ProInfoDetaisList);
							}
							if(StringUtil.isNotNullOrEmpty(hotelDetailImg)){
								List<ImgInfo> imgInfoList= hotelDetailImg.getResImages();
								List<ImgInfo> list2=new ArrayList<ImgInfo>();
								list2.addAll(imgInfoList);
								resBaseInfoList.get(0).setImgInfoList(list2);
							}
							resBaseInfoList.get(0).setId(resId);
							Integer saleStatus = 1;
							resBaseInfoList.get(0).setSaleStatus(saleStatus);
							resBaseInfoList.get(0).setLatestUpdateTime(sdfupdate.format(new Date()));
							mongoTemplate.save(resBaseInfoList.get(0),"resBaseInfo");
						//log.info("插入酒店详细信息到mongodb成功!");
							log.info("插入酒店详细信息到mongodb成功!");
				}
			}
		} catch (Exception e) {
			LogRecordHol logRecordHol=new LogRecordHol();
			logRecordHol.setBizCode("HOL-IncrDetail");
			logRecordHol.setCreateTime(new Date());
			logRecordHol.setTitle("更新酒店详细信息");
			logRecordHol.setDesc("同步酒店信息失败,酒店ID为："+String.valueOf(resId));
			logRecordHol.setResId(resId);
			mongoTemplate.save(logRecordHol, "logRecordHol");
			//log.error("同步酒店详情失败, 酒店ID为: "+resId);
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
						if(StringUtil.isNotNullOrEmpty(hotelDetailImg)){
							List<ImgInfo> imgInfoList= hotelDetailImg.getResImages();
							List<ImgInfo> list2=new ArrayList<ImgInfo>();
							list2.addAll(imgInfoList);
							resBaseInfoList.get(0).setImgInfoList(list2);
						}
						resBaseInfoList.get(0).setId(Long.valueOf(resId));
						List<String> strs  = Tool.intToTwoPower(resBaseInfoList.get(0).getCreditCards().intValue());
						resBaseInfoList.get(0).setCreditCardsTarget(strs);
						String updateTime = sdfupdate.format(new Date());
						mongoTemplate.upsert(new Query(Criteria.where("_id").is(resId)), new Update().set("latestUpdateTime", updateTime), "resBaseInfo");
						return resBaseInfoList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<FormDate> updateProductunique(Agent agent, Long resId, Long productUniqueId) {
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
			assignDateHotelReq.setNeedSpecialPolicy(1);
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calEndTime);
			AssignDateHotel assignDateHotel=  queryAssignDateHotel(assignDateHotelReq);
			ProInfoDetail proinfo = mongoTemplate.findOne(new Query(Criteria.where("_id").is(productUniqueId)),ProInfoDetail.class);
			
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
								 			fd.setPrice(entry.getValue().getDistributionSalePrice());
								 		}
								 	}
							 	}
							 	if(proinfomap != null){
							 		for(Map.Entry<String, ProSaleInfoDetail> entry : proinfomap.entrySet()) {
								 		if(fd.getDay().compareTo(DateUtil.stringToSimpleString(entry.getKey())) == 0){
								 			fd.setPrice2(entry.getValue().getDistributionSalePrice());
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
	public Boolean doIncrInventoryWithProductUnique(Agent agent, Long resId, Long productUniqueId) {
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
			assignDateHotelReq.setNeedSpecialPolicy(1);
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
							mongoTemplate.save(pro, "proInfoDetail");
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
			mongoTemplate.save(logRecordHol, "logRecordHol");
			//log.error("同步插入库存失败, 酒店ID为: "+resId+"政策ID为: "+productUniqueId);
		}
		return true;
	}
	
	
	
	
	
}

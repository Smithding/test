package com.tempus.gss.product.hol.thread;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.query.LogRecordQuery;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.product.hol.api.entity.ProfitPrice;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.request.tc.AssignDateHotelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.OrderDetailInfoReq;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfoSum;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderInfomationDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.product.hol.api.service.IHolProfitService;
import com.tempus.gss.product.hol.api.syn.ISyncHotelInfo;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelOrderService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.service.TCHotelSupplierServiceImpl;
import com.tempus.gss.product.hol.utils.HolAnnotation;
import com.tempus.gss.product.hol.utils.TrackTime;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

@Service
public class SyncHotelInfoImpl implements ISyncHotelInfo {
	protected final transient Logger logger = LogManager.getLogger(TCHotelSupplierServiceImpl.class);
	@Autowired
	private ITCHotelSupplierService tcHotelSupplierService;
	
	@Autowired
	private ITCHotelInterService tcHotelInterService;
	
	@Autowired
	private IBQYHotelSupplierService bqyHotelSupplierService;
	
	@Autowired
	private IBQYHotelInterService bqyHotelInterService;
	
	@Autowired
	private MongoTemplate mongoTemplate1;
	
	@Autowired
	IHolProfitService holProfitService;
	
	@Autowired
	ITCHotelInterService hotelInterService;
	
	@Autowired
	ITCHotelOrderService tcHotelOrderService;
	
	@Reference
	private ILogService logService;
	
	@Value("${bqy.count}")
	private int PAGE_SIZE;			//查询id数量
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	

	@Override
	public ResBaseInfo queryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException {
		 long start = System.currentTimeMillis();
		if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }else{
        	if(StringUtil.isNullOrEmpty(agent.getType())){
        		throw new GSSException("获取某一酒店详细信息", "0102", "agentType为空");
        	}
        }
		if (StringUtil.isNullOrEmpty(resId)) {
            logger.error("酒店id为空");
            throw new GSSException("获取某一酒店详细信息", "0100", "酒店id为空");
        }
		if (StringUtil.isNullOrEmpty(startTime)) {
			logger.error("开始日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "开始日期为空");
        }
        if (StringUtil.isNullOrEmpty(endTime)) {
            logger.error("结束日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
        }
        ResBaseInfo tcResBaseInfo = null;
        Integer sumPrice=0;
        Integer days = 0;
		try {
			days= DateUtil.daysBetween(startTime, endTime);
		//	Future<AssignDateHotel> assignDateHotelFuture = tcHotelSupplierService.queryListById(resId, AssignDateHotel.class);
			Future<ResProBaseInfos> resProBaseInfosFuture = tcHotelSupplierService.queryResProBaseInfos(resId);
			Future<ResBaseInfo> resBaseInfoFuture = tcHotelSupplierService.queryResBaseInfo(resId);
			Future<ImgInfoSum> imgInfoSumFuture = tcHotelSupplierService.queryImgInfoSum(resId);
			
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setStartTime(startTime);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(sdf.parse(endTime));
	        calendar.add(Calendar.DAY_OF_MONTH, -1);
			assignDateHotelReq.setEndTime(sdf.format(calendar.getTime()));
			AssignDateHotel assignDateHotel=  hotelInterService.queryAssignDateHotel(assignDateHotelReq);
			
			tcResBaseInfo = resBaseInfoFuture.get();
			ResProBaseInfos resProBaseInfos = resProBaseInfosFuture.get();
			ImgInfoSum imgInfoSum = imgInfoSumFuture.get();
			
			if(StringUtil.isNotNullOrEmpty(assignDateHotel)) {
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				List<ResProBaseInfo> resProBaseInfoList= resProBaseInfos.getResProBaseInfos();
				List<ImgInfo> imgInfoList= imgInfoSum.getImgInfoList();
				
				if(StringUtil.isNotNullOrEmpty(tcResBaseInfo) && StringUtil.isNotNullOrEmpty(resProBaseInfoList)){
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
					
					for(ProDetails ppp : ProInfoDetaisList){
						for(ImgInfo img : list2) {
							if(ppp.getProId().equals(img.getResProId())) {
								ppp.setImgUrl(img.getImageUrl());
							}
						}
         				List<Integer> pppRice = new ArrayList<Integer>();
         				List<ResProBaseInfo> p = ppp.getResProBaseInfoList();
         				if(StringUtil.isNotNullOrEmpty(p)){
     	            		for(ResProBaseInfo pro : p){
     	            			pro.setSupplierType(1);
     	            			Integer firstPrice = 999999;
     	            			TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
     	            			Calendar da = Calendar.getInstance(); 
	                   			 for (int i = 0; i < days; i++) {
	                   				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
	                   				 ProSaleInfoDetail.setDistributionSalePrice(0);
	                   				 da.setTime(sdf.parse(startTime));
	                   				 da.add(Calendar.DAY_OF_MONTH, i);
	                   				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
	                				}
	                   				 for(ProInfoDetail proInfo : proInfoDetailList) {
	                   			 		if(pro.getProductUniqueId().equals(proInfo.getProductUniqueId())) {
	                   			 			TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proInfo.getProSaleInfoDetails();
	                   			 			if(StringUtil.isNotNullOrEmpty(proSaleInfoDetails)) {
	                   			 				if(proSaleInfoDetails.containsKey(DateUtil.stringToLonString(startTime))) {
		                   			 				Integer firProPrice = proSaleInfoDetails.get(DateUtil.stringToLonString(startTime)).getDistributionSalePrice();
		                   			 				if(StringUtil.isNotNullOrEmpty(firProPrice)) {
		                   			 					if(!agent.getNum().equals(401803070321014723L)) {
			                   			 					BigDecimal profitPrice = holProfitService.computeTcProfitPrice(agent, firProPrice, agent.getType());
				         	                				if(StringUtil.isNotNullOrEmpty(profitPrice)){
				         	                					pro.setRebateRateProfit(profitPrice);
				         	                				}
		                   			 					}
		                   			 				}
	                   			 				}
		                   			 			int kk = 0;
		         	    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
		         	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
		         	    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
		         	    	        					if(begincompare >= 0 && endCompare < 0){
		         	    	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
		         	    	        						Integer price= entry.getValue().getDistributionSalePrice();
		         	    	        						pppRice.add(price);
		         	    	        						if(!entry.getValue().getInventoryStats().equals(4)) {
		         	    	        							SimpleDateFormat newsdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		         	    	        							String nowTime = newsdf.format(new Date());
		         	    	        							int startDate = entry.getValue().getStartDate().compareTo(nowTime);
		         	    	        							int endDate = entry.getValue().getEndDate().compareTo(nowTime);
		         	    	        							if(startDate > 0 || endDate < 0) {
		         	    	        								pro.setBookStatus(0);
		         	    	        							}
		         	    	        						}else {
		         	    	        							pro.setBookStatus(0);
		         	    	        						}
	         	    	        							if(days.equals(1) && (sdf.format(new Date()).equals(startTime))) {
	         	    	        								Date startTime1 = DateUtil.stringToSimpleDate(entry.getValue().getStartTime());
		         	    	        							Date endTime1 = DateUtil.stringToSimpleDate(entry.getValue().getEndTime());
		         	    	        							Date nowDate = new Date();
		         	    	        							if(nowDate.before(startTime1) || endTime1.before(nowDate)) {
		         	    	        								pro.setBookStatus(0);
		         	    	        							}
	         	    	        							}
		         	    	        						sumPrice += price;
		         	    	        						kk += 1;
		         	    	        						if(kk == 1) {
		         	    	        							firstPrice = entry.getValue().getDistributionSalePrice();
		         	    	        						}
		         	    	        					}
		         	    	        					/*if(kk == days.intValue()) {
	         	    	        							break;
	         	    	        						}*/
		         	            					}
		     	    	        					pro.setProSaleInfoDetailsTarget(mapPro);
		     	    	        					pro.setFirPrice(firstPrice);
		     	    	        					if(pppRice.size() == 1){
		     	    	        						ppp.setProDetailConPrice(pppRice.get(0));
		     	    	        					}else if(pppRice.size() >= 2){
		     	    	        						ppp.setProDetailConPrice(Collections.min(pppRice));
		     	    	        					}else {
		     	    	        						ppp.setSaleStatus(0);
		     	    	        					}
		     	    	        					if(kk < days.intValue()) {
		     	    	        						pro.setBookStatus(0);
	        	    	        					}
		     	    	        					if(kk != 0){
		        	    	        					pro.setConPrice(sumPrice/kk);
		        	    	        				}
		        	    	        				kk = 0;
		        	    	        				sumPrice =0;
	                   			 			}else {
	                   			 				pro.setBookStatus(0);
	                   			 				pro.setFirPrice(firstPrice);
	                   			 			}
	                   			 		}
	                   			 		/*else {
	                   			 			pro.setBookStatus(0);
	                   			 			pro.setFirPrice(firstPrice);
	                   			 		}*/
	                   			 	}
     		            		}
         					}
	         				if(pppRice.size() == 0) {
	         					ppp.setSaleStatus(0);
	         				}
         				}
					tcResBaseInfo.setImgInfoList(list2);
					tcResBaseInfo.setProDetails(ProInfoDetaisList);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		long end = System.currentTimeMillis();  
	    System.out.println("完成任务queryHotelDetail，耗时：" + (end - start) + "毫秒"); 
		return tcResBaseInfo;
	}
	
	@Override
	@Async
	public Future<ResBaseInfo> newQueryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException {
				long start = System.currentTimeMillis();
				if (StringUtil.isNullOrEmpty(agent)) {
		            logger.error("agent对象为空");
		            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
		        }else{
		        	if(StringUtil.isNullOrEmpty(agent.getType())){
		        		throw new GSSException("获取某一酒店详细信息", "0102", "agentType为空");
		        	}
		        }
				if (StringUtil.isNullOrEmpty(resId)) {
		            logger.error("酒店id为空");
		            throw new GSSException("获取某一酒店详细信息", "0100", "酒店id为空");
		        }
				if (StringUtil.isNullOrEmpty(startTime)) {
					logger.error("开始日期为空");
		            throw new GSSException("获取某一酒店详细信息", "0105", "开始日期为空");
		        }
		        if (StringUtil.isNullOrEmpty(endTime)) {
		            logger.error("结束日期为空");
		            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
		        }
		        ResBaseInfo tcResBaseInfo = null;
		        Integer sumPrice=0;
		        Integer days = 0;
				try {
					
					AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
					assignDateHotelReq.setResId(resId);
					assignDateHotelReq.setSourceFrom("-1");
					assignDateHotelReq.setStartTime(startTime);
					Calendar calendar = Calendar.getInstance();
			        calendar.setTime(sdf.parse(endTime));
			        calendar.add(Calendar.DAY_OF_MONTH, -1);
					assignDateHotelReq.setEndTime(sdf.format(calendar.getTime()));
					Future<AssignDateHotel> assignDateHotelFu=  hotelInterService.queryFuAssignDateHotel(assignDateHotelReq);
					
					days= DateUtil.daysBetween(startTime, endTime);
				//	Future<AssignDateHotel> assignDateHotelFuture = tcHotelSupplierService.queryListById(resId, AssignDateHotel.class);
					Future<ResProBaseInfos> resProBaseInfosFuture = tcHotelSupplierService.queryResProBaseInfos(resId);
					Future<ResBaseInfo> resBaseInfoFuture = tcHotelSupplierService.queryResBaseInfo(resId);
					Future<ImgInfoSum> imgInfoSumFuture = tcHotelSupplierService.queryImgInfoSum(resId);
					Future<List<ProfitPrice>> computeProfitByAgentFu = null;
					String supplierSource = "tc";
					computeProfitByAgentFu = holProfitService.computeProfitByAgentNum(agent, agent.getType(),supplierSource);
					
					tcResBaseInfo = resBaseInfoFuture.get();
					ResProBaseInfos resProBaseInfos = resProBaseInfosFuture.get();
					ImgInfoSum imgInfoSum = imgInfoSumFuture.get();
					AssignDateHotel assignDateHotel = assignDateHotelFu.get();
					//System.out.println("TC: "+JsonUtil.toJson(assignDateHotel));
					if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
						List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
						List<ResProBaseInfo> resProBaseInfoListBeFore= resProBaseInfos.getResProBaseInfos();
						List<ImgInfo> imgInfoList= imgInfoSum.getImgInfoList();
						
						if(StringUtil.isNotNullOrEmpty(tcResBaseInfo) && StringUtil.isNotNullOrEmpty(resProBaseInfoListBeFore)){
							List<ImgInfo> list2=Lists.newArrayList();
							Map<String, ImgInfo> mm = new HashMap<String, ImgInfo>();
							if(imgInfoList!=null && imgInfoList.size() > 0) {
								mm = imgInfoList.stream().collect(Collectors.toMap(ImgInfo::getResProId, a -> a,(k1,k2)->k1));
								list2 = mm.entrySet().stream().map(et ->et.getValue()).collect(Collectors.toList());
								//这个去重效率慢
								//list2 = imgInfoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ImgInfo::getResProId))), ArrayList::new));
							}
							
							List<ResProBaseInfo> resProBaseInfoList = new ArrayList<ResProBaseInfo>();
							for(ProInfoDetail proSaleKey : proInfoDetailList) {
								Integer firstPrice = 999999;
								List<ResProBaseInfo> filterList = resProBaseInfoListBeFore.stream().filter(pro -> pro.getProductUniqueId().equals(proSaleKey.getProductUniqueId())).collect(Collectors.toList());
								if(filterList != null && filterList.size() >0) {
									ResProBaseInfo pro = filterList.get(0);
									TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
									List<Integer> pppRice = new ArrayList<Integer>();
		 	            			Calendar da = Calendar.getInstance(); 
		                   			 for (int i = 0; i < days; i++) {
		                   				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
		                   				 ProSaleInfoDetail.setDistributionSalePrice(0);
		                   				 da.setTime(sdf.parse(startTime));
		                   				 da.add(Calendar.DAY_OF_MONTH, i);
		                   				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
		                				}
									TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proSaleKey.getProSaleInfoDetails();
		       			 			if(StringUtil.isNotNullOrEmpty(proSaleInfoDetails)) {
		       			 				if(proSaleInfoDetails.containsKey(DateUtil.stringToLonString(startTime))) {
		           			 				Integer firProPrice = proSaleInfoDetails.get(DateUtil.stringToLonString(startTime)).getDistributionSalePrice();
		           			 				if(StringUtil.isNotNullOrEmpty(firProPrice)) {
		           			 					if(computeProfitByAgentFu!=null) {
		           			 						List<ProfitPrice> computeProfitByAgent = computeProfitByAgentFu.get();
			           			 					if(computeProfitByAgent != null && computeProfitByAgent.size() > 0) {
			               			 					for(ProfitPrice profit : computeProfitByAgent) {
			               			 						BigDecimal lowerPrice = profit.getPriceFrom();
			               			 						BigDecimal upPrice = profit.getPriceTo();
			               			 						BigDecimal firPrice = new BigDecimal(firProPrice);
			               			 						if(lowerPrice.compareTo(firPrice) <= 0 && upPrice.compareTo(firPrice) >= 0) {
			               			 							BigDecimal rate = profit.getRate();
			               			 							rate = rate.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
			               			 							pro.setRebateRateProfit(rate);
			               			 						}
			               			 					}
			           			 					}
		           			 					}
		           			 				}
		       			 				}
		           			 			int kk = 0;
		 	    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
		 	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
		 	    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
		 	    	        					if(begincompare >= 0 && endCompare < 0){
		 	    	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
		 	    	        						Integer price= entry.getValue().getDistributionSalePrice();
		 	    	        						pppRice.add(price);
		 	    	        						if(!entry.getValue().getInventoryStats().equals(4)) {
		 	    	        							SimpleDateFormat newsdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 	    	        							String nowTime = newsdf.format(new Date());
		 	    	        							int startDate = entry.getValue().getStartDate().compareTo(nowTime);
		 	    	        							int endDate = entry.getValue().getEndDate().compareTo(nowTime);
		 	    	        							if(startDate > 0 || endDate < 0) {
		 	    	        								pro.setBookStatus(0);
		 	    	        							}
		 	    	        						}else {
		 	    	        							pro.setBookStatus(0);
		 	    	        						}
			    	        							if(days.equals(1) && (sdf.format(new Date()).equals(startTime))) {
			    	        								Date startTime1 = DateUtil.stringToSimpleDate(entry.getValue().getStartTime());
		 	    	        							Date endTime1 = DateUtil.stringToSimpleDate(entry.getValue().getEndTime());
		 	    	        							Date nowDate = new Date();
			 	    	        							if(nowDate.before(startTime1) || endTime1.before(nowDate)) {
			 	    	        								pro.setBookStatus(0);
			 	    	        							}
			    	        							}
		 	    	        						sumPrice += price;
		 	    	        						kk += 1;
		 	    	        						if(kk == 1) {
		 	    	        							firstPrice = entry.getValue().getDistributionSalePrice();
		 	    	        						}
		 	    	        					}
		 	    	        					/*if(kk == days.intValue()) {
			    	        							break;
			    	        						}*/
		 	            					}
			    	        					pro.setProSaleInfoDetailsTarget(mapPro);
			    	        					pro.setFirPrice(firstPrice);
			    	        					if(pppRice.size() == 1){
			    	        						pro.setAdvancedLimitDays(pppRice.get(0));
			    	        					}else if(pppRice.size() >= 2){
			    	        						pro.setAdvancedLimitDays(Collections.min(pppRice));
			    	        					}else {
			    	        						pro.setBookStatus(0);
			    	        					}
			    	        					if(kk < days.intValue()) {
			    	        						pro.setBookStatus(0);
			    	        					}
			    	        					if(kk != 0){
			    	        					pro.setConPrice(sumPrice/kk);
			    	        				}
			    	        				kk = 0;
			    	        				sumPrice =0;
		       			 			}else {
		       			 				pro.setBookStatus(0);
		       			 				pro.setFirPrice(firstPrice);
		       			 			}
		       			 			pro.setResProName(pro.getResProName().replaceAll("\\s*", "").replaceAll("（", "(").replaceAll("）", ")"));
		       			 			pro.setSupplierType(1);
									resProBaseInfoList.add(pro);
								}
							}
							Map<String, List<ResProBaseInfo>> proMap = resProBaseInfoList.stream().collect(Collectors.groupingBy(ResProBaseInfo::getProId));
							
							List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
							if(StringUtil.isNotNullOrEmpty(proMap)){
								for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
									ProDetails proInfoDetai=new ProDetails();
									proInfoDetai.setProId(baseList.getKey());
									proInfoDetai.setResProName(proMap.get(baseList.getKey()).get(0).getResProName());
									proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
									proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
									proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
									proInfoDetai.setBedSize(proMap.get(baseList.getKey()).get(0).getBedSize());
									List<ResProBaseInfo> valueList = baseList.getValue();
									if(valueList != null && valueList.size() > 0) {
										//valueList.sort(Comparator.comparingInt(ResProBaseInfo :: getFirPrice));
										int sum = valueList.stream().mapToInt(ResProBaseInfo :: getAdvancedLimitDays).sum();
										//int asInt = valueList.stream().mapToInt(ResProBaseInfo :: getFirPrice).min().getAsInt();
										//proInfoDetai.setMinPrice(asInt);
										proInfoDetai.setProDetailConPrice(sum/valueList.size());
									}else {
										proInfoDetai.setSaleStatus(0);
									}
									if(mm.containsKey(baseList.getKey())) {
										String imageUrl = mm.get(baseList.getKey()).getImageUrl();
										proInfoDetai.setImgUrl(imageUrl);
									}
									
									proInfoDetai.setResProBaseInfoList(valueList);
									ProInfoDetaisList.add(proInfoDetai);
								}
							}
							tcResBaseInfo.setImgInfoList(list2);
							tcResBaseInfo.setProDetails(ProInfoDetaisList);
						}
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				long end = System.currentTimeMillis();  
			    System.out.println("完成任务newQueryHotelDetail，耗时：" + (end - start) + "毫秒"); 
				//return tcResBaseInfo;
			    return new AsyncResult<ResBaseInfo>(tcResBaseInfo);
	}
	
	@Override
	public ResBaseInfo queryProDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException {
				if (StringUtil.isNullOrEmpty(agent)) {
		            logger.error("agent对象为空");
		            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
		        }else{
		        	if(StringUtil.isNullOrEmpty(agent.getType())){
		        		throw new GSSException("获取某一酒店详细信息", "0102", "agentType为空");
		        	}
		        }
				if (StringUtil.isNullOrEmpty(resId)) {
		            logger.error("酒店id为空");
		            throw new GSSException("获取某一酒店详细信息", "0100", "酒店id为空");
		        }
				if (StringUtil.isNullOrEmpty(startTime)) {
					logger.error("开始日期为空");
		            throw new GSSException("获取某一酒店详细信息", "0105", "开始日期为空");
		        }
		        if (StringUtil.isNullOrEmpty(endTime)) {
		            logger.error("结束日期为空");
		            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
		        }
		        ResBaseInfo tcResBaseInfo = new ResBaseInfo();
		        tcResBaseInfo.setId(resId);
		        tcResBaseInfo.setResId(resId);
		        Integer sumPrice=0;
		        Integer days = 0;
				try {
					
					AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
					assignDateHotelReq.setResId(resId);
					assignDateHotelReq.setSourceFrom("-1");
					assignDateHotelReq.setStartTime(startTime);
					Calendar calendar = Calendar.getInstance();
			        calendar.setTime(sdf.parse(endTime));
			        calendar.add(Calendar.DAY_OF_MONTH, -1);
					assignDateHotelReq.setEndTime(sdf.format(calendar.getTime()));
					Future<AssignDateHotel> assignDateHotelFu=  hotelInterService.queryFuAssignDateHotel(assignDateHotelReq);
					
					days= DateUtil.daysBetween(startTime, endTime);
					Future<ResProBaseInfos> resProBaseInfosFuture = tcHotelSupplierService.queryResProBaseInfos(resId);
					Future<ImgInfoSum> imgInfoSumFuture = tcHotelSupplierService.queryImgInfoSum(resId);
					Future<List<ProfitPrice>> computeProfitByAgentFu = null;
					String supplierSource = "tc";
					computeProfitByAgentFu = holProfitService.computeProfitByAgentNum(agent, agent.getType(), supplierSource);
					
					ResProBaseInfos resProBaseInfos = resProBaseInfosFuture.get();
					ImgInfoSum imgInfoSum = imgInfoSumFuture.get();
					AssignDateHotel assignDateHotel = assignDateHotelFu.get();
					//System.out.println("TC: "+JsonUtil.toJson(assignDateHotel));
					if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
						List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
						List<ResProBaseInfo> resProBaseInfoListBeFore= resProBaseInfos.getResProBaseInfos();
						List<ImgInfo> imgInfoList= imgInfoSum.getImgInfoList();
						
						if(StringUtil.isNotNullOrEmpty(resProBaseInfoListBeFore)){
							Map<String, ImgInfo> mm = new HashMap<String, ImgInfo>();
							if(imgInfoList!=null && imgInfoList.size() > 0) {
								mm = imgInfoList.stream().collect(Collectors.toMap(ImgInfo::getResProId, a -> a,(k1,k2)->k1));
								//list2 = mm.entrySet().stream().map(et ->et.getValue()).collect(Collectors.toList());
							}
							
							List<ResProBaseInfo> resProBaseInfoList = new ArrayList<ResProBaseInfo>();
							for(ProInfoDetail proSaleKey : proInfoDetailList) {
								Integer firstPrice = 999999;
								List<ResProBaseInfo> filterList = resProBaseInfoListBeFore.stream().filter(pro -> pro.getProductUniqueId().equals(proSaleKey.getProductUniqueId())).collect(Collectors.toList());
								if(filterList != null && filterList.size() >0) {
									ResProBaseInfo pro = filterList.get(0);
									TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
									List<Integer> pppRice = new ArrayList<Integer>();
		 	            			Calendar da = Calendar.getInstance(); 
		                   			 for (int i = 0; i < days; i++) {
		                   				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
		                   				 ProSaleInfoDetail.setDistributionSalePrice(0);
		                   				 da.setTime(sdf.parse(startTime));
		                   				 da.add(Calendar.DAY_OF_MONTH, i);
		                   				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
		                				}
									TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proSaleKey.getProSaleInfoDetails();
		       			 			if(StringUtil.isNotNullOrEmpty(proSaleInfoDetails)) {
		       			 				if(proSaleInfoDetails.containsKey(DateUtil.stringToLonString(startTime))) {
		           			 				Integer firProPrice = proSaleInfoDetails.get(DateUtil.stringToLonString(startTime)).getDistributionSalePrice();
		           			 				if(StringUtil.isNotNullOrEmpty(firProPrice)) {
		           			 					if(computeProfitByAgentFu!=null) {
		           			 						List<ProfitPrice> computeProfitByAgent = computeProfitByAgentFu.get();
			           			 					if(computeProfitByAgent != null && computeProfitByAgent.size() > 0) {
			               			 					for(ProfitPrice profit : computeProfitByAgent) {
			               			 						BigDecimal lowerPrice = profit.getPriceFrom();
			               			 						BigDecimal upPrice = profit.getPriceTo();
			               			 						BigDecimal firPrice = new BigDecimal(firProPrice);
			               			 						if(lowerPrice.compareTo(firPrice) <= 0 && upPrice.compareTo(firPrice) >= 0) {
			               			 							BigDecimal rate = profit.getRate();
			               			 							rate = rate.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
			               			 							pro.setRebateRateProfit(rate);
			               			 						}
			               			 					}
			           			 					}
		           			 					}
		           			 				}
		       			 				}
		           			 			int kk = 0;
		 	    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
		 	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
		 	    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
		 	    	        					if(begincompare >= 0 && endCompare < 0){
		 	    	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
		 	    	        						Integer price= entry.getValue().getDistributionSalePrice();
		 	    	        						pppRice.add(price);
		 	    	        						if(!entry.getValue().getInventoryStats().equals(4)) {
		 	    	        							SimpleDateFormat newsdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 	    	        							String nowTime = newsdf.format(new Date());
		 	    	        							int startDate = entry.getValue().getStartDate().compareTo(nowTime);
		 	    	        							int endDate = entry.getValue().getEndDate().compareTo(nowTime);
		 	    	        							if(startDate > 0 || endDate < 0) {
		 	    	        								pro.setBookStatus(0);
		 	    	        							}
		 	    	        						}else {
		 	    	        							pro.setBookStatus(0);
		 	    	        						}
			    	        							if(days.equals(1) && (sdf.format(new Date()).equals(startTime))) {
			    	        								Date startTime1 = DateUtil.stringToSimpleDate(entry.getValue().getStartTime());
		 	    	        							Date endTime1 = DateUtil.stringToSimpleDate(entry.getValue().getEndTime());
		 	    	        							Date nowDate = new Date();
			 	    	        							if(nowDate.before(startTime1) || endTime1.before(nowDate)) {
			 	    	        								pro.setBookStatus(0);
			 	    	        							}
			    	        							}
		 	    	        						sumPrice += price;
		 	    	        						kk += 1;
		 	    	        						if(kk == 1) {
		 	    	        							firstPrice = entry.getValue().getDistributionSalePrice();
		 	    	        						}
		 	    	        					}
		 	    	        					/*if(kk == days.intValue()) {
			    	        							break;
			    	        						}*/
		 	            					}
			    	        					pro.setProSaleInfoDetailsTarget(mapPro);
			    	        					pro.setFirPrice(firstPrice);
			    	        					if(pppRice.size() == 1){
			    	        						pro.setAdvancedLimitDays(pppRice.get(0));
			    	        					}else if(pppRice.size() >= 2){
			    	        						pro.setAdvancedLimitDays(Collections.min(pppRice));
			    	        					}else {
			    	        						pro.setBookStatus(0);
			    	        					}
			    	        					if(kk < days.intValue()) {
			    	        						pro.setBookStatus(0);
			    	        					}
			    	        					if(kk != 0){
			    	        					pro.setConPrice(sumPrice/kk);
			    	        				}
			    	        				kk = 0;
			    	        				sumPrice =0;
		       			 			}else {
		       			 				pro.setBookStatus(0);
		       			 				pro.setFirPrice(firstPrice);
		       			 			}
		       			 			pro.setResProName(pro.getResProName().replaceAll("\\s*", "").replaceAll("（", "(").replaceAll("）", ")"));
		       			 			pro.setSupplierType(1);
									resProBaseInfoList.add(pro);
								}
							}
							Map<String, List<ResProBaseInfo>> proMap = resProBaseInfoList.stream().collect(Collectors.groupingBy(ResProBaseInfo::getProId));
							
							List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
							if(StringUtil.isNotNullOrEmpty(proMap)){
								for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
									ProDetails proInfoDetai=new ProDetails();
									proInfoDetai.setProId(baseList.getKey());
									proInfoDetai.setResProName(proMap.get(baseList.getKey()).get(0).getResProName());
									proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
									proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
									proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
									proInfoDetai.setBedSize(proMap.get(baseList.getKey()).get(0).getBedSize());
									List<ResProBaseInfo> valueList = baseList.getValue();
									if(valueList != null && valueList.size() > 0) {
										//valueList.sort(Comparator.comparingInt(ResProBaseInfo :: getFirPrice));
										int sum = valueList.stream().mapToInt(ResProBaseInfo :: getAdvancedLimitDays).sum();
										//int asInt = valueList.stream().mapToInt(ResProBaseInfo :: getFirPrice).min().getAsInt();
										//proInfoDetai.setMinPrice(asInt);
										proInfoDetai.setProDetailConPrice(sum/valueList.size());
									}else {
										proInfoDetai.setSaleStatus(0);
									}
									if(mm.containsKey(baseList.getKey())) {
										String imageUrl = mm.get(baseList.getKey()).getImageUrl();
										proInfoDetai.setImgUrl(imageUrl);
									}
									
									proInfoDetai.setResProBaseInfoList(valueList);
									ProInfoDetaisList.add(proInfoDetai);
								}
							}
							tcResBaseInfo.setProDetails(ProInfoDetaisList);
						}
					}else {
						logger.info("查询酒店价格库存为空, 酒店ID为: "+resId+", 返回值: "+JsonUtil.toJson(assignDateHotel));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return tcResBaseInfo;
			    //return new AsyncResult<ResBaseInfo>(tcResBaseInfo);
	}
	
	@Override
	public ResBaseInfo newQueryHolProDetail(Agent agent, Long resId, String uniProId, String startTime, String endTime) throws GSSException {
		long start = System.currentTimeMillis();
		if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }else{
        	if(StringUtil.isNullOrEmpty(agent.getType())){
        		throw new GSSException("获取某一酒店详细信息", "0102", "agentType为空");
        	}
        }
		if (StringUtil.isNullOrEmpty(resId)) {
            logger.error("酒店id为空");
            throw new GSSException("获取某一酒店详细信息", "0100", "酒店id为空");
        }
		if (StringUtil.isNullOrEmpty(startTime)) {
			logger.error("开始日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "开始日期为空");
        }
        if (StringUtil.isNullOrEmpty(endTime)) {
            logger.error("结束日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
        }
        ResBaseInfo tcResBaseInfo = null;
        Integer sumPrice=0;
        Integer days = 0;
		try {
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setProductUniqueId(uniProId);
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setStartTime(startTime);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(sdf.parse(endTime));
	        calendar.add(Calendar.DAY_OF_MONTH, -1);
			assignDateHotelReq.setEndTime(sdf.format(calendar.getTime()));
			Future<AssignDateHotel> assignDateHotelFu=  hotelInterService.queryFuAssignDateHotel(assignDateHotelReq);
			
			days= DateUtil.daysBetween(startTime, endTime);
		//	Future<AssignDateHotel> assignDateHotelFuture = tcHotelSupplierService.queryListById(resId, AssignDateHotel.class);
			Future<ResProBaseInfos> resProBaseInfosFuture = tcHotelSupplierService.queryResProBaseInfos(resId);
			Future<ResBaseInfo> resBaseInfoFuture = tcHotelSupplierService.queryResBaseInfo(resId);
			Future<List<ProfitPrice>> computeProfitByAgentFu = null;
			String supplierSource = "tc";
			computeProfitByAgentFu = holProfitService.computeProfitByAgentNum(agent, agent.getType(),supplierSource);
			
			tcResBaseInfo = resBaseInfoFuture.get();
			ResProBaseInfos resProBaseInfos = resProBaseInfosFuture.get();
			AssignDateHotel assignDateHotel = assignDateHotelFu.get();
			
			if(StringUtil.isNotNullOrEmpty(assignDateHotel) && StringUtil.isNotNullOrEmpty(assignDateHotel.getProInfoDetailList())) {
				Integer firstPrice = 999999;
				List<ProInfoDetail> proInfoDetailList= assignDateHotel.getProInfoDetailList();
				List<ResProBaseInfo> resProBaseInfoListBeFore= resProBaseInfos.getResProBaseInfos();
				
				if(StringUtil.isNotNullOrEmpty(tcResBaseInfo) && StringUtil.isNotNullOrEmpty(resProBaseInfoListBeFore)){
					
					List<ResProBaseInfo> resProBaseInfoList = new ArrayList<ResProBaseInfo>();
					for(ProInfoDetail proSaleKey : proInfoDetailList) {
						BigDecimal totalProfitPrice = new BigDecimal(0);
						List<ResProBaseInfo> filterList = resProBaseInfoListBeFore.stream().filter(pro -> pro.getProductUniqueId().equals(proSaleKey.getProductUniqueId())).collect(Collectors.toList());
						if(filterList != null && filterList.size() >0) {
							ResProBaseInfo pro = filterList.get(0);
							TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
							List<Integer> pppRice = new ArrayList<Integer>();
 	            			Calendar da = Calendar.getInstance(); 
                   			 for (int i = 0; i < days; i++) {
                   				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
                   				 ProSaleInfoDetail.setDistributionSalePrice(0);
                   				 da.setTime(sdf.parse(startTime));
                   				 da.add(Calendar.DAY_OF_MONTH, i);
                   				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
                				}
							TreeMap<String, ProSaleInfoDetail> proSaleInfoDetails = proSaleKey.getProSaleInfoDetails();
       			 			if(StringUtil.isNotNullOrEmpty(proSaleInfoDetails)) {
       			 			List<ProfitPrice> computeProfitByAgent = computeProfitByAgentFu.get();
       			 				if(proSaleInfoDetails.containsKey(DateUtil.stringToLonString(startTime))) {
           			 				Integer firProPrice = proSaleInfoDetails.get(DateUtil.stringToLonString(startTime)).getDistributionSalePrice();
           			 				if(StringUtil.isNotNullOrEmpty(firProPrice)) {
           			 					if(computeProfitByAgentFu!=null) {
	           			 					if(computeProfitByAgent != null && computeProfitByAgent.size() > 0) {
	               			 					for(ProfitPrice profit : computeProfitByAgent) {
	               			 						BigDecimal lowerPrice = profit.getPriceFrom();
	               			 						BigDecimal upPrice = profit.getPriceTo();
	               			 						BigDecimal firPrice = new BigDecimal(firProPrice);
	               			 						if(lowerPrice.compareTo(firPrice) <= 0 && upPrice.compareTo(firPrice) >= 0) {
	               			 							BigDecimal rate = profit.getRate();
	               			 							rate = rate.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
	               			 							pro.setRebateRateProfit(rate);
	               			 						}
	               			 					}
	           			 					}
           			 					}
           			 				}
       			 				}
           			 			int kk = 0;
 	    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
 	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
 	    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
 	    	        					if(begincompare >= 0 && endCompare < 0){
 	    	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
 	    	        						Integer price= entry.getValue().getDistributionSalePrice();
 	    	        						BigDecimal bigPrice = new BigDecimal(price);
 	    	        						if(computeProfitByAgent != null && computeProfitByAgent.size() > 0) {
	               			 					for(ProfitPrice profit : computeProfitByAgent) {
	               			 						BigDecimal lowerPrice = profit.getPriceFrom();
	               			 						BigDecimal upPrice = profit.getPriceTo();
	               			 						if(lowerPrice.compareTo(bigPrice) <= 0 && upPrice.compareTo(bigPrice) >= 0) {
	               			 							BigDecimal rate = profit.getRate();
	               			 							rate = rate.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
	               			 							BigDecimal profit11 = bigPrice.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
	               			 							totalProfitPrice = totalProfitPrice.add(profit11);
	               			 						}
	               			 					}
	           			 					}
 	    	        						
 	    	        						pppRice.add(price);
 	    	        						if(!entry.getValue().getInventoryStats().equals(4)) {
 	    	        							SimpleDateFormat newsdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 	    	        							String nowTime = newsdf.format(new Date());
 	    	        							int startDate = entry.getValue().getStartDate().compareTo(nowTime);
 	    	        							int endDate = entry.getValue().getEndDate().compareTo(nowTime);
 	    	        							if(startDate > 0 || endDate < 0) {
 	    	        								pro.setBookStatus(0);
 	    	        							}
 	    	        						}else {
 	    	        							pro.setBookStatus(0);
 	    	        						}
	    	        							if(days.equals(1) && (sdf.format(new Date()).equals(startTime))) {
	    	        								Date startTime1 = DateUtil.stringToSimpleDate(entry.getValue().getStartTime());
 	    	        							Date endTime1 = DateUtil.stringToSimpleDate(entry.getValue().getEndTime());
 	    	        							Date nowDate = new Date();
 	    	        							if(nowDate.before(startTime1) || endTime1.before(nowDate)) {
 	    	        								pro.setBookStatus(0);
 	    	        							}
	    	        							}
 	    	        						sumPrice += price;
 	    	        						kk += 1;
 	    	        						if(kk == 1) {
 	    	        							firstPrice = entry.getValue().getDistributionSalePrice();
 	    	        						}
 	    	        					}
 	    	        					/*if(kk == days.intValue()) {
	    	        							break;
	    	        						}*/
 	            					}
	    	        					pro.setProSaleInfoDetailsTarget(mapPro);
	    	        					pro.setFirPrice(firstPrice);
	    	        					if(pppRice.size() == 1){
	    	        						pro.setAdvancedLimitDays(pppRice.get(0));
	    	        					}else if(pppRice.size() >= 2){
	    	        						pro.setAdvancedLimitDays(Collections.min(pppRice));
	    	        					}else {
	    	        						pro.setBookStatus(0);
	    	        					}
	    	        					if(kk < days.intValue()) {
	    	        						pro.setBookStatus(0);
	    	        					}
	    	        					if(kk != 0){
	    	        					pro.setConPrice(sumPrice/kk);
	    	        				}
	    	        				pro.setUserDays(days);
	    	    					pro.setUserSumPrice(sumPrice);
	    	        				kk = 0;
	    	        				sumPrice =0;
       			 			}else {
       			 				pro.setBookStatus(0);
       			 				pro.setFirPrice(firstPrice);
       			 			}
       			 			pro.setTotalRebateRateProfit(totalProfitPrice);
							resProBaseInfoList.add(pro);
						}
					}
					Map<String, List<ResProBaseInfo>> proMap = resProBaseInfoList.stream().collect(Collectors.groupingBy(ResProBaseInfo::getProId));
					
					List<ProDetails> ProInfoDetaisList=new ArrayList<ProDetails>();
					if(StringUtil.isNotNullOrEmpty(proMap)){
						for (Map.Entry<String, List<ResProBaseInfo>> baseList : proMap.entrySet()) {
							ProDetails proInfoDetai=new ProDetails();
							proInfoDetai.setProId(baseList.getKey());
							proInfoDetai.setResProName(proMap.get(baseList.getKey()).get(0).getResProName());
							proInfoDetai.setRoomSize(proMap.get(baseList.getKey()).get(0).getRoomSize());
							proInfoDetai.setRoomFloor(proMap.get(baseList.getKey()).get(0).getRoomFloor());
							proInfoDetai.setRoomFacilities(proMap.get(baseList.getKey()).get(0).getRoomFacilities());
							proInfoDetai.setBedSize(proMap.get(baseList.getKey()).get(0).getBedSize());
							List<ResProBaseInfo> valueList = baseList.getValue();
							if(valueList != null && valueList.size() > 0) {
								int sum = valueList.stream().mapToInt(ResProBaseInfo :: getAdvancedLimitDays).sum();
								proInfoDetai.setProDetailConPrice(sum/valueList.size());
							}else {
								proInfoDetai.setSaleStatus(0);
							}
							proInfoDetai.setResProBaseInfoList(valueList);
							ProInfoDetaisList.add(proInfoDetai);
						}
					}
					tcResBaseInfo.setProDetails(ProInfoDetaisList);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		long end = System.currentTimeMillis();  
	    System.out.println("完成任务newQueryHotelDetail，耗时：" + (end - start) + "毫秒"); 
		return tcResBaseInfo;
		
	}


	@Override
	@Async
	@TrackTime(param = "查询BYQ酒店房型耗时")
	public Future<ResBaseInfo> queryBQYHotelListForAsync(Agent agent, Long bqyHotelId, String checkinDate,
			String checkoutDate, String cityCode) throws Exception {
	    Future<ResBaseInfo> hotelDetail = bqyHotelSupplierService.singleHotelDetail(agent,String.valueOf(bqyHotelId), checkinDate, checkoutDate, cityCode);
		return new AsyncResult<ResBaseInfo>(hotelDetail.get());
	}

	@Override
	@Async
	@HolAnnotation
	@TrackTime(param = "查询TC酒店房型耗时")
	public Future<ResBaseInfo> queryTCHelListForAsync(Agent agent, Long tcHotelId, String checkinDate,
			String checkoutDate) throws Exception{
		//Future<ResBaseInfo> hotelDetailFu = queryProDetail(agent, tcHotelId, checkinDate, checkoutDate);
		ResBaseInfo queryProDetail = queryProDetail(agent, tcHotelId, checkinDate, checkoutDate);
		return new AsyncResult<ResBaseInfo>(queryProDetail);
	}

	@Override
	@Async
	public Future<ResBaseInfo> queryTCHolForAsyncBack(Agent agent, Long tcHotelId) {
		ResBaseInfo hotelDetail = tcHotelInterService.updateSingleResDetail(agent, String.valueOf(tcHotelId));
		return new AsyncResult<ResBaseInfo>(hotelDetail);
	}
	
	
	@Override
	public void pullBQYHotelInfo() {
		//将MongoDB中数据清空
		bqyHotelInterService.deleteMongoDBData();
		//拉取城市信息
		//bqyHotelInterService.pullCityDetail();
		//拉取酒店ID并存储MongoDB
		//listHotelId();
		
		List<HotelId> hotelIdList = null;
		//获取ID数量
		//long totalHotelIdNum = hotelIdList.size();
		long totalHotelIdNum = mongoTemplate1.count(new Query(), HotelId.class);
		long count = 1;
		if ((totalHotelIdNum / PAGE_SIZE) > 1) {
			count = totalHotelIdNum % PAGE_SIZE == 0 ? totalHotelIdNum / PAGE_SIZE : totalHotelIdNum / PAGE_SIZE + 1;
		}
		for (int i = 0; i < count; i++) {
			int start = i * PAGE_SIZE;
			//long lastIndex = (start + PAGE_SIZE) > totalHotelIdNum ? totalHotelIdNum : start + PAGE_SIZE;
			Query query = new Query().skip(start).limit(PAGE_SIZE);
			hotelIdList = mongoTemplate1.find(query, HotelId.class);
			//开启线程拉去酒店数量
			bqyHotelInterService.pullHotelInfoByIdList(hotelIdList);
		}
	}
	
	private void listHotelId() {
		int i = 1;
		Map<String, Object> map = new HashMap<>();
		//long totalCount = bqyHotelInterService.queryHotelIdCount(map);
		//long pageNo = totalCount % PAGE_SIZE == 0 ? totalCount/PAGE_SIZE : (totalCount/PAGE_SIZE)+1;
		map.put("PageSize", PAGE_SIZE);
		while (true) {
			map.put("PageNo", i);
			List<HotelId> hotelIdList = bqyHotelInterService.queryHotelIdList(map);
			if (null != hotelIdList && hotelIdList.size() > 0) {
				i++;
				mongoTemplate1.insert(hotelIdList, HotelId.class);
				continue;
			}
			System.out.println("i最后的值为:" + i);
			break;
		}
	}
	
	/**
	 * 异步查询日志
	 * @param hotelOrderNo
	 * @return
	 */
	@Async
	Future<List<LogRecord>> queryLogList(String hotelOrderNo){
		LogRecordQuery query = new LogRecordQuery.Builder().bizCode("HOL-Order").bizNo(hotelOrderNo).build();
		 List<LogRecord> logList = logService.query(query);
		 return new AsyncResult<List<LogRecord>>(logList);
	}

	@Override
	public Map<String, Object> queryBackOrderDetail(Agent agent,String hotelOrderNo, Long saleOrderNo) throws GSSException{
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			 
			OrderDetailInfoReq orderDetailInfoReq=new OrderDetailInfoReq();
			orderDetailInfoReq.setOrderId(hotelOrderNo);
			Future<OrderInfomationDetail> futureorderDetailInfo = tcHotelOrderService.futureorderDetailInfo(agent, orderDetailInfoReq);
			Future<HotelOrder> futureOrderDetail = tcHotelOrderService.getFutureOrderDetail(agent, hotelOrderNo);
			Future<List<LogRecord>> list = queryLogList(hotelOrderNo);
			
			
			HotelOrder hotelOrder = futureOrderDetail.get();
			OrderInfomationDetail orderInfomationDetail = futureorderDetailInfo.get();
			List<LogRecord> logList = list.get();
			
			if(StringUtil.isNotNullOrEmpty(hotelOrder)) {
				map.put("hotelOrder", hotelOrder);
			}
			if(StringUtil.isNotNullOrEmpty(hotelOrder)) {
				map.put("orderInfomationDetail", orderInfomationDetail);
			}
			if(StringUtil.isNotNullOrEmpty(hotelOrder)) {
				map.put("logList", logList);
			}
			
			return map;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
}	

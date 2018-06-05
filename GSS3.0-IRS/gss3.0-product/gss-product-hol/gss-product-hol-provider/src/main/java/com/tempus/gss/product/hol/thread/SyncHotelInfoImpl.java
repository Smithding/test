package com.tempus.gss.product.hol.thread;

import java.math.BigDecimal;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.request.tc.SingleHotelDetailReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfoSum;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.product.hol.api.service.IHolProfitService;
import com.tempus.gss.product.hol.api.syn.ISyncHotelInfo;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.service.TCHotelSupplierServiceImpl;
import com.tempus.gss.vo.Agent;

@Service
public class SyncHotelInfoImpl implements ISyncHotelInfo {
	protected final transient Logger log = LogManager.getLogger(TCHotelSupplierServiceImpl.class);
	@Autowired
	private ITCHotelSupplierService tcHotelSupplierService;
	
	@Autowired
	private IBQYHotelSupplierService bqyHotelSupplierService;
	
	@Autowired
	private IBQYHotelInterService bqyHotelService;
	
	@Autowired
	private MongoTemplate mongoTemplate1;
	
	@Reference
	IHolProfitService holProfitService;
	
	@Reference
	ITCHotelInterService hotelInterService;
	
	@Value("${bqy.count}")
	private int PAGE_SIZE;			//查询id数量
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException {
		//Future<TCResponse<ResBaseInfo>> queryHotelList = tcHotelSupplierService.queryHotelList(agent, hotelSearchReq);
		//Future<TCResponse<ResBaseInfo>> queryHotelList2 = iBQYHotelSupplierService.queryHotelList(hotelSearchReq);
		TCResponse<ResBaseInfo> tcResponse = null;
		//TCResponse<ResBaseInfo> bqyResponse = null;
		/*try {
			//Future<TCResponse<ResBaseInfo>> future = RpcContext.getContext().getFuture();
			
			while(true) {
				if(queryHotelList.isDone()) {
					if(StringUtil.isNotNullOrEmpty(queryHotelList)) {
						tcResponse = queryHotelList.get();
					}
					//bqyResponse = queryHotelList2.get();
					System.out.println("bqy Size: "+bqyResponse.getResponseResult().size());
					System.out.println(JsonUtil.toJson(bqyResponse.getResponseResult()));
					for(ResBaseInfo rs : bqyResponse.getResponseResult()) {
						System.out.println("bqy: "+rs.getResName());
					}
					break;
				}
			}
			if(StringUtil.isNullOrEmpty(hotelSearchReq.getAirRailWay())) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		
		return tcResponse;
		//return null;
	}

	@Override
	public TCResponse<ResBaseInfo> queryHotelListForBack(Agent agent, HotelListSearchReq hotelSearchReq)
			throws GSSException {
		Future<TCResponse<ResBaseInfo>> queryHotelList = tcHotelSupplierService.queryHotelListForBack(agent, hotelSearchReq);
		Future<TCResponse<ResBaseInfo>> queryHotelList2 = bqyHotelSupplierService.queryHotelList(hotelSearchReq);
		TCResponse<ResBaseInfo> tcResponse = null;
		TCResponse<ResBaseInfo> bqyResponse = null;
		try {
			while(true) {
				if(queryHotelList.isDone() && queryHotelList2.isDone()) {
					tcResponse = queryHotelList.get();
					bqyResponse = queryHotelList2.get();
					break;
				}
			}
			if(StringUtil.isNullOrEmpty(hotelSearchReq.getAirRailWay())) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tcResponse;
	}

	@Override
	public void pullBQYHotelInfo() {
		//将MongoDB中数据清空
		//bqyHotelService.deleteMongoDBData();
		//拉取城市信息
		//bqyHotelService.pullCityDetail();
		//拉取酒店ID并存储MongoDB
		//获取BQY酒店ID
		//List<HotelId> hotelIdList = bqyHotelService.queryHotelIdList();
		//将获取的酒店ID保存到mongoDB中
		//mongoTemplate1.insert(hotelIdList, HotelId.class);
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
			bqyHotelService.pullHotelInfoByIdList(hotelIdList);
		}
	}

	@Override
	public ResBaseInfo queryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException {
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }else{
        	if(StringUtil.isNullOrEmpty(agent.getType())){
        		throw new GSSException("获取某一酒店详细信息", "0102", "agentType为空");
        	}
        }
		if (StringUtil.isNullOrEmpty(resId)) {
            log.error("酒店id为空");
            throw new GSSException("获取某一酒店详细信息", "0100", "酒店id为空");
        }
		if (StringUtil.isNullOrEmpty(startTime)) {
			log.error("开始日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "开始日期为空");
        }
        if (StringUtil.isNullOrEmpty(endTime)) {
            log.error("结束日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
        }
        ResBaseInfo tcResBaseInfo = null;
        Integer sumPrice=0;
        Integer days = 0;
		try {
			days= DateUtil.daysBetween(startTime, endTime);
			Future<AssignDateHotel> assignDateHotelFuture = tcHotelSupplierService.queryListById(resId, AssignDateHotel.class);
			Future<ResProBaseInfos> resProBaseInfosFuture = tcHotelSupplierService.queryListById(resId, ResProBaseInfos.class);
			Future<ResBaseInfo> resBaseInfoFuture = tcHotelSupplierService.queryListById(resId, ResBaseInfo.class);
			Future<ImgInfoSum> imgInfoSumFuture = tcHotelSupplierService.queryListById(resId, ImgInfoSum.class);
			while(true) {
				if(assignDateHotelFuture.isDone() && resProBaseInfosFuture.isDone() && resBaseInfoFuture.isDone() && imgInfoSumFuture.isDone()) {
					break;
				}
			}
			AssignDateHotel assignDateHotel = assignDateHotelFuture.get();
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
			                   			 				BigDecimal profitPrice = holProfitService.computeTcProfitPrice(agent, firProPrice, agent.getType());
			         	                				if(StringUtil.isNotNullOrEmpty(profitPrice)){
			         	                					pro.setRebateRateProfit(profitPrice);
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
		         	    	        							int startDate = DateUtil.stringToSimpleString(entry.getValue().getStartDate()).compareTo(startTime);
		         	    	        							int endDate = DateUtil.stringToSimpleString(entry.getValue().getEndDate()).compareTo(endTime);
		         	    	        							if(startDate > 0 || endDate < 0) {
		         	    	        								pro.setBookStatus(0);
		         	    	        							}
		         	    	        						}else {
		         	    	        							pro.setBookStatus(0);
		         	    	        						}
	         	    	        							if(days.equals(1) && sdf.format(new Date()).equals(startTime)) {
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
		         	    	        					if(kk == days.intValue()) {
	         	    	        							break;
	         	    	        						}
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
			}else {
				return null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tcResBaseInfo;
	}

	@Override
	public <T> T queryDetailById(Long id, Class<T> clazz) throws GSSException {
		Future<T> imgInfoSumFuture = tcHotelSupplierService.queryListById(id, clazz);
		T t = null;
		while(true) {
			if(imgInfoSumFuture.isDone()) {
				break;
			}
		}
		try {
			t = imgInfoSumFuture.get();
			return t;
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
}	

package com.tempus.gss.product.hol.service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.contant.OperateEnum;
import com.tempus.gss.product.hol.api.contant.QueryProperty;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.HotelName;
import com.tempus.gss.product.hol.api.entity.LastestResRecord;
import com.tempus.gss.product.hol.api.entity.LogRecordHol;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.request.tc.AssignDateHotelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.SingleHotelDetailReq;
import com.tempus.gss.product.hol.api.entity.response.ResIdList;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.BusinessSection;
import com.tempus.gss.product.hol.api.entity.response.tc.CityAreaScenic;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ImgInfoSum;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResTrafficInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.product.hol.api.service.FutureResult;
import com.tempus.gss.product.hol.api.service.IHolProfitService;
import com.tempus.gss.product.hol.api.syn.IHolMongoQuery;
import com.tempus.gss.product.hol.api.syn.ITCHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.product.hol.api.util.DateUtil;
import com.tempus.gss.product.hol.api.util.GPSUtil;
import com.tempus.gss.product.hol.api.util.Tool;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;

import httl.util.StringUtils;
/**
 * 同程酒店查询数据库接口实现
 * @author kai.yang
 *
 */
@Service
public class TCHotelSupplierServiceImpl implements ITCHotelSupplierService{
	protected final transient Logger log = LogManager.getLogger(TCHotelSupplierServiceImpl.class);
	
	@Autowired
	MongoTemplate mongoTemplate1;
	
	@Reference
	ITCHotelInterService hotel;
	
	@Reference
    IHolMongoQuery holMongoQuery;
	
	@Reference
	IHolProfitService holProfitService;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void deleteProInfoDetailByDate(String strDate) {
		log.info("每天删除当天日期前一天的酒店价格库存信息开始");
		Query query = Query.query(Criteria.where("startDate").lte(strDate));
		mongoTemplate1.remove(query, ProInfoDetail.class);
		log.info("每天晚上十一点删除结束");
	}
	
	/**
	 * 根据酒店id查找具体酒店信息
	 */
	@Cacheable(value = "ResBaseInfo", key = "#id",unless="")
	@Override
	public ResBaseInfo queryListByResId(Agent agent, Long id) {
		
		ResBaseInfo resDetail= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)), ResBaseInfo.class);
		if(StringUtil.isNotNullOrEmpty(resDetail)){
			List<String> strs  = Tool.intToTwoPower(resDetail.getCreditCards().intValue());
        	resDetail.setCreditCardsTarget(strs);
		}
		return resDetail;
	}
	
	@Override
	public <T> T queryListByProId(String id, Class<T> clazz) {
		T t= mongoTemplate1.findOne(new Query(Criteria.where("proId").is(id)),clazz);
		
		return t;
	}
	@Override
	public <T> T queryListByProductUniqueId(String id, Class<T> clazz) {
		
		T t= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),clazz);
		
		return t;
	}
	
	@Override
	public <T> List<T> queryAllNotNull(Class<T> clazz) {
		List<T> res = mongoTemplate1.find(new Query(Criteria.where("_id").ne("").ne(null)),clazz);
		return res;
	}
	

	@Override
	public List<ResInfoList> queryResInfoListByVague(String cityName, String vague, String startTime, String endTime) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("cityName").is(cityName), Criteria.where("resName").regex(".*?\\" +vague+ ".*"));
		List<ResInfoList> res = mongoTemplate1.find(new Query(criatira), ResInfoList.class);
		return res;
	}
	
	@Cacheable(value = "CityAreaScenic", key = "#cityName",unless="")
	@Override
	public CityAreaScenic queryResInfoListByCity(Agent agent, String cityName) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取城市景点列表", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(cityName)) {
            log.error("cityName入参为空");
            throw new GSSException("获取城市景点列表", "0102", "cityName入参为空");
        }
		
		CityAreaScenic result = mongoTemplate1.findOne(new Query(Criteria.where("cityName").is(cityName)),CityAreaScenic.class);
		if(StringUtil.isNotNullOrEmpty(result)){
			return result;
		}else{
			List<CityAreaScenic> cityAreaScenicList=new ArrayList<CityAreaScenic>();
			CityAreaScenic cityAreaScenic=new CityAreaScenic();
			Set<String> cityArea= new HashSet<String>();//区域
			Set<String> cityScenic=new HashSet<String>();//热门景点 
			Set<String> cityAirRailWay=new HashSet<String>();//城市的机场火车站
			Set<String> citySubWay=new HashSet<String>();//城市的地铁站
			Set<String> cityBusinessSectionInfo=new HashSet<String>();//热门商圈
			
			List<ResBaseInfo> res= mongoTemplate1.find(new Query(Criteria.where("cityName").is(cityName)),ResBaseInfo.class);
			for(ResBaseInfo rs : res){
				cityArea.add(rs.getSectionName());
				for(BusinessSection bus : rs.getBusinessSectionInfoList()){
					cityBusinessSectionInfo.add(bus.getBusinessSectionName().trim());
				}
				for(ResTrafficInfo traffic : rs.getHotelTrafficInfo()){
					if(traffic.getStartLocation().contains("景点")){
						cityScenic.add(traffic.getDescription().trim());
					}
					if(traffic.getStartLocation().contains("机场") || traffic.getStartLocation().contains("火车站")){
						cityAirRailWay.add(traffic.getDescription().trim());
					}
					if(traffic.getStartLocation().contains("地铁站")){
						citySubWay.add(traffic.getDescription().trim());
					}
				}
			}
			cityAreaScenic.setCityName(cityName);
			cityAreaScenic.setCityArea(cityArea);
			cityAreaScenic.setCityScenic(cityScenic);
			cityAreaScenic.setCityAirRailWay(cityAirRailWay);
			cityAreaScenic.setCitySubWay(citySubWay);
			cityAreaScenic.setCityBusinessSectionInfo(cityBusinessSectionInfo);
			cityAreaScenicList.add(cityAreaScenic);
			//mongoTemplate1.insert(cityAreaScenic, "cityAreaScenic");
			mongoTemplate1.insert(cityAreaScenicList, CityAreaScenic.class);
			//mongoTemplate1.save(cityAreaScenic, "cityAreaScenic");
			
			return cityAreaScenic;
		}
	}

	@Override
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException{
		//System.out.println("开始做任务一");  
       // long start = System.currentTimeMillis();
       // System.out.println("f1 : " + Thread.currentThread().getName() + "   " + UUID.randomUUID().toString());
		log.info("查询酒店列表开始");
        if (StringUtil.isNullOrEmpty(hotelSearchReq)) {
            log.error("hotelSearchReq查询条件为空");
            throw new GSSException("获取酒店列表", "0101", "hotelSearchReq查询条件为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取酒店列表", "0102", "agent对象为空");
        } else {
            if(StringUtil.isNullOrEmpty(agent.getType())){
            	throw new GSSException("获取酒店列表", "0102", "agentType为空");
            }
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode())) {
            log.error("城市编号为空");
            throw new GSSException("获取酒店列表", "0103", "城市编号为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getPageCount())) {
            log.error("页码为空");
            throw new GSSException("获取酒店列表", "0107", "页码为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getRowCount())) {
            log.error("每页条数为空");
            throw new GSSException("获取酒店列表", "0108", "每页条数为空");
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getSaleStatus())) {
            log.error("可售状态入参为空");
            throw new GSSException("获取酒店列表", "0118", "可售状态入参为空");
        }
        TCResponse<ResBaseInfo> response = new TCResponse<ResBaseInfo>();
        List<ResBaseInfo> res=null;
		Query query=new Query();
        Criteria criatira = new Criteria();
        
        Calendar date = Calendar.getInstance();  
        Calendar dateAdd = Calendar.getInstance();
        dateAdd.add(Calendar.MONTH, 2);
        dateAdd.add(Calendar.DAY_OF_MONTH, -1);
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getBegin())) {
        	hotelSearchReq.setBegin(sdf.format(date.getTime()));
        }
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getEnd())) {
        	Calendar datetomm = Calendar.getInstance();
        	datetomm.add(Calendar.DAY_OF_MONTH, 1);
        	hotelSearchReq.setEnd(sdf.format(datetomm.getTime()));
        }
        Integer sumPrice=0;
        Integer days=0;
        int skip= (hotelSearchReq.getPageCount()-1)* (hotelSearchReq.getRowCount());
  		query.skip(skip);
  		query.limit(hotelSearchReq.getRowCount());
  		//排序
  		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getOrderBy())){
  			if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getOrderBy().get(0))){
  				List<Order> orders = new ArrayList<Order>();
	  			for(String ss : hotelSearchReq.getOrderBy()){
	  				String[] arrs = ss.split("_");
	  				if(!arrs[0].equals("resGradeId")){
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
        try {
 			days= DateUtil.daysBetween(hotelSearchReq.getBegin(), hotelSearchReq.getEnd());
 			int beginResult= hotelSearchReq.getBegin().compareTo(sdf.format(date.getTime()));
 			int endResult= hotelSearchReq.getEnd().compareTo(sdf.format(dateAdd.getTime()));
 			if(beginResult >= 0 && endResult <= 0){
 				if(StringUtils.isNotEmpty(hotelSearchReq.getCityCode())){
 					criatira.and("cityName").regex("^.*"+hotelSearchReq.getCityCode()+".*$");
 					//criatira.and("cityName").is(hotelSearchReq.getCityCode());
 					//criatira.orOperator(Criteria.where("cityName").is(hotelSearchReq.getCityCode()), Criteria.where("sectionName").regex(".*?\\" +hotelSearchReq.getCityCode()+ ".*"));
 				}
 				if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSaleStatus())){
 					criatira.and("saleStatus").is(hotelSearchReq.getSaleStatus());
 				}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getKeyword())){
 					String keyword = hotelSearchReq.getKeyword().trim();
 					String escapeHtml = StringEscapeUtils.unescapeHtml(keyword);
 					String[] fbsArr = { "(", ")" };  //  "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
 			        	 for (String key : fbsArr) { 
 			        		 if(escapeHtml.contains(key)){
 			        			escapeHtml = escapeHtml.replace(key, "\\" + key);
 			        		 }
 			        	 }
 					criatira.and("resName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
 				}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getSectionName())){
 					criatira.and("sectionName").is(hotelSearchReq.getSectionName());
 				}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getBusinessSectionInfo())){
 					Criteria criatiraBusinessSectionInfo = new Criteria();
 					criatiraBusinessSectionInfo.and("businessSectionName").is(hotelSearchReq.getBusinessSectionInfo());
 					criatira.and("businessSectionInfoList").elemMatch(criatiraBusinessSectionInfo);
 				}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getCityScenic())){
 						Criteria criatiraCityScenic = new Criteria();
 						criatiraCityScenic.and("description").is(hotelSearchReq.getCityScenic());
 	 					criatira.and("hotelTrafficInfo").elemMatch(criatiraCityScenic);
 	 			}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getAirRailWay())){
 	 					Criteria criatiraAirRailWay = new Criteria();
 	 					criatiraAirRailWay.and("description").is(hotelSearchReq.getAirRailWay());
 	 					criatira.and("hotelTrafficInfo").elemMatch(criatiraAirRailWay);
 	 			}
 				if(StringUtils.isNotEmpty(hotelSearchReq.getSubWay())){
 	 					Criteria criatiraSubWay = new Criteria();
 	 					criatiraSubWay.and("description").is(hotelSearchReq.getSubWay());
 	 					criatira.and("hotelTrafficInfo").elemMatch(criatiraSubWay);
 	 			}
 				if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition())){
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getPriceFromToList())){
 	 					criatira.and("minPrice").gte(hotelSearchReq.getSearchCondition().getPriceFromToList().get(0).getPriceFrom().intValue()).lte(hotelSearchReq.getSearchCondition().getPriceFromToList().get(0).getPriceTo().intValue());
 	 				}
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getHotelLevelList())){
 						List<String> hlevle = hotelSearchReq.getSearchCondition().getHotelLevelList(); 
 	 	 				if(!(hlevle.isEmpty()) && !(hlevle.get(0).isEmpty())){ 	 					
 	 	 					criatira.and("resGradeId").in(Arrays.asList(hlevle.get(0).split(",")));
 	 	 				}
 					}
 	 				if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getBrandList())){
 	 					criatira.and("brandInfo.resBrandName").in(hotelSearchReq.getSearchCondition().getBrandList());
 	 				}
 				}
 				criatira.and("resCommonPrice").ne(999999);
 				log.info("酒店查询条件："+JsonUtil.toJson(criatira));
 				res = mongoTemplate1.find(query.addCriteria(criatira), ResBaseInfo.class);
 	        }else{
 	        	log.error("时间选择不对");
 	        	throw new GSSException("获取酒店列表", "0999", "时间选择不对,只能选择即日起两个月之内的日期");
 	        }
 		
        /*if(StringUtil.isNotNullOrEmpty(res)){
        	for(ResBaseInfo rs: res){
            	//Map<String, List<ResProBaseInfo>> proMap= rs.getProMap();
            	List<ProDetails> lii = rs.getProDetails();
            		if(StringUtil.isNotNullOrEmpty(lii)){
            			Iterator<ProDetails> iterlii = lii.iterator();
            			//for(ProDetails ppp : lii){
            			while(iterlii.hasNext()){
            				ProDetails ppp= iterlii.next();
            				List<ResProBaseInfo> p = ppp.getResProBaseInfoList();
            				if(StringUtil.isNotNullOrEmpty(p)){
            					Iterator<ResProBaseInfo> iter = p.iterator();
                        		//for(ResProBaseInfo pro : p){
                				while(iter.hasNext()){
                					ResProBaseInfo pro = iter.next();
                        			ProInfoDetail proInfoDetail=queryListByProductUniqueId(pro.getProductUniqueId().longValue(), ProInfoDetail.class);
                        			TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
                        			Calendar da = Calendar.getInstance(); 
                        			 for (int i = 0; i < days; i++) {
                        				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
                        				 ProSaleInfoDetail.setDistributionSalePrice(0);
                        				 ProSaleInfoDetail.setInventoryStats(4);
                        				 ProSaleInfoDetail.setInventoryRemainder(0);
                        				 ProSaleInfoDetail.setOpeningSale(false);
                        				 da.setTime(sdf.parse(hotelSearchReq.getBegin()));
                        				 da.add(Calendar.DAY_OF_MONTH, i);
                        				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
                     				}
                        			//List<Integer> proInventory = new ArrayList<Integer>();
                        			//List<Integer> guaranteeType = new ArrayList<Integer>();
                        			if(proInfoDetail != null && proInfoDetail.getProSaleInfoDetails() == null){
                        				 iter.remove();
                        				 if(p.size() == 0){
                        					 iterlii.remove();	
                        				 }
                        			}
                            		if(StringUtil.isNotNullOrEmpty(proInfoDetail) && StringUtil.isNotNullOrEmpty(proInfoDetail.getProSaleInfoDetails())){
                            			if(!proInfoDetail.getProSaleInfoDetails().containsKey(DateUtil.stringToLonString(hotelSearchReq.getBegin()))){
                            				 iter.remove();
                            				 if(p.size() == 0){
                            					 iterlii.remove();
                            				 }
                            			}
                            			if(proInfoDetail.getProSaleInfoDetails().containsKey(DateUtil.stringToLonString(hotelSearchReq.getBegin()))){
                            				Integer firProPrice = proInfoDetail.getProSaleInfoDetails().get(DateUtil.stringToLonString(hotelSearchReq.getBegin())).getDistributionSalePrice();
                            				
                            				BigDecimal profitPrice = holProfitService.computeTcProfitPrice(agent, firProPrice, agent.getType());
                            				if(StringUtil.isNotNullOrEmpty(profitPrice)){
                            					pro.setRebateRateProfit(profitPrice);
                            				}
                            			}
                            				int kk = 0;
                    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
                    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(hotelSearchReq.getBegin());
                    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(hotelSearchReq.getEnd());
                    	        				if(begincompare >= 0 && endCompare < 0){
                	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
                	        						Integer price= entry.getValue().getDistributionSalePrice();
                	        						sumPrice += price;
                	        						kk += 1;
                            					}
                            				}
                    	        			if(mapPro.size() >= 1){
            	    	        				pro.setProSaleInfoDetailsTarget(mapPro);
            	    	        				ProSaleInfoDetail proSaleInfoDetail = mapPro.get(hotelSearchReq.getBegin());
												if(StringUtil.isNotNullOrEmpty(proSaleInfoDetail)){
            	    	        					pro.setFirPrice(proSaleInfoDetail.getDistributionSalePrice());
            	    	        					if(proSaleInfoDetail.getInventoryStats().equals(4) || (proSaleInfoDetail.getOpeningSale().equals(false) && proSaleInfoDetail.getInventoryRemainder().equals(0))){
                	        							pro.setFirPrice(987654321);
                	        							ppp.setProDetailConPrice(987654321);
                	        						}
            	    	        				}
            	    	        				if(kk != 0){
            	    	        					pro.setConPrice(sumPrice/kk);
            	    	        				}
            	    	        				kk = 0;
            	    	        			}
                    	        			else{
            	    	        				pro.setFirPrice(987654321);
            	    	        				ppp.setProDetailConPrice(987654321);
            	    	        			}
            	    	        			sumPrice =0;
            	            		}
                            			if(StringUtil.isNullOrEmpty(pro.getFirPrice()) || pro.getFirPrice().equals(0)){
                            				pro.setFirPrice(987654321);
                            				ppp.setProDetailConPrice(987654321);
                            			}
            	            	}
            	            }
            				else{
            					ppp.setProDetailConPrice(987654321);
            				}
            			}
            			rs.setProDetails(lii);
            		}
            	}
        	}*/
        } catch (Exception e) {
        	log.error("查询酒店列表异常：",e);
 		}
        //总条数
  		int count= (int)mongoTemplate1.count(query, ResBaseInfo.class);
  		//总页数
  		int totalPage= (int)(count/hotelSearchReq.getRowCount()+1);
  		response.setTotalPatge(Integer.valueOf(totalPage));
  		response.setTotalCount(Integer.valueOf(count));
		/*if(StringUtil.isNotNullOrEmpty(res)){
				for(ResBaseInfo resInfo : res){
					if(StringUtil.isNotNullOrEmpty(resInfo)){
						List<ProDetails> proDetails = resInfo.getProDetails();
						if(StringUtil.isNotNullOrEmpty(proDetails)){
							for(ProDetails pros :proDetails){
								List<ResProBaseInfo> resProBaseInfoList = pros.getResProBaseInfoList();
								if(resProBaseInfoList.size() >= 2){
									Collections.sort(resProBaseInfoList, new Comparator<ResProBaseInfo>() {
										@Override
										public int compare(ResProBaseInfo o1, ResProBaseInfo o2) {
											if(StringUtil.isNotNullOrEmpty(o1.getFirPrice()) && StringUtil.isNotNullOrEmpty(o2.getFirPrice())){
												if(o1.getFirPrice() > o2.getFirPrice()){
													pros.setProDetailConPrice(o2.getFirPrice());
													return 1;
												}else if(o1.getFirPrice() < o2.getFirPrice()){
													pros.setProDetailConPrice(o1.getFirPrice());
													return -1;
												}
												else{
													return 0;
												}
											}
												return 0;
										}
									});
								}else if(resProBaseInfoList.size() == 1){
									pros.setProDetailConPrice(resProBaseInfoList.get(0).getFirPrice());
								}else{
									pros.setProDetailConPrice(987654321);
								}
							}
							if(proDetails.size() >= 2){
								Collections.sort(proDetails, new Comparator<ProDetails>() {
									@Override
									public int compare(ProDetails o1, ProDetails o2) {
										if(StringUtil.isNotNullOrEmpty(o1.getProDetailConPrice()) && StringUtil.isNotNullOrEmpty(o2.getProDetailConPrice())){
											if(o1.getProDetailConPrice() > o2.getProDetailConPrice()){
												return 1;
											}else if(o1.getProDetailConPrice() < o2.getProDetailConPrice()){
												return -1;
											}
											else{
												return 0;
											}
										}
											return 0;
									}
								});
							}
						}
					}
				}
			response.setResponseResult(res);
		}*/
  		response.setResponseResult(res);
  		//Future<TCResponse<ResBaseInfo>> rrr = new AsyncResult<TCResponse<ResBaseInfo>>(response);
  		//RpcContext.getContext().setFuture(rrr);
		log.info("查询酒店列表结束");
		//long end = System.currentTimeMillis();  
      //  System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");  
		return response;
        
		//return new AsyncResult<TCResponse<ResBaseInfo>>(response);
	}
	
	
	@Override
	public ResBaseInfo queryHotelDetail(Agent agent, Long resId, String startTime, String endTime) throws GSSException{
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
        
        Integer sumPrice=0;
        Integer days = 0;
        try{
        	 days= DateUtil.daysBetween(startTime, endTime);
        }catch(ParseException e){
        	e.printStackTrace();
        }
       
        ResBaseInfo resDetail =mongoTemplate1.findOne(new Query(Criteria.where("_id").is(resId)),ResBaseInfo.class);
        List<ProInfoDetail> proInfoDetailList = mongoTemplate1.find(new Query(Criteria.where("resId").is(resId)),ProInfoDetail.class);
        try {
        	 if(StringUtil.isNotNullOrEmpty(resDetail) && StringUtil.isNotNullOrEmpty(resDetail.getProDetails())){
             	List<ProDetails> lii = resDetail.getProDetails();
             	if(StringUtil.isNotNullOrEmpty(lii)){
         			for(ProDetails ppp : lii){
         				List<ResProBaseInfo> p = ppp.getResProBaseInfoList();
         				if(StringUtil.isNotNullOrEmpty(p)){
     	            		for(ResProBaseInfo pro : p){
     	            			TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
     	            			Calendar da = Calendar.getInstance(); 
                   			 for (int i = 0; i < days; i++) {
                   				 ProSaleInfoDetail ProSaleInfoDetail=new ProSaleInfoDetail();
                   				 ProSaleInfoDetail.setDistributionSalePrice(0);
                   				 da.setTime(sdf.parse(startTime));
                   				 da.add(Calendar.DAY_OF_MONTH, i);
                   				 mapPro.put(sdf.format(da.getTime()), ProSaleInfoDetail);
                				}
		                   			 if(StringUtil.isNotNullOrEmpty(proInfoDetailList)) {
		                   				 for(ProInfoDetail proInfo : proInfoDetailList) {
		                   			 		if(proInfo.getProductUniqueId().equals(pro.getProductUniqueId())) {
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
			                   			 			Integer firstPrice = 999999;
			         	    	        			for (Map.Entry<String, ProSaleInfoDetail> entry : proSaleInfoDetails.entrySet()) {
			         	    	        				int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
			         	    	        				int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
			         	    	        					if(begincompare >= 0 && endCompare < 0){
			         	    	        						mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
			         	    	        						Integer price= entry.getValue().getDistributionSalePrice();
			         	    	        						if(!entry.getValue().getInventoryStats().equals(4)) {
			         	    	        							int startDate = DateUtil.stringToSimpleString(entry.getValue().getStartDate()).compareTo(startTime);
			         	    	        							int endDate = DateUtil.stringToSimpleString(entry.getValue().getEndDate()).compareTo(endTime);
			         	    	        							if(startDate > 0 && endDate < 0) {
			         	    	        								pro.setBookStatus(0);
			         	    	        							}
			         	    	        						}
			         	    	        						if(days.equals(1)) {
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
			     	    	        					/*if(StringUtil.isNotNullOrEmpty(mapPro.get(startTime))){
			     	    	        						pro.setFirPrice(mapPro.get(startTime).getDistributionSalePrice());
			     	    	        					}*/
			     	    	        					if(kk < days.intValue()) {
			     	    	        						pro.setBookStatus(0);
		        	    	        					}
			     	    	        					if(kk != 0){
			        	    	        					pro.setConPrice(sumPrice/kk);
			        	    	        				}
			        	    	        				kk = 0;
			        	    	        				sumPrice =0;
		                   			 			}else {
		                   			 				//pro.setFirPrice(987654321);
		                   			 				//pro.setConPrice(987654321);
		                   			 				pro.setBookStatus(0);
		                   			 			}
			                   			 		/*if(StringUtil.isNullOrEmpty(pro.getFirPrice()) || pro.getFirPrice().equals(0)){
			     	            					pro.setFirPrice(987654321);
			     	            					pro.setConPrice(987654321);
			     	            				}*/
		                   			 		}
		                   			 	}
		                   			 }
     		            		}
         					}
         				}
             		}
             	}else{
             		throw new GSSException("查询某一酒店详情出错", "0130", "酒店信息为空");
             	}
		} catch (Exception e) {
			log.error("查询某一酒店详情出错"+e);
            throw new GSSException("查询某一酒店详情出错", "0130", "查询某一酒店详情出错");
		}
       
			return resDetail;
		}

	@Override
	public void insertLastestResByUser(Agent agent, String userId, Long resId) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(userId)) {
            log.error("用户id为空");
            throw new GSSException("插入某一用户最近浏览记录", "0100", "用户id为空");
        }
		if (StringUtil.isNullOrEmpty(resId)) {
            log.error("用户id为空");
            throw new GSSException("插入某一用户最近浏览记录", "0100", "酒店id为空");
        }
		try {
			ResBaseInfo resDetail = queryListByResId(agent, resId);
			LastestResRecord lastestResRecord=new LastestResRecord();
			Date newDate=new Date();
			//List<LastestResRecord> lastestResRecordList= new ArrayList<LastestResRecord>();
			if(StringUtil.isNotNullOrEmpty(resDetail)){
				if(StringUtil.isNotNullOrEmpty(resDetail.getImgInfoList())){
					List<ImgInfo> img = resDetail.getImgInfoList();
					if(StringUtil.isNotNullOrEmpty(img)){
			    		for(ImgInfo mm : img){
			    			if(mm.getIsResDefault().equals(1)){
			    				lastestResRecord.setImageUrl(mm.getImageUrl());
			    				break;
			    			}
			    		}
				}
	    		lastestResRecord.setUserId(userId);
	    		lastestResRecord.setResId(resDetail.getResId());
	        	lastestResRecord.setResName(resDetail.getResName());
	        	lastestResRecord.setResGrade(resDetail.getResGrade());
	        	lastestResRecord.setMinPrice(resDetail.getMinPrice());
	        	lastestResRecord.setRecordDate(newDate);
	        	mongoTemplate1.insert(lastestResRecord, "lastestResRecord");
	        	//lastestResRecordList.add(lastestResRecord);
				}
			}
		} catch (Exception e) {
			 log.error("用户id为空"+e);
	         throw new GSSException("插入某一用户最近浏览记录", "0100", "酒店id为空");
		}
	}

	@Override
	public List<LastestResRecord> queryLastestResByUser(Agent agent, String userId,int limit) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(userId)) {
            log.error("用户id为空");
            throw new GSSException("获取某一用户最近10条浏览记录", "0100", "用户id为空");
        }
		Query query = Query.query(Criteria.where("userId").is(userId)).with(new Sort(Direction.DESC, "recordDate")).limit(30);
		List<LastestResRecord> lastestResRecordList= mongoTemplate1.find(query,LastestResRecord.class);
		try {
			if(StringUtil.isNotNullOrEmpty(lastestResRecordList)){
				if(lastestResRecordList.size() >= 2){
					for (int i = 0; i < lastestResRecordList.size(); i++) {
						for (int j = lastestResRecordList.size()-1;j > i; j--) {
							if(lastestResRecordList.get(j).getResId().equals(lastestResRecordList.get(i).getResId())){
								lastestResRecordList.remove(j);
							}
						}
					}
					/*Collections.sort(lastestResRecordList, new Comparator<LastestResRecord>() {
						@Override
						public int compare(LastestResRecord o1, LastestResRecord o2) {
							if(o1.getRecordDate().getTime() > o2.getRecordDate().getTime()){
								return 1;
							}else if(o1.getRecordDate().getTime() < o2.getRecordDate().getTime()){
								return -1;
							}else{
								return 0;
							}
						}
					});*/
				}else{
					return lastestResRecordList;
				}
				if(lastestResRecordList.size() >= limit){
					lastestResRecordList = lastestResRecordList.subList(0, limit);
				}
			}
		} catch (Exception e) {
			log.error("获取某一用户最近10条浏览记录异常：",e);
			throw new GSSException("获取某一用户最近10条浏览记录", "0100", "获取某一用户最近10条浏览记录出错");
		}
		return lastestResRecordList;
	}

	@Override
	public ResProBaseInfo queryInventPrice(Agent agent, String productUniqueId, String startTime, String endTime) throws GSSException{
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取某一酒店详细信息", "0102", "agent对象为空");
        }
		if (StringUtil.isNullOrEmpty(productUniqueId)) {
            log.error("政策id为空");
            throw new GSSException("获取某一房型价格详细信息", "0100", "政策id为空");
        }
		if (StringUtil.isNullOrEmpty(startTime)) {
            log.error("开始日期为空");
            throw new GSSException("获取某一酒店详细信息", "0104", "开始日期为空");
        }
        if (StringUtil.isNullOrEmpty(endTime)) {
            log.error("结束日期为空");
            throw new GSSException("获取某一酒店详细信息", "0105", "结束日期为空");
        }
		Integer sumPrice=0;
        Integer days = 0;
        BigDecimal totalProfitPrice = new BigDecimal(0);
        ResProBaseInfo resProBaseInfo=new ResProBaseInfo();
        try{
        	 days= DateUtil.daysBetween(startTime, endTime);
        }catch(ParseException e){
        	e.printStackTrace();
        }
		ProInfoDetail proInfoDetail=queryListByProductUniqueId(productUniqueId, ProInfoDetail.class);
		TreeMap<String, ProSaleInfoDetail> mapPro=new TreeMap<String, ProSaleInfoDetail>();
		try {
			if(StringUtil.isNotNullOrEmpty(proInfoDetail) && StringUtil.isNotNullOrEmpty(proInfoDetail.getProSaleInfoDetails())){
				for (Map.Entry<String, ProSaleInfoDetail> entry : proInfoDetail.getProSaleInfoDetails().entrySet()) {
					int begincompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(startTime);
					int endCompare = DateUtil.stringToSimpleString(entry.getKey()).compareTo(endTime);
						if(begincompare >= 0 && endCompare < 0){
							mapPro.put(DateUtil.stringToSimpleString(entry.getKey()), entry.getValue());
							Integer price= entry.getValue().getDistributionSalePrice();
							BigDecimal bigPrice = new BigDecimal(price);
							BigDecimal profitPrice = holProfitService.computeTcProfitPrice(agent, price, agent.getType());
							if(StringUtil.isNotNullOrEmpty(profitPrice)){
								BigDecimal profit = bigPrice.multiply(profitPrice);
								totalProfitPrice = totalProfitPrice.add(profit);
							}
							sumPrice += price;
						}
						/*if(endCompare >= 0){
							break;
						}*/
					}
					if(StringUtil.isNotNullOrEmpty(mapPro)){
						resProBaseInfo.setProSaleInfoDetailsTarget(mapPro);
						if(StringUtil.isNotNullOrEmpty(mapPro.get(startTime))){
							resProBaseInfo.setFirPrice(mapPro.get(startTime).getDistributionSalePrice());
						}
					}
					resProBaseInfo.setConPrice(sumPrice/(days));
					resProBaseInfo.setUserDays(days);
					resProBaseInfo.setUserSumPrice(sumPrice);
					resProBaseInfo.setTotalRebateRateProfit(totalProfitPrice);
					//sumPrice =0;
				}
		} catch (Exception e) {
			log.error("获取某一房型信息出错：",e);
			throw new GSSException("获取某一房型信息出错", "0140", "获取某一房型信息出错");
		}
		return resProBaseInfo;
	}
	
	
	@Override
	@Async
	public Future<TCResponse<ResBaseInfo>> queryHotelListForBack(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException{
		log.info("查询酒店列表开始");
        if (StringUtil.isNullOrEmpty(hotelSearchReq)) {
            log.error("hotelSearchReq查询条件为空");
            throw new GSSException("获取酒店列表", "0101", "hotelSearchReq查询条件为空");
        }
        if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取酒店列表", "0102", "agent对象为空");
        } 
        if (StringUtil.isNullOrEmpty(hotelSearchReq.getResId()) && StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode()) && StringUtil.isNullOrEmpty(hotelSearchReq.getResGradeId()) && StringUtil.isNullOrEmpty(hotelSearchReq.getKeyword())) {
        	hotelSearchReq.setCityCode("北京");
        }
        TCResponse<ResBaseInfo> response = new TCResponse<ResBaseInfo>();
        List<ResBaseInfo> res=null;
        Query query=new Query();
        Criteria criatira = new Criteria();
        try {
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
					criatira.and("resName").regex("^.*"+escapeHtml+".*$");//"^.*"+hotelName+".*$"
    		}
    		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResId())){
    			criatira.and("_id").is(Long.valueOf(hotelSearchReq.getResId()));
    		}
    		if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGradeId())){
    			criatira.and("resGradeId").is(hotelSearchReq.getResGradeId());
    		}
    		res = mongoTemplate1.find(query.addCriteria(criatira), ResBaseInfo.class);
    		//总条数
      		int count= (int)mongoTemplate1.count(query, ResBaseInfo.class);
      		//总页数
      		int totalPage= (int)(count/hotelSearchReq.getRowCount()+1);
      		response.setTotalPatge(Integer.valueOf(totalPage));
      		response.setTotalCount(Integer.valueOf(count));
    		
      		if(StringUtil.isNotNullOrEmpty(res)){
      			response.setResponseResult(res);
    			//System.out.println("1111111111111"+JsonUtil.toJson(res));
    		}
		} catch (Exception e) {
			log.error("获取酒店列表出错"+e);
            throw new GSSException("获取酒店列表出错", "0109", "获取酒店列表出错");
		}
        
		return new AsyncResult<TCResponse<ResBaseInfo>>(response);
	}

	@Override
	public List<String> getCityNames(Agent agent, String keyword,String city) {
		log.info("查询酒店名称列表开始**************");
		List<String> result = Lists.newArrayList();

    	DBObject dbObject = new BasicDBObject(); 
    	
    	 Pattern pattern;//用在模糊查询得时候，对字段进行匹配  
         if(StringUtils.isNotBlank(keyword)){//模糊查询  
        	 keyword = keyword.trim();
				String escapeHtml = StringEscapeUtils.unescapeHtml(keyword);
				String[] fbsArr = { "(", ")"};  // "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
		        	 for (String key : fbsArr) { 
		        		 if(escapeHtml.contains(key)){
		        			escapeHtml = escapeHtml.replace(key, "\\" + key);
		        		 }
		        	 }
             pattern = Pattern.compile("^.*"+escapeHtml+".*$", Pattern.CASE_INSENSITIVE);  
             dbObject.put("name",pattern);   
         }
         dbObject.put("city",city);
    	BasicDBObject fieldsObject=new BasicDBObject();  
    	//指定返回的字段  
    	fieldsObject.put("name", true);       
    	  
    	Query query = new BasicQuery(dbObject,fieldsObject).limit(20);
    	query.with(new Sort(Direction.DESC, "checkTimes"));
    	List<HotelName> hns = mongoTemplate1.find(query, HotelName.class);
    	log.info("查询酒店名称列表结束**************");
		for (HotelName h : hns) {
			result.add(h.getName());
		}
		return result;
	}

	@Override
	public int saleStatusUpdate(Agent agent, Long resId, Integer saleStatus) throws GSSException {
		if (StringUtil.isNullOrEmpty(agent)) {
            log.error("agent对象为空");
            throw new GSSException("获取酒店列表", "0102", "agent对象为空");
        } 
        if (StringUtil.isNullOrEmpty(resId)) {
        	throw new GSSException("获取酒店列表", "0118", "传入RESID为空");
        }
        try {
        	SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String updateTime = sdfupdate.format(new Date());
        	Query query = Query.query(Criteria.where("_id").is(resId));
        	Update update = Update.update("saleStatus", saleStatus).set("latestUpdateTime", updateTime);
        	mongoTemplate1.upsert(query, update, ResBaseInfo.class);
          // mongoTemplate1.upsert(new Query(Criteria.where("_id").is(resId)), new Update().set("saleStatus", saleStatus), "resBaseInfo");
		} catch (Exception e) {
			log.error("修改可售状态出错"+e);
			throw new GSSException("修改可售状态", "0118", "修改可售状态失败");
		}
		return 1;
	}

	@Override
	public List<ResBaseInfo> findNearHotel(Agent agent, String city,String sectionName, double lat, double lon, double distance,int top) {
		List<ResBaseInfo> result = Lists.newArrayList();
		DBObject dbObject = new BasicDBObject();  
        dbObject.put("sectionName",sectionName);            
        dbObject.put("cityName",city);
	   	BasicDBObject fieldsObject=new BasicDBObject();        
	   	Query query = new BasicQuery(dbObject,fieldsObject);  
	   	long b = System.currentTimeMillis();
	   	List<ResBaseInfo> rbis = mongoTemplate1.find(query, ResBaseInfo.class);
	   	long e = System.currentTimeMillis();
	   
	   	int i=0;
	   	for (ResBaseInfo resBaseInfo : rbis) {
	   		List<ResGPSInfo> gps = resBaseInfo.getResGPS();
	   		if(gps.size()>0)
	   		{
	   			ResGPSInfo rgi = gps.get(0);
	   			Double myDistance = GPSUtil.getDistance(lat, lon, Double.parseDouble(rgi.getLat()), Double.parseDouble(rgi.getLon())); 
	   			if((myDistance<=distance)&&(i++<top)&&(myDistance!=0))
	   			{	   	
	   				//用于距离展示，不作他用
	   				resBaseInfo.setSumPros(myDistance.intValue());
	   				result.add(resBaseInfo);
	   			}
	   		}			
		}
		log.info("耗时："+(e-b));
	   	System.out.println("<<<耗时："+(e-b));
		return result;
	}

	@Override
	public List<HotelName> getHotelNames(Agent agent, String hotelName) {
		log.info("关键字模糊查询酒店名称列表开始，入参: "+hotelName);

    	DBObject dbObject = new BasicDBObject(); 
    	 String[] fbsArr = { "(", ")"};  //  "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
    	 Pattern pattern;//用在模糊查询得时候，对字段进行匹配  
         if(StringUtils.isNotBlank(hotelName)){//模糊查询  
        	 hotelName = hotelName.trim();
        	 hotelName = StringEscapeUtils.unescapeHtml(hotelName);
        	 for (String key : fbsArr) {
        		 if(hotelName.contains(key)){
        			 hotelName = hotelName.replace(key, "\\" + key);
        		 }
        	 }
             pattern = Pattern.compile("^.*"+hotelName+".*$", Pattern.CASE_INSENSITIVE);
             dbObject.put("name",pattern);   
         }
    	BasicDBObject fieldsObject=new BasicDBObject();
    	Query query = new BasicQuery(dbObject,fieldsObject).limit(10);
    	query.with(new Sort(Direction.DESC, "checkTimes"));
    	List<HotelName> hns = mongoTemplate1.find(query, HotelName.class);
    	log.info("关键字模糊查询酒店名称列表结束**************");
		
		return hns;
	}

	@Override
	public Boolean insertNewRes(Agent agent, Long resId) {
		log.info("手动添加酒店开始, 入参："+resId);
		Boolean flag = false;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();  
		Calendar calAdd = Calendar.getInstance();
		String calStartTime= sdf.format(cal.getTime());
		
		calAdd.add(Calendar.MONTH, 2);
		calAdd.add(Calendar.DAY_OF_MONTH, -1);
		String calAddTime= sdf.format(calAdd.getTime());
		Integer saleStatus = 1;
		try {
			AssignDateHotelReq assignDateHotelReqFirstMonth=new AssignDateHotelReq();
			assignDateHotelReqFirstMonth.setResId(resId);
			assignDateHotelReqFirstMonth.setSourceFrom("-1");
			//assignDateHotelReqFirstMonth.setNeedSpecialPolicy(1);
			assignDateHotelReqFirstMonth.setStartTime(calStartTime);
			assignDateHotelReqFirstMonth.setEndTime(calAddTime);
			AssignDateHotel assignDateHotelFirstMonth=  hotel.queryAssignDateHotel(assignDateHotelReqFirstMonth);
			
			if(assignDateHotelFirstMonth !=null){
				List<ProInfoDetail> proInfoDetailList= assignDateHotelFirstMonth.getProInfoDetailList();
					if(proInfoDetailList != null && proInfoDetailList.size() > 0){
						for(ProInfoDetail pro : proInfoDetailList){
							pro.setId(pro.getProductUniqueId());
							pro.setResId(assignDateHotelFirstMonth.getResId());
							pro.setUpdateInvenTime(sdfupdate.format(new Date()));
							mongoTemplate1.save(pro, "proInfoDetail");
						} 
						SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
		    			singleHotelDetailReq.setResId(String.valueOf(resId));
		    			singleHotelDetailReq.setSourceForm("-1");
		    			singleHotelDetailReq.setRequestContent("res,respro,rimg");
		    			TCHotelDetailResult hotelDetail=hotel.queryTCHotelDetail(singleHotelDetailReq);
		    			
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
			    								if(StringUtil.isNotNullOrEmpty(map)){
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
    							/*if(imgInfoList!=null && imgInfoList.size() > 0) {
    								List<ImgInfo> list2=new ArrayList<ImgInfo>();
	    							list2.addAll(imgInfoList);
	    							tcResBaseInfo.setImgInfoList(list2);
    							}*/
    							
    							tcResBaseInfo.setSaleStatus(saleStatus);
	    						tcResBaseInfo.setLatestUpdateTime(sdfupdate.format(new Date()));
	    						mongoTemplate1.save(tcResBaseInfo, "resBaseInfo");
	    						
	    						List<ResGPSInfo> resGPS = tcResBaseInfo.getResGPS();
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
	    								mongoTemplate1.save(oneMidInfo, "holMidBaseInfo");
	    							}else {
		    							/**
		    							 * 保存中间表
		    							 */
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
		    							mongoTemplate1.save(holMidBaseInfo, "holMidBaseInfo");
		    						}
	    						}
	    						ResIdList resIdList =new ResIdList();
	    						resIdList.setId(resId);
	    						mongoTemplate1.save(resIdList, "resIdList");
	    						flag = true;
    					}
    				}
			}
		}
	} catch (GSSException e) {
		LogRecordHol logRecordHol=new LogRecordHol();
		logRecordHol.setBizCode("HOL-All");
		logRecordHol.setCreateTime(new Date());
		logRecordHol.setTitle("更新酒店详细信息");
		logRecordHol.setDesc("同步酒店全部信息失败,酒店ID为："+String.valueOf(resId));
		logRecordHol.setResId(resId);
		mongoTemplate1.save(logRecordHol, "logRecordHol");
	}
		return flag;
	}

	@Override
	public Boolean updateLuceneDate(Agent agent,List<HotelName> list) throws IOException {
		//List<HotelName> list = mongoTemplate1.find(new Query(Criteria.where("_id").ne("").ne(null)), HotelName.class);
		//try {
			Directory directory = FSDirectory.open(Paths.get("./index"));
	    	//Version version = Version.LUCENE_7_1_0;
	    	
	    		Analyzer analyzer = new IKAnalyzer();
		    	//创建索引写入配置
		    	IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		    	  
		    	//创建索引写入对象
		    	IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			//System.out.println(i);
			/*Query query = Query.query(Criteria.where("_id").ne("").ne(null));
			query.skip(i);
			query.limit(100);
			List<HotelName> list = mongoTemplate1.find(query,HotelName.class);*/
			if(StringUtil.isNotNullOrEmpty(list)) {
				System.out.println(list.size());
				for (int j = 0; j < list.size(); j++) {
					Document doc = new Document();
			    	doc.add(new LongPoint("id", list.get(j).getId()));
			    	doc.add(new StoredField("id", list.get(j).getId()));
			    	doc.add(new TextField("name", list.get(j).getName(), Field.Store.YES));
			    	doc.add(new StringField("city", list.get(j).getCity(), Field.Store.YES));
					//doc.add(new IntPoint("saleStatus", list.get(j).getSaleStatus()));
					//doc.add(new StoredField("saleStatus", list.get(j).getSaleStatus()));
					doc.add(new LongPoint("checkTimes", list.get(j).getCheckTimes()));
			    	doc.add(new StoredField("checkTimes", list.get(j).getCheckTimes()));
					indexWriter.addDocument(doc);
				}     
				indexWriter.commit();
				indexWriter.close();
			}
	    	directory.close();
	    	log.info("添加全文检索结束");
	    	
		/*} catch (Exception e) {
			log.error("添加全文检索失败");
			throw new GSSException("添加全文检索", "0218", "添加全文检索失败"+e.getMessage());
		}*/
		return true;
	}

	@Override
	public List<HotelName> queryHotelNamesByLucene(Agent agent,String name) {
		log.info("查询索引关键字入参: "+name);
		List<HotelName> hns = new ArrayList<HotelName>();
		try {
			Analyzer analyzer = new IKAnalyzer();
	    	Directory directory = FSDirectory.open(Paths.get("./index"));
	    	IndexReader indexReader = DirectoryReader.open(directory);
	    	IndexSearcher indexSearcher = new IndexSearcher(indexReader);
	    	
	    	String searchField = "name";
	    	//String checkTimes = "checkTimes";
	    	//String[] filedStr = new String[]{searchField, checkTimes};
	    	//QueryParser parser = new MultiFieldQueryParser(filedStr, analyzer);
	    	QueryParser parser = new QueryParser(searchField, analyzer);
	    	org.apache.lucene.search.Query query = parser.parse(name);
	    	//org.apache.lucene.search.Query query = new MultiFieldQueryParser(filedStr, analyzer).parse(name);
	    	/*Term term = new Term(searchField, "上海");
	    	query = new PrefixQuery(term);*/
	    	/*TokenStream tokenStream = analyzer.tokenStream("name", new StringReader(name));
	        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
	        tokenStream.reset();
	        while (tokenStream.incrementToken()) {
	            System.out.println("分词后: "+charTermAttribute.toString());
	        }
	        tokenStream.end();*/
	    	//org.apache.lucene.search.Sort sort = new org.apache.lucene.search.Sort(new SortField[]{new SortField(checkTimes, SortField.Type.LONG,true),SortField.FIELD_SCORE});
	    	/*Term term = new Term(searchField, name);
	    	query = new WildcardQuery(term);*/
	        //SortField sortField = new SortField("checkTimes", SortField.Type.STRING,true);
	       // org.apache.lucene.search.Sort sort = new org.apache.lucene.search.Sort(sortField);
	    	//TopDocs topDocs = indexSearcher.search(query, 20, sort);
	    	TopDocs topDocs = indexSearcher.search(query, 20);
	    	SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
	    	Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
	    	
	    	//System.out.println("命中：" + topDocs.totalHits);
	    	for(ScoreDoc scoreDoc : topDocs.scoreDocs){
	    		//System.out.println("得分: "+scoreDoc.score);
	    		Document document = indexSearcher.doc(scoreDoc.doc);
	    		String resId = document.get("id");
	    		String beResName = document.get("name");
	    		//String checkTimess = document.get("checkTimes");
	    		String noResName = document.get("name");
	    		String city = document.get("city");
	    		// 内容增加高亮显示
	    		TokenStream tokenStream1 = analyzer.tokenStream("name", new StringReader(beResName));
	    	    String resName = highlighter.getBestFragment(tokenStream1, beResName);
	    	    HotelName hn = new HotelName();
	    		/*System.out.println("resId: "+resId);
	    		System.out.println("resName: "+resName);
	    		System.out.println("checkTimes: "+checkTimess);
	    		System.out.println("noResName: "+noResName);*/
	    	    hn.setId(Long.valueOf(resId));
	    	    hn.setCity(city);
	    	    hn.setLabel(resName);
	    	    hn.setName(noResName);
	    	    hns.add(hn);
	    	}
	    	indexReader.close();
	    	directory.close();
	    	//tokenStream.close();
	        analyzer.close();
	    	
		} catch (Exception e) {
			// TODO: handle exception
		}
			return hns;
	}

	@Override
	public Boolean changeLuceneData(HotelName hotelName) {
		try {
			Directory directory = FSDirectory.open(Paths.get("./index"));
			Analyzer analyzer = new IKAnalyzer();//中文分词
			
	        //创建索引写入配置
	        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
	        //创建索引写入对象
	        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

	        Document doc = new Document();
	        
	        doc.add(new LongPoint("id", hotelName.getId()));
	    	doc.add(new StoredField("id", hotelName.getId()));
	    	doc.add(new TextField("name", hotelName.getName(), Field.Store.YES));
	    	doc.add(new StringField("city",hotelName.getCity(), Field.Store.YES));
			//doc.add(new IntPoint("saleStatus", list.get(j).getSaleStatus()));
			//doc.add(new StoredField("saleStatus", list.get(j).getSaleStatus()));
			doc.add(new LongPoint("checkTimes", hotelName.getCheckTimes()));
	    	doc.add(new StoredField("checkTimes", hotelName.getCheckTimes()));
	    	
	    	indexWriter.updateDocument(new Term("id",hotelName.getId().toString()), doc);
	    	 indexWriter.commit();
	    	indexWriter.close();
	    	directory.close();
	    	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return null;
	}

	

}

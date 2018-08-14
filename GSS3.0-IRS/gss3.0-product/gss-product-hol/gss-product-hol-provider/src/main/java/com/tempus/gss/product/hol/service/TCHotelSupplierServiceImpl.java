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
import java.util.Random;
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
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
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
import org.springframework.data.geo.Point;
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
import com.tempus.gss.product.hol.api.entity.response.tc.PaymentWay;
import com.tempus.gss.product.hol.api.entity.response.tc.ProDetails;
import com.tempus.gss.product.hol.api.entity.response.tc.ProInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ProSaleInfoDetail;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResGPSInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResProBaseInfos;
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
import com.tempus.gss.product.hol.utils.RedisService;
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
	
	@Autowired
	RedisService redisService;
	
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
	//@Cacheable(value = "ResBaseInfo", key = "#id",unless="")
	@Override
	public ResBaseInfo queryListByResId(Agent agent, Long id) {
		ResBaseInfo resDetail = null;
		String perKey = "TCHOL"+id;
		resDetail = (ResBaseInfo)redisService.get(perKey);
		
		if(null == resDetail) {
			resDetail= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)), ResBaseInfo.class);
			
			redisService.set(perKey, resDetail, Long.valueOf(60 * 60 * 24));
		}
		//ResBaseInfo resDetail= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)), ResBaseInfo.class);
		return resDetail;
	}
	
	/**
	 * 根据酒店id查找具体酒店信息
	 */
	//@Cacheable(value = "HolMidBaseInfo", key = "#id",unless="")
	@Override
	public HolMidBaseInfo queryMidListByResId(Agent agent, String id) {
		HolMidBaseInfo resDetail = null;
		String perKey = "MidHOL"+id;
		resDetail = (HolMidBaseInfo)redisService.get(perKey);
		if(null == resDetail) {
			resDetail= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)), HolMidBaseInfo.class);
			
			redisService.set(perKey, resDetail, Long.valueOf(60 * 60 * 24));
		}
		
		return resDetail;
	}
	
	@Override
	public <T> T queryListByProId(String id, Class<T> clazz) {
		T t= mongoTemplate1.findOne(new Query(Criteria.where("proId").is(id)),clazz);
		
		return t;
	}
	
	@Override
	public <T> T queryHolByResName(String resName, Class<T> clazz) {
		
			String escapeHtml = StringEscapeUtils.unescapeHtml(resName.trim());
			String[] fbsArr = { "(", ")" };  //  "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" 
	        	 for (String key : fbsArr) { 
	        		 if(escapeHtml.contains(key)){
	        			escapeHtml = escapeHtml.replace(key, "\\" + key);
	        		 }
	        	 }
		T t= mongoTemplate1.findOne(new Query(Criteria.where("resName").is(escapeHtml)),clazz);
		
		return t;
	}
	
	
	@Override
	public <T> T queryDetailById(Long id, Class<T> clazz) {
		T t= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),clazz);
		
		return t;
	}
	
	@Override
	public <T> T queryListByProductUniqueId(String id, Class<T> clazz) {
		
		T t= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),clazz);
		
		return t;
	}
	
	@Override
	public <T> T queryListById(Long id, Class<T> clazz) {
		T t = null;
		t= mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),clazz);
		return t;
	}
	
	@Override
	@Async
	public Future<ResBaseInfo> queryResBaseInfo(Long id){
		ResBaseInfo result = null;
		String perKey = "TCHOL"+id;
		result = (ResBaseInfo)redisService.get(perKey);
		if(null == result) {
			result = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),ResBaseInfo.class);
			redisService.set(perKey, result, Long.valueOf(60 * 60 * 24));
		}
		return new AsyncResult<ResBaseInfo>(result);
	}
	
	@Override
	@Async
	public Future<ResProBaseInfos> queryResProBaseInfos(Long id){
		ResProBaseInfos result = null;
		String perKey = "TCResPro"+id;
		result = (ResProBaseInfos)redisService.get(perKey);
		if(null == result) {
			result = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),ResProBaseInfos.class);
			redisService.set(perKey, result, Long.valueOf(60 * 60 * 24 * 3));
		}
		return new AsyncResult<ResProBaseInfos>(result);
	}
	
	@Override
	@Async
	public Future<ImgInfoSum> queryImgInfoSum(Long id){
		ImgInfoSum result = null;
		String perKey = "TCHOLImg"+id;
		result = (ImgInfoSum)redisService.get(perKey);
		if(null == result) {
			result = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)),ImgInfoSum.class);
			redisService.set(perKey, result, Long.valueOf(60 * 60 * 24 * 3));
		}
		return new AsyncResult<ImgInfoSum>(result);
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
				/*for(ResTrafficInfo traffic : rs.getHotelTrafficInfo()){
					if(traffic.getStartLocation().contains("景点")){
						cityScenic.add(traffic.getDescription().trim());
					}
					if(traffic.getStartLocation().contains("机场") || traffic.getStartLocation().contains("火车站")){
						cityAirRailWay.add(traffic.getDescription().trim());
					}
					if(traffic.getStartLocation().contains("地铁站")){
						citySubWay.add(traffic.getDescription().trim());
					}
				}*/
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
        //long start = System.currentTimeMillis();
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
       // Integer sumPrice=0;
       // Integer days=0;
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
 			//days= DateUtil.daysBetween(hotelSearchReq.getBegin(), hotelSearchReq.getEnd());
 			int beginResult= hotelSearchReq.getBegin().compareTo(sdf.format(date.getTime()));
 			int endResult= hotelSearchReq.getEnd().compareTo(sdf.format(dateAdd.getTime()));
 			if(beginResult >= 0 && endResult <= 0){
 				if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo())) {
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo().getLon()) && StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo().getLat())) {
 						hotelSearchReq.setCityCode("");
 	 					Point point =new Point(Double.valueOf(hotelSearchReq.getResGPSInfo().getLon()), Double.valueOf(hotelSearchReq.getResGPSInfo().getLat()));
 	 					criatira.and("resGpsLocation").near(point).maxDistance(0.2D);//100000/6378137
 					}else {
 						throw new GSSException("获取酒店列表", "0625", "按照坐标查询出错, 请同时输入经纬度");
 					}
 					/*Criteria latlon = new Criteria();
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo().getType())) {
 						latlon.and("type").is(hotelSearchReq.getResGPSInfo().getType());
 					}else {
 						throw new GSSException("获取酒店列表", "0103", "查询经纬度类型为空");
 					}
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo().getLon())) {
 						latlon.and("lon").regex("^.*"+hotelSearchReq.getResGPSInfo().getLon()+".*$");
 					}else {
 						throw new GSSException("获取酒店列表", "0103", "查询经度条件为空");
 					}
 					if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getResGPSInfo().getLat())) {
 						latlon.and("lat").regex("^.*"+hotelSearchReq.getResGPSInfo().getLat()+".*$");
 					}else {
 						throw new GSSException("获取酒店列表", "0103", "查询纬度条件为空");
 					}
	 				criatira.and("resGPS").elemMatch(latlon);*/
 				}else if (StringUtil.isNullOrEmpty(hotelSearchReq.getCityCode()) && StringUtil.isNullOrEmpty(hotelSearchReq.getResGPSInfo())) {
 		        	hotelSearchReq.setCityCode("北京");
 		        }
 				if(StringUtils.isNotEmpty(hotelSearchReq.getCityCode())){
 					//criatira.and("cityName").regex("^.*"+hotelSearchReq.getCityCode()+".*$");
 					criatira.and("cityName").is(hotelSearchReq.getCityCode());
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
 	 	 					String sss = hlevle.get(0);
 	 	 					if(sss.contains("27")) {
 	 	 						sss = sss + ",32,34";
 	 	 					}
 	 	 					if(sss.contains("26")) {
 	 	 						sss = sss + ",31";
 	 	 					}
 	 	 					if(sss.contains("24")) {
 	 	 						sss = sss + ",30";
 	 	 					}
 	 	 					if(sss.contains("23")) {
 	 	 						sss = sss + ",28";
 	 	 					}
 	 	 					criatira.and("resGradeId").in(Arrays.asList(sss.split(",")));
 	 	 				}
 					}
 	 				if(StringUtil.isNotNullOrEmpty(hotelSearchReq.getSearchCondition().getBrandList())){
 	 					criatira.and("brandInfo.resBrandName").in(hotelSearchReq.getSearchCondition().getBrandList());
 	 				}
 				}
 				//criatira.and("resCommonPrice").ne(999999);
 				log.info("酒店查询条件："+JsonUtil.toJson(criatira));
 				res = mongoTemplate1.find(query.addCriteria(criatira), ResBaseInfo.class);
 	        }else{
 	        	log.error("时间选择不对");
 	        	throw new GSSException("获取酒店列表", "0999", "时间选择不对,只能选择即日起两个月之内的日期");
 	        }
        } catch (Exception e) {
        	log.error("查询酒店列表异常：",e);
 		}
        //总条数
  		int count= (int)mongoTemplate1.count(query, ResBaseInfo.class);
  		//总页数
  		int totalPage= (int)(count/hotelSearchReq.getRowCount()+1);
  		response.setTotalPatge(Integer.valueOf(totalPage));
  		response.setTotalCount(Integer.valueOf(count));
  		response.setResponseResult(res);
  		//Future<TCResponse<ResBaseInfo>> rrr = new AsyncResult<TCResponse<ResBaseInfo>>(response);
  		//RpcContext.getContext().setFuture(rrr);
		log.info("查询酒店列表结束");
		//long end = System.currentTimeMillis();  
        //System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");  
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
        //List<ProInfoDetail> proInfoDetailList = mongoTemplate1.find(new Query(Criteria.where("resId").is(resId)),ProInfoDetail.class);
        try {
        	 if(StringUtil.isNotNullOrEmpty(resDetail) && StringUtil.isNotNullOrEmpty(resDetail.getProDetails())){
             	List<ProDetails> lii = resDetail.getProDetails();
             	if(StringUtil.isNotNullOrEmpty(lii)){
         			for(ProDetails ppp : lii){
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
		                   			// if(StringUtil.isNotNullOrEmpty(proInfoDetailList)) {
		                   				 //for(ProInfoDetail proInfo : proInfoDetailList) {
		                   				ProInfoDetail proInfo = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(pro.getProductUniqueId())),ProInfoDetail.class);
		                   			 		if(StringUtil.isNotNullOrEmpty(proInfo)) {
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
			         	    	        							if(startDate > 0 && endDate < 0) {
			         	    	        								pro.setBookStatus(0);
			         	    	        							}
			         	    	        						}
		         	    	        							if(sdf.format(new Date()).equals(startTime)) {
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
		                   			 		}else {
		                   			 			pro.setBookStatus(0);
		                   			 			pro.setFirPrice(firstPrice);
		                   			 		}
		                   			 	//}
		                   			// }
     		            		}
         					}
	         				if(pppRice.size() == 0) {
	         					ppp.setSaleStatus(0);
	         				}
         				}
             		}
             	}else{
             		throw new GSSException("查询某一酒店详情出错", "0130", "酒店信息为空");
             	}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询某一酒店详情出错"+e);
            throw new GSSException("查询某一酒店详情出错", "0130", "查询某一酒店详情出错");
		}
       
			return resDetail;
		}

	@Override
	public void insertLastestResByUser(Agent agent, String userId, String resId) throws GSSException{
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
			//ResBaseInfo resDetail = queryListByResId(agent, resId);
			HolMidBaseInfo resDetail = queryMidListByResId(agent, resId);
			LastestResRecord lastestResRecord=new LastestResRecord();
			Date newDate=new Date();
			//List<LastestResRecord> lastestResRecordList= new ArrayList<LastestResRecord>();
			if(StringUtil.isNotNullOrEmpty(resDetail)){
				if(StringUtils.isNotEmpty(resDetail.getTitleImg())) {
					lastestResRecord.setImageUrl(resDetail.getTitleImg());
				}
	    		lastestResRecord.setUserId(userId);
	    		lastestResRecord.setResId(resDetail.getResId());
	        	lastestResRecord.setResName(resDetail.getResName());
	        	lastestResRecord.setResGrade(resDetail.getResGrade());
	        	lastestResRecord.setMinPrice(resDetail.getMinPrice());
	        	lastestResRecord.setRecordDate(newDate);
	        	mongoTemplate1.save(lastestResRecord, "lastestResRecord");
	        	//lastestResRecordList.add(lastestResRecord);
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
	public ResProBaseInfo queryInventPrice(Agent agent, Long resId, String productUniqueId, String startTime, String endTime) throws GSSException{
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
        
		//ProInfoDetail proInfoDetail=queryListByProductUniqueId(productUniqueId, ProInfoDetail.class);
       // AssignDateHotel assignDateHotel = queryDetailById(resId, AssignDateHotel.class);
        
        AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
        assignDateHotelReq.setResId(resId);
		assignDateHotelReq.setSourceFrom("-1");
		assignDateHotelReq.setStartTime(startTime);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(endTime));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
		assignDateHotelReq.setEndTime(sdf.format(calendar.getTime()));
		AssignDateHotel assignDateHotel=  hotel.queryAssignDateHotel(assignDateHotelReq);
		
        if(StringUtil.isNotNullOrEmpty(assignDateHotel)) {
        	List<ProInfoDetail> proInfoDetailList = assignDateHotel.getProInfoDetailList();
        	if(StringUtil.isNotNullOrEmpty(proInfoDetailList)) {
        		for(ProInfoDetail proInfoDetail : proInfoDetailList) {
        			if(proInfoDetail.getProductUniqueId().equals(productUniqueId)) {
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
        			}
        		}
        	}
        }
        }catch(Exception e){
        	e.printStackTrace();
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
	public TCResponse<ResBaseInfo> queryHolListForBackNo(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException{
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
        	hotelSearchReq.setCityCode("北京市");
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
    				criatira.and("cityName").regex("^.*"+hotelSearchReq.getCityCode()+".*$");
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
        
		return response;
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
	public List<HolMidBaseInfo> findNearHotel(Agent agent, double lat, double lon) {
		Query query=new Query();
        Criteria criatira = new Criteria();
        query.skip(0);
  		query.limit(9);
  		criatira.and("saleStatus").is(1);
  		Point point =new Point(lon, lat);
		criatira.and("resPosition").near(point).maxDistance(0.005D);//100000/6378137
		
		List<HolMidBaseInfo> result = mongoTemplate1.find(query.addCriteria(criatira), HolMidBaseInfo.class);
		if(StringUtil.isNotNullOrEmpty(result)) {
			if(result.size() > 1) {
				result.remove(0);
				for (HolMidBaseInfo resBaseInfo : result) {
					if(StringUtil.isNotNullOrEmpty(resBaseInfo.getResPosition())) {
						Double myDistance = GPSUtil.getDistance(lat, lon, resBaseInfo.getResPosition()[1], resBaseInfo.getResPosition()[0]); 
		   				//用于距离展示，不作他用
		   				resBaseInfo.setTravelGuide(String.valueOf(myDistance));
					}
				}
			}
		}
		return result;
		/*List<ResBaseInfo> result = Lists.newArrayList();
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
		return result;*/
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
		SimpleDateFormat sdfupdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer minPrice = new Random().nextInt(300) + 100;
		Integer flag = 0;
		try {
			Calendar cal = Calendar.getInstance();  
			Calendar calAdd = Calendar.getInstance();
			String calStartTime= sdf.format(cal.getTime());
			
			calAdd.add(Calendar.MONTH, 2);
			calAdd.add(Calendar.DAY_OF_MONTH, -1);
			String calAddTime= sdf.format(calAdd.getTime());
			
			AssignDateHotelReq assignDateHotelReq=new AssignDateHotelReq();
			assignDateHotelReq.setResId(resId);
			assignDateHotelReq.setSourceFrom("-1");
			assignDateHotelReq.setStartTime(calStartTime);
			assignDateHotelReq.setEndTime(calAddTime);
			AssignDateHotel assignDateHotel=  hotel.queryAssignDateHotel(assignDateHotelReq);
			
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
				if(minProPrice != null){
					flag = 1;
					if(minProPrice.size() >= 2){
						minPrice= Collections.min(minProPrice);
					}else if(minProPrice.size() == 1){
						minPrice= minProPrice.get(0);
					}
				}
			}else {
				return false;
			}
			
			SingleHotelDetailReq singleHotelDetailReq=new SingleHotelDetailReq();
			singleHotelDetailReq.setResId(String.valueOf(resId));
			singleHotelDetailReq.setSourceForm("-1");
			singleHotelDetailReq.setRequestContent("res,respro,rimg");
			TCHotelDetailResult hotelDetail=hotel.queryTCHotelDetail(singleHotelDetailReq);
			List<ResBaseInfo> resBaseInfos = hotelDetail.getResBaseInfos();
			List<ImgInfo> resImages = hotelDetail.getResImages();
			List<ResProBaseInfo> resProBaseInfos = hotelDetail.getResProBaseInfos();
			ResBaseInfo resBaseInfo = null;
			if(StringUtil.isNotNullOrEmpty(resBaseInfos)) {
				resBaseInfo = resBaseInfos.get(0);
				resBaseInfo.setMinPrice(minPrice);
				resBaseInfo.setResCommonPrice(minPrice);
				if(flag.equals(1)) {
					resBaseInfo.setSaleStatus(1);
				}else {
					resBaseInfo.setSaleStatus(0);
				}
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
				mongoTemplate1.save(imgInfoSum, "imgInfoSum");
				mongoTemplate1.save(resBaseInfo, "resBaseInfo");
			}
			
			if(StringUtil.isNotNullOrEmpty(resProBaseInfos)) {
				ResProBaseInfos resProBase =new ResProBaseInfos();
				resProBase.setId(resId);
				resProBase.setResProBaseInfos(resProBaseInfos);
				resProBase.setLatestUpdateTime(sdfupdate.format(new Date()));
				mongoTemplate1.save(resProBase, "resProBaseInfos");
				
				ResIdList resIdList =new ResIdList();
				resIdList.setId(resId);
				mongoTemplate1.save(resIdList, "resIdList");
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
		return true;
	}

	@Override
	public Boolean updateLuceneDate(Agent agent,List<HolMidBaseInfo> list) throws IOException {
		try {
			Directory directory = FSDirectory.open(Paths.get("./index"));
	    	
    		Analyzer analyzer = new IKAnalyzer();
	    	//创建索引写入配置
	    	IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		    	  
	    	//创建索引写入对象
	    	IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			if(StringUtil.isNotNullOrEmpty(list)) {
				System.out.println(list.size());
				for (int j = 0; j < list.size(); j++) {
					Document doc = new Document();
					
					doc.add(new StringField("id", list.get(j).getId(), Field.Store.YES));
			    	doc.add(new TextField("name", list.get(j).getResName(), Field.Store.YES));
			    	doc.add(new StringField("city", list.get(j).getCityName(), Field.Store.YES));
					doc.add(new LongPoint("checkTimes", list.get(j).getBookTimes()));
			    	doc.add(new StoredField("checkTimes", list.get(j).getBookTimes()));
					indexWriter.addDocument(doc);
				}     
				indexWriter.commit();
				indexWriter.close();
			}
	    	directory.close();
	    	log.info("添加全文检索结束");
	    	
		} catch (Exception e) {
			log.error("添加全文检索失败");
			throw new GSSException("添加全文检索", "0218", "添加全文检索失败"+e.getMessage());
		}
		return true;
	}

	@Override
	public List<HotelName> queryHotelNamesByLucene(Agent agent,String name, String city) {
		log.info("查询索引关键字入参: "+name+", 城市: "+city);
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
	    	org.apache.lucene.search.Query query1 = new TermQuery(new Term("city",city));
	    	QueryParser parser = new QueryParser(searchField, analyzer);
	    	org.apache.lucene.search.Query query2 = parser.parse(name);
	    	BooleanQuery.Builder builder = new BooleanQuery.Builder();
	    	builder.add(query1, Occur.MUST);
	        builder.add(query2, Occur.MUST);
	        
	        BooleanQuery query = builder.build();
	    	
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
	    		String cityRes = document.get("city");
	    		// 内容增加高亮显示
	    		TokenStream tokenStream1 = analyzer.tokenStream("name", new StringReader(beResName));
	    	    String resName = highlighter.getBestFragment(tokenStream1, beResName);
	    	    HotelName hn = new HotelName();
	    		
	    	    hn.setId(Long.valueOf(resId));
	    	    hn.setCity(cityRes);
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
			//System.out.println("查询结果: "+JsonUtil.toJson(hns));
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

	@Override
	public ImgInfoSum queryImgInfoSum(Agent agent, Long resId) {
		String perKey = "TCHOLImg"+resId;
		ImgInfoSum imgInfoSum = null;
		imgInfoSum = (ImgInfoSum)redisService.get(perKey);
		
		try {
			if(null == imgInfoSum) {
				imgInfoSum = mongoTemplate1.findOne(new Query(Criteria.where("_id").is(resId)),ImgInfoSum.class);
				redisService.set(perKey, imgInfoSum, Long.valueOf(60 * 60 * 24 * 7));
			}
			
		} catch (Exception e) {
			throw new GSSException("查询酒店图片", "0220", "查询酒店图片异常, "+e.getMessage());
		}
		return imgInfoSum;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentWay> queryPaymentWay() {
		List<PaymentWay> list = null;
		String perKey = "HolCard";
		list = (List<PaymentWay>)redisService.get(perKey);
		
		if(null == list) {
			list = mongoTemplate1.find(new Query(Criteria.where("_id").ne("").ne(null)),PaymentWay.class);
			redisService.set(perKey, list);
		}
		return list;
	}

}

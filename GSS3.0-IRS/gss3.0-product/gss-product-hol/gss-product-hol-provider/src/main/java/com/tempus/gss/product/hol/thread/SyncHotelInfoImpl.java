package com.tempus.gss.product.hol.thread;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.vo.bqy.HotelId;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.product.hol.api.syn.ISyncHotelInfo;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.vo.Agent;

@Service
public class SyncHotelInfoImpl implements ISyncHotelInfo {
	@Autowired
	private ITCHotelSupplierService tcHotelSupplierService;
	
	@Autowired
	private IBQYHotelSupplierService bqyHotelSupplierService;
	
	@Autowired
	private IBQYHotelInterService bqyHotelService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Value("${bqy.count}")
	private int PAGE_SIZE;			//查询id数量

	@Override
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException {
		Future<TCResponse<ResBaseInfo>> queryHotelList = tcHotelSupplierService.queryHotelList(agent, hotelSearchReq);
		//Future<TCResponse<ResBaseInfo>> queryHotelList2 = iBQYHotelSupplierService.queryHotelList(hotelSearchReq);
		TCResponse<ResBaseInfo> tcResponse = null;
		//TCResponse<ResBaseInfo> bqyResponse = null;
		try {
			//Future<TCResponse<ResBaseInfo>> future = RpcContext.getContext().getFuture();
			
			while(true) {
				if(queryHotelList.isDone()) {
					if(StringUtil.isNotNullOrEmpty(queryHotelList)) {
						tcResponse = queryHotelList.get();
					}
					//bqyResponse = queryHotelList2.get();
					/*System.out.println("bqy Size: "+bqyResponse.getResponseResult().size());
					System.out.println(JsonUtil.toJson(bqyResponse.getResponseResult()));
					for(ResBaseInfo rs : bqyResponse.getResponseResult()) {
						System.out.println("bqy: "+rs.getResName());
					}*/
					break;
				}
			}
			/*if(StringUtil.isNullOrEmpty(hotelSearchReq.getAirRailWay())) {
				
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
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
		bqyHotelService.deleteMongoDBData();
		//拉取城市信息
		//bqyHotelService.pullCityDetail();
		//拉取酒店ID并存储MongoDB
		//获取BQY酒店ID
		//List<HotelId> hotelIdList = bqyHotelService.queryHotelIdList();
		//将获取的酒店ID保存到mongoDB中
		//mongoTemplate.insert(hotelIdList, HotelId.class);
		List<HotelId> hotelIdList = null;
		//获取ID数量
		//long totalHotelIdNum = hotelIdList.size();
		long totalHotelIdNum = mongoTemplate.count(new Query(), HotelId.class);
		long count = 1;
		if ((totalHotelIdNum / PAGE_SIZE) > 1) {
			count = totalHotelIdNum % PAGE_SIZE == 0 ? totalHotelIdNum / PAGE_SIZE : totalHotelIdNum / PAGE_SIZE + 1;
		}
		for (int i = 0; i < count; i++) {
			long lastIndex = ((i * PAGE_SIZE) + PAGE_SIZE) > totalHotelIdNum ? totalHotelIdNum : (i * PAGE_SIZE) + PAGE_SIZE;
			
			Query query = new Query().skip(i * PAGE_SIZE).limit(Integer.valueOf(String.valueOf(lastIndex)));
			hotelIdList = mongoTemplate.find(query, HotelId.class);
			//开启线程拉去酒店数量
			bqyHotelService.pullHotelInfoByIdList(hotelIdList);
		}
	}
	
}	

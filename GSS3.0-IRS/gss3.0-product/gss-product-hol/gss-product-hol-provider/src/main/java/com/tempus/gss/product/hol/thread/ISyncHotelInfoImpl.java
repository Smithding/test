package com.tempus.gss.product.hol.thread;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.service.IBQYHotelSupplierService;
import com.tempus.gss.product.hol.api.syn.ISyncHotelInfo;
import com.tempus.gss.product.hol.api.syn.ITCHotelSupplierService;
import com.tempus.gss.vo.Agent;

@Service
public class ISyncHotelInfoImpl implements ISyncHotelInfo {
	@Autowired
	private ITCHotelSupplierService iTCHotelSupplierService;
	
	@Autowired
	IBQYHotelSupplierService iBQYHotelSupplierService;

	@Override
	public TCResponse<ResBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) throws GSSException {
		Future<TCResponse<ResBaseInfo>> queryHotelList = iTCHotelSupplierService.queryHotelList(agent, hotelSearchReq);
		Future<TCResponse<ResBaseInfo>> queryHotelList2 = iBQYHotelSupplierService.queryHotelList(hotelSearchReq);
		TCResponse<ResBaseInfo> tcResponse = null;
		TCResponse<ResBaseInfo> bqyResponse = null;
		try {
			//Future<TCResponse<ResBaseInfo>> future = RpcContext.getContext().getFuture();
			
			while(true) {
				if(queryHotelList.isDone() && queryHotelList2.isDone()) {
					tcResponse = queryHotelList.get();
					bqyResponse = queryHotelList2.get();
					/*System.out.println("bqy Size: "+bqyResponse.getResponseResult().size());
					System.out.println(JsonUtil.toJson(bqyResponse.getResponseResult()));
					for(ResBaseInfo rs : bqyResponse.getResponseResult()) {
						System.out.println("bqy: "+rs.getResName());
					}*/
					break;
				}
			}
			if(StringUtil.isNullOrEmpty(hotelSearchReq.getAirRailWay())) {
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return tcResponse;
		//return null;
	}
	
}	

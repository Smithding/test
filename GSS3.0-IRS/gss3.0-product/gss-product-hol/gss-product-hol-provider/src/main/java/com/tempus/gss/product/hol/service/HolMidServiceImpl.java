package com.tempus.gss.product.hol.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.HolMidBaseInfo;
import com.tempus.gss.product.hol.api.entity.request.HotelListSearchReq;
import com.tempus.gss.product.hol.api.entity.response.TCResponse;
import com.tempus.gss.product.hol.api.service.IHolMidService;
import com.tempus.gss.vo.Agent;

/**
 * 酒店中间表Service实现
 */
@Service
public class HolMidServiceImpl implements IHolMidService {
	
	@Autowired
	private MongoTemplate mongoTemplate1;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public TCResponse<HolMidBaseInfo> queryHotelList(Agent agent, HotelListSearchReq hotelSearchReq) {
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
        TCResponse<HolMidBaseInfo> response = new TCResponse<HolMidBaseInfo>();
        Query query=new Query();
        Criteria criatira = new Criteria();
        int skip= (hotelSearchReq.getPageCount()-1)* (hotelSearchReq.getRowCount());
  		query.skip(skip);
  		query.limit(hotelSearchReq.getRowCount());
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

}

package com.tempus.gss.product.hol.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.product.hol.api.service.IResBrandInfoService;
import com.tempus.gss.vo.Agent;

/**
 *
 * ResBrandInfo 表数据服务层接口实现类
 *
 */
@Service
@EnableAutoConfiguration
public class ResBrandInfoServiceImpl  implements IResBrandInfoService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired 
	MongoTemplate mongoTemplate;
	
	
	@Cacheable(value = "ResBrandInfos", key = "#agent.owner",unless="")
	@Override
	public List<ResBrandInfo> findAll(Agent agent) {
		Query query=new Query();
		List<ResBrandInfo> resBrandInfos = mongoTemplate.find(query , ResBrandInfo.class);
		return resBrandInfos;
	}
	
	 
}
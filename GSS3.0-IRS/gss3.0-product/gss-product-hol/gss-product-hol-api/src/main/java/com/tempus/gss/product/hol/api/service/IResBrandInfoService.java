package com.tempus.gss.product.hol.api.service;

import java.util.List;

import com.tempus.gss.product.hol.api.entity.response.tc.ResBrandInfo;
import com.tempus.gss.vo.Agent;

/**
 *
 * ResBrandInfo 表数据服务层接口
 *
 */
public interface IResBrandInfoService {

	/**
	 * 查询酒店控润分页
	 * @param page
	 * @param requestWithActor
	 * @return
	 */
	public List<ResBrandInfo> findAll(Agent agent);
	
	 
}
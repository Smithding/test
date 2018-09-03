package com.tempus.gss.product.ift.dao.policy;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyQuery;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;


/**
 * 
 * <pre>
 * <b>匹配国际政策数据库操作接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月3日 下午2:29:59
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月3日 下午2:29:59    cz
 *         new file.
 * </pre>
 */
public interface IftQueryPolicyMapper extends AutoMapper<IftPolicy> {


	/**
	 *  查询航班政策
	 *  
	 * @param owner 归集单位
	 * @param query 查询条件
	 * @return List<IftPolicy> 政策结果集合
	 */
	List<IftPolicy> query(@Param("owner") int owner, FlightQuery query);
}

package com.tempus.gss.product.ift.dao.policy;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyQuery;

/**
 * 
 * <pre>
 * <b>国际政策数据库操作接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年8月27日 下午2:38:05
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月27日 下午2:38:05    cz
 *         new file.
 * </pre>
 */
public interface IftPolicyMapper extends AutoMapper<IftPolicy> {

	/**
	 * 添加国际政策
	 * 
	 * @param iftPolicy 国际政策信息
	 * @return int 保存记录数
	 */
	int insert(IftPolicy iftPolicy);
	
	/**
	 * 根据ID获取政策详情信息
	 * 
	 * @param owner 归集单位
	 * @param policyId 政策ID
	 * @return IftPolicy 政策详情
	 */
	IftPolicy find(@Param("owner") int owner,  @Param("policyId") long policyId);
	
	/**
	 *  分页查询国际政策数据
	 * 
	 * @param page 分页信息
	 * @param query 查询条件
	 * @return List<Long> 国际政策ID集合
	 */
	List<Long> query(RowBounds page, IftPolicyQuery query);
	
	/**
	 * 设置国际政策为无效
	 * 
	 * @param owner 归集信息
	 * @param policyId 国际政策ID
	 * @return int 修改行数记录
	 */
	int setInvalid(@Param("owner") int owner, @Param("policyId") long policyId, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime);
	
	/**
	 * 	修改国际政策状态
	 * 
	 * @param owner 归集信息
	 * @param policyIds 国际政策ID集合
	 * @param status 状态
	 * @return int 修改行数记录 
	 */
	int changeStatus(@Param("owner") int owner,  @Param("policyIds") Long[] policyIds, @Param("status") int status, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime);
	
	/**
	 * 	删除国际政策
	 * 
	 * @param owner 归集信息
	 * @param policyIds 国际政策ID集合
	 * @return int 删除行数记录 
	 */
	int delete(@Param("owner") int owner,  @Param("policyIds") Long[] policyIds, @Param("modifier") String modifier, @Param("modifyTime") Date modifyTime);
}

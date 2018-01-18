package com.tempus.gss.product.ift.dao.setting;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * <b>描述信息</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> Aaron
 * <b>Date:</b> 2016年10月11日  10:46
 * <b>Copyright:</b> Copyright 2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年10月11日  10:46   Aaron
 *         new file.
 * </pre>
 */
public interface ConfigsDao extends AutoMapper<IFTConfigs> {

	/**
	 * 获取客商配置列表
	 *
	 * @param owner 数据归集编号
	 * @param channelIds 客商IDS
	 * @return List<IFTConfigs> 客商配置列表
	 */
	List<IFTConfigs> find(@Param("owner") int owner, @Param("channelIds") Long[] channelIds);

	/**
	 * 查询对应的客商配置列表
	 * 
	 * @param configs 匹配条件
	 * @return List<IFTConfigs>客商配置列表
	 */
	List<IFTConfigs> query(IFTConfigs configs);

	/**
	 * 检查客商配置是否存在
	 * 
	 * @param configs 检查条件
	 * @return int 存在总数
	 */
	int exist(IFTConfigs configs);

	/**
	 * 添加客商配置.
	 *
	 * @param configs 客商配置
	 * @return int 成功添加数量
	 */
	int insert(IFTConfigs configs);

	/**
	 * 修改客商配置.
	 *
	 * @param configs 客商配置
	 * @return int 成功修改数量
	 */
	int update(IFTConfigs configs);

	/**
	 * 移除客商配置.
	 *
	 * @param configs 移除条件
	 * @return int 成功移除数量
	 */
	int delete(IFTConfigs configs);

}
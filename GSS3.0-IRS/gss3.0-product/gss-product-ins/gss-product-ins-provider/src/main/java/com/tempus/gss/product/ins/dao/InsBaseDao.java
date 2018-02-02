package com.tempus.gss.product.ins.dao;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

public interface InsBaseDao<K, T> {
	/*int deleteByPrimaryKey(String string);*/

	int insert(K record);

	int insertSelective(K record);

	K selectByPrimaryKey(Long l);

	int updateByPrimaryKeySelective(K record);

	int updateByPrimaryKey(K record);

	/*
	* 根据关键字查询对象 分页
	* */
	List<K> queryObjByKey(Page<K> page,T record);
	/*
	* 根据关键字查询j对象 分页
	* */
	List<K> queryAllObjByKey(Page<K> page,T record);
	/**
	 * 根据关键字查询对象 不分页
	 * @param record
	 * @return
	 */
	List<K> queryObjByKey(T record);

}

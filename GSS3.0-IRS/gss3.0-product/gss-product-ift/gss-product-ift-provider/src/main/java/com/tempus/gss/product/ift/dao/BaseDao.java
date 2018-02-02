package com.tempus.gss.product.ift.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

public interface BaseDao<K, T> {


	/*int deleteByPrimaryKey(String string);*/

	int insert(K record);

	int insertSelective(K record);

	K selectByPrimaryKey(Long l);

	int updateByPrimaryKeySelective(K record);

	int updateByPrimaryKey(K record);

	/**
	* 根据关键字查询对象
	* */
	List<K> queryObjByKey(Page<K> page,T record);



}

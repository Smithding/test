/**
 * BaseDao.java
 * com.tempus.gss.product.unp.dao
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年2月24日 		niepeng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.unp.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

/**
 * ClassName:BaseDao
 *
 * @author   niepeng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年2月24日		上午9:26:42
 *
 * @see 	 
 *  
 */
@Component("unpBaseDao")
public interface BaseDao<K,T> {
	
	int insert(K record);

	int insertSelective(K record);

	K selectByPrimaryKey(Long l);

	int updateByPrimaryKeySelective(K record);

	int updateByPrimaryKey(K record);

	/**
	* 根据关键字查询对象 分页
	*/
	List<K> queryObjByKey(Page<K> page,T record);

	/**
	 * 根据关键字查询对象 不分页
	 * @param record
	 * @return
	 */
	List<K> queryObjByKey(T record);
}


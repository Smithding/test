package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpGroupType;

public interface UnpGroupTypeMapper {
    int deleteByPrimaryKey(Long typeNo);

    int insert(UnpGroupType record);

    int insertSelective(UnpGroupType record);

    UnpGroupType selectByPrimaryKey(Long typeNo);

    int updateByPrimaryKeySelective(UnpGroupType record);

    int updateByPrimaryKey(UnpGroupType record);
}
package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpItemType;

public interface UnpItemTypeMapper {
    int deleteByPrimaryKey(Long itemTypeNo);
    
    int insert(UnpItemType record);
    
    int insertSelective(UnpItemType record);
    
    UnpItemType selectByPrimaryKey(Long itemTypeNo);
    
    int updateByPrimaryKeySelective(UnpItemType record);
    
    int updateByPrimaryKey(UnpItemType record);
}
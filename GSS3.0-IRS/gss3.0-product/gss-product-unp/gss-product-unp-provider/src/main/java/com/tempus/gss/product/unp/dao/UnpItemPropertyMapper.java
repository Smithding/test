package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpItemProperty;

public interface UnpItemPropertyMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(UnpItemProperty record);
    
    int insertSelective(UnpItemProperty record);
    
    UnpItemProperty selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(UnpItemProperty record);
    
    int updateByPrimaryKey(UnpItemProperty record);
}
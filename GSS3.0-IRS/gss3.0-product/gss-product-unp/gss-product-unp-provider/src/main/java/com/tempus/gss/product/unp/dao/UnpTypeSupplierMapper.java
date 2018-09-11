package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpTypeSupplier;

public interface UnpTypeSupplierMapper {
    int deleteByPrimaryKey(Long no);
    
    int insert(UnpTypeSupplier record);
    
    int insertSelective(UnpTypeSupplier record);
    
    UnpTypeSupplier selectByPrimaryKey(Long no);
    
    int updateByPrimaryKeySelective(UnpTypeSupplier record);
    
    int updateByPrimaryKey(UnpTypeSupplier record);
}
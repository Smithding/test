package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSaleRefundItem;

public interface UnpSaleRefundItemMapper {
    int deleteByPrimaryKey(Long itemId);
    
    int insert(UnpSaleRefundItem record);
    
    int insertSelective(UnpSaleRefundItem record);
    
    UnpSaleRefundItem selectByPrimaryKey(Long itemId);
    
    int updateByPrimaryKeySelective(UnpSaleRefundItem record);
    
    int updateByPrimaryKey(UnpSaleRefundItem record);
}
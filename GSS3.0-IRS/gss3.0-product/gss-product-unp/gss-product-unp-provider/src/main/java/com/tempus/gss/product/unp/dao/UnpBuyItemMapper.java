package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpBuyItem;

public interface UnpBuyItemMapper {
    int deleteByPrimaryKey(Long itemId);
    
    int insert(UnpBuyItem record);
    
    int insertSelective(UnpBuyItem record);
    
    UnpBuyItem selectByPrimaryKey(Long itemId);
    
    int updateByPrimaryKeySelective(UnpBuyItem record);
    
    int updateByPrimaryKey(UnpBuyItem record);
}
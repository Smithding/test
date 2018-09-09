package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSaleItem;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;

import java.util.List;

public interface UnpSaleItemMapper {
    int deleteByPrimaryKey(Long itemId);
    
    int insert(UnpSaleItem record);
    
    int insertSelective(UnpSaleItem record);
    
    UnpSaleItem selectByPrimaryKey(Long itemId);
    
    List<UnpSaleItem> selectItems(UnpOrderVo params);
    
    int updateByPrimaryKeySelective(UnpSaleItem record);
    
    int updateByPrimaryKey(UnpSaleItem record);
}
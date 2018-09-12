package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSaleRefundItem;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;

import java.util.List;

public interface UnpSaleRefundItemMapper {
    int deleteByPrimaryKey(Long itemId);
    
    int insert(UnpSaleRefundItem record);
    
    int insertSelective(UnpSaleRefundItem record);
    
    UnpSaleRefundItem selectByPrimaryKey(Long itemId);
    
    List<UnpSaleRefundItem> selectItems(UnpOrderQueryVo vo);
    
    int updateByPrimaryKeySelective(UnpSaleRefundItem record);
    
    int updateByPrimaryKey(UnpSaleRefundItem record);
}
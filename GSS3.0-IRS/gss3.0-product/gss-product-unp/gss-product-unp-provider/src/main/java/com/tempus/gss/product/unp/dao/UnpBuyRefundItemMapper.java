package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpBuyRefundItem;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;

import java.util.List;

public interface UnpBuyRefundItemMapper {
    int deleteByPrimaryKey(Long itemId);
    
    int insert(UnpBuyRefundItem record);
    
    int insertSelective(UnpBuyRefundItem record);
    
    UnpBuyRefundItem selectByPrimaryKey(Long itemId);
    
    List<UnpBuyRefundItem> selectItems(UnpOrderQueryVo vo);
    
    int updateByPrimaryKeySelective(UnpBuyRefundItem record);
    
    int updateByPrimaryKey(UnpBuyRefundItem record);
}
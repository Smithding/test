package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpBuyRefund;

/**
 * @author ZhangBro
 */
public interface UnpBuyRefundMapper {
    int deleteByPrimaryKey(Long buyRefundOrderNo);
    
    int insert(UnpBuyRefund record);
    
    int insertSelective(UnpBuyRefund record);
    
    UnpBuyRefund selectByPrimaryKey(Long buyRefundOrderNo);
    
    int updateByPrimaryKeySelective(UnpBuyRefund record);
    
    int updateByPrimaryKey(UnpBuyRefund record);
}
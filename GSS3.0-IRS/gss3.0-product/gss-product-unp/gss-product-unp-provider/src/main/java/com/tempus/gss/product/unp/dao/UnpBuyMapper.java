package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpBuy;

public interface UnpBuyMapper {
    int deleteByPrimaryKey(Long buyOrderNo);

    int insert(UnpBuy record);

    int insertSelective(UnpBuy record);

    UnpBuy selectByPrimaryKey(Long buyOrderNo);

    int updateByPrimaryKeySelective(UnpBuy record);

    int updateByPrimaryKey(UnpBuy record);
}
package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpBuyRefundItem;

public interface UnpBuyRefundItemMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(UnpBuyRefundItem record);

    int insertSelective(UnpBuyRefundItem record);

    UnpBuyRefundItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(UnpBuyRefundItem record);

    int updateByPrimaryKey(UnpBuyRefundItem record);
}
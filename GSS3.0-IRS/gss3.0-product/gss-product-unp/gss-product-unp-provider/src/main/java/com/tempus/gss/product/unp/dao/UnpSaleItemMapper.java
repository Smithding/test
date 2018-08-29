package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSaleItem;

public interface UnpSaleItemMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(UnpSaleItem record);

    int insertSelective(UnpSaleItem record);

    UnpSaleItem selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(UnpSaleItem record);

    int updateByPrimaryKey(UnpSaleItem record);
}
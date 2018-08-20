package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSale;

public interface UnpSaleMapper {
    int deleteByPrimaryKey(Long saleOrderNo);

    int insert(UnpSale record);

    int insertSelective(UnpSale record);

    UnpSale selectByPrimaryKey(Long saleOrderNo);

    int updateByPrimaryKeySelective(UnpSale record);

    int updateByPrimaryKey(UnpSale record);
}
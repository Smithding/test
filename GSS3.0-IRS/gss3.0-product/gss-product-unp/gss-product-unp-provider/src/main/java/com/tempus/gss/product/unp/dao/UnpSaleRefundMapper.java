package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpSaleRefund;

public interface UnpSaleRefundMapper {
    int deleteByPrimaryKey(Long saleRefundOrderNo);

    int insert(UnpSaleRefund record);

    int insertSelective(UnpSaleRefund record);

    UnpSaleRefund selectByPrimaryKey(Long saleRefundOrderNo);

    int updateByPrimaryKeySelective(UnpSaleRefund record);

    int updateByPrimaryKey(UnpSaleRefund record);
}
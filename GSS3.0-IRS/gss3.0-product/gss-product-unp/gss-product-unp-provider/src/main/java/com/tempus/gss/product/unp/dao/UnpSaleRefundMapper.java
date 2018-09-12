package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpSaleRefund;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;

public interface UnpSaleRefundMapper {
    int deleteByPrimaryKey(Long saleRefundOrderNo);
    
    int insert(UnpSaleRefund record);
    
    int insertSelective(UnpSaleRefund record);
    
    UnpSaleRefund selectByPrimaryKey(Long saleRefundOrderNo);
    
    Page<UnpSaleRefund> queryList(Page<UnpSaleRefund> wrapper, UnpOrderQueryVo vo);
    
    int updateByPrimaryKeySelective(UnpSaleRefund record);
    
    int updateByPrimaryKey(UnpSaleRefund record);
}
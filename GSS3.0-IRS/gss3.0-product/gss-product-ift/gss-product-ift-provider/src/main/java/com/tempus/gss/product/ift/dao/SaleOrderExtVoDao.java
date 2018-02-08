package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.vo.SaleChangeExtVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleOrderExtVoDao extends BaseDao<SaleOrderExt, SaleOrderExtVo> {
    /**
     * 根据交易单号查询销售单明细
     */
    public List<SaleOrderExtVo> selectByTraNo(Long transationOrderNo);

    /**
     * 根据销售单号查询销售单明细
     */
     SaleOrderExtVo selectBySaleNo(Long saleOrderNo);


}
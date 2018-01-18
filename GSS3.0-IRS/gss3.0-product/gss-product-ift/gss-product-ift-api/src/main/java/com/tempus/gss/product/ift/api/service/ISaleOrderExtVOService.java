package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.vo.SaleChangeExtVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;

import java.util.List;

public interface ISaleOrderExtVOService {
    /**
     * 根据交易单编号获取销售单拓展VO
     *
     * @return
     */
    List<SaleOrderExtVo> selectByTraNo(Long transationOrderNo) throws Exception;

    /**
     * 根据销售单编号获取销售单拓展VO
     *
     * @return
     */
    SaleOrderExtVo selectBySaleNo(Long saleOrderNo) throws Exception;



}

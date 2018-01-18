package com.tempus.gss.product.hol.dao;


import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.SaleChangeExt;
import com.tempus.gss.product.hol.api.entity.vo.SaleChangeExtVo;

import java.util.List;

/**
 *
 * SaleChangeExt 表数据库控制层接口
 *
 */
public interface SaleChangeExtMapper extends AutoMapper<SaleChangeExt> {
    /**
     * 根据条件查询退房订单列表
     * @param saleChangeExtVo
     * @return
     */
    List<SaleChangeExt> querySaleChangeExtList(Page<SaleChangeExt> page, SaleChangeExtVo saleChangeExtVo);
}
package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;

import java.util.List;

public interface UnpSaleMapper {
    int deleteByPrimaryKey(Long saleOrderNo);
    
    int insertSelective(UnpSale record);
    
    UnpSale selectByPrimaryKey(Long saleOrderNo);
    
    List<UnpSale> queryOrderList(Page<UnpSale> page, UnpOrderQueryVo param);
    
    int updateByPrimaryKeySelective(UnpSale record);
    
    int updateByPrimaryKey(UnpSale record);
}
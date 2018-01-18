package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PriceSpec;
import com.tempus.gss.product.ift.api.entity.vo.PriceSpecVo;

public interface PriceSpecDao extends BaseDao<PriceSpec, PriceSpecVo> {
    
    public PriceSpec selectAirLine(String airline);
    
    public int deleteByPrimaryKeySelective(PriceSpec price);
    
    public int validate(RequestWithActor<PriceSpec> price);
}

package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.Leg;

import java.util.List;

public interface ILegService {
    //通过销售单号查询航段集合
    List<Leg> selectLegBySaleOrderNo(Long saleOrderNo);
    //通过changeNo查询改签航段集合
    List<Leg> selectLegsBySaleChangeOrderNo(Long saleChangeNo);
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.entity.vo.QueryIBEDetailVo;
import com.tempus.gss.vo.Agent;

import java.text.ParseException;
import java.util.List;

/**
 * 国际机票查询服务接口.
 * 提供白屏查询结果.
 */
public interface IQueryService {

	 Page<QueryIBEDetail> query(Page<QueryIBEDetail> page,RequestWithActor<FlightQueryRequest> flightQueryRequest) ;

    void mappingPriceSpec(QueryIBEDetail queryIBEDetail, String customerTypeNo, Agent agent);


    Page<QueryIBEDetailVo> queryApi(Page<QueryIBEDetailVo> page, RequestWithActor<FlightQueryRequest> flightQueryRequest) ;
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;

/**
 * Created by 杨威 on 2017/9/19.
 */
public interface IMultipassMappingService {
    Page<QueryIBEDetail> query(Page<QueryIBEDetail> page, RequestWithActor<FlightQueryRequest> flightQueryRequest);
}

package com.tempus.gss.product.ift.api.service.search;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;

public interface NewQueryService {
	 public Page<QueryIBEDetail> query(Page<QueryIBEDetail> page, RequestWithActor<FlightQueryRequest> flightQueryRequest);
}

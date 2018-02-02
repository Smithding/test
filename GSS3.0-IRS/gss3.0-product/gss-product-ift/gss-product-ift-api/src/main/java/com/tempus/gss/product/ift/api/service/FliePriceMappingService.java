package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.vo.Agent;

import java.util.List;
import java.util.Map;

/**
 * Created by 杨威 on 2017/10/21.
 */
public interface FliePriceMappingService {
    void exchangeFilePrice(QueryIBEDetail queryIBEDetail, Agent agent);

    Map mappingPolicy(String goDep, String goArr, String backArr, String airline, String passengerType, List<Flight> goFlights, List<Flight> backFlights, Agent agent);
}

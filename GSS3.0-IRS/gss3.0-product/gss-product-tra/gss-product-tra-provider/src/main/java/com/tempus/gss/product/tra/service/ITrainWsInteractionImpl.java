package com.tempus.gss.product.tra.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.tra.api.entity.*;
import com.tempus.gss.product.tra.api.service.ITrainWsInteractionService;
import com.tempus.gss.product.tra.util.DataConvertUtils;
import com.tempus.gss.product.tra.util.PropertiesUtils;
import com.tempus.gss.product.tra.util.TrainHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by 杨威 on 2017/2/21.
 */
@Service
@EnableAutoConfiguration
public class ITrainWsInteractionImpl implements ITrainWsInteractionService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 查询火车票
     */
    @Override
    public TrainResponse TrainSearch(QueryTrainRequest request) throws Exception {
        log.info("创旅站站查询开始=============================");
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("fromStation", request.getFrom());
        map.put("toStation", request.getTo());
        map.put("trainDate", request.getDate().replaceAll("-", ""));
        if (!StringUtils.isEmpty(request.getTicketType())) {
            map.put("ticketType", request.getTicketType());
        }
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.searchUrl")) ? "http://openapi.17usoft.net/dts/TrainSearch/" : PropertiesUtils.get("train.searchUrl");
        url += "train?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        log.info("创旅车票查询返回：" + result);
        TrainResponse train = DataConvertUtils.getInstance().fromJson(result, TrainResponse.class);
        if (!train.getMsgCode().equals("100")) {
            log.info(train.getMsgCode() + "创旅：" + train.getMsgInfo());
        }
		log.info("获取TrainSearchResponse耗时："+String.valueOf((System.currentTimeMillis() - startTime)));
        log.info("创旅站站查询结束=============================");
        return train;
    }

    /**
     * 查询火车票车次
     */
    @Override
    public TrainNoResponse queryCheci(QueryCheciRequest request) throws Exception {
        log.info("创旅车次查询开始=============================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("trainNo", request.getCheci());
        map.put("trainDate", request.getDate().replaceAll("-", ""));
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.searchUrl")) ? "http://openapi.17usoft.net/dts/TrainSearch/" : PropertiesUtils.get("train.searchUrl");
        url += "TrainNo?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        log.info("创旅车次查询返回：" + result);
        TrainNoResponse noResponse = DataConvertUtils.getInstance().fromJson(result, TrainNoResponse.class);
        log.info("创旅车次查询结束=============================");
        return noResponse;
    }
    /**
     * 火车票下单
     */
    @Override
    public BookTicketsResponse orderAdd(String reqStr) throws Exception {
        log.info("创旅下单开始=============================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("param", reqStr);
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.orderUrl")) ? "http://openapi.17usoft.net/dto/trainOrder/" : PropertiesUtils.get("train.orderUrl");
        url += "bookTickets?";
        String result = TrainHttpClient.getInstance().getResForPost(map, url);
        log.info("创旅下单返回：" + result);
        BookTicketsResponse response = DataConvertUtils.getInstance().fromJson(result, BookTicketsResponse.class);
        log.info("创旅下单结束=============================");
        return response;
    }
    /**
     * 火车票详情
     */
    @Override
    public OrderDetailResponse orderDetailForCy(String orderId) throws Exception {
        log.info("创旅详情开始=============================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderNo", orderId);
        map.put("method", "orderDetail");
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.orderUrl")) ? "http://openapi.17usoft.net/dto/trainOrder/" : PropertiesUtils.get("train.orderUrl");
        url += "orderDetail?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        log.info("创旅详情返回：" + result);
        OrderDetailResponse response = DataConvertUtils.getInstance().fromJson(result, OrderDetailResponse.class);
        log.info("创旅详情结束=============================");
        return response;
    }
    /**
     * 取消火车票
     */
    @Override
    public CyOrderResponse cancelOrder(String orderNumber) throws Exception {
        log.info("创旅消单开始=============================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "cancelOrder");
        map.put("orderNo", orderNumber);
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.orderUrl")) ? "http://openapi.17usoft.net/dto/trainOrder/" : PropertiesUtils.get("train.orderUrl");
        url += "cancelOrder?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        log.info("创旅消单返回：" + result);
        CyOrderResponse response = DataConvertUtils.getInstance().fromJson(result, CyOrderResponse.class);
        log.info("创旅消单结束=============================");
        return response;
    }
    /**
     * 火车票出票
     */

    @Override
    public CyOrderResponse applyOrder(String orderId) throws Exception {
        log.info("创旅出票开始orderId=============================" + orderId);
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderNo", orderId);
        map.put("method", "applyIssueOrder");
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.orderUrl")) ? "http://openapi.17usoft.net/dto/trainOrder/" : PropertiesUtils.get("train.orderUrl");
        url += "applyIssueOrder?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        CyOrderResponse response = DataConvertUtils.getInstance().fromJson(result, CyOrderResponse.class);
        log.info("创旅出票结束orderId=============================" + orderId);
        return response;
    }
    /**
     * 火车票退票
     */
    @Override
    public CyOrderResponse orderReturn(OrderReturnReq request) throws Exception {
        log.info("创旅退票开始=============================");
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "applyRefundOrder");
        map.put("orderNo", request.getOrderNumber());
        map.put("passengerId", request.getPassengerId());
        String url = StringUtils.isEmpty(PropertiesUtils.get("train.orderUrl")) ? "http://openapi.17usoft.net/dto/trainOrder/" : PropertiesUtils.get("train.orderUrl");
        url += "applyRefundOrder?";
        String result = TrainHttpClient.getInstance().getResFortbe(map, url);
        log.info("创旅退票返回：" + result);
        CyOrderResponse response = DataConvertUtils.getInstance().fromJson(result, CyOrderResponse.class);
        log.info("创旅退票结束=============================");
        return response;
    }


}

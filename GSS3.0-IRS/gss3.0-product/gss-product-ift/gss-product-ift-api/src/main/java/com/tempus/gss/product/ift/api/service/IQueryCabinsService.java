package com.tempus.gss.product.ift.api.service;

import com.tempus.tbe.entity.ShoppingOutPut;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;

import java.text.ParseException;
import java.util.List;

/**
 * 查询单个航班更懂舱位的接口
 * 传入参数：FlightQueryRequest
 */
public interface IQueryCabinsService {
    
    /**
     * 查询更多舱位
     * @param flightQueryRequest
     * @return
     */
    List<QueryIBEDetail> query(RequestWithActor<FlightQueryRequest> flightQueryRequest);
    
    /**
     * 处理查询结果，匹配政策
     * @param shoppingOutPut
     * @return
     * @throws ParseException
     */
    QueryIBEDetail shoppingOutPutConvertQueryIBEDetail(ShoppingOutPut shoppingOutPut) throws ParseException;
    
    /**
     * 重写的查询结果处理
     * @param shoppingOutPut
     * @return
     * @throws ParseException
     */
    List<QueryIBEDetail> dealWithShoppingOut(ShoppingOutPut shoppingOutPut) throws ParseException;
}

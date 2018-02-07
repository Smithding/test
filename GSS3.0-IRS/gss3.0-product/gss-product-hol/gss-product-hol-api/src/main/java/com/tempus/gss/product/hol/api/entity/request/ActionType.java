package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;

/**
 * 千淘接口请求操作类型
 * Created by luofengjie on 2017/3/24.
 */

public enum ActionType implements Serializable {
    /**
     * 酒店搜索
     */
    CreateResponseMutil,
    /**
     * 酒店查询
     */
    CreateResponseByHotel,
    /**
     * 订单创建
     */
    SaveOrder,
    /**
     * 支付订单
     */
    PayOrder,
    /**
     * 取消订单
     */
    CancelOrder,
    /**
     * 查询订单状态
     */
    GetOrderStatus,
    /**
     * 查询城市列表
     */
    QueryCity,
    /**
     * 查询城市热点
     */
    GetHotPort,
    /**
     * 查询地铁
     */
    GetRainWay,
    /**
     * 查询订单详情
     */
    QueryOrder,
    /**
     * 申请退房
     */
    ApplyReturnOrder,
    /**
     * 订单列表
     */
    queryOrderListForQianTaiEx,
    /**
     * 查询城市列表
     */
    QueryCityInter,
    /**
     * 查询酒店图片
     */
    QueryHotelPicture,
    /**
     * 推送供应商信息
     */
    USBSupplier,
    /**
     * 推送价格策略
     */
    PricePolicy,

    /**
     * 查询酒店品牌
     */
    QueryBrand,
    /**
     * 订单人工确认成功
     */
    SuperSucess,
    /**
     *订单人工确认失败
     */
    SuperFail,
    /**
     *退房单人工确认成功
     */
    CheckReturnForce,
    /**
     *退房单人工确认失败
     */
    CheckReturnFail
}

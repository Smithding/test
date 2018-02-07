package com.tempus.gss.product.hol.api.util;

import java.util.HashMap;
import java.util.Map;

import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;

/**
 * 酒店订单状态值映射处理
 */
public class OrderStatusUtils {
    /**
     * 酒店订单状态值 本系统订单状态值     含义
     * 100             1             处理中
     * 101             2             预订成功
     * 102			   3	                  下单失败	
     * 200             4             已确认
     * 300             5             新单
     * 301             6             变价
     * 400             7             取消中
     * 401             8             取消成功
     * 500             9             满房
     * 550             10             测试
     * 600             11            拒单
     * 650			   12                        待审核
     * 700             13            在入住
     * 701             14            入住正常
     * 702             15            NOSHOW(确认未入住)
     * 800             16            LESSSHOW(提前离店)
     * 801			   17                        延住
     * 900             18            已结算
     * 950			   19                        订单结束
     */
    public static final Map<String, Integer> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put("100", 1);
        STATUS_MAP.put("101", 2);
        STATUS_MAP.put("102", 3);
        STATUS_MAP.put("200", 4);
        STATUS_MAP.put("300", 5);
        STATUS_MAP.put("301", 6);
        STATUS_MAP.put("400", 7);
        STATUS_MAP.put("401", 8);
        STATUS_MAP.put("500", 9);
        STATUS_MAP.put("550", 10);
        STATUS_MAP.put("600", 11);
        STATUS_MAP.put("650", 12);
        STATUS_MAP.put("700", 13);
        STATUS_MAP.put("701", 14);
        STATUS_MAP.put("702", 15);
        STATUS_MAP.put("800", 16);
        STATUS_MAP.put("801", 17);
        STATUS_MAP.put("900", 18);
        STATUS_MAP.put("950", 19);
    }

    /**
     * 返回本系统订单状态值，用于入库
     *
     * @param statusType
     * @return
     */
    public static Integer getStatus(OwnerOrderStatus statusType) {
        return STATUS_MAP.get(statusType.getKey());
    }
}

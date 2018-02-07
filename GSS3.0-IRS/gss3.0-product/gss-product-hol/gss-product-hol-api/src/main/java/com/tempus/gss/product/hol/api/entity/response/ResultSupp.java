package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 千淘酒店信息中的策略实体
 * Created by luofengjie on 2017/3/24.
 */
public class ResultSupp implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Dict<策略编号,Dict<房型编号,房型信息>>
     */
    @JSONField(name = "Rooms")
    private Map<String,Map<String,QTRoomType>> rooms;
    /**
     * Dict<策略编号,Dict<价格计划编号,价格计划信息>>
     */
    @JSONField(name = "Plans")
    private Map<String,Map<String,QTPricePlan>> plans;
    /**
     * Dict<策略编号,价格集合>
     */
    @JSONField(name = "Prices")
    private Map<String,List<ResultDetail>> prices;

    public Map<String, Map<String, QTRoomType>> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Map<String, QTRoomType>> rooms) {
        this.rooms = rooms;
    }

    public Map<String, List<ResultDetail>> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, List<ResultDetail>> prices) {
        this.prices = prices;
    }

    public Map<String, Map<String, QTPricePlan>> getPlans() {
        return plans;
    }

    public void setPlans(Map<String, Map<String, QTPricePlan>> plans) {
        this.plans = plans;
    }
}

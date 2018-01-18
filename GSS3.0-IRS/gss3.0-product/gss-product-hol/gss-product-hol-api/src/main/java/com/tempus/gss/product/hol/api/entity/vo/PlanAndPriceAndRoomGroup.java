package com.tempus.gss.product.hol.api.entity.vo;

import com.tempus.gss.product.hol.api.entity.response.QTPricePlan;
import com.tempus.gss.product.hol.api.entity.response.QTRoomType;
import com.tempus.gss.product.hol.api.entity.response.ResultDetail;

/**
 * 计划、价格、房型对象组合
 * Created by luofengjie on 2017/3/28.
 */
public class PlanAndPriceAndRoomGroup {
    private QTPricePlan qtPricePlan;
    private ResultDetail resultDetail;
    private QTRoomType qtRoomType;

    public QTPricePlan getQtPricePlan() {
        return qtPricePlan;
    }

    public void setQtPricePlan(QTPricePlan qtPricePlan) {
        this.qtPricePlan = qtPricePlan;
    }

    public ResultDetail getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(ResultDetail resultDetail) {
        this.resultDetail = resultDetail;
    }

    public QTRoomType getQtRoomType() {
        return qtRoomType;
    }

    public void setQtRoomType(QTRoomType qtRoomType) {
        this.qtRoomType = qtRoomType;
    }
}

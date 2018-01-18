package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 千淘通用请求入参
 * Created by luofengjie on 2017/3/28.
 */
public class QTCommonRequest implements Serializable{

    /**
     * 请求操作类型
     */
    @JSONField(name = "Action")
    private String action;

    /**
     * 请求入参json字符串
     */
    @JSONField(name = "RequestJson")
    private String requestJson;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }
}

package com.tempus.gss.product.hol.api.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 千淘接口请求响应基类
 * Created by luofengjie on 2017/3/24.
 */
public class BaseResponse implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 是否成功
     */
    @JSONField(name = "IsSuccess")
    private Boolean isSuccess;
    /**
     * 正常操作完成返回的json字符串
     */
    @JSONField(name = "ReturnJson")
    private String returnJson;
    /**
     * 错误提示
     */
    @JSONField(name = "ErrorMessage")
    private String errorMessage;

    public Boolean getIsSuccess() {
        return this.isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getReturnJson() {
        return returnJson;
    }

    public void setReturnJson(String returnJson) {
        this.returnJson = returnJson;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

package com.tempus.gss.product.hol.api.entity.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 千淘接口请求基类
 * Created by luofengjie on 2017/3/24.
 */

public class BaseRequest implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 分子公司编号(即当前ubp节点编号)
     */
    @JSONField(name = "CustomerCode")
    protected String customerCode;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

}

package com.tempus.gss.product.unp.api.entity.util;

/**
 * @author ZhangBro
 */
public class UnpResult<T> {
    
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    
    private Integer code;
    private String msg;
    private T entity;
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public T getEntity() {
        return entity;
    }
    
    public void setEntity(T entity) {
        this.entity = entity;
    }
    
    UnpResult() {
        this.code = FAILED;
        this.msg = "Failed By Default!";
        this.entity = null;
    }
}

package com.tempus.gss.product.unp.api.entity.util;

import javax.swing.*;
import java.io.Serializable;

/**
 * @author ZhangBro
 */
public class UnpResult<T> implements Serializable {
    
    public static final String DEFAULT_FAILED = "Failed By Default!";
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;
    private static final long serialVersionUID = -1302296694836057302L;
    
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
        if (this.msg != null) {
            if (DEFAULT_FAILED.equals(this.msg)) {
                this.msg = msg;
            } else {
                this.msg += ("|" + msg);
            }
        } else {
            this.msg = msg;
        }
    }
    
    public T getEntity() {
        return entity;
    }
    
    public void setEntity(T entity) {
        this.entity = entity;
    }
    
    public void success(String msg, T t) {
        this.setMsg(msg);
        this.setCode(SUCCESS);
        this.setEntity(t);
    }
    
    public void failed(String msg, T t) {
        this.setMsg(msg);
        this.setCode(FAILED);
        this.setEntity(t);
    }
    
    public UnpResult() {
        this.code = FAILED;
        this.msg = DEFAULT_FAILED;
        this.entity = null;
    }
}

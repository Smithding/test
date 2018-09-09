package com.tempus.gss.product.hol.api.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装AJAX调用以后的JSON返回值
 * 其中正确返回值:
 *  {state:0, data:返回数据, message:成功消息}
 * 错误返回值:
 *  {state:1, data:null, message:错误消息}
 */
public class ResponseResult<T> implements Serializable {

    /*
     * 处理状态
     * 1成功  0失败
     */
    private int status;
    /*
     * 消息
     */
    private String msg;
    /*
     * 数据
     */
    private List<T> list;


    public ResponseResult() {

    }

    public ResponseResult(int status, String msg, List<T> list) {
        this.status = status;
        this.msg = msg;
        this.list = list;
    }

    public ResponseResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 返回处理状态
     * @return 状态值 1表示成功;0表示失败
     */
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public List<T> getList() { return list; }
    public void setList(List<T> list) {
        this.list = list;
    }

}

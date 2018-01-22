package com.tempus.gss.product.ift.api.entity.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public class TicketSenderVo implements Serializable {
    private Long id;
    //用户Id
    private String userId;
    /***姓名*/
    private String name;
    /**状态*/
    private Integer status;
    /**操作人*/
    private String modifier;
    /**起始时间*/
    private String startTime;
    /**结束时间*/
    private String endTime;
    /**排序字段*/
    private String sort;
    //类型
    private String types;

    @Override
    public String toString() {
        return "TicketSenderVo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", modifier='" + modifier + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sort='" + sort + '\'' +
                ", types='" + types + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}

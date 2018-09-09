package com.tempus.gss.product.hol.api.entity.vo.bqy.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 酒店增量查询
 */
@Setter
@Getter
public class IncrQueryParam extends BaseQueryParam {

    @JSONField(name = "PageNo")
    private Integer pageNo;             //页数

    @JSONField(name = "PageSize")
    private Integer pageSize;           //页大小

    @JSONField(name = "UpdatTime")
    private String updatTime;          //更新时间

    @JSONField(name = "Timetype")
    private String timetype = EnumTimeType.HALF_HOUR.getKey(); //时间范围

}

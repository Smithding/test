package com.tempus.gss.product.hol.api.entity.vo.bqy.response;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class InrcHotelIdResult implements Serializable{

    private static final long serialVersionUID = -7644415242971483921L;

    @JSONField(name = "TotalItemCount")
    private Integer totalItemCount;

    @JSONField(name = "PageSize")
    private Integer pageSize;

    @JSONField(name = "PageNo")
    private Integer pageNo;

    @JSONField(name = "Items")
    private List<HotelIDModel> items;

    @JSONField(name = "TotalCount")
    private Integer totalCount;
}

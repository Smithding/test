package com.tempus.gss.product.hol.api.entity.vo.bqy.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class HotelIDModel implements Serializable {
    private static final long serialVersionUID = 8066877879989985129L;

    @JSONField(name = "HotelId")
    private String hotelId;
}

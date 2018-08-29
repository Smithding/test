package com.tempus.gss.product.hol.api.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *  Statement查询参数
 */
@Setter
@Getter
public class QueryStatementVo implements Serializable{
    private static final long serialVersionUID = 4104268811034013661L;

    /**
     *  选择时间 (1.入住日期; 2.预定日期; 3.离店日期;)
     */
    private Integer dateChange;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 销售单号
     */
    private Long saleOrderNo;

    /**
     * 供应商订单号
     */
    private String supplierOrderNo;

    /**
     * 入住人
     */
    private String checkinPerson;

    /**
     * 酒店名称
     */
    private String hotelName;
}

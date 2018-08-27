package com.tempus.gss.product.hol.api.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.tempus.gss.product.hol.api.report.annotation.util.ExcelImportField;
import com.tempus.gss.product.hol.api.report.annotation.validate.IsDate;
import com.tempus.gss.product.hol.api.report.annotation.validate.IsNum;
import com.tempus.gss.product.hol.api.report.annotation.validate.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店报表
 */
@Setter
@Getter
public class HolStatement implements Serializable {

    private static final long serialVersionUID = 206759349277506723L;

    private Long id;

    /**
     * 销售公司
     */
    @ExcelImportField(order = 1)
    @NotNull
    private String saleCorporation;

    /**
     * 结算公司
     */
    @ExcelImportField(order = 2)
    @NotNull
    private String balanceCorporation;

    /**
     * 产品类型
     */
    @ExcelImportField(order = 3)
    @NotNull
    private String productType;

    /**
     * 业务线
     */
    @ExcelImportField(order = 4)
    @NotNull
    private String businessLine;

    /**
     * 销售部门
     */
    @ExcelImportField(order = 5)
    @NotNull
    private String saleDepartment;

    /**
     * 客户名称
     */
    @ExcelImportField(order = 6)
    @NotNull
    private String customerName;

    /**
     * 客户编号
     */
    @ExcelImportField(order = 7)
    @NotNull
    private String customerNo;

    /**
     * 账单号
     */
    @ExcelImportField(order = 8)
    @NotNull
    private String billNo;

    /**
     * 订单来源
     */
    @ExcelImportField(order = 9)
    @NotNull
    private String orderSource;

    /**
     * 销售订单号
     */
    @ExcelImportField(order = 10)
    @NotNull
    @IsNum
    private Long saleOrderNo;

    /**
     * 产品订单号
     */
    @ExcelImportField(order = 11)
    @NotNull
    @IsNum
    private Long productOrderNo;

    /**
     * 城市
     */
    @ExcelImportField(order = 12)
    @NotNull
    private String city;

    /**
     * 酒店名称
     */
    @ExcelImportField(order = 13)
    @NotNull
    private String hotelName;

    /**
     * 交易时间
     */
    @ExcelImportField(order = 14)
    @NotNull
    @IsDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dealDate;

    /**
     * 入住人
     */
    @ExcelImportField(order = 15)
    @NotNull
    private String checkinPerson;

    /**
     * 入住房号
     */
    @ExcelImportField(order = 16)
    @NotNull
    private String checkinRoomCode;

    /**
     * 入住时间
     */
    @ExcelImportField(order = 17)
    @NotNull
    @IsDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkinDate;

    /**
     * 离店时间
     */
    @ExcelImportField(order = 18)
    @NotNull
    @IsDate
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkoutDate;

    /**
     * 晚数
     */
    @ExcelImportField(order = 19)
    @NotNull
    @IsNum
    private Integer nightNum;

    /**
     * 预定房间数
     */
    @ExcelImportField(order = 20)
    @NotNull
    @IsNum
    private Integer roomNum;

    /**
     * 间夜数
     */
    @ExcelImportField(order = 21)
    @NotNull
    @IsNum
    private Integer roomNightNum;

    /**
     * 房型
     */
    @ExcelImportField(order = 22)
    @NotNull
    private String roomType;

    /**
     * 其它
     */
    @ExcelImportField(order = 23)
    @NotNull
    private String other;

    /**
     * 其它费用
     */
    @ExcelImportField(order = 24)
    @NotNull
    @IsNum
    private BigDecimal otherPrice;

    /**
     * 采购单价
     */
    @ExcelImportField(order = 25)
    @NotNull
    @IsNum
    private BigDecimal purchaseUnitPrice;

    /**
     * 应付金额
     */
    @ExcelImportField(order = 26)
    @NotNull
    @IsNum
    private BigDecimal amountPayable;

    /**
     * 实付金额
     */
    @ExcelImportField(order = 27)
    @NotNull
    @IsNum
    private BigDecimal factAmountPayable;

    /**
     * 销售单价
     */
    @ExcelImportField(order = 28)
    @NotNull
    @IsNum
    private BigDecimal saleUnitPrice;

    /**
     * 应收金额
     */
    @ExcelImportField(order = 29)
    @NotNull
    @IsNum
    private BigDecimal amountReceivable;

    /**
     * 实收金额
     */
    @ExcelImportField(order = 30)
    @NotNull
    @IsNum
    private BigDecimal factAmountReceivable;

    /**
     * 毛利
     */
    @ExcelImportField(order = 31)
    @NotNull
    @IsNum
    private BigDecimal grossMargin;

    /**
     * 供应商订单号
     */
    @ExcelImportField(order = 32)
    @NotNull
    private String supplierOrderNo;

    /**
     *供应商结算方式
     */
    @ExcelImportField(order = 33)
    @NotNull
    private String supplierSettleType;

    /**
     *供应商支付方式
     */
    @ExcelImportField(order = 34)
    @NotNull
    private String supplierPayType;

    /**
     * 供应商支付流水
     */
    @ExcelImportField(order = 35)
    @NotNull
    private String supplierPayWater;

    /**
     * 备注
     */
    @ExcelImportField(order = 36)
    @NotNull
    private String remark;

    /**
     *佣金率
     */
    @ExcelImportField(order = 37)
    @NotNull
    @IsNum
    private BigDecimal commission;

    /**
     * 返佣金额
     */
    @ExcelImportField(order = 38)
    @NotNull
    @IsNum
    private BigDecimal rebatePrice;

}


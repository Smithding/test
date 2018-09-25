package com.tempus.gss.product.unp.api.service;

import java.util.Random;

/**
 * @author ZhangBro
 * 这里定义一些常量
 */
public abstract class BaseUnpService {
    /**
     * 失效
     */
    public final static int INVALID = 0;
    /**
     * 有效
     */
    public final static int VALID = 1;
    /**
     * 验证类型：所有
     */
    public final static int VALID_TYPE_ALL = 1;
    /**
     * 验证类型：销售
     */
    public final static int VALID_TYPE_SALE = 2;
    /**
     * 验证类型：采购
     */
    public final static int VALID_TYPE_BUY = 3;
    /**
     * 验证类型：销售变更
     */
    public final static int VALID_TYPE_SALE_REFUND = 4;
    /**
     * 验证类型：采购变更
     */
    public final static int VALID_TYPE_BUY_REFUND = 5;
    /**
     * 通用产品单号（长度-2）  实际长度为 （LENGTH_OF_NO+2）
     * Long最长 19位  并不是19位9
     */
    public final static int LENGTH_OF_NO = 16;
    
    /**
     * 通用产品交易单前缀
     */
    public final static String PREFIX_TRA = "91";
    /**
     * 通用产品销售单前缀
     */
    public final static String PREFIX_SALE = "92";
    /**
     * 通用产品采购单前缀
     */
    public final static String PREFIX_BUY = "93";
    /**
     * 通用产品销售变单前缀
     */
    public final static String PREFIX_SALE_REFUND = "94";
    /**
     * 通用产品采购变更单前缀
     */
    public final static String PREFIX_BUY_REFUND = "95";
    
    public Long getUnpNo(String prefix) {
        //92开头 18位Long型
        Random r = new Random();
        String source = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH_OF_NO; i++) {
            sb.append(source.charAt(r.nextInt(9)));
        }
        System.out.print("getUnpNo:" + (prefix + sb.toString()).length());
        return Long.valueOf(prefix + sb.toString());
    }
    
    //_____________导报表字段______________//
    /**
     * 供应商
     */
    public final String SUPPLIER_NAME = "supplierName";
    /**
     * 订单来源
     */
    public final String SOURCE = "source";
    /**
     * 产品名称
     */
    public final String UNP_NAME = "unpName";
    /**
     * 产品状态
     */
    public final String UNP_TYPE_STATUS = "unpTypeStatus";
    /**
     * 销售公司
     */
    public final String SALE_COM = "saleCom";
    /**
     * 客户名称
     */
    public final String CUSTOMER_NAME = "customerName";
    /**
     * 销售部门
     */
    public final String SALE_DEPT = "saleDept";
    /**
     * 交易日期
     */
    public final String ORDERING_TIME = "orderingTime";
    /**
     * 订单号
     */
    public final String THIRD_BUS_NO = "thirdBusNo";
    /**
     * 旅客姓名
     */
    public final String PASSENGERNAME = "passengerName";
    /**
     * 单价
     */
    public final String PRICE = "price";
    /**
     * 实收手续费
     */
    public final String FEE = "fee";
    /**
     * 应收金额
     */
    public final String PLAN_AMOUNT = "planAmount";
    /**
     * 实收金额
     */
    public final String ACTUAL_AMOUNT = "actualAmount";
    /**
     * 采购应付
     */
    public final String BUY_PLAN_AMOUNT = "buyPlanAmount";
    /**
     * 是否赠送
     */
    public final String FREE = "free";
    /**
     * 备注
     */
    public final String REMARK = "remark";
    /**
     * 出票人
     */
    public final String SALER = "saler";
    /**
     * 客服
     */
    public final String CUSTOMER_SERVICE = "customerService";
    /**
     * 业务线
     */
    public final String SERVICE_LINE = "serviceLine";
    /**
     * 乘车日期
     */
    public final String DEP_DATE = "depDate";
    /**
     * 车次
     */
    public final String TRA_NUM = "traNum";
    /**
     * 行程
     */
    public final String LEG = "leg";
    /**
     * 张数
     */
    public final String NUM = "num";
    /**
     * 收款方式
     */
    public final String PAY_WAY = "payWay";
    /**
     * 收款账号
     */
    public final String PAY_ACCOUNT = "payAccount";
    /**
     * 转帐明细备注
     */
    public final String PAY_REMARK = "payRemark";
    /**
     * 自动生成的大类
     */
    public final String GENERATE = "GENERATE";
    
}

package com.tempus.gss.product.unp.service;

import java.util.Random;

/**
 * @author ZhangBro
 * 这里定义一些常量
 */
public abstract class BaseUnpService {
    /**
     * 失效
     */
    final static int INVALID = 0;
    /**
     * 有效
     */
    final static int VALID = 1;
    /**
     * 验证类型：所有
     */
    final static int VALID_TYPE_ALL = 1;
    /**
     * 验证类型：销售
     */
    final static int VALID_TYPE_SALE = 2;
    /**
     * 验证类型：采购
     */
    final static int VALID_TYPE_BUY = 3;
    /**
     * 验证类型：销售变更
     */
    final static int VALID_TYPE_SALE_REFUND = 4;
    /**
     * 验证类型：采购变更
     */
    final static int VALID_TYPE_BUY_REFUND = 5;
    /**
     * 通用产品单号（长度-2）  实际长度为 （LENGTH_OF_NO+2）
     * Long最长 19位  并不是19位9
     */
    final static int LENGTH_OF_NO = 16;
    
    /**
     * 通用产品交易单前缀
     */
    final static String PREFIX_TRA = "91";
    /**
     * 通用产品销售单前缀
     */
    final static String PREFIX_SALE = "92";
    /**
     * 通用产品采购单前缀
     */
    final static String PREFIX_BUY = "93";
    /**
     * 通用产品销售变单前缀
     */
    final static String PREFIX_SALE_REFUND = "94";
    /**
     * 通用产品采购变更单前缀
     */
    final static String PREFIX_BUY_REFUND = "95";
    
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
}

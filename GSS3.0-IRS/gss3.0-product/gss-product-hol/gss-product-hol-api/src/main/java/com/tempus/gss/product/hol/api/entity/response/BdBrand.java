package com.tempus.gss.product.hol.api.entity.response;

import java.io.Serializable;

/**
 * 酒店品牌实体
 * Created by luofengjie on 2017/4/28.
 */
public class BdBrand implements Serializable{
    /**
     * 品牌编号
     */
    private String brandCode;
    /**
     * 品牌名称
     */
    private String brandName;

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}

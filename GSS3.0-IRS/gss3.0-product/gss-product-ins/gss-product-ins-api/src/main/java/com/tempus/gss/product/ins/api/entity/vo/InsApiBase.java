package com.tempus.gss.product.ins.api.entity.vo;

/**
 * Created by luofengjie on 2017/2/20.
 */
public class InsApiBase {

    // 客户编号
    private Long customerNo;
    public int current;
    public int size;

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

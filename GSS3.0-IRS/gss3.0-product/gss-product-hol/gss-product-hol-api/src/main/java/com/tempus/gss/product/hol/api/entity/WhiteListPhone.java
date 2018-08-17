package com.tempus.gss.product.hol.api.entity;

import java.io.Serializable;

/**
 * 电话白名单
 */
public class WhiteListPhone implements Serializable {

    private static final long serialVersionUID = 7781268223726284916L;

    private String id;      //id

    private String linkman; //联系人

    private String phone;   //电话

    private String remark;  //备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

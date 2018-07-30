package com.tempus.gss.product.ift.api.entity.iftVo;

import java.io.Serializable;

//查询使用用户实体Bean
public class IftUserVo implements Serializable {

    private static final long serialVersionUID = 1240312760106552689L;
    //用户主键ID
    private Long userId;
    //用户名称
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

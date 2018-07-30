package com.tempus.gss.product.ift.api.entity.iftVo;

//查询使用用户实体Bean
public class IftUserVo {
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

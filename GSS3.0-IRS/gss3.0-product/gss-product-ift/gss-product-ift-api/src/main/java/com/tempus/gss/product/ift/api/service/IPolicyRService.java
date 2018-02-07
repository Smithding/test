package com.tempus.gss.product.ift.api.service;

import com.tempus.gss.product.ift.api.entity.Policy;

import java.util.List;

/**
 * Created by 杨威 on 2017/12/11.
 */
public interface IPolicyRService  {
    void policyProduction(Policy policy);
    List<Policy> queryObjByODs(Policy policyVo);
}

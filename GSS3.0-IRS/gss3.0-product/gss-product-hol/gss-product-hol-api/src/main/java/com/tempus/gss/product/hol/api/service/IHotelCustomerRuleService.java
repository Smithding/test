package com.tempus.gss.product.hol.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HolCustomerRule;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 *  酒店规则
 */
public interface IHotelCustomerRuleService {

    /**
     *  分页查询
     * @param page
     * @param search
     * @return
     */
    Page<HolCustomerRule> list(Agent agent, Page page, HolCustomerRule search);

    /**
     *  查询(不分页)
     * @param page
     * @param search
     * @return
     */
    List<HolCustomerRule> list(Agent agent, HolCustomerRule search);

    /**
     *  保存
     * @param agent
     * @param customerRule
     */
    void save(Agent agent, HolCustomerRule customerRule);

    /**
     *  根据id查询
     * @param agent
     * @param id
     * @return
     */
    HolCustomerRule getById(Agent agent, Long id);

    /**
     * 根据id删除
     * @param agent
     * @param id
     */
    void deleteById(Agent agent, Long id);
}

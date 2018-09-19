package com.tempus.gss.product.hol.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.product.hol.api.entity.HolCustomerRule;
import com.tempus.gss.product.hol.api.service.IHotelCustomerRuleService;
import com.tempus.gss.vo.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;

/**
 *  客户规则接口实现
 */
@Service
public class HotelCustomerRuleServiceImpl implements IHotelCustomerRuleService {
    @Autowired
    private MongoTemplate mongoTemplate1;

    @Override
    public Page<HolCustomerRule> list(Agent agent, Page page, HolCustomerRule search) {
        Criteria criatira = new Criteria();
        addCriatira(search, criatira);
        int skip= (page.getCurrent()-1)* (page.getSize());
        Query query = new Query();
        query.skip(skip);
        query.limit(page.getSize());
        List<HolCustomerRule> customerRuleList = mongoTemplate1.find(query.addCriteria(criatira), HolCustomerRule.class);
        //总条数
        int count= (int)mongoTemplate1.count(query, HolCustomerRule.class);
        //总页数
        //int totalPage= (int)(count / page.getSize()+1);
        page.setRecords(customerRuleList);
        page.setTotal(count);
        return page;
    }

    @Override
    public List<HolCustomerRule> list(Agent agent, HolCustomerRule search) {
        Criteria criatira = new Criteria();
        addCriatira(search, criatira);
        Query query = new Query();
        List<HolCustomerRule> customerRuleList = mongoTemplate1.find(query.addCriteria(criatira), HolCustomerRule.class);
        return customerRuleList;
    }

    /**
     * 条件添加
     * @param search
     * @param criatira
     */
    private void addCriatira(HolCustomerRule search, Criteria criatira) {
        if (null != search) {
            String customerName = search.getCustomerName();
            String loginName = search.getLoginName();
            String payType = search.getPayType();
            if (StringUtil.isNotNullOrEmpty(customerName)) {
                String[] fbsArr = { "(", ")" };
                for (String key : fbsArr) {
                    if(customerName.contains(key)){
                        customerName = customerName.replace(key, "\\" + key);
                    }
                }
                criatira.and("customerName").regex("^.*"+customerName+".*$");
            }
            if (StringUtil.isNotNullOrEmpty(loginName)) {
                String[] fbsArr = { "(", ")" };
                for (String key : fbsArr) {
                    if(loginName.contains(key)){
                        loginName = loginName.replace(key, "\\" + key);
                    }
                }
                criatira.and("loginName").regex("^.*"+loginName+".*$");
            }
            if (StringUtil.isNotNullOrEmpty(payType)) {
                criatira.and("payType").regex("^.*"+payType+".*$");
            }
        }
    }

    @Override
    public void save(Agent agent, HolCustomerRule customerRule) {
        if (null == customerRule.getId()) {
            long id = IdWorker.getId();
            customerRule.setId(id);
        }
        mongoTemplate1.save(customerRule);
    }

    @Override
    public HolCustomerRule getById(Agent agent, Long id) {
        return mongoTemplate1.findOne(new Query(Criteria.where("_id").is(id)), HolCustomerRule.class);
    }

    @Override
    public void deleteById(Agent agent, Long id) {
        mongoTemplate1.remove(new Query(Criteria.where("_id").is(id)), HolCustomerRule.class);
    }
}

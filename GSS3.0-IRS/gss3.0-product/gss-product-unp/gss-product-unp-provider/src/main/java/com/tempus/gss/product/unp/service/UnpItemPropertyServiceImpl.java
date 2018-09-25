package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.unp.api.entity.UnpItemProperty;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpItemPropertyVo;
import com.tempus.gss.product.unp.api.service.BaseUnpService;
import com.tempus.gss.product.unp.api.service.UnpItemPropertyService;
import com.tempus.gss.product.unp.dao.UnpItemPropertyMapper;
import com.tempus.gss.vo.Agent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author ZhangBro
 */
@Service
public class UnpItemPropertyServiceImpl extends BaseUnpService implements UnpItemPropertyService {
    
    @Autowired
    private UnpItemPropertyMapper propertyMapper;
    
    @Override
    public UnpResult<String> updateProperties(Agent agent, List<UnpItemProperty> param) {
        return null;
    }
    
    @Override
    public UnpResult<String> addProperties(Agent agent, List<UnpItemProperty> param) {
        return null;
    }
    
    @Override
    public List<UnpItemProperty> getProperties(UnpItemPropertyVo param) {
        return propertyMapper.getAllPropertiersByItemCode(param.getCode());
        
    }
    
    @Override
    public UnpResult<Map<String, List<UnpItemProperty>>> getPropertiesByItems(Agent agent, List<UnpItemProperty> params) {
        return null;
    }
}

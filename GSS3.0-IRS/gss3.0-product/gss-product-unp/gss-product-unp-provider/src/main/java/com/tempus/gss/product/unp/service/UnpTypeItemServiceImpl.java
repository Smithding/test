package com.tempus.gss.product.unp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.service.UnpTypeItemService;
import com.tempus.gss.product.unp.dao.UnpGroupTypeMapper;
import com.tempus.gss.product.unp.dao.UnpItemTypeMapper;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UnpTypeItemServiceImpl extends BaseUnpService implements UnpTypeItemService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UnpItemTypeMapper itemTypeMapper;
    @Autowired
    private UnpGroupTypeMapper groupTypeMapper;
    
    @Override
    public UnpResult<List<UnpGroupType>> getGroups(Agent agent, Page<UnpGroupType> param) {
        return null;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public UnpResult<UnpGroupType> addGroup(Agent agent, UnpGroupType group) {
        UnpResult<UnpGroupType> result = new UnpResult<>();
        try {
            if (groupTypeMapper.insertSelective(group) > 0) {
                result.success("OK", group);
            } else {
                result.failed("Error", null);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error", e);
            result.failed(e.getMessage(), null);
            return result;
        }
    }
    
    @Override
    public UnpResult<List<UnpGroupType>> addGroups(Agent agent, List<UnpGroupType> groups) {
        return null;
    }
    
    @Override
    public UnpResult<List<UnpItemType>> getItems(Agent agent, Page<UnpItemType> param) {
        return null;
    }
    
    @Override
    public UnpResult<UnpItemType> addItem(Agent agent, UnpItemType item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        try {
            if (itemTypeMapper.insertSelective(item) > 0) {
                result.success("OK", item);
            } else {
                result.failed("Error", null);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error", e);
            result.failed(e.getMessage(), null);
            return result;
        }
    }
    
    @Override
    public UnpResult<List<UnpItemType>> addItems(Agent agent, List<UnpItemType> items) {
        return null;
    }
    
    @Override
    public UnpResult<UnpItemType> delete(Agent agent, UnpItemType item) {
        return null;
    }
}

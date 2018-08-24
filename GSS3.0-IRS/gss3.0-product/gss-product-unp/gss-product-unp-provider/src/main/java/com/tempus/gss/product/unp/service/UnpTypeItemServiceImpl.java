package com.tempus.gss.product.unp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;
import com.tempus.gss.product.unp.api.service.UnpTypeItemService;
import com.tempus.gss.product.unp.dao.UnpGroupTypeMapper;
import com.tempus.gss.product.unp.dao.UnpItemTypeMapper;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZhangBro
 */
@Service
public class UnpTypeItemServiceImpl extends BaseUnpService implements UnpTypeItemService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UnpItemTypeMapper itemTypeMapper;
    @Autowired
    private UnpGroupTypeMapper groupTypeMapper;
    
    @Override
    public Page<UnpGroupType> getGroups(Page<UnpGroupType> page, UnpGroupItemVo param) {
        try {
            if (null == page) {
                page = new Page<>();
            }
            if (null == param) {
                param = new UnpGroupItemVo();
            }
            List<UnpGroupType> list = groupTypeMapper.queryGroups(page, param);
            page.setRecords(list);
        } catch (Exception e) {
            logger.error("getGroups  Error", e);
        }
        return page;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public UnpResult<UnpGroupType> addGroup(Agent agent, UnpGroupType group) {
        UnpResult<UnpGroupType> result = new UnpResult<>();
        try {
            if (groupTypeMapper.insertSelective(group) > 0) {
                result.success("Add Group : OK", group);
            } else {
                result.failed("Error", null);
            }
        } catch (Exception e) {
            logger.error("addGroup Error", e);
            result.failed(e.getMessage(), null);
        }
        return result;
    }
    
    @Override
    public UnpResult<List<UnpGroupType>> addGroups(Agent agent, List<UnpGroupType> groups) {
        return null;
    }
    
    @Override
    public Page<UnpItemType> getItems(Page<UnpItemType> page, UnpGroupItemVo param) {
        try {
            List<UnpItemType> list = itemTypeMapper.queryItems(page, param);
            page.setRecords(list);
        } catch (Exception e) {
            logger.error("getItems  Error", e);
        }
        
        return page;
    }
    
    @Override
    public UnpResult<UnpItemType> addItem(Agent agent, UnpItemType item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        try {
            if (itemTypeMapper.insertSelective(item) > 0) {
                result.success("Add Item : OK", item);
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
    public UnpResult<String> delete(Agent agent, Boolean group, Long... itemNo) {
        return null;
    }
    
    @Override
    public UnpResult<String> delete(Agent agent, Boolean group, String... itemNo) {
        return null;
    }
    
    @Override
    public UnpResult<String> reuse(Agent agent, Boolean group, Long... itemNo) {
        return null;
    }
    
    @Override
    public UnpResult<String> reuse(Agent agent, Boolean group, String... itemNo) {
        return null;
    }
    
    @Override
    public <T> UnpResult<T> update(Agent agent, T... entities) {
        if (null == entities || entities.length == 0) {
            UnpResult
        }
        for (T entity : entities) {
            if (entity instanceof UnpGroupType) {
            
            } else if (entity instanceof UnpItemType) {
            
            } else {
            
            }
        }
    }
    
}

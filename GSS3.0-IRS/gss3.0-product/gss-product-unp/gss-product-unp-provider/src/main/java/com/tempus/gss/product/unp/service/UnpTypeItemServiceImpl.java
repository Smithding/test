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
    public UnpResult<List<UnpGroupType>> getGroups(Agent agent, Page<UnpGroupType> param) {
        return null;
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
    public UnpResult<UnpItemType> delete(Agent agent, Long itemNo) {
        UnpItemType updateEntity = new UnpItemType();
        updateEntity.setValid(0);
        updateEntity.setItemTypeNo(itemNo);
        return this.update(agent, updateEntity);
        
    }
    
    @Override
    public UnpResult<UnpItemType> reuse(Agent agent, Long itemNo) {
        UnpItemType updateEntity = new UnpItemType();
        updateEntity.setValid(1);
        updateEntity.setItemTypeNo(itemNo);
        return this.update(agent, updateEntity);
    }
    
    @Override
    public UnpResult<UnpItemType> delete(Agent agent, UnpItemType item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        if (null == item) {
            result.setCode(UnpResult.FAILED);
            result.setMsg("要删除的item不能为空");
            return result;
        }
        item.setValid(0);
        return this.update(agent, item);
    }
    
    @Override
    public UnpResult<UnpItemType> reuse(Agent agent, UnpItemType item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        if (null == item) {
            result.setCode(UnpResult.FAILED);
            result.setMsg("要删除的item不能为空");
            return result;
        }
        item.setValid(1);
        return this.update(agent, item);
    }
    
    @Override
    public UnpResult<UnpItemType> update(Agent agent, UnpItemType item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        try {
            if (null == item) {
                result.setCode(UnpResult.FAILED);
                result.setMsg("要删除的item不能为空");
                return result;
            }
            if (itemTypeMapper.updateByPrimaryKeySelective(item) > 0) {
                result.setCode(UnpResult.SUCCESS);
                result.setMsg("SUCCESS");
                return result;
            } else {
                result.setCode(UnpResult.FAILED);
                result.setMsg("无数据变更");
                return result;
            }
        } catch (Exception e) {
            logger.error("Error", e);
            result.setCode(UnpResult.FAILED);
            result.setMsg("Error : " + e.getMessage());
            return result;
        }
    }
}

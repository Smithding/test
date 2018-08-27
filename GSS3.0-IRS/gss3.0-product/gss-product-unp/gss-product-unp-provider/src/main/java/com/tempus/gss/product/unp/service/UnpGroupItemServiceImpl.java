package com.tempus.gss.product.unp.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;
import com.tempus.gss.product.unp.api.service.UnpGroupItemService;
import com.tempus.gss.product.unp.dao.UnpGroupTypeMapper;
import com.tempus.gss.product.unp.dao.UnpItemTypeMapper;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ZhangBro
 */
@Service
public class UnpGroupItemServiceImpl extends BaseUnpService implements UnpGroupItemService {
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
        if (!StringUtil.isNotBlank(group.getCode())) {
        
        }
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
    public <T> UnpResult<String> valid(Agent agent, Boolean group, int valid, T... params) {
        UnpResult<String> result = new UnpResult<String>();
        if (null == params || params.length == 0) {
            result.failed("参数为空", null);
            return result;
        }
        UnpGroupType[] entities = new UnpGroupType[params.length];
        try {
            if (null == group || !group) {
                for (int i = 0; i < params.length; i++) {
                    UnpGroupType u = new UnpGroupType();
                    if (params[i] instanceof Long) {
                        //如果是Long类型  表示传进ID
                        u.setTypeNo((Long) params[i]);
                    }
                    if (params[i] instanceof String) {
                        //如果是String类型  表示传进  唯一  CODE
                        u.setCode((String) params[i]);
                    }
                    u.setValid(valid);
                    entities[i] = u;
                }
                UnpResult<UnpGroupType> update = this.update(agent, entities);
                result.setMsg(update.getMsg());
                result.setCode(update.getCode());
            } else {
                UnpItemType[] items = new UnpItemType[params.length];
                for (int i = 0; i < params.length; i++) {
                    UnpItemType iu = new UnpItemType();
                    if (params[i] instanceof Long) {
                        //如果是Long类型  表示传进ID
                        iu.setItemTypeNo((Long) params[i]);
                    }
                    if (params[i] instanceof String) {
                        //如果是String类型  表示传进  唯一  CODE
                        iu.setCode((String) params[i]);
                    }
                    iu.setValid(valid);
                    items[i] = iu;
                }
                UnpResult<UnpItemType> update = this.update(agent, items);
                result.setMsg(update.getMsg());
                result.setCode(update.getCode());
            }
        } catch (Exception e) {
            logger.error("Error ", e);
            result.failed(e.getMessage(), null);
        }
        return result;
    }
    
    @Override
    public UnpResult<String> delete(Agent agent, Boolean group, Long... itemNo) {
        return this.valid(agent, group, INVALID, itemNo);
    }
    
    @Override
    public UnpResult<String> delete(Agent agent, Boolean group, String... codes) {
        return this.valid(agent, group, INVALID, codes);
    }
    
    @Override
    public UnpResult<String> reuse(Agent agent, Boolean group, Long... itemNo) {
        return this.valid(agent, group, VALID, itemNo);
    }
    
    @Override
    public UnpResult<String> reuse(Agent agent, Boolean group, String... codes) {
        return this.valid(agent, group, VALID, codes);
    }
    
    @Override
    public <T> UnpResult<T> update(Agent agent, T... entities) {
        UnpResult<T> result = new UnpResult<T>();
        if (null == entities || entities.length == 0) {
            result.failed("参数为空", null);
            return result;
        }
        int groupUpdated = 0;
        int itemUpdated = 0;
        try {
            for (T entity : entities) {
                if (entity instanceof UnpGroupType) {
                    ((UnpGroupType) entity).setModifier(agent.getAccount());
                    ((UnpGroupType) entity).setModifyTime(new Date());
                    UnpGroupType beforeUpdate = groupTypeMapper.selectByCode(((UnpGroupType) entity).getTypeNo());
                    if (null == beforeUpdate) {
                        continue;
                    }
                    int g = groupTypeMapper.updateByPrimaryKeySelective((UnpGroupType) entity);
                    if (g > 0) {
                        groupUpdated += g;
                    }
                    //如果改了大类  小类应该一起修改
                    UnpItemType itemToUpdate = new UnpItemType();
                    itemToUpdate.setGroupCode(((UnpGroupType) entity).getCode());
                    itemToUpdate.setName(((UnpGroupType) entity).getName());
                    itemToUpdate.setModifier(agent.getAccount());
                    itemToUpdate.setModifyTime(new Date());
                    //构建修改小类的参数
                    UnpItemType itemParam = new UnpItemType();
                    if (StringUtil.isNotBlank(((UnpGroupType) entity).getCode())) {
                        //如果大类修改了CODE
                        itemParam.setGroupCode(beforeUpdate.getCode());
                    }
                    
                    if (StringUtil.isNotBlank(((UnpGroupType) entity).getName())) {
                        //如果大类修改了NAME
                        itemParam.setGroupName(beforeUpdate.getName());
                    }
                    
                    //大类修改可见性
                    itemParam.setValid(((UnpGroupType) entity).getValid());
                    int i = itemTypeMapper.updateSelective(itemToUpdate, itemParam);
                    if (i > 0) {
                        itemUpdated += i;
                    }
                } else if (entity instanceof UnpItemType) {
                    ((UnpItemType) entity).setModifier(agent.getAccount());
                    ((UnpItemType) entity).setModifyTime(new Date());
                    UnpItemType itemBefore = null;
                    if (((UnpItemType) entity).getCode() == null) {
                        itemBefore = itemTypeMapper.selectByPrimaryKey(((UnpItemType) entity).getItemTypeNo());
                    } else {
                        itemBefore = itemTypeMapper.selectByCode(((UnpItemType) entity).getCode());
                    }
                    if (null == itemBefore) {
                        continue;
                    }
                    int i = itemTypeMapper.updateSelective((UnpItemType) entity, itemBefore);
                    if (i > 0) {
                        itemUpdated += i;
                    }
                } else {
                    result.failed("参数请填大类或者小类实体", null);
                }
            }
            result.success("共" + entities.length + "条，更新大类：" + groupUpdated + "条，小类：" + itemUpdated + "条！", null);
            logger.info("共{}条，更新大类：{}条，小类：{}条！", entities.length, groupUpdated, itemUpdated);
        } catch (Exception e) {
            logger.error("Error", e);
            result.failed(e.getMessage(), null);
        }
        return result;
    }
    
}

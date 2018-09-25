package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;
import com.tempus.gss.product.unp.api.service.BaseUnpService;
import com.tempus.gss.product.unp.api.service.UnpGroupItemService;
import com.tempus.gss.product.unp.dao.UnpGroupTypeMapper;
import com.tempus.gss.product.unp.dao.UnpItemTypeMapper;
import com.tempus.gss.util.NullableCheck;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public UnpGroupType getGroupByCode(String code) {
        try {
            if (NullableCheck.isNullOrEmpty(code)) {
                return null;
            }
            return this.groupTypeMapper.selectByCode(code);
        } catch (Exception e) {
            logger.error("getGroups  Error", e);
            return null;
        }
    }
    
    @Override
    public <T> UnpResult<T> add(Agent agent, UnpGroupItemVo param) {
        this.addGroup(agent, param);
        this.addItem(agent, param);
        return null;
    }
    
    @Override
    public UnpResult<Object> add(Agent agent, UnpGroupItemVo param, Boolean group) {
        UnpResult<Object> result = new UnpResult<>();
        if (group != null && group) {
            UnpResult<UnpGroupType> groupResult = this.addGroup(agent, param);
            result.setCode(groupResult.getCode());
            result.setMsg(groupResult.getMsg());
        } else {
            UnpResult<UnpItemType> itemResult = this.addItem(agent, param);
            result.setCode(itemResult.getCode());
            result.setMsg(itemResult.getMsg());
        }
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public UnpResult<UnpGroupType> addGroup(Agent agent, UnpGroupItemVo group) {
        UnpResult<UnpGroupType> result = new UnpResult<>();
        try {
            if (agent == null || group == null) {
                result.failed("参数 不能为空", null);
                return result;
            }
            if (NullableCheck.isNullOrEmpty(group.getCode())) {
                result.failed("产品代码 不能为空", null);
                return result;
            }
            if (NullableCheck.isNullOrEmpty(group.getName())) {
                result.failed("产品名称 不能为空", null);
                return result;
            }
            Long groupNo = IdWorker.getId();
            String creator = agent.getAccount();
            UnpGroupType groupToAdd = null;
            groupToAdd = this.getGroupByCode(group.getCode());
            if (null != groupToAdd) {
                result.failed("分类代码已经存在", groupToAdd);
                return result;
            }
            groupToAdd = new UnpGroupType();
            groupToAdd.setTypeNo(groupNo);
            groupToAdd.setCode(group.getCode());
            groupToAdd.setName(group.getName());
            groupToAdd.setOwner(agent.getOwner());
            groupToAdd.setRemark(group.getRemark());
            groupToAdd.setStatus(group.getStatus());
            groupToAdd.setValid(VALID);
            groupToAdd.setCreator(creator);
            groupToAdd.setCreateTime(new Date());
            if (groupTypeMapper.insertSelective(groupToAdd) > 0) {
                result.success("Add Group : OK", groupToAdd);
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
    public List<UnpItemType> getItems(String param) {
        List<UnpItemType> list = new ArrayList<>();
        try {
            list = itemTypeMapper.queryItemsByUncertainFactors(param);
        } catch (Exception e) {
            logger.error("getItems By Uncertain Factors  Error", e);
        }
        return list;
    }
    
    @Override
    public UnpItemType getItemByCode(String code) {
        try {
            if (NullableCheck.isNullOrEmpty(code)) {
                return null;
            }
            return this.itemTypeMapper.selectByCode(code);
        } catch (Exception e) {
            logger.error("getGroups  Error", e);
            return null;
        }
    }
    
    @Override
    public List<UnpItemType> getItemByName(String name) {
        try {
            if (NullableCheck.isNullOrEmpty(name)) {
                return null;
            }
            return this.itemTypeMapper.selectByName(name);
        } catch (Exception e) {
            logger.error("getGroups  Error", e);
            return null;
        }
    }
    
    @Override
    public UnpResult<UnpItemType> addItem(Agent agent, UnpGroupItemVo item) {
        UnpResult<UnpItemType> result = new UnpResult<>();
        try {
            if (agent == null || item == null) {
                result.failed("参数 不能为空", null);
                return result;
            }
            if (NullableCheck.isNullOrEmpty(item.getCode())) {
                result.failed("大类代码 不能为空", null);
                return result;
            }
            if (NullableCheck.isNullOrEmpty(item.getItemCode())) {
                result.failed("产品代码 不能为空", null);
                return result;
            }
            if (NullableCheck.isNullOrEmpty(item.getItemName())) {
                result.failed("产品名称 不能为空", null);
                return result;
            }
            String groupName = "";
            Long itemNo = IdWorker.getId();
            BigDecimal baseAmount = new BigDecimal(0);
            if (null != item.getBaseAmount()) {
                baseAmount = item.getBaseAmount();
            }
            if (NullableCheck.isNotNullAndEmpty(item.getCode())) {
                UnpGroupType group = this.getGroupByCode(item.getCode());
                if (group == null) {
                    result.failed("分类不存在", null);
                    return result;
                } else {
                    groupName = group.getName();
                }
            }
            UnpItemType itemToAdd = null;
            itemToAdd = this.getItemByCode(item.getItemCode());
            if (itemToAdd != null) {
                result.failed("要添加的产品代码已存在", itemToAdd);
                return result;
            }
            itemToAdd = new UnpItemType();
            itemToAdd.setItemTypeNo(itemNo);
            itemToAdd.setOwner(agent.getOwner());
            itemToAdd.setCode(item.getItemCode());
            itemToAdd.setName(item.getItemName());
            itemToAdd.setRemark(item.getRemark());
            itemToAdd.setBaseAmount(baseAmount);
            itemToAdd.setGroupCode(item.getCode());
            itemToAdd.setGroupName(groupName);
            itemToAdd.setImg(item.getImg());
            itemToAdd.setCreator(agent.getAccount());
            itemToAdd.setCreateTime(new Date());
            itemToAdd.setSortNo(item.getSortNo());
            itemToAdd.setValid(VALID);
            
            if (itemTypeMapper.insertSelective(itemToAdd) > 0) {
                result.success("Add Item : OK", itemToAdd);
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
    
    @SafeVarargs
    @Override
    public final <T> UnpResult<String> valid(Agent agent, Boolean group, Integer valid, T... params) {
        UnpResult<String> result = new UnpResult<String>();
        if (null == params || params.length == 0) {
            result.failed("参数为空", null);
            return result;
        }
        UnpGroupType[] entities = new UnpGroupType[params.length];
        try {
            if (null != group && group) {
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
    
    @SafeVarargs
    @Override
    public final <T> UnpResult<T> update(Agent agent, T... entities) {
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
                    UnpGroupType beforeUpdate = null;
                    if (((UnpGroupType) entity).getCode() == null) {
                        beforeUpdate = groupTypeMapper.selectByPrimaryKey(((UnpGroupType) entity).getTypeNo());
                    } else {
                        beforeUpdate = groupTypeMapper.selectByCode(((UnpGroupType) entity).getCode());
                    }
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
                    if (NullableCheck.isNotNullAndEmpty(((UnpGroupType) entity).getCode())) {
                        //如果大类修改了CODE
                        throw new GSSException("UNP", "00", "不可修改代号");
                        
                    }
                    itemToUpdate.setGroupCode(beforeUpdate.getCode());
                    if (NullableCheck.isNotNullAndEmpty(((UnpGroupType) entity).getName())) {
                        //如果大类修改了NAME
                        itemToUpdate.setGroupName(beforeUpdate.getName());
                    }
                    //大类修改可见性
                    itemToUpdate.setValid(((UnpGroupType) entity).getValid());
                    int i = itemTypeMapper.updateSelective(itemToUpdate);
                    if (i > 0) {
                        itemUpdated += i;
                    }
                } else if (entity instanceof UnpItemType) {
                    
                    UnpItemType itemBefore = null;
                    if (((UnpItemType) entity).getCode() == null) {
                        itemBefore = itemTypeMapper.selectByPrimaryKey(((UnpItemType) entity).getItemTypeNo());
                    } else {
                        itemBefore = itemTypeMapper.selectByCode(((UnpItemType) entity).getCode());
                    }
                    if (null == itemBefore) {
                        continue;
                    }
                    ((UnpItemType) entity).setModifier(agent.getAccount());
                    ((UnpItemType) entity).setModifyTime(new Date());
                    ((UnpItemType) entity).setItemTypeNo(itemBefore.getItemTypeNo());
                    int i = itemTypeMapper.updateByPrimaryKeySelective((UnpItemType) entity);
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

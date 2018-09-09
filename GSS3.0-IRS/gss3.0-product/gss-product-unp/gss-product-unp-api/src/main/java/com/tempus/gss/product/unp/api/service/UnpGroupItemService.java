package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * @author ZhangBro
 * <p>
 * unp产品大小类管理
 */
public interface UnpGroupItemService {
    /**
     * @param page
     *         agent
     * @param param
     *         param
     * @return
     */
    Page<UnpGroupType> getGroups(Page<UnpGroupType> page, UnpGroupItemVo param);
    
    UnpGroupType getGroupByCode(String code);
    
    /**
     * 等待被改写
     *
     * @param agent
     *         agent
     * @param param
     *         param
     * @return UnpResult
     */
    <T> UnpResult<T> add(Agent agent, UnpGroupItemVo param);
    
    UnpResult<Object> add(Agent agent, UnpGroupItemVo param, Boolean group);
    
    /**
     * @param agent
     *         agent
     * @param group
     *         group
     * @return UnpResult
     */
    UnpResult<UnpGroupType> addGroup(Agent agent, UnpGroupItemVo group);
    
    /**
     * @param agent
     *         agent
     * @param groups
     *         groups
     * @return UnpResult
     */
    UnpResult<List<UnpGroupType>> addGroups(Agent agent, List<UnpGroupType> groups);
    
    /**
     * @param page
     *         page
     * @param param
     *         param
     * @return UnpResult
     */
    Page<UnpItemType> getItems(Page<UnpItemType> page, UnpGroupItemVo param);
    
    /**
     * 模糊匹配查询小类，只通过一个字符串查询
     * @param param 可以是大类 code/name  也可以是 小类 code/name
     * @return
     */
    List<UnpItemType> getItems(String param);
    
    UnpItemType getItemByCode(String code);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     * @return UnpResult
     */
    UnpResult<UnpItemType> addItem(Agent agent, UnpGroupItemVo item);
    
    /**
     * 批量添加小类
     *
     * @param agent
     *         agent
     * @param items
     *         items
     * @return UnpResult
     */
    UnpResult<List<UnpItemType>> addItems(Agent agent, List<UnpItemType> items);
    
    /**
     * 修改记录可见性
     *
     * @param agent
     *         agent
     * @param group
     *         group
     * @param valid
     *         valid
     * @param itemNos
     *         itemNos
     * @return
     */
    <T> UnpResult<String> valid(Agent agent, Boolean group, Integer valid, T... itemNos);
    
    /**
     * 删除特定的小类（逻辑删除）
     *
     * @param agent
     *         agent
     * @param itemNo
     *         itemNo
     * @param group
     *         是否更新 group 为否时更新小类
     * @return UnpResult
     */
    UnpResult<String> delete(Agent agent, Boolean group, Long... itemNo);
    
    UnpResult<String> delete(Agent agent, Boolean group, String... itemNo);
    
    /**
     * 是否更新 group 为否时更新小类No
     * 重用特定的小类
     *
     * @param agent
     *         agent
     * @param itemNo
     *         itemNo
     * @param group
     *         是否更新 group 为否时更新小类s
     * @return UnpResult
     */
    UnpResult<String> reuse(Agent agent, Boolean group, Long... itemNo);
    
    UnpResult<String> reuse(Agent agent, Boolean group, String... itemNo);
    
    /**
     * 自动判断类型并更新
     * 删除特定的 类（逻辑删除）
     *
     * @param agent
     *         agent
     * @param entities
     *         entities
     * @return UnpResult
     */
    <T> UnpResult<T> update(Agent agent, T... entities);
    
}

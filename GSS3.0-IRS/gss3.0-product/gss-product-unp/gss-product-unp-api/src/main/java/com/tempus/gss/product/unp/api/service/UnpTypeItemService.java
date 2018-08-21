package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * @author ZhangBro
 * <p>
 * unp产品大小类管理
 */
public interface UnpTypeItemService {
    /**
     * @param agent
     *         agent
     * @param param
     *         param
     * @return
     */
    UnpResult<List<UnpGroupType>> getGroups(Agent agent, Page<UnpGroupType> param);
    
    /**
     * @param agent
     *         agent
     * @param group
     *         group
     * @return UnpResult
     */
    UnpResult<UnpGroupType> addGroup(Agent agent, UnpGroupType group);
    
    /**
     * @param agent
     *         agent
     * @param groups
     *         groups
     * @return UnpResult
     */
    UnpResult<List<UnpGroupType>> addGroups(Agent agent, List<UnpGroupType> groups);
    
    /**
     * @param agent
     *         agent
     * @param param
     *         param
     * @return UnpResult
     */
    UnpResult<List<UnpItemType>> getItems(Agent agent, Page<UnpItemType> param);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     * @return UnpResult
     */
    UnpResult<UnpItemType> addItem(Agent agent, UnpItemType item);
    
    /**
     * @param agent
     *         agent
     * @param items
     *         items
     *         批量添加小类
     * @return UnpResult
     */
    UnpResult<List<UnpItemType>> addItems(Agent agent, List<UnpItemType> items);
    
    /**
     * @param agent
     *         agent
     * @param itemNo
     *         itemNo
     *         删除特定的某个小类（逻辑删除）
     * @return UnpResult
     */
    UnpResult<UnpItemType> delete(Agent agent, Long itemNo);
    
    /**
     * @param agent
     *         agent
     * @param itemNo
     *         itemNo
     *         重用特定的某个小类（逻辑删除）
     * @return UnpResult
     */
    UnpResult<UnpItemType> reuse(Agent agent, Long itemNo);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     *         删除特定的某个小类（逻辑删除）
     * @return UnpResult
     */
    UnpResult<UnpItemType> delete(Agent agent, UnpItemType item);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     *         重用特定的某个小类（逻辑删除）
     * @return UnpResult
     */
    UnpResult<UnpItemType> reuse(Agent agent, UnpItemType item);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     *         删除特定的某个小类（逻辑删除）
     * @return UnpResult
     */
    UnpResult<UnpItemType> update(Agent agent, UnpItemType item);
    
}

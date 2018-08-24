package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;
import com.tempus.gss.vo.Agent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangBro
 * <p>
 * unp产品大小类管理
 */
@Service
public interface UnpTypeItemService {
    /**
     * @param page
     *         agent
     * @param param
     *         param
     * @return
     */
    Page<UnpGroupType> getGroups(Page<UnpGroupType> page, UnpGroupItemVo param);
    
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
     * @param page
     *         page
     * @param param
     *         param
     * @return UnpResult
     */
    Page<UnpItemType> getItems(Page<UnpItemType> page, UnpGroupItemVo param);
    
    /**
     * @param agent
     *         agent
     * @param item
     *         item
     * @return UnpResult
     */
    UnpResult<UnpItemType> addItem(Agent agent, UnpItemType item);
    
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
     * 是否更新 group 为否时更新小类
     * 删除特定的小类（逻辑删除）
     *
     * @param agent
     *         agent
     * @param itemNo
     *         itemNo
     * @param group
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

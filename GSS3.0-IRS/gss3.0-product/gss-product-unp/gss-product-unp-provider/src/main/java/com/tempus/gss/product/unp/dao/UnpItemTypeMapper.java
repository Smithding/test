package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;

import java.util.List;

public interface UnpItemTypeMapper {
    int deleteByPrimaryKey(Long itemTypeNo);
    
    int insertSelective(UnpItemType record);
    
    List<UnpItemType> queryItems(Page<UnpItemType> page, UnpGroupItemVo param);
    
    UnpItemType selectByPrimaryKey(Long itemTypeNo);
    
    UnpItemType selectByCode(String itemTypeNo);
    
    int updateByPrimaryKeySelective(UnpItemType record);
    
    int updateSelective(UnpItemType record, UnpItemType param);
    
}
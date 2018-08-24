package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpGroupType;
import com.tempus.gss.product.unp.api.entity.vo.UnpGroupItemVo;

import java.util.List;

public interface UnpGroupTypeMapper {
    int deleteByPrimaryKey(Long typeNo);
    
    int insertSelective(UnpGroupType record);
    
    UnpGroupType selectByPrimaryKey(Long typeNo);
    
    List<UnpGroupType> queryGroups(Page<UnpGroupType> page, UnpGroupItemVo param);
    
    int updateByPrimaryKeySelective(UnpGroupType record);
    
}
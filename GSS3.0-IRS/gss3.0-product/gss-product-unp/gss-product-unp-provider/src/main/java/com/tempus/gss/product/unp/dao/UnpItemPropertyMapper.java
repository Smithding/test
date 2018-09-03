package com.tempus.gss.product.unp.dao;

import com.tempus.gss.product.unp.api.entity.UnpItemProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UnpItemPropertyMapper {
    int deleteByPrimaryKey(Long id);
    
    int insert(UnpItemProperty record);
    
    int insertSelective(UnpItemProperty record);
    
    UnpItemProperty selectByPrimaryKey(Long id);
    
    List<UnpItemProperty> getAllPropertiersByItemCode(String itemCode);
    
    int updateByPrimaryKeySelective(UnpItemProperty record);
    
    int updateByPrimaryKey(UnpItemProperty record);
}
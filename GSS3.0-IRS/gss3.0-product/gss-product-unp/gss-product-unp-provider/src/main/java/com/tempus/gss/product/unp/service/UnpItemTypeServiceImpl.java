package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.unp.api.entity.UnpItemType;
import com.tempus.gss.product.unp.api.service.UnpItemTypeService;
import com.tempus.gss.product.unp.dao.UnpItemTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class UnpItemTypeServiceImpl implements UnpItemTypeService {
    @Autowired
    private UnpItemTypeMapper unpItemTypeMapper;
    @Override
    public List<UnpItemType> queryAllUnpItemType() {
        return unpItemTypeMapper.queryAllItems();
    }
}

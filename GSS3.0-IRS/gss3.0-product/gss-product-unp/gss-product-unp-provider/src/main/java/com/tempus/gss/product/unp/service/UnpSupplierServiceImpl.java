package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpTypeSupplier;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.SupplierVo;
import com.tempus.gss.product.unp.api.service.BaseUnpService;
import com.tempus.gss.product.unp.api.service.UnpSupplierService;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * @author ZhangBro
 */
@Service
public class UnpSupplierServiceImpl extends BaseUnpService implements UnpSupplierService {
    @Override
    public List<UnpTypeSupplier> getSuppliers(Agent agent, Page<SupplierVo> param) {
        return null;
    }
    
    @Override
    public UnpResult<UnpTypeSupplier> addSupplier(Agent agent, UnpTypeSupplier unpTypeSupplier) {
        return null;
    }
    
    @Override
    public UnpResult<List<UnpTypeSupplier>> addSuppliers(Agent agent, List<UnpTypeSupplier> unpTypeSuppliers) {
        return null;
    }
}

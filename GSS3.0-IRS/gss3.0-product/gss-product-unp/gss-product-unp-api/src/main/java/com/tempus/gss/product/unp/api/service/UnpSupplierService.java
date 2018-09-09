package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.UnpTypeSupplier;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.SupplierVo;
import com.tempus.gss.vo.Agent;

import java.util.List;

/**
 * @author ZhangBro
 */
public interface UnpSupplierService {
    /**
     * @param agent
     *         agent
     * @param param
     *         param
     *         根据参数 获取供应商列表
     * @return
     */
    List<UnpTypeSupplier> getSuppliers(Agent agent, Page<SupplierVo> param);
    
    /**
     * @param agent
     *         agent
     * @param unpTypeSupplier
     *         unpTypeSupplier
     *         添加unp供应商
     * @return UnpResult
     */
    UnpResult<UnpTypeSupplier> addSupplier(Agent agent, UnpTypeSupplier unpTypeSupplier);
    
    /**
     * @param agent
     *         agent
     * @param unpTypeSuppliers
     *         unpTypeSuppliers
     *         批量添加unp供应商
     * @return UnpResult
     */
    UnpResult<List<UnpTypeSupplier>> addSuppliers(Agent agent, List<UnpTypeSupplier> unpTypeSuppliers);
    
}

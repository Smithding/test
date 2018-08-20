package com.tempus.gss.product.unp.api.service;

import com.tempus.gss.product.unp.api.entity.UnpTypeSupplier;
import com.tempus.gss.product.unp.api.entity.vo.SupplierVo;
import com.tempus.gss.vo.Agent;

import java.util.List;

public interface UnpSupplierService {
    List<UnpTypeSupplier> getSuppliers(Agent agent, SupplierVo param);

}

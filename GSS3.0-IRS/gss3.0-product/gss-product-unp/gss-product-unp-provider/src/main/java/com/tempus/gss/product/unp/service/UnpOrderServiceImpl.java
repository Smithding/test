package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.product.unp.dao.*;
import com.tempus.gss.util.NullableCheck;
import com.tempus.gss.vo.Agent;
import org.ietf.jgss.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ZhangBro
 */
@Service
public class UnpOrderServiceImpl extends BaseUnpService implements UnpOrderService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UnpSaleMapper unpSaleMapper;
    @Autowired
    private UnpSaleItemMapper unpSaleItemMapper;
    @Autowired
    private UnpBuyMapper unpBuyMapper;
    @Autowired
    private UnpBuyItemMapper unpBuyItemMapper;
    @Autowired
    private UnpSaleRefundMapper unpSaleRefundMapper;
    @Autowired
    private UnpSaleRefundItemMapper unpSaleRefundItemMapper;
    @Autowired
    private UnpBuyRefundMapper unpBuyRefundMapper;
    @Autowired
    private UnpBuyRefundItemMapper unpBuyRefundItemMapper;
    
    @Override
    public UnpResult<UnpSale> createOrder(Agent agent, UnpOrderCreateVo orderCreateVo) {
        try {
            this.createValid(orderCreateVo);
            return null;
        } catch (Exception e) {
            logger.error("Error", e);
            return null;
        }
    }
    
    @Override
    public UnpResult<UnpSale> createSale(Agent agent, UnpOrderCreateVo request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createBuy(Agent agent, UnpOrderCreateVo request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createSaleRefund(Agent agent, UnpOrderCreateVo request) {
        return null;
    }
    
    @Override
    public UnpResult<UnpSale> createBuyRefund(Agent agent, UnpOrderCreateVo request) {
        return null;
    }
    
    private void createValid(UnpOrderCreateVo createVo) throws GSSException {
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
        if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getKey(), 0, "XXX 不能为空");}
    }
    
    @Override
    public Page<UnpSale> querySaleOrderList(Page<UnpSale> wrapper, UnpOrderVo param) {
        if (null == wrapper) {
            
            wrapper = new Page<>();
        }
        if (null == param) {
            param = new UnpOrderVo();
        }
        List<UnpSale> list = unpSaleMapper.queryOrderList(wrapper, param);
        wrapper.setRecords(list);
        return wrapper;
    }
}

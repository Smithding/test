package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.product.unp.api.entity.UnpBuy;
import com.tempus.gss.product.unp.api.entity.UnpBuyItem;
import com.tempus.gss.product.unp.api.entity.UnpSale;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.product.unp.dao.*;
import com.tempus.gss.system.vo.ApiGenerType;
import com.tempus.gss.util.NullableCheck;
import com.tempus.gss.vo.Agent;
import org.ietf.jgss.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
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
    public UnpResult<UnpBuy> createBuy(Agent agent, UnpOrderCreateVo request) {
        logger.info("创建采购单开始");
        UnpBuy unpBuy =null;
        List<UnpBuyItem> unpBuyItemList = null;
        UnpResult<UnpBuy> unpBuyUnpResult=new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        if (agent==null){
            logger.error("创建采购订单失败[]","agent不能为null");
        }
        try {
            long buyOrderNo = IdWorker.getId();
            //buy_items 表
            if (request!=null && request.getBuyItems()!=null){
                unpBuyItemList=request.getBuyItems();
                for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                    if (unpBuyItem!=null){
                        unpBuyItem.setBuyOrderNo(buyOrderNo);
                        unpBuyItem.setItemStatus(1);
                        planAmount=planAmount.add(unpBuyItem.getGroupAmount());
                        unpBuyItemMapper.insert(unpBuyItem);
                    }
                }
                //unp_buy 表
                unpBuy = request.getUnpBuy();
                unpBuy.setTraNo(request.getTraNo());
                unpBuy.setThirdBusNo(request.getTraNo());
                unpBuy.setSupplierId(request.getSupplierId());
                unpBuy.setSupplierType(request.getSupplierType());
                unpBuy.setRemark(request.getBuyRemark());
                unpBuy.setBuyOrderNo(buyOrderNo);
                unpBuy.setCreator(agent.getAccount());
                unpBuy.setModifier(agent.getAccount());
                unpBuy.setOwner(agent.getOwner());
                unpBuy.setPlanAmount(planAmount);
                unpBuy.setCreateTime(new Date());
                unpBuy.setModifyTime(new Date());
                unpBuy.setValid(1);
                unpBuy.setStatus(1);
                unpBuyMapper.insert(unpBuy);
            }
            unpBuyUnpResult.setCode(1);
            unpBuyUnpResult.setMsg("创建采购单成功");
            unpBuyUnpResult.setEntity(unpBuy);
        } catch (Exception e) {
            logger.error("创建采购单失败",e);
            unpBuyUnpResult.setCode(0);
            unpBuyUnpResult.setMsg("创建采购单失败");
            unpBuyUnpResult.setEntity(null);
            return  unpBuyUnpResult;
        }
        logger.info("创建采购单结束");
        return unpBuyUnpResult;
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

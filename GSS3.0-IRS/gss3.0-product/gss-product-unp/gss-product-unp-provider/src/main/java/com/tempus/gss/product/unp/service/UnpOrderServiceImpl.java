package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.CostType;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.product.unp.api.entity.*;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpCreateOrderRefund;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderUpdateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.service.UnpItemTypeService;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.product.unp.dao.*;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.vo.ApiGenerType;
import com.tempus.gss.util.NullableCheck;
import com.tempus.gss.vo.Agent;
import org.apache.ibatis.jdbc.Null;
import org.ietf.jgss.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
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
    @Reference
    IMaxNoService maxNoService;
    /* 采购单 */
    @Reference
    IBuyOrderService buyOrderService;
    @Reference
    UnpItemTypeService unpItemTypeService;
    /*应收应付*/
    @Reference
    IPlanAmountRecordService planAmountRecordService;
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
        if (agent==null){
            logger.error("创建采购订单失败[]","agent不能为null");
        }
        if (request==null){
            logger.error("创建采购订单失败[]","request不能为null");
        }
        if (request.getUnpBuy()==null){
            logger.error("创建采购订单失败[]","npBuyU不能为null");
        }

        if (request.getBuyItems()==null){
            logger.error("创建采购订单失败[]","buyItems不能为null");
        }
        UnpBuy unpBuy =null;
        List<UnpBuyItem> unpBuyItemList = null;
        UnpResult<UnpBuy> unpBuyUnpResult=new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        try {
            long buyOrderNo = maxNoService.generateBizNo("UNP_BUY_ORDER_NO",37);
            Long businessSignNo = IdWorker.getId();
            List<UnpItemType> unpItemTypes = unpItemTypeService.queryAllUnpItemType();
            String productName="";
            //buy_items 表
            if (request!=null && request.getBuyItems()!=null){
                unpBuyItemList=request.getBuyItems();
                for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                    if (unpBuyItem!=null){
                        unpBuyItem.setBuyOrderNo(buyOrderNo);
                        unpBuyItem.setItemStatus(1);
                        planAmount=planAmount.add(unpBuyItem.getGroupAmount());
                        unpBuyItemMapper.insertSelective(unpBuyItem);
                        for (UnpItemType unpItemType : unpItemTypes) {
                            if (unpItemType.getItemTypeNo().equals(unpBuyItem.getUnpType())){
                                productName+=unpItemType.getName()+",";
                                break;
                            }
                        }
                    }
                }
                //unp_buy 表
                unpBuy = request.getUnpBuy();
                unpBuy.setBuyOrderNo(buyOrderNo);
                unpBuy.setCreator(agent.getAccount());
                unpBuy.setModifier(agent.getAccount());
                unpBuy.setOwner(agent.getOwner());
                unpBuy.setPlanAmount(planAmount);
                unpBuy.setCreateTime(new Date());
                unpBuy.setModifyTime(new Date());
                unpBuy.setValid(1);//有效
                unpBuy.setStatus(1);//待处理
                unpBuy.setPayStatus(1);//待支付
                unpBuyMapper.insertSelective(unpBuy);
                //创建os_buy 主单记录
                BuyOrder buyOrder = new BuyOrder();
                buyOrder.setOwner(agent.getOwner());
                buyOrder.setBuyOrderNo(buyOrderNo);
                buyOrder.setGoodsType(9);//通用产品
                buyOrder.setGoodsSubType(2);//采购单
                buyOrder.setGoodsName(productName);
                buyOrder.setSupplierTypeNo(unpBuy.getSupplierType());
                buyOrder.setSupplierNo(unpBuy.getSupplierId());
                buyOrder.setBusinessSignNo(businessSignNo);
                buyOrder.setBuyChannelNo("UNP");
                buyOrder.setBsignType(1);//销采
                buyOrder.setBuyChildStatus(1);//待处理
                buyOrder.setPayable(planAmount);
                buyOrderService.create(agent,buyOrder);
                //创建应收
                CreatePlanAmountVO planAmountRecordType= new CreatePlanAmountVO();
                planAmountRecordType.setRecordNo(buyOrderNo);
                planAmountRecordType.setIncomeExpenseType(2);//收支类型 1 收，2 为支
                planAmountRecordType.setBusinessType(BusinessType.BUYORDER.getKey());
                planAmountRecordType.setRecordMovingType(CostType.FARE.getKey());
                planAmountRecordType.setPlanAmount(planAmount);//合计
                //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9 通用产品
                planAmountRecordType.setGoodsType(GoodsBigType.GENERAL.getKey());
                planAmountRecordService.create(agent, planAmountRecordType);
                unpBuyUnpResult.setCode(1);
                unpBuyUnpResult.setMsg("创建采购单成功");
                unpBuyUnpResult.setEntity(unpBuy);
                logger.info("创建采购单结束");
            }
        } catch (Exception e) {
            logger.error("创建采购单失败",e);
            unpBuyUnpResult.setCode(0);
            unpBuyUnpResult.setMsg("创建采购单失败");
            unpBuyUnpResult.setEntity(null);
            return  unpBuyUnpResult;
        }
        return unpBuyUnpResult;
    }
    
    @Override
    public UnpResult<UnpSale> createSaleRefund(Agent agent, UnpOrderCreateVo request) {
        return null;
    }

    @Override
    public UnpResult<UnpBuyRefund> createBuyRefund(Agent agent, UnpCreateOrderRefund request) {
        logger.info("创建采购退单开始");
        if (agent==null){
            logger.error("创建采购退单失败[]","agent不能为null");
        }
        if (request==null){
            logger.error("创建采购退单失败[]","request不能为null");
        }
        if (request.getUnpBuyRefund()==null){
            logger.error("创建采购退单失败[]","npBuyU不能为null");
        }

        if (request.getUnpBuyRefundItemList()==null){
            logger.error("采购订单更新失败[]","buyItems不能为null");
        }
        UnpBuyRefund unpBuyRefund = null;
        List<UnpBuyRefundItem> unpBuyRefundItemList = null;
        UnpResult<UnpBuyRefund> unpResult = new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        String productName="";
        try {
            long buyRefundOrderNo = maxNoService.generateBizNo("BUY_REFUND_ORDER_NO",37);
            List<UnpItemType> unpItemTypes = unpItemTypeService.queryAllUnpItemType();
            Long businessSignNo = IdWorker.getId();
            unpBuyRefundItemList=request.getUnpBuyRefundItemList();
            for (UnpBuyRefundItem unpBuyRefundItem : unpBuyRefundItemList) {
                unpBuyRefundItem.setBuyRefundOrderNo(buyRefundOrderNo);
                unpBuyRefundItem.setItemStatus(1);
                planAmount=planAmount.add(unpBuyRefundItem.getGroupAmount());
                unpBuyRefundItemMapper.insertSelective(unpBuyRefundItem);
                for (UnpItemType unpItemType : unpItemTypes) {
                    if (unpItemType.getItemTypeNo().equals(unpBuyRefundItem.getUnpType())){
                        productName+=unpItemType.getName()+",";
                        break;
                    }
                }
            }
            //更新unp_buy_refund
            unpBuyRefund = request.getUnpBuyRefund();
            unpBuyRefund.setBuyOrderNo(buyRefundOrderNo);
            unpBuyRefund.setCreator(agent.getAccount());
            unpBuyRefund.setModifier(agent.getAccount());
            unpBuyRefund.setOwner(agent.getOwner());
            unpBuyRefund.setRefundAmount(planAmount);
            unpBuyRefund.setCreateTime(new Date());
            unpBuyRefund.setModifyTime(new Date());
            unpBuyRefund.setValid(1);//有效
            unpBuyRefund.setStatus(1);//待处理
            unpBuyRefund.setPayStatus(1);//待支付
            unpBuyRefundMapper.insertSelective(unpBuyRefund);
            //创建os_buy 主单记录
            BuyOrder buyOrder = new BuyOrder();
            buyOrder.setOwner(agent.getOwner());
            buyOrder.setBuyOrderNo(unpBuyRefund.getBuyOrderNo());
            buyOrder.setGoodsType(9);//通用产品
            buyOrder.setGoodsSubType(3);//采购单
            buyOrder.setGoodsName(productName);
            buyOrder.setSupplierTypeNo(unpBuyRefund.getSupplierType());
            buyOrder.setSupplierNo(unpBuyRefund.getSupplierId());
            buyOrder.setBusinessSignNo(businessSignNo);
            buyOrder.setBuyChannelNo("UNP");
            buyOrder.setBsignType(1);//销采
            buyOrder.setBuyChildStatus(1);//待处理
            buyOrder.setPayable(planAmount);
            buyOrderService.create(agent,buyOrder);
            //创建应收
            CreatePlanAmountVO planAmountRecordType= new CreatePlanAmountVO();
            planAmountRecordType.setRecordNo(unpBuyRefund.getBuyOrderNo());
            planAmountRecordType.setIncomeExpenseType(1);//收支类型 1 收，2 为支
            planAmountRecordType.setBusinessType(BusinessType.BUYORDER.getKey());
            planAmountRecordType.setRecordMovingType(CostType.FARE.getKey());
            planAmountRecordType.setPlanAmount(planAmount);//合计
            //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9 通用产品
            planAmountRecordType.setGoodsType(GoodsBigType.GENERAL.getKey());
            planAmountRecordService.create(agent, planAmountRecordType);
            unpResult.setCode(1);
            unpResult.setMsg("创建采购退单成功");
            unpResult.setEntity(unpBuyRefund);
            logger.info("创建采购退单结束");
        } catch (com.tempus.gss.system.service.GSSException e) {
            unpResult.setCode(1);
            unpResult.setMsg("创建采购退单失败");
            unpResult.setEntity(unpBuyRefund);
            logger.error("创建采购退单失败",e);
        }
        return unpResult;
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

    @Override
    public UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderUpdateVo request) {
        logger.info("采购单更新开始");
        if (agent==null){
            logger.error("采购订单更新失败[]","agent不能为null");
        }
        if (request==null){
            logger.error("采购订单更新失败[]","request不能为null");
        }
        if (request.getUnpBuy()==null){
            logger.error("采购订单更新失败[]","npBuyU不能为null");
        }

        if (request.getBuyItems()==null){
            logger.error("采购订单更新失败[]","buyItems不能为null");
        }
        UnpBuy unpBuy =null;
        List<UnpBuyItem> unpBuyItemList = null;
        UnpResult<UnpBuy> unpBuyUnpResult=new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        try {
            if (request!=null){
                unpBuy= request.getUnpBuy();
                unpBuyItemList=request.getBuyItems();
                UnpBuy queryUnpBuy = unpBuyMapper.selectBySaleOrderNo(unpBuy.getSaleOrderNo());//判断原单是否可操作
                if (queryUnpBuy ==null && queryUnpBuy.getChangeType()==2){
                    throw new  Exception("不存在可操作订单");
                }
                //支付操作
                if (request.getOperationType()==1){
                    unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                    unpBuy.setModifier(agent.getAccount());
                    unpBuy.setModifyTime(new Date());
                    unpBuy.setStatus(3);
                    unpBuy.setChangeType(0);
                    unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                    for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                        unpBuyItem.setItemStatus(3);
                        unpBuyItem.setChangeType(0);
                        unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                    }
                }
                //退 暂时不管 原单状态
                if (request.getOperationType()==2){
                        //判断原单是否全部退
                    List<UnpBuyItem> queryUnpBuyItemList = unpBuyItemMapper.selectCompletedByBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                    if (queryUnpBuyItemList!=null && unpBuyItemList!=null){
                        //判断是否部分退
                        if (queryUnpBuyItemList.size() > unpBuyItemList.size()){
                            //部分退
                            unpBuy.setModifier(agent.getAccount());
                            unpBuy.setModifyTime(new Date());
                            unpBuy.setChangeType(3);
                            unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                            unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                            }else{
                            unpBuy.setModifier(agent.getAccount());
                            unpBuy.setModifyTime(new Date());
                            unpBuy.setChangeType(1);
                            unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                            unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                          }
                        }
                    for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                            unpBuyItem.setItemStatus(1);
                            unpBuyItem.setChangeType(1);
                            unpBuyItem.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                            unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);

                    }
                }
                //取消  退票取消事暂时不管原单状态
                if (request.getOperationType()==3){
                    if(queryUnpBuy.getStatus()==1){
                        //订单取消 原单状态 buyItem 表状态 都变为取消
                        unpBuy.setModifier(agent.getAccount());
                        unpBuy.setModifyTime(new Date());
                        unpBuy.setStatus(4);
                        unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                            unpBuyItem.setChangeType(0);
                            unpBuyItem.setItemStatus(4);
                            unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                        }
                    }
                    //退票取消 原单状态不变 buyIteam 变为 已完成状态
                    if (queryUnpBuy.getStatus()==3){
                        //查询是否已有小类完成退款
                        //查询为未完成的退款的小类
                        int hasRefund=0;//完场退款数量
                        int noRefund=0;//已退 未退款
                        List<UnpBuyItem> unpBuyRefundItemList = unpBuyItemMapper.selectRefundByBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        for (UnpBuyItem unpBuyItem : unpBuyRefundItemList) {
                            if (unpBuyItem.getItemStatus()==1){
                                noRefund++;
                            }
                            if (unpBuyItem.getItemStatus()==3){
                                hasRefund++;
                            }
                        }
                        //无完成退票 取消的小类数量 与 退票 小类数量相同时 更改原单changeType
                        if (hasRefund<1 && noRefund==unpBuyItemList.size()){
                            unpBuy.setModifier(agent.getAccount());
                            unpBuy.setModifyTime(new Date());
                            unpBuy.setChangeType(0);
                            unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                            unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        }else {
                            unpBuy.setModifier(agent.getAccount());
                            unpBuy.setModifyTime(new Date());
                            unpBuy.setChangeType(3);
                            unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                            unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        }
                        for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                            unpBuyItem.setChangeType(0);
                            unpBuyItem.setItemStatus(3);
                            unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                        }
                    }

                }
                //退款 待处理状态更换为 已处理
                if (request.getOperationType()==4){
                    for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                        unpBuyItem.setItemStatus(3);
                        unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                    }
                }
            }
            unpBuyUnpResult.setCode(1);
            unpBuyUnpResult.setMsg("创建采购单成功");
            unpBuyUnpResult.setEntity(unpBuy);
            logger.info("采购单更新结束");
        } catch (Exception e) {
            unpBuyUnpResult.setCode(1);
            unpBuyUnpResult.setMsg("采购单更新失败");
            unpBuyUnpResult.setEntity(null);
            logger.error("采购单更新异常",e);
            return  unpBuyUnpResult;
        }
        return unpBuyUnpResult;
    }
}

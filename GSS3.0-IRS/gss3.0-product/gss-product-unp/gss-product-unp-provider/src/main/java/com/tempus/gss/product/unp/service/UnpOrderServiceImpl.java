package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.entity.enums.*;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.unp.api.entity.*;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpRefundVo;
import com.tempus.gss.product.unp.api.service.UnpItemTypeService;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.product.unp.dao.*;
import com.tempus.gss.util.NullableCheck;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangBro
 */
@Service
public class UnpOrderServiceImpl extends BaseUnpService implements UnpOrderService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Reference
    ITransationOrderService transationOrderService;
    
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    
    @Reference
    ICertificateService certificateService;
    
    @Reference
    ISaleOrderService osSaleorderservice;
    
    @Reference
    UnpItemTypeService unpItemTypeService;
    
    @Autowired
    private UnpSaleMapper unpSaleMapper;
    
    @Autowired
    private UnpItemTypeMapper itemTypeMapper;
    
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
    IBuyOrderService buyOrderService;
    @Reference
    IBuyChangeService buyChangeService;
    @Reference
    ISaleChangeService saleChangeService;
    
    @Override
    public UnpSale getSaleOrderInfo(UnpOrderQueryVo params) {
        UnpSale unpSale = null;
        Page<UnpSale> page = this.querySaleOrderList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpSale = page.getRecords().get(0);
        if (unpSale == null) {
            return null;
        }
        UnpOrderQueryVo p = new UnpOrderQueryVo();
        p.setSaleOrderNo(unpSale.getSaleOrderNo());
        List<UnpSaleItem> saleItems = this.getItems(p);
        unpSale.setSaleItems(saleItems);
        return unpSale;
    }

    @Override
    public UnpSaleRefund getSaleRefundOrderInfo(UnpOrderQueryVo params) {
        UnpSaleRefund unpSaleRefund=null;
        Page<UnpSaleRefund> page = this.querySaleOrderRefundList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpSaleRefund = page.getRecords().get(0);
        if (unpSaleRefund == null) {
            return null;
        }
        UnpOrderQueryVo p = new UnpOrderQueryVo();
        p.setSaleChangeNo(unpSaleRefund.getSaleRefundOrderNo());
        List<UnpSaleRefundItem> saleRefundItems = this.getSaleRefundItems(p);
        unpSaleRefund.setItems(saleRefundItems);
        return unpSaleRefund;
    }

    @Override
    public UnpBuyRefund getBuyRefundOrderInfo(UnpOrderQueryVo params) {
        UnpBuyRefund unpBuyRefund = null;
        Page<UnpBuyRefund> page = this.queryBuyOrderRefundList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpBuyRefund = page.getRecords().get(0);
        if (unpBuyRefund == null) {
            return null;
        }
        UnpOrderQueryVo p = new UnpOrderQueryVo();
        p.setBuyChangeNo(unpBuyRefund.getBuyRefundOrderNo());
        List<UnpBuyRefundItem> buyRefundItems = this.getBuyRefundItems(p);
        unpBuyRefund.setItems(buyRefundItems);
        return unpBuyRefund;
    }

    @Override
    public UnpBuy getBuyOrderInfo(UnpOrderQueryVo params) {
        UnpBuy unpBuy = null;
        Page<UnpBuy> page = this.queryBuyOrderList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpBuy = page.getRecords().get(0);
        if (unpBuy == null) {
            return null;
        }
        UnpOrderQueryVo p = new UnpOrderQueryVo();
        p.setBuyOrderNo(unpBuy.getBuyOrderNo());
        List<UnpBuyItem> buyItems = this.getBuyItems(p);
        unpBuy.setBuyItems(buyItems);
        return unpBuy;
    }
    
    public UnpBuy getBuyInfos(UnpOrderQueryVo params) {
        UnpBuy unpBuy = this.unpBuyMapper.selectByPrimaryKey(params.getBuyOrderNo());
        if (unpBuy == null) {
            return null;
        }
        UnpOrderQueryVo p = new UnpOrderQueryVo();
        p.setBuyOrderNo(unpBuy.getBuyOrderNo());
        List<UnpBuyItem> unpBuyItems = this.getBuyItems(p);
        unpBuy.setBuyItems(unpBuyItems);
        return unpBuy;
    }
    
    @Override
    
    public UnpResult<UnpSale> createOrder(Agent agent, UnpOrderVo orderCreateVo) {
        UnpResult<UnpSale> result = new UnpResult<>();
        try {
            boolean createTra = true;
            this.createValid(orderCreateVo, VALID_TYPE_ALL);
            if (orderCreateVo.getUnpSale().getTraNo() != null) {
                TransationOrder to = transationOrderService.getTOrderByNo(agent, orderCreateVo.getUnpSale().getTraNo());
                if (to != null) {
                    createTra = false;
                }
            }
            if (createTra) {
                TransationOrder transationOrder = new TransationOrder();
                Long traNo = this.getUnpNo(PREFIX_TRA);
                transationOrder.setTransationOrderNo(traNo);
                transationOrder.setCustomerNo(orderCreateVo.getUnpSale().getCustomerNo());
                transationOrder.setCustomerTypeNo(orderCreateVo.getUnpSale().getCustomerType());
                transationOrder.setOrderingLoginName(agent.getAccount());
                transationOrder.setOwner(agent.getOwner());
                transationOrder.setPayStatus(PayStatus.NO_PAYMENT.getKey());
                transationOrder.setValid(VALID);
                transationOrder.setCreateTime(new Date());
                transationOrder.setSourceChannelNo("WEB");
                TransationOrder createTraOrder = transationOrderService.create(agent, transationOrder);
                if (createTraOrder == null) {
                    result.failed("创建交易单失败", null);
                    return result;
                }
                orderCreateVo.getUnpSale().setTraNo(traNo);
            }
            //交易单存在，不必创建
            //--------创建sale相关------------//
            UnpResult<UnpSale> saleResult = this.createSale(agent, orderCreateVo);
            if (saleResult.getCode() == 0) {
                return saleResult;
            }
            //将生成的销售单号填入采购单中
            orderCreateVo.getUnpBuy().setSaleOrderNo(saleResult.getEntity().getSaleOrderNo());
            //--------创建buy相关------------//
            UnpResult<UnpBuy> buyResult = this.createBuy(agent, orderCreateVo);
            if (buyResult.getCode() == 0) {
                result.failed(buyResult.getMsg(), null);
                return result;
            }
            result.success("创建订单成功", saleResult.getEntity());
        } catch (Exception e) {
            logger.error("Error", e);
            result.failed(e.getMessage(), null);
        }
        return result;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UnpResult<UnpSale> createSale(Agent agent, UnpOrderVo request) {
        UnpResult<UnpSale> result = new UnpResult<>();
        try {
            this.createValid(request, VALID_TYPE_SALE);
            UnpSale unpSale = request.getUnpSale();
            BigDecimal planAmount = new BigDecimal(0);
            List<UnpSaleItem> items = request.getSaleItems();
            List<UnpBuyItem> buyItems = request.getBuyItems();
            String goodsName = "通用产品";
            List<String> goods = new ArrayList<>();
            Long saleOrderNo = this.getUnpNo(PREFIX_SALE);
            //----------计算金额 创建items------------//
            for (UnpSaleItem o : items) {
                
                if (o.getUnpType() == null) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售小类编号 不能为空");
                }
                UnpItemType item = itemTypeMapper.selectByPrimaryKey(o.getUnpType());
                if (item == null) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售小类编号 不存在此编号对应的小类");
                }
                goods.add(item.getName());
                if (o.getGroupAmount() != null && o.getGroupAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //本小类总金额不为空且大于0，判断是否赠送
                    if (NullableCheck.isNotNullAndEmpty(o.getAdditionalInfo())) {
                        try {
                            String reg = "true|t|1|positive|ok|yes|y|是";
                            Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
                            ArrayList list = JSON.parse(o.getAdditionalInfo(), ArrayList.class);
                            list.forEach(m -> {
                                Map<String, Object> map = (HashMap<String, Object>) m;
                                if ("FREE".equalsIgnoreCase(map.get("eName").toString())) {
                                    String free = map.get("value").toString();
                                    Matcher matcher = pattern.matcher(free);
                                    if (matcher.matches()) {
                                        logger.info("小类【{}|{}|{}】被认定为【赠送】", o.getUnpType(), item.getCode(), o.getGroupAmount());
                                        result.setMsg("小类【" + o.getUnpType() + "|" + item.getCode() + "|" + o.getGroupAmount() + "】被认定为【赠送】");
                                        o.setGroupAmount(new BigDecimal(0));
                                    }
                                }
                            });
                            
                        } catch (Exception e) {
                            logger.error("AdditionalInfo  json转对象失败", e);
                            throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "明细列表中 小类【" + o.getUnpType() + "|" + item.getCode() + "】的AdditionalInfo信息  不是一个标准的JSON格式字符串");
                        }
                    }
                    planAmount = planAmount.add(o.getGroupAmount());
                }
                Long itemId = IdWorker.getId();
                o.setChangeType(EUnpConstant.ChangeType.DEFAULT.getKey());
                o.setItemId(itemId);
                o.setSaleOrderNo(saleOrderNo);
                o.setValid(VALID);
                if (unpSaleItemMapper.insertSelective(o) <= 0) {
                    result.setMsg("小类【" + o.getUnpType() + "|" + item.getCode() + "|" + o.getGroupAmount() + "】添加失败");
                } else {
                    request.getBuyItems().forEach(bi -> {
                        if (bi.getUnpType().equals(o.getUnpType())) {
                            bi.setSaleItemNo(o.getItemId());
                        }
                    });
                }
            }
            
            unpSale.setPlanAmount(planAmount);
            unpSale.setChangeType(EUnpConstant.ChangeType.DEFAULT.getKey());
            unpSale.setCreator(agent.getAccount());
            unpSale.setCreateTime(new Date());
            unpSale.setOwner(agent.getOwner());
            unpSale.setSaleOrderNo(saleOrderNo);
            unpSale.setStatus(EUnpConstant.OrderStatus.READY.getKey());
            unpSaleMapper.insertSelective(unpSale);
            //----------创建OS域销售单----------//
            SaleOrder saleOrder = new SaleOrder();
            saleOrder.setActorUser(agent.getAccount());
            saleOrder.setOwner(agent.getOwner());
            saleOrder.setTransationOrderNo(unpSale.getTraNo());
            saleOrder.setSaleOrderNo(unpSale.getSaleOrderNo());
            saleOrder.setOrderType(OrderType.SALEORDER.getKey());
            saleOrder.setSourceChannelNo("WEB");
            saleOrder.setCustomerNo(unpSale.getCustomerNo());
            saleOrder.setCustomerTypeNo(unpSale.getCustomerType());
            saleOrder.setOrderingLoginName(agent.getAccount());
            saleOrder.setOrderingTime(new Date());
            saleOrder.setGoodsType(GoodsBigType.GENERAL.getKey());
            saleOrder.setGoodsSubType(EgoodsSubType.SALE.getKey());
            saleOrder.setGoodsName(goodsName + ":" + String.join(",", goods));
            saleOrder.setBusinessSignNo(IdWorker.getId());
            saleOrder.setBsignType(BSignType.SALEBUY.getKey());
            saleOrder.setOrderChildStatus(unpSale.getStatus());
            SaleOrder saleOrderCreated = osSaleorderservice.create(agent, saleOrder);
            if (saleOrderCreated == null) {
                result.failed("创建OS销售单失败", null);
                return result;
            }
            PlanAmountRecord planAmountRecord = this.createPlanAmount(agent, unpSale.getSaleOrderNo(), unpSale.getPlanAmount(), BusinessType.SALEORDER, IncomeExpenseType.INCOME, CostType.FARE);
            if (planAmountRecord != null) {
                logger.info("创建销售应收{}", planAmountRecord.getPlanAmount());
                result.success("创建销售应收" + planAmountRecord.getPlanAmount(), unpSale);
            }
        } catch (GSSException e) {
            logger.error("Error", e);
            result.failed(e.getMessage(), null);
        }
        return result;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PlanAmountRecord createPlanAmount(Agent agent, Long recordNo, BigDecimal planAmount, BusinessType businessType, IncomeExpenseType incomeExpenseType, CostType costType) {
        CreatePlanAmountVO createPlanAmountVO = new CreatePlanAmountVO();
        createPlanAmountVO.setPlanAmount(planAmount);
        createPlanAmountVO.setGoodsType(GoodsBigType.GENERAL.getKey());
        createPlanAmountVO.setIncomeExpenseType(incomeExpenseType.getKey());
        createPlanAmountVO.setBusinessType(businessType.getKey());
        createPlanAmountVO.setRecordNo(recordNo);
        createPlanAmountVO.setRecordMovingType(costType.getKey());
        return planAmountRecordService.create(agent, createPlanAmountVO);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UnpResult<UnpBuy> createBuy(Agent agent, UnpOrderVo request) {
        this.createValid(request, VALID_TYPE_BUY);
        UnpResult<UnpBuy> unpBuyUnpResult = new UnpResult<>();
        logger.info("创建UNP采购单开始");
        if (agent == null) {
            logger.error("创建UNP采购订单失败{}", "agent不能为null");
            unpBuyUnpResult.failed("agent不能为null", null);
            return unpBuyUnpResult;
        }
        List<UnpBuyItem> unpBuyItemList = request.getBuyItems();
        BigDecimal planAmount = new BigDecimal(0);
        try {
            Long buyOrderNo = this.getUnpNo(PREFIX_BUY);
            Long businessSignNo = IdWorker.getId();
            List<UnpItemType> unpItemTypes = unpItemTypeService.queryAllUnpItemType();
            String productName = "通用产品";
            List<String> nameList = new ArrayList<>();
            //buy_items 表
            for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                if (unpBuyItem != null) {
                    if (unpBuyItem.getUnpType() == null) {
                        throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "小类编号 不能为空");
                    }
                    unpBuyItem.setItemId(IdWorker.getId());
                    unpBuyItem.setBuyOrderNo(buyOrderNo);
                    unpBuyItem.setItemStatus(1);
                    planAmount = planAmount.add(unpBuyItem.getGroupAmount());
                    unpBuyItemMapper.insertSelective(unpBuyItem);
                    unpItemTypes.forEach(i -> {
                        if (i.getItemTypeNo().equals(unpBuyItem.getUnpType())) {
                            nameList.add(i.getName());
                        }
                    });
                }
            }
            //unp_buy 表
            UnpBuy unpBuy = request.getUnpBuy();
            SaleOrder saleOrder = osSaleorderservice.getSOrderByNo(agent, unpBuy.getSaleOrderNo());
            if (saleOrder == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购单对应销售单不存在");
            }
            unpBuy.setTraNo(saleOrder.getTransationOrderNo());
            unpBuy.setBuyOrderNo(buyOrderNo);
            unpBuy.setSaleOrderNo(saleOrder.getSaleOrderNo());
            unpBuy.setCreator(agent.getAccount());
            unpBuy.setModifier(agent.getAccount());
            unpBuy.setOwner(agent.getOwner());
            unpBuy.setPlanAmount(planAmount);
            unpBuy.setCreateTime(new Date());
            unpBuy.setModifyTime(new Date());
            unpBuy.setValid(VALID);
            unpBuy.setStatus(EUnpConstant.OrderStatus.READY.getKey());
            unpBuy.setPayStatus(EUnpConstant.PayStatus.NOT_PAIED.getKey());
            unpBuyMapper.insertSelective(unpBuy);
            //创建os_buy 主单记录
            BuyOrder buyOrder = new BuyOrder();
            buyOrder.setOwner(agent.getOwner());
            buyOrder.setBuyOrderNo(buyOrderNo);
            //通用产品
            buyOrder.setGoodsType(9);
            //采购单
            buyOrder.setSaleOrderNo(unpBuy.getSaleOrderNo());
            buyOrder.setGoodsSubType(2);
            buyOrder.setGoodsName(productName + ":" + String.join(",", nameList));
            buyOrder.setSupplierTypeNo(unpBuy.getSupplierType());
            buyOrder.setSupplierNo(unpBuy.getSupplierId());
            buyOrder.setBusinessSignNo(businessSignNo);
            buyOrder.setBuyChannelNo("WEB");
            //销采
            buyOrder.setBsignType(BSignType.SALEBUY.getKey());
            //待处理
            buyOrder.setBuyChildStatus(unpBuy.getStatus());
            buyOrder.setPayable(planAmount);
            buyOrder.setSaleOrderNo(unpBuy.getSaleOrderNo());
            BuyOrder buyOrder1 = buyOrderService.create(agent, buyOrder);
            //创建应收
            PlanAmountRecord planAmountRecord = this.createPlanAmount(agent, buyOrderNo, planAmount, BusinessType.BUYORDER, IncomeExpenseType.EXPENSE, CostType.FARE);
            if (planAmountRecord != null) {
                logger.info("创建采购应收{}", planAmountRecord.getPlanAmount());
                unpBuyUnpResult.success("创建采购单成功", unpBuy);
            }
            logger.info("创建采购单结束");
        } catch (Exception e) {
            logger.error("创建采购单失败", e);
            unpBuyUnpResult.failed(e.getMessage(), null);
        }
        return unpBuyUnpResult;
    }
    
    @Override
    public UnpResult<UnpSaleRefund> createSaleRefund(Agent agent, UnpOrderVo request) {
        this.createValid(request, VALID_TYPE_SALE_REFUND);
        logger.info("创建销售采购退单开始");
        UnpSaleRefund unpSaleRefund = new UnpSaleRefund();
        UnpSaleRefundItem unpSaleRefundItem = new UnpSaleRefundItem();
        List<UnpSaleItem> saleItems = request.getSaleItems();
        UnpResult<UnpSaleRefund> unpResult = new UnpResult<>();
        BigDecimal planAmount = new BigDecimal(0);
        String productName = "通用产品";
        List<String> goods = new ArrayList<>();
        
        try {
            //获取采购单信息
            UnpSale unpSale = unpSaleMapper.selectByPrimaryKey(request.getUnpSale().getSaleOrderNo());
            if (unpSale == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购单不存在");
            }
            Long saleRefundOrderNo = this.getUnpNo(PREFIX_SALE_REFUND);
            unpSaleRefund.setSaleRefundOrderNo(saleRefundOrderNo);
            unpSaleRefund.setSaleOrderNo(unpSale.getSaleOrderNo());
            unpSaleRefund.setOwner(unpSale.getOwner());
            unpSaleRefund.setCustomerType(unpSale.getCustomerType());
            unpSaleRefund.setCustomerNo(unpSale.getCustomerNo());
            unpSaleRefund.setRemark(request.getUnpBuy().getRemark());
            unpSaleRefund.setTraNo(unpSale.getTraNo());
            unpSaleRefund.setChangeType(EUnpConstant.ChangeType.REFUND.getKey());
            unpSaleRefund.setPayStatus(EUnpConstant.PayStatus.NOT_PAIED.getKey());
            unpSaleRefund.setStatus(EUnpConstant.OrderStatus.READY.getKey());
            unpSaleRefund.setCreator(agent.getAccount());
            unpSaleRefund.setCreateTime(new Date());
            for (UnpSaleItem saleItem : saleItems) {
                if (saleItem.getItemId() == null) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购明细单编号不能为空");
                }
                UnpSaleItem unpSaleItem = unpSaleItemMapper.selectByPrimaryKey(saleItem.getItemId());
                UnpItemType unpItemType = itemTypeMapper.selectByPrimaryKey(unpSaleItem.getUnpType());
                goods.add(unpItemType.getName());
                planAmount = planAmount.add(unpSaleItem.getGroupAmount());
                unpSaleRefundItem.setItemId(IdWorker.getId());
                unpSaleRefundItem.setSaleRefundOrderNo(saleRefundOrderNo);
                unpSaleRefundItem.setUnpType(GoodsBigType.GENERAL.getKey());
                unpSaleRefundItem.setNum(unpSaleItem.getNum());
                unpSaleRefundItem.setChangeType(EUnpConstant.ChangeType.REFUND.getKey());
                unpSaleRefundItem.setGroupAmount(unpSaleItem.getGroupAmount());
                unpSaleRefundItem.setItemStatus(EUnpConstant.OrderStatus.READY.getKey());
                if (unpSaleRefundItemMapper.insertSelective(unpSaleRefundItem) < 0) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "明细列表中 小类【" + unpSaleRefundItem.getUnpType() + "】创建失败");
                }
            }
            //创建 refund 主表
            unpSaleRefund.setRefundAmount(planAmount);
            if (unpSaleRefundMapper.insertSelective(unpSaleRefund) < 0) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售退单创建失败");
            }
            //创建os_buyChange主单记录
            SaleChange saleChange = new SaleChange();
            saleChange.setSaleChangeNo(unpSaleRefund.getSaleRefundOrderNo());
            saleChange.setOwner(unpSaleRefund.getOwner());
            saleChange.setBusinessSignNo(IdWorker.getId());
            saleChange.setSaleOrderNo(unpSaleRefund.getSaleOrderNo());
            saleChange.setTransationOrderNo(unpSaleRefund.getTraNo());
            saleChange.setOrderChangeType(ChangeType.RETREAT.getKey());
            saleChange.setIncomeExpenseType(IncomeExpenseType.INCOME.getKey());
            saleChange.setChangeReason(unpSaleRefund.getRemark());
            saleChange.setPayStatus(PayStatus.NO_PAYMENT.getKey());
            saleChange.setStatus(BSignType.REFUND.getKey());
            saleChange.setChildStatus(unpSaleRefund.getStatus());
            saleChange.setCreateTime(unpSaleRefund.getCreateTime());
            saleChange.setGoodsType(GoodsBigType.GENERAL.getKey());
            saleChange.setGoodsSubType(EgoodsSubType.BUY_RETREAT.getKey());
            saleChange.setGoodsName(productName + ":" + String.join(",", goods));
            saleChange.setBsignType(BSignType.REFUND.getKey());
            SaleChange saleChange1 = saleChangeService.create(agent, saleChange);
            if (saleChange1 == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "os销售退单创建失败");
            }
            //创建应收
            CreatePlanAmountVO planAmountRecordType = new CreatePlanAmountVO();
            planAmountRecordType.setRecordNo(unpSaleRefund.getSaleRefundOrderNo());
            planAmountRecordType.setIncomeExpenseType(IncomeExpenseType.EXPENSE.getKey());
            planAmountRecordType.setBusinessType(BusinessType.SALECHANGE.getKey());
            planAmountRecordType.setRecordMovingType(CostType.FARE.getKey());
            planAmountRecordType.setPlanAmount(planAmount);
            //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9 通用产品
            planAmountRecordType.setGoodsType(GoodsBigType.GENERAL.getKey());
            PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, planAmountRecordType);
            if (planAmountRecord == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售退单创建退款应付失败");
            }
            unpResult.success("创建销售退单成功", unpSaleRefund);
            logger.info("创建销售退单结束");
        } catch (com.tempus.gss.system.service.GSSException e) {
            unpResult.failed("创建销售退单失败", unpSaleRefund);
            logger.error("创建销售退单失败", e);
            return unpResult;
        }
        return unpResult;
    }
    
    @Override
    public UnpResult<UnpBuyRefund> createBuyRefund(Agent agent, UnpOrderVo request) {
        this.createValid(request, VALID_TYPE_BUY_REFUND);
        logger.info("创建采购退单开始");
        UnpBuyRefund unpBuyRefund = new UnpBuyRefund();
        UnpBuyRefundItem unpBuyRefundItem = new UnpBuyRefundItem();
        List<UnpBuyItem> buyItems = request.getBuyItems();
        UnpResult<UnpBuyRefund> unpResult = new UnpResult<>();
        BigDecimal planAmount = new BigDecimal(0);
        String productName = "通用产品";
        List<String> goods = new ArrayList<>();
        
        try {
            //获取采购单信息
            UnpBuy unpBuy = unpBuyMapper.selectByPrimaryKey(request.getUnpBuy().getBuyOrderNo());
            if (unpBuy == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购单不存在");
            }
            Long buyRefundOrderNo = this.getUnpNo(PREFIX_BUY_REFUND);
            unpBuyRefund.setBuyRefundOrderNo(buyRefundOrderNo);
            unpBuyRefund.setBuyOrderNo(unpBuy.getBuyOrderNo());
            unpBuyRefund.setOwner(unpBuy.getOwner());
            unpBuyRefund.setSupplierType(unpBuy.getSupplierType());
            unpBuyRefund.setSupplierId(unpBuy.getSupplierId());
            unpBuyRefund.setRemark(request.getUnpBuy().getRemark());
            unpBuyRefund.setTraNo(unpBuy.getTraNo());
            unpBuyRefund.setChangeType(EUnpConstant.ChangeType.REFUND.getKey());
            unpBuyRefund.setThirdBusNo(unpBuy.getThirdBusNo());
            unpBuyRefund.setPayStatus(EUnpConstant.PayStatus.NOT_PAIED.getKey());
            unpBuyRefund.setStatus(EUnpConstant.OrderStatus.READY.getKey());
            unpBuyRefund.setCreator(agent.getAccount());
            unpBuyRefund.setCreateTime(new Date());
            for (UnpBuyItem buyItem : buyItems) {
                if (buyItem.getItemId() == null) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购明细单编号不能为空");
                }
                UnpBuyItem unpBuyItem = unpBuyItemMapper.selectByPrimaryKey(buyItem.getItemId());
                UnpItemType unpItemType = itemTypeMapper.selectByPrimaryKey(unpBuyItem.getUnpType());
                goods.add(unpItemType.getName());
                planAmount = planAmount.add(unpBuyItem.getGroupAmount());
                unpBuyRefundItem.setItemId(IdWorker.getId());
                unpBuyRefundItem.setBuyRefundOrderNo(buyRefundOrderNo);
                unpBuyRefundItem.setUnpType(GoodsBigType.GENERAL.getKey());
                unpBuyRefundItem.setNum(buyItem.getNum());
                unpBuyRefundItem.setNum(buyItem.getNum());
                unpBuyRefundItem.setChangeType(EUnpConstant.ChangeType.REFUND.getKey());
                unpBuyRefundItem.setGroupAmount(buyItem.getGroupAmount());
                unpBuyRefundItem.setItemStatus(EUnpConstant.OrderStatus.READY.getKey());
                int unpBuyRefundItemMapperRecord = unpBuyRefundItemMapper.insertSelective(unpBuyRefundItem);
                if (unpBuyRefundItemMapperRecord < 0) {
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "明细列表中 小类【" + unpBuyRefundItem.getUnpType() + "】创建失败");
                }
            }
            //创建 refund 主表
            if (unpBuy.getThirdBusNo() == null) {
                unpBuyRefund.setThirdBusNo(IdWorker.getId());
            }
            unpBuyRefund.setRefundAmount(planAmount);
            if (unpBuyRefundMapper.insertSelective(unpBuyRefund) < 0) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购退单创建失败");
            }
            //创建os_buyChange主单记录
            BuyChange buyChange = new BuyChange();
            buyChange.setBuyChangeNo(unpBuyRefund.getBuyRefundOrderNo());
            buyChange.setOwner(agent.getOwner());
            buyChange.setBusinessSignNo(IdWorker.getId());
            buyChange.setBuyOrderNo(unpBuyRefund.getBuyOrderNo());
            buyChange.setOrderChangeType(ChangeType.RETREAT.getKey());
            buyChange.setIncomeExpenseType(IncomeExpenseType.INCOME.getKey());
            buyChange.setChangeReason(unpBuyRefund.getRemark());
            buyChange.setPayStatus(PayStatus.NO_PAYMENT.getKey());
            buyChange.setStatus(BSignType.REFUND.getKey());
            buyChange.setChildStatus(unpBuyRefund.getStatus());
            buyChange.setCreateTime(unpBuyRefund.getCreateTime());
            buyChange.setGoodsType(GoodsBigType.GENERAL.getKey());
            buyChange.setGoodsSubType(EgoodsSubType.BUY_RETREAT.getKey());
            buyChange.setGoodsName(productName + ":" + String.join(",", goods));
            buyChange.setBsignType(BSignType.REFUND.getKey());
            BuyChange buyChange1 = buyChangeService.create(agent, buyChange);
            if (buyChange1 == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "os采购退单创建失败");
            }
            //创建应收
            CreatePlanAmountVO planAmountRecordType = new CreatePlanAmountVO();
            planAmountRecordType.setRecordNo(unpBuyRefund.getBuyRefundOrderNo());
            planAmountRecordType.setIncomeExpenseType(IncomeExpenseType.INCOME.getKey());
            planAmountRecordType.setBusinessType(BusinessType.BUYCHANGE.getKey());
            planAmountRecordType.setRecordMovingType(CostType.FARE.getKey());
            planAmountRecordType.setPlanAmount(planAmount);
            //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9 通用产品
            planAmountRecordType.setGoodsType(GoodsBigType.GENERAL.getKey());
            PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, planAmountRecordType);
            if (planAmountRecord == null) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购退单创建退款应付失败");
            }
            unpResult.success("创建采购退单成功", unpBuyRefund);
            logger.info("创建采购退单结束");
        } catch (com.tempus.gss.system.service.GSSException e) {
            unpResult.failed("创建采购退单失败", unpBuyRefund);
            logger.error("创建采购退单失败", e);
            return unpResult;
        }
        return unpResult;
    }
    
    private void createValid(UnpOrderVo createVo, Integer validType) throws GSSException {
        if (validType == VALID_TYPE_ALL) {
            if (NullableCheck.isNullOrEmpty(createVo)) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "创建请求参数 不能为空");}
        } else if (validType == VALID_TYPE_SALE) {
            if (NullableCheck.isNullOrEmpty(createVo.getUnpSale())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售总单信息 不能为空");}
            if (NullableCheck.isNullOrEmpty(createVo.getSaleItems())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售明细 至少一条");}
            if (NullableCheck.isNullOrEmpty(createVo.getUnpSale().getCustomerNo())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "客户编号 不能为空");}
        } else if (validType == VALID_TYPE_BUY) {
            if (NullableCheck.isNullOrEmpty(createVo.getUnpBuy())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购总单信息 不能为空");}
            if (NullableCheck.isNullOrEmpty(createVo.getUnpBuy().getSaleOrderNo())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购对应销售单号  不可同时为空");}
            if (NullableCheck.isNullOrEmpty(createVo.getUnpBuy().getSupplierId())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购供应商 不能为空");}
            if (NullableCheck.isNullOrEmpty(createVo.getUnpBuy().getBuyAccount())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购付款账号 不能为空");}
            if (NullableCheck.isNullOrEmpty(createVo.getBuyItems())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购明细 至少一条");}
        }
    }
    
    private void createValid(UnpRefundVo refundVo, Integer validType) throws GSSException {
        if (validType == VALID_TYPE_SALE_REFUND) {
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpSaleRefund())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售总单信息 不能为空");}
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpSaleRefund().getSaleOrderNo())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售总单单号 不能为空");}
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpSaleRefundItemList())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售明细 至少一条");}
        } else if (validType == VALID_TYPE_BUY_REFUND) {
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpBuyRefund())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购总单信息 不能为空");}
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpBuyRefund().getBuyOrderNo())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购总单单号 不能为空");}
            if (NullableCheck.isNullOrEmpty(refundVo.getUnpBuyRefundItemList())) {throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "采购明细 至少一条");}
        } else {
            throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "验证类型参数错误【1~5】");
        }
        
    }
    
    @Override
    public Page<UnpSale> querySaleOrderList(Page<UnpSale> wrapper, UnpOrderQueryVo param) {
        if (null == wrapper) {
            wrapper = new Page<>();
        }
        if (null == param) {
            param = new UnpOrderQueryVo();
        }
        List<UnpSale> list = null;
        try {
            list = unpSaleMapper.queryOrderList(wrapper, param);
        } catch (Exception e) {
            logger.error("Error", e);
        }
        wrapper.setRecords(list);
        return wrapper;
    }
    
    @Override
    public Page<UnpBuy> queryBuyOrderList(Page<UnpBuy> wrapper, UnpOrderQueryVo param) {
        if (null == wrapper) {
            wrapper = new Page<>();
        }
        if (null == param) {
            param = new UnpOrderQueryVo();
        }
        List<UnpBuy> list = null;
        try {
            list = unpBuyMapper.queryBuyOrderList(wrapper, param);
        } catch (Exception e) {
            logger.error("Error", e);
        }
        wrapper.setRecords(list);
        return wrapper;
    }
    
    @Override
    public Page<UnpSaleRefund> querySaleOrderRefundList(Page<UnpSaleRefund> wrapper, UnpOrderQueryVo param) {
        List<UnpSaleRefund> unpSaleRefunds = unpSaleRefundMapper.queryList(wrapper, param);
        wrapper.setRecords(unpSaleRefunds);
        return  wrapper;
    }
    
    @Override
    public Page<UnpBuyRefund> queryBuyOrderRefundList(Page<UnpBuyRefund> wrapper, UnpOrderQueryVo param) {
        List<UnpBuyRefund> unpBuyRefunds = this.unpBuyRefundMapper.queryList(wrapper, param);
        wrapper.setRecords(unpBuyRefunds);
        return  wrapper;
    }
    
    @Override
    public UnpSaleRefund querySaleOrderRefund(UnpOrderQueryVo param) {
        UnpSaleRefund unpSaleRefund = null;
        List<UnpSaleRefundItem> items = new ArrayList<>();
        Page<UnpSaleRefund> page = new Page<>(1, 1);
        UnpOrderQueryVo newVo = new UnpOrderQueryVo();
        newVo.setSaleChangeNo(param.getSaleChangeNo());
        newVo.setBuyChangeNo(param.getBuyChangeNo());
        page = this.querySaleOrderRefundList(page, newVo);
        if (page.getRecords() != null && page.getRecords().size() > 0) {
            unpSaleRefund = page.getRecords().get(0);
            if (unpSaleRefund != null) {
                param = new UnpOrderQueryVo();
                param.setSaleChangeNo(unpSaleRefund.getSaleRefundOrderNo());
                items = this.getSaleRefundItems(param);
                unpSaleRefund.setItems(items);
            }
        }
        logger.info("【UNP】查询单条销售退 parameters:{},itemSize:{}", param, items.size());
        return unpSaleRefund;
    }
    
    @Override
    public UnpBuyRefund queryBuyOrderRefund(UnpOrderQueryVo param) {
        UnpBuyRefund unpBuyRefund = null;
        List<UnpBuyRefundItem> items = new ArrayList<>();
        Page<UnpBuyRefund> page = new Page<>(1, 1);
        UnpOrderQueryVo newVo = new UnpOrderQueryVo();
        newVo.setSaleChangeNo(param.getSaleChangeNo());
        newVo.setBuyChangeNo(param.getBuyChangeNo());
        page = this.queryBuyOrderRefundList(page, newVo);
        if (page.getRecords() != null && page.getRecords().size() > 0) {
            unpBuyRefund = page.getRecords().get(0);
            if (unpBuyRefund != null) {
                param = new UnpOrderQueryVo();
                param.setSaleChangeNo(unpBuyRefund.getBuyRefundOrderNo());
                items = this.getBuyRefundItems(param);
                unpBuyRefund.setItems(items);
            }
        }
        logger.info("【UNP】查询单条采购退 parameters:{},itemSize:{}", param, items.size());
        return unpBuyRefund;
    }
    
    @Override
    public UnpResult<UnpSale> updateSale(Agent agent, UnpOrderVo request) {
        UnpResult<UnpSale> result = new UnpResult<>();
        UnpSale unpSale = request.getUnpSale();
        if (unpSale != null) {
            List<UnpSaleItem> saleItems = unpSale.getSaleItems();
            saleItems.forEach(item -> {
                if (item != null && item.getItemId() != null) {
                    if (!unpSale.getSaleOrderNo().equals(item.getSaleOrderNo())) {
                        throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "只可修改当前大单下的明细单");
                    }
                    item.setSaleOrderNo(null);
                    int updateFlag = this.unpSaleItemMapper.updateByPrimaryKeySelective(item);
                    if (updateFlag <= 0) {
                        logger.error("销售明细单【{}】修改失败,目标:{}", item.getItemId(), item);
                        result.setMsg("销售明细单【" + item.getItemId() + "】修改失败");
                    }
                }
            });
            int flag = this.unpSaleMapper.updateByPrimaryKeySelective(unpSale);
            if (flag > 0) {
                logger.info("更新销售单成功:{}，开始更新OS销售单", unpSale.getSaleOrderNo());
                osSaleorderservice.updateStatus(agent, unpSale.getSaleOrderNo(), unpSale.getStatus());
            }
        }
        return result;
    }
    
    @Override
    public UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderVo request) {
        UnpBuy unpBuy = null;
        List<UnpBuyItem> unpBuyItemList = null;
        UnpResult<UnpBuy> unpBuyUnpResult = new UnpResult<>();
        BigDecimal planAmount = new BigDecimal(0);
        logger.info("采购单更新开始");
        if (agent == null) {
            logger.error("采购订单更新失败{}", "agent不能为null");
            unpBuyUnpResult.failed("agent不能为null", null);
            return unpBuyUnpResult;
        }
        if (request == null) {
            logger.error("采购订单更新失败{}", "request不能为null");
            unpBuyUnpResult.failed("agent不能为null", null);
            return unpBuyUnpResult;
        }
        if (request.getUnpBuy() == null) {
            logger.error("采购订单更新失败{}", "unpBuy不能为null");
            unpBuyUnpResult.failed("agent不能为null", null);
            return unpBuyUnpResult;
        }
        
        if (request.getBuyItems() == null) {
            logger.error("采购订单更新失败{}", "buyItems不能为null");
            unpBuyUnpResult.failed("buyItems不能为null", null);
            return unpBuyUnpResult;
        }
        try {
            unpBuy = request.getUnpBuy();
            unpBuyItemList = request.getBuyItems();
            //判断原单是否可操作
            UnpBuy queryUnpBuy = unpBuyMapper.selectBySaleOrderNo(unpBuy.getSaleOrderNo());
            if (queryUnpBuy == null) {
                throw new Exception("不存在可操作订单");
            }
            //支付操作
            if (request.getOperationType() == 1) {
                unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                unpBuy.setModifier(agent.getAccount());
                unpBuy.setModifyTime(new Date());
                unpBuy.setStatus(3);
                unpBuy.setChangeType(0);
                unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                    unpBuyItem.setItemStatus(3);
                    unpBuyItem.setChangeType(0);
                    unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                }
            }
            //退 暂时不管 原单状态
            if (request.getOperationType() == 2) {
                //判断原单是否全部退
                List<UnpBuyItem> queryUnpBuyItemList = unpBuyItemMapper.selectCompletedByBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                if (queryUnpBuyItemList != null && unpBuyItemList != null) {
                    //判断是否部分退
                    if (queryUnpBuyItemList.size() > unpBuyItemList.size()) {
                        //部分退
                        unpBuy.setModifier(agent.getAccount());
                        unpBuy.setModifyTime(new Date());
                        unpBuy.setChangeType(3);
                        unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                    } else {
                        unpBuy.setModifier(agent.getAccount());
                        unpBuy.setModifyTime(new Date());
                        unpBuy.setChangeType(1);
                        unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                    }
                    unpBuyItemList.forEach(unpBuyItem -> {
                        unpBuyItem.setItemStatus(1);
                        unpBuyItem.setChangeType(1);
                        unpBuyItem.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                    });
                }
            }
            //取消  退票取消事暂时不管原单状态
            if (request.getOperationType() == 3) {
                if (queryUnpBuy.getStatus() == 1) {
                    //订单取消 原单状态 buyItem 表状态 都变为取消
                    unpBuy.setModifier(agent.getAccount());
                    unpBuy.setModifyTime(new Date());
                    unpBuy.setStatus(4);
                    unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                    unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                    buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                    for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                        unpBuyItem.setChangeType(0);
                        unpBuyItem.setItemStatus(4);
                        unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                    }
                }
                //退票取消 原单状态不变 buyIteam 变为 已完成状态
                if (queryUnpBuy.getStatus() == 3) {
                    //查询是否已有小类完成退款
                    //查询为未完成的退款的小类
                    //完场退款数量
                    int hasRefund = 0;
                    //已退 未退款
                    int noRefund = 0;
                    List<UnpBuyItem> unpBuyRefundItemList = unpBuyItemMapper.selectRefundByBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                    for (UnpBuyItem unpBuyItem : unpBuyRefundItemList) {
                        if (unpBuyItem.getItemStatus() == 1) {
                            noRefund++;
                        }
                        if (unpBuyItem.getItemStatus() == 3) {
                            hasRefund++;
                        }
                    }
                    //无完成退票 取消的小类数量 与 退票 小类数量相同时 更改原单changeType
                    if (hasRefund < 1 && noRefund == unpBuyItemList.size()) {
                        unpBuy.setModifier(agent.getAccount());
                        unpBuy.setModifyTime(new Date());
                        unpBuy.setChangeType(0);
                        unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                    } else {
                        unpBuy.setModifier(agent.getAccount());
                        unpBuy.setModifyTime(new Date());
                        unpBuy.setChangeType(3);
                        unpBuy.setBuyOrderNo(queryUnpBuy.getBuyOrderNo());
                        unpBuyMapper.updateByPrimaryKeySelective(unpBuy);
                        buyOrderService.updatePayStatus(agent, unpBuy.getBuyOrderNo(), unpBuy.getStatus());
                    }
                    for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                        unpBuyItem.setChangeType(0);
                        unpBuyItem.setItemStatus(3);
                        unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
                    }
                }
                
            }
            //退款 待处理状态更换为 已处理
            if (request.getOperationType() == 4) {
                for (UnpBuyItem unpBuyItem : unpBuyItemList) {
                    unpBuyItem.setItemStatus(3);
                    unpBuyItemMapper.updateByPrimaryKeySelective(unpBuyItem);
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
            logger.error("采购单更新异常", e);
            return unpBuyUnpResult;
        }
        return unpBuyUnpResult;
    }
    
    @Override
    public UnpResult<UnpSaleRefund> updateSale(Agent agent, UnpRefundVo request) {
        this.createValid(request, VALID_TYPE_SALE_REFUND);
        UnpResult<UnpSaleRefund> result = new UnpResult<>();
        UnpSaleRefund unpSaleRefund = request.getUnpSaleRefund();
        List<UnpSaleRefundItem> unpSaleRefundItemList = request.getUnpSaleRefundItemList();
        unpSaleRefundItemList.forEach(item -> {
            if (!item.getSaleRefundOrderNo().equals(unpSaleRefund.getSaleRefundOrderNo())) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "只可修改当前大单下的明细单");
            }
            item.setSaleRefundOrderNo(null);
            if (unpSaleRefundItemMapper.updateByPrimaryKeySelective(item) <= 0) {
                logger.error("采购明细单【{}】修改失败,目标:{}", item.getItemId(), item);
                result.setMsg("销售明细单【" + item.getItemId() + "】修改失败");
            }
        });
        unpSaleRefund.setSaleRefundOrderNo(null);
        int i = unpSaleRefundMapper.updateByPrimaryKeySelective(unpSaleRefund);
        if (i > 0) {
            logger.info("更新销售退单单成功:{}，开始更新OS销售退单", unpSaleRefund.getSaleRefundOrderNo());
            saleChangeService.updateStatus(agent, unpSaleRefund.getSaleRefundOrderNo(), unpSaleRefund.getStatus());
        }
        return result;
    }
    
    @Override
    public UnpResult<UnpBuyRefund> updateBuy(Agent agent, UnpRefundVo request) {
        this.createValid(request, VALID_TYPE_BUY_REFUND);
        UnpResult<UnpBuyRefund> result = new UnpResult<>();
        UnpBuyRefund unpBuyRefund = request.getUnpBuyRefund();
        List<UnpBuyRefundItem> unpBuyRefundItemList = request.getUnpBuyRefundItemList();
        unpBuyRefundItemList.forEach(item -> {
            if (!item.getBuyRefundOrderNo().equals(unpBuyRefund.getBuyRefundOrderNo())) {
                throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "只可修改当前大单下的明细单");
            }
            item.setBuyRefundOrderNo(null);
            if (unpBuyRefundItemMapper.updateByPrimaryKeySelective(item) <= 0) {
                logger.error("采购明细单【{}】修改失败,目标:{}", item.getItemId(), item);
                result.setMsg("采购明细单【" + item.getItemId() + "】修改失败");
            }
        });
        unpBuyRefund.setBuyRefundOrderNo(null);
        int i = unpBuyRefundMapper.updateByPrimaryKeySelective(unpBuyRefund);
        if (i > 0) {
            logger.info("更新采购退单成功:{}，开始更新OS采购退单", unpBuyRefund.getBuyRefundOrderNo());
            buyChangeService.updateStatus(agent, unpBuyRefund.getBuyRefundOrderNo(), unpBuyRefund.getStatus());
        }
        return result;
    }
    
    @Override
    public List<UnpSaleItem> getItems(UnpOrderQueryVo params) {
        List<UnpSaleItem> list;
        list = unpSaleItemMapper.selectItems(params);
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        logger.info("【UNP】查询-SALE_ITEM SIZE:{},parameter:{}", list.size(), params);
        return list;
    }
    
    @Override
    public List<UnpBuyItem> getBuyItems(UnpOrderQueryVo params) {
        List<UnpBuyItem> list = unpBuyItemMapper.selectItems(params.getBuyOrderNo());
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        logger.info("【UNP】查询-BUY_ITEM SIZE:{},parameter:{}", list.size(), params);
        return list;
    }
    
    @Override
    public UnpSaleItem getItem(Long itemNo) {
        UnpOrderQueryVo param = new UnpOrderQueryVo();
        param.setItemNo(itemNo);
        return this.getItems(param).get(0);
    }
    
    @Override
    public List<UnpSaleRefundItem> getSaleRefundItems(UnpOrderQueryVo params) {
        List<UnpSaleRefundItem> list = unpSaleRefundItemMapper.selectItems(params);
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        logger.info("【UNP】查询-SALE_REFUND_ITEM SIZE:{},parameter:{}", list.size(), params);
        return list;
    }
    
    @Override
    public List<UnpBuyRefundItem> getBuyRefundItems(UnpOrderQueryVo params) {
        List<UnpBuyRefundItem> list = unpBuyRefundItemMapper.selectItems(params);
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        logger.info("【UNP】查询-BUY_REFUND_ITEM SIZE:{},parameter:{}", list.size(), params);
        return list;
    }
    
    @Override
    public UnpResult<Object> saleRefund(Long saleRefundNo) throws GSSException {
        return null;
    }
    
    @Override
    public UnpResult<Object> buyRefund(Long buyRefundNo) throws GSSException {
        return null;
    }
}

package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.PlanAmountRecord;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.entity.enums.*;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.product.unp.api.entity.*;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpCreateOrderRefund;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderUpdateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
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
    
    @Override
    public UnpSale getSaleOrderInfo(UnpOrderVo params) {
        UnpSale unpSale = null;
        Page<UnpSale> page = this.querySaleOrderList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpSale = page.getRecords().get(0);
        if (unpSale == null) {
            return null;
        }
        UnpOrderVo p = new UnpOrderVo();
        p.setSaleOrderNo(unpSale.getSaleOrderNo());
        List<UnpSaleItem> saleItems = this.getItems(p);
        unpSale.setSaleItems(saleItems);
        return unpSale;
    }
    
    @Override
    public UnpBuy getBuyOrderInfo(UnpOrderVo params) {
        UnpBuy unpBuy = null;
        Page<UnpBuy> page = this.queryBuyOrderList(new Page<>(1, 1), params);
        if (page == null || page.getRecords() == null) {
            return null;
        }
        unpBuy = page.getRecords().get(0);
        if (unpBuy == null) {
            return null;
        }
        UnpOrderVo p = new UnpOrderVo();
        p.setBuyOrderNo(unpBuy.getBuyOrderNo());
        List<UnpBuyItem> buyItems = this.getBuyItems(p);
        unpBuy.setBuyItems(buyItems);
        return unpBuy;
    }
    
    public UnpBuy getBuyInfos(UnpOrderVo params) {
        UnpBuy unpBuy = this.unpBuyMapper.selectByPrimaryKey(params.getBuyOrderNo());
        if (unpBuy == null) {
            return null;
        }
        UnpOrderVo p = new UnpOrderVo();
        p.setBuyOrderNo(unpBuy.getBuyOrderNo());
        List<UnpBuyItem> unpBuyItems = this.getBuyItems(p);
        unpBuy.setBuyItems(unpBuyItems);
        return unpBuy;
    }
    
    @Override
    
    public UnpResult<UnpSale> createOrder(Agent agent, UnpOrderCreateVo orderCreateVo) {
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
    public UnpResult<UnpSale> createSale(Agent agent, UnpOrderCreateVo request) {
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
                        bi.getUnpType().equals(o.getUnpType());
                        bi.setSaleItemNo(o.getItemId());
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
            CreatePlanAmountVO createPlanAmountVO = new CreatePlanAmountVO();
            createPlanAmountVO.setPlanAmount(unpSale.getPlanAmount());
            createPlanAmountVO.setGoodsType(GoodsBigType.GENERAL.getKey());
            createPlanAmountVO.setIncomeExpenseType(IncomeExpenseType.INCOME.getKey());
            createPlanAmountVO.setBusinessType(BusinessType.SALE_ORDER);
            createPlanAmountVO.setRecordNo(unpSale.getSaleOrderNo());
            createPlanAmountVO.setRecordMovingType(CostType.FARE.getKey());
            PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, createPlanAmountVO);
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
    
    @Override
    public UnpResult<UnpBuy> createBuy(Agent agent, UnpOrderCreateVo request) {
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
            CreatePlanAmountVO planAmountRecordType = new CreatePlanAmountVO();
            planAmountRecordType.setRecordNo(buyOrderNo);
            //收支类型 1 收，2 为支
            planAmountRecordType.setIncomeExpenseType(IncomeExpenseType.EXPENSE.getKey());
            planAmountRecordType.setBusinessType(BusinessType.BUYORDER.getKey());
            planAmountRecordType.setRecordMovingType(CostType.FARE.getKey());
            //合计
            planAmountRecordType.setPlanAmount(planAmount);
            //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 9 通用产品
            planAmountRecordType.setGoodsType(GoodsBigType.GENERAL.getKey());
            planAmountRecordService.create(agent, planAmountRecordType);
            unpBuyUnpResult.success("创建采购单成功", unpBuy);
            logger.info("创建采购单结束");
        } catch (Exception e) {
            logger.error("创建采购单失败", e);
            unpBuyUnpResult.failed(e.getMessage(), null);
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
        if (agent == null) {
            logger.error("创建采购退单失败[]", "agent不能为null");
        }
        if (request == null) {
            logger.error("创建采购退单失败[]", "request不能为null");
        }
        if (request.getUnpBuyRefund() == null) {
            logger.error("创建采购退单失败[]", "npBuyU不能为null");
        }
        
        if (request.getUnpBuyRefundItemList() == null) {
            logger.error("采购订单更新失败[]", "buyItems不能为null");
        }
        UnpBuyRefund unpBuyRefund = null;
        List<UnpBuyRefundItem> unpBuyRefundItemList = null;
        UnpResult<UnpBuyRefund> unpResult = new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        String productName = "";
        try {
            Long buyRefundOrderNo = this.getUnpNo(PREFIX_SALE_REFUND);
            List<UnpItemType> unpItemTypes = unpItemTypeService.queryAllUnpItemType();
            Long businessSignNo = IdWorker.getId();
            unpBuyRefundItemList = request.getUnpBuyRefundItemList();
            for (UnpBuyRefundItem unpBuyRefundItem : unpBuyRefundItemList) {
                unpBuyRefundItem.setBuyRefundOrderNo(buyRefundOrderNo);
                unpBuyRefundItem.setItemStatus(1);
                planAmount = planAmount.add(unpBuyRefundItem.getGroupAmount());
                unpBuyRefundItemMapper.insertSelective(unpBuyRefundItem);
                for (UnpItemType unpItemType : unpItemTypes) {
                    if (unpItemType.getItemTypeNo().equals(unpBuyRefundItem.getUnpType())) {
                        productName += unpItemType.getName() + ",";
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
            buyOrderService.create(agent, buyOrder);
            //创建应收
            CreatePlanAmountVO planAmountRecordType = new CreatePlanAmountVO();
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
            logger.error("创建采购退单失败", e);
        }
        return unpResult;
    }
    
    private void createValid(UnpOrderCreateVo createVo, Integer validType) throws GSSException {
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
        } else if (validType == VALID_TYPE_SALE_REFUND) {
        
        } else if (validType == VALID_TYPE_BUY_REFUND) {
        
        } else {
            throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "验证类型参数错误【1~5】");
        }
        
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
    public Page<UnpBuy> queryBuyOrderList(Page<UnpBuy> wrapper, UnpOrderVo param) {
        if (null == wrapper) {
            
            wrapper = new Page<>();
        }
        if (null == param) {
            param = new UnpOrderVo();
        }
        List<UnpBuy> list = unpBuyMapper.queryBuyOrderList(wrapper, param);
        wrapper.setRecords(list);
        return wrapper;
    }
    
    @Override
    public UnpResult<UnpSale> updateSale(Agent agent, UnpOrderUpdateVo request) {
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
                    if (updateFlag > 0) {
                        result.setMsg("销售明细单【" + item.getItemId() + "】修改失败");
                    }
                }
            });
            this.unpSaleMapper.updateByPrimaryKeySelective(unpSale);
        }
        return result;
    }
    
    @Override
    public UnpResult<UnpBuy> updateBuy(Agent agent, UnpOrderUpdateVo request) {
        logger.info("采购单更新开始");
        if (agent == null) {
            logger.error("采购订单更新失败{}", "agent不能为null");
        }
        if (request == null) {
            logger.error("采购订单更新失败{}", "request不能为null");
        }
        if (request.getUnpBuy() == null) {
            logger.error("采购订单更新失败{}", "npBuyU不能为null");
        }
        
        if (request.getBuyItems() == null) {
            logger.error("采购订单更新失败{}", "buyItems不能为null");
        }
        UnpBuy unpBuy = null;
        List<UnpBuyItem> unpBuyItemList = null;
        UnpResult<UnpBuy> unpBuyUnpResult = new UnpResult();
        BigDecimal planAmount = new BigDecimal(0);
        try {
            if (request != null) {
                unpBuy = request.getUnpBuy();
                unpBuyItemList = request.getBuyItems();
                UnpBuy queryUnpBuy = unpBuyMapper.selectBySaleOrderNo(unpBuy.getSaleOrderNo());//判断原单是否可操作
                if (queryUnpBuy == null && queryUnpBuy.getChangeType() == 2) {
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
                        } else {
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
                if (request.getOperationType() == 3) {
                    if (queryUnpBuy.getStatus() == 1) {
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
                    if (queryUnpBuy.getStatus() == 3) {
                        //查询是否已有小类完成退款
                        //查询为未完成的退款的小类
                        int hasRefund = 0;//完场退款数量
                        int noRefund = 0;//已退 未退款
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
                        } else {
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
                if (request.getOperationType() == 4) {
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
            logger.error("采购单更新异常", e);
            return unpBuyUnpResult;
        }
        return unpBuyUnpResult;
    }
    
    @Override
    public List<UnpSaleItem> getItems(UnpOrderVo params) {
        List<UnpSaleItem> list;
        list = unpSaleItemMapper.selectItems(params);
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }
    
    @Override
    public List<UnpBuyItem> getBuyItems(UnpOrderVo params) {
        UnpBuyItem buyItem = new UnpBuyItem();
        buyItem.setBuyOrderNo(params.getBuyOrderNo());
        List<UnpBuyItem> list = unpBuyItemMapper.selectItemsByParams(buyItem);
        if (NullableCheck.isNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }
    
    @Override
    public UnpSaleItem getItem(Long itemNo) {
        UnpOrderVo param = new UnpOrderVo();
        param.setItemNo(itemNo);
        return this.getItems(param).get(0);
    }
}

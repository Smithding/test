package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.IncomeExpenseType;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpOrder;
import com.tempus.gss.product.unp.api.entity.UnpOrderChange;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderChangeVo;
import com.tempus.gss.product.unp.api.service.IUnpOrderChangeService;
import com.tempus.gss.product.unp.dao.OrderChangeDao;
import com.tempus.gss.product.unp.dao.OrderDao;
import com.tempus.gss.system.service.IDictService;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:UnpOrderServiceImpl
 * Function: 通用产品订单管理
 *
 * @author niepeng
 * @Date 2017年3月10日        上午8:59:26
 * @see
 * @since Ver 1.1
 */
@Service
@EnableAutoConfiguration
public class UnpOrderChangeServiceImpl implements IUnpOrderChangeService {
    
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    
    @Reference
    IMaxNoService maxNoService;
    
    @Reference
    ISaleOrderService saleOrderService;
    
    @Reference
    IBuyOrderService buyOrderService;
    
    @Reference
    IBuyChangeService buyChangeService;
    @Reference
    IDictService dictService;
    
    @Autowired
    OrderDao unpOrderDao;
    
    @Autowired
    OrderChangeDao orderChangeDao;
    
    @Reference
    ISaleChangeService saleChangeService;
    
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    
    @Reference
    ICertificateService certificateService;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createOrderChange(RequestWithActor<Long> requestWithActor) throws Exception {
        log.info("通用产品退单开始==============");
        if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
            log.error("传入参数为空");
            throw new GSSException("传入参数为空", "1010", "通用产品退单失败");
        }
        Agent agent = requestWithActor.getAgent();
        try {
            Long businessSignNo = IdWorker.getId();
            Long changeOrderNo = maxNoService.generateBizNo("UNP_CHANGE_ORDER_NO", 54);
            Long buyChangeNo = maxNoService.generateBizNo("UNP_BUY_CHANGE_NO", 55);
            Long orderNo = requestWithActor.getEntity();
            UnpOrder unpOrder = unpOrderDao.getOrderByNo(orderNo);
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, orderNo);
            unpOrder.setSaleOrder(saleOrder);
            if (saleOrder == null) {
                log.error("根据orderNo查询saleOrder为空");
                throw new GSSException("根据orderNo查询saleOrder为空", "1010", "退火车票失败");
            }
            if (unpOrder.getBuyOrderNo() != null) {
                BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, unpOrder.getBuyOrderNo());
                if (buyOrder == null) {
                    log.error("根据buyOrderNo查询buyOrder为空");
                    throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "通用产品退单失败");
                }
                BuyChange buyChange = new BuyChange();
                buyChange.setBuyChangeNo(buyChangeNo);
                // 变更类型 1废 2退 3改
                buyChange.setOrderChangeType(2);
                buyChange.setBusinessSignNo(businessSignNo);
                buyChange.setBsignType(4);
                buyChange.setOwner(agent.getOwner());
                buyChange.setCreateTime(new Date());
                buyChange.setChildStatus(1);
                buyChange.setGoodsType(buyOrder.getGoodsType());
                buyChange.setGoodsSubType(buyOrder.getGoodsSubType());
                buyChange.setGoodsName(buyOrder.getGoodsName());
                buyChange.setIncomeExpenseType(1);
                buyChange.setBuyOrderNo(buyOrder.getBuyOrderNo());
                buyChangeService.create(requestWithActor.getAgent(), buyChange);
                
                CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
                BigDecimal buyPrice = unpOrder.getBuyPrice();
                if (buyPrice == null) {
                    buyPrice = new BigDecimal(0);
                }
                buyOrderPlanAmountVO.setPlanAmount(buyPrice);
                buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());
                buyOrderPlanAmountVO.setRecordNo(buyChangeNo);
                buyOrderPlanAmountVO.setBusinessType(5);
                buyOrderPlanAmountVO.setIncomeExpenseType(1);
                buyOrderPlanAmountVO.setRecordMovingType(14);
                PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
                if (planAmountRecord1 == null) {
                    log.info("创建采购变更应收记录失败");
                    throw new GSSException("创建采购变更应收记录失败", "1010", "创建采购变更应收记录失败");
                }
            }
            if (unpOrder.getSaleOrder() != null) {
                /* 创建销售变更单 */
                SaleChange saleChange = new SaleChange();
                saleChange.setSaleChangeNo(changeOrderNo);
                saleChange.setSaleOrderNo(orderNo);
                // 变更类型 1废 2退 3改
                saleChange.setOrderChangeType(2);
                saleChange.setBusinessSignNo(businessSignNo);
                // 1销采 2换票 3废和退 4改签
                saleChange.setBsignType(3);
                saleChange.setOwner(agent.getOwner());
                saleChange.setCreateTime(new Date());
                saleChange.setChildStatus(1);
                saleChange.setGoodsType(saleOrder.getGoodsType());
                saleChange.setGoodsSubType(saleOrder.getGoodsSubType());
                saleChange.setGoodsName(saleOrder.getGoodsName());
                saleChange.setTransationOrderNo(unpOrder.getSaleOrder().getTransationOrderNo());
                //交易号
                saleChange.setIncomeExpenseType(2);
                //收支类型 1.收 2.支
                saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
                if (saleChange == null) {
                    log.error("创建销售变更单失败");
                    throw new GSSException("创建销售变更单失败", "1010", "通用产品退单失败");
                }
                CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
                BigDecimal salePrice = unpOrder.getSalePrice();
                if (salePrice == null) {
                    salePrice = new BigDecimal(0);
                }
                saleOrderPlanAmountVO.setPlanAmount(salePrice);
                saleOrderPlanAmountVO.setGoodsType(saleOrder.getGoodsType());
                //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                saleOrderPlanAmountVO.setRecordNo(changeOrderNo);
                //记录编号   自动生成
                saleOrderPlanAmountVO.setBusinessType(4);
                //业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                saleOrderPlanAmountVO.setIncomeExpenseType(2);
                // 收支类型 1 收，2 为支
                saleOrderPlanAmountVO.setRecordMovingType(4);
                PlanAmountRecord planAmountRecord = planAmountRecordService.create(requestWithActor.getAgent(), saleOrderPlanAmountVO);
                if (planAmountRecord == null) {
                    log.info("创建销售应付记录失败");
                    throw new GSSException("创建销售应付记录失败", "1010", "创建销售应付记录失败");
                }
            }
            UnpOrderChange unpOrderChange = new UnpOrderChange();
            /* 设置销售单拓展编号 */
            unpOrderChange.setChangeOrderNo(changeOrderNo);
            unpOrderChange.setBuyChangeNo(buyChangeNo);
            unpOrderChange.setValid(true);
            unpOrderChange.setStatus(1);
            unpOrderChange.setCreateTime(new Date());
            unpOrderChange.setOwner(agent.getOwner());
            unpOrderChange.setCreator(agent.getAccount());
            unpOrderChange.setOrderNo(orderNo);
            int flag = orderChangeDao.insertSelective(unpOrderChange);
            if (flag == 0) {
                log.error("通用产品退单失败");
                throw new GSSException("创建通用产品退单失败", "1010", "通用产品退单失败");
            }
            UnpOrder unpOrderUpdate = new UnpOrder();
            unpOrderUpdate.setOrderNo(unpOrder.getOrderNo());
            unpOrderUpdate.setStatus(4);
            unpOrderDao.updateByPrimaryKeySelective(unpOrderUpdate);
            
        } catch (Exception e) {
            log.error("通用产品退单失败", e);
            throw new GSSException("创建通用产品退单失败", "1010", e.getMessage());
        }
        log.info("通用产品退单结束==============");
        return true;
        
    }
    
    @Override
    public UnpOrderChange queryOrderChangeByNo(RequestWithActor<Long> requestWithActor) {
        log.info("查询通用产品订单开始==============");
        if (requestWithActor.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            log.error("订单号为空");
            throw new GSSException("订单号为空", "1010", "查询通用产品订单失败");
        }
        UnpOrderChange orderChange = orderChangeDao.getOrderChangeByNo(requestWithActor.getEntity());
        SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), requestWithActor.getEntity());
        orderChange.setSaleChange(saleChange);
        log.info("查询通用产品订单结束==============");
        return orderChange;
    }
    
    @Override
    public UnpOrderChange queryBuyChangeByNo(RequestWithActor<Long> requestWithActor) {
        log.info("查询通用产品订单开始==============");
        if (requestWithActor.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            log.error("订单号为空");
            throw new GSSException("订单号为空", "1010", "查询通用产品订单失败");
        }
        UnpOrderChange orderChange = orderChangeDao.queryBuyChangeByNo(requestWithActor.getEntity());
        SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), orderChange.getChangeOrderNo());
        orderChange.setSaleChange(saleChange);
        log.info("查询通用产品订单结束==============");
        return orderChange;
    }
    
    @Override
    public Page<UnpOrderChange> queryChangeOrderList(Page<UnpOrderChange> page, RequestWithActor<UnpOrderChangeVo> pageRequest) {
        log.info("根据条件查询通用产品订单开始==============");
        Agent agent = pageRequest.getAgent();
        if (agent == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        
        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
        if (agent.getType() != null && agent.getType() != 0L) {
            //不是运营平台账号
            if (pageRequest.getEntity() == null) {
                UnpOrderChangeVo order = new UnpOrderChangeVo();
                order.setOwner(pageRequest.getAgent().getOwner());
                order.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(order);
            } else {
                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
                pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
            }
        }
        /* 分页列表 */
        List<UnpOrderChange> unpOrderChangeList = orderChangeDao.queryOrderChangeList(page, pageRequest.getEntity());
        for (UnpOrderChange change : unpOrderChangeList) {
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, change.getChangeOrderNo());
            change.setSaleChange(saleChange);
        }
        page.setRecords(unpOrderChangeList);
        log.info("根据条件查询通用产品订单结束==============");
        return page;
    }
    
    @Override
    @Transactional
    public int deleteOrderChagne(RequestWithActor<Long> orderChangeNo) throws Exception {
        int flag = 0;
        if (orderChangeNo == null) {
            log.error("删除异常，请选择需要删除的记录");
            throw new GSSException("删除订单模块", "0401", "订单号为空");
        }
        try {
            UnpOrderChange unpOrderChange = orderChangeDao.getOrderChangeByNo(orderChangeNo.getEntity());
            if (unpOrderChange != null) {
                if (unpOrderChange.getValid()) {
                    unpOrderChange.setValid(false);
                    unpOrderChange.setModifier(orderChangeNo.getAgent().getAccount());
                    unpOrderChange.setModifyTime(new Date());
                }
                flag = orderChangeDao.updateSelectiveById(unpOrderChange);
                if (0 == flag) {
                    throw new GSSException("删除订单模块", "0402", "删除订单失败");
                }
            } else {
                flag = 0;
            }
        } catch (Exception e) {
            log.error("删除订单修改valid失败", e);
            throw new GSSException("删除订单模块", "0403", "删除订单失败");
        }
        return flag;
    }
    
    @Override
    @Transactional
    public boolean cancelOrderChange(RequestWithActor<Long> orderChangeNo) {
        log.info("取消订单开始==============");
        if (orderChangeNo.getAgent() == null || orderChangeNo.getEntity() == null) {
            log.info("取消订单参数为空");
            throw new GSSException("取消订单参数为空", "1010", "取消订单失败");
        }
        UnpOrderChange unpOrderChange = orderChangeDao.getOrderChangeByNo(orderChangeNo.getEntity());
        unpOrderChange.setStatus(3);
        unpOrderChange.setModifier(orderChangeNo.getAgent().getAccount());
        unpOrderChange.setModifyTime(new Date());
        int flag = orderChangeDao.updateByPrimaryKeySelective(unpOrderChange);
        if (flag == 0) {
            return false;
        }
        log.info("取消订单结束==============");
        return true;
        
    }
    
    @Override
    @Transactional
    public boolean changePay(RequestWithActor<UnpOrderChangeVo> requestWithActor) throws Exception {
        log.info("支付通用产品订单开始==========");
        Agent agent = requestWithActor.getAgent();
        UnpOrderChangeVo unpOrderChangeVo = requestWithActor.getEntity();
        if (agent == null || unpOrderChangeVo == null) {
            log.info("订单参数为空");
            throw new GSSException("订单参数为空", "1010", "支付失败");
        }
        
        //根据订单号获取订单信息
        UnpOrderChange unpOrderChange = orderChangeDao.getOrderChangeByNo(unpOrderChangeVo.getChangeOrderNo());
        
        UnpOrder unpOrder = unpOrderDao.getOrderByNo(unpOrderChange.getOrderNo());
        
        //销售支付处理
        if (null != unpOrderChangeVo.getChannelId()) {
            
            //判断支付方式，如果是挂账，那么调用挂账服务确认是否挂账成功，如果是现付，那么直接修改支付状态
            Integer payStatus = 1;
            Integer payType = 1;
            if (unpOrderChangeVo.getSaleAccountType() == 2000001 || unpOrderChangeVo.getSaleAccountType() == 3000003) {
                //0表示现付，其它表示挂账
                unpOrder.setPayStatus(5);
                //已退款
                unpOrderChange.setSaleRefundPrice(unpOrderChangeVo.getPayAmount());
                payType = 3;
                //线下支付
                payStatus = 3;
            } else {
                unpOrder.setPayStatus(4);
                //已挂账
                unpOrder.setPayAmount(unpOrder.getSalePrice());
                payType = 2;
                //挂账
                payStatus = 4;
            }
            
            //创建实退信息
            CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
            List<BusinessOrderInfo> orderInfoList = new ArrayList<>();
            BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
            businessOrderInfo.setRecordNo(unpOrderChangeVo.getChangeOrderNo());
            businessOrderInfo.setBusinessType(BusinessType.SALECHANGE.getKey());
            businessOrderInfo.setActualAmount(unpOrderChangeVo.getPayAmount());
            orderInfoList.add(businessOrderInfo);
            certificateCreateVO.setPayType(payType);
            certificateCreateVO.setPayWay(unpOrderChangeVo.getSaleAccountType());
            certificateCreateVO.setCustomerNo(unpOrder.getChannelId());
            certificateCreateVO.setCustomerTypeNo(unpOrder.getChannelType());
            certificateCreateVO.setIncomeExpenseType(IncomeExpenseType.EXPENSE.getKey());
            certificateCreateVO.setAccoutNo(unpOrderChangeVo.getSaleAccount() + "");
            certificateCreateVO.setSubBusinessType(3);
            certificateCreateVO.setServiceLine("1");
            //业务线固定传1
            certificateCreateVO.setThirdPayNo(unpOrderChangeVo.getPayAmountTransactionNo());
            //支付流水号
            certificateCreateVO.setOrderInfoList(orderInfoList);
            try {
                certificateService.saleRefundCert(agent, certificateCreateVO);
                
                //修改订单状态为已退款
                unpOrder.setPayStatus(payStatus);
                //5,已退款
                unpOrder.setModifier(agent.getAccount());
                unpOrder.setModifyTime(new Date());
                unpOrderDao.updateByPrimaryKeySelective(unpOrder);
                
                //更改销售单
                saleChangeService.updatePayStatus(agent, unpOrderChangeVo.getChangeOrderNo(), payStatus);
                
                //更改变更单
                unpOrderChange.setStatus(2);
                unpOrderChange.setModifier(agent.getAccount());
                unpOrderChange.setModifyTime(new Date());
                orderChangeDao.updateByPrimaryKeySelective(unpOrderChange);
                
            } catch (Exception e) {
                log.error("退款报错" + e);
                throw new GSSException("订单参数为空", "1010", "支付失败" + e);
            }
        }
        log.info("支付通用产品订单结束==========");
        return true;
    }
}


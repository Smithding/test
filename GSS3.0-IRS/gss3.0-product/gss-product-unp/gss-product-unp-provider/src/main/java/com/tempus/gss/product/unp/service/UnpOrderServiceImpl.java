/**
 * UnpOrderServiceImpl.java
 * com.tempus.gss.product.unp.service
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2017年3月10日 		niepeng
 * <p>
 * Copyright (c) 2017, TNT All Rights Reserved.
 */

package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.entity.enums.*;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.unp.api.entity.UnpOrder;
import com.tempus.gss.product.unp.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.service.IUnpOrderService;
import com.tempus.gss.product.unp.dao.OrderDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class UnpOrderServiceImpl implements IUnpOrderService {
    
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    
    @Reference
    IMaxNoService maxNoService;
    
    @Autowired
    OrderDao unpOrderDao;
    
    @Reference
    ISaleOrderService saleOrderService;
    
    @Reference
    IBuyOrderService buyOrderService;
    
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    @Reference
    ICertificateService certificateService;
    
    @Reference
    ITransationOrderService tranOrderService;
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {
        log.info("创建通用产品订单开始==========");
        
        if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
            log.info("创建通用产品订单参数为空");
            throw new GSSException("创建通用产品订单参数为空", "1010", "创建通用产品订单失败");
        }
        Agent agent = requestWithActor.getAgent();
        
        /*创建通用产品订单*/
        UnpOrder unpOrder = requestWithActor.getEntity().getUnpOrder();
        if (unpOrder == null) {
            log.info("通用产品订单对象为空");
            throw new GSSException("通用产品订单对象为空", "1010", "创建通用产品订单失败");
        }
        unpOrder.setOwner(agent.getOwner());
        /*判断用户是否输入了交易单号，输入了的话，将之关联，没有的话创建一个交易单*/
        Long transactionId = requestWithActor.getEntity().getUnpOrder().getTradeNo();
        if (null == transactionId) {
            transactionId = IdWorker.getId();
            //创建交易单
            TransationOrder tr = new TransationOrder();
            tr.setCreateTime(new Date());
            tr.setCustomerNo(unpOrder.getChannelId());
            tr.setCustomerTypeNo(unpOrder.getChannelType());
            tr.setOrderingLoginName(agent.getAccount());
            tr.setOwner(agent.getOwner());
            tr.setSourceChannelNo(agent.getDevice());
            tr.setPayStatus(PayStatus.NO_PAYMENT.getKey());
            tr.setTransationOrderNo(transactionId);
            tr.setValid(1);
            tranOrderService.create(agent, tr);
        }
        
        //生成订单号
        Long orderNo = maxNoService.generateBizNo("UNP_SALE_ORDER_NO", 60);
        unpOrder.setOrderNo(orderNo);
        unpOrder.setTradeNo(transactionId);
        unpOrder.setBatchNo(orderNo);
        
        Long businessSignNo = IdWorker.getId();
        //如果有销售信息，那么生成销售单
        SaleOrder saleOrder = new SaleOrder();
        CreatePlanAmountVO planAmountVO = new CreatePlanAmountVO();
        boolean createSale = false;
        boolean createBuy = false;
        if (null != unpOrder.getChannelId()) {
            //待支付
            unpOrder.setPayStatus(1);
            /*创建销售单*/
            saleOrder = new SaleOrder();
            saleOrder.setOrderType(OrderType.SALEORDER.getKey());
            saleOrder.setSaleOrderNo(orderNo);
            saleOrder.setBusinessSignNo(businessSignNo);
            saleOrder.setBsignType(BSignType.SALEBUY.getKey());
            saleOrder.setCustomerNo(unpOrder.getChannelId());
            saleOrder.setCustomerTypeNo(unpOrder.getChannelType());
            saleOrder.setSourceChannelNo("UNP");
            saleOrder.setTransationOrderNo(unpOrder.getTradeNo());
            saleOrder.setOrderingLoginName(agent.getAccount());
            //通用产品
            saleOrder.setGoodsType(GoodsBigType.GENERAL.getKey());
            //通用商品 代码
            saleOrder.setGoodsSubType(EgoodsSubType.SALE.getKey());
            saleOrder.setGoodsName(StringUtils.isBlank(unpOrder.getRemark()) ? "通用产品" : unpOrder.getRemark());
            // 待支付
            saleOrder.setPayStatus(PayStatus.NO_PAYMENT.getKey());
            saleOrder.setOrderChildStatus(1);
            // 创建销售应收记录
            planAmountVO.setRecordNo(unpOrder.getOrderNo());
            planAmountVO.setBusinessType(BusinessType.SALEORDER.getKey());
            //业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
            planAmountVO.setGoodsType(GoodsBigType.GENERAL.getKey());
            //商品大类
            planAmountVO = new CreatePlanAmountVO();
            planAmountVO.setIncomeExpenseType(1);
            // 收支类型 1 收，2 为支
            planAmountVO.setRecordMovingType(CostType.FARE.getKey());
            planAmountVO.setPlanAmount(unpOrder.getSalePrice());
            createSale = true;
            
        }
        //如果有采购信息，那么生成采购单
        BuyOrder buyOrder = null;
        if (null != unpOrder.getBuyChannelId()) {
            Long buyOrderNo = maxNoService.generateBizNo("UNP_BUY_ORDER_NO", 61);
            unpOrder.setBuyOrderNo(buyOrderNo);
            //待支付
            unpOrder.setBuyPayStatus(1);
            /* 创建采购单 */
            buyOrder = new BuyOrder();
            buyOrder.setSaleOrderNo(unpOrder.getOrderNo());
            buyOrder.setBuyOrderNo(unpOrder.getBuyOrderNo());
            buyOrder.setBusinessSignNo(businessSignNo);
            buyOrder.setBsignType(1);
            //通用产品
            buyOrder.setGoodsType(GoodsBigType.GENERAL.getKey());
            //subType填入的是 dict表对应的产品的id
            buyOrder.setGoodsSubType(EgoodsSubType.BUY.getKey());
            buyOrder.setGoodsName(StringUtils.isBlank(unpOrder.getRemark()) ? "通用产品" : unpOrder.getRemark());
            buyOrder.setBuyChannelNo("unp");
            buyOrder.setSupplierNo(unpOrder.getBuyChannelId());
            buyOrder.setSupplierTypeNo(unpOrder.getBuyChannelType());
            // 采购单子状态 待处理（1），处理中（2），已完成（3），已取消（4）
            buyOrder.setBuyChildStatus(1);
            // 创建采购应付记录
            planAmountVO = new CreatePlanAmountVO();
            //记录编号   自动生成
            planAmountVO.setRecordNo(unpOrder.getBuyOrderNo());
            //业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
            planAmountVO.setBusinessType(BusinessType.BUYORDER.getKey());
            //商品大类
            planAmountVO.setGoodsType(GoodsBigType.GENERAL.getKey());
            planAmountVO.setRecordMovingType(CostType.FARE.getKey());
            // 收支类型 1 收，2 为支
            planAmountVO.setIncomeExpenseType(2);
            planAmountVO.setPlanAmount(unpOrder.getBuyPrice());
            createBuy = true;
            
        }
        //保存通用产品订单信息
        int insertOK = unpOrderDao.insertSelective(unpOrder);
        
        if (insertOK > 0) {
            //生成通用订单成功后
            if (createSale) {
                //生成销售单
                saleOrderService.create(requestWithActor.getAgent(), saleOrder);
            }
            if (createBuy) {
                //生成采购单
                buyOrderService.create(requestWithActor.getAgent(), buyOrder);
            }
        }
        //创建应收应付
        planAmountRecordService.create(requestWithActor.getAgent(), planAmountVO);
        log.info("创建通用产品订单结束==========");
        return unpOrder.getOrderNo();
    }
    
    @Override
    public UnpOrder queryOrderByNo(RequestWithActor<Long> requestWithActor) {
        log.info("查询通用产品订单开始==============");
        if (requestWithActor.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            log.error("订单号为空");
            throw new GSSException("订单号为空", "1010", "查询通用产品订单失败");
        }
        UnpOrder unpOrder = unpOrderDao.getOrderByNo(requestWithActor.getEntity());
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(), requestWithActor.getEntity());
        unpOrder.setSaleOrder(saleOrder);
        log.info("查询通用产品订单结束==============");
        return unpOrder;
    }
    
    @Override
    public Page<UnpOrder> queryOrderList(Page<UnpOrder> page, RequestWithActor<UnpOrderVo> pageRequest) {
        log.info("根据条件查询通用产品订单开始==============");
        Agent agent = pageRequest.getAgent();
        if (agent == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
        if (agent.getType() != null && agent.getType() != 0L) { //不是运营平台账号
            if (pageRequest.getEntity() == null) {
                UnpOrderVo order = new UnpOrderVo();
                order.setOwner(pageRequest.getAgent().getOwner());
                order.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(order);
            } else {
                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
                pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
            }
        }
        /* 分页列表 */
        List<UnpOrder> unpOrderList = unpOrderDao.queryOrderList(page, pageRequest.getEntity());
        for (UnpOrder unp : unpOrderList) {
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, unp.getOrderNo());
            unp.setSaleOrder(saleOrder);
        }
        page.setRecords(unpOrderList);
        log.info("根据条件查询通用产品订单结束==============");
        return page;
    }
    
    @Override
    @Transactional
    public int deleteOrder(RequestWithActor<Long> orderNo) throws Exception {
        int flag = 0;
        if (orderNo == null) {
            log.error("删除异常，请选择需要删除的记录");
            throw new GSSException("删除订单模块", "0401", "订单号为空");
        }
        try {
            UnpOrder unpOrder = unpOrderDao.getOrderByNo(orderNo.getEntity());
            if (unpOrder != null) {
                if (unpOrder.getValid()) {
                    unpOrder.setValid(false);
                    unpOrder.setModifier(orderNo.getAgent().getAccount());
                    unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                }
                flag = unpOrderDao.updateById(unpOrder);
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
    public boolean cancelOrder(RequestWithActor<Long> requestWithActor) {
        log.info("取消订单开始==============");
        if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
            log.info("取消订单参数为空");
            throw new GSSException("取消订单参数为空", "1010", "取消订单失败");
        }
        Agent agent = requestWithActor.getAgent();
        Long orderNo = requestWithActor.getEntity();
        UnpOrder unpOrder = unpOrderDao.getOrderByNo(orderNo);
        if (unpOrder != null) {
            // 更新销售单为取消状态
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, unpOrder.getOrderNo());
            if (null != saleOrder) {
                saleOrder.setOrderStatus(4);//已取消
                saleOrderService.update(agent, saleOrder);
            }
            // 更新采购单为取消状态
            BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, unpOrder.getBuyOrderNo());
            if (null != buyOrder) {
                buyOrderService.update(agent, buyOrder);
                buyOrder.setBuyStatus(4);//已取消
            }
            //订单
            try {
                unpOrder.setStatus(4);//已取消
                unpOrder.setModifier(requestWithActor.getAgent().getAccount());
                unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                unpOrderDao.updateById(unpOrder);
            } catch (ParseException e) {
                log.info("操作异常!");
                throw new GSSException("操作异常!", "1010", "操作失败");
            }
        } else {
            log.info("取消异常!");
            throw new GSSException("取消异常!", "1010", "取消订单失败");
        }
        log.info("取消订单结束==============");
        return true;
    }
    
    @Override
    @Transactional
    public boolean updateOrderStatus(RequestWithActor<Long> requestWithActor, Integer status) {
        log.info("修改订单状态开始==============");
        if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
            log.info("参数为空");
            throw new GSSException("参数为空", "1010", "修改订单状态失败");
        }
        Long orderNo = requestWithActor.getEntity();
        UnpOrder unpOrder = unpOrderDao.getOrderByNo(orderNo);
        if (unpOrder != null) {
            try {
                unpOrder.setStatus(status);
                unpOrder.setModifier(requestWithActor.getAgent().getAccount());
                unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                unpOrderDao.updateById(unpOrder);
            } catch (ParseException e) {
                log.info("操作异常!");
                throw new GSSException("操作异常!", "1010", "操作失败");
            }
        } else {
            log.info("无效的订单");
            throw new GSSException("无效的订单", "1010", "修改订单状态失败");
        }
        log.info("修改订单状态结束==============");
        return true;
    }
    
    @Override
    @Transactional
    public boolean updateOrderPayStatus(RequestWithActor<UnpOrderVo> requestWithActor) {
        try {
            Agent agent = requestWithActor.getAgent();
            UnpOrderVo unpOrderVo = requestWithActor.getEntity();
            if (agent == null || unpOrderVo == null) {
                log.info("订单参数为空");
                throw new GSSException("订单参数为空", "1010", "支付失败");
            }
            //根据订单号获取订单信息
            UnpOrder unpOrder = unpOrderDao.getOrderByNo(unpOrderVo.getOrderNo());
            Integer payStatus = 1;
            switch (unpOrderVo.getSaleAccountType()) {//支付类型（1 在线支付 2 帐期或代付 3 线下支付）
                case 1: {
                    payStatus = 3;
                }
                break;
                case 2: {
                    payStatus = 4;
                }
                break;
                case 3: {
                    payStatus = 3;
                }
                break;
                default:
                    break;
            }
            if (null != unpOrderVo.getOfflinePayee() && null != unpOrderVo.getOfflinePayer() && null != unpOrderVo.getOfflinePayNo()) {
                //线下支付修改订单信息
                unpOrder.setOfflinePayee(unpOrderVo.getOfflinePayee());
                unpOrder.setOfflinePayer(unpOrderVo.getOfflinePayer());
                unpOrder.setOfflinePayNo(unpOrderVo.getOfflinePayNo());
                unpOrder.setOfflinePayRemark(unpOrderVo.getOfflinePayRemark());
            }
            //更改订单信息
            unpOrder.setPayTime(new Date());
            unpOrder.setModifier(agent.getAccount());
            unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            unpOrder.setPayStatus(payStatus);
            if (null != unpOrder.getChannelId() && null == unpOrder.getBuyChannelId() && (2 == unpOrder.getPayStatus() || 3 == unpOrder.getPayStatus())) {
                //更改订单信息
                //没有采购信息的时候，销售处理完，该订单也处理完
                unpOrder.setStatus(3);//已处理
            }
            if (null == unpOrder.getChannelId() && null != unpOrder.getBuyChannelId() && (2 == unpOrder.getBuyPayStatus() || 3 == unpOrder.getBuyPayStatus())) {
                //更改订单信息
                // 没有销售信息的时候，采购处理完，该订单也处理完
                unpOrder.setStatus(3);//已处理
            }
            if (null != unpOrder.getChannelId() && null != unpOrder.getBuyChannelId() && (2 == unpOrder.getPayStatus() || 3 == unpOrder.getPayStatus()) && (2 == unpOrder.getBuyPayStatus() || 3 == unpOrder.getBuyPayStatus())) {
                //更改订单信息，销售，采购信息都存在
                
                unpOrder.setStatus(3);//已处理
            }
//            return unpOrderDao.updateById(unpOrder) > 0;
            return unpOrderDao.updateByPrimaryKeySelective(unpOrder);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改订单状态的时候出错了~");
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean pay(RequestWithActor<UnpOrderVo> requestWithActor) throws Exception {
        log.info("支付通用产品订单开始==========");
        try {
            Agent agent = requestWithActor.getAgent();
            UnpOrderVo unpOrderVo = requestWithActor.getEntity();
            if (agent == null || unpOrderVo == null) {
                log.info("订单参数为空");
                throw new GSSException("订单参数为空", "1010", "支付失败");
            }
            //根据订单号获取订单信息
            UnpOrder unpOrder = unpOrderDao.getOrderByNo(unpOrderVo.getOrderNo());
            //销售支付处理
            if (null != unpOrderVo.getChannelId()) {
                //判断支付方式，如果是挂账，那么调用挂账服务确认是否挂账成功，如果是现付，那么直接修改支付状态
                Integer payStatus = 1;
                Integer payType = 1;
                if (unpOrderVo.getSaleAccountType() == 2000001 || unpOrderVo.getSaleAccountType() == 3000003) {//0表示现付，其它表示挂账
                    unpOrder.setPayStatus(3);//已支付
                    unpOrder.setPayAmount(unpOrderVo.getPayAmount());
                    payType = 3;//线下支付
                    payStatus = 3;
                } else {
                    unpOrder.setPayStatus(2);//已挂账
                    unpOrder.setPayAmount(unpOrder.getSalePrice());
                    payType = 2;//挂账
                    payStatus = 4;
                }
                //更改订单信息
                unpOrder.setPayTime(new Date());
                unpOrder.setModifier(agent.getAccount());
                unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                if (null == unpOrder.getBuyChannelId()) {//如果销售处理完成，并且没有采购数据，那么订单可标识为已完成
                    unpOrder.setStatus(3);//已处理
                }
                
                //创建实收信息
                CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                List<BusinessOrderInfo> orderInfoList = Lists.newArrayList();
                BusinessOrderInfo orderInfo = new BusinessOrderInfo();
                orderInfo.setRecordNo(unpOrder.getOrderNo());
                orderInfo.setBusinessType(BusinessType.SALEORDER.getKey());
                orderInfo.setActualAmount(unpOrderVo.getPayAmount());
                orderInfoList.add(orderInfo);
                certificateCreateVO.setOrderInfoList(orderInfoList);//业务单信息
                certificateCreateVO.setPayWay(unpOrderVo.getSaleAccountType());//支付方式
                certificateCreateVO.setPayType(payType);
                certificateCreateVO.setAccoutNo(unpOrderVo.getSaleAccount() + "");//销售付款账号
                certificateCreateVO.setServiceLine("1");//业务线固定传1
                certificateCreateVO.setChannel("SALE");//
                certificateCreateVO.setCustomerTypeNo(unpOrder.getChannelType());
                certificateCreateVO.setIncomeExpenseType(IncomeExpenseType.INCOME.getKey());
                certificateCreateVO.setCustomerNo(unpOrder.getChannelId());
                certificateCreateVO.setSubBusinessType(1);//销采
                certificateService.createCertificateAndPay(agent, certificateCreateVO);
                
                //更改销售单
                saleOrderService.updatePayStatus(agent, unpOrderVo.getOrderNo(), payStatus);
                unpOrderDao.updateById(unpOrder);
            }
            //采购支付处理
            if (null != unpOrderVo.getBuyChannelId()) {
                //判断付款方式，如果是挂账，那么调用挂账服务确认是否挂账成功，如果是现付，那么直接修改支付状态
                Integer payStatus = 1;
                Integer payType = 1;
                if (unpOrderVo.getBuyAccountType() == 2000001 || unpOrderVo.getBuyAccountType() == 3000003) {//0表示现付，其它表示挂账
                    unpOrder.setBuyPayStatus(3);//已支付
                    unpOrder.setBuyPayAmount(unpOrderVo.getBuyPayAmount());
                    payType = 3;
                    payStatus = 3;
                } else {
                    unpOrder.setBuyPayStatus(2);//已挂账
                    unpOrder.setPayAmount(unpOrder.getBuyPrice());
                    payType = 2;
                    payStatus = 4;
                }
                //更改订单信息
                unpOrder.setPayTime(new Date());
                unpOrder.setModifier(agent.getAccount());
                unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                if (null == unpOrder.getChannelId()) {//如果销售处理完成，并且没有采购数据，那么订单可标识为已完成
                    unpOrder.setStatus(3);//已处理
                }
                
                //创建实付信息
                CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                List<BusinessOrderInfo> orderInfoList = Lists.newArrayList();
                BusinessOrderInfo orderInfo = new BusinessOrderInfo();
                orderInfo.setRecordNo(unpOrder.getBuyOrderNo());
                orderInfo.setBusinessType(BusinessType.BUYORDER.getKey());
                orderInfo.setActualAmount(unpOrderVo.getBuyPayAmount());
                orderInfoList.add(orderInfo);
                certificateCreateVO.setOrderInfoList(orderInfoList);//业务单信息
                certificateCreateVO.setPayWay(unpOrderVo.getBuyAccountType());//支付方式
                certificateCreateVO.setPayType(payType);
                //certificateCreateVO.setAccoutNo("3".equals(payType) ? unpOrderVo.getCapitalAccount() : unpOrderVo.getBuyAccount());//付款账号
                certificateCreateVO.setAccoutNo(unpOrderVo.getBuyAccount() + "");//付款账号
                certificateCreateVO.setServiceLine("1");//业务线固定传1
                certificateCreateVO.setChannel("BUY");//
                certificateCreateVO.setCustomerTypeNo(unpOrder.getBuyChannelType());
                certificateCreateVO.setIncomeExpenseType(IncomeExpenseType.EXPENSE.getKey());
                certificateCreateVO.setCustomerNo(unpOrder.getBuyChannelId());
                certificateCreateVO.setSubBusinessType(1);//销采
                certificateCreateVO.setThirdPayNo(unpOrderVo.getPayAmountTransactionNo());//支付流水号
                certificateCreateVO.setThirdBusNo(String.valueOf(unpOrder.getBuyOrderNo()));//第三方业务编号 多个以","隔开 (销售不用传)
                certificateService.createBuyCertificate(agent, certificateCreateVO);
                
                //更改采购单
                buyOrderService.updatePayStatus(agent, unpOrder.getBuyOrderNo(), payStatus);
                unpOrderDao.updateById(unpOrder);
            }
            if (null != unpOrder.getChannelId() && null != unpOrder.getBuyChannelId() && (2 == unpOrder.getPayStatus() || 3 == unpOrder.getPayStatus()) && (2 == unpOrder.getBuyPayStatus() || 3 == unpOrder.getBuyPayStatus())) {
                //更改订单信息
                unpOrder.setStatus(3);//已处理
                unpOrder.setModifier(agent.getAccount());
                unpOrder.setModifyTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
                unpOrderDao.updateById(unpOrder);
            }
            log.info("支付通用产品订单结束==========");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GSSException("支付失败", "1010", "支付失败" + e);
        }
        return true;
    }
}


package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleQueryOrderVo;
import com.tempus.gss.product.ift.api.service.IExtraOrderService;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.product.ift.dao.BuyOrderExtDao;
import com.tempus.gss.product.ift.dao.LegDao;
import com.tempus.gss.product.ift.dao.PassengerDao;
import com.tempus.gss.product.ift.dao.PnrDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;
import com.tempus.tbe.entity.PnrOutPut;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import sun.print.SunMinMaxPage;

import java.math.BigDecimal;
import java.util.*;

/**
 * 杂费单服务实现类
 *
 * @author zhi.li
 * @create 2018-05-10 19:47
 **/
@Service
public class ExtraOrderServiceImpl implements IExtraOrderService {

    private static Logger logger = LogManager.getLogger(ExtraOrderServiceImpl.class);

    @Reference
    ITransationOrderService transationOrderService;
    @Reference
    IMaxNoService maxNoService;
    @Reference
    IOrderService orderService;
    @Reference
    ISaleOrderService saleOrderService;
    @Reference
    ILogService logerService;
    @Autowired
    LegDao legdao;
    @Autowired
    PassengerDao passengerDao;
    @Autowired
    PnrDao pnrDao;
    @Autowired
    BuyOrderExtDao buyOrderExtDao;
    @Value("${dpsconfig.job.owner}")
    protected String owner;



    @Override
    @Transactional
    public void createExtraOrder(Agent agent, RequestWithActor<OrderCreateVo> requestWithActor) {
        validateParam(requestWithActor);
        logger.info("开始国际机票预订,预订参数:"+ JSONObject.toJSONString(requestWithActor));
        SaleOrderExt saleOrderExt =  requestWithActor.getEntity().getSaleOrderExt();
        Long transactionId = IdWorker.getId();
        logger.info("国际机票白屏预订交易单号:{}",transactionId);
        //saleOrderExt.setSaleOrder(getSaleOrder(agent,transactionId));
        String contact = saleOrderExt.getContactName();//联系人
        String mobile = saleOrderExt.getContactMobile();
        Date createTime = new Date();
        //保存交易单信息
        saveTransactionOrder(agent,contact,mobile,transactionId,createTime);
        List<Passenger> passengers = saleOrderExt.getPassengerList();
        BigDecimal receivable = this.getRecivable(passengers);
        SaleOrder saleOrder = getSaleOrder(agent,transactionId);
        saleOrder.setReceivable(receivable);
        saleOrderExt.setSaleOrder(saleOrder);
        requestWithActor.getEntity().setSaleOrderExt(saleOrderExt);
        try {
            //调用国际订单创建接口
          orderService.createOrder(requestWithActor);
        } catch (Exception e) {
            logger.error("国际机票白屏预订有误",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateExtraOrderStatus() {
        Integer[] createTypeArray ={7,8,9,10,11,12};
        List<SaleOrderExt> saleOrderExts =orderService.getAssignedOrders(createTypeArray);
        if(saleOrderExts!=null && saleOrderExts.size()>0) {
            logger.info("更新杂费单订单数量："+saleOrderExts.size());
            Date date = new Date();
            for (SaleOrderExt orderExt : saleOrderExts) {
                Long saleOrderNo = orderExt.getSaleOrderNo();
                Agent agent = new Agent(Integer.valueOf(owner));
                try {
                    saleOrderService.updateStatus(agent, saleOrderNo, 4);
                    saveLog(saleOrderNo,date);
                } catch (Exception e) {
                    logger.error("杂费单状态修改异常：", e);
                }
            }
            logger.info("本次更新杂费单状态结束");
        }else{
            logger.info("本次更新杂费单状态数量为0");
        }
    }


    //保存日志
    private void saveLog(Long orderNo,Date date){
        try {
            LogRecord logRecord = new LogRecord();
            logRecord.setAppCode("IFT");
            logRecord.setCreateTime(date);
            logRecord.setTitle("国际杂费单");
            logRecord.setDesc("定时更新已支付杂费单状态");
            logRecord.setOptLoginName("sys");
            logRecord.setRequestIp("127.0.0.1");
            logRecord.setBizCode("IFT-ExtraOrderServiceImpl-updateExtraOrderStatus");
            logRecord.setBizNo(String.valueOf(orderNo));
            logerService.insert(logRecord);
        }catch (Exception e){
            logger.error("定时更新杂费单状态时保存日志异常",e);
        }
    }

    /** 获取已支付的杂费订单 */
    private void validateParam(RequestWithActor<OrderCreateVo> requestWithActor){
          /* 校验登录用户 */
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("登录用户为空", "0101", "创建订单失败");
        }

         /* 校验条件 */
        if (requestWithActor.getEntity() == null) {
            throw new GSSException("销售单为空", "0101", "创建订单失败");
        }

        SaleOrderExt saleOrderExt =  requestWithActor.getEntity().getSaleOrderExt();

        if (saleOrderExt.getLegList() == null && saleOrderExt.getPassengerList() == null) {
            throw new GSSException("航程或乘客为空", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder() == null) {
            logger.error("销售单信息为空");
            throw new GSSException("销售单信息为空", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getCustomerNo() == null || saleOrderExt.getSaleOrder().getCustomerNo() == 0) {
            logger.error("销售单的客户编号为空(CustomerNo)");
            throw new GSSException("销售单的客户编号为空(CustomerNo)", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getCustomerTypeNo() == null || saleOrderExt.getSaleOrder().getCustomerTypeNo() == 0) {
            logger.error("销售单的客户类型为空(CustomerTypeNo)");
            throw new GSSException("销售单的客户类型为空(CustomerTypeNo)", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getOrderingLoginName() == null) {
            logger.error("销售单的用户登录名为空(OrderingLoginName)");
            throw new GSSException("销售单的用户登录名为空(OrderingLoginName)", "0102", "创建订单失败");
        }
    }

    //获取交易单
    private SaleOrder getSaleOrder(Agent agent,Long transactionId){
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setTransationOrderNo(transactionId);
        saleOrder.setSourceChannelNo(agent.getDevice());
        saleOrder.setCustomerNo(agent.getNum());
        saleOrder.setCustomerTypeNo(agent.getType());
        saleOrder.setOrderingLoginName(agent.getAccount());
        saleOrder.setOrderType(1);
        return saleOrder;
    }

    //构造交易单信息
    private void saveTransactionOrder(Agent agent,String contacts,String mobile,Long transactionId,Date createTime){
        TransationOrder transationOrder = new TransationOrder();
        transationOrder.setContacts(contacts);
        transationOrder.setCreateTime(createTime);
        transationOrder.setCustomerNo(agent.getNum());
        transationOrder.setCustomerTypeNo(agent.getType());
        transationOrder.setMobile(mobile);
        transationOrder.setOrderingLoginName(agent.getAccount());
        transationOrder.setOwner(agent.getOwner());
        transationOrder.setSourceChannelNo(agent.getDevice());
        transationOrder.setPayStatus(1);
        transationOrder.setTransationOrderNo(transactionId);
        transationOrder.setValid(1);
        //生成交易单
        transationOrderService.create(agent, transationOrder);
        logger.info("杂费单交易信息：{}",transationOrder.toString());
    }


    private BigDecimal getRecivable(List<Passenger> passengers){
        BigDecimal sum = new BigDecimal(0);
        for (Passenger passenger : passengers) {
            if ("1".equals(passenger.getPassengerType())) {
                passenger.setPassengerType("ADT");
            }
            if ("2".equals(passenger.getPassengerType())) {
                passenger.setPassengerType("CHD");
            }
            if ("3".equals(passenger.getPassengerType())) {
                passenger.setPassengerType("INF");
            }
            if (!"ADT".equals(passenger.getPassengerType()) && !"CHD".equals(passenger.getPassengerType()) && !"CNN".equals(passenger.getPassengerType()) && !"INF".equals(passenger.getPassengerType())) {
                throw new GSSException("乘客类型错误,乘客类型只能是ADT(成人) CHD/CNN(儿童) INF(婴儿)", "0101", "创建订单失败");
            }
            if (!"1".equals(passenger.getGender()) && !"0".equals(passenger.getGender())) {
                throw new GSSException("乘客性别错误,0:女 1：男", "0101", "创建订单失败");
            }
            sum = sum.add(passenger.getSalePrice());
        }
        return  sum;
    }


}

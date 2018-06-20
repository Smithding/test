package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.tempus.gss.controller.Result;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.order.entity.DifferenceOrder;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.TransationOrder;
import com.tempus.gss.order.service.IDifferenceOrderService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.order.service.ITransationOrderService;
import com.tempus.gss.pay.service.IPayWayService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.service.IExtraOrderService;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.product.ift.dao.*;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    @Reference
    IPayWayService payWayService;
    @Reference
    ICustomerService customerService;
    @Reference
    IDifferenceOrderService differenceOrderService;
    @Reference
    IUserService userService;
    @Reference
    ISupplierService supplierService;
    @Autowired
    LegDao legdao;
    @Autowired
    PassengerDao passengerDao;
    @Autowired
    SaleOrderDetailDao saleOrderDetailDao;
    @Autowired
    PnrDao pnrDao;
    @Autowired
    BuyOrderExtDao buyOrderExtDao;
    @Value("${dpsconfig.job.owner}")
    protected String owner;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createExtraOrder(RequestWithActor<DifferenceOrder> requestWithActor) {
        logger.info("开始国际机票预订,预订参数:" + JSONObject.toJSONString(requestWithActor));
        Result result = new Result();
        try {
        DifferenceOrder differenceOrderInput = requestWithActor.getEntity();
        result = validateParam(differenceOrderInput, result);
        if(StringUtils.equals(result.getCode(),"0")){
            return result;
        }
        Agent agent =requestWithActor.getAgent();
        Integer deptCode = 0;
        String goodsName = "";
        Integer goodsSubType = 0;
        Long businessSignNo = null;
        Integer goodsType = differenceOrderInput.getGoodsType();
        //Integer businessType = differenceOrderInput.getBusinessType();
        Long businessOrderNo = differenceOrderInput.getBusinessOrderNo();
        Long customerNo = differenceOrderInput.getCustomerNo();
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, differenceOrderInput.getBusinessOrderNo());
        RequestWithActor<Long> param = new RequestWithActor<>();
        if (null != saleOrder && null != saleOrder.getSaleOrderNo()) {
            param.setEntity(saleOrder.getSaleOrderNo());
            SaleOrderExt saleOrderExt = orderService.getOrder(param);
            if (null != saleOrderExt && saleOrderExt.getSaleOrder() != null) {
                try {
                    if (null != saleOrderExt.getSaleOrder().getGoodsType()) {
                        goodsSubType = saleOrderExt.getSaleOrder().getGoodsType();
                    }
                    String creator = saleOrderExt.getSaleOrder().getOrderingLoginName();
                    User user = userService.findUserByLoginName(agent, creator);
                    deptCode = user.getDeptId();
                    if (null == deptCode) {
                        deptCode = 0;
                    }
                    if (null != saleOrder.getBusinessSignNo()) {
                        businessSignNo = saleOrder.getBusinessSignNo();
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                    logger.error("国际查询PNR失败！");
                }
            }

        }
        DifferenceOrder differenceOrder = new DifferenceOrder();
        differenceOrder.setOwner(agent.getOwner());
        if (customerNo != null) {
            differenceOrder.setCustomerNo(customerNo);
            Customer customer = customerService.getCustomerByNo(agent, customerNo);
            if (null != customer && customer.getCustomerTypeNo() != null) {
                differenceOrder.setCustomerTypeNo(customer.getCustomerTypeNo().intValue());
            } else {
                differenceOrder.setCustomerNo(customerNo);
                Supplier supplier = supplierService.getSupplierByNo(agent, customerNo);
                if (null != supplier && supplier.getCustomerTypeNo() != null) {
                    differenceOrder.setCustomerTypeNo(supplier.getCustomerTypeNo().intValue());
                }
            }
        }
        differenceOrder.setTraNo(differenceOrderInput.getTraNo());
        differenceOrder.setBusinessOrderNo(businessOrderNo);
        differenceOrder.setBusinessType(differenceOrderInput.getBusinessType());
        differenceOrder.setReceivable(differenceOrderInput.getReceivable());
        differenceOrder.setDifferenceDesc(differenceOrderInput.getDifferenceDesc());
        differenceOrder.setDifferenceType(differenceOrderInput.getDifferenceType());
        differenceOrder.setIncomeExpenseType(differenceOrderInput.getIncomeExpenseType());
        differenceOrder.setPnr(differenceOrderInput.getPnr());
        differenceOrder.setTicketNos(differenceOrderInput.getTicketNos());

        Calendar calendar = Calendar.getInstance();
        differenceOrder.setOrderingTime(calendar.getTime());

        differenceOrder.setActorTime(calendar.getTime());
        differenceOrder.setActorUser(agent.getAccount());
        differenceOrder.setActorDesc("创建");

        differenceOrder.setDeptCode(deptCode.toString());
        differenceOrder.setGoodsName(goodsName);
        differenceOrder.setGoodsSubType(goodsSubType);
        differenceOrder.setGoodsType(goodsType);
        differenceOrder.setPayStatus(1);
        differenceOrder.setSettlementStatus(1);
        differenceOrder.setBusinessSignNo(businessSignNo);
        differenceOrder.setIncidentalType(differenceOrderInput.getDifferenceType());
        differenceOrder.setLog(this.filterNull(differenceOrder.getLog()) + "$EOF$" + differenceOrder.getActorTime() + "$BREAK$" + differenceOrder.getActorUser() + "$BREAK$" + differenceOrder.getActorDesc());
        differenceOrderService.create(agent, differenceOrder);
        result.setCode("1");
        result.setMessage("创建成功");
        }catch (Exception e){
            logger.error("保存杂费单时异常",e);
            result.setCode("0");
            result.setMessage("保存杂费单时异常");
        }
        return result;
    }

    @Override
    public void updateExtraOrderStatus(RequestWithActor<DifferenceOrder> requestWithActor) throws Exception {
        DifferenceOrder extraOrder = requestWithActor.getEntity();
        if(extraOrder ==null || extraOrder.getDifferenceOrderNo() ==null || extraOrder.getAuditStatus()==null){
            throw new RuntimeException("审核的杂费单信息参数异常");
        }
        logger.info("主管审核的杂费单信息:"+extraOrder.toString());
        Integer status = extraOrder.getAuditStatus();
        if(StringUtils.equals("-1",String.valueOf(status)) && StringUtils.isEmpty(extraOrder.getActorDesc())){
            throw new RuntimeException("杂费单审核不通过时，备注信息不能为空");
        }
        Agent agent = requestWithActor.getAgent();
        differenceOrderService.update(agent,extraOrder);
    }

    
    /** 获取已支付的杂费订单 */
    private Result validateParam(DifferenceOrder differenceOrderInput,Result result) {
        if (null == differenceOrderInput) {
            result.setMessage("空,创建差异单失败!");
            result.setCode("0");
        } else if (differenceOrderInput.getBusinessOrderNo() == null) {
            result.setMessage("单号为空,创建差异单失败!");
            result.setCode("0");
        } else if (differenceOrderInput.getDifferenceType() == 0) {
            result.setMessage("差异单类型为空,创建差异单失败!");
            result.setCode("0");
        } else if ("".equals(differenceOrderInput.getDifferenceDesc()) || differenceOrderInput.getDifferenceDesc() == null) {
            result.setMessage("差异单说明为空,创建差异单失败!");
            result.setCode("0");
        } else if (differenceOrderInput.getIncomeExpenseType() == null) {
            result.setMessage("差异单收支类型为空,创建差异单失败!");
            result.setCode("0");
        }
        return result;
    }
    
    //获取交易单
    private SaleOrder getSaleOrder(Agent agent, Long transactionId) {
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
    private void saveTransactionOrder(Agent agent, String contacts, String mobile, Long transactionId, Date createTime) {
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
        logger.info("杂费单交易信息：{}", transationOrder.toString());
    }
    
    private BigDecimal getRecivable(List<Passenger> passengers) {
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
        return sum;
    }

    public final String datetime2Str(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
        return "";
    }

    private String filterNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
}

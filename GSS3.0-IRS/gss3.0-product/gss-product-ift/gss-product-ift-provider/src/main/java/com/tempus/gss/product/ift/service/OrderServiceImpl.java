package com.tempus.gss.product.ift.service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

import com.tempus.gss.mq.MqSender;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.CostType;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.entity.vo.UpdatePlanAmountVO;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.service.*;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.product.ift.dao.*;
import com.tempus.gss.product.ift.help.IftLogHelper;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.websocket.SocketDO;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.service.IGetPnrService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.cps.entity.Account;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.IAccountService;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ICustomerTypeService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.mss.service.IMssReserveService;
import com.tempus.gss.pay.service.facade.IPayRestService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.vo.BlackOrderExtVo;
import com.tempus.gss.product.ift.api.entity.vo.DemandTeamVo;
import com.tempus.gss.product.ift.api.entity.vo.IftTicketMessage;
import com.tempus.gss.product.ift.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderInformVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderPriceVo;
import com.tempus.gss.product.ift.api.entity.vo.OrderRefuseRequest;
import com.tempus.gss.product.ift.api.entity.vo.PassengerListVo;
import com.tempus.gss.product.ift.api.entity.vo.PassengerVo;
import com.tempus.gss.product.ift.api.entity.vo.QueryPnrAndTimeVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderDetailVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleQueryOrderVo;
import com.tempus.gss.product.ift.api.entity.vo.TicketRequest;
import com.tempus.gss.product.ift.api.entity.vo.WarnOrderRequest;
import com.tempus.gss.product.ift.api.entity.webservice.InairlinesVo;
import com.tempus.gss.product.ift.api.entity.webservice.InpayVo;
import com.tempus.gss.product.ift.api.entity.webservice.InsaleVo;
import com.tempus.gss.product.ift.api.entity.webservice.settt.InallsaleVo;
import com.tempus.gss.product.ift.api.entity.webservice.settt.InsaleService;
import com.tempus.gss.product.ift.api.entity.webservice.settt.InsaleService_Service;
import com.tempus.gss.product.ift.api.entity.webservice.settt.ReturnSettInfo;
import com.tempus.gss.product.ift.mq.IftTicketMqSender;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbe.entity.PnrOutPut;

@Service
@org.springframework.stereotype.Service("iftOrderService")
@EnableAutoConfiguration
public class OrderServiceImpl implements IOrderService {
    
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    SaleOrderExtDao saleOrderExtDao;
    @Autowired
    LegDao legdao;
    @Autowired
    PassengerDao passengerDao;
    @Autowired
    SaleOrderDetailDao saleOrderDetailDao;
    @Reference
    IGetPnrService getPnrService;
    @Reference
    IConfigsService configsService;
    /* 销售单 */
    @Reference
    ISaleOrderService saleOrderService;
    
    /* 应收应付 */
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    
    /* 采购单 */
    @Reference
    IBuyOrderService buyOrderService;
    
    @Autowired
    PnrDao pnrDao;
    
    @Autowired
    BuyOrderExtDao buyOrderExtDao;
    
    @Autowired
    BuyOrderDetailDao buyOrderDetailDao;
    
    @Reference
    IMaxNoService maxNoService;
    
    @Reference
    ISupplierService supplierService;
    
    @Reference
    ICertificateService certificateService;
    
    @Reference
    IAccountService accountService;
    
    @Reference
    IPayRestService payRestService;
    
    @Reference
    ICustomerTypeService customerTypeService;
    
    @Reference
    IMssReserveService mssReserveService;
    
    @Reference
    ICustomerService customerService;
    
    @Reference
    IOrderService orderService;
    
    @Reference
    IPassengerService passengerService;
    
    @Reference
    ITicketSenderService iTicketSenderService;
    
    @Reference
    ITransationOrderService transationOrderService;
    
    @Reference
    IWarnOrderService warnOrderService;
    
    @Reference
    protected IPlanAmountRecordService needPayService;
    
    @Autowired
    IftPlaneTicketService planeTicketService;
    
    @Autowired
    GssMainDao gssMainDao;
    
    @Autowired
    AdjustOrderDao adjustOrderDao;
    
    @Autowired
    IftTicketMqSender iftTicketMqSender;
    @Reference
    IAirportService airportService;
    @Autowired
    MqSender mqSender;
    @Reference
    IUserService userService;
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    @Reference
    protected ITicketSenderService ticketSenderService;
    @Autowired
    IftMessageServiceImpl iftMessageServiceImpl;
    
    @Value("${dpsconfig.job.owner}")
    protected String owner;
    
    /**
     * 创建订单. 通过白屏查询、Pnr、需求单、手工方式创建订单.
     *
     * @param requestWithActor
     * @return
     */
    @Override
    @Transactional
    public SaleOrderExt createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {

//        log.info("创建订单开始========" + JsonUtil.toJson(requestWithActor));
        
        /* 校验登录用户 */
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("登录用户为空", "0101", "创建订单失败");
        }
        /* 校验条件 */
        if (requestWithActor.getEntity() == null) {
            throw new GSSException("销售单为空", "0101", "创建订单失败");
        }
        /* 校验销售单条件 */
        SaleOrderExt saleOrderExt = requestWithActor.getEntity().getSaleOrderExt();
        if (saleOrderExt.getLegList() == null && saleOrderExt.getPassengerList() == null) {
            throw new GSSException("航程或乘客为空", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder() == null) {
            log.error("销售单信息为空");
            throw new GSSException("销售单信息为空", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getCustomerNo() == null || saleOrderExt.getSaleOrder().getCustomerNo() == 0) {
            log.error("销售单的客户编号为空(CustomerNo)");
            throw new GSSException("销售单的客户编号为空(CustomerNo)", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getCustomerTypeNo() == null || saleOrderExt.getSaleOrder().getCustomerTypeNo() == 0) {
            log.error("销售单的客户类型为空(CustomerTypeNo)");
            throw new GSSException("销售单的客户类型为空(CustomerTypeNo)", "0102", "创建订单失败");
        }
        if (saleOrderExt.getSaleOrder().getOrderingLoginName() == null) {
            log.error("销售单的用户登录名为空(OrderingLoginName)");
            throw new GSSException("销售单的用户登录名为空(OrderingLoginName)", "0102", "创建订单失败");
        }
        Long saleOrderNo = maxNoService.generateBizNo("IFT_SALE_ORDER_NO", 37);
        Long businessSignNo = IdWorker.getId();
        List<Passenger> passengers = saleOrderExt.getPassengerList();
        try {
            Agent agent = requestWithActor.getAgent();
            Date today = new Date();
            String creator = String.valueOf(agent.getAccount());
            /* 新增航程航段 */
            StringBuffer goodsName = new StringBuffer();
            int legSize = requestWithActor.getEntity().getSaleOrderExt().getLegList().size();
            for (int i = 0; i < legSize; i++) {
                Leg leg = requestWithActor.getEntity().getSaleOrderExt().getLegList().get(i);
                leg.setSaleOrderNo(saleOrderNo);
                leg.setLegNo(maxNoService.generateBizNo("IFT_LEG_NO", 27));
                leg.setParentSection(i);
                // 启用状态
                leg.setStatus(String.valueOf(1));
                leg.setValid((byte) 1);
                leg.setCreator(creator);
                leg.setCreateTime(today);
                leg.setOwner(agent.getOwner());
                // 原始航段
                leg.setChildSection(0);
                legdao.insertSelective(leg);
                goodsName.append(leg.getDepAirport());
                goodsName.append("-");
                if (i == legSize - 1) {
                    goodsName.append(leg.getArrAirport());
                }
                
            }
            /* 增加旅客信息 */
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
                passenger.setSaleOrderNo(saleOrderNo);
                passenger.setPassengerNo(maxNoService.generateBizNo("IFT_PASSENGER_NO", 29));
                passenger.setOwner(agent.getOwner());
                passenger.setStatus(String.valueOf(1));
                passenger.setCreator(creator);
                passenger.setCreateTime(today);
                //todo 出票类型和政策有关目前先保存BSP
                passenger.setTicketType("BSP");
                passenger.setValid((byte) 1);
                passengerDao.insertSelective(passenger);
            }
            
            /* 创建采购单 */
            Long buyOrderNo = maxNoService.generateBizNo("IFT_BUY_ORDER_NO", 24);
            BuyOrderExt buyOrderExt = requestWithActor.getEntity().getBuyOrderExt();
            if (buyOrderExt == null) {
                buyOrderExt = new BuyOrderExt();
            }
            if (buyOrderExt.getBuyOrder() == null) {
                buyOrderExt.setBuyOrder(new BuyOrder());
            }
            buyOrderExt.getBuyOrder().setOwner(agent.getOwner());
            buyOrderExt.getBuyOrder().setBuyOrderNo(buyOrderNo);
            buyOrderExt.getBuyOrder().setSaleOrderNo(saleOrderNo);
            buyOrderExt.getBuyOrder().setGoodsType(2);// 商品大类 2=国际机票
            //见com.tempus.gss.order.entity.EgoodsSubType（OS）
            buyOrderExt.getBuyOrder().setGoodsSubType(2);// 采购单设为2
            buyOrderExt.getBuyOrder().setGoodsName(goodsName.toString());
            
            if (buyOrderExt.getBuyOrder().getSupplierNo() == null || buyOrderExt.getBuyOrder().getSupplierNo() == 0 || buyOrderExt.getBuyOrder().getSupplierTypeNo() == null || buyOrderExt.getBuyOrder().getSupplierTypeNo() == 0) {
                /* 查询客商编号，默认第一个数据 */
                Supplier supplier = new Supplier();
                supplier.setProductType("1000002");
                List<Supplier> supplierList = supplierService.getSupplierList(agent, supplier);
                if (supplierList == null || supplierList.size() == 0) {
                    log.error("获取客商编号为空");
                    throw new GSSException("获取客商编号为空", "0101", "创建订单失败");
                }
                if (requestWithActor.getEntity().getBuyOrderExt() != null && requestWithActor.getEntity().getBuyOrderExt().getSupplierNo() != null) {
                    buyOrderExt.getBuyOrder().setSupplierNo(requestWithActor.getEntity().getBuyOrderExt().getSupplierNo());
                    buyOrderExt.getBuyOrder().setSupplierTypeNo(requestWithActor.getEntity().getBuyOrderExt().getSupplierTypeNo());
                } else {
                    buyOrderExt.getBuyOrder().setSupplierNo(supplierList.get(0).getSupplierNo());
                    buyOrderExt.getBuyOrder().setSupplierTypeNo(supplierList.get(0).getCustomerTypeNo());
                }
            }
            
            buyOrderExt.getBuyOrder().setBuyChannelNo(agent.getDevice());
            buyOrderExt.getBuyOrder().setBusinessSignNo(businessSignNo);// 业务批次号
            buyOrderExt.getBuyOrder().setBsignType(1);// 1销采 2换票 3废和退 4改签
            buyOrderExt.getBuyOrder().setBuyChildStatus(1);// 未审核
            
            /* 创建采购单的pnr信息 */
            Pnr pnr = saleOrderExt.getImportPnr();
            if (pnr != null) {
                pnr.setPnrNo(maxNoService.generateBizNo("IFT_PNR_NO", 32));
                /* 设置采购单编号 */
                try {
                    PnrOutPut pnrOutPut = getPnrService.getPnr(StringUtils.defaultString(buyOrderExt.getAirline()), pnr.getPnr());
                    pnr.setPnrContent(pnrOutPut.getPnrInfo());
                    pnr.setBigPnr(pnrOutPut.getIcsPnr());
                } catch (Exception e) {
                    log.info("获取pnr信息失败", e);
                }
                pnr.setCreateTime(new Date());
                pnr.setCreator(String.valueOf(agent.getId()));
                pnr.setOwner(agent.getOwner());
                pnr.setPnrType(1);// 1：ETERM
                pnr.setSourceNo(saleOrderNo);
                pnr.setValid((byte) 1);
                log.info("pnr表保存信息:" + pnr.toString());
                pnrDao.insertSelective(pnr);
            }
            
            /* 创建主订单 */
            SaleOrder saleOrder = saleOrderExt.getSaleOrder();
            if (saleOrder == null) {
                saleOrder = new SaleOrder();
            }
            saleOrder.setSaleOrderNo(saleOrderNo);
            saleOrder.setOwner(agent.getOwner());
            saleOrder.setSourceChannelNo(saleOrder.getSourceChannelNo() == null ? agent.getDevice() : saleOrder.getSourceChannelNo());
            saleOrder.setCustomerTypeNo(saleOrder.getCustomerTypeNo() == null ? 0 : saleOrder.getCustomerTypeNo());
            saleOrder.setCustomerNo(saleOrder.getCustomerNo() == null ? 0 : saleOrder.getCustomerNo());
            saleOrder.setOrderingLoginName(agent.getAccount());
            saleOrder.setGoodsType(2);// 商品大类 2=国际机票
            saleOrder.setGoodsSubType(1);// 销售单设为1
            saleOrder.setGoodsName(goodsName.toString());
            saleOrder.setOrderingTime(today);// 下单时间
            saleOrder.setPayStatus(1);// 待支付
            saleOrder.setValid(1);// 有效
            saleOrder.setBusinessSignNo(businessSignNo);// 业务批次号
            saleOrder.setBsignType(1);// 1销采 2换票 3废和退 4改签
            saleOrder.setOrderChildStatus(saleOrder.getOrderChildStatus() == null ? 1 : saleOrder.getOrderChildStatus());
            // 1.待核价
            // 2.已核价
            // 3.出票中
            // 4.已出票
            // 5.已取消
            saleOrderService.create(requestWithActor.getAgent(), saleOrder);
            
            buyOrderService.create(requestWithActor.getAgent(), buyOrderExt.getBuyOrder());
            
            /* 封装人+航段对象 */
            List<SaleOrderDetail> saleOrderDetailList = new ArrayList<>();
            for (Leg leg : saleOrderExt.getLegList()) {
                for (Passenger passenger : passengers) {
                    SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
                    saleOrderDetail.setSaleOrderDetailNo(maxNoService.generateBizNo("IFT_SALE_ORDER_DETAIL_NO", 36));
                    /* 设置航程 */
                    saleOrderDetail.setLegNo(leg.getLegNo());
                    /* 设置乘客 */
                    saleOrderDetail.setPassengerNo(passenger.getPassengerNo());
                    saleOrderDetail.setSaleOrderNo(saleOrderNo);
                    saleOrderDetail.setOwner(agent.getOwner());
                    saleOrderDetail.setValid((byte) 1);
                    saleOrderDetail.setCreator(creator);
                    saleOrderDetail.setCreateTime(today);
                    saleOrderDetail.setIsChange(false);
                    saleOrderDetail.setStatus("1");
                    saleOrderDetail.setCabin(leg.getCabin());
                    saleOrderDetail.setDeptProfit(passenger.getDeptProfit());
                    saleOrderDetail.setProfit(passenger.getProfit());
                    /* 创建销售订单明细 */
                    saleOrderDetailDao.insertSelective(saleOrderDetail);
                    saleOrderDetail.setPassenger(passenger);
                    saleOrderDetailList.add(saleOrderDetail);
                }
            }
            
            /* 采购单明细 */
            for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
                BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
                buyOrderDetail.setBuyOrderDetailNo(maxNoService.generateBizNo("IFT_BUY_ORDER_DETAIL_NO", 23));
                buyOrderDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
                buyOrderDetail.setBuyOrderNo(buyOrderNo);
                buyOrderDetail.setOwner(agent.getOwner());
                buyOrderDetail.setCreateTime(today);
                buyOrderDetail.setCreator(creator);
                buyOrderDetail.setValid((byte) 1);
                buyOrderDetailDao.insertSelective(buyOrderDetail);
            }
            
            /* 创建销售拓展单 */
            saleOrderExt.setSaleOrderNo(saleOrderNo);
            if (pnr != null && pnr.getPnrNo() != null) {
                saleOrderExt.setPnrNo(pnr.getPnrNo());// 设置pnr编号
            }
            saleOrderExt.setOwner(agent.getOwner());
            saleOrderExt.setCreator(creator);
            saleOrderExt.setCreateTime(today);
            saleOrderExt.setValid((byte) 1);
            saleOrderExt.setLocker(0L);// 解锁状态
//            saleOrderExt.setSaleCurrency();
            if (null == saleOrderExt.getSaleCurrency()) {
                saleOrderExt.setSaleCurrency("CNY");
            }
            if (null == saleOrderExt.getExchangeRate()) {
                IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
                Map<String, Object> map = configs.getConfigsMap();
                Object object = map.get("exChangeRate");
                if (object != null && "" != object) {
                    String exChangeRateStr = object.toString();
                    BigDecimal exChangeRate = new BigDecimal(exChangeRateStr);
                    saleOrderExt.setExchangeRate(exChangeRate);
                }
            }
            log.info("国际保存的订单扩展表信息:" + saleOrderExt.toString());
            saleOrderExtDao.insertSelective(saleOrderExt);
            
            /* 创建采购单 */
            //todo 出票类型和政策有关目前先保存BSP
            buyOrderExt.setTicketType("BSP");
            buyOrderExt.setBuyOrderNo(buyOrderNo);
            buyOrderExt.setOwner(agent.getOwner());
            buyOrderExt.setCreator(creator);
            buyOrderExt.setCreateTime(today);
            buyOrderExt.setValid((byte) 1);
            buyOrderExtDao.insertSelective(buyOrderExt);
            log.info("创建国际订单成功=====saleOrderNo=" + saleOrderNo);
            // String logstr = "<p>" + String.format("创建国际订单成功=====", new Date()) + ":saleOrderNo=" + JsonUtil.toJson(saleOrderNo);
            
            /* 创建操作日志 */
            try {
                String title = "创建国际销售单";
                String logstr = "用户" + agent.getAccount() + "创建国际销售单：" + "[" + saleOrderNo + "]";
                IftLogHelper.logger(agent, saleOrder.getTransationOrderNo(), saleOrderNo, title, logstr);
            } catch (Exception e) {
                log.error("添加操作日志异常===" + e);
            }
            //将插入的数据原样返回
            saleOrderExt.setSaleOrderDetailList(saleOrderDetailList);
            
        } catch (GSSException ex) {
            log.error("创建订单失败", ex);
            throw ex;
        } catch (Exception e) {
            log.error("创建订单失败", e);
            throw new GSSException("创建订单异常," + e.getMessage(), "0101", "创建订单失败");
        }
        
        log.info("创建订单结束========");
        return saleOrderExt;
    }
    
    /**
     * 根据订单编号查询订单.
     *
     * @param orderNo
     * @return
     */
    @Override
    public SaleOrderExt getOrder(RequestWithActor<Long> orderNo) {
        
        log.info("订单查询开始======orderNo=" + orderNo.getEntity());
        SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(orderNo.getEntity());
        if (saleOrderExt == null) {
            log.error("订单号不存在,orderNo=" + orderNo.getEntity());
            return null;
        }
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(orderNo.getAgent(), orderNo.getEntity());
        if (saleOrder != null) {
            saleOrderExt.setSaleOrder(saleOrder);
        }
        log.info("订单查询结束======");
        return saleOrderExt;
    }
    
    /**
     * @param saleOrderExt
     * @return
     */
    @Override
    @Transactional
    public int updateSaleOrderExt(RequestWithActor<SaleOrderExt> saleOrderExt) {
        SaleOrderExt saleOrderExt1 = saleOrderExt.getEntity();
        log.info("updateSaleOrderExt 更新扩展表信息" + saleOrderExt1.toString());
        int flag = saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt1);
        if (flag == 0) {
            throw new GSSException("修改订单模块", "0301", "修改订单失败");
        }
        /* 创建操作日志 */
        try {
            String logstr = null;
            String title = null;
            if (saleOrderExt.getEntity().getSaleOrderDetailList() != null && saleOrderExt.getEntity().getSaleOrderDetailList().get(0).getStatus().equals("1")) {
                title = "锁定国际销售单";
                logstr = "用户" + saleOrderExt.getAgent().getAccount() + "锁定国际销售单：" + "[" + saleOrderExt.getEntity().getSaleOrderNo() + "]";
                
            } else if (saleOrderExt.getEntity().getSaleOrderDetailList() != null && saleOrderExt.getEntity().getSaleOrderDetailList().get(0).getStatus().equals("15")) {
                title = "打回订单";
                logstr = "用户" + saleOrderExt.getAgent().getAccount() + "打回订单：" + "[" + saleOrderExt.getEntity().getSaleOrderNo() + "]";
                
            } else {
                title = "修改国际销售单";
                logstr = "用户" + saleOrderExt.getAgent().getAccount() + "修改国际销售单：" + "[" + saleOrderExt.getEntity().getSaleOrderNo() + "]";
            }
            IftLogHelper.logger(saleOrderExt.getAgent(), saleOrderExt.getEntity().getSaleOrderNo(), title, logstr);
            /*LogRecord logRecord = new LogRecord();
            logRecord.setAppCode("UBP");
            logRecord.setCreateTime(new Date());
            logRecord.setTitle("修改国际销售单");
            logRecord.setDesc(logstr);
            logRecord.setOptLoginName(saleOrderExt.getAgent().getAccount());
            logRecord.setRequestIp(saleOrderExt.getAgent().getIp());
            logRecord.setBizCode("IFT");
            logRecord.setBizNo(String.valueOf(saleOrderExt.getEntity().getSaleOrderNo()));
            logService.insert(logRecord);*/
        } catch (Exception e) {
            log.error("添加操作日志异常===", e);
        }
        return flag;
    }
    
    /**
     * @param saleOrderExt
     * @return
     */
    @Override
    @Transactional
    public int auditOrder(RequestWithActor<SaleOrderExt> saleOrderExt, Long supplierNo, String airLine, String ticketType) {
        
        Agent agent = saleOrderExt.getAgent();
        SaleOrderExt orderExt = saleOrderExt.getEntity();
        log.info("更新的订单信息:" + orderExt.toString());
        int flag = saleOrderExtDao.updateByPrimaryKeySelective(orderExt);
        
        try {
            if (orderExt != null) {
                // 修改buyOrderExt信息
                List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(orderExt.getSaleOrderNo());
                if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                    Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
                    BuyOrderExt buyOrderExt = buyOrderExtList.get(0);
                    buyOrderExt.setSupplierNo(supplierNo);
                    buyOrderExt.setAirline(airLine);
                    buyOrderExt.setTicketType(ticketType);
                    buyOrderExt.setSupplierTypeNo(supplier.getCustomerTypeNo());
                    buyOrderExt.setModifyTime(new Date());
                    buyOrderExt.setModifier(agent.getAccount());
                    buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
                    
                    // 修改buyOrder的supplierNo信息
                    BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderExt.getBuyOrderNo());
                    buyOrder.setSupplierNo(supplierNo);
                    buyOrderService.update(agent, buyOrder);
                }
                
            }
            /* 创建操作日志 */
            try {
                String logstr = "用户" + agent.getAccount() + "国际订单核价：" + "[" + orderExt.getSaleOrderNo() + "]成功";
                String title = "国际订单核价";
                IftLogHelper.logger(agent, orderExt.getSaleOrderNo(), title, logstr);

               /* LogRecord logRecord = new LogRecord();
                logRecord.setAppCode("UBP");
                logRecord.setCreateTime(new Date());
                logRecord.setTitle("国际订单核价");
                logRecord.setDesc(logstr);
                logRecord.setOptLoginName(saleOrderExt.getAgent().getAccount());
                logRecord.setRequestIp(saleOrderExt.getAgent().getIp());
                logRecord.setBizCode("IFT");
                logRecord.setBizNo(String.valueOf(orderExt.getSaleOrderNo()));
                logService.insert(logRecord);*/
            } catch (Exception e) {
                log.error("添加操作日志异常===" + e);
            }
        } catch (Exception e) {
            log.error("修改状态出现异常，e=" + e);
            throw new GSSException("修改状态出现异常，e=" + e, "1001", "修改采购单状态失败");
        }
        return flag;
    }
    
    /**
     * 查询订单. 为运营平台订单管理提供服务.(预定)
     *
     * @param saleOrderQueryRequest
     * @return 结果返回到销售平台，销售平台需要二次处理.
     */
    @Override
    public Page<SaleOrderExt> queryFromSale(Page<SaleOrderExt> page, RequestWithActor<SaleQueryOrderVo> saleOrderQueryRequest) {
        log.info("订单查询参数" + JSON.toJSONString(saleOrderQueryRequest));
        try {
            if (saleOrderQueryRequest.getAgent() == null && saleOrderQueryRequest.getAgent().getOwner() == 0) {
                throw new GSSException("查询订单模块（为运营平台订单管理提供服务）", "0401", "传入参数为空");
            }
            saleOrderQueryRequest.getEntity().setOwner(saleOrderQueryRequest.getAgent().getOwner());
            saleOrderQueryRequest.getEntity().setValid((byte) 1);
            if (saleOrderQueryRequest.getAgent().getNum() != null) {
                saleOrderQueryRequest.getEntity().setCustomerNo(saleOrderQueryRequest.getAgent().getNum().toString());
            }
            
            // long startTime = System.currentTimeMillis();
            log.info("查询订单接口开始=====");
            Boolean isNeedCustomer = saleOrderQueryRequest.getEntity().getCustomerCount();
            List<SaleOrderExt> saleOrderExtList = null;
            if (isNeedCustomer) {
                List<Customer> customers = customerService.getSubCustomerByCno(saleOrderQueryRequest.getAgent(), saleOrderQueryRequest.getAgent().getNum());
                List<Long> longList = new ArrayList<>();
                if (null != customers) {
                    for (Customer customer : customers) {
                        longList.add(customer.getCustomerNo());
                    }
                }
                saleOrderQueryRequest.getEntity().setLongList(longList);
            }
            saleOrderExtList = saleOrderExtDao.queryFromSaleQueryOrderVo(page, saleOrderQueryRequest.getEntity());
            
            //long endTime = System.currentTimeMillis();
            //log.info("查询订单接口结束=====" + (endTime - startTime));
          /*  List<SaleOrderExt> tempList = new ArrayList<>();
            if (null != saleOrderExtList) {
                for (SaleOrderExt order : saleOrderExtList) {
                    SaleOrder saleOrder = saleOrderService.getSOrderByNo(saleOrderQueryRequest.getAgent(), order.getSaleOrderNo());
                    order.setSaleOrder(saleOrder);
                    tempList.add(order);
                }
            }*/
            // long objectTime = System.currentTimeMillis();
            // log.info("封装订单接口结束=====" + (objectTime - endTime));
            page.setRecords(saleOrderExtList);
            
            log.info("查询订单模块（为运营平台订单管理提供服务）结束");
        } catch (Exception e) {
            log.error("查询订单异常", e);
            throw new GSSException("查询订单模块（为运营平台订单管理提供服务）", "0403", "查询订单异常");
        }
        return page;
    }
    
    /**
     * 根据业务情况接收出票消息，修改采购单状态为待出票
     *
     * @param requestWithActor
     * @return
     */
    @Override
    public boolean updateBuyOrderStatus(RequestWithActor<Long> requestWithActor) {
        Agent agent = requestWithActor.getAgent();
        Long saleOrderNo = requestWithActor.getEntity();
        if (agent == null) {
            log.error("agent为空");
            throw new GSSException("agent为空", "1001", "修改采购单状态失败");
        }
        if (saleOrderNo == null) {
            log.error("saleOrderNo为空");
            throw new GSSException("saleOrderNo为空", "1002", "修改采购单状态失败");
        }
        
        try {
            log.info("receiveTicket->" + JSON.toJSONString(requestWithActor));
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
            if (saleOrder != null) {
                saleOrderService.updateStatus(agent, saleOrder.getSaleOrderNo(), 3);// 将销售单改为出票中的状态
                List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);
                for (BuyOrder buyOrder : buyOrderList) {
                    if (buyOrder.getBusinessSignNo().equals(saleOrder.getBusinessSignNo())) {
                        buyOrderService.updateStatus(agent, buyOrder.getBuyOrderNo(), 2);// 将采购单状态改为出票中
                    }
                }
            }
            /* 创建操作日志 */
            try {
                String logstr = "用户" + saleOrder.getModifier() + "成功支付：" + "[" + saleOrderNo + "]" + "￥" + saleOrder.getReceived();
                String title = "订单支付";
                IftLogHelper.logger(agent, saleOrderNo, title, logstr);
            } catch (Exception e) {
                log.error("添加操作日志异常===" + e);
            }
        } catch (Exception e) {
            log.error("修改采购单状态异常");
            throw new GSSException("修改采购单状态异常", "1003", "修改采购单状态失败");
        }
        return true;
    }
    
    /**
     * 生成应收和应付的记录.
     *
     * @param passengerList
     * @return
     */
    @Override
    @Transactional
    public boolean confirmPrice(RequestWithActor<List<Passenger>> passengerList) {
        
        log.info("订单核价开始");
        boolean flag = false;
        try {
            Agent agent = passengerList.getAgent();
            if (agent == null && passengerList.getEntity() == null) {
                throw new GSSException("订单核价模块", "0601", "传入参数为空");
            }
            BigDecimal saleTotal = new BigDecimal(0);// 销售单合计
            BigDecimal buyTotal = new BigDecimal(0);// 采购单合计
            BigDecimal serviceTotal = new BigDecimal(0);// 服务费合计
            for (Passenger passenger : passengerList.getEntity()) {
                if (passenger.getBuyPrice() != null) {
                    buyTotal = buyTotal.add(passenger.getBuyPrice());
                }
                if (passenger.getSalePrice() != null) {
                    saleTotal = saleTotal.add(passenger.getSalePrice());
                }
                // 服务费合计
                if (passenger.getServiceCharge() != null && !"".equals(passenger.getServiceCharge())) {
                    serviceTotal = serviceTotal.add(passenger.getServiceCharge());
                }
            }
            
            // 根据订单编号查询订单
            SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(passengerList.getEntity().get(0).getSaleOrderNo());
            
            List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(passengerList.getEntity().get(0).getSaleOrderNo());
            if (serviceTotal.signum() > 0) {
            
            }
            if (!serviceTotal.equals(BigDecimal.ZERO)) {
                // 创建服务费应收
                CreatePlanAmountVO service = new CreatePlanAmountVO();
                service.setRecordNo(saleOrderExt.getSaleOrderNo());// 记录编号
                service.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                service.setRecordMovingType(CostType.SERVICE_CHARGE.getKey());
                service.setPlanAmount(serviceTotal);// 合计
                service.setBusinessType(BusinessType.SALEORDER.getKey());
                // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                service.setGoodsType(2);
                planAmountRecordService.create(passengerList.getAgent(), service);
            }
            if (saleOrderExt != null) {
                // 销售单应付
                CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                createPlanAmountVOType.setRecordNo(saleOrderExt.getSaleOrderNo());// 记录编号
                createPlanAmountVOType.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                createPlanAmountVOType.setRecordMovingType(CostType.FARE.getKey());
                createPlanAmountVOType.setPlanAmount(saleTotal);// 合计
                createPlanAmountVOType.setBusinessType(BusinessType.SALEORDER.getKey());
                // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                createPlanAmountVOType.setGoodsType(2);
                planAmountRecordService.create(passengerList.getAgent(), createPlanAmountVOType);
            } else {
                log.error("销售单为空");
                throw new GSSException("销售单为空", "0601", "核价修改销售、采购失败");
            }
            if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                // 采购单应收
                CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                createPlanAmountVOType.setRecordNo(buyOrderExtList.get(0).getBuyOrderNo());// 记录编号
                createPlanAmountVOType.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
                createPlanAmountVOType.setRecordMovingType(CostType.FARE.getKey());
                createPlanAmountVOType.setPlanAmount(buyTotal);// 合计
                createPlanAmountVOType.setBusinessType(BusinessType.BUYORDER.getKey());
                // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                createPlanAmountVOType.setGoodsType(2);
                planAmountRecordService.create(passengerList.getAgent(), createPlanAmountVOType);
            } else {
                log.error("采购单为空");
                throw new GSSException("采购单为空", "0601", "核价修改销售、采购失败");
            }
            saleOrderExt.setModifier(agent.getAccount());
            log.info("订单核价，更新saleOrderExt信息" + saleOrderExt.toString());
            saleOrderExtDao.updateByPrimaryKey(saleOrderExt);
            if (saleOrderExt != null) {
                SaleOrderDetail detail = new SaleOrderDetail();
                detail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                detail.setStatus("2");//设置已核价
                saleOrderDetailDao.updateByOrderNo(detail);
            }
            log.info("订单核价结束");
        } catch (Exception e) {
            log.error("订单核价失败", e);
            throw new GSSException("核价修改失败", "0603", "核价修改失败");
        }
        
        return flag;
        
    }
    
    /**
     * 取消订单.
     *
     * @param requestWithActor
     *         ，销售订单号
     * @return
     */
    @Override
    @Transactional
    public boolean cancelOrder(RequestWithActor<Long> requestWithActor) {
        
        boolean flag = true;
        Agent agent = requestWithActor.getAgent();
        Long saleOrderNo = requestWithActor.getEntity();
        log.info("取消订单开始！");
        try {
            if (saleOrderNo == null) {
                log.error("saleOrderNo入参为空==");
                throw new GSSException("saleOrderNo入参为空==", "1001", "取消订单失败");
            }
            
            SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
            List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(saleOrderNo);
            if (saleOrderExt != null) {
                /**
                 * 1、修改订单域子状态
                 * 2、修改国际机票订单状态
                 */
                saleOrderService.updateStatus(agent, requestWithActor.getEntity(), 11);// 改为已取消状态
                SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
                saleOrderDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                saleOrderDetail.setStatus("11");
                saleOrderDetailDao.updateByOrderNo(saleOrderDetail);
                
                List<Passenger> passengers = saleOrderExt.getPassengerList();
                RequestWithActor<List<Passenger>> param = new RequestWithActor<>();
                param.setEntity(passengers);
                param.setAgent(agent);
                this.verify(param);
                //判断是否已支付  若已支付则退款
                if (saleOrderExt.getSaleOrder() != null && (saleOrderExt.getSaleOrder().getPayStatus() == 3 || saleOrderExt.getSaleOrder().getPayStatus() == 4)) {
                    CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                    certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
                    certificateCreateVO.setReason("销售退款单信息"); //补充说明
                    certificateCreateVO.setSubBusinessType(1); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
                    certificateCreateVO.setServiceLine("2");
                    certificateCreateVO.setCustomerNo(saleOrderExt.getSaleOrder().getCustomerNo());
                    certificateCreateVO.setCustomerTypeNo(saleOrderExt.getSaleOrder().getCustomerTypeNo());
                    
                    /**销售退款*/
                    List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
                    BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
                    businessOrderInfo.setActualAmount(saleOrderExt.getSaleOrder().getReceived());
                    businessOrderInfo.setBusinessType(2);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                    businessOrderInfo.setRecordNo(saleOrderExt.getSaleOrderNo());
                    orderInfoList.add(businessOrderInfo);
                    certificateCreateVO.setOrderInfoList(orderInfoList);
                    BigDecimal saleRefundPrice = certificateService.saleRefundCert(agent, certificateCreateVO);
                    log.info("销售退款金额：" + saleRefundPrice);
                    
                    /**
                     * 采购退款
                     * 目前采购只做了记账，所以不用采购退款
                     */
                    /*if(CollectionUtils.isNotEmpty(buyOrderExtList)){
                        BuyOrderExt buyOrderExt = buyOrderExtList.get(0);
                        certificateCreateVO.setIncomeExpenseType(1); //收支类型 1 收，2 为支
                        certificateCreateVO.setReason("采购退款单信息"); //补充说明
                        orderInfoList = new ArrayList<>(); //支付订单
                        businessOrderInfo = new BusinessOrderInfo();
                        //businessOrderInfo.setActualAmount(saleOrderExt.getSaleOrder().getReceived());
                        businessOrderInfo.setBusinessType(3);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                        businessOrderInfo.setRecordNo(buyOrderExt.getBuyOrderNo());
                        orderInfoList.add(businessOrderInfo);
                        certificateCreateVO.setOrderInfoList(orderInfoList);
                        BigDecimal buyRefundPrice = certificateService.buyRefundCert(agent,certificateCreateVO);
                        log.info("采购退款金额："+buyRefundPrice);
                    }*/
                }
            }
            
            
            /* 创建操作日志 */
            try {
                String logstr = "用户" + agent.getAccount() + "国际订单取消：" + "[" + saleOrderNo + "]";
                String title = "国际订单取消";
                IftLogHelper.logger(agent, saleOrderNo, title, logstr);
               /* LogRecord logRecord = new LogRecord();
                logRecord.setAppCode("UBP");
                logRecord.setCreateTime(new Date());
                logRecord.setTitle("国际订单取消");
                logRecord.setDesc(logstr);
                logRecord.setOptLoginName(agent.getAccount());
                logRecord.setRequestIp(agent.getIp());
                logRecord.setBizCode("IFT");
                logRecord.setBizNo(String.valueOf(saleOrderNo));
                logService.insert(logRecord);*/
            } catch (Exception e) {
                log.error("添加操作日志异常===" + e);
            }
        } catch (Exception e) {
            e.getMessage();
            flag = false;
        }
        log.info("取消订单结束");
        return flag;
    }
    
    /**
     * 修改采购价.
     *
     * @param passengerList
     *         ，乘客价格.
     * @return
     */
    // @Override
    @Transactional
    public boolean editBuyPrice(RequestWithActor<List<Passenger>> passengerList) {
        
        log.info("订单核价开始");
        boolean flag = false;
        try {
            if (passengerList == null || passengerList.getEntity().size() == 0) {
                throw new GSSException("订单核价模块", "0801", "传入参数为空");
            }
            // 采购应收、应付合计
            int buytotal = 0;
            // 计算合计
            for (Passenger passenger : passengerList.getEntity()) {
                if (passenger.getBuyFare() != null || passenger.getBuyTax() != null || passenger.getBuyAgencyFee() != null) {
                    BigDecimal buyprice = passenger.getBuyFare().add(passenger.getBuyTax()).add(passenger.getBuyAgencyFee());
                    buytotal += buyprice.intValue();
                }
                // 更新修改后的passenger
                int passengerUpdateFlag = passengerDao.updateByPrimaryKeySelective(passenger);
                if (passengerUpdateFlag == 0) {
                    throw new GSSException("订单核价模块", "0802", "修改订单核价失败");
                }
            }
            // 根据passengerNo查询出该条记录数(修改后的)
            Passenger passengerSelect = passengerDao.selectByPrimaryKey(passengerList.getEntity().get(0).getPassengerNo());
            // 根据订单编号查询订单
            SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(passengerSelect.getSaleOrderNo());
            // 创建应收记录
            CreatePlanAmountVO CreatePlanAmountVOType = new CreatePlanAmountVO();
            // planAmountRecordType1.setRecordNo(maxNoService.generateBizNo("OS_PLANAMOUNTRECORD",
            // 80));//记录编号 自动生成
            CreatePlanAmountVOType.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
            // 记录变动类型 如 1 销售，2销售补单 3 补收，4 销售废退， 5 销售改签\n11 采购，12 采购补单 13 补付 14
            // 采购废退，15 采购改签
            CreatePlanAmountVOType.setRecordMovingType(11);
            CreatePlanAmountVOType.setPlanAmount(new BigDecimal(buytotal));// 合计
            // planAmountRecordType1.setSettlementNo();//结算周期编号
            // planAmountRecordType1.setMovingReason();//补充说明
            // planAmountRecordType1.setSettlementStatus();//结算状态
            // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
            CreatePlanAmountVOType.setGoodsType(saleOrderExt.getSaleOrder().getGoodsType());
            planAmountRecordService.create(passengerList.getAgent(), CreatePlanAmountVOType);
            
            // 创建应付记录
            // planAmountRecordType1.setRecordNo(maxNoService.generateBizNo("OS_PLANAMOUNTRECORD",
            // 80));//记录编号 自动生成
            CreatePlanAmountVOType.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
            planAmountRecordService.create(passengerList.getAgent(), CreatePlanAmountVOType);
            flag = true;
            log.info("订单核价结束");
        } catch (Exception e) {
            log.error("订单核价失败", e);
            throw new GSSException("核价修改失败", "0803", "核价修改失败");
        }
        
        return flag;
    }
    
    /**
     * 确认出单 更改os_sale_order的订单状态 填写sale_order_detail的票号
     *
     * @param requestWithActor
     * @return
     */
    @Override
    @Transactional
    public boolean issuing(RequestWithActor<PassengerListVo> requestWithActor) throws GSSException{
        
        Agent agent = requestWithActor.getAgent();
        log.info("出单开始");
        if (agent == null) {
            log.error("当前用户为空");
            throw new GSSException("当前用户为空", "0101", "拒单操作失败!");
        }
        try {
            PassengerListVo listVo = requestWithActor.getEntity();
            
            //判断资金帐号提前至此
            Account account = accountService.getAccountByAccountNo(agent, listVo.getAccountNo());
            if (account == null) {
                throw new GSSException("创建采购付款单失败", "0102", "资金帐号未能查出相应数据!account为空！accountNo=" + listVo.getAccountNo());
            }
            Long saleOrderNo = listVo.getpVoList().get(0).getSaleOrderNo();
            Supplier supplier = supplierService.getSupplierByNo(agent, listVo.getSupplierNo());
            List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);
            BuyOrder buyOrder = null;
            if (buyOrderList != null && buyOrderList.size() != 0) {
                buyOrder = buyOrderList.get(0);
                this.createBuyCertificate(agent, buyOrder.getBuyOrderNo(), buyOrder.getPayable().doubleValue(), account.getAccountNo(), supplier.getSupplierNo(), supplier.getCustomerTypeNo(), 2, account.getType(), "BUY", listVo.getDealNo());
            }
            //StringBuffer ticketNoArray = new StringBuffer();
            Date date = new Date();
            String office = listVo.getOffice();
            SaleOrderExt saleOrderExt = null;
            saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
            Long pnrNo = null;
            Pnr pnr = saleOrderExt.getImportPnr();
            if (pnr != null) {
                pnr.setPnr(listVo.getPnr());
                pnr.setBigPnr(listVo.getBigPnr());
                pnrDao.updateByPrimaryKey(pnr);
            } else {
                pnrNo = savePnr(pnrNo, listVo, saleOrderNo, agent, date);
            }
            if (pnrNo != null) {
                saleOrderExt.setPnrNo(pnrNo);
            }
            saleOrderExt.setOffice(office);
            saleOrderExt.setDrawerLoginName(agent.getAccount());
            saleOrderExt.setIssueTime(date);
            
            List<SaleOrderDetail> detailList = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);
            BigDecimal payable = new BigDecimal(0);
            for (PassengerVo pgerVo : listVo.getpVoList()) {
                payable = payable.add(pgerVo.getBuyPrice());
                String passengerNo = pgerVo.getPassengerNo().toString();
                Passenger passenger = new Passenger();
                BeanUtils.copyProperties(pgerVo, passenger);
                //出票更新乘客信息
                log.info("出票更新乘客信息" + passenger.toString());
                passengerDao.updateByPrimaryKeySelective(passenger);
                // 表单传过来的票号list下标
                int index = 0;
                // 找出同一个乘客的明细对应到的对应航程
                for (SaleOrderDetail detail : detailList) {
                    String detailPgerNo = detail.getPassengerNo().toString();
                    if (passengerNo.equals(detailPgerNo)) {
                        String detailLeg = detail.getLeg().getDepAirport() + "-" + detail.getLeg().getArrAirport();
                        // 取出航程和票号的对象
                        SaleOrderDetailVo detailVo = pgerVo.getDetailList().get(index);
                        if (detailLeg.equals(detailVo.getLegValue())) {
                            // 找到乘客的明细,写入对应航程的票号
                            detail.setTicketNo(detailVo.getTicketNo());
                            detail.setStatus("4");// 已出票
                            // 更新表
                            saleOrderDetailDao.updateByPrimaryKeySelective(detail);
                            Leg leg = detail.getLeg();
                            String legValue = leg.getDepAirport() + leg.getStopAirport() + leg.getArrAirport() + "/";
                            /*ticketNoArray.append(detailVo.getTicketNo());
                            if (index != detailList.size() - 1) {
                                ticketNoArray.append(",");
                            }*/
                            log.info("乘客[" + passengerNo + "]的票号写入成功,对应航程为:" + legValue);
                        }
                        index++;
                    }
                }
            }
            //设置采购币种
            saleOrderExt.setCurrency(listVo.getpVoList().get(0).getCurrency());
            Long preLocker = saleOrderExt.getLocker();
            saleOrderExt.setLocker(0L);
            // 修改采购单信息
            //TODO
            updateBuyOrder(agent, buyOrder, payable, listVo, supplier);
            // 更改主订单状态
            int result = 0;
            saleOrderService.updateStatus(agent, saleOrderNo, 4);// 将状态改为已出票
            result = saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
            log.info("saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt):result={}", result);
            String logstr = "用户" + agent.getAccount() + "成功出票：" + "[" + saleOrderNo + "]";
            SaleOrder saleOrder = saleOrderExt.getSaleOrder();
            Long transationOrderNo = null;
            if (null != saleOrder) {
                transationOrderNo = saleOrder.getTransationOrderNo();
                //
                ApiCallBack(saleOrder, agent, logstr, date);
            }
            // 配送管理出票队列
            sendTicketInfoByMq(agent, transationOrderNo);
            /* 创建操作日志 */
            //saveLog(agent, saleOrderNo, logstr, transationOrderNo);
            String title = "国际订单出票";
            IftLogHelper.logger(agent, transationOrderNo, saleOrderNo, title, logstr);
            log.info("出单操作成功");
            //销售员订单数量减一
            subSaleOrderNum(agent, preLocker);
        }  catch (GSSException e) {
            log.error("创建采购付款单异常！GSSException", e);
            throw new GSSException(e.getModule(), e.getCode(), e.getMessage());
        }  catch (Exception e) {
            //这里的异常一般不是业务异常
            log.error("国际机票出票异常", e);
            throw new GSSException("国际机票出票异常", "0102", "出单操作失败!" + e);
        }
        log.info("出单结束");
        return true;
    }
    
    /**
     * 创建采购付款单
     *
     * @param agent
     *         终端信息
     * @param buyOrderNo
     *         采购单ID
     * @param payAmount
     *         支付金额
     * @param payAccount
     *         支付账号
     * @param customerNo
     *         采购渠道
     * @param customerTypeNo
     *         采购渠道类型
     * @param channel
     *         渠道
     * @param payType
     *         支付方式
     * @param payWay
     *         支付类型
     * @param thirdPayNo
     *         第三方业务编号 多个以","隔开
     */
    public void createBuyCertificate(Agent agent, long buyOrderNo, double payAmount, long payAccount, long customerNo, long customerTypeNo, int payType, int payWay, String channel, String thirdPayNo) {
        
        CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
        certificateCreateVO.setAccoutNo(payAccount + ""); // 支付账号
        certificateCreateVO.setChannel(channel); // 渠道 未知
        certificateCreateVO.setCustomerNo(customerNo); // 供应商ID
        certificateCreateVO.setCustomerTypeNo(customerTypeNo); // 供应商类型
        certificateCreateVO.setIncomeExpenseType(2); // 收支类型 1 收，2 为支
        certificateCreateVO.setPayType(payType); // 支付类型（1 在线支付 2 帐期或代付 3 线下支付）
        certificateCreateVO.setPayWay(payWay); // 支付方式
        certificateCreateVO.setReason("采购单付款信息"); // 补充说明
        certificateCreateVO.setServiceLine("1"); // 业务线
        certificateCreateVO.setSubBusinessType(1); // 业务小类 1.销采 2.补收退 3.废退 4.变更
        // 5.错误修改
        //certificateCreateVO.setThirdBusNo(thirdBusNo); // 第三方业务编号 多个以","隔开
        // (销售不用传)
        certificateCreateVO.setThirdPayNo(thirdPayNo); // 第三方支付流水 多个以","隔开
        // (销售不用传)
        List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); // 支付订单
        BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
        businessOrderInfo.setActualAmount(new BigDecimal(payAmount));
        businessOrderInfo.setBusinessType(3);
        businessOrderInfo.setRecordNo(buyOrderNo);
        orderInfoList.add(businessOrderInfo);
        certificateCreateVO.setOrderInfoList(orderInfoList);
        
        try {
            this.certificateService.createBuyCertificate(agent, certificateCreateVO);
        }   catch (GSSException e) {
            throw new GSSException(e.getModule(), e.getCode(), e.getMessage());
        }  catch (Exception e) {
            throw new GSSException("创建采购付款单失败，e=" + e, "10001", "创建采购付款单失败");
        }
        
    }
    
    /**
     * 拒单.
     *
     * @param refuseRequest
     *         ，拒单请求.
     * @return
     */
    @Override
    @Transactional
    public boolean refuse(RequestWithActor<OrderRefuseRequest> refuseRequest) {
        
        log.info("拒单开始");
        if (refuseRequest.getAgent() == null) {
            log.error("当前用户为空");
            throw new GSSException("当前用户为空", "0101", "拒单操作失败!");
        }
        if (refuseRequest.getEntity() == null || refuseRequest.getEntity().getSaleOrderNo() == null) {
            log.error("必要参数为空");
            throw new GSSException("必要参数为空", "0102", "拒单操作失败!");
        }
        Long saleOrderNo = refuseRequest.getEntity().getSaleOrderNo();
        String remark = refuseRequest.getEntity().getRemark();
        //杂费单特殊处理
        SaleOrder saleOrder = null;
        if (StringUtils.isNotBlank(refuseRequest.getEntity().getChildStatus())) {
            saleOrder = saleOrderService.updateStatus(refuseRequest.getAgent(), saleOrderNo, 14);// 销售单改为已拒单
        } else {
            saleOrder = saleOrderService.updateStatus(refuseRequest.getAgent(), saleOrderNo, 5);// 销售单改为已取消
        }
        List<SaleOrderDetail> saleOrderDetailList = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);
        if (saleOrderDetailList != null && saleOrderDetailList.size() > 0) {
            for (SaleOrderDetail bean : saleOrderDetailList) {
                /**修改订单状态*/
                bean.setStatus("14");
                saleOrderDetailDao.updateByPrimaryKeySelective(bean);
            }
        }
        // 更新拒单原因
        SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo);
        saleOrderExt.setSaleOrderNo(saleOrderNo);
        saleOrderExt.setSaleRemark(remark);
        saleOrderExtDao.updateByPrimaryKeySelective(saleOrderExt);
        
        List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(refuseRequest.getAgent(), refuseRequest.getEntity().getSaleOrderNo());
        if (buyOrderList != null && buyOrderList.size() != 0) {
            for (BuyOrder buyOrder : buyOrderList) {
                if (buyOrder.getBusinessSignNo().equals(saleOrder.getBusinessSignNo())) {
                    buyOrderService.updateStatus(refuseRequest.getAgent(), buyOrder.getBuyOrderNo(), 4);// 采购单改为已拒单
                }
            }
        }
        
        /* 创建操作日志 */
        try {
            String logstr = null;
            String title = null;
            if (saleOrderDetailList != null && saleOrderDetailList.get(0).getSaleOrderExt() != null && saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder() != null) {
                if (saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 7) {
                    if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 14) {
                        title = "国际冲单拒单";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际冲单拒单：" + "[" + saleOrderNo + "]";
                        
                    } else if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 5) {
                        title = "国际冲单取消";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际冲单取消：" + "[" + saleOrderNo + "]";
                    }
                } else if (saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 8) {
                    if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 14) {
                        title = "国际补单拒单";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际补单拒单：" + "[" + saleOrderNo + "]";
                        
                    } else if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 5) {
                        title = "国际补单取消";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际补单取消：" + "[" + saleOrderNo + "]";
                    }
                } else if (saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 9 || saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 10) {
                    if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 14) {
                        title = "国际调整单拒单";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际调整单拒单：" + "[" + saleOrderNo + "]";
                        
                    } else if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 5) {
                        title = "国际调整单取消";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际调整单取消：" + "[" + saleOrderNo + "]";
                    }
                } else if (saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 11) {
                    if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 14) {
                        title = "国际ADM单拒单";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际ADM单拒单：" + "[" + saleOrderNo + "]";
                        
                    } else if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 5) {
                        title = "国际ADM单取消";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际ADM单取消：" + "[" + saleOrderNo + "]";
                    }
                    
                } else if (saleOrderDetailList.get(0).getSaleOrderExt().getCreateType() == 12) {
                    if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 14) {
                        title = "国际ACM单拒单";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际ACM单拒单：" + "[" + saleOrderNo + "]";
                        
                    } else if (saleOrderDetailList.get(0).getSaleOrderExt().getSaleOrder().getOrderChildStatus() == 5) {
                        title = "国际ACM单取消";
                        logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际ACM单取消：" + "[" + saleOrderNo + "]";
                    }
                } else {
                    logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际订单拒单：" + "[" + saleOrderNo + "]";
                }
            } else {
                title = "国际订单拒单";
                logstr = "用户" + refuseRequest.getAgent().getAccount() + "国际订单拒单：" + "[" + saleOrderNo + "]";
            }
            IftLogHelper.logger(refuseRequest.getAgent(), saleOrder.getTransationOrderNo(), saleOrderNo, title, logstr);
           /* LogRecord logRecord = new LogRecord();
            logRecord.setAppCode("UBP");
            logRecord.setCreateTime(new Date());
            logRecord.setTitle("国际订单拒单");
            logRecord.setDesc(logstr);
            logRecord.setOptLoginName(refuseRequest.getAgent().getAccount());
            logRecord.setRequestIp(refuseRequest.getAgent().getIp());
            logRecord.setBizCode("IFT");
            logRecord.setBizNo(String.valueOf(saleOrderNo));
            Map<String, Object> otherOpts = new HashMap<String, Object>();
            otherOpts.put("transationOrderNo", saleOrder.getTransationOrderNo());
            logRecord.setOtherOpts(otherOpts);
            logService.insert(logRecord);*/
        } catch (Exception e) {
            log.error("添加操作日志异常===" + e);
        }
        log.info("拒单结束");
        return true;
    }
    
    /**
     * 创建销售退款单
     *
     * @return
     */
    @Override
    public boolean saleRefund(Agent agent, Long saleOrderNo) throws GSSException {
        if (agent == null) {
            log.error("agent 为空");
            throw new GSSException("agent 为空", "1001", "创建销售退款单失败");
        }
        if (saleOrderNo == null) {
            log.error("saleOrderNo 为空");
            throw new GSSException("saleOrderNo 为空", "1001", "创建销售退款单失败");
        }
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
        // 当支付状态为已支付（1）时创建销售退款单
        if (saleOrder != null) {
            if (saleOrder.getPayStatus() == 1) {
                try {
                    CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                    certificateCreateVO.setIncomeExpenseType(2); // 收支类型 1 收，2
                    // 为支
                    // certificateCreateVO.setPayType(payType); //支付类型（1 在线支付 2
                    // 帐期或代付 3 线下支付）
                    certificateCreateVO.setReason("销售退款单信息"); // 补充说明
                    certificateCreateVO.setSubBusinessType(1); // 业务小类 1.销采
                    // 2.补收退 3.废退
                    // 4.变更 5.错误修改
                    List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); // 支付订单
                    BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
                    certificateCreateVO.setServiceLine("1"); // 业务线
                    businessOrderInfo.setActualAmount(saleOrder.getReceived());
                    certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
                    certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
                    businessOrderInfo.setBusinessType(2);// 1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                    businessOrderInfo.setRecordNo(saleOrderNo);
                    orderInfoList.add(businessOrderInfo);
                    certificateCreateVO.setOrderInfoList(orderInfoList);
                    certificateService.saleRefundCert(agent, certificateCreateVO);
                } catch (Exception e) {
                    log.error("创建销售退款单失败," + e);
                    throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
                }
            }
        }
        return true;
    }
    
    /**
     * 拒单重提.
     *
     * @param saleOrderNo
     * @return
     */
    // @Override
    public boolean resubmit(RequestWithActor<Long> saleOrderNo) {
        
        log.info("拒单重提开始");
        log.info("拒单重提结束");
        return false;
    }
    
    /**
     * 回贴票号.
     *
     * @param ticketRequest
     * @return
     */
    // @Override
    @Transactional
    public boolean writeTicketNo(RequestWithActor<TicketRequest> ticketRequest) {
        
        log.info("回贴票号开始");
        try {
            if (ticketRequest.getAgent() == null) {
                throw new GSSException("回贴票号模块", "1101", "agent为空");
            }
            // 根据传入参数changePnr判断是否为换编出票
            if (ticketRequest.getEntity().isChangePnr()) {
                // 新增新的pnr
                Pnr pnr = new Pnr();
                // pnr.setPnrNo(maxNoService.generateBizNo("IFT_PNR", 32));
                pnr.setOwner(ticketRequest.getAgent().getOwner());
                pnr.setPnr(ticketRequest.getEntity().getNewPnr());// 航司PNR编号
                pnr.setBigPnr(ticketRequest.getEntity().getBigPnr());
                // setSourceNo/setPnrSource/setPnrContent等参数从buyOrderDetailList中第一条记录获取
                if (ticketRequest.getEntity().getBuyOrderDetailList() != null && ticketRequest.getEntity().getBuyOrderDetailList().size() > 0) {
                    for (int i = 1; i < ticketRequest.getEntity().getBuyOrderDetailList().size(); i++) {
                        pnr.setSourceNo(ticketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getSourceNo());// 订单来源编号
                        // 可能为采购单编号，也可能为销售单编号，具体根据pnrSource确定
                        pnr.setPnrSource(ticketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getPnrSource());// PNR来源
                        // 1：导入，2：采购，3：改签
                        pnr.setPnrContent(ticketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getPnrContent());// PNR内容
                    }
                }
                pnr.setValid((byte) 1);
                pnr.setCreator(ticketRequest.getAgent().getAccount());
                pnr.setCreateTime(new Date());
                pnrDao.insert(pnr);
                
                for (BuyOrderDetail buyOrderDetail : ticketRequest.getEntity().getBuyOrderDetailList()) {
                    // 在buyOrderDetail中修改出票时间、票号等属性 并执行update语句进行修改
                    buyOrderDetail.setTicketTime(new Date());// 出票时间
                    // buyOrderDetail.getTicketNo();//票号
                    buyOrderDetail.setModifier(ticketRequest.getAgent().getAccount());// 修改人
                    buyOrderDetail.setModifyTime(new Date());// 修改时间
                    buyOrderDetail.setStatus("1");// 状态设置为已出票
                    int updateTickerFlag = buyOrderDetailDao.updateByPrimaryKeySelective(buyOrderDetail);
                    if (updateTickerFlag == 0) {
                        throw new GSSException("回贴票号模块", "1102", "更新BuyOrderDetail表中票号失败");
                    }
                }
                // 根据采采购明细中的采购编号修改采购中的出票类型等信息、只获取list中的第一条数据进行修改
                List<BuyOrderDetail> buyOrderDetail = ticketRequest.getEntity().getBuyOrderDetailList();
                BuyOrderExt buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrderDetail.get(0).getBuyOrderNo());
                if (ticketRequest.getEntity().getTicketType() != null) {
                    buyOrderExt.setTicketType(ticketRequest.getEntity().getTicketType());// 出票类型
                }
                buyOrderExt.setModifier(ticketRequest.getAgent().getAccount());// 修改人
                buyOrderExt.setModifyTime(new Date());// 修改时间
                int updateTicketTypeFlag = buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
                if (updateTicketTypeFlag == 0) {
                    throw new GSSException("回贴票号模块", "1103", "更新BuyOrderExt表中出票类型失败失败");
                }
            }
            log.info("回贴票号结束");
        } catch (Exception e) {
            log.error("回贴票号异常", e);
            throw new GSSException("回贴票号模块", "1104", "回贴票号异常");
        }
        
        return true;
    }
    
    /**
     * 锁单、解锁.
     *
     * @param saleOrderNo
     * @return
     */
    @Override
    @Transactional
    public boolean lock(RequestWithActor<Long> saleOrderNo) {
        
        log.info("锁单、解锁开始");
        boolean flag = false;
        try {
            SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(saleOrderNo.getEntity().longValue());
            if (saleOrderExt != null) {
                // locker 为0表示解锁 大于0表示锁定
                if (saleOrderExt.getLocker() == null || saleOrderExt.getLocker() == 0) {
                    saleOrderExt.setLocker(saleOrderNo.getAgent().getId() == null ? 1 : saleOrderNo.getAgent().getId());
                    saleOrderExt.setLockTime(new Date());
                } else {
                    saleOrderExt.setLocker(0L);
                    saleOrderExt.setLockTime(new Date());
                }
                int updateLocker = saleOrderExtDao.updateLocker(saleOrderExt);
                if (updateLocker == 1) {
                    flag = true;
                }
            }
            log.info("锁单、解锁结束");
        } catch (Exception e) {
            log.error("锁单、解锁失败", e);
            throw new GSSException("锁单、解锁模块", "1201", "锁单、解锁失败");
        }
        return flag;
    }
    
    @Override
    public SaleOrderExt getDemandNo(RequestWithActor<Long> demandNo) {
        
        log.info("查询开始");
        SaleOrderExt saleOrderExt = null;
        try {
            saleOrderExt = saleOrderExtDao.selectDemandOrder(demandNo.getEntity());
        } catch (Exception e) {
            log.error("查询订单失败", e);
            throw new GSSException("查询订单模块", "1301", "获取订单信息失败");
        }
        if (saleOrderExt != null && saleOrderExt.getLegList() != null && saleOrderExt.getLegList().size() > 0) {
            for (Leg leg : saleOrderExt.getLegList()) {
                String arrCityName = getByCityCode(leg.getArrAirport());
                String depCityName = getByCityCode(leg.getDepAirport());
                leg.setArrCityName(arrCityName == null ? "" : arrCityName);
                leg.setDepCityName(depCityName == null ? "" : depCityName);
            }
        }
        return saleOrderExt;
    }
    
    /**
     * 根据机场三字码获取城市名称
     * @param cityCode
     * @return
     */
    /**
     * 根据机场三字码获取城市名称
     *
     * @param cityCode
     * @return
     */
    private String getByCityCode(String cityCode) {
        String arrAirport = "";
        if (cityCode != null) {
            Airport airport = airportService.queryAirportByCode(cityCode, "D");
            if (airport == null) {
                airport = airportService.queryAirportByCode(cityCode, "i");
            }
            if (airport != null) {
                arrAirport = airport.getCityCName();
            }
        }
        
        return arrAirport;
    }
    
    @Override
    @Transactional
    public boolean verify(RequestWithActor<List<Passenger>> passengerList) {
        log.info("拒单并退款创建应收应付开始");
        boolean flag = false;
        try {
            if (passengerList == null) {
                throw new GSSException("订单核价模块", "0601", "传入参数为空");
            }
            BigDecimal saleTotal = new BigDecimal(0);// 销售单合计
            BigDecimal buyTotal = new BigDecimal(0);// 采购单合计
            for (Passenger passenger : passengerList.getEntity()) {
                if (passenger.getBuyPrice() != null) {
                    buyTotal = buyTotal.add(passenger.getBuyPrice());
                }
                if (passenger.getSalePrice() != null) {
                    // 合计
                    saleTotal = saleTotal.add(passenger.getSalePrice());
                }
            }
            // 根据订单编号查询订单
            SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(passengerList.getEntity().get(0).getSaleOrderNo());
            List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(passengerList.getEntity().get(0).getSaleOrderNo());
            
            if (saleOrderExt != null) {
                // 销售单应付
                CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                createPlanAmountVOType.setRecordNo(saleOrderExt.getSaleOrderNo());// 记录编号
                createPlanAmountVOType.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
                createPlanAmountVOType.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                createPlanAmountVOType.setPlanAmount(saleTotal);// 合计
                createPlanAmountVOType.setBusinessType(BusinessType.SALEORDER.getKey());
                // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                createPlanAmountVOType.setGoodsType(2);
                planAmountRecordService.create(passengerList.getAgent(), createPlanAmountVOType);
            } else {
                log.error("销售单为空");
                throw new GSSException("销售单为空", "0601", "核价修改销售、采购失败");
            }
            if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                
                // 采购单应收
                CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                createPlanAmountVOType.setRecordNo(buyOrderExtList.get(0).getBuyOrderNo());// 记录编号
                createPlanAmountVOType.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                createPlanAmountVOType.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                createPlanAmountVOType.setPlanAmount(buyTotal);// 合计
                createPlanAmountVOType.setBusinessType(BusinessType.BUYORDER.getKey());
                // 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                createPlanAmountVOType.setGoodsType(2);
                planAmountRecordService.create(passengerList.getAgent(), createPlanAmountVOType);
            } else {
                log.error("采购单为空");
                throw new GSSException("采购单为空", "0601", "核价修改销售、采购失败");
            }
            log.info("订单核价结束");
            flag = true;
        } catch (Exception e) {
            log.error("订单核价失败", e);
            throw new GSSException("核价修改失败", "0603", "核价修改失败");
        }
        return flag;
    }
    
    /**
     * 根据票号查询
     */
    @Override
    public List<ReportVo> getReportByTicketNo(Agent agent, String ticketNo) {
        log.info("根据票号获取报表开始==================");
        log.info("==================ticketNo=" + ticketNo);
        if (ticketNo == null || "".equals(ticketNo)) {
            throw new GSSException("票号为空", "0603", "根据票号查询失败");
        }
        if (agent == null) {
            throw new GSSException("agent为空", "0603", "根据票号查询失败");
        }
        List<ReportVo> report = saleOrderExtDao.selectByTicketNo(ticketNo);
        log.info("查询结果为：" + report.toString());
        return report;
    }
    
    @Override
    public List<QueryByPnr> queryByPnr(Agent agent, String pnr) {
        if (agent == null) {
            throw new GSSException("根据PNR查询失败", "0603", "agent为空");
        }
        if (pnr == null) {
            throw new GSSException("根据PNR查询失败", "0603", "pnr为空");
        }
        // 根据PNR获取pnrNo
        List<QueryByPnr> queryByPnr = new ArrayList<QueryByPnr>();
        List<Pnr> pnrList = pnrDao.queryByPnr(pnr);
        for (Pnr pnrS : pnrList) {
            QueryByPnr query = new QueryByPnr();
            SaleOrderExt ext = saleOrderExtDao.selectByPrimaryKey(pnrS.getSourceNo());
            if (ext == null) { ext = saleOrderExtDao.queryByPnrNo(pnrS.getPnrNo()); }
            if (ext != null) {
                SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, ext.getSaleOrderNo());
                ext.setSaleOrder(saleOrder);
                List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, ext.getSaleOrderNo());
                if (buyOrderList != null) {
                    for (BuyOrder buyOrder : buyOrderList) {
                        if (buyOrder.getBusinessSignNo().equals(saleOrder.getBusinessSignNo())) {
                            BuyOrderExt buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrder.getBuyOrderNo());
                            query.setBuyOrderExt(buyOrderExt);
                            query.setBuyOrder(buyOrder);
                        }
                    }
                }
                query.setSaleOrderExt(ext);
                queryByPnr.add(query);
            }
        }
        return queryByPnr;
    }
    
    @Override
    public boolean queryByPnr(Agent agent, String pnr, int day) {
        if (agent == null) {
            throw new GSSException("根据PNR查询失败", "0603", "agent为空");
        }
        if (pnr == null) {
            throw new GSSException("根据PNR查询失败", "0603", "pnr为空");
        }
        String regex = "[A-Za-z0-9]{6}";
        if (!pnr.matches(regex)) {
            throw new GSSException("根据PNR查询失败", "0603", "请传入正确的6位PNR号！不能包含中文及特殊字符");
        }
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        List<Pnr> pneList = pnrDao.selectByPnr(pnr);
        QueryPnrAndTimeVo vo = new QueryPnrAndTimeVo();
        
        for (Pnr pnrS : pneList) {
            vo.setPnrNo(pnrS.getPnrNo());
            vo.setStart(now.getTime());
            vo.setEnd(new Date());
            SaleOrderExt ext = saleOrderExtDao.queryByPnrNoAndTime(vo);
            if (ext != null) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 黑屏预定
     */
    @Override
    public Long blankBookTicketing(RequestWithActor<OrderCreateVo> requestWithActor, BlackOrderExtVo blackOrderExtVo) {
        Long saleOrderNo = 0l;
        try {
            log.info("创建订单开始========");
//			List<Passenger>  psglist = requestWithActor.getEntity().getSaleOrderExt().getPassengerList();
//			List<Leg>  leglist = requestWithActor.getEntity().getSaleOrderExt().getLegList();
//			SaleOrderExt  saleOrderExtn  = requestWithActor.getEntity().getSaleOrderExt();
//			for(Legpassenger  legpsg : saleOrderExtn.getLegpassengerList()){
//				psglist.add(legpsg.getPassenger());
//				leglist.add(legpsg.getLeg());
//			}
//			requestWithActor.getEntity().getSaleOrderExt().setPassengerList(psglist);
//			requestWithActor.getEntity().getSaleOrderExt().setLegList(leglist);
//
            SaleOrderExt saleOrderExt = createOrder(requestWithActor);
            log.info("创建订单结束========");
            
            log.info("核价开始========");
//			Long saleOrderNo = 371705120453282405l;
//			Agent agent = new Agent(8023, 1111L, 1111L, 1111L, "测试账号", "测试认证令牌token", "192.168.0.1", "OAPI");
//			String json = "{    \"saleOrderNo\": 371705120453282405,    \"supplierNo\": 411704180244370097,    \"received\": 5689.28,    \"buyorderPrice\": 5689.28,    \"saleRemark\": \"TEST\",    \"orderPriceVoList\": [        {            \"passengerType\": \"ADT\",            \"orderType\": \"1\",            \"saleFare\": 4171,            \"saleTax\": 1559,            \"saleBrokerage\": 1,            \"saleAgencyFee\": 1,            \"saleRebate\": 1,            \"saleAwardPrice\": 1,            \"salePrice\": 5689.28,            \"serviceCharge\": 1        },        {},        {},        {            \"passengerType\": \"ADT\",            \"orderType\": \"2\",            \"buyFare\": 4171,            \"buyTax\": 1559,            \"buyBrokerage\": 1,            \"buyAgencyFee\": 1,            \"buyRebate\": 1,            \"buyAwardPrice\": 1,            \"buyPrice\": 5689.28        }    ]}";
//			blackOrderExtVo.setSaleOrderExtVo(new Gson().fromJson(json, SaleOrderExtVo.class));
            RequestWithActor<Long> requestWithActor1 = new RequestWithActor<>();
            requestWithActor1.setAgent(AgentUtil.getAgent());
            requestWithActor1.setEntity(saleOrderExt.getSaleOrderNo());
            SaleOrderExt saleOrderExt1 = orderService.getOrder(requestWithActor1);
            if (blackOrderExtVo.getSaleOrderExtVo().orderPriceVoList != null && blackOrderExtVo.getSaleOrderExtVo().orderPriceVoList.size() > 0) {
                for (OrderPriceVo orderPriceVo : blackOrderExtVo.getSaleOrderExtVo().orderPriceVoList) {
                    for (Passenger passenger : saleOrderExt1.getPassengerList()) {
                        // 销售单
                        if (orderPriceVo.getPassengerType() != null && orderPriceVo.getOrderType() != null) {
                            if (orderPriceVo.getPassengerType().equals(passenger.getPassengerType()) && ("1").equals(orderPriceVo.getOrderType())) {
                                passenger.setSaleFare(orderPriceVo.getSaleFare());
                                passenger.setSaleTax(orderPriceVo.getSaleTax());
                                passenger.setSaleAgencyFee(orderPriceVo.getSaleAgencyFee());
                                passenger.setSaleAwardPrice(orderPriceVo.getSaleAwardPrice());
                                passenger.setSaleBrokerage(orderPriceVo.getSaleBrokerage());
                                passenger.setSaleRebate(orderPriceVo.getSaleRebate());
                                passenger.setSalePrice(orderPriceVo.getSalePrice());
                                passenger.setServiceCharge(orderPriceVo.getServiceCharge());
                                // 修改价格信息
                                RequestWithActor<Passenger> updatePassenger = new RequestWithActor<>();
                                updatePassenger.setEntity(passenger);
                                updatePassenger.setAgent(AgentUtil.getAgent());
                                passengerService.updatePassenger(updatePassenger);
                            }
                            if (orderPriceVo.getPassengerType().equals(passenger.getPassengerType()) && ("2").equals(orderPriceVo.getOrderType())) {
                                passenger.setBuyFare(orderPriceVo.getBuyFare());
                                passenger.setBuyTax(orderPriceVo.getBuyTax());
                                passenger.setBuyAgencyFee(orderPriceVo.getBuyAgencyFee());
                                passenger.setBuyAwardPrice(orderPriceVo.getBuyAwardPrice());
                                passenger.setBuyBrokerage(orderPriceVo.getBuyBrokerage());
                                passenger.setBuyRebate(orderPriceVo.getBuyRebate());
                                passenger.setBuyPrice(orderPriceVo.getBuyPrice());
                                // 修改价格信息
                                RequestWithActor<Passenger> updatePassenger = new RequestWithActor<>();
                                updatePassenger.setEntity(passenger);
                                updatePassenger.setAgent(AgentUtil.getAgent());
                                passengerService.updatePassenger(updatePassenger);
                            }
                        }
                    }
                }
                
                // 创建应收应付
                RequestWithActor<List<Passenger>> listRequestWithActor = new RequestWithActor<>();
                listRequestWithActor.setAgent(AgentUtil.getAgent());
                listRequestWithActor.setEntity(saleOrderExt1.getPassengerList());
                orderService.confirmPrice(listRequestWithActor);
                // 修改SaleOrder状态为已核价
                saleOrderService.updateStatus(AgentUtil.getAgent(), saleOrderExt1.getSaleOrder().getSaleOrderNo(), 2);
                // 修改saleOrderExt
                saleOrderExt1.setModifyTime(new Date());
                saleOrderExt1.setSaleRemark(blackOrderExtVo.getSaleOrderExtVo().getSaleRemark());
                saleOrderExt1.setLocker(0L);
                //saleOrderExt1.setModifier(AgentUtil.getAgent().getAccount());
                orderService.auditOrder(new RequestWithActor<>(AgentUtil.getAgent(), saleOrderExt1), blackOrderExtVo.getSupplierNo(), blackOrderExtVo.getAirLine(), blackOrderExtVo.getSaleOrderExtVo().getTicketType());
                log.info("核价结束========");
            }
//			PassengerListVo passengerListVo = blackOrderExtVo.getPgerVoList();
//			for(PassengerVo passengerVo : passengerListVo.getpVoList()){
//				for(Legpassenger  legpsg : requestWithActor.getEntity().getSaleOrderExt().getLegpassengerList()){
//					passengerVo.setPassengerNo(legpsg.getPassenger().getPassengerNo());
//					passengerVo.setSaleOrderNo(legpsg.getLeg().getSaleOrderNo());
//					for(SaleOrderDetailVo saleOrderDetailVo : passengerVo.getDetailList()){
//						saleOrderDetailVo.setTicketNo(legpsg.getTicketNo());
//						saleOrderDetailVo.setLegValue(legpsg.getLeg().getDepAirport()+"-"+legpsg.getLeg().getArrAirport());
//					}
//				}
//			}
//			System.out.println(new Gson().toJson(passengerListVo));
//			try {
//				RequestWithActor<PassengerListVo> requestWithActor2 = new RequestWithActor<PassengerListVo>();
//				requestWithActor2.setAgent(AgentUtil.getAgent());
//				requestWithActor2.setEntity(passengerListVo);
//				orderService.issuing(requestWithActor2);
//			} catch (Exception e) {
//				log.info("黑屏出票失败，e=" + e);
//			}
            saleOrderNo = saleOrderExt.getSaleOrderNo();
        } catch (Exception e1) {
            log.info("黑屏出票失败，e=", e1);
        }
        return saleOrderNo;
    }
    
    @Override
    public boolean createPnr(Agent agent, Pnr insetPnr, SaleOrderExt saleOrderExt) {
        Long pnrNo = null;
        try {
            pnrNo = maxNoService.generateBizNo("IFT_PNR_NO", 32);
            insetPnr.setPnr(insetPnr.getPnr());
            insetPnr.setSourceNo(saleOrderExt.getSaleOrderNo());
            insetPnr.setBigPnr("");
            insetPnr.setPnrNo(pnrNo);
            insetPnr.setCreator(agent.getAccount());
            insetPnr.setCreateTime(new Date());
            insetPnr.setOwner(agent.getOwner());
            insetPnr.setValid((byte) 1);
            insetPnr.setStatus("1");
            pnrDao.insertSelective(insetPnr);
        } catch (Exception e) {
            log.info("保存PNR，e=", e);
        }
        return false;
    }
    
    @Override
    @Transactional
    public SaleOrderExt getTeamDemandNo(RequestWithActor<DemandTeamVo> demandTeamVo) {
        log.info("查询开始");
        SaleOrderExt saleOrderExt = null;
        try {
            saleOrderExt = saleOrderExtDao.selectDemandTeamOrder(demandTeamVo.getEntity());
        } catch (Exception e) {
            log.error("查询订单失败", e);
            throw new GSSException("查询订单模块", "1301", "获取订单信息失败");
        }
        return saleOrderExt;
    }
    
    /****
     * 订单分配
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public void assign(Long saleOrderNo) {
        log.info("第一步：查询符合条件的出票订单...");
        Integer[] createTypeStatusArray = {1, 2, 3, 4, 6};
        List<SaleOrderExt> saleOrderExtList = getNoHandleOrders(createTypeStatusArray);
        if (saleOrderNo != null) {
            //如果有销售单号，把其他的订单都移出只分改销售单号对应的单
            Iterator<SaleOrderExt> iterator = saleOrderExtList.iterator();
            while (iterator.hasNext()) {
                SaleOrderExt orderExt = iterator.next();
                if (!orderExt.getSaleOrderNo().equals(saleOrderNo)) {
                    iterator.remove();
                }
            }
        }
        if (saleOrderExtList != null && saleOrderExtList.size() > 0) {
            log.info("查询到" + saleOrderExtList.size() + "条可分配订单...");
        } else {
            log.info("未查询到可以分配的出票订单,结束此次任务...");
            return;
        }
        log.info("第二步：查询在线采购出票员...");
        List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-ticketSender"); //采购出票员
        log.info("是否有在线出票员:" + (senders != null));
        if (senders != null && senders.size() > 0) {
            Agent agent = new Agent(Integer.valueOf(owner));
            IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
            Map config = configs.getConfig();
            String str_maxOrderNum = (String) config.get("maxOrderNum");
            log.info("有在线出票员人数:{},获得配置最大分单数：{}", senders.size(), str_maxOrderNum);
            Long maxOrderNum = Long.valueOf(str_maxOrderNum);
            Date updateTime = new Date();
            log.info("第三步：判断出票员手头出票订单数量...");
            for (SaleOrderExt order : saleOrderExtList) {
                for (TicketSender peopleInfo : senders) {
                    log.info(peopleInfo.getName() + "订单数量：" + peopleInfo.getOrdercount());
                    if (peopleInfo.getOrdercount() >= maxOrderNum) {
                        continue;
                    } else {
                        log.info("第四步:满足条件的分配详细明细...1.将设置为出票中");
                        /***修改订单明细表*/
                        updateSaleOrderDetail(order, peopleInfo, updateTime);
                        /**锁单*/
                        log.info("2.锁单,锁单人是被分配人...");
                        assingLockOrder(order, peopleInfo, updateTime, agent);
                        /***增加出票人订单数*/
                        log.info("3.增加出票人的未出票订单数量...");
                        increaseOrderCount(peopleInfo);
                        /***发送消息至消息队列 通知出票员*/
                        sendInfo(peopleInfo.getUserid(), order.getSaleOrderNo(), String.valueOf(order.getSaleOrder().getOrderStatus()));
                        log.info("4.发信息通知出票员出票,订单" + order.getSaleOrderNo() + "将分给出票员结束");
                        break;
                    }
                }
            }
            log.info("此次分单结束...");
        } else {
            log.info("未查询在线出票员...");
        }
    }
    
    @Override
    @Transactional
    public long addInfPassengers(Long saleOrderNo, RequestWithActor<OrderCreateVo> requestWithActor, String remark) {
        // 校验数据
        if (null == saleOrderNo) {
            throw new GSSException("原销售单号不能为空", "0101", "补婴儿订单创建失败");
        }
        /* 校验销售单条件 */
        SaleOrderExt saleOrderExt = requestWithActor.getEntity().getSaleOrderExt();
        if (saleOrderExt.getPassengerList() == null) {
            throw new GSSException("乘客为空", "0102", "补婴儿订单创建失败");
        }
        // 判断原单是否已经支付
        Agent agent = requestWithActor.getAgent();
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
        //未支付
        if (saleOrder != null && saleOrder.getPayStatus() != 4) {
            Date today = new Date();
            String creator = String.valueOf(agent.getAccount());
            RequestWithActor<Long> requestWithActor1 = new RequestWithActor<>();
            requestWithActor1.setAgent(agent);
            requestWithActor1.setEntity(Long.valueOf(saleOrderNo));
            SaleOrderExt oldSaleOrderExt = orderService.getOrder(requestWithActor1);
            List<Passenger> oldList = oldSaleOrderExt.getPassengerList();
            List<Passenger> infList = requestWithActor.getEntity().getSaleOrderExt().getPassengerList();
            oldList.addAll(infList);
            /* 增加婴儿 */
            for (Passenger passenger : infList) {
                if ("1".equals(passenger.getPassengerType())) {
                    passenger.setPassengerType("ADT");
                }
                if ("2".equals(passenger.getPassengerType())) {
                    passenger.setPassengerType("CHD");
                }
                if ("3".equals(passenger.getPassengerType())) {
                    passenger.setPassengerType("INF");
                }
                if (!"ADT".equals(passenger.getPassengerType()) && !"CHD".equals(passenger.getPassengerType()) && !"INF".equals(passenger.getPassengerType())) {
                    throw new GSSException("乘客类型错误,乘客类型只能是INF(婴儿)", "0101", "补婴儿订单创建失败");
                }
                if (!"1".equals(passenger.getGender()) && !"0".equals(passenger.getGender())) {
                    throw new GSSException("乘客性别错误,0:女 1：男", "0101", "补婴儿订单创建失败");
                }
                passenger.setSaleOrderNo(saleOrderNo);
                passenger.setPassengerNo(maxNoService.generateBizNo("IFT_PASSENGER_NO", 29));
                passenger.setOwner(agent.getOwner());
                passenger.setStatus(String.valueOf(1));
                passenger.setCreator(creator);
                passenger.setCreateTime(today);
                passenger.setValid((byte) 1);
                passengerDao.insertSelective(passenger);
            }
            //原采购单号
            Long buyOrderNo = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo).get(0).getBuyOrderNo();
            /* 封装增加婴儿+航段对象 */
            List<SaleOrderDetail> saleOrderDetailList = new ArrayList<>();
            for (Leg leg : oldSaleOrderExt.getLegList()) {
                for (Passenger passenger : infList) {
                    SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
                    saleOrderDetail.setSaleOrderDetailNo(maxNoService.generateBizNo("IFT_SALE_ORDER_DETAIL_NO", 36));
                    /* 设置航程 */
                    saleOrderDetail.setLegNo(leg.getLegNo());
                    /* 设置乘客 */
                    saleOrderDetail.setPassengerNo(passenger.getPassengerNo());
                    saleOrderDetail.setSaleOrderNo(saleOrderNo);
                    saleOrderDetail.setOwner(agent.getOwner());
                    saleOrderDetail.setValid((byte) 1);
                    saleOrderDetail.setCreator(creator);
                    saleOrderDetail.setCreateTime(today);
                    saleOrderDetail.setIsChange(false);
                    saleOrderDetail.setStatus("1");
                    saleOrderDetail.setCabin(leg.getCabin());
                    /* 创建销售订单明细 */
                    saleOrderDetailDao.insertSelective(saleOrderDetail);
                    saleOrderDetailList.add(saleOrderDetail);
                }
            }
            /* 采购单明细 */
            for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
                BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
                buyOrderDetail.setBuyOrderDetailNo(maxNoService.generateBizNo("IFT_BUY_ORDER_DETAIL_NO", 23));
                buyOrderDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
                buyOrderDetail.setBuyOrderNo(buyOrderNo);
                buyOrderDetail.setOwner(agent.getOwner());
                buyOrderDetail.setCreateTime(today);
                buyOrderDetail.setCreator(creator);
                buyOrderDetail.setValid((byte) 1);
                buyOrderDetailDao.insertSelective(buyOrderDetail);
            }
            //修改SaleOrder状态为待核价
            saleOrderService.updateStatus(agent, saleOrderNo, 1);
            RequestWithActor<SaleOrderExt> returnOrderRequest = new RequestWithActor<>();
            saleOrderExt.setLinkType(3);
            returnOrderRequest.setEntity(saleOrderExt);
            returnOrderRequest.setAgent(agent);
            orderService.updateSaleOrderExt(returnOrderRequest);
            log.info("原订单号[{}]后补婴儿成功,", saleOrderNo);
        } else {//已支付
            RequestWithActor<Long> requestWithActor1 = new RequestWithActor<>();
            requestWithActor1.setAgent(agent);
            requestWithActor1.setEntity(Long.valueOf(saleOrderNo));
            SaleOrderExt oldSaleOrderExt = orderService.getOrder(requestWithActor1);
            
            List<Passenger> infList = requestWithActor.getEntity().getSaleOrderExt().getPassengerList();
            
            //获取交易单
            Long transactionId = IdWorker.getId();
            SaleOrder saleOrder1 = new SaleOrder();
            saleOrder1.setTransationOrderNo(transactionId);
            saleOrder1.setSourceChannelNo(agent.getDevice());
            saleOrder1.setCustomerNo(agent.getNum());
            saleOrder1.setCustomerTypeNo(agent.getType());
            saleOrder1.setOrderingLoginName(agent.getAccount());
            saleOrder1.setOrderType(1);
//			requestWithActor.getEntity().getSaleOrderExt().setSaleOrder(saleOrder1);
            TransationOrder tr = setTransationOrderValue(agent, requestWithActor.getEntity().getSaleOrderExt().getContactName(), requestWithActor.getEntity().getSaleOrderExt().getContactMobile(), transactionId);
            //生成交易单
            transationOrderService.create(agent, tr);
            
            Long saleOrderNo1 = maxNoService.generateBizNo("IFT_SALE_ORDER_NO", 37);
            Long businessSignNo = IdWorker.getId();
            try {
                Agent agent1 = requestWithActor.getAgent();
                Date today = new Date();
                String creator = String.valueOf(agent1.getAccount());
                /* 新增航程航段 */
                StringBuffer goodsName = new StringBuffer();
                for (int i = 0; i < oldSaleOrderExt.getLegList().size(); i++) {
                    Leg leg = oldSaleOrderExt.getLegList().get(i);
                    leg.setSaleOrderNo(saleOrderNo1);
                    leg.setLegNo(maxNoService.generateBizNo("IFT_LEG_NO", 27));
                    leg.setParentSection(i);
                    leg.setStatus(String.valueOf(1));// 启用状态
                    leg.setValid((byte) 1);
                    leg.setCreator(creator);
                    leg.setCreateTime(today);
                    leg.setOwner(agent1.getOwner());
                    leg.setChildSection(0);// 原始航段
                    legdao.insertSelective(leg);
                    goodsName.append(leg.getDepAirport());
                    goodsName.append("-");
                    if (i == oldSaleOrderExt.getLegList().size() - 1) {
                        goodsName.append(leg.getArrAirport());
                    }
                    
                }
                /* 增加旅客信息 */
                CollectionUtils.filter(infList, o -> {
                    return StringUtils.isNotBlank(o.getPassengerType());
                });
                for (Passenger passenger : infList) {
                    if ("3".equals(passenger.getPassengerType())) {
                        passenger.setPassengerType("INF");
                    }
                    if (!"ADT".equals(passenger.getPassengerType()) && !"CHD".equals(passenger.getPassengerType()) && !"INF".equals(passenger.getPassengerType())) {
                        throw new GSSException("乘客类型错误,乘客类型只能是INF(婴儿)", "0101", "创建订单失败");
                    }
                    if (!"1".equals(passenger.getGender()) && !"0".equals(passenger.getGender())) {
                        throw new GSSException("乘客性别错误,0:女 1：男", "0101", "创建订单失败");
                    }
                    passenger.setSaleOrderNo(saleOrderNo1);
                    passenger.setPassengerNo(maxNoService.generateBizNo("IFT_PASSENGER_NO", 29));
                    passenger.setOwner(agent1.getOwner());
                    passenger.setStatus(String.valueOf(1));
                    passenger.setCreator(creator);
                    passenger.setCreateTime(today);
                    passenger.setValid((byte) 1);
                    passengerDao.insertSelective(passenger);
                }
                
                /* 创建采购单 */
                Long buyOrderNo = maxNoService.generateBizNo("IFT_BUY_ORDER_NO", 24);
                BuyOrderExt buyOrderExt = requestWithActor.getEntity().getBuyOrderExt();
                if (buyOrderExt == null) {
                    buyOrderExt = new BuyOrderExt();
                }
                if (buyOrderExt.getBuyOrder() == null) {
                    buyOrderExt.setBuyOrder(new BuyOrder());
                }
                buyOrderExt.getBuyOrder().setOwner(agent1.getOwner());
                buyOrderExt.getBuyOrder().setBuyOrderNo(buyOrderNo);
                buyOrderExt.getBuyOrder().setSaleOrderNo(saleOrderNo1);
                buyOrderExt.getBuyOrder().setGoodsType(2);// 商品大类 2=国际机票
                buyOrderExt.getBuyOrder().setGoodsSubType(2);// 采购单
                buyOrderExt.getBuyOrder().setGoodsName(goodsName.toString());
                
                if (buyOrderExt.getBuyOrder().getSupplierNo() == null || buyOrderExt.getBuyOrder().getSupplierNo() == 0 || buyOrderExt.getBuyOrder().getSupplierTypeNo() == null || buyOrderExt.getBuyOrder().getSupplierTypeNo() == 0) {
                    /* 查询客商编号，默认第一个数据 */
                    Supplier supplier = new Supplier();
                    supplier.setProductType("2");
                    List<Supplier> supplierList = supplierService.getSupplierList(agent1, supplier);
                    if (supplierList == null || supplierList.size() == 0) {
                        log.error("获取客商编号为空");
                        throw new GSSException("获取客商编号为空", "0101", "创建订单失败");
                    }
                    buyOrderExt.getBuyOrder().setSupplierNo(supplierList.get(0).getSupplierNo());
                    buyOrderExt.getBuyOrder().setSupplierTypeNo(supplierList.get(0).getCustomerTypeNo());
                }
                
                buyOrderExt.getBuyOrder().setBuyChannelNo(agent1.getDevice());
                buyOrderExt.getBuyOrder().setBusinessSignNo(businessSignNo);// 业务批次号
                buyOrderExt.getBuyOrder().setBsignType(1);// 1销采 2换票 3废和退 4改签
                buyOrderExt.getBuyOrder().setBuyChildStatus(1);// 未审核
                
                /* 创建采购单的pnr信息 */
                Pnr pnr = saleOrderExt.getImportPnr();
                if (pnr != null) {
                    pnr.setPnrNo(maxNoService.generateBizNo("IFT_PNR_NO", 32));
                    /* 设置采购单编号 */
                    try {
						/*PnrOutPut pnrOutPut = getPnrService.getPnr(StringUtils.defaultString(buyOrderExt.getAirline()),
								pnr.getPnr());
						pnr.setPnrContent(pnrOutPut.getPnrInfo());*/
                    } catch (Exception e) {
                        log.info("获取pnr信息失败");
                    }
                    pnr.setCreateTime(new Date());
                    pnr.setCreator(String.valueOf(agent1.getId()));
                    pnr.setOwner(agent1.getOwner());
                    pnr.setPnrType(1);// 1：ETERM
                    pnr.setSourceNo(saleOrderNo1);
                    pnr.setValid((byte) 1);
                    pnrDao.insertSelective(pnr);
                }
                /* 创建主订单 */
                saleOrder1.setSaleOrderNo(saleOrderNo1);
                saleOrder1.setOwner(agent1.getOwner());
                saleOrder1.setSourceChannelNo(saleOrder1.getSourceChannelNo() == null ? agent1.getDevice() : saleOrder1.getSourceChannelNo());
                saleOrder1.setCustomerTypeNo(saleOrder1.getCustomerTypeNo() == null ? 0 : saleOrder1.getCustomerTypeNo());
                saleOrder1.setCustomerNo(saleOrder1.getCustomerNo() == null ? 0 : saleOrder1.getCustomerNo());
                saleOrder1.setOrderingLoginName(agent1.getAccount());
                saleOrder1.setGoodsType(2);// 商品大类 2=国际机票
                saleOrder1.setGoodsSubType(1);// 销售单
                saleOrder1.setGoodsName(goodsName.toString());
                saleOrder1.setOrderingTime(today);// 下单时间
                saleOrder1.setPayStatus(1);// 待支付
                saleOrder1.setValid(1);// 有效
                saleOrder1.setBusinessSignNo(businessSignNo);// 业务批次号
                saleOrder1.setBsignType(1);// 1销采 2换票 3废和退 4改签
                saleOrder1.setOrderChildStatus(saleOrder1.getOrderChildStatus() == null ? 1 : saleOrder1.getOrderChildStatus());// 1.待核价  2.已核价  3.出票中  4.已出票  5.已取消
                saleOrderService.create(requestWithActor.getAgent(), saleOrder1);
                buyOrderService.create(requestWithActor.getAgent(), buyOrderExt.getBuyOrder());
                
                /* 封装人+航段对象 */
                List<SaleOrderDetail> saleOrderDetailList = new ArrayList<>();
                for (Leg leg : oldSaleOrderExt.getLegList()) {
                    for (Passenger passenger : infList) {
                        SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
                        saleOrderDetail.setSaleOrderDetailNo(maxNoService.generateBizNo("IFT_SALE_ORDER_DETAIL_NO", 36));
                        /* 设置航程 */
                        saleOrderDetail.setLegNo(leg.getLegNo());
                        /* 设置乘客 */
                        saleOrderDetail.setPassengerNo(passenger.getPassengerNo());
                        saleOrderDetail.setSaleOrderNo(saleOrderNo1);
                        saleOrderDetail.setOwner(agent1.getOwner());
                        saleOrderDetail.setValid((byte) 1);
                        saleOrderDetail.setCreator(creator);
                        saleOrderDetail.setCreateTime(today);
                        saleOrderDetail.setIsChange(false);
                        saleOrderDetail.setStatus("1");
                        saleOrderDetail.setCabin(leg.getCabin());
                        /* 创建销售订单明细 */
                        saleOrderDetailDao.insertSelective(saleOrderDetail);
                        saleOrderDetailList.add(saleOrderDetail);
                    }
                }
                
                /* 采购单明细 */
                for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
                    BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
                    buyOrderDetail.setBuyOrderDetailNo(maxNoService.generateBizNo("IFT_BUY_ORDER_DETAIL_NO", 23));
                    buyOrderDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
                    buyOrderDetail.setBuyOrderNo(buyOrderNo);
                    buyOrderDetail.setOwner(agent1.getOwner());
                    buyOrderDetail.setCreateTime(today);
                    buyOrderDetail.setCreator(creator);
                    buyOrderDetail.setValid((byte) 1);
                    buyOrderDetailDao.insertSelective(buyOrderDetail);
                }
                
                /* 创建销售拓展单 */
                saleOrderExt.setSaleOrderNo(saleOrderNo1);
                if (pnr != null && pnr.getPnrNo() != null) {
                    saleOrderExt.setPnrNo(pnr.getPnrNo());// 设置pnr编号
                }
                saleOrderExt.setOwner(agent1.getOwner());
                saleOrderExt.setCreator(creator);
                saleOrderExt.setCreateTime(today);
                saleOrderExt.setValid((byte) 1);
                saleOrderExt.setLocker(0L);  // 解锁状态
                saleOrderExt.setLinkType(3); //补婴儿
                saleOrderExt.setLinkNo(saleOrderNo + "");//关联主单号
                saleOrderExt.setLegType(oldSaleOrderExt.getLegType());
                saleOrderExt.setCreateType(oldSaleOrderExt.getCreateType());
                saleOrderExtDao.insertSelective(saleOrderExt);
                
                /* 创建采购单 */
                buyOrderExt.setBuyOrderNo(buyOrderNo);
                buyOrderExt.setOwner(agent1.getOwner());
                buyOrderExt.setCreator(creator);
                buyOrderExt.setCreateTime(today);
                buyOrderExt.setValid((byte) 1);
                buyOrderExtDao.insertSelective(buyOrderExt);
                log.info("创建国际订单成功=====saleOrderNo=" + saleOrderNo1);
                /* 更新原单 */
                RequestWithActor<SaleOrderExt> returnOrderRequest = new RequestWithActor<>();
                oldSaleOrderExt.setLinkType(3);
                oldSaleOrderExt.setLinkNo(saleOrderNo1 + "");//新销售单号
                returnOrderRequest.setEntity(oldSaleOrderExt);
                returnOrderRequest.setAgent(agent);
                orderService.updateSaleOrderExt(returnOrderRequest);
                //String logstr = "<p>" + String.format("创建国际订单成功=====", new Date()) + ":saleOrderNo=" + JsonUtil.toJson(saleOrderNo1);
                
                /* 创建操作日志 */
                try {
                    String logstr = "用户" + agent1.getAccount() + "创建国际销售单：" + "[" + saleOrderNo1 + "]";
                    String title = "创建国际销售单";
                    IftLogHelper.logger(agent1, saleOrderNo1, title, logstr);
                  /*  LogRecord logRecord = new LogRecord();
                    logRecord.setAppCode("UBP");
                    logRecord.setCreateTime(new Date());
                    logRecord.setTitle("创建国际销售单");
                    logRecord.setDesc(logstr);
                    logRecord.setOptLoginName(agent1.getAccount());
                    logRecord.setRequestIp(agent1.getIp());
                    logRecord.setBizCode("IFT");
                    logRecord.setBizNo(String.valueOf(saleOrderNo1));
                    Map<String, Object> otherOpts = new HashMap<String, Object>();
                    otherOpts.put("transationOrderNo", saleOrder1.getTransationOrderNo());
                    logRecord.setOtherOpts(otherOpts);
                    logService.insert(logRecord);*/
                } catch (Exception e) {
                    log.error("添加操作日志异常===" + e);
                }
                
            } catch (GSSException ex) {
                log.error("创建订单失败", ex);
                throw ex;
            } catch (Exception e) {
                log.error("创建订单失败", e);
                throw new GSSException("创建订单异常," + e.getMessage(), "0101", "创建订单失败");
            }
        }
        return 0;
    }
    
    private TransationOrder setTransationOrderValue(Agent agent, String contacts, String mobile, Long transactionId) {
        TransationOrder tr = new TransationOrder();
        tr.setContacts(contacts);
        tr.setCreateTime(new Date());
        tr.setCustomerNo(agent.getNum());
        tr.setCustomerTypeNo(agent.getType());
        tr.setMobile(mobile);
        tr.setOrderingLoginName(agent.getAccount());
        tr.setOwner(agent.getOwner());
        tr.setSourceChannelNo(agent.getDevice());
        tr.setPayStatus(1);
        tr.setTransationOrderNo(transactionId);
        tr.setValid(1);
        return tr;
    }
    
    @Override
    public int outTicketInform(OrderInformVo orderInformVo) {
        if (orderInformVo == null || orderInformVo.getInformType() == null || orderInformVo.getSaleOrderNo() == null) {
            throw new GSSException("订单变更通知参数错误", "0403", "订单变更通知参数错误");
        }
        Passenger passenger = new Passenger();
        BuyOrderExt buyOrderExt = new BuyOrderExt();
        SaleOrderExt saleOrderExt = new SaleOrderExt();
        SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
        if (orderInformVo.getInformType() == 1) {
            try {
                PropertyUtils.copyProperties(passenger, orderInformVo);
                PropertyUtils.copyProperties(saleOrderDetail, orderInformVo);
                PropertyUtils.copyProperties(buyOrderExt, orderInformVo);
                PropertyUtils.copyProperties(saleOrderExt, orderInformVo);
                passengerDao.updateByOrderNo(passenger);
                buyOrderExtDao.updateByOrderNo(buyOrderExt);
                saleOrderExtDao.updateByPrimaryKey(saleOrderExt);
            } catch (Exception e) {
                log.error("订单变更通知", e);
            }
        } else if (orderInformVo.getInformType() == 5) {
            saleOrderDetail.setRefuseReason(orderInformVo.getRefuseReason());
            saleOrderDetail.setStatus(orderInformVo.getStatus());
            saleOrderDetail.setSaleOrderNo(orderInformVo.getSaleOrderNo());
        }
        saleOrderDetailDao.updateByOrderNo(saleOrderDetail);
        return 0;
    }
    
    @Override
    public String warnOrder(RequestWithActor<WarnOrderRequest> requestWithActor) {
        
        if (null == requestWithActor || null == requestWithActor.getAgent() || null == requestWithActor.getEntity()) {
            throw new GSSException("调整单提醒参数错误", "0403", "调整单提醒参数错误");
        }
        
        WarnOrderRequest warnOrderRequest = requestWithActor.getEntity();
        WarnOrder warnOrder = new WarnOrder();
        int status = warnOrderRequest.getStatus();
        String orderId = warnOrderRequest.getOrderid();
        String remarks = warnOrderRequest.getRemarks();
        int revalue = 0;
        if (0 == status) {
            warnOrder.setDatetime(DateUtil.getFormatDate(new Date(), DateUtil.DATAFORMAT_STR));
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(), Long.parseLong(orderId));
            warnOrder.setOrderid(orderId);
            warnOrder.setRemarks(remarks);
            warnOrder.setStatus(status + "");
            if (null != saleOrder) {
                Long customerNo = saleOrder.getCustomerNo();
                if (null != customerNo) {
                    warnOrder.setUserid(customerNo.toString());
                }
            }
            revalue = warnOrderService.addWarnOrder(warnOrder);
        } else {
            warnOrder.setOrderid(orderId);
            List<WarnOrder> warnOrders = warnOrderService.findWarnOrders(warnOrder);
            if (null != warnOrders && warnOrders.size() > 0) {
                warnOrder = warnOrders.get(0);
                warnOrder.setStatus(status + "");
                revalue = warnOrderService.updateWarnOrder(warnOrder);
            }
        }
        if (revalue > 0) {
            return "1";
        } else {
            return "0";
        }
    }
    
    @Override
    @Transactional
    public ReturnSettInfo uploadUbpInfo(Agent agent, Long supplierNo, SaleOrderExtVo saleOrderExtVo) {
        URL url = null;
        ReturnSettInfo ubpInfo = new ReturnSettInfo();
        try {
            String gssWebService_wsdlLocation = "http://10.2.104.113/gss_accounts/ws/insaleservice?wsdl";
            url = new URL(gssWebService_wsdlLocation);
        } catch (Exception ex) {
            log.error("无法获取到值", ex);
            throw new RuntimeException();
        }
        InsaleService_Service service = new InsaleService_Service(url);
        InsaleService insaleService = service.getInsaleServicePort();
        List<Long> orders = new ArrayList<Long>();
        orders.add(saleOrderExtVo.getSaleOrderNo());
//		List<Long> ids  = iGssMainService.queryAllNotUploadId(orders);
//		if(!StringUtil.isNotNullOrEmpty(ids)){
//			ubpInfo.setMessage("解挂失败,该订单未推送至结算");
//			ubpInfo.setCode("400");
//			return ubpInfo;
//		}
        // 根据销售单号查询机票
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("saleOrdNum", saleOrderExtVo.getSaleOrderNo() + "");
        List<IftPlaneTicket> planeTickets = planeTicketService.selectIftByMap(map);
        
        GssMain gssMain = selectBatchIds(saleOrderExtVo.getSaleOrderNo());
        RequestWithActor<Long> requestWithActor1 = new RequestWithActor<>();
        requestWithActor1.setEntity(saleOrderExtVo.getSaleOrderNo());
        SaleOrderExt saleOrderExt = orderService.getOrder(requestWithActor1);
        
        InallsaleVo sale = new InallsaleVo();
        sale.setOrderid(saleOrderExtVo.getSaleOrderNo() + "");//工单号
        sale.setPnr(saleOrderExt.getPnrNo() + "");// 设置pnr编号;
        Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
        sale.setSupplier(supplier.getShortName());//供应商名称
        sale.setSupplieradd(supplier.getAddress());//供应商地址
        sale.setIssuedate(DateUtil.dateToDateString(saleOrderExt.getIssueTime(), "yyyy-MM-dd"));// 出票日期
        sale.setAircode(planeTickets.get(0).getAirCode());//结算代码
        sale.setRate(null == planeTickets.get(0).getRate() ? 0d : planeTickets.get(0).getRate().doubleValue());//汇率     ??
        sale.setSaledept(gssMain.getSaleDept() != null ? gssMain.getSaleDept() : "深圳总部");// 销售部门
        sale.setSalecom(gssMain.getSaleCom()); // 销售公司
        sale.setIssuer(planeTickets.get(0).getTicPeo());//出票员
        sale.setBusinessline(gssMain.getLine());// 业务线
        if (StringUtils.isNotBlank(saleOrderExt.getStatus())) {
            sale.setStatus(saleOrderExt.getStatus());//默认传0  4:解挂
        } else {
            sale.setStatus("4");//默认传0  4:解挂
        }
        sale.setPolicy("");//政策信息
        sale.setCustomername(gssMain.getCusName()); // 客户名称
        sale.setCustomeradd("深圳市");//客户地点
        sale.setCustomerid(gssMain.getCusCode());//客户ID
        sale.setMemberid("");//会员号
        sale.setContacts(saleOrderExt.getContactName());//联系人
        sale.setPhone(saleOrderExt.getContactMobile());//联系电话
        sale.setBookticketperson("");//对方订票人
        sale.setBookticketpersondep("");//对方费用归属部门
        sale.setBilltype("1");// 票据种类 1 收据 2.发票  先默认1
        sale.setInvoicecode("");//发票号
        sale.setLimitcondition("");//机票限制条件
        sale.setRemark("解挂测试");//备注
        sale.setLegtype("1");// 编码 1 ：不分航" 2 ：分航段  先默认1
        if ("挂账支付".equals(gssMain.getPaidStatus())) {
            sale.setPaystatus("2");// 支付状态： 0 线上已支付 1未支付 2线下已支付
        } else {
            sale.setPaystatus("0");// 支付状态： 0 线上已支付 1未支付 2线下已支付
        }
        sale.setPaymentterms(gssMain.getPaidMethod());// 付款条件  // 付款方式
        //订单创建类型. 1：PNR,2：白屏，3：手工补单.4散客需求单.5:团队押金单6.PNR后台下单7.冲单8.补单9.调整单1 10.调整单2 11.ADM单 12.ACM单
        if ("1".equals(saleOrderExt.getCreateType() + "") || "2".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("0"); //票证类型0：正常票  0：正常票  2：废票  3：补单  9：冲单   11：调整单1 12：调整单2  13：改期票   14：换开票   15：团队票  10: 删除订单
        } else if ("11".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("ADM");
        } else if ("12".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("ACM");
        } else if ("9".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("11");
        } else if ("10".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("12");
        } else if ("7".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("9");
        } else if ("8".equals(saleOrderExt.getCreateType() + "") || "3".equals(saleOrderExt.getCreateType() + "")) {
            sale.setTickettype("3");
        } else {
            ubpInfo.setMessage("解挂失败,未知票证类型");
            ubpInfo.setCode("400");
            return ubpInfo;
        }
        sale.setType("0");//机票
        sale.setTicketcom(gssMain.getSaleCom());//出票公司
        sale.setSellperson(planeTickets.get(0).getTicPeo());//销售员
        sale.setSelldate(DateUtil.dateToDateString(saleOrderExt.getIssueTime(), "yyyy-MM-dd"));//销售日期（销售员建单日期）
        sale.setErrorway("");//差错处理方式
        sale.setAgreement("");//是否协议客户
        sale.setClearingway(gssMain.getSettlMethod());//结算方式
        sale.setTanagementarea("深圳");//管理区域
        sale.setAgencyid(saleOrderExt.getTeamNo() + "");//团队编号
        sale.setFilenumber("");//出票文件号
        String payNum = "";
        for (IftPlaneTicket planeTicket : planeTickets) {
            if (planeTickets.size() == 1) {
                payNum = planeTicket.getSuppPayNum();//支付流水号
            } else {
                payNum = planeTicket.getSuppPayNum() + ",";
            }
        }
        sale.setPayno(payNum);//支付流水号   逗号分割
        sale.setPlatformorderid("");//平台订单号
        sale.setPlatfromcustomername("");//平台客户名称
        sale.setTripartiteAgreementNo("");//三方协议号
        sale.setBillingCode("");//开票代码
        
        List<InsaleVo> insaleVoList = new ArrayList<InsaleVo>();
        List<SaleOrderDetail> detailList = saleOrderDetailDao.selectBySaleOrderNo(saleOrderExtVo.getSaleOrderNo());
        for (OrderPriceVo orderPriceVo : saleOrderExtVo.getOrderPriceVoList()) {
            for (Passenger plt : saleOrderExt.getPassengerList()) {
                String passengerNo = plt.getPassengerNo().toString();
                String detailLeg = "";
                // 找出同一个乘客的明细对应到的对应航程
                for (SaleOrderDetail detail : detailList) {
                    String detailPgerNo = detail.getPassengerNo().toString();
                    if (passengerNo.equals(detailPgerNo)) {
                        detailLeg = detail.getTicketNo();
                    }
                }
                //销售单
                if (orderPriceVo.getPassengerType() != null && orderPriceVo.getOrderType() != null) {
                    if (orderPriceVo.getPassengerType().equals(plt.getPassengerType()) && ("2").equals(orderPriceVo.getOrderType())) {
                        InsaleVo insaleVo = new InsaleVo();
                        // 计算一张票的营业部毛利
                        Double yyb_maoli = 0.0;
                        Double maoli = 0.0;
                        Double jingjia_heji = 0.0;
                        Double daili = orderPriceVo.getBuyAgencyFee() == null ? 0d : orderPriceVo.getBuyAgencyFee().doubleValue();// 代理
                        Double dikou = orderPriceVo.getBuyAgencyFee() == null ? 0d : orderPriceVo.getBuyAgencyFee().doubleValue();// 底扣
                        Double qValue = 0d;// Q值
                        Double dijia = orderPriceVo.getBuyFare() == null ? 0d : orderPriceVo.getBuyFare().doubleValue();// 底价
                        jingjia_heji = jingjia_heji + dijia * (100 - daili) / 100 + qValue * (100 - daili) / 100;
                        
                        Double buytax = orderPriceVo.getBuyTax() == null ? 0d : orderPriceVo.getBuyTax().doubleValue();
                        Double saleJgPrice = orderPriceVo.getSaleJgPrice() == null ? 0d : orderPriceVo.getSaleJgPrice().doubleValue();
                        // 营业部毛利=实收-{（底价）*（1-代理费率）*（1-底扣）+税+Q值*(1-代理费%)+加价金额}
                        yyb_maoli = yyb_maoli + (dijia * (100 - daili) / 100 * (100 - dikou) / 100) + qValue * (100 - daili) / 100;
                        maoli = maoli + (dijia * (100 - daili) / 100) + qValue * (100 - daili) / 100;
                        yyb_maoli = saleJgPrice - yyb_maoli - buytax;
                        insaleVo.setSalesmargin(yyb_maoli);// 营业部毛利
                        maoli = saleJgPrice - maoli - buytax;
                        insaleVo.setProfit(maoli);// 毛利 计算
                        jingjia_heji = jingjia_heji + buytax;
                        insaleVo.setBasepricetotal(jingjia_heji);// 净价合计
                        insaleVo.setActualbaseprice(0d);//实际底价  不要
                        insaleVo.setActualproxyrate(0d);//实际代理费  不要
                        insaleVo.setTicketno(detailLeg);//票号
                        insaleVo.setPassengername(plt.getSurname() + " " + plt.getName());//旅客姓名
                        insaleVo.setPassengersex(plt.getGender());//性别
                        insaleVo.setNationality(plt.getNationality());//国籍
                        insaleVo.setIdnumber(plt.getCertNo());//证件号码
                        insaleVo.setBirthdate(DateUtil.getFormatDate(plt.getPassengerBirth(), "yyyy-MM-dd"));//出生日期
                        insaleVo.setPapersvalid(DateUtil.getFormatDate(plt.getCertValid(), "yyyy-MM-dd"));//证件有效期
                        insaleVo.setNum(1);//张数    ???  有几个票号
                        insaleVo.setPaidamount(gssMain.getActAmount() == null ? 0d : gssMain.getActAmount().doubleValue());//实收金额
                        insaleVo.setPmcurrency("CNY");//实收金额币种
                        insaleVo.setTax(buytax);//税金   ???
                        insaleVo.setInvalidbaseprice(0d);//废票底价??
                        insaleVo.setInvalidprice(0d);//废票售价??
                        insaleVo.setOldticketno("");//旧票号  ??
                        insaleVo.setCurrency("CNY");//币种 净价和税金的币种
                        insaleVoList.add(insaleVo);
                    }
                }
            }
        }
        sale.setSaleList(insaleVoList);
        
        List<InpayVo> inpayVoList = new ArrayList<InpayVo>();
        //OS_CERTIFICATE   OS_TRANSATIONORDER
        for (IftPlaneTicket planeTicket : planeTickets) {
            InpayVo inpayVo = new InpayVo();
            inpayVo.setPayway(planeTicket.getSuppPayMode());//支付方式
            inpayVo.setPayno(planeTicket.getSuppPayNum());//支付流水号
            inpayVo.setPaymoney(planeTicket.getReceAmount() == null ? 0d : planeTicket.getReceAmount().doubleValue());//支付金额    ???
            inpayVo.setPaydate(planeTicket.getIssueDate().substring(0, 9));//支付日期  ??
            inpayVo.setOrderid(saleOrderExtVo.getSaleOrderNo() + "");//订单号
            inpayVo.setPayrecord("");//支付凭证
            inpayVoList.add(inpayVo);
        }
        sale.setPayList(inpayVoList);
        
        List<InairlinesVo> inairlinesVoList = new ArrayList<InairlinesVo>();
        for (OrderPriceVo orderPriceVo : saleOrderExtVo.getOrderPriceVoList()) {
            for (SaleOrderDetail saleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                //销售单
                if (orderPriceVo.getPassengerType() != null && orderPriceVo.getOrderType() != null) {
                    if (orderPriceVo.getPassengerType().equals(saleOrderDetail.getPassenger().getPassengerType()) && ("2").equals(orderPriceVo.getOrderType())) {
                        Leg leg = saleOrderDetail.getLeg();
                        // 计算一张票的营业部毛利
                        Double yyb_maoli = 0.0;
                        Double maoli = 0.0;
                        Double jingjia_heji = 0.0;
                        Double daili = orderPriceVo.getBuyAgencyFee() == null ? 0d : orderPriceVo.getBuyAgencyFee().doubleValue();// 代理
                        Double dikou = orderPriceVo.getBuyAgencyFee() == null ? 0d : orderPriceVo.getBuyAgencyFee().doubleValue();// 底扣
                        Double qValue = 0d;// Q值
                        Double dijia = orderPriceVo.getBuyFare() == null ? 0d : orderPriceVo.getBuyFare().doubleValue();// 底价
                        
                        Double buytax = orderPriceVo.getBuyTax() == null ? 0d : orderPriceVo.getBuyTax().doubleValue();
                        Double saleJgPrice = orderPriceVo.getSaleJgPrice() == null ? 0d : orderPriceVo.getSaleJgPrice().doubleValue();
                        jingjia_heji = jingjia_heji + dijia * (100 - daili) / 100 + qValue * (100 - daili) / 100;
                        // 营业部毛利=实收-{（底价）*（1-代理费率）*（1-底扣）+税+Q值*(1-代理费%)+加价金额}
                        yyb_maoli = yyb_maoli + (dijia * (100 - daili) / 100 * (100 - dikou) / 100) + qValue * (100 - daili) / 100;
                        maoli = maoli + (dijia * (100 - daili) / 100) + qValue * (100 - daili) / 100;
                        yyb_maoli = saleJgPrice - yyb_maoli - buytax;
                        InairlinesVo inairlinesVo = new InairlinesVo();
                        inairlinesVo.setFlightno(leg.getFlightNo());//航班号
                        inairlinesVo.setCabin(leg.getCabin());//仓位
                        inairlinesVo.setDeparturedate(DateUtil.dateToDateString(leg.getDepTime(), "yyyy-MM-dd"));//乘机日期
                        inairlinesVo.setRouting(leg.getDepAirport() + "-" + leg.getArrAirport());//航段
                        inairlinesVo.setDeparturetime(DateUtil.dateToDateString(leg.getDepTime(), "yyyy-MM-dd"));//起飞时间
                        inairlinesVo.setArrivaltime(DateUtil.dateToDateString(leg.getArrTime(), "yyyy-MM-dd"));//到达时间
                        inairlinesVo.setPassengerstype(saleOrderDetail.getPassenger().getPassengerType());//乘机人类型
                        inairlinesVo.setProxyrate(daili);//代理费率  ??
                        inairlinesVo.setDeductedpoints(dikou);//底扣点
                        inairlinesVo.setAfrerrentnpoint(0d);//后返点  ??  不要
                        inairlinesVo.setQvalue(0d);//Q值  不要
                        inairlinesVo.setAddprice(0d);//加价   附加费
                        inairlinesVo.setBaseprice(dijia);//底价(票面价)
                        inairlinesVo.setSaleprice(saleJgPrice);//销售价(销售 结算价)
                        inairlinesVo.setOldticketno("");//旧票号  ??
                        inairlinesVo.setTicketno(saleOrderDetail.getTicketNo());//票号
                        inairlinesVoList.add(inairlinesVo);
                    }
                }
            }
        }
        sale.setAirlinesList(inairlinesVoList);
        try {
            log.info("解挂订单请求:", JsonUtil.toJson(sale));
            ubpInfo = insaleService.addInallsale(sale);
            log.info("结算系统解挂返回原始JSON:", JsonUtil.toJson(ubpInfo));
            /**如果创建类型是冲单、补单、调整单1、调整单2、ADM单、ACM单，就不用解挂**/String createTypes = "7,8,9,10,11,12";
            if (ubpInfo.getCode().equals("0") && createTypes.indexOf(saleOrderExt.getCreateType() + "") == -1) {
                
                List<AdjustOrder> adjustorder = adjustOrderDao.getAdjustOrderByOrderId(saleOrderExtVo.getSaleOrderNo() + "");
                if (adjustorder != null && adjustorder.size() > 0) {
                    
                    for (AdjustOrder ado : adjustorder) {
                        ado.setAdjustflag("2");// 已解挂
                        adjustOrderDao.updateByPrimaryKey(ado);
                    }
                }
                ubpInfo.setMessage("解挂成功");
                ubpInfo.setCode("200");
            } else {
                ubpInfo.setMessage("解挂失败" + ubpInfo.getMessage());
                ubpInfo.setCode("400");
            }
            log.info("解挂订单结算系统数据响应：{}", ubpInfo);
        } catch (Exception e) {
            log.error("解挂异常", e);
            ubpInfo.setMessage("解挂异常,该订单未推送至结算");
            ubpInfo.setCode("400");
        }
        return ubpInfo;
    }
    
    @Override
    @Transactional
    public GssMain selectBatchIds(Long orederNo) {
        return gssMainDao.selectByOrderNo(orederNo);
    }
    
    @Override
    @Transactional
    public void updateBuy(SaleOrderExtVo saleOrderExtVo, Long supplierNo, Agent agent, Long needPayId, Integer incomeExpenseType, Integer recordMovingType, Double planAmount, List<SaleOrderDetail> saleOrderDetailList, String movingReason) throws Exception {
        // 修改buyOrderExt信息
        List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(saleOrderExtVo.getSaleOrderNo());
        try {
            if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                Supplier supplier = supplierService.getSupplierByNo(agent, supplierNo);
                BuyOrderExt buyOrderExt = buyOrderExtList.get(0);
                buyOrderExt.setSupplierNo(supplierNo);
                buyOrderExt.setSupplierTypeNo(supplier.getCustomerTypeNo());
                buyOrderExt.setModifyTime(new Date());
                buyOrderExt.setModifier(agent.getAccount());
                buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
                // 修改buyOrder的supplierNo信息
                BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderExt.getBuyOrderNo());
                buyOrder.setSupplierNo(supplierNo);
                buyOrderService.update(agent, buyOrder);
            }
            //修改乘机人采购结算价
            for (OrderPriceVo orderPriceVo : saleOrderExtVo.getOrderPriceVoList()) {
                for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
                    //销售单
                    if (orderPriceVo.getPassengerType() != null && orderPriceVo.getOrderType() != null) {
                        if (orderPriceVo.getPassengerType().equals(saleOrderDetail.getPassenger().getPassengerType()) && ("2").equals(orderPriceVo.getOrderType())) {
                            // 修改价格信息
                            saleOrderDetail.getPassenger().setBuyFare(orderPriceVo.getBuyFare());
                            saleOrderDetail.getPassenger().setBuyTax(orderPriceVo.getBuyTax());
                            saleOrderDetail.getPassenger().setBuyAgencyFee(orderPriceVo.getBuyAgencyFee());
                            saleOrderDetail.getPassenger().setBuyRebate(orderPriceVo.getBuyRebate());
                            saleOrderDetail.getPassenger().setBuyBrokerage(orderPriceVo.getBuyBrokerage());
                            saleOrderDetail.getPassenger().setBuyAwardPrice(orderPriceVo.getBuyAwardPrice());
                            saleOrderDetail.getPassenger().setBuyPrice(orderPriceVo.getBuyPrice());
                            RequestWithActor<Passenger> updatePassenger = new RequestWithActor<>();
                            updatePassenger.setEntity(saleOrderDetail.getPassenger());
                            updatePassenger.setAgent(agent);
                            passengerService.updatePassenger(updatePassenger);
                        }
                    }
                }
            }
            //根据应收记录ID修改应收(付)金额，只能修改采购单或采购变更单
            if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                UpdatePlanAmountVO planAmountVO = new UpdatePlanAmountVO();
                planAmountVO.setId(needPayId);
                planAmountVO.setIncomeExpenseType(incomeExpenseType);
                planAmountVO.setMovingReason(movingReason);
                planAmountVO.setPlanAmount(new BigDecimal(planAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
                planAmountVO.setRecordMovingType(recordMovingType);
                needPayService.updateBuyPlanAmount(agent, planAmountVO);
            } else {
                throw new GSSException("采购单为空", "0601", "修改采购失败");
            }
        } catch (Exception e) {
            throw new GSSException(e.getMessage(), "0602", "修改采购失败");
        }
    }
    
    @Override
    @Transactional
    public int addAdjustOrder(AdjustOrder adjustOrder, InallsaleRequest inallsaleRequest, SaleOrderExt saleOrderExt) {
        int num = 0;
        try {
            List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(Long.valueOf(inallsaleRequest.getOrderid()));
            // 打回类型  //0:销售员    //1:出票员                挂给销售，还是出票
            if ("0".equals(inallsaleRequest.getSelectType())) {
                adjustOrder.setUserid(saleOrderExt.getCreator() + "");
            } else if ("1".equals(inallsaleRequest.getSelectType())) {
                adjustOrder.setUserid(buyOrderExtList.get(0).getCreator() + "");
            } else {
                adjustOrder.setUserid(saleOrderExt.getCreator() + "");
            }
            num = adjustOrderDao.insert(adjustOrder);
        } catch (Exception e) {
            log.error("addAdjustOrder", e);
        }
        return num;
    }
    
    /**
     * 已超过下单时间30分钟,订单被拒
     *
     * @param agent
     * @param saleOrderNo
     */
    @Override
    @Transactional
    public void updateStatusAsRefuse(Agent agent, Long saleOrderNo) {
        SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
        saleOrderDetail.setSaleOrderNo(saleOrderNo);
        saleOrderDetail.setStatus("11");
        log.info("审核订单" + saleOrderNo + "已超过下单时间30分钟,订单被拒：" + saleOrderDetail.toString());
        saleOrderDetailDao.updateByOrderNo(saleOrderDetail);
        //更新销售订单
        saleOrderService.updateStatus(agent, saleOrderNo, 11);
    }
    
    /**
     * 查询已出票，但是没有配送产品，并且在这个表(LS_DELIVERY_BATCH_OS)没有数据
     *
     * @param saleOrderQueryRequest
     * @return 结果返回到销售平台，销售平台需要二次处理.
     */
    @Override
    public Page<SaleOrderExt> selectOutTicketOrder(Page<SaleOrderExt> page, RequestWithActor<SaleQueryOrderVo> saleOrderQueryRequest) {
        log.info("订单查询开始,查询参数" + JSON.toJSONString(saleOrderQueryRequest));
        try {
            if (saleOrderQueryRequest.getAgent() == null && saleOrderQueryRequest.getAgent().getOwner() == 0) {
                throw new GSSException("查询订单模块（为运营平台订单管理提供服务）", "0401", "传入参数为空");
            }
            saleOrderQueryRequest.getEntity().setOwner(saleOrderQueryRequest.getAgent().getOwner());
            saleOrderQueryRequest.getEntity().setValid((byte) 1);
            if (saleOrderQueryRequest.getAgent().getNum() != null) {
                saleOrderQueryRequest.getEntity().setCustomerNo(saleOrderQueryRequest.getAgent().getNum().toString());
            }
            
            long startTime = System.currentTimeMillis();
            log.info("查询订单接口开始=====");
            Boolean isNeedCustomer = saleOrderQueryRequest.getEntity().getCustomerCount();
            List<SaleOrderExt> saleOrderExtList = null;
            if (isNeedCustomer) {
                List<Customer> customers = customerService.getSubCustomerByCno(saleOrderQueryRequest.getAgent(), saleOrderQueryRequest.getAgent().getNum());
                List<Long> longList = new ArrayList<>();
                if (null != customers) {
                    for (Customer customer : customers) {
                        longList.add(customer.getCustomerNo());
                    }
                }
                saleOrderQueryRequest.getEntity().setLongList(longList);
            }
            saleOrderExtList = saleOrderExtDao.selectOutTicketOrder(page, saleOrderQueryRequest.getEntity());
            
            long endTime = System.currentTimeMillis();
            log.info("查询订单接口结束=====" + (endTime - startTime));
            List<SaleOrderExt> tempList = new ArrayList<>();
            if (null != saleOrderExtList) {
                for (SaleOrderExt order : saleOrderExtList) {
                    SaleOrder saleOrder = saleOrderService.getSOrderByNo(saleOrderQueryRequest.getAgent(), order.getSaleOrderNo());
                    order.setSaleOrder(saleOrder);
                    tempList.add(order);
                }
            }
            long objectTime = System.currentTimeMillis();
            log.info("封装订单接口结束=====" + (objectTime - endTime));
            page.setRecords(tempList);
            
            log.info("查询订单模块（为运营平台订单管理提供服务）结束");
        } catch (Exception e) {
            log.error("查询订单异常", e);
            throw new GSSException("查询订单模块（为运营平台订单管理提供服务）", "0403", "查询订单异常");
        }
        return page;
    }
    
    private void updateSaleOrderDetail(SaleOrderExt order, TicketSender peopleInfo, Date date) {
        SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
        saleOrderDetail.setStatus("3");//出票中
        saleOrderDetail.setSaleOrderNo(order.getSaleOrderNo());
        saleOrderDetail.setModifier(peopleInfo.getUserid() + "");
        saleOrderDetail.setModifyTime(date);
        saleOrderDetailDao.updateByOrderNo(saleOrderDetail);
    }
    
    private void assingLockOrder(SaleOrderExt order, TicketSender sender, Date date, Agent agent) {
        User user = userService.findUserByLoginName(agent, sender.getUserid());
        order.setLocker(user.getId());
        order.setLockTime(date);
        saleOrderExtDao.updateLocker(order);
    }
    
    private void increaseOrderCount(TicketSender sender) {
        sender.setOrdercount(sender.getOrdercount() + 1);
        sender.setIds(sender.getId() + "");
        iTicketSenderService.update(sender);
    }
    
    private void subSaleOrderNum(Agent agent, Long locker) {
        try {
            User user = userService.selectById(agent, locker);
            if (user != null) {
                TicketSender ticketSender = iTicketSenderService.getTicketSenderByLoginId(user.getLoginName());
                if (ticketSender != null) {
                    ticketSender.setOrdercount(ticketSender.getOrdercount() - 1);
                    log.info("更新出票员的订单数量" + ticketSender.toString());
                    iTicketSenderService.updateByPrimaryKey(ticketSender);
                }
            }
        } catch (Exception e) {
            log.error("更新出票员的出票订单数量异常", e);
        }
    }
    
    private void sendInfo(String userId, Long saleOrderNo, String orderStatus) {
        SocketDO sdo = new SocketDO();
        sdo.setType(1000002);
        sdo.setOther(orderStatus);//
        sdo.setLoginName(userId);
        sdo.setSaleOrder(String.valueOf(saleOrderNo));
        mqSender.send("gss-websocket-exchange", "notice", sdo);
    }
    
    /** 获取可以出票的分配订单 */
    public List<SaleOrderExt> getAssignedOrders(Integer[] createTypeStatusArray) {
        SaleQueryOrderVo saleQueryOrderVo = new SaleQueryOrderVo();
        saleQueryOrderVo.setPayStatuss("3,4");
        saleQueryOrderVo.setValid((byte) 1);
        saleQueryOrderVo.setOrderStatus(2);
        saleQueryOrderVo.setLocker(0L);
        saleQueryOrderVo.setCreateTypeStatusArray(createTypeStatusArray);
        List<SaleOrderExt> saleOrderExtList = saleOrderExtDao.queryAssignOrder(saleQueryOrderVo);
        return saleOrderExtList;
    }
    
    /**
     * 定时器获取待出票订单
     *
     * @param createTypeStatusArray
     * @return
     */
    public List<SaleOrderExt> getNoHandleOrders(Integer[] createTypeStatusArray) {
        
        List<SaleOrderExt> saleOrderExtList = saleOrderExtDao.queryNoHandOrder(); //获取未锁定的出票单
        return saleOrderExtList;
    }
    
    private void sendTicketInfoByMq(Agent agent, Long transationOrderNo) throws RuntimeException {
        /*SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
        Long transationOrderNo = null;
        if (null != saleOrder) {
            transationOrderNo = saleOrder.getTransationOrderNo();
        }*/
        IftTicketMessage iftTicketMessage = new IftTicketMessage();
        iftTicketMessage.setTradeNo(transationOrderNo);
        iftTicketMessage.setOwner(agent.getOwner());
        log.info("国际机票出票通知--->发送MQ消息：" + ToStringBuilder.reflectionToString(iftTicketMessage));
        iftTicketMqSender.send(IftTicketMqSender.TICKETED_KEY, iftTicketMessage);
    }
    
    private void updateBuyOrder(Agent agent, BuyOrder buyOrder, BigDecimal payable, PassengerListVo listVo, Supplier supplier) throws RuntimeException {
        //Supplier supplier = supplierService.getSupplierByNo(agent, listVo.getSupplierNo());
        //List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);
        //if (buyOrderList != null && buyOrderList.size() != 0) {
        //   BuyOrder buyOrder = buyOrderList.get(0);
        BuyOrder newBuyOrder = new BuyOrder();
        newBuyOrder.setSupplierNo(supplier.getSupplierNo());
        newBuyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
        newBuyOrder.setBuyChildStatus(3);// 待处理（1），处理中（2），已出票（3），已拒单（4）
        newBuyOrder.setBuyOrderNo(buyOrder.getBuyOrderNo());
        newBuyOrder.setGoodsType(buyOrder.getGoodsType());
        newBuyOrder.setPayable(payable);
        buyOrderService.update(agent, newBuyOrder);
        //Account account = accountService.getAccountByAccountNo(agent, listVo.getAccountNo());
          /*  if (account != null) {
                this.createBuyCertificate(agent, buyOrder.getBuyOrderNo(), buyOrder.getPayable().doubleValue(), account.getAccountNo(), supplier.getSupplierNo(), supplier.getCustomerTypeNo(), 2, account.getType(), "BUY", tickets, listVo.getDealNo());
                log.info("调用订单创建采购付款单成功，BuyOrderNo=" + buyOrder.getBuyOrderNo() + ",account = " + account);
            } else {
                throw new GSSException("创建采购付款单失败", "0102", "资金帐号未能查出相应数据!account为空！accountNo=" + listVo.getAccountNo());
            }*/
        // 修改出票类型
        BuyOrderExt buyOrderExt = buyOrderExtDao.selectByPrimaryKey(buyOrder.getBuyOrderNo());
        if (buyOrderExt != null) {
            buyOrderExt.setTicketType(listVo.getTicketType());
            buyOrderExt.setBuyRemarke(listVo.getBuyRemarke());
        }
        buyOrderExtDao.updateByPrimaryKeySelective(buyOrderExt);
        //}
    }
    
    private Long savePnr(Long pnrNo, PassengerListVo listVo, Long saleOrderNo, Agent agent, Date date) throws RuntimeException {
        Pnr insetPnr = new Pnr();
        pnrNo = maxNoService.generateBizNo("IFT_PNR_NO", 32);
        insetPnr.setPnr(listVo.getPnr());
        insetPnr.setSourceNo(saleOrderNo);
        insetPnr.setBigPnr(listVo.getBigPnr());
        insetPnr.setPnrNo(pnrNo);
        insetPnr.setCreator(agent.getAccount());
        insetPnr.setCreateTime(date);
        insetPnr.setOwner(agent.getOwner());
        insetPnr.setValid((byte) 1);
        pnrDao.insertSelective(insetPnr);
        return pnrNo;
    }
    
    private void ApiCallBack(SaleOrder saleOrder, Agent agent, String logstr, Date date) {
        if ("API".equals(saleOrder.getSourceChannelNo())) {//外部订单
            try {
                Agent newAgent = new Agent(agent.getOwner(), saleOrder.getCustomerTypeNo(), saleOrder.getCustomerNo(), agent.getId(), agent.getAccount(), agent.getToken(), agent.getIp(), agent.getDevice(), null);
                mssReserveService.drawTicketOta(newAgent, saleOrder.getSaleOrderNo(), "2");
                logstr += "<p>" + String.format("出单操作成功:%1$tF %1$tT", date) + ":" + JsonUtil.toJson(saleOrder.getSaleOrderNo());
            } catch (Exception e) {
                log.error("国际订单出票回调失败=============e" + e);
                // throw new GSSException("国际订单出票回调失败","","国际订单出票回调失败"+e);
            }
        }
    }
}

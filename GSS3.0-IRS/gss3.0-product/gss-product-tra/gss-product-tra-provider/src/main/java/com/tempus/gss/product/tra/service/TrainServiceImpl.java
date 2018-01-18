package com.tempus.gss.product.tra.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.ops.util.ConvertUtils;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.BuyChange;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.CertificateCreateVO;
import com.tempus.gss.order.entity.CostType;
import com.tempus.gss.order.entity.CreatePlanAmountVO;
import com.tempus.gss.order.entity.GoodsBigType;
import com.tempus.gss.order.entity.PlanAmountRecord;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.IBuyChangeService;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ICertificateService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleChangeService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.tra.api.entity.BookSeatRequest;
import com.tempus.gss.product.tra.api.entity.BookTicketsRequest;
import com.tempus.gss.product.tra.api.entity.BookTicketsResponse;
import com.tempus.gss.product.tra.api.entity.ContactInfo;
import com.tempus.gss.product.tra.api.entity.CyOrderResponse;
import com.tempus.gss.product.tra.api.entity.OrderReturnReq;
import com.tempus.gss.product.tra.api.entity.Passenger;
import com.tempus.gss.product.tra.api.entity.QueryTrainRequest;
import com.tempus.gss.product.tra.api.entity.TraPassenger;
import com.tempus.gss.product.tra.api.entity.TraProfitControl;
import com.tempus.gss.product.tra.api.entity.TraSaleChangeDetail;
import com.tempus.gss.product.tra.api.entity.TraSaleChangeExt;
import com.tempus.gss.product.tra.api.entity.TraSaleOrderDetail;
import com.tempus.gss.product.tra.api.entity.TraSaleOrderExt;
import com.tempus.gss.product.tra.api.entity.TrainResponse;
import com.tempus.gss.product.tra.api.entity.vo.IsSueOrderVo;
import com.tempus.gss.product.tra.api.entity.vo.NotifyOvertimeOrderVo;
import com.tempus.gss.product.tra.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.tra.api.entity.vo.PassengerVo;
import com.tempus.gss.product.tra.api.entity.vo.RefundTicketVo;
import com.tempus.gss.product.tra.api.entity.vo.TraSaleChangeExtVo;
import com.tempus.gss.product.tra.api.entity.vo.TraSaleOrderExtVo;
import com.tempus.gss.product.tra.api.service.ITrainService;
import com.tempus.gss.product.tra.api.service.ITrainWsInteractionService;
import com.tempus.gss.product.tra.dao.TraPassengerDao;
import com.tempus.gss.product.tra.dao.TraProfitControlDao;
import com.tempus.gss.product.tra.dao.TraSaleChangeDetailDao;
import com.tempus.gss.product.tra.dao.TraSaleChangeExtDao;
import com.tempus.gss.product.tra.dao.TraSaleOrderDetailDao;
import com.tempus.gss.product.tra.dao.TraSaleOrderExtDao;
import com.tempus.gss.product.tra.util.DataConvertUtils;
import com.tempus.gss.product.tra.util.PropertiesUtils;
import com.tempus.gss.product.tra.util.TrainUtil;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;

/**
 * Created by 杨威 on 2017/2/25.
 */
@Service
@EnableAutoConfiguration
public class TrainServiceImpl implements ITrainService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ITrainWsInteractionService trainWsInteractionService;

    @Reference
    IMaxNoService maxNoService;
    @Reference
    ISaleOrderService saleOrderService;

    @Reference
    IBuyOrderService buyOrderService;
    @Reference
    IBuyChangeService buyChangeService;

    @Reference
    ISaleChangeService saleChangeService;
    @Autowired
    TraSaleOrderDetailDao traSaleOrderDetailDao;

    // 应收应付
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    @Autowired
    TraProfitControlDao traProfitControlDao;
    @Autowired
    TraSaleOrderExtDao traSaleOrderExtDao;
    @Reference
    ISupplierService supplierService;
    @Autowired
    TraPassengerDao traPassengerDao;
    @Autowired
    ITrainWsInteractionService trainWsInteractionImpl;
    @Autowired
    TraSaleChangeExtDao traSaleChangeExtDao;
    @Autowired
    TraSaleChangeDetailDao traSaleChangeDetailDao;

    @Reference
    ICertificateService certificateService;
    @Reference
	ICustomerService customerService;
    /**
     *查询火车票
     *
     * @return
     */
    @Override
    public TrainResponse queryTrain(QueryTrainRequest request) {
        long queryTime = System.currentTimeMillis();
        try {
            TrainResponse train = trainWsInteractionService.TrainSearch(request);
            queryTime = System.currentTimeMillis() - queryTime;
            if (null != train) {
                log.info("火车票查询接口>>>>>出发城市：" + request.getFrom() + ",到达城市:"
                        + request.getTo() + ",出发日期:" + request.getDate() + ",耗时:" + queryTime);
            }
            return train;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *创建火车票订单
     *
     * @return
     */
    @Override
    public Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {
        log.info("创建火车票订单开始==========");

        if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
            log.info("创建火车票订单参数为空");
            throw new GSSException("创建火车票订单参数为空", "1010", "创建火车票订单失败");
        }
        Agent agent = requestWithActor.getAgent();

        if (requestWithActor.getEntity().getSaleOrderExt() == null) {
            log.info("火车票销售单对象为空");
            throw new GSSException("火车票销售单对象为空", "1010", "创建火车票订单失败");
        }
        TraSaleOrderExt saleOrderExt = requestWithActor.getEntity().getSaleOrderExt();
        if (requestWithActor.getEntity().getOrderCreateWay() == null
                || "".equals(requestWithActor.getEntity().getOrderCreateWay())) {
            log.info("创建订单方式orderCreateWay为空");
            throw new GSSException("创建订单方式orderCreateWay为空", "1010", "创建火车票订单失败");
        }
        Integer orderCreateWay = requestWithActor.getEntity().getOrderCreateWay();

        int orderChildStatus = 1;

        Long businessSignNo = IdWorker.getId();
        /*创建销售单*/
        SaleOrder saleOrder = saleOrderExt.getSaleOrder();
        if (null == saleOrder) {
            saleOrder = new SaleOrder();
        }
        saleOrder.setOrderType(1);
        Long saleOrderBizNo = maxNoService.generateBizNo("TRA_SALE_ORDER_NO", 15);
        saleOrderExt.setOrderNo(saleOrderBizNo);
        String reqStr = getTrainWebServiceRequest(requestWithActor.getEntity());
        BookTicketsResponse orderResponse = trainWsInteractionImpl.orderAdd(reqStr);
        if (orderResponse.getMsgCode() != null && orderResponse.getMsgCode().equals("100")) {
            saleOrder.setSaleOrderNo(saleOrderBizNo);
            saleOrder.setBusinessSignNo(businessSignNo);
            saleOrderExt.setOutOrderNo(orderResponse.getOrderNo());
            saleOrder.setBsignType(1);
            Long customerTypeNo = saleOrder.getCustomerTypeNo();
            if (customerTypeNo == null) {
                log.info("客户类型customerTypeNo为空!");
                throw new GSSException("客户类型customerTypeNo为空!", "0", "创建火车票订单失败");
            }
            if (301 != customerTypeNo && 302 != customerTypeNo && 303 != customerTypeNo && 306 != customerTypeNo) {
                log.info("客户类型customerTypeNo取值范围是301,302,303,306!");
                throw new GSSException("客户类型customerTypeNo取值范围是301,302,303,306!", "0", "创建火车票订单失败");
            }
            saleOrder.setCustomerTypeNo(customerTypeNo);
            Long customerNo = saleOrder.getCustomerNo();
            if (customerNo == null) {
                saleOrder.setCustomerNo(1L);
            }
            saleOrder.setSourceChannelNo("tra");
            Long transactionOrderNo = saleOrder.getTransationOrderNo();
            if (transactionOrderNo != null) {
                saleOrder.setTransationOrderNo(transactionOrderNo);
            } else {
                saleOrder.setTransationOrderNo(saleOrderBizNo);
            }
            saleOrder.setOrderChildStatus(orderChildStatus);
            saleOrder.setOrderingLoginName(agent.getAccount());
            saleOrder.setGoodsType(GoodsBigType.TRAIN_TICKET.getKey());
            saleOrder.setGoodsSubType(GoodsBigType.TRAIN_TICKET.getKey());
            saleOrder.setGoodsName(saleOrderExt.getTrainName());
            saleOrder.setPayStatus(1); // 待支付
            saleOrder.setValid(1);
            saleOrderExt.setSaleOrder(saleOrder);

		    /* 创建采购单 */
            BuyOrder buyOrder = new BuyOrder();
            buyOrder.setSaleOrderNo(saleOrder.getSaleOrderNo());
            Long buyOrderBizNo = maxNoService.generateBizNo("TRA_BUY_ORDER_NO", 16);
            buyOrder.setBuyOrderNo(buyOrderBizNo);
            buyOrder.setBusinessSignNo(businessSignNo);
            buyOrder.setBsignType(1);
            buyOrder.setGoodsType(GoodsBigType.TRAIN_TICKET.getKey());
            buyOrder.setGoodsSubType(GoodsBigType.TRAIN_TICKET.getKey());
            buyOrder.setGoodsName(saleOrderExt.getTrainName());
            buyOrder.setBuyChannelNo("tra");
            buyOrder.setValid(1);
            BigDecimal salePrice = saleOrderExt.getSalePrice();
            List<TraProfitControl> profitControls = traProfitControlDao.selectByTrainName("train");
            if (orderCreateWay == 2) {
                salePrice = saleOrderExt.getSalePrice();
            } else {
                for (TraProfitControl profitControl : profitControls) {
                    if (customerTypeNo.toString().equals(profitControl.getCustomerTypeNo().toString())) {
                        salePrice = salePrice.add(profitControl.getBrokerage());
                    }
                }
            }
            Supplier supplier = supplierService.getSupplierByName(agent,"12306");
            if (supplier == null) {
                log.error("火车票供应商不存在");
                throw new GSSException("火车票供应商不存在", "0101", "创建火车票订单失败");
            }
            buyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
            buyOrder.setSupplierNo(supplier.getSupplierNo());
            buyOrder.setBuyChildStatus(orderChildStatus); // 采购单子状态 未投保（1），已投保（2），已取消（3），投保异常（4），已退保（5）
            saleOrderExt.setBuyOrder(buyOrder);
            saleOrderExt.setSaleOrderNo(saleOrder.getSaleOrderNo());
            saleOrderExt.setCreateTime(new Date());
            saleOrderExt.setOwner(requestWithActor.getAgent().getOwner());
            saleOrderExt.setCreator(requestWithActor.getAgent().getAccount());
            saleOrderExt.setSaleOrderNo(saleOrder.getSaleOrderNo());
            saleOrderExt.setBuyOrderNo(buyOrder.getBuyOrderNo());
            saleOrderExt.setIssueDate(new Date());
            saleOrderExt.setValid(true);
            if (salePrice == null) {
                log.info("销售价为空");
                throw new GSSException("销售价为空", "1010", "创建火车票订单失败");
            }

            List<TraSaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
            if(saleOrderDetailList == null || saleOrderDetailList.size() == 0) {
            	log.info("销售订单详情为空");
                throw new GSSException("销售订单详情为空", "1010", "创建火车票订单失败");
            }

            BigDecimal totalPremium = saleOrderExt.getPremium();
            traSaleOrderExtDao.insertSelective(saleOrderExt);
            /**
             * 为防止生成订单异常时,仍生成了销售单和采购单
             * 故将生成销售单和采购单放在生成订单之后
             */
            saleOrderService.create(requestWithActor.getAgent(), saleOrder);
            buyOrderService.create(requestWithActor.getAgent(), buyOrder);
        /* 创建子订单 */
            if (saleOrderExt.getSaleOrderDetailList() != null && !"".equals(saleOrderExt.getSaleOrderDetailList())) {
                for (TraSaleOrderDetail saleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                    TraPassenger traPassenger = saleOrderDetail.getTraPassenger();
                    Long passengerNo = maxNoService.generateBizNo("TRA_PASSENGE_NO", 18);
                    traPassenger.setPassengerNo(passengerNo);
                    traPassengerDao.insertSelective(traPassenger);
                    // 设置子订单初始状态为1(未投保)
                    saleOrderDetail.setStatus(1);
                    if (saleOrderExt.getSaleOrderNo() == null) {
                        throw new GSSException("主订单号为空,子订单无法插入", "1010", "订单创建失败");
                    }
                    saleOrderDetail.setPassengerNo(passengerNo);
                    saleOrderDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                    Long saleOrderDetailNo = maxNoService.generateBizNo("TRA_SALEORDERDETAIL_NO", 19);
                    saleOrderDetail.setSaleOrderDetailNo(saleOrderDetailNo);
                    saleOrderDetail.setOwner(agent.getOwner());
                    saleOrderDetail.setCreateTime(new Date());
                    saleOrderDetail.setCreator(agent.getId().toString());
                    saleOrderDetail.setValid(true);
                    traSaleOrderDetailDao.insertSelective(saleOrderDetail);
                    //创建服务费应收记录
                    if (saleOrderDetail.getServiceFee() != null && saleOrderDetail.getServiceFee().doubleValue() > 0) {
                        CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
                        saleOrderPlanAmountVO.setPlanAmount(saleOrderDetail.getServiceFee());// 销售应收金额
                        saleOrderPlanAmountVO.setGoodsType(GoodsBigType.TRAIN_TICKET.getKey());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送 10 火车票
                        saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
                        saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                        saleOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                        saleOrderPlanAmountVO.setRecordMovingType(CostType.SERVICE_CHARGE.getKey());// 费用类型 1.销售 2.采购 3.票面价 4.机建费(具体由业务待定) 5.服务费
                        // 销售废退， 5 销售改签 11 采购，12
                        // 采购补单 13 补付 14 采购废退，15
                        // 采购改签
                        planAmountRecordService.create(requestWithActor.getAgent(), saleOrderPlanAmountVO);
                    }
                }
            } else {
                log.info("子订单为空");
                throw new GSSException("火车票子订单为空", "1010", "创建火车票订单失败");
            }

            // 创建销售应收记录
            CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
            if (totalPremium != null) {
                saleOrderPlanAmountVO.setPlanAmount(totalPremium);// 销售应收金额
            }
            saleOrderPlanAmountVO.setGoodsType(saleOrderExt.getSaleOrder().getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
            saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
            saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
            saleOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
            saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
            // 销售废退， 5 销售改签 11 采购，12
            // 采购补单 13 补付 14 采购废退，15
            // 采购改签
            planAmountRecordService.create(requestWithActor.getAgent(), saleOrderPlanAmountVO);

            // 创建采购应付记录
            CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
            BigDecimal buyPrice = null;
            buyPrice = saleOrderExt.getBuyPrice();
            if (buyPrice == null) {
                log.info("采购价为空");
                throw new GSSException("采购价为空", "1010", "创建火车票订单失败");
            }
//            BigDecimal buyOrderAmount = buyPrice.multiply(new BigDecimal(totalInsuredCount));
            buyOrderPlanAmountVO.setPlanAmount(totalPremium);// 采购应付金额
            buyOrderPlanAmountVO.setGoodsType(saleOrderExt.getBuyOrder().getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
            buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
            buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
            buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
            buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
            // 销售废退， 5 销售改签 11 采购，12
            // 采购补单 13 补付 14 采购废退，15
            // 采购改签
            planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
        }
        log.info("创建火车票订单结束==========");
        return saleOrderExt.getSaleOrderNo();
    }

    //根据参数，封装为铁友请求参数
    public static String getTrainWebServiceRequest(OrderCreateVo orderCreateVo) throws Exception {
        BookTicketsRequest req = new BookTicketsRequest();
        TraSaleOrderExt saleOrderExt = orderCreateVo.getSaleOrderExt();
        List<TraSaleOrderDetail> traSaleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
        TraSaleOrderDetail traSaleOrderDetail = traSaleOrderDetailList.get(0);
        req.setQueryKey(orderCreateVo.getQueryKey());
        req.setJourneyType("1");
        req.setUserAccount("");
        req.setOutOrderNo(saleOrderExt.getOrderNo() + "");
        req.setTrainNo(traSaleOrderDetail.getTrainno());
        req.setFromStation(TrainUtil.getChinese(traSaleOrderDetail.getFromstation()));
        req.setToStation(TrainUtil.getChinese(traSaleOrderDetail.getTostation()));
        req.setIsPost(0);
        req.setIsOnLine(1);//是否线上出票（0：否，1：是）
        req.setNoticeType(0);
        req.setIsProduction(PropertiesUtils.get("train.isProduct"));
        req.setTicketModel("0");
        req.setAcceptNoSeat("0");
        req.setAccountNo("");
        req.setAccountPwd("");
        ContactInfo info = new ContactInfo();
        info.setPerson(TrainUtil.getChinese(saleOrderExt.getPerson()));
        info.setCellphone(saleOrderExt.getCellphone());
        info.setEmail(StringUtils.isEmpty(saleOrderExt.getEmail()) ? "" : saleOrderExt.getEmail());
        req.setContactInfo(info);
        List<Passenger> passengers = new ArrayList<Passenger>();
        for (TraSaleOrderDetail saleOrderDetail : traSaleOrderDetailList) {
            TraPassenger trainPassenger = saleOrderDetail.getTraPassenger();
            Passenger passenger = new Passenger();
            passenger.setPassengerType(trainPassenger.getPassengerType());
            passenger.setPassengerName(TrainUtil.getChinese(trainPassenger.getName()));
            passenger.setIdType(trainPassenger.getCertType());
            passenger.setIdCard(trainPassenger.getCertNo());
            passenger.setBirthday(TrainUtil.getDay(trainPassenger.getPassengerBirth()));
            passenger.setInsureCount(0);
            passenger.setInsurePrice(0);
            passenger.setInsurNo("");
            String sex = trainPassenger.getGender();
            if (StringUtils.isBlank(sex)) {
                passenger.setSex("0");
            } else {
                passenger.setSex("0".equals(sex) ? "1" : "0");
            }
            passenger.setSeatClass(TrainUtil.getSeatClass(TrainUtil.getChinese(saleOrderDetail.getSeatclass())));
            passenger.setTicketPrice(saleOrderDetail.getBuyPrice().toString());
            passengers.add(passenger);
        }
        req.setPassengers(passengers);
        return DataConvertUtils.getInstance().toJson(req);
    }

    /**
     * 查询火车票详情
     *
     * @return
     */
    @Override
    public TraSaleOrderExt queryTraSaleOrder(RequestWithActor<Long> requestWithActor) {
        log.info("查询火车票订单开始==============");
        if (requestWithActor.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            log.error("火车票订单号为空");
            throw new GSSException("保火车票订单为空", "1010", "查询火车票失败");
        }
        TraSaleOrderExt saleOrderExt = traSaleOrderExtDao.selectByPrimaryKey(requestWithActor.getEntity());
        if (null == saleOrderExt) {
            return null;
        }
        /**
         * 关联取出销售单的数据
         */
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(), requestWithActor.getEntity());
        saleOrderExt.setSaleOrder(saleOrder);
        log.info("查询保单结束==============");
        return saleOrderExt;
    }

    @Override
    public int deleteSaleOrder(RequestWithActor<Long> id) throws Exception {
        int flag = 0;
        if (id == null) {
            log.error("SaleOrder删除异常，请选择需要删除的记录");
            throw new GSSException("删除订单模块", "0401", "id传入参数为空");
        }
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        try {
            TraSaleOrderExt se = traSaleOrderExtDao.selectByPrimaryKey(id.getEntity().longValue());
            if (se != null) {
                if (se.getValid()) {
                    se.setValid(false);
                    se.setModifier(id.getAgent().getAccount());
                    se.setModifyTime(simple.parse(simple.format(new Date())));
                }
                flag = traSaleOrderExtDao.updateByPrimaryKey(se);
                if (flag == 0) {
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

    /**
     * 查询火车票集合
     *
     * @return
     */
    @Override
    public Page<TraSaleOrderExt> querySaleOrderList(Page<TraSaleOrderExt> page, RequestWithActor<TraSaleOrderExtVo> pageRequest) {
        log.info("根据条件查询火车票开始==============");
        log.info("pageRequest = "+ JSON.toJSONString(pageRequest));
        Agent agent = pageRequest.getAgent();
        if (pageRequest.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        //只能查询自己的订单数据
        if (pageRequest.getEntity() == null) {
            /*TraSaleOrderExtVo order = new TraSaleOrderExtVo();
            order.setOwner(pageRequest.getAgent().getOwner());
            order.setCreator(pageRequest.getAgent().getAccount());
            pageRequest.setEntity(order);*/
            TraSaleOrderExtVo orderVo = new TraSaleOrderExtVo();
            orderVo.setOwner(pageRequest.getAgent().getOwner());
            orderVo.setCustomerNo(pageRequest.getAgent().getNum());
            orderVo.setCustomerTypeNo(ConvertUtils.longToInt(pageRequest.getAgent().getType()));
            pageRequest.setEntity(orderVo);
        } else {
        	List<Customer> lowerCustomers = null;
        	//如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
    		if(agent.getType() != null && agent.getType() != 0L){ //不是运营平台账号
    			
    				if(pageRequest.getEntity().isLowerLevel()==true){
    					lowerCustomers = customerService.getSubCustomerByCno(agent, agent.getNum());
    					pageRequest.getEntity().setLowerCustomers(lowerCustomers);
    				}
    		}
        	pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
        	pageRequest.getEntity().setCustomerNo(pageRequest.getAgent().getNum());
        	pageRequest.getEntity().setCustomerTypeNo(ConvertUtils.longToInt(pageRequest.getAgent().getType()));
        }
     
        /* 分页列表 */
        List<TraSaleOrderExt> tempList = traSaleOrderExtDao.queryObjByKey(page, pageRequest.getEntity());
        List<TraSaleOrderExt> saleOrderExtList = new ArrayList<TraSaleOrderExt>();
        /**
         * 根据saleorderno通过API接口去其他子系统去获取数据
         * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
         */
        for (TraSaleOrderExt order : tempList) {
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), order.getSaleOrderNo());

        	order.setStatus(order.getSaleOrderDetailList().get(0).getStatus());
        	order.setTrainNo(order.getSaleOrderDetailList().get(0).getTrainno());
        	String name = "";
        	for (TraSaleOrderDetail detail : order.getSaleOrderDetailList()) {
        		name += detail.getTraPassenger().getName()+",";
			}
//        	order.setSurname(surname);
        	order.setName(name.substring(0, name.length()-1));
        	order.setCustomerNo(saleOrder.getCustomerNo());
        	order.setCustomerTypeNo(saleOrder.getCustomerTypeNo().intValue());
            
            order.setSaleOrder(saleOrder);
            saleOrderExtList.add(order);
        }
        page.setRecords(saleOrderExtList);
        log.info("根据条件查询火车票结束==============");
        return page;
    }

    @Override
    public Integer countSaleOrderList(RequestWithActor<TraSaleOrderExtVo> pageRequest) {

        log.info("根据条件统计火车票开始==============");
        log.info("pageRequest = "+ JSON.toJSONString(pageRequest));
        if (pageRequest.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        //只能查询自己的订单数据
        if (pageRequest.getEntity() == null) {
            /*TraSaleOrderExtVo order = new TraSaleOrderExtVo();
            order.setOwner(pageRequest.getAgent().getOwner());
            order.setCreator(pageRequest.getAgent().getAccount());
            pageRequest.setEntity(order);*/
            TraSaleOrderExtVo orderVo = new TraSaleOrderExtVo();
            orderVo.setOwner(pageRequest.getAgent().getOwner());
            orderVo.setCustomerNo(pageRequest.getAgent().getNum());
            orderVo.setCustomerTypeNo(ConvertUtils.longToInt(pageRequest.getAgent().getType()));
            pageRequest.setEntity(orderVo);
        } else {
        	pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
        	pageRequest.getEntity().setCustomerNo(pageRequest.getAgent().getNum());
        	pageRequest.getEntity().setCustomerTypeNo(ConvertUtils.longToInt(pageRequest.getAgent().getType()));
        }
        
        /* 分页列表 */
        List<TraSaleOrderExt> tempList = traSaleOrderExtDao.queryObjByKey(null, pageRequest.getEntity());

        log.info("根据条件统计火车票结束==============");
        return tempList.size();
    }

    /**
     * 更新详情状态
     *
     * @return
     */
    @Override
    public int updateStatusDetail(RequestWithActor<TraSaleOrderDetail> requestWithActor) {
        return traSaleOrderDetailDao.updateByPrimaryKey(requestWithActor.getEntity());
    }

    /**
     * 取消火车票
     *
     * @return
     */
    @Override
    @Transactional
    public boolean cancelSaleOrderExt(RequestWithActor<Long> requestWithActor) throws Exception {
        TraSaleOrderExt saleOrderExt = traSaleOrderExtDao.selectByPrimaryKey(requestWithActor.getEntity());
        CyOrderResponse cyOrderResponse = trainWsInteractionService.cancelOrder(saleOrderExt.getOutOrderNo());
        log.info("同程接口返回："+cyOrderResponse+"=============");
        if ("100".equals(cyOrderResponse.getMsgCode())) {
            for (TraSaleOrderDetail traSaleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                traSaleOrderDetail.setStatus(8);
                traSaleOrderDetail.setModifier(requestWithActor.getAgent().getAccount());
                traSaleOrderDetail.setModifyTime(new Date());
                traSaleOrderDetailDao.updateByPrimaryKeySelective(traSaleOrderDetail);
            }
            saleOrderService.updateStatus(requestWithActor.getAgent(), saleOrderExt.getSaleOrderNo(), 8);
            return true;
        }
        return false;

    }

    /**
     * 申请火车票出票
     *
     * @return
     */

    @Override
    public boolean buyTrain(RequestWithActor<Long> requestWithActor) throws Exception {
        log.info("订单号："+requestWithActor.getEntity()+",申请出票");
        TraSaleOrderExt saleOrderExt = traSaleOrderExtDao.selectByPrimaryKey(requestWithActor.getEntity());
        log.info("订单号："+saleOrderExt.getOutOrderNo());
        CyOrderResponse cyOrderResponse = trainWsInteractionService.applyOrder(saleOrderExt.getOutOrderNo());
        if ("100".equals(cyOrderResponse.getMsgCode())) {
            saleOrderService.updateStatus(requestWithActor.getAgent(),saleOrderExt.getSaleOrderNo(),3);//将状态修改为出票中
            return true;
        } else {
            log.info(cyOrderResponse.getMsgInfo());
            throw new GSSException(cyOrderResponse.getMsgInfo(), cyOrderResponse.getMsgCode(), "申请出票失败");
        }

    }

    /**
     * 申请退票火车票
     *
     * @return
     */
    @Override
    public boolean refundSaleOrderExt(RequestWithActor<TraSaleOrderExt> requestWithActor) {
        TraSaleOrderExt saleOrderExt = requestWithActor.getEntity();
        log.info("退火车票开始==============");
        try {
        	if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
                log.error("订单号为空");
                throw new GSSException("订单号为空", "1010", "退火车票失败");
            }
            Agent agent = requestWithActor.getAgent();
            Long saleOrderNo = null;
            if (saleOrderExt == null) {
                log.info("查询订单结果为空!");
                throw new GSSException("查询订单结果为空!", "1010", "退火车票失败");
            }
            OrderReturnReq request = new OrderReturnReq();
            CyOrderResponse orderResponse = null;
            for (TraSaleOrderDetail traSaleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                request.setPassengerId(traSaleOrderDetail.getOutPassengerNo());
                request.setOrderNumber(saleOrderExt.getOutOrderNo());
                orderResponse = trainWsInteractionService.orderReturn(request);
            }
            
            if ("100".equals(orderResponse.getMsgCode())) {
                Long businessSignNo = IdWorker.getId();
                Long saleChangeNo = maxNoService.generateBizNo("TRA_SALE_CHANGE_EXT_NO", 42);

                saleOrderNo = saleOrderExt.getSaleOrderNo();
                if (saleOrderNo == null) {
                    log.error("saleOrderNo为空");
                    throw new GSSException("saleOrderNo为空", "1010", "退火车票失败");
                }
                SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
                if (saleOrder == null) {
                    log.error("根据saleOrderNo查询saleOrder为空");
                    throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退火车票失败");
                }

                /* 创建采购变更单 */
                BuyChange buyChange = new BuyChange();
                buyChange.setBuyChangeNo(maxNoService.generateBizNo("TRA_BUY_CHANGE_NO", 44));
                buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
                buyChange.setBusinessSignNo(businessSignNo);
                buyChange.setBsignType(4);
                buyChange.setOwner(agent.getOwner());
                buyChange.setCreateTime(new Date());
                buyChange.setChildStatus(7);
                buyChange.setGoodsType(10);//商品大类 10 火车票
                buyChange.setGoodsSubType(0);//TODO 没有小类为0
                buyChange.setGoodsName(saleOrderExt.getTrainName());//TODO
                buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
                buyChange.setValid(1);
                if (saleOrderExt.getBuyOrderNo() != null) {
                    buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
                } else {
                    log.error("采购单编号buyOrderNo为空");
                    throw new GSSException("采购单编号buyOrderNo为空", "1010", "退火车票失败");
                }
                buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
                if (buyChange == null) {
                    log.error("创建采购变更单失败");
                    throw new GSSException("创建采购变更单失败", "1010", "退火车票失败");
                }

                /* 创建销售变更单 */
                SaleChange saleChange = new SaleChange();
                saleChange.setSaleChangeNo(saleChangeNo);
                saleChange.setSaleOrderNo(saleOrderNo);
                saleChange.setOrderChangeType(2);// 变更类型 1废 2退 3改
                saleChange.setBusinessSignNo(businessSignNo);
                saleChange.setBsignType(3);// 1销采 2换票 3废和退 4改签
                saleChange.setOwner(agent.getOwner());
                saleChange.setCreateTime(new Date());
                saleChange.setChildStatus(7);
                saleChange.setGoodsType(10);// 商品大类 10 火车票
                saleChange.setGoodsSubType(0);//TODO 没有小类为0
                saleChange.setGoodsName(saleOrderExt.getTrainName());//TODO
                saleChange.setValid(1);
                // 设置交易单号
                Long transationOrderNo = saleOrder.getTransationOrderNo();
                if (transationOrderNo == null) {
                    log.error("transationOrderNo为空");
                    throw new GSSException("transationOrderNo为空", "1010", "退火车票失败");
                }
                saleChange.setTransationOrderNo(transationOrderNo);//交易号
                saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
                saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
                if (saleChange == null) {
                    log.error("创建销售变更单失败");
                    throw new GSSException("创建销售变更单失败", "1010", "退火车票失败");
                }
                // 创建销售变更明细
                for (TraSaleOrderDetail traSaleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                    TraSaleChangeDetail saleChangeDetail = new TraSaleChangeDetail();
                    saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("TRA_SALE_CHANGE_DETAIL_NO", 43));
                    saleChangeDetail.setSaleChangeNo(saleChangeNo);
                    saleChangeDetail.setStatus("7");
                    saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
                    saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                    saleChangeDetail.setSaleOrderDetailNo(traSaleOrderDetail.getSaleOrderDetailNo());
                    saleChangeDetail.setOwner(agent.getOwner());
                    saleChangeDetail.setCreator(agent.getAccount());
                    saleChangeDetail.setCreateTime(new Date());
                    saleChangeDetail.setValid((byte) 1);
                    int saleChangeDetailNum = traSaleChangeDetailDao.insertSelective(saleChangeDetail);
                    if (saleChangeDetailNum == 0) {
                        log.error("创建销售变更明细失败");
                        throw new GSSException("创建销售变更明细失败", "1010", "退火车票失败");
                    }
                }
                // 创建火车票变更扩展单
                TraSaleChangeExt saleChangeExt = new TraSaleChangeExt();
                /* 设置销售单拓展编号 */
                saleChangeExt.setSaleOrderNo(saleOrderNo);
                saleChangeExt.setSaleChangeNo(saleChangeNo);
                saleChangeExt.setBuyChangeNo(buyChange.getBuyChangeNo());
                saleChangeExt.setOwner(agent.getOwner());
                saleChangeExt.setRefundWay(1);
                saleChangeExt.setChangeType(2);
                saleChangeExt.setModifier(agent.getAccount());
                saleChangeExt.setModifyTime(new Date());
                saleChangeExt.setStatus("1");
                saleChangeExt.setCreateTime(new Date());
                saleChangeExt.setCreator(agent.getAccount());
                saleChangeExt.setValid(new Byte("1"));
                int saleChangeExtNum = traSaleChangeExtDao.insertSelective(saleChangeExt);
                if (saleChangeExtNum == 0) {
                    log.error("创建火车票变更扩展单失败");
                    throw new GSSException("创建火车票变更扩展单失败", "1010", "退火车票失败");
                }
                Long customerTypeNo = saleOrder.getCustomerTypeNo();
                if (customerTypeNo == null) {
                    log.info("客户类型customerTypeNo为空!");
                    throw new GSSException("客户类型customerTypeNo为空!", "0", "退火车票失败");
                }
                // 创建销售应付记录
                CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
                BigDecimal salePrice = null;
                for (TraSaleOrderDetail traSaleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
                    if (salePrice == null) {
                        salePrice = traSaleOrderDetail.getSalePrice();
                    } else {
                        salePrice = salePrice.add(traSaleOrderDetail.getSalePrice());
                    }
                }
                if (salePrice == null) {
                    log.info("订单销售价salePrice为空");
                    throw new GSSException("订单销售价salePrice为空", "1010", "退火车票失败");
                }
                saleOrderPlanAmountVO.setPlanAmount(salePrice);
                saleOrderPlanAmountVO.setGoodsType(saleOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
                saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                saleOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
                saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
                // 销售废退， 5 销售改签 11 采购，12
                // 采购补单 13 补付 14 采购废退，15
                // 采购改签
                PlanAmountRecord planAmountRecord = planAmountRecordService.create(requestWithActor.getAgent(), saleOrderPlanAmountVO);
                if (planAmountRecord == null) {
                    log.info("创建销售应付记录失败");
                    throw new GSSException("创建销售应付记录失败", "1010", "退火车票失败");
                }
                // 创建采购应收记录
                Long buyOrderNo = saleOrderExt.getBuyOrderNo();
                if (buyOrderNo == null) {
                    log.error("buyOrderNo为空");
                    throw new GSSException("buyOrderNo为空", "1010", "退火车票失败");
                }
                BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
                if (buyOrder == null) {
                    log.error("根据buyOrderNo查询buyOrder为空");
                    throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退火车票失败");
                }
                CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
                BigDecimal buyPrice = saleOrderExt.getBuyPrice();
                if (buyPrice == null) {
                    log.info("火车票采购价buyPrice为空");
                    throw new GSSException("火车票采购价buyPrice为空", "1010", "退火车票失败");
                }
                buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
                buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
                buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                buyOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
                // 销售废退， 5 销售改签 11 采购，12
                // 采购补单 13 补付 14 采购废退，15
                // 采购改签
                PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
                if (planAmountRecord1 == null) {
                    log.info("创建采购应收记录失败");
                    throw new GSSException("创建采购应收记录失败", "1010", "退火车票失败");
                }
                
                //主订单状态改为退票中
    			RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
    			requestTraSaleOrderDetail.setAgent(agent);
    			for(TraSaleOrderDetail traSaleOrderDetail :saleOrderExt.getSaleOrderDetailList()){
    				traSaleOrderDetail.setStatus(5);
    				requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
    				updateStatusDetail(requestTraSaleOrderDetail);
    			}
    			saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5);
            } else {
                log.error("调用调用火车票接口出错");
                throw new GSSException("调用调用火车票接口出错", "1010", "退火车票失败");
            }
        } catch (Exception e) {
            log.error("调用退火车票接口出错："+e.getMessage());
            e.printStackTrace();
            return false;
        }

        log.info("退火车票结束==============");
        return true;
    }

    /**
     *更新退票火车票
     *
     * @return
     */
    @Override
    public boolean updateRefund(RequestWithActor<Long> requestWithActor, RefundTicketVo refundTicketVo) {
        TraSaleOrderExt saleOrderExt = traSaleOrderExtDao.selectByPrimaryKey(requestWithActor.getEntity());
        if ("100".equals(refundTicketVo.getMsgCode())) {
            saleOrderService.updateStatus(requestWithActor.getAgent(), saleOrderExt.getSaleOrderNo(), 4); //已退保
            buyOrderService.updateStatus(requestWithActor.getAgent(), saleOrderExt.getBuyOrderNo(), 5); //已退保
            // 设置子订单状态为3(已退保)
            List<TraSaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
            if (saleOrderDetails == null || saleOrderDetails.size() > 1 || saleOrderDetails.size() == 0) {
                log.error("根据保单号查询子订单错误!");
                throw new GSSException("根据订单号查询子订单错误!", "1010", "退火车票失败");
            }
            TraSaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);
            saleOrderDetail.setStatus(3);
            traSaleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
            // 创建销售退款单
            this.saleRefund(requestWithActor.getAgent(), requestWithActor.getEntity());
        } else {
            // 退保失败
            log.error(refundTicketVo.getMsgInfo());
            throw new GSSException(refundTicketVo.getMsgInfo(), "1010", "退火车票失败");
        }
        return true;
    }


    /**
     * 创建销售退款单
     *
     * @return
     */
    private boolean saleRefund(Agent agent, Long saleOrderNo) throws GSSException {

        log.info("退火车票时创建销售退款单开始-------------");
        if (agent == null) {
            log.error("agent 为空");
            throw new GSSException("agent 为空", "1001", "创建销售退款单失败");
        }
        if (saleOrderNo == null) {
            log.error("saleOrderNo 为空");
            throw new GSSException("saleOrderNo 为空", "1001", "创建销售退款单失败");
        }
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
        //当支付状态为已支付（1）时创建销售退款单
        if (saleOrder != null) {
            if (saleOrder.getPayStatus() == 1) {
                try {
                    CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                    certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
                    //		certificateCreateVO.setPayType(payType); //支付类型（1 在线支付 2 帐期或代付 3 线下支付）
                    certificateCreateVO.setReason("销售退款单信息"); //补充说明
                    certificateCreateVO.setSubBusinessType(3); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
                    List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
                    BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
                    businessOrderInfo.setActualAmount(saleOrder.getReceived());
                    certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
                    certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
                    businessOrderInfo.setBusinessType(2);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                    businessOrderInfo.setRecordNo(saleOrderNo);
                    orderInfoList.add(businessOrderInfo);
                    certificateCreateVO.setOrderInfoList(orderInfoList);
                    log.info("创建销售退款单的参数certificateCreateVO---------->" + certificateCreateVO.toString());
                    certificateService.saleRefundCert(agent, certificateCreateVO);
                } catch (Exception e) {
                    log.error("创建销售退款单失败," + e);
                    throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
                }
            }
        }

        log.info("退火车票时创建销售退款单结束-------------");
        return true;
    }
    /**
     *查询火车票订单信息
     *
     * @return
     */
    public TraSaleOrderExt queryByBuyOrderNo(RequestWithActor<Long> buyOrderNo) {
        return traSaleOrderExtDao.queryByBuyOrderNo(buyOrderNo.getEntity());
    }

    /**
     * 通过条件查询查询火车票退票单
     */
    public Page<TraSaleChangeExt> querySaleChangeList(Page<TraSaleChangeExt> page, RequestWithActor<TraSaleChangeExtVo> pageRequest) {
        log.info("根据条件查询火车票开始==============");
        if (pageRequest.getAgent() == null) {
            log.error("当前操作用户不能为空");
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        try {
            //只能查询自己的订单数据
            if (pageRequest.getEntity() == null) {
                /*TraSaleOrderExtVo order = new TraSaleOrderExtVo();
                order.setOwner(pageRequest.getAgent().getOwner());
                order.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(order);*/
            	TraSaleChangeExtVo orderVo = new TraSaleChangeExtVo();
                orderVo.setOwner(pageRequest.getAgent().getOwner());
                orderVo.setCustomerNo(pageRequest.getAgent().getNum());
                orderVo.setCustomerTypeNo(pageRequest.getAgent().getType());
                pageRequest.setEntity(orderVo);
            } else {
            	pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
            	pageRequest.getEntity().setCustomerNo(pageRequest.getAgent().getNum());
            	pageRequest.getEntity().setCustomerTypeNo(pageRequest.getAgent().getType());
            }
    		/* 分页列表 */
            List<TraSaleChangeExt> tempList = traSaleChangeExtDao.queryObjByKey(page, pageRequest.getEntity());
            List<TraSaleChangeExt> saleOrderExtList = new ArrayList<TraSaleChangeExt>();
            /**
             * 根据saleorderno通过API接口去其他子系统去获取数据
             * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
             */
            for (TraSaleChangeExt order : tempList) {
                SaleChange saleChange = saleChangeService.getSaleChangeByNo(pageRequest.getAgent(), order.getSaleChangeNo());
                order.setSaleChange(saleChange);
                saleOrderExtList.add(order);
            }
            page.setRecords(saleOrderExtList);
            log.info("根据条件查询火车票结束==============");
        } catch (Exception e) {
            log.error("查询出错！" + e);
        }

        return page;
    }

    /**
     * 根据退票编号查询订单
     *
     * @param requestWithActor
     * @return
     */
    public TraSaleChangeExt querySaleChange(RequestWithActor<Long> requestWithActor) {
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            throw new GSSException("传入单号为空", "1010", "传入单号为空");
        }
        TraSaleChangeExt ext = traSaleChangeExtDao.selectByPrimaryKey(requestWithActor.getEntity());
        SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), requestWithActor.getEntity());
        ext.setSaleChange(saleChange);
        return ext;
    }
    
    /**
     * 根据销售单号获取所有变更单（包括变更单详情）
     *
     * @param requestWithActor
     * @return
     */
    public List<TraSaleChangeExt> querySaleChangeListBySaleOrderNo(RequestWithActor<Long> requestWithActor) {
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
        }
        if (requestWithActor.getEntity() == null) {
            throw new GSSException("传入单号为空", "1010", "传入单号为空");
        }
        TraSaleChangeExtVo order = new TraSaleChangeExtVo();
        order.setOwner(requestWithActor.getAgent().getOwner());
        order.setCustomerNo(requestWithActor.getAgent().getNum());
        order.setCustomerTypeNo(requestWithActor.getAgent().getType());
        order.setSaleOrderNo(requestWithActor.getEntity());
        
        List<TraSaleChangeExt> extList = traSaleChangeExtDao.queryObjByEntity(order);
        
        for (TraSaleChangeExt ext : extList) {
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), ext.getSaleChangeNo());
            ext.setSaleChange(saleChange);
        }
        return extList;
    }
    
    /**
     *查询火车票变更信息
     *
     * @return
     */
    public TraSaleChangeExt queryByBuyChangeNo(RequestWithActor<Long> buyChagneNo) {
        return traSaleChangeExtDao.queryByBuyChangeNo(buyChagneNo.getEntity());
    }
    /**
     *查询火车票详情
     *
     * @return
     */
    public TraSaleOrderDetail getSaleOrderDetail(RequestWithActor<Long> saleOrderDetailNo) {
        return traSaleOrderDetailDao.selectByPrimaryKey(saleOrderDetailNo.getEntity());
    }
    
    /**
     * 占座结果推送
     */
    @Override
	public boolean bookSeat(Agent agent,String method, String param, String sign) {
		try {
			if(agent == null){
				log.info("agent为空！");
				return false;
			}
			Boolean flag = true; // 乘客姓名是否ok
			BookSeatRequest request = DataConvertUtils.getInstance().fromJson(param, BookSeatRequest.class);
			if (StringUtils.isBlank(request.getOrderNo())) {
				log.info("接口orderNo为空!");
				return false;
			}
			if (StringUtils.isBlank(request.getOutOrderNo())) {
				log.info("接口outOrderNo为空!");
				return false;
			}
			if (StringUtils.isBlank(request.getMsgCode())) {
				log.info("接口msgCode为空!");
				return false;
			}
			RequestWithActor<Long> requestWithActor =new RequestWithActor<Long>();
			requestWithActor.setEntity(Long.parseLong(request.getOutOrderNo()));
			requestWithActor.setAgent(agent);
			TraSaleOrderExt traSaleOrderExt = queryTraSaleOrder(requestWithActor);
			List<TraSaleOrderDetail>  traSaleOrderDetails = traSaleOrderExt.getSaleOrderDetailList();
			log.info("订座状态值code######" + request.getMsgCode());
			if (request.getMsgCode().equals("100")) {
				if (null == request.getPassengers() || request.getPassengers().size() <= 0) {
					log.info("接口乘客列表为空!");
					return false;
				}
				for (PassengerVo p : request.getPassengers()) {
					if (StringUtils.isBlank(p.getPassengerName())) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					log.info("接口PassengerName乘客姓名不能存在空值!");
					return false;
				}

				RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
				requestTraSaleOrderDetail.setAgent(agent);
				for (PassengerVo p : request.getPassengers()) {
					for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderDetails){
						if(p.getCardNo().equals(traSaleOrderDetail.getTraPassenger().getCertNo())){
							traSaleOrderDetail.setOutPassengerNo(p.getPassengerId());
							traSaleOrderDetail.setSeatNo(p.getSeatNo());
							traSaleOrderDetail.setServiceFee(new BigDecimal(p.getServiceCharge()));
							traSaleOrderDetail.setStatus(2);
							requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
							updateStatusDetail(requestTraSaleOrderDetail);
						}
					}
				}
				saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), 2);
			} else {
				RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
				requestTraSaleOrderDetail.setAgent(agent);
				for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderDetails){
					traSaleOrderDetail.setStatus(10);
					requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
					updateStatusDetail(requestTraSaleOrderDetail);
				}
				saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), 10);
				//占座失败 记录失败原因
				TraSaleOrderExt updateExt = new TraSaleOrderExt();
				updateExt.setSaleOrderNo(Long.parseLong(request.getOutOrderNo()));
				
//				updateExt.setRemark(paramJsonObject.getString("msgInfo"));
				updateExt.setRemark(request.getMsgInfo());
				
				traSaleOrderExtDao.updateByPrimaryKeySelective(updateExt);
			}
		} catch (Exception e) {
			log.info("===========占座结果通知异常=========="+e.getMessage());
			return false;
		}
		return true;
	}
    
    /**
     * 出票结果推送
     */
	@Override
	public boolean issueOrder(Agent agent, String method, String param, String sign) {
		try {
			if(agent == null){
				log.info("agent为空！");
				return false;
			}
			int orderState = 0; // 订单状态ORDER_STATE
			IsSueOrderVo request = DataConvertUtils.getInstance().fromJson(param, IsSueOrderVo.class);
			if (StringUtils.isBlank(request.getOutOrderNo())) {
				log.info("接口orderNo为空!");
				return false;
			}
			String code = request.getMsgCode();// 状态码
			if (StringUtils.isBlank(code)) {
				log.info("接口msgCode为空!");
				return false;
			}
			if (code.equals("100")) {
				orderState = 4;
			} else {
				orderState = 11;
				log.info("出票失败!" + request.getMsgInfo());
			}
			RequestWithActor<Long> requestWithActor =new RequestWithActor<Long>();
			requestWithActor.setAgent(agent);
			requestWithActor.setEntity(Long.parseLong(request.getOutOrderNo()));
			TraSaleOrderExt traSaleOrderExt = queryTraSaleOrder(requestWithActor);
			RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
			requestTraSaleOrderDetail.setAgent(agent);
			for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderExt.getSaleOrderDetailList()){
				traSaleOrderDetail.setStatus(orderState);
				requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
				updateStatusDetail(requestTraSaleOrderDetail);
			}
			saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), orderState);
		} catch (Exception e) {
			log.info("===========出票结果通知异常==========" + e.getMessage());
			return false;
		}
		return true;
	}

    /**
     * 订单过期推送
     */
	@Override
	public boolean notifyOvertimeOrder(Agent agent, String method, String param, String sign) {
		try {
			if(agent == null){
				log.info("agent为空！");
				return false;
			}
			NotifyOvertimeOrderVo request = DataConvertUtils.getInstance().fromJson(param, NotifyOvertimeOrderVo.class);
			if (StringUtils.isBlank(request.getOutOrderNo())) {
				log.info("接口orderNo为空!");
				return false;
			}
			if (StringUtils.isBlank(request.getMsgCode())) {
				log.info("接口msgCode为空!");
				return false;
			}
			RequestWithActor<Long> requestWithActor =new RequestWithActor<Long>();
			requestWithActor.setAgent(agent);
			requestWithActor.setEntity(Long.parseLong(request.getOutOrderNo()));
			TraSaleOrderExt traSaleOrderExt = queryTraSaleOrder(requestWithActor);
			RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
			requestTraSaleOrderDetail.setAgent(agent);
			for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderExt.getSaleOrderDetailList()){
				traSaleOrderDetail.setStatus(8);
				requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
				updateStatusDetail(requestTraSaleOrderDetail);
			}
			saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), 8);
			//订单过期 记录原因
			TraSaleOrderExt updateExt = new TraSaleOrderExt();
			updateExt.setSaleOrderNo(Long.parseLong(request.getOutOrderNo()));
			updateExt.setRemark(request.getMsgCode() +":"+ request.getMsgInfo());
			traSaleOrderExtDao.updateByPrimaryKeySelective(updateExt);
		} catch (Exception e) {
			log.info("===========订单过期推送结果通知异常==========" + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
     * 退票退款结果推送
     */
	@Override
	public boolean refundTicket(Agent agent, String method, String param, String sign) {
		try {
			if(agent == null){
				log.info("agent为空！");
				return false;
			}
			Boolean flag = true; // 乘客姓名是否ok
			RefundTicketVo request = DataConvertUtils.getInstance().fromJson(param, RefundTicketVo.class);
			if (StringUtils.isBlank(request.getOutOrderNo())) {
				log.info("接口orderNo为空!");
				return false;
			}
			if (StringUtils.isBlank(request.getMsgCode())) {
				log.info("接口msgCode为空!");
				return false;
			}
			RequestWithActor<Long> requestWithActor =new RequestWithActor<Long>();
			requestWithActor.setAgent(agent);
			requestWithActor.setEntity(Long.parseLong(request.getOutOrderNo()));
			TraSaleOrderExt traSaleOrderExt = queryTraSaleOrder(requestWithActor);
			List<TraSaleOrderDetail>  traSaleOrderDetails = traSaleOrderExt.getSaleOrderDetailList();
			log.info("订座状态值code######" + request.getMsgCode());
			if (request.getMsgCode().equals("100")) {
				if (null == request.getPassengers() || request.getPassengers().size() <= 0) {
					log.info("接口乘客列表为空!");
					return false;
				}
				for (PassengerVo p : request.getPassengers()) {
					if (StringUtils.isBlank(p.getPassengerName())) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					log.info("接口PassengerName乘客姓名不能存在空值!");
					return false;
				}

				RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
				requestTraSaleOrderDetail.setAgent(agent);
				for (PassengerVo p : request.getPassengers()) {
					for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderDetails){
						if(p.getCardNo().equals(traSaleOrderDetail.getTraPassenger().getCertNo())){
							traSaleOrderDetail.setOutPassengerNo(p.getPassengerId());
							traSaleOrderDetail.setSeatNo(p.getSeatNo());
							traSaleOrderDetail.setServiceFee(new BigDecimal(p.getServiceCharge()));
							traSaleOrderDetail.setStatus(7);
							requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
							updateStatusDetail(requestTraSaleOrderDetail);
						}
					}
				}
				saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), 7);
			} else {
				//没有退票申请失败的状态，暂时不做订单状态修改，只记录失败原因
				/*RequestWithActor<TraSaleOrderDetail> requestTraSaleOrderDetail=new RequestWithActor<TraSaleOrderDetail>();
				requestTraSaleOrderDetail.setAgent(agent);
				for(TraSaleOrderDetail traSaleOrderDetail :traSaleOrderDetails){
					traSaleOrderDetail.setStatus(5);
					requestTraSaleOrderDetail.setEntity(traSaleOrderDetail);
					updateStatusDetail(requestTraSaleOrderDetail);
				}
				saleOrderService.updateStatus(agent, traSaleOrderExt.getSaleOrderNo(), 5);*/
				//退票失败 记录失败原因
				TraSaleOrderExt updateExt = new TraSaleOrderExt();
				updateExt.setSaleOrderNo(Long.parseLong(request.getOutOrderNo()));
				updateExt.setRemark("退票申请失败："+request.getMsgCode()+"-->"+request.getMsgInfo());
				traSaleOrderExtDao.updateByPrimaryKeySelective(updateExt);
			}
		} catch (Exception e) {
			log.info("===========订单退票退款结果推送通知异常==========" + e.getMessage());
			return false;
		}
		return true;
	}
}

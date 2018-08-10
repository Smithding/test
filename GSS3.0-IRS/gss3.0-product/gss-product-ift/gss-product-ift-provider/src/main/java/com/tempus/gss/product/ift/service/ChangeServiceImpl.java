package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mss.service.IMssReserveService;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.CostType;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.CreatePlanAmountVO;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.entity.vo.*;
import com.tempus.gss.product.ift.api.service.*;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.product.ift.dao.*;
import com.tempus.gss.product.ift.help.IftLogHelper;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class ChangeServiceImpl implements IChangeService {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Reference
    private IPassengerChangePriceService pgerChangePriceService;
    @Reference
    ISaleChangeService saleChangeService;
    @Reference
    private IConfigsService configsService;
    @Reference
    IBuyChangeService buyChangeService;
    @Reference
    IBuyOrderService buyOrderService;
    /*销售单*/
    @Reference
    ISaleOrderService saleOrderService;
    @Reference
    IMaxNoService maxNoService;
    @Reference
    IBuyOrderExtService buyOrderExtService;
    /*应收应付*/
    @Reference
    IPlanAmountRecordService planAmountRecordService;
    @Reference
    ILogService logService;
    @Reference
    IChangeService changeExtService;
    @Reference
    ICustomerService customerService;
    @Reference
    IUserService userService;
    @Reference
    ISaleOrderExtService saleOrderExtService;
    @Reference
    ISaleOrderDetailService saleOrderDetailService;
    @Autowired
    SaleOrderDetailDao saleOrderDetailDao;
    @Autowired
    LegDao legDao;
    @Autowired
    PassengerChangePriceDao passengerChangePriceDao;
    @Autowired
    SaleChangeExtDao saleChangeExtDao;
    @Autowired
    PnrDao pnrDao;
    @Autowired
    BuyOrderDetailDao buyOrderDetailDao;
    @Autowired
    BuyOrderExtDao buyOrderExtDao;
    @Autowired
    BuyChangeExtDao buyChangeExtDao;
    @Autowired
    SaleOrderExtDao saleOrderExtDao;
    @Autowired
    SaleChangeDetailDao saleChangeDetailDao;
    @Reference
    IMssReserveService mssReserveService;
    @Autowired
    TicketSenderDao ticketSenderDao;
    @Reference
    ITicketSenderService ticketSenderService;
    @Reference
    ICertificateService certificateService;
    @Reference
    IIftMessageService iftMessageService;
    @Reference
    ITicketSenderService iTicketSenderService;
    @Reference
    IftBuyChangeExtService buyChangeExtService;
    @Value("${dpsconfig.job.owner}")
    protected String owner;

    @Override
    @Transactional
    public SaleChangeExt apiCreateChange(RequestWithActor<ChangeCreateVo> requestWithActor) {
        log.info("申请改签单开始=========");
        if (requestWithActor.getEntity().getSaleOrderNo() == null || requestWithActor.getEntity().getSaleOrderNo() == 0) {
            log.error("需要改签的销售单编号为空");
            throw new GSSException("需要改签的销售单编号为空", "0001", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getOldPassengerNoList() == null || requestWithActor.getEntity().getOldLegNoList() == null) {
            log.error("需要改签的乘客或者航程编号为空");
            throw new GSSException("需要改签的乘客或者航程编号为空", "0002", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getOldLegNoList() == null || requestWithActor.getEntity().getOldLegNoList().size() == 0) {
            log.error("改签的旧航程为空");
            throw new GSSException("改签的旧航程为空", "0003", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getLegList() == null || requestWithActor.getEntity().getLegList().size() == 0) {
            log.error("需要改签的航程为空");
            throw new GSSException("需要改签的航程为空", "0003", "创建改签单失败");
        }
        SaleOrderExt saleOrderExt = saleOrderExtDao.selectByPrimaryKey(requestWithActor.getEntity().getSaleOrderNo());
        if (saleOrderExt == null) {
            log.error("销售单为空");
            throw new GSSException("销售单为空", "0004", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getContactName() == null) {
            log.error("需要改签的联系人名称为空");
            throw new GSSException("改签的联系人名称名称为空（ContactName）", "0006", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getContactPhone() == null) {
            log.error("需要改签的联系人手机号名称为空");
            throw new GSSException("改签的联系人手机号名称为空（ContactPhone）", "0007", "创建改签单失败");
        }
        if (requestWithActor.getEntity().getChangeReason() == null || requestWithActor.getEntity().getChangeReason() == "") {
            log.error("改签原因为空");
            throw new GSSException("改签原因为空", "0008", "创建改签单失败");
        }
        SaleChangeExt saleChangeExt = new SaleChangeExt();
        try {
            Date createTime = new Date();
            Long businessSignNo = IdWorker.getId();
            Agent agent = requestWithActor.getAgent();
            Long saleChangeNo = maxNoService.generateBizNo("IFT_SALE_CHANGE_EXT_NO", 38);
            Long buyChangeNo = maxNoService.generateBizNo("IFT_BUY_CHANGE_NO", 47);
            List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();

            //查询出所有的销售变更单对应的生成的saleOrderDetail全部添加到saleOrderDetailList中
            List<SaleChange> saleChangeList = saleChangeService.getSaleChangesBySONo(requestWithActor.getAgent(), saleOrderExt.getSaleOrderNo());
            List<Long> saleChangeNoList = new ArrayList<>();
            for (SaleChange saleChange : saleChangeList) {
                if(saleChange.getOrderChangeType() == 3){
                    saleChangeNoList.add(saleChange.getSaleChangeNo());
                }
            }
            if(saleChangeNoList != null&& saleChangeNoList.size()>0 ){
                List<SaleOrderDetail> saleOrderDetails = saleOrderDetailService.selectBySaleChangeNoList(saleChangeNoList);
                if(saleOrderDetails!=null && saleOrderDetails.size()>0){
                    saleOrderDetailList.addAll(saleOrderDetails);
                }
            }

            /*修改需要改签的人+航段信息*/
            for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
                for (Long oldPassengerNo : requestWithActor.getEntity().getOldPassengerNoList()) {
                    if (saleOrderDetail.getPassengerNo().equals(oldPassengerNo)) {

                        for (Long oldLegNo : requestWithActor.getEntity().getOldLegNoList()) {
                            if (saleOrderDetail.getLegNo().equals(oldLegNo)) {
                                if (!"4".equals(saleOrderDetail.getStatus())) {
                                    log.error("该行程不是已出票状态，无法申请改签");
                                    throw new GSSException("该行程不是已出票状态，无法申请改签", "0008", "创建改签单失败");
                                }
                                if ("7".equals(saleOrderDetail.getStatus()) || "10".equals(saleOrderDetail.getStatus())) {
                                    log.error("该行程已申请改签，无法重新申请");
                                    throw new GSSException("该行程已申请改签，无法重新申请", "0008", "创建改签单失败");
                                } else {
                                    saleOrderDetail.setStatus("10");//TODO 将状态修改为改签状态
                                    saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);

                                    //添加旧改签单明细
                                    SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
                                    saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("IFT_SALE_CHANGE_DETAIL_NO", 49));
                                    saleChangeDetail.setSaleChangeNo(saleChangeNo);
                                    saleChangeDetail.setOwner(agent.getOwner());
                                    saleChangeDetail.setBuyChangeNo(buyChangeNo);
                                    saleChangeDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
                                    saleChangeDetail.setCreateTime(createTime);
                                    saleChangeDetail.setCreator(agent.getAccount());
                                    saleChangeDetail.setValid((byte) 1);
                                    saleChangeDetail.setOrderDetailType(1);
                                    saleChangeDetailDao.insertSelective(saleChangeDetail);
                                }
                            }
                        }
                    }
                }
            }

            //            /*修改改签的航程信息*/
            //			for (Leg leg : saleOrderExt.getLegList()) {
            //				for (Long oldLegNo : requestWithActor.getEntity().getOldLegNoList()) {
            //					if (leg.getLegNo().equals(oldLegNo)) {
            //						leg.setStatus("2");//TODO 修改航程状态为改签状态
            //						legDao.updateByPrimaryKeySelective(leg);
            //					}
            //				}
            //			}

            /*新增改签航段*/
            int legLength = requestWithActor.getEntity().getLegList().size();
            for (int i = 0; i < legLength; i++) {
                Leg leg = requestWithActor.getEntity().getLegList().get(i);
                leg.setSaleOrderNo(requestWithActor.getEntity().getSaleOrderNo());
                leg.setParentSection(i);
                leg.setLegNo(maxNoService.generateBizNo("IFT_LEG_NO", 27));
                leg.setOwner(agent.getOwner());
                leg.setCreateTime(createTime);
                leg.setCreator(agent.getAccount());
                leg.setStatus(String.valueOf(1));
                leg.setValid((byte) 1);
                leg.setChildSection(1);//改签过的航段
                legDao.insertSelective(leg);
            }

            //TODO 这里需要重新获得新的行程信息
            /*新增人+航段行程*/
            for (Long passengerNo : requestWithActor.getEntity().getOldPassengerNoList()) {
                for (Leg leg : requestWithActor.getEntity().getLegList()) {
                    SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
                    saleOrderDetail.setSaleOrderDetailNo(maxNoService.generateBizNo("IFT_SALE_ORDER_DETAIL_NO", 36));
                    //saleOrderDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                    saleOrderDetail.setSaleOrderNo(saleChangeNo);
                    saleOrderDetail.setPassengerNo(passengerNo);
                    saleOrderDetail.setLegNo(leg.getLegNo());
                    saleOrderDetail.setOwner(agent.getOwner());
                    saleOrderDetail.setCreateTime(createTime);
                    saleOrderDetail.setCreator(agent.getAccount());
                    saleOrderDetail.setValid((byte) 1);
                    saleOrderDetail.setStatus("1");
                    saleOrderDetail.setIsChange(true);
                    saleOrderDetail.setCabin(leg.getCabin());
                    saleOrderDetailDao.insertSelective(saleOrderDetail);

                    //添加新改签单明细
                    SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
                    saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("IFT_SALE_CHANGE_DETAIL_NO", 49));
                    saleChangeDetail.setSaleChangeNo(saleChangeNo);
                    saleChangeDetail.setOwner(agent.getOwner());
                    saleChangeDetail.setBuyChangeNo(buyChangeNo);
                    saleChangeDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
                    saleChangeDetail.setCreateTime(createTime);
                    saleChangeDetail.setCreator(agent.getAccount());
                    saleChangeDetail.setValid((byte) 1);
                    saleChangeDetail.setOrderDetailType(2);
                    saleChangeDetailDao.insertSelective(saleChangeDetail);
                }
            }


            /*改签乘客价格*/
           /* for (Long passengerNo : requestWithActor.getEntity().getOldPassengerNoList()) {
                PassengerChangePrice passengerChangePrice = new PassengerChangePrice();
                passengerChangePrice.setPassengerNo(passengerNo);
                passengerChangePrice.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
                passengerChangePrice.setOwner(agent.getOwner());
                passengerChangePrice.setValid((byte) 1);
                passengerChangePrice.setChangePriceNo(maxNoService.generateBizNo("IFT_PASSENGER_CHANGE_PRICE_NO", 30));
                passengerChangePriceDao.insertSelective(passengerChangePrice);
            }*/

            /*通过编号查询出销售单*/
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderExt.getSaleOrderNo());
            /*创建销售改签单*/
            SaleChange saleChange = new SaleChange();
            saleChange.setSaleChangeNo(saleChangeNo);
            saleChange.setOrderChangeType(3);//3为改签
            saleChange.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
            saleChange.setBusinessSignNo(businessSignNo);
            saleChange.setBsignType(5);//1销采 2换票 3废 4退 5改签
            saleChange.setOwner(agent.getOwner());
            saleChange.setCreateTime(createTime);
            saleChange.setChildStatus(1);//1.待审核 2.已审核 3.退票中 废票中 改签中 10.已完成  11.已取消
            saleChange.setChangeReason(requestWithActor.getEntity().getChangeReason());
            saleChange.setGoodsType(2);//商品大类 2=国际机票
            saleChange.setGoodsSubType(13);//销改
            saleChange.setGoodsName("");//TODO
            saleChange.setTransationOrderNo(saleOrder.getTransationOrderNo());//交易号
            saleChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
            saleChangeService.create(requestWithActor.getAgent(), saleChange);

            /*创建销售改签单拓展*/
            //多次改签
            //获取上次改签的pnr 编码 以及供应商
            SaleChangeExt lastSaleChangeExt =saleChangeExtDao.selectLastChangeExtByPassengerNo(requestWithActor.getEntity().getOldPassengerNoList().get(0));
            if (lastSaleChangeExt !=null){
                saleChangeExt.setSupplierNo(lastSaleChangeExt.getSupplierNo());
                saleChangeExt.setPnrNo(lastSaleChangeExt.getPnrNo());
                saleChangeExt.setOffice(lastSaleChangeExt.getOffice());
            }
            saleChangeExt.setSaleChangeNo(saleChangeNo);
            saleChangeExt.setChangeType(3);//3为改签,同saleChange的orderChangeType
            saleChangeExt.setContactName(requestWithActor.getEntity().getContactName());
            saleChangeExt.setContactMobile(requestWithActor.getEntity().getContactPhone());
            saleChangeExt.setStatus("1");//1待处理
            saleChangeExt.setCreateTime(createTime);
            saleChangeExt.setCreator(agent.getAccount());
            saleChangeExt.setCustomerRemark(requestWithActor.getEntity().getChangeReason());//改签原因
            saleChangeExt.setValid((byte) 1);
            saleChangeExt.setAirlineStatus(1);
            saleChangeExt.setOwner(agent.getOwner());
            saleChangeExt.setCustomerNo(agent.getNum());
            saleChangeExt.setCustomerTypeNo(agent.getType());
            setChangeOrderLocker(saleChangeExt,agent,saleOrder);
            log.info("申请改签单时保存的改签单信息:{}",saleChangeExt.toString());
            saleChangeExtDao.insertSelective(saleChangeExt);
            //销售改签分单（如果该单不是OP下单才会执行分单）
            iftMessageService.sendChangeMessage(saleOrderExt.getSaleOrderNo(),agent.getOwner()+"","salesman-change");
            //如果在setChangeOrderLocker方法中设置了locker那么在sendChangeMessage则查询不到该订单，所以需要在跟新一次该出票员的销售改签数量
            setTicketSenderNum(saleChangeExt,agent,saleOrder);
            /*通过销售单编号获取采购单*/
            List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(requestWithActor.getEntity().getSaleOrderNo());
            /*创建采购改签单*/
            BuyChange buyChange = new BuyChange();
            buyChange.setBuyChangeNo(buyChangeNo);
            buyChange.setOrderChangeType(3);//3为改签
            buyChange.setBusinessSignNo(businessSignNo);
            buyChange.setBsignType(3);
            buyChange.setOwner(agent.getOwner());
            buyChange.setCreateTime(createTime);
            buyChange.setChildStatus(1);//1.待审核 2.已审核 3.处理中 10.已处理 11.已取消
            buyChange.setGoodsType(2);//商品大类 2=国际机票
            //OS-api\entity\EgoodsSubType
            buyChange.setGoodsSubType(23);//采购改单
            buyChange.setGoodsName("");//TODO
            buyChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
            if (buyOrderExtList != null && buyOrderExtList.size() != 0) { buyChange.setBuyOrderNo(buyOrderExtList.get(0).getBuyOrderNo()); }
            buyChangeService.create(requestWithActor.getAgent(), buyChange);

            /*创建采购改签单拓展*/
            BuyChangeExt buyChangeExt = new BuyChangeExt();
            buyChangeExt.setBuyChangeNo(buyChangeNo);
            buyChangeExt.setOwner(agent.getOwner());
            buyChangeExt.setCreateTime(createTime);
            buyChangeExt.setValid((byte) 1);
            buyChangeExt.setTicketType("BSP");
            buyChangeExtDao.insertSelective(buyChangeExt);

            /*创建新增操作日志*/
            try {
                String logstr ="用户"+agent.getAccount()+"申请改签："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
                LogRecord logRecord = new LogRecord();
                logRecord.setCreateTime(createTime);
                logRecord.setTitle("创建国际改签单");
                logRecord.setDesc(logstr);
                logRecord.setOptLoginName(requestWithActor.getAgent().getAccount());
                logRecord.setRequestIp(requestWithActor.getAgent().getIp());
                logRecord.setBizCode("IFT");
                logRecord.setBizNo(String.valueOf(requestWithActor.getEntity().getSaleOrderNo()));
                Map<String, Object> otherOpts = new HashMap<String, Object>();
                otherOpts.put("transationOrderNo", saleChange.getTransationOrderNo());
                logRecord.setOtherOpts(otherOpts);
                logService.insert(logRecord);
            } catch (Exception e) {
                log.error("添加(title=创建国际改签单)操作日志异常===" + e);
            }

        } catch (GSSException ubp) {
            log.error("创建改签单失败", ubp);
            throw ubp;

        } catch (Exception e) {
            log.error("创建改签单失败", e);
            throw new GSSException("创建改签单失败", "0003", "创建改签单失败");
        }
        log.info("申请改签单结束=========");
        return saleChangeExt;
    }

    private void setTicketSenderNum(SaleChangeExt saleChangeExt, Agent agent, SaleOrder saleOrder) {
        if(StringUtils.isNotBlank(saleOrder.getSourceChannelNo()) && StringUtils.equals("OP",saleOrder.getSourceChannelNo())){
            User user = userService.findUserByLoginName(agent,agent.getAccount());
            if(user!=null) {
                ticketSenderService.updateByLockerId(agent,user.getId(),"SALE_CHANGE_NUM");
            }
        }
    }

    @Override
    public Long createChange(RequestWithActor<ChangeCreateVo> requestWithActor) {
        boolean flag = false;
        SaleChangeExt saleChangeExt = null;
        try {
            saleChangeExt = this.apiCreateChange(requestWithActor);
            /*if (saleChangeExt == null) {
                flag = false;
            }
            flag = true;*/
        } catch (Exception e) {
            //flag = false;
            throw new GSSException("创建改签单失败", "0003", "创建改签单失败" + e);
        }
        return saleChangeExt!=null?saleChangeExt.getSaleChangeNo():null;
    }

    /**
     * 改签单创建应收应付.
     *
     * @param passengerChangeList
     * @return
     */
    @Override
    @Transactional
    public boolean verify(RequestWithActor<List<PassengerChangePrice>> passengerChangeList) {
        log.info("改签单创建应收应付.");
        Agent agent = passengerChangeList.getAgent();
        boolean flag = false;
        try {
            if (passengerChangeList == null || passengerChangeList.getEntity().size() == 0) {
                throw new GSSException("改签单创建应收应付", "0201", "传入参数为空");
            }
            //新增改签单
            BigDecimal saleTotal = new BigDecimal(0);//销售单合计
            BigDecimal buyTotal = new BigDecimal(0);//采购单合计
            int changPriceFlag = 0;
            for (PassengerChangePrice passengerChangePrice : passengerChangeList.getEntity()) {
                if (passengerChangePrice.getSaleCountPrice() != null) {
                    saleTotal = saleTotal.add(passengerChangePrice.getSaleCountPrice());
                }
                if (passengerChangePrice.getBuyCountPrice() != null) {
                    buyTotal = buyTotal.add(passengerChangePrice.getBuyCountPrice());
                }
                changPriceFlag = passengerChangePriceDao.updateByPrimaryKeySelective(passengerChangePrice);
                //新增改签单成功后新增应收、应付
            }
            if (changPriceFlag != 0) {
                PassengerChangePrice passengerChangePriceSelect = passengerChangePriceDao.selectByPrimaryKey(passengerChangeList.getEntity().get(0).getChangePriceNo());
                //ChangePriceNo()对应saleChangeNo
                List<BuyOrderExt> buyOrderExtList = buyOrderExtDao.selectBuyOrderBySaleOrderNo(passengerChangePriceSelect.getSaleOrderNo());
                List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, buyOrderExtList.get(0).getBuyOrderNo());
                if (passengerChangePriceSelect.getSaleChangeNo() != null) {
                    //销售改签应收、应付
                    CreatePlanAmountVO planAmountRecordType2 = new CreatePlanAmountVO();
                    planAmountRecordType2.setRecordNo(passengerChangePriceSelect.getSaleChangeNo());
                    planAmountRecordType2.setIncomeExpenseType(1);//收支类型 1 收，2 为支
                    planAmountRecordType2.setBusinessType(BusinessType.SALECHANGE.getKey());//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                    planAmountRecordType2.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                    planAmountRecordType2.setPlanAmount(saleTotal);//合计
                    //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                    planAmountRecordType2.setGoodsType(2);
                    planAmountRecordService.create(passengerChangeList.getAgent(), planAmountRecordType2);
                }
                if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
                    //采购改签应收应付
                    CreatePlanAmountVO planAmountRecordType1 = new CreatePlanAmountVO();
                    planAmountRecordType1.setRecordNo(buyChangeList.get(0).getBuyChangeNo());
                    planAmountRecordType1.setIncomeExpenseType(2);//收支类型 1 收，2 为支
                    planAmountRecordType1.setBusinessType(BusinessType.BUYCHANGE.getKey());
                    planAmountRecordType1.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                    planAmountRecordType1.setPlanAmount(buyTotal);//合计
                    //商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                    planAmountRecordType1.setGoodsType(GoodsBigType.INTERNATIONAL_AIR_TICKET.getKey());
                    planAmountRecordService.create(passengerChangeList.getAgent(), planAmountRecordType1);
                }
                flag = true;
            }
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ passengerChangeList.getAgent().getAccount()+"国际改签创建应收应付："+"["+passengerChangeList.getEntity().get(0).getSaleOrderNo()+"]";
                String title = "国际改签创建应收应付";
                IftLogHelper.logger(agent,passengerChangeList.getEntity().get(0).getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际改签创建应收应付)操作日志异常===" + e);
            }
            log.info("审核改签单结束");
        } catch (Exception e) {
            log.error("审核改签单异常", e);
            throw new GSSException("审核改签单模块", "0201", "改签异常");
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean refundVerify(RequestWithActor<List<PassengerChangePrice>> passengerChangeList) {

        log.info("改签单审核开始");
        boolean flag = false;
        try {
            BigDecimal saleTotal = new BigDecimal(0);// 销售单合计
            BigDecimal buyTotal = new BigDecimal(0);// 采购单合计
            for (PassengerChangePrice passenger : passengerChangeList.getEntity()) {
                if (passenger.getSaleCountPrice() != null) {
                    saleTotal = saleTotal.add(passenger.getSaleCountPrice());
                }
                if (passenger.getBuyCountPrice() != null) {
                    buyTotal = buyTotal.add(passenger.getBuyCountPrice());
                }
            }
            // 根据订单编号查询订单
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(passengerChangeList.getAgent(),
                    passengerChangeList.getEntity().get(0).getSaleChangeNo());
            if (saleChange != null) {
                // 销售单应收
                CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                createPlanAmountVOType.setRecordNo(saleChange.getSaleChangeNo());// 记录编号
                createPlanAmountVOType.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
                createPlanAmountVOType.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                createPlanAmountVOType.setPlanAmount(saleTotal);// 合计
                createPlanAmountVOType
                        .setBusinessType(BusinessType.SALECHANGE.getKey());// 业务类型 2，销售单，3，采购单，4,变更单（可以根据变更表设计情况将废退改分开）
                createPlanAmountVOType.setGoodsType(2);// 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                planAmountRecordService.create(passengerChangeList.getAgent(), createPlanAmountVOType);
            } else {
                log.error("改签单为空");
                throw new GSSException("改签单为空", "0601", "核价修改销售、采购失败");
            }

            List<BuyOrder> buyOrderList = buyOrderService
                    .getBuyOrdersBySONo(passengerChangeList.getAgent(), saleChange.getSaleOrderNo());
            if (buyOrderList != null) {
                for (BuyOrder buyOrder : buyOrderList) {
                    List<BuyChange> buyChangeList = buyChangeService
                            .getBuyChangesByBONo(passengerChangeList.getAgent(), buyOrder.getBuyOrderNo());
                    for (BuyChange buyChange : buyChangeList) {
                        if (buyChange.getBusinessSignNo().equals(saleChange.getBusinessSignNo())) {
                            // 采购单应付
                            CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
                            createPlanAmountVOType.setRecordNo(buyChange.getBuyChangeNo());// 记录编号
                            createPlanAmountVOType.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                            createPlanAmountVOType.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
                            createPlanAmountVOType.setPlanAmount(buyTotal);// 合计
                            createPlanAmountVOType.setBusinessType(
                                    BusinessType.BUYCHANGE.getKey());// 业务类型 2，销售单，3，采购单，4，变更单（可以根据变更表设计情况将废退改分开）
                            createPlanAmountVOType.setGoodsType(2);// 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                            planAmountRecordService.create(passengerChangeList.getAgent(), createPlanAmountVOType);
                        }
                    }
                }
            } else {
                log.error("改签单为空");
                throw new GSSException("改签单为空", "0601", "核价修改销售、采购失败");
            }
            log.info("订单核价结束");
            flag = true;
        } catch (Exception e) {
            log.error("订单核价失败", e);
            throw new GSSException("核价修改失败", "0603", "核价修改失败");
        }
        return flag;
    }

    @Override
    public boolean saleRefund(Agent agent, Long saleChangeNo) throws GSSException {

        if (agent == null) {
            log.error("agent 为空");
            throw new GSSException("agent 为空", "1001", "创建销售退款单失败");
        }
        if (saleChangeNo == null) {
            log.error("saleChangeNo 为空");
            throw new GSSException("saleChangeNo 为空", "1001", "创建销售退款单失败");
        }
        SaleChangeExt saleChangeExt = this.getSaleChangeExtByNo(new RequestWithActor<>(agent, saleChangeNo));
        SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleChangeExt.getSaleChange().getSaleOrderNo());
        //当支付状态为已支付（payStatus=3）时创建销售退款单
        if (saleChangeExt != null && saleChangeExt.getSaleChange() != null) {
            if (saleChangeExt.getSaleChange().getPayStatus() == 1) {
                try {
                    CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
                    certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
                    certificateCreateVO.setReason("销售付款单信息"); //补充说明
                    certificateCreateVO.setSubBusinessType(4); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
                    List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
                    BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();

                    businessOrderInfo.setActualAmount(saleChangeExt.getSaleChange().getPlanAmount());
                    certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
                    certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());

                    businessOrderInfo.setBusinessType(4);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                    businessOrderInfo.setRecordNo(saleChangeNo);
                    orderInfoList.add(businessOrderInfo);
                    certificateCreateVO.setOrderInfoList(orderInfoList);
                    certificateCreateVO.setServiceLine("2");
                    certificateService.saleRefundCert(agent, certificateCreateVO);
                } catch (Exception e) {
                    log.error("创建销售退款单失败," + e);
                    throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
                }
            }
        }

        return true;
    }

    @Override
    public boolean buyRefund(Agent agent, Long saleChangeNo, Long saleOrderNo) throws GSSException {

        if (agent == null) {
            log.error("agent 为空");
            throw new GSSException("agent 为空", "1001", "创建销售退款单失败");
        }
        if (saleChangeNo == null) {
            log.error("saleChangeNo 为空");
            throw new GSSException("saleChangeNo 为空", "1001", "创建销售退款单失败");
        }
        try {
            CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
            certificateCreateVO.setIncomeExpenseType(1); //收支类型 1 收，2 为支
            certificateCreateVO.setReason("创建采购退款单信息"); //补充说明
            certificateCreateVO.setSubBusinessType(4); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
            certificateCreateVO.setServiceLine("2");
            List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
            BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();

            List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);
            if (buyOrderList != null && buyOrderList.size() != 0) {
                BuyOrder buyOrder = buyOrderList.get(0);
                certificateCreateVO.setCustomerNo(buyOrder.getSupplierNo());
                certificateCreateVO.setCustomerTypeNo(buyOrder.getSupplierTypeNo());

                List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, buyOrder.getBuyOrderNo());
                SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeNo);
                if (buyChangeList != null && buyChangeList.size() != 0 && saleChange != null) {
                    for (BuyChange buyChange : buyChangeList) {
                        if (buyChange.getBusinessSignNo().equals(saleChange.getBusinessSignNo())) {
                            businessOrderInfo.setActualAmount(buyChange.getPlanAmount());
                            businessOrderInfo.setBusinessType(5);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
                            businessOrderInfo.setRecordNo(buyChange.getBuyChangeNo());
                        }
                    }
                }
            }
            orderInfoList.add(businessOrderInfo);
            certificateCreateVO.setOrderInfoList(orderInfoList);
            certificateService.buyRefundCert(agent, certificateCreateVO);
        } catch (Exception e) {
            log.error("创建采购退款单单失败," + e);
            throw new GSSException("创建采购退款单失败," + e, "1001", "创建采购退款单失败");
        }
        return true;
    }

    /**
     * 通过废退单编号获取销售废退单（包含saleChange）
     *
     * @param requestWithActor
     */
    @Override
    public SaleChangeExt getSaleChangeExtByNo(RequestWithActor<Long> requestWithActor) {

        SaleChangeExt saleChangeExt = new SaleChangeExt();
        log.info("获取销售废退单开始============");
        try {
            if (requestWithActor.getEntity().longValue() == 0) {
                log.error("销售单废退编号为空");
                throw new GSSException("销售单废退编号为空", "0001", "获取销售废退单失败");
            }
            // 销售单编号
            saleChangeExt = saleChangeExtDao.selectByPrimaryKey(requestWithActor.getEntity().longValue());
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(),requestWithActor.getEntity());
            if (saleChangeExt != null) {
                saleChangeExt.setSaleChange(saleChange == null ? new SaleChange() : saleChange);
            }
            log.info("获取销售单结束============");
        } catch (Exception e) {
            log.error("废退单编号获取销售废退单", e);
        }

        return saleChangeExt;
    }

    /**
     * 改签处理
     *
     * @param changeTicketRequest
     * @return
     */
    @Override
    @Transactional
    public boolean changeHandle(RequestWithActor<ChangeTicketRequest> changeTicketRequest) {
        log.info("改签处理开始");
        try {
            if (changeTicketRequest.getAgent() == null || changeTicketRequest.getEntity() == null) {
                throw new GSSException("回贴票号模块", "1101", "传入参数为空");
            }
            //根据传入参数changePnr判断是否为改签
            if (changeTicketRequest.getEntity().isChangePnr()) {
                //新增新的pnr
                Pnr pnr = new Pnr();
                //pnr.setPnrNo(maxNoService.generateBizNo("IFT_PNR", 32));
                pnr.setOwner(changeTicketRequest.getAgent().getOwner());
                pnr.setPnr(changeTicketRequest.getEntity().getNewPnr());//航司PNR编号
                pnr.setBigPnr(changeTicketRequest.getEntity().getBigPnr());
                //setSourceNo/setPnrSource/setPnrContent等参数从buyOrderDetailList中第一条记录获取
                if (changeTicketRequest.getEntity().getBuyOrderDetailList() != null && changeTicketRequest.getEntity().getBuyOrderDetailList().size() > 0) {
                    for (int i = 1; i < changeTicketRequest.getEntity().getBuyOrderDetailList().size(); i++) {
                        pnr.setSourceNo(changeTicketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getSourceNo());//订单来源编号 可能为采购单编号，也可能为销售单编号，具体根据pnrSource确定
                        pnr.setPnrSource(changeTicketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getPnrSource());//PNR来源 1：导入，2：采购，3：改签
                        pnr.setPnrContent(changeTicketRequest.getEntity().getBuyOrderDetailList().get(1).getPnr().getPnrContent());//PNR内容
                    }
                }
                pnr.setValid((byte) 1);
                pnr.setCreator(changeTicketRequest.getAgent().getAccount());
                pnr.setCreateTime(new Date());
                pnrDao.insert(pnr);

                for (BuyOrderDetail buyOrderDetail : changeTicketRequest.getEntity().getBuyOrderDetailList()) {
                    //在buyOrderDetail中票号等属性  并执行update语句进行修改
                    buyOrderDetail.setModifier(changeTicketRequest.getAgent().getAccount());//修改人
                    buyOrderDetail.setModifyTime(new Date());//修改时间
                    buyOrderDetail.setStatus("6");//状态设置为已改签
                    int updateTickerFlag = buyOrderDetailDao.updateByPrimaryKeySelective(buyOrderDetail);
                    if (updateTickerFlag == 0) {
                        throw new GSSException("回贴票号模块", "1102", "更新BuyOrderDetail表中票号失败");
                    }
                }
            }
            log.info("回贴票号结束");
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+changeTicketRequest.getAgent().getAccount()+"国际改签处理："+"["+changeTicketRequest.getEntity().getBuyOrderDetailList().get(1).getBuyOrderNo()+"]";
                String title ="国际改签处理";
                IftLogHelper.logger(changeTicketRequest.getAgent(),changeTicketRequest.getEntity().getBuyOrderDetailList().get(1).getBuyOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际改签处理)操作日志异常===" + e);
            }
        } catch (Exception e) {
            log.error("回贴票号异常", e);
            throw new GSSException("回贴票号模块", "1104", "回贴票号异常");
        }
        log.info("改签处理结束");
        return false;
    }

    /**
     * 根据业务情况接收出票消息，修改采购变更单状态为待出票
     *
     * @param requestWithActor
     * @return
     */
    @Override
    public boolean updateBuyChangeStatus(RequestWithActor<Long> requestWithActor) {
        Agent agent = requestWithActor.getAgent();
        IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
        Map<String, Object> map = configs.getConfigsMap();
        //改签流程是否经过出票部配置
        boolean changeAfterTicketer;
        changeAfterTicketer = (null != map && map.get("changeAfterTicketer").equals("true"));
        Long saleChangeNo = requestWithActor.getEntity();
        if (agent == null) {
            log.error("agent为空");
            throw new GSSException("agent为空", "1001", "修改采购变更单状态失败");
        }
        if (saleChangeNo == null) {
            log.error("saleChangeNo为空");
            throw new GSSException("saleChangeNo为空", "1002", "修改采购变更单状态失败");
        }
        try {
            log.info("receiveTicket->" + JSON.toJSONString(requestWithActor));
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeNo);

            RequestWithActor<Long> saleChangeExtRequest = new RequestWithActor<Long>();
            saleChangeExtRequest.setAgent(agent);
            saleChangeExtRequest.setEntity(saleChangeNo);
            SaleChangeExt changeExt = changeExtService.getSaleChange(saleChangeExtRequest);
            if (saleChange != null) {
                List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, saleChangeNo);
                if(isSaleChangeRefund(saleChange,buyChangeList)){
                    //采购改签打回销售退费
                    saleChangeService.updateStatus(agent, saleChange.getSaleChangeNo(), 11);// 将销售状态改为销售审核
                    for (BuyChange buyChange : buyChangeList) {
                        buyOrderService.updateStatus(agent, buyChange.getBuyChangeNo(), 11);//将采购单状态改为待出票
                    }
                } else{
                    if (null != changeExt) {
                        Integer ticketChangeType = changeExt.getTicketChangeType();
                        if (null != ticketChangeType) {
                            String type = ticketChangeType.toString();
                            if (changeAfterTicketer) {
                                //需要审核
                                if ("2".equals(type)) {// 2 改期 ---> 销售
                                    saleChangeService.updateStatus(agent, saleChange.getSaleChangeNo(), 6);// 将销售状态改为销售审核
                                } else {// 3 换开---> 出票
                                    saleChangeService.updateStatus(agent, saleChange.getSaleChangeNo(), 3);// 将销售状态改为改签中
                                }
                            } else {
                                //不需要审核
                                saleChangeService.updateStatus(agent, saleChange.getSaleChangeNo(), 3);// 将销售状态改为改签中
                            }
                        }
                    }

                    for (BuyChange buyChange : buyChangeList) {
                        if (buyChange.getBusinessSignNo().equals(saleChange.getBusinessSignNo())) {
                            buyOrderService.updateStatus(agent, buyChange.getBuyChangeNo(), 2);//将采购单状态改为待出票
                        }
                    }
                }
            }
            /* 创建操作日志 */
            try {
                String logstr=null;
                String title = null;
                if (changeExt !=null && changeExt.getSaleChange()!=null){
                    if (changeExt.getSaleChange().getChildStatus()==2){
                        title="改签订单支付";
                         logstr ="用户"+changeExt.getModifier()+"订单改签支付："+"["+changeExt.getSaleChange().getSaleOrderNo()+"]"+"￥"+changeExt.getSaleChange().getActualAmount();

                    }else {
                        title="改签订单退款";
                        logstr ="用户"+changeExt.getModifier()+"订单改签退款："+"["+changeExt.getSaleChange().getSaleOrderNo()+"]";

                    }
                }

                IftLogHelper.logger(agent,changeExt.getSaleChange().getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加操作日志异常===" + e);
            }

        } catch (Exception e) {
            log.error("修改采购变更单状态异常");
            throw new GSSException("修改采购变更单状态异常", "1003", "修改采购变更单状态失败");
        }
        return true;
    }

    private boolean isSaleChangeRefund(SaleChange saleChange, List<BuyChange> buyChangeList) {
        boolean result = false;
        if(saleChange.getChildStatus().equals(15)){
            result = true;
        }
        for (BuyChange buyChange : buyChangeList) {
            if (!buyChange.getChildStatus().equals(15)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 改签单审核
     * 修改状态主订单os_sale_change的状态
     * 已审核:child_status=2
     * 待支付:pay_status=1
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeAudit(RequestWithActor<ChangePriceRequest> requestWithActor) {
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("当前用户不能为空", "0101", "当前操作用户为空");
        }
        if (requestWithActor == null || requestWithActor.getEntity() == null) {
            throw new GSSException("改签审核失败", "0102", "改签审核发生异常,请检查");
        }
        try {
            //修改改签类型
            SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(requestWithActor.getEntity().getSaleChangeNo());
            //Long saleLocker = saleChangeExt.getLocker();
            saleChangeExt = changeExtService.updateSaleChangeExt(requestWithActor, saleChangeExt);
            List<ChangePriceVo> adtList = requestWithActor.getEntity().getSaleAdtPriceList();
            List<ChangePriceVo> chdList = requestWithActor.getEntity().getSaleChdPriceList();
            List<ChangePriceVo> infList = requestWithActor.getEntity().getSaleInfPriceList();
            List<Leg> legList = requestWithActor.getEntity().getLegList();
            if(adtList != null){
                 //可以儿童一个人改签所有成人列表可能为空
                log.info("保存变更价格数据" + adtList.size());
            }
            handle(requestWithActor, adtList);
            handle(requestWithActor, chdList);
            handle(requestWithActor, infList);
            Long saleChangeNo = requestWithActor.getEntity().getSaleChangeNo();
            //判断费用是否为0
            if(isNoFee(requestWithActor.getEntity().getSaleAdtPriceList(),requestWithActor.getEntity().getSaleChdPriceList(),requestWithActor.getEntity().getSaleInfPriceList())){
                saleChangeService.updateStatus(requestWithActor.getAgent(), saleChangeNo, 3);
                log.info("修改采购状态" + saleChangeNo);
                //如果支付为0
                saleChangeService.updatePayStatus(requestWithActor.getAgent(), saleChangeNo, 3);
                log.info("修改采购支付状态" + saleChangeNo);
               /* saleChangeService.updatePayStatus(requestWithActor.getAgent(), saleChangeNo, 3);
                log.info("修改采购支付状态" + saleChangeNo);*/
            } else{
                saleChangeService.updateStatus(requestWithActor.getAgent(), saleChangeNo, 2);
                log.info("修改采购状态" + saleChangeNo);
                saleChangeService.updatePayStatus(requestWithActor.getAgent(), saleChangeNo, 1);
                log.info("修改采购支付状态" + saleChangeNo);
            }
                /*saleChangeService.updateStatus(requestWithActor.getAgent(), saleChangeNo, 2);
                log.info("修改采购状态" + saleChangeNo);
                saleChangeService.updatePayStatus(requestWithActor.getAgent(), saleChangeNo, 1);
                log.info("修改采购支付状态" + saleChangeNo);*/
           // BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNo(saleChangeNo);
            BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
            //出票员更新销售
            ticketSenderService.updateByLockerId(requestWithActor.getAgent(),saleChangeExt.getLocker(),"SALE_CHANGE_NUM");
            if(buyChangeExt != null){
                log.info("修改审核备注" + buyChangeExt.getBuyChangeNo());
              //  buyChangeExt.setChangeRemark(requestWithActor.getEntity().getChangeRemark());
                buyChangeExtDao.updateByPrimaryKey(buyChangeExt);
            }
            if(isNoFee(requestWithActor.getEntity().getSaleAdtPriceList(),requestWithActor.getEntity().getSaleChdPriceList(),requestWithActor.getEntity().getSaleInfPriceList())) {
                try {
                    //直接将单分配给销售改签员
                    RequestWithActor<Long> saleChangeRequest = new RequestWithActor<>();
                    saleChangeRequest.setAgent(requestWithActor.getAgent());
                    saleChangeRequest.setEntity(requestWithActor.getEntity().getSaleChangeNo());
                    changeExtService.assignChange(saleChangeRequest);
                } catch (Exception e) {
                    log.error("直接将改签单分给采购人员异常", e);
                }
            }
            if (legList != null && legList.size() > 0) {
                for (Leg leg : legList) {
                    legDao.updateByPrimaryKeySelective(leg);
                }
            }
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际改签单审核："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
                String title = "国际改签单审核";
                IftLogHelper.logger(requestWithActor.getAgent(),requestWithActor.getEntity().getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际改签单审核)操作日志异常===" , e);
            }
        } catch (Exception e) {
            log.error("改签审核失败" , e);
            throw new GSSException("改签审核失败", "0103", "改签确认异常,请检查");
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SaleChangeExt updateSaleChangeExt(RequestWithActor<ChangePriceRequest> requestWithActor, SaleChangeExt saleChangeExt) {
        saleChangeExt.setTicketChangeType(requestWithActor.getEntity().getTicketChangeType());
        //saleChangeExt.setLocker(0L);
        saleChangeExt.setModifier(requestWithActor.getAgent().getAccount());
        saleChangeExt.setModifyTime(new Date());
        saleChangeExt.setCurrency(requestWithActor.getEntity().getCurrency());
        saleChangeExt.setExchangeRate(requestWithActor.getEntity().getSaleExchangeRate());
        saleChangeExt.setSaleCurrency(requestWithActor.getEntity().getSaleCurrency());
        if (saleChangeExt !=null && saleChangeExt.getChangeRemark()!=null&&saleChangeExt.getChangeRemark() !=""){

        saleChangeExt.setChangeRemark(saleChangeExt.getChangeRemark()+"#"+requestWithActor.getEntity().getChangeRemark());
        }else {
            saleChangeExt.setChangeRemark(requestWithActor.getEntity().getChangeRemark());
        }
        log.info("保存采购数据" + saleChangeExt.toString());
        saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
        return saleChangeExt;
    }

    /**
     * 改签打回单审核
     */
    @Override
    public boolean changeReAudit(RequestWithActor<ChangePriceRequest> requestWithActor) {
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("当前用户不能为空", "0101", "当前操作用户为空");
        }
        if (requestWithActor == null || requestWithActor.getEntity() == null) {
            throw new GSSException("改签重新审核失败", "0102", "改签重新审核发生异常,请检查");
        }
        try {
            //修改改签类型
            SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(requestWithActor.getEntity().getSaleChangeNo());
            changeExtService.updateSaleChangeExt(requestWithActor, saleChangeExt);
            List<ChangePriceVo> adtList = requestWithActor.getEntity().getSaleAdtPriceList();
            List<ChangePriceVo> chdList = requestWithActor.getEntity().getSaleChdPriceList();
            List<ChangePriceVo> infList = requestWithActor.getEntity().getSaleInfPriceList();
            List<Leg> legList = requestWithActor.getEntity().getLegList();
            if(adtList != null){
                //可以儿童一个人改签所有成人列表可能为空
                log.info("保存变更价格数据" + adtList.size());
            }
            reHandle(requestWithActor, adtList);
            reHandle(requestWithActor, chdList);
            reHandle(requestWithActor, infList);
            Long saleChangeNo = requestWithActor.getEntity().getSaleChangeNo();

            saleChangeService.updateStatus(requestWithActor.getAgent(), saleChangeNo, 3);
            log.info("修改采购状态" + saleChangeNo);
            try{
                //直接将单分配给采购人
                RequestWithActor<Long> saleChangeRequest = new RequestWithActor<>();
                saleChangeRequest.setAgent(requestWithActor.getAgent());
                saleChangeRequest.setEntity(requestWithActor.getEntity().getSaleChangeNo());
                changeExtService.assignChange(saleChangeRequest);
            }catch (Exception e){
                log.error("直接将改签单分给采购人员异常",e);
            }

            BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
            if(buyChangeExt != null){
                log.info("修改审核备注" + buyChangeExt.getBuyChangeNo());
                buyChangeExt.setChangeRemark(requestWithActor.getEntity().getChangeRemark());
                buyChangeExtDao.updateByPrimaryKey(buyChangeExt);
            }
            if (legList != null && legList.size() > 0) {
                for (Leg leg : legList) {
                    legDao.updateByPrimaryKeySelective(leg);
                }
            }
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际重新改签单审核："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
                String title = "国际重新改签单审核";
                IftLogHelper.logger(requestWithActor.getAgent(),requestWithActor.getEntity().getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际重新改签单审核)操作日志异常===" , e);
            }
        } catch (Exception e) {
            log.error("改签重新审核失败" , e);
            throw new GSSException("改签重新审核失败", "0103", "改签确认异常,请检查");
        }
        return true;
    }

    private boolean isNoFee(List<ChangePriceVo> saleAdtPriceList,List<ChangePriceVo> saleChdPriceList,List<ChangePriceVo> saleInfPriceList) {

        boolean isNoFee = false;
        BigDecimal all = new BigDecimal(0);

        if(saleAdtPriceList != null && saleAdtPriceList.get(0) != null){
            ChangePriceVo changePriceVo = saleAdtPriceList.get(0);
            if(changePriceVo.getSalePrice() != null){
                all = all.add(changePriceVo.getSalePrice());
            }
            if(changePriceVo.getSaleTax() != null){
                all = all.add(changePriceVo.getSaleTax());
            }
            if(changePriceVo.getSaleBrokerage() != null){
                all = all.add(changePriceVo.getSaleBrokerage());
            }
            if(changePriceVo.getSaleRest() != null){
                all = all.add(changePriceVo.getSaleRest());
            }
            if(changePriceVo.getCountPrice() != null){
                all = all.add(changePriceVo.getCountPrice());
            }
        }

        if (saleChdPriceList !=null && saleChdPriceList.get(0) != null) {
            ChangePriceVo changePriceVo2 = saleChdPriceList.get(0);
            if(changePriceVo2.getSalePrice() != null){
                all = all.add(changePriceVo2.getSalePrice());
            }
            if(changePriceVo2.getSaleTax() != null){
                all = all.add(changePriceVo2.getSaleTax());
            }
            if(changePriceVo2.getSaleBrokerage() != null){
                all = all.add(changePriceVo2.getSaleBrokerage());
            }
            if(changePriceVo2.getSaleRest() != null){
                all = all.add(changePriceVo2.getSaleRest());
            }
            if(changePriceVo2.getCountPrice() != null){
                all = all.add(changePriceVo2.getCountPrice());
            }
        }

        if (saleInfPriceList != null && saleInfPriceList.get(0) != null) {
            ChangePriceVo changePriceVo3 = saleInfPriceList.get(0);
            if(changePriceVo3.getSalePrice() != null){
                all = all.add(changePriceVo3.getSalePrice());
            }
            if(changePriceVo3.getSaleTax() != null){
                all = all.add(changePriceVo3.getSaleTax());
            }
            if(changePriceVo3.getSaleBrokerage() != null){
                all = all.add(changePriceVo3.getSaleBrokerage());
            }
            if(changePriceVo3.getSaleRest() != null){
                all = all.add(changePriceVo3.getSaleRest());
            }
            if(changePriceVo3.getCountPrice() != null){
                all = all.add(changePriceVo3.getCountPrice());
            }
        }

        BigDecimal zore = new BigDecimal("0");
        if(all.compareTo(zore) <= 0){
            isNoFee = true;
        }
        return isNoFee;
    }

    public boolean handle(RequestWithActor<ChangePriceRequest> requestWithActor, List<ChangePriceVo> voList) {
        boolean flag = false;
        if (voList == null) {
            return false;
        }
        for (int i = 0; i < voList.size(); i++) {
            ChangePriceVo priceVo = new ChangePriceVo();
            if (i > 0) {
                ChangePriceVo temp = voList.get(0);
                priceVo = voList.get(i);
                //同一种乘客类型价格信息都一样
                priceVo.setSalePrice(temp.getSalePrice());
                priceVo.setCountPrice(temp.getCountPrice());
                priceVo.setSaleBrokerage(temp.getSaleBrokerage());
                priceVo.setSaleTax(temp.getSaleTax());
                priceVo.setSaleRest(temp.getSaleRest());
                priceVo.setSaleAgencyFee(temp.getSaleAgencyFee());
                priceVo.setSaleRebate(temp.getSaleRebate());
                priceVo.setBuyPrice(temp.getBuyPrice());
                priceVo.setBuyCountPrice(temp.getBuyCountPrice());
                priceVo.setBuyBrokerage(temp.getBuyBrokerage());
                priceVo.setBuyAgencyFee(temp.getBuyAgencyFee());
                priceVo.setBuyRebate(temp.getBuyRebate());
                priceVo.setBuyTax(temp.getBuyTax());
                priceVo.setBuyRest(temp.getBuyRest());
                priceVo.setAllSalePrice(temp.getAllSalePrice());
                priceVo.setAllBuyPrice(temp.getAllBuyPrice());
                priceVo.setProfit(temp.getProfit());
            } else {
                priceVo = voList.get(i);
            }
            PassengerChangePrice priceChange = new PassengerChangePrice();
            Long passengerChangePriceNo = maxNoService.generateBizNo("IFT_PASSENGER_CHANGE_PRICE_NO", 30);
            priceChange.setAllBuyPrice(priceVo.getAllBuyPrice().add(priceVo.getBuyCountPrice()));
            priceChange.setAllSalePrice(priceVo.getAllSalePrice().add(priceVo.getCountPrice()));
            priceChange.setChangePriceNo(passengerChangePriceNo);
            priceChange.setSaleChangeNo(priceVo.getSaleChangeNo());
            priceChange.setPassengerNo(priceVo.getPassengerNo());
            priceChange.setSaleOrderNo(priceVo.getSaleOrderNo());
            priceChange.setPassengerType(priceVo.getPassengerType());
            priceChange.setSaleCountPrice(priceVo.getCountPrice());
            priceChange.setSalePrice(priceVo.getSalePrice());
            priceChange.setSaleAgencyFee(priceVo.getSaleAgencyFee());
            priceChange.setSaleRebate(priceVo.getSaleRebate());
            priceChange.setSaleRest(priceVo.getSaleRest());
            priceChange.setSaleTax(priceVo.getSaleTax());
            priceChange.setSaleBrokerage(priceVo.getSaleBrokerage());
            priceChange.setBuyCountPrice(priceVo.getBuyCountPrice());
            priceChange.setBuyPrice(priceVo.getBuyPrice());
            priceChange.setBuyRest(priceVo.getBuyRest());
            priceChange.setBuyTax(priceVo.getBuyTax());
            priceChange.setBuyAgencyFee(priceVo.getBuyAgencyFee());
            priceChange.setBuyRebate(priceVo.getBuyRebate());
            priceChange.setBuyBrokerage(priceVo.getBuyBrokerage());
            priceChange.setCreateTime(new Date());
            priceChange.setModifyTime(new Date());
            priceChange.setOwner(requestWithActor.getAgent().getOwner());
            priceChange.setValid((byte) 1);
            priceChange.setStatus("1");
            priceChange.setCreator(requestWithActor.getAgent().getAccount());
            priceChange.setModifier(requestWithActor.getAgent().getAccount());
            priceChange.setBuyCurrency(priceVo.getBuyCurrency());
            priceChange.setBuyExchangeRate(priceVo.getBuyExchangeRate());
            priceChange.setProfit(priceVo.getProfit());
            int result = passengerChangePriceDao.insert(priceChange);
            if (result == 0) {
                flag = false;
                throw new GSSException("改签审核创建失败", "0101", "创建发生异常,请检查");
            }
            flag = true;
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际创建审核改签单："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
                String title ="国际创建审核改签单";
                IftLogHelper.logger(requestWithActor.getAgent(),requestWithActor.getEntity().getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际创建审核改签单)操作日志异常===" + e);
            }
        }

        return flag;
    }
    public boolean reHandle(RequestWithActor<ChangePriceRequest> requestWithActor, List<ChangePriceVo> voList) {

        //查询出所有的changePrice然后更新
        Long saleChangeNo = requestWithActor.getEntity().getSaleChangeNo();
        List<PassengerChangePrice> priceList = passengerChangePriceDao.selectPricerByChangeNo(saleChangeNo);
        boolean flag = false;
        if (voList == null) {
            return false;
        }
        ChangePriceVo priceVo = voList.get(0);
        for (PassengerChangePrice passengerChangePrice : priceList) {
            if (passengerChangePrice.getPassenger().getPassengerType().equals(priceVo.getPassengerType())) {
                //如果类型相同覆盖之前的值
                passengerChangePrice.setSaleCountPrice(priceVo.getCountPrice());
                passengerChangePrice.setSalePrice(priceVo.getSalePrice());
                passengerChangePrice.setSaleAgencyFee(priceVo.getSaleAgencyFee());
                passengerChangePrice.setSaleRebate(priceVo.getSaleRebate());
                passengerChangePrice.setSaleRest(priceVo.getSaleRest());
                passengerChangePrice.setSaleTax(priceVo.getSaleTax());
                passengerChangePrice.setSaleBrokerage(priceVo.getSaleBrokerage());
                passengerChangePrice.setBuyCountPrice(priceVo.getBuyCountPrice());
                passengerChangePrice.setBuyPrice(priceVo.getBuyPrice());
                passengerChangePrice.setBuyRest(priceVo.getBuyRest());
                passengerChangePrice.setBuyTax(priceVo.getBuyTax());
                passengerChangePrice.setBuyAgencyFee(priceVo.getBuyAgencyFee());
                passengerChangePrice.setBuyRebate(priceVo.getBuyRebate());
                passengerChangePrice.setBuyBrokerage(priceVo.getBuyBrokerage());
                passengerChangePrice.setBuyCurrency(priceVo.getBuyCurrency());
                passengerChangePrice.setBuyExchangeRate(priceVo.getBuyExchangeRate());
                passengerChangePrice.setModifyTime(new Date());
                passengerChangePrice.setOwner(requestWithActor.getAgent().getOwner());
                passengerChangePrice.setStatus("1");
                passengerChangePrice.setModifier(requestWithActor.getAgent().getAccount());
                passengerChangePrice.setAllBuyPrice(priceVo.getAllBuyPrice().add(priceVo.getBuyCountPrice()));
                passengerChangePrice.setAllSalePrice(priceVo.getAllSalePrice().add(priceVo.getCountPrice()));
                int result = passengerChangePriceDao.updateByPrimaryKeySelective(passengerChangePrice);
                if (result == 0) {
                    flag = false;
                    throw new GSSException("改签重新审核创建失败", "0101", "创建发生异常,请检查");
                }
                flag = true;
                /*创建新增操作日志*/
                try {
                    String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际重新审核改签单："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
                    String title ="国际重新审核改签单";
                    IftLogHelper.logger(requestWithActor.getAgent(),requestWithActor.getEntity().getSaleOrderNo(),title,logstr);
                } catch (Exception e) {
                    log.error("添加(title=国际重新审核改签单)操作日志异常===" + e);
                }
            }

        }
        return flag;
    }

    /**
     * 取消改签.
     * 拒单
     *
     * @param requestWithActor
     * @return
     */
    @Override
    public boolean cancel(RequestWithActor<Long> requestWithActor) {
        log.info("取消该签单开始============");
        Agent agent = requestWithActor.getAgent();
        Long saleChangeNo = requestWithActor.getEntity();
        if (agent == null) {
            log.error("agent 为空");
            throw new GSSException("agent 为空", "0101", "取消该签单失败");
        }
        if (saleChangeNo == null || saleChangeNo == 0) {
            log.error("saleChangeNo 为空");
            throw new GSSException("saleChangeNo 为空", "0101", "取消该签单失败");
        }

        SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeNo);

        if (saleChange == null) {
            log.error("saleChangeNo=" + saleChangeNo + ",该改签单不存在");
            throw new GSSException("saleChangeNo=" + saleChangeNo + ",该该签单不存在", "0101", "取消该签单失败");
        }
       /* if (saleChange.getPayStatus() == 3 || saleChange.getPayStatus() == 4) {
            log.error("saleChangeNo=" + saleChangeNo + ",该单状态已支付，无法取消");
            throw new GSSException("saleChangeNo=" + saleChangeNo + ",该单状态已支付，无法取消", "0101", "取消该签单失败");
        }*/
        saleChange.setChildStatus(11);
        saleChange.setModifyTime(new Date());
        saleChange.setModifier(agent.getAccount());
        try {
            saleChangeService.update(agent, saleChange);
        } catch (Exception e) {
            log.error("该签单更新失败，e=" + e);
            throw new GSSException("该签单更新失败，e=" + e, "0101", "取消该签单失败");
        }

        //将saleOrderDetail的订单详情的状态改为已取消
        SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
        if (saleChangeExt != null) {
            if (saleChangeExt.getSaleChangeDetailList() != null) {
                for (SaleChangeDetail saleChangeDetail : saleChangeExt.getSaleChangeDetailList()) {
                    SaleOrderDetail saleOrderDetail = saleOrderDetailDao.selectByPrimaryKey(saleChangeDetail.getSaleOrderDetailNo());
                    if (saleChangeDetail.getOrderDetailType() == 2) {
                        if (saleOrderDetail != null) {
                            saleOrderDetail.setStatus(String.valueOf(11));//新增航段改为已取消
                            saleOrderDetail.setModifyTime(new Date());
                            saleOrderDetail.setModifier(agent.getAccount());
                            saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
                        }
                    } else if (saleChangeDetail.getOrderDetailType() == 1) {
                        if (saleOrderDetail != null) {
                            saleOrderDetail.setStatus(String.valueOf(4));//旧航段改为待审核
                            saleOrderDetail.setModifyTime(new Date());
                            saleOrderDetail.setModifier(agent.getAccount());
                            saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
                        }
                    }
                }
            }

        }

        //修改采购废退单信息
        List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleChange.getSaleOrderNo());
        if (buyOrderList != null && buyOrderList.size() != 0) {
            BuyOrder buyOrder = buyOrderList.get(0);
            List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, buyOrder.getBuyOrderNo());
            if (buyChangeList != null) {
                for (BuyChange buyChange : buyChangeList) {
                    if (buyChange.getBusinessSignNo().equals(saleChange.getBusinessSignNo())) {
                        buyChange.setChildStatus(11);//变更为已取消
                        buyChange.setModifier(agent.getAccount());
                        buyChange.setModifyTime(new Date());
                        buyChangeService.update(agent, buyChange);
                    }
                }
            }
        }
        /*创建新增操作日志*/
        try {
            String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"取消改签："+"["+saleChange.getSaleOrderNo()+"]";
            String title ="国际改签单取消";
            IftLogHelper.logger(requestWithActor.getAgent(),saleChange.getSaleOrderNo(),title,logstr);
        } catch (Exception e) {
            log.error("添加(title=国际改签单取消)操作日志异常===" + e);
        }
        return true;
    }

    /**
     * 改签拒单
     *
     * @param saleChangeNo
     * @return
     */
    @Override
    @Transactional
    public boolean refuse(RequestWithActor<Long> saleChangeNo, String reason) {
        log.info("拒单开始");
        try {
            if (saleChangeNo.getAgent() == null) {
                throw new GSSException("当前用户不能为空", "0101", "当前操作用户为空");
            }
            if (saleChangeNo == null || saleChangeNo.getEntity() == null) {
                throw new GSSException("改签确认失败", "0102", "改签确认发生异常,请检查");
            }
            Long changeNo = saleChangeNo.getEntity().longValue();
            SaleChangeExt changeExt = saleChangeExtDao.selectByPrimaryKey(changeNo);
            changeExt.setRefuseReason(reason);
            changeExt.setAirlineStatus(4);
            saleChangeExtDao.updateByPrimaryKey(changeExt);
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(saleChangeNo.getAgent(), saleChangeNo.getEntity());

            List<PassengerChangePrice> priceList = pgerChangePriceService.getChangePriceList(saleChangeNo);
            int chaildStatus = 15;//打回
            /*if (priceListIsNoFee(priceList)) {
                chaildStatus = 11; //取消
            }*/
            //主订单状态改成 15=拒单
            saleChangeService.updateStatus(saleChangeNo.getAgent(), changeNo, chaildStatus);
            //修改采购单的状态为15
            List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(saleChangeNo.getAgent(), saleChange.getSaleOrderNo());
            if (buyOrderList != null && buyOrderList.size() > 0) {
                for (BuyOrder buyOrder : buyOrderList) {
                    if (buyOrder.getBsignType() == 4) {
                        List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(saleChangeNo.getAgent(), buyOrder.getBuyOrderNo());
                        for (BuyChange buyChange : buyChangeList) {
                            buyChangeService.updateStatus(saleChangeNo.getAgent(), buyChange.getBuyChangeNo(), chaildStatus);
                        }
                    }
                }
            }

            //原单状态恢复 TODO
            /*SaleOrderExt saleOrderExt = saleOrderExtService.
                    selectBySaleOrderNo(saleChangeNo.getAgent(), saleChange.getSaleOrderNo());
           // List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
            List<SaleChangeDetail> saleChangeDetailList = changeExt.getSaleChangeDetailList();
            for (SaleChangeDetail saleChangeDetail : saleChangeDetailList) {
                SaleOrderDetail saleOrderDetail = saleChangeDetail.getSaleOrderDetail();
                if(saleChangeDetail.getOrderDetailType() == 1){
                    saleOrderDetail.setStatus("4");
                } else{
                    saleOrderDetail.setStatus("11");
                }
                saleOrderDetail.setModifier(saleChangeNo.getAgent().getAccount());
                saleOrderDetail.setModifyTime(new Date());
                RequestWithActor<SaleOrderDetail> saleOrderDetailRequestWithActor = new RequestWithActor<>();
                saleOrderDetailRequestWithActor.setAgent(saleChangeNo.getAgent());
                saleOrderDetailRequestWithActor.setEntity(saleOrderDetail);
                saleOrderDetailService.upateSaleOrder(saleOrderDetailRequestWithActor);
            }

            saleOrderService.updateStatus(saleChangeNo.getAgent(),saleOrderExt.getSaleOrderNo(),4);*/

            try {
                mssReserveService.changeInform(saleChangeNo.getAgent(), changeNo, "2");
            } catch (Exception e) {
                log.error("改签出票回调mss接口出错==========e=" + e);
            }

            log.info("拒单结束");
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ saleChangeNo.getAgent().getAccount()+"国际改签单拒单："+"["+changeExt.getSaleChange().getSaleOrderNo()+"]";
                String title ="国际改签单拒单";
                IftLogHelper.logger(saleChangeNo.getAgent(),changeExt.getSaleChange().getSaleOrderNo(),title,logstr);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("添加(title=国际改签单拒单)操作日志异常===" + e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("改签拒单异常！！！" + e);
        }
        return true;
    }

    private boolean priceListIsNoFee(List<PassengerChangePrice> priceList) {


        boolean isNoFee = false;
        BigDecimal all = new BigDecimal(0);
        if(priceList == null )
            return true;
        for (PassengerChangePrice changePriceVo : priceList) {
            all = all.add(changePriceVo.getSalePrice());
            all = all.add(changePriceVo.getSaleTax());
            all = all.add(changePriceVo.getSaleBrokerage());
            all = all.add(changePriceVo.getSaleRest());
            all = all.add(changePriceVo.getBuyPrice());
            all = all.add(changePriceVo.getBuyTax());
            all = all.add(changePriceVo.getBuyBrokerage());
            all = all.add(changePriceVo.getBuyRest());
            all = all.add(changePriceVo.getBuyCountPrice());
        }
        BigDecimal zore = new BigDecimal("0");
        if(all.compareTo(zore) <= 0){
            isNoFee = true;
        }
        return isNoFee;

    }

    /**
     * 改签单查询.
     *
     * @param saleChangeQueryRequest
     * @return
     */
    public Page<SaleChangeExt> querySaleChange(Page<SaleChangeExt> page, RequestWithActor<SaleChangeExtVo> saleChangeQueryRequest) {
        log.info("改签单查询开始");
        List<SaleChangeExt> changeExtList = new ArrayList<>();
        try {
            if (saleChangeQueryRequest == null || saleChangeQueryRequest.getAgent().getOwner() == 0) {
                log.error("传入参数为空");
                throw new GSSException("改签单查询模块", "0201", "传入参数为空");
            }
            saleChangeQueryRequest.getEntity().setCustomerNo(saleChangeQueryRequest.getAgent().getNum());
            saleChangeQueryRequest.getEntity().setOwner(saleChangeQueryRequest.getAgent().getOwner());
            Boolean isNeedCustomer = saleChangeQueryRequest.getEntity().getCustomerCount();
            if (isNeedCustomer) {
                List<Customer> customers = customerService.getSubCustomerByCno(saleChangeQueryRequest.getAgent(), saleChangeQueryRequest.getAgent().getNum());
                List<Long> longList = new ArrayList<>();
                if (null != customers) {
                    for (Customer customer : customers) {
                        longList.add(customer.getCustomerNo());
                    }
                }
                saleChangeQueryRequest.getEntity().setLongList(longList);
            }
            List<SaleChangeExt> tempList = saleChangeExtDao.queryObjByKey(page, saleChangeQueryRequest.getEntity());
            if (null != tempList) {
                for (SaleChangeExt changeExt : tempList) {
                    SaleChange change = saleChangeService.getSaleChangeByNo(saleChangeQueryRequest.getAgent(), changeExt.getSaleChangeNo());
                    changeExt.setSaleChange(change);
                    changeExtList.add(changeExt);
                }
            }
            page.setRecords(changeExtList);
        } catch (Exception e) {
            log.error("改签单查询失败", e);
            throw new GSSException("改签单查询模块", "0201", "改签单查询失败");
        }
        log.info("改签单查询结束");
        return page;
    }

    /**
     * 根据改签单编号查询详情.
     *
     * @param saleChangeNo
     * @return
     */
    @Override
    public SaleChangeExt getSaleChange(RequestWithActor<Long> saleChangeNo) {
        log.info("查询过去改签单详情开始");
        if (saleChangeNo == null) {
            log.error("传入参数为空");
            throw new GSSException("过去改签单详情模块", "0201", "传入参数为空");
        }
        SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo.getEntity().longValue());
        if (saleChangeExt != null) {
            SaleChange change = saleChangeService.getSaleChangeByNo(saleChangeNo.getAgent(), saleChangeNo.getEntity().longValue());
            if (change != null) { saleChangeExt.setSaleChange(change); }
        }
        if (saleChangeExt != null && saleChangeExt.getChangeType() == 3) {
            saleChangeExt.setPassengerChangePriceList(passengerChangePriceDao.selectPricerByChangeNo(saleChangeExt.getSaleChangeNo()));
        }
        log.info("查询过去改签单详情结束");
        return saleChangeExt;
    }

    @Override
    public List<SaleChangeExt> getSaleChangeExt(RequestWithActor<Long> requestWithActor) {
        log.info("获取销售单开始============");
        if (requestWithActor.getEntity().longValue() == 0) {
            log.error("销售单编号为空");
            throw new GSSException("销售单编号为空", "0001", "获取销售单失败");
        }

        if (requestWithActor.getAgent() == null) {
            log.error("agent为空");
            throw new GSSException("agent为空", "0001", "获取销售单失败");
        }
        // 销售单编号
        List<SaleChangeExt> saleChangeExtList = saleChangeExtDao.queryBySaleOrderNo(requestWithActor.getEntity());
        List<SaleChangeExt> newSaleChangeExtList = new ArrayList<>();
        if (saleChangeExtList != null) {
            for (SaleChangeExt saleChangeExt : saleChangeExtList) {
                SaleChange saleChange = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeExt.getSaleChangeNo());
                if (saleChange != null) {
                    if (saleChange.getOrderChangeType() == 3) {
                        saleChangeExt.setSaleChange(saleChange);
                        newSaleChangeExtList.add(saleChangeExt);
                    }
                }
            }
        }

        log.info("获取销售单结束============");
        return newSaleChangeExtList;
    }

    /**
     * 锁住
     *
     * @param saleChange
     * @return
     */
    @Transactional
    public boolean locker(RequestWithActor<SaleChangeExtVo> saleChange,String type) {
        log.info("锁住订单开始");
        boolean flag = false;
        SaleChangeExtVo changeVo = saleChange.getEntity();
        if (changeVo == null || changeVo.getSaleChangeNo() == null) {
            log.error("传入参数为空");
            throw new GSSException("锁住销售改签单", "0202", "传入参数为空");
        }
        SaleChangeExt changeExt = saleChangeExtDao.selectByPrimaryKey(changeVo.getSaleChangeNo());
        long originLocker = changeExt.getLocker();
        long afterLocker = 0l;
        try {
            if (changeExt != null) {
                //locker 为0表示解锁  大于0表示锁定
                if (changeExt.getLocker() == null || changeVo.getLocker() == 1) {
                    afterLocker = saleChange.getAgent().getId();
                    changeExt.setLocker(afterLocker);
                    changeExt.setAloneLocker(afterLocker);
                    changeExt.setModifier(saleChange.getAgent().getAccount());
                    changeExt.setLockTime(new Date());
                    changeExt.setModifyTime(new Date());

                } else {
                    changeExt.setLocker(0L);
                    changeExt.setAloneLocker(0l);
                    changeExt.setModifier(saleChange.getAgent().getAccount());
                    changeExt.setLockTime(new Date());
                    changeExt.setModifyTime(new Date());
                }
                int updateLocker = changeExtService.updateLocker(changeExt);
                //更新锁定前出票员
                ticketSenderService.updateByLockerId(saleChange.getAgent(),originLocker,"SALE_CHANGE_NUM");
                ticketSenderService.updateByLockerId(saleChange.getAgent(),originLocker,"BUY_CHANGE_NUM");
                //更新锁定后的出票员
                ticketSenderService.updateByLockerId(saleChange.getAgent(),afterLocker,"SALE_CHANGE_NUM");
                ticketSenderService.updateByLockerId(saleChange.getAgent(),afterLocker,"BUY_CHANGE_NUM");
                if (updateLocker == 1) {
                    flag = true;
                }
            }
            log.info("锁住订单结束");
            /*创建新增操作日志*/
            try {
                SaleOrder saleOrder = saleOrderService.getSOrderByNo(saleChange.getAgent(), changeVo.getSaleChangeNo());
                Long saleOrderNo = changeVo.getSaleChangeNo();
                String logstr ="用户"+ saleChange.getAgent().getAccount()+"国际改签单锁定："+"["+saleOrderNo+"]";
                String title = "国际改签单锁定";
                IftLogHelper.logger(saleChange.getAgent(),saleOrderNo,title,logstr);
            } catch (Exception e) {
                log.error("添加(title=国际改签单锁定)操作日志异常===", e);
            }
        } catch (Exception e) {
            log.error("锁住销售改签单异常", e);
            throw new GSSException("锁住销售改签单异常", "0202", "锁住销售改签单异常");
        }
        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int updateLocker(SaleChangeExt changeExt) {
        return saleChangeExtDao.updateLocker(changeExt);
    }

    @Override
    public void updateBuyRemarkBySelectBuyChangeNo(BuyChangeExt buyChangeExt) {
        try {
            //根据改签单号查询改签单
            BuyChangeExt buyChangeExtBySaleChange = changeExtService.getBuyChangeExtBySaleChangeNo(buyChangeExt.getBuyChangeNo());
            String changeRemark = buyChangeExt.getChangeRemark();
            if (buyChangeExtBySaleChange != null) {
                if (buyChangeExtBySaleChange.getChangeRemark() != null && "" != buyChangeExtBySaleChange.getChangeRemark())
                    buyChangeExt.setChangeRemark(buyChangeExtBySaleChange.getChangeRemark() + "#$" + changeRemark);
                buyChangeExtDao.updateBuyRemarkBySelectBuyChangeNo(buyChangeExt);
            } else {
                buyChangeExtDao.updateBuyRemarkBySelectBuyChangeNo(buyChangeExt);
            }
        }catch (Exception e){
            log.error("采购备注更新异常", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int queryChangeCountBylocker(Long userId) {
        int lockCount = saleChangeExtDao.queryChangeCountBylocker(owner, userId);
        return lockCount;
    }

    /**
     * 改签单出单确认
     * 状态改成10=已改签
     * 支付状态改成3=已支付
     */
    public boolean changeConfirm(RequestWithActor<ChangePriceRequest> requestWithActor) {
        log.info("改签确认操作开始");
        if (requestWithActor.getAgent() == null) {
            log.error("当前用户为空");
            throw new GSSException("当前用户为空", "0101", "当前用户为空");
        }
        if (requestWithActor.getEntity() == null) {
            log.error("必要参数为空");
            throw new GSSException("必要参数为空", "0102", "必要参数为空");
        }
        boolean flag = false;
        try {
            Long saleChangeNo = requestWithActor.getEntity().getSaleChangeNo();
            List<ChangePriceVo> priceList = requestWithActor.getEntity().getBuyPriceList();
            for (ChangePriceVo priceVo : priceList) {
                List<Long> detailNoList = priceVo.getSaleOrderDetailNoList();
                //List<String> ticketNoList = priceVo.getTicketNoList();
                for (int i = 0; i < detailNoList.size(); i++) {
                    SaleOrderDetail orderDetail = saleOrderDetailDao.selectByPrimaryKey(detailNoList.get(i));
                    SaleChangeDetail changeDetail = saleChangeDetailDao.selectBySaleOrderDetailNoAndSaleChangeNo(orderDetail.getSaleOrderDetailNo(),saleChangeNo);
                    if(changeDetail.getOrderDetailType() == 2){
                        //新增航段才把状态改为出票
                        orderDetail.setOwner(requestWithActor.getAgent().getOwner());
                        orderDetail.setModifyTime(new Date());
                        orderDetail.setModifier(requestWithActor.getAgent().getAccount());
                        orderDetail.setStatus("4");//将改签的新行程变为已出票
                        orderDetail.setTicketNo(priceVo.getTicketNo());
                        saleOrderDetailDao.updateByPrimaryKeySelective(orderDetail);
                    }
                }
                PassengerChangePrice passengerChangePrice = new PassengerChangePrice();
                passengerChangePrice.setChangePriceNo(priceVo.getChangePriceNo());
                passengerChangePrice.setBuyRebate(priceVo.getBuyRebate());
                passengerChangePrice.setBuyAgencyFee(priceVo.getBuyAgencyFee());
                passengerChangePrice.setBuyBrokerage(priceVo.getBuyBrokerage());
                passengerChangePrice.setBuyCountPrice(priceVo.getBuyCountPrice());
                passengerChangePrice.setBuyPrice(priceVo.getBuyPrice());
                passengerChangePrice.setBuyRest(priceVo.getBuyRest());
                passengerChangePrice.setBuyTax(priceVo.getBuyTax());
                passengerChangePrice.setBuyCurrency(priceVo.getBuyCurrency());
                passengerChangePrice.setBuyExchangeRate(priceVo.getBuyExchangeRate());
                passengerChangePriceDao.updateByPrimaryKeySelective(passengerChangePrice);

            }
            saleChangeService.updateStatus(requestWithActor.getAgent(), saleChangeNo, 10);

            try {
                mssReserveService.changeInform(requestWithActor.getAgent(), saleChangeNo, "2");
            } catch (Exception e) {
                log.error("改签出票回调mss接口出错==========e=" + e);
            }
            
            /*创建新增操作日志*/
            try {
                SaleChange change = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeNo);
                String logstr ="用户"+requestWithActor.getAgent().getAccount()+"国际改签单出单确认："+"["+change.getSaleOrderNo()+"]";
                String title = "国际改签单出单确认";
                IftLogHelper.logger(requestWithActor.getAgent(),change.getSaleOrderNo(),title,logstr);

            } catch (Exception e) {
                log.error("添加(title=国际改签单出单确认)操作日志异常===" + e);
            }
            flag = true;
        } catch (Exception e) {
            log.error("改签异常",e);
        }
        log.info("改签确认操作结束");
        return flag;
    }

    /**
     * 更改客商时需要创建(BuyOrder/BuyOrderExt/BuyOrderDetail)
     */
    public boolean createBuyOrder(RequestWithActor<ChangeCreateVo> requestWithActor) {
        try {
            if (requestWithActor.getEntity().getBuyOrder().getBuyOrderNo() == null || requestWithActor.getEntity().getBuyOrder().getBuyOrderNo() == 0) {
                log.error("采购单编号为空");
                throw new GSSException("采购单编号为空", "0001", "创建采购单失败");
            }
            //StringBuffer ticketNoArray = new StringBuffer();
            if (requestWithActor.getEntity().getBuyOrder() != null) {
                Long buyOrderNo = maxNoService.generateBizNo("IFT_BUY_ORDER_NO", 24);
                //创建采购单
                requestWithActor.getEntity().getBuyOrder().setBuyOrderNo(buyOrderNo);
                requestWithActor.getEntity().getBuyOrder().setSupplierNo(requestWithActor.getEntity().getSupplierNo());
                requestWithActor.getEntity().getBuyOrder().setSupplierTypeNo(requestWithActor.getEntity().getSupplierTypeNo());
                requestWithActor.getEntity().getBuyOrder().setBsignType(4);
                requestWithActor.getEntity().getBuyOrder().setBuyer(requestWithActor.getAgent().getAccount());
                requestWithActor.getEntity().getBuyOrder().setPayStatus(null);
                requestWithActor.getEntity().getBuyOrder().setBuyStatus(null);
                requestWithActor.getEntity().getBuyOrder().setBuyChildStatus(null);
                requestWithActor.getEntity().getBuyOrder().setBusinessSignNo(requestWithActor.getEntity().getBusinessSignNo());
                buyOrderService.create(requestWithActor.getAgent(), requestWithActor.getEntity().getBuyOrder());
                //创建采购单拓展
                BuyOrderExt buyOrderExt = buyOrderExtService.selectByBuyOrderNo(requestWithActor.getAgent(), buyOrderNo);
                if (buyOrderExt != null) {
                    buyOrderExt.setBuyOrderNo(buyOrderNo);
                    buyOrderExt.setSupplierNo(requestWithActor.getEntity().getSupplierNo());
                    buyOrderExt.setSupplierTypeNo(requestWithActor.getEntity().getSupplierTypeNo());
                    buyOrderExt.setCreateTime(new Date());
                    buyOrderExt.setCreator(requestWithActor.getAgent().getAccount());
                    buyOrderExtDao.insertSelective(buyOrderExt);
                    //创建采购单详情
                    if (buyOrderExt.getBuyOrderDetailList() != null) {
                        for (BuyOrderDetail buyOrderDetail : buyOrderExt.getBuyOrderDetailList()) {
                            buyOrderDetail.setBuyOrderDetailNo(maxNoService.generateBizNo("IFT_BUY_ORDER_DETAIL_NO", 23));
                            buyOrderDetail.setBuyOrderNo(buyOrderNo);
                            buyOrderDetail.setTicketTime(new Date());
                            buyOrderDetail.setStatus("1");
                            buyOrderDetail.setCreator(requestWithActor.getAgent().getAccount());
                            buyOrderDetail.setCreateTime(new Date());
                            buyOrderDetail.setModifier(null);
                            buyOrderDetail.setModifyTime(null);
                            buyOrderDetailDao.insertSelective(buyOrderDetail);
                        }
                    }
                }
            }
            /*创建新增操作日志*/
            try {
                String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"创建国际改签采购单："+"["+requestWithActor.getEntity().getBuyOrderNo()+"]";
                String title = "创建国际改签采购单";
                IftLogHelper.logger(requestWithActor.getAgent(),requestWithActor.getEntity().getBuyOrderNo(),title,logstr);
            } catch (Exception e) {
                log.error("添加(title=创建国际改签采购单)操作日志异常===" + e);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return true;

    }

    /**
     * 订单改签通知
     *
     * @param orderInformVo
     * @return
     */
    @Override
    public int orderChangeInform(OrderInformVo orderInformVo) {
        BuyOrderExt buyOrderExt = new BuyOrderExt();
        SaleChangeExt saleChangeExt = new SaleChangeExt();
        SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
        SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
        PassengerChangePrice passengerChangePrice = new PassengerChangePrice();
        if (orderInformVo.getInformType() == 2 || orderInformVo.getInformType() == 4) {
            saleChangeDetail.setTicketNo(orderInformVo.getTicketNo());
            saleChangeDetail.setSaleChangeNo(orderInformVo.getSaleOrderNo());

            saleOrderDetail.setStatus(orderInformVo.getStatus());
            saleOrderDetail.setChangeOrderNo(orderInformVo.getSaleOrderNo());

            buyOrderExt.setChangeOrderNo(orderInformVo.getSaleOrderNo());
            buyOrderExt.setSupplierNo(orderInformVo.getSupplierNo());

            saleChangeExt.setChangeRemark(orderInformVo.getSaleRemark());
            saleChangeExt.setSaleChangeNo(orderInformVo.getSaleOrderNo());

            saleChangeDetailDao.updateByChangeOrderNo(saleChangeDetail);
            saleOrderDetailDao.updateByChangeOrderNo(saleOrderDetail);
            buyOrderExtDao.updateByChangeOrderNo(buyOrderExt);
            saleChangeExtDao.updateByPrimaryKey(saleChangeExt);
        } else if (orderInformVo.getInformType() == 3) {
            saleChangeExt.setChangeRemark(orderInformVo.getSaleRemark());
            saleChangeExt.setSaleChangeNo(orderInformVo.getSaleOrderNo());

            passengerChangePrice.setBuyBrokerage(orderInformVo.getBuyBrokerage());
            passengerChangePrice.setSaleChangeNo(orderInformVo.getSaleOrderNo());
            passengerChangePriceDao.updateByChangeOrderNo(passengerChangePrice);
            saleChangeExtDao.updateByPrimaryKey(saleChangeExt);
        } else if (orderInformVo.getInformType() == 5) {
            saleOrderDetail.setStatus(orderInformVo.getStatus());
            saleOrderDetail.setChangeOrderNo(orderInformVo.getSaleOrderNo());
            saleOrderDetailDao.updateByChangeOrderNo(saleOrderDetail);

            saleChangeExt.setSaleChangeNo(orderInformVo.getSaleOrderNo());
            saleChangeExt.setChangeType(orderInformVo.getChangeType());
            saleChangeExt.setRefuseReason(orderInformVo.getRefuseReason());
            saleChangeExtDao.updateByPrimaryKey(saleChangeExt);
        }
        return 0;
    }

    @Override
    public int updateByPrimarykey(SaleChangeExt salechangeExt) {
        log.info("更新销售变更单扩展表信息"+salechangeExt.toString());
        return saleChangeExtDao.updateByPrimaryKey(salechangeExt);
    }

    @Override
    public BuyChangeExt getBuyChangeExtBySaleChangeNo(Long saleChangeNo) {
        //return buyChangeExtDao.selectBySaleChangeNo(saleChangeNo);
        return buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
    }

    @Override
    public void clearLockerAndUpdateOldLocker(RequestWithActor<Long> requestWithActor,String type) {
        //查询出之前的saleOrderExt保存之前的oldLocker
        SaleChangeExt changeExt = changeExtService.getSaleChange(requestWithActor);
        Long oldLocker = changeExt.getLocker();
        //赋值locker为之前销售审核id更新saleOrderExt
        User user = userService.findUserByLoginName(requestWithActor.getAgent(), changeExt.getModifier());
        changeExt.setLocker(user.getId());
        changeExt.setLockTime(new Date());
        saleChangeExtDao.updateLocker(changeExt);
        //更新之前保存的oldLocker的数量
        ticketSenderService.updateByLockerId(requestWithActor.getAgent(),oldLocker,type);
    }

    /**
     * 采购改签订单分单
     */
    @Override
    @Transactional
    public void assignChange(RequestWithActor<Long> requestWithActor) {
        Long changeOrderNo = requestWithActor.getEntity();
        List<SaleChangeExt> saleChangeExts = null;
        SaleChangeExt saleChangeExt = null;
        if (changeOrderNo == 0L|| changeOrderNo==null) {
            log.info("定时任务分采购改签单开始,第一步：查询符合条件的采购改签订单...");
            saleChangeExts = saleChangeExtDao.queryChangeBylocker(owner, 0l);
            if (saleChangeExts != null && saleChangeExts.size() > 0) {
                log.info("查询到" + saleChangeExts.size() + "条可分配订单...");
            } else {
                log.info("未查询到可以分配的采购改签订单,结束此次任务...");
                return;
            }
        } else {
            log.info("直接将采购改签单分给在线业务员员，单号:{}", changeOrderNo);
            saleChangeExt = this.getSaleChangeExtByNo(requestWithActor);
        }
        Agent agent = new Agent(Integer.valueOf(owner));
        if(!configsService.getIsDistributeTicket(agent)){
            //如果不是系统分单
            if ((changeOrderNo == null || changeOrderNo == 0L) && saleChangeExts != null) {
                taskAssign(saleChangeExts,null,null,requestWithActor.getAgent(),null);
            }else{
                derectAssign(saleChangeExt, null, null, requestWithActor.getAgent(), null);
            }
            log.info("此次分单结束...");
            return ;
        }
        log.info("第二步：查询在线采购改签员...");
        List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-change");  //采购改签员
        log.info("是否有在线出票员:" + (senders != null));
        if (senders != null && senders.size() > 0) {

            IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
            Map config = configs.getConfig();
            String str_maxOrderNum = (String) config.get("maxOrderNum");
            log.info("获得配置最大分单数：" + str_maxOrderNum);
            Long maxOrderNum = Long.valueOf(str_maxOrderNum);
            Date updateTime = new Date();
            if ((changeOrderNo == null || changeOrderNo == 0L) && saleChangeExts != null) {
                taskAssign(saleChangeExts, senders, maxOrderNum, agent, updateTime);
            } else {
                derectAssign(saleChangeExt, senders, maxOrderNum, agent, updateTime);
            }
            log.info("此次分单结束...");
        } else {
            log.info("未查询在线出票员...");
        }
    }



    public void increaseBuyChangeNum(Agent agent, Long lockerId) {
       // User user = userService.findUserByLoginName(agent, lockerId.getUserid());
        ticketSenderService.updateByLockerId(agent,lockerId,"BUY_CHANGE_NUM");

       /* int lockCount = changeExtService.queryChangeCountBylocker(user.getId());
        lockerId.setBuyChangeNum(lockCount);
        lockerId.setIds(lockerId.getId() + "");
        iTicketSenderService.update(lockerId);*/
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long assingLockSaleChangeExt(SaleChangeExt order, TicketSender sender, Date date, Agent agent) {
        User user = userService.findUserByLoginName(agent, sender.getUserid());
        Long buyLockerId = null;
        BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(order.getSaleChangeNo());
        if(user != null){
            buyLockerId = user.getId();
            if(buyChangeExt != null){
                if(buyChangeExt.getBuyLocker() == null || buyChangeExt.getBuyLocker() == 0l){
                    buyChangeExt.setBuyLocker(buyLockerId);
                    buyChangeExt.setModifyTime(date);
                    buyChangeExtService.updateBuyChangeExt(buyChangeExt);
                    sender.setBuyChangeNum(sender.getBuyChangeNum()+1);
                }
            }
        }
        return buyChangeExt.getBuyLocker();
       /* order.setLocker(user.getId());
        order.setLockTime(date);
        saleChangeExtDao.updateLocker(order);*/
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void assingAloneLockSaleChangeExt(SaleChangeExt order, Date date, Agent agent) {
        BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(order.getSaleChangeNo());
        if(buyChangeExt != null){
            buyChangeExt.setBuyLocker(order.getAloneLocker());
            buyChangeExt.setModifyTime(date);
            buyChangeExtService.updateBuyChangeExt(buyChangeExt);
        }
    }

    /**
     * 定时任务将单分给在线业务员
     * @param saleChangeExts
     * @param senders
     * @param maxOrderNum
     * @param agent
     * @param updateTime
     */
    private void taskAssign(List<SaleChangeExt> saleChangeExts, List<TicketSender> senders, Long maxOrderNum,Agent agent,Date updateTime){
        log.info("第三步：判断出票员手头出票订单数量...");
        for (SaleChangeExt order : saleChangeExts) {
            if(!configsService.getIsDistributeTicket(agent)){
                //如果不是系统分单
                assingAloneLockSaleChangeExt(order,new Date(),agent);
                //TicketSender ticketSender = iTicketSenderService.queryByUserId(order.getAloneLocker());
                increaseBuyChangeNum(agent, order.getAloneLocker());
            } else{
                for (TicketSender peopleInfo : senders) {
                    log.info(peopleInfo.getName() + "未处理采购改签单数量：" + peopleInfo.getBuyChangeNum());
                    if (peopleInfo.getBuyChangeNum() >= maxOrderNum) {
                        continue;
                    } else {
                        log.info("第四步:分单...");
                        /**锁单*/
                        log.info("1.锁单,锁单人是被分配人...");
                        Long buyLockerId = assingLockSaleChangeExt(order, peopleInfo, updateTime, agent);
                        /***增加出票人订单数*/
                        log.info("2.增加出票人的未处理采购改签单数量...");
                        increaseBuyChangeNum(agent, buyLockerId);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 直接将改签单分给在线业务员
     * @param saleChangeExt
     * @param senders
     * @param maxOrderNum
     * @param agent
     * @param updateTime
     */
    private void derectAssign(SaleChangeExt saleChangeExt,List<TicketSender> senders, Long maxOrderNum,Agent agent,Date updateTime){
        log.info("第三步：判断出票员手头出票订单数量...");
        if(!configsService.getIsDistributeTicket(agent)){
            //如果不是系统分单
            assingAloneLockSaleChangeExt(saleChangeExt,new Date(), agent);
            /*saleChangeExt = configsService.setLockerAsAloneLocker(saleChangeExt);
            Long locker = saleChangeExt.getLocker();
            TicketSender ticketSender = iTicketSenderService.queryByUserId(locker);*/
            increaseBuyChangeNum(agent, saleChangeExt.getAloneLocker());
        } else{
            for (TicketSender peopleInfo : senders) {
                log.info(peopleInfo.getName() + "未处理采购改签单数量：" + peopleInfo.getBuyChangeNum());
                if (peopleInfo.getBuyChangeNum() >= maxOrderNum) {
                    continue;
                } else {
                    log.info("第四步:分单...");
                    /**锁单*/
                    log.info("1.锁单,锁单人是被分配人...");
                    Long buyLockerId = assingLockSaleChangeExt(saleChangeExt, peopleInfo, updateTime, agent);
                    /***增加出票人订单数*/
                    log.info("2.增加出票人的未处理采购改签单数量...");
                    increaseBuyChangeNum(agent, buyLockerId);
                    break;
                }
            }
        }
    }

    //改签扩展表锁单员设置
    private SaleChangeExt setChangeOrderLocker(SaleChangeExt saleChangeExt,Agent agent,SaleOrder saleOrder){
        if(StringUtils.isNotBlank(saleOrder.getSourceChannelNo()) && StringUtils.equals("OP",saleOrder.getSourceChannelNo())){
            User user = userService.findUserByLoginName(agent,agent.getAccount());
            if(user!=null) {
                saleChangeExt.setLocker(agent.getId());
                saleChangeExt.setAloneLocker(agent.getId());
            }
        }
        return  saleChangeExt;
    }

}

package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.gson.Gson;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mq.MqSender;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.CostType;
import com.tempus.gss.order.entity.vo.ActualInfoSearchVO;
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
import com.tempus.gss.product.ift.mq.IftTicketMqSender;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class RefundServiceImpl implements IRefundService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SaleOrderExtDao saleOrderExtDao;

	@Autowired
	ISaleOrderExtService saleOrderExtService;

	@Autowired
	SaleChangeExtDao saleChangeExtDao;

	@Reference
	IBuyChangeService buyChangeService;

	@Reference
	IMaxNoService maxNoService;

	@Autowired
	SaleOrderDetailDao saleOrderDetailDao;

	@Autowired
	PassengerRefundPriceDao passengerRefundPriceDao;
	
	@Autowired
	PassengerDao passengerDao;

	// 应收应付
	@Reference
	IPlanAmountRecordService planAmountRecordService;

	@Reference
	ISaleChangeService saleChangeService;

	@Reference
	ISaleOrderService saleOrderService;

	@Reference
	IBuyOrderService buyOrderService;

	@Autowired
	BuyOrderExtDao buyOrderExtDao;

	@Autowired
	BuyChangeExtDao buyChangeExtDao;

	@Autowired
	PnrDao pnrDao;

	@Autowired
	SaleChangeDetailDao saleChangeDetailDao;

	@Reference
	IBuyOrderExtService buyOrderExtService;

	@Reference
	ICertificateService certificateService;

	@Reference
	IActualAmountRecorService actualAmountRecorService;

	@Reference
	ITransationOrderService transationOrderService;
	
	@Reference
	ISupplierService supplierService;
	@Reference
	IRefundService refundService;

	@Reference
	ILogService logService;
	@Reference
	ICustomerService customerService;
	@Reference
	IDifferenceOrderService differenceOrderService;
	@Autowired
	IftTicketMqSender iftTicketMqSender;
	@Reference
	IConfigsService configsService;
	@Autowired
	MqSender mqSender;
	@Reference
	ITicketSenderService iTicketSenderService;
	@Value("${dpsconfig.job.owner}")
	protected String owner;
	@Reference
	IIftMessageService	IIftMessageService;
	@Reference
	ISaleOrderDetailService detailService;
	@Reference
	ITicketSenderService ticketSenderService;
	@Reference
	IUserService userService;
	@Reference
	IPassengerRefundPriceService passengerRefundPriceService;
	@Reference
	IPassengerChangePriceService passengerChangePriceService;
	@Reference
	IftBuyChangeExtService buyChangeExtService;

	SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	@Override
	public boolean createRefund(RequestWithActor<RefundCreateVo> requestWithActor) {
		boolean flag=false;
		try{
			SaleChangeExt ext=this.createRefundExt(requestWithActor);
			//退废分单
			if(ext == null){
				flag = false;
			}
			flag=true;
		}catch(Exception e){
			flag = false;
			throw new GSSException("创建改签单失败", "0003", "创建改签单失败"+e);
		}
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				//查询当前单是否OP下单如果是直接指派当前业务员
				//销售退废分单操作 (开启线程分单，因为不结束这个方法，数据库中的saleChangeOrder未生成，在其他dubbo服务空间内无法查找到)
				Integer refundType = requestWithActor.getEntity().getRefundType();
				String refundTypeStr = "";
				if(new Integer(1).equals(refundType)){
					//废票
					refundTypeStr ="salesman-waste";
				} else {
					refundTypeStr ="salesman-refund";
				}
				IIftMessageService.sendRefuseMessage(requestWithActor.getEntity().getSaleOrderNo(),requestWithActor.getAgent().getOwner()+"",refundTypeStr);
			}
		}).start();*/
		return flag;
	}

	@Override
	public SaleChange createAjustSaleOrder(RequestWithActor<SaleOrderExt> saleOrderExt, Integer IncomeExpenseType){
		log.info("创建杂费单的销售改签单开始===========");
		/* 创建销售废退拓展单 */
		SaleChange saleChange = new SaleChange();
		Agent agent = saleOrderExt.getAgent();
		SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderExt.getEntity().getOriginalOrderNo());
		Long businessSignNo = IdWorker.getId();
		Long saleChangeNo = maxNoService.generateBizNo("IFT_SALE_CHANGE_EXT_NO", 38);
		saleChange.setSaleChangeNo(saleChangeNo);
		saleChange.setOrderChangeType(3);//3为改签
		saleChange.setSaleOrderNo(saleOrderExt.getEntity().getOriginalOrderNo());
		saleChange.setBusinessSignNo(businessSignNo);
		saleChange.setBsignType(5);
		saleChange.setOwner(agent.getOwner());
		saleChange.setCreateTime(new Date());
		saleChange.setChildStatus(1);//1.待审核 2.已审核 3.退票中 废票中 改签中 10.已完成  11.已取消
		saleChange.setGoodsType(2);//商品大类 2=国际机票
		saleChange.setGoodsSubType(13);//销改
		saleChange.setGoodsName("");//TODO
		saleChange.setTransationOrderNo(saleOrder.getTransationOrderNo());//交易号
		saleChange.setIncomeExpenseType(IncomeExpenseType); //根据杂费单填金额的正负设置支或者收
		saleChangeService.create(agent,saleChange);
		return saleChange;
	}

	@Override
	@Transactional
	public SaleChangeExt createRefundExt(RequestWithActor<RefundCreateVo> requestWithActor) {
		log.info("创建废退单申请开始===========");

		if (requestWithActor.getEntity() == null) {
			log.error("创建废退单参数不满足");
			throw new GSSException("创建废退单参数不满足", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getPassengerLegVoList() == null
				|| requestWithActor.getEntity().getPassengerLegVoList().size() == 0) {
			log.error("废退单行程信息为空");
			throw new GSSException("废退单行程信息为空(PassengerLegVoList)", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getRefundType() == null || requestWithActor.getEntity().getRefundType() == 0) {
			log.error("废退类型为空");
			throw new GSSException("废退类型为空(RefundType)", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getContactName() == null) {
			log.error("废退单申请人名称为空");
			throw new GSSException("废退单申请人名称为空（ContactName）", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getContactPhone() == null) {
			log.error("废退单申请人手机号名称为空");
			throw new GSSException("废退单申请人手机号名称为空（ContactPhone）", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getRefundType() == 2 && requestWithActor.getEntity().getRefundWay() == null) {
			log.error("退票时退票方式为空");
			throw new GSSException("退票时退票方式为空（RefundWay）", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getRefundReason() == null) {
			log.error("废退原因为空");
			throw new GSSException("废退原因为空（RefundReason）", "0001", "创建废退单失败");
		}
		if (requestWithActor.getEntity().getSaleOrderNo() == null) {
			log.error("销售单编号为空");
			throw new GSSException("销售单编号为空（SaleOrderNo）", "0001", "创建废退单失败");
		}
		/* 创建销售废退拓展单 */
		SaleChangeExt saleChangeExt = new SaleChangeExt();
		Date createTime = new Date();

		try {
			Long businessSignNo = IdWorker.getId();
			Agent agent = requestWithActor.getAgent();
			Long saleChangeNo = maxNoService.generateBizNo("IFT_SALE_CHANGE_EXT_NO", 38);
			/* 查询出销售单拓展表 */
			SaleOrderExt saleOrderExt = saleOrderExtService.selectBySaleOrderNo(agent,
					requestWithActor.getEntity().getSaleOrderNo());

			// 创建销售废退单明细
			List<SaleOrderDetail> saleOrderDetailList = new ArrayList<>();
			for (PassengerLegVo passengerLegVo : requestWithActor.getEntity().getPassengerLegVoList()) {
				SaleOrderDetail saleOrderDetail = saleOrderDetailDao
						.selectByPassengerAndLeg(passengerLegVo.getPassengerNo(), passengerLegVo.getLegNo());
				if (saleOrderDetail == null) {
					log.error("根据人编号、行程编号查询人+航段信息为空 passengerNo=" + passengerLegVo.getPassengerNo() + ",legNo="
							+ passengerLegVo.getLegNo());
					throw new GSSException("根据人编号、行程编号查询人+航段信息为空 passengerNo=" + passengerLegVo.getPassengerNo()
							+ ",legNo=" + passengerLegVo.getLegNo(), "0002", "创建废退单失败");
				}
				if (!"4".equals(saleOrderDetail.getStatus())) {
					log.error("该行程不是已出票状态，无法申请废退");
					throw new GSSException("该行程不是已出票状态，无法申请废退", "0008", "创建废退单失败");
				}
				saleOrderDetailList.add(saleOrderDetail);

			}

			List<BuyOrderExt> buyOrderExtList = buyOrderExtService.selectListBySaleOrderNo(agent,
					requestWithActor.getEntity().getSaleOrderNo());

			Long buyChangeNo = 0L;
			String ticketType = "BSP";
			//取最新的采购商
			if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
				BuyOrderExt buyOrderExt = buyOrderExtList.get(0);
				/* 创建采购废退单 */
				BuyChange buyChange = new BuyChange();
				buyChangeNo = maxNoService.generateBizNo("IFT_BUY_CHANGE_NO", 47);
				buyChange.setBuyChangeNo(buyChangeNo);
				buyChange.setOrderChangeType(requestWithActor.getEntity().getRefundType());
				buyChange.setBusinessSignNo(businessSignNo);
				if(requestWithActor.getEntity().getRefundType()==1) {
					buyChange.setBsignType(3);
				}
				if(requestWithActor.getEntity().getRefundType()==2) {
					buyChange.setBsignType(4);
				}
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(createTime);
				buyChange.setChildStatus(1);// 1.待审核 2.已审核 3.处理中
				// 10.已处理 11.已取消
				buyChange.setGoodsType(2);// 商品大类 2=国际机票
				buyChange.setGoodsSubType(22);// 采购退单
				buyChange.setGoodsName("");// TODO
				buyChange.setIncomeExpenseType(1);// 收支类型 1.收 2.支
				buyChange.setBuyOrderNo(buyOrderExt.getBuyOrderNo());
				buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if(StringUtil.isNotNullOrEmpty(buyOrderExt.getTicketType())){
					ticketType = buyOrderExt.getTicketType();
				}

				for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {

					/* 创建销售废退单明细 */
						SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
						saleChangeDetail
								.setSaleChangeDetailNo(maxNoService.generateBizNo("IFT_SALE_CHANGE_DETAIL_NO", 49));
						saleChangeDetail.setSaleChangeNo(saleChangeNo);
						saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
						saleChangeDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
						saleChangeDetail.setOwner(agent.getOwner());
						saleChangeDetail.setCreateTime(createTime);
						saleChangeDetail.setCreator(String.valueOf(agent.getAccount()));
						saleChangeDetail.setValid((byte) 1);
						saleChangeDetailDao.insertSelective(saleChangeDetail);
					}
				}

			/* 创建销售废退单 */
			SaleChange saleChange = new SaleChange();
			saleChange.setSaleChangeNo(saleChangeNo);
			saleChange.setSaleOrderNo(requestWithActor.getEntity().getSaleOrderNo());
			/* 设置销售改签单的改签类型 */
			saleChange.setOrderChangeType(requestWithActor.getEntity().getRefundType());
			saleChange.setBusinessSignNo(businessSignNo);
			saleChange.setBsignType(3);// 1销采 2换票 3废和退 4改签
			saleChange.setOwner(agent.getOwner());
			saleChange.setCreateTime(createTime);
			saleChange.setChildStatus(1);// 1.待审核 2.已审核 3.退票中 废票中 改签中 10.已完成
			// 11.已取消
			saleChange.setChangeReason(requestWithActor.getEntity().getRefundReason());
			saleChange.setKeepSeat(requestWithActor.getEntity().getKeepSeat());// 默认 0.不留席位,1 留席位
			saleChange.setGoodsType(2);// 商品大类 2=国际机票
			saleChange.setGoodsSubType(12);// 销售退单
			saleChange.setGoodsName("");// TODO
			saleChange.setTransationOrderNo(saleOrderExt.getSaleOrder().getTransationOrderNo());// 交易号
			saleChange.setIncomeExpenseType(2);// 收支类型 1.收 2.支
			saleChangeService.create(requestWithActor.getAgent(), saleChange);

			/* 设置销售单拓展编号 */
			saleChangeExt.setSaleChangeNo(saleChangeNo);
			/* 设置废退方式 1.自愿、2.非自愿 */
			saleChangeExt.setRefundWay(requestWithActor.getEntity().getRefundWay());
			saleChangeExt.setOwner(agent.getOwner());
			saleChangeExt.setCreateTime(createTime);
			User user = userService.findUserByLoginName(agent,agent.getAccount());
			/*if(user!=null) {
				saleChangeExt.setLocker(user.getId());
			}*/
			saleChangeExt.setCreator(String.valueOf(agent.getAccount()));
			saleChangeExt.setChangeType(requestWithActor.getEntity().getRefundType());
			saleChangeExt.setContactName(requestWithActor.getEntity().getContactName());
			saleChangeExt.setContactMobile(requestWithActor.getEntity().getContactPhone());
			saleChangeExt.setValid((byte) 1);
			saleChangeExt.setRefundWay(requestWithActor.getEntity().getRefundWay() == null ? 0
					: requestWithActor.getEntity().getRefundWay());
			saleChangeExt.setRefundFileUrl(requestWithActor.getEntity().getRefundFileUrl());
			saleChangeExt.setAirlineStatus(1);//航司状态设置为1，待审核
			saleChangeExt.setCustomerRemark(requestWithActor.getEntity().getCustomerRemark());//用户备注
			Long customerNo = agent.getNum();
			Long customerTypeNo = agent.getType();
			SaleOrder saleOrder = saleOrderExt.getSaleOrder();
			if(customerNo==null){
				customerNo = saleOrder.getCustomerNo();
			}
			if(customerTypeNo == null){
				customerTypeNo = saleOrder.getCustomerTypeNo();
			}
			saleChangeExt.setCustomerNo(customerNo);
			saleChangeExt.setCustomerTypeNo(customerTypeNo);
			setExtLockerVal(saleOrder,agent,saleChangeExt);
			setChangeOrderLocker(saleChangeExt,agent,saleOrder);
			log.info("申请退费单时保存的退费单信息:{}",saleChangeExt.toString());
			saleChangeExtDao.insertSelective(saleChangeExt);
			//创建采购变更单
			createBuyChangeExt(createTime,agent,buyChangeNo,saleOrderExt.getOffice(),ticketType);

			//废退分单（如果该单不是OP下单才会执行分单）
			String refundTypeStr = "";
			if(new Integer(1).equals(requestWithActor.getEntity().getRefundType())){
				//废票
				refundTypeStr ="salesman-waste";
			} else {
				refundTypeStr ="salesman-refund";
			}
			IIftMessageService.sendRefuseMessage(requestWithActor.getEntity().getSaleOrderNo(),requestWithActor.getAgent().getOwner()+"",refundTypeStr);
			//如果在setChangeOrderLocker方法中设置了locker那么在sendChangeMessage则查询不到该订单，所以需要在跟新一次该出票员的销售改签数量
			setTicketSenderNum(saleChangeExt,agent,saleOrder);
			/* 修改销售单明细 */
			if (requestWithActor.getEntity().getPassengerLegVoList() != null
					&& requestWithActor.getEntity().getPassengerLegVoList().size() != 0) {
				for (SaleOrderDetail saleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
					for (PassengerLegVo passengerLegVo : requestWithActor.getEntity().getPassengerLegVoList()) {
						/* 判断传入需要废退的航班+人的信息是否跟订单的信息一致 */
						if (saleOrderDetail.getLegNo().equals(passengerLegVo.getLegNo())
								&& saleOrderDetail.getPassengerNo().equals(passengerLegVo.getPassengerNo())) {
							if (requestWithActor.getEntity().getRefundType() == 1) {
								saleOrderDetail.setStatus("9");// TODO 修改成已废的状态
							}
							if (requestWithActor.getEntity().getRefundType() == 2) {
								saleOrderDetail.setStatus("8");// TODO 修改成已退的状态
							}
							/* 修改销售单明细状态 */
							saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
						}
					}
				}
			}
			//根据passengerNo去重
			List<PassengerLegVo> list=requestWithActor.getEntity().getPassengerLegVoList();
			for(int i=0;i<list.size();i++){
	            for(int y=list.size()-1;;y--){
	            	if(i == y){
	            		break;
	            	}else{
	            		if(list.get(i).getPassengerNo().equals(list.get(y).getPassengerNo())){
	            			list.remove(y);
	            		}
	            	}
	            }
	        }
			for (PassengerLegVo passengerLegVo : list) {
				Passenger passenger=passengerDao.selectByPrimaryKey(passengerLegVo.getPassengerNo());
				PassengerRefundPrice passengerRefundPrice = new PassengerRefundPrice();
				passengerRefundPrice
				.setPassengerRefundPriceNo(maxNoService.generateBizNo("IFT_PASSENGER_REFUND_PRICE_NO", 31));
				passengerRefundPrice.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				passengerRefundPrice.setSaleChangeNo(saleChangeNo);
				passengerRefundPrice.setPassengerNo(passengerLegVo.getPassengerNo());
				passengerRefundPrice.setOwner(agent.getOwner());
				passengerRefundPrice.setCreator(agent.getAccount());
				passengerRefundPrice.setCreateTime(createTime);
				passengerRefundPrice.setValid((byte)1);
				passengerRefundPrice.setStatus("1");
				passengerRefundPrice.setPassengerType(passenger.getPassengerType());
				
				/* 创建废退乘机人价格表 */
				passengerRefundPriceDao.insertSelective(passengerRefundPrice);
			}
			
			/*创建新增操作日志*/
			try {
				String logstr= null;
				String title="创建国际退/废销售单";
				if (requestWithActor.getEntity().getRefundType()==1){
					logstr ="用户"+ agent.getAccount()+"创建国际废销售单："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";

				}else if(requestWithActor.getEntity().getRefundType()==2){
					logstr ="用户"+ agent.getAccount()+"创建国际退票销售单："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";

				}else{
					logstr ="用户"+ agent.getAccount()+"创建国际退/废销售单："+"["+requestWithActor.getEntity().getSaleOrderNo()+"]";
				}
				IftLogHelper.logger(agent,saleChange.getTransationOrderNo(),requestWithActor.getEntity().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加(title=创建国际退/废销售单)操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("创建废退单失败", e);
			throw new GSSException("创建废退单失败" + e, "0002", "创建废退单失败");
		}
		log.info("创建废退单申请结束===========");
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				//销售退废分单操作 (开启线程分单，因为不结束这个方法，数据库中的saleChangeOrder未生成，在其他dubbo服务空间内无法查找到)
				Integer refundType = requestWithActor.getEntity().getRefundType();
				String refundTypeStr = "";
				if(new Integer(1).equals(refundType)){
					//废票
					refundTypeStr ="salesman-waste";
				} else {
					refundTypeStr ="salesman-refund";
				}
				IIftMessageService.sendRefuseMessage(requestWithActor.getEntity().getSaleOrderNo(),requestWithActor.getAgent().getOwner()+"",refundTypeStr);
			}
		}).start();*/
		return saleChangeExt;
	}

	private void setTicketSenderNum(SaleChangeExt saleChangeExt, Agent agent, SaleOrder saleOrder) {
		if(StringUtils.isNotBlank(saleOrder.getSourceChannelNo()) && StringUtils.equals("OP",saleOrder.getSourceChannelNo())){
			User user = userService.findUserByLoginName(agent,agent.getAccount());
			if(user!=null) {
				ticketSenderService.updateByLockerId(agent,user.getId(),"SALE_REFUSE_NUM");
			}
		}
	}

	//退废扩展表锁单员设置
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


	/**
	 * 采购退款
	 * @param requestWithActor
	 */
	@Transactional
	@Override
	public void buyReturnMoney(RequestWithActor<AirLineRefundRequest> requestWithActor) {
		Agent agent = requestWithActor.getAgent();
		AirLineRefundRequest airLineRefundRequest = requestWithActor.getEntity();
		Long saleChangeNo = airLineRefundRequest.getAuditSaleChangeNo();
		SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent,saleChangeNo);
		RequestWithActor<Long> parameter = new RequestWithActor<>();
		parameter.setEntity(saleChangeNo);
		parameter.setAgent(agent);
		//创建采购变更单应收
		refundService.buyVerify(parameter);
		//采购变更单退款
		refundService.buyRefund(requestWithActor,saleChange.getSaleOrderNo());
	}


	/**
	 * 通过销售单编号获取废退变更单 saleOrderNO
	 *
	 * @param requestWithActor
	 */
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
				SaleChange saleChange = saleChangeService
						.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeExt.getSaleChangeNo());
				if (saleChange != null) {
					if (saleChange.getOrderChangeType() == 1 || saleChange.getOrderChangeType() == 2) {
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
			log.info("------------", e);
		}

		return saleChangeExt;
	}

	@Override
	public BuyChangeExt getBuyChangeExtBySaleChangeNo(Long saleChangeNo) {
		BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
		return buyChangeExt;
	}


	@Override
	@Transactional
	public boolean lockRefund(RequestWithActor<Long> requestWithActor) {

		boolean flag = false;
		log.info("审核废退单锁定开始=========");
		try {

			Long saleChangeNo = requestWithActor.getEntity().longValue();
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
			SaleChange saleChangeByNo = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeNo);
			//long originLocker = saleChangeExt.getLocker();
			//long originBuyLocker = 0;
			int updateFlag = 0 ;
			if (1 == saleChangeByNo.getChildStatus() &&
					(saleChangeExt.getLocker() == 0l||saleChangeExt.getLocker() == null)
					) {
				//销售的锁单
				saleChangeExt.setLocker(requestWithActor.getAgent().getId());
				saleChangeExt.setAloneLocker(requestWithActor.getAgent().getId());
				// 锁起状态为Id
				//saleChangeExt.setLocker(1L);
				saleChangeExt.setLockTime(new Date());
				//int updateFlag = saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
				 updateFlag = refundService.updateLocker(saleChangeExt);
			} else if(2 == saleChangeByNo.getChildStatus()){
				//采购的锁单
				BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
				if(buyChangeExt.getBuyLocker()==null || buyChangeExt.getBuyLocker() == 0l){
					//originBuyLocker = buyChangeExt.getBuyLocker();
					buyChangeExt.setBuyLocker(requestWithActor.getAgent().getId());
					buyChangeExt.setModifyTime(new Date());
					updateFlag = buyChangeExtService.updateBuyChangeExt(buyChangeExt);
				}
			}

			//锁定前先判断是否有locker
			/*if(originLocker != 0l){
				//对应locker出票员的num-1
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),originLocker,"SALE_REFUSE_NUM");
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),originLocker,"BUY_REFUSE_NUM");
			}*/
			if (updateFlag == 1) {
				flag = true;
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),requestWithActor.getAgent().getId(),"SALE_REFUSE_NUM");
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),requestWithActor.getAgent().getId(),"BUY_REFUSE_NUM");
			}
			/*创建操作日志*/
			try {
				String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际废/退锁定："+"["+saleChangeExt.getSaleChangeDetailList().get(0).getSaleOrderDetail().getSaleOrderNo()+"]";
				String title = "国际废/退锁定";
				IftLogHelper.logger(requestWithActor.getAgent(),saleChangeExt.getSaleChangeDetailList().get(0).getSaleOrderDetail().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=国际废/退锁定）操作日志异常===" + e);
			}
			log.info("审核废退单锁定结束=========");
		} catch (Exception e) {
			log.error("锁定废退单失败", e);
			throw new GSSException("锁定废退单失败", "0002", "锁定废退单失败");
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean unLock(RequestWithActor<Long> requestWithActor) {

		boolean flag = false;
		log.info("审核废退单解锁开始=========");
		try {
			if (requestWithActor.getEntity().longValue() == 0) {
				log.error("销售单编号为空");
				throw new GSSException("销售单编号为空", "0001", "锁定废退单失败");
			}
			Long saleChangeNo = requestWithActor.getEntity().longValue();
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
			SaleChange saleChangeByNo = saleChangeService.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeNo);
			int updateFlag = 0 ;
			Long locker = null;
			if (1 == saleChangeByNo.getChildStatus() ) {
				locker = saleChangeExt.getLocker();
				saleChangeExt.setLocker(0L);
				updateFlag = refundService.updateLocker(saleChangeExt);
			}else if(2 == saleChangeByNo.getChildStatus()){
				//采购的解锁
				BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
				locker = buyChangeExt.getBuyLocker();
				buyChangeExt.setBuyLocker(0l);
				buyChangeExt.setModifyTime(new Date());
				updateFlag = buyChangeExtService.updateBuyChangeExt(buyChangeExt);
			}

			if(updateFlag == 1){
				flag = true;
				//对应出票员num-1
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),locker,"SALE_REFUSE_NUM");
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),locker,"BUY_REFUSE_NUM");
			}
			/*创建操作日志*/
			try {
				String logstr ="用户"+ requestWithActor.getAgent().getAccount()+"国际废/退解锁："+"["+saleChangeExt.getSaleChangeDetailList().get(0).getSaleOrderDetail().getSaleOrderNo()+"]";
                String title = "国际废/解退锁";
                IftLogHelper.logger(requestWithActor.getAgent(),saleChangeExt.getSaleChangeDetailList().get(0).getSaleOrderDetail().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=国际废/退解锁）操作日志异常===" + e);
			}
			log.info("审核废退单解锁结束=========");
		} catch (Exception e) {
			log.error("锁定废退单失败", e);
			throw new GSSException("锁定废退单失败", "0002", "锁定废退单失败");
		}
		return flag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateLocker(SaleChangeExt saleChangeExt) {
		return  saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
	}


	/*@Override
	@Transactional
	public boolean verify(RequestWithActor<List<PassengerRefundPrice>> passengerRefundList) {

		log.info("废退单审核开始");
		boolean flag = false;
		try {
			BigDecimal saleTotal = new BigDecimal(0);// 销售单合计
			BigDecimal buyTotal = new BigDecimal(0);// 采购单合计
			for (PassengerRefundPrice passenger : passengerRefundList.getEntity()) {
				if (passenger.getBuyFefundAccount() != null) {
					buyTotal = buyTotal.add(passenger.getBuyFefundAccount());
				}
				if (passenger.getSaleRefundAccount() != null) {
					// 合计
					saleTotal = saleTotal.add(passenger.getSaleRefundAccount());
				}
			}
			// 根据订单编号查询订单
			SaleChange saleChange = saleChangeService.getSaleChangeByNo(passengerRefundList.getAgent(),
					passengerRefundList.getEntity().get(0).getSaleChangeNo());
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
				planAmountRecordService.create(passengerRefundList.getAgent(), createPlanAmountVOType);
			} else {
				log.error("废退销售单为空");
				throw new GSSException("废退销售单为空", "0601", "核价修改销售、采购失败");
			}

			List<BuyOrder> buyOrderList = buyOrderService
					.getBuyOrdersBySONo(passengerRefundList.getAgent(), saleChange.getSaleOrderNo());
			if (buyOrderList != null) {
				for (BuyOrder buyOrder : buyOrderList) {
					List<BuyChange> buyChangeList = buyChangeService
							.getBuyChangesByBONo(passengerRefundList.getAgent(), buyOrder.getBuyOrderNo());
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
							planAmountRecordService.create(passengerRefundList.getAgent(), createPlanAmountVOType);
						}
					}
				}
			} else {
				log.error("废退采购单为空");
				throw new GSSException("废退采购单为空", "0601", "核价修改销售、采购失败");
			}
			log.info("订单核价结束");
			flag = true;
		} catch (Exception e) {
			log.error("订单核价失败", e);
			throw new GSSException("核价修改失败", "0603", "核价修改失败");
		}
		return flag;
	}*/

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public boolean buyVerify(RequestWithActor<Long> requestWithActor) {
		log.info("废退单审核开始");
		boolean flag = false;
		try {
			Agent agent = requestWithActor.getAgent();
			Long saleChangeNo = requestWithActor.getEntity();
			RequestWithActor<PassengerRefundPrice> params = new RequestWithActor<>();
			PassengerRefundPrice passengerRefundPrice = new PassengerRefundPrice();
			params.setAgent(agent);
			passengerRefundPrice.setSaleChangeNo(saleChangeNo);
			params.setEntity(passengerRefundPrice);
			RequestWithActor<List<PassengerRefundPrice>> passengerRefundList = new RequestWithActor<List<PassengerRefundPrice>>();
			List<PassengerRefundPrice> passengerRefundPrices = passengerRefundPriceService.queryPassengerRefundPriceList(params);
			passengerRefundList.setAgent(agent);
			passengerRefundList.setEntity(passengerRefundPrices);

			BigDecimal buyTotal = new BigDecimal(0);// 采购单合计
			for (PassengerRefundPrice passenger : passengerRefundList.getEntity()) {
				if (passenger.getBuyFefundAccount() != null) {
					buyTotal = buyTotal.add(passenger.getBuyFefundAccount());
				}
			}
			// 根据订单编号查询订单
			SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent,
					passengerRefundList.getEntity().get(0).getSaleChangeNo());
			List<BuyOrder> buyOrderList = buyOrderService
					.getBuyOrdersBySONo(agent, saleChange.getSaleOrderNo());
			if (buyOrderList != null) {
				for (BuyOrder buyOrder : buyOrderList) {
					List<BuyChange> buyChangeList = buyChangeService
							.getBuyChangesByBONo(agent, buyOrder.getBuyOrderNo());
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
							planAmountRecordService.create(agent, createPlanAmountVOType);
						}
					}
				}
			} else {
				log.error("废退采购单为空");
				throw new GSSException("废退采购单为空", "0601", "核价修改销售、采购失败");
			}
			log.info("订单核价结束");
			flag = true;
		}catch (Exception e){
			log.error("创建采购退款应收",e);
		}
		return flag;
	}

	@Transactional
	@Override
	public boolean saleVerify(RequestWithActor<List<PassengerRefundPrice>> passengerRefundList) {
		log.info("废退单审核开始");
		boolean flag = false;
		try {
			Agent agent = passengerRefundList.getAgent();
			BigDecimal saleTotal = new BigDecimal(0);// 销售单合计
			for (PassengerRefundPrice passenger : passengerRefundList.getEntity()) {
				if (passenger.getSaleRefundAccount() != null) {
					// 合计
					saleTotal = saleTotal.add(passenger.getSaleRefundAccount());
				}
			}
			// 根据订单编号查询订单
			SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent,
					passengerRefundList.getEntity().get(0).getSaleChangeNo());
			if (saleChange != null) {
				Long recordNo = saleChange.getSaleChangeNo();
				SaleChangeExt saleChangeExt = this.getSaleChangeExtByNo(new RequestWithActor<>(agent, recordNo));
				//根据乘客编号去查询该乘客有改签完成的改签订单！
				/*List<PassengerRefundPrice> passengerRefundPriceList = saleChangeExt.getPassengerRefundPriceList();
				if(passengerRefundPriceList != null || passengerRefundPriceList.size() > 0){
					PassengerRefundPrice passengerRefundPrice = passengerRefundPriceList.get(0);
					PassengerChangePrice lastChangeByPgerNoAndSaleOrderNo = passengerChangePriceService.getChangePassgerByPgerNo(passengerRefundPrice.getPassengerNo(), passengerRefundPrice.getSaleOrderNo());
					if(lastChangeByPgerNoAndSaleOrderNo != null){
						log.info("创建销售退款应收业务单号："+lastChangeByPgerNoAndSaleOrderNo.getSaleChangeNo());
						recordNo = lastChangeByPgerNoAndSaleOrderNo.getSaleChangeNo();
					}
				}*/
				// 销售单应收
				CreatePlanAmountVO createPlanAmountVOType = new CreatePlanAmountVO();
				createPlanAmountVOType.setRecordNo(recordNo);// 记录编号
				createPlanAmountVOType.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
				createPlanAmountVOType.setRecordMovingType(CostType.COMMISSION_CHARGE.getKey());
				createPlanAmountVOType.setPlanAmount(saleTotal);// 合计
				createPlanAmountVOType
						.setBusinessType(BusinessType.SALECHANGE.getKey());// 业务类型 2，销售单，3，采购单，4,变更单（可以根据变更表设计情况将废退改分开）
				createPlanAmountVOType.setGoodsType(2);// 商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
				planAmountRecordService.create(agent, createPlanAmountVOType);
			} else {
				log.error("废退销售单为空");
				throw new GSSException("废退销售单为空", "0601", "核价修改销售、采购失败");
			}
		}catch (Exception e){
			log.error("创建销售退款应收",e);
		}
		return flag;
	}

	/**
	 * 创建销售退款单
	 *
	 * @return
	 */
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
				int	subBusinessType = 3;
				Long recordNo = saleChangeNo;
				//根据乘客编号去查询该乘客有改签完成的改签订单！
				/*List<PassengerRefundPrice> passengerRefundPriceList = saleChangeExt.getPassengerRefundPriceList();
				if(passengerRefundPriceList != null || passengerRefundPriceList.size() > 0){
					PassengerRefundPrice passengerRefundPrice = passengerRefundPriceList.get(0);
					PassengerChangePrice lastChangeByPgerNoAndSaleOrderNo = passengerChangePriceService.getChangePassgerByPgerNo(passengerRefundPrice.getPassengerNo(), passengerRefundPrice.getSaleOrderNo());
					if(lastChangeByPgerNoAndSaleOrderNo != null){
						log.info("创建销售退款单的子业务类型为4");
						subBusinessType = 4;//如果存在已完成的改签订单那么子业务类型为4
						recordNo = lastChangeByPgerNoAndSaleOrderNo.getSaleChangeNo();
					}
				}*/
				try {
					CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
					certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
					certificateCreateVO.setReason("销售付款单信息"); //补充说明
					certificateCreateVO.setSubBusinessType(subBusinessType); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
					List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
					BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();

					businessOrderInfo.setActualAmount(saleChangeExt.getSaleChange().getPlanAmount());
					certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
					certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());

					businessOrderInfo.setBusinessType(4);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
					businessOrderInfo.setRecordNo(recordNo);
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

	/**
	 * 创建采购退款单
	 *
	 * @return
	 */
	public boolean buyRefund(RequestWithActor<AirLineRefundRequest> requestWithActor, Long saleOrderNo) throws GSSException {
		try {
			Agent agent = requestWithActor.getAgent();
			AirLineRefundRequest refundRequest = requestWithActor.getEntity();
			CertificateCreateVO createVO = new CertificateCreateVO();
			createVO.setIncomeExpenseType(1); //收支类型 1 收，2 为支
			createVO.setReason("创建采购退款单信息"); //补充说明
			createVO.setSubBusinessType(3); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
			createVO.setServiceLine("2");
			createVO.setAccoutNo(refundRequest.getRefundVoList().get(0).getAccountNo());
			List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
			BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
            Long buyOrderNo = 0L;
            Long saleChangeNo = refundRequest.getAuditSaleChangeNo();
			List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrderNo);
			if (buyOrderList != null && buyOrderList.size() != 0) {
				BuyOrder buyOrder = buyOrderList.get(0);
				createVO.setCustomerNo(buyOrder.getSupplierNo());
				createVO.setCustomerTypeNo(buyOrder.getSupplierTypeNo());
				buyOrderNo = buyOrder.getBuyOrderNo();
				List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, buyOrder.getBuyOrderNo());
				SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeNo);
				if (buyChangeList != null && buyChangeList.size() > 0 && saleChange != null) {
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
			createVO.setOrderInfoList(orderInfoList);
			ActualInfoSearchVO vo = actualAmountRecorService.queryActualInfoByBusNo(agent, buyOrderNo, 3);//查询采购单对应的支付方式
			if(vo!=null && StringUtils.isNotEmpty(vo.getPayWay())){
				Integer payWayCode=Integer.parseInt(vo.getPayWay().split(",")[0]);
				createVO.setPayType(payWayCode);
				createVO.setPayWay(Integer.parseInt(vo.getPayWay()));
			}
			List<AirLineRefundVo> refundVos = refundRequest.getRefundVoList();
			if(refundVos!=null && refundVos.size()>0){
			     String str = "";
				for(AirLineRefundVo refundVo:refundVos){
			   	str = str+ "," +refundVo.getCapitalFow();
			   }
				createVO.setThirdPayNo(str);
			}
			//创建采购退款单
			log.info("航司退款审核结算,创建采购退款单参数:"+createVO.toString());
			certificateService.buyRefundByList(agent,createVO);
		} catch (Exception e) {
			log.error("创建采购退款单单失败," + e);
			throw new GSSException("创建采购退款单失败," + e, "1001", "创建采购退款单失败");
		}
		return true;
	}

	/**
	 * 废票处理
	 *
	 * @param saleChangeNo
	 * @return
	 */
	@Override
	@Transactional
	public boolean invalidHandle(RequestWithActor<Long> saleChangeNo) {

		log.info("废票处理开始=========");
		try {
			if (saleChangeNo.getEntity().longValue() == 0) {
				log.error("销售单编号为空");
				throw new GSSException("销售单编号为空", "0001", "废票处理失败");
			}
			// 获取SaleChange对象
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo.getEntity().longValue());
			SaleChange saleChange = saleChangeService.getSaleChangeByNo(saleChangeNo.getAgent(),
					saleChangeExt.getSaleChangeNo());
			if (saleChange.getOrderChangeType() == 1) {
				// saleChange.setSaleChangeNo(saleChangeExt.getSaleChangeNo());
				saleChange.setChildStatus(1);// 设置子状态 1 为废票处理成功
				saleChangeService.update(saleChangeNo.getAgent(), saleChange);// 更具传入变更单号，修改子状态
			}
			/*创建操作日志*/
			try {
				String logstr ="用户"+ saleChangeNo.getAgent().getAccount()+"国际废票处理："+"["+saleChangeExt.getSaleChange().getSaleOrderNo()+"]";
				String title="国际废票处理";
				IftLogHelper.logger(saleChangeNo.getAgent(),saleChangeExt.getSaleChange().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=国际废票处理）操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("废票处理失败", e);
			throw new GSSException("废票处理失败", "0002", "废票处理失败");
		}
		return true;
	}

	/**
	 * 退票处理
	 *
	 * @param saleChangeNo
	 * @return
	 */
	@Override
	@Transactional
	public boolean refundHandle(RequestWithActor<Long> saleChangeNo) {

		log.info("废票处理开始=========");
		try {
			if (saleChangeNo.getEntity().longValue() == 0) {
				log.error("销售单编号为空");
				throw new GSSException("销售单编号为空", "0001", "废票处理失败");
			}
			// 获取SaleChange对象
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo.getEntity().longValue());
			SaleChange saleChange = saleChangeService.getSaleChangeByNo(saleChangeNo.getAgent(),
					saleChangeExt.getSaleChangeNo());
			if (saleChange.getOrderChangeType() == 2) {
				saleChange.setChildStatus(2);// 设置子状态 2 为退票处理成功
				saleChangeService.update(saleChangeNo.getAgent(), saleChange);// 更具传入变更单号，修改子状态
			}

			/*创建操作日志*/
			try {
				String logstr ="用户"+ saleChangeNo.getAgent().getAccount()+"国际退票处理："+"["+saleChangeExt.getSaleChange().getSaleOrderNo()+"]";
				String title = "国际退票处理";
                IftLogHelper.logger(saleChangeNo.getAgent(),saleChangeExt.getSaleChange().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=国际退票处理）操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("废票处理失败", e);
			throw new GSSException("废票处理失败", "0002", "废票处理失败");
		}
		return true;
	}

	/**
	 * 取消废退.
	 *
	 * @param requestWithActor
	 * @return
	 */
	@Override
	@Transactional
	public boolean cancel(RequestWithActor<Long> requestWithActor) {

		log.info("取消废退处理开始=========");
		Agent agent = requestWithActor.getAgent();
		Long saleChangeNo = requestWithActor.getEntity();
		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("agent为空", "0001", "取消废退失败");
		}
		if (saleChangeNo == null || saleChangeNo == 0) {
			log.error("销售单编号为空");
			throw new GSSException("销售单编号为空", "0001", "取消废退失败");
		}
		// 获取SaleChange对象
		SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeNo);
		if (saleChange == null) {
			log.error("废退单不存在,saleChangeNo=" + saleChangeNo);
			throw new GSSException("废退单不存在,saleChangeNo=" + saleChangeNo, "0001", "取消废退失败");
		}
		if (saleChange.getChildStatus() != 1) {
			log.error("废退单号：saleChangeNo=" + saleChangeNo + "的状态不为待审核，无法取消废退");
			throw new GSSException("废退单号：saleChangeNo=" + saleChangeNo + "的状态不为待审核，无法取消废退", "0001", "取消废退失败");
		}
		try {
			saleChangeService.updateStatus(agent, saleChange.getSaleChangeNo(),11);// 更具传入变更单号，修改子状态
            Date modifyTime = new Date();
			//修改销售单明细
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChange.getSaleChangeNo());
			Long locker = null;
			if (saleChangeExt != null) {
				locker = saleChangeExt.getLocker();
				if (saleChangeExt.getSaleChangeDetailList() != null) {
					for (SaleChangeDetail saleChangeDetail : saleChangeExt.getSaleChangeDetailList()) {
						SaleOrderDetail saleOrderDetail = saleOrderDetailDao
								.selectByPrimaryKey(saleChangeDetail.getSaleOrderDetailNo());
						if (saleChangeDetail != null) {
							saleOrderDetail.setStatus(String.valueOf(4));//将状态改为已出票
							saleOrderDetail.setModifier(agent.getAccount());
							saleOrderDetail.setModifyTime(modifyTime);
							saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
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
							buyChange.setModifyTime(modifyTime);
							buyChangeService.update(agent, buyChange);
						}
					}
				}
			}
			ticketSenderService.updateByLockerId(agent,locker,"SALE_REFUSE_NUM");
			/*创建操作日志*/
			try {
				String logstr= null;
				if (saleChange.getOrderChangeType()==1){
					logstr ="用户"+ agent.getAccount()+"取消国际废销售单："+"["+saleChange.getSaleOrderNo()+"]";

				}else if(saleChange.getOrderChangeType()==2){
					logstr ="用户"+ agent.getAccount()+"取消国际退票销售单："+"["+saleChange.getSaleOrderNo()+"]";

				}else{
;                   logstr ="用户"+requestWithActor.getAgent().getAccount()+"取消国际退/废销售单："+"["+saleChange.getSaleOrderNo()+"]";

				}
                String title ="取消国际退/废销售单";
                IftLogHelper.logger(agent,saleChange.getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=取消国际退/废销售单）操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("取消废退失败", e);
			throw new GSSException("取消废退失败", "0002", "取消废退失败");
		}
		return true;
	}

	/**
	 * 拒绝废退.
	 *
	 * @param saleChangeNo
	 * @param reason
	 * @return
	 */
	@Override
	@Transactional
	public boolean refuse(RequestWithActor<Long> saleChangeNo, String reason) {

		log.info("拒绝废退开始=========");
		try {
			if (saleChangeNo.getEntity().longValue() == 0) {
				log.error("销售单编号为空");
				throw new GSSException("销售单编号为空", "0001", "拒绝废退失败");
			}
			// 获取SaleChange对象
			SaleChange saleChange = saleChangeService
					.getSaleChangeByNo(saleChangeNo.getAgent(), saleChangeNo.getEntity().longValue());
			saleChange.setChildStatus(14);// 设置废退状态 4为拒绝废退 ChangeStatus byLZ  4-->14
			Date modifyTime = new Date();
			Agent agent = saleChangeNo.getAgent();
			saleChange.setModifier(agent.getAccount());
			saleChange.setModifyTime(modifyTime);
			saleChangeService.update(saleChangeNo.getAgent(), saleChange);// 更具传入变更单号，修改子状态
			SaleChangeExt saleChangeExt = this.getSaleChangeExtByNo(saleChangeNo);
			if(saleChangeExt!=null){
				Long locker = saleChangeExt.getLocker();
//				saleChangeExt.setLocker(0L);
//				saleChangeExt.setModifier(agent.getAccount());
//				saleChangeExt.setModifyTime(modifyTime);
//                saleChangeExtDao.updateLocker(saleChangeExt);
                //Long saleOrderNo = saleChangeExt.getSaleChange().getSaleOrderNo();
                //只修改被拒单的航段+乘机人detail的状态，不能全部修改为4
				List<SaleChangeDetail> saleChangeDetailList = saleChangeExt.getSaleChangeDetailList();
				List<SaleOrderDetail> detailList = new ArrayList<>();
				for (SaleChangeDetail saleChangeDetail : saleChangeDetailList) {
					SaleOrderDetail saleOrderDetail = new SaleOrderDetail();
					saleOrderDetail.setSaleOrderDetailNo(saleChangeDetail.getSaleOrderDetailNo());
					saleOrderDetail.setStatus("4");
					saleOrderDetail.setRefuseReason(reason);
					detailList.add(saleOrderDetail);
				}
				//修改废退乘客的refundPrice的valid为0
				List<PassengerRefundPrice> passengerRefundPrices = passengerRefundPriceDao.selectPassengerRefundPriceBySaleOrderNo(saleChangeNo.getEntity());
				for (PassengerRefundPrice passengerRefundPrice : passengerRefundPrices) {
					passengerRefundPrice.setValid((byte) 0);
					passengerRefundPriceDao.updateByPrimaryKeySelective(passengerRefundPrice);
				}
				detailService.batchUpdate(detailList);
				//detailService.updateSaleOrderDetailStatusByNo(saleOrderNo,4);
				ticketSenderService.updateByLockerId(agent,locker,"SALE_REFUSE_NUM");
			}
			/*创建操作日志*/
			try {
			    String logstr=null;
			   if (saleChange.getOrderChangeType()==2){
                   logstr ="用户"+saleChangeNo.getAgent().getAccount()+"国际拒绝退票："+"["+saleChange.getSaleOrderNo()+"]";

               }else if(saleChange.getOrderChangeType()==1){
                   logstr ="用户"+saleChangeNo.getAgent().getAccount()+"国际拒绝废票："+"["+saleChange.getSaleOrderNo()+"]";

               } else {
			       logstr ="用户"+saleChangeNo.getAgent().getAccount()+"国际拒绝退/废："+"["+saleChange.getSaleOrderNo()+"]";
               }
                String title ="国际拒绝退/废";
                IftLogHelper.logger(saleChangeNo.getAgent(),saleChange.getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=国际拒绝退/废）操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("拒绝废退失败", e);
			throw new GSSException("拒绝废退失败", "0002", "拒绝废退失败");
		}
		return true;
	}

	@Override
	@Transactional
	public Page<SaleChangeVo> querySaleChangeExtByVo(Page page, RequestWithActor<SaleChangeExtVo> requestWithActor)
			throws Exception {

		log.info("查询废退改签单开始=======");
		try {
			if (requestWithActor.getEntity() == null) {
				log.error("废退单查询对象为空");
				throw new GSSException("废退单查询对象为空", "0001", "查询废退改签单失败");
			}
			String sourceChannelNo = null;
			handleTicketNo(requestWithActor);
			log.info("获取费退改签单的数据,查询条件为："+ JsonUtil.toJson(requestWithActor.getEntity()));

			List<SaleChangeExt> list = saleChangeExtDao.queryObjByKey(page, requestWithActor.getEntity());
			log.info("获取费退改签单的集合数据条数为："+list.size());
			List<SaleChangeVo> saleChangeVoList = new ArrayList<>();
			for (SaleChangeExt saleChangeExt : list) {
				SaleChangeVo saleChangeVo = new SaleChangeVo();
				SaleChange saleChange = saleChangeService
						.getSaleChangeByNo(requestWithActor.getAgent(), saleChangeExt.getSaleChangeNo());
				if (saleChange != null && saleChange.getSaleOrderNo() != 0) {

					//saleChangeVo.setSaleOrderNo(saleChange.getSaleOrderNo());//销售单编号
					SaleOrderExt saleOrderExt = saleOrderExtService
							.selectBySaleOrderNo(requestWithActor.getAgent(), saleChange.getSaleOrderNo());
					if (saleOrderExt != null && saleOrderExt.getSaleOrderNo() != null) {
						List<BuyOrder> buyOrderList = buyOrderService
								.getBuyOrdersBySONo(requestWithActor.getAgent(), saleOrderExt.getSaleOrderNo());
						for(BuyOrder buyOrder:buyOrderList){
							if(buyOrder.getBusinessSignNo().equals(saleOrderExt.getSaleOrder().getBusinessSignNo())){
								Supplier supplier=supplierService.getSupplierByNo(requestWithActor.getAgent(),buyOrder.getSupplierNo());
								saleChangeVo.setTicketType(supplier.getShortName());//票证类型获取供应商
							}
							List<BuyChange> buyChangeList=buyChangeService.getBuyChangesByBONo(requestWithActor.getAgent(), buyOrder.getBuyOrderNo());
							for(BuyChange buyChange:buyChangeList){
								if(saleChange.getBusinessSignNo().equals(buyChange.getBusinessSignNo())){
									saleChangeVo.setBuyStatus(buyChange.getChildStatus());//采购办理状态
								}
							}
						}

							String fight = null;
							String name = null;
							String ticketNo = null;
							String fightNo = null;

							//乘机人只显示退废的
						List<PassengerRefundPrice> passengerRefundPriceList = saleChangeExt.getPassengerRefundPriceList();
						for (PassengerRefundPrice passengerRefundPrice : passengerRefundPriceList) {
							Passenger passenger = passengerRefundPrice.getPassenger();
							if (passenger.getName() == null)
								passenger.setName("");
							if (passenger.getSurname() == null)
								passenger.setSurname("");
							if (name == null || name.equals("")) {
								name = passenger.getSurname() + passenger.getName();
							} else {
								name = name + "," + passenger.getSurname() + passenger.getName();
							}
						}
						/*if (saleOrderExt.getPassengerList() != null) {
								for (Passenger passenger : saleOrderExt.getPassengerList()) {
									if (passenger.getName() == null)
										passenger.setName("");
									if (passenger.getSurname() == null)
										passenger.setSurname("");
									if (name == null || name.equals("")) {
										name = passenger.getSurname() + passenger.getName();
									} else {
										name = name + "," + passenger.getSurname() + passenger.getName();
									}
								}
							}*/
						// List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
							List<SaleChangeDetail> saleChangeDetailList = saleChangeExt.getSaleChangeDetailList();
							List<SaleOrderDetail> saleOrderDetails = new ArrayList<>();
							for (SaleChangeDetail saleChangeDetail : saleChangeDetailList) {
								saleOrderDetails.add(saleChangeDetail.getSaleOrderDetail());
							}

							if ( saleOrderDetails != null) {
								for (SaleOrderDetail saleOrderDetail : saleOrderDetails) {
									//票号
									if (saleOrderDetail.getTicketNo() == null) {
										saleOrderDetail.setTicketNo("");
									}
									if (ticketNo == null || ticketNo.equals("")) {
										ticketNo = saleOrderDetail.getTicketNo();
									} else if(!ticketNo.contains(saleOrderDetail.getTicketNo())){
										ticketNo = ticketNo + "," + saleOrderDetail.getTicketNo();
									}

								}
							}

							saleChangeVo.setTicketNo(ticketNo);//票号
							saleChangeVo.setLeg(fight);//航程
							saleChangeVo.setAlineNo(fightNo);
							saleChangeVo.setPassenger(name);//乘机人

							//pnr
							if(saleOrderExt.getImportPnr()!=null){
								saleChangeVo.setPnr(saleOrderExt.getImportPnr().getPnr());
							}
						//订单来源
						if (saleOrderExt.getSaleOrder() != null) {
							if (saleOrderExt.getSaleOrder().getSourceChannelNo() == null) {
								saleOrderExt.getSaleOrder().setSourceChannelNo("");
							}
							if (saleOrderExt.getSaleOrder().getSourceChannelNo() != null || !("")
									.equals(saleOrderExt.getSaleOrder().getSourceChannelNo())) {
								sourceChannelNo = saleOrderExt.getSaleOrder().getSourceChannelNo();
							} else {
								sourceChannelNo = sourceChannelNo + "-" + saleOrderExt.getSaleOrder().getSourceChannelNo();
							}
							saleChangeVo.setOrderSource(sourceChannelNo);
						}
					}
					saleChangeVo.setCreateTime(saleChangeExt.getCreateTime());
					if (saleChange.getChildStatus() != null) {
						saleChangeVo.setSaleStatus(saleChange.getChildStatus());//客户办理状态
						saleChangeVo.setRefundStatus(saleChange.getChildStatus());
					}
					if (saleChangeExt.getPassengerRefundPriceList() != null
							&& saleChangeExt.getPassengerRefundPriceList().size() > 0) {
						saleChangeVo.setCost(saleChangeExt.getPassengerRefundPriceList().get(0).getSaleCount());//设置废退费用
					}
					saleChangeVo.setSaleChangeNo(saleChangeExt.getSaleChangeNo());//
					saleChangeVo.setAgentId(requestWithActor.getAgent().getId());
					saleChangeVo.setLocker(saleChangeExt.getLocker());
					saleChangeVo.setBuyLocker(saleChangeExt.getBuyLocker());
					saleChangeVo.setCustomerName(saleChangeExt.getCustomerName());
					//设置操作人
					saleChangeVo.setHandlers(saleChangeExt.getHandlers());
					saleChangeVo.setAirLineRefundStatus(saleChangeExt.getAirLineRefundStatus());
					saleChangeVoList.add(saleChangeVo);
				}
				page.setRecords(saleChangeVoList);
			}
			//log.info("查询废退改签单结束，获取费退改签单的数据为："+ JsonUtil.toJson(page));
		} catch (Exception e) {
			log.error("查询废退改签单", e);
		}
		log.info("查询废退改签单结束=======");
		return page;
	}

	private void handleTicketNo(RequestWithActor<SaleChangeExtVo> saleOrderQueryRequest) {
		String ticketNo = saleOrderQueryRequest.getEntity().getTicketNo();
		if(StringUtils.isNotEmpty(ticketNo)){
			if(!ticketNo.contains("-")){
				//如果没有“-”的话在第四个位置加上“-”
				StringBuilder sb = new StringBuilder(ticketNo);
				sb.insert(3,"-");
				saleOrderQueryRequest.getEntity().setTicketNo(sb.toString());
			}
		}
	}


	@Override
	@Transactional
	public Page<SaleChangeExt> queryListByVo(Page<SaleChangeExt> page, RequestWithActor<SaleChangeExtVo> requestWithActor)
			throws Exception {

		log.info("查询废退改签单开始=======");
		Agent agent = requestWithActor.getAgent();
		try {
			if (requestWithActor.getEntity() == null) {
				log.error("废退单查询对象为空");
				throw new GSSException("废退单查询对象为空", "0001", "查询废退改签单失败");
			}
			if("API".equals(requestWithActor.getAgent().getDevice())){
				requestWithActor.getEntity().setCustomerNo(requestWithActor.getAgent().getNum());
			}
			Boolean isNeedCustomer=requestWithActor.getEntity().getCustomerCount();
			if(isNeedCustomer){
				List<Customer> customers = customerService.getSubCustomerByCno(requestWithActor.getAgent(), requestWithActor.getAgent().getNum());
				List<Long> longList=new ArrayList<>();
				if(null!=customers&&customers.size()>0) {
					for (Customer customer : customers) {
						longList.add(customer.getCustomerNo());
					}
				}
				requestWithActor.getEntity().setLongList(longList);
			}
			handleTicketNo(requestWithActor);
			List<SaleChangeExt> list = saleChangeExtDao.queryObjByKey(page, requestWithActor.getEntity());
			if(null!=list) {
				for (SaleChangeExt saleChangeExt : list) {
					SaleChange saleChange = saleChangeService.getSaleChangeByNo(agent, saleChangeExt.getSaleChangeNo());
					if (saleChange != null) {
						saleChangeExt.setSaleChange(saleChange);
					}
				}
			}

			page.setRecords(list);
		} catch (Exception e) {
			log.error("查询废退改签单", e);
			
		}
		log.info("查询废退改签单结束=======");
		return page;
	}

	/**
	 * 修改
	 */
	@Override
	public boolean updateSaleChangeExt(RequestWithActor<SaleChangeExt> saleOrderChangeExt) {

		boolean flag = false;
		try {
			SaleChangeExt saleChangeExt = saleOrderChangeExt.getEntity();
			log.info("修改销售变更单信息:"+saleChangeExt.toString());
			int updateFlag = saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
			if (updateFlag == 1) {
				flag = true;
			}
			/*创建操作日志*/
			try {
				String logstr=null;
                String title=null;
				if(saleOrderChangeExt.getEntity()!=null && saleOrderChangeExt.getEntity().getSaleChange() !=null ){
					if(saleOrderChangeExt.getEntity().getSaleChange().getOrderChangeType()==2){
					    if(saleOrderChangeExt.getEntity().getAirlineStatus() !=null && saleOrderChangeExt.getEntity().getAirlineStatus()==1){
                            title ="审核国际退票销售单";
						    logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"审核国际退票销售单："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";
                        }
						if (saleOrderChangeExt.getEntity().getAirlineStatus() !=null && saleOrderChangeExt.getEntity().getAirlineStatus()==4){
                            title ="国际退票销售单拒单";
							logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"国际退票销售单拒单："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";

						}
						if (saleOrderChangeExt.getEntity().getSaleChange().getChildStatus()==10){
                            title ="退票退费";
							logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"退票退费："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";

						}
					}else if(saleOrderChangeExt.getEntity().getSaleChange().getOrderChangeType()==1){
                        if(saleOrderChangeExt.getEntity().getAirlineStatus() !=null && saleOrderChangeExt.getEntity().getAirlineStatus()==1) {
                            title ="审核国际废票销售单";
                            logstr = "用户" + saleOrderChangeExt.getAgent().getAccount() + "审核国际废票销售单：" + "[" + saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo() + "]";
                        }
						if (saleOrderChangeExt.getEntity().getAirlineStatus() !=null && saleOrderChangeExt.getEntity().getAirlineStatus()==4){
                            title ="国际废票销售单拒单";
						    logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"国际废票销售单拒单："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";

						}
						if (saleOrderChangeExt.getEntity().getSaleChange().getChildStatus()==10){
                            title ="废票退费";
							logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"废票退费："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";

						}
					}
				}else {
                    title ="修改国际退/废销售单";
				    logstr ="用户"+saleOrderChangeExt.getAgent().getAccount()+"修改国际退/废销售单："+"["+saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo()+"]";
				}

                IftLogHelper.logger(saleOrderChangeExt.getAgent(),saleOrderChangeExt.getEntity().getSaleChange().getSaleOrderNo(),title,logstr);
			} catch (Exception e) {
				log.error("添加（title=修改国际退/废销售单）操作日志异常===" + e);
			}
		} catch (Exception e) {
			log.error("修改国际废/退单失败===" + e);
		}
		return flag;
	}

	@Override
	public int orderRefundInform(OrderInformVo orderInformVo) {
		BuyOrderExt buyOrderExt = new BuyOrderExt();
		SaleChangeExt saleChangeExt = new SaleChangeExt();
		PassengerRefundPrice passengerRefundPrice = new PassengerRefundPrice();
		SaleOrderDetail  saleOrderDetail = new SaleOrderDetail();

		try {
			saleChangeExt.setSaleChangeNo(orderInformVo.getSaleOrderNo());
			if(orderInformVo.getInformType()==5){
				saleChangeExt.setRefuseReason(orderInformVo.getRefuseReason());
				saleChangeExt.setChangeType(orderInformVo.getChangeType());

				saleOrderDetail.setStatus(orderInformVo.getStatus());
				saleOrderDetail.setChangeOrderNo(orderInformVo.getSaleOrderNo());
				saleOrderDetailDao.updateByChangeOrderNo(saleOrderDetail);
			}else {
				saleChangeExt.setChangeRemark(orderInformVo.getSaleRemark());
				buyOrderExt.setChangeOrderNo(orderInformVo.getSaleOrderNo());
				buyOrderExt.setSupplierNo(orderInformVo.getSupplierNo());

				PropertyUtils.copyProperties(passengerRefundPrice,orderInformVo);
				buyOrderExtDao.updateByChangeOrderNo(buyOrderExt);
				passengerRefundPriceDao.updateByChangeOrderNo(passengerRefundPrice);
			}
			saleChangeExtDao.updateByPrimaryKey(saleChangeExt);
		} catch (Exception e) {
			log.error("orderRefundInform", e);
		}
		return 0;
	}

	/**
	 * 采购废票分单任务
	 */
	@Override
	@Transactional
	public void assignBuyWaste(RequestWithActor<Long> requestWithActor) {
        Long wasteOrderNo = requestWithActor.getEntity();
		List<SaleChangeExt> saleChangeExts = null;
		SaleChangeExt saleChangeExt = null;
        if(wasteOrderNo==0L||wasteOrderNo==null){
			log.info("定时任务分采购废票单开始,第一步：查询符合条件的采购废票订单...");
			saleChangeExts = saleChangeExtDao.queryBuyWasteBylocker(owner, 0l);
			if (saleChangeExts != null && saleChangeExts.size() > 0) {
				log.info("查询到" + saleChangeExts.size() + "条可分配订单...");
			} else {
				log.info("未查询到可以分配的采购废票订单,结束此次任务...");
				return;
			}
		}else {
			log.info("直接将采购废票单分给在线业务员员，单号:{}", wasteOrderNo);
			saleChangeExt = this.getSaleChangeExtByNo(requestWithActor);
		}
		Agent agent = new Agent(Integer.valueOf(owner));
		boolean isDistributeTicket = configsService.getIsDistributeTicket(agent);
		if(!isDistributeTicket){
			//如果不是系统分单
			if ((wasteOrderNo == null || wasteOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts,null,null,requestWithActor.getAgent(),null,isDistributeTicket);
			}else{
				directAssign(saleChangeExt, null, null, requestWithActor.getAgent(), null,isDistributeTicket);
			}
			log.info("此次分单结束...");
			return ;
		}

		log.info("第二步：查询在线采购废票员...");
		List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-waste"); //采购废票人员
		log.info("是否有在线出票员:" + (senders != null));
		if (senders != null && senders.size() > 0) {

			Long maxOrderNum = getMaxAbleAssignNum(agent);
			log.info("在线出票员人数:" + (senders.size()) + "获得配置最大分单数：" + maxOrderNum);
			Date updateTime = new Date();
			if ((wasteOrderNo == null || wasteOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts, senders, maxOrderNum, agent, updateTime,isDistributeTicket);
			}else{
				directAssign(saleChangeExt, senders, maxOrderNum, agent, updateTime,isDistributeTicket);
			}
			log.info("此次分单结束...");
		} else {
			log.info("未查询在线出票员...");
		}

	}


	private void increaseBuyRefuseNum(Agent agent, Long buyLocker) {

		//查询该种类型单被该业务员锁住的数量赋值给BuyRefuseNum字段
//		User user = userService.findUserByLoginName(agent, buyLocker.getUserid());
//		int lockCount = refundService.queryBuyRefundAndDelCountBylocker(user.getId());
//		buyLocker.setBuyRefuseNum(lockCount);
//		buyLocker.setIds(buyLocker.getId() + "");
//		iTicketSenderService.update(buyLocker);
		iTicketSenderService.updateByLockerId(agent,buyLocker,"BUY_REFUSE_NUM");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void assingLockSaleChangeExt(SaleChangeExt order, Date date) {
		/*User user = userService.findUserByLoginName(agent, sender.getUserid());
		order.setLocker(user.getId());
		order.setLockTime(date);
		saleChangeExtDao.updateLocker(order);*/
		Long saleChangeNo = order.getSaleChangeNo();
		BuyChangeExt buyChangeExt =buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
		buyChangeExt.setBuyLocker(order.getAloneLocker());
		buyChangeExt.setModifyTime(date);
		log.info("更新采购变更单扩展表所锁单人："+buyChangeExt.toString());
		buyChangeExtService.updateBuyChangeExt(buyChangeExt);
	}

	public BuyChangeExt assingLockSaleChangeExt(SaleChangeExt order, TicketSender sender, Date date, Agent agent) {
		Long saleChangeNo = order.getSaleChangeNo();
		BuyChangeExt buyChangeExt =buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
		if(buyChangeExt.getBuyLocker() == null || buyChangeExt.getBuyLocker() == 0l){
			User user = userService.findUserByLoginName(agent, sender.getUserid());
			if (user!=null) {
				buyChangeExt.setBuyLocker(user.getId());
				buyChangeExt.setModifyTime(date);
				log.info("自动分单更新采购变更单扩展表所锁单人："+buyChangeExt.toString());
				sender.setBuyRefuseNum(sender.getBuyRefuseNum()+1);
				buyChangeExtService.updateBuyChangeExt(buyChangeExt);
			}
		}
		return buyChangeExt;
	}


	/**
	 * 采购退票分单任务
	 */
	@Override
	@Transactional
	public void assignBuyRefund(RequestWithActor<Long> requestWithActor) {
		Long refundOrderNo = requestWithActor.getEntity();
		List<SaleChangeExt> saleChangeExts = null;
		SaleChangeExt saleChangeExt = null;
		if (refundOrderNo == 0L|| refundOrderNo==null) {
			log.info("定时任务分采购退票单开始,第一步：查询符合条件的采购退票订单...");
			saleChangeExts = saleChangeExtDao.queryRefundBylocker(owner, 0l);
			if (saleChangeExts != null && saleChangeExts.size() > 0) {
				log.info("查询到" + saleChangeExts.size() + "条可分配订单...");
			} else {
				log.info("未查询到可以分配的采购退票订单,结束此次任务...");
				return;
			}
		} else {
			log.info("直接将采购退票单分给在线业务员，单号:{}", refundOrderNo);
			saleChangeExt = this.getSaleChangeExtByNo(requestWithActor);
		}
		Agent agent = new Agent(Integer.valueOf(owner));
		Boolean isDistribute = configsService.getIsDistributeTicket(agent);
		if(!isDistribute){
			//如果不是系统分单
			if ((refundOrderNo == null || refundOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts,null,null,requestWithActor.getAgent(),null,isDistribute);
			}else{
				directAssign(saleChangeExt, null, null, requestWithActor.getAgent(), null,isDistribute);
			}
			log.info("此次分单结束...");
			return ;
		}
		log.info("第二步：查询在线采购退票员...");
		List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-refund"); //采购退票人员
		log.info("是否有在线出票员:" + (senders != null));
		if (senders != null && senders.size() > 0) {

			Long maxOrderNum = getMaxAbleAssignNum(agent);
			log.info("有在线出业务员人数:" + (senders.size()) + "获得配置最大分单数：" + maxOrderNum);
			Date updateTime = new Date();
			if ((refundOrderNo == null || refundOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts, senders, maxOrderNum, agent, updateTime,isDistribute);
			}else{
				directAssign(saleChangeExt, senders, maxOrderNum, agent, updateTime,isDistribute);
			}
			log.info("此次分单结束...");
		} else {
			log.info("未查询到在线出票员...");
		}

	}

	@Transactional
	@Override
	public void shoppingRefuse(RequestWithActor<Long> requestWithActor) {
		log.info("----航司退款拒单开始----");
		if (requestWithActor.getEntity() == null && requestWithActor.getEntity() == 0L)
			throw new GSSException("废退单查询参数空", "0001", "查询废退改签单失败");
		SaleChangeExt saleChangeExt = refundService.getSaleChangeExtByNo(requestWithActor);
		SaleChange saleChange = saleChangeExt.getSaleChange();
		Long saleChangeNo = saleChange.getSaleChangeNo();
		if (saleChangeExt == null || saleChangeNo == null || saleChangeNo == 0L)
			throw new GSSException("废退单查询结果空", "0001", "查询废退改签单失败");
		//修改销售单状态
		Date modifyTime = new Date();
		Agent agent = requestWithActor.getAgent();
		saleChange.setModifier(agent.getAccount());
		saleChange.setModifyTime(modifyTime);
		if(saleChange.getChildStatus()!=10) {//若不是已退款则更新为已核价
			saleChange.setChildStatus(2);//
		}
		saleChangeService.update(agent, saleChange);
		//修改采购单状态
		Long saleOrdernNo = saleChange.getSaleOrderNo();
		//String buyModifier = "";
		List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(agent, saleOrdernNo);
		for(BuyOrder buyOrder:buyOrderList){
			List<BuyChange> buyChangeList = buyChangeService.getBuyChangesByBONo(agent, buyOrder.getBuyOrderNo());
			for(BuyChange buyChange:buyChangeList){
				//buyModifier = buyChange.getModifier();
				buyChange.setChildStatus(1);
				buyChange.setModifier(agent.getAccount());
				buyChange.setModifyTime(modifyTime);
				log.info("修改采购变更单信息"+buyChange.toString());
				buyChangeService.update(agent, buyChange);
			}
		}
		BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
		if(buyChangeExt !=null &&  0 != buyChangeExt.getBuyLocker()){
			//更新出票人信息
			ticketSenderService.updateByLockerId(agent,buyChangeExt.getBuyLocker(),"BUY_REFUSE_NUM");
		}
		log.info("---航司退款拒单结束---");
		//更新前一位采购人的订单处理数量
	/*	User user = userService.findUserByLoginName(agent,agent.getAccount());
		if(user!=null) {
			saleChangeExt.setLocker(user.getId());
			RequestWithActor<SaleChangeExt> saleOrderChange = new RequestWithActor<>();
			saleOrderChange.setAgent(agent);
			saleOrderChange.setEntity(saleChangeExt);
			refundService.updateSaleChangeExt(saleOrderChange);
			iTicketSenderService.updateByLockerId(agent, user.getId(), "BUY_REFUSE_NUM");
		}*/
	}

	/**
	 *航司退款审核通过
	 * @param requestWithActor
	 */
	@Transactional
	@Override
	public void shoppingAuditPass(RequestWithActor<AirLineRefundRequest> requestWithActor) {
		Agent agent = requestWithActor.getAgent();
		Date modifyTime = new Date();
		AirLineRefundRequest refundRequest = requestWithActor.getEntity();
		log.info("提交航司信息:"+new Gson().toJson(refundRequest));
		Long saleChangeNo = refundRequest.getAuditSaleChangeNo();
		RequestWithActor<Long> parameter = new RequestWithActor<>();
		parameter.setEntity(saleChangeNo);
		parameter.setAgent(agent);
		SaleChangeExt saleChangeExt = refundService.getSaleChangeExtByNo(parameter);
		if(saleChangeExt!=null) {
			//更新当前操作员订单数量
			//Long lockerId = saleChangeExt.getLocker();
			//saleChangeExt.setLocker(0L);
			//状态查看assist.js alineQuitSaleStatus方法状态 1,"待审核",2,"已审核",3,"已退废",4,"已拒单"
			saleChangeExt.setAirlineStatus(3);//航司已审核
			saleChangeExt.setModifier(agent.getAccount());
			saleChangeExt.setAuditPerson(agent.getAccount());
			saleChangeExt.setModifyTime(modifyTime);
			RequestWithActor requestWithActor1 = new RequestWithActor();
			requestWithActor1.setEntity(saleChangeExt);
			requestWithActor.setAgent(agent);
			log.info("航司审核更新变更单号信息:"+saleChangeExt.toString());
			refundService.updateSaleChangeExt(requestWithActor1);
			/*if(lockerId!=null) {
				iTicketSenderService.updateByLockerId(agent, lockerId, "BUY_REFUSE_NUM");
			}*/
		}else{
			throw new GSSException("变更单为空", "0001", "查询废退改签单失败");
		}
		//创建采购退款单退款
		refundService.buyReturnMoney(requestWithActor);
		//更新变更单审核状态
		SaleChange saleChange = saleChangeExt.getSaleChange();
		if(saleChange!=null) {
			try {
				BuyChangeExt buyChangeExt= buyChangeExtDao.selectBySaleChangeNoFindOne(saleChange.getSaleChangeNo());
				Long buyChangeNo = buyChangeExt.getBuyChangeNo();
				if(buyChangeExt!=null) {
					String remark = buyChangeExt.getChangeRemark();
					remark = remark + refundRequest.getRemark();
					buyChangeExt.setChangeRemark(remark);
					buyChangeExt.setAirLineRefundStatus(1);//二审审核通过
					buyChangeExt.setModifier(agent.getAccount());
					buyChangeExt.setModifyTime(modifyTime);
					log.info("退废单航司退款审核通过时修改变更变信息："+buyChangeExt.toString());
					buyChangeExtDao.updateByPrimaryKey(buyChangeExt);
					//更新采购员订单数量
					iTicketSenderService.updateByLockerId(agent, buyChangeExt.getBuyLocker(), "BUY_REFUSE_NUM");
				}
				BuyChange buyChange = buyChangeService.getBuyChangeByNo(agent,buyChangeNo);
				if(buyChange!=null){//1 已退款
					buyChange.setChildStatus(10);//采购审核完成  采购退款完成
					buyChange.setModifier(agent.getAccount());
					buyChange.setModifyTime(modifyTime);
					log.info("修改采购变更单信息"+buyChange.toString());
					buyChangeService.update(agent, buyChange);
				}
			}catch (Exception e){
              log.error("更新国际采购扩展单信息异常",e);
			}
		}
	}

	/**
	 * 退废单销售退款
	 * @param requestWithActor
	 */
	@Transactional
	@Override
	public void saleReturnMoney(RequestWithActor<PassengerRefundPriceVo> requestWithActor) {
		Agent agent = requestWithActor.getAgent();
		PassengerRefundPriceVo passengerRefundPriceVo = requestWithActor.getEntity();
		RequestWithActor<List<PassengerRefundPrice>> passengerRefundList = new RequestWithActor<List<PassengerRefundPrice>>();
		PassengerRefundPrice passengerRefundPrice = new PassengerRefundPrice();
		passengerRefundPrice.setSaleChangeNo(passengerRefundPriceVo.getSaleChangeNo());
		RequestWithActor<PassengerRefundPrice> params = new RequestWithActor<>();
		params.setAgent(agent);
		params.setEntity(passengerRefundPrice);
		List<PassengerRefundPrice> passengerRefundPrices = passengerRefundPriceService.queryPassengerRefundPriceList(params);
		passengerRefundList.setEntity(passengerRefundPrices);
		passengerRefundList.setAgent(agent);

		//创建应收应付
		refundService.saleVerify(passengerRefundList);
		//创建销售退款单
		refundService.saleRefund(agent,passengerRefundPriceVo.getSaleChangeNo());
	}

	//更新采购变更单扩展表航司审核状态为拒单
	@Transactional
	public void updateBuyExtRefund(RequestWithActor<Long> requestWithActor){
		Long saleChangeNo = requestWithActor.getEntity();
		Agent agent = requestWithActor.getAgent();
		BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
		if(buyChangeExt!=null){
			//采购拒单  将状态设置为-1
			buyChangeExt.setAirLineRefundStatus(-1);
			buyChangeExt.setModifier(agent.getAccount());
			buyChangeExt.setModifyTime(new Date());
			log.info("更新采购变更单扩展表航司审核状态为拒单:"+buyChangeExt.toString());
			buyChangeExtDao.updateByPrimaryKeySelective(buyChangeExt);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public int queryBuyRefundAndDelCountBylocker(Long userId) {
		int lockCount = saleChangeExtDao.queryBuyRefundAndDelCountBylocker(owner, userId);
		return lockCount;
	}

	@Override
	public void auditWasteTicket(RequestWithActor<wasteAuditPramaVo> request) throws GSSException {
		Agent agent = request.getAgent();
		wasteAuditPramaVo entity = request.getEntity();
		String changeRemark = entity.getChangeRemark();
		PassengerRefundPriceVo passengerRefundPriceVo = entity.getPassengerRefundPriceVo();
		Long saleChangeNo = entity.getSaleChangeNo();

		try {
			if (passengerRefundPriceVo.getPassengerRefundPriceList() != null
					&& passengerRefundPriceVo.getPassengerRefundPriceList().size() != 0) {
				//根据saleChangeNo进行修改退废价格信息
				for (Passenger passenger : passengerRefundPriceVo.getPassenger()) {
					for (PassengerRefundPrice passengerRefund : passengerRefundPriceVo.getPassengerRefundPriceList()) {
						if (passenger.getPassengerType().equals(passengerRefund.getPassengerType())
								|| ("1".equals(passenger.getPassengerType()) && "ADT".equals(passengerRefund.getPassengerType()))
								|| ("2".equals(passenger.getPassengerType()) && "CHD".equals(passengerRefund.getPassengerType()))
								|| ("3".equals(passenger.getPassengerType()) && "INF".equals(passengerRefund.getPassengerType()))) {
							//根据条件查询passengerRefundPriceNo
							RequestWithActor<PassengerRefundPrice> passengerRefundPrice = new RequestWithActor<>();
							PassengerRefundPrice price = new PassengerRefundPrice();
							price.setSaleChangeNo(passengerRefundPriceVo.getSaleChangeNo());
							price.setSaleOrderNo(passengerRefundPriceVo.getSaleOrderNo());
							price.setPassengerNo(passenger.getPassengerNo());
							passengerRefundPrice.setEntity(price);
							passengerRefundPrice.setAgent(agent);
							PassengerRefundPrice getPassengerChangePrice = passengerRefundPriceService.getPassengerRefundPrice(passengerRefundPrice);
							//根据passengerRefundPriceNo修改价格
							RequestWithActor<PassengerRefundPrice> saleADTPassengerRefundPrice = new RequestWithActor<>();
							passengerRefund.setPassengerRefundPriceNo(getPassengerChangePrice.getPassengerRefundPriceNo());
							passengerRefund.setSaleChangeNo(passengerRefundPriceVo.getSaleChangeNo());
							/*	passengerRefund.setCurrency(passengerRefundPriceVo.getCurrency());
								passengerRefund.setSaleCurrency(passengerRefundPriceVo.getSaleCurrency());
								passengerRefund.setExchangeRate(passengerRefundPriceVo.getExchangeRate());*/
							saleADTPassengerRefundPrice.setEntity(passengerRefund);
							saleADTPassengerRefundPrice.setAgent(agent);
							passengerRefundPriceService.updatePassengerRefundPrice(saleADTPassengerRefundPrice);
						}
					}
				}
			}

			RequestWithActor<Long> saleChangeRequest = new RequestWithActor<Long>();
			saleChangeRequest.setEntity(saleChangeNo);
			saleChangeRequest.setAgent(agent);
			Date time = new Date();
			SaleChangeExt saleChangeExt = refundService.getSaleChangeExtByNo(saleChangeRequest);

			if (saleChangeExt != null) {
				//点击审核通过时修改销售但状态为已审核
				if (saleChangeExt.getSaleChange() != null) {
					saleChangeService.updateStatus(agent, saleChangeExt.getSaleChange().getSaleChangeNo(), 2);
					//修改saleChangeExt的审核人员信息并解锁
					RequestWithActor<SaleChangeExt> saleOrderChangeExt = new RequestWithActor<>();
					long locker = saleChangeExt.getLocker();
					saleChangeExt.setStatus("2");
					saleChangeExt.setRefundWay(passengerRefundPriceVo.getRefundWay());
					saleChangeExt.setAuditPerson(agent.getAccount());
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					saleChangeExt.setAuditTime(simple.format(time));
					if (changeRemark != null && "" != changeRemark) {
						saleChangeExt.setChangeRemark(changeRemark);
					}
					saleChangeExt.setExchangeRate(passengerRefundPriceVo.getPassengerRefundPriceList().get(0).getExchangeRate());
					saleChangeExt.setSaleCurrency(passengerRefundPriceVo.getPassengerRefundPriceList().get(0).getSaleCurrency());
					saleOrderChangeExt.setEntity(saleChangeExt);
					saleOrderChangeExt.setAgent(agent);
					refundService.updateSaleChangeExt(saleOrderChangeExt);
					BuyChangeExt buyChangeExt = buyChangeExtService.queryBuyChgeExtByNo(saleChangeNo);
					if (buyChangeExt != null) {
						buyChangeExtService.updateBuyChangeByChangeNo(saleChangeRequest, "0");
					}
					ticketSenderService.updateByLockerId(agent, locker, "SALE_REFUSE_NUM");
					refundService.assignBuyWaste(saleChangeRequest);
				}
			}
		} catch (Exception e){
			log.error("废票销售审核异常："+e);
			throw new GSSException("废票销售审核异常:" + e, "0002", "废票销售审核异常");
		}
	}

	@Override
	public void auditRefundTicket(RequestWithActor<wasteAuditPramaVo> request) throws GSSException {
		Agent agent = request.getAgent();
		wasteAuditPramaVo entity = request.getEntity();
		String changeRemark = entity.getChangeRemark();
		PassengerRefundPriceVo passengerRefundPriceVo = entity.getPassengerRefundPriceVo();
		Long saleChangeNo = entity.getSaleChangeNo();

		RequestWithActor<Long> saleChangeRequest = new RequestWithActor<Long>();
		saleChangeRequest.setEntity(saleChangeNo);
		saleChangeRequest.setAgent(agent);
		try {
			List<PassengerRefundPrice> passengerRefundPrices = passengerRefundPriceVo.getPassengerRefundPriceList();
			String currency = passengerRefundPriceVo.getCurrency();
			if (passengerRefundPrices!=null && passengerRefundPrices.size()!= 0) {
				//根据saleChangeNo进行修改退废价格信息
				List<Passenger> passengerList = passengerRefundPriceVo.getPassenger();
				for (Passenger passenger : passengerList) {
					for (PassengerRefundPrice passengerRefund : passengerRefundPrices) {
						if (passenger.getPassengerType().equals(passengerRefund.getPassengerType())
								|| ("1".equals(passenger.getPassengerType()) && "ADT".equals(passengerRefund.getPassengerType()))
								|| ("2".equals(passenger.getPassengerType()) && "CHD".equals(passengerRefund.getPassengerType()))
								|| ("3".equals(passenger.getPassengerType()) && "INF".equals(passengerRefund.getPassengerType()))) {
							//根据条件查询passengerRefundPriceNo
							RequestWithActor<PassengerRefundPrice> passengerRefundPrice = new RequestWithActor<>();
							PassengerRefundPrice price = new PassengerRefundPrice();
							price.setSaleChangeNo(passengerRefundPriceVo.getSaleChangeNo());
							price.setSaleOrderNo(passengerRefundPriceVo.getSaleOrderNo());
							price.setPassengerNo(passenger.getPassengerNo());
							passengerRefundPrice.setEntity(price);
							passengerRefundPrice.setAgent(agent);
							PassengerRefundPrice getPassengerChangePrice = passengerRefundPriceService.getPassengerRefundPrice(passengerRefundPrice);
							//根据passengerRefundPriceNo修改价格
							RequestWithActor<PassengerRefundPrice> saleADTPassengerRefundPrice = new RequestWithActor<>();
							passengerRefund.setPassengerRefundPriceNo(getPassengerChangePrice.getPassengerRefundPriceNo());
							passengerRefund.setSaleChangeNo(passengerRefundPriceVo.getSaleChangeNo());
							if(currency != null && !"".equals(currency)){
								passengerRefund.setBuyCurrency(currency);//采购货币
							}
							BigDecimal buyExchangeRate = passengerRefundPriceVo.getBuyExchangeRate();
							if(buyExchangeRate != null){
								passengerRefund.setBuyExchangeRate(buyExchangeRate);//采购汇率
							}
							passengerRefund.setSaleCurrency(passengerRefundPriceVo.getSaleCurrency());//销售货币
							passengerRefund.setExchangeRate(passengerRefundPriceVo.getExchangeRate());//销售汇率
							saleADTPassengerRefundPrice.setEntity(passengerRefund);
							saleADTPassengerRefundPrice.setAgent(agent);
							log.info("-----"+passengerRefund.toString());
							passengerRefundPriceService.updatePassengerRefundPrice(saleADTPassengerRefundPrice);
						}
					}
				}
			}
			SaleChangeExt saleChangeExt = refundService.getSaleChangeExtByNo(saleChangeRequest);
			if (saleChangeExt != null) {
				//点击审核通过时修改销售但状态为已审核
				if (saleChangeExt.getSaleChange() != null) {
					long locker = 0l;
					//修改saleChange的子状态
					saleChangeService.updateStatus(agent,saleChangeExt.getSaleChange().getSaleChangeNo(),2);
					//修改saleChangeExt的审核人员信息并解锁
					RequestWithActor<SaleChangeExt> saleOrderChangeExt = new RequestWithActor<>();
					locker = saleChangeExt.getLocker();
					// saleChangeExt.setLocker(0L);
					saleChangeExt.setStatus("2");
					if (changeRemark !=null&&""!=changeRemark){
						saleChangeExt.setChangeRemark(changeRemark);
					}
					saleChangeExt.setRefundWay(passengerRefundPriceVo.getRefundWay());
					saleChangeExt.setAuditPerson(agent.getAccount());
					saleChangeExt.setCurrency(currency);
					saleChangeExt.setExchangeRate(passengerRefundPriceVo.getPassengerRefundPriceList().get(0).getExchangeRate());
					saleChangeExt.setSaleCurrency(passengerRefundPriceVo.getPassengerRefundPriceList().get(0).getSaleCurrency());
					SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					saleChangeExt.setAuditTime(simple.format(new Date()));
					saleOrderChangeExt.setEntity(saleChangeExt);
					saleOrderChangeExt.setAgent(agent);
					refundService.updateSaleChangeExt(saleOrderChangeExt);
					//审核完进行分单
					RequestWithActor<Long> requestWithActor = new RequestWithActor<>();
					requestWithActor.setAgent(agent);
					requestWithActor.setEntity(saleChangeExt.getSaleChangeNo());
					buyChangeExtService.updateBuyChangeByChangeNo(requestWithActor, "0");
					try {
						refundService.assignBuyRefund(requestWithActor);
					} catch (Exception e) {
						log.error("采购退票分单异常",e);
					}
					//更新出票员
					ticketSenderService.updateByLockerId(agent,locker,"SALE_REFUSE_NUM");
				}
			}
		} catch (Exception e) {
			log.error("退票销售审核异常",e);
			throw new GSSException("退票销售审核异常:" + e, "0002", "退票销售审核异常");
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
	private void taskAssign(List<SaleChangeExt> saleChangeExts, List<TicketSender> senders, Long maxOrderNum,Agent agent,Date updateTime,boolean isDistributeTicket){
		for (SaleChangeExt order : saleChangeExts) {
			if(!isDistributeTicket){
				//如果不是系统分单
				assingLockSaleChangeExt(order,updateTime);
				iTicketSenderService.updateByLockerId(agent,order.getAloneLocker(),"BUY_REFUSE_NUM");
			} else{
				log.info("第三步：判断出票员手头出票订单数量...");
				for (TicketSender peopleInfo : senders) {
					log.info(peopleInfo.getName() + "未处理采购废票单数量：" + peopleInfo.getBuyRefuseNum());
					if (peopleInfo.getBuyRefuseNum() >= maxOrderNum) {
						continue;
					} else {
						/**锁单*/
						log.info("第四步:满足条件的分配明细1.锁单,锁单人是被分配人...");
						BuyChangeExt buyChangeExt = assingLockSaleChangeExt(order, peopleInfo, updateTime, agent);
						/***增加出票人订单数*/
						log.info("2.增加出票人的未处理采购废票单数量...");
						peopleInfo.setUpdatetime(updateTime);
						increaseBuyRefuseNum(agent, buyChangeExt.getBuyLocker());
						break;
					}
				}
			}
		}
	}

	/**
	 * 直接将单分给在线业务员
	 * @param saleChangeExt
	 * @param senders
	 * @param maxOrderNum
	 * @param agent
	 * @param updateTime
	 */
	private void directAssign(SaleChangeExt saleChangeExt,List<TicketSender> senders, Long maxOrderNum,Agent agent,Date updateTime,boolean isDistributeTicket){
		log.info("第三步：判断出票员手头出票订单数量...");
		if(!isDistributeTicket){
			//如果不是系统分单
			assingLockSaleChangeExt(saleChangeExt,updateTime);
			iTicketSenderService.updateByLockerId(agent,saleChangeExt.getAloneLocker(),"BUY_REFUSE_NUM");
		} else{
			for (TicketSender peopleInfo : senders) {
				log.info(peopleInfo.getName() + "未处理采购单数量：" + peopleInfo.getBuyRefuseNum());
				if (peopleInfo.getBuyRefuseNum() >= maxOrderNum) {
					continue;
				} else {
					//log.info("第四步:满足条件的分配详细明细...1.将设置为出票中");
					/**锁单*/
					BuyChangeExt buyChangeExt = assingLockSaleChangeExt(saleChangeExt, peopleInfo, updateTime, agent);
					/***增加出票人订单数*/
					log.info("增加出票人的未处理采购单数量...");
					increaseBuyRefuseNum(agent, buyChangeExt.getBuyLocker());
					break;
				}
			}
		}
	}

	//获取最大分单数量
	private Long getMaxAbleAssignNum(Agent agent){
		Long maxOrderNum = 0L;
		IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
		Map config = configs.getConfig();
		String str_maxOrderNum = (String) config.get("maxOrderNum");
		if(StringUtils.isNotBlank(str_maxOrderNum)){
			maxOrderNum = Long.valueOf(str_maxOrderNum);
		}
		return  maxOrderNum;
	}

	//设置退废单扩展表的locker值
	private  SaleChangeExt setExtLockerVal(SaleOrder saleOrder,Agent agent,SaleChangeExt saleChangeExt){
		if(StringUtils.isNotBlank(saleOrder.getSourceChannelNo()) && StringUtils.equals("OP",saleOrder.getSourceChannelNo())){
			User user = userService.findUserByLoginName(agent,agent.getAccount());
			if(user!=null) {
				saleChangeExt.setLocker(user.getId());
			}
		}
		return  saleChangeExt;
	}

	//创建采购变更单扩展表
	private void createBuyChangeExt(Date createTime,Agent agent,Long buyChangeNo,String office,String ticketType){
      BuyChangeExt buyChangeExt = new BuyChangeExt();
      buyChangeExt.setAirLineRefundStatus(0);
      buyChangeExt.setOwner(agent.getOwner());
      buyChangeExt.setCreateTime(createTime);
      buyChangeExt.setValid((byte)1);
      buyChangeExt.setCreator(agent.getAccount());
      buyChangeExt.setStatus("1");
      buyChangeExt.setOffice(office);
      buyChangeExt.setTicketType(ticketType);
      buyChangeExt.setBuyChangeNo(buyChangeNo);
      log.info("创建采购变更单扩展表"+buyChangeExt.toString());
      buyChangeExtDao.insertSelective(buyChangeExt);
	}
}

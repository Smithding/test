package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mq.MqSender;
import com.tempus.gss.order.entity.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	PnrDao pnrDao;

	@Autowired
	SaleChangeDetailDao saleChangeDetailDao;

	@Autowired
	IBuyOrderExtService buyOrderExtService;

	@Reference
	ICertificateService certificateService;

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

	SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	@Override
	@Transactional
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
		new Thread(new Runnable() {
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
		}).start();
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
	public SaleChangeExt createRefundExt(RequestWithActor<RefundCreateVo> requestWithActor) {
		log.info("创建废退单申请开始===========");
		/* 创建销售废退拓展单 */
		SaleChangeExt saleChangeExt = new SaleChangeExt();
		
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

			//取最新的采购商
			if (buyOrderExtList != null && buyOrderExtList.size() != 0) {
				BuyOrderExt buyOrderExt = buyOrderExtList.get(0);
				/* 创建采购废退单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("IFT_BUY_CHANGE_NO", 47));
				buyChange.setOrderChangeType(requestWithActor.getEntity().getRefundType());
				buyChange.setBusinessSignNo(businessSignNo);
				if(requestWithActor.getEntity().getRefundType()==1) {
					buyChange.setBsignType(3);
				}
				if(requestWithActor.getEntity().getRefundType()==2) {
					buyChange.setBsignType(4);
				}
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(1);// 1.待审核 2.已审核 3.处理中
				// 10.已处理 11.已取消
				buyChange.setGoodsType(2);// 商品大类 2=国际机票
				buyChange.setGoodsSubType(22);// 采购退单
				buyChange.setGoodsName("");// TODO
				buyChange.setIncomeExpenseType(1);// 收支类型 1.收 2.支
				buyChange.setBuyOrderNo(buyOrderExt.getBuyOrderNo());
				buyChangeService.create(requestWithActor.getAgent(), buyChange);

				for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {

					/* 创建销售废退单明细 */
						SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
						saleChangeDetail
								.setSaleChangeDetailNo(maxNoService.generateBizNo("IFT_SALE_CHANGE_DETAIL_NO", 49));
						saleChangeDetail.setSaleChangeNo(saleChangeNo);
						saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
						saleChangeDetail.setSaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
						saleChangeDetail.setOwner(agent.getOwner());
						saleChangeDetail.setCreateTime(new Date());
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
			saleChange.setCreateTime(new Date());
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
			saleChangeExt.setCreateTime(new Date());
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
			log.info("申请退费单时保存的退费单信息:{}",saleChangeExt.toString());
			saleChangeExtDao.insertSelective(saleChangeExt);
			/*//销售退废分单操作
			Integer refundType = requestWithActor.getEntity().getRefundType();
			String refundTypeStr = "";
			if(new Integer(1).equals(refundType)){
				//废票
				refundTypeStr ="salesman-waste";
			} else {
				refundTypeStr ="salesman-refund";
			}
			IIftMessageService.sendRefuseMessage(requestWithActor.getEntity().getSaleOrderNo(),agent.getOwner()+"",refundTypeStr);*/

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
				passengerRefundPrice.setCreateTime(new Date());
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
	@Transactional
	public boolean lockRefund(RequestWithActor<Long> requestWithActor) {

		boolean flag = false;
		log.info("审核废退单锁定开始=========");
		try {

			Long saleChangeNo = requestWithActor.getEntity().longValue();
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
			long originLocker = saleChangeExt.getLocker();


			saleChangeExt.setLocker(requestWithActor.getAgent().getId());
			// 锁起状态为Id
			//saleChangeExt.setLocker(1L);
			saleChangeExt.setLockTime(new Date());
			//int updateFlag = saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
			int updateFlag = refundService.updateLocker(saleChangeExt);

			//锁定前先判断是否有locker
			if(originLocker != 0l){
				//对应locker出票员的num-1
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),originLocker,"SALE_REFUSE_NUM");
				iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),originLocker,"BUY_REFUSE_NUM");
			}
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

		log.info("审核废退单解锁开始=========");
		try {
			if (requestWithActor.getEntity().longValue() == 0) {
				log.error("销售单编号为空");
				throw new GSSException("销售单编号为空", "0001", "锁定废退单失败");
			}
			Long saleChangeNo = requestWithActor.getEntity().longValue();
			SaleChangeExt saleChangeExt = saleChangeExtDao.selectByPrimaryKey(saleChangeNo);
			long locker = saleChangeExt.getLocker();
			saleChangeExt.setLocker(0L);
			refundService.updateLocker(saleChangeExt);
			//对应出票员num-1
			iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),locker,"SALE_REFUSE_NUM");
			iTicketSenderService.updateByLockerId(requestWithActor.getAgent(),locker,"BUY_REFUSE_NUM");
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
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateLocker(SaleChangeExt saleChangeExt) {
		return  saleChangeExtDao.updateByPrimaryKeySelective(saleChangeExt);
	}

	/**
	 * 废退单审核 填入乘客退废税费
	 *
	 * @param passengerRefundList
	 * @return
	 */
	@Override
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
				try {
					CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
					certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
					certificateCreateVO.setReason("销售付款单信息"); //补充说明
					certificateCreateVO.setSubBusinessType(3); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
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

	/**
	 * 创建采购退款单
	 *
	 * @return
	 */
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
			certificateCreateVO.setSubBusinessType(3); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
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
			if (saleChangeExt != null) {
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
			saleChange.setChildStatus(4);// 设置废退状态 4为拒绝废退
			Date modifyTime = new Date();
			Agent agent = saleChangeNo.getAgent();
			saleChange.setModifier(agent.getAccount());
			saleChange.setModifyTime(modifyTime);
			saleChangeService.update(saleChangeNo.getAgent(), saleChange);// 更具传入变更单号，修改子状态
			SaleChangeExt saleChangeExt = this.getSaleChangeExtByNo(saleChangeNo);
			if(saleChangeExt!=null){
				Long locker = saleChangeExt.getLocker();
				saleChangeExt.setLocker(0L);
				saleChangeExt.setModifier(agent.getAccount());
				saleChangeExt.setModifyTime(modifyTime);
                saleChangeExtDao.updateLocker(saleChangeExt);
                Long saleOrderNo = saleChangeExt.getSaleChange().getSaleOrderNo();
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

							//乘机人
							if (saleOrderExt.getPassengerList() != null) {
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
							}
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
					}
					if (saleChangeExt.getPassengerRefundPriceList() != null
							&& saleChangeExt.getPassengerRefundPriceList().size() > 0) {
						saleChangeVo.setCost(saleChangeExt.getPassengerRefundPriceList().get(0).getSaleCount());//设置废退费用
					}
					saleChangeVo.setSaleChangeNo(saleChangeExt.getSaleChangeNo());//
					saleChangeVo.setAgentId(requestWithActor.getAgent().getId());
					saleChangeVo.setLocker(saleChangeExt.getLocker());
					//设置操作人
					saleChangeVo.setHandlers(saleChangeExt.getHandlers());
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
			int updateFlag = saleChangeExtDao.updateByPrimaryKeySelective(saleOrderChangeExt.getEntity());
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
		log.info("第二步：查询在线采购废票员...");
		List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-waste"); //采购废票人员
		log.info("是否有在线出票员:" + (senders != null));
		if (senders != null && senders.size() > 0) {
			Agent agent = new Agent(Integer.valueOf(owner));
			Long maxOrderNum = getMaxAbleAssignNum(agent);
			log.info("在线出票员人数:" + (senders.size()) + "获得配置最大分单数：" + maxOrderNum);
			Date updateTime = new Date();
			if ((wasteOrderNo == null || wasteOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts, senders, maxOrderNum, agent, updateTime);
			}else{
				derectAssign(saleChangeExt, senders, maxOrderNum, agent, updateTime);
			}
			log.info("此次分单结束...");
		} else {
			log.info("未查询在线出票员...");
		}

	}


	private void increaseBuyRefuseNum(Agent agent, TicketSender sender) {

		//查询该种类型单被该业务员锁住的数量赋值给BuyRefuseNum字段
		User user = userService.findUserByLoginName(agent, sender.getUserid());
		int lockCount = saleChangeExtDao.queryBuyRefundAndDelCountBylocker(owner, user.getId());
		sender.setBuyRefuseNum(lockCount);
		sender.setIds(sender.getId() + "");
		iTicketSenderService.update(sender);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void assingLockSaleChangeExt(SaleChangeExt order, TicketSender sender, Date date, Agent agent) {
		User user = userService.findUserByLoginName(agent, sender.getUserid());
		order.setLocker(user.getId());
		order.setLockTime(date);
		saleChangeExtDao.updateLocker(order);
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
		log.info("第二步：查询在线采购退票员...");
		List<TicketSender> senders = ticketSenderService.getSpecTypeOnLineTicketSender("buysman-refund"); //采购退票人员
		log.info("是否有在线出票员:" + (senders != null));
		if (senders != null && senders.size() > 0) {
			Agent agent = new Agent(Integer.valueOf(owner));
			Long maxOrderNum = getMaxAbleAssignNum(agent);
			log.info("有在线出业务员人数:" + (senders.size()) + "获得配置最大分单数：" + maxOrderNum);
			Date updateTime = new Date();
			if ((refundOrderNo == null || refundOrderNo == 0L) && saleChangeExts != null) {
				taskAssign(saleChangeExts, senders, maxOrderNum, agent, updateTime);
			}else{
				derectAssign(saleChangeExt, senders, maxOrderNum, agent, updateTime);
			}
			log.info("此次分单结束...");
		} else {
			log.info("未查询在线出票员...");
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
			for (TicketSender peopleInfo : senders) {
				log.info(peopleInfo.getName() + "未处理采购废票单数量：" + peopleInfo.getBuyRefuseNum());
				if (peopleInfo.getBuyRefuseNum() >= maxOrderNum) {
					continue;
				} else {
					/**锁单*/
					log.info("第四步:满足条件的分配明细...1.锁单,锁单人是被分配人...");
					assingLockSaleChangeExt(order, peopleInfo, updateTime, agent);
					/***增加出票人订单数*/
					log.info("2.增加出票人的未处理采购废票单数量...");
					peopleInfo.setUpdatetime(updateTime);
					increaseBuyRefuseNum(agent, peopleInfo);
					break;
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
	private void derectAssign(SaleChangeExt saleChangeExt,List<TicketSender> senders, Long maxOrderNum,Agent agent,Date updateTime){
		log.info("第三步：判断出票员手头出票订单数量...");
		for (TicketSender peopleInfo : senders) {
			log.info(peopleInfo.getName() + "未处理采购单数量：" + peopleInfo.getBuyRefuseNum());
			if (peopleInfo.getBuyRefuseNum() >= maxOrderNum) {
				continue;
			} else {
				log.info("第四步:满足条件的分配详细明细...1.将设置为出票中");
				/**锁单*/
				log.info("2.锁单,锁单人是被分配人...");
				assingLockSaleChangeExt(saleChangeExt, peopleInfo, updateTime, agent);
				/***增加出票人订单数*/
				log.info("3.增加出票人的未处理采购单数量...");
				increaseBuyRefuseNum(agent, peopleInfo);
				break;
			}
		}
	}

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

}

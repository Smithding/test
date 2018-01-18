package com.tempus.gss.product.ins.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.IAccountService;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.mss.entity.vo.MssApiInsCallbackVo;
import com.tempus.gss.mss.service.IMssReserveService;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.BuyChange;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.entity.CertificateCreateVO;
import com.tempus.gss.order.entity.CreatePlanAmountVO;
import com.tempus.gss.order.entity.PlanAmountRecord;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.IBuyChangeService;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.order.service.ICertificateService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.order.service.ISaleChangeService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.pay.service.facade.IPayRestService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.common.entity.Response;
import com.tempus.gss.product.common.util.HttpClientUtil;
import com.tempus.gss.product.common.util.MD5Util;
import com.tempus.gss.product.ins.api.entity.Insurance;
import com.tempus.gss.product.ins.api.entity.InsuranceProfit;
import com.tempus.gss.product.ins.api.entity.ProfitControl;
import com.tempus.gss.product.ins.api.entity.ResultInsure;
import com.tempus.gss.product.ins.api.entity.SaleChangeDetail;
import com.tempus.gss.product.ins.api.entity.SaleChangeExt;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.InsureExtVo;
import com.tempus.gss.product.ins.api.entity.vo.InsureRequestVo;
import com.tempus.gss.product.ins.api.entity.vo.OrderCancelVo;
import com.tempus.gss.product.ins.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderDetailVo;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ins.api.service.IInsuranceProfitService;
import com.tempus.gss.product.ins.api.service.IInsuranceService;
import com.tempus.gss.product.ins.api.service.IOrderService;
import com.tempus.gss.product.ins.dao.InsuranceDao;
import com.tempus.gss.product.ins.dao.OrderServiceDao;
import com.tempus.gss.product.ins.dao.OrderServiceReportDao;
import com.tempus.gss.product.ins.dao.ProfitControlDao;
import com.tempus.gss.product.ins.dao.SaleChangeDetailDao;
import com.tempus.gss.product.ins.dao.SaleChangeExtDao;
import com.tempus.gss.product.ins.dao.SaleOrderDetailDao;
import com.tempus.gss.product.ins.dao.SupplierDao;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.IParamService;
import com.tempus.gss.vo.Agent;

@Service(retries = 0, timeout = 60000)
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class OrderServiceImpl implements IOrderService {

	/**
	 * 保险经纪分配的渠道名
	 */
	private static final String INS_SOURCE_NAME = "ins_source_name";

	/**
	 * 退保地址
	 */
	private static final String INSURE_CANCEL_PATH = "insure_cancel_path";

	/**
	 * 投保地址
	 */
	private static final String INSURE_PATH = "insure_path";

	/**
	 * 保险经纪分配的渠道密钥
	 */
	private static final String INSURE_KEY = "insure_key";

	private static final String COMPANY_NAME_ZHONGAN = "众安";

	private static final String COMPANY_NAME_YATAI = "亚太";

	private static final String COMPANY_NAME_HEZONG = "合众";
	
	private static final String COMPANY_NAME_SHIDAI = "史带";	
	
	private static final String COMPANY_NAME_TAIPING = "太平";
	
	private static final String COMPANY_NAME_XINDA = "信达";
	//国内
	private static final String PLAN_TYPE_INLAND = "tphyi260";
	
	private static final String PLAN_TYPE_INTERNAT = "tphyi120";
	
	private static final String COMPANY_NAME_HUAXIA = "华夏";

	private static final String COMPANY_NAME_JUNLONG = "君龙";
	private static final String RESPONSE_SUCCESS = "0000";

	protected final transient Logger log = LoggerFactory.getLogger(getClass());

	// 应收应付
	@Reference
	IPlanAmountRecordService planAmountRecordService;

	@Autowired
	OrderServiceDao orderServiceDao;

	@Reference
	IMaxNoService maxNoService;

	@Autowired
	SaleOrderDetailDao saleOrderDetailDao;

	@Autowired
	InsuranceDao insuranceDao;

	@Autowired
	ProfitControlDao profitControlDao;

	@Reference
	ISaleOrderService saleOrderService;

	@Reference
	IBuyOrderService buyOrderService;

	@Autowired
	SaleChangeExtDao saleChangeExtDao;
    
	@Autowired
	OrderServiceReportDao orderServiceReportDao;
	
	
	@Autowired
	SupplierDao supplierDao;

	@Autowired
	SaleChangeDetailDao saleChangeDetailDao;

	@Reference
	IBuyChangeService buyChangeService;

	@Reference
	ISaleChangeService saleChangeService;
	@Reference
	IInsuranceService insuranceService;

	@Reference
	IParamService paramService;

	@Reference
	ICertificateService certificateService;

	@Reference
	private IAccountService accountService;

	@Reference
	private IPayRestService payRestService;

	@Reference
	ISupplierService supplierService;

	@Reference
	IMssReserveService mssReserveService;
	@Reference
	ICustomerService customerService;
    @Reference
    public IInsuranceProfitService insuranceProfitService;
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {

		log.info("创建保险订单开始==========");

		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.info("创建保险订单参数为空");
			throw new GSSException("创建保险订单参数为空", "1010", "创建保险订单失败");
		}
		Agent agent = requestWithActor.getAgent();
		if (requestWithActor.getEntity().getSaleOrderExt() == null) {
			log.info("保险销售单对象为空");
			throw new GSSException("保险销售单对象为空", "1010", "创建保险订单失败");
		}
		SaleOrderExt saleOrderExt = requestWithActor.getEntity().getSaleOrderExt();
		
		if (requestWithActor.getEntity().getOrderCreateWay() == null
				|| "".equals(requestWithActor.getEntity().getOrderCreateWay())) {
			log.info("创建订单方式orderCreateWay为空");
			throw new GSSException("创建订单方式orderCreateWay为空", "1010", "创建保险订单失败");
		}
		Integer orderCreateWay = requestWithActor.getEntity().getOrderCreateWay();
		
		if (saleOrderExt.getInsurance() == null) {
			log.info("保险产品insurance为空");
			throw new GSSException("保险产品insurance为空", "1010", "创建保险订单失败");
		}
		Insurance insurance = saleOrderExt.getInsurance();
		
		if (saleOrderExt.getInsurance().getInsuranceNo() == null
				|| "".equals(saleOrderExt.getInsurance().getInsuranceNo())) {
			log.info("保险产品编号insuranceNo为空");
			throw new GSSException("保险产品编号insuranceNo为空", "1010", "创建保险订单失败");
		}
		Long insuranceNo = saleOrderExt.getInsurance().getInsuranceNo();

		insurance = insuranceDao.selectByPrimaryKey(insuranceNo);
		if (insurance == null) {
			log.info("根据保险产品编号insuranceNo查询产品对象为空");
			throw new GSSException("根据保险产品编号insuranceNo查询产品对象为空", "1010", "创建保险订单失败");
		}

		if (insurance.getCompanyName() == null || "".equals(insurance.getCompanyName())) {
			log.info("保险产品的保险公司companyName为空");
			throw new GSSException("保险产品的保险公司companyName为空", "1010", "创建保险订单失败");
		}
		String companyName = insurance.getCompanyName();
		
		if (insurance.getInsureType() == null || "".equals(insurance.getInsureType())) {
			log.info("保险产品的险种类型insureType为空");
			throw new GSSException("保险产品的险种类型insureType为空", "1010", "创建保险订单失败");
		}
		String insureType = insurance.getInsureType();

		// 根据保险产品的保险名称和险种类型来判断该保险产品投保时是否需要传扩展字段对象
		InsureExtVo insureExtVo = null;
		if ((companyName.contains(COMPANY_NAME_YATAI) || companyName.contains(COMPANY_NAME_ZHONGAN))
				&& ("1".equals(insureType) || "2".equals(insureType) || "3".equals(insureType))) {
			if (saleOrderExt.getInsureExtVo() == null || "".equals(saleOrderExt.getInsureExtVo())) {
				log.info("保险扩展字段对象insureExtVo为空");
				throw new GSSException("保险扩展字段对象insureExtVo为空", "1010", "创建保险订单失败");
			}
			insureExtVo = saleOrderExt.getInsureExtVo();
		}

		if (insurance.getBuyWay() == null) {
			log.info("产品购买方式buyWay为空");
			throw new GSSException("产品购买方式buyWay为空", "0", "创建保险订单失败");
		}
		// 设置订单初始状态为未投保
		int orderChildStatus = 1;

		Long businessSignNo = IdWorker.getId();
		/*创建销售单*/
		SaleOrder saleOrder = saleOrderExt.getSaleOrder();
		if (null == saleOrder) {
			saleOrder = new SaleOrder();
			saleOrder.setOrderType(1);
		}
		Long saleOrderBizNo = maxNoService.generateBizNo("INS_SALE_ORDER_NO", 10);
		saleOrder.setSaleOrderNo(saleOrderBizNo);
		saleOrder.setBusinessSignNo(businessSignNo);
		saleOrder.setBsignType(1);
		Long customerTypeNo = saleOrder.getCustomerTypeNo();
		if (customerTypeNo == null) {
			log.info("客户类型customerTypeNo为空!");
			throw new GSSException("客户类型customerTypeNo为空!", "0", "创建保险订单失败");
		}
		if (301 != customerTypeNo && 302 != customerTypeNo && 303 != customerTypeNo && 306 != customerTypeNo) {
			log.info("客户类型customerTypeNo取值范围是301,302,303,306!");
			throw new GSSException("客户类型customerTypeNo取值范围是301,302,303,306!", "0", "创建保险订单失败");
		}
		saleOrder.setCustomerTypeNo(customerTypeNo);
		Long customerNo = saleOrder.getCustomerNo();
		if (customerNo == null) {
			saleOrder.setCustomerNo(1L);
		}
		saleOrder.setSourceChannelNo("ins");
		Long transactionOrderNo = saleOrder.getTransationOrderNo();
		if (transactionOrderNo != null) {
			saleOrder.setTransationOrderNo(transactionOrderNo);
		} else {
			saleOrder.setTransationOrderNo(saleOrderBizNo);
		}
		saleOrder.setOrderChildStatus(orderChildStatus);
		saleOrder.setOrderingLoginName(agent.getAccount());
		saleOrder.setGoodsType(3);
		saleOrder.setGoodsSubType(1);
		saleOrder.setGoodsName(insurance.getName());
		saleOrder.setPayStatus(1); // 待支付
		saleOrderExt.setSaleOrder(saleOrder);

		/* 创建采购单 */
		BuyOrder buyOrder = new BuyOrder();
		buyOrder.setSaleOrderNo(saleOrder.getSaleOrderNo());
		Long buyOrderBizNo = maxNoService.generateBizNo("INS_BUY_ORDER_NO", 81);
		buyOrder.setBuyOrderNo(buyOrderBizNo);
		buyOrder.setBusinessSignNo(businessSignNo);
		buyOrder.setBsignType(1);
		buyOrder.setGoodsType(3);
		buyOrder.setGoodsSubType(1);
		buyOrder.setGoodsName(insurance.getName());
		buyOrder.setBuyChannelNo("ins");
		Long supplierNo = insurance.getSupplierNo();
		Supplier supplier = supplierService.getSupplierByNo(requestWithActor.getAgent(),supplierNo);
		if (supplier == null) {
			log.error("保险供应商不存在");
			throw new GSSException("保险供应商不存在", "0101", "创建订单失败");
		}
		buyOrder.setSupplierTypeNo(supplier.getCustomerTypeNo());
		buyOrder.setSupplierNo(supplier.getSupplierNo());
		buyOrder.setBuyChildStatus(orderChildStatus); // 采购单子状态 未投保（1），已投保（2），已取消（3），投保异常（4），已退保（5）
		saleOrderExt.setBuyOrder(buyOrder);
		saleOrderExt.setSaleOrderNo(saleOrder.getSaleOrderNo());
		/*创建保险销售拓展单*/
		ObjectMapper mapper = new ObjectMapper();
		saleOrderExt.setCreateTime(new Date());
		saleOrderExt.setOwner(requestWithActor.getAgent().getOwner());
		saleOrderExt.setCreator(requestWithActor.getAgent().getAccount());
		saleOrderExt.setSaleOrderNo(saleOrder.getSaleOrderNo());
		saleOrderExt.setBuyOrderNo(buyOrder.getBuyOrderNo());
		saleOrderExt.setInsuranceNo(insuranceNo);
		saleOrderExt.setIssueDate(new Date());
		saleOrderExt.setOrderType(insurance.getBuyWay());
		if (saleOrderExt.getEffectDate() != null) {
			saleOrderExt.setEffectDate(saleOrderExt.getEffectDate());
		}
		if (saleOrderExt.getExpireDate() != null) {
			saleOrderExt.setExpireDate(saleOrderExt.getExpireDate());
		}
		if (saleOrderExt.getHolderType() != null
				&& "".equals(saleOrderExt.getHolderType())) {
			saleOrderExt.setHolderType(saleOrderExt.getHolderType());
		}
		if (StringUtils.isNotBlank(saleOrderExt.getHolderName())) {
			saleOrderExt.setHolderName(saleOrderExt.getHolderName());
		}
		if (saleOrderExt.getHolderCertiType() != null
				&& "".equals(saleOrderExt.getHolderCertiType())) {
			saleOrderExt.setHolderCertiType(saleOrderExt.getHolderCertiType());
		}
		if (StringUtils.isNotBlank(saleOrderExt.getHolderCertiNo())) {
			saleOrderExt.setHolderCertiNo(saleOrderExt.getHolderCertiNo());
		}
		if (saleOrderExt.getHolderSex() != null
				&& "".equals(saleOrderExt.getHolderSex())) {
			saleOrderExt.setHolderSex(saleOrderExt.getHolderSex());
		}
		if (saleOrderExt.getHolderBirthday() != null
				&& "".equals(saleOrderExt.getHolderBirthday())) {
			saleOrderExt.setHolderBirthday(saleOrderExt.getHolderBirthday());
		}
		if (StringUtils.isNoneBlank(saleOrderExt.getHolderEmail())) {
			saleOrderExt.setHolderEmail(saleOrderExt.getHolderEmail());
		}
		if (StringUtils.isNotBlank(saleOrderExt.getHolderPhone())) {
			saleOrderExt.setHolderPhone(saleOrderExt.getHolderPhone());
		}

		String productKey = insurance.getProductKey();
		if (StringUtils.isNotBlank(productKey)) {
			saleOrderExt.setProductKey(productKey);
		}

		BigDecimal salePrice = null;
		 Long customerType = Long.parseLong((customerTypeNo+"").substring(0, 3));
		List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(insuranceNo);
		if (orderCreateWay == 2) {
			salePrice = saleOrderExt.getSalePrice();
		} else {
			
			for (ProfitControl profitControl : profitControls) {
				if (customerType.toString().equals(profitControl.getCustomerTypeNo().toString())) {
					salePrice = profitControl.getSalePrice();
				}
			}
		}

		if (salePrice == null) {
			log.info("销售价为空");
			throw new GSSException("销售价为空", "1010", "创建保险订单失败");
		}else{
			saleOrderExt.setSalePrice(salePrice);
		}

		// 总投保份数
		int totalInsuredCount = 0;
		List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
		if (saleOrderDetailList != null && saleOrderDetailList.size() > 0) {
			for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
				Integer insuranceNum = saleOrderDetail.getInsuranceNum();
				totalInsuredCount += insuranceNum;
			}
		} else {
			log.info("被保人个数为空");
			throw new GSSException("被保人个数为空", "1010", "创建保险订单失败");
		}
		if (totalInsuredCount == 0) {
			log.info("总投保份数为0!");
			throw new GSSException("总投保份数为0!", "1010", "创建保险订单失败");
		}

		// 传过来的总保费
		BigDecimal totalPremium = saleOrderExt.getTotalPremium();
		// 根据控润渠道销售价计算的总保费
		BigDecimal premiums = salePrice;
		//获取分销商的采购价
		 List<Insurance> insuranceCai =   insuranceDao.selectInsuranceByNo(saleOrderExt.getInsuranceNo()) ;
		 saleOrderExt.setBuyPrice(insuranceCai.get(0).getBuyPrice());
		 //二级控润
        List<InsuranceProfit> insuranceProfitList = insuranceProfitService.queryInsuranceProfitByNo(agent, saleOrderExt.getInsuranceNo());
        BigDecimal a = new BigDecimal(100);
        for(InsuranceProfit insuranceProfit:insuranceProfitList){
            if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
          	  //判断是控点还是固定控  1为控点    2为固定控          
              if(insuranceProfit.getProfitMode()==1){
/*              	 if(insuranceProfit.getProfitCount()!=null){
              		 premiums = (premiums.multiply(insuranceProfit.getProfitCount()).divide(a)).add(premiums);    
            	   }else{
            		   Log.error("保险控点为空");
            	   }*/
            
             }else if(insuranceProfit.getProfitMode()==2){
          	   if(insuranceProfit.getProfitMoney()!=null){
          		   premiums = premiums.add(insuranceProfit.getProfitMoney());
          	   }else{
          		   Log.error("保险控现为空");
          	   }
          		   
             }
          }
        }
   
        saleOrderExt.setSalePrice(premiums);
        //单个保险再乘以购买保险的总人数
        SaleOrderExt SaleOrderExt = requestWithActor.getEntity().getSaleOrderExt();
        int personnum = 0;
        for(SaleOrderDetail saleOrderDetail:SaleOrderExt.getSaleOrderDetailList()){
        	if(saleOrderDetail.getInsuranceNum()==0){
        		personnum++;
        	}else{
        		personnum +=saleOrderDetail.getInsuranceNum();
        	}
        }
        //存储总保险分数
        saleOrderExt.setSumCopies(personnum);
        //存储面价
        saleOrderExt.setFacePrice(insurance.getFacePrice());
        if(personnum!=0){
        	premiums = premiums.multiply(new BigDecimal(personnum));
        }
      /*  //计算出来的单价乘以保险分数
        premiums = premiums.multiply(new BigDecimal(saleOrderExt.getInsuredCount()));*/
		if (totalPremium != null && premiums != null) {
			// 两个总保费比较
			if (totalPremium.compareTo(premiums) != 0) {
				log.error("总保费校验不通过!");
				throw new GSSException("总保费校验不通过!", "1010", "创建保险订单失败");
			}
			saleOrderExt.setTotalPremium(totalPremium);
		} else {
			log.error("总保费为空!");
			throw new GSSException("总保费为空!", "1010", "创建保险订单失败");
		}
		saleOrderExt.setValid(null);
		saleOrderExt.setInsurance(insurance);
	
		//根据保险险种来存储扩展信息
		if (saleOrderExt.getInsureExtVo() != null && (saleOrderExt.getInsureExtVo().getFlightNo() != null || saleOrderExt.getInsureExtVo().getFlightInfoVo() != null)) {
			if(!insurance.getCompanyName().contains(COMPANY_NAME_ZHONGAN)){
				saleOrderExt.getInsureExtVo().getFlightInfoVo().setDestinationCode(null);
				saleOrderExt.getInsureExtVo().getFlightInfoVo().setOriginCode(null);
			}
			if(insurance.getCompanyName().contains(COMPANY_NAME_TAIPING)){
				if(saleOrderExt.getInternatOrcivil()==1){
					//太平 保国内
					saleOrderExt.getInsureExtVo().getFlightInfoVo().setPlanType(PLAN_TYPE_INLAND);
				}else if(saleOrderExt.getInternatOrcivil()==2){
					//太平 保国际
					saleOrderExt.getInsureExtVo().getFlightInfoVo().setPlanType(PLAN_TYPE_INTERNAT);
				}
				saleOrderExt.getInsureExtVo().getFlightInfoVo().setQuantity(1);
			}
			String extendedFieldsJson = mapper.writeValueAsString(saleOrderExt.getInsureExtVo());
			saleOrderExt.setExtendedFieldsJson(extendedFieldsJson);
		}
		orderServiceDao.insertSelective(saleOrderExt);

		/**
		 * 为防止生成订单异常时,仍生成了销售单和采购单
		 * 故将生成销售单和采购单放在生成订单之后
		 */
		saleOrderService.create(requestWithActor.getAgent(), saleOrder);
		buyOrderService.create(requestWithActor.getAgent(), buyOrder);

		/* 创建子订单 */
		if (saleOrderExt.getSaleOrderDetailList() != null && !"".equals(saleOrderExt.getSaleOrderDetailList())) {
			for (SaleOrderDetail saleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
				saleOrderDetail.setInsuredNo(maxNoService.generateBizNo("INS_INSURED_NO", 46));
				// 设置子订单初始状态为1(未投保)
				saleOrderDetail.setStatus(1);
				if (saleOrderExt.getSaleOrderNo() == null) {
					throw new GSSException("主订单号为空,子订单无法插入", "1010", "订单创建失败");
				}
				saleOrderDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleOrderDetail.setOwner(agent.getOwner());
				saleOrderDetail.setCreateTime(new Date());
				saleOrderDetail.setCreator(agent.getId().toString());
				saleOrderDetail.setIsReport("0");
				saleOrderDetailDao.insertSelective(saleOrderDetail);
			}
		} else {
			log.info("保险子订单为空");
			throw new GSSException("保险子订单为空", "1010", "创建保险订单失败");
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
		if ("1".equals(orderCreateWay.toString())) {
			buyPrice = insurance.getBuyPrice();
		} else if ("2".equals(orderCreateWay.toString())) {
			buyPrice = saleOrderExt.getBuyPrice();
		} else {
			log.info("订单创建方式orderCreateWay错误!");
			throw new GSSException("订单创建方式orderCreateWay错误!", "1010", "创建保险订单失败");
		}
		if (buyPrice == null) {
			log.info("采购价为空");
			throw new GSSException("采购价为空", "1010", "创建保险订单失败");
		}
		BigDecimal buyOrderAmount = buyPrice.multiply(new BigDecimal(totalInsuredCount));
		buyOrderPlanAmountVO.setPlanAmount(buyOrderAmount);// 采购应付金额
		buyOrderPlanAmountVO.setGoodsType(saleOrderExt.getBuyOrder().getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
		buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
		buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
		buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
		buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
													// 销售废退， 5 销售改签 11 采购，12
													// 采购补单 13 补付 14 采购废退，15
													// 采购改签
		planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
		log.info("创建保险订单结束==========");
		return saleOrderExt.getSaleOrderNo();
	}


	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void buyInsureForBack(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {

		RequestWithActor<Long> buyInsureRequest = new RequestWithActor<>();
		buyInsureRequest.setAgent(requestWithActor.getAgent());
		buyInsureRequest.setEntity(requestWithActor.getEntity().getSaleOrderExt().getSaleOrderNo());
		this.buyInsure4Back(buyInsureRequest);
	}

	/**
	 * 
	 * buyInsureForWeb:前台投保
	 *
	 * @param  @param requestWithActor    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	//	private void buyInsureForWeb(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception {
	//
	//		RequestWithActor<Long> buyInsureRequest = new RequestWithActor<>();
	//		buyInsureRequest.setAgent(requestWithActor.getAgent());
	//		buyInsureRequest.setEntity(requestWithActor.getEntity().getSaleOrderExt().getSaleOrderNo());
	//		log.info("--------------------------------------调用 buyInsure-------------------------------------");
	//		this.buyInsure(buyInsureRequest);
	//	}

	@Transactional
	public SaleOrderExtVo buyInsure4Back(RequestWithActor<Long> requestWithActor) throws Exception {
		log.info("投保开始==============");
		SaleOrderExtVo saleOrderExtVo = null;
		String msg="";
		try {
			if (requestWithActor.getEntity() == null ||
					requestWithActor.getEntity() == 0) {
				log.info("投保参数为空");
				throw new GSSException("投保参数为空", "1010", "投保失败");
			}

			SaleOrderExt saleOrderExt = orderServiceDao.selectByPrimaryKey(requestWithActor.getEntity());
			if (saleOrderExt == null) {
				log.info("根据销售单编号查询保险订单结果为空");
				throw new GSSException("根据销售单编号查询保险订单结果为空", "1010", "投保失败");
			}
			String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
			saleOrderExt.setSourceName(sourceName);

			Insurance insurance = saleOrderExt.getInsurance();
			if (null == insurance) {
				log.error("该订单对应的保险产品为空!");
				throw new GSSException("该订单对应的保险产品为空!", "1010", "投保失败");
			}
			String companyName = insurance.getCompanyName();
			if (StringUtils.isBlank(companyName)) {
				log.error("保险产品的保险公司名称为空!");
				throw new GSSException("保险产品的保险公司名称为空!", "1010", "投保失败");
			}

			String insurePath = paramService.getValueByKey(INSURE_PATH);
			if (StringUtils.isBlank(insurePath)) {
				log.error("当前保险产品的投保请求路径为空,请在参数管理处配置!");
				throw new GSSException("当前保险产品的投保请求路径为空,请在参数管理处配置!", "1010", "投保失败");
			}

			List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
			if (saleOrderDetailList == null || saleOrderDetailList.size() == 0) {
				log.error("子订单为空!");
				throw new GSSException("子订单为空!", "1010", "投保失败");
			}
			// 对被保人循环进行投保操作
			for (SaleOrderDetail saleOrderDetail : saleOrderDetailList) {
				List<SaleOrderDetail> saleOrderDetails = new ArrayList<SaleOrderDetail>();
				SaleOrderDetail detail = new SaleOrderDetail();
				detail.setInsuredName(saleOrderDetail.getInsuredName());
				detail.setInsuredCertiType(saleOrderDetail.getInsuredCertiType());
				detail.setInsuredCertiNo(saleOrderDetail.getInsuredCertiNo());
				detail.setInsuredPhone(saleOrderDetail.getInsuredPhone());
				detail.setInsuredBirthday(saleOrderDetail.getInsuredBirthday()); // yyyyMMdd
				detail.setInsuredSex(saleOrderDetail.getInsuredSex());
				saleOrderDetails.add(detail);
				saleOrderExt.setSaleOrderDetailList(saleOrderDetails);

				/* 调用保险经纪的接口进行投保，请求是json串 */
				ObjectMapper mapper = new ObjectMapper();
				InsureRequestVo insureRequestVo = null;
				insureRequestVo = toInsureRequestVo4Back(saleOrderExt);

				String json = mapper.writeValueAsString(insureRequestVo);
				log.info("投保请求json报文------->" + json);

				@SuppressWarnings("unchecked")
				Response<SaleOrderExtVo> response = (Response<SaleOrderExtVo>) HttpClientUtil.doJsonPost(insurePath,
						json,
						Response.class);
				log.error("调用保险经济返回报文=======>"+response);
				if (response == null || "".equals(response)) {
					log.error("调用保险经纪接口出错");
					// 更新采购单状态为4(投保异常)
					buyOrderService.updateStatus(requestWithActor.getAgent(), requestWithActor.getEntity(), 4);
					throw new GSSException("调用保险经纪接口返回response为空", "1010", "投保失败");
				}
				if (RESPONSE_SUCCESS.equals(response.getCode())) {
					log.info("投保响应成功");
					// 更新采购单状态为2(已投保)
					saleOrderService.updateStatus(requestWithActor.getAgent(), requestWithActor.getEntity(), 2);
				} else {
					log.error(response.getMsg());
					throw new GSSException(response.getMsg(), "1010", "投保失败");
				}
				String jsonStr = JSON.toJSONString(response.getData());
				JSON js = JSON.parseObject(jsonStr);
				saleOrderExtVo = JSON.toJavaObject(js, SaleOrderExtVo.class);
				if (companyName.contains(COMPANY_NAME_YATAI)) {
					if (response.getMsg() != null) {
						saleOrderDetail.setPolicyNo(response.getMsg());
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}
				}

				if (companyName.contains(COMPANY_NAME_ZHONGAN)) {
					if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
						saleOrderDetail.setPolicyNo(saleOrderExtVo.getPolicyNo());
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}
				}

				orderServiceDao.updateByPrimaryKeySelective(saleOrderExt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("调用保险经纪接口出错");
			throw new GSSException("调用保险经纪接口出错", "1010", "投保失败");

		}
		log.info("投保结束==============");
		return saleOrderExtVo;
	}

	InsureRequestVo toInsureRequestVoForWeb(SaleOrderExt saleOrderExt) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Insurance insurance = saleOrderExt.getInsurance();
		List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();

		InsureRequestVo insureRequestVo = new InsureRequestVo();
		insureRequestVo.setProductKey(StringUtils.defaultString(insurance.getProductKey()));
		insureRequestVo.setEffectDate(saleOrderExt.getEffectDate());
		int sum = 0;
	    for(SaleOrderDetail saleOrderDetail:saleOrderDetailList){
	    	sum += saleOrderDetail.getInsuranceNum();
	    }
		insureRequestVo.setTotalPremium(insurance.getFacePrice().multiply(new BigDecimal(sum)));
		try {
			insureRequestVo.setIssueDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			log.error("转换issueDate异常");
			e.printStackTrace();
		}
		//君龙保险要求 投保人必须与被保人一致
		if(insurance.getCompanyName().contains(COMPANY_NAME_JUNLONG)){
			if(saleOrderExt.getSaleOrderDetailList().get(0)!=null){
				Calendar calendar = Calendar.getInstance();    
			    calendar.setTime(saleOrderExt.getExpireDate());
			    calendar.add(Calendar.SECOND, -1);
				try {
					insureRequestVo.setExpireDate(sdf.parse(sdf.format(calendar.getTime())));
				} catch (ParseException e1) {
					log.error("转换ExpireDate异常");
					e1.printStackTrace();
				}
					insureRequestVo.setPolicyHolderType(1);//1为个人 2为企业
					insureRequestVo.setPolicyHolderSex(Integer.valueOf(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredSex()));
					insureRequestVo.setPolicyHolderCertiType(Integer.valueOf(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredCertiType()));
					insureRequestVo.setPolicyHolderCertiNo(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredCertiNo());
					insureRequestVo.setPolicyHolderName(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredName());
					insureRequestVo.setPolicyHolderPhone(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredPhone());
				String policyHolderEmail = paramService.getValueByKey("ins_holder_email");
				if (StringUtils.isNotBlank(policyHolderEmail)) {
					insureRequestVo.setPolicyHolderEmail(policyHolderEmail);
				}
				  try {
					insureRequestVo.setPolicyHolderBirthday(simple.parse(saleOrderExt.getSaleOrderDetailList().get(0).getInsuredBirthday()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			insureRequestVo.setExpireDate(saleOrderExt.getExpireDate());
		String policyHolderType = paramService.getValueByKey("ins_holder_type");
		if (StringUtils.isNoneBlank(policyHolderType)) {
			insureRequestVo.setPolicyHolderType(Integer.valueOf(policyHolderType));
		}
		String policyHolderSex = paramService.getValueByKey("ins_holder_sex");
		if (insurance.getCompanyName().contains(COMPANY_NAME_HEZONG)&&StringUtils.isNoneBlank(policyHolderSex)) {
			insureRequestVo.setPolicyHolderSex(Integer.valueOf(policyHolderSex));
		}
		String policyHolderCertiType = paramService.getValueByKey("ins_holder_certi_type");
		if (StringUtils.isNotBlank(policyHolderCertiType)) {
			insureRequestVo.setPolicyHolderCertiType(Integer.valueOf(policyHolderCertiType));
		}
		String policyHolderCertiNo = paramService.getValueByKey("ins_holder_certi_no");
		if (StringUtils.isNotBlank(policyHolderCertiNo)) {
			insureRequestVo.setPolicyHolderCertiNo(policyHolderCertiNo);
		}
		String policyHolderName = paramService.getValueByKey("ins_holder_name");
		if (StringUtils.isNotBlank(policyHolderName)) {
			insureRequestVo.setPolicyHolderName(policyHolderName);
		}
		String policyHolderPhone = paramService.getValueByKey("ins_holder_phone");
		if (StringUtils.isNotBlank(policyHolderPhone)) {
			insureRequestVo.setPolicyHolderPhone(policyHolderPhone);
		}
		String policyHolderEmail = paramService.getValueByKey("ins_holder_email");
		if (StringUtils.isNotBlank(policyHolderEmail)) {
			insureRequestVo.setPolicyHolderEmail(policyHolderEmail);
		}
		String policyHolderBirthday = paramService.getValueByKey("ins_holder_birthday");
		if (StringUtils.isNotBlank(policyHolderBirthday)) {
			Date parse = null;
			try {
				parse = simple.parse(policyHolderBirthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			insureRequestVo.setPolicyHolderBirthday(parse);
		}
		}
		Date issueDate = null;
		String issueDateStr = "";
		if (null != insureRequestVo.getIssueDate() && !"".equals(insureRequestVo.getIssueDate())) {
			issueDate = insureRequestVo.getIssueDate();
			issueDateStr = DateUtil.getFormatDate(issueDate, "yyyy-MM-dd HH:mm:ss");
		}
		String insureKey = paramService.getValueByKey(INSURE_KEY);
		String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
		if (StringUtils.isNotBlank(insureKey)) {
			insureRequestVo.setSign(MD5Util.MD5(issueDateStr + insureKey));
		} else {
			log.error("当前保险产品key为空,请在参数管理处配置!");
			throw new GSSException("当前保险产品key为空,请在参数管理处配置!", "1010", "投保失败");
		}

		insureRequestVo.setSourceName(sourceName);
		try{
			//太平的扩展字段进行特殊处理   （详细请看文档）
			if(insurance.getCompanyName().contains(COMPANY_NAME_TAIPING)||insurance.getCompanyName().contains(COMPANY_NAME_JUNLONG)){
				InsureExtVo InsureExtVo=null;
				ObjectMapper mapper = new ObjectMapper();
				if(saleOrderExt.getExtendedFieldsJson()!=null){
					 InsureExtVo=mapper.readValue(saleOrderExt.getExtendedFieldsJson(), InsureExtVo.class);
					 InsureExtVo.getFlightInfoVo().setQuantity(saleOrderExt.getSaleOrderDetailList().get(0).getInsuranceNum());
				}
				String extendedFieldsJson = mapper.writeValueAsString(InsureExtVo.getFlightInfoVo());
				saleOrderExt.setExtendedFieldsJson(extendedFieldsJson);
				insureRequestVo.setPolicyHolderName(saleOrderDetailList.get(0).getInsuredName());
				insureRequestVo.setPolicyHolderPhone(saleOrderDetailList.get(0).getInsuredPhone());
/*				insureRequestVo.setPolicyHolderCertiNo(saleOrderDetailList.get(0).getInsuredCertiNo());
				insureRequestVo.setPolicyHolderCertiType(Integer.parseInt(saleOrderDetailList.get(0).getInsuredCertiType()));
				insureRequestVo.setPolicyHolderBirthday(saleOrderDetailList.get(0).getInsuredBirthday());*/
			}
		}catch(Exception e){
			log.error("json转对象，对象转json异常"+e,1);
		}
		insureRequestVo.setExtendedFieldSJson(saleOrderExt.getExtendedFieldsJson());
		insureRequestVo.setSaleOrderDetailList(null);
		insureRequestVo.setInsuredList(saleOrderDetailList);
		return insureRequestVo;
	}
	@Override
	public ResultInsure buyInsureForPerson(RequestWithActor<Long> requestWithActor, Long insuredNo) throws Exception {
		log.info("进入投保接口==============");
		ResultInsure result = new ResultInsure();
		SaleOrderExtVo saleOrderExtVo = null;
		try {
			if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null ||
					requestWithActor.getEntity() == 0) {
				log.info("投保参数为空");
				throw new GSSException("投保参数为空", "1010", "投保失败");
			}
			Agent agent = requestWithActor.getAgent();
			Long saleOrderNo = requestWithActor.getEntity();

			SaleOrderExt saleOrderExt = orderServiceDao.selectByPrimaryKey(saleOrderNo);
			if (saleOrderExt == null) {
				log.info("根据销售单编号查询保险订单结果为空");
				throw new GSSException("根据销售单编号查询保险订单结果为空", "1010", "投保失败");
			}
			String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
			saleOrderExt.setSourceName(sourceName);
			Insurance insurance = saleOrderExt.getInsurance();
			if (null == insurance) {
				log.error("该订单对应的保险产品为空!");
				throw new GSSException("该订单对应的保险产品为空!", "1010", "投保失败");
			}
			String companyName = insurance.getCompanyName();
			if (StringUtils.isBlank(companyName)) {
				log.error("保险产品的保险公司名称为空!");
				throw new GSSException("保险产品的保险公司名称为空!", "1010", "投保失败");
			}
			String code = insurance.getProductKey();
			if (StringUtils.isBlank(code)) {
				log.error("保险产品的code为空!");
				throw new GSSException("保险产品的code为空!", "1010", "投保失败");
			}

			String insurePath = paramService.getValueByKey(INSURE_PATH);
			if (StringUtils.isBlank(insurePath)) {
				log.error("当前保险产品的投保请求路径为空,请在参数管理处配置!");
				throw new GSSException("当前保险产品的投保请求路径为空,请在参数管理处配置!", "1010", "投保失败");
			}

			List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
			if (saleOrderDetailList == null || saleOrderDetailList.size() == 0) {
				log.error("子订单为空!");
				throw new GSSException("子订单为空!", "1010", "投保失败");
			}

			StringBuffer insureNoArray = new StringBuffer();
			// 对被保人循环进行投保操作
			for (SaleOrderDetail saleOrderDetail : saleOrderDetailList){
				if(saleOrderDetail.getInsuredNo().equals(insuredNo)){

						List<SaleOrderDetail> saleOrderDetails = new ArrayList<SaleOrderDetail>();
						SaleOrderDetail detail = new SaleOrderDetail();
						detail.setInsuredName(saleOrderDetail.getInsuredName());
						detail.setInsuredCertiType(saleOrderDetail.getInsuredCertiType());
						detail.setInsuredCertiNo(saleOrderDetail.getInsuredCertiNo());
						detail.setInsuredPhone(saleOrderDetail.getInsuredPhone());
						detail.setInsuredBirthday(saleOrderDetail.getInsuredBirthday()); // yyyyMMdd
						detail.setInsuredSex(saleOrderDetail.getInsuredSex());
						detail.setInsuranceNum(saleOrderDetail.getInsuranceNum());
						if(insurance.getCompanyName().contains(COMPANY_NAME_JUNLONG)){
							detail.setPremium(insurance.getFacePrice().multiply( new BigDecimal(saleOrderDetail.getInsuranceNum())));
						}
						//如果是华夏和太平险 则需要添加保险分数
						if(companyName.contains(COMPANY_NAME_HUAXIA)||companyName.contains(COMPANY_NAME_TAIPING)){
							detail.setInsuranceNum(saleOrderDetail.getInsuranceNum());
						}
						saleOrderDetails.add(detail);
						saleOrderExt.setSaleOrderDetailList(saleOrderDetails);
				/* 调用保险经纪的接口进行投保，请求是json串 */
				ObjectMapper mapper = new ObjectMapper();
				InsureRequestVo insureRequestVoForWeb = toInsureRequestVoForWeb(saleOrderExt);
				String json = mapper.writeValueAsString(insureRequestVoForWeb);
				log.info("投保请求报文=======> " + json);
				@SuppressWarnings("unchecked")
				Response<SaleOrderExtVo> response = (Response<SaleOrderExtVo>) HttpClientUtil.doJsonPost(insurePath,
						json,
						Response.class);
				log.error("调用保险经济返回报文=======>"+response);
				Long buyOrderNo = saleOrderExt.getBuyOrderNo();
				result.setCode(response.getCode());
				result.setMessage(response.getMsg());
				if (response == null || "".equals(response)) {
					log.error("调用保险经纪接口出错");
					// 更新采购单状态为4(投保异常)
					saleOrderService.updateStatus(agent, saleOrderNo, 4);
					buyOrderService.updateStatus(agent, buyOrderNo, 4);
					throw new GSSException("调用保险经纪接口返回response为空", "1010", "投保失败");
				}
				log.error("获取返回值------------------->"+response.getData());
				String jsonStr = JSON.toJSONString(response.getData());
				JSON js = JSON.parseObject(jsonStr);
				saleOrderExtVo = JSON.toJavaObject(js, SaleOrderExtVo.class);

				if (RESPONSE_SUCCESS.equals(response.getCode())) {
					log.info("投保响应成功");
					// 设置子订单状态为2(已投保)
					saleOrderDetail.setStatus(2);
					boolean panDuan = false;
					// 更新销售单和采购单状态为2(已投保)
					for(SaleOrderDetail saleOrderDetailForStatus:saleOrderDetailList){
						if(saleOrderDetailForStatus.getStatus()==1){
							panDuan = true;
						
						}
					}
			         if(panDuan==true){
			        	saleOrderService.updateStatus(agent, saleOrderNo, 7);
						buyOrderService.updateStatus(agent,buyOrderNo,7);
			         }else{
			        	 	saleOrderService.updateStatus(agent, saleOrderNo, 2);
							buyOrderService.updateStatus(agent,buyOrderNo,2); 
			         }
					if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
						saleOrderDetail.setPolicyNo(saleOrderExtVo.getPolicyNo());
						//投保时间
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}
					if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
						String policyNo = saleOrderExtVo.getPolicyNo();
						insureNoArray.append(policyNo).append(",");
					}
				} else {
					log.error(response.getMsg());
					saleOrderService.updateStatus(agent, saleOrderNo, 4);
					buyOrderService.updateStatus(agent, buyOrderNo, 4);
					throw new GSSException(response.getMsg(), "1010", "投保失败");
				}
				orderServiceDao.updateByPrimaryKeySelective(saleOrderExt);
				
			}
			}
			return result;
			
		}catch(GSSException e){
			log.error("投保失败！错误信息:"+result.getMessage());
			throw new Exception("个人重新投保失败！");
		}finally{
			return result;
		}

	}

	@Override
	@Transactional
	public boolean buyInsure(RequestWithActor<Long> requestWithActor) throws Exception {
		log.info("进入投保接口==============");
		SaleOrderExtVo saleOrderExtVo = null;
		try {
			if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null ||
					requestWithActor.getEntity() == 0) {
				log.info("投保参数为空");
				throw new GSSException("投保参数为空", "1010", "投保失败");
			}
			Agent agent = requestWithActor.getAgent();
			Long saleOrderNo = requestWithActor.getEntity();
			SaleOrderExt saleOrderExt = orderServiceDao.selectByPrimaryKey(saleOrderNo);
			if (saleOrderExt == null) {
				log.info("根据销售单编号查询保险订单结果为空");
				throw new GSSException("根据销售单编号查询保险订单结果为空", "1010", "投保失败");
			}
			String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
			saleOrderExt.setSourceName(sourceName);
			Insurance insurance = saleOrderExt.getInsurance();
			if (null == insurance) {
				log.error("该订单对应的保险产品为空!");
				throw new GSSException("该订单对应的保险产品为空!", "1010", "投保失败");
			}
			String companyName = insurance.getCompanyName();
			if (StringUtils.isBlank(companyName)) {
				log.error("保险产品的保险公司名称为空!");
				throw new GSSException("保险产品的保险公司名称为空!", "1010", "投保失败");
			}
			String code = insurance.getProductKey();
			if (StringUtils.isBlank(code)) {
				log.error("保险产品的code为空!");
				throw new GSSException("保险产品的code为空!", "1010", "投保失败");
			}

			String insurePath = paramService.getValueByKey(INSURE_PATH);
			if (StringUtils.isBlank(insurePath)) {
				log.error("当前保险产品的投保请求路径为空,请在参数管理处配置!");
				throw new GSSException("当前保险产品的投保请求路径为空,请在参数管理处配置!", "1010", "投保失败");
			}

			List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();
			if (saleOrderDetailList == null || saleOrderDetailList.size() == 0) {
				log.error("子订单为空!");
				throw new GSSException("子订单为空!", "1010", "投保失败");
			}

			StringBuffer insureNoArray = new StringBuffer();
			// 对被保人循环进行投保操作
			for (SaleOrderDetail saleOrderDetail : saleOrderDetailList){
					List<SaleOrderDetail> saleOrderDetails = new ArrayList<SaleOrderDetail>();
					SaleOrderDetail detail = new SaleOrderDetail();
					detail.setInsuredName(saleOrderDetail.getInsuredName());
					detail.setInsuredCertiType(saleOrderDetail.getInsuredCertiType());
					detail.setInsuredCertiNo(saleOrderDetail.getInsuredCertiNo());
					detail.setInsuredPhone(saleOrderDetail.getInsuredPhone());
					detail.setInsuredBirthday(saleOrderDetail.getInsuredBirthday()); // yyyyMMdd
					detail.setInsuredSex(saleOrderDetail.getInsuredSex());
					detail.setInsuranceNum(saleOrderDetail.getInsuranceNum());
					if(insurance.getCompanyName().contains(COMPANY_NAME_JUNLONG)){
						detail.setPremium(insurance.getFacePrice().multiply( new BigDecimal(saleOrderDetail.getInsuranceNum())));
					}
					//如果是华夏和太平险 则需要添加保险分数
					if(companyName.contains(COMPANY_NAME_HUAXIA)||companyName.contains(COMPANY_NAME_TAIPING)){
						detail.setInsuranceNum(saleOrderDetail.getInsuranceNum());
					}
					saleOrderDetails.add(detail);
					saleOrderExt.setSaleOrderDetailList(saleOrderDetails);

				/* 调用保险经纪的接口进行投保，请求是json串 */
				ObjectMapper mapper = new ObjectMapper();
				InsureRequestVo insureRequestVoForWeb = toInsureRequestVoForWeb(saleOrderExt);
				String json = mapper.writeValueAsString(insureRequestVoForWeb);
				log.info("投保请求报文=======> " + json);

				@SuppressWarnings("unchecked")
				Response<SaleOrderExtVo> response = (Response<SaleOrderExtVo>) HttpClientUtil.doJsonPost(insurePath,
						json,
						Response.class);
				log.error("调用保险经济返回报文=======>"+response);
				Long buyOrderNo = saleOrderExt.getBuyOrderNo();
				if (response == null || "".equals(response)) {
					log.error("调用保险经纪接口出错");
					// 更新采购单状态为4(投保异常)
					saleOrderService.updateStatus(agent, saleOrderNo, 4);
					buyOrderService.updateStatus(agent, buyOrderNo, 4);
					throw new GSSException("调用保险经纪接口返回response为空", "1010", "投保失败");
				}
				log.error("投保放回数据-------》"+response.getData());
					String jsonStr = JSON.toJSONString(response.getData());
					JSON js = JSON.parseObject(jsonStr);
					saleOrderExtVo = JSON.toJavaObject(js, SaleOrderExtVo.class);
				if (RESPONSE_SUCCESS.equals(response.getCode())) {
					log.info("投保响应成功");
					// 设置子订单状态为2(已投保)
					saleOrderDetail.setStatus(2);
					// 更新销售单和采购单状态为2(已投保)
					if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
						log.error("获取保险保单成功--------》"+saleOrderExtVo.getPolicyNo());
						saleOrderDetail.setPolicyNo(saleOrderExtVo.getPolicyNo());
						//投保时间
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}
					saleOrderService.updateStatus(agent, saleOrderNo, 2);
					log.error("调用采购单开始--------》");
					buyOrderService.updateStatus(agent,buyOrderNo,2);
					log.error("调用采购单结束--------》");
				} else {
					log.error(response.getMsg());
					saleOrderService.updateStatus(agent, saleOrderNo, 4);
					buyOrderService.updateStatus(agent, buyOrderNo, 4);
					throw new GSSException(response.getMsg(), "1010", "投保失败");
				}
				if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
					log.error("获取保险保单成功--------》"+saleOrderExtVo.getPolicyNo());
					saleOrderDetail.setPolicyNo(saleOrderExtVo.getPolicyNo());
					saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
				}
               
			/*	if (insureRequestVoForWeb.getPolicyHolderType() != null
						&& "".equals(insureRequestVoForWeb.getPolicyHolderType())) {
					saleOrderExt.setHolderType(insureRequestVoForWeb.getPolicyHolderType());
				}
				if (StringUtils.isNotBlank(insureRequestVoForWeb.getPolicyHolderName())) {
					saleOrderExt.setHolderName(insureRequestVoForWeb.getPolicyHolderName());
				}
				if (insureRequestVoForWeb.getPolicyHolderCertiType() != null
						&& "".equals(insureRequestVoForWeb.getPolicyHolderCertiType())) {
					saleOrderExt.setHolderCertiType(insureRequestVoForWeb.getPolicyHolderCertiType());
				}
				if (StringUtils.isNotBlank(insureRequestVoForWeb.getPolicyHolderCertiNo())) {
					saleOrderExt.setHolderCertiNo(insureRequestVoForWeb.getPolicyHolderCertiNo());
				}
				if (insureRequestVoForWeb.getPolicyHolderSex() != null
						&& "".equals(insureRequestVoForWeb.getPolicyHolderSex())) {
					saleOrderExt.setHolderSex(insureRequestVoForWeb.getPolicyHolderSex());
				}
				if (insureRequestVoForWeb.getPolicyHolderBirthday() != null
						&& "".equals(insureRequestVoForWeb.getPolicyHolderBirthday())) {
					saleOrderExt.setHolderBirthday(insureRequestVoForWeb.getPolicyHolderBirthday());
				}
				if (StringUtils.isNoneBlank(insureRequestVoForWeb.getPolicyHolderEmail())) {
					saleOrderExt.setHolderEmail(insureRequestVoForWeb.getPolicyHolderEmail());
				}
				if (StringUtils.isNotBlank(insureRequestVoForWeb.getPolicyHolderPhone())) {
					saleOrderExt.setHolderPhone(insureRequestVoForWeb.getPolicyHolderPhone());
				}*/
				// 为创建采购付款单获取保单号列
				if (StringUtils.isNotBlank(saleOrderExtVo.getPolicyNo())) {
					String policyNo = saleOrderExtVo.getPolicyNo();
					log.error("获取保险保单号列--------》");
					insureNoArray.append(policyNo).append(",");
				}

				orderServiceDao.updateByPrimaryKeySelective(saleOrderExt);
			}

			/**
			 * 创建采购付款单 
			 *//*
			// 月结使用欠款账户
			// 获取保险产品的供应商编号
			Long supplierNo = insurance.getSupplierNo();
			// 查询供应商
			Supplier supplier = supplierService.getSupplierByNo(agent,supplierNo);
			// 根据供应商编号查询保险供应商
			InsSupplier insSupplier = supplierDao.selectByPrimaryKey(supplierNo);
			Long buyOrderNo = saleOrderExt.getBuyOrderNo();
			BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);

			if (insureNoArray.length() > 0) {
				insureNoArray.deleteCharAt(insureNoArray.length() - 1);
			}

			if (insurance.getPayModel() == 1) {
				log.info("使用月结账户支付----------");
				Long accountNo = Long.parseLong(supplier.getAccountNo());
				if (accountNo == null) {
					log.error("开户账户为空!");
					throw new GSSException("开户账户编号为空!", "1010", "投保失败");
				}
				Account account = accountService.getAccountByAccountNo(agent,accountNo);
				if (account != null) {
					this.createBuyCertificate(agent, buyOrder.getBuyOrderNo(), buyOrder.getPayable().doubleValue(),
							account.getAccountNo(), supplier.getSupplierNo(), supplier.getCustomerTypeNo(), 2,
							2000003,
							"BUY", insureNoArray.toString());
				}
			} else {
				// 现结使用支付账户
				log.info("使用现结账户支付----------");
				Long capitalAccountNo = insSupplier.getCapitalAccountNo();
				if (capitalAccountNo == null) {
					log.error("支付账户为空!");
					throw new GSSException("开户账户为空!", "1010", "投保失败");
				}
				CapitalAccount capitalAccount = payRestService.queryCapitalAccountByNo(agent, capitalAccountNo);
				if (capitalAccount != null) {
					this.createBuyCertificate(agent, buyOrder.getBuyOrderNo(), buyOrder.getPayable().doubleValue(),
							capitalAccount.getAccountNo(), supplier.getSupplierNo(), supplier.getCustomerTypeNo(),
							1, capitalAccount.getPayWayCode(), "BUY",
							insureNoArray.toString());
				}
			}*/
			try{
				//mss平台承保通知接口
				MssApiInsCallbackVo vo = new MssApiInsCallbackVo();
				vo.setInsOrderId(saleOrderNo);
				vo.setInsNumber(saleOrderDetailList.size());
				vo.setPrice(saleOrderExt.getTotalPremium().doubleValue());
				vo.setCertNo(insureNoArray.toString());
				mssReserveService.acceptInsInform(agent,vo);
			}catch (Exception e){
				log.info("mss平台承保通知接口出错");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("调用保险经纪接口出错");
			throw new GSSException("调用保险经纪接口出错", "1010", "投保失败");

		}
		log.info("退出投保接口==============");
		return true;
	}

	/**
	 * 
	 * toInsureRequestVo4Back:转换成投保请求vo
	 *
	 * @param  @param saleOrderExt
	 * @param  @return    设定文件
	 * @return InsureRequestVo    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private InsureRequestVo toInsureRequestVo4Back(SaleOrderExt saleOrderExt) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Insurance insurance = saleOrderExt.getInsurance();
		List<SaleOrderDetail> saleOrderDetailList = saleOrderExt.getSaleOrderDetailList();

		InsureRequestVo insureRequestVo = new InsureRequestVo();
		insureRequestVo.setProductKey(StringUtils.defaultString(insurance.getProductKey()));
		insureRequestVo.setEffectDate(saleOrderExt.getEffectDate());
		insureRequestVo.setExpireDate(saleOrderExt.getExpireDate());
		int sum = 0;
		 for(SaleOrderDetail saleOrderDetail:saleOrderDetailList){
		    	sum += saleOrderDetail.getInsuranceNum();
		    }
			insureRequestVo.setTotalPremium(insurance.getFacePrice().multiply(new BigDecimal(sum)));
		try {
			insureRequestVo.setIssueDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			log.error("转换issueDate异常");
			e.printStackTrace();
		}
		insureRequestVo.setPolicyHolderType(saleOrderExt.getHolderType());
		insureRequestVo.setPolicyHolderCertiType(saleOrderExt.getHolderCertiType());
		insureRequestVo.setPolicyHolderCertiNo(StringUtils.defaultString(saleOrderExt.getHolderCertiNo()));
		insureRequestVo.setPolicyHolderName(StringUtils.defaultString(saleOrderExt.getHolderName()));
		insureRequestVo.setPolicyHolderBirthday(saleOrderExt.getHolderBirthday());
		insureRequestVo.setPolicyHolderPhone(StringUtils.defaultString(saleOrderExt.getHolderPhone()));
		insureRequestVo.setPolicyHolderEmail(StringUtils.defaultString(saleOrderExt.getHolderEmail()));
		Date issueDate = null;
		String issueDateStr = "";
		if (null != insureRequestVo.getIssueDate() && !"".equals(insureRequestVo.getIssueDate())) {
			issueDate = insureRequestVo.getIssueDate();
			issueDateStr = DateUtil.getFormatDate(issueDate, "yyyy-MM-dd HH:mm:ss");
		}
		String insureKey = paramService.getValueByKey(INSURE_KEY);
		if (StringUtils.isNotBlank(insureKey)) {
			insureRequestVo.setSign(MD5Util.MD5(issueDateStr + insureKey));
		} else {
			log.error("当前保险产品key为空,请在参数管理处配置!");
			throw new GSSException("当前保险产品key为空,请在参数管理处配置!", "1010", "投保失败");
		}
		String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
		insureRequestVo.setSourceName(sourceName);
		insureRequestVo.setExtendedFieldSJson(saleOrderExt.getExtendedFieldsJson());
		insureRequestVo.setSaleOrderDetailList(saleOrderDetailList);

		return insureRequestVo;
	}

	@Override
/*	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)*/
	public ResultInsure cancelSaleOrder(RequestWithActor<OrderCancelVo> requestWithActor,int isRefund) throws Exception {

		log.info("线上产品退保开始==============");
		ResultInsure resultInsure = new ResultInsure();
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null ||
				requestWithActor.getEntity().getPolicyNoList().isEmpty()) {
			log.error("退保单号为空");
			throw new GSSException("退保单号为空", "1010", "退保失败");
		}
		log.info("退保入参{}",JSONObject.toJSONString(requestWithActor.getEntity().getPolicyNoList()));
		Agent agent = requestWithActor.getAgent();
		List<String> policyNoList = requestWithActor.getEntity().getPolicyNoList();

		SaleOrderExt saleOrderExt = null;
		Long saleOrderNo = null;
		String error = null;
		StringBuffer policyNoArray = new StringBuffer();
		for (String policyNo : policyNoList) {
			policyNoArray.append(policyNo+",");
			List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectDetailByPolicyNo(policyNo);
			if (saleOrderDetails == null || saleOrderDetails.size() > 1 || saleOrderDetails.size() == 0) {
				log.error("根据保单号查询子订单错误!");
				throw new GSSException("根据保单号查询子订单错误!", "1010", "退保失败");
			}
			SaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);
			saleOrderExt = orderServiceDao.selectByPolicyNo(policyNo);
			if (saleOrderExt == null) {
				log.info("根据退保单号查询订单结果为空!");
				throw new GSSException("根据退保单号查询订单结果为空!", "1010", "退保失败");
			}
			if (saleOrderExt.getInsurance() == null) {
				log.info("订单的保险产品insurance为空");
				throw new GSSException("订单的保险产品insurance为空", "1010", "退保失败");
			}
			Insurance insurance = saleOrderExt.getInsurance();
        
			try {
				Long businessSignNo = IdWorker.getId();
				Long saleChangeNo = maxNoService.generateBizNo("INS_SALE_CHANGE_EXT_NO", 51);

				saleOrderNo = saleOrderExt.getSaleOrderNo();
				if (saleOrderNo == null) {
					log.error("saleOrderNo为空");
					throw new GSSException("saleOrderNo为空", "1010", "退保失败");
				}
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
				if (saleOrder == null) {
					log.error("根据saleOrderNo查询saleOrder为空");
					throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退保失败");
				}

				/* 创建采购变更单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("INS_BUY_CHANGE_NO", 53));
				buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
				buyChange.setBusinessSignNo(businessSignNo);
				buyChange.setBsignType(4);
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				buyChange.setGoodsType(3);//商品大类 3 保险
				buyChange.setGoodsSubType(0);//TODO 没有小类为0
				buyChange.setGoodsName("");//TODO
				buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
				if (saleOrderExt.getBuyOrderNo() != null) {
					buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
				} else {
					log.error("采购单编号buyOrderNo为空");
					throw new GSSException("采购单编号buyOrderNo为空", "1010", "退保失败");
				}
				buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if (buyChange == null) {
					log.error("创建采购变更单失败");
					throw new GSSException("创建采购变更单失败", "1010", "退保失败");
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
				saleChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				saleChange.setGoodsType(3);// 商品大类 3 保险
				saleChange.setGoodsSubType(0);//TODO 没有小类为0
				saleChange.setGoodsName(insurance.getName());//TODO
				// 设置交易单号
				Long transationOrderNo = saleOrder.getTransationOrderNo();
				if (transationOrderNo == null) {
					log.error("transationOrderNo为空");
					throw new GSSException("transationOrderNo为空", "1010", "退保失败");
				}
				saleChange.setTransationOrderNo(transationOrderNo);//交易号
				saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
				saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
				if (saleChange == null) {
					log.error("创建销售变更单失败");
					throw new GSSException("创建销售变更单失败", "1010", "退保失败");
				}
				// 创建销售变更明细
				SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
				saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("INS_SALE_CHANGE_DETAIL_NO", 52));
				saleChangeDetail.setSaleChangeNo(saleChangeNo);
				saleChangeDetail.setStatus("2"); // 已退保
				saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
				saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleChangeDetail.setOwner(agent.getOwner());
				saleChangeDetail.setCreator(agent.getAccount());
				saleChangeDetail.setCreateTime(new Date());
				int saleChangeDetailNum = saleChangeDetailDao.insertSelective(saleChangeDetail);
				if (saleChangeDetailNum == 0) {
					log.error("创建销售变更明细失败");
					throw new GSSException("创建销售变更明细失败", "1010", "退保失败");
				}
				BigDecimal salePrice = null;
				salePrice = saleOrderDetail.getPremium();
				if (salePrice == null) {
					log.info("订单销售价salePrice为空");
					throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
				}
				// 创建保险变更扩展单
				SaleChangeExt saleChangeExt = new SaleChangeExt();
				/* 设置销售单拓展编号 */
				saleChangeExt.setSaleChangeNo(saleChangeNo);
				saleChangeExt.setOwner(agent.getOwner());
				saleChangeExt.setCreateTime(new Date());
				saleChangeExt.setChangeType(1);
				saleChangeExt.setCreator(agent.getAccount());
				saleChangeExt.setRightRefund(salePrice);
				saleChangeExt.setValid(true);
				saleChangeExt.setSaleOrderNo(saleOrderNo);
				saleChangeExt.setInsuredNo(saleOrderDetail.getInsuredNo());
				int saleChangeExtNum = saleChangeExtDao.insertSelective(saleChangeExt);
				if (saleChangeExtNum == 0) {
					log.error("创建保险变更扩展单失败"); 
					throw new GSSException("创建保险变更扩展单失败", "1010", "退保失败");
				}
				Long insuranceNo = insurance.getInsuranceNo();
				if (insuranceNo == null) {
					log.info("保险产品的insuranceNo为空");
					throw new GSSException("保险产品的insuranceNo为空", "1010", "退保失败");
				}
				Long customerTypeNo = saleOrder.getCustomerTypeNo();
				if (customerTypeNo == null) {
					log.info("客户类型customerTypeNo为空!");
					throw new GSSException("客户类型customerTypeNo为空!", "0", "退保失败");
				}
		
				// 创建销售应付记录
				CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
		
				saleOrderPlanAmountVO.setPlanAmount(salePrice);
				saleOrderPlanAmountVO.setGoodsType(saleOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
				saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
				saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
				saleOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
				saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
				
				// 创建采购应收记录
				Long buyOrderNo = saleOrderExt.getBuyOrderNo();
				if (buyOrderNo == null) {
					log.error("buyOrderNo为空");
					throw new GSSException("buyOrderNo为空", "1010", "退保失败");
				}
				BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
				if (buyOrder == null) {
					log.error("根据buyOrderNo查询buyOrder为空");
					throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
				}
				CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
				BigDecimal buyPrice = insurance.getBuyPrice();
				if (buyPrice == null) {
					log.info("保险产品采购价buyPrice为空");
					throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
				}
				buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
				buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
				buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
				buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
				buyOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
				buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
				// 销售废退， 5 销售改签 11 采购，12
				// 采购补单 13 补付 14 采购废退，15
				boolean isCancel = false;
				 boolean isCancel2 = false;
/*				if(isRefund==1){
					// 采购改签
					PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, saleOrderPlanAmountVO);
					// 采购改签
					PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(agent, buyOrderPlanAmountVO);
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
				
					if (planAmountRecord == null) {
						log.info("创建销售应付记录失败");
						throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
					}
					if (planAmountRecord1 == null) {
						log.info("创建采购应收记录失败");
						throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
					}
				}*/
				
			
				ObjectMapper mapper = new ObjectMapper();
				OrderCancelVo orderCancelVo = toOrderCancelVo(saleOrderExt, policyNo);
				String json = mapper.writeValueAsString(orderCancelVo);

				String insureCancelPath = paramService.getValueByKey(INSURE_CANCEL_PATH);
				if (StringUtils.isBlank(insureCancelPath)) {
					log.error("当前保险产品的退保请求路径为空,请在参数管理处配置!");
					throw new GSSException("当前保险产品的退保请求路径为空,请在参数管理处配置!", "1010", "退保失败");
				}
				@SuppressWarnings("unchecked")
				Response<JSONObject> response = (Response<JSONObject>) HttpClientUtil.doJsonPost(insureCancelPath,
						json,
						Response.class);
				if(response!=null){
					error = response.getMsg();
					resultInsure.setCode(response.getCode());
					resultInsure.setMessage(response.getMsg());
				}
				log.info("保险经纪接口响应:"+JSONObject.toJSONString(response));

				if (RESPONSE_SUCCESS.equals(response.getCode())||"该保单已退保，请不要重复操作".equals(response.getMsg())) {
					// 设置子订单状态为3(已退保)
				
					if(isRefund==1){
						// 采购改签
						PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, saleOrderPlanAmountVO);
						// 采购改签
						PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(agent, buyOrderPlanAmountVO);
						// 销售废退， 5 销售改签 11 采购，12
						// 采购补单 13 补付 14 采购废退，15
					
						if (planAmountRecord == null) {
							log.info("创建销售应付记录失败");
							throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
						}
						if (planAmountRecord1 == null) {
							log.info("创建采购应收记录失败");
							throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
						}
					/*	for(SaleOrderDetail saleOrderDetailChange:saleOrderExt.getSaleOrderDetailList()){
							if(saleOrderDetail.getInsuredNo().equals(saleOrderDetailChange.getInsuredNo())){
								isCancel = true;
							}
							if(saleOrderDetailChange.getStatus()==8){
			         			isCancel = true;
			         		}
							
						}
						if(isCancel==false){
							SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 9);
			         	}else{*/
						saleOrderDetail.setStatus(8);//退款中
						int s = saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
			         	   SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 8);//退款中
/*			         	}*/
						
						// 创建销售退款单
						this.saleRefund(agent, saleOrderNo);
					}else{
						isCancel = false;
						for(SaleOrderDetail saleOrderDetailChange:saleOrderExt.getSaleOrderDetailList()){
							if(saleOrderDetailChange.getStatus()==5){
			         			isCancel = true;
			         		}
							if(saleOrderDetail.getInsuredNo().equals(saleOrderDetailChange.getInsuredNo())){
								isCancel = true;
							}
							
						}
						if(isCancel==false){
							SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6);
			         	}else{
			         	   SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5);
			         	}
						saleOrderDetail.setStatus(5);
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}

					
	
				} else {
					return resultInsure;
					
				}
			} catch (JsonProcessingException e) {
				log.error("调用保险经纪退保接口出错  原因:"+resultInsure.getMessage());
				e.printStackTrace();
				return resultInsure;
			}
		}
		//更新销售单和采购单状态
		List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
		//如果全部子订单为已退保，则销售单和采购单状态为已退保状态，如果子订单有部分已退保，则销售单和采购单状态为部分退保
		int count = 0;
		for(SaleOrderDetail saleOrderDetail:saleOrderDetails){
			if(saleOrderDetail.getStatus() == 3){
				count++;
			}
		}
		if(isRefund==1){
/*			if((count+policyNoList.size()) == saleOrderDetails.size()){*/
			saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 8); //部分退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 8); //退款中
/*			}else{
				
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 9); //部分退保
			}*/
		}else{
			if((count+policyNoList.size()) == saleOrderDetails.size()){
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5); //已退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 5); //已退保
			}else{
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6); //部分退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 6); //部分退保
			}
		}
		try{
			//mss平台退保通知接口
			MssApiInsCallbackVo vo = new MssApiInsCallbackVo();
			vo.setInsOrderId(saleOrderNo);
			vo.setInsNumber(saleOrderExt.getSaleOrderDetailList().size());
			vo.setPrice(saleOrderExt.getTotalPremium().doubleValue());
			if (policyNoArray.length() > 0) {
				policyNoArray.deleteCharAt(policyNoArray.length() - 1);
			}
			vo.setCertNo(policyNoArray.toString());
			mssReserveService.returnInsInform(agent,vo);
		}catch (Exception e){
			log.info("mss平台退保通知接口出错");
			e.printStackTrace();
		}
		log.info("线上产品退保结束==============");
		return resultInsure;
	}

	/**
	 * toOrderCancelVo:转换为OrderCancelVo对象
	 *
	 * @param @param  saleOrderExt
	 * @param @return 设定文件
	 * @return OrderCancelVo DOM对象
	 * @throws @since CodingExample Ver 1.1
	 */
	private OrderCancelVo toOrderCancelVo(SaleOrderExt saleOrderExt, String policyNo) {
		OrderCancelVo orderCancelVo = new OrderCancelVo();
		orderCancelVo.setPolicyNo(policyNo);
		String sourceName = paramService.getValueByKey(INS_SOURCE_NAME);
		orderCancelVo.setSourceName(sourceName);
		String insureKey = paramService.getValueByKey(INSURE_KEY);
		if (StringUtils.isNotBlank(insureKey)) {
			orderCancelVo.setSign(MD5Util.MD5(sourceName + policyNo + insureKey));
		} else {
			log.error("当前保险产品key为空,请在参数管理处配置!");
			throw new GSSException("当前保险产品key为空,请在参数管理处配置!", "1010", "退保失败");
		}
		return orderCancelVo;

	}

	@Override
	public SaleOrderExt querySaleOrder(RequestWithActor<Long> requestWithActor) {
		log.info("查询保单开始==============");
		if (requestWithActor.getAgent() == null) {
			log.error("当前操作用户不能为空");
			throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
		}
		if (requestWithActor.getEntity() == null) {
			log.error("保单号为空");
			throw new GSSException("保单号为空", "1010", "查询保单失败");
		}
		SaleOrderExt saleOrderExt = orderServiceDao.selectByPrimaryKey(requestWithActor.getEntity());
		/**
		 * 关联取出销售单的数据
		 */
		SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(), requestWithActor.getEntity().longValue());
		saleOrderExt.setSaleOrder(saleOrder);
		log.info("查询保单结束==============");
		return saleOrderExt;
	}

	@Override
	public Page<SaleOrderExt> querySaleOrderList(Page<SaleOrderExt> page, RequestWithActor<SaleOrderExtVo> pageRequest) {

		log.info("根据条件查询保单开始==============");
		Agent agent = pageRequest.getAgent();
		//获取当前客商下的子账户
		List<Customer> lowerCustomers = null;
		if (agent == null) {
			log.error("当前操作用户不能为空");
			throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
		}
		//如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
		if(agent.getType() != null && agent.getType() != 0L){ //不是运营平台账号
			
				if(pageRequest.getEntity().isLowerLevel()==true){
					lowerCustomers = customerService.getSubCustomerByCno(agent, agent.getNum());
					pageRequest.getEntity().setLowerCustomers(lowerCustomers);
				}
				pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
				pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
		
		}
		/* 分页列表 */
		try{
			List<SaleOrderExt> tempList = orderServiceDao.queryObjByKey(page, pageRequest.getEntity());
			List<SaleOrderExt> saleOrderExtList = new ArrayList<SaleOrderExt>();
			for(SaleOrderExt order: tempList) {
				BigDecimal prise = new BigDecimal(order.getSaleOrderDetailList().size());
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), order.getSaleOrderNo());
				order.setSaleOrder(saleOrder);
				/*if(prise!=null){
					order.setTotalPremium(prise.multiply(order.getTotalPremium()));
				}*/
				
				order.setLowerCustomers(lowerCustomers);
				saleOrderExtList.add(order);
			}
	
			page.setRecords(saleOrderExtList);
			log.info("根据条件查询保单结束==============");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/**
		 * 根据saleorderno通过API接口去其他子系统去获取数据
		 * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
		 */
	
		return page;
	}
	
	@Override
	public Page<SaleOrderExt> queryReportOrderList(Page<SaleOrderExt> page, RequestWithActor<SaleOrderExtVo> pageRequest) {

		log.info("根据条件查询保单开始==============");
		Agent agent = pageRequest.getAgent();
		//获取当前客商下的子账户
		List<Customer> lowerCustomers = null;
		if (agent == null) {
			log.error("当前操作用户不能为空");
			throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
		}
		//如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
		if(agent.getType() != null && agent.getType() != 0L){ //不是运营平台账号
				if(pageRequest.getEntity().isLowerLevel()==true){
					lowerCustomers = customerService.getSubCustomerByCno(agent, agent.getNum());
					pageRequest.getEntity().setLowerCustomers(lowerCustomers);
				}
				pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
				pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
		
		}
		/* 分页列表 */
		try{
			List<SaleOrderExt> tempList = orderServiceReportDao.queryObjByKey(page, pageRequest.getEntity());
			List<SaleOrderExt> saleOrderExtList = new ArrayList<SaleOrderExt>();
			for(SaleOrderExt order: tempList) {
				BigDecimal prise = new BigDecimal(order.getSaleOrderDetailList().size());
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), order.getSaleOrderNo());
				order.setSaleOrder(saleOrder);
				/*if(prise!=null){
					order.setTotalPremium(prise.multiply(order.getTotalPremium()));
				}*/
				
				order.setLowerCustomers(lowerCustomers);
				saleOrderExtList.add(order);
			}
	
			page.setRecords(saleOrderExtList);
			log.info("根据条件查询保单结束==============");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/**
		 * 根据saleorderno通过API接口去其他子系统去获取数据
		 * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
		 */
	
		return page;
	}
	@Override
	@Transactional
	public int deleteSaleOrder(RequestWithActor<Long> saleOrderNo) throws Exception {

		int flag = 0;
		if (saleOrderNo == null) {
			log.error("SaleOrder删除异常，请选择需要删除的记录");
			throw new GSSException("删除订单模块", "0401", "id传入参数为空");
		}
		try {
			SaleOrderExt se = orderServiceDao.selectByPrimaryKey(saleOrderNo.getEntity().longValue());
			if(se != null) {
				if (se.getValid()) {
					se.setValid(false);
					se.setModifier(saleOrderNo.getAgent().getAccount());
					se.setModifyTime(simple.parse(simple.format(new Date())));
				}
				flag = orderServiceDao.updateByPrimaryKey(se);
				if (flag == 0) {
					throw new GSSException("删除订单模块", "0402", "删除订单失败");
				}
			}else {
				flag = 0;
			}
			
		} catch (Exception e) {
			log.error("删除订单修改valid失败", e);
			throw new GSSException("删除订单模块", "0403", "删除订单失败");
		}

		return flag;
	}

	@Override
	public List<ProfitControl> selectByInsuranceNo(Long insuranceNo) {

		List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(insuranceNo);
		return profitControls;
		
	}

	@Override
	public Page<SaleOrderDetail> selectDetailBySaleOrderNo(Page<SaleOrderDetail> page,
			RequestWithActor<SaleOrderDetailVo> requestWithActor) {

		Long saleOrderNo = requestWithActor.getEntity().getSaleOrderNo();
		List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);
		/**
		 * 根据saleorderno通过API接口去其他子系统去获取数据
		 * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
		 */
		SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(),saleOrderNo);
		for(SaleOrderDetail order: saleOrderDetails) {
			order.setSaleOrder(saleOrder);
		}
		page.setRecords(saleOrderDetails);
		return page;
	}
	/**
	 * 线下个人退款
	 * @param requestWithActor
	 * @return
     */
    public boolean refundForPersonDetail(SaleOrderExt saleOrderExt,SaleOrderDetail saleOrderDetail,Agent agent){
    	saleOrderDetail.setStatus(8);
		saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
		// 创建销售应付记录
					CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
					BigDecimal salePrice = null;
					RequestWithActor<Long> requestWithActorTwo = new RequestWithActor<Long>();
					requestWithActorTwo.setEntity(saleOrderExt.getInsuranceNo());
					// 更新子状态
					SaleOrder saleOrder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 8);//退款zhogn
					Insurance insurance =  insuranceService.getInsurance(requestWithActorTwo);
					salePrice = saleOrderDetail.getPremium();
				/*	List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(saleOrderExt.getInsuranceNo());
					Long customerTypeNo = Long.parseLong((saleOrder.getCustomerTypeNo()+"").substring(0, 3));
					if (!profitControls.isEmpty()) {
						for (ProfitControl profitControl : profitControls) {
							if (customerTypeNo.equals(profitControl.getCustomerTypeNo())) {
								salePrice = profitControl.getSalePrice();
							}
						}
					}
					Agent newAgent = new Agent(agent.getOwner(), saleOrder.getCustomerTypeNo(),agent.getNum());
					 //二级控润
			        InsuranceProfit insuranceProfit = insuranceProfitService.queryInsuranceProfitByNo(newAgent, insurance.getInsuranceNo());
			        BigDecimal a = new BigDecimal(100);
		            if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
		            	  //判断是控点还是固定控  1为控点    2为固定控          
		                if(insuranceProfit.getProfitMode()==1){
		                	 if(insuranceProfit.getProfitCount()!=null){
		                		 //暂时没做点控
		                		 insurance.setBuyPrice((insurance.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(insurance.getBuyPrice()));    
		              	   }else{
		              		   Log.error("保险控点为空");
		              	   }
		              
		               }else if(insuranceProfit.getProfitMode()==2){
		            	   if(insuranceProfit.getProfitMoney()!=null){
		            		   salePrice =  salePrice.add(insuranceProfit.getProfitMoney());    
		            	   }else{
		            		   Log.error("保险控现为空");
		            	   }
		            		   
		               }
		                
		            }*/
					if (salePrice == null) {
						log.info("订单销售价salePrice为空");
						throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
					}
					/*//计算一个人买的份数  总价
				    salePrice = salePrice.multiply(new BigDecimal(saleOrderDetail.getInsuranceNum()));*/
					saleOrderPlanAmountVO.setPlanAmount(salePrice);
					saleOrderPlanAmountVO.setGoodsType(saleOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
					saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
					saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
					saleOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
					saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
					// 采购改签
					PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, saleOrderPlanAmountVO);
					if (planAmountRecord == null) {
						log.info("创建销售应付记录失败");
						throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
					}
					// 创建采购应收记录
					Long buyOrderNo = saleOrderExt.getBuyOrderNo();
					if (buyOrderNo == null) {
						log.error("buyOrderNo为空");
						throw new GSSException("buyOrderNo为空", "1010", "退保失败");
					}
					BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
					if (buyOrder == null) {
						log.error("根据buyOrderNo查询buyOrder为空");
						throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
					}
					CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
					BigDecimal buyPrice = insurance.getBuyPrice();
					if (buyPrice == null) {
						log.info("保险产品采购价buyPrice为空");
						throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
					}
					buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
					buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
					buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
					buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
					buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
					buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
					// 采购改签
					PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(agent, buyOrderPlanAmountVO);
					if (planAmountRecord1 == null) {
						log.info("创建采购应收记录失败");
						throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
					}
					  this.newSaleRefund(agent, saleOrderExt.getSaleOrderNo(),saleOrderDetail.getPremium());
    	return true;
    }
	/**
	 * 线下产品退保
	 * @param requestWithActor
	 * @return
     */
	@Override
	public Boolean cancelSaleOrderOffline(RequestWithActor<OrderCancelVo> requestWithActor,int isRefund){
		log.info("线下产品退保开始==============");
		Agent agent = requestWithActor.getAgent();
		if(StringUtil.isNullOrEmpty(agent)){
			log.error("agent对象为空");
			throw new GSSException("线下产品退保", "1001", "agent对象为空");
		}
		List<String> policyNoList = requestWithActor.getEntity().getPolicyNoList();
		if(StringUtil.isNullOrEmpty(policyNoList)){
			log.error("退保单号集合为空");
			throw new GSSException("线下产品退保", "1002", "退保单号集合为空");
		}
		log.info("退保入参{}",JSONObject.toJSONString(requestWithActor.getEntity().getPolicyNoList()));
		try{
			SaleOrderExt saleOrderExt = null;
			Long saleOrderNo = null;
			for (String policyNo : policyNoList) {
				saleOrderExt = orderServiceDao.selectByPolicyNo(policyNo);
				// 设置子订单状态为3(已退保)
				List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectDetailByPolicyNo(policyNo);
				if (StringUtil.isNullOrEmpty(saleOrderDetails)|| saleOrderDetails.size()!=1) {
					log.error("根据保单号查询子订单错误!");
					throw new GSSException("线下产品退保!", "1003", "根据保单号查询子订单错误");
				}
				SaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);
				if(isRefund==1){
					saleOrderDetail.setStatus(8);
				}else{
					saleOrderDetail.setStatus(3);
				}
				saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
				
				Insurance insurance = saleOrderExt.getInsurance();
				
				Long businessSignNo = IdWorker.getId();
				Long saleChangeNo = maxNoService.generateBizNo("INS_SALE_CHANGE_EXT_NO", 51);

				saleOrderNo = saleOrderExt.getSaleOrderNo();
				if (saleOrderNo == null) {
					log.error("saleOrderNo为空");
					throw new GSSException("saleOrderNo为空", "1010", "退保失败");
				}
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
				if (saleOrder == null) {
					log.error("根据saleOrderNo查询saleOrder为空");
					throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退保失败");
				}
				
				/* 创建采购变更单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("INS_BUY_CHANGE_NO", 53));
				buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
				buyChange.setBusinessSignNo(businessSignNo);
				buyChange.setBsignType(4);
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				buyChange.setGoodsType(3);//商品大类 3 保险
				buyChange.setGoodsSubType(0);//TODO 没有小类为0
				buyChange.setGoodsName("");//TODO
				buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
				if (saleOrderExt.getBuyOrderNo() != null) {
					buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
				} else {
					log.error("采购单编号buyOrderNo为空");
					throw new GSSException("采购单编号buyOrderNo为空", "1010", "退保失败");
				}
				buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if (buyChange == null) {
					log.error("创建采购变更单失败");
					throw new GSSException("创建采购变更单失败", "1010", "退保失败");
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
				saleChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				saleChange.setGoodsType(3);// 商品大类 3 保险
				saleChange.setGoodsSubType(0);//TODO 没有小类为0
				saleChange.setGoodsName(insurance.getName());//TODO
				// 设置交易单号
				Long transationOrderNo = saleOrder.getTransationOrderNo();
				if (transationOrderNo == null) {
					log.error("transationOrderNo为空");
					throw new GSSException("transationOrderNo为空", "1010", "退保失败");
				}
				saleChange.setTransationOrderNo(transationOrderNo);//交易号
				saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
				saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
				if (saleChange == null) {
					log.error("创建销售变更单失败");
					throw new GSSException("创建销售变更单失败", "1010", "退保失败");
				}
				
				// 创建销售变更明细
				SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
				saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("INS_SALE_CHANGE_DETAIL_NO", 52));
				saleChangeDetail.setSaleChangeNo(saleChangeNo);
				saleChangeDetail.setStatus("2"); // 已退保
				saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
				saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleChangeDetail.setOwner(agent.getOwner());
				saleChangeDetail.setCreator(agent.getAccount());
				saleChangeDetail.setCreateTime(new Date());
				int saleChangeDetailNum = saleChangeDetailDao.insertSelective(saleChangeDetail);
				if (saleChangeDetailNum == 0) {
					log.error("创建销售变更明细失败");
					throw new GSSException("创建销售变更明细失败", "1010", "退保失败");
				}
				BigDecimal salePrice = null;
				//拿订单详情的价格进行退还
				salePrice = saleOrderDetail.getPremium();
				if (salePrice == null) {
					log.info("订单销售价salePrice为空");
					throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
				}
				// 创建保险变更扩展单
				SaleChangeExt saleChangeExt = new SaleChangeExt();
				/* 设置销售单拓展编号 */
				saleChangeExt.setSaleChangeNo(saleChangeNo);
				saleChangeExt.setOwner(agent.getOwner());
				saleChangeExt.setCreateTime(new Date());
				saleChangeExt.setChangeType(1);
				saleChangeExt.setCreator(agent.getAccount());
				saleChangeExt.setRightRefund(salePrice);
				saleChangeExt.setSaleOrderNo(saleOrderNo);
				saleChangeExt.setValid(true);
				saleChangeExt.setInsuredNo(saleOrderDetail.getInsuredNo());
				int saleChangeExtNum = saleChangeExtDao.insertSelective(saleChangeExt);
				if (saleChangeExtNum == 0) {
					log.error("创建保险变更扩展单失败");
					throw new GSSException("创建保险变更扩展单失败", "1010", "退保失败");
				}
				Long insuranceNo = insurance.getInsuranceNo();
				if (insuranceNo == null) {
					log.info("保险产品的insuranceNo为空");
					throw new GSSException("保险产品的insuranceNo为空", "1010", "退保失败");
				}
				Long customerTypeNo = saleOrder.getCustomerTypeNo();
				if (customerTypeNo == null) {
					log.info("客户类型customerTypeNo为空!");
					throw new GSSException("客户类型customerTypeNo为空!", "0", "退保失败");
				}
				if(isRefund==1){
					// 创建销售应付记录
					CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
				/*	List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(insuranceNo);
					 Long customerType = Long.parseLong((customerTypeNo+"").substring(0, 3));
					if (!profitControls.isEmpty()) {
						for (ProfitControl profitControl : profitControls) {
							if (customerType.toString().equals(profitControl.getCustomerTypeNo().toString())) {
								salePrice = profitControl.getSalePrice();
							}
						}
					}
					Agent newAgent = new Agent(agent.getOwner(), saleOrder.getCustomerTypeNo(),agent.getNum());
					 //二级控润
			        InsuranceProfit insuranceProfit = insuranceProfitService.queryInsuranceProfitByNo(newAgent, insurance.getInsuranceNo());
			        BigDecimal a = new BigDecimal(100);
		            if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
		            	  //判断是控点还是固定控  1为控点    2为固定控          
		                if(insuranceProfit.getProfitMode()==1){
		                	 if(insuranceProfit.getProfitCount()!=null){
		                		 //暂时没做点控
		                		 insurance.setBuyPrice((insurance.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(insurance.getBuyPrice()));    
		              	   }else{
		              		   Log.error("保险控点为空");
		              	   }
		              
		               }else if(insuranceProfit.getProfitMode()==2){
		            	   if(insuranceProfit.getProfitMoney()!=null){
		            		   salePrice =  salePrice.add(insuranceProfit.getProfitMoney());    
		            	   }else{
		            		   Log.error("保险控现为空");
		            	   }
		            		   
		               }
		            }*/
					if (salePrice == null) {
						log.info("订单销售价salePrice为空");
						throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
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
						throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
					}
					// 创建采购应收记录
					Long buyOrderNo = saleOrderExt.getBuyOrderNo();
					if (buyOrderNo == null) {
						log.error("buyOrderNo为空");
						throw new GSSException("buyOrderNo为空", "1010", "退保失败");
					}
					BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
					if (buyOrder == null) {
						log.error("根据buyOrderNo查询buyOrder为空");
						throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
					}
					CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
					BigDecimal buyPrice = insurance.getBuyPrice();
					if (buyPrice == null) {
						log.info("保险产品采购价buyPrice为空");
						throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
					}
					buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
					buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
					buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
					buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
					buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
					buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
					// 采购改签
					PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
					if (planAmountRecord1 == null) {
						log.info("创建采购应收记录失败");
						throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
					}
			   		saleOrderService.updateStatus(AgentUtil.getAgent(), saleOrderExt.getSaleOrderNo(), 8);//退款中
			/*		boolean isCancel = false;
					 boolean isCancel2 = false;
					for(SaleOrderDetail saleOrderDetailChange:saleOrderExt.getSaleOrderDetailList()){
						if(saleOrderDetailChange.getStatus()==8){
		         			isCancel = true;
		         		}else{
		         			isCancel2 = true;
		         		}
						if(isCancel==true&&isCancel2==true){
							saleOrderService.updateStatus(AgentUtil.getAgent(), saleOrderExt.getSaleOrderNo(), 9);
			         	}
			         	if(isCancel==true&&isCancel2==false){
			         		saleOrderService.updateStatus(AgentUtil.getAgent(), saleOrderExt.getSaleOrderNo(), 8);
			         	}
					}*/
					// 创建销售退款单
					this.saleRefund(agent,saleOrderNo);
				}
				}
			
			//更新销售单和采购单状态
			List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
			//如果全部子订单为已退保，则销售单和采购单状态为已退保状态，如果子订单有部分已退保，则销售单和采购单状态为部分退保
			int count = 0;
			for(SaleOrderDetail saleOrderDetail:saleOrderDetails){
				if(saleOrderDetail.getStatus() == 3){
					count++;
				}
			}
			if(isRefund!=1){
				if((count+policyNoList.size()) == saleOrderDetails.size()){
					saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5); //已退保
					if(isRefund!=1){
						buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 5); //已退保
					}
				}else{
					saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6); //部分退保
					if(isRefund!=1){
					buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 6); //部分退保
					}
				}
			}
			
			
		}catch (Exception e){
			log.error("退保失败:"+e);
			throw new GSSException("线下产品退保", "1004", "退保失败");
		}
		return true;
	}

	@Override
	public void updateStatus4Offline(Agent agent, Long saleOrderNo, Integer orderChildStatus) {

		log.info("更新线下保险产品订单状态开始==============");
		if (agent == null || saleOrderNo == null) {
			log.info("线下保险产品订单参数为空");
			throw new GSSException("线下保险产品订单参数为空", "1010", "更新线下保险产品订单状态失败");
		}

		// 更新子状态
		SaleOrder saleOrder = saleOrderService.updateStatus(agent, saleOrderNo, orderChildStatus);

		//更新被保人状态
		List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);

		for (SaleOrderDetail saleOrderDetail : saleOrderDetails) {
			if (orderChildStatus == 5) {
				saleOrderDetail.setStatus(3);
			} else if (orderChildStatus == 3 || orderChildStatus == 4) {
				saleOrderDetail.setStatus(1);
			} else {
				saleOrderDetail.setStatus(orderChildStatus);
			}
			saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
		}
		if (saleOrder == null) {
			log.info("更新线下保险产品订单状态异常!");
			throw new GSSException("更新线下保险产品订单状态异常!", "1010", "更新线下保险产品订单状态失败");
		}
		if(orderChildStatus==8){
			RequestWithActor<Long> requestWithActor = new RequestWithActor<Long>();
			RequestWithActor<Long> requestWithActorTwo = new RequestWithActor<Long>();
			requestWithActor.setAgent(agent);
			requestWithActor.setEntity(saleOrderNo);
			SaleOrderExt saleOrderExt = querySaleOrder(requestWithActor);
			requestWithActorTwo.setEntity(saleOrderExt.getInsuranceNo());
			
			Insurance insurance =  insuranceService.getInsurance(requestWithActorTwo);
			
			// 创建销售应付记录
			CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
			BigDecimal salePrice = null;
			salePrice = saleOrderExt.getTotalPremium();
			/*List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(saleOrderExt.getInsuranceNo());
			 Long customerTypeNo = Long.parseLong((saleOrder.getCustomerTypeNo()+"").substring(0, 3));
			if (!profitControls.isEmpty()) {
				for (ProfitControl profitControl : profitControls) {
					if (customerTypeNo.equals(profitControl.getCustomerTypeNo())) {
						salePrice = profitControl.getSalePrice();
					}
				}
			}
			Agent newAgent = new Agent(agent.getOwner(), saleOrder.getCustomerTypeNo(),agent.getNum());
			 //二级控润
	        InsuranceProfit insuranceProfit = insuranceProfitService.queryInsuranceProfitByNo(newAgent, insurance.getInsuranceNo());
	        BigDecimal a = new BigDecimal(100);
            if(insuranceProfit.getProfitCount()!=null||insuranceProfit.getProfitMoney()!=null){
            	  //判断是控点还是固定控  1为控点    2为固定控          
                if(insuranceProfit.getProfitMode()==1){
                	 if(insuranceProfit.getProfitCount()!=null){
                		 //暂时没做点控
                		 insurance.setBuyPrice((insurance.getBuyPrice().multiply(insuranceProfit.getProfitCount()).divide(a)).add(insurance.getBuyPrice()));    
              	   }else{
              		   Log.error("保险控点为空");
              	   }
              
               }else if(insuranceProfit.getProfitMode()==2){
            	   if(insuranceProfit.getProfitMoney()!=null){
            		   salePrice =  salePrice.add(insuranceProfit.getProfitMoney());    
            	   }else{
            		   Log.error("保险控现为空");
            	   }
            		   
               }
            }*/
			if (salePrice == null) {
				log.info("订单销售价salePrice为空");
				throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
			}
		/*	//退款的单价乘以总分数
			 int personnum = 0;
		        for(SaleOrderDetail saleOrderDetail:saleOrderExt.getSaleOrderDetailList()){
		        	if(saleOrderDetail.getInsuranceNum()==0){
		        		personnum++;
		        	}else{
		        		personnum +=saleOrderDetail.getInsuranceNum();
		        	}
		        }
		        if(personnum!=0){
		        	salePrice = salePrice.multiply(new BigDecimal(personnum));
		        }*/
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
				throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
			}
			// 创建采购应收记录
			Long buyOrderNo = saleOrderExt.getBuyOrderNo();
			if (buyOrderNo == null) {
				log.error("buyOrderNo为空");
				throw new GSSException("buyOrderNo为空", "1010", "退保失败");
			}
			BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
			if (buyOrder == null) {
				log.error("根据buyOrderNo查询buyOrder为空");
				throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
			}
			CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
			BigDecimal buyPrice = insurance.getBuyPrice();
			if (buyPrice == null) {
				log.info("保险产品采购价buyPrice为空");
				throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
			}
			buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
			buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
			buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
			buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
			buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
			buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
			// 销售废退， 5 销售改签 11 采购，12
			// 采购补单 13 补付 14 采购废退，15
			// 采购改签
			PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
			if (planAmountRecord1 == null) {
				log.info("创建采购应收记录失败");
				throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
			}
			
			  this.saleRefundForUncustomed(agent, saleOrderNo);
		}
       
		log.info("更新线下保险产品订单状态为" + orderChildStatus + "成功!");
		log.info("更新线下保险产品订单状态结束==============");
	}
	@Override
	public void updateStatus4OfflineForPerson(Agent agent, Long saleOrderNo, Integer orderChildStatus,Long insuredNo) {

		log.info("更新线下保险产品订单状态开始==============");
		if (agent == null || saleOrderNo == null) {
			log.info("线下保险产品订单参数为空");
			throw new GSSException("线下保险产品订单参数为空", "1010", "更新线下保险产品订单状态失败");
		}

		// 更新子状态
		SaleOrder saleOrder = saleOrderService.updateStatus(agent, saleOrderNo, orderChildStatus);

		//更新被保人状态
		List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);

		for (SaleOrderDetail saleOrderDetail : saleOrderDetails) {
			if(saleOrderDetail.getInsuranceNum().equals(insuredNo)){
				
			
			if (orderChildStatus == 5) {
				saleOrderDetail.setStatus(3);
			} else if (orderChildStatus == 3 || orderChildStatus == 4) {
				saleOrderDetail.setStatus(1);
			} else {
				saleOrderDetail.setStatus(orderChildStatus);
			}
			saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
		}

		if (saleOrder == null) {
			log.info("更新线下保险产品订单状态异常!");
			throw new GSSException("更新线下保险产品订单状态异常!", "1010", "更新线下保险产品订单状态失败");
		}

		log.info("更新线下保险产品订单状态为" + orderChildStatus + "成功!");
		log.info("更新线下保险产品订单状态结束==============");
		}
	}
	@Override
	public int updateStatusDetail(RequestWithActor<SaleOrderDetail> requestWithActor) {

		log.info("更新线下保险产品保单号开始==============");
		if (requestWithActor == null) {
			log.info("线下保险产品订单参数为空");
			throw new GSSException("线下保险产品订单参数为空", "1010", "更新线下保险产品订单状态失败");
		}
		int price = saleOrderDetailDao.updateByPrimaryKeySelective(requestWithActor.getEntity());

		log.info("更新线下保险产品保单号结束==============");
		return price;
	}

	/**
	 * 取消订单
	 * @param requestWithActor
	 * @return
     */
	@Override
	public boolean cancelSaleOrderExt(RequestWithActor<Long> requestWithActor) {

		log.info("取消订单开始==============");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.info("取消订单参数为空");
			throw new GSSException("取消订单参数为空", "1010", "取消订单失败");
		}
		Agent agent = requestWithActor.getAgent();
		Long saleOrderNo = requestWithActor.getEntity();

		// 更新销售单为取消状态
		SaleOrder saleOrder = saleOrderService.updateStatus(agent, saleOrderNo, 3);

		if (saleOrder == null) {
			log.info("更新销售单状态异常!");
			throw new GSSException("更新销售单状态异常!", "1010", "取消订单失败");
		}

		// 更新采购单为取消状态
		SaleOrderExt saleOrderExt = orderServiceDao.selectByPrimaryKey(saleOrderNo);
		BuyOrder buyOrder = buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 3);

		if (buyOrder == null) {
			log.info("更新采购单状态异常!");
			throw new GSSException("更新采购单状态异常!", "1010", "取消订单失败");
		}


		log.info("取消订单结束==============");
		return true;
	}

	@Override
	public List<SaleOrderDetail> selectDetailListBySaleOrderNo(RequestWithActor<Long> requestWithActor) {
		log.info("根据销售单号查询被保人列表开始==============");
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null) {
			log.info("查询被保人列表参数为空");
			throw new GSSException("查询被保人列表参数为空", "1010", "查询被保人列表失败");
		}
		Long saleOrderNo = requestWithActor.getEntity();
		List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectBySaleOrderNo(saleOrderNo);
		log.info("根据销售单号查询被保人列表结束==============");
		return saleOrderDetails;

	}

	/**
	 * 创建采购付款单
	 * 
	 * payWay 支付方式(代扣，代缴必传)
	 * payType 支付类型
	 * accoutNo 付款账号(代扣，代缴传 付款账号，BSP账期传开账账号)
	 * serviceLine 业务线
	 * channel	渠道
	 * customerTypeNo 供应商类型
	 * incomeExpenseType 收支类型(采购付款为支)
	 * customerNo 供应商编号
	 * subBusinessType	业务小类
	 * thirdBusNo 第三方业务编号（BSP传票号，其它B2B平台传平台订单号）
	 * thirdPayNo 第三方支付流水（BSP不传，其它B2B平台传代扣代缴账号）
	 * reason 补充说明(有就传)
	 * List<BusinessOrderInfo>业务单信息列表
	 * recordNo	业务单号
	 * businessType	业务类型
	 *
	 * @param agent          终端信息
	 * @param buyOrderNo     采购单ID
	 * @param payAmount      支付金额
	 * @param payAccount     支付账号
	 * @param customerNo     采购渠道
	 * @param customerTypeNo 采购渠道类型
	 * @param channel        渠道
	 * @param payType        支付方式
	 * @param payWay         支付类型
	 * @param thirdBusNo     第三方支付流水 多个以","隔开
	 */
	public void createBuyCertificate(Agent agent, long buyOrderNo, double payAmount, long payAccount, long customerNo,
			long customerTypeNo,
			int payType, int payWay, String channel, String thirdBusNo) {

		CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
		certificateCreateVO.setAccoutNo(String.valueOf(payAccount)); //支付账号
		certificateCreateVO.setChannel(channel); //渠道 未知
		certificateCreateVO.setCustomerNo(customerNo); //供应商ID
		certificateCreateVO.setCustomerTypeNo(customerTypeNo); //供应商类型
		certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
		certificateCreateVO.setPayType(payType); //支付类型（1 在线支付 2 帐期或代付 3 线下支付）
		certificateCreateVO.setPayWay(payWay); //支付方式
		certificateCreateVO.setReason("采购单付款信息"); //补充说明
		certificateCreateVO.setServiceLine("1"); //业务线
		certificateCreateVO.setSubBusinessType(1); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
		certificateCreateVO.setThirdBusNo(thirdBusNo); //第三方业务编号 多个以","隔开 (销售不用传)
		List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
		BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
		businessOrderInfo.setActualAmount(new BigDecimal(payAmount));
		businessOrderInfo.setBusinessType(3);
		businessOrderInfo.setRecordNo(buyOrderNo);
		orderInfoList.add(businessOrderInfo);
		certificateCreateVO.setOrderInfoList(orderInfoList);

		try {
			log.info("创建采购付款单的参数certificateCreateVO----------> " + certificateCreateVO.toString());
			this.certificateService.createBuyCertificate(agent, certificateCreateVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 创建销售退款单
	 *
	 * @return
	 */
	public boolean saleRefund(Agent agent, Long saleOrderNo) throws GSSException {
       
		log.info("退保时创建销售退款单开始-------------");

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
					certificateCreateVO.setSubBusinessType(1); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
					List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
					BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
					businessOrderInfo.setActualAmount(saleOrder.getReceived());
					certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
					certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
					businessOrderInfo.setBusinessType(2);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
					businessOrderInfo.setRecordNo(saleOrderNo);
					orderInfoList.add(businessOrderInfo);
					certificateCreateVO.setOrderInfoList(orderInfoList);
					certificateCreateVO.setServiceLine("3");
					log.info("创建销售退款单的参数certificateCreateVO---------->" + certificateCreateVO.toString());
					certificateService.saleRefundCert(agent, certificateCreateVO);
				} catch (Exception e) {
					log.error("创建销售退款单失败," + e);
					throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
				}
			}
		}

		log.info("退保时创建销售退款单结束-------------");
		return true;
	}
	/**未投保退款
	 * 创建销售退款单
	 *
	 * @return
	 */
	public boolean saleRefundForUncustomed(Agent agent, Long saleOrderNo) throws GSSException {
		log.info("退保时创建销售退款单开始-------------");
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
			if (saleOrder.getPayStatus() ==1) {
				try {
					CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
					certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
					/*		certificateCreateVO.setPayType(2); //支付类型（1 在线支付 2 帐期或代付 3 线下支付）*/	
				certificateCreateVO.setReason("销售退款单信息"); //补充说明
					certificateCreateVO.setSubBusinessType(1); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
					List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
					BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
					businessOrderInfo.setActualAmount(saleOrder.getReceived());
					certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
					certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
					businessOrderInfo.setBusinessType(2);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
					businessOrderInfo.setRecordNo(saleOrderNo);
					orderInfoList.add(businessOrderInfo);
					certificateCreateVO.setOrderInfoList(orderInfoList);
					certificateCreateVO.setServiceLine("3");
					log.info("创建销售退款单的参数certificateCreateVO---------->" + certificateCreateVO.toString());
					certificateService.saleRefundCert(agent, certificateCreateVO);
				} catch (Exception e) {
					log.error("创建销售退款单失败," + e);
					throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
				}
			}
		}

		log.info("退保时创建销售退款单结束-------------");
		return true;
	}
	/**未投保退款
	 * 创建销售退款单
	 *
	 * @return
	 */
	public boolean newSaleRefund(Agent agent, Long saleOrderNo,BigDecimal actualAmount) throws GSSException {
		log.info("退保时创建销售退款单开始-------------");
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
			if (saleOrder.getPayStatus() ==1) {
				try {
					CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
					certificateCreateVO.setIncomeExpenseType(2); //收支类型 1 收，2 为支
					/*		certificateCreateVO.setPayType(2); //支付类型（1 在线支付 2 帐期或代付 3 线下支付）*/	
				certificateCreateVO.setReason("销售退款单信息"); //补充说明
					certificateCreateVO.setSubBusinessType(1); //业务小类 1.销采 2.补收退 3.废退 4.变更 5.错误修改
					List<BusinessOrderInfo> orderInfoList = new ArrayList<>(); //支付订单
					BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
					businessOrderInfo.setActualAmount(actualAmount);
					certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
					certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
					businessOrderInfo.setBusinessType(2);//1.交易单，2.销售单，3.采购单，4.销售变更单，5.采购变更单
					businessOrderInfo.setRecordNo(saleOrderNo);
					orderInfoList.add(businessOrderInfo);
					certificateCreateVO.setOrderInfoList(orderInfoList);
					certificateCreateVO.setServiceLine("3");
					log.info("创建销售退款单的参数certificateCreateVO---------->" + certificateCreateVO.toString());
					certificateService.saleRefundCert(agent, certificateCreateVO);
				} catch (Exception e) {
					log.error("创建销售退款单失败," + e);
					throw new GSSException("创建销售退款单失败," + e, "1001", "创建销售退款单失败");
				}
			}
		}

		log.info("退保时创建销售退款单结束-------------");
		return true;
	}

	@Override
	public Boolean cancelSaleOrder(RequestWithActor<OrderCancelVo> requestWithActor) throws Exception {


		log.info("线上产品退保开始==============");

		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null ||
				requestWithActor.getEntity().getPolicyNoList().isEmpty()) {
			log.error("退保单号为空");
			throw new GSSException("退保单号为空", "1010", "退保失败");
		}
		log.info("退保入参{}",JSONObject.toJSONString(requestWithActor.getEntity().getPolicyNoList()));
		Agent agent = requestWithActor.getAgent();
		List<String> policyNoList = requestWithActor.getEntity().getPolicyNoList();

		SaleOrderExt saleOrderExt = null;
		Long saleOrderNo = null;
		StringBuffer policyNoArray = new StringBuffer();
		for (String policyNo : policyNoList) {
			policyNoArray.append(policyNo+",");
			saleOrderExt = orderServiceDao.selectByPolicyNo(policyNo);
			List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectDetailByPolicyNo(policyNo);
			if (StringUtil.isNullOrEmpty(saleOrderDetails)|| saleOrderDetails.size()!=1) {
				log.error("根据保单号查询子订单错误!");
				throw new GSSException("线下产品退保!", "1003", "根据保单号查询子订单错误");
			}
			SaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);
			if (saleOrderExt == null) {
				log.info("根据退保单号查询订单结果为空!");
				throw new GSSException("根据退保单号查询订单结果为空!", "1010", "退保失败");
			}
			if (saleOrderExt.getInsurance() == null) {
				log.info("订单的保险产品insurance为空");
				throw new GSSException("订单的保险产品insurance为空", "1010", "退保失败");
			}
			Insurance insurance = saleOrderExt.getInsurance();

			try {
				Long businessSignNo = IdWorker.getId();
				Long saleChangeNo = maxNoService.generateBizNo("INS_SALE_CHANGE_EXT_NO", 51);

				saleOrderNo = saleOrderExt.getSaleOrderNo();
				if (saleOrderNo == null) {
					log.error("saleOrderNo为空");
					throw new GSSException("saleOrderNo为空", "1010", "退保失败");
				}
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
				if (saleOrder == null) {
					log.error("根据saleOrderNo查询saleOrder为空");
					throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退保失败");
				}

				/* 创建采购变更单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("INS_BUY_CHANGE_NO", 53));
				buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
				buyChange.setBusinessSignNo(businessSignNo);
				buyChange.setBsignType(4);
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				buyChange.setGoodsType(3);//商品大类 3 保险
				buyChange.setGoodsSubType(0);//TODO 没有小类为0
				buyChange.setGoodsName("");//TODO
				buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
				if (saleOrderExt.getBuyOrderNo() != null) {
					buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
				} else {
					log.error("采购单编号buyOrderNo为空");
					throw new GSSException("采购单编号buyOrderNo为空", "1010", "退保失败");
				}
				buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if (buyChange == null) {
					log.error("创建采购变更单失败");
					throw new GSSException("创建采购变更单失败", "1010", "退保失败");
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
				saleChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				saleChange.setGoodsType(3);// 商品大类 3 保险
				saleChange.setGoodsSubType(0);//TODO 没有小类为0
				saleChange.setGoodsName(insurance.getName());//TODO
				// 设置交易单号
				Long transationOrderNo = saleOrder.getTransationOrderNo();
				if (transationOrderNo == null) {
					log.error("transationOrderNo为空");
					throw new GSSException("transationOrderNo为空", "1010", "退保失败");
				}
				saleChange.setTransationOrderNo(transationOrderNo);//交易号
				saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
				saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
				if (saleChange == null) {
					log.error("创建销售变更单失败");
					throw new GSSException("创建销售变更单失败", "1010", "退保失败");
				}
				// 创建销售变更明细
				SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
				saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("INS_SALE_CHANGE_DETAIL_NO", 52));
				saleChangeDetail.setSaleChangeNo(saleChangeNo);
				saleChangeDetail.setStatus("2"); // 已退保
				saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
				saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleChangeDetail.setOwner(agent.getOwner());
				saleChangeDetail.setCreator(agent.getAccount());
				saleChangeDetail.setCreateTime(new Date());
				int saleChangeDetailNum = saleChangeDetailDao.insertSelective(saleChangeDetail);
				if (saleChangeDetailNum == 0) {
					log.error("创建销售变更明细失败");
					throw new GSSException("创建销售变更明细失败", "1010", "退保失败");
				}
				BigDecimal salePrice = saleOrderExt.getTotalPremium();
				if (salePrice == null) {
					log.info("订单销售价salePrice为空");
					throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
				}
				// 创建保险变更扩展单
				SaleChangeExt saleChangeExt = new SaleChangeExt();
				/* 设置销售单拓展编号 */
				saleChangeExt.setSaleChangeNo(saleChangeNo);
				saleChangeExt.setOwner(agent.getOwner());
				saleChangeExt.setCreateTime(new Date());
				saleChangeExt.setChangeType(1);
				saleChangeExt.setCreator(agent.getAccount());
				saleChangeExt.setRightRefund(salePrice);
				saleChangeExt.setSaleOrderNo(saleOrderNo);
				saleChangeExt.setValid(true);
				saleChangeExt.setInsuredNo(saleOrderDetail.getInsuredNo());
				int saleChangeExtNum = saleChangeExtDao.insertSelective(saleChangeExt);
				if (saleChangeExtNum == 0) {
					log.error("创建保险变更扩展单失败");
					throw new GSSException("创建保险变更扩展单失败", "1010", "退保失败");
				}
				Long insuranceNo = insurance.getInsuranceNo();
				if (insuranceNo == null) {
					log.info("保险产品的insuranceNo为空");
					throw new GSSException("保险产品的insuranceNo为空", "1010", "退保失败");
				}
				Long customerTypeNo = saleOrder.getCustomerTypeNo();
				if (customerTypeNo == null) {
					log.info("客户类型customerTypeNo为空!");
					throw new GSSException("客户类型customerTypeNo为空!", "0", "退保失败");
				}
				// 创建销售应付记录
				CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
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
					throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
				}
				// 创建采购应收记录
				Long buyOrderNo = saleOrderExt.getBuyOrderNo();
				if (buyOrderNo == null) {
					log.error("buyOrderNo为空");
					throw new GSSException("buyOrderNo为空", "1010", "退保失败");
				}
				BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
				if (buyOrder == null) {
					log.error("根据buyOrderNo查询buyOrder为空");
					throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
				}
				CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
				BigDecimal buyPrice = insurance.getBuyPrice();
				if (buyPrice == null) {
					log.info("保险产品采购价buyPrice为空");
					throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
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
					throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
				}
				ObjectMapper mapper = new ObjectMapper();
				OrderCancelVo orderCancelVo = toOrderCancelVo(saleOrderExt, policyNo);
				String json = mapper.writeValueAsString(orderCancelVo);

				String insureCancelPath = paramService.getValueByKey(INSURE_CANCEL_PATH);
				if (StringUtils.isBlank(insureCancelPath)) {
					log.error("当前保险产品的退保请求路径为空,请在参数管理处配置!");
					throw new GSSException("当前保险产品的退保请求路径为空,请在参数管理处配置!", "1010", "退保失败");
				}
				@SuppressWarnings("unchecked")
				Response<JSONObject> response = (Response<JSONObject>) HttpClientUtil.doJsonPost(insureCancelPath,
						json,
						Response.class);
				log.info("保险经纪接口响应:"+JSONObject.toJSONString(response));
				if (RESPONSE_SUCCESS.equals(response.getCode())) {
					saleOrderDetail.setStatus(3);
					saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);

					// 创建销售退款单
					this.saleRefund(agent, saleOrderNo);
				} else {
					// 退保失败
					log.error(response.getMsg());
					throw new GSSException(response.getMsg(), "1010", "退保失败");
				}
			} catch (JsonProcessingException e) {
				log.error("调用保险经纪退保接口出错");
				e.printStackTrace();
			}
		}
		//更新销售单和采购单状态
		List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
		//如果全部子订单为已退保，则销售单和采购单状态为已退保状态，如果子订单有部分已退保，则销售单和采购单状态为部分退保
		int count = 0;
		for(SaleOrderDetail saleOrderDetail:saleOrderDetails){
			if(saleOrderDetail.getStatus() == 3){
				count++;
			}
		}
		if((count+policyNoList.size()) == saleOrderDetails.size()){
			saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5); //已退保
			buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 5); //已退保
		}else{
			saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6); //部分退保
			buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 6); //部分退保
		}
		try{
			//mss平台退保通知接口
			MssApiInsCallbackVo vo = new MssApiInsCallbackVo();
			vo.setInsOrderId(saleOrderNo);
			vo.setInsNumber(saleOrderExt.getSaleOrderDetailList().size());
			vo.setPrice(saleOrderExt.getTotalPremium().doubleValue());
			if (policyNoArray.length() > 0) {
				policyNoArray.deleteCharAt(policyNoArray.length() - 1);
			}
			vo.setCertNo(policyNoArray.toString());
			mssReserveService.returnInsInform(agent,vo);
		}catch (Exception e){
			log.info("mss平台退保通知接口出错");
			e.printStackTrace();
		}
		log.info("线上产品退保结束==============");
		return true;
	
	}


	@Override
	public Boolean cancelSaleOrderOffline(RequestWithActor<OrderCancelVo> requestWithActor) throws Exception {

		log.info("线下产品退保开始==============");
		Agent agent = requestWithActor.getAgent();
		if(StringUtil.isNullOrEmpty(agent)){
			log.error("agent对象为空");
			throw new GSSException("线下产品退保", "1001", "agent对象为空");
		}
		List<String> policyNoList = requestWithActor.getEntity().getPolicyNoList();
		if(StringUtil.isNullOrEmpty(policyNoList)){
			log.error("退保单号集合为空");
			throw new GSSException("线下产品退保", "1002", "退保单号集合为空");
		}
		log.info("退保入参{}",JSONObject.toJSONString(requestWithActor.getEntity().getPolicyNoList()));
		try{
			SaleOrderExt saleOrderExt = null;
			Long saleOrderNo = null;
			for (String policyNo : policyNoList) {
				saleOrderExt = orderServiceDao.selectByPolicyNo(policyNo);
				// 设置子订单状态为3(已退保)
				List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectDetailByPolicyNo(policyNo);
				if (StringUtil.isNullOrEmpty(saleOrderDetails)|| saleOrderDetails.size()!=1) {
					log.error("根据保单号查询子订单错误!");
					throw new GSSException("线下产品退保!", "1003", "根据保单号查询子订单错误");
				}
				SaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);

					saleOrderDetail.setStatus(8);
				saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
				
				Insurance insurance = saleOrderExt.getInsurance();
				
				Long businessSignNo = IdWorker.getId();
				Long saleChangeNo = maxNoService.generateBizNo("INS_SALE_CHANGE_EXT_NO", 51);

				saleOrderNo = saleOrderExt.getSaleOrderNo();
				if (saleOrderNo == null) {
					log.error("saleOrderNo为空");
					throw new GSSException("saleOrderNo为空", "1010", "退保失败");
				}
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
				if (saleOrder == null) {
					log.error("根据saleOrderNo查询saleOrder为空");
					throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退保失败");
				}
				
				/* 创建采购变更单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("INS_BUY_CHANGE_NO", 53));
				buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
				buyChange.setBusinessSignNo(businessSignNo);
				buyChange.setBsignType(4);
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				buyChange.setGoodsType(3);//商品大类 3 保险
				buyChange.setGoodsSubType(0);//TODO 没有小类为0
				buyChange.setGoodsName("");//TODO
				buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
				if (saleOrderExt.getBuyOrderNo() != null) {
					buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
				} else {
					log.error("采购单编号buyOrderNo为空");
					throw new GSSException("采购单编号buyOrderNo为空", "1010", "退保失败");
				}
				buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if (buyChange == null) {
					log.error("创建采购变更单失败");
					throw new GSSException("创建采购变更单失败", "1010", "退保失败");
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
				saleChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				saleChange.setGoodsType(3);// 商品大类 3 保险
				saleChange.setGoodsSubType(0);//TODO 没有小类为0
				saleChange.setGoodsName(insurance.getName());//TODO
				// 设置交易单号
				Long transationOrderNo = saleOrder.getTransationOrderNo();
				if (transationOrderNo == null) {
					log.error("transationOrderNo为空");
					throw new GSSException("transationOrderNo为空", "1010", "退保失败");
				}
				saleChange.setTransationOrderNo(transationOrderNo);//交易号
				saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
				saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
				if (saleChange == null) {
					log.error("创建销售变更单失败");
					throw new GSSException("创建销售变更单失败", "1010", "退保失败");
				}
				
				// 创建销售变更明细
				SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
				saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("INS_SALE_CHANGE_DETAIL_NO", 52));
				saleChangeDetail.setSaleChangeNo(saleChangeNo);
				saleChangeDetail.setStatus("2"); // 已退保
				saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
				saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleChangeDetail.setOwner(agent.getOwner());
				saleChangeDetail.setCreator(agent.getAccount());
				saleChangeDetail.setCreateTime(new Date());
				int saleChangeDetailNum = saleChangeDetailDao.insertSelective(saleChangeDetail);
				if (saleChangeDetailNum == 0) {
					log.error("创建销售变更明细失败");
					throw new GSSException("创建销售变更明细失败", "1010", "退保失败");
				}
				BigDecimal salePrice = saleOrderExt.getTotalPremium();
					if (salePrice == null) {
						log.info("订单销售价salePrice为空");
						throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
					}
				// 创建保险变更扩展单
				SaleChangeExt saleChangeExt = new SaleChangeExt();
				/* 设置销售单拓展编号 */
				saleChangeExt.setSaleChangeNo(saleChangeNo);
				saleChangeExt.setOwner(agent.getOwner());
				saleChangeExt.setCreateTime(new Date());
				saleChangeExt.setChangeType(1);
				saleChangeExt.setCreator(agent.getAccount());
				saleChangeExt.setRightRefund(salePrice);
				saleChangeExt.setValid(true);
				saleChangeExt.setSaleOrderNo(saleOrderNo);
				saleChangeExt.setInsuredNo(saleOrderDetail.getInsuredNo());
				int saleChangeExtNum = saleChangeExtDao.insertSelective(saleChangeExt);
				if (saleChangeExtNum == 0) {
					log.error("创建保险变更扩展单失败");
					throw new GSSException("创建保险变更扩展单失败", "1010", "退保失败");
				}
				Long insuranceNo = insurance.getInsuranceNo();
				if (insuranceNo == null) {
					log.info("保险产品的insuranceNo为空");
					throw new GSSException("保险产品的insuranceNo为空", "1010", "退保失败");
				}
				Long customerTypeNo = saleOrder.getCustomerTypeNo();
				if (customerTypeNo == null) {
					log.info("客户类型customerTypeNo为空!");
					throw new GSSException("客户类型customerTypeNo为空!", "0", "退保失败");
				}
					// 创建销售应付记录
					CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
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
						throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
					}
					// 创建采购应收记录
					Long buyOrderNo = saleOrderExt.getBuyOrderNo();
					if (buyOrderNo == null) {
						log.error("buyOrderNo为空");
						throw new GSSException("buyOrderNo为空", "1010", "退保失败");
					}
					BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
					if (buyOrder == null) {
						log.error("根据buyOrderNo查询buyOrder为空");
						throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
					}
					CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
					BigDecimal buyPrice = insurance.getBuyPrice();
					if (buyPrice == null) {
						log.info("保险产品采购价buyPrice为空");
						throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
					}
					buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
					buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
					buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
					buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
					buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
					buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
					// 采购改签
					PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
					if (planAmountRecord1 == null) {
						log.info("创建采购应收记录失败");
						throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
					}
					// 创建销售退款单
					this.saleRefund(agent,saleOrderNo);
				}
			
			//更新销售单和采购单状态
			List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
			//如果全部子订单为已退保，则销售单和采购单状态为已退保状态，如果子订单有部分已退保，则销售单和采购单状态为部分退保
			int count = 0;
			for(SaleOrderDetail saleOrderDetail:saleOrderDetails){
				if(saleOrderDetail.getStatus() == 3){
					count++;
				}
			}
			if((count+policyNoList.size()) == saleOrderDetails.size()){
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5); //已退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 5); //已退保
			}else{
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6); //部分退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 6); //部分退保
			}
		}catch (Exception e){
			log.error("退保失败:"+e);
			throw new GSSException("线下产品退保", "1004", "退保失败");
		}
		return true;
	
	}


	@Override
	public List<SaleOrderExt> querySaleOrderForTranSaction(RequestWithActor<Long> requestWithActor) {
		log.info("查询保单开始==============");
		if (requestWithActor.getAgent() == null) {
			log.error("当前操作用户不能为空");
			throw new GSSException("当前操作用户不能为空", "1010", "当前操作用户不能为空");
		}
		if (requestWithActor.getEntity() == null) {
			log.error("保单号为空");
			throw new GSSException("保单号为空", "1010", "查询保单失败");
		}
		List<SaleOrderExt> saleOrderExts = orderServiceDao.selectByTransaction(requestWithActor.getEntity()+"");
		/**
		 * 关联取出销售单的数据
		 */
		/*SaleOrder saleOrder = saleOrderService.getSOrderByNo(requestWithActor.getAgent(), requestWithActor.getEntity().longValue());*/
		/*saleOrderExt.setSaleOrder(saleOrder);*/
		log.info("查询保单结束==============");
		return saleOrderExts;
	}


	@Override
	public boolean refundForB2BPersonDetail(SaleOrderExt saleOrderExt, SaleOrderDetail saleOrderDetail, Agent agent) {
		//更改状态为退款审核中的状态
		saleOrderDetail.setStatus(12);
		saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
		SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 12);//退款中
    	return true;
	}


	@Override
	public ResultInsure cancelSaleOrderForB2b(RequestWithActor<OrderCancelVo> requestWithActor, int isRefund)
			throws Exception {
		log.info("线上产品退保开始==============");
		ResultInsure resultInsure = new ResultInsure();
		if (requestWithActor.getAgent() == null || requestWithActor.getEntity() == null ||
				requestWithActor.getEntity().getPolicyNoList().isEmpty()) {
			log.error("退保单号为空");
			throw new GSSException("退保单号为空", "1010", "退保失败");
		}
		log.info("退保入参{}",JSONObject.toJSONString(requestWithActor.getEntity().getPolicyNoList()));
		Agent agent = requestWithActor.getAgent();
		List<String> policyNoList = requestWithActor.getEntity().getPolicyNoList();

		SaleOrderExt saleOrderExt = null;
		Long saleOrderNo = null;
		String error = null;
		StringBuffer policyNoArray = new StringBuffer();
		for (String policyNo : policyNoList) {
			policyNoArray.append(policyNo+",");
			List<SaleOrderDetail> saleOrderDetails = saleOrderDetailDao.selectDetailByPolicyNo(policyNo);
			if (saleOrderDetails == null || saleOrderDetails.size() > 1 || saleOrderDetails.size() == 0) {
				log.error("根据保单号查询子订单错误!");
				throw new GSSException("根据保单号查询子订单错误!", "1010", "退保失败");
			}
			SaleOrderDetail saleOrderDetail = saleOrderDetails.get(0);
			saleOrderExt = orderServiceDao.selectByPolicyNo(policyNo);
			if (saleOrderExt == null) {
				log.info("根据退保单号查询订单结果为空!");
				throw new GSSException("根据退保单号查询订单结果为空!", "1010", "退保失败");
			}
			if (saleOrderExt.getInsurance() == null) {
				log.info("订单的保险产品insurance为空");
				throw new GSSException("订单的保险产品insurance为空", "1010", "退保失败");
			}
			Insurance insurance = saleOrderExt.getInsurance();
        
			try {
				Long businessSignNo = IdWorker.getId();
				Long saleChangeNo = maxNoService.generateBizNo("INS_SALE_CHANGE_EXT_NO", 51);

				saleOrderNo = saleOrderExt.getSaleOrderNo();
				if (saleOrderNo == null) {
					log.error("saleOrderNo为空");
					throw new GSSException("saleOrderNo为空", "1010", "退保失败");
				}
				SaleOrder saleOrder = saleOrderService.getSOrderByNo(agent, saleOrderNo);
				if (saleOrder == null) {
					log.error("根据saleOrderNo查询saleOrder为空");
					throw new GSSException("根据saleOrderNo查询saleOrder为空", "1010", "退保失败");
				}
				/* 创建采购变更单 */
				BuyChange buyChange = new BuyChange();
				buyChange.setBuyChangeNo(maxNoService.generateBizNo("INS_BUY_CHANGE_NO", 53));
				buyChange.setOrderChangeType(2); // 变更类型 1废 2退 3改
				buyChange.setBusinessSignNo(businessSignNo);
				buyChange.setBsignType(4);
				buyChange.setOwner(agent.getOwner());
				buyChange.setCreateTime(new Date());
				buyChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				buyChange.setGoodsType(3);//商品大类 3 保险
				buyChange.setGoodsSubType(0);//TODO 没有小类为0
				buyChange.setGoodsName("");//TODO
				buyChange.setIncomeExpenseType(1);//收支类型 1.收 2.支
				if (saleOrderExt.getBuyOrderNo() != null) {
					buyChange.setBuyOrderNo(saleOrderExt.getBuyOrderNo());
				} else {
					log.error("采购单编号buyOrderNo为空");
					throw new GSSException("采购单编号buyOrderNo为空", "1010", "退保失败");
				}
				buyChange = buyChangeService.create(requestWithActor.getAgent(), buyChange);
				if (buyChange == null) {
					log.error("创建采购变更单失败");
					throw new GSSException("创建采购变更单失败", "1010", "退保失败");
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
				saleChange.setChildStatus(2);// 未退保（1），已退保（2），已取消（3）
				saleChange.setGoodsType(3);// 商品大类 3 保险
				saleChange.setGoodsSubType(0);//TODO 没有小类为0
				saleChange.setGoodsName(insurance.getName());//TODO
				// 设置交易单号
				Long transationOrderNo = saleOrder.getTransationOrderNo();
				if (transationOrderNo == null) {
					log.error("transationOrderNo为空");
					throw new GSSException("transationOrderNo为空", "1010", "退保失败");
				}
				saleChange.setTransationOrderNo(transationOrderNo);//交易号
				saleChange.setIncomeExpenseType(2);//收支类型 1.收 2.支
				saleChange = saleChangeService.create(requestWithActor.getAgent(), saleChange);
				if (saleChange == null) {
					log.error("创建销售变更单失败");
					throw new GSSException("创建销售变更单失败", "1010", "退保失败");
				}
				// 创建销售变更明细
				SaleChangeDetail saleChangeDetail = new SaleChangeDetail();
				saleChangeDetail.setSaleChangeDetailNo(maxNoService.generateBizNo("INS_SALE_CHANGE_DETAIL_NO", 52));
				saleChangeDetail.setSaleChangeNo(saleChangeNo);
				saleChangeDetail.setStatus("2"); // 已退保
				saleChangeDetail.setBuyChangeNo(buyChange.getBuyChangeNo());
				saleChangeDetail.setSaleOrderNo(saleOrderExt.getSaleOrderNo());
				saleChangeDetail.setOwner(agent.getOwner());
				saleChangeDetail.setCreator(agent.getAccount());
				saleChangeDetail.setCreateTime(new Date());
				int saleChangeDetailNum = saleChangeDetailDao.insertSelective(saleChangeDetail);
				if (saleChangeDetailNum == 0) {
					log.error("创建销售变更明细失败");
					throw new GSSException("创建销售变更明细失败", "1010", "退保失败");
				}
				BigDecimal salePrice = null;
				salePrice = saleOrderDetail.getPremium();
				if (salePrice == null) {
					log.info("订单销售价salePrice为空");
					throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
				}
				// 创建保险变更扩展单
				SaleChangeExt saleChangeExt = new SaleChangeExt();
				/* 设置销售单拓展编号 */
				saleChangeExt.setSaleChangeNo(saleChangeNo);
				saleChangeExt.setOwner(agent.getOwner());
				saleChangeExt.setCreateTime(new Date());
				saleChangeExt.setChangeType(1);
				saleChangeExt.setCreator(agent.getAccount());
				saleChangeExt.setRightRefund(salePrice);
				saleChangeExt.setValid(true);
				saleChangeExt.setSaleOrderNo(saleOrderNo);
				saleChangeExt.setInsuredNo(saleOrderDetail.getInsuredNo());
				int saleChangeExtNum = saleChangeExtDao.insertSelective(saleChangeExt);
				if (saleChangeExtNum == 0) {
					log.error("创建保险变更扩展单失败"); 
					throw new GSSException("创建保险变更扩展单失败", "1010", "退保失败");
				}
				Long insuranceNo = insurance.getInsuranceNo();
				if (insuranceNo == null) {
					log.info("保险产品的insuranceNo为空");
					throw new GSSException("保险产品的insuranceNo为空", "1010", "退保失败");
				}
				Long customerTypeNo = saleOrder.getCustomerTypeNo();
				if (customerTypeNo == null) {
					log.info("客户类型customerTypeNo为空!");
					throw new GSSException("客户类型customerTypeNo为空!", "0", "退保失败");
				}
		
				// 创建销售应付记录
				CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
		
				saleOrderPlanAmountVO.setPlanAmount(salePrice);
				saleOrderPlanAmountVO.setGoodsType(saleOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
				saleOrderPlanAmountVO.setRecordNo(saleOrder.getSaleOrderNo());//记录编号   自动生成
				saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
				saleOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
				saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
				
				// 创建采购应收记录
				Long buyOrderNo = saleOrderExt.getBuyOrderNo();
				if (buyOrderNo == null) {
					log.error("buyOrderNo为空");
					throw new GSSException("buyOrderNo为空", "1010", "退保失败");
				}
				BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
				if (buyOrder == null) {
					log.error("根据buyOrderNo查询buyOrder为空");
					throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
				}
				CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
				BigDecimal buyPrice = insurance.getBuyPrice();
				if (buyPrice == null) {
					log.info("保险产品采购价buyPrice为空");
					throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
				}
				buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
				buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
				buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
				buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
				buyOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
				buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
				// 销售废退， 5 销售改签 11 采购，12
				// 采购补单 13 补付 14 采购废退，15
				boolean isCancel = false;
				 boolean isCancel2 = false;
/*				if(isRefund==1){
					// 采购改签
					PlanAmountRecord planAmountRecord = planAmountRecordService.create(agent, saleOrderPlanAmountVO);
					// 采购改签
					PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(agent, buyOrderPlanAmountVO);
					// 销售废退， 5 销售改签 11 采购，12
					// 采购补单 13 补付 14 采购废退，15
				
					if (planAmountRecord == null) {
						log.info("创建销售应付记录失败");
						throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
					}
					if (planAmountRecord1 == null) {
						log.info("创建采购应收记录失败");
						throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
					}
				}*/
				
			
				ObjectMapper mapper = new ObjectMapper();
				OrderCancelVo orderCancelVo = toOrderCancelVo(saleOrderExt, policyNo);
				String json = mapper.writeValueAsString(orderCancelVo);

				String insureCancelPath = paramService.getValueByKey(INSURE_CANCEL_PATH);
				if (StringUtils.isBlank(insureCancelPath)) {
					log.error("当前保险产品的退保请求路径为空,请在参数管理处配置!");
					throw new GSSException("当前保险产品的退保请求路径为空,请在参数管理处配置!", "1010", "退保失败");
				}
				@SuppressWarnings("unchecked")
				Response<JSONObject> response = (Response<JSONObject>) HttpClientUtil.doJsonPost(insureCancelPath,
						json,
						Response.class);
				if(response!=null){
					error = response.getMsg();
					resultInsure.setCode(response.getCode());
					resultInsure.setMessage(response.getMsg());
				}
				log.info("保险经纪接口响应:"+JSONObject.toJSONString(response));

				if (RESPONSE_SUCCESS.equals(response.getCode())||"该保单已退保，请不要重复操作".equals(response.getMsg())) {
					// 设置子订单状态为3(已退保)
				
					if(isRefund==1){
						
						saleOrderDetail.setStatus(12);//退款中
						int s = saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
			         	   SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 12);//退款中
/*			         	}*/
						// 创建销售退款单
				/*		this.saleRefund(agent, saleOrderNo);*/
					}else{
						isCancel = false;
						for(SaleOrderDetail saleOrderDetailChange:saleOrderExt.getSaleOrderDetailList()){
							if(saleOrderDetailChange.getStatus()==5){
			         			isCancel = true;
			         		}
							if(saleOrderDetail.getInsuredNo().equals(saleOrderDetailChange.getInsuredNo())){
								isCancel = true;
							}
							
						}
						if(isCancel==false){
							SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6);
			         	}else{
			         	   SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5);
			         	}
						saleOrderDetail.setStatus(5);
						saleOrderDetail.setModifyTime(new Date());
						saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
					}

					
	
				} else {
					return resultInsure;
					
				}
			} catch (JsonProcessingException e) {
				log.error("调用保险经纪退保接口出错  原因:"+resultInsure.getMessage());
				e.printStackTrace();
				return resultInsure;
			}
		}
		//更新销售单和采购单状态
		List<SaleOrderDetail> saleOrderDetails = saleOrderExt.getSaleOrderDetailList();
		//如果全部子订单为已退保，则销售单和采购单状态为已退保状态，如果子订单有部分已退保，则销售单和采购单状态为部分退保
		int count = 0;
		for(SaleOrderDetail saleOrderDetail:saleOrderDetails){
			if(saleOrderDetail.getStatus() == 3){
				count++;
			}
		}
		if(isRefund==1){
/*			if((count+policyNoList.size()) == saleOrderDetails.size()){*/
			saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 12); //部分退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 12); //退款中
/*			}else{
				
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 9); //部分退保
			}*/
		}else{
			if((count+policyNoList.size()) == saleOrderDetails.size()){
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5); //已退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 5); //已退保
			}else{
				saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6); //部分退保
				buyOrderService.updateStatus(agent, saleOrderExt.getBuyOrderNo(), 6); //部分退保
			}
		}
		try{
			//mss平台退保通知接口
			MssApiInsCallbackVo vo = new MssApiInsCallbackVo();
			vo.setInsOrderId(saleOrderNo);
			vo.setInsNumber(saleOrderExt.getSaleOrderDetailList().size());
			vo.setPrice(saleOrderExt.getTotalPremium().doubleValue());
			if (policyNoArray.length() > 0) {
				policyNoArray.deleteCharAt(policyNoArray.length() - 1);
			}
			vo.setCertNo(policyNoArray.toString());
			mssReserveService.returnInsInform(agent,vo);
		}catch (Exception e){
			log.info("mss平台退保通知接口出错");
			e.printStackTrace();
		}
		log.info("线上产品退保结束==============");
		return resultInsure;
	}


	@Override
	public boolean cacelForB2BPersonDetail(SaleOrderExt saleOrderExt, SaleOrderDetail saleOrderDetail, Agent agent) {
		//更改状态为退款审核中的状态
				saleOrderDetail.setStatus(5);
				saleOrderDetailDao.updateByPrimaryKeySelective(saleOrderDetail);
				boolean isTrue = true;
				for(SaleOrderDetail SaleOrderDetailChange:saleOrderExt.getSaleOrderDetailList()){
					if(SaleOrderDetailChange.getStatus()!=5){
						isTrue = false;
						break;
					}
				}
				if(isTrue){
					SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 5);//改为已退保
				}else{
					SaleOrder saleorder = saleOrderService.updateStatus(agent, saleOrderExt.getSaleOrderNo(), 6);//改为已退保
				}
				
		    	return true;
	}
}
/*//创建销售应付记录
CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
BigDecimal salePrice = null;
List<ProfitControl> profitControls = profitControlDao.selectByInsuranceNo(insuranceNo);
if (!profitControls.isEmpty()) {
	for (ProfitControl profitControl : profitControls) {
		if (customerTypeNo.toString().equals(profitControl.getCustomerTypeNo().toString())) {
			salePrice = profitControl.getSalePrice();
		}
	}
}
if (salePrice == null) {
	log.info("订单销售价salePrice为空");
	throw new GSSException("订单销售价salePrice为空", "1010", "退保失败");
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
	throw new GSSException("创建销售应付记录失败", "1010", "退保失败");
}
// 创建采购应收记录
Long buyOrderNo = saleOrderExt.getBuyOrderNo();
if (buyOrderNo == null) {
	log.error("buyOrderNo为空");
	throw new GSSException("buyOrderNo为空", "1010", "退保失败");
}
BuyOrder buyOrder = buyOrderService.getBOrderByBONo(agent, buyOrderNo);
if (buyOrder == null) {
	log.error("根据buyOrderNo查询buyOrder为空");
	throw new GSSException("根据buyOrderNo查询buyOrder为空", "1010", "退保失败");
}
CreatePlanAmountVO buyOrderPlanAmountVO = new CreatePlanAmountVO();
BigDecimal buyPrice = insurance.getBuyPrice();
if (buyPrice == null) {
	log.info("保险产品采购价buyPrice为空");
	throw new GSSException("保险产品采购价buyPrice为空", "1010", "退保失败");
}
buyOrderPlanAmountVO.setPlanAmount(buyPrice);// 采购应收金额
buyOrderPlanAmountVO.setGoodsType(buyOrder.getGoodsType());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
buyOrderPlanAmountVO.setRecordNo(buyOrder.getBuyOrderNo());//记录编号   自动生成
buyOrderPlanAmountVO.setBusinessType(3);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
buyOrderPlanAmountVO.setIncomeExpenseType(2);// 收支类型 1 收，2 为支
buyOrderPlanAmountVO.setRecordMovingType(11);// 记录变动类型 如 1 销售，2销售补单 3 补收，4
// 销售废退， 5 销售改签 11 采购，12
// 采购补单 13 补付 14 采购废退，15
// 采购改签
PlanAmountRecord planAmountRecord1 = planAmountRecordService.create(requestWithActor.getAgent(), buyOrderPlanAmountVO);
if (planAmountRecord1 == null) {
	log.info("创建采购应收记录失败");
	throw new GSSException("创建采购应收记录失败", "1010", "退保失败");
}*/
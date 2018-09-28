package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.controller.Result;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.dps.product.dict.PolicyStatus;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.file.service.FastDFSClientService;
import com.tempus.gss.file.service.FilePath;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.exception.ProductException;
import com.tempus.gss.product.ift.api.entity.helper.ExcelHelper;
import com.tempus.gss.product.ift.api.entity.vo.PolicyExcelBean;
import com.tempus.gss.product.ift.api.entity.vo.PolicyVo;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.product.ift.api.service.IPolicyIndexService;
import com.tempus.gss.product.ift.api.service.IPolicyRService;
import com.tempus.gss.product.ift.api.service.IPolicyService;
import com.tempus.gss.product.ift.dao.CabinDao;
import com.tempus.gss.product.ift.dao.PolicyDao;
import com.tempus.gss.product.ift.dao.ProfitControlDao;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.system.service.IParamService;
import com.tempus.gss.util.EntityUtil;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbe.entity.*;
import com.tempus.tbe.service.ICreatePnrService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 国际机票产品服务.
 */
@Service
@EnableAutoConfiguration
public class PolicyServiceImpl implements IPolicyService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	PolicyDao policyDao;

	@Autowired
	CabinDao cabinDao;

	@Reference
	private IMaxNoService maxNoService;

	@Reference
	private IParamService iParamService;

	@Reference
	private ICreatePnrService createPnrService;

	@Reference
	private IOrderService orderService;
	
	@Autowired
	private FastDFSClientService fastDFSClientService;
	
	@Autowired
	protected PolicyIftHelper policyHelper;
	
	@Reference
	protected ISupplierService supplierService;

	@Autowired
	private ProfitControlDao profitControlDao;

	@Value("${ift.office}")
	private String office;
	@Autowired
	private IPolicyIndexService policyIndexService ;

	@Autowired
	IPolicyRService policyRService;

	protected static String SUCCESS_CODE = "1";
	protected static String FAILED_CODE = "-1";

	protected static String SAVE_SUCCESS = "保存成功！";
	protected static String SAVE_FAILED = "保存失败！";

	protected static String UPDATE_SUCCESS = "修改成功！";
	protected static String UPDATE_FAILED = "修改失败！";

	protected static String DEL_SUCCESS = "删除成功！";
	protected static String DEL_FAILED = "删除失败！";

	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");

	/**新增政策信息*/
	@Override
	@Transactional
	public int createPolicy(RequestWithActor<Policy> policyCreateRequest) throws Exception{
		log.info("创建政策开始==========");
		int flag = 1;
		if(policyCreateRequest.getEntity()!=null){
			policyCreateRequest.getEntity().setPolicyNo(null);
			policyCreateRequest.getEntity().setId(null);
			/**新增政策信息*/
			Long policyNo = maxNoService.generateBizNo("IFT_POLICY_NO", 20);
			policyCreateRequest.getEntity().setPolicyNo(policyNo);
			policyCreateRequest.getEntity().setValid(true);
			policyDao.insert(policyCreateRequest.getEntity());
			policyIndexService.policyIndexsProduction(policyCreateRequest.getEntity());
			policyRService.policyProduction(policyCreateRequest.getEntity());
			/**新增舱位信息和新增舱位渠道控润信息*/
			if(policyCreateRequest.getEntity().getCabinList()!=null&&policyCreateRequest.getEntity().getCabinList().size()>0){
				for (Cabin cabin : policyCreateRequest.getEntity().getCabinList()) {
					cabin.setPolicyNo(policyCreateRequest.getEntity().getPolicyNo());
					cabin = insertPolicyCabin(cabin);
					if(cabin.getProfitControlList()!=null&&cabin.getProfitControlList().size()>0){
						for(int i = 0; i < cabin.getProfitControlList().size(); i++){
							ProfitControl profitControl = cabin.getProfitControlList().get(i);
							profitControl.setPolicyCabinNo(cabin.getPolicyCabinNo());
							if (i == 0) {
								profitControl.setCustomerTypeNo(301L);// 301（分销商）
							} else if (i == 1) {
								profitControl.setCustomerTypeNo(302L);// 302（集团客户）
							} else if (i == 2) {
								profitControl.setCustomerTypeNo(303L);// 303（散客会员）
							} else {
								profitControl.setCustomerTypeNo(306L);// 306（体内销售）
							}
							insertProfitControl(profitControl);
						}
					}
				}
			}
		}
		return flag;
	}

	/**修改政策信息*/
	@Override
	@Transactional
	public int updatePolicy(RequestWithActor<Policy> policyCreateRequest) throws Exception{
		log.info("修改政策开始==========");
		int flag = 1;
		if(policyCreateRequest.getEntity()!=null){
			/**根据政策编号删除舱位和舱位渠道控润信息**/
			if(policyCreateRequest.getEntity().getPolicyNo()!=null){
				cabinDao.deleteByPolicyNo(policyCreateRequest.getEntity().getPolicyNo());
				profitControlDao.deleteByPolicyNo(policyCreateRequest.getEntity().getPolicyNo());
			}else{
				log.error("政策编号为空,删除舱位信息失败!");
				throw new GSSException("更新政策模块", "0103", "删除舱位和舱位渠道控润信息失败");
			}
			/**修改政策信息*/
			policyCreateRequest.getEntity().setValid(true);
			policyDao.updateByPrimaryKeySelective(policyCreateRequest.getEntity());
			/**新增舱位信息和新增舱位渠道控润信息*/
			if(policyCreateRequest.getEntity()!=null&&policyCreateRequest.getEntity().getCabinList()!=null&&policyCreateRequest.getEntity().getCabinList().size()>0){
				for (Cabin cabin : policyCreateRequest.getEntity().getCabinList()) {
					cabin.setPolicyNo(policyCreateRequest.getEntity().getPolicyNo());
					cabin = insertPolicyCabin(cabin);
					if(cabin.getProfitControlList()!=null&&cabin.getProfitControlList().size()>0){
						for(int i = 0; i < cabin.getProfitControlList().size(); i++){
							ProfitControl profitControl = cabin.getProfitControlList().get(i);
							profitControl.setPolicyCabinNo(cabin.getPolicyCabinNo());
							if (i == 0) {
								profitControl.setCustomerTypeNo(301L);// 301（分销商）
							} else if (i == 1) {
								profitControl.setCustomerTypeNo(302L);// 302（集团客户）
							} else if (i == 2) {
								profitControl.setCustomerTypeNo(303L);// 303（散客会员）
							} else {
								profitControl.setCustomerTypeNo(306L);// 306（体内销售）
							}
							insertProfitControl(profitControl);
						}
					}
				}
			}
			policyIndexService.policyIndexsProduction(policyCreateRequest.getEntity());
			policyRService.policyProduction(policyCreateRequest.getEntity());
		}
		return flag;
	}

	/**新增舱位渠道控润信息*/
	private ProfitControl insertProfitControl(ProfitControl profitControl){
		Long profitControlNo = maxNoService.generateBizNo("IFT_PROFIT_CONTROL_NO", 22);
		profitControl.setId(null);
		profitControl.setProfitControlNo(profitControlNo);
		profitControlDao.insert(profitControl);
		return profitControl;
	}

	/**新增舱位信息**/
	private Cabin insertPolicyCabin(Cabin cabin){
		Long policyCabinNo = maxNoService.generateBizNo("IFT_CABIN_NO", 21);
		cabin.setId(null);
		cabin.setPolicyCabinNo(policyCabinNo);
		cabinDao.insert(cabin);
		return cabin;
	}

	/**
	 * 政策查询
	 * @param query
	 * @return PageInfo<Policy> 分页的政策结果
	 * @throws Exception
	 */
	@Override
	public Page<Policy> selectPolicy(Page<Policy> page, RequestWithActor<PolicyVo> query) throws Exception {

		log.info("查询政策开始");
		try {
			if (query == null ) {
				throw new GSSException("查询政策模块", "0301", "传入参数为空");
			}
			if ( query.getAgent().getOwner() == 0) {
				throw new GSSException("查询政策模块", "0301", "传入参数为空");
			}
			query.getEntity().setValid(true);
			List<Policy> policyList = policyDao.queryObjByKey(page, query.getEntity());
			page.setRecords(policyList);
		} catch (Exception e) {
			log.error("政策表中未查询出相应的结果集", e);
			throw new GSSException("查询政策模块", "0302", "查询政策失败");

		}
		log.info("查询政策结束");
		return page;
	}

	/**
	 * 删除政策
	 * @param policyId
	 * @return 1 删除成功
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int deletePolicy(RequestWithActor<Long> policyId) throws Exception {

		int flag = 0;
		if (policyId == null) {
			log.error("Policy删除异常，请选择需要删除的记录");
			throw new GSSException("删除政策模块", "0401", "policyId传入参数为空");
		}
		try {
			Policy policy = policyDao.selectByPrimaryKey(policyId.getEntity().longValue());
			if (policy.getValid()) {
				policy.setValid(false);
				policy.setModifier(policyId.getAgent().getAccount());
				policy.setModifyTime(simple.parse(simple.format(new Date())));
			}
			flag = policyDao.updateValid(policy);
			policyIndexService.policyIndexsProduction(policy);
			policyRService.policyProduction(policy);
			if (flag == 0) {
				throw new GSSException("删除政策模块", "0402", "删除政策失败");
			}
		} catch (Exception e) {
			log.error("删除政策修改valid失败", e);
			throw new GSSException("删除政策模块", "0403", "删除政策失败");
		}
		return flag;
	}

	/**
	 * 挂起、解挂政策.
	 * @param policyId
	 * @param hangUp
	 * true（挂起），false（解挂）.
	 * @return 1 挂起、解挂成功
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int hangUpPolicy(RequestWithActor<Long> policyId, boolean hangUp) throws Exception {

		log.info("挂起、解挂政策开始");
		int flag = 0;
		try {
			if (policyId != null) {
				Policy policy = policyDao.selectByPrimaryKey(policyId.getEntity().longValue());
				int status;
				if (hangUp) {// 挂起
					status = 2;
					policy.setModifier(policyId.getAgent().getAccount());
					policy.setModifyTime(simple.parse(simple.format(new Date())));
				} else {// 解挂
					status = 1;
					policy.setModifier(policyId.getAgent().getAccount());
					policy.setModifyTime(simple.parse(simple.format(new Date())));
				}
				policy.setStatus(status);
				flag = policyDao.updateStatus(policy);
				policyIndexService.policyIndexsProduction(policy);
				policyRService.policyProduction(policy);
			} else {
				log.error("传入参数policyId为空");
				throw new GSSException("挂起、解挂政策", "0501", "传入参数policyId为空");
			}
		} catch (Exception e) {
			log.error("挂起、解挂政策失败", e);
			throw new GSSException("挂起、解挂政策", "0502", "挂起、解挂政策失败");
		}
		return flag;
	}

	/**
	 * 根据出发地点和到达地点，航司查询政策
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Policy> queryObjByOD(Policy record) throws Exception {

		return policyDao.queryObjByOD(record);
	}

	/**
	 * 根据id查找记录
	 *
	 * @param id
	 * @return Policy
	 */
	public Policy getPolicyById(Long id) {

		return policyDao.selectByPrimaryKey(id);
	}

	/**
	 * 根据policyId查找记录
	 * @param policyId
	 * @return Policy
	 */
	public Policy getPolicyByPolicyId(Long policyId) {
		return policyDao.selectByPlolicyId(policyId);
	}

	public int updatePolicyById(RequestWithActor<Policy> requestWithActor) throws Exception {
		log.info("更新政策开始");
		int flag = 0;
		try {
			if (requestWithActor.getAgent() == null && requestWithActor.getEntity() == null) {
				throw new GSSException("更新政策模块", "0201", "传入参数为空");
			}
			// 查询出该policy的记录
			Policy policySelect = null;
			if (requestWithActor.getEntity().getPolicyNo() != null) {
				policySelect = policyDao.selectByPlolicyId(requestWithActor.getEntity().getPolicyNo());
			}
			int version = policySelect.getVersion() + 1;
			Policy policy = requestWithActor.getEntity();
			policy.setVersion(version);
			requestWithActor.setEntity(policy);
			flag = this.createPolicy(requestWithActor);
			policyIndexService.policyIndexsProduction(policy);
			policyRService.policyProduction(policy);
		} catch (Exception e) {
			log.error("更新失败", e);
			throw new GSSException("更新政策模块", "0207", "更新政策、舱位、控润异常");
		}
		log.info("更新政策结束");
		return flag;
	}

	@Override
	@Transactional
	public List<String> importFile(Agent agent, Integer type, String filePath, String name, String num, Integer status, Integer fileType) throws ProductException {
		List<String> reStrings = new ArrayList<>();
		try {
			if (null == type || null == filePath) {
				reStrings.add("false");
				return reStrings;
			}
			byte[] bytes = fastDFSClientService.downloadFile(filePath); // 下载数据
			XSSFWorkbook xssFWorkbook = ExcelHelper.parseXSSFWorkbook(bytes);
			XSSFSheet sheet = xssFWorkbook.getSheetAt(0);
			List<Policy> poliyAddList = new ArrayList<Policy>();
			Map<String, Object> map = new HashMap<String, Object>();
			int rowSize = sheet.getPhysicalNumberOfRows(); // 行数
			int realValueRows = 0;
			boolean isRightFlag = true; // 当前行 是否通过校验标志

			/**解析数据，并且将数据封装到对象中**/
			switch (type) {
				case 1: // 普通
					for (int i = 1; i < rowSize; i++) {
						Row row = sheet.getRow(i);
						if (null != row.getCell(0) && !"".equals(row.getCell(0).toString())) {
							Map<Integer, String> paramMap = policyHelper.setSingleCommonMap();
							isRightFlag = policyHelper.getValuesForSingCommon(map, row, paramMap, isRightFlag,xssFWorkbook);
							map.put("policyType", type);
							map.put("status", status);
							if (isRightFlag) {
								Policy policy = new Policy();
								BeanUtils.populate(policy,map);
								if (null != policy) {
									poliyAddList.add(policy);
								}
							}
							map.clear();
							realValueRows++;
						}
					}
					break;
			  	 default:
					break;
			}

			if (poliyAddList.size() == realValueRows) {
				// 批量插入产品
				addByList(agent, poliyAddList, num);
				sheet.getWorkbook().close();
				reStrings.add("true");
				return reStrings;
			} else {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				try {
					sheet.getWorkbook().write(byteArrayOutputStream);
					byte[] datas = byteArrayOutputStream.toByteArray();
					FilePath filePathError = fastDFSClientService.uploadFile(new ByteArrayInputStream(datas), "xlsx");
					String errorFilePath = filePathError.getUrl();
					reStrings.add("false");
					reStrings.add(errorFilePath);
					sheet.getWorkbook().close();
				} catch (IOException e) {
					log.error("写入错误数据异常", e);
					String Stringpath = writeErrorToFile("写入错误数据异常，请联系技术人员"+e);
					reStrings.add("false");
					reStrings.add(Stringpath);
					return reStrings;
				}
				return reStrings;
			}

		} catch (Exception e) {
			log.error("政策解析错误，请确认模板是否和选择的导入类型匹配:" + e.getMessage(), e);
			String Stringpath = writeErrorToFile("政策解析错误，请确认模板是否和选择的导入类型匹配:" + e.getMessage());
			reStrings.add("false");
			reStrings.add(Stringpath);
			return reStrings;
		}
	}

	@Override
	public List<Policy> importFile(String filePath){
		List<Policy> policyList = new ArrayList<>();
		if(StringUtils.isNotBlank(filePath)){
			try{
				byte[] bytes = fastDFSClientService.downloadFile(filePath); // 下载数据
				XSSFWorkbook xssFWorkbook = ExcelHelper.parseXSSFWorkbook(bytes);
				XSSFSheet sheet = xssFWorkbook.getSheetAt(0);
				Map<String, Object> map = new HashMap<>();
				int rowSize = sheet.getPhysicalNumberOfRows(); // 行数
				boolean isRightFlag = true; // 当前行 是否通过校验标志

				/**将数据解析成PolicyExcelBean对象的集合*/
				List<PolicyExcelBean> policyExcelBeanList = new ArrayList<>();
				for (int i = 1; i < rowSize; i++) {
					Row row = sheet.getRow(i);
					if (null != row.getCell(0) && !"".equals(row.getCell(0).toString())) {
						Map<Integer, String> paramMap = policyHelper.setSingleCommonMap();
						isRightFlag = policyHelper.getValuesForSingCommon(map, row, paramMap, isRightFlag,xssFWorkbook);
						map.put("policyType", 5);
						map.put("status", 1);
						if (isRightFlag) {
							PolicyExcelBean policyExcelBean = new PolicyExcelBean();
							BeanUtils.populate(policyExcelBean,map);
							if (null != policyExcelBean) {
								policyExcelBeanList.add(policyExcelBean);
							}
						}
						map.clear();
					}
				}
				policyList = getByPolicyExcelBeans(policyExcelBeanList);
			}catch (Exception e){
				log.error("政策解析错误，请确认模板是否和选择的导入类型匹配:" + e.getMessage(), e);
				throw new GSSException("政策管理模块","1010","将文本数据解析成对象失败");
			}
		}
		return policyList;
	}

	/**
	 * 将excel政策集合转换成Policy集合
	 * @param policyExcelBeanList
	 * @return
	 */
	private List<Policy> getByPolicyExcelBeans(List<PolicyExcelBean> policyExcelBeanList) throws Exception {
		List<Policy> policyList = new ArrayList<>();
		if(policyExcelBeanList!=null&&policyExcelBeanList.size()>0){
			for(PolicyExcelBean excelBean:policyExcelBeanList){
				if(policyList.size()>0){
					for(Policy policy:policyList){
						/**
						 * 1、id相同
						 * 2、政策编号相同
						 * 3、出票航司、产品商、旅客类型、去程起点、去程起点除外、去程终点/折返点、去程终点/折返点除外、回程终点、回程终点除外、跨航线组合匹配、去程旅行日期这些字段同时相等
						 * 只要满足其中一个条件就认为该政策相同，只需合并舱位信息，否则就认为是另外一条政策
						 */
						if((excelBean.getId()!=null&&policy.getId()!=null&&excelBean.getId()==policy.getId())||(excelBean.getPolicyNo()!=null&&policy.getPolicyNo()!=null&&excelBean.getPolicyNo()==policy.getPolicyNo())) {
							mergeCabin(policy,excelBean);
						}else if(StringUtils.equals(excelBean.getAirline(),policy.getAirline())&&
								StringUtils.equals(excelBean.getProductor(),policy.getProductor())&&
								StringUtils.equals(excelBean.getTravellerType(),policy.getTravellerType())&&
								StringUtils.equals(excelBean.getGoStart(),policy.getGoStart())&&
								StringUtils.equals(excelBean.getExceptGoStart(),policy.getExceptGoStart())&&
								StringUtils.equals(excelBean.getGoEnd(),policy.getGoEnd())&&
								StringUtils.equals(excelBean.getExceptGoEnd(),policy.getExceptGoEnd())&&
								StringUtils.equals(excelBean.getBackEnd(),policy.getBackEnd())&&
								StringUtils.equals(excelBean.getExceptBackEnd(),policy.getExceptBackEnd())&&
								excelBean.getAllowCrossLineMatch()==policy.getAllowCrossLineMatch()&&
								StringUtils.equals(excelBean.getTravelDate(),policy.getTravelDate())){
							mergeCabin(policy,excelBean);
						}else{
							policyList.add(getByPolicyExcelBean(excelBean));
						}
					}
				}else if(StringUtils.isBlank(excelBean.getAirline())&& StringUtils.isBlank(excelBean.getProductor())&&
						StringUtils.isBlank(excelBean.getTravellerType())&& StringUtils.isBlank(excelBean.getGoStart())&&
						StringUtils.isBlank(excelBean.getExceptGoStart())&& StringUtils.isBlank(excelBean.getGoEnd())&&
						StringUtils.isBlank(excelBean.getExceptGoEnd())&& StringUtils.isBlank(excelBean.getBackEnd())&&
						StringUtils.isBlank(excelBean.getExceptBackEnd())&&excelBean.getAllowCrossLineMatch()==null&&
						StringUtils.isBlank(excelBean.getTravelDate())){
					continue;
				}else{
					policyList.add(getByPolicyExcelBean(excelBean));
				}
			}
		}
		return policyList;
	}

	/**
	 * 将excelBean转换成政策信息
	 * @param excelBean
	 * @return
	 * @throws Exception
	 */
	private Policy getByPolicyExcelBean(PolicyExcelBean excelBean) throws Exception {
		Policy policy = new Policy();
		Cabin cabin = new Cabin();
		PropertyUtils.copyProperties(cabin,excelBean);
		PropertyUtils.copyProperties(policy,excelBean);
		List<Cabin> cabinList = new ArrayList<>();
		cabinList.add(cabin);
		policy.setCabinList(cabinList);
		return policy;
	}

	/**
	 * 合并舱位信息
	 * @param policy
	 * @param excelBean
	 * @throws Exception
	 */
	private void mergeCabin(Policy policy,PolicyExcelBean excelBean)throws Exception{
		List<Cabin> cabinList = policy.getCabinList();
		Cabin cabin = new Cabin();
		PropertyUtils.copyProperties(cabin,excelBean);
		if(cabinList==null){
			cabinList = new ArrayList<>();
			cabinList.add(cabin);
			policy.setCabinList(cabinList);
		}else{
			cabinList.add(cabin);
		}
	}


	@Override
	public List<Policy> getPolicyByIds(String policyIds) {
		Policy policy = new Policy();
		policy.setIds(policyIds);
		return policyDao.getByPolicyIds(policy);
	}


	// 产品批量导入相关服务 start
	public void addByList(Agent agent, List<Policy> policyList, String batchNo) throws ProductException {
		if (policyList.size() > 0) {
			for (Policy policy : policyList) {
				policy.setOwner(agent.getOwner());
				policy.setValid(true);
				policy.setBatchNum(batchNo);
				if (null == policy.getStatus()) {
					policy.setStatus(PolicyStatus.WAIT_AUDIT.getKey());
				}
				EntityUtil.setAddInfo(policy, agent); // 添加创建者信息
				Long policyNo = maxNoService.generateBizNo("IFT_POLICY_NO", 20);
				policy.setPolicyNo(policyNo);
			    //插入舱位？？
			}
			policyDao.insertByList(policyList);
			if (log.isInfoEnabled()) {
				log.info(agent.getAccount() + "," + agent.getIp() + "," + " 批量添加产品，产品批次号 " + batchNo);
			}
		}
	}

	public Result createPnr(Agent agent, SaleOrderExt saleOrderExt) {
        String isCreatePnr = iParamService.getValueByKey("is_CreatPnr");
		if("true".equals(isCreatePnr)){
			try {
				log.info("创建国际PNR传入参数:"+JsonUtil.toJson(saleOrderExt));
				CreatePnrIn createPnrIn = new CreatePnrIn();
				createPnrIn.setOffice(office);
				//出票时间
				if(null!=saleOrderExt.getCreateTime()) {
					createPnrIn.setTicketTimeLimit(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				List<SdFlightSegment> segments = new ArrayList<SdFlightSegment>();
				List<AirTraveler> passengers = new ArrayList<AirTraveler>();
				int detailSize = saleOrderExt.getSaleOrderDetailList().size();
				for(int i=0;i<detailSize;i++){
					SdFlightSegment sdFlightSegment = new SdFlightSegment();
					sdFlightSegment.setRph((i+1));
					sdFlightSegment.setDepartureAirport(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getDepAirport());
					sdFlightSegment.setArrivalAirport(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getArrAirport());
					sdFlightSegment.setDepartureDateTime(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getDepTime(),"yyyy-MM-dd"));
					sdFlightSegment.setFlightNumber(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getFlightNo());
					sdFlightSegment.setMarketingAirline(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getAirline());
					sdFlightSegment.setResBookDesigCode(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getCabin());
					AirTraveler airTraveler = new AirTraveler();
					airTraveler.setPersonNameZH(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getSurname()+"/"+saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getName());
					airTraveler.setPersonNameEN(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getSurname()+"/"+saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getName());
					airTraveler.setDocType(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertType());
					airTraveler.setDocID(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertNo());
					airTraveler.setRph((i+1));
					airTraveler.setTel(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPhone());
					airTraveler.setPassengerTypeCode(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPassengerType());
					airTraveler.setGender("1".equals(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getGender())?"MALE":"FEMALE");
					airTraveler.setExpireDate(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertValid(),"yyyy-MM-dd"));
					airTraveler.setDocHolderNationality(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getNationality());
					airTraveler.setBirthDate(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPassengerBirth(),"yyyy-MM-dd"));
					passengers.add(airTraveler);
					segments.add(sdFlightSegment);

				}
				createPnrIn.setSegments(segments);

                int passengerSize = passengers.size()-1;
				for  ( int  i  =   0 ; i  <passengerSize ; i ++ )   {
					for  ( int  j  =  passengers.size()-1; j  >  i; j -- )   {
						if  (passengers.get(j).getPersonNameEN().equals(passengers.get(i).getPersonNameEN()))   {
							passengers.remove(j);
						}
					}
				}
				createPnrIn.setPassengers(passengers);


				Contact contact = new Contact(saleOrderExt.getContactName(),saleOrderExt.getContactMobile(), "");
				createPnrIn.setLxr(contact);
				CreatePnrOut reatePnrOut = createPnrService.createPnrI(createPnrIn);
				log.info("操作成功返回数据"+JsonUtil.toJson(reatePnrOut));
//				String json = "{\"pnr\":\"KP557W\",\"segs\":[{\"rph\":1,\"departureDateTime\":\"2017-05-24 19:40\",\"arrivalDateTime\":\"2017-05-24 22:00\",\"flightNumber\":\"5712\",\"numberInParty\":1,\"status\":\"HK\",\"isChanged\":false,\"departureAirport\":\"HND\",\"arrivalAirport\":\"GMP\",\"departureTerminal\":\"\",\"arrivalTerminal\":\"\",\"airline\":\"KE\",\"resBookDesigCode\":\"B\",\"isCodeshare\":false}],\"code\":\"178200\",\"shortText\":\"成功返回\"}";
//				CreatePnrOut  reatePnrOut  = new Gson().fromJson(json,CreatePnrOut.class);
				if(StringUtil.isNotNullOrEmpty(reatePnrOut.getPnr())){
					Pnr pnr = new Pnr();
					pnr.setPnr(reatePnrOut.getPnr());
					orderService.createPnr(agent,pnr,saleOrderExt.getSaleOrderNo());
					return new Result(SUCCESS_CODE, SAVE_SUCCESS);
				}else{
					return new Result(FAILED_CODE, SAVE_SUCCESS);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new Result(FAILED_CODE, SAVE_SUCCESS);
			}
		}else{
			return new Result(FAILED_CODE, SAVE_SUCCESS,"创建PNR接口已经关闭,请联系平台开启！");
		}
	}

	public Result createAndReturnPnr(SaleOrderExt saleOrderExt) {
		String isCreatePnr = iParamService.getValueByKey("is_CreatPnr");
		if ("true".equals(isCreatePnr)) {
			try {
				log.info("创建国际PNR传入参数:" + JsonUtil.toJson(saleOrderExt));
				CreatePnrIn createPnrIn = new CreatePnrIn();
				createPnrIn.setOffice(office);

				createPnrIn.setTicketTimeLimit(com.tempus.gss.product.hol.api.util.DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));


				List<SdFlightSegment> segments = new ArrayList<SdFlightSegment>();
				List<AirTraveler> passengers = new ArrayList<AirTraveler>();
				int detailSize = saleOrderExt.getSaleOrderDetailList().size();
				for (int i = 0; i < detailSize; i++) {
					SdFlightSegment sdFlightSegment = new SdFlightSegment();
					sdFlightSegment.setRph((i + 1));
					sdFlightSegment.setDepartureAirport(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getDepAirport());
					sdFlightSegment.setArrivalAirport(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getArrAirport());
					sdFlightSegment.setDepartureDateTime(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getDepTime(), "yyyy-MM-dd"));
					sdFlightSegment.setFlightNumber(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getFlightNo());
					sdFlightSegment.setMarketingAirline(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getAirline());
					sdFlightSegment.setResBookDesigCode(saleOrderExt.getSaleOrderDetailList().get(i).getLeg().getCabin());
					AirTraveler airTraveler = new AirTraveler();
					airTraveler.setPersonNameZH(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getSurname() + "/" + saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getName());
					airTraveler.setPersonNameEN(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getSurname() + "/" + saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getName());
					airTraveler.setDocType(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertType());
					airTraveler.setDocID(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertNo());
					airTraveler.setRph((i + 1));
					airTraveler.setTel(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPhone());
					airTraveler.setPassengerTypeCode(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPassengerType());
					airTraveler.setGender("1".equals(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getGender()) ? "MALE" : "FEMALE");
					airTraveler.setExpireDate(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getCertValid(), "yyyy-MM-dd"));
					airTraveler.setDocHolderNationality(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getNationality());
					airTraveler.setBirthDate(com.tempus.gss.product.hol.api.util.DateUtil.format(saleOrderExt.getSaleOrderDetailList().get(i).getPassenger().getPassengerBirth(), "yyyy-MM-dd"));
					passengers.add(airTraveler);
					segments.add(sdFlightSegment);

				}
				createPnrIn.setSegments(segments);

				int passengerSize = passengers.size() - 1;
				for (int i = 0; i < passengerSize; i++) {
					for (int j = passengerSize; j > i; j--) {
						if (passengers.get(j).getPersonNameEN().equals(passengers.get(i).getPersonNameEN())) {
							passengers.remove(j);
						}
					}
				}
				createPnrIn.setPassengers(passengers);

				Contact contact = new Contact(saleOrderExt.getContactName(), saleOrderExt.getContactMobile(), "");
				createPnrIn.setLxr(contact);
				CreatePnrOut reatePnrOut = createPnrService.createPnrI(createPnrIn);
				log.info("操作成功返回数据" + JsonUtil.toJson(reatePnrOut));
//				String json = "{\"pnr\":\"KP557W\",\"segs\":[{\"rph\":1,\"departureDateTime\":\"2017-05-24 19:40\",\"arrivalDateTime\":\"2017-05-24 22:00\",\"flightNumber\":\"5712\",\"numberInParty\":1,\"status\":\"HK\",\"isChanged\":false,\"departureAirport\":\"HND\",\"arrivalAirport\":\"GMP\",\"departureTerminal\":\"\",\"arrivalTerminal\":\"\",\"airline\":\"KE\",\"resBookDesigCode\":\"B\",\"isCodeshare\":false}],\"code\":\"178200\",\"shortText\":\"成功返回\"}";
//				CreatePnrOut  reatePnrOut  = new Gson().fromJson(json,CreatePnrOut.class);
				if (StringUtil.isNotNullOrEmpty(reatePnrOut.getPnr())) {
					Pnr pnr = new Pnr();
					pnr.setPnr(reatePnrOut.getPnr());
					//Pnr changePnr = orderService.createChangePnr(AgentUtil.getAgent(), pnr, saleOrderExt);

					return new Result(SUCCESS_CODE, SAVE_SUCCESS,pnr);
				} else {
					return new Result(FAILED_CODE, SAVE_SUCCESS);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new Result(FAILED_CODE, SAVE_SUCCESS);
			}
		} else {
			return new Result(FAILED_CODE, SAVE_SUCCESS, "创建PNR接口已经关闭,请联系平台开启！");
		}
	}

	private String writeErrorToFile(String sb) {
		try {
			FilePath uploadFile = fastDFSClientService.uploadFile(new ByteArrayInputStream(sb.getBytes("GBK")), "txt");
			return uploadFile.getUrl();
		} catch (Exception e) {
			log.error("写入错误到文件系统失败:", e);
		}
		return null;
	}

}

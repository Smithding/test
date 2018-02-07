package com.tempus.gss.product.ift.service;

import java.util.Date;
import java.util.List;

import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.gson.Gson;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.CustomerType;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ICustomerTypeService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Demand;
import com.tempus.gss.product.ift.api.entity.DemandDetail;
import com.tempus.gss.product.ift.api.entity.DemandPassenger;
import com.tempus.gss.product.ift.api.entity.vo.DemandVo;
import com.tempus.gss.product.ift.api.service.IDemandService;
import com.tempus.gss.product.ift.dao.DemandDao;
import com.tempus.gss.product.ift.dao.DemandDetailDao;
import com.tempus.gss.product.ift.dao.DemandPassengerDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.vo.Agent;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class DemandServiceImpl implements IDemandService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	DemandDao demandDao;
	@Autowired
	DemandDetailDao demandDetailDao;
	@Autowired
	DemandPassengerDao demandPassengerDao;
	@Reference
	IMaxNoService maxNoService;

	@Reference
	ICustomerService customerService;
	@Reference
	ICustomerTypeService customerTypeService;
	@Reference
	private IAirportService airportService;


	/**
	 * @param demandRequest 创建请求.
	 * @return 1:创建成功
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int create(RequestWithActor<Demand> demandRequest) throws Exception{
		log.info("创建需求单开始");
		Agent agent = demandRequest.getAgent();

		if (demandRequest.getEntity() == null){
			log.error("传入参数为空");
			throw new GSSException("创建需求模块", "0101", "传入参数为空");
		}else if(demandRequest.getEntity().getAdultCount() == null){
			log.error("需求单成人数量为空");
			throw new GSSException("创建需求模块", "0103", "需求单成人数量为空");
		}else if(demandRequest.getEntity().getPassengerType() == null){
			log.error("需求单乘客类型为空");
			throw new GSSException("创建需求模块", "0104", "需求单乘客类型为空");
		}else if(demandRequest.getEntity().getServiceClass() == null){
			log.error("需求单舱位等级为空");
			throw new GSSException("创建需求模块", "0105", "需求单舱位等级为空");
		}else if(demandRequest.getEntity().getContactName() == null){
			log.error("需求单联系人为空");
			throw new GSSException("创建需求模块", "0106", "需求单联系人为空");
		}else if(demandRequest.getEntity().getContactPhone() == null){
			log.error("需求单联系人手机号为空");
			throw new GSSException("创建需求模块", "0107", "需求单联系人手机号为空");
		}else if(agent ==null) {
			log.error("agent为空");
			throw new GSSException("创建需求模块", "0109", "agent为空");
		}else if(!"1".equals(demandRequest.getEntity().getPassengerType())&&!"2".equals(demandRequest.getEntity().getPassengerType())&&!"3".equals(demandRequest.getEntity().getPassengerType())&&!"4".equals(demandRequest.getEntity().getPassengerType())){
			log.error("需求单乘客类型必须传1,2,3,4 分别表示1.普通，2.学生，3.新移民 4.劳务");
			throw new GSSException("需求单乘客类型必须传1,2,3,4 分别表示1.普通，2.学生，3.新移民 4.劳务", "0112", "创建需求模块");
		}else if(demandRequest.getEntity().getIsTeam() == null){
			log.error("需求单类型为空");
			demandRequest.getEntity().setIsTeam((byte)1);
            throw new GSSException("创建需求模块", "0110", "需求单类型为空");
		}


		/**
		 * 校验客商编号
		 */
		Customer customer = customerService.getCustomerByNo(agent,agent.getNum());
		if(customer==null){
			log.error("客商编号与客商系统数据不一致");
			throw new GSSException("客商编号与客商系统数据不一致（customerNo）", "0101", "创建需求模块");
		}else{
			if(!customer.getCustomerTypeNo().equals(agent.getType())){
				log.error("客商类型与客商系统数据不一致");
				throw new GSSException("客商类型与客商系统数据不一致（customerTypeNo）", "0101", "创建需求模块");
			}
		}
		try {
			Long demandNo = maxNoService.generateBizNo("IFT_DEMAND_NO", 25);
			Long teamNO = IdWorker.getId();
			demandRequest.getEntity().setDemandNo(demandNo);
			demandRequest.getEntity().setTeamNo(teamNO);
			demandRequest.getEntity().setOwner(agent.getOwner());
			demandRequest.getEntity().setValid((byte)1);
			demandRequest.getEntity().setLocker(0L);//创建需求单统一为解锁状态
			demandRequest.getEntity().setCreator(agent.getAccount());
			demandRequest.getEntity().setCreateTime(new Date());
			demandRequest.getEntity().setStatus("1");//待核价
			demandRequest.getEntity().setCustomerNo(agent.getNum());
			demandRequest.getEntity().setCustomerTypeNo(String.valueOf(agent.getType()));
			int demandFlag = demandDao.insertSelective(demandRequest.getEntity());
			if (demandFlag == 0) {
				log.error("demand插入异常");
				throw new GSSException("创建需求模块", "0102", "需求插入失败");
			}
			if (demandRequest.getEntity().getDemandDetailList() != null
					&& demandRequest.getEntity().getDemandDetailList().size() != 0) {
				for (DemandDetail demandDetail : demandRequest.getEntity().getDemandDetailList()) {
					demandDetail.setDemandDetailNo(maxNoService.generateBizNo("IFT_DEMAND_DETAIL_NO", 26));
					demandDetail.setDemandNo(demandNo);
					demandDetail.setCreateTime(new Date());
					demandDetail.setCreator(agent.getAccount());
					if (demandDetail.getDemandDetailNo() == null
							|| demandDetail.getDepTime() == null
							|| demandDetail.getArrAirport() == null
							|| demandDetail.getDepAirport() == null) {
						log.error("需求明细表关键字段不能为空");
						throw new GSSException("更新需求模块", "0103", "需求明细表关键字段不能为空");
					}
					demandDetail.setValid((byte)1);
					demandDetail.setOwner(agent.getOwner());
					int demandDetailFlag = demandDetailDao.insertSelective(demandDetail);
					if (demandDetailFlag == 0) {
						log.error("demandDetail插入异常");
						throw new GSSException("创建需求模块", "0104", "需求明细插入失败");
					}
				}
			}
			//团队需求单可以不传乘客信息
			if("0".equals(demandRequest.getEntity().getIsTeam()+"")){
				if (demandRequest.getEntity().getDemandPassengerList() != null
						&& demandRequest.getEntity().getDemandPassengerList().size() > 0) {
					for (DemandPassenger demandPassenger : demandRequest.getEntity().getDemandPassengerList()) {
						demandPassenger.setPassengerNo(maxNoService.generateBizNo("IFT_DEMAND_PASSENGER_NO", 50));
						demandPassenger.setCreator(agent.getAccount());
						demandPassenger.setDemandNo(demandNo);
						demandPassenger.setCreateTime(new Date());
						demandPassenger.setOwner(agent.getOwner());
						if (demandPassenger.getPassengerNo() == null) {
							log.error("需求乘客信息表关键字段不能为空");
							throw new GSSException("更新需求模块", "0105", "需求乘客信息表关键字段不能为空");
						}
						demandPassenger.setValid((byte)1);
						int demandPassengerFlag = demandPassengerDao.insertSelective(demandPassenger);
						if (demandPassengerFlag == 0) {
							log.error("demandPassenger插入异常");
							throw new GSSException("创建需求模块", "0106", "需求乘客信息插入失败");
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("需求单创建失败", e);
			throw new GSSException("创建需求模块", "0107", "创建需求模块失败");
		}

		return 1;
	}


	/**
	 * @param demandNo 需求单编号.
	 *                 Valid 删除标志 0 无效 已删除 1 有效
	 * @return
	 */
	@Override
	@Transactional
	public boolean cancel(RequestWithActor<Long> demandNo) {
		log.info("取消需求单号开始");
		boolean flagBoolean = false;
		try {
			Demand demand = demandDao.selectByPrimaryKey(demandNo.getEntity().longValue());
			if (demand != null) {
				demand.setLocker(0L);
				demand.setStatus(String.valueOf(3));//状态3为已取消
				demand.setModifier(demandNo.getAgent().getAccount());
				demand.setModifyTime(new Date());
				int flag = demandDao.updateByPrimaryKeySelective(demand);
				if (flag == 1) {
					flagBoolean = true;
				} else {
					log.info("需求修改状态失败");
					throw new GSSException("取消需求单号模块", "0201", "需求修改状态失败");
				}
			} else {
				log.info("根据该单号未查询出相应数据");
				throw new GSSException("取消需求单号模块", "0202", "需求修改状态失败");
			}
		} catch (Exception e) {
			log.error("取消状态失败", e);
			throw new GSSException("取消需求单号模块", "0203", "取消状态失败");
		}
		return flagBoolean;
	}

	/**
	 * @param requestWithActor 查询请求
	 * @return PageInfo<Demand> 分页的需求列表
	 */
	@Override
	public Page<Demand> queryDemandList(Page<Demand> page, RequestWithActor<DemandVo> requestWithActor) {
		log.info("需求查询开始");
		try {
			requestWithActor.getEntity().setOwner(requestWithActor.getAgent().getOwner());
			List<Demand> demandList = demandDao.queryObjByKey(page, requestWithActor.getEntity());
			System.out.println(new Gson().toJson(demandList));
			//订单来源查询！！！
			for (Demand demand : demandList) {
				if (demand.getCustomerTypeNo() != null) {
					CustomerType customerType = customerTypeService
							.getCustomerTypeNameByNo(requestWithActor.getAgent(),Long.valueOf(demand.getCustomerTypeNo()));
					if (customerType != null) {
						demand.setCustomerTypeNo(customerType.getName());
					}
				}
			}
			page.setRecords(demandList);
		} catch (Exception e) {
			log.error("查询请求失败", e);
			throw new GSSException("查询需求模块", "0302", "查询失败");
		}
		log.info("需求查询结束");
		return page;
	}

	/**
	 * @param demandNo 查询请求
	 * @return Demand 单条记录数
	 */
	@Override
	public Demand getDemand(RequestWithActor<Long> demandNo) {
		Demand demand = demandDao.selectByPrimaryKey(demandNo.getEntity().longValue());
		if(demand!=null&&demand.getDemandDetailList()!=null&&demand.getDemandDetailList().size()>0){
			for(DemandDetail demandDetail:demand.getDemandDetailList()){
				String arrCityName = getByCityCode(demandDetail.getArrAirport());
				String depCityName = getByCityCode(demandDetail.getDepAirport());
				demandDetail.setArrCityName(arrCityName==null ? "":arrCityName);
				demandDetail.setDepCityName(depCityName==null ? "":depCityName);
			}
		}
		return demand;
	}

	/**
	 * 根据机场三字码获取城市名称
	 * @param cityCode
	 * @return
	 */
	private String getByCityCode(String cityCode){
		String arrAirport = "";
		if(cityCode!=null){
			Airport airport = airportService.queryAirportByCode(cityCode, "D");
			if(airport==null){
				airport =  airportService.queryAirportByCode(cityCode, "i");
			}
			if(airport!=null){
				arrAirport = airport.getCityCName();
			}
		}

		return arrAirport;
	}

	/**
	 * @param demandNo 需求单核价请求.
	 *                 status 启用状态 1：待核价，2：已核价，3：已取消
	 * @return
	 */
	@Override
	@Transactional
	public boolean confirmPrice(RequestWithActor<Long> demandNo) {

		log.info("需求单核价请求开始");
		boolean flagBoolean = false;
		try {
			Demand demand = demandDao.selectByPrimaryKey(demandNo.getEntity().longValue());
			if (demand != null) {
				if (demand.getStatus().equals("2")) {
					demand.setStatus("3");
					demand.setModifier(demandNo.getAgent().getAccount());
					demand.setModifyTime(new Date());
					int flag = demandDao.updateStatus(demand);
					if (flag == 1) {
						flagBoolean = true;
					} else {
						log.info("修改需求statu失败");
						throw new GSSException("需求单核价模块", "0501", "修改需求statu失败");
					}
				}
			} else {
				log.info("根据该单号未查询出相应数据");
			}
		} catch (Exception e) {
			log.error("修改status失败", e);
			throw new GSSException("需求单核价模块", "0503", "需求模块修改status失败");
		}
		log.info("需求单核价请求结束");
		return flagBoolean;
	}

	/**
	 * @param demand
	 * @return 1:修改成功
	 */
	@Override
	@Transactional
	public int updateDemand(RequestWithActor<Demand> demand) {
		log.info("修改需求单开始");
		try {
			int flag = demandDao.updateByPrimaryKeySelective(demand.getEntity());
			if (flag == 0) {
				log.error("需求更新失败");
				throw new GSSException("更新需求模块", "0601", "需求更新失败");
			}
			if (demand.getEntity().getDemandDetailList() != null
					&& demand.getEntity().getDemandDetailList().size() != 0) {
				for (DemandDetail demandDetail : demand.getEntity().getDemandDetailList()) {
					if (demandDetail.getDemandDetailNo() == null) {
						log.info("需求明细表关键字段不能为空");
						throw new GSSException("更新需求模块", "0602", "需求明细表关键字段不能为空");
					}
					int demandDetailFlag = demandDetailDao.updateByPrimaryKeySelective(demandDetail);
					if (demandDetailFlag == 0) {
						log.info("需求更新明细失败");
						throw new GSSException("更新需求模块", "0603", "需求更新明细失败");
					}
				}
			}
			if (demand.getEntity().getDemandPassengerList() != null
					&& demand.getEntity().getDemandPassengerList().size() != 0) {
				for (DemandPassenger demandPassenger : demand.getEntity().getDemandPassengerList()) {
					if (demandPassenger.getPassengerNo() == null) {
						log.error("需求乘客信息表关键字段不能为空");
						throw new GSSException("更新需求模块", "0604", "需求乘客信息表关键字段不能为空");
					}
					int demandPassengerFlag = demandPassengerDao.updateByPrimaryKeySelective(demandPassenger);
					if (demandPassengerFlag == 0) {
						log.error("需求更新乘客信息失败");
						throw new GSSException("更新需求模块", "0605", "需求更新乘客信息失败");
					}
				}
			}
		} catch (Exception e) {
			log.error("更新失败", e);
			throw new GSSException("更新需求模块", "0606", "需求更新模块失败");
		}
		log.info("修改需求单结束");
		return 1;
	}

	@Override
	public Long countDemand(RequestWithActor<DemandVo> demandVo) {
		log.info("需求数量查询开始");
		Long count = 0L;
		try {
			count = demandDao.countDemand(demandVo.getEntity());
		} catch (Exception e) {
			log.error("查询请求失败", e);
			throw new GSSException("需求数量查询开始", "0302", "查询失败");
		}
		log.info("需求数量查询结束");
		return count;
	}

	/**
	 * 锁定 解锁
	 */
	@Override
	@Transactional
	public boolean locker(RequestWithActor<Long> demandNo) {
		log.info("锁定 解锁开始");
		boolean flagBoolean = false;
		try {
			Demand demand = demandDao.selectByPrimaryKey(demandNo.getEntity().longValue());
			//锁定并将状态设置为已核价
			if (demand != null) {
				if (demand.getLocker() == 0 || demand.getLocker() == null) {
					demand.setLocker(demandNo.getAgent().getId()==null?1:demandNo.getAgent().getId());
					demand.setModifier(demandNo.getAgent().getAccount());
					demand.setModifyTime(new Date());
					int flag = demandDao.updateByPrimaryKeySelective(demand);
					if (flag == 1) {
						flagBoolean = true;
					} else {
						log.info("锁定 失败");
						throw new GSSException("锁定 解锁模块", "0201", "锁定失败");
					}
				} else {//解锁
					demand.setLocker(0L);
					demand.setModifier(demandNo.getAgent().getAccount());
					demand.setModifyTime(new Date());
					int flag = demandDao.updateByPrimaryKeySelective(demand);
					if (flag == 1) {
						flagBoolean = true;
					} else {
						log.info(" 解锁失败");
						throw new GSSException("锁定 解锁模块", "0202", "解锁失败");
					}
				}
			} else {
				log.info("根据该单号未查询出相应数据");
			}
		} catch (Exception e) {
			log.error("锁定 解锁失败", e);
			throw new GSSException("锁定 解锁模块", "0203", "锁定 解锁失败");
		}
		log.info("锁定 解锁结束");
		return flagBoolean;
	}


	@Override
	@Transactional
	public int updateDemandStatus(RequestWithActor<Demand> demand) {
		log.info("更新需求单状态开始");
		try {
			int flag = demandDao.updateByPrimaryKeySelective(demand.getEntity());
			if (flag == 0) {
				log.error("更新需求单状态");
				throw new GSSException("更新需求模块", "0601", "需求更新失败");
			}
		} catch (Exception e) {
			log.error("更新需求单状态", e);
			throw new GSSException("更新需求模块", "0606", "需求更新模块失败");
		}
		log.info("更新需求单状态结束");
		return 1;
	}

}

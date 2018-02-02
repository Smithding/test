package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.*;
import com.tempus.gss.order.service.*;
import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.SaleChangeDetail;
import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ift.api.service.IAccountService;
import com.tempus.gss.product.ift.dao.*;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/1/13.
 */
@Service
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class IftAccountServiceImpl implements IAccountService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SaleOrderDetailDao saleOrderDetailDao;
	/*应收应付*/
	@Reference
	IPlanAmountRecordService planAmountRecordService;

	@Reference
	IBuyOrderService buyOrderService;

	@Reference
	IBuyChangeService buyChangeService;

	@Autowired
	PassengerDao passengerDao;


	@Autowired
	SaleChangeDetailDao saleChangeDetailDao;

	@Autowired
	PassengerChangePriceDao passengerChangePriceDao;

	@Reference
	ISaleChangeService saleChangeService;

	@Reference
	ISaleOrderService saleOrderService;

	@Autowired
	SaleChangeExtDao saleChangeExtDao;

	@Override
	@Transactional
	public List<UpdatePlanAmountVO> adjustAccountPrice(Agent agent, Map<String, BigDecimal> sb) {
		List<UpdatePlanAmountVO> updatePlanAmountVOList = new ArrayList<>();
		if (agent == null) {
			log.error("agent为空");
			throw new GSSException("agent为空", "0001", "根据票号修改采购结算价失败");
		}
		if (sb == null) {
			log.error("集合为空");
			throw new GSSException("集合为空", "0002", "根据票号修改采购结算价失败");
		}
		try {
			log.info("根据票号修改采购结算价开始======sb=" + sb.toString());
			Map<String, BigDecimal> orderMap = new HashMap<>();
			Map<String, BigDecimal> changeMap = new HashMap<>();
			Iterator<Map.Entry<String, BigDecimal>> it = sb.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, BigDecimal> entry = it.next();
				String ticketNo = entry.getKey();
				BigDecimal price = entry.getValue();
				List<SaleOrderDetail> saleOrderDetailList = saleOrderDetailDao.selectByTicketNo(ticketNo);
				if (saleOrderDetailList != null && saleOrderDetailList.size() != 0) {
					SaleOrderDetail saleOrderDetail = saleOrderDetailList.get(0);
					if (saleOrderDetail.getIsChange()) {
						changeMap.put(ticketNo, price);
					} else {
						orderMap.put(ticketNo, price);
					}
				}
			}
			updatePlanAmountVOList.addAll(adjustOrder(agent, orderMap));
			updatePlanAmountVOList.addAll(adjustChange(agent, changeMap));
		}catch (GSSException ub){
			throw ub;
		}catch (Exception e){
			log.error("根据票号修改采购结算价异常，e:"+e);
			throw new GSSException("根据票号修改采购结算价异常，e:"+e, "0002", "根据票号修改采购结算价失败");
		}
		return updatePlanAmountVOList;
	}

	@Transactional
	public List<UpdatePlanAmountVO> adjustOrder(Agent agent, Map<String, BigDecimal> sb) {
		List<UpdatePlanAmountVO> updatePlanAmountVOList = new ArrayList<>();
		try {
			BuyOrder buyOrder = null;
			PlanAmountRecord par = null;
			BigDecimal newTotalPrice = null;
			Iterator<Map.Entry<String, BigDecimal>> it = sb.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, BigDecimal> entry = it.next();
				String ticketNo = entry.getKey();
				BigDecimal price = entry.getValue();
				List<SaleOrderDetail> saleOrderDetailList = saleOrderDetailDao.selectByTicketNo(ticketNo);
				if (saleOrderDetailList != null && saleOrderDetailList.size() != 0) {
					SaleOrderDetail saleOrderDetail = saleOrderDetailList.get(0);
					//判断是为采购单，或者为采购变更单
					if (saleOrderDetail.getIsChange()) {
						log.error("saleOrderDetailNo="+saleOrderDetail.getSaleOrderDetailNo()+",不为采购单的人加航段");
						throw new GSSException("saleOrderDetailNo="+saleOrderDetail.getSaleOrderDetailNo()+",不为采购单的人加航段", "0003", "根据票号修改采购结算价失败");
					}
					//根据销售单信息获取采购单
					List<BuyOrder> buyOrderList = buyOrderService
							.getBuyOrdersBySONo(agent, saleOrderDetail.getSaleOrderNo());
					if (buyOrderList != null && buyOrderList.size() != 0) {
						//暂时无多个供应商，默认取第一个
						if (buyOrder == null) {
							buyOrder = buyOrderList.get(0);
						} else if (!buyOrder.getBuyOrderNo().equals(buyOrderList.get(0).getBuyOrderNo())) {
							log.error("采购单不一致");
							throw new GSSException("采购单不一致", "0003", "根据票号修改采购结算价失败");
						}

						//根据采购单信息获取应收应付记录
						List<PlanAmountRecord> planAmountRecordList = planAmountRecordService
								.queryListByBusNo(agent, buyOrder.getBuyOrderNo(), 3);

						for (PlanAmountRecord planAmountRecord : planAmountRecordList) {
							if (planAmountRecord.getBusinessType().equals(3)) {
								par = planAmountRecord;
								if (newTotalPrice == null) {
									newTotalPrice = par.getPlanAmount();
								}
								break;
							}
						}

						newTotalPrice = newTotalPrice.subtract((saleOrderDetail.getPassenger().getBuyPrice()))
								.add(price);
						log.info("采购单应收价格,newTotalPrice" + newTotalPrice + ",ticketNo=" + ticketNo);
						//修改乘机人采购结算价
						saleOrderDetail.getPassenger().setBuyPrice(price);
						passengerDao.updateByPrimaryKeySelective(saleOrderDetail.getPassenger());

					}

				}
			}
			if(newTotalPrice!=null) {
				//修改应收应付
				UpdatePlanAmountVO updatePlanAmountVO = new UpdatePlanAmountVO();
				updatePlanAmountVO.setId(par.getId());
				updatePlanAmountVO.setPlanAmount(newTotalPrice);
				updatePlanAmountVO.setIncomeExpenseType(par.getIncomeExpenseType());
				updatePlanAmountVO.setRecordMovingType(par.getRecordMovingType());
				planAmountRecordService.updateBuyPlanAmount(agent, updatePlanAmountVO);
				updatePlanAmountVOList.add(updatePlanAmountVO);
			}
			log.info("根据票号修改采购结算价结束======");
			return updatePlanAmountVOList;
		} catch (GSSException ub) {
			throw ub;
		} catch (Exception e) {
			log.error("根据票号修改采购结算价失败，e:"+e);
			throw new GSSException("根据票号修改采购结算价失败，e:"+e, "0003", "根据票号修改采购结算价失败");
		}
	}

	@Transactional
	public List<UpdatePlanAmountVO> adjustChange(Agent agent, Map<String, BigDecimal> sb) {
		List<UpdatePlanAmountVO> updatePlanAmountVOList = new ArrayList<>();
		try {
			BuyChange buyChange = null;
			PlanAmountRecord par = null;
			BigDecimal newTotalPrice = null;
			Iterator<Map.Entry<String, BigDecimal>> it = sb.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, BigDecimal> entry = it.next();
				String ticketNo = entry.getKey();
				BigDecimal price = entry.getValue();
				List<SaleOrderDetail> saleOrderDetailList = saleOrderDetailDao.selectByTicketNo(ticketNo);
				if (saleOrderDetailList != null && saleOrderDetailList.size() != 0) {
					SaleOrderDetail saleOrderDetail = saleOrderDetailList.get(0);
					//判断是为采购单，或者为采购变更单
					if (!saleOrderDetail.getIsChange()) {
						log.error("saleOrderDetailNo="+saleOrderDetail.getSaleOrderDetailNo()+",不为采购变更单的人加航段");
						throw new GSSException("saleOrderDetailNo="+saleOrderDetail.getSaleOrderDetailNo()+",不为采购单的人加航段", "0003", "根据票号修改采购结算价失败");
					}
					SaleChangeDetail saleChangeDetail = saleChangeDetailDao.selectBySaleOrderDetailNo(saleOrderDetail.getSaleOrderDetailNo());
					//根据销售单信息获取采购单
					BuyChange bc = buyChangeService.getBuyChangeByNo(agent,saleChangeDetail.getBuyChangeNo());
					if(buyChange==null){
						buyChange = bc;
					}else if (!buyChange.getBuyChangeNo().equals(bc.getBuyChangeNo())) {
						log.error("采购变更单不一致");
						throw new GSSException("采购变更单不一致", "0003", "根据票号修改采购结算价失败");
					}

					//根据采购单信息获取应收应付记录
					List<PlanAmountRecord> planAmountRecordList = planAmountRecordService
							.queryListByBusNo(agent, buyChange.getBuyChangeNo(), 5);

					for (PlanAmountRecord planAmountRecord : planAmountRecordList) {
						if (planAmountRecord.getBusinessType().equals(5)) {
							par = planAmountRecord;
							if (newTotalPrice == null) {
								newTotalPrice = par.getPlanAmount();
							}
							break;
						}
					}

					if (saleChangeDetail != null) {
						SaleChangeExt saleChangeExt = saleChangeExtDao
								.selectByPrimaryKey(saleChangeDetail.getSaleChangeNo());

						PassengerChangePrice pcp = null;
						if (saleChangeExt != null) {
							List<PassengerChangePrice> passengerChangePriceList = saleChangeExt
									.getPassengerChangePriceList();
							for (PassengerChangePrice passengerChangePrice : passengerChangePriceList) {
								if (passengerChangePrice.getPassengerNo()
										.equals(saleOrderDetail.getPassenger().getPassengerNo())) {
									pcp = passengerChangePrice;
									break;
								}
							}
						}
						if (pcp != null) {
							newTotalPrice = newTotalPrice.subtract((pcp.getBuyPrice())).add(price);
							log.info("采购单应收价格,newTotalPrice" + newTotalPrice + ",ticketNo=" + ticketNo);

							pcp.setBuyPrice(price);
							passengerChangePriceDao.updateByPrimaryKeySelective(pcp);

						}
					}
				}
			}
			if(newTotalPrice!=null) {
				//修改应收应付
				UpdatePlanAmountVO updatePlanAmountVO = new UpdatePlanAmountVO();
				updatePlanAmountVO.setId(par.getId());
				updatePlanAmountVO.setPlanAmount(newTotalPrice);
				updatePlanAmountVO.setIncomeExpenseType(par.getIncomeExpenseType());
				updatePlanAmountVO.setRecordMovingType(par.getRecordMovingType());
				planAmountRecordService.updateBuyPlanAmount(agent, updatePlanAmountVO);
				updatePlanAmountVOList.add(updatePlanAmountVO);
			}
			log.info("根据票号修改采购结算价结束======");
			return updatePlanAmountVOList;
		} catch (GSSException ub) {
			throw ub;
		} catch (Exception e) {
			log.error("根据票号修改采购结算价失败，e:"+e);
			throw new GSSException("根据票号修改采购结算价失败，e:"+e, "0003", "根据票号修改采购结算价失败");
		}
	}
}

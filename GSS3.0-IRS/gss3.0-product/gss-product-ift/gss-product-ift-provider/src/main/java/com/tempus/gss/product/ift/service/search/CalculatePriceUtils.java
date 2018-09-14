package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.util.CollectionUtils;

import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.formula.FormulaParameters;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyChange;
import com.tempus.gss.util.JsonUtil;

import springfox.documentation.spring.web.json.Json;


/**
 * 根据政策计算航程价格
 * 
 * @author juan.yin
 *
 */
public class CalculatePriceUtils {
	/**
	 * 计算白屏航班查询舱位价格(只有当往返是会存在两条政策)
	 * 
	 * @param queryIBEDetail
	 * @param policyList
	 * @param formula
	 * 计算公式 1计奖的部分*(1-代理费率)(1-返点)+无奖的部分*(1-代理费率)+税费+手续费
	 * 计奖的部分*(1-代理费率-返点)+无奖的部分*(1-代理费率)+税费+手续费
	 */
	public static QueryIBEDetail fligthCalculate(QueryIBEDetail queryIBEDetail, List<IftPolicy> policyList, int formula) {
		IftPolicy policy = new IftPolicy();// 单程只会有一条政策
		// 单程
		List<QueryIBEDetail> details = new ArrayList<QueryIBEDetail>();
		/*if (queryIBEDetail.getLegType().intValue() == 1) {*/
			if(!CollectionUtils.isEmpty(policyList)){
				for (IftPolicy iftPolicy : policyList) {
					//根据政策计算价格
					QueryIBEDetail detail = (QueryIBEDetail)CloneUtils.deepCopy(queryIBEDetail);
					// 根据政策计算价格
					detail = oneWayQueryIBEDetail(detail, iftPolicy, formula);
					details.add(detail);
				}
			}else{
				//根据政策计算价格
				QueryIBEDetail detail = (QueryIBEDetail)CloneUtils.deepCopy(queryIBEDetail);
				// 根据政策计算价格
				detail = oneWayQueryIBEDetail(detail, policy, formula);
				details.add(detail);
			}
			//价格排序
			priceSort(details);
	/*	}else{
				if(!CollectionUtils.isEmpty(policyList)){
					for (IftPolicy iftPolicy : policyList) {
						//根据政策计算价格
						queryIBEDetail = oneWayQueryIBEDetail(queryIBEDetail,iftPolicy,formula);
						details.add(queryIBEDetail);
					}
				}else{
					//当没有政策的情况，给个空的政策对象
					queryIBEDetail = oneWayQueryIBEDetail(queryIBEDetail,policy,formula);
					details.add(queryIBEDetail);
				}
				//价格排序
				priceSort(details);
		}*/
		return details.get(0);
	}
	/**
	 * 订单预定页面计算订单价格
	 * 
	 * @param queryIBEDetail
	 * @param policyList
	 * @param formula
	 * 计算公式 1计奖的部分*(1-代理费率)(1-返点)+无奖的部分*(1-代理费率)+税费+手续费
	 * 计奖的部分*(1-代理费率-返点)+无奖的部分*(1-代理费率)+税费+手续费
	 */
	public static List<IftPolicyChange> orderPolicyCalculate(QueryIBEDetail queryIBEDetail, List<IftPolicy> policyList,
			int formula) {
		List<IftPolicyChange> flightPolicies = new ArrayList<IftPolicyChange>();
		try {
			if (!CollectionUtils.isEmpty(policyList)) {
				for (IftPolicy iftPolicy : policyList) {
					QueryIBEDetail detail = (QueryIBEDetail)CloneUtils.deepCopy(queryIBEDetail);
					// 根据政策计算价格
					detail = oneWayQueryIBEDetail(detail, iftPolicy, formula);
					// 订单预定页面价格和政策组装
					IftPolicyChange policyChange = OrderPolicyUtils.getIftPolicyChange(detail, iftPolicy);
					flightPolicies.add(policyChange);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		priceSortCabins(flightPolicies);
		return flightPolicies;
	}
	/**
	 * 单程根据政策计算价格
	 * @param queryIBEDetail
	 * @param policy
	 * @param formula
	 * @return
	 */
	private static  QueryIBEDetail oneWayQueryIBEDetail(QueryIBEDetail queryIBEDetail,IftPolicy policy,int formula){
		for (CabinsPricesTotals cabins : queryIBEDetail.getCabinsPricesTotalses()) {
			for (PassengerTypePricesTotal passengerTypePricesTotal : cabins.getPassengerTypePricesTotals()) {// 乘客价格信息
				FormulaParameters formulaP = new FormulaParameters();
				if (policy==null||(queryIBEDetail.getIsShare()==null||queryIBEDetail.getIsShare()==false)&&
						(queryIBEDetail.getRtnIsShare()==null||queryIBEDetail.getRtnIsShare()==false)) {// 是否直飞/或者不包含共享航程
					 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
				}else{//包含共享段
					if(policy.getShareIsSuitAirline()!=null&&policy.getShareIsSuitAirline()){//是否指定航司给全部奖励
						for (Flight flight : queryIBEDetail.getFlights()) {
							String codeShare = flight.getCodeshare()!=null?flight.getCodeshare().substring(0, 2):"";
							if(codeShare!=""&&policy.getShareSuitAirline().contains(codeShare)){//政策指定的共享航司是否包含航班查询中的共享航司
								 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							}
						}
					}else{
						if(policy.getShareRewardType()==null||policy.getShareRewardType().equals("")||policy.getShareRewardType().intValue()==1){
							 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							 formulaP.setSaleRebate(new BigDecimal(0));//重新设置返点为0
							 formulaP.setShareRebate(new BigDecimal(0));//重新设置返点为0
						}else if(policy.getShareRewardType().intValue()==2){//2全程指定奖励 shareLegReward
							 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							 formulaP.setSaleRebate(policy.getShareAssignReward());//设置指定奖励
							 formulaP.setShareRebate(policy.getShareAssignReward());//重新设置返点为0
						}else if(policy.getShareRewardType().intValue()==3){//3共享段无奖励
							formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							formulaP.setShareRebate(new BigDecimal(0));//设置指定奖励
						}else if(policy.getShareRewardType().intValue()==4){//4共享段指定奖励 shareLegReward
							formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							formulaP.setShareRebate(policy.getShareLegReward());//设置指定奖励
						}else if(policy.getShareRewardType().intValue()==5){//5给全部奖励
							formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
						}
						formulaP.setIsShare(true);
					}
				}
				formulaP.setFlightType(queryIBEDetail.getLegType());//设置航程信息
				if(queryIBEDetail.getLegType().intValue() == 1){
					formulaPrice(passengerTypePricesTotal, formulaP, formula);//单程计算价格
				}else{
					towFormulaPrice(passengerTypePricesTotal, formulaP, formula);//单程计算价格
				}
			}
		}
		return queryIBEDetail;
	}
	/**
	 * 单程计算销售价格
	 * @param passengerTypePricesTotal
	 * @param formulaP
	 * @param formula
	 */
	private static void formulaPrice(PassengerTypePricesTotal passengerTypePricesTotal,FormulaParameters formulaP,int formula){
		switch (formula) {
		case 1:
			// 公式2:票价*（1-代理费率）*（1-返点）+ 税款 + 手续费-直减费用(往返直减)
			formulaP = FormulaUtils.formulaMethod1(formulaP);
			break;
		default:
			// 计奖的部分*(1-代理费率-返点)+无奖的部分*(1-代理费率)+税费+手续费-直减费用
			formulaP = FormulaUtils.formulaMethod2(formulaP);
		}
		passengerTypePricesTotal.setSalePrice(formulaP.getSalePrice().setScale(0, BigDecimal.ROUND_UP));//销售价格
		passengerTypePricesTotal.setAwardPrice(formulaP.getAwardPrice());//及奖价格
		setPassengerTypePricesTotal(passengerTypePricesTotal,formulaP);
	}
	/**
	 * 往返计算销售价格
	 * @param passengerTypePricesTotal
	 * @param formulaP
	 * @param formula
	 */
	private static void towFormulaPrice(PassengerTypePricesTotal passengerTypePricesTotal,FormulaParameters formulaP,int formula){
		switch (formula) {
		case 1:
			// 公式2:票价*（1-代理费率）*（1-返点）+ 税款 + 手续费-直减费用(往返直减)
			formulaP = FormulaUtils.towFormulaMethod1(formulaP);
			break;
		default:
			// 计奖的部分*(1-代理费率-返点)+无奖的部分*(1-代理费率)+税费+手续费-直减费用
			formulaP = FormulaUtils.towFormulaMethod2(formulaP);
		}
		passengerTypePricesTotal.setSalePrice(formulaP.getSalePrice().setScale(0, BigDecimal.ROUND_UP));//销售价格
		passengerTypePricesTotal.setAwardPrice(formulaP.getAwardPrice());//及奖价格
		setPassengerTypePricesTotal(passengerTypePricesTotal,formulaP);
	}
    /**
     * 价格排序
     * @param details
     */
	private static void priceSort(List<QueryIBEDetail> details) {
		// 按结算价从低到高排序
		if (!CollectionUtils.isEmpty(details)) {
			Collections.sort(details, new Comparator<QueryIBEDetail>() {
				public int compare(QueryIBEDetail o1, QueryIBEDetail o2) {

					if (o1.getCabinsPricesTotalses().get(0).getSalePriceCount() > o2.getCabinsPricesTotalses().get(0)
							.getSalePriceCount()) {
						return 1;
					} else if (o1.getCabinsPricesTotalses().get(0).getSalePriceCount() > o2.getCabinsPricesTotalses()
							.get(0).getSalePriceCount())
						return 0;
					return -1;
				}
			});
		}
	}
	 /**
     * 订单预订页面价格排序
     * @param details
     */
	private static void priceSortCabins(List<IftPolicyChange> flightPolicies) {
		// 按结算价从低到高排序
		if (!CollectionUtils.isEmpty(flightPolicies)) {
			Collections.sort(flightPolicies, new Comparator<IftPolicyChange>() {
				public int compare(IftPolicyChange o1, IftPolicyChange o2) {

					if (o1.getCabinsPricesTotalses().get(0).getSalePriceCount() > o2.getCabinsPricesTotalses().get(0)
							.getSalePriceCount()) {
						return 1;
					} else if (o1.getCabinsPricesTotalses().get(0).getSalePriceCount() > o2.getCabinsPricesTotalses()
							.get(0).getSalePriceCount())
						return 0;
					return -1;
				}
			});
		}
	}
	/**
	 * 设置乘客对象价格数据
	 * @param passengerTypePricesTotal
	 * @param formula 
	 */
	public static void setPassengerTypePricesTotal(PassengerTypePricesTotal passengerTypePricesTotal,FormulaParameters formula){
		passengerTypePricesTotal.setFavorable(formula.getFare().add(formula.getTax()).subtract(passengerTypePricesTotal.getSalePrice()));//优惠.=票面价+税费-售价
		passengerTypePricesTotal.setSaleRebate(formula.getSaleRebate());//返点
		passengerTypePricesTotal.setAgencyFee(formula.getAgencyFee());//代理费率，小于等于1的2位小数，0.01表示一个点.
		passengerTypePricesTotal.setBrokerage(formula.getBrokerage());//手续费，5，表示￥5.
	}
}

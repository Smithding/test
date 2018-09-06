package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;

import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.formula.FormulaParameters;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightPolicy;

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
	public static QueryIBEDetail fligthCalculate(QueryIBEDetail queryIBEDetail, List<FlightPolicy> policyList, int formula) {
		// 单程
		if (queryIBEDetail.getLegType().intValue() == 1) {
			IftPolicy policy = new IftPolicy();// 单程只会有一条政策
			if(!CollectionUtils.isEmpty(policyList)){
				 policy = policyList.get(0).getIftPolicy();// 单程只会有一条政策
			}
			for (CabinsPricesTotals cabins : queryIBEDetail.getCabinsPricesTotalses()) {
				List<PassengerTypePricesTotal> passengerTypePricesTotals = cabins.getPassengerTypePricesTotals();
				for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {// 乘客价格信息
					FormulaParameters formulaP = new FormulaParameters();
					if (policy==null||(queryIBEDetail.getIsShare()!=null&&queryIBEDetail.getIsShare())) {// 是否直飞/或者不是不包含共享航程
						 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
					}else{//包含共享段
						if(policy.getShareIsSuitAirline()){//是否指定航司给全部奖励
							for (Flight flight : queryIBEDetail.getFlights()) {
								String codeShare = flight.getCodeshare()!=null?flight.getCodeshare().substring(0, 2):"";
								if(codeShare!=""&&policy.getShareSuitAirline().contains(codeShare)){//政策指定的共享航司是否包含航班查询中的共享航司
									 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
								}
							}
						}else{
							if(policy.getShareRewardType().intValue()==1){//1全程无奖励
								 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
								 formulaP.setSaleRebate(new BigDecimal(0));//重新设置返点为0
							}else if(policy.getShareRewardType().intValue()==2){//2全程指定奖励 shareLegReward
								 formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
								 formulaP.setSaleRebate(policy.getShareAssignReward());//设置指定奖励
							}else if(policy.getShareRewardType().intValue()==3){//3共享段无奖励
								formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
								formulaP.setShareRebate(new BigDecimal(0));//设置指定奖励
								formulaP.setIsShare(true);
							}else if(policy.getShareRewardType().intValue()==4){//4共享段指定奖励 shareLegReward
								formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
								formulaP.setShareRebate(policy.getShareLegReward());//设置指定奖励
								formulaP.setIsShare(true);
							}else if(policy.getShareRewardType().intValue()==5){//5给全部奖励
								formulaP = FormulaUtils.getFormula(passengerTypePricesTotal, policy,queryIBEDetail.getMileages());
							}
						}
					}
					switch (formula) {
					case 1:
						// 公式2:票价*（1-代理费率）*（1-返点）+ 税款 + 手续费-直减费用(往返直减)
						formulaP = FormulaUtils.formulaMethod2(formulaP);
						break;
					default:
						// 计奖的部分*(1-代理费率-返点)+无奖的部分*(1-代理费率)+税费+手续费-直减费用
						formulaP = FormulaUtils.formulaMethod1(formulaP);
					}
					passengerTypePricesTotal.setSalePrice(formulaP.getSalePrice().setScale(0, BigDecimal.ROUND_UP));//销售价格
					passengerTypePricesTotal.setAwardPrice(formulaP.getAwardPrice());//及奖价格
					setPassengerTypePricesTotal(passengerTypePricesTotal,formulaP);
				}
				//cabins.setFareCount(fareCount);
			}
		}else{
			if(policyList.size()>1){//当只有一条政策的情况
				
			}else{//当有两条政策的情况
				
			}
		}
		return queryIBEDetail;
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

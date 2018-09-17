package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;

import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.formula.FormulaParameters;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.Mileage;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbe.entity.NucFareInfo;

public class FormulaUtils {
	protected static final Logger logerr = LogManager.getLogger(FormulaUtils.class);
	/**
	 * 公式1:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 +
	 * 手续费-直减费用(往返直减)
	 */
	public static BigDecimal formulaPrice1(FormulaParameters formulaParameters) {
		BigDecimal salePrice = formulaParameters.getAwardPrice()
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())).multiply(
						new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))))
				.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
		if (formulaParameters.getFlightType().intValue() == 1)//设置直减价格
			salePrice.subtract(formulaParameters.getOneWayPrivilege());
		else
			salePrice.subtract(formulaParameters.getRoundTripPrivilege());
		return salePrice;
	}
	/**
	 * 公式2:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	public static BigDecimal formulaPrice2(FormulaParameters formulaParameters) {
		BigDecimal salePrice = formulaParameters.getAwardPrice()
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
						.subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())).multiply(
						new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))))
				.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
		if (formulaParameters.getFlightType().intValue() == 1)//设置直减价格
			salePrice.subtract(formulaParameters.getOneWayPrivilege());
		else
			salePrice.subtract(formulaParameters.getRoundTripPrivilege());
		return salePrice;
	}

	/**
	 * 单程公式1:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters formulaMethod1(FormulaParameters formulaParameters) {
		BigDecimal salePrice = new BigDecimal(0);
		// 当包含共享段航程计算价格，当没有里程按整段计算，因为没有里程拆分不了价格(暂时只有PNR导入获取不到里程)
		if (formulaParameters.getIsShare()!=null&&formulaParameters.getIsShare()&&!CollectionUtils.isEmpty(formulaParameters.getMileage())) {
				salePrice = sharePrice1(formulaParameters.getMileage().get(0),formulaParameters);
		} else {// 当不包含共享航程的情况计算销售价格
			formulaParameters.setAwardPrice(formulaParameters.getFare());
			salePrice = formulaPrice1(formulaParameters);
		}
		formulaParameters.setSalePrice(salePrice);
		return formulaParameters;
	}
	/**
	 * 单程公式2公式2:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 +
	 * 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters formulaMethod2(FormulaParameters formulaParameters) {
		BigDecimal salePrice = new BigDecimal(0);
		// 当包含共享段航程计算价格，当没有里程按整段计算，因为没有里程拆分不了价格(暂时只有PNR导入获取不到里程)
		if (formulaParameters.getIsShare()!=null&&formulaParameters.getIsShare()&&!CollectionUtils.isEmpty(formulaParameters.getMileage())) {
			salePrice = sharePrice2(formulaParameters.getMileage().get(0), formulaParameters);
		} else {// 当不包含共享航程的情况计算销售价格
			formulaParameters.setAwardPrice(formulaParameters.getFare());// 记奖励价格为票面
			salePrice = formulaPrice2(formulaParameters);
		}
		formulaParameters.setSalePrice(salePrice);
		return formulaParameters;
	}
	/**
	 * 往返1公式2:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 +
	 * 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters towFormulaMethod1(FormulaParameters formulaParameters) {
		BigDecimal salePrice = new BigDecimal(0);
		BigDecimal awardPrice = new BigDecimal(0);
		// 当包含共享段航程计算价格，当没有里程按整段计算，因为没有里程拆分不了价格(暂时只有PNR导入获取不到里程)
		if (formulaParameters.getIsShare()!=null&&formulaParameters.getIsShare()&&!CollectionUtils.isEmpty(formulaParameters.getMileage())) {
			if(!CollectionUtils.isEmpty(formulaParameters.getNucFareInfos())){//当NUC不为空的时候安NUC拆
				//根据NUC拆分往返每段的票面价格
				Map<String,Object> mapPrice = getNucConvertPrice(formulaParameters.getFare(),formulaParameters.getNucFareInfos(),formulaParameters.getMileage());
				for (Mileage mileage : formulaParameters.getMileage()) {//里程
					FormulaParameters parameters = formulaParameters;
					BigDecimal price = new BigDecimal(0);//每段的结算价格相加等于总的计算价格
					if(mileage.getFlightNum()==0){//第一段航程的里程比
						 parameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("oneTicketPrice"))));//第一段的票面
						 price = sharePrice1(mileage, formulaParameters);
					}else{//第二段航程里程比
						 parameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("towTicketPrice"))));//第二段的票面
						 price = sharePrice1(mileage, formulaParameters);
					}
					salePrice = salePrice.add(price);//总的结算价格
					awardPrice = awardPrice.add(formulaParameters.getAwardPrice());
				}
				//计算完成，重新赋值票面价格
				formulaParameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("totalPcie"))));
				formulaParameters.setAwardPrice(awardPrice);
			}else{//或者按里程拆。这种只能适用往返为一条政策的情况，当往返匹配两天政策的情况不适用
				Mileage mileage = getMileage(formulaParameters.getMileage());//把往返的里程相加组合成一个里程对象
				salePrice = sharePrice1(mileage, formulaParameters);
			}
		} else {// 当不包含共享航程的情况计算销售价格
			formulaParameters.setAwardPrice(formulaParameters.getFare());// 记奖励价格为票面
			salePrice = formulaPrice1(formulaParameters);
		}
		salePrice = salePrice.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));//加上基建燃油和开票费用
		formulaParameters.setSalePrice(salePrice);
		return formulaParameters;
	}
	/**
	 * 往返公式2:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 +
	 * 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters towFormulaMethod2(FormulaParameters formulaParameters) {
		BigDecimal salePrice = new BigDecimal(0);
		BigDecimal awardPrice = new BigDecimal(0);
		// 当包含共享段航程计算价格，当没有里程按整段计算，因为没有里程拆分不了价格(暂时只有PNR导入获取不到里程)
		if (formulaParameters.getIsShare()!=null&&formulaParameters.getIsShare()&&!CollectionUtils.isEmpty(formulaParameters.getMileage())) {
			if(!CollectionUtils.isEmpty(formulaParameters.getNucFareInfos())){//当NUC不为空的时候安NUC拆
				//根据NUC拆分往返每段的票面价格
				Map<String,Object> mapPrice = getNucConvertPrice(formulaParameters.getFare(),formulaParameters.getNucFareInfos(),formulaParameters.getMileage());
				for (Mileage mileage : formulaParameters.getMileage()) {//里程
					FormulaParameters parameters = formulaParameters;
					BigDecimal price = new BigDecimal(0);//每段的结算价格相加等于总的计算价格
					if(mileage.getFlightNum()==0){//第一段航程的里程比
						 parameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("oneTicketPrice"))));//第一段的票面
						 price = sharePrice2(mileage, formulaParameters);
					}else{//第二段航程里程比
						 parameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("towTicketPrice"))));//第二段的票面
						 price = sharePrice2(mileage, formulaParameters);
					}
					salePrice = salePrice.add(price);//总的结算价格
					awardPrice.add(formulaParameters.getAwardPrice());
				}
				//计算完成，重新赋值票面价格
				formulaParameters.setFare(new BigDecimal(String.valueOf(mapPrice.get("totalPcie"))));
				formulaParameters.setAwardPrice(awardPrice);
			}else{//或者按里程拆。这种只能适用往返为一条政策的情况，当往返匹配两天政策的情况不适用
				Mileage mileage = getMileage(formulaParameters.getMileage());//把往返的里程相加组合成一个里程对象
				salePrice = sharePrice2(mileage, formulaParameters);
			}
		} else {// 当不包含共享航程的情况计算销售价格
			formulaParameters.setAwardPrice(formulaParameters.getFare());// 记奖励价格为票面
			salePrice = formulaPrice2(formulaParameters);
		}
		salePrice = salePrice.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));//加上基建燃油和开票费用
		formulaParameters.setSalePrice(salePrice);
		return formulaParameters;
	}
	/**
	 * 共享航程的价格 公式1:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 * 
	 * @param mileage
	 * @param ticket
	 * @param formulaParameters
	 * @return
	 */
	public static BigDecimal sharePrice1(Mileage mileage, FormulaParameters formulaParameters) {
		BigDecimal ticket = formulaParameters.getFare();// 票面
		BigDecimal shareMileage = new BigDecimal(mileage.getShareMileage());
		BigDecimal totalMileage = new BigDecimal(mileage.getTotalMileage());
		BigDecimal mileageProportion = shareMileage.divide(totalMileage, 2, BigDecimal.ROUND_HALF_UP);// 获取到共享段的航程比例如：（2000/8000） 
		BigDecimal sharePrice = new BigDecimal(ticket.multiply(mileageProportion).doubleValue()).setScale(0,
				BigDecimal.ROUND_UP);// 共享段票面
		BigDecimal notSharePrice = ticket.subtract(sharePrice);// 非共享段票面
		// 计算共享段销售价格
		BigDecimal shareSalePrice = sharePrice
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getShareRebate().divide(new BigDecimal(100))));
		// 计算非共享段销售价格
		BigDecimal notSalePrice = notSharePrice
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))));
		BigDecimal salePrice = shareSalePrice.add(notSalePrice);
		if (formulaParameters.getFlightType().intValue() == 1){
			//设置直减价格
			salePrice.subtract(formulaParameters.getOneWayPrivilege());
			//因为该方法公用，所有只有单程才加上机建燃油，往返因为要调该方法两次。所有不再该方法中计算
			salePrice = salePrice.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
		}else{
			salePrice.subtract(formulaParameters.getRoundTripPrivilege());
		}
		if(formulaParameters.getShareRebate().doubleValue()==0){
			formulaParameters.setAwardPrice(notSharePrice);
		}else{
			formulaParameters.setAwardPrice(ticket);
		}
		logerr.info("共享段票面"+sharePrice+"非共享段票面"+shareSalePrice+"里程比"+JsonUtil.toJson(mileage)+"formulaParameters:"+JsonUtil.toJson(formulaParameters));
		return salePrice;
	}

	/**
	 * 共享段计算价格 公式2:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 +
	 * 手续费-直减费用(往返直减)
	 * 
	 * @param mileage
	 * @param ticket
	 * @param formulaParameters
	 * @return
	 */
	public static BigDecimal sharePrice2(Mileage mileage, FormulaParameters formulaParameters) {
		BigDecimal ticket = formulaParameters.getFare();// 票面
		BigDecimal shareMileage = new BigDecimal(mileage.getShareMileage());
		BigDecimal totalMileage = new BigDecimal(mileage.getTotalMileage());
		BigDecimal mileageProportion = shareMileage.divide(totalMileage, 2, BigDecimal.ROUND_HALF_UP);// 获取到共享段的航程比例如：（2000/8000） 
		BigDecimal sharePrice = new BigDecimal(ticket.multiply(mileageProportion).doubleValue()).setScale(0,BigDecimal.ROUND_UP);// 共享段票面
		BigDecimal notSharePrice = ticket.subtract(sharePrice);// 非共享段票面
		// 计算共享段销售价格
		BigDecimal shareSalePrice = sharePrice
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
						.subtract(formulaParameters.getShareRebate().divide(new BigDecimal(100))));
		// 计算非共享段销售价格
		BigDecimal notSalePrice = notSharePrice
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
						.subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))));
		BigDecimal salePrice = shareSalePrice.add(notSalePrice);
		if (formulaParameters.getFlightType().intValue() == 1){
			//设置直减价格
			salePrice.subtract(formulaParameters.getOneWayPrivilege());
			//因为该方法公用，所有只有单程才加上机建燃油，往返因为要调该方法两次。所有不再该方法中计算
			salePrice = salePrice.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()));
		}else{
			salePrice.subtract(formulaParameters.getRoundTripPrivilege());
		}
		if(formulaParameters.getShareRebate().doubleValue()==0){
			formulaParameters.setAwardPrice(notSharePrice);
		}else{
			formulaParameters.setAwardPrice(ticket);
		}
		logerr.info("共享段票面"+sharePrice+"非共享段票面"+shareSalePrice+"里程比"+JsonUtil.toJson(mileage)+"formulaParameters:"+JsonUtil.toJson(formulaParameters));
		return salePrice;
	}
	/**
	 * 根据NUC拆分往返每段的票面价格
	 * @param fare 总票面
	 * @param nucFareInfos nuc航段价格
	 * @param mileageList 里程比
	 * @return
	 */
	public static Map<String,Object> getNucConvertPrice(BigDecimal fare,List<NucFareInfo> nucFareInfos,List<Mileage> mileageList){
		Map<String,Object> mapPrice = new HashMap<String,Object>();
		double oneTicketPrice = 0;//第一段票面
		for (Mileage mileage : mileageList) {
			for (NucFareInfo fareInfo : nucFareInfos) {
				oneTicketPrice = fareInfo.getNucOfBase();
				if(mileage.getFlightNum()==fareInfo.getRph()&&mileage.getFlightNum()==0){//拆一段的nuc价格，第二段的直接用总票面减去第一段算出来的价格；
					if(fareInfo.getBsrRate()!=0){//根据NUC的汇率汇算成人民币
						oneTicketPrice = oneTicketPrice*fareInfo.getBsrRate();
					}if(fareInfo.getRoeRate()!=0){
						oneTicketPrice = oneTicketPrice*fareInfo.getRoeRate();
					}
				}
				break;
			}
			break;
		}
		oneTicketPrice = Math.ceil(oneTicketPrice);//向上取整
		double towTicketPrice = fare.doubleValue()-oneTicketPrice;//第二段票面
		mapPrice.put("oneTicketPrice", oneTicketPrice);
		mapPrice.put("towTicketPrice", towTicketPrice);
		mapPrice.put("totalPcie", fare);
		return mapPrice;
	}
	/**
	 * 把往返或者多段的里程集合合并成一个里程对象
	 * @param mileageList
	 * @return
	 */
	public static Mileage getMileage(List<Mileage> mileageList){
		Mileage totalMileage = new Mileage();
		for (Mileage mileage : mileageList) {//里程 {
			totalMileage.setTotalMileage(totalMileage.getTotalMileage()+mileage.getTotalMileage());
			totalMileage.setShareMileage(totalMileage.getShareMileage()+mileage.getShareMileage());
			totalMileage.setNotShareMileage(totalMileage.getNotShareMileage()+mileage.getNotShareMileage());
		}
		return totalMileage;
	}

	/**
	 * 封装计算价格实体类
	 * 
	 * @param passengerTypePricesTotal
	 * @param policy
	 * @return
	 */
	public static FormulaParameters getFormula(PassengerTypePricesTotal passengerTypePricesTotal, IftPolicy policy,
			List<Mileage> mileage) {
		FormulaParameters formula = new FormulaParameters();
		try {
			formula.setFare(passengerTypePricesTotal.getFare());// 票面
			BigDecimal agencyFee = policy.getAgencyFee() == null ? new BigDecimal(0) : policy.getAgencyFee();// 代理费
			BigDecimal rewardFee = policy.getRewardFee() == null ? new BigDecimal(0) : policy.getRewardFee();// 下游返点
			BigDecimal openTicketFee = policy.getOpenTicketFee() == null ? new BigDecimal(0)
					: policy.getOpenTicketFee();// 手续费
			BigDecimal oneWayPrivilege = policy.getOneWayPrivilege() == null ? new BigDecimal(0)
					: policy.getOneWayPrivilege();// 单程直减费用
			BigDecimal roundTripPrivilege = policy.getRoundTripPrivilege() == null ? new BigDecimal(0)
					: policy.getRoundTripPrivilege();// 单程直减费用
			if (passengerTypePricesTotal.getPassengerType().equals("CNN")||passengerTypePricesTotal.getPassengerType().equals("CHD")) {
				/** 儿童是否可开无代理费，0否（默认），1是 */
				if (policy.getChdTicketNoAgencyFee() != null && policy.getChdTicketNoAgencyFee())
					formula.setAgencyFee(new BigDecimal(0));// 代理费
				else
					formula.setAgencyFee(agencyFee);// 代理费
				// 儿童票奖励方式，1奖励与成人一致（默认）,2可开无奖励，3不可开，4指定奖励
				int chdRewardType = policy.getChdRewardType() == null ? 5 : policy.getChdRewardType().intValue();
				switch (chdRewardType) {
				case 1:// 1奖励与成人一致（默认
					formula.setSaleRebate(rewardFee);// 下游返点
					break;
				case 2:// 2可开无奖励
					formula.setSaleRebate(new BigDecimal(0));// 下游返点
					break;
				case 4:// 4指定奖励
					formula.setSaleRebate(policy.getChdAssignRewardFee());// 下游返点
					break;
				default:
					formula.setSaleRebate(rewardFee);// 下游返点
					break;
				}
				formula.setBrokerage(openTicketFee.add(
						policy.getChdAddHandlingFee() == null ? new BigDecimal(0) : policy.getChdAddHandlingFee()));// 手续费
				if (policy.getChdPrivilege() != null && policy.getChdPrivilege()) {
					formula.setOneWayPrivilege(new BigDecimal(0));// 单程直减费用
					formula.setRoundTripPrivilege(new BigDecimal(0));// 单程直减费用
				} else {
					formula.setOneWayPrivilege(oneWayPrivilege);// 单程直减费用
					formula.setRoundTripPrivilege(roundTripPrivilege);// 单程直减费用
				}
			} else if (passengerTypePricesTotal.getPassengerType().equals("INF")) {// 婴儿
				formula.setAgencyFee(new BigDecimal(0));// 代理费
				formula.setSaleRebate(new BigDecimal(0));// 下游返点
				formula.setBrokerage(openTicketFee.add(policy.getInfAddHandlingFee() == null ? new BigDecimal(0) : policy.getInfAddHandlingFee()));// 手续费
				formula.setOneWayPrivilege(new BigDecimal(0));// 单程直减费用
				formula.setRoundTripPrivilege(new BigDecimal(0));// 单程直减费用
				formula.setAgencyFee(agencyFee);// 代理费
			} else {// 成人
				formula.setAgencyFee(agencyFee);// 代理费
				formula.setSaleRebate(rewardFee);// 下游返点
				formula.setBrokerage(openTicketFee);// 手续费
				formula.setOneWayPrivilege(oneWayPrivilege);// 单程直减费用
				formula.setRoundTripPrivilege(roundTripPrivilege);// 单程直减费用
			}
			formula.setTax(passengerTypePricesTotal.getTax());// 基建燃油附加费用
			formula.setIsShare(false);// 默认设置为不是（当真的存在共享航班，但是奖励和非共享的航程奖励一致时，该值还是false）
			formula.setShareRebate(new BigDecimal(0));// 默认设置为0
			formula.setMileage(mileage);// 设置航程信息
			formula.setNucFareInfos(passengerTypePricesTotal.getNucFareInfos());//nuc价格
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formula;
	}
	/**
	 * 控润
	 * @param policy 政策
	 * @param passengerType 成人类型
	 * @param formulaParameters 乘客的价格返点信息
	 * @param profit 控润信息
	 * @return
	 */
	public static FormulaParameters getProfit(IftPolicy policy,String passengerType,FormulaParameters formulaParameters,Profit profit) {
		try{
			//当政策不为空的情况对政策进行控润
			if(policy!=null&&!policy.equals("")){
				if(profit!=null&&!profit.equals("")&&profit.getPriceType().intValue()!=1){//价格方式  1 不控 2 控点 
					 if (!passengerType.equals("INF")) {// 婴儿不控润
						 if(profit.getPriceType().intValue()==2){// 2 控点
							 formulaParameters.setProfitRebate(profit.getRebate());//控的返点
							 formulaParameters.setProfitPrice(new BigDecimal(0));//空的返现
							 formulaParameters.setSaleRebate(formulaParameters.getSaleRebate().subtract(profit.getRebate()));//在返点基础扣点控点
							 formulaParameters.setShareRebate(formulaParameters.getShareRebate().subtract(profit.getRebate()));//在共享段返点基础扣点
						 }else if(profit.getPriceType().intValue()==3){//3 控现 把钱加在服务费上
							 formulaParameters.setProfitRebate(new BigDecimal(0));//控的返点
							 formulaParameters.setBrokerage(profit.getRaisePrice());// 控现 把钱加在服务费上
							 formulaParameters.setProfitPrice(formulaParameters.getBrokerage().add(profit.getRaisePrice()));//加钱
						 }
					 }
				}
			}
		}catch(Exception e){
			logerr.error("设置控润异常"+e.getMessage());
			return formulaParameters;
		}
		return formulaParameters;
	}
}

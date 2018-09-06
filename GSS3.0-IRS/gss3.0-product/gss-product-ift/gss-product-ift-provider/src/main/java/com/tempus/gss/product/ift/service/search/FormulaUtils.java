package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.List;

import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.formula.FormulaParameters;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.Mileage;

public class FormulaUtils {
	/**
	 * 公式1:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	/*public static BigDecimal formulaMethod1(FormulaParameters formulaParameters) {
		return formulaParameters.getAwardPrice()
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
						.subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
						.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))))
				.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
				.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
	}*/

	/**
	 * 公式2:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	/*public static BigDecimal formulaMethod2(FormulaParameters formulaParameters) {
		return formulaParameters.getAwardPrice()
				.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
						.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))))
				.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
				.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
	}*/
	/**
	 * 公式1:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters formulaMethod1(FormulaParameters formulaParameters) {
		 if(formulaParameters.getIsShare()){//当包含共享段航程计算价格
			 setSharePrice1(formulaParameters.getMileage().get(0),formulaParameters);
		 }else{//当不包含共享航程的情况计算销售价格
			 formulaParameters.setAwardPrice(formulaParameters.getFare());
			 BigDecimal salePrice = formulaParameters.getAwardPrice()
						.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
								.subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
						.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
								.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))))
						.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
						.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
			 formulaParameters.setSalePrice(salePrice);
		 }
		return formulaParameters;
	}
	/**
	 * 公式2:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 */
	public static FormulaParameters formulaMethod2(FormulaParameters formulaParameters) {
	 if(formulaParameters.getIsShare()){//当包含共享段航程计算价格
		 setSharePrice2(formulaParameters.getMileage().get(0),formulaParameters);
		 }else{//当不包含共享航程的情况计算销售价格
			 formulaParameters.setAwardPrice(formulaParameters.getFare());//记奖励价格为票面
			 BigDecimal salePrice = formulaParameters.getAwardPrice()
						.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
						.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
						.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
								.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))))
						.add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
						.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
			 formulaParameters.setSalePrice(salePrice);
		 }
		 return formulaParameters;
	}
	/**
	 * 计算共享航程的价格 公式1:记奖励*（1-代理费率-返点）+(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 * @param mileage
	 * @param ticket
	 * @param formulaParameters
	 * @return
	 */
    public static BigDecimal setSharePrice1(Mileage mileage,FormulaParameters formulaParameters){
    	BigDecimal ticket = formulaParameters.getFare();//票面
    	BigDecimal mileageProportion =  new BigDecimal(mileage.getShareMileage()/mileage.getTotalMileage()) ;//获取到共享段的航程比例如：（2000/8000）
    	BigDecimal sharePrice = new BigDecimal(ticket.multiply(mileageProportion).doubleValue()).setScale(0, BigDecimal.ROUND_UP);//共享段票面
    	BigDecimal notSharePrice = ticket.subtract(sharePrice);//非共享段票面
    	//计算共享段销售价格
    	BigDecimal shareSalePrice = sharePrice.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
						.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))));
    	//计算非共享段销售价格
    	BigDecimal notSalePrice = notSharePrice.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100))))
				.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100))))
				.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice()))
						.multiply(new BigDecimal(1).subtract(formulaParameters.getSaleRebate().divide(new BigDecimal(100)))));
    	BigDecimal salePrice = shareSalePrice.add(notSalePrice).add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
				.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
    	return salePrice;
    }
    
    /**
	 * 公式2:记奖励*（1-代理费率）*（1-返点）+(票面-记奖励)*(1-代理费率) +(票面-记奖励)*(1-代理费率)+税款 + 手续费-直减费用(往返直减)
	 * @param mileage
	 * @param ticket
	 * @param formulaParameters
	 * @return
	 */
    public static BigDecimal setSharePrice2(Mileage mileage,FormulaParameters formulaParameters){
    	BigDecimal ticket = formulaParameters.getFare();//票面
    	BigDecimal mileageProportion =  new BigDecimal(mileage.getShareMileage()/mileage.getTotalMileage()) ;//获取到共享段的航程比例如：（2000/8000）
    	BigDecimal sharePrice = new BigDecimal(ticket.multiply(mileageProportion).doubleValue()).setScale(0, BigDecimal.ROUND_UP);//共享段票面
    	BigDecimal notSharePrice = ticket.subtract(sharePrice);//非共享段票面
    	//计算共享段销售价格
    	BigDecimal shareSalePrice = sharePrice.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
				.subtract(formulaParameters.getShareRebate().divide(new BigDecimal(100))))
		.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())));
    	//计算非共享段销售价格
    	BigDecimal notSalePrice = notSharePrice.multiply(new BigDecimal(1).subtract(formulaParameters.getAgencyFee().divide(new BigDecimal(100)))
				.subtract(formulaParameters.getShareRebate().divide(new BigDecimal(100))))
		.add((formulaParameters.getFare().subtract(formulaParameters.getAwardPrice())));
    	BigDecimal salePrice = shareSalePrice.add(notSalePrice).add(formulaParameters.getTax().add(formulaParameters.getBrokerage()))
				.subtract(formulaParameters.getOneWayPrivilege()).subtract(formulaParameters.getRoundTripPrivilege());
    	return salePrice;
    }
	/**
	 * 封装计算价格实体类
	 * 
	 * @param passengerTypePricesTotal
	 * @param policy
	 * @return
	 */
	public static FormulaParameters getFormula(PassengerTypePricesTotal passengerTypePricesTotal, IftPolicy policy,List<Mileage> mileage) {
		FormulaParameters formula = new FormulaParameters();
		formula.setFare(passengerTypePricesTotal.getFare());// 票面
		BigDecimal agencyFee = policy.getAgencyFee() == null ? new BigDecimal(0) : policy.getAgencyFee();//代理费
		BigDecimal rewardFee = policy.getRewardFee() == null ? new BigDecimal(0) : policy.getRewardFee();// 下游返点
		BigDecimal openTicketFee = policy.getOpenTicketFee() == null ? new BigDecimal(0) : policy.getOpenTicketFee();// 手续费
		BigDecimal oneWayPrivilege = policy.getOneWayPrivilege() == null ? new BigDecimal(0) : policy.getOneWayPrivilege();// 单程直减费用
		BigDecimal roundTripPrivilege = policy.getRoundTripPrivilege() == null ? new BigDecimal(0) : policy.getRoundTripPrivilege();// 单程直减费用
		if(passengerTypePricesTotal.getPassengerType().equals("CNN")){
			/** 儿童是否可开无代理费，0否（默认），1是 */
			if(policy.getChdTicketNoAgencyFee())
				formula.setAgencyFee( new BigDecimal(0));//代理费
			else
				formula.setAgencyFee(agencyFee);//代理费
			 //儿童票奖励方式，1奖励与成人一致（默认）,2可开无奖励，3不可开，4指定奖励
			 switch (policy.getChdRewardType().intValue()) {
			case 1://1奖励与成人一致（默认
				formula.setSaleRebate(rewardFee);// 下游返点
				break;
			case 2://2可开无奖励
				formula.setSaleRebate(new BigDecimal(0));// 下游返点
				break;
			case 4://4指定奖励
				formula.setSaleRebate(policy.getChdAssignRewardFee());// 下游返点
				break;
			default:
				formula.setSaleRebate(rewardFee);// 下游返点
				break;
			}
			formula.setBrokerage(openTicketFee.add(policy.getChdAddHandlingFee()==null?new BigDecimal(0):policy.getChdAddHandlingFee()));//手续费
            if(policy.getChdPrivilege()){
            	formula.setOneWayPrivilege(new BigDecimal(0));// 单程直减费用
    			formula.setRoundTripPrivilege(new BigDecimal(0));// 单程直减费用
            }else{
            	formula.setOneWayPrivilege(oneWayPrivilege);// 单程直减费用
    			formula.setRoundTripPrivilege(roundTripPrivilege);// 单程直减费用
            }
		}else if(passengerTypePricesTotal.getPassengerType().equals("INF")){//婴儿
			formula.setAgencyFee( new BigDecimal(0));//代理费
			formula.setSaleRebate(new BigDecimal(0));// 下游返点
			formula.setBrokerage(openTicketFee.add(policy.getInfAddHandlingFee()==null?new BigDecimal(0):policy.getInfAddHandlingFee()));//手续费
			formula.setOneWayPrivilege(new BigDecimal(0));// 单程直减费用
			formula.setRoundTripPrivilege(new BigDecimal(0));// 单程直减费用
			formula.setAgencyFee(agencyFee);//代理费
		}else{//成人
			formula.setAgencyFee(agencyFee);//代理费
			formula.setSaleRebate(rewardFee);// 下游返点
			formula.setBrokerage(openTicketFee);//手续费
			formula.setOneWayPrivilege(oneWayPrivilege);// 单程直减费用
			formula.setRoundTripPrivilege(roundTripPrivilege);// 单程直减费用
		}
		formula.setTax(passengerTypePricesTotal.getTax());// 基建燃油附加费用
		formula.setIsShare(false);//默认设置为不是（当真的存在共享航班，但是奖励和非共享的航程奖励一致时，该值还是false）
		formula.setShareRebate(new BigDecimal(0));//默认设置为0
		formula.setMileage(mileage);//设置航程信息
		return formula;
	}
}

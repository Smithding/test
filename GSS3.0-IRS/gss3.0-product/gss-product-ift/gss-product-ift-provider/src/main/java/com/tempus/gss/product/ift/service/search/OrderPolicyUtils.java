package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.Date;

import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftFlightPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyChange;

/**
 * 订单预定页面政策和价格组装工具类
 * @author juan.yin
 *
 */
public class OrderPolicyUtils {
	/**
	 * 订单预定页面价格和政策组装
	 * @param detail
	 * @param policy
	 * @return
	 */
	public static IftPolicyChange getIftPolicyChange(QueryIBEDetail detail ,IftPolicy policy){
		IftPolicyChange iftPolicyChange = new IftPolicyChange();
		IftFlightPolicy flightPolicy = getIftFlightPolicy(policy,new BigDecimal(detail.getCabinsPricesTotalses().get(0).getSalePriceCount()),new BigDecimal(detail.getCabinsPricesTotalses().get(0).getFavorableCount()));
		iftPolicyChange.setFlightPolicy(flightPolicy);
		iftPolicyChange.setCabinsPricesTotalses(detail.getCabinsPricesTotalses());
		return iftPolicyChange;
	}
	/**
	 * 封装国际订单预定页面政策显示
	 * @param policy
	 * @param salePriceCount
	 * @param favorableCount
	 * @return
	 */
	public static IftFlightPolicy getIftFlightPolicy(IftPolicy policy,BigDecimal salePriceCount,BigDecimal favorableCount){
		IftFlightPolicy flightPolicy = new IftFlightPolicy();
		flightPolicy.setPolicyId(policy.getId());
		/**
		 * 订单优惠总金额
		 */
		flightPolicy.setFavorableCount(favorableCount);
		/**
		 * 订单销售总金额
		 */
		flightPolicy.setSalePrice(salePriceCount);
		/** 上游代理费用 */
		flightPolicy.setOriginalAgencyFee(policy.getOriginalAgencyFee());

		/** 上游奖励费用 */
		flightPolicy.setOriginalRewardFee(policy.getOriginalRewardFee());

		/** 下游代理费 */
		flightPolicy.setAgencyFee(policy.getAgencyFee());

		/** 下游奖励费用 */
		flightPolicy.setRewardFee(policy.getRewardFee());

		/** 单程直减费用 */
		flightPolicy.setOneWayPrivilege(policy.getOneWayPrivilege());

		/** 往返直减费用 */
		flightPolicy.setRoundTripPrivilege(policy.getRoundTripPrivilege());

		/** 开票费 */
		flightPolicy.setOpenTicketFee(policy.getOpenTicketFee());
		/** 供应商Office, 多个以"/"分隔 */
		flightPolicy.setSupplierOffice(policy.getSupplierOffice());

		/** 开票Office, 多个以"/"分隔 */
		flightPolicy.setTicketOffice(policy.getTicketOffice());

		/** 儿童票奖励方式，1奖励与成人一致（默认）,2可开无奖励，3不可开，4指定奖励 */
		flightPolicy.setChdRewardType(policy.getChdRewardType());

		/** 儿童指定奖励 */
		flightPolicy.setChdAssignRewardFee(policy.getChdAssignRewardFee());

		/** 儿童是否加收手续费，0否（默认），1是 */
		flightPolicy.setChdIsAddHandlingFee(policy.getChdIsAddHandlingFee());

		/** 儿童加收的手续费 */
		flightPolicy.setChdAddHandlingFee(policy.getChdAddHandlingFee());

		/** 儿童是否可开无代理费，0否（默认），1是 */
		flightPolicy.setChdTicketNoAgencyFee(policy.getChdTicketNoAgencyFee());

		/** 儿童是否不单开，0否（默认），1是 */
		flightPolicy.setChdNotAloneTicket(policy.getChdNotAloneTicket());

		/** 儿童是否不享受直减，0否（默认），1是 */
		flightPolicy.setChdPrivilege(policy.getChdPrivilege());

		/** 婴儿票：1可开无奖励,2不可开 */
		flightPolicy.setInfTicket(policy.getInfTicket());

		/** 婴儿是否加收手续费，0否（默认），1是 */
		flightPolicy.setInfIsAddHandlingFee(policy.getInfIsAddHandlingFee());

		/** 婴儿加收的手续费 */
		flightPolicy.setInfAddHandlingFee(policy.getInfAddHandlingFee());

		/** 共享奖励类型：1全程无奖励，2全程指定奖励，3共享段无奖励，4共享段指定奖励，5给全部奖励 */
		flightPolicy.setShareRewardType(policy.getShareRewardType());

		/** 存在共享航班时全程指定奖励 */
		flightPolicy.setShareAssignReward(policy.getShareAssignReward());

		/** 共享航班航段指定奖励 */
		flightPolicy.setShareLegReward(policy.getShareLegReward());

		/** 共享是否勾选仅和以下航司间共享时给全部奖励 ，0否（默认），1是 */
		flightPolicy.setShareIsSuitAirline(policy.getShareIsSuitAirline());

		/** 共享以下航司间给全部奖励，航司二字代码，多个以"/"分割 */
		flightPolicy.setShareSuitAirline(policy.getShareSuitAirline());
	  
		/** 备注 */
		flightPolicy.setRemark(policy.getRemark());
		
		String day = DateUtil.getDayOfWeek(new Date(), 0);
		if(day.endsWith("6")){
			/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
			flightPolicy.setTicketDate(policy.getSatTicketDate());
		}else if(day.endsWith("7")){
			/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
			flightPolicy.setTicketDate(policy.getSunTicketDate());
		}else{
			/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
			flightPolicy.setTicketDate(policy.getTicketDate());
		}
		return flightPolicy;
	}
}

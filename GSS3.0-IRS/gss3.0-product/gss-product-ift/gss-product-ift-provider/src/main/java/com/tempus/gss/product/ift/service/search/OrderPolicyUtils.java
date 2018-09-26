package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.Profit;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftFlightPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftOrderPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftOrderPrice;
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
	public static IftPolicyChange getIftPolicyChange(QueryIBEDetail detail ,IftPolicy policy,Profit profit){
		// 暂时因为航班数据有问题，一个舱位有两个价格，所以暂时保留第一个舱位价格，其他价格移除掉
		if (detail.getCabinsPricesTotalses().size() > 1) {
			for (int i = 0; i < detail.getCabinsPricesTotalses().size(); i++) {
				if (i > 0) {
					detail.getCabinsPricesTotalses().remove(i);
				}
			}
		}
		IftPolicyChange iftPolicyChange = new IftPolicyChange();
		BigDecimal salePriceCount = new BigDecimal(0);//总销售价格
		BigDecimal far = new BigDecimal(0);//订单总票面
		Map<String,Integer> passengerTypeCount = detail.getCabinsPricesTotalses().get(0).getPassengerTypeCount();//订单乘客人数MPA
		for (PassengerTypePricesTotal priceTotal : detail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
			salePriceCount = salePriceCount.add(priceTotal.getSalePrice().multiply(new BigDecimal(passengerTypeCount.get(priceTotal.getPassengerType()))));
			far = far.add(priceTotal.getFare().multiply(new BigDecimal(passengerTypeCount.get(priceTotal.getPassengerType()))));
		}
		BigDecimal  favorableCount = far.subtract(salePriceCount);//优惠总金额
		IftFlightPolicy flightPolicy = getIftFlightPolicy(policy,salePriceCount,favorableCount,profit);
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
	public static IftFlightPolicy getIftFlightPolicy(IftPolicy policy,BigDecimal salePriceCount,BigDecimal favorableCount,Profit profit){
		IftFlightPolicy flightPolicy = new IftFlightPolicy();
	     BigDecimal profitRebate = new BigDecimal(0);//控润返点
	     BigDecimal profitPrice  = new BigDecimal(0); //控润加价
		if(profit!=null&&profit.getId()!=null){
			if(profit.getPriceType().intValue()==2){
				profitRebate = profit.getRebate();
			}else if(profit.getPriceType().intValue()==3){
				profitPrice = profit.getRaisePrice();
			}
		}
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

		String mixCabinType ="";
        if(policy.getMixCabinType()!=null&&policy.getMixCabinType().contains("2")){
        	mixCabinType = "：票面略低";
        }
		/** 开票Office, 多个以"/"分隔 */
        flightPolicy.setTicketOffice(policy.getTicketOffice()+mixCabinType);
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
         StringBuffer chdStr = new StringBuffer();
		 if(policy.getChdNotAloneTicket()!=null&&policy.getChdNotAloneTicket()){
			 chdStr.append("儿童不单开;");
		 }
		 if(policy.getChdRewardType()!=null){
			switch (policy.getChdRewardType()) {
			case 1:
				 chdStr.append("儿童奖励与成人一致;");
				break;
			case 2:
				chdStr.append("儿童奖可开无奖励;");
				break;
			case 3:
				chdStr.append("儿童奖不可开;");
				break;
			case 4:
				/*chdStr.append("儿童指定奖励:" + policy.getChdAssignRewardFee() == null
						? new BigDecimal(0).subtract(profitRebate).setScale(2, BigDecimal.ROUND_CEILING)
						: policy.getChdAssignRewardFee().subtract(profitRebate).setScale(2, BigDecimal.ROUND_CEILING));*/
				break;
			default:
				 chdStr.append("儿童奖励与成人一致;");
				break;
			}
		 }
		 if(policy.getChdIsAddHandlingFee()!=null&&policy.getChdIsAddHandlingFee()){
			 chdStr.append("儿童加收手续费;"+policy.getChdAddHandlingFee().add(profitPrice));
		 }
		 if(policy.getChdTicketNoAgencyFee()!=null&&policy.getChdTicketNoAgencyFee()){
			 chdStr.append("儿童可开无代理费;");
		 }
		 if(policy.getChdPrivilege()!=null&&policy.getChdPrivilege()){
			 chdStr.append("儿童不享受直减;");
		 }
		 flightPolicy.setChdRemark(chdStr.toString());
		/** 婴儿票：1可开无奖励,2不可开 */
		flightPolicy.setInfTicket(policy.getInfTicket());

		/** 婴儿是否加收手续费，0否（默认），1是 */
		flightPolicy.setInfIsAddHandlingFee(policy.getInfIsAddHandlingFee());

		/** 婴儿加收的手续费 */
		flightPolicy.setInfAddHandlingFee(policy.getInfAddHandlingFee());
		StringBuffer infStr = new StringBuffer();
		if (policy.getInfTicket() != null) {
			infStr.append(policy.getInfTicket().intValue() == 1 ? "可开无奖励;" : "不可开;");
		}
		if (policy.getInfIsAddHandlingFee() != null && policy.getInfIsAddHandlingFee()) {
			infStr.append("婴儿加收的手续费" + policy.getInfAddHandlingFee());
		}
		flightPolicy.setInfRemark(infStr.toString());
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
	    StringBuffer shareStr = new StringBuffer();
	    if(policy.getShareRewardType()!=null){
	    	switch (policy.getShareRewardType()) {
			case 1:
				shareStr.append("全程无奖励");
				break;
			case 2:
				shareStr.append("全程指定奖励:"+(null == policy.getShareAssignReward() ? 0 : policy.getShareAssignReward().subtract(profitRebate).setScale(2, BigDecimal.ROUND_CEILING)));
				break;
			case 3:
				shareStr.append("全程无奖励");
				break;
			case 4:
				shareStr.append("共享段指定奖励:"+(null == policy.getShareLegReward() ? 0 : policy.getShareLegReward().subtract(profitRebate).setScale(2, BigDecimal.ROUND_CEILING)));
				break;
			case 5:
				shareStr.append("给全部奖励");
				break;
			default:
				shareStr.append("全程无奖励");
				break;
			}
	    }
	    if(policy.getShareIsSuitAirline()!=null&&policy.getShareIsSuitAirline()){
	    	shareStr.append("以下航司间共享时给全部奖励"+policy.getShareSuitAirline());
	    }
	    flightPolicy.setShareRemark(shareStr.toString());
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
		flightPolicy.setTicketWay(policy.getTicketWay());
		return flightPolicy;
	}
	/**
	 * 订单政策保存数据封装
	 * @param policy
	 * @param saleId
	 * @param buyId
	 * @param orderPrice订单销售政策数据
	 * @return
	 */
	public static IftOrderPolicy setOrderPolicy(IftPolicy policy,Long saleId,Long buyId,IftOrderPrice orderPrice){
		IftOrderPolicy orderPolicy = new IftOrderPolicy();
		/** 归集单位 */
		orderPolicy.setOwner(policy.getOwner());

		/** 政策ID集合，多个以"/"分割 */
		orderPolicy.setPolicyId(String.valueOf(policy.getId()));

		/** 销售单ID */
		orderPolicy.setSaleId(saleId);

		/** 采购单ID */
		orderPolicy.setBuyId(buyId);

		/** 产品名称（别名） */
		orderPolicy.setName(policy.getName());

		/** 产品来源 */
		orderPolicy.setSource(policy.getSource());

		/** 航程类型, 1:单程(默认); 2:往返; 多个以"/"分割,例：1/2 */
		orderPolicy.setVoyageType(String.valueOf(policy.getVoyageType()));

		/** 票本类型，BSP、B2B、境外电子;多个以"/"分割，例:BSP/B2B */
		orderPolicy.setTicketWay(policy.getTicketWay());

		/** 航空公司编码 */
		orderPolicy.setAirline(policy.getAirline());

		/** 供应商, 可多个, 例如:[{\\\\ */
		orderPolicy.setSuppliers(policy.getSuppliers());

		/** 供应商Office, 多个以"/"分隔 */
		orderPolicy.setSupplierOffice(policy.getSupplierOffice());
		/** 开票Office, 多个以"/"分隔 */
		orderPolicy.setTicketOffice(policy.getTicketOffice());
		/** 上游代理费用 */
		orderPolicy.setOriginalAgencyFee(policy.getOriginalAgencyFee());

		/** 上游奖励费用 */
		orderPolicy.setOriginalRewardFee(policy.getOriginalRewardFee());

		/** 下游代理费 */
		orderPolicy.setAgencyFee(policy.getAgencyFee());

		/** 下游奖励费用 */
		orderPolicy.setRewardFee(policy.getRewardFee());
		for (PassengerTypePricesTotal prices : orderPrice.getPsgPolicyTotal()) {
			/** 控润点数 */
			orderPolicy.setProfitFee(prices.getProfitRebate());
			/** 实际销售奖励费 */
			orderPolicy.setSaleRewardFee(prices.getSaleRebate());
			/**控润的加钱*/
			orderPolicy.setProfitPrice(prices.getAddPrice());
			break;
		}
		/** 单程直减费用 */
		orderPolicy.setOneWayPrivilege(policy.getOneWayPrivilege());

		/** 往返直减费用 */
		orderPolicy.setRoundTripPrivilege(policy.getRoundTripPrivilege());

		/** 开票费 */
		orderPolicy.setOpenTicketFee(policy.getOpenTicketFee());

		/** 是否自动出票，0不自动出（默认），1自动出票 */
		orderPolicy.setAutoTicket(policy.getAutoTicket());

		/** 是否航司普发GDS政策,1是（默认），0否 */
		orderPolicy.setIsGds(policy.getIsGds());

		/** 平时开票时间（星期一至星期五），例:00:00-23:59 */
		orderPolicy.setTicketDate(policy.getTicketDate());

		/** 星期六是否开票,0不开票，1开票（默认） */
		orderPolicy.setSatIsTicket(policy.getSatIsTicket());

		/** 星期六开票时间，例:00:00-23:59 */
		orderPolicy.setSatTicketDate(policy.getSatTicketDate());

		/** 星期日是否开票,0不开票，1开票（默认） */
		orderPolicy.setSunIsTicket(policy.getSunIsTicket());

		/** 星期日开票时间，例:00:00-23:59 */
		orderPolicy.setSunTicketDate(policy.getSunTicketDate());

		/** 儿童票奖励方式，1奖励与成人一致（默认）,2可开无奖励，3不可开，4指定奖励 */
		orderPolicy.setChdRewardType(policy.getChdRewardType());

		/** 儿童指定奖励 */
		orderPolicy.setChdAssignRewardFee(policy.getChdAssignRewardFee());

		/** 儿童是否加收手续费，0否（默认），1是 */
		orderPolicy.setChdIsAddHandlingFee(policy.getChdIsAddHandlingFee());

		/** 儿童加收的手续费 */
		orderPolicy.setChdAddHandlingFee(policy.getChdAddHandlingFee());

		/** 儿童是否可开无代理费，0否（默认），1是 */
		orderPolicy.setChdTicketNoAgencyFee(policy.getChdTicketNoAgencyFee());

		/** 儿童是否不单开，0否（默认），1是 */
		orderPolicy.setChdNotAloneTicket(policy.getChdNotAloneTicket());

		/** 儿童是否不享受直减，0否（默认），1是 */
		orderPolicy.setChdPrivilege(policy.getChdPrivilege());

		/** 婴儿票：1可开无奖励,2不可开 */
		orderPolicy.setInfTicket(policy.getInfTicket());

		/** 婴儿是否加收手续费，0否（默认），1是 */
		orderPolicy.setInfIsAddHandlingFee(policy.getInfIsAddHandlingFee());

		/** 婴儿加收的手续费 */
		orderPolicy.setInfAddHandlingFee(policy.getInfAddHandlingFee());

		/** PNR出票方式：1无需换编码（默认），2换编码出票，3大编出票（无需换编），4大编出票（需换编），5无位不换出票（外放有位时换编） */
		orderPolicy.setPnrTicketType(policy.getPnrTicketType());

		/** 共享奖励类型：1全程无奖励，2全程指定奖励，3共享段无奖励，4共享段指定奖励，5给全部奖励 */
		orderPolicy.setShareRewardType(policy.getShareRewardType());

		/** 存在共享航班时全程指定奖励 */
		orderPolicy.setShareAssignReward(policy.getAgencyFee());

		/** 共享航班航段指定奖励 */
		orderPolicy.setShareLegReward(policy.getAgencyFee());

		/** 共享是否勾选仅和以下航司间共享时给全部奖励 ，0否（默认），1是 */
		orderPolicy.setShareIsSuitAirline(policy.getShareIsSuitAirline());

		/** 共享以下航司间给全部奖励，航司二字代码，多个以"/"分割 */
		orderPolicy.setShareSuitAirline(policy.getShareSuitAirline());

		/** 出票备注 */
		orderPolicy.setTicketRemarks("");

		/** 备注 */
		orderPolicy.setRemark(policy.getRemark());

		return orderPolicy;
	}
}

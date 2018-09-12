package com.tempus.gss.product.ift.service.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;

/**
 * 
 * <pre>
 * <b>国际政策过滤政策规则工具.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月5日 上午11:30:29
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月5日 上午11:30:29    cz
 *         new file.
 * </pre>
 */
public class IftPolicyRuleUtils {
	
	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(IftPolicyRuleUtils.class);
	
	/**
	 * 检查产品是否符合航司
	 * 
	 * @param policy  待匹配的政策
	 * @param airlines  航司集合
	 * @return
	 */
	public boolean matcheAirline(IftPolicy policy, List<String> airlines) {
		boolean result = true;
		for(String airline : airlines){
			result = policy.getAirline().equals(airline) || (StringUtils.isNotBlank(policy.getSuitAirline()) && policy.getSuitAirline().contains(airline));
			if(!result){
				break;
			}
		}
		this.log(policy,result,"检查产品是否符合航司[matcheAirline]未通过");
		return result;
	}
	
	/**
	 * 检查产品是否适用该航班去程舱位
	 * 
	 * @param policy  待匹配的政策
	 * @param cabins  舱位集合
	 * @return
	 */
	public boolean matcheDepartCabin(IftPolicy policy, List<String> cabins) {
		boolean matcheResult = true;
		String[] policyCabins = policy.getDepartCabin().split(",");
		for(String cabin : cabins){
			boolean result = false;
			for(String policyCabin : policyCabins){
				if(policyCabin.equals(cabin)){
					result = true;
					break;
				}
			}
			if(!result){
				matcheResult = false;
				break;
			}
		}
		this.log(policy,matcheResult,"检查产品是否适用该航班去程舱位[matcheDepartCabin]未通过");
		return matcheResult;
	}
	
	/**
	 * 检查产品是否适用该航班中转舱位
	 * 
	 * @param policy  待匹配的政策
	 * @param cabins  舱位集合
	 * @return
	 */
	public boolean matcheTransitCabin(IftPolicy policy, List<String> cabins) {
		boolean matcheResult = true;
		if(StringUtils.isNotBlank(policy.getTransitCabin()) && CollectionUtils.isNotEmpty(cabins)){
			String[] policyCabins = policy.getTransitCabin().split(",");
			for(String cabin : cabins){
				boolean result = false;
				for(String policyCabin : policyCabins){
					if(policyCabin.equals(cabin)){
						result = true;
						break;
					}
				}
				if(!result){
					matcheResult = false;
					break;
				}
			}
		}
		this.log(policy,matcheResult,"检查产品是否适用该航班中转舱位[matcheTransitCabin]未通过");
		return matcheResult;
	}
	
	/**
	 * 检查往返返回机场匹配
	 * 
	 * @param policy  待匹配的政策
	 * @param query  查询政策信息
	 * @return
	 */
	public boolean matcheRtnAirport(IftPolicy policy, FlightQuery query) {
		boolean result = true;
		if((2 == policy.getVoyageType() || 3 == policy.getVoyageType()) && null != policy.getRoundTripAirportType()){
			result = false;
			//1政策始发机场范围已经在sql中过滤
			if(2 == policy.getRoundTripAirportType()){//同属洲际
				if(query.getDepartContinent().equals(query.getArriveContinent())){
					result = true;
				}
			}else if(3 == policy.getRoundTripAirportType()){//必须返回行程出发机场
				if(query.getDepartAirport().equals(query.getArriveAirport())){
					result = true;
				}
			}else if(4 == policy.getRoundTripAirportType()){//指定返回国家或机场范围
				if(StringUtils.isNotBlank(policy.getRoundTripAirport()) 
					&& (policy.getRoundTripAirport().contains(query.getArriveAirport()) || policy.getRoundTripAirport().contains(query.getArriveCountry()))){
					result = true;
				}
			}
		}
		this.log(policy,result,"检查往返返回机场匹配[matcheRtnAirport]未通过");
		return result;
	}
	
	/**
	 * 检查不适用转机机场
	 * 
	 * @param policy  待匹配的政策
	 * @param transitAirports 转机机场集合
	 * @return
	 */
	public boolean matcheNotSuitTransitAirport(IftPolicy policy, List<String> transitAirports) {
		boolean result = true;
		if(null != policy.getIsNotsuitTransferAirport() && policy.getIsNotsuitTransferAirport()){
			if(StringUtils.isNotBlank(policy.getNotsuitTransferAirport())){
				for(String transitAirport : transitAirports){
					if(policy.getNotsuitTransferAirport().contains(transitAirport)){
						result = false;
						break;
					}
				}
			}
		}
		this.log(policy,result,"检查不适用转机机场[matcheNotSuitTransitAirport]未通过");
		return result;
	}
	
	/**
	 * 检查目的地缺口程
	 * 
	 * @param policy  待匹配的政策
	 * @param isArnk  航线是否缺口
	 * @return
	 */
	public boolean matcheArnk(IftPolicy policy, boolean isArnk) {
		boolean result = true;
		if(null != policy.getArnkType() && 3 == policy.getArnkType()){
			if(isArnk){
				result = false;
			}
		}
		this.log(policy,result,"检查目的地缺口程[matcheArnk]未通过");
		return result;
	}
	
	/**
	 * 检查乘客类型不适用
	 * 
	 * @param policy  待匹配的政策
	 * @param passengers  乘客集合
	 * @return
	 */
	public boolean matchePassengerType(IftPolicy policy, List<Passenger> passengers) {
		boolean result = true;
		//TODO 未知乘客类型，后实现
		this.log(policy,result,"检查乘客类型不适用[matchePassengerType]未通过");
		return result;
	}
	
	/**
	 * 检查航程类型不适用
	 * 
	 * @param policy  待匹配的政策
	 * @param borders 航程集合
	 * @return
	 */
	public boolean matcheFlightType(IftPolicy policy, ArrayList<String> borders) {
		boolean result = true;
		if(borders.size() > 1 && StringUtils.isNotBlank(policy.getNotsuitPassengerType())){
			
		}
		this.log(policy,result,"检查航程类型不适用[matcheFlightType]未通过");
		return result;
	}
	
	/**
	 * 检查运价基础
	 * 
	 * @param policy  待匹配的政策
	 * @param fareBasis 运价基础
	 * @return
	 */
	public boolean matcheFareBasis(IftPolicy policy, String fareBasis) {
		boolean result = true;
		/* 首先检查不适用的运价基础 */
		if(null != policy.getIsFareIncludeText() && policy.getIsFareIncludeText() && null != policy.getFareIncludeType() && 3 == policy.getFareIncludeType()){
			if(StringUtils.isNotBlank(policy.getFareIncludeText())){
				String[] policyFareBasisList = policy.getFareIncludeText().split("/");
				for(String policyFareBasis : policyFareBasisList){
					if(fareBasis.contains(policyFareBasis)){
						result = false;
						break;
					}
				}
			}
		}
		
		/* 检查适用的运价基础 */
		if(result && null != policy.getIsSuitFareBase() && policy.getIsSuitFareBase()){
			if(StringUtils.isNotBlank(policy.getSuitFareBase())){
				String[] policySuitFareBases = policy.getSuitFareBase().split("/");
				String[] fareBasisList = fareBasis.split("\\+");
				for(int i=0; i < policySuitFareBases.length; i++){
					boolean isHand = false;
					for(String fareBasisStr : fareBasisList){
						if(policySuitFareBases[i].equals(fareBasisStr)){
							isHand = true;
							break;
						}
					}
					if(!isHand){
						result = false;
						break;
					}
				}
			}
		}
		this.log(policy,result,"检查运价基础[matcheFareBasis]未通过");
		return result;
	}
	
	/**
	 * 检查去程适用航班
	 * 
	 * @param policy  待匹配的政策
	 * @param flightNos 去程航班号集合
	 * @return
	 */
	public boolean matcheDepartSuitFlight(IftPolicy policy, List<String> flightNos) {
		boolean result = true;
		if(null != policy.getIsFlySuitFlight() && policy.getIsFlySuitFlight()){
			if(StringUtils.isNotBlank(policy.getFlySuitFlight())){
				for(String flightNo : flightNos){
					if(!policy.getFlySuitFlight().contains(flightNo)){
						result = false;
						break;
					}
				}
			}
		}
		this.log(policy,result,"检查去程适用航班[matcheDepartSuitFlight]未通过");
		return result;
	}
	
	/**
	 * 检查回程适用航班
	 * 
	 * @param policy  待匹配的政策
	 * @param flightNos 回程航班号集合
	 * @return
	 */
	public boolean matcheArriveSuitFlight(IftPolicy policy, List<String> flightNos) {
		boolean result = true;
		if((2 == policy.getVoyageType() || 3 == policy.getVoyageType()) && null != policy.getIsRtnSuitFlight() && policy.getIsRtnSuitFlight()){
			if(StringUtils.isNotBlank(policy.getRtnSuitFlight())){
				for(String flightNo : flightNos){
					if(!policy.getRtnSuitFlight().contains(flightNo)){
						result = false;
						break;
					}
				}
			}
		}
		this.log(policy,result,"检查回程适用航班[matcheArriveSuitFlight]未通过");
		return result;
	}
	
	/**
	 * 检查不适用航班
	 * 
	 * @param policy  待匹配的政策
	 * @param flightNos 航班号集合
	 * @return
	 */
	public boolean matcheNotSuitFlight(IftPolicy policy, List<String> flightNos) {
		boolean result = true;
		if(null != policy.getIsNotSuitFlight() && policy.getIsNotSuitFlight()){
			if(StringUtils.isNotBlank(policy.getNotSuitFlight())){
				for(String flightNo : flightNos){
					if(policy.getNotSuitFlight().contains(flightNo)){
						result = false;
						break;
					}
				}
			}
		}
		this.log(policy,result,"检查不适用航班[matcheNotSuitFlight]未通过");
		return result;
	}
	
	/**
	 * 检查允许混舱1/2RT
	 * 
	 * @param policy 待匹配的政策
	 * @param cabinGrades 航班的舱位等级集合
	 * @return 
	 */
	public boolean matcheMixtrueCabin(IftPolicy policy, List<String> cabinGrades){
		boolean result = true;
		if(null != policy.getIsMixCabin() && policy.getIsMixCabin()){
			String filghtCabinGrade = cabinGrades.get(0);
			for(String cabinGrade : cabinGrades){
				if(!cabinGrade.equals(filghtCabinGrade)){
					result = false;
					break;
				}
			}
		}
		this.log(policy,result,"检查允许混舱1/2RT[matcheMixtrueCabin]未通过");
		return result;
	}
	
	/**
	 * 检查票面范围
	 * 
	 * @param policy 待匹配的政策
	 * @param parpirce 单人总票面
	 * @return 
	 */
	public boolean matcheParpirce(IftPolicy policy, double parpirce){
		boolean result = true;
		if(null != policy.getIsParLimit() && policy.getIsParLimit()){
			if(StringUtils.isNotBlank(policy.getParLimit()) && !"-".equals(policy.getParLimit())){
				String[] limits = policy.getParLimit().split("-");
				if(parpirce < Integer.parseInt(limits[0]) || parpirce > Integer.parseInt(limits[1])){
					result = false;
				}
			}
		}
		this.log(policy,result,"检查票面范围[matcheParpirce]未通过");
		return result;
	}
	
	/**
	 * 检查不适用航线
	 * 
	 * @param policy  待匹配的政策
	 * @param depart  出发机场
	 * @param arrive 到达机场
	 * @return
	 */
	public boolean matchedExclude(IftPolicy policy, String depart, String arrive) {
		boolean result = true;
		if(null != policy.getIsNotSuitRoute() && policy.getIsNotSuitRoute()){
			if(StringUtils.isNotBlank(policy.getNotSuitRoute())){
				if(policy.getNotSuitRoute().contains(depart+arrive)){
					result = false;
				}
			}
		}
		this.log(policy,result,"检查不适用航线[matchedExclude]未通过");
		return result;
	}
	
	
	/**
	 * 国际政策规则匹配日志记录器
	 * 
	 * @param policy 政策信息
	 * @param result 匹配结果
	 * @param content 内容
	 */
	public void log(IftPolicy policy, boolean result, String content){
		if(!result){
			logger.info("政策ID[" + policy.getId() + "]--"+content);
		}
	}
	
	public static void main(String[] args) {
//		String str = "J,F,P,C,D,I,W,S1,S2,S3,Y,B,M,H,U,A,L,E,V,Z,T,R,K,Q,N";
//		String[] cabins = {"S1","W"};
//		System.out.println(StringUtil2.containsAnyString(str, cabins));
		
//		String[] oldimg = "J,F,P,C,D,I,W,S1,S2,S3,Y,B,M,H,U,A,L,E,V,Z,T,R,K,Q,N".split(",");
//		String str = "J,F,P,C,D,I,W,S1,S2,S3,Y,B,M,H,U,A,L,E,V,Z,T,R,K,Q,N";
		
//		IftPolicyRuleUtils utils = new IftPolicyRuleUtils();
//		IftPolicy iftPolicy = new IftPolicy();
//		iftPolicy.setDepartCabin(str);
//		boolean result = utils.matcheDepartCabin(iftPolicy, cabins);
//		System.out.println(result);
		
//		String[] cabins = str.split(",");
//		System.out.println(cabins.length);
//		System.out.println(Arrays.binarySearch(cabins, "K"));
		String str = "J+P+A";
		for(String s : str.split("\\+")){
			System.out.println(s);
		}
	}
}

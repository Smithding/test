package com.tempus.gss.product.ift.service.search;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	 * @param airline  航司
	 * @return
	 */
	public boolean matcheAirline(IftPolicy policy, String airline) {
		boolean result = policy.getAirline().equals(airline) || (StringUtils.isNotBlank(policy.getSuitAirline()) && policy.getSuitAirline().contains(airline));
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
	public boolean matcheDepartCabin(IftPolicy policy, String[] cabins) {
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
	public boolean matcheTransitCabin(IftPolicy policy, String[] cabins) {
		boolean matcheResult = true;
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
	public boolean matcheTransitCabin(IftPolicy policy, FlightQuery query) {
		boolean result = true;
		if((2 == policy.getVoyageType() || 3 == policy.getVoyageType()) && null != policy.getRoundTripAirportType()){
			result = false;
			if(1 == policy.getRoundTripAirportType()){
				
//				if((StringUtils.isNotBlank(policy.getDepartAirport()) && policy.getDepartAirport().contains(query.getArriveAirport())) ||
//						)
			}
		}
		this.log(policy,result,"检查往返返回机场匹配[matcheTransitCabin]未通过");
		return result;
	}
	
	/**
	 * 检查去程适用航班
	 * 
	 * @param policy  待匹配的政策
	 * @param flightNo 去程航班号
	 * @return
	 */
	public boolean matcheDepartSuitFlight(IftPolicy policy, String flightNo) {
		boolean result = true;
		if(null != policy.getIsFlySuitFlight() && policy.getIsFlySuitFlight()){
			if(StringUtils.isNotBlank(policy.getFlySuitFlight())){
				if(!policy.getFlySuitFlight().contains(flightNo)){
					result = false;
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
	 * @param flightNo 回程航班号
	 * @return
	 */
	public boolean matcheArriveSuitFlight(IftPolicy policy, String flightNo) {
		boolean result = true;
		if((2 == policy.getVoyageType() || 3 == policy.getVoyageType()) && null != policy.getIsRtnSuitFlight() && policy.getIsRtnSuitFlight()){
			if(StringUtils.isNotBlank(policy.getRtnSuitFlight())){
				if(!policy.getRtnSuitFlight().contains(flightNo)){
					result = false;
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
	public boolean matcheNotSuitFlight(IftPolicy policy, String[] flightNos) {
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
		String[] cabins = {"S1","W"};
//		System.out.println(StringUtil2.containsAnyString(str, cabins));
		
//		String[] oldimg = "J,F,P,C,D,I,W,S1,S2,S3,Y,B,M,H,U,A,L,E,V,Z,T,R,K,Q,N".split(",");
		String str = "J,F,P,C,D,I,W,S1,S2,S3,Y,B,M,H,U,A,L,E,V,Z,T,R,K,Q,N";
		
		IftPolicyRuleUtils utils = new IftPolicyRuleUtils();
		IftPolicy iftPolicy = new IftPolicy();
		iftPolicy.setDepartCabin(str);
		boolean result = utils.matcheDepartCabin(iftPolicy, cabins);
		System.out.println(result);
		
//		String[] cabins = str.split(",");
//		System.out.println(cabins.length);
//		System.out.println(Arrays.binarySearch(cabins, "K"));

	}
}

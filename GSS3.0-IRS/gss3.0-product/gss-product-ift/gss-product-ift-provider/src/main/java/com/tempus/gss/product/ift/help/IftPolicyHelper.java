package com.tempus.gss.product.ift.help;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.service.policy.IftPolicyServiceImpl;
import com.tempus.gss.product.ift.service.search.IftPolicyRuleUtils;
import com.tempus.gss.util.Collections;
import com.tempus.gss.vo.Agent;
import com.tempus.tbd.entity.Country;
import com.tempus.tbd.service.IAirportService;

/**
 * 
 * <pre>
 * <b>政策匹配辅助工具类.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月8日 上午10:30:42
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月8日 上午10:30:42    cz
 *         new file.
 * </pre>
 */
@Service(value = "ift.IftPolicyHelper")
public class IftPolicyHelper {
	
	@Reference
	private IAirportService airportService;
	
	/** 日志记录器. */
	protected static Logger logger = LogManager.getLogger(IftPolicyServiceImpl.class);
	
	/**
	 * 得到航程类型
	 * 
	 * @param legs 航程信息
	 * @return int 航程类型：1，单程;2，往返;
	 */
	public int getLegType(List<Leg> legs){
		int voyagType = 1;
		if(legs.size() > 1){
			Leg departLeg = legs.get(0);
			Leg arriveLeg = legs.get(legs.size() -1);
			if(departLeg.getDepAirport().equals(arriveLeg.getArrAirport())){
				return voyagType;
			}
		}
		return voyagType;
	}
	
	/**
	 * 组装查询政策条件
	 * 
	 * @param agent 用户信息
	 * @param legs 航段集合
	 * @param passengerNumber 人数
	 * @return FlightQuery 组装好的查询条件
	 */
	public FlightQuery packageQuery(Agent agent, List<Leg> legs, int passengerNumber){
		FlightQuery query = new FlightQuery();
		try {
			int legType = this.getLegType(legs);
			Leg departLeg = legs.get(0);
			Leg arriveLeg = legs.get(legs.size() -1);
			Leg rtnLeg = null;
			if(2 == legType){ //获取回程航段
				for(int i = 0; i < legs.size(); i++){
					if(i > 0){
						if(legs.get(i - 1).getDepAirport().equals(legs.get(i).getArrAirport())){
							rtnLeg = legs.get(i);
							break;
						}
					}
				}
			}
			query.setOwner(agent.getOwner());
			query.setAirline(departLeg.getAirline());// 出票航司
			query.setDepartAirport(departLeg.getDepAirport());// 出发城市
			Country deparcountry = airportService.queryCountryByAirport(departLeg.getDepAirport());// 根据出发城市查询所属的国家
			if (deparcountry != null && !deparcountry.equals("")) {
				query.setDepartContinent(deparcountry.getAreaCode().replace(" ", ""));// 三字码所属州
				query.setDepartCountry(deparcountry.getCountryCode());// 三字码所属国家
				query.setDepartSign(deparcountry.getDomOrInt());//国际I还是国内D
			} else {
				logger.info(departLeg.getDepAirport() + "基础数据获取到城市信息");
			}
			query.setFlyDate(departLeg.getDepTime());// 去程时间
			query.setVoyageType(legType);// 航程类型：1:单程; 2:往返
															// 3:联程.
			if (legType == 2) {
				query.setRtnFlyDate(rtnLeg.getDepTime());// 回程时间
				query.setRtnDepartWeek(Integer.parseInt(DateUtil.getDayOfWeek(query.getRtnFlyDate(), 0)));// 去程星期几
				query.setArriveAirport(rtnLeg.getDepAirport());
				Country airrcountry = airportService.queryCountryByAirport(rtnLeg.getDepAirport());// 根据抵达城市查询所属的国家
				if (airrcountry != null && !airrcountry.equals("")) {
					query.setArriveContinent(airrcountry.getAreaCode().replace(" ", ""));// 三字码所属州
					query.setArriveCountry(airrcountry.getCountryCode());// 三字码所属国家
					query.setArriveSign(airrcountry.getDomOrInt());
				} else {
					logger.info(arriveLeg.getArrAirport() + "基础数据获取到城市信息");
				}
			}else{
				query.setArriveAirport(arriveLeg.getArrAirport());
				Country airrcountry = airportService.queryCountryByAirport(arriveLeg.getArrAirport());// 根据抵达城市查询所属的国家
				if (airrcountry != null && !airrcountry.equals("")) {
					query.setArriveContinent(airrcountry.getAreaCode().replace(" ", ""));// 三字码所属州
					query.setArriveCountry(airrcountry.getCountryCode());// 三字码所属国家
					query.setArriveSign(airrcountry.getDomOrInt());
				} else {
					logger.info(arriveLeg.getArrAirport() + "基础数据获取到城市信息");
				}
			}
			query.setSaleWeek(Integer.parseInt(DateUtil.getDayOfWeek(new Date(), 0)));// 当前星期
			query.setDepartWeek(Integer.parseInt(DateUtil.getDayOfWeek(query.getFlyDate(), 0)));// 去程星期几
			query.setPsgerNum(passengerNumber);// 查询人数
			query.setPsgerEven(query.getPsgerNum() % 2 == 0 ? true : false);// 人数是双数，还上单数
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	/**
	 * 根据政策规则过滤政策(白屏预订过滤政策)
	 * 
	 * @param passengers 乘客信息
	 * @param legs 航段信息
	 * @param pnr 6位编码
	 * @param pnrContext 编码内容
	 * @return List<IftPolicy> 过滤后的政策集合
	 */
	public List<IftPolicy> ruleFilter(List<IftPolicy> iftPolicyList, List<Leg> legs, FlightQuery query, double parPrice){
		ArrayList<String> airlines = new ArrayList<>();//航司集合
		ArrayList<String> cabins = new ArrayList<>(); //舱位集合
		ArrayList<String> flightNos = new ArrayList<>(); //航班号集合
//		ArrayList<String> cabinGrades = new ArrayList<>(); //舱位等级集合
		ArrayList<String> flyFlightNos = new ArrayList<>();//去程航班号集合
		ArrayList<String> rtnFlightNos = new ArrayList<>();//回程航班号集合
		ArrayList<String> transitCabins = new ArrayList<>();//中转舱位集合
		ArrayList<String> transitAirports = new ArrayList<>();//中转机场集合
		
		IftPolicyRuleUtils ruleUtils = new IftPolicyRuleUtils();
		
		if(1 == query.getVoyageType()){//单程
			for(int i = 0; i < legs.size(); i++){
				Leg leg = legs.get(i);
				airlines.add(leg.getAirline());
				cabins.add(leg.getCabin());
				flightNos.add(leg.getFlightNo());
//				cabinGrades[i] = 
				flyFlightNos.add(leg.getFlightNo());
				if(legs.size() > 1 && i > 0){
					transitCabins.add(leg.getCabin());
					transitAirports.add(leg.getDepAirport());
				}
			}
			iftPolicyList = Collections.filter(iftPolicyList, (iftPolicy) -> ruleUtils.matcheAirline(iftPolicy, airlines)
					&& ruleUtils.matcheDepartCabin(iftPolicy, cabins)
					&& ruleUtils.matcheTransitCabin(iftPolicy, transitCabins) 
					&& ruleUtils.matcheNotSuitTransitAirport(iftPolicy, transitAirports)
					&& ruleUtils.matcheDepartSuitFlight(iftPolicy, flightNos)
					&& ruleUtils.matcheNotSuitFlight(iftPolicy, flightNos) 
					&& ruleUtils.matcheMixtrueCabin(iftPolicy, cabins)
					&& ruleUtils.matcheParpirce(iftPolicy, parPrice)
					&& ruleUtils.matchedExclude(iftPolicy, query.getDepartAirport(), query.getArriveAirport()));
		}else{//往返
			ArrayList<Leg> flyLegs = new ArrayList<>(); //去程
			ArrayList<Leg> rtnLegs = new ArrayList<>(); //回程
			boolean startRtn = false;
			for(int i = 0; i < legs.size(); i++){ //遍历获取信息并得到去程以及回航段
				Leg leg = legs.get(i);
				airlines.add(leg.getAirline());
				cabins.add(leg.getCabin());
				flightNos.add(leg.getFlightNo());
//				cabinGrades[i] = 
				if(!startRtn){
					if(legs.get(i - 1).getDepAirport().equals(legs.get(i).getArrAirport())){
						startRtn = true;
					}
					flyLegs.add(leg);
				}else{
					rtnLegs.add(leg);
				}
			}
			if(flyLegs.size() > 1 && rtnLegs.size() > 1){
				for(int i = 0; i < flyLegs.size(); i++){ //遍历去程获取转机信息
					Leg leg = legs.get(i);
					flyFlightNos.add(leg.getFlightNo());
					if(i > 0){
						transitCabins.add(leg.getCabin());
						transitAirports.add(leg.getDepAirport());
					}
				}
				for(int i = 0; i < rtnLegs.size(); i++){//遍历回程获取转机信息
					Leg leg = legs.get(i);
					rtnFlightNos.add(leg.getFlightNo());
					if(i > 0){
						transitCabins.add(leg.getCabin());
						transitAirports.add(leg.getDepAirport());
					}
				}
			}
			iftPolicyList = Collections.filter(iftPolicyList, (iftPolicy) -> ruleUtils.matcheAirline(iftPolicy, airlines)
					&& ruleUtils.matcheDepartCabin(iftPolicy, cabins)
					&& ruleUtils.matcheTransitCabin(iftPolicy, transitCabins) 
					&& ruleUtils.matcheRtnAirport(iftPolicy, query)
					&& ruleUtils.matcheNotSuitTransitAirport(iftPolicy, transitAirports)
					&& ruleUtils.matcheDepartSuitFlight(iftPolicy, flyFlightNos)
					&& ruleUtils.matcheArriveSuitFlight(iftPolicy, rtnFlightNos)
					&& ruleUtils.matcheNotSuitFlight(iftPolicy, flightNos) 
					&& ruleUtils.matcheMixtrueCabin(iftPolicy, cabins)
					&& ruleUtils.matcheParpirce(iftPolicy, parPrice)
					&& ruleUtils.matchedExclude(iftPolicy, query.getDepartAirport(), query.getArriveAirport()));
		}
		return iftPolicyList;
	}
	
	
	/**
	 * 根据政策规则过滤政策(PNR过滤政策)
	 * 
	 * @param passengers 乘客信息
	 * @param legs 航段信息
	 * @param pnr 6位编码
	 * @param pnrContext 编码内容
	 * @return List<IftPolicy> 过滤后的政策集合
	 */
	public List<IftPolicy> ruleFilterByPnr(List<IftPolicy> iftPolicyList, List<Passenger> passengers, List<Leg> legs, FlightQuery query,
			String pnr, String pnrContext){
		ArrayList<String> airlines = new ArrayList<>();//航司集合
		ArrayList<String> cabins = new ArrayList<>(); //舱位集合
		ArrayList<String> flightNos = new ArrayList<>(); //航班号集合
//		ArrayList<String> cabinGrades = new ArrayList<>(); //舱位等级集合
		ArrayList<String> flyFlightNos = new ArrayList<>();//去程航班号集合
		ArrayList<String> rtnFlightNos = new ArrayList<>();//回程航班号集合
		ArrayList<String> transitCabins = new ArrayList<>();//中转舱位集合
		ArrayList<String> transitAirports = new ArrayList<>();//中转机场集合
		
		IftPolicyRuleUtils ruleUtils = new IftPolicyRuleUtils();
		
		if(1 == query.getVoyageType()){//单程
			for(int i = 0; i < legs.size(); i++){
				Leg leg = legs.get(i);
				airlines.add(leg.getAirline());
				cabins.add(leg.getCabin());
				flightNos.add(leg.getFlightNo());
//				cabinGrades[i] = 
				flyFlightNos.add(leg.getFlightNo());
				if(legs.size() > 1 && i > 0){
					transitCabins.add(leg.getCabin());
					transitAirports.add(leg.getDepAirport());
				}
			}
			iftPolicyList = Collections.filter(iftPolicyList, (iftPolicy) -> ruleUtils.matcheAirline(iftPolicy, airlines)
					&& ruleUtils.matcheDepartCabin(iftPolicy, cabins)
					&& ruleUtils.matcheTransitCabin(iftPolicy, transitCabins) 
					&& ruleUtils.matcheNotSuitTransitAirport(iftPolicy, transitAirports)
					&& ruleUtils.matcheDepartSuitFlight(iftPolicy, flightNos)
					&& ruleUtils.matcheNotSuitFlight(iftPolicy, flightNos) 
					&& ruleUtils.matcheMixtrueCabin(iftPolicy, cabins)
					&& ruleUtils.matcheParpirce(iftPolicy, passengers.get(0).getSaleFare().doubleValue())
					&& ruleUtils.matchedExclude(iftPolicy, query.getDepartAirport(), query.getArriveAirport()));
		}else{//往返
			ArrayList<Leg> flyLegs = new ArrayList<>(); //去程
			ArrayList<Leg> rtnLegs = new ArrayList<>(); //回程
			boolean startRtn = false;
			for(int i = 0; i < legs.size(); i++){ //遍历获取信息并得到去程以及回航段
				Leg leg = legs.get(i);
				airlines.add(leg.getAirline());
				cabins.add(leg.getCabin());
				flightNos.add(leg.getFlightNo());
//				cabinGrades[i] = 
				if(!startRtn){
					if(legs.get(i - 1).getDepAirport().equals(legs.get(i).getArrAirport())){
						startRtn = true;
					}
					flyLegs.add(leg);
				}else{
					rtnLegs.add(leg);
				}
			}
			if(flyLegs.size() > 1 && rtnLegs.size() > 1){
				for(int i = 0; i < flyLegs.size(); i++){ //遍历去程获取转机信息
					Leg leg = legs.get(i);
					flyFlightNos.add(leg.getFlightNo());
					if(i > 0){
						transitCabins.add(leg.getCabin());
						transitAirports.add(leg.getDepAirport());
					}
				}
				for(int i = 0; i < rtnLegs.size(); i++){//遍历回程获取转机信息
					Leg leg = legs.get(i);
					rtnFlightNos.add(leg.getFlightNo());
					if(i > 0){
						transitCabins.add(leg.getCabin());
						transitAirports.add(leg.getDepAirport());
					}
				}
			}
			iftPolicyList = Collections.filter(iftPolicyList, (iftPolicy) -> ruleUtils.matcheAirline(iftPolicy, airlines)
					&& ruleUtils.matcheDepartCabin(iftPolicy, cabins)
					&& ruleUtils.matcheTransitCabin(iftPolicy, transitCabins) 
					&& ruleUtils.matcheRtnAirport(iftPolicy, query)
					&& ruleUtils.matcheNotSuitTransitAirport(iftPolicy, transitAirports)
					&& ruleUtils.matcheDepartSuitFlight(iftPolicy, flyFlightNos)
					&& ruleUtils.matcheArriveSuitFlight(iftPolicy, rtnFlightNos)
					&& ruleUtils.matcheNotSuitFlight(iftPolicy, flightNos) 
					&& ruleUtils.matcheMixtrueCabin(iftPolicy, cabins)
					&& ruleUtils.matcheParpirce(iftPolicy, passengers.get(0).getSaleFare().doubleValue())
					&& ruleUtils.matchedExclude(iftPolicy, query.getDepartAirport(), query.getArriveAirport()));
		}
		return iftPolicyList;
	}
}

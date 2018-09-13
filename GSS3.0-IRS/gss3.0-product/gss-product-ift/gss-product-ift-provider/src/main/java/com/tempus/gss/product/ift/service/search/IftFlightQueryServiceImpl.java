package com.tempus.gss.product.ift.service.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.Leg;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.PnrPassenger;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicy;
import com.tempus.gss.product.ift.api.entity.policy.IftPolicyChange;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.policy.IftPolicyService;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;
import com.tempus.gss.product.ift.dao.policy.IftQueryPolicyMapper;
import com.tempus.gss.product.ift.help.IftPolicyHelper;
import com.tempus.gss.util.Collections;
import com.tempus.gss.vo.Agent;
import com.tempus.tbe.entity.AvailableJourney;

/**
 * 
 * <pre>
 * <b>根据航班信息匹配政策接口实现.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月4日 下午2:11:46
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月4日 下午2:11:46    cz
 *         new file.
 * </pre>
 */
@Service
public class IftFlightQueryServiceImpl implements IftFlightQueryService {
	protected static final Logger log = LogManager.getLogger(IftFlightQueryServiceImpl.class);
	@Autowired
	private IftQueryPolicyMapper iftQueryPolicyMapper;
	@Autowired
	private IftFlightQueryUtils fftFlightQueryUtils;
	@Autowired
	private IftPolicyHelper policyHelper;
	
	@Autowired
	private IftPolicyService policyService;
	
	@Override
	public QueryIBEDetail mappingPriceSpec(QueryIBEDetail queryIBEDetail,List<IftPolicy> iftPolicyList,String customerTypeNo, RequestWithActor<FlightQueryRequest> request) {
		
		/* 组装航程信息 */
		List<Leg> legs = new ArrayList<Leg>();
		for (Flight flights : queryIBEDetail.getFlights()) {
			Leg leg = new Leg();
			leg.setAirline(flights.getAirline());//航司
			leg.setFlightNo(flights.getFlightNo());//航班号
			leg.setDepTime(flights.getDepTime());//起飞时间
            leg.setArrTime(flights.getArrTime());//到达时间.
            leg.setCabin(flights.getFlightCabinPriceVos().get(0).getCabin());
            leg.setArrAirport(flights.getArrAirport());//到达机场.
			leg.setDepAirport(flights.getDepAirport());//起点机场.
            leg.setGoBack(flights.getDirection().equals("go")?1:2);//方向标识 go/back
            leg.setStopAirport(flights.getStopOverAirport());//经停机场
            leg.setLegNo(Long.parseLong(String.valueOf(flights.getFlightNum())));
            legs.add(leg);
		}
		int adtCount = request.getEntity().getAdultCount();
		int chdCount = request.getEntity().getChildCount();
		int infCount = request.getEntity().getInfantCount();
		FlightQuery query = policyHelper.packageQuery(request.getAgent(), legs, queryIBEDetail.getTicketAirline(), adtCount, chdCount, infCount);
		
		/* 航班查询政策时，先过滤一次航司 */
		IftPolicyRuleUtils iftPolicyRuleUtils = new IftPolicyRuleUtils();
		iftPolicyList = Collections.filter(iftPolicyList, (iftPolicy) -> iftPolicyRuleUtils.matcheAirline(iftPolicy, query.getAirline()));
		
		iftPolicyList = policyHelper.ruleFilter(iftPolicyList, legs, query, 
				queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals().get(0).getFareBasis(), 
				queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals().get(0).getFare().doubleValue());
		QueryIBEDetail detail  = CalculatePriceUtils.fligthCalculate(queryIBEDetail,iftPolicyList, 1);
		return detail;
	}
	@Override
	public List<IftPolicy> matcPolicy(RequestWithActor<FlightQueryRequest> request) {
		if(request.getEntity().getJson()!=null&&!request.getEntity().getJson().equals("")){//当json不为空的时候，是点更多舱位查询航班数据
			AvailableJourney availableJourney = JSONObject.parseObject(request.getEntity().getJson(), AvailableJourney.class);
			FlightQueryRequest flightQueryRequest = request.getEntity();
			flightQueryRequest.setAirline(availableJourney.getOdOption().get(0).getFlight().get(0).getMarketingAirline());//出票航司
			request.setEntity(flightQueryRequest);
		}
		FlightQuery query = fftFlightQueryUtils.getFlightQueryParam(request);
		List<IftPolicy> iftPolicyList = iftQueryPolicyMapper.query(query);
		return iftPolicyList;
	}
	@Override
	public List<IftPolicyChange> orderPolicy(Agent agent,QueryIBEDetail queryIBEDetail, int adtNumber, int chdNumber, int infNumber) {
		/* 组装航程信息 */
		List<Leg> legs = new ArrayList<Leg>();
		for (Flight flights : queryIBEDetail.getFlights()) {
			Leg leg = new Leg();
			leg.setAirline(flights.getAirline());//航司
			leg.setFlightNo(flights.getFlightNo());//航班号
			leg.setDepTime(flights.getDepTime());//起飞时间
            leg.setArrTime(flights.getArrTime());//到达时间.
            leg.setCabin(flights.getFlightCabinPriceVos().get(0).getCabin());
            leg.setArrAirport(flights.getArrAirport());//到达机场.
			leg.setDepAirport(flights.getDepAirport());//起点机场.
            leg.setGoBack(flights.getDirection().equals("go")?1:2);//方向标识 go/back
            leg.setStopAirport(flights.getStopOverAirport());//经停机场
            leg.setLegNo(Long.parseLong(String.valueOf(flights.getFlightNum())));
            legs.add(leg);
		}
		
		List<IftPolicy> iftPolicyList = policyService.getPolicys(agent, legs, queryIBEDetail.getTicketAirline(), queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals().get(0).getFareBasis()
				, queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals().get(0).getFare().doubleValue(), adtNumber, chdNumber, infNumber);
		List<IftPolicyChange> policyChanges = CalculatePriceUtils.orderPolicyCalculate(queryIBEDetail,iftPolicyList,1);
		return policyChanges;
	}
	@Override
	public List<IftPolicyChange> orderPolicyByPnr(Agent agent, QueryIBEDetail queryIBEDetail, String pnr, String pnrContext) {
		
		List<Passenger> passengers = new ArrayList<Passenger>();
		for (PnrPassenger pnrPassenger : queryIBEDetail.getPnrPassengers()) {
			Passenger passenger = new Passenger();
			passenger.setPassengerType(pnrPassenger.getPassengerstype());//乘客类型
			passenger.setPassengerBirth(DateUtil.getDate(pnrPassenger.getPassengerbirthday(), DateUtil.DATAFORMAT_STR));//乘客生日
			passenger.setName(pnrPassenger.getPassengername());//乘客名字
			passenger.setGender(pnrPassenger.getPassengersex());////乘机人性别 ( MR MS  CHD  OTHER)
			passenger.setCertNo(pnrPassenger.getPassengeridentitynumber());//证件号码
			passenger.setCertType(pnrPassenger.getPassengeridentitytype());//证件类型
			passengers.add(passenger);
		}
		
		/* 组装航程信息 */
		List<Leg> legs = new ArrayList<Leg>();
		for (Flight flights : queryIBEDetail.getFlights()) {
			Leg leg = new Leg();
			leg.setAirline(flights.getAirline());//航司
			leg.setFlightNo(flights.getFlightNo());//航班号
			leg.setDepTime(flights.getDepTime());//起飞时间
            leg.setArrTime(flights.getArrTime());//到达时间.
            leg.setCabin(flights.getFlightCabinPriceVos().get(0).getCabin());
            leg.setArrAirport(flights.getArrAirport());//到达机场.
			leg.setDepAirport(flights.getDepAirport());//起点机场.
            leg.setGoBack(flights.getDirection().equals("go")?1:2);//方向标识 go/back
            leg.setStopAirport(flights.getStopOverAirport());//经停机场
            leg.setLegNo(Long.parseLong(String.valueOf(flights.getFlightNum())));
            legs.add(leg);
		}
		
		List<IftPolicy> iftPolicyList = policyService.getPolicysByPnr(agent, passengers, legs, queryIBEDetail.getTicketAirline(),  queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals().get(0).getFareBasis(), pnr, pnrContext);
		List<IftPolicyChange> policyChanges = CalculatePriceUtils.orderPolicyCalculate(queryIBEDetail,iftPolicyList,1);
		return policyChanges;
	}
}

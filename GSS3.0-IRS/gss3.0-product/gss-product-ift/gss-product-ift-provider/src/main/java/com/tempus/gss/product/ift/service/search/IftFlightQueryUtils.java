package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.search.FlightQuery;
import com.tempus.gss.product.ift.api.entity.vo.FlightCabinPriceVo;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.util.Collections;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbd.entity.Country;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.entity.AvailableJourney;
import com.tempus.tbe.entity.FareBase;
import com.tempus.tbe.entity.PsgerFare;
import com.tempus.tbe.entity.PsgerFlight;
import com.tempus.tbe.entity.ShoppingFare;
import com.tempus.tbe.entity.ShoppingFlight;
import com.tempus.tbe.entity.ShoppingOD;
import com.tempus.tbe.entity.ShoppingOutPut;
import com.tempus.tbe.entity.Tax;

@Service(value = "ift.fftFlightQueryUtils")
public class IftFlightQueryUtils {
	protected final transient Logger logerr = LoggerFactory.getLogger(getClass());
	@Reference
	private IAirportService airportService;

	/**
	 * 根据查询白屏航班条件获取政策信息,政策条件封装（调用类：IftFlightQueryServiceImpl.matcPolicy）
	 * 
	 * @param flightQuery
	 * @return
	 */
	public FlightQuery getFlightQueryParam(RequestWithActor<FlightQueryRequest> request) {
		FlightQuery query = new FlightQuery();
		try {
			FlightQueryRequest flightQuery = request.getEntity();
			query.setOwner(request.getAgent().getOwner());
			query.setAirline(query.getAirline());// 出票航司
			query.setDepartAirport(flightQuery.getDepAirport());// 出发城市
			Country deparcountry = airportService.queryCountryByAirport(flightQuery.getDepAirport());// 根据出发城市查询所属的国家
			if (deparcountry != null && !deparcountry.equals("")) {
				query.setDepartContinent(deparcountry.getContinentArea().replace(" ", ""));// 三字码所属州
				query.setDepartCountry(deparcountry.getCountryEName());// 三字码所属国家
			} else {
				logerr.info(flightQuery.getDepAirport() + "基础数据获取到城市信息");
			}
			query.setFlyDate(DateUtil.getDate(flightQuery.getDepDate(), DateUtil.DATAFORMAT_STR));// 去程时间
			query.setVoyageType(flightQuery.getLegType());// 航程类型：1:单程; 2:往返
															// 3:联程.
			if (flightQuery.getLegType().intValue() == 2) {
				query.setRtnFlyDate(DateUtil.getDate(flightQuery.getReturnDate(), DateUtil.DATAFORMAT_STR));// 回程时间
				query.setRtnDepartWeek(Integer.parseInt(DateUtil.getDayOfWeek(query.getRtnFlyDate(), 0)));// 去程星期几
			}
			query.setArriveAirport(flightQuery.getArrAirport());
			Country airrcountry = airportService.queryCountryByAirport(flightQuery.getArrAirport());// 根据抵达城市查询所属的国家
			if (airrcountry != null && !airrcountry.equals("")) {
				query.setArriveContinent(airrcountry.getContinentArea().replace(" ", ""));// 三字码所属州
				query.setArriveCountry(airrcountry.getCountryEName());// 三字码所属国家
			} else {
				logerr.info(flightQuery.getDepAirport() + "基础数据获取到城市信息");
			}
			query.setSaleWeek(Integer.parseInt(DateUtil.getDayOfWeek(new Date(), 0)));// 当前星期
			query.setDepartWeek(Integer.parseInt(DateUtil.getDayOfWeek(query.getFlyDate(), 0)));// 去程星期几
			query.setPsgerNum(flightQuery.getAdultCount() + flightQuery.getChildCount() + flightQuery.getInfantCount());// 查询人数
			query.setPsgerEven(query.getPsgerNum() % 2 == 0 ? true : false);// 人数是双数，还上单数
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query;
	}

	/**
	 * shopping航班查询解析封装对应的参数格式，和政策进行对比筛选
	 *
	 * @param shoppingOutPut
	 * @return List<QueryIBEDetail>
	 */

	public List<QueryIBEDetail> shoppingOutPutConvertQueryIBEDetails(ShoppingOutPut shoppingOutPut)
			throws ParseException {
		SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<QueryIBEDetail> queryIBEDetails = new ArrayList<QueryIBEDetail>();
		try{
		// 行程集合信息 ShoppingOD 中转的航班信息
		// ShoppingOD.ShoppingFlight对象航段信息。（一般可能会有几段航班）
		List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
		for (AvailableJourney availableJourney : availableJourneys) {
			QueryIBEDetail queryIBEDetail = new QueryIBEDetail();
			// 将availableJourney保存在queryIBEDetail中，以便查询更多舱位
			queryIBEDetail.setAvailableJourney(availableJourney);
			queryIBEDetail.setAvailableJourneyJson(JsonUtil.toJson(availableJourney));
			// 票价信息
			ShoppingFare shoppingFare = availableJourney.getFare();
			// 舱位价格信息
			List<CabinsPricesTotals> cabinsPricesTotalses = new ArrayList<CabinsPricesTotals>();
			CabinsPricesTotals cabinsPricesTotals = new CabinsPricesTotals();
			int abinsCount = 0;
			String cabins = "";
			String ticketType = "";
			List<PassengerTypePricesTotal> passengerTypePricesTotals = new ArrayList<PassengerTypePricesTotal>();
			// shoppingFare.getPsgerFares（航班查询列表才有）
			for (PsgerFare psgerFare : shoppingFare.getPsgerFares()) {
				PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
				// 总个行程的票面价格
				passengerTypePricesTotal.setFare(new BigDecimal(psgerFare.getBaseFare().getAmount()));
				// 运价计算横式
				passengerTypePricesTotal.setFareLinear(psgerFare.getFareLinear());
				if (psgerFare.getFareLinear().contains("/it")) {
					ticketType = "IT";
				}
				// 乘客类型ADT成人,CNN儿童,INF婴儿
				passengerTypePricesTotal.setPassengerType(psgerFare.getPsgerInfo().getCode());
				// 运价基础代码 KOBCN
				passengerTypePricesTotal.setFareBasis(psgerFare.getFareBasis());
				BigDecimal taxs = new BigDecimal(0);
				BigDecimal taxxt = new BigDecimal(0);// 所有基建燃油等附加费用之和
				if (null != psgerFare.getTaxs()) {// 当有机建燃油费用的时候
					// 暂时知道五种类型YQ LI CN XT
					// BP；XT=psgerFare.getTaxs()除CN值相加+（CN乘以航班段数）；
					for (Tax tax : psgerFare.getTaxs()) {
						if ("XT".equals(tax.getTaxCode())) {// 所有的附加费用之和
							taxxt = taxxt.add(tax.getAmount());
						} else {// 当没有XT是。把所有的附加类型费用相加等于所有的税费之后
							taxs = taxs.add(tax.getAmount());
						}
					}
				} else {// 当没有机建燃油费用的时候
					double baseFare = 0;// 票面价格
					double tatalFare = 0;// 票面+基建价格（不包含燃油价格）
					if (null != shoppingFare.getBaseFare()) {
						baseFare = shoppingFare.getBaseFare().getAmount();
					} // 每段的票面总和
					if (null != shoppingFare.getTotalFare()) {
						tatalFare = shoppingFare.getTotalFare().getAmount();
					} // 总费用；票面+所有附加费用
					taxs = BigDecimal.valueOf(tatalFare - baseFare).abs();// 所有的附加费用
				}
				if (taxxt.compareTo(new BigDecimal(0)) > 0) {// XT有值，直接获取该字段
					passengerTypePricesTotal.setTax(taxxt);
				} else {
					passengerTypePricesTotal.setTax(taxs);
				}
				passengerTypePricesTotals.add(passengerTypePricesTotal);// 乘客类型价格信息
			}
			cabinsPricesTotalses.add(cabinsPricesTotals);
			// 舱位价格设置
			cabinsPricesTotals.setPassengerTypePricesTotals(passengerTypePricesTotals);
			// 实际出票航司
			queryIBEDetail.setTicketAirline(shoppingFare.getTicketingCarrier());
			// 设置舱位价格
			queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
			// 设置航程类型
			if (availableJourney.getOdOption().size() > 1) {
				queryIBEDetail.setLegType(2);
			} else {
				queryIBEDetail.setLegType(1);
			}
			List<Flight> flightList = new ArrayList<Flight>();
			for (ShoppingOD shoppingOD : availableJourney.getOdOption()) {
				String direction = "";
				if ("tvl".equals(shoppingOD.getDirection())) {// 封装去程信息
					queryIBEDetail.setGoDepAirport(shoppingOD.getOrgCode());// 出发城市
					queryIBEDetail.setGoArrAirport(shoppingOD.getDstCode());// 到达城市
					queryIBEDetail.setGoDepTime(fl.parse(shoppingOD.getDepartureTime()));// 起飞时间
					queryIBEDetail.setGoArrTime(fl.parse(shoppingOD.getArrivalTime()));// 到达时间
					queryIBEDetail.setGoDuration(shoppingOD.getDuration());// 该OD总飞行时间
					if (queryIBEDetail.getFlights().size() > 1) {
						// 是否直飞
						queryIBEDetail.setDirect(false);
					} else {
						// 是否直飞
						queryIBEDetail.setDirect(true);
					}
					direction = "go";
				} else {// 封装回程信息
					queryIBEDetail.setBackDepAirport(shoppingOD.getOrgCode());// 出发城市
					queryIBEDetail.setBackArrAirport(shoppingOD.getDstCode());// 到达城市
					queryIBEDetail.setBackDepTime(fl.parse(shoppingOD.getDepartureTime()));// 起飞时间
					queryIBEDetail.setBackArrTime(fl.parse(shoppingOD.getArrivalTime()));// 到达时间
					queryIBEDetail.setBackDuration(shoppingOD.getDuration());// 该OD总飞行时间
					if (queryIBEDetail.getFlights().size() > 1) {
						// 是否直飞
						queryIBEDetail.setDirect(false);
					} else {
						// 是否直飞
						queryIBEDetail.setDirect(true);
					}
					direction = "back";
				}
				Map<String, String> map = new HashMap<String, String>();
				// 设置乘客类型 od运价基础 类型有：KHOCJ LLAROTC8 QHOCJ KKE00RCJ W1LOCN
				// MLAROTC8 EKE00RCJ K1LOCN K4KRPWQJ LSFWCJ KSFWCJ BOFFCC2
				// MHOCJ U2SRPWQJ MNN7OGDE Q1LOCN NSRWCJ WHXORCCC H2SRPWQJ
				// BLAROTC8 VHXORCCC H1LOCN QLORCCC HJLWCJ BOWCZ1 YOW
				// DLRCB1O MSFWCJ COWD YOWB1 DSFWCJ YNN0OGBA YOWB1 YOW3 COW
				// YNN0OGBA JOWB1
				for (FareBase fareBase : shoppingOD.getFareBases()) {
					map.put(fareBase.getPsgerType(), fareBase.getFareBase());
				}
				// 1不共享 2共享 3SPA（当共享和SPA共存，默认为共享）
				Map<String, String> mapAirCode = new HashMap<String, String>();
				mapAirCode.put(shoppingOD.getFlight().get(0).getMarketingAirline(),
						shoppingOD.getFlight().get(0).getMarketingAirline());
				Map<String,String> gapFlight = new HashMap<String, String>();//验证航程是否缺口
				// 设置每段的具体航程信息
				for (ShoppingFlight shoppingFlight : shoppingOD.getFlight()) {
					Flight flight = new Flight();
					flight.setTicketType(ticketType); // 票证类型
					flight.setDirection(direction);
					flight.setTpm(Integer.parseInt(shoppingFlight.getTPM()));// 设置里程数
					flight.setDepAirport(shoppingFlight.getDepartureAirport());// 出发城市
					flight.setArrAirport(shoppingFlight.getArrivalAirport());// 达到城市
					flight.setDepTerminal(shoppingFlight.getDepterm());// 出发航站楼
					flight.setArrTerminal(shoppingFlight.getArrterm());// 到达航站楼
					flight.setAirline(shoppingFlight.getMarketingAirline());// 航司
					flight.setCodeshare(shoppingFlight.getOpCode() + shoppingFlight.getOpFltNo());// 当有值表示是共享航班，实际的承运航司和航班号
					flight.setFlightNum(shoppingFlight.getRph());// 航段表示，表示第几段
					flight.setEquipment(shoppingFlight.getEquipment());// 机型
					flight.setFlightNo(shoppingFlight.getFlightNumber());// 航班号
					flight.setArrTime(fl.parse(shoppingFlight.getArrivalDate() + " " + shoppingFlight.getArrivalTime()));// 航班到达时间
					flight.setDepTime(fl.parse(shoppingFlight.getDepartureDate() + " " + shoppingFlight.getDepartureTime()));// 航班出发时间
					if (null != shoppingFlight.getStopOvers() && shoppingFlight.getStopOvers().size() > 0) {// 是否有航班经停
						flight.setStopOverAirport(shoppingFlight.getStopOvers().get(0).getAirport());// 经停机场
						flight.setStopOverDuration(shoppingFlight.getStopOvers().get(0).getDuration());// 经停时间，如 1:30表示经停1 小时30 分钟
					}
					//当上一段的抵达不等于下一段的开始标示缺口成
					if(!gapFlight.isEmpty()&&!gapFlight.get("gapFlight").toUpperCase().equals(shoppingFlight.getDepartureAirport().toUpperCase())){
						Country deparcountry = airportService.queryCountryByAirport(shoppingFlight.getDepartureAirport());// 根据出发城市查询所属的国家
						if (deparcountry != null && !deparcountry.equals("")) {
							queryIBEDetail.setGapCountry(deparcountry.getCountryEName());//缺口国家
						} else {
							logerr.info(shoppingFlight.getDepartureAirport() + "基础数据获取到城市信息");
						}
					}else{
						gapFlight.put("gapFlight",shoppingFlight.getDepartureAirport());
					}
					flight.setDuration(shoppingFlight.getDuration());// 飞行时间,如 3:05表示飞行3 小时5 分钟
					List<FlightCabinPriceVo> flightCabinPriceVos = new ArrayList<FlightCabinPriceVo>();
					for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
						// psgerFltInfos": {"baggagePieces": 1,"baggageWeight":
						// 0,"cabinCode": "K", "cabinPref": "ECONOMY",
						// "psgerType": "ADT" }
						for (PsgerFlight psgerFlight : shoppingFlight.getPsgerFltInfos()) {
							if (psgerFlight.getPsgerType().equals(passengerTypePricesTotal.getPassengerType())) {
								FlightCabinPriceVo flightCabinPriceVo = new FlightCabinPriceVo();// 每段乘客的报价内容.
								flightCabinPriceVo.setCabin(psgerFlight.getCabinCode());// 舱位代码
								flightCabinPriceVo.setFareBasisCode(map.get(passengerTypePricesTotal.getPassengerType()));// 乘客类型od运价
								flightCabinPriceVo.setPassengerType(passengerTypePricesTotal.getPassengerType());// 设置乘客类型
								flightCabinPriceVos.add(flightCabinPriceVo);//
								flight.setGrade(psgerFlight.getCabinPref());// 舱位级别 ECONOMY
								flight.setBaggage(psgerFlight.getBaggageWeightUnit());// 行李重量单位，如 K(千克)
							}
						}
					}
					String type = null;
					// flightCabinPriceVos 对象{ "award": false, "cabin":
					// "V","fareBasisCode": "VLAROTC8", "passengerType":
					// "ADT","vipPrice": false }
					for (FlightCabinPriceVo flightCabinPriceVo : flightCabinPriceVos) {
						if (null == type) {
							type = flightCabinPriceVo.getPassengerType();
						}
						// 中转舱位叠加成VV格式
						if (type.equals(flightCabinPriceVo.getPassengerType())) {
							cabins = cabins + flightCabinPriceVo.getCabin();
						}
					}
					// J:9,C:9,D:9,I:9,Y:9,B:9,H:9,K:9,M:9,L:7,V:3,S:5,N:2
					for (String bookDesig : shoppingFlight.getResBookDesigList().split(",")) {
						if (bookDesig.contains("")) {
							// 设置舱位座数量
							if (cabins.contains(bookDesig.split(":")[0])) {
								abinsCount = Integer.parseInt(bookDesig.split(":")[1]);
							}
						}
					}
					flight.setFlightCabinPriceVos(flightCabinPriceVos);
					flightList.add(flight);
				}
				queryIBEDetail.setFlights(flightList);
			}
			cabinsPricesTotals.setCabins(cabins);// 舱位代码
			cabinsPricesTotals.setCabinsCount(abinsCount);// 舱位数量
			queryIBEDetails.add(queryIBEDetail);
		}
		}catch(Exception e){
			logerr.error("shopping航班查询解析封装对应的参数格式，和政策进行对比筛选:shoppingOutPutConvertQueryIBEDetails", e.getMessage());
		}
		return queryIBEDetails;
	}
}

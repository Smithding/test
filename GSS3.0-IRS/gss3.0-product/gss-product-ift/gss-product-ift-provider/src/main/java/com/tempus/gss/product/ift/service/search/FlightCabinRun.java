package com.tempus.gss.product.ift.service.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.entity.AvailableJourney;
import com.tempus.tbe.entity.ConnectionLocation;
import com.tempus.tbe.entity.PassengerTypeQuantity;
import com.tempus.tbe.entity.ShoppingFlight;
import com.tempus.tbe.entity.ShoppingOD;
import com.tempus.tbe.entity.ShoppingOneInput;
import com.tempus.tbe.entity.ShoppingOneOD;
import com.tempus.tbe.entity.ShoppingOutPut;
import com.tempus.tbe.entity.ShoppingSeg;
import com.tempus.tbe.service.IShoppingService;

public class FlightCabinRun implements Runnable {
	/** 日志记录器. */
	protected static final Logger log = LogManager.getLogger(NewQueryServiceImpl.class);
	private RequestWithActor<FlightQueryRequest> flightQueryRequest;
	private IShoppingService shoppingService;
	private List<QueryIBEDetail> queryIBEDetails; 
	private IftFlightQueryUtils iftFlightQueryUtils;
	private String iataNo;
	private String office;
	public FlightCabinRun(RequestWithActor<FlightQueryRequest> flightQueryRequest, IShoppingService shoppingService,IftFlightQueryUtils iftFlightQueryUtils,String iataNo, String office) {
		this.flightQueryRequest = flightQueryRequest;
		this.shoppingService = shoppingService;
		this.iftFlightQueryUtils =iftFlightQueryUtils;
		this.iataNo = iataNo;
		this.office = office;
	}
	public List<QueryIBEDetail> getQueryIBEDetails() {
		return queryIBEDetails;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		FlightQueryRequest flightQuery = flightQueryRequest.getEntity();
		log.info("查询参数：" + JsonUtil.toJson(flightQuery));
		ShoppingOneInput shoppingOneInput = new ShoppingOneInput();
		shoppingOneInput.setIataNo(iataNo);
		shoppingOneInput.setOffice(office);
		List<PassengerTypeQuantity> psger = new ArrayList<PassengerTypeQuantity>();
		if (null != flightQuery.getChildCount() && flightQuery.getChildCount() > 0) {
			psger.add(new PassengerTypeQuantity("CNN", flightQuery.getChildCount()));
		}
		if (null != flightQuery.getAdultCount() && flightQuery.getAdultCount() > 0) {
			psger.add(new PassengerTypeQuantity("ADT", flightQuery.getAdultCount()));
		}
		if (null != flightQuery.getInfantCount() && flightQuery.getInfantCount() > 0) {
			psger.add(new PassengerTypeQuantity("INF", flightQuery.getInfantCount()));
		}

		AvailableJourney availableJourney1 = JSONObject.parseObject(flightQuery.getJson(), AvailableJourney.class);
		List<ShoppingFlight> flight1 = availableJourney1.getOdOption().get(0).getFlight();
		String departureAirport = flight1.get(0).getDepartureAirport();
		String airrAirport = null;
		if (flight1.size() == 1) {
			airrAirport = flight1.get(0).getArrivalAirport();
		} else {
			airrAirport = flight1.get(flight1.size() - 1).getArrivalAirport();
		}
		shoppingOneInput.setPsgers(psger);
		List<ShoppingSeg> segs = new ArrayList<ShoppingSeg>();
		ShoppingSeg shoppingSeg = new ShoppingSeg();
		shoppingSeg.setDepartureDate(flight1.get(0).getDepartureDate());
		shoppingSeg.setDestinationLocation(airrAirport);
		shoppingSeg.setOriginLocation(departureAirport);
		/*Airport depAirport = airportService.queryAirportByCode(departureAirport, "I");
		if (null == depAirport) {
			depAirport = airportService.queryAirportByCode(departureAirport, "D");
		}
		Airport arrAirport = airportService.queryAirportByCode(airrAirport, "I");
		if (null == arrAirport) {
			arrAirport = airportService.queryAirportByCode(airrAirport, "D");
		}*/
		if (null != flightQuery.getTransfers() && flightQuery.getTransfers().size() > 0) {
			List<ConnectionLocation> connectionLocations = new ArrayList<ConnectionLocation>();
			for (String transfer : flightQuery.getTransfers()) {
				connectionLocations.add(new ConnectionLocation(transfer, true));
			}
			shoppingSeg.setConnectionLocations(connectionLocations);
		}
		segs.add(shoppingSeg);
		if (null != flightQuery.getLegType() && 2 == flightQuery.getLegType()) {
			shoppingSeg = new ShoppingSeg();
			shoppingSeg.setDepartureDate(flightQuery.getReturnDate());
			shoppingSeg.setDestinationLocation(flightQuery.getDepAirport());
			shoppingSeg.setOriginLocation(flightQuery.getArrAirport());
			segs.add(shoppingSeg);
		}
		shoppingOneInput.setSegs(segs);
		// 取得页面传过来的json类型的字符出并转化为相应的类
		AvailableJourney availableJourney = JsonUtil.toBean(flightQuery.getJson(),new TypeReference<AvailableJourney>() {});
		// 设置input的OD信息
		List<ShoppingOneOD> shoppingOneODList = new ArrayList<ShoppingOneOD>();
		int rph = 1;
		for (ShoppingOD shoppingOD : availableJourney.getOdOption()) {
			for (ShoppingFlight flight : shoppingOD.getFlight()) {
				if (flight.isCodeshare()) {
					// 如果是共享航班，将原航班和实际承运航班互换
					String airline = flight.getMarketingAirline();
					String flightNum = flight.getFlightNumber();
					String opCode = flight.getOpCode();
					String opFltNo = flight.getOpFltNo();
					flight.setMarketingAirline(opCode);
					flight.setFlightNumber(opFltNo);
					flight.setOpCode(airline);
					flight.setOpFltNo(flightNum);
				}
			}
			ShoppingOneOD sUse = new ShoppingOneOD();
			sUse.setOdDetail(shoppingOD);
			sUse.setOrgCode(shoppingOD.getOrgCode());
			sUse.setDstCode(shoppingOD.getDstCode());
			sUse.setRph(rph);
			shoppingOneODList.add(sUse);
			rph++;
		}
		shoppingOneInput.setOds(shoppingOneODList);
		try {
			ShoppingOutPut shoppingOutPut = shoppingService.shoppingOneI(shoppingOneInput);
			log.info("开始调用shopping数据转换"+JsonUtil.toJson(shoppingOutPut));
			//因为获取更多舱位返回的航班集合里面把共享的航程全转换成实际承运的航司数据，这样影响政策匹配，因此需要把白屏查询带过的航班数据替换现有的航班数据结果集
			if(!CollectionUtils.isEmpty(shoppingOutPut.getAvailableJourneys())){
				for (AvailableJourney journey : shoppingOutPut.getAvailableJourneys()) {//当有几个舱位组合，这就是几条数据
					for (ShoppingOD shoppingOD : journey.getOdOption()) {//获取更多舱位返回的航班集合
						for (ShoppingOD shoppings : availableJourney.getOdOption()) {//白屏查询列表页面带过的航班数据
							if(shoppingOD.getRph()==shoppings.getRph()){
								shoppingOD.setFlight(shoppings.getFlight());
							}
						}
					}
				}
				
				queryIBEDetails = iftFlightQueryUtils.shoppingOutPutConvertQueryIBEDetails(shoppingOutPut);
			}
		} catch (Exception e) {
			log.error("调用shopping接口", e);
		}
	}

}

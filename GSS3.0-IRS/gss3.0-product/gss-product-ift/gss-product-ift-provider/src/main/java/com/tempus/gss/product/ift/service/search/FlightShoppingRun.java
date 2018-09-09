package com.tempus.gss.product.ift.service.search;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbe.entity.AvailableJourney;
import com.tempus.tbe.entity.ConnectionLocation;
import com.tempus.tbe.entity.PassengerTypeQuantity;
import com.tempus.tbe.entity.ShoppingInput;
import com.tempus.tbe.entity.ShoppingOutPut;
import com.tempus.tbe.entity.ShoppingSeg;
import com.tempus.tbe.service.IShoppingService;

/**
 * 白屏列表调用shopping获取航班列表数据
 * 
 * @author juan.yin
 *
 */
public class FlightShoppingRun implements Runnable {

	/** 日志记录器. */
	protected static final Logger log = LogManager.getLogger(NewQueryServiceImpl.class);
	private RequestWithActor<FlightQueryRequest> flightQueryRequest;
	private IShoppingService shoppingService;
	private IftFlightQueryUtils iftFlightQueryUtils;
	private List<QueryIBEDetail> queryIBEDetailList;
	private String iataNo;
	private String office;

	public List<QueryIBEDetail> getQueryIBEDetailList() {
		return queryIBEDetailList;
	}

	public FlightShoppingRun(RequestWithActor<FlightQueryRequest> flightQueryRequest, IShoppingService shoppingService,
			IftFlightQueryUtils iftFlightQueryUtils, String iataNo,
			String office) {
		this.flightQueryRequest = flightQueryRequest;
		this.shoppingService = shoppingService;
		this.iftFlightQueryUtils = iftFlightQueryUtils;
		this.iataNo = iataNo;
		this.office = office;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		FlightQueryRequest flightQuery = flightQueryRequest.getEntity();
		log.info("查询参数：" + JsonUtil.toJson(flightQuery));
		ShoppingInput shoppingInput = new ShoppingInput();
		shoppingInput.setIataNo(iataNo);
		shoppingInput.setOffice(office);
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
		if (StringUtils.isNotBlank(flightQuery.getGrade())) {
			if (flightQuery.getGrade().equals("F")) {
				shoppingInput.setCabinPref("First");
			}
			if (flightQuery.getGrade().equals("C")) {
				shoppingInput.setCabinPref("Business");
			}
			if (flightQuery.getGrade().equals("Y")) {
				shoppingInput.setCabinPref("Economy");
			}
		}
		if (null != flightQuery.getAirline() && !"".equals(flightQuery.getAirline())) {
			shoppingInput.setAirCode(flightQuery.getAirline());
		}
		shoppingInput.setPsger(psger);
		List<ShoppingSeg> segs = new ArrayList<ShoppingSeg>();
		ShoppingSeg shoppingSeg = new ShoppingSeg(flightQuery.getDepDate(), flightQuery.getDepAirport(),
				flightQuery.getArrAirport());
		if (null != flightQuery.getTransfers() && flightQuery.getTransfers().size() > 0) {
			List<ConnectionLocation> connectionLocations = new ArrayList<ConnectionLocation>();
			for (String transfer : flightQuery.getTransfers()) {
				connectionLocations.add(new ConnectionLocation(transfer, true));
			}
			shoppingSeg.setConnectionLocations(connectionLocations);
		}
		segs.add(shoppingSeg);
		if (null != flightQuery.getLegType() && 2 == flightQuery.getLegType()) {
			shoppingSeg = new ShoppingSeg(flightQuery.getReturnDate(), flightQuery.getArrAirport(),
					flightQuery.getDepAirport());
			segs.add(shoppingSeg);
		}
		shoppingInput.setSegs(segs);
		log.info("开始调用shopping接口,入参:{}", JsonUtil.toJson(shoppingInput));
		ShoppingOutPut shoppingOutPut = shoppingService.shoppingI(shoppingInput);
		log.info("完成调用shopping接口" + shoppingOutPut.getShortText());
		List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
		if (null != availableJourneys || availableJourneys.size() > 1) {
			log.info("开始调用shopping数据转换");
			try {
				queryIBEDetailList = iftFlightQueryUtils.shoppingOutPutConvertQueryIBEDetails(shoppingOutPut);
			} catch (ParseException e) {
				log.error("开始调用shopping数据转换", e);
			}
		}
	}
}

package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightCabinPriceVo;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.IQueryCabinsService;
import com.tempus.gss.product.ift.api.service.IQueryService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.NotSupportException;
import com.tempus.tbe.entity.*;
import com.tempus.tbe.service.IShoppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 杨威 on 2017/9/6.
 */
@Service
@EnableAutoConfiguration
public class QueryCabinsServiceImpl implements IQueryCabinsService {
    @Autowired
    private IQueryService queryService;
    @Value("${ift.iataNo}")
    private String iataNo;
    @Value("${ift.office}")
    private String office;
    @Reference
    private IAirportService airportService;
    /*shopping接口服务*/
    @Reference
    private IShoppingService shoppingService;
    
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public List<QueryIBEDetail> query(RequestWithActor<FlightQueryRequest> flightQueryRequest) {
        Page<QueryIBEDetail> pages = new Page<QueryIBEDetail>();
        FlightQueryRequest flightQuery = flightQueryRequest.getEntity();
        log.info("查询参数：" + JsonUtil.toJson(flightQuery));
        Agent agent = flightQueryRequest.getAgent();
        if (null == agent) {
            return null;
        }
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
        
        shoppingOneInput.setPsgers(psger);
        List<ShoppingSeg> segs = new ArrayList<ShoppingSeg>();
        ShoppingSeg shoppingSeg = new ShoppingSeg();
        shoppingSeg.setDepartureDate(flightQuery.getDepDate());
        shoppingSeg.setDestinationLocation(flightQuery.getArrAirport());
        shoppingSeg.setOriginLocation(flightQuery.getDepAirport());
        Airport depAirport = airportService.queryAirportByCode(flightQuery.getDepAirport(), "I");
        if (null == depAirport) {
            depAirport = airportService.queryAirportByCode(flightQuery.getDepAirport(), "D");
        }
        Airport arrAirport = airportService.queryAirportByCode(flightQuery.getArrAirport(), "I");
        if (null == arrAirport) {
            arrAirport = airportService.queryAirportByCode(flightQuery.getArrAirport(), "D");
        }
        if(depAirport.getCountryCode().equals(arrAirport.getCountryCode())) {
            return null;
        }
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
        //取得页面传过来的json类型的字符出并转化为相应的类
        AvailableJourney availableJourney = JsonUtil.toBean(flightQuery.getJson(), new TypeReference<AvailableJourney>() {
        });
        //设置input的OD信息
        List<ShoppingOneOD> shoppingOneODList = new ArrayList<>();
        int rph = 1;
        for (ShoppingOD shoppingOD : availableJourney.getOdOption()) {
            for(ShoppingFlight flight:shoppingOD.getFlight()){
                if(flight.isCodeshare()){
//                    如果是共享航班，将原航班和实际承运航班互换
                    String airline=flight.getMarketingAirline();
                    String flightNum=flight.getFlightNumber();
                    String opCode=flight.getOpCode();
                    String opFltNo=flight.getOpFltNo();
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
        
        log.info("开始调用shopping接口");
        log.info(JsonUtil.toJson(shoppingOneInput));
        ShoppingOutPut shoppingOutPut = null;
        try {
            shoppingOutPut = shoppingService.shoppingOneI(shoppingOneInput);
        } catch (NotSupportException e) {
            e.printStackTrace();
        }
        log.info("完成调用shopping接口"+shoppingOutPut.getShortText());
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        if (null == availableJourneys || availableJourneys.size() < 1) {
            return null;
        }
        List<QueryIBEDetail> queryIBEDetails = new ArrayList<>();
        log.info("开始调用shopping数据转换");
        try {
            queryIBEDetails = dealWithShoppingOut(shoppingOutPut);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("开始匹配政策");
        for(QueryIBEDetail q:queryIBEDetails){
            queryService.mappingPriceSpec(q, flightQuery.getCustomerType(), agent);
                   log.info(JsonUtil.toJson(q));
        }
        return queryIBEDetails;
    }
    
    /**
     * 查询更多舱位  匹配政策  选择要显示的信息
     *
     * @param shoppingOutPut
     * @return
     * @throws ParseException
     */
    @Override
    @Deprecated
    public QueryIBEDetail shoppingOutPutConvertQueryIBEDetail(ShoppingOutPut shoppingOutPut) throws ParseException {
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        QueryIBEDetail queryIBEDetail = new QueryIBEDetail();
        for (AvailableJourney availableJourney : availableJourneys) {
            ShoppingFare shoppingFare = availableJourney.getFare();
            List<CabinsPricesTotals> cabinsPricesTotalses = new ArrayList<CabinsPricesTotals>();
            CabinsPricesTotals cabinsPricesTotals = new CabinsPricesTotals();
            int abinsCount = 0;
            String cabins = "";
            String ticketType = "";
            List<PassengerTypePricesTotal> passengerTypePricesTotals = new ArrayList<PassengerTypePricesTotal>();
            for (PsgerFare psgerFare : shoppingFare.getPsgerFares()) {
                PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
                passengerTypePricesTotal.setFare(new BigDecimal(psgerFare.getBaseFare().getAmount()));
                passengerTypePricesTotal.setFareLinear(psgerFare.getFareLinear());
                passengerTypePricesTotal.setPassengerType(psgerFare.getPsgerInfo().getCode());
                passengerTypePricesTotal.setFareBasis(psgerFare.getFareBasis());
                BigDecimal taxs = new BigDecimal(0);
                BigDecimal taxxt = new BigDecimal(0);
                if (null != psgerFare.getTaxs()) {
                    for (Tax tax : psgerFare.getTaxs()) {
                        if ("XT".equals(tax.getTaxCode())) {
                            taxxt = taxxt.add(tax.getAmount());
                        } else {
                            taxs = taxs.add(tax.getAmount());
                        }
                    }
                } else {
                    double baseFare = 0;
                    double tatalFare = 0;
                    if (null != shoppingFare.getBaseFare()) {baseFare = shoppingFare.getBaseFare().getAmount();}
                    if (null != shoppingFare.getTotalFare()) {tatalFare = shoppingFare.getTotalFare().getAmount();}
                    taxs = BigDecimal.valueOf(baseFare - tatalFare).abs();
                }
                if (taxxt.compareTo(new BigDecimal(0)) > 0) {
                    passengerTypePricesTotal.setTax(taxxt);
                } else {
                    passengerTypePricesTotal.setTax(taxs);
                }
                passengerTypePricesTotals.add(passengerTypePricesTotal);
            }
            cabinsPricesTotalses.add(cabinsPricesTotals);
            cabinsPricesTotals.setPassengerTypePricesTotals(passengerTypePricesTotals);
            queryIBEDetail.setTicketAirline(shoppingFare.getTicketingCarrier());
            queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
            if (availableJourney.getOdOption().size() > 1) {
                queryIBEDetail.setLegType(2);
            } else {
                queryIBEDetail.setLegType(1);
            }
            List<Flight> flightList = new ArrayList<Flight>();
            for (ShoppingOD shoppingOD : availableJourney.getOdOption()) {
                String direction = "";
                if ("tvl".equals(shoppingOD.getDirection())) {//封装去程信息
                    queryIBEDetail.setGoDepAirport(shoppingOD.getOrgCode());
                    queryIBEDetail.setGoArrAirport(shoppingOD.getDstCode());
                    queryIBEDetail.setGoDepTime(fl.parse(shoppingOD.getDepartureTime()));
                    queryIBEDetail.setGoArrTime(fl.parse(shoppingOD.getArrivalTime()));
                    queryIBEDetail.setGoDuration(shoppingOD.getDuration());
                    direction = "go";
                } else {//封装回程信息
                    queryIBEDetail.setBackDepAirport(shoppingOD.getOrgCode());
                    queryIBEDetail.setBackArrAirport(shoppingOD.getDstCode());
                    queryIBEDetail.setBackDepTime(fl.parse(shoppingOD.getDepartureTime()));
                    queryIBEDetail.setBackArrTime(fl.parse(shoppingOD.getArrivalTime()));
                    queryIBEDetail.setBackDuration(shoppingOD.getDuration());
                    direction = "back";
                }
                Map<String, String> map = new HashMap<String, String>();
                for (FareBase fareBase : shoppingOD.getFareBases()) {
                    map.put(fareBase.getPsgerType(), fareBase.getPsgerType());
                }
                for (ShoppingFlight shoppingFlight : shoppingOD.getFlight()) {
                    Flight flight = new Flight();
                    flight.setTicketType(ticketType);
                    flight.setDirection(direction);
                    flight.setTpm(Integer.parseInt(shoppingFlight.getTPM()));
                    flight.setDepAirport(shoppingFlight.getDepartureAirport());
                    flight.setArrAirport(shoppingFlight.getArrivalAirport());
                    flight.setDepTerminal(shoppingFlight.getDepterm());
                    flight.setArrTerminal(shoppingFlight.getArrterm());
                    flight.setAirline(shoppingFlight.getMarketingAirline());
                    flight.setCodeshare(shoppingFlight.getOpCode());
                    flight.setFlightNum(shoppingFlight.getRph());
                    flight.setEquipment(shoppingFlight.getEquipment());
                    flight.setFlightNo(shoppingFlight.getFlightNumber());
                    flight.setArrTime(fl.parse(shoppingFlight.getArrivalDate() + " " + shoppingFlight.getArrivalTime()));
                    flight.setDepTime(fl.parse(shoppingFlight.getDepartureDate() + " " + shoppingFlight.getDepartureTime()));
                    if (null != shoppingFlight.getStopOvers() && shoppingFlight.getStopOvers().size() > 0) {
                        flight.setStopOverAirport(shoppingFlight.getStopOvers().get(0).getAirport());
                        flight.setStopOverDuration(shoppingFlight.getStopOvers().get(0).getDuration());
                    }
                    flight.setDuration(shoppingFlight.getDuration());
                    List<FlightCabinPriceVo> flightCabinPriceVos = new ArrayList<FlightCabinPriceVo>();
                    for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
                        for (PsgerFlight psgerFlight : shoppingFlight.getPsgerFltInfos()) {
                            if (psgerFlight.getPsgerType().equals(passengerTypePricesTotal.getPassengerType())) {
                                FlightCabinPriceVo flightCabinPriceVo = new FlightCabinPriceVo();
                                flightCabinPriceVo.setCabin(psgerFlight.getCabinCode());
                                flightCabinPriceVo.setFareBasisCode(map.get(passengerTypePricesTotal.getPassengerType()));
                                flightCabinPriceVo.setPassengerType(passengerTypePricesTotal.getPassengerType());
                                flightCabinPriceVos.add(flightCabinPriceVo);
                                flight.setGrade(psgerFlight.getCabinPref());
                                flight.setBaggage(psgerFlight.getBaggageWeightUnit());
                            }
                        }
                        
                    }
                    for (FlightCabinPriceVo flightCabinPriceVo : flightCabinPriceVos) {
                        String type = null;
                        if (null == type) {
                            type = flightCabinPriceVo.getPassengerType();
                        }
                        if (type.equals(flightCabinPriceVo.getPassengerType())) {
                            cabins = cabins + flightCabinPriceVo.getCabin();
                        }
                    }
                    for (String bookDesig : shoppingFlight.getResBookDesigList().split(",")) {
                        if (bookDesig.contains("")) {
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
            cabinsPricesTotals.setCabins(cabins);
            cabinsPricesTotals.setCabinsCount(abinsCount);
            queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
            
        }
        
        return queryIBEDetail;
    }
    
    /**
     * 查询更多舱位  匹配政策  选择要显示的信息
     *
     * @param shoppingOutPut
     * @return
     * @throws ParseException
     */
    @Override
    public List<QueryIBEDetail> dealWithShoppingOut(ShoppingOutPut shoppingOutPut) throws ParseException {
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        List<QueryIBEDetail> queryIBEDetailList = new ArrayList<>();
        for (AvailableJourney availableJourney : availableJourneys) {
            QueryIBEDetail queryIBEDetail = new QueryIBEDetail();
            ShoppingFare shoppingFare = availableJourney.getFare();
            List<CabinsPricesTotals> cabinsPricesTotalses = new ArrayList<CabinsPricesTotals>();
            CabinsPricesTotals cabinsPricesTotals = new CabinsPricesTotals();
            int abinsCount = 0;
            String cabins = "";
            String ticketType = "";
            List<PassengerTypePricesTotal> passengerTypePricesTotals = new ArrayList<PassengerTypePricesTotal>();
            for (PsgerFare psgerFare : shoppingFare.getPsgerFares()) {
                PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
                passengerTypePricesTotal.setFare(new BigDecimal(psgerFare.getBaseFare().getAmount()));
                passengerTypePricesTotal.setFareLinear(psgerFare.getFareLinear());
                passengerTypePricesTotal.setPassengerType(psgerFare.getPsgerInfo().getCode());
                passengerTypePricesTotal.setFareBasis(psgerFare.getFareBasis());
                BigDecimal taxs = new BigDecimal(0);
                BigDecimal taxxt = new BigDecimal(0);
                if (null != psgerFare.getTaxs()) {
                    for (Tax tax : psgerFare.getTaxs()) {
                        if ("XT".equals(tax.getTaxCode())) {
                            taxxt = taxxt.add(tax.getAmount());
                        } else {
                            taxs = taxs.add(tax.getAmount());
                        }
                    }
                } else {
                    BigDecimal baseFare = new BigDecimal(0);
                    BigDecimal totalFare = new BigDecimal(0);
                    if (null != shoppingFare.getBaseFare()) {baseFare = new BigDecimal(shoppingFare.getBaseFare().getAmount());}
                    if (null != shoppingFare.getTotalFare()) {totalFare = new BigDecimal(shoppingFare.getTotalFare().getAmount());}
                    taxs =totalFare.subtract(baseFare);
                }
                if (taxxt.compareTo(new BigDecimal(0)) > 0) {
                    passengerTypePricesTotal.setTax(taxxt);
                } else {
                    passengerTypePricesTotal.setTax(taxs);
                }
                passengerTypePricesTotals.add(passengerTypePricesTotal);
            }
            cabinsPricesTotalses.add(cabinsPricesTotals);
            cabinsPricesTotals.setPassengerTypePricesTotals(passengerTypePricesTotals);
            queryIBEDetail.setTicketAirline(shoppingFare.getTicketingCarrier());
            queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
            if (availableJourney.getOdOption().size() > 1) {
                queryIBEDetail.setLegType(2);
            } else {
                queryIBEDetail.setLegType(1);
            }
            List<Flight> flightList = new ArrayList<Flight>();
            for (ShoppingOD shoppingOD : availableJourney.getOdOption()) {
                String direction = "";
                if ("tvl".equals(shoppingOD.getDirection())) {//封装去程信息
                    queryIBEDetail.setGoDepAirport(shoppingOD.getOrgCode());
                    queryIBEDetail.setGoArrAirport(shoppingOD.getDstCode());
                    queryIBEDetail.setGoDepTime(fl.parse(shoppingOD.getDepartureTime()));
                    queryIBEDetail.setGoArrTime(fl.parse(shoppingOD.getArrivalTime()));
                    queryIBEDetail.setGoDuration(shoppingOD.getDuration());
                    direction = "go";
                } else {//封装回程信息
                    queryIBEDetail.setBackDepAirport(shoppingOD.getOrgCode());
                    queryIBEDetail.setBackArrAirport(shoppingOD.getDstCode());
                    queryIBEDetail.setBackDepTime(fl.parse(shoppingOD.getDepartureTime()));
                    queryIBEDetail.setBackArrTime(fl.parse(shoppingOD.getArrivalTime()));
                    queryIBEDetail.setBackDuration(shoppingOD.getDuration());
                    direction = "back";
                }
                Map<String, String> map = new HashMap<String, String>();
                for (FareBase fareBase : shoppingOD.getFareBases()) {
                    map.put(fareBase.getPsgerType(), fareBase.getPsgerType());
                }
                for (ShoppingFlight shoppingFlight : shoppingOD.getFlight()) {
                    Flight flight = new Flight();
                    flight.setTicketType(ticketType);
                    flight.setDirection(direction);
                    flight.setTpm(Integer.parseInt(shoppingFlight.getTPM()));
                    flight.setDepAirport(shoppingFlight.getDepartureAirport());
                    flight.setArrAirport(shoppingFlight.getArrivalAirport());
                    flight.setDepTerminal(shoppingFlight.getDepterm());
                    flight.setArrTerminal(shoppingFlight.getArrterm());
                    flight.setAirline(shoppingFlight.getMarketingAirline());
                    flight.setCodeshare(shoppingFlight.getOpCode());
                    flight.setFlightNum(shoppingFlight.getRph());
                    flight.setEquipment(shoppingFlight.getEquipment());
                    flight.setFlightNo(shoppingFlight.getFlightNumber());
                    flight.setArrTime(fl.parse(shoppingFlight.getArrivalDate() + " " + shoppingFlight.getArrivalTime()));
                    flight.setDepTime(fl.parse(shoppingFlight.getDepartureDate() + " " + shoppingFlight.getDepartureTime()));
                    if (null != shoppingFlight.getStopOvers() && shoppingFlight.getStopOvers().size() > 0) {
                        flight.setStopOverAirport(shoppingFlight.getStopOvers().get(0).getAirport());
                        flight.setStopOverDuration(shoppingFlight.getStopOvers().get(0).getDuration());
                    }
                    flight.setDuration(shoppingFlight.getDuration());
                    List<FlightCabinPriceVo> flightCabinPriceVos = new ArrayList<FlightCabinPriceVo>();
                    for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
                        for (PsgerFlight psgerFlight : shoppingFlight.getPsgerFltInfos()) {
                            if (psgerFlight.getPsgerType().equals(passengerTypePricesTotal.getPassengerType())) {
                                FlightCabinPriceVo flightCabinPriceVo = new FlightCabinPriceVo();
                                flightCabinPriceVo.setCabin(psgerFlight.getCabinCode());
                                flightCabinPriceVo.setFareBasisCode(map.get(passengerTypePricesTotal.getPassengerType()));
                                flightCabinPriceVo.setPassengerType(passengerTypePricesTotal.getPassengerType());
                                flightCabinPriceVos.add(flightCabinPriceVo);
                                flight.setGrade(psgerFlight.getCabinPref());
                                flight.setBaggage(psgerFlight.getBaggageWeightUnit());
                            }
                        }
                        
                    }
                    String type = null;
                    for (FlightCabinPriceVo flightCabinPriceVo : flightCabinPriceVos) {
                        if (null == type) {
                            type = flightCabinPriceVo.getPassengerType();
                        }
                        if (type.equals(flightCabinPriceVo.getPassengerType())) {
                            cabins = cabins + flightCabinPriceVo.getCabin();
                        }
                    }
                    for (String bookDesig : shoppingFlight.getResBookDesigList().split(",")) {
                        if (bookDesig.contains("")) {
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
            cabinsPricesTotals.setCabins(cabins);
            cabinsPricesTotals.setCabinsCount(abinsCount);
            queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
            queryIBEDetailList.add(queryIBEDetail);
        }
        
        return queryIBEDetailList;
    }
}

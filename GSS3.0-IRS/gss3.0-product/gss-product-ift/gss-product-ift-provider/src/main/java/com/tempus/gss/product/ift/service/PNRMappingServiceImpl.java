package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.ops.util.CommenUtil;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.FlightCabinPriceVo;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.product.ift.api.service.IQueryService;
import com.tempus.gss.product.ift.api.service.PNRMappingService;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.NotSupportException;
import com.tempus.tbe.entity.*;
import com.tempus.tbe.service.IFareService;
import com.tempus.tbe.service.IGetPnrService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 杨威 on 2017/9/6.
 */
@Service
public class PNRMappingServiceImpl implements PNRMappingService {
    @Autowired
    private IQueryService queryService;
    @Reference
    public IGetPnrService getPnrService;
    @Reference
    public IFareService fareService;
    @Reference
    IOrderService orderService;
    /**
     * 航班基础数据服务
     */
    @Reference
    private IAirportService airportService;
    @Value("${ift.iataNo}")
    private String iataNo;
    @Value("${ift.office}")
    private String office;
    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public QueryIBEDetail pnrMapping(RequestWithActor<String> flightQueryRequest, String customerType) {
        String flightQuery = flightQueryRequest.getEntity();
        log.info("查询参数：" + JsonUtil.toJson(flightQuery));
        Agent agent = flightQueryRequest.getAgent();
        if (null == agent) {
            return null;
        }
        QueryIBEDetail queryIBEDetail = null;
        try {
            PnrOutPut pnr = getPnrService.getPnr(office, flightQuery);
            log.info("国际机票PNR预定返回原始信息：" + JsonUtil.toJson(pnr));
            queryIBEDetail = getPnrByQueryIBEDetail(pnr);
            getFSI( queryIBEDetail);
            queryService.mappingPriceSpec(queryIBEDetail, customerType, agent);
        } catch (Exception e) {
            log.error("pnr匹配出错", e);
            return queryIBEDetail;
        }
        return queryIBEDetail;
    }

    @Override
    public QueryIBEDetail contentPnrMapping(RequestWithActor<String> flightQueryRequest, String customerType) {
        String flightQuery = flightQueryRequest.getEntity();
        log.info("查询参数：" + JsonUtil.toJson(flightQuery));
        Agent agent = flightQueryRequest.getAgent();
        if (null == agent) {
            return null;
        }
        QueryIBEDetail queryIBEDetail = null;
        try {
            PnrOutPut pnr = getPnrService.getInfoByPnrRawI(office, CommenUtil.decodeHtml(flightQuery));
            log.info("国际机票PNR内容预定返回原始信息：" + JsonUtil.toJson(pnr));
            queryIBEDetail = getPnrByQueryIBEDetail(pnr);
            getFSI( queryIBEDetail);
        } catch (Exception e) {
            log.info("pnr内容匹配出错", e);
        }
        queryService.mappingPriceSpec(queryIBEDetail, customerType, agent);
        return queryIBEDetail;
    }

    @Override
    public Map<String,String> pnrValidate(Agent agent, String pnrType, String pnrCode, String contentPnr) {
        Map<String,String> map = new HashMap<>();
        String code = "0";
        String info="";
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PnrOutPut pnr = null;
        try {
            if("2".equals(pnrType)){
                pnr = getPnrService.getInfoByPnrRawI(office, CommenUtil.decodeHtml(contentPnr));
            }else{
                pnr = getPnrService.getPnr(office, pnrCode);
            }
            if(pnr!=null){
                if(("-43010").equals(pnr.getCode())) {
                    code = "1";
                    info =pnr.getShortText();
                }else if(!("178200").equals(pnr.getCode())) {
                    code = "2";
                    info = pnr.getShortText();
                }
            }else{
                code = "2";
                info = "根据PNR没有获取到相关信息，请核实PNR！";
            }

            /***
             * 验证pnr是否已经使用
             */
            if("0".equals(code)&&pnr!=null&&!getOrderByPnr(agent, pnr.getPnrNo())){
                code = "3";
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        map.put(code,info);
        return map;
    }

    public QueryIBEDetail getPnrByQueryIBEDetail(PnrOutPut pnr) throws ParseException {
        QueryIBEDetail queryIBEDetail = new QueryIBEDetail();
        queryIBEDetail.setPnr(pnr.getPnrNo());
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        /*PnrOutPut pnr = null;
        try {
            pnr = getPnrService.getPnr(office, pnrCode);
            log.info("国际机票PNR预定返回原始信息："+JsonUtil.toJson(pnr));
        }catch (Exception e){
            log.info(e.getMessage());
        }*/
        //JourneyType pnr行程类型  OW:单程 RT:往返 MS:多段
        if ("OW".equals(pnr.getJourneyType())) {
            queryIBEDetail.setLegType(1);
        } else if ("RT".equals(pnr.getJourneyType())) {
            queryIBEDetail.setLegType(2);
        } else if ("MS".equals(pnr.getJourneyType())) {
            queryIBEDetail.setLegType(3);
        }
        List<PnrSeg> pnrSegs = pnr.getFlightSegs();
        List<Flight> flightList = new ArrayList<Flight>();
        List<CabinsPricesTotals> cabinsPricesTotalses = new ArrayList<CabinsPricesTotals>();
        CabinsPricesTotals cabinsPricesTotals = new CabinsPricesTotals();
        List<PnrAirTraveler> pnrAirTravelers = pnr.getAirTravelers();
        List<PassengerTypePricesTotal> passengerTypePricesTotals = new ArrayList<PassengerTypePricesTotal>();
        List<PnrPassenger> pnrPassengers = new ArrayList<PnrPassenger>();
        boolean isadt = true;
        boolean isinf = true;
        boolean iscnn = true;
        for (PnrAirTraveler pnrAirTraveler : pnrAirTravelers) {
            PnrPassenger pnrPassenger = new PnrPassenger();
            if ("ADT".equals(pnrAirTraveler.getPassengerTypeCode()) && isinf) {
                PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
                passengerTypePricesTotal.setPassengerType(pnrAirTraveler.getPassengerTypeCode());
                passengerTypePricesTotals.add(passengerTypePricesTotal);
                isinf = false;
            }
            if ("INF".equals(pnrAirTraveler.getPassengerTypeCode()) && iscnn) {
                PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
                passengerTypePricesTotal.setPassengerType(pnrAirTraveler.getPassengerTypeCode());
                passengerTypePricesTotals.add(passengerTypePricesTotal);
                iscnn = false;
            }
            if (("CNN".equals(pnrAirTraveler.getPassengerTypeCode()) || "CHD".equals(pnrAirTraveler.getPassengerTypeCode())) && isadt) {
                PassengerTypePricesTotal passengerTypePricesTotal = new PassengerTypePricesTotal();
                passengerTypePricesTotal.setPassengerType(pnrAirTraveler.getPassengerTypeCode());
                passengerTypePricesTotals.add(passengerTypePricesTotal);
                isadt = false;
            }
            pnrPassenger.setNamePNR(pnrAirTraveler.getNamePNR());
            pnrPassenger.setPassengerbirthday(pnrAirTraveler.getBirthDate());
            pnrPassenger.setPassengeridentitynumber(pnrAirTraveler.getDocID());
            pnrPassenger.setPassengeridentitytype(pnrAirTraveler.getDocType());
            pnrPassenger.setPassengerstype(pnrAirTraveler.getPassengerTypeCode());
            pnrPassenger.setPassengername(pnrAirTraveler.getPersonNameEN());
            pnrPassenger.setPassengernationality(pnrAirTraveler.getDocHolderNationality());
            pnrPassenger.setPassengerpassportvalidity(pnrAirTraveler.getExpireDate());
            pnrPassenger.setPassengersex(pnrAirTraveler.getGender());
            pnrPassenger.setPhone(pnrAirTraveler.getTel());
            pnrPassengers.add(pnrPassenger);
        }
        cabinsPricesTotals.setPassengerTypePricesTotals(passengerTypePricesTotals);
        cabinsPricesTotalses.add(cabinsPricesTotals);
        for (PnrSeg pnrSeg : pnrSegs) {
            if (!"ARRIVAL_UNKNOWN".equals(pnrSeg.getSegmentType())||(StringUtils.isNotBlank(pnrSeg.getArrivalAirport())&&StringUtils.isNotBlank(pnrSeg.getDepartureAirport()))) {
                List<FlightCabinPriceVo> flightCabinPriceVos = new ArrayList<FlightCabinPriceVo>();
                Flight flight = new Flight();
                flight.setTicketType(pnrSeg.getTicket());
                flight.setTpm(0);//里程
                flight.setDepAirport(pnrSeg.getDepartureAirport());
                flight.setArrAirport(pnrSeg.getArrivalAirport());
                flight.setDepTerminal(pnrSeg.getDepartureTerminal());
                flight.setArrTerminal(pnrSeg.getDepartureTerminal());
                flight.setAirline(pnrSeg.getAirline());
                flight.setCodeshare(pnrSeg.getOaCode() + pnrSeg.getOaFlightNumber());
                flight.setFlightNum(pnrSeg.getRph());
                flight.setFlightNo(pnrSeg.getFlightNumber());
                if (null != pnrSeg.getArrivalDateTime()) {
                    flight.setArrTime(fl.parse(pnrSeg.getArrivalDateTime().replaceAll("[a-zA-Z]", " ")));
                }
                if (null != pnrSeg.getDepartureDateTime()) {
                    flight.setDepTime(fl.parse(pnrSeg.getDepartureDateTime().replaceAll("[a-zA-Z]", " ")));
                }
                for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
                    FlightCabinPriceVo flightCabinPriceVo = new FlightCabinPriceVo();
                    flightCabinPriceVo.setCabin(pnrSeg.getResBookDesigCode());
                    flightCabinPriceVo.setPassengerType(passengerTypePricesTotal.getPassengerType());
                    flightCabinPriceVos.add(flightCabinPriceVo);
                }
                flight.setFlightCabinPriceVos(flightCabinPriceVos);
                flightList.add(flight);
            }
        }
        queryIBEDetail.setFlights(flightList);
        queryIBEDetail.setCabinsPricesTotalses(cabinsPricesTotalses);
        queryIBEDetail.setPnrPassengers(pnrPassengers);
        getDirection(queryIBEDetail);
        getFlightOD(queryIBEDetail);
        Flight flight = null;
        if (queryIBEDetail.getFlights().size() > 1) {
            flight = getMainFlight(queryIBEDetail.getFlights());
        } else if (queryIBEDetail.getFlights().size() == 1) {
            flight = queryIBEDetail.getFlights().get(0);
        }
        queryIBEDetail.setTicketAirline(flight.getAirline());
        try {
            getPnrByQueryIBEDetail(queryIBEDetail, pnr.getPnrNo());

        } catch (Exception e) {
            log.error("getPnrByQueryIBEDetail", e);
        }
        return queryIBEDetail;
    }

    public void getPnrByQueryIBEDetail(QueryIBEDetail queryIBEDetail, String pnrCode) throws NotSupportException {
        QteReq reqobj = new QteReq();
        reqobj.setAirportCode(queryIBEDetail.getFlights().get(0).getDepAirport());
        reqobj.setOffice(office);
        reqobj.setIataNo(iataNo);
        reqobj.setPnr(pnrCode.trim());
        reqobj.setTicketingCarrier(queryIBEDetail.getTicketAirline());
        List<PsgerInput> psgerInputs = new ArrayList<PsgerInput>();
        List<PassengerTypePricesTotal> passengerTypePricesTotals = queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals();
        for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
            PsgerInput psgerInput = new PsgerInput();
            psgerInput.setPsgerType(passengerTypePricesTotal.getPassengerType());
            if ("CHD".equals(passengerTypePricesTotal.getPassengerType())) {
                psgerInput.setPsgerType("CNN");
                passengerTypePricesTotal.setPassengerType("CNN");
            }
            psgerInput.setPsgerNum(1);
            psgerInputs.add(psgerInput);
        }
        reqobj.setListPsgerType(psgerInputs);
        log.info("国际机票qte参数原始信息：" + JsonUtil.toJson(reqobj));
        QteRes qteRes = fareService.getFareByPnrI(reqobj);
        List<QteResult> qteResults = qteRes.getListQte();
        log.info("国际机票qte返回原始信息：" + JsonUtil.toJson(qteRes));
        if (null == qteResults) {
            SegQteReq segQteReq = new SegQteReq();
            segQteReq.setOffice(office);
            segQteReq.setIataNo(iataNo);
            segQteReq.setTicketingCarrier(queryIBEDetail.getTicketAirline());
            psgerInputs = new ArrayList<PsgerInput>();
            passengerTypePricesTotals = queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals();
            for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
                PsgerInput psgerInput = new PsgerInput();
                psgerInput.setPsgerType(passengerTypePricesTotal.getPassengerType());
                if ("CHD".equals(passengerTypePricesTotal.getPassengerType())) {
                    psgerInput.setPsgerType("CNN");
                    passengerTypePricesTotal.setPassengerType("CNN");
                }
                psgerInput.setPsgerNum(1);
                psgerInputs.add(psgerInput);
            }
            List<QteFlight> flights = new ArrayList<QteFlight>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            for (Flight flight : queryIBEDetail.getFlights()) {
                QteFlight qteFlight = new QteFlight();
                qteFlight.setDepDateTime(sdf.format(flight.getDepTime()));
                qteFlight.setArrDateTime(sdf.format(flight.getArrTime()));
                qteFlight.setDepAirport(flight.getDepAirport());
                qteFlight.setArrAirport(flight.getArrAirport());
                qteFlight.setPlaneType(flight.getEquipment());
                qteFlight.setAirCode(flight.getAirline());
                qteFlight.setFltNo(flight.getFlightNo());
                qteFlight.setCabinCode(flight.getFlightCabinPriceVos().get(0).getCabin());
                flights.add(qteFlight);
            }
            segQteReq.setFlights(flights);
            segQteReq.setListPsgerType(psgerInputs);
            log.info("国际机票segqte参数原始信息：" + JsonUtil.toJson(segQteReq));
            qteRes = fareService.patBySegForPricing(segQteReq);
            log.info("国际机票segqte返回原始信息：" + JsonUtil.toJson(qteRes));
            qteResults = qteRes.getListQte();
        }
        if (null == qteResults) {
            return;
        }

        for (QteResult qteResult : qteResults) {
            for (PassengerTypePricesTotal passengerTypePricesTotal : passengerTypePricesTotals) {
                if (passengerTypePricesTotal.getPassengerType().equals(qteResult.getPriceType())) {
                    if ("CNY".equals(qteResult.getBaseFare().getCurrencyCode())) {
                        if (null != passengerTypePricesTotal.getFare()) {
                            if (passengerTypePricesTotal.getFare().compareTo(new BigDecimal(qteResult.getBaseFare().getAmount())) > 0) {
                                passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getBaseFare().getAmount()));
                            }
                        } else {
                            passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getBaseFare().getAmount()));
                        }

                    } else {
                        if (null != qteResult.getEquivFare()) {
                            passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getEquivFare().getAmount()));
                            if (null != passengerTypePricesTotal.getFare()) {
                                if (passengerTypePricesTotal.getFare().compareTo(new BigDecimal(qteResult.getEquivFare().getAmount())) > 0) {
                                    passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getEquivFare().getAmount()));
                                }
                            } else {
                                passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getEquivFare().getAmount()));
                            }
                        }
                    }
                    passengerTypePricesTotal.setTax(new BigDecimal(qteResult.getTotalFare().getAmount()).subtract(null != passengerTypePricesTotal.getFare() ? passengerTypePricesTotal.getFare() : new BigDecimal(0)));
                    if (null == passengerTypePricesTotal.getFare()) {
                        if (null != passengerTypePricesTotal.getFare()) {
                            if (passengerTypePricesTotal.getFare().compareTo(new BigDecimal(qteResult.getEquivFare().getAmount()).subtract(passengerTypePricesTotal.getTax())) > 0) {
                                passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getEquivFare().getAmount()).subtract(passengerTypePricesTotal.getTax()));
                            }
                        } else {
                            passengerTypePricesTotal.setFare(new BigDecimal(qteResult.getEquivFare().getAmount()).subtract(passengerTypePricesTotal.getTax()));
                        }
                    }
                    passengerTypePricesTotal.setFareLinear(qteResult.getNuc());
                    passengerTypePricesTotal.setFareBasis(qteResult.getFareBasis());
                    passengerTypePricesTotal.setPassengerType(PassengerTypeEnum.getValue(passengerTypePricesTotal.getPassengerType()));
                }
            }
        }
    }

    public void getDirection(QueryIBEDetail queryIBEDetail) {
        List<Flight> flights = queryIBEDetail.getFlights();
        if(null==queryIBEDetail.getLegType()||"".equals(queryIBEDetail.getLegType())){
            if(flights.size()>1){
                String depAirport=flights.get(0).getDepAirport();
                String arrAirport=flights.get(flights.size()-1).getArrAirport();
                if(depAirport.equals(arrAirport)){
                    queryIBEDetail.setLegType(2);
                }else{
                    Airport depAir = airportService.queryAirportByCode(depAirport, "I");
                    if (null == depAirport) {
                        depAir = airportService.queryAirportByCode(depAirport, "D");
                    }
                    Airport arrAir = airportService.queryAirportByCode(arrAirport, "I");
                    if (null == arrAir) {
                        arrAir = airportService.queryAirportByCode(arrAirport, "D");
                    }
                    String depCity = depAir.getCityCode();
                    String arrCity = arrAir.getCityCode();
                    if(depCity.equals(arrCity)){
                        queryIBEDetail.setLegType(1);
                    }else{
                        boolean isMultipass = false;
                        for (int i = 0; i <= flights.size() - 2; i++) {
                            Flight flight0 = flights.get(i);
                            Flight flight1 = flights.get(i + 1);
                            long temptime = flight1.getDepTime().getTime()-flight0.getArrTime().getTime() ;
                            if(temptime > 24 * 3600 * 1000){
                                isMultipass=true;
                            }
                        }
                        if(isMultipass){
                            queryIBEDetail.setLegType(3);
                        }else{
                            queryIBEDetail.setLegType(1);
                        }
                    }
                }
            }else{
                queryIBEDetail.setLegType(1);
            }
        }
        if (1 == queryIBEDetail.getLegType()) {
            for (Flight flight : flights) {
                flight.setDirection("go");
            }
        } if (2 == queryIBEDetail.getLegType()) {
            long temptime = 0;
            long temp = 0;
            String airport = "";
            for (int i = 0; i <=flights.size() - 2; i++) {
                Flight flight0 = flights.get(i);
                Flight flight1 = flights.get(i + 1);
                temp =flight1.getDepTime().getTime()- flight0.getArrTime().getTime();
                if (temp > temptime) {
                    temptime = temp;
                    airport = flight0.getArrAirport();
                }
            }
            boolean isgo = true;
            for (Flight flight : flights) {
                if (isgo) {
                    flight.setDirection("go");
                    if (flight.getArrAirport().equals(airport)) {
                        isgo = false;
                    }
                } else {
                    flight.setDirection("back");
                }
            }
        } if (3 == queryIBEDetail.getLegType()) {
            long temptime = 0;
            long temp = 0;
            String airport = "";
            for (int i = 0; i < flights.size() - 2; i++) {
                Flight flight0 = flights.get(i);
                Flight flight1 = flights.get(i + 1);
                temp = flight0.getArrTime().getTime() - flight1.getDepTime().getTime();
                if (temp > temptime) {
                    temptime = temp;
                    airport = flight0.getArrAirport();
                }
            }
            if (temptime > 24 * 3600 * 1000) {
                boolean isgo = true;
                for (Flight flight : flights) {
                    if (isgo) {
                        flight.setDirection("go");
                        if (flight.getArrAirport().equals(airport)) {
                            isgo = false;
                        }
                    } else {
                        flight.setDirection("back");
                    }
                }
            } else {
                for (Flight flight : flights) {
                    flight.setDirection("go");
                }
            }
        }
    }

    /**
     * 获取主程
     *
     * @param flights
     * @return Flight
     */
    private Flight getMainFlight(List<Flight> flights) {
        Flight mainFlight = new Flight();
        Integer Tpm = 0;
        List<Flight> countryFlights = new ArrayList<Flight>();
        for (Flight flight : flights) {
            if ("go".equals(flight.getDirection())) {
                Airport depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "I");
                if (null == depAirport) {
                    depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "D");
                }
                Airport arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "I");
                if (null == arrAirport) {
                    arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "D");
                }
                if (null == depAirport) {
                    log.info("depAirport机场三字码：" + flight.getDepAirport());
                }
                if (null == arrAirport) {
                    log.info("arrAirport机场三字码：" + flight.getArrAirport());
                }
                String depCountry = depAirport.getCountryCode();
                String arrCountry = arrAirport.getCountryCode();
                if (!depCountry.equals(arrCountry)) {//判断是否是跨国段
                    countryFlights.add(flight);
                }
            }
        }
        for (Flight flight : countryFlights) {
            if (flight.getTpm() == null) flight.setTpm(1);
            if (Tpm == 0 || Tpm < flight.getTpm()) {//根据里程判断主航段
                Tpm = flight.getTpm();
                mainFlight = flight;
            }
        }
        return mainFlight;
    }

    public void getFlightOD(QueryIBEDetail queryIBEDetail) {
        String goarrOD = null;
        String godepOD = null;
        String backarrOD = null;
        String backdepOD = null;
        for (Flight flight : queryIBEDetail.getFlights()) {
            if ("go".equals(flight.getDirection())) {
                if (null == godepOD) {
                    godepOD = flight.getDepAirport();
                }
                goarrOD = flight.getArrAirport();
            }
            if ("back".equals(flight.getDirection())) {
                if (null == backdepOD) {
                    backdepOD = flight.getDepAirport();
                }
                backarrOD = flight.getArrAirport();
            }
        }
        queryIBEDetail.setGoArrAirport(goarrOD);
        queryIBEDetail.setGoDepAirport(godepOD);
        queryIBEDetail.setBackDepAirport(backdepOD);
        queryIBEDetail.setBackArrAirport(backarrOD);
    }

    /**
     * 验证pnr是否有效
     *
     * @param pnrCode
     * @return
     */
    private Boolean getOrderByPnr(Agent agent, String pnrCode) {
        List<QueryByPnr> queryByPnrList = orderService.queryByPnr(agent, pnrCode);
        if (queryByPnrList != null && queryByPnrList.size() > 0) {
            for (QueryByPnr queryByPnr : queryByPnrList) {
                if (queryByPnr != null && queryByPnr.getSaleOrderExt() != null && queryByPnr.getSaleOrderExt().getSaleOrder() != null) {
                    SaleOrder saleOrder = queryByPnr.getSaleOrderExt().getSaleOrder();
                    /**只要有一条数据不存在这几个状态中的一个，pnr就不可使用*/
                    switch (saleOrder.getOrderChildStatus()){
                        case 8:
                            break;
                        case 9:
                            break;
                        case 11:
                            break;
                        case 14:
                            break;
                        default:
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取FSI
     *
     * @param queryIBEDetail
     * @return Flight
     */
    private void getFSI(QueryIBEDetail queryIBEDetail) {
        String timechange = "";
        String stopOver = "";
        if (1 == queryIBEDetail.getLegType()||null== queryIBEDetail.getLegType()) {//单程获取航班时间差
            if (queryIBEDetail.getFlights().size() >= 1) {
                Flight tempflight = null;
                for (Flight flight : queryIBEDetail.getFlights()) {
                    if (null == tempflight) {
                        tempflight = flight;
                        StringBuffer fsi = new StringBuffer("S&nbsp;");
                        Calendar ca = Calendar.getInstance();
                        ca.setTime(flight.getDepTime());
                        String airLine = flight.getAirline();
                        String flightNo = flight.getFlightNo();
                        fsi.append(airLine).append("&nbsp;");
                        fsi.append(flightNo).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR));
                        fsi.append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;");
                        fsi =fsi.append(flight.getDepAirport()).append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                        ca.setTime(flight.getArrTime());
                        fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                        flight.setFsi(fsi.toString());
                    } else {
                        long time = flight.getDepTime().getTime() - tempflight.getArrTime().getTime();
                        long min = time / 1000 / 60;
                        if (min / 60 > 24) {
                            stopOver = "S";
                        } else {
                            stopOver = "X";
                        }
                        if (tempflight.getArrTime().getTime() > tempflight.getDepTime().getTime()) {
                            timechange = ">";
                        } else {
                            timechange = "<";
                        }
                        StringBuffer fsi = new StringBuffer("S&nbsp;");
                        Calendar ca = Calendar.getInstance();
                        ca.setTime(tempflight.getDepTime());
                        fsi.append(tempflight.getAirline()).append("&nbsp;");
                        fsi.append(tempflight.getFlightNo()).append(tempflight.getFlightCabinPriceVos().get(0).getCabin());
                        fsi.append(ca.get(Calendar.DAY_OF_YEAR)).append(getmonthStrEnglish(tempflight.getDepTime())).append("&nbsp;");
                        fsi = fsi.append(tempflight.getDepAirport()).append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                        ca.setTime(tempflight.getArrTime());
                        fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(tempflight.getArrAirport()).append("0").append(stopOver);
                        tempflight.setFsi(fsi.toString());
                        tempflight = flight;
                        fsi = new StringBuffer("S&nbsp;");
                         ca = Calendar.getInstance();
                        ca.setTime(flight.getDepTime());
                        fsi = fsi.append(flight.getAirline()).append("&nbsp;").append(flight.getFlightNo()).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                .append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;").append(flight.getDepAirport())
                                .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                        ca.setTime(flight.getArrTime());
                        fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                        flight.setFsi(fsi.toString());
                    }
                }
            }
        } else {//往返获取时间差
            Flight tempgoflight = null;
            Flight tempbackflight = null;
            if (queryIBEDetail.getFlights().size() >= 2) {
                for (Flight flight : queryIBEDetail.getFlights()) {
                    if ("go".equals(flight.getDirection())) {
                        if (null == tempgoflight) {
                            tempgoflight = flight;
                            StringBuffer fsi = new StringBuffer("S&nbsp;");
                            Calendar ca = Calendar.getInstance();
                            ca.setTime(flight.getDepTime());
                            fsi.append(flight.getAirline()).append("&nbsp;");
                            fsi.append(flight.getFlightNo()).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR));
                            fsi.append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;");
                            fsi = fsi.append(flight.getDepAirport()).append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(flight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                            flight.setFsi(fsi.toString());
                        } else {
                            long time = flight.getDepTime().getTime() - tempgoflight.getArrTime().getTime();
                            long min = time / 1000 / 60;
                            if (min / 60 > 24) {
                                stopOver = "S";
                            } else {
                                stopOver = "X";
                            }
                            if (tempgoflight.getArrTime().getTime() > tempgoflight.getDepTime().getTime()) {
                                timechange = ">";
                            } else {
                                timechange = "<";
                            }
                            StringBuffer fsi = new StringBuffer("S&nbsp;");
                            Calendar ca = Calendar.getInstance();
                            ca.setTime(tempgoflight.getDepTime());
                            fsi = fsi.append(tempgoflight.getAirline()).append("&nbsp;").append(tempgoflight.getFlightNo()).append(tempgoflight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                    .append(getmonthStrEnglish(tempgoflight.getDepTime())).append("&nbsp;").append(tempgoflight.getDepAirport())
                                    .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(tempgoflight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(tempgoflight.getArrAirport()).append("0").append(stopOver);
                            tempgoflight.setFsi(fsi.toString());
                            tempgoflight = flight;
                            fsi = new StringBuffer("S&nbsp;");
                             ca = Calendar.getInstance();
                            ca.setTime(flight.getDepTime());
                            fsi = fsi.append(flight.getAirline()).append("&nbsp;").append(flight.getFlightNo()).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                    .append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;").append(flight.getDepAirport())
                                    .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(flight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                            flight.setFsi(fsi.toString());
                        }
                    } else {
                        if (null == tempbackflight) {
                            tempbackflight = flight;
                            StringBuffer fsi = new StringBuffer("S&nbsp;");
                            Calendar ca = Calendar.getInstance();
                            ca.setTime(flight.getDepTime());
                            fsi = fsi.append(flight.getAirline()).append("&nbsp;").append(flight.getFlightNo()).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                    .append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;").append(flight.getDepAirport())
                                    .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(flight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                            flight.setFsi(fsi.toString());
                        } else {
                            long time = flight.getDepTime().getTime() - tempbackflight.getArrTime().getTime();
                            long min = time / 1000 / 60;
                            tempbackflight.setTransferTime(min / 60 + ":" + min % 60);
                            if (min / 60 > 24) {
                                stopOver = "S";
                            } else {
                                stopOver = "X";
                            }
                            if (tempbackflight.getArrTime().getTime() > tempbackflight.getDepTime().getTime()) {
                                timechange = ">";
                            } else {
                                timechange = "<";
                            }
                            StringBuffer fsi = new StringBuffer("S&nbsp;");
                            Calendar ca = Calendar.getInstance();
                            ca.setTime(tempbackflight.getDepTime());
                            fsi = fsi.append(tempbackflight.getAirline()).append("&nbsp;").append(tempbackflight.getFlightNo()).append(tempbackflight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                    .append(getmonthStrEnglish(tempbackflight.getDepTime())).append("&nbsp;").append(tempbackflight.getDepAirport())
                                    .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(tempbackflight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(tempbackflight.getArrAirport()).append("0").append(stopOver);
                            tempbackflight.setFsi(fsi.toString());
                            tempbackflight = flight;
                            fsi = new StringBuffer("S&nbsp;");
                             ca = Calendar.getInstance();
                            ca.setTime(flight.getDepTime());
                            fsi = fsi.append(flight.getAirline()).append("&nbsp;").append(flight.getFlightNo()).append(flight.getFlightCabinPriceVos().get(0).getCabin()).append(ca.get(Calendar.DAY_OF_YEAR))
                                    .append(getmonthStrEnglish(flight.getDepTime())).append("&nbsp;").append(flight.getDepAirport())
                                    .append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(timechange);
                            ca.setTime(flight.getArrTime());
                            fsi = fsi.append(ca.get(Calendar.HOUR_OF_DAY)>=10?""+ca.get(Calendar.HOUR_OF_DAY):"0"+ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MILLISECOND)>=10?""+ca.get(Calendar.MILLISECOND):"0"+ca.get(Calendar.MILLISECOND)).append(flight.getArrAirport()).append("0").append("S");
                            flight.setFsi(fsi.toString());
                        }
                    }
                }
            }
        }
    }

    public String getmonthStrEnglish(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m = cal.get(Calendar.MONTH);
        String monthStr = null;
        if (m == 0) {
            monthStr = "JAN";
        } else if (m == 1) {
            monthStr = "FEB";
        } else if (m == 2) {
            monthStr = "MAR";
        } else if (m == 3) {
            monthStr = "APR";
        } else if (m == 4) {
            monthStr = "MAY";
        } else if (m == 5) {
            monthStr = "JUN";
        } else if (m == 6) {
            monthStr = "JUL";
        } else if (m == 7) {
            monthStr = "AUG";
        } else if (m == 8) {
            monthStr = "SEP";
        } else if (m == 9) {
            monthStr = "OCT";
        } else if (m == 10) {
            monthStr = "NOV";
        } else if (m == 11) {
            monthStr = "DEC";
        } else {
            monthStr = "";
        }
        return monthStr;
    }
}

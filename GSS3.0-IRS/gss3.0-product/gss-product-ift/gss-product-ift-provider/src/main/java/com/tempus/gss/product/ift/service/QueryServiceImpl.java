package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.entity.Customer;
import com.tempus.gss.cps.entity.SubControlRule;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISubControlRuleService;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mss.entity.vo.VoyageLowestPriceVo;
import com.tempus.gss.mss.service.IMssVoyagePriceService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.formula.*;
import com.tempus.gss.product.ift.api.entity.vo.*;
import com.tempus.gss.product.ift.api.service.*;
import com.tempus.gss.product.ift.dao.ProfitDao;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.entity.*;
import com.tempus.tbe.service.IShoppingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 杨威 on 2016/10/12.
 */
@Service
@EnableAutoConfiguration
public class QueryServiceImpl implements IQueryService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return  new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    /*航司总则服务*/
    @Autowired
    private IPriceSpecService priceSpecService;
    
    /*航班基础数据服务*/
    @Reference
    private IAirportService airportService;
    /*政策服务*/
    @Autowired
    private IPolicyService policyService;
    
    /*shopping接口服务*/
    @Reference
    private IShoppingService shoppingService;
    
    @Reference
    private IMssVoyagePriceService mssVoyagePriceService;
    
    @Reference
    ISubControlRuleService subControlRuleService;

    /*多程单独计算*/
    @Reference
    IMultipassMappingService multipassMappingService;
    @Autowired
    ProfitDao profitDao;
    @Reference
    protected ICustomerService customerService;
    @Autowired
    FliePriceMappingService fliePriceMappingService;
    @Reference
    ILogService logService ;
    @Autowired
    IPolicyRService  policyRService;
    
    @Value("${ift.iataNo}")
    private String iataNo;
    @Value("${ift.office}")
    private String office;
    
    /**
     * 白屏查询机票信息.
     *
     * @param flightQueryRequest
     * @return
     */
    @Override
    public Page<QueryIBEDetail> query(Page<QueryIBEDetail> page, RequestWithActor<FlightQueryRequest> flightQueryRequest) {
        FlightQueryRequest flightQuery = flightQueryRequest.getEntity();
        if(3==flightQuery.getLegType()){
            return  multipassMappingService.query(page, flightQueryRequest);
        }
        Page<QueryIBEDetail> pages = new Page<QueryIBEDetail>();

        log.info("查询参数：" + JsonUtil.toJson(flightQuery));
        Agent agent = flightQueryRequest.getAgent();
        if (null == agent) {
            return null;
        }
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
		ShoppingSeg shoppingSeg = new ShoppingSeg(flightQuery.getDepDate(),flightQuery.getDepAirport(),flightQuery.getArrAirport());
        if (null != flightQuery.getTransfers() && flightQuery.getTransfers().size() > 0) {
            List<ConnectionLocation> connectionLocations = new ArrayList<ConnectionLocation>();
            for (String transfer : flightQuery.getTransfers()) {
                connectionLocations.add(new ConnectionLocation(transfer, true));
            }
            shoppingSeg.setConnectionLocations(connectionLocations);
        }
        segs.add(shoppingSeg);
        if (null != flightQuery.getLegType() && 2 == flightQuery.getLegType()) {
            shoppingSeg = new ShoppingSeg(flightQuery.getReturnDate(), flightQuery.getArrAirport(), flightQuery.getDepAirport());
            segs.add(shoppingSeg);
        }
        shoppingInput.setSegs(segs);
        log.info("开始调用shopping接口,入参:{}",JsonUtil.toJson(shoppingInput));
        ShoppingOutPut shoppingOutPut = shoppingService.shoppingI(shoppingInput);
        log.info("完成调用shopping接口"+shoppingOutPut.getShortText());
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        if (null == availableJourneys || availableJourneys.size() < 1) {
            return null;
        }
        List<QueryIBEDetail> queryIBEDetailList = null;
        log.info("开始调用shopping数据转换");
        try {
            queryIBEDetailList = shoppingOutPutConvertQueryIBEDetails(shoppingOutPut);
        } catch (ParseException e) {
            log.error("开始调用shopping数据转换", e);
        }
        log.info("开始匹配政策");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = taskExecutor();
        for (QueryIBEDetail queryIBEDetail : queryIBEDetailList) {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mappingPriceSpec(queryIBEDetail, flightQuery.getCustomerType(), agent);
                }
            });
        }
        threadPoolTaskExecutor.shutdown();
        try {
            while (true) {
                if (threadPoolTaskExecutor.getThreadPoolExecutor().isTerminated()) {
                    log.info("查询结束了！");
                    break;
                }
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            log.error("政策匹配",e);
        }
        log.info("开始组装数据");
        queryIBEDetailList = combineQueryIBEDetails(queryIBEDetailList, flightQueryRequest.getAgent().getDevice(),agent);
        log.info("完成组装数据");
        log.info(JsonUtil.toJson(queryIBEDetailList));
        pages.setRecords(queryIBEDetailList);
        return pages;
    }

    /**
     * 匹配政策算价格
     *
     * @param queryIBEDetail
     * @return
     */
    @Override
    public void mappingPriceSpec(QueryIBEDetail queryIBEDetail, String customerTypeNo, Agent agent) {
        RequestWithActor<String> stringRequestWithActor = new RequestWithActor<String>();
        stringRequestWithActor.setEntity(queryIBEDetail.getTicketAirline());
        stringRequestWithActor.setAgent(agent);
        if(null==queryIBEDetail.getTicketAirline()){
           return;
        }
        PriceSpec priceSpec = priceSpecService.getPriceSpec(stringRequestWithActor);
        if(null==priceSpec){
            stringRequestWithActor.setEntity("*");
            priceSpec = priceSpecService.getPriceSpec(stringRequestWithActor);
        }
        Formula formula = JsonUtil.toBean(priceSpec.getFormulaData(), Formula.class);
        priceSpec.setFormula(formula);
        queryIBEDetail.setPriceSpecNo(priceSpec.getPriceSpecNo());

        Map<String, String> passengerTypeMap = new HashMap<String, String>();
        for (Flight flight : queryIBEDetail.getFlights()) {//判断航段有没有奖励
            for (FlightCabinPriceVo flightCabinPriceVo : flight.getFlightCabinPriceVos()) {
                if (flightCabinPriceVo.getPassengerType().equals(priceSpec.getNoAwardType())) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
                passengerTypeMap.put(flightCabinPriceVo.getPassengerType(), flightCabinPriceVo.getPassengerType());
                if ("SOTO".equals(flight.getTicketType()) && priceSpec.isNoAwardSoto()) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
                if ("IT".equals(flight.getTicketType()) && priceSpec.isNoAwardIt()) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
                if ("OPEN".equals(flight.getTicketType()) && priceSpec.isNoAwardOpen()) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
                if (priceSpec.getNoAwardCabin().contains(flightCabinPriceVo.getCabin())) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
                if (priceSpec.getNoAwardFlightNo().contains(flight.getFlightNo())) {
                    flightCabinPriceVo.setAward(false);
                } else {
                    flightCabinPriceVo.setAward(true);
                }
            }
        }
        for (CabinsPricesTotals cabinsPricesTotals : queryIBEDetail.getCabinsPricesTotalses()) {//整体价格记不记奖
            for (PassengerTypePricesTotal passengerTypePricesTotal : cabinsPricesTotals.getPassengerTypePricesTotals()) {
                if (null != priceSpec.getNoAwardBelowFare() && passengerTypePricesTotal.getFare().compareTo(priceSpec.getNoAwardBelowFare()) == -1) {
                    passengerTypePricesTotal.setAward(false);
                } else {
                    passengerTypePricesTotal.setAward(true);
                }
                 if (priceSpec.isNoAwardIt() && null!=passengerTypePricesTotal.getFareBasis()&&passengerTypePricesTotal.getFareBasis().contains("/it")) {
                    passengerTypePricesTotal.setAward(false);
                } else {
                    passengerTypePricesTotal.setAward(true);
                }
            }
        }
        String goDep = null;
        Integer flightNum = 0;
        String direction = "";
        if (1 == priceSpec.getGoDep()) {//出票航第一个航段的起点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("go")) {
                    if (flightNum >= flight.getFlightNum() || flightNum == 0) {
                        goDep = flight.getDepAirport();
                        direction = flight.getDirection();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        
        if (2 == priceSpec.getGoDep()) {//出票航自承运第一个航段起点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("go")) {
                    if (flight.getAirline().equals(queryIBEDetail.getTicketAirline()) && (flightNum >= flight.getFlightNum() || flightNum == 0)) {
                        goDep = flight.getDepAirport();
                        direction = flight.getDirection();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        if (3 == priceSpec.getGoDep()) {//第一个跨国段的起点
            for (Flight flight : queryIBEDetail.getFlights()) {
                Airport depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "I");
                if (null == depAirport) {
                    depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "D");
                }
                Airport arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "I");
                if (null == arrAirport) {
                    arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "D");
                }
                String depCountry = depAirport.getCountryCode();
                String arrCountry = arrAirport.getCountryCode();
                if (flight.getDirection().equals("go")) {
                    if (!depCountry.equals(arrCountry) && (flightNum >= flight.getFlightNum() || flightNum == 0)) {
                        direction = flight.getDirection();
                        goDep = flight.getDepAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        String goArr = null;
        String backDep = null;
        if (1 == priceSpec.getMiddle()) {//实际行程的折返点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("go")) {
                    if (direction.equals(flight.getDirection()) && (flightNum <= flight.getFlightNum() || flightNum == 0)) {
                        goArr = flight.getArrAirport();
                        backDep = flight.getArrAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
            
        }
        if (2 == priceSpec.getMiddle()) {//去程/回程出票航的最远点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("go")) {
                    if (flight.getAirline().equals(queryIBEDetail.getTicketAirline()) && direction.equals(flight.getDirection()) && (flightNum <= flight.getFlightNum() || flightNum == 0)) {
                        goArr = flight.getArrAirport();
                        backDep = flight.getArrAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        if (3 == priceSpec.getMiddle()) {//去程主航段终点/回程主航段起点
            Integer Tpm = 0;
            List<Flight> countryFlights = new ArrayList<Flight>();
            for (Flight flight : queryIBEDetail.getFlights()) {
                Airport depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "I");
                if (null == depAirport) {
                    depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "D");
                }
                Airport arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "I");
                if (null == arrAirport) {
                    arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "D");
                }
                String depCountry = depAirport.getCountryCode();
                String arrCountry = arrAirport.getCountryCode();
                if (flight.getDirection().equals("go")) {
                    if (!depCountry.equals(arrCountry) && direction.equals(flight.getDirection())) {
                        countryFlights.add(flight);
                    }
                }
            }
            for (Flight flight : countryFlights) {
                if (Tpm == 0 || Tpm < flight.getTpm()) {
                    goArr = flight.getArrAirport();
                    backDep = flight.getArrAirport();
                }
            }
        }
        String backArr = null;
        if (1 == priceSpec.getBackArr()) {// 出票航最后一个航段的终点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("back")) {
                    if (!direction.equals(flight.getDirection()) && (flightNum <= flight.getFlightNum() || flightNum == 0)) {
                        backArr = flight.getArrAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        if (2 == priceSpec.getBackArr()) {//出票航自承运的最后一个航段终点
            for (Flight flight : queryIBEDetail.getFlights()) {
                if (flight.getDirection().equals("back")) {
                    if (flight.getAirline().equals(queryIBEDetail.getTicketAirline()) && !direction.equals(flight.getDirection()) && (flightNum <= flight.getFlightNum() || flightNum == 0)) {
                        backArr = flight.getArrAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        if (3 == priceSpec.getBackArr()) {// 最后一个跨国段的终点
            for (Flight flight : queryIBEDetail.getFlights()) {
                Airport depAirport = airportService.queryAirportByCode(flight.getDepAirport(), "I");
                if (null == depAirport) {
                    depAirport = airportService.queryAirportByCode(goDep, "D");
                }
                Airport arrAirport = airportService.queryAirportByCode(flight.getArrAirport(), "I");
                if (null == arrAirport) {
                    arrAirport = airportService.queryAirportByCode(goDep, "D");
                }
                String depCountry = depAirport.getCountryCode();
                String arrCountry = arrAirport.getCountryCode();
                if (flight.getDirection().equals("back")) {
                    if (!direction.equals(flight.getDirection()) && !depCountry.equals(arrCountry) && (flightNum <= flight.getFlightNum() || flightNum == 0)) {
                        backArr = flight.getDepAirport();
                        flightNum = flight.getFlightNum();
                    }
                }
            }
        }
        List<Flight> goFlights = new ArrayList<Flight>();
        List<Flight> backFlights = new ArrayList<Flight>();
        for (Flight flight : queryIBEDetail.getFlights()) {//得到去程航班
            if ("go".equals(flight.getDirection())) {
                goFlights.add(flight);
            } else {
                backFlights.add(flight);
            }
        }
        queryIBEDetail.setGoDepOD(goDep);
        queryIBEDetail.setGoArrOD(goArr);
        queryIBEDetail.setBackDepOD(goArr);
        queryIBEDetail.setBackArrOD(backArr);
        fliePriceMappingService.exchangeFilePrice( queryIBEDetail,  agent);
        for (PassengerTypePricesTotal passengerTypePricesTotal : queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
            if(null==passengerTypePricesTotal.getFare()) {
                return;
            }
            String passengerType = passengerTypePricesTotal.getPassengerType();
            Map policyMap = null;
            if (passengerTypePricesTotal.getAward()) {//如果价格记奖才去匹配政策
                policyMap = mappingPolicy(goDep, goArr, backArr, queryIBEDetail.getTicketAirline(), passengerType, goFlights, backFlights, customerTypeNo, agent);
            }
            Airport depAirport = airportService.queryAirportByCode(queryIBEDetail.getGoDepAirport(), "I");
            if (null == depAirport) {
                depAirport = airportService.queryAirportByCode(queryIBEDetail.getGoDepAirport(), "D");
            }
            Airport arrAirport = airportService.queryAirportByCode(queryIBEDetail.getGoArrAirport(), "I");
            if (null == arrAirport) {
                arrAirport = airportService.queryAirportByCode(queryIBEDetail.getGoArrAirport(), "D");
            }
            String depCity = depAirport.getCityCode();
            String arrCity = arrAirport.getCityCode();
            Map<String, String> uncMap = null;
            if (null != policyMap) {//没有政策不需要解析横式
                uncMap = getNucByFareLinear(passengerTypePricesTotal.getFareLinear(), queryIBEDetail.getGoArrAirport(), arrCity, queryIBEDetail.getGoDepAirport(), depCity, passengerTypePricesTotal.getFareBasis(), queryIBEDetail.getLegType());
            }
            if (2 == queryIBEDetail.getLegType()) {//往返政策要根据总则的计算方式计算
                Integer combinationPrice = priceSpec.getFormula().getCombinationPrice();
                if (null == combinationPrice) {
                    combinationPrice = 0;
                }
                switch (combinationPrice) {//根据总则的计算方式计算价格
                    case 1:
                        BigDecimal salePrice = getCombinationPrice(passengerTypePricesTotal, queryIBEDetail, priceSpec, policyMap, 1);
                        passengerTypePricesTotal.setSalePrice(salePrice.setScale(0, BigDecimal.ROUND_UP));
                        break;
                    case 2:
                        BigDecimal salePrice2 = getCombinationPrice(passengerTypePricesTotal, queryIBEDetail, priceSpec, policyMap, 2);
                        passengerTypePricesTotal.setSalePrice(salePrice2.setScale(0, BigDecimal.ROUND_UP));
                        break;
                    case 3:
                        BigDecimal salePrice1 = getCombinationPrice(passengerTypePricesTotal, queryIBEDetail, priceSpec, policyMap, 1);
                        BigDecimal salePrice3 = getCombinationPrice(passengerTypePricesTotal, queryIBEDetail, priceSpec, policyMap, 2);
                        if (salePrice1.compareTo(salePrice3) < 1) {
                            passengerTypePricesTotal.setSalePrice(salePrice1.setScale(0, BigDecimal.ROUND_UP));
                        } else {
                            passengerTypePricesTotal.setSalePrice(salePrice1.setScale(0, BigDecimal.ROUND_UP));
                        }
                        break;
                    default:
                        BigDecimal salePrice4 = getCombinationPrice(passengerTypePricesTotal, queryIBEDetail, priceSpec, policyMap, 1);
                        passengerTypePricesTotal.setSalePrice(salePrice4.setScale(0, BigDecimal.ROUND_UP));
                        break;
                }
            } else {//计算单程价格
                BigDecimal salePrice=null;
                BigDecimal awardPrice=passengerTypePricesTotal.getFare();
                if(null!=policyMap){
                    Policy policy = (Policy) policyMap.get("policy");
                    if(null==policy.getqRebate()||!policy.getqRebate()){
                        BigDecimal Qvalue = new BigDecimal(0);
                        if (null != uncMap && null != uncMap.get("Q")) {
                            Qvalue = new BigDecimal((String) uncMap.get("Q")).multiply(new BigDecimal((String) uncMap.get("roe")));
                        }
                        awardPrice= awardPrice.subtract(Qvalue);
                    }
                    if(null==policy.getsRebate()||!policy.getsRebate()){
                        BigDecimal Svalue = new BigDecimal(0);
                        if (null != uncMap && null != uncMap.get("S")) {
                            Svalue = new BigDecimal((String) uncMap.get("S")).multiply(new BigDecimal((String) uncMap.get("roe")));
                        }
                        awardPrice=awardPrice.subtract(Svalue);
                    }
                    if(null!=policy.getYryqRebate()&&policy.getYryqRebate()){
                        BigDecimal YQvalue = new BigDecimal(0);
                        BigDecimal YRvalue = new BigDecimal(0);
                        HashMap<String, BigDecimal> map= passengerTypePricesTotal.getTaxs();
                        if (null != uncMap && null != map.get("YQ")) {
                            YQvalue = new BigDecimal((String) uncMap.get("YQ"));
                        }
                        if (null != uncMap && null != map.get("YR")) {
                            YRvalue = new BigDecimal((String) uncMap.get("YR"));
                        }
                        awardPrice=awardPrice.add(YRvalue).add(YQvalue);
                        passengerTypePricesTotal.setFare(passengerTypePricesTotal.getFare().add(YRvalue).add(YQvalue));
                        passengerTypePricesTotal.setTax(passengerTypePricesTotal.getTax().subtract(YRvalue).subtract(YQvalue));
                    }
                }
                salePrice = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, passengerTypePricesTotal.getFare(), passengerTypePricesTotal.getTax(), awardPrice, goFlights, "go");
                passengerTypePricesTotal.setSalePrice(salePrice.setScale(0, BigDecimal.ROUND_UP));
            }
            if (null != policyMap) {//有政策获取政策中的代理费率，返点，手续费
                if(null!=policyMap.get("saleRebate")){
                    passengerTypePricesTotal.setSaleRebate((BigDecimal) policyMap.get("saleRebate"));
                    passengerTypePricesTotal.setBrokerage((BigDecimal) policyMap.get("saleOwBrokerage"));
                }else{
                    passengerTypePricesTotal.setSaleRebate((BigDecimal) policyMap.get("backSaleRebate"));
                    passengerTypePricesTotal.setBrokerage((BigDecimal) policyMap.get("backSaleOwBrokerage"));
                }
                BigDecimal awardPrice=(BigDecimal)policyMap.get("awardPrice");
                if(null==policyMap.get("awardPrice")){
                    passengerTypePricesTotal.setAwardPrice((BigDecimal) policyMap.get("backAwardPrice"));
                }else {
                    if(null!= policyMap.get("backAwardPrice")){
                        passengerTypePricesTotal.setAwardPrice(awardPrice.add((BigDecimal) policyMap.get("backAwardPrice")));
                    }
                    passengerTypePricesTotal.setAwardPrice(awardPrice);
                }
                Policy policy = (Policy) policyMap.get("policy");
                if (null == policy) {
                    policy = (Policy) policyMap.get("backPolicy");
                }
                passengerTypePricesTotal.setAgencyFee(policy.getAgencyFee());
                passengerTypePricesTotal.setPriceNo(policy.getPolicyNo());
            } else {//没有有政策获取政策中的代理费率，手续费
                NoMatchFormulaPara noMatchFormulaPara = priceSpec.getFormula().getNoMatchFormulaPara();
                passengerTypePricesTotal.setBrokerage(noMatchFormulaPara.getBrokerage());
                passengerTypePricesTotal.setAgencyFee(noMatchFormulaPara.getAgencyFee());
            }
        }
    }
    
    /**
     * 获取往返城最佳返点
     *
     * @param goDep, goArr, backArr, airline, passengerTypeMap ,goFlights, backFlights, customerTypeNo
     * @return Map
     */
    private Map mappingPolicy(String goDep, String goArr, String backArr, String airline, String passengerType, List<Flight> goFlights, List<Flight> backFlights, String customerTypeNo, Agent agent) {
        List<Policy> goPolicyList = new ArrayList<Policy>();
        Policy policyVo = new Policy();
        policyVo.setGoStart( goDep );
        policyVo.setGoEnd( goArr );
        policyVo.setAirline(airline);
        policyVo.setTravellerType(passengerType );
        policyVo.setTravellerLimit(1);
        policyVo.setOwner(agent.getOwner());
        policyVo.setTripType(1);
        goPolicyList=policyRService.queryObjByODs(policyVo);
        log.info("查询查询政策goPolicyList：" +(goPolicyList==null?0:goPolicyList.size()) );
        Flight goflight = getMainFlight(goFlights);
        for (int i = goPolicyList.size() - 1; i >= 0; i--) {//
            Policy policy = goPolicyList.get(i);
            if (!checkPolicyDate(policy, goflight, false) || !checkPolicyFlight(policy, goflight, false) || !checkPolicyOther(policy, goFlights)) {
                goPolicyList.remove(i);
            }
        }
        List<Map> goMapList = mappingCabins(goPolicyList, goflight, customerTypeNo);
        if (null != backArr && !"".equals(backArr)) {//取返程政策
            List<Policy> backPolicyList = new ArrayList<Policy>();
            policyVo = new Policy();
            policyVo.setGoStart(goArr );
            policyVo.setGoEnd( backArr );
            policyVo.setAirline(airline);
            policyVo.setTravellerType( passengerType );
            policyVo.setTravellerLimit(1);
            policyVo.setOwner(agent.getOwner());
            policyVo.setTripType(2);
            backPolicyList=policyRService.queryObjByODs(policyVo);
            Flight backFlight = getMainFlight(backFlights);
            for (int i = backPolicyList.size() - 1; i >= 0; i--) {//根据限制条件去除政策
                Policy policy = backPolicyList.get(i);
                if (!checkPolicyDate(policy, backFlight, true) || !checkPolicyFlight(policy, backFlight, true) || !checkPolicyOther(policy, backFlights)) {
                    backPolicyList.remove(i);
                }
            }
            List<Map> flightMapList = new ArrayList<Map>();
            List<Map> backMapList = mappingCabins(backPolicyList, backFlight, customerTypeNo);//匹配仓位
            if (goMapList.size() < 1) {
                if (backMapList.size() < 1) {
                    return null;
                } else {
                    Policy backPolicy = (Policy) backMapList.get(0).get("policy");//获取最优政策
                    Map map = new HashMap<String, Object>();
                    map.put("backPolicy", backPolicy);
                    map.put("backSaleRebate", backMapList.get(0).get("saleRebate"));
                    map.put("backSaleOwBrokerage", backMapList.get(0).get("saleOwBrokerage"));
                    map.put("backSaleRtBrokerage", backMapList.get(0).get("saleRtBrokerage"));
                    return map;
                }
            } else if (backMapList.size() < 1) {
                Map map = goMapList.get(0);
                return map;
            }
            Policy goPolicy = (Policy) goMapList.get(0).get("policy");
            Policy backPolicy = (Policy) backMapList.get(0).get("policy");
            if (!goPolicy.getAllowCrossLineMatch() && !backPolicy.getAllowCrossLineMatch()) {
                Map map = goMapList.get(0);
                map.put("backPolicy", backPolicy);
                map.put("backSaleRebate", backMapList.get(0).get("saleRebate"));
                map.put("backSaleOwBrokerage", backMapList.get(0).get("saleOwBrokerage"));
                map.put("backSaleRtBrokerage", backMapList.get(0).get("saleRtBrokerage"));
                return map;
            }
            for (Map goMap : goMapList) {//跨航线组合匹配
                goPolicy = (Policy) goMap.get("policy");
                for (Map backMap : backMapList) {
                    backPolicy = (Policy) backMap.get("policy");
                    if (goPolicy.getAllowCrossLineMatch() || backPolicy.getAllowCrossLineMatch()) {
                        if (goPolicy.getPolicyNo().equals(backPolicy.getPolicyNo())) {
                            Map map = new HashMap();
                            map.put("goMap", goMap);
                            map.put("backMap", backMap);
                            flightMapList.add(map);
                        }
                    } else {
                        Map map = new HashMap();
                        map.put("goMap", goMap);
                        map.put("backMap", backMap);
                        flightMapList.add(map);
                    }
                }
            }
            for (int i = 0; i < flightMapList.size(); i++) {//对比返点排序倒序排列
                for (int j = i; j < flightMapList.size(); j++) {
                    Map mapi = flightMapList.get(i);
                    BigDecimal saleRebateI = ((BigDecimal) ((Map) mapi.get("goMap")).get("saleRebate")).add((BigDecimal) ((Map) mapi.get("backMap")).get("saleRebate"));
                    Map mapj = flightMapList.get(j);
                    BigDecimal saleRebateJ = ((BigDecimal) ((Map) mapj.get("goMap")).get("saleRebate")).add((BigDecimal) ((Map) mapj.get("backMap")).get("saleRebate"));
                    if (saleRebateI.compareTo(saleRebateJ) < 1) {
                        Map map = flightMapList.get(i);
                        flightMapList.set(i, flightMapList.get(j));
                        flightMapList.set(i, map);
                    }
                }
            }
            Map map = flightMapList.get(0);//取最高返点
            Map goMap = (Map) map.get("goMap");
            goMap.put("backPolicy", backPolicy);
            goMap.put("backSaleRebate", ((Map) map.get("backMap")).get("saleRebate"));
            goMap.put("backSaleOwBrokerage", ((Map) map.get("backMap")).get("saleOwBrokerage"));
            goMap.put("backSaleRtBrokerage", ((Map) map.get("backMap")).get("saleRtBrokerage"));
            return goMap;
        }
        if (goMapList.size() < 1) {
            return null;
        }
        return goMapList.get(0);
    }
    
    /**
     * 检验政策日期
     *
     * @param policy goflight isback
     * @return boolean
     */
    private boolean checkPolicyDate(Policy policy, Flight goflight, boolean isback) {
        boolean check = true;
        SimpleDateFormat stPolicy = new SimpleDateFormat("yyyy/MM/dd");//2016-10-08
        SimpleDateFormat stFlight = new SimpleDateFormat("yyyy/MM/dd");
        
        String[] travelDates = null;
        if (isback) {
            travelDates = policy.getBackDate().split(",");
        } else {
            travelDates = policy.getTravelDate().split(",");
        }
        
        String[] ticketDates = policy.getTicketDate().split("-");
        try {
            boolean isok = false;
            for (String travelDate : travelDates) {
                String[] s_travelDate = travelDate.split("-");
                if (goflight.getDepTime().getTime() >= stFlight.parse(s_travelDate[0]).getTime() && goflight.getDepTime().getTime() <= stFlight.parse(s_travelDate[1]).getTime()) {
                    isok = true;
                }
            }
            if (isok && goflight.getDepTime().getTime() >= stPolicy.parse(ticketDates[0]).getTime() && goflight.getDepTime().getTime() <= stPolicy.parse(ticketDates[1]).getTime()) {
                check = true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            log.error("checkPolicyDate", e);
        }
        if (getDateTime(policy.getPolicyEffectStart()) <= new Date().getTime() && getDateTime(policy.getPolicyEffectEnd()) >= new Date().getTime()) {
            check = true;
        } else {
            return false;
        }
        return check;
    }
    
    /**
     * 检验政策航段
     *
     * @param policy goflight isback
     * @return boolean
     */
    private boolean checkPolicyFlight(Policy policy, Flight goflight, boolean isback) {
        boolean check = true;
        if (!isback && policy.getExceptGoStart().contains(goflight.getDepAirport())) {
            return false;
        }
        if (!isback && policy.getExceptGoEnd().contains(goflight.getArrAirport())) {
            return false;
        }
        if (isback && policy.getExceptGoEnd().contains(goflight.getDepAirport())) {
            return false;
        }
        if (isback && policy.getExceptBackEnd().contains(goflight.getArrAirport())) {
            return false;
        }
        if (!isback && (policy.getGoNotFlight().contains(goflight.getArrAirport()) || policy.getGoNotFlight().contains(goflight.getDepAirport()))) {
            return false;
        }
        if (!isback && null != policy.getGoMustFlight() && !"".equals(policy.getGoMustFlight()) && (!policy.getGoMustFlight().contains(goflight.getArrAirport()) || !policy.getGoMustFlight().contains(goflight.getDepAirport()))) {
            return false;
        }
        if (isback && policy.getBackNotFlight().contains(goflight.getArrAirport()) || policy.getBackNotFlight().contains(goflight.getDepAirport())) {
            return false;
        }
        if (isback && null != policy.getGoMustFlight() && !"".equals(policy.getGoMustFlight()) && (!policy.getBackMustFlight().contains(goflight.getArrAirport()) || !policy.getBackMustFlight().contains(goflight.getDepAirport()))) {
            return false;
        }
        if (!isback && null != policy.getGoFlightNo() && !"".equals(policy.getGoFlightNo()) && !policy.getGoFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (isback && null != policy.getGoFlightNo() && !"".equals(policy.getGoFlightNo()) && !policy.getBackFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (policy.getExceptFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (policy.getExceptFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        return check;
    }
    
    /**
     * 检验政策其它设置
     *
     * @param policy flights
     * @return boolean
     */
    private boolean checkPolicyOther(Policy policy, List<Flight> flights) {
        boolean check = true;
        if (null != policy.getIsSameCarrier()) {//全程是否同一承运人
            if (policy.getIsSameCarrier()) {
                String airline = null;
                for (Flight flight : flights) {
                    if (airline == null) {
                        airline = flight.getAirline();
                    }
                    if (!airline.equals(flight.getAirline())) {
                        return false;
                    }
                    
                }
            }
            
        }
        if (null != policy.getIsCodeShare()) {//是否代码共享
            if (policy.getIsCodeShare()) {
                String airline = null;
                for (Flight flight : flights) {
                    if (null != flight.getCodeshare() && !policy.getShareAirline().contains(flight.getCodeshare())) {
                        for (FlightCabinPriceVo FlightCabinPriceVo : flight.getFlightCabinPriceVos()) {
                            FlightCabinPriceVo.setAward(false);
                        }
                    }
                    
                }
            }
        }
        if (null != policy.getOnlyDirectFlight()) {  //是否必须直飞
            if (policy.getOnlyDirectFlight() && flights.size() > 1) {
                return false;
            }
        }
        return check;
    }
    
    /**
     * 检验政策仓位
     *
     * @param policys goflight customerTypeNo
     * @return List<Map>
     */
    private List<Map> mappingCabins(List<Policy> policys, Flight goflight, String customerTypeNo) {
        List<Map> mapList = new ArrayList<Map>();
        for (Policy policy : policys) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("policy", policy);
            for (Cabin cabin : policy.getCabinList()) {
                if ((cabin.getCabin().contains(goflight.getFlightCabinPriceVos().get(0).getCabin())|| ("*".equals(cabin.getCabin())&&(null==cabin.getExclusiveCabin()||!cabin.getExclusiveCabin().contains(goflight.getFlightCabinPriceVos().get(0).getCabin()))))&& false != goflight.getFlightCabinPriceVos().get(0).isAward()) {
                    for (ProfitControl profitControl : cabin.getProfitControlList()) {//判断渠道
                        if (customerTypeNo.equals("" + profitControl.getCustomerTypeNo())) {
                            if (null != profitControl.getSaleRebate() && profitControl.getSaleRebate().compareTo(new BigDecimal(0)) == 1) {
                                map.put("saleRebate", profitControl.getSaleRebate());
                                map.put("saleOwBrokerage", profitControl.getSaleOwBrokerage());
                                map.put("saleRtBrokerage", profitControl.getSaleRtBrokerage());
                            }
                            
                        }
                    }
                    if (null == map.get("saleRebate")//仓位默认政策
                            || ((BigDecimal) map.get("saleRebate")).compareTo(new BigDecimal(0)) < 1) {
                        map.put("saleRebate", cabin.getSaleRebate());
                        map.put("saleOwBrokerage", cabin.getSaleOwBrokerage());
                        map.put("saleRtBrokerage", cabin.getSaleRtBrokerage());
                    }
                }
            }
            if (null != map.get("saleRebate")) {
                mapList.add(map);
            }
        }
        for (int i = 0; i < mapList.size(); i++) {//找出做优政策
            for (int j = i; j < mapList.size(); j++) {
                if (((BigDecimal) mapList.get(i).get("saleRebate")).compareTo((BigDecimal) mapList.get(j).get("saleRebate")) < 1) {
                    Map map = mapList.get(i);
                    mapList.set(i, mapList.get(j));
                    mapList.set(j, map);
                }
            }
        }
        return mapList;
    }
    
    /**
     * 检验政策仓位
     *
     * @param shoppingOutPut
     * @return List<QueryIBEDetail>
     */
    
    private List<QueryIBEDetail> shoppingOutPutConvertQueryIBEDetails(ShoppingOutPut shoppingOutPut) throws ParseException {
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<QueryIBEDetail> queryIBEDetails = new ArrayList<QueryIBEDetail>();
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        for (AvailableJourney availableJourney : availableJourneys) {
            QueryIBEDetail queryIBEDetail = new QueryIBEDetail();
            //将availableJourney保存在queryIBEDetail中，以便查询更多舱位
            queryIBEDetail.setAvailableJourney(availableJourney);
            queryIBEDetail.setAvailableJourneyJson(JsonUtil.toJson(availableJourney));
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
                if (psgerFare.getFareLinear().contains("/it")) {
                    ticketType = "IT";
                }
                passengerTypePricesTotal.setPassengerType(psgerFare.getPsgerInfo().getCode());
                passengerTypePricesTotal.setFareBasis(psgerFare.getFareBasis());
                BigDecimal taxs = new BigDecimal(0);
                BigDecimal taxxt = new BigDecimal(0);
                if (null != psgerFare.getTaxs()) {
                    HashMap<String,BigDecimal> maptaxs=new HashMap<String,BigDecimal>();
                    for (Tax tax : psgerFare.getTaxs()) {
                        if ("XT".equals(tax.getTaxCode())) {
                            taxxt = taxxt.add(tax.getAmount());
                        } else {
                            taxs = taxs.add(tax.getAmount());
                        }
                        maptaxs.put(tax.getTaxCode(),tax.getAmount());
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
                    flight.setCodeshare(shoppingFlight.getOpCode()+shoppingFlight.getOpFltNo());
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
            queryIBEDetails.add(queryIBEDetail);
        }
        return queryIBEDetails;
    }

    /**
     * 得到国内附加段
     *
     * @param flights goDep goArr backArr
     * @return List<Flight>
     */
    private List<Flight> getAddonFlight(List<Flight> flights) {
        List<Flight> flightList = new ArrayList<Flight>();
        Flight mainFlight = getMainFlight(flights);
        for (Flight flight : flights) {
            if (flight.getArrAirport().equals(mainFlight.getDepAirport()) && flight.getDirection().equals("go")) {
                flightList.add(flight);
            }
            if (flight.getDepAirport().equals(mainFlight.getDepAirport()) && flight.getDirection().equals("back")) {
                flightList.add(flight);
            }
        }
        return flightList;
    }

    /**
     * 得到国外延伸段
     *
     * @param flights goDep goArr backArr
     * @return List<Flight>
     */
    private List<Flight> getSPAFlight(List<Flight> flights) {
        List<Flight> flightList = new ArrayList<Flight>();
        Flight mainFlight = getMainFlight(flights);
        for (Flight flight : flights) {
            if (flight.getDepAirport().equals(mainFlight.getArrAirport()) && flight.getDirection().equals("go")) {
                flightList.add(flight);
            }
            if (flight.getArrAirport().equals(mainFlight.getArrAirport()) && flight.getDirection().equals("back")) {
                flightList.add(flight);
            }
        }
        return flightList;

    }

    /**
     * 解析运价横式
     *
     * @param fareLinear goDep goArr
     * @return List<Flight>
     */
    private Map<String, String> getNucByFareLinear(String fareLinear, String arr, String arrCity, String dep, String depCity, String fareBasis, Integer legType) {
        Map<String, String> map = new HashMap<String, String>();
        LogRecord log =new LogRecord();
        log.setCreateTime(new Date());
        log.setAppCode("B2B");
        log.setBizCode("POLICY");
        log.setDesc( "fareLinear:"+fareLinear+"--"+"arr:"+ arr + "--arrCity:"+ arrCity+ "--dep:" +dep + "--depCity:" +depCity +"--fareBasis:" + fareBasis+"--legType:" + legType);
        log.setBizNo("fareLinear12345678968574512");
        logService.insert(log);
        if(null==fareLinear){
            fareLinear="";
        }
        fareLinear=fareLinear.toLowerCase();
        if (fareBasis.contains("+")) {
            for (String fareBasi : fareBasis.split("/+")) {
                fareLinear = fareLinear.replace(fareBasi.toLowerCase(), "");
            }
        } else {
            fareLinear = fareLinear.replace(fareBasis.toLowerCase(), "");
        }
        if ("".equals(fareLinear)) {
            map.put("Q", "0");
            if (null == legType || 1 == legType) {
                map.put("goNuc", "1");
                map.put("totalNuc", "1");
                map.put("roe", "1");
            } else {
                map.put("goNuc", "1");
                map.put("backNuc", "1");
                map.put("totalNuc", "2");
                map.put("roe", "1");
            }
        } else if(fareLinear.contains("/it")) {
            fareLinear=fareLinear.replaceAll("/it","");
            String[] fc = fareLinear.split(" ");
            for (String a : fc) {
                Pattern pattern = Pattern.compile("^q.*");
                Matcher matcher = pattern.matcher(a);
                String nuc = "";
                if (matcher.matches()) {
                    nuc = a.replaceAll(arr, "").replaceAll("[a-z]", "").trim();
                    if (!"".equals(nuc)) {
                        map.put("Q", nuc);
                    }
                }
                if ( a.contains("s")) {
                    nuc = a.replaceAll(arr, "").replaceAll("[a-z]", "").trim();
                    if (!"".equals(nuc)) {
                        map.put("S", nuc);
                    }
                }
                if (a.contains("roe")) {
                    nuc = a.substring(a.indexOf("roe"));
                    nuc = nuc.replaceAll("roe", "").replaceAll("roe", "");
                    if (!"".equals(nuc)) {
                        map.put("roe", nuc);
                    }
                }
                if(null!=map.get("roe")&&!"".equals(map.get("roe"))){
                    if(null!=map.get("Q")&&!"".equals(map.get("Q"))){
                        BigDecimal  Qvalue=new BigDecimal(map.get("roe")).multiply(new BigDecimal(map.get("Q")));
                        map.put("Qvalue",Qvalue.toString() );
                    }
                    if(null!=map.get("S")&&!"".equals(map.get("S"))){
                        BigDecimal  Svalue=new BigDecimal(map.get("roe")).multiply(new BigDecimal(map.get("S")));
                        map.put("Svalue", Svalue.toString());
                    }
                }
            }
        } else {
            String[] fc = fareLinear.split(" ");
            arr = arr.toLowerCase();
            dep = dep.toLowerCase();
            arrCity = arrCity.toLowerCase();
            depCity = depCity.toLowerCase();
            String nuc = "";
            for (String a : fc) {
                Pattern pattern = Pattern.compile("^q.*");
                Matcher matcher = pattern.matcher(a);
                if (matcher.matches()) {
                    nuc = a.replaceAll(arr, "").replaceAll("[a-z]", "").trim();
                    if (!"".equals(nuc)) {
                        map.put("Q", nuc);
                    }
                }
                if (a.contains("S")||a.contains("s") ) {
                    nuc = a.replaceAll(arr, "").replaceAll("[a-z]", "").trim();
                    if (!"".equals(nuc)) {
                        map.put("S", nuc);
                    }
                }
                if (a.contains(arr) || a.contains(arrCity)) {
                    nuc = a.replaceAll(arr, "").replaceAll("[a-z]", "").replaceAll("[/-]", "");
                    if (!"".equals(a)) {
                        map.put("goNuc", nuc);
                    }
                }
                if (a.contains(arrCity)) {
                    nuc = a.replaceAll(arrCity, "").replaceAll("[a-z]", "").replaceAll("[/-]", "");
                    if (!"".equals(a)) {
                        map.put("goNuc", nuc);
                    }
                }
                if (a.contains(dep)) {
                    nuc = a;
                    if (a.indexOf("nuc") > 0) {
                        nuc = a.substring(0, a.indexOf("nuc") - 1);
                    }
                    nuc = nuc.substring(nuc.indexOf(dep));
                    nuc = nuc.replaceAll(dep, "").replaceAll("[a-z]", "");
                    if (!"".equals(nuc)) {
                        map.put("backNuc", nuc);
                    }
                }
                if (a.contains(depCity)) {
                    nuc = a;
                    if (a.indexOf("nuc") > 0) {
                        nuc = a.substring(0, a.indexOf("nuc") - 1);
                    }
                    nuc = nuc.substring(nuc.indexOf(depCity));
                    nuc = nuc.replaceAll(depCity, "").replaceAll("[a-z]", "");
                    if (!"".equals(nuc)) {
                        map.put("backNuc", nuc);
                    }
                }
                if (a.contains("nuc")) {
                    nuc = a.substring(a.indexOf("nuc"));
                    nuc = nuc.replaceAll("nuc", "").replaceAll("end", "");
                    map.put("totalNuc", nuc);
                }
                if (a.contains("roe")) {
                    nuc = a.substring(a.indexOf("roe"));
                    nuc = nuc.replaceAll("roe", "").replaceAll("roe", "");
                    map.put("roe", nuc);
                }
            }
            if (null == legType || 1 == legType) {//
                if (null == map.get("goNuc")) {
                    BigDecimal goNuc = new BigDecimal(map.put("totalNuc", nuc)).subtract(new BigDecimal((String) map.get("Q")));
                    map.put("goNuc", goNuc + "");
                }
                if (null == map.get("Q")) {
                    BigDecimal goNuc = new BigDecimal(map.put("totalNuc", nuc)).subtract(new BigDecimal((String) map.get("goNuc")));
                    map.put("Q", goNuc + "");
                }
            } else {
                if (null == map.get("goNuc")) {
                    BigDecimal goNuc = new BigDecimal(map.put("totalNuc", nuc)).subtract(new BigDecimal((String) map.get("Q"))).subtract(new BigDecimal((String) map.get("backNuc")));
                    map.put("goNuc", goNuc + "");
                }
                if (null == map.get("Q")) {
                    BigDecimal goQ = new BigDecimal(map.put("totalNuc", nuc)).subtract(new BigDecimal((String) map.get("goNuc"))).subtract(new BigDecimal((String) map.get("backNuc")));
                    ;
                    map.put("Q", goQ + "");
                }
                if (null == map.get("backNuc")) {
                    BigDecimal backNuc = new BigDecimal(map.put("totalNuc", nuc)).subtract(new BigDecimal((String) map.get("Q"))).subtract(new BigDecimal((String) map.get("goNuc")));
                    map.put("backNuc", backNuc + "");
                }
            }
        }

        return map;
    }

    /**
     * 计算运价
     *
     * @param policyMap formula
     * @return BigDecimal
     */
    private BigDecimal getfare(Map policyMap, Formula formula, QueryIBEDetail queryIBEDetail, BigDecimal fare, BigDecimal tax, BigDecimal awardPrice, List<Flight> flights, String type) {
        Policy policy = null;
        BigDecimal saleRebate = null;
        BigDecimal saleBrokerage = null;
        BigDecimal salePrice = null;
        List<Flight> salelist = new ArrayList<Flight>();
        for (Flight flight : flights) {
            if (flight.getDirection().equals(type)) {
                salelist.add(flight);
            }
        }
        if (null != policyMap) {
            if ("back".equals(type)) {
                policy = (Policy) policyMap.get("backPolicy");
                saleRebate = (BigDecimal) policyMap.get("backSaleRebate");
                saleBrokerage = (BigDecimal) policyMap.get("backSaleRtBrokerage");
            } else {
                policy = (Policy) policyMap.get("policy");
                saleRebate = (BigDecimal) policyMap.get("saleRebate");
                saleBrokerage = (BigDecimal) policyMap.get("saleOwBrokerage");
            }
        }

        if (null == policy) {
            NoMatchFormulaPara noMatchFormulaPara = formula.getNoMatchFormulaPara();
            boolean iscombination = false;
            if (null != policyMap && ((null == policyMap.get("backPolicy") && null != policyMap.get("policy")) || ((null != policyMap.get("backPolicy") && null == policyMap.get("policy"))))) {
                iscombination = true;
            }
            salePrice = noMatchFormulaPara.formulaMethod(fare, tax, iscombination, queryIBEDetail.getLegType());
        } else {
            if (null != policyMap.get("backPolicy")) {
                saleBrokerage = (BigDecimal) policyMap.get("backSaleRtBrokerage");
                saleBrokerage = saleBrokerage.divide(new BigDecimal(2));
            }
            Flight mainFlight = getMainFlight(salelist);
            if (1 == formula.getSelectFormulaNo()) {//计算公式1
                Formula01Para formula01Para = formula.getFormula01Para();
                Integer tpmTotal = 0;
                Integer reTpm = 0;
                Integer otherTpm = 0;
                BigDecimal rePrice = new BigDecimal(0);
                BigDecimal otherPrice = new BigDecimal(0);
                List<Flight> reFlights = new ArrayList<Flight>();
                List<Flight> otherFlights = new ArrayList<Flight>();
                for (Flight flight : salelist) {
                    tpmTotal = tpmTotal + flight.getTpm();
                    if (flight.getFlightCabinPriceVos().get(0).isAward()) {
                        otherFlights.add(flight);
                        otherTpm = otherTpm + flight.getTpm();
                        continue;
                    }
                    if (!flight.getAirline().equals(queryIBEDetail.getTicketAirline())) {
                        reTpm = reTpm + flight.getTpm();
                        reFlights.add(flight);
                    }

                }
                if (otherTpm > 0) {
                    FormulaParameters formulaParameters = new FormulaParameters();
                    formulaParameters.setFare(awardPrice.multiply(new BigDecimal(reTpm / tpmTotal)));
                    formulaParameters.setAgencyFee(policy.getAgencyFee());
                    formulaParameters.setAwardPrice(new BigDecimal(0));
                    formulaParameters.setSaleRebate(new BigDecimal(0));
                    formulaParameters.setBrokerage(new BigDecimal(0));
                    formulaParameters.setTax(new BigDecimal(0));
                    otherPrice = formula01Para.formulaMethod4(formulaParameters);
                }
                flights.removeAll(otherFlights);
                flights.removeAll(reFlights);
                if (reTpm > 0) {//不记奖航段计算价格
                    if (null != formula01Para.getRegionList() && formula01Para.getRegionList().size() > 0) {
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(reTpm / tpmTotal)));
                        formulaParameters.setAwardPrice(new BigDecimal(0));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        for (Formula01Region formula01Region : formula01Para.getRegionList()) {
                            if (formula01Region.getLow().compareTo(new BigDecimal(reTpm / tpmTotal)) < 1 && formula01Region.getHing().compareTo(new BigDecimal(reTpm / tpmTotal)) > 0) {
                                formulaParameters.setSaleRebate(saleRebate.subtract(formula01Region.getRebate()));
                            }
                        }
                        switch (formula01Para.getFormulaNo()) {
                            case 1:
                                rePrice = formula01Para.formulaMethod1(formulaParameters);
                                break;
                            case 2:
                                rePrice = formula01Para.formulaMethod2(formulaParameters);
                                break;
                            case 3:
                                rePrice = formula01Para.formulaMethod3(formulaParameters);
                                break;
                            case 4:
                                rePrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                            default:
                                rePrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                        }
                    } else {
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setAwardPrice(new BigDecimal(0));
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(reTpm / tpmTotal)));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setSaleRebate(new BigDecimal(0));
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        rePrice = formula01Para.formulaMethod4(formulaParameters);
                    }
                }
                List<Flight> addonFlight = getAddonFlight(salelist);
                Integer addonTpm = 0;
                for (Flight flight : addonFlight) {
                    if (flight.getAirline().equals(queryIBEDetail.getTicketAirline())) {
                        addonTpm = addonTpm + flight.getTpm();
                    }
                }
                flights.removeAll(addonFlight);
                BigDecimal addonPrice = new BigDecimal(0);
                if (addonTpm > 0) {//国内附加段计算价格
                    if (null != formula01Para.getInlandRebateList() && formula01Para.getInlandRebateList().size() > 0) {
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(reTpm / tpmTotal)));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        if ("back".equals(type)) {
                            if (null != formula01Para.getInlandRebateList().get(1)) {
                                formulaParameters.setSaleRebate(saleRebate.subtract(formula01Para.getInlandRebateList().get(1)));
                            } else {
                                formulaParameters.setSaleRebate(saleRebate);
                            }
                        } else {
                            if (null != formula01Para.getInlandRebateList().get(0)) {
                                formulaParameters.setSaleRebate(saleRebate.subtract(formula01Para.getInlandRebateList().get(0)));
                            } else {
                                formulaParameters.setSaleRebate(saleRebate);
                            }
                        }
                        switch (formula01Para.getFormulaNo()) {
                            case 1:
                                addonPrice = formula01Para.formulaMethod1(formulaParameters);
                                break;
                            case 2:
                                addonPrice = formula01Para.formulaMethod2(formulaParameters);
                                break;
                            case 3:
                                addonPrice = formula01Para.formulaMethod3(formulaParameters);
                                break;
                            case 4:
                                addonPrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                            default:
                                addonPrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                        }
                    } else {
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(addonTpm / tpmTotal)));
                        formulaParameters.setAwardPrice(new BigDecimal(0));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setSaleRebate(new BigDecimal(0));
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        addonPrice = formula01Para.formulaMethod4(formulaParameters);
                    }
                }
                List<Flight> spaFlight = getSPAFlight(salelist);
                Integer spaTpm = 0;
                BigDecimal spaPrice = new BigDecimal(0);
                for (Flight flight : spaFlight) {
                    if (flight.getAirline().equals(queryIBEDetail.getTicketAirline())) {
                        spaTpm = spaTpm + flight.getTpm();
                    }
                }
                flights.removeAll(spaFlight);
                if (spaTpm > 0) {//境外延伸段计算价格
                    if (null != formula01Para.getForeignRebateList() && formula01Para.getForeignRebateList().size() > 0) {
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(reTpm / tpmTotal)));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setAwardPrice(new BigDecimal(0));
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        if ("back".equals(type)) {
                            if(null!=formula01Para.getForeignRebateList()){
                                formulaParameters.setSaleRebate(saleRebate.subtract(formula01Para.getForeignRebateList().get(1)));
                            }else{
                                formulaParameters.setSaleRebate(saleRebate);
                            }
                        } else {
                            if(null!=formula01Para.getForeignRebateList()) {
                                formulaParameters.setSaleRebate(saleRebate.subtract(formula01Para.getForeignRebateList().get(0)));
                            }else{
                                formulaParameters.setSaleRebate(saleRebate);
                            }
                        }
                        switch (formula01Para.getFormulaNo()) {//判断计算公式编号
                            case 1:
                                spaPrice = formula01Para.formulaMethod1(formulaParameters);
                                break;
                            case 2:
                                spaPrice = formula01Para.formulaMethod2(formulaParameters);
                                break;
                            case 3:
                                spaPrice = formula01Para.formulaMethod3(formulaParameters);
                                break;
                            case 4:
                                spaPrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                            default:
                                spaPrice = formula01Para.formulaMethod4(formulaParameters);
                                break;
                        }
                    } else {//默认用计算公式4
                        FormulaParameters formulaParameters = new FormulaParameters();
                        formulaParameters.setFare(awardPrice.multiply(new BigDecimal(addonTpm / tpmTotal)));
                        formulaParameters.setAgencyFee(policy.getAgencyFee());
                        formulaParameters.setAwardPrice(new BigDecimal(0));
                        formulaParameters.setSaleRebate(new BigDecimal(0));
                        formulaParameters.setBrokerage(new BigDecimal(0));
                        formulaParameters.setTax(new BigDecimal(0));
                        spaPrice = formula01Para.formulaMethod4(formulaParameters);
                    }
                }
                Integer mainTpm = 0;
                BigDecimal mainPrice = new BigDecimal(0);
                mainTpm = mainFlight.getTpm();
                mainFlight.setPolicyNo(policy.getPolicyNo());
                FormulaParameters formulaParameters = new FormulaParameters();
                formulaParameters.setFare(awardPrice.multiply(new BigDecimal(mainTpm / tpmTotal)));
                formulaParameters.setAgencyFee(policy.getAgencyFee());
                formulaParameters.setAwardPrice(awardPrice.multiply(new BigDecimal(mainTpm / tpmTotal)));
                formulaParameters.setSaleRebate(saleRebate);
                formulaParameters.setBrokerage(saleBrokerage);
                formulaParameters.setTax(new BigDecimal(0));
                switch (formula01Para.getFormulaNo()) {//判断计算公式编号
                    case 1:
                        mainPrice = formula01Para.formulaMethod1(formulaParameters);
                        break;
                    case 2:
                        mainPrice = formula01Para.formulaMethod2(formulaParameters);
                        break;
                    case 3:
                        mainPrice = formula01Para.formulaMethod3(formulaParameters);
                        break;
                    case 4:
                        mainPrice = formula01Para.formulaMethod4(formulaParameters);
                        break;
                    default:
                        mainPrice = formula01Para.formulaMethod4(formulaParameters);
                        break;
                }
                salePrice = mainPrice.add(spaPrice).add(addonPrice).add(rePrice).add(otherPrice).add(tax);
            }
            if (2 == formula.getSelectFormulaNo()) {//计算公式2
                Formula02Para formula02Para = formula.getFormula02Para();
                int awardTmp = 0;
                int otherTpm = 0;
                Integer tpmTotal = 0;
                for (Flight flight : salelist) {
                    tpmTotal = tpmTotal + flight.getTpm();
                    if (flight.getFlightCabinPriceVos().get(0).isAward()) {
                        otherTpm = otherTpm + flight.getTpm();
                        continue;
                    }
                }
                List<Flight> addonFlight = getAddonFlight(salelist);
                String mainFareBasisCode = null;
                for (FlightCabinPriceVo flightCabinPriceVo : mainFlight.getFlightCabinPriceVos()) {
                    if (flightCabinPriceVo.getPassengerType().equals(policy.getTravellerType())) {
                        mainFareBasisCode = flightCabinPriceVo.getFareBasisCode();
                    }
                }
                if (null != addonFlight && addonFlight.size() > 0) {//国内附加段计算价格
                    String filghtFareBasisCode = "";
                    for (Flight flight : addonFlight) {
                        for (FlightCabinPriceVo flightCabinPriceVo : flight.getFlightCabinPriceVos()) {
                            if (flightCabinPriceVo.getPassengerType().equals(policy.getTravellerType())) {
                                filghtFareBasisCode = flightCabinPriceVo.getFareBasisCode();
                            }
                        }
                        if (formula02Para.isInlandRewards()) {
                            for (Formula02Carrier formula02Carrier : formula02Para.getCarrierList()) {
                                if (flight.getAirline().equals(queryIBEDetail.getTicketAirline())) {
                                    if (1 == formula02Carrier.getLegType()) {//国内附加段自承运计奖方式。
                                        if (formula02Carrier.isUseIt()) {
                                            if (formula02Carrier.isSameWithMainLeg()) {//与主航段同一票价计算组计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段同一票价计算组不计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                            if (formula02Carrier.isDifferentWithMainLeg()) {//与主航段不同票价计算组计奖.
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段不同票价计算组不计奖.
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                        } else {
                                            otherTpm = otherTpm + flight.getTpm();
                                        }
                                    }
                                } else {
                                    if (2 == formula02Carrier.getLegType()) {//国内附加段非自承运计奖方式。
                                        if (formula02Carrier.isUseIt()) {
                                            if (formula02Carrier.isSameWithMainLeg()) {//与主航段同一票价计算组计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段同一票价计算组不计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                            if (formula02Carrier.isDifferentWithMainLeg()) {//与主航段不同票价计算组计奖.
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段不同票价计算组不计奖.
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                        } else {
                                            otherTpm = otherTpm + flight.getTpm();
                                        }
                                    }
                                }
                            }
                        } else {
                            otherTpm = otherTpm + flight.getTpm();
                        }
                    }
                }
                List<Flight> spaFlight = getSPAFlight(salelist);
                if (null != spaFlight && spaFlight.size() > 0) {//境外延伸段计算价格
                    String filghtFareBasisCode = "";
                    for (Flight flight : spaFlight) {
                        for (FlightCabinPriceVo flightCabinPriceVo : flight.getFlightCabinPriceVos()) {
                            if (flightCabinPriceVo.getPassengerType().equals(policy.getTravellerType())) {
                                filghtFareBasisCode = flightCabinPriceVo.getFareBasisCode();
                            }
                        }
                        if (formula02Para.isForeignRewards()) { //境外延伸段是否计奖
                            for (Formula02Carrier formula02Carrier : formula02Para.getCarrierList()) {
                                if (flight.getAirline().equals(queryIBEDetail.getTicketAirline())) {
                                    if (3 == formula02Carrier.getLegType()) { //国外附加段自承运计奖方式
                                        if (formula02Carrier.isUseIt()) {
                                            if (formula02Carrier.isSameWithMainLeg()) {//与主航段同一票价计算组计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段同一票价计算组不计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                            if (formula02Carrier.isDifferentWithMainLeg()) {//与主航段不同票价计算组计奖
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段不同票价计算组不计奖
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                        } else {
                                            otherTpm = otherTpm + flight.getTpm();
                                        }
                                    }
                                } else {
                                    if (4 == formula02Carrier.getLegType()) {//国外附加段非自承运计奖方式
                                        if (formula02Carrier.isUseIt()) {
                                            if (formula02Carrier.isSameWithMainLeg()) {//与主航段同一票价计算组计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段同一票价计算组不计奖
                                                if (filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                            if (formula02Carrier.isDifferentWithMainLeg()) {//与主航段不同票价计算组计奖
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    awardTmp = awardTmp + flight.getTpm();
                                                    flight.setPolicyNo(policy.getPolicyNo());
                                                }
                                            } else {//与主航段不同票价计算组不计奖
                                                if (!filghtFareBasisCode.equals(mainFareBasisCode)) {
                                                    otherTpm = otherTpm + flight.getTpm();
                                                }
                                            }
                                        } else {
                                            otherTpm = otherTpm + flight.getTpm();
                                        }
                                    }
                                }
                            }
                        } else {
                            otherTpm = otherTpm + flight.getTpm();
                        }
                    }
                }
                awardTmp = awardTmp + mainFlight.getTpm();
                mainFlight.setPolicyNo(policy.getPolicyNo());
                FormulaParameters formulaParameters = new FormulaParameters();
                if(0==tpmTotal){
                    formulaParameters.setAwardPrice(awardPrice);
                }else{
                    formulaParameters.setAwardPrice(awardPrice.multiply(new BigDecimal(awardTmp / tpmTotal)));
                }
                formulaParameters.setFare(fare);
                formulaParameters.setAgencyFee(policy.getAgencyFee());
                formulaParameters.setTax(tax);
                formulaParameters.setSaleRebate(saleRebate);
                formulaParameters.setBrokerage(saleBrokerage);
                if ("back".equals(type)) {
                    policyMap.put("backAwardPrice", formulaParameters.getAwardPrice());
                }else {
                    policyMap.put("awardPrice", formulaParameters.getAwardPrice());
                }
                if(null==formula02Para.getFormulaNo()){
                    formula02Para.setFormulaNo(0);
                }
                switch (formula02Para.getFormulaNo()) {//判断计算公式编号
                    case 1:
                        salePrice = formula02Para.formulaMethod1(formulaParameters);
                        break;
                    case 2:
                        salePrice = formula02Para.formulaMethod2(formulaParameters);
                        break;
                    default:
                        salePrice = formula02Para.formulaMethod2(formulaParameters);
                        break;
                }
            }
        }
        return salePrice;
    }
    
    /**
     * 获取主程
     *
     * @param backFlights
     * @return Flight
     */
    private Flight getMainFlight(List<Flight> backFlights) {
        Flight mainFlight = new Flight();
        Integer Tpm = 0;
        List<Flight> countryFlights = new ArrayList<Flight>();
        for (Flight flight : backFlights) {
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
        for (Flight flight : countryFlights) {
            if (Tpm == 0 || Tpm < flight.getTpm()) {//根据里程判断主航段
                Tpm = flight.getTpm();
                mainFlight = flight;
            }
        }
        return mainFlight;
    }
    
    /**
     * 计算往返程
     *
     * @param passengerTypePricesTotal queryIBEDetail priceSpec policyMap type
     * @return BigDecimal
     */
    private BigDecimal getCombinationPrice(PassengerTypePricesTotal passengerTypePricesTotal, QueryIBEDetail queryIBEDetail, PriceSpec priceSpec, Map policyMap, int type) {
        Airport depAirport = airportService.queryAirportByCode(queryIBEDetail.getGoDepAirport(), "I");
        if (null == depAirport) {
            depAirport = airportService.queryAirportByCode(queryIBEDetail.getGoDepAirport(), "D");
        }
        Airport arrAirport = airportService.queryAirportByCode(queryIBEDetail.getGoArrAirport(), "I");
        if (null == arrAirport) {
            arrAirport = airportService.queryAirportByCode(queryIBEDetail.getGoArrAirport(), "D");
        }
        String depCity = depAirport.getCityCode();
        String arrCity = arrAirport.getCityCode();
        if (null == policyMap) {//往返没政策计算票价
            BigDecimal awardGoPrice = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, passengerTypePricesTotal.getFare().divide(new BigDecimal(2)), passengerTypePricesTotal.getTax(), new BigDecimal(0), queryIBEDetail.getFlights(), "go");
            BigDecimal awardBackPrice = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, passengerTypePricesTotal.getFare().divide(new BigDecimal(2)), new BigDecimal(0), new BigDecimal(0), queryIBEDetail.getFlights(), "back");
            return awardGoPrice.add(awardBackPrice);
        } else {//往返有政策计算票价
            Map<String, String> uncMap = getNucByFareLinear(passengerTypePricesTotal.getFareLinear(), queryIBEDetail.getGoArrAirport(), arrCity, queryIBEDetail.getGoDepAirport(), depCity, passengerTypePricesTotal.getFareBasis(), queryIBEDetail.getLegType());
            if (1 == type) {//1（往返程价格分开算）
                BigDecimal Qvalue = new BigDecimal(0);
                if (null != uncMap.get("Q")) {//获取Q值
                    Qvalue = new BigDecimal((String) uncMap.get("Q")).multiply(new BigDecimal((String) uncMap.get("roe")));
                }
                BigDecimal awardGoFare = null;
                BigDecimal awardBackFare =null;
                if(null==uncMap.get("goNuc")){
                    awardGoFare = passengerTypePricesTotal.getFare().divide(new BigDecimal(2)).subtract(Qvalue);
                    awardBackFare = passengerTypePricesTotal.getFare().divide(new BigDecimal(2)).subtract(Qvalue);
                }else{
                    awardGoFare = new BigDecimal((String) uncMap.get("goNuc")).multiply(new BigDecimal((String) uncMap.get("roe")));
                    awardBackFare = new BigDecimal((String) uncMap.get("backNuc")).multiply(new BigDecimal((String) uncMap.get("roe")));
                }
                 if ("1".equals(uncMap.get("goNuc"))) {//获取nuc
                    awardGoFare = passengerTypePricesTotal.getFare().divide(new BigDecimal(2));
                    awardBackFare = passengerTypePricesTotal.getFare().divide(new BigDecimal(2));
                }
                BigDecimal goFare=awardGoFare.add(Qvalue);
                Policy policy = (Policy) policyMap.get("policy");
                if(null!=policy.getqRebate()&&policy.getqRebate()){
                    awardGoFare= awardGoFare.add(Qvalue);
                }
                BigDecimal Svalue = new BigDecimal(0);
                if (null != uncMap && null != uncMap.get("S")) {
                    Svalue = new BigDecimal((String) uncMap.get("S")).multiply(new BigDecimal((String) uncMap.get("roe")));
                }
                if(null!=policy.getsRebate()&&policy.getsRebate()){
                    awardGoFare= awardGoFare.add(Svalue);
                }
                goFare=awardGoFare.add(Qvalue).add(Svalue);
                if(null!=policy.getYryqRebate()&&policy.getYryqRebate()){
                    BigDecimal YQvalue = new BigDecimal(0);
                    BigDecimal YRvalue = new BigDecimal(0);
                    HashMap<String, BigDecimal> map= passengerTypePricesTotal.getTaxs();
                    if (null != uncMap && null != map.get("YQ")) {
                        YQvalue = new BigDecimal((String) uncMap.get("YQ"));
                    }
                    if (null != uncMap && null != map.get("YR")) {
                        YRvalue = new BigDecimal((String) uncMap.get("YR"));
                    }
                    awardGoFare= awardGoFare.add(YRvalue).add(YQvalue);
                    goFare=goFare.add(YRvalue).add(YQvalue);
                    passengerTypePricesTotal.setTax(passengerTypePricesTotal.getTax().subtract(YRvalue).subtract(YQvalue));
                }
                BigDecimal awardGoPrice = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, goFare, passengerTypePricesTotal.getTax(), awardGoFare, queryIBEDetail.getFlights(), "go");
                BigDecimal awardBackPrice = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, awardBackFare, new BigDecimal(0), awardBackFare, queryIBEDetail.getFlights(), "back");
                return awardGoPrice.add(awardBackPrice);
            }
            if (2 == type) {// 2（往返程价格1/2）
                Policy policy = (Policy) policyMap.get("policy");
                BigDecimal awardGoFare2 = passengerTypePricesTotal.getFare().divide(new BigDecimal(2));
                BigDecimal awardBackFare2 = passengerTypePricesTotal.getFare().divide(new BigDecimal(2));
                BigDecimal goFare2 = awardGoFare2;
                BigDecimal backFare2 = awardBackFare2;
                if(null==policy.getqRebate()||!policy.getqRebate()){
                    BigDecimal Qvalue2 = new BigDecimal(0);
                    if (null != uncMap.get("Q")) {
                        Qvalue2 = new BigDecimal((String) uncMap.get("Q")).multiply(new BigDecimal((String) uncMap.get("roe")));
                    }
                    awardGoFare2= awardGoFare2.subtract(Qvalue2.divide(new BigDecimal(2)));
                    awardBackFare2= awardBackFare2.subtract(Qvalue2.divide(new BigDecimal(2)));
                }
                if(null==policy.getsRebate()||!policy.getsRebate()){
                    BigDecimal Svalue = new BigDecimal(0);
                    if (null != uncMap && null != uncMap.get("S")) {
                        Svalue = new BigDecimal((String) uncMap.get("S")).multiply(new BigDecimal((String) uncMap.get("roe")));
                    }
                    awardGoFare2= awardGoFare2.subtract(Svalue.divide(new BigDecimal(2)));
                    awardBackFare2= awardBackFare2.subtract(Svalue.divide(new BigDecimal(2)));
                }
                if(null!=policy.getYryqRebate()&&policy.getYryqRebate()){
                    BigDecimal YQvalue = new BigDecimal(0);
                    BigDecimal YRvalue = new BigDecimal(0);
                    HashMap<String, BigDecimal> map= passengerTypePricesTotal.getTaxs();
                    if (null != uncMap && null != map.get("YQ")) {
                        YQvalue = new BigDecimal((String) uncMap.get("YQ"));
                    }
                    if (null != uncMap && null != map.get("YR")) {
                        YRvalue = new BigDecimal((String) uncMap.get("YR"));
                    }
                    awardGoFare2= awardGoFare2.add(YRvalue).add(YQvalue);
                    goFare2=goFare2.add(YRvalue).add(YQvalue);
                    passengerTypePricesTotal.setTax(passengerTypePricesTotal.getTax().subtract(YRvalue).subtract(YQvalue));
                }

                BigDecimal awardGoPrice2 = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, goFare2, passengerTypePricesTotal.getTax(), awardGoFare2, queryIBEDetail.getFlights(), "go");
                BigDecimal awardBackPrice2 = getfare(policyMap, priceSpec.getFormula(), queryIBEDetail, backFare2, new BigDecimal(0), awardBackFare2, queryIBEDetail.getFlights(), "back");
                return awardGoPrice2.add(awardBackPrice2);
            }
        }
        
        return null;
    }
    
    /**
     * 合并行程相同的数据
     *
     * @param queryIBEDetails List<QueryIBEDetail>
     */
    private List<QueryIBEDetail> combineQueryIBEDetails(List<QueryIBEDetail> queryIBEDetails, String type,Agent agent) {
        Map<String, QueryIBEDetail> queryIBEDetailMap = new HashMap<String, QueryIBEDetail>();
        BigDecimal profit=getProfitByAgent( agent);
        QueryIBEDetail minqueryIBEDetail = null;
        for (QueryIBEDetail queryIBEDetail : queryIBEDetails) {//组装数据
            StringBuffer sb = new StringBuffer("");
            String key = sb.append(queryIBEDetail.getTicketAirline()).append(queryIBEDetail.getGoDepAirport()).append(queryIBEDetail.getGoArrAirport()).append(queryIBEDetail.getGoDepTime()).append(queryIBEDetail.getGoArrTime()).append(queryIBEDetail.getBackArrAirport()).append(queryIBEDetail.getBackDepAirport()).append(queryIBEDetail.getBackDepTime()).append(queryIBEDetail.getBackArrTime()).toString();
            QueryIBEDetail qe = queryIBEDetailMap.get(key);
            if (null == qe) {
                queryIBEDetailMap.put(key, queryIBEDetail);
            } else {
                qe.getCabinsPricesTotalses().addAll(queryIBEDetail.getCabinsPricesTotalses());
            }
            if (null != minqueryIBEDetail) {//获得最便宜航段价格
                if (minqueryIBEDetail.getCabinsPricesTotalses().size() > 0 && queryIBEDetail.getCabinsPricesTotalses().size() > 0) {
                    for (PassengerTypePricesTotal minpassengerTypePricesTotal : minqueryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
                        for (PassengerTypePricesTotal passengerTypePricesTotal : minqueryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
                            if ("ADT".equals(passengerTypePricesTotal.getPassengerType()) && "ADT".equals(minpassengerTypePricesTotal.getPassengerType())) {
                                if (passengerTypePricesTotal.getSalePrice().compareTo(minpassengerTypePricesTotal.getSalePrice()) < 0) {
                                    minqueryIBEDetail = queryIBEDetail;
                                }
                            }

                        }
                    }
                }
            } else {
                minqueryIBEDetail = queryIBEDetail;
            }
            for (CabinsPricesTotals cabinsPricesTotals:  queryIBEDetail.getCabinsPricesTotalses()) {
                for (PassengerTypePricesTotal passengerTypePricesTotal : cabinsPricesTotals.getPassengerTypePricesTotals()) {
                    if(null!=passengerTypePricesTotal.getSalePrice()){
                        passengerTypePricesTotal.setSalePrice(passengerTypePricesTotal.getSalePrice().add(profit));
                        passengerTypePricesTotal.setAddPrice(profit);
                    }
                }
            }
            getTsransferTime(queryIBEDetail);
        }
        if (null != minqueryIBEDetail) {
            insertBatchVoyages(minqueryIBEDetail, type);
        }
        List queryIBEDetailList = new ArrayList<QueryIBEDetail>();
        queryIBEDetailList.addAll(queryIBEDetailMap.values());
        return queryIBEDetailList;
    }
    
    /**
     * 添加最低价格
     *
     * @param queryIBEDetail
     */
    private void insertBatchVoyages(QueryIBEDetail queryIBEDetail, String type) {
        VoyageLowestPriceVo voyageLowestPrice = new VoyageLowestPriceVo();
        //SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd");
        voyageLowestPrice.setDepart(queryIBEDetail.getGoDepAirport());
        voyageLowestPrice.setArrive(queryIBEDetail.getGoArrAirport());
        String departDate = dateFormat.get().format(queryIBEDetail.getGoDepTime());
        //voyageLowestPrice.setDepartDate(fl.format(queryIBEDetail.getGoDepTime()));
        voyageLowestPrice.setDepartDate(departDate);
        if (2 == queryIBEDetail.getLegType().intValue()) {
            String backDate = dateFormat.get().format(queryIBEDetail.getBackDepTime());
            voyageLowestPrice.setDepartDate(departDate+ "/" + backDate);
        }
        voyageLowestPrice.setSource(type);
        voyageLowestPrice.setType(2);
        voyageLowestPrice.setVoyageType(queryIBEDetail.getLegType());
        for (PassengerTypePricesTotal passengerTypePricesTotal : queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
            if ("ADT".equals(passengerTypePricesTotal.getPassengerType())) {//只获取成人的价格
                voyageLowestPrice.setParPrice(passengerTypePricesTotal.getFare().doubleValue());
                voyageLowestPrice.setSalePrice(passengerTypePricesTotal.getSalePrice().doubleValue());
            }
        }
        List<VoyageLowestPriceVo> var1 = new ArrayList<VoyageLowestPriceVo>();
        var1.add(voyageLowestPrice);
        new Thread() {//异步调用新增最便宜航段
            public void run() {
                mssVoyagePriceService.insertBatchVoyages(var1);
            }
        }.start();
    }
    
    /**
     * 添加最地价格
     *
     * @param queryIBEDetail
     */
    private void getTsransferTime(QueryIBEDetail queryIBEDetail) {
        if (1 == queryIBEDetail.getLegType()) {//单程获取航班时间差
            if (queryIBEDetail.getFlights().size() > 1) {
                Flight tempflight = null;
                for (Flight flight : queryIBEDetail.getFlights()) {
                    if (null == tempflight) {
                        tempflight = flight;
                    } else {
                        long time = flight.getDepTime().getTime() - tempflight.getArrTime().getTime();
                        long min = time / 1000 / 60;
                        tempflight.setTransferTime(min / 60 + ":" + min % 60);
                        tempflight = flight;
                    }
                }
            }
        } else {//往返获取时间差
            Flight tempgoflight = null;
            Flight tempbackflight = null;
            if (queryIBEDetail.getFlights().size() > 2) {
                for (Flight flight : queryIBEDetail.getFlights()) {
                    if ("go".equals(flight.getDirection())) {
                        if (null == tempgoflight) {
                            tempgoflight = flight;
                        } else {
                            long time = flight.getDepTime().getTime() - tempgoflight.getArrTime().getTime();
                            long min = time / 1000 / 60;
                            tempgoflight.setTransferTime(min / 60 + ":" + min % 60);
                            tempgoflight = flight;
                        }
                    } else {
                        if (null == tempbackflight) {
                            tempbackflight = flight;
                        } else {
                            long time = flight.getDepTime().getTime() - tempbackflight.getArrTime().getTime();
                            long min = time / 1000 / 60;
                            tempbackflight.setTransferTime(min / 60 + ":" + min % 60);
                            tempbackflight = flight;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 封装api接口
     *
     * @param pageVo
     */
    @Override
    public Page<QueryIBEDetailVo> queryApi(Page<QueryIBEDetailVo> pageVo, RequestWithActor<FlightQueryRequest> flightQueryRequest) {
        Page<QueryIBEDetail> page = query(null, flightQueryRequest);
        List<QueryIBEDetailVo> QueryIBEDetailVoList = pageVo.getRecords();
        for (QueryIBEDetail queryIBEDetail : page.getRecords()) {
            QueryIBEDetailVo queryIBEDetailVo = copyQueryIBEDetail(queryIBEDetail);
            QueryIBEDetailVoList.add(queryIBEDetailVo);
        }
        return pageVo;
    }
    
    /**
     * 封装api数据
     *
     * @param queryIBEDetail
     */
    private QueryIBEDetailVo copyQueryIBEDetail(QueryIBEDetail queryIBEDetail) {
        QueryIBEDetailVo queryIBEDetailVo = new QueryIBEDetailVo();
        queryIBEDetailVo.setTicketAirline(queryIBEDetail.getTicketAirline());
        queryIBEDetailVo.setDirect(queryIBEDetail.isDirect());
        queryIBEDetailVo.setLegType(queryIBEDetail.getLegType());
        queryIBEDetailVo.setGoDepTime(queryIBEDetail.getGoDepTime());
        queryIBEDetailVo.setGoArrTime(queryIBEDetail.getGoArrTime());
        queryIBEDetailVo.setGoDepAirport(queryIBEDetail.getGoDepAirport());
        queryIBEDetailVo.setGoArrAirport(queryIBEDetail.getGoArrAirport());
        queryIBEDetailVo.setBackDepTime(queryIBEDetail.getBackDepTime());
        queryIBEDetailVo.setBackArrTime(queryIBEDetail.getBackArrTime());
        queryIBEDetailVo.setBackDepAirport(queryIBEDetail.getBackDepAirport());
        queryIBEDetailVo.setBackArrAirport(queryIBEDetail.getBackArrAirport());
        queryIBEDetailVo.setGoDuration(queryIBEDetail.getGoDuration());
        queryIBEDetailVo.setBackDuration(queryIBEDetail.getBackDuration());
        queryIBEDetailVo.setPriceSpecNo(queryIBEDetail.getPriceSpecNo());
        List<CabinsPricesTotalsVo> cabinsPricesTotalsVoList = new ArrayList<CabinsPricesTotalsVo>();
        for (CabinsPricesTotals cabinsPricesTotals : queryIBEDetail.getCabinsPricesTotalses()) {
            CabinsPricesTotalsVo cabinsPricesTotalsVo = copyCabinsPricesTotals(cabinsPricesTotals);
            cabinsPricesTotalsVoList.add(cabinsPricesTotalsVo);
        }
        queryIBEDetailVo.setCabinsPricesTotalses(cabinsPricesTotalsVoList);
        List<FlightVo> flightVoList = new ArrayList<FlightVo>();
        for (Flight flight : queryIBEDetail.getFlights()) {
           // FlightVo flightVo = copyFlight(flight);
            FlightVo flightVo = new FlightVo();
            BeanUtils.copyProperties(flight,flightVo);
            flightVoList.add(flightVo);
        }
        queryIBEDetailVo.setFlights(flightVoList);
        return queryIBEDetailVo;
    }
    
    /**
     * 封装api数据
     *
     * @param cabinsPricesTotals
     */
    private CabinsPricesTotalsVo copyCabinsPricesTotals(CabinsPricesTotals cabinsPricesTotals) {
        CabinsPricesTotalsVo cabinsPricesTotalsVo = new CabinsPricesTotalsVo();
        cabinsPricesTotalsVo.setCabins(cabinsPricesTotals.getCabins());
        cabinsPricesTotalsVo.setCabinsCount(cabinsPricesTotals.getCabinsCount());
        cabinsPricesTotalsVo.setTicketRule(cabinsPricesTotals.getTicketRule());
        List<PassengerTypePricesTotalVo> passengerTypePricesTotalVoList = new ArrayList<PassengerTypePricesTotalVo>();
        for (PassengerTypePricesTotal passengerTypePricesTotal : cabinsPricesTotals.getPassengerTypePricesTotals()) {
            PassengerTypePricesTotalVo passengerTypePricesTotalVo = copyPassengerTypePricesTotal(passengerTypePricesTotal);
            passengerTypePricesTotalVoList.add(passengerTypePricesTotalVo);
        }
        cabinsPricesTotalsVo.setPassengerTypePricesTotals(passengerTypePricesTotalVoList);
        return cabinsPricesTotalsVo;
    }
    
    /**
     * 封装api数据
     *
     * @param passengerTypePricesTotal
     */
    private PassengerTypePricesTotalVo copyPassengerTypePricesTotal(PassengerTypePricesTotal passengerTypePricesTotal) {
        PassengerTypePricesTotalVo passengerTypePricesTotalVo = new PassengerTypePricesTotalVo();
        passengerTypePricesTotalVo.setPassengerType(passengerTypePricesTotal.getPassengerType());
        passengerTypePricesTotalVo.setFare(passengerTypePricesTotal.getFare());
        passengerTypePricesTotalVo.setSalePrice(passengerTypePricesTotal.getSalePrice());
        passengerTypePricesTotalVo.setTax(passengerTypePricesTotal.getTax());
        passengerTypePricesTotalVo.setFavorable(passengerTypePricesTotal.getFavorable());
        passengerTypePricesTotalVo.setPriceNo(passengerTypePricesTotal.getPriceNo());
        return passengerTypePricesTotalVo;
    }
    
    /**
     * 封装api数据
     *
     * @param
     */
   /* private FlightVo copyFlight(Flight flight) {
        FlightVo flightVo = new FlightVo();
        flightVo.setAirline(flight.getAirline());
        flightVo.setFlightNo(flight.getFlightNo());
        flightVo.setGrade(flight.getGrade());
        flightVo.setBaggage(flight.getBaggage());
        flightVo.setDepTime(flight.getDepTime());
        flightVo.setArrTime(flight.getArrTime());
        flightVo.setArrAirport(flight.getArrAirport());
        flightVo.setDepAirport(flight.getDepAirport());
        flightVo.setDepTerminal(flight.getDepTerminal());
        flightVo.setArrTerminal(flight.getArrTerminal());
        flightVo.setTpm(flight.getTpm());
        flightVo.setDuration(flight.getDuration());
        flightVo.setDirection(flight.getDirection());
        flightVo.setCodeshare(flight.getCodeshare());
        flightVo.setEquipment(flight.getEquipment());
        flightVo.setTicketType(flight.getTicketType());
        flightVo.setTransferTime(flight.getTransferTime());
        flightVo.setPolicyNo(flight.getPolicyNo());
        flightVo.setPolicyVersion(flight.getPolicyVersion());
        flightVo.setStopOverAirport(flight.getStopOverAirport());
        flightVo.setStopOverDuration(flight.getStopOverDuration());
        flightVo.setFlightNum(flight.getFlightNum());
        return flightVo;
    }*/
    
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(8);
        pool.setMaxPoolSize(30);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.initialize();
        return pool;
    }
    
    private long getDateTime(String time) {
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer dateTime = new StringBuffer(fl.format(new Date()));
        dateTime.append(" ");
        dateTime.append(time);
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return f2.parse(dateTime.toString()).getTime();
        } catch (ParseException e) {
            log.error("getDateTime", e);
            return 0l;
        }
    }
    
    /**
     * 国际机票三次控润
     *
     * @param passengerTypePricesTotal
     * @param agent
     */
    private void runControl(PassengerTypePricesTotal passengerTypePricesTotal, Agent agent) {
        SubControlRule subControlRule = new SubControlRule();
        subControlRule.setSubNo(agent.getNum());
        subControlRule.setProId((int) passengerTypePricesTotal.getPriceNo());
        List<SubControlRule> subControlRuleList = subControlRuleService.selectSubControlRuleByMap(subControlRule);
        if (subControlRuleList != null && subControlRuleList.size() > 0) {
            for (SubControlRule bean : subControlRuleList) {
               //按区间控
                if (bean.getType() == 1) {
                    if (passengerTypePricesTotal.getSalePrice().floatValue() >= bean.getStart() && passengerTypePricesTotal.getSalePrice().floatValue() <= bean.getEnd()) {
                        passengerTypePricesTotal.setSalePrice(passengerTypePricesTotal.getSalePrice().add(new BigDecimal(bean.getPoint())));
                        break;
                    }
                } else {
                    //固定值
                    if (bean.getType() == 2)
                        passengerTypePricesTotal.setSalePrice(passengerTypePricesTotal.getSalePrice().add(new BigDecimal(bean.getPoint())));
                    break;
                }
            }
        }
    }

    /**
     * 国际机票二次控润
     *
     *
     * @param agent
     */
    public BigDecimal getProfitByAgent(Agent agent) {
        ProfitVO profitVO = new ProfitVO();
        Customer customer = customerService.getCustomerByNo(agent, agent.getNum());
        if(null==customer){
            return new BigDecimal(0);
        }
        profitVO.setOwner(agent.getOwner());
        profitVO.setCustomerTypeNo(customer.getCustomerTypeNo());
        List<Profit>  profits= profitDao.queryProfit(profitVO);
        if (profits != null && profits.size() > 0) {
            for (Profit bean : profits) {
                if(null!=bean.getCustomerNo()&&bean.getCustomerNo().equals(agent.getNum())){
                    if(null!=bean.getRaisePrice()){
                        return  bean.getRaisePrice();
                    }
                }else if(bean.getCustomerNo()==null){
                    if(null!=bean.getRaisePrice()){
                        return  bean.getRaisePrice();
                    }
                }else{
                    return new BigDecimal(0);
                }
            }
        }
        return new BigDecimal(0);
    }
}

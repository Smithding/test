package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.dao.Ift_cabinDao;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.service.FliePriceMappingService;
import com.tempus.gss.product.ift.dao.FilePriceDao;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 杨威 on 2017/8/30.
 */
@Service
public class FliePriceMappingServicelmpl implements FliePriceMappingService {
    /*航班基础数据服务*/
    @Reference
    private IAirportService airportService;
    /*政策服务*/
    @Autowired
    private FilePriceDao filePriceDao;
    @Autowired
    Ift_cabinDao ift_cabinDao;
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void exchangeFilePrice(QueryIBEDetail queryIBEDetail, Agent agent){
        for (PassengerTypePricesTotal passengerTypePricesTotal : queryIBEDetail.getCabinsPricesTotalses().get(0).getPassengerTypePricesTotals()) {
            String passengerType = passengerTypePricesTotal.getPassengerType();
            List<Flight> goFlights = new ArrayList<Flight>();
            List<Flight> backFlights = new ArrayList<Flight>();
            for (Flight flight : queryIBEDetail.getFlights()) {//得到去程航班
                if ("go".equals(flight.getDirection())) {
                    goFlights.add(flight);
                } else {
                    backFlights.add(flight);
                }
            }
            Map map = mappingPolicy(queryIBEDetail.getGoDepOD(), queryIBEDetail.getGoArrOD(), queryIBEDetail.getBackArrOD(), queryIBEDetail.getTicketAirline(), passengerType,
                    goFlights, backFlights, agent);
            if(null!=map){
                BigDecimal fare = (BigDecimal) map.get("fileCabiniPrice");
                if(null!=fare) {
                    passengerTypePricesTotal.setFare(fare);
                }
                if (2 == queryIBEDetail.getLegType()) {//往返政策要根据总则的计算方式计算
                    fare = (BigDecimal) map.get("backFileCabiniPrice");
                    if(null!=fare){
                        passengerTypePricesTotal.setFare(passengerTypePricesTotal.getFare().add(fare));
                    }
                }
            }
        }
    }


    /**
     * 获取往返城最佳返点
     *
     * @param goDep, goArr, backArr, airline, passengerTypeMap ,goFlights, backFlights, customerTypeNo
     * @return Map
     */
    @Override
    public Map mappingPolicy(String goDep, String goArr, String backArr, String airline, String passengerType, List<Flight> goFlights, List<Flight> backFlights, Agent agent) {
        List<FilePrice> goPolicyList = new ArrayList<FilePrice>();
        FilePrice filePrice=  new FilePrice();
        filePrice.setGoStart("%" + goDep + "%");
        filePrice.setGoEnd("%" + goArr + "%");
        filePrice.setAirline(airline);
        filePrice.setTravellerType("%" + passengerType + "%");
        filePrice.setOwner(agent.getOwner());
        filePrice.setPolicyType("(1,2,3,4)");
        try {
            List<FilePrice> policys = filePriceDao.queryObjByOD(filePrice);
            goPolicyList.addAll(policys);
        } catch (Exception e) {
            log.error("mappingPolicy", e);
        }

        Flight goflight = getMainFlight(goFlights);
        for (int i = goPolicyList.size() - 1; i >= 0; i--) {//
            FilePrice policy = goPolicyList.get(i);
            if (!checkPolicyDate(policy, goflight, false) || !checkPolicyFlight(policy, goflight, false)) {
                goPolicyList.remove(i);
            }
        }

        List<Map> goMapList = mappingCabins(goPolicyList, goflight);
        if (null != backArr && !"".equals(backArr)) {//取返程政策
            Airport backArrAirport = airportService.queryAirportByCode(backArr, "I");
            if (null == backArrAirport) {
                backArrAirport = airportService.queryAirportByCode(backArr, "D");
            }
            String backArrArea = backArrAirport.getAreaCode();
            List<FilePrice> backPolicyList = new ArrayList<FilePrice>();
            filePrice=  new FilePrice();
            filePrice.setGoStart("%" + goArr + "%");
            filePrice.setGoEnd("%" + backArr + "%");
            filePrice.setAirline(airline);
            filePrice.setTravellerType("%" + passengerType + "%");

            filePrice.setOwner(agent.getOwner());
            try {
                List<FilePrice> policys = filePriceDao.queryObjByOD(filePrice);//根据机场取返程政策
                backPolicyList.addAll(policys);
            } catch (Exception e) {
                log.error("mappingPolicy", e);
            }
            Flight backFlight = getMainFlight(backFlights);
            for (int i = backPolicyList.size() - 1; i >= 0; i--) {//根据限制条件去除政策
                FilePrice policy = backPolicyList.get(i);
                if (!checkPolicyDate(policy, backFlight, true) ||
                        !checkPolicyFlight(policy, backFlight, true) ) {
                    backPolicyList.remove(i);
                }
            }
            List<Map> flightMapList = new ArrayList<Map>();
            List<Map> backMapList = mappingCabins(backPolicyList, backFlight);//匹配仓位
            if (goMapList.size() < 1) {
                if (backMapList.size() < 1) {
                    return null;
                } else {
                    FilePrice backFilePrice = (FilePrice) backMapList.get(0).get("filePrice");//获取最优政策
                    Map map = new HashMap<String, Object>();
                    map.put("backFilePrice", backFilePrice);
                    map.put("backFileCabiniPrice", backMapList.get(0).get("fileCabiniPrice"));
                    return map;
                }
            }else if (backMapList.size() < 1) {
                Map map = goMapList.get(0);
                return map;
            }
            for (Map goMap : goMapList) {//跨航线组合匹配
                for (Map backMap : backMapList) {
                    Map map = new HashMap();
                    map.put("goMap", goMap);
                    map.put("backMap", backMap);
                    flightMapList.add(map);
                }
            }
            for (int i = 0; i < flightMapList.size(); i++) {//对比返点排序倒序排列
                for (int j = i; j < flightMapList.size(); j++) {
                    Map mapi = flightMapList.get(i);
                    BigDecimal saleRebateI = ((BigDecimal) ((Map) mapi.get("goMap")).get("fileCabiniPrice")).add((BigDecimal) ((Map) mapi.get("backMap")).get("fileCabiniPrice"));
                    Map mapj = flightMapList.get(j);
                    BigDecimal saleRebateJ = ((BigDecimal) ((Map) mapj.get("goMap")).get("fileCabiniPrice")).add((BigDecimal) ((Map) mapj.get("backMap")).get("fileCabiniPrice"));
                    if (saleRebateI.compareTo(saleRebateJ) < 1) {
                        Map map = flightMapList.get(i);
                        flightMapList.set(i, flightMapList.get(j));
                        flightMapList.set(i, map);
                    }
                }
            }
            Map map = flightMapList.get(0);//取最高返点
            Map goMap = (Map) map.get("goMap");
            goMap.put("backPolicy", ((Map) map.get("backMap")).get("backFilePrice"));
            goMap.put("backFileCabiniPrice", ((Map) map.get("backMap")).get("fileCabiniPrice"));
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
    private boolean checkPolicyDate(FilePrice policy, Flight goflight, boolean isback) {
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
        return check;
    }

    /**
     * 检验政策航段
     *
     * @param policy goflight isback
     * @return boolean
     */
    private boolean checkPolicyFlight(FilePrice policy, Flight goflight, boolean isback) {
        boolean check = true;
        if (!isback &&  null != policy.getExceptGoStart() &&policy.getExceptGoStart().contains(goflight.getDepAirport())) {
            return false;
        }
        if (!isback && null != policy.getExceptGoEnd() &&policy.getExceptGoEnd().contains(goflight.getArrAirport())) {
            return false;
        }
        if (isback && null != policy.getExceptGoEnd() &&policy.getExceptGoEnd().contains(goflight.getDepAirport())) {
            return false;
        }
        if (isback && null != policy.getExceptBackEnd() &&policy.getExceptBackEnd().contains(goflight.getArrAirport())) {
            return false;
        }
        if (!isback &&null != policy.getGoNotFlight() && (policy.getGoNotFlight().contains(goflight.getArrAirport()) || (null != policy.getGoNotFlight() && policy.getGoNotFlight().contains(goflight.getDepAirport())))) {
            return false;
        }
        if (!isback && null!=policy.getGoMustFlight()&&!"".equals(policy.getGoMustFlight())&&(!policy.getGoMustFlight().contains(goflight.getArrAirport()) || (null != policy.getGoMustFlight() &&!policy.getGoMustFlight().contains(goflight.getDepAirport())))) {
            return false;
        }
        if (isback && null != policy.getBackNotFlight() &&policy.getBackNotFlight().contains(goflight.getArrAirport()) || (null != policy.getBackNotFlight() &&policy.getBackNotFlight().contains(goflight.getDepAirport()))) {
            return false;
        }
        if (isback && null!=policy.getGoMustFlight()&&!"".equals(policy.getGoMustFlight())&&(!policy.getBackMustFlight().contains(goflight.getArrAirport()) ||  (null != policy.getBackNotFlight() &&!policy.getBackMustFlight().contains(goflight.getDepAirport())))) {
            return false;
        }
        if (!isback && null!=policy.getGoFlightNo()&&!"".equals(policy.getGoFlightNo())&&!policy.getGoFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (isback && null!=policy.getGoFlightNo()&&!"".equals(policy.getGoFlightNo())&&!policy.getBackFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (null!=policy.getGoFlightNo()&&policy.getGoFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        if (null!=policy.getGoFlightNo()&&policy.getGoFlightNo().contains(goflight.getFlightNo())) {
            return false;
        }
        return check;
    }
    /**
     * 检验政策仓位
     *
     * @param policys goflight customerTypeNo
     * @return List<Map>
     */
    private List<Map> mappingCabins(List<FilePrice> policys, Flight goflight) {
        List<Map> mapList = new ArrayList<Map>();
        for (FilePrice policy : policys) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("policy", policy);
            List<Ift_cabin>  cabinList = ift_cabinDao.selectCabinByPolicyNo(policy.getPolicyNo());
            for (Ift_cabin cabin : cabinList) {
                if (cabin.getCabin().equals(goflight.getFlightCabinPriceVos().get(0).getCabin())&&false!=goflight.getFlightCabinPriceVos().get(0).isAward()) {
                    map.put("fileCabiniPrice", cabin.getPrice());
                }
            }
            if(null!=map.get("fileCabiniPrice")){
                mapList.add(map);
            }
        }
        for (int i = 0; i < mapList.size(); i++) {//找出做优政策
            for (int j = i; j < mapList.size(); j++) {
                if (((BigDecimal) mapList.get(i).get("fileCabiniPrice")).compareTo((BigDecimal) mapList.get(j).get("fileCabiniPrice")) < 1) {
                    Map map = mapList.get(i);
                    mapList.set(i, mapList.get(j));
                    mapList.set(j, map);
                }
            }
        }
        return mapList;
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
            if(null==depAirport){
                log.info("depAirport机场三字码："+flight.getDepAirport());
            }
            if(null==arrAirport){
                log.info("arrAirport机场三字码："+flight.getArrAirport());
            }
            String depCountry = depAirport.getCountryCode();
            String arrCountry = arrAirport.getCountryCode();
            if (!depCountry.equals(arrCountry)) {//判断是否是跨国段
                countryFlights.add(flight);
            }
        }
        for (Flight flight : countryFlights) {
            if (Tpm == 0 || Tpm < flight.getTpm()) {//根据里程判断主航段
                Tpm=flight.getTpm();
                mainFlight = flight;
            }
        }
        return mainFlight;
    }
    private long getDateTime(String time){
        SimpleDateFormat fl = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer dateTime=new StringBuffer(fl.format(new Date()));
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
}

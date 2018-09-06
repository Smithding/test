package com.tempus.gss.product.ift.service.search;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.cps.service.ICustomerService;
import com.tempus.gss.cps.service.ISubControlRuleService;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.mss.entity.vo.VoyageLowestPriceVo;
import com.tempus.gss.mss.service.IMssVoyagePriceService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.CabinsPricesTotals;
import com.tempus.gss.product.ift.api.entity.Flight;
import com.tempus.gss.product.ift.api.entity.PassengerTypePricesTotal;
import com.tempus.gss.product.ift.api.entity.QueryIBEDetail;
import com.tempus.gss.product.ift.api.entity.vo.FlightQueryRequest;
import com.tempus.gss.product.ift.api.service.FliePriceMappingService;
import com.tempus.gss.product.ift.api.service.IMultipassMappingService;
import com.tempus.gss.product.ift.api.service.IPolicyRService;
import com.tempus.gss.product.ift.api.service.IPolicyService;
import com.tempus.gss.product.ift.api.service.search.IftFlightQueryService;
import com.tempus.gss.product.ift.api.service.search.NewQueryService;
import com.tempus.gss.product.ift.dao.ProfitDao;
import com.tempus.gss.util.JsonUtil;
import com.tempus.gss.vo.Agent;
import com.tempus.tbd.service.IAirportService;
import com.tempus.tbe.entity.AvailableJourney;
import com.tempus.tbe.entity.ConnectionLocation;
import com.tempus.tbe.entity.PassengerTypeQuantity;
import com.tempus.tbe.entity.ShoppingInput;
import com.tempus.tbe.entity.ShoppingOutPut;
import com.tempus.tbe.entity.ShoppingSeg;
import com.tempus.tbe.service.IShoppingService;

/**
 * Created by 杨威 on 2016/10/12.
 */
@Service
@EnableAutoConfiguration
public class NewQueryServiceImpl implements NewQueryService {
    /** 日志记录器. */
	protected static final Logger log = LogManager.getLogger(NewQueryServiceImpl.class);
    private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return  new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
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
    @Reference(version="dev-yj")
    IftFlightQueryService iftFlightQueryService;
    @Autowired
    private IftFlightQueryUtils iftFlightQueryUtils;
    
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
    public Page<QueryIBEDetail> query(Page<QueryIBEDetail> page, RequestWithActor<FlightQueryRequest> flightQueryRequest) {
        FlightQueryRequest flightQuery = flightQueryRequest.getEntity();
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
        System.out.println("完成调用shopping接口"+JsonUtil.toJson(shoppingOutPut));
        List<AvailableJourney> availableJourneys = shoppingOutPut.getAvailableJourneys();
        if (null == availableJourneys || availableJourneys.size() < 1) {
            return null;
        }
        List<QueryIBEDetail> queryIBEDetailList = null;
        log.info("开始调用shopping数据转换");
        try {
        	iftFlightQueryUtils = new IftFlightQueryUtils();
            queryIBEDetailList = iftFlightQueryUtils.shoppingOutPutConvertQueryIBEDetails(shoppingOutPut);
        } catch (ParseException e) {
            log.error("开始调用shopping数据转换", e);
        }
        log.info("开始匹配政策");
        List<QueryIBEDetail> newQueryIBEDetailList = new ArrayList<>();
        for (QueryIBEDetail queryIBEDetail : queryIBEDetailList) {
        	QueryIBEDetail detail = iftFlightQueryService.mappingPriceSpec(queryIBEDetail,flightQueryRequest,flightQuery.getCustomerType(), agent);
                System.out.println("sgsyy"+JsonUtil.toJson(detail));
                newQueryIBEDetailList.add(detail);
        }
        log.info("开始组装数据");
        queryIBEDetailList = combineQueryIBEDetails(newQueryIBEDetailList, flightQueryRequest.getAgent().getDevice(),agent);
        log.info("完成组装数据");
        log.info("sdgsd"+JsonUtil.toJson(queryIBEDetailList));
        pages.setRecords(queryIBEDetailList);
        return pages;
    }
    /**
     * 合并行程相同的数据
     *
     * @param queryIBEDetails List<QueryIBEDetail>
     */
    private List<QueryIBEDetail> combineQueryIBEDetails(List<QueryIBEDetail> queryIBEDetails, String type,Agent agent) {
        Map<String, QueryIBEDetail> queryIBEDetailMap = new HashMap<String, QueryIBEDetail>();
        BigDecimal profit= new BigDecimal(0);
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
                                if (passengerTypePricesTotal.getSalePrice()!=null &&minpassengerTypePricesTotal.getSalePrice()!=null && passengerTypePricesTotal.getSalePrice().compareTo(minpassengerTypePricesTotal.getSalePrice()) < 0) {
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
        List<QueryIBEDetail> queryIBEDetailList = new ArrayList<QueryIBEDetail>();
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
}

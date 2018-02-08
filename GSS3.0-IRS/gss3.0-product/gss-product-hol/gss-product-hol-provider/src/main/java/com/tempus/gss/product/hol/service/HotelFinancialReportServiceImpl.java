package com.tempus.gss.product.hol.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HotelFinancialReport;
import com.tempus.gss.product.hol.api.entity.vo.QueryFinancialReportVo;
import com.tempus.gss.product.hol.api.service.IHotelFinancialReportService;
import com.tempus.gss.product.hol.dao.HotelFinancialReportMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HotelFinancialReportServiceImpl implements IHotelFinancialReportService{
    @Autowired
    HotelFinancialReportMapper mapper;

    private QueryFinancialReportVo buildQuery(String saleOrderNo, String orderStatus, String supplierOrderNo, Integer payWay, String reservePerson, String checkinPerson, String checkinCity, String hotelName, String minReserveDate,  String maxReserveDate,String checkinDate, String checkoutDate,String depDateBegin,String depDateOver) {
        return new QueryFinancialReportVo(saleOrderNo,  orderStatus,  supplierOrderNo,  payWay,  reservePerson,  checkinPerson,  checkinCity,  hotelName,  minReserveDate, maxReserveDate, checkinDate,  checkoutDate,depDateBegin,depDateOver);
    }

    @Override
    public Page<HotelFinancialReport> selectHotelFinancialReport(Page<HotelFinancialReport> page, String saleOrderNo,String orderStatus,String supplierOrderNo,
                                                                 Integer payWay,String reservePerson,String checkinPerson,String checkinCity,String hotelName,
                                                                 String minReserveDate,String maxReserveDate,String checkinDate,String checkoutDate,String depDateBegin,String depDateOver) {
        if (null == page) {
            return  null;
        }
        QueryFinancialReportVo query= buildQuery(saleOrderNo,  orderStatus,  supplierOrderNo,  payWay,  reservePerson,  checkinPerson,  checkinCity,  hotelName,  minReserveDate, maxReserveDate, checkinDate,  checkoutDate,depDateBegin,depDateOver);

        List<HotelFinancialReport> hotelFinancialReports = mapper.queryHotelFinancialReport(page,query);
        if(null!=hotelFinancialReports){
            for(HotelFinancialReport hotelFinancialReport:hotelFinancialReports){
                if(null!=hotelFinancialReport.getCity()&&!"".equals(hotelFinancialReport.getCity())) {
                    String[] split1 = hotelFinancialReport.getCity().split("市");
                    if(split1.length>1) {
                        hotelFinancialReport.setCity(split1[0]);
                    }else {
                        hotelFinancialReport.setCity(null);
                    }
                }
                if(null!=hotelFinancialReport.getSalePrices()){
                    String[] split=hotelFinancialReport.getSalePrices().split(",");
                    hotelFinancialReport.setSalePrice(new BigDecimal(split[0]));
                    hotelFinancialReport.setSettlePrice(new BigDecimal(split[0]));
                }else {
                    hotelFinancialReport.setSalePrice(new BigDecimal(0));
                    hotelFinancialReport.setSettlePrice(new BigDecimal(0));
                }
                hotelFinancialReport.setSettleTotalPrice(hotelFinancialReport.getSaleTotalPrice());
                if(null==hotelFinancialReport.getUpCommission()){
                   hotelFinancialReport.setUpCommission(new BigDecimal(0));
                }
                hotelFinancialReport.setReceivableCommission(hotelFinancialReport.getUpCommission().multiply(hotelFinancialReport.getSettleTotalPrice()));
                if(null==hotelFinancialReport.getRebatePrice()){
                    hotelFinancialReport.setRebatePrice(new BigDecimal(0));
                }
                if(null==hotelFinancialReport.getSaleTotalPrice()){
                    hotelFinancialReport.setSaleTotalPrice(new BigDecimal(0));
                }
                if(null==hotelFinancialReport.getReceivableCommission()){
                    hotelFinancialReport.setReceivableCommission(new BigDecimal(0));
                }
                hotelFinancialReport.setGross(hotelFinancialReport.getReceivableCommission().subtract(hotelFinancialReport.getRebatePrice()));
                hotelFinancialReport.setOpenAccountName(hotelFinancialReport.getReservcePerson());
                if(null!=hotelFinancialReport.getDownCommission()){
                    hotelFinancialReport.setDownCommission(hotelFinancialReport.getDownCommission().divide(new BigDecimal(100)));
                }else {
                    hotelFinancialReport.setDownCommission(new BigDecimal(0));
                }
            }
        }
        page.setRecords(hotelFinancialReports);
        return page;
    }


}

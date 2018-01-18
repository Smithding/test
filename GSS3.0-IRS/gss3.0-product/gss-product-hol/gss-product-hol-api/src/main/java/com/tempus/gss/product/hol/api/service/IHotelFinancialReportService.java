package com.tempus.gss.product.hol.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.HotelFinancialReport;
import com.tempus.gss.product.hol.api.entity.vo.QueryFinancialReportVo;

public interface IHotelFinancialReportService {
    Page<HotelFinancialReport> selectHotelFinancialReport(Page<HotelFinancialReport> page,String saleOrderNo,String orderStatus,String supplierOrderNo,
                                                          Integer payWay,String reservePerson,String checkinPerson,String checkinCity,String hotelName,
                                                          String minReserveDate, String maxReserveDate,String checkinDate,String checkoutDate,String depDateBegin,String depDateOver);


}

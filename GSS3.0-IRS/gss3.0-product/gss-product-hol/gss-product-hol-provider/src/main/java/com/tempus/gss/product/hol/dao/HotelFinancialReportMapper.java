package com.tempus.gss.product.hol.dao;

import com.tempus.gss.product.hol.api.entity.HotelFinancialReport;
import com.tempus.gss.product.hol.api.entity.vo.QueryFinancialReportVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HotelFinancialReportMapper {
    public List<HotelFinancialReport> queryHotelFinancialReport(RowBounds page, QueryFinancialReportVo query);
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GzOutReport;
import com.tempus.gss.product.ift.api.entity.GzOutReportParams;

import java.util.List;
import java.util.Map;

public interface IGZOutTicketService {

    Page<GZOutTicket> selectGZOutTicketReport(Page<GZOutTicket> page, String air,String ticketNo, String pnr,String cabin,String beginTicketDate, String overTicketDate,String beginFlyDate, String overFlyDate, String depPlace,String arrPlace,String supplier,String customer);

    Page<GzOutReport> queryIssueRecords(Page<GzOutReport> page, GzOutReportParams params);

    List<Map<String,Object>> queryIssueRecordsSum(GzOutReportParams params);
}

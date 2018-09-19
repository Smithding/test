package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GzOutReport;
import com.tempus.gss.product.ift.api.entity.GzOutReportParams;
import com.tempus.gss.product.ift.api.entity.vo.QueryGZOutReportVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface GZOutReportDao {
    
    List<GZOutTicket> queryGZOutReport(RowBounds page, QueryGZOutReportVo query);

    List<GzOutReport> queryIssueRecords(Page<GzOutReport> page, GzOutReportParams params);

    List<Map<String,Object>> queryIssueRecordsSum(GzOutReportParams params);
}

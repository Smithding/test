package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IFTIssueReport;
import com.tempus.gss.product.ift.api.entity.IFTIssueReportParams;
import com.tempus.gss.product.ift.api.entity.vo.ReportRefundVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;

import java.util.List;

/**
 * 国际机票退票报表服务
 *
 * @author ZhangBro
 */
public interface ReportService {
    /**
     * 查询国际退票列表
     */
    Page<ReportRefundVo> getAllRefund(Page<ReportRefundVo> page, ReportVo reportIn);
    
    List<ReportVo> queryReportRecords(ReportVo reportIn);
    
    List<ReportRefundVo> getAllRefundRecords(ReportVo reportVo);
    
    List<String> queryIssueCabins();
    
    List<String> queryIssueDeptNameList();
    
    Page<IFTIssueReport> queryIssueRecords(Page<IFTIssueReport> page, IFTIssueReportParams params);
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.vo.ReportRefundVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;

import java.util.List;

/**
 * 国际机票退票报表服务
 */
public interface ReportService {
    /**
     * 查询国际退票列表
     */
    Page<ReportRefundVo> getAllRefund(Page<ReportRefundVo> page, ReportVo reportIn);

    List<ReportVo> queryReportRecords(ReportVo reportIn);

    List<ReportRefundVo> getAllRefundRecords(ReportVo reportVo);
}

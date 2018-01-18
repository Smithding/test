package com.tempus.gss.product.hol.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.vo.ReportVO;

/**
 * 酒店客户账单报表服务
 */
public interface IHolCustomerBillReportService {
    /**
     * 导出报表之  酒店 客户账单表
     * 入参：ReportVO
     *
     * @param page
     * @param reportIn
     * @return 工作表  提供给控制器返回下载页面
     */
    
    Page<ReportVO> holCustomerBillReport(Page<ReportVO> page, ReportVO reportIn);
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;

/**
 * 国际机票退票报表服务
 */
public interface ReportService {
    /**
     * 查询国际退票列表
     */
    Page<ReportVo> getAllRefund(Page<ReportVo> page, ReportVo reportIn);
}

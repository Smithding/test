package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo;

import java.util.List;

public interface UnpSaleReportService {
    Page<UnpSaleReportVo> queryUnpSaleReports(Page<UnpSaleReportVo> page, SaleReportQueryVo SaleReportQueryVo);
}

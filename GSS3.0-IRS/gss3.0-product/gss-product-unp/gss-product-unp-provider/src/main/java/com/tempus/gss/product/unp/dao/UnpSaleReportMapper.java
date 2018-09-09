package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo;

import java.util.List;

public interface UnpSaleReportMapper {
    List<UnpSaleReportVo> queryUnpSaleReports(Page<UnpSaleReportVo> page, SaleReportQueryVo SaleReportQueryVo);
}

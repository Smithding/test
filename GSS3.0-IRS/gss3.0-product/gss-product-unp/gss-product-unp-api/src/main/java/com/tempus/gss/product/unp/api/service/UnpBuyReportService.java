package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.BuyReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpBuyReportVo;

public interface UnpBuyReportService {
    Page<UnpBuyReportVo> queryUnpBuyReports(Page<UnpBuyReportVo> page, BuyReportQueryVo buyReportQueryVo);
}

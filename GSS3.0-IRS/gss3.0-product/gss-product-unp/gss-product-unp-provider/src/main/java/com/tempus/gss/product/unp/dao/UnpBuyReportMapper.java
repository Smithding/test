package com.tempus.gss.product.unp.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.BuyReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpBuyReportVo;

import java.util.List;

public interface UnpBuyReportMapper {
    List<UnpBuyReportVo> queryUnpBuyReports(Page<UnpBuyReportVo> page, BuyReportQueryVo buyReportQueryVo);
}

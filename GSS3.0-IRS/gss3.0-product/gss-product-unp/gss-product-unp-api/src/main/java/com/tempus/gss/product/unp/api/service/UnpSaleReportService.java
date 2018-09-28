package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo;

import java.util.List;
/**
 * @author zhangcheng
 */
public interface UnpSaleReportService {
    /**
     *
     * 功能描述: 查询 销售报表
     *
     * @param: [page, saleReportQueryVo]
     * @return: com.baomidou.mybatisplus.plugins.Page<com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo>
     * @auther: zhangcheng
     * @date: 2018/9/13 16:14
     */
    Page<UnpSaleReportVo> queryUnpSaleReports(Page<UnpSaleReportVo> page, SaleReportQueryVo SaleReportQueryVo);
}

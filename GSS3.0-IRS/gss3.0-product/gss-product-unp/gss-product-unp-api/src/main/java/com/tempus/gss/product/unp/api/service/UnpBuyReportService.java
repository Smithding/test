package com.tempus.gss.product.unp.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.BuyReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpBuyReportVo;

/**
 * @author zhangcheng
 */
public interface UnpBuyReportService {
    /**
     *
     * 功能描述: 采购报表查询
     *
     * @param: [page, buyReportQueryVo]
     * @return: com.baomidou.mybatisplus.plugins.Page<com.tempus.gss.product.unp.api.entity.vo.UnpBuyReportVo>
     * @auther: zhangcheng
     * @date: 2018/9/13 16:16
     */
    Page<UnpBuyReportVo> queryUnpBuyReports(Page<UnpBuyReportVo> page, BuyReportQueryVo buyReportQueryVo);
}

package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpSaleReportVo;
import com.tempus.gss.product.unp.api.service.UnpSaleReportService;
import com.tempus.gss.product.unp.dao.UnpSaleReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.rmi.runtime.Log;

import java.util.List;
@Service
public class UnpSaleReportServiceImpl implements UnpSaleReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UnpSaleReportMapper unpSaleReportMapper;
    @Override
    public Page<UnpSaleReportVo> queryUnpSaleReports(Page<UnpSaleReportVo> page, SaleReportQueryVo saleReportQueryVo) {
        logger.info("传入查询参数:"+saleReportQueryVo);

        try {
            List<UnpSaleReportVo> unpSaleReportVos= unpSaleReportMapper.queryUnpSaleReports(page,saleReportQueryVo);
            page.setRecords(unpSaleReportVos);
        } catch (Exception e) {
           logger.error("Error",e);
        }
        return page;
    }
}

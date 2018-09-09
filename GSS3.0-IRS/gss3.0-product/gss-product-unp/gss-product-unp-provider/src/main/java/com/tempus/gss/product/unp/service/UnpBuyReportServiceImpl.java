package com.tempus.gss.product.unp.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.unp.api.entity.vo.BuyReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.SaleReportQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpBuyReportVo;
import com.tempus.gss.product.unp.api.service.UnpBuyReportService;
import com.tempus.gss.product.unp.dao.UnpBuyReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UnpBuyReportServiceImpl implements UnpBuyReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UnpBuyReportMapper unpBuyReportMapper;
    @Override
    public Page<UnpBuyReportVo> queryUnpBuyReports(Page<UnpBuyReportVo> page, BuyReportQueryVo buyReportQueryVo) {
        logger.info("查询参数:"+buyReportQueryVo);

        try {
            List<UnpBuyReportVo> unpBuyReportVos = unpBuyReportMapper.queryUnpBuyReports(page, buyReportQueryVo);
            page.setRecords(unpBuyReportVos);
        } catch (Exception e) {
            logger.error("UNP采购报表查询异常",e);
        }
        return  page;
    }
}

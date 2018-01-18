package com.tempus.gss.product.hol.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.vo.ReportVO;
import com.tempus.gss.product.hol.api.service.IHolCustomerBillReportService;
import com.tempus.gss.product.hol.dao.HolCustomerBillMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michel
 */
@Service
@EnableAutoConfiguration
public class HolCustomerBillReportServiceImpl implements IHolCustomerBillReportService {
    
    @Autowired
    HolCustomerBillMapper holCustomerBillMapper;
    
    private Logger log = LogManager.getLogger("HolCustomerBillReportImpl");
    
    /**
     * 客户订单报表导出服务实现类
     * 入参：ReportVO，即使查询全部也需要传一个空的ReportVO进来，不可传null
     *
     * @param reportIn
     * @return
     */
    @Override
    public Page<ReportVO> holCustomerBillReport(Page<ReportVO> page, ReportVO reportIn) {
        try {
            if (null == reportIn) {
                log.error("客户订单查询模块, 0909, 输入参数错误");
                throw new GSSException("客户订单查询模块", "0909", "输入参数错误");
            }
            List<ReportVO> reportOuts = new ArrayList<>();
            switch (reportIn.getQueryType()) {
                case 1:
                default: {
                    reportOuts = holCustomerBillMapper.queryInfos(page, reportIn);
                }
                break;
                case 2: {
                    reportOuts = holCustomerBillMapper.queryByCus(page, reportIn);
                }
                break;
                case 3: {
                    reportOuts = holCustomerBillMapper.queryByHolName(page, reportIn);
                }
                break;
                case 4: {reportOuts = holCustomerBillMapper.queryByCus(page, reportIn);}
                break;
            }
            for (ReportVO v : reportOuts) {
                v.setValues();
            }
            page.setRecords(reportOuts);
            return page;
        } catch (Exception e) {
            log.error("客户订单查询模块, 0901, 报表导出失败");
            e.printStackTrace();
            throw new GSSException("客户订单查询模块", "0901", "报表导出失败");
        }
        
    }
    
}

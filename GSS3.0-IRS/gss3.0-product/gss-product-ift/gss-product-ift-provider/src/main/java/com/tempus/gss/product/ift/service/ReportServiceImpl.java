package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.service.ReportService;
import com.tempus.gss.product.ift.dao.RefundReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Service
@EnableAutoConfiguration
public class ReportServiceImpl implements ReportService {
    @Autowired
    RefundReportDao refundReportDao;
    
    @Override
    public Page<ReportVo> getAllRefund(Page<ReportVo> page, ReportVo reportIn) {
        List<ReportVo> list = refundReportDao.getAll(page,reportIn);
//        for(ReportVo r:list){
//            r.setTicketNum();
//        }
//        Page<ReportVo> pageOut = new Page<>();
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ReportVo> queryReportRecords(ReportVo reportIn) {
        List<ReportVo> result = refundReportDao.queryReportRecords(reportIn);
        return result;
    }


}

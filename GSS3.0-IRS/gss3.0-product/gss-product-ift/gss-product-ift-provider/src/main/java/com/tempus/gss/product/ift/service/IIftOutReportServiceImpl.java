package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.IftOutReportVO;
import com.tempus.gss.product.ift.api.service.IIftOutReportService;
import com.tempus.gss.product.ift.dao.IftOutReportDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class IIftOutReportServiceImpl implements IIftOutReportService {
    
    @Autowired
    private IftOutReportDao iftOutReportDao;
    
    @Override
    public Page<IftOutReport> getReports(Page<IftOutReport> page, IftOutReportVO iftOutReportVo) {
        List<IftOutReport> list = iftOutReportDao.queryIftOutReport(page, iftOutReportVo);
        page.setRecords(list);
        return page;
    }
    
    @Override
    public List<String> selectDeptNameList() {
        return iftOutReportDao.selectDeptNameList();
    }
    
    @Override
    public List<String> getCabins() {
        return iftOutReportDao.getCabins();
    }
}

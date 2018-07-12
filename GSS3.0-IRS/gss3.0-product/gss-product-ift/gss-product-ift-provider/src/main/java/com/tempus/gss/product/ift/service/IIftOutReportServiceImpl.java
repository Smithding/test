package com.tempus.gss.product.ift.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.vo.QueryIftOutReportVo;
import com.tempus.gss.product.ift.api.service.IIftOutReportService;
import com.tempus.gss.product.ift.dao.IftOutReportDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class IIftOutReportServiceImpl implements IIftOutReportService {

    @Autowired
    private IftOutReportDao iftOutReportDao;

    private QueryIftOutReportVo buildQuery(String deptName,String beginDate,String OverDate,String date,String changeType){
        return new QueryIftOutReportVo(deptName,beginDate,OverDate,date,changeType);
    }
    @Override
    public Page<IftOutReport> selectAllIftReport(Page<IftOutReport> page, String deptName, String beginDate, String overDate,String changeType) {
        if (null == page) {
            return  null;
        }
        QueryIftOutReportVo queryIftOutReportVo=buildQuery(deptName,beginDate,overDate,null,changeType);
        List<IftOutReport> iftOutReports = iftOutReportDao.queryIftOutReport(page, queryIftOutReportVo);
        page.setRecords(iftOutReports);
        return page;
    }

    @Override
    public Page<IftOutReport> selectOneIftReport(Page<IftOutReport> page, String deptName, String date,String changeType,String beginDate,String overDate) {
        if (null == page) {
            return  null;
        }
        QueryIftOutReportVo queryIftOutReportVo=buildQuery(deptName,beginDate,overDate,date,changeType);
        List<IftOutReport> iftOutReports = iftOutReportDao.queryIftOutReport(page, queryIftOutReportVo);
        page.setRecords(iftOutReports);
        return page;
    }

    @Override
    public List<String> selectDept_nameList() {
        return iftOutReportDao.getDept_nameList();
    }
}

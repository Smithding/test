package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftOutReport;

import java.util.List;

public interface IIftOutReportService {
    //汇总表
    Page<IftOutReport> selectAllIftReport(Page<IftOutReport> page, String deptName, String beginDate, String overDate,String changeType);
    //日报表
    Page<IftOutReport> selectOneIftReport(Page<IftOutReport> page, String deptName, String date,String changeType);

    List<String> selectDept_nameList();

}

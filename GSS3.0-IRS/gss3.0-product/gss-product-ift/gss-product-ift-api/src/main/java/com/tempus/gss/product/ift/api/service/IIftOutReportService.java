package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.IftOutReportVO;

import java.util.List;

/**
 * @author ZhangBro
 */
public interface IIftOutReportService {
    
    /**
     * @param page
     * @param iftOutReportVo
     * @return
     */
    Page<IftOutReport> getReports(Page<IftOutReport> page, IftOutReportVO iftOutReportVo);
    
    /**
     * @return
     */
    List<String> selectDeptNameList();
    
    /**
     * @return
     */
    List<String> getCabins();
    
}

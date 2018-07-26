package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.IftOutReportVO;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IssueReportDao {
    
    List<IftOutReport> queryIftOutReport(RowBounds page, IftOutReportVO query);
    
    List<String> selectDeptNameList();
    
    List<String> getCabins();
}

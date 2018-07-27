package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.IFTIssueReport;
import com.tempus.gss.product.ift.api.entity.IFTIssueReportParams;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ZhangBro
 */
public interface IssueReportDao {
    
    List<IFTIssueReport> queryIftOutReport(RowBounds page, IFTIssueReportParams params);
    
    List<String> selectDeptNameList();
    
    List<String> getCabins();
}

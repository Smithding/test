package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.IFTIssueReport;
import com.tempus.gss.product.ift.api.entity.IFTIssueReportParams;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ZhangBro
 */
public interface IssueReportDao extends BaseDao<IFTIssueReport, IFTIssueReportParams> {
    
    /**
     * @param page
     * @param params
     *         IFTIssueReportParams 实体
     * @return list<IFTIssueReport>
     */
    List<IFTIssueReport> queryIFTIssueReport(RowBounds page, IFTIssueReportParams params);
    
    /**
     * @return list
     */
    List<String> queryIssueDeptNameList();
    
    /**
     * @return list
     */
    List<String> queryIssueCabins();
}

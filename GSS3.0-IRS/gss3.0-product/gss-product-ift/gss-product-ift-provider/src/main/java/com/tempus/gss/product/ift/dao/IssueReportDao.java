package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.IFTIssueReport;
import com.tempus.gss.product.ift.api.entity.IFTIssueReportParams;
import com.tempus.gss.product.ift.api.entity.IftBuyReport;

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
    List<IFTIssueReport> queryIFTIssued(Page<IFTIssueReport> page, IFTIssueReportParams params);
    
    List<IFTIssueReport> queryIFTChanged(Page<IFTIssueReport> page, IFTIssueReportParams params);
    
    /**
     * @return list
     */
    List<String> queryIssueDeptNameList();
    
    /**
     * @return list
     */
    List<String> queryIssueCabins();
    
    /**
     * 采购单报表数据查询
     */
    List<IftBuyReport> iftBuyReport(Page<IftBuyReport> page, IFTIssueReportParams iftOutReportVo);
    
    /**
     * 采购单报表数据查询  辅助查询  获取leg信息
     */
    IftBuyReport selectLegsByBuyOrderNo(String legNos);
}

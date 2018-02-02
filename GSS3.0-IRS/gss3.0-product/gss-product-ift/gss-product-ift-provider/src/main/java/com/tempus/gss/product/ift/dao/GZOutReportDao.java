package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.vo.QueryGZOutReportVo;
import com.tempus.gss.product.ift.api.entity.vo.QueryIftOutReportVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GZOutReportDao {


     List<GZOutTicket> queryGZOutReport(RowBounds page, QueryGZOutReportVo query);

}

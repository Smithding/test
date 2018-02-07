package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GZRefundTicket;
import com.tempus.gss.product.ift.api.entity.vo.QueryGZOutReportVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GZRefundReportDao {


     List<GZRefundTicket> queryGZRefundReport(RowBounds page, QueryGZOutReportVo query);

}

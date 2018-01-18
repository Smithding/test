package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.IftOutReport;
import com.tempus.gss.product.ift.api.entity.vo.QueryIftOutReportVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IftOutReportDao {


     List<IftOutReport> queryIftOutReport(RowBounds page, QueryIftOutReportVo query);

     List<String> getDept_nameList();
}

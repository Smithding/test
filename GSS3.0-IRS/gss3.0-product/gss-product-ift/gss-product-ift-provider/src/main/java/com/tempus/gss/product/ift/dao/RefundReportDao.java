package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RefundReportDao extends AutoMapper<ReportVo>{
   List<ReportVo> getAll(RowBounds page,ReportVo reportIn);

   List<ReportVo> queryReportRecords(ReportVo reportIn);
}
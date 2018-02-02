package com.tempus.gss.product.hol.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.hol.api.entity.vo.ReportVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("holCustomerBillMapper")
public interface HolCustomerBillMapper {
    /**
     * 按酒店查询
     * @param reportRequestVO
     */
    
    List<ReportVO> queryByHolName(Page<ReportVO> page, ReportVO reportRequestVO);
    
    /**
     *查询客户预定、按照分销商查询  reportRequestVO.queryType=4 按照分销商查询
     * @param reportRequestVO
     */
    
    List<ReportVO> queryByCus(Page<ReportVO> page, ReportVO reportRequestVO);
    
    /**
     * 传入ReportrequestVO对象，返回查询到的对象。
     *
     * @param reportRequestVO
     */
    
    List<ReportVO> queryInfos(Page<ReportVO> page, ReportVO reportRequestVO);
}

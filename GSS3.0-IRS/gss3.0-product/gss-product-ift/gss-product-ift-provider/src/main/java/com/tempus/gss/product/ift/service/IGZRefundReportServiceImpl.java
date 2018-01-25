package com.tempus.gss.product.ift.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GZRefundTicket;
import com.tempus.gss.product.ift.api.entity.vo.QueryGZOutReportVo;
import com.tempus.gss.product.ift.api.service.IGZOutTicketService;
import com.tempus.gss.product.ift.api.service.IGZRefundTicketService;
import com.tempus.gss.product.ift.dao.GZOutReportDao;
import com.tempus.gss.product.ift.dao.GZRefundReportDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class IGZRefundReportServiceImpl implements IGZRefundTicketService {

    @Autowired
    private GZRefundReportDao gzRefundReportDao;

    private QueryGZOutReportVo buildQuery(String air, String ticketNo, String pnr, String cabin, String beginTicketDate,String overTicketDate,String beginFlyDate,String overFlyDate,String depPlace,String arrPlace,String supplier,String customer){
        return new QueryGZOutReportVo(air,ticketNo,pnr,cabin,beginTicketDate,overTicketDate,beginFlyDate,overFlyDate,depPlace,arrPlace,supplier,customer);
    }
    @Override
    public Page<GZRefundTicket> selectGZRefundTicketReport(Page<GZRefundTicket> page, String air, String ticketNo, String pnr, String cabin, String beginTicketDate, String overTicketDate, String beginFlyDate, String overFlyDate, String depPlace,String arrPlace, String supplier, String customer) {
        if (null == page) {
            return  null;
        }
        QueryGZOutReportVo queryGZOutReportVo=buildQuery(air,ticketNo,pnr,cabin,beginTicketDate,overTicketDate,beginFlyDate,overFlyDate,depPlace,arrPlace,supplier,customer);
        List<GZRefundTicket> gZOutReports = gzRefundReportDao.queryGZRefundReport(page, queryGZOutReportVo);
        if(null!=gZOutReports&&gZOutReports.size()>0){
            for(GZRefundTicket gZOutReport:gZOutReports){
                gZOutReport.setDepartDate(getYMDDate(gZOutReport.getDepartDate()));
                gZOutReport.setRefundDate(getYMDDate(gZOutReport.getRefundDate()));
                if(null!=gZOutReport.getPassengerType()) {
                    switch (gZOutReport.getPassengerType()) {
                        case "ADT":
                            gZOutReport.setPassengerType("成人");
                            break;
                        case "CHD":
                            gZOutReport.setPassengerType("儿童");
                            break;
                        case "INF":
                            gZOutReport.setPassengerType("婴儿");
                            break;
                        default:
                            break;
                    }
                }

                gZOutReport.setSettlePrice(gZOutReport.getSettlePrice()==null?0d:gZOutReport.getSettlePrice());//结算净价
                gZOutReport.setTax(gZOutReport.getTax()==null?0d:gZOutReport.getTax());//税金

            }
        }
        page.setRecords(gZOutReports);
        return page;
    }

    private String getYMDDate(String date){
        String[] split=null;
        if(null!=date&&!"".equals(date)){
            split = date.split(" ");
            return split[0];
        }
        return null;
    }

}

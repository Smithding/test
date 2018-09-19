package com.tempus.gss.product.ift.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.GZOutTicket;
import com.tempus.gss.product.ift.api.entity.GzOutReport;
import com.tempus.gss.product.ift.api.entity.GzOutReportParams;
import com.tempus.gss.product.ift.api.entity.vo.QueryGZOutReportVo;
import com.tempus.gss.product.ift.api.service.IGZOutTicketService;
import com.tempus.gss.product.ift.dao.GZOutReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IGZOutReportServiceImpl implements IGZOutTicketService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GZOutReportDao gzOutReportDao;

    private QueryGZOutReportVo buildQuery(String air, String ticketNo, String pnr, String cabin, String beginTicketDate,String overTicketDate,String beginFlyDate,String overFlyDate,String depPlace,String arrPlace,String supplier,String customer){
        return new QueryGZOutReportVo(air,ticketNo,pnr,cabin,beginTicketDate,overTicketDate,beginFlyDate,overFlyDate,depPlace,arrPlace,supplier,customer);
    }
    @Override
    public Page<GZOutTicket> selectGZOutTicketReport(Page<GZOutTicket> page, String air, String ticketNo, String pnr, String cabin, String beginTicketDate, String overTicketDate, String beginFlyDate, String overFlyDate, String depPlace,String arrPlace, String supplier, String customer) {
        if (null == page) {
            return  null;
        }
        QueryGZOutReportVo queryGZOutReportVo=buildQuery(air,ticketNo,pnr,cabin,beginTicketDate,overTicketDate,beginFlyDate,overFlyDate,depPlace,arrPlace,supplier,customer);
        List<GZOutTicket> gZOutReports = gzOutReportDao.queryGZOutReport(page, queryGZOutReportVo);
        if(null!=gZOutReports&&gZOutReports.size()>0){
            for(GZOutTicket gZOutReport:gZOutReports){
                gZOutReport.setDepartDate(getYMDDate(gZOutReport.getDepartDate()));
                gZOutReport.setTicketDate(getYMDDate(gZOutReport.getTicketDate()));
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
                if(null!=gZOutReport.getTicketType()) {
                    switch (gZOutReport.getTicketType()) {
                        case "1":
                            gZOutReport.setTicketType("PNR");
                            break;
                        case "2":
                            gZOutReport.setTicketType("白屏");
                            break;
                        case "3":
                            gZOutReport.setTicketType("手工补单");
                            break;
                        case "4":
                            gZOutReport.setTicketType("散客需求单");
                            break;
                        case "5":
                            gZOutReport.setTicketType("团队押金单");
                            break;
                        case "6":
                            gZOutReport.setTicketType("PNR后台下单");
                            break;
                        case "7":
                            gZOutReport.setTicketType("冲单");
                            break;
                        case "8":
                            gZOutReport.setTicketType("补单");
                            break;
                        case "9":
                            gZOutReport.setTicketType("调整单1");
                            break;
                        case "10":
                            gZOutReport.setTicketType("调整单2");
                            break;
                        case "11":
                            gZOutReport.setTicketType("ADM单");
                            break;
                        case "12":
                            gZOutReport.setTicketType("ACM单");
                            break;
                        default:
                            break;
                    }
                }
                gZOutReport.setSettlePrice(gZOutReport.getSettlePrice()==null?0d:gZOutReport.getSettlePrice());//结算净价
                gZOutReport.setTax(gZOutReport.getTax()==null?0d:gZOutReport.getTax());//税金
                gZOutReport.setqValue(gZOutReport.getqValue()==null?0d:gZOutReport.getqValue());//Q值
                gZOutReport.setEndPoint(gZOutReport.getEndPoint()==null?0d:gZOutReport.getEndPoint());//底扣
                gZOutReport.setLastRebatePoint(gZOutReport.getLastRebatePoint()==null?0d:gZOutReport.getLastRebatePoint());//后返
                gZOutReport.setAddPrice(gZOutReport.getAddPrice()==null?0d:gZOutReport.getAddPrice());//加价
                gZOutReport.setEndPrice(gZOutReport.getEndPrice()==null?0d:gZOutReport.getEndPrice());//底价
                gZOutReport.setAgencyFee(gZOutReport.getAgencyFee()==null?0d:gZOutReport.getAgencyFee());//代理费率
                // 营业部毛利=实收-{（底价）*（1-代理费率）*（1-底扣）+税+Q值*(1-代理费%)+加价金额}
               gZOutReport.setBusinessGross(gZOutReport.getSettlePrice()-(gZOutReport.getEndPrice()*(1-gZOutReport.getAgencyFee()/100)*(1-gZOutReport.getEndPoint()/100)+gZOutReport.getqValue()*(1-gZOutReport.getAgencyFee()/100)+gZOutReport.getAddPrice()));
                //毛利=实收-{（底价）*（1-代理费率）+税+Q值*(1-代理费%)+加价金额}
                gZOutReport.setGross(gZOutReport.getSettlePrice()-(gZOutReport.getEndPrice()*(1-gZOutReport.getAgencyFee()/100)+gZOutReport.getqValue()*(1-gZOutReport.getAgencyFee()/100)+gZOutReport.getAddPrice()));

            }
        }
        page.setRecords(gZOutReports);
        return page;
    }

    @Override
    public Page<GzOutReport> queryIssueRecords(Page<GzOutReport> page, GzOutReportParams params) {
        List<GzOutReport> list = new ArrayList<>();
        try {
            list = gzOutReportDao.queryIssueRecords(page,params);
        } catch (Exception e) {
            logger.error("国际广州报表查询 ERROR  {}", e);
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<Map<String, Object>> queryIssueRecordsSum(GzOutReportParams params) {
        return gzOutReportDao.queryIssueRecordsSum(params);
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

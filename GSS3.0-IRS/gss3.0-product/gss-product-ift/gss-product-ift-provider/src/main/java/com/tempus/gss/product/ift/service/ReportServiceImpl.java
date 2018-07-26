package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.ReportRefundVo;
import com.tempus.gss.product.ift.api.entity.vo.ReportVo;
import com.tempus.gss.product.ift.api.service.ReportService;
import com.tempus.gss.product.ift.dao.IssueReportDao;
import com.tempus.gss.product.ift.dao.RefundReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.math.BigDecimal;
import java.util.List;

@Service
@EnableAutoConfiguration
public class ReportServiceImpl implements ReportService {
    @Autowired
    RefundReportDao refundReportDao;
    
    @Autowired
    private IssueReportDao issueReportDao;
    
    @Override
    public Page<ReportRefundVo> getAllRefund(Page<ReportRefundVo> page, ReportVo reportIn) {
        // List<ReportVo> list = refundReportDao.getAll(page,reportIn);
        
        List<ReportRefundVo> list = refundReportDao.getAllWithList(page, reportIn);
        handleReportRefundVo(list);
//        for(ReportVo r:list){
//            r.setTicketNum();
//        }
//        Page<ReportVo> pageOut = new Page<>();
        page.setRecords(list);
        return page;
    }
    
    @Override
    public List<ReportRefundVo> getAllRefundRecords(ReportVo reportVo) {
        List<ReportRefundVo> list = refundReportDao.getAllRefundRecords(reportVo);
        handleReportRefundVo(list);
        return list;
    }
    
    private void handleReportRefundVo(List<ReportRefundVo> list) {
        for (ReportRefundVo reportRefundVo : list) {
            List<SaleChangeDetail> saleChangeDetailList = reportRefundVo.getSaleChangeDetailList();
            List<PassengerRefundPrice> passengerRefundPriceList = reportRefundVo.getPassengerRefundPriceList();
            List<Leg> legList = reportRefundVo.getLegList();
            //票号   “/” 分割
            String allTicketNo = "";
            for (SaleChangeDetail saleChangeDetail : saleChangeDetailList) {
                String ticketNo = saleChangeDetail.getSaleOrderDetail().getTicketNo();
                if (!allTicketNo.contains(ticketNo)) {
                    allTicketNo += ticketNo + ",";
                }
            }
            
            if (!"".equals(allTicketNo)) {
                allTicketNo = allTicketNo.substring(0, allTicketNo.length() - 1);
            }
            reportRefundVo.setTicketNo(allTicketNo);
            //设置航程
            String allLeg = "";
            for (Leg leg : legList) {
                allLeg += leg.getDepAirport() + "-" + leg.getArrAirport() + ",";
            }
            if (!"".equals(allLeg)) {
                allLeg = allLeg.substring(0, allLeg.length() - 1);
            }
            reportRefundVo.setLeg(allLeg);
            for (PassengerRefundPrice passengerRefundPrice : passengerRefundPriceList) {
                handleNullValue(passengerRefundPrice);
                //销售结算价
                reportRefundVo.setSalePrice(reportRefundVo.getSalePrice() == null ? passengerRefundPrice.getSaleTicketAccount() : reportRefundVo.getSalePrice().add(passengerRefundPrice.getSaleTicketAccount()));
                //退票费
                reportRefundVo.setRefundPrice(reportRefundVo.getRefundPrice() == null ? passengerRefundPrice.getSaleRefundPrice() : reportRefundVo.getRefundPrice().add(passengerRefundPrice.getSaleRefundPrice()));
                //实退款 factRefundAccount
                reportRefundVo.setFactRefundAccount(reportRefundVo.getFactRefundAccount() == null ? passengerRefundPrice.getSaleRefundAccount() : reportRefundVo.getFactRefundAccount().add(passengerRefundPrice.getSaleRefundAccount()));
                //buyRefundAccount  采购退款
                reportRefundVo.setBuyRefundAccount(reportRefundVo.getBuyRefundAccount() == null ? passengerRefundPrice.getBuyFefundAccount() : reportRefundVo.getBuyRefundAccount().add(passengerRefundPrice.getBuyFefundAccount()));
                //tax  税费
                reportRefundVo.setTax(reportRefundVo.getTax() == null ? passengerRefundPrice.getSaleTax() : reportRefundVo.getTax().add(passengerRefundPrice.getSaleTax()));
                //settlePrice   净结算价
                reportRefundVo.setSettlePrice(reportRefundVo.getFactRefundAccount());
            }
            if (passengerRefundPriceList != null && passengerRefundPriceList.size() > 0) {
                //chargeProfit   冲抵营业毛利
                reportRefundVo.setChargeProfit(passengerRefundPriceList.get(0).getChargeProfit());
                //汇率
                reportRefundVo.setExchange(passengerRefundPriceList.get(0).getExchangeRate());
            }
            //reportRefundVo.setDepGrossProfit(reportRefundVo.getFactRefundAccount().subtract(reportRefundVo.getBuyRefundAccount()));
            //reportRefundVo.setGrossProfit(reportRefundVo.getDepGrossProfit());
            reportRefundVo.setSaleChangeDetailList(null);
        }
    }
    
    private void handleNullValue(PassengerRefundPrice passengerRefundPrice) {
        
        if (passengerRefundPrice.getSaleTicketAccount() == null) {
            passengerRefundPrice.setSaleTicketAccount(new BigDecimal(0));
        }
        if (passengerRefundPrice.getSaleRefundPrice() == null) {
            passengerRefundPrice.setSaleRefundPrice(new BigDecimal(0));
        }
        if (passengerRefundPrice.getSaleBrokerage() == null) {
            passengerRefundPrice.setSaleBrokerage(new BigDecimal(0));
        }
        if (passengerRefundPrice.getSaleRefundAccount() == null) {
            passengerRefundPrice.setSaleRefundAccount(new BigDecimal(0));
        }
        if (passengerRefundPrice.getBuyFefundAccount() == null) {
            passengerRefundPrice.setBuyFefundAccount(new BigDecimal(0));
        }
        if (passengerRefundPrice.getChargeProfit() == null) {
            passengerRefundPrice.setChargeProfit(new BigDecimal(0));
        }
        if (passengerRefundPrice.getSaleTax() == null) {
            passengerRefundPrice.setSaleTax(new BigDecimal(0));
        }
        
    }
    
    @Override
    public List<ReportVo> queryReportRecords(ReportVo reportIn) {
        List<ReportVo> result = refundReportDao.queryReportRecords(reportIn);
        return result;
    }
    
    @Override
    public Page<IftOutReport> queryIssueRecords(Page<IftOutReport> page, IftOutReportVO iftOutReportVo) {
        List<IftOutReport> list = issueReportDao.queryIftOutReport(page, iftOutReportVo);
        page.setRecords(list);
        return page;
    }
    
    @Override
    public List<String> queryIssueDeptNameList() {
        return issueReportDao.selectDeptNameList();
    }
    
    @Override
    public List<String> queryIssueCabins() {
        return issueReportDao.getCabins();
    }
    
}

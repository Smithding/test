package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.order.entity.ActualAmountRecord;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.vo.ActualInfoSearchVO;
import com.tempus.gss.order.entity.vo.CertificateCreateVO;
import com.tempus.gss.order.entity.vo.PlanInfoSearchVO;
import com.tempus.gss.order.entity.vo.UpdatePlanAmountVO;
import com.tempus.gss.order.service.ICertificateService;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderExt;
import com.tempus.gss.product.ift.api.entity.iftVo.IftRepayVo;
import com.tempus.gss.product.ift.api.service.IIftOrderRePayService;
import com.tempus.gss.product.ift.api.service.IOrderService;
import com.tempus.gss.vo.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IftOrderRePayServiceImpl implements IIftOrderRePayService {

    private static final Logger logger = LogManager.getLogger(IftOrderRePayServiceImpl.class);

    @Reference
    private IPlanAmountRecordService planAmountRecordService;
    @Reference
    private ICertificateService certificateService;
    @Reference
    private IOrderService iftOrderService;


    @Override
    public void rePay(RequestWithActor<IftRepayVo> requestWithActor) {
        Agent agent = requestWithActor.getAgent();
        IftRepayVo repayVo  = requestWithActor.getEntity();
        Long bussinessNo  = repayVo.getBussinessNo();
        logger.info("重新支付入参:"+repayVo.toString());
        PlanInfoSearchVO planInfoSearchVO = planAmountRecordService.queryPlanInfoByBusNo(agent,bussinessNo,repayVo.getBusinessType());
        BigDecimal plantTotalAmount = planInfoSearchVO.getTotalAmount();
        CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
        RequestWithActor<Long> orderParam = new RequestWithActor<>();
        orderParam.setEntity(bussinessNo);
        orderParam.setAgent(agent);
        SaleOrderExt saleOrderExt = iftOrderService.getOrder(orderParam);
        if(saleOrderExt!=null)
        {
            SaleOrder saleOrder = saleOrderExt.getSaleOrder();
            certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
            certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
            certificateCreateVO.setTransationOrderNo(String.valueOf(saleOrder.getTransationOrderNo()));
            ActualInfoSearchVO actualInfoSearchVO = certificateService.queryListByTONo(agent,saleOrder.getTransationOrderNo());
            List<ActualAmountRecord> actualAmountRecords = actualInfoSearchVO.getActualAmountRecordList();
            if(actualAmountRecords.size()>0){
                ActualAmountRecord record = actualAmountRecords.get(0);
                certificateCreateVO.setAccoutNo(record.getCapitalAccountNo());
                certificateCreateVO.setPayWay(Integer.valueOf(record.getPayWay()));
            }
            List<BusinessOrderInfo> orderInfoList = new ArrayList<BusinessOrderInfo>();// 业务单信息列表
            BusinessOrderInfo businessOrder = new BusinessOrderInfo();
            businessOrder.setRecordNo(bussinessNo);// 业务单号
            businessOrder.setBusinessType(2);// 业务类型(1.交易单,2.销售单,3.采购单,4.销售变更单,5.采购变更单)
            orderInfoList.add(businessOrder);
        }

        //重新支付
        List<BusinessOrderInfo> orderInfoList = new ArrayList<BusinessOrderInfo>();// 业务单信息列表
        certificateCreateVO.setPayType(2);//1 在线支付 2 帐期或代付 3 线下支付
        certificateCreateVO.setOrderInfoList(orderInfoList);
        if(planInfoSearchVO!=null){
            //更新销售应收应付
            UpdatePlanAmountVO updatePlanAmountVO = new UpdatePlanAmountVO();
            updatePlanAmountVO.setId(bussinessNo);
            updatePlanAmountVO.setIncomeExpenseType(1);//收
            updatePlanAmountVO.setPlanAmount(repayVo.getAmount());
            updatePlanAmountVO.setRecordMovingType(1);//1.销售
            planAmountRecordService.update(agent,updatePlanAmountVO);
        }
        BigDecimal result = plantTotalAmount.subtract(repayVo.getAmount());
        logger.info("比较新应收总和和就应收总和结果："+result.doubleValue());
        //重新合计金额变大 重新支付金额
        if(result.doubleValue()>0){
            certificateCreateVO.setIncomeExpenseType(1);//收支类型 1 收，2 为支
            logger.info("销售审核后再次支付:"+certificateCreateVO.toString());
            certificateService.__createCertificateAndPay(agent,certificateCreateVO);
        }else{
            certificateCreateVO.setIncomeExpenseType(2);//收支类型 1 收，2 为支
            //金额变小 调用退款接口 金额原路退还
            certificateService.saleRefundCert(agent,certificateCreateVO);
        }


    }
}

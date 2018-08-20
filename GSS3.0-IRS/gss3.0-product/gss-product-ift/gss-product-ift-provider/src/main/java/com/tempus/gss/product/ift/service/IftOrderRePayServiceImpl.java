package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.ActualAmountRecord;
import com.tempus.gss.order.entity.BusinessOrderInfo;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.entity.enums.OSResultType;
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
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.vo.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
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
        IftRepayVo repayVo = requestWithActor.getEntity();
        Long bussinessNo = repayVo.getBussinessNo();
        logger.info("重新支付入参:" + repayVo.toString());
        PlanInfoSearchVO planInfoSearchVO = planAmountRecordService.queryPlanInfoByBusNo(agent, bussinessNo, repayVo.getBusinessType());
        BigDecimal plantTotalAmount = planInfoSearchVO.getTotalAmount();
        CertificateCreateVO certificateCreateVO = new CertificateCreateVO();
        RequestWithActor<Long> orderParam = new RequestWithActor<>();
        orderParam.setEntity(bussinessNo);
        orderParam.setAgent(agent);
        SaleOrderExt saleOrderExt = iftOrderService.getOrder(orderParam);
        if (saleOrderExt != null) {
            SaleOrder saleOrder = saleOrderExt.getSaleOrder();

            certificateCreateVO.setSubBusinessType(1);
            certificateCreateVO.setCustomerNo(saleOrder.getCustomerNo());
            certificateCreateVO.setCustomerTypeNo(saleOrder.getCustomerTypeNo());
            certificateCreateVO.setTransationOrderNo(String.valueOf(saleOrder.getTransationOrderNo()));
            ActualInfoSearchVO actualInfoSearchVO = certificateService.queryListByTONo(agent, saleOrder.getTransationOrderNo());
            List<ActualAmountRecord> actualAmountRecords = actualInfoSearchVO.getActualAmountRecordList();
            if (actualAmountRecords.size() > 0) {//todo 添加实收判断 actualAmountRecords.getAuditStatus()=2 支付成功的  取这个记录的信息
                for (ActualAmountRecord record : actualAmountRecords) {
                    if (record.getAuditStatus()==2){
                        certificateCreateVO.setAccoutNo(record.getCapitalAccountNo());
                        certificateCreateVO.setPayWay(Integer.valueOf(record.getPayWay()));
                        break;
                    }
                }
            }
            List<BusinessOrderInfo> orderInfoList = new ArrayList<BusinessOrderInfo>();// 业务单信息列表

            BusinessOrderInfo businessOrder = new BusinessOrderInfo();
            businessOrder.setRecordNo(bussinessNo);// 业务单号
            businessOrder.setBusinessType(2);// 业务类型(1.交易单,2.销售单,3.采购单,4.销售变更单,5.采购变更单)
            orderInfoList.add(businessOrder);
        }

        //重新支付
        List<BusinessOrderInfo> orderInfoList = new ArrayList<BusinessOrderInfo>();// 业务单信息列表
        BusinessOrderInfo businessOrderInfo = new BusinessOrderInfo();
        businessOrderInfo.setBusinessType(2);// 业务类型(1.交易单，2.销售单，3.采购单，4.销售变更单（可以根据变更表设计情况将废退改分开），5.采购变更单)
        businessOrderInfo.setRecordNo(bussinessNo);
        orderInfoList.add(businessOrderInfo);
        certificateCreateVO.setPayType(2);//1 在线支付 2 帐期或代付 3 线下支付
        certificateCreateVO.setChannel("SALE");
        certificateCreateVO.setServiceLine("1");
        if (planInfoSearchVO != null) {
            //更新销售应收应付
            UpdatePlanAmountVO updatePlanAmountVO = new UpdatePlanAmountVO();
            if (planInfoSearchVO.getPlanAmountRecordList() != null) {
                updatePlanAmountVO.setId(planInfoSearchVO.getPlanAmountRecordList().get(0).getId());
            }
            updatePlanAmountVO.setIncomeExpenseType(1);//收
            updatePlanAmountVO.setPlanAmount(repayVo.getAmount());
            updatePlanAmountVO.setRecordMovingType(1);//1.销售
            planAmountRecordService.update(agent, updatePlanAmountVO);
        }
        BigDecimal result = repayVo.getAmount().subtract(plantTotalAmount);
        certificateCreateVO.setSaleOrderNo(repayVo.getBussinessNo().toString());//销售单号
        logger.info("比较新应收总和和就应收总和结果：" + result.doubleValue());
        //  this.createBuyCertificate(AgentUtil.getAgent(), buychange.getBuyChangeNo(), buychange.getPlanAmount().doubleValue(), changePrice.getAccountNo(), supplier.getSupplierNo(), supplier.getCustomerTypeNo(), 3, 2000003, "BUY", thirdBusNo, changePrice.getDealNo());
        //(Agent agent, long buyOrderNo, double payAmount, long payAccount, long customerNo, long customerTypeNo, int payType, int payWay, String channel, String thirdBusNo, String thirdPayNo) {
        //重新合计金额变大 重新支付金额
        if (result.doubleValue() > 0) {
            certificateCreateVO.setIncomeExpenseType(1);//收支类型 1 收，2 为支
            logger.info("销售审核后再次支付:" + certificateCreateVO.toString());
            businessOrderInfo.setActualAmount(result);
            certificateCreateVO.setOrderInfoList(orderInfoList);
            try {
                OSResultType payResult = certificateService.__createCertificateAndPay(agent, certificateCreateVO);
                logger.info("{}", payResult.getMessage());
            } catch (Exception e) {
                logger.error("shibai", e);
            }
        }
        if (result.doubleValue() < 0) {
            certificateCreateVO.setIncomeExpenseType(2);//收支类型 1 收，2 为支
            //金额变小 调用退款接口 金额原路退还 退款不能部分退款  退款 先退全部 在重新支付一次
            BigDecimal refundAmount = new BigDecimal(0);
            businessOrderInfo.setActualAmount(new BigDecimal(0).subtract(result));
            certificateCreateVO.setOrderInfoList(orderInfoList);
            try {
                refundAmount = certificateService.saleRefundCert(agent, certificateCreateVO);
                logger.info("{}", refundAmount);
                if (refundAmount.doubleValue()>0){
                    certificateCreateVO.setIncomeExpenseType(1);
                    OSResultType payResult = certificateService.__createCertificateAndPay(agent, certificateCreateVO);
                    logger.info("{}", payResult.getMessage());
                }
            } catch (GSSException e) {
                logger.info("", e);
            }

        }


    }
}

package com.tempus.gss.product.unp.service.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.enums.BusinessType;
import com.tempus.gss.order.entity.enums.GoodsBigType;
import com.tempus.gss.order.entity.enums.IncomeExpenseType;
import com.tempus.gss.order.entity.vo.PayNoticeVO;
import com.tempus.gss.order.entity.vo.PayReceiveVO;
import com.tempus.gss.product.unp.api.entity.*;
import com.tempus.gss.product.unp.api.entity.enums.EUnpConstant;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderQueryVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpOrderVo;
import com.tempus.gss.product.unp.api.entity.vo.UnpRefundVo;
import com.tempus.gss.product.unp.api.service.UnpOrderService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付状态监听类
 *
 * @author ZhangBro
 */
@Component("unpPayListener")
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "unp.payNoticeQue", durable = "true"),
                                         exchange = @Exchange(value = "gss-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true"),
                                         key = "pay"))
public class PayListener {
    
    private Logger logger = LoggerFactory.getLogger(PayListener.class);
    @Reference
    UnpOrderService unpOrderService;
    
    @RabbitHandler
    public void processLogRecord(PayNoticeVO payNoticeVO) {
        try {
            if (GoodsBigType.GENERAL.getKey() == payNoticeVO.getGoodsType() && PayReceiveVO.PS_PAY_STATUS_SUCCESS == payNoticeVO.getPayStatus()) {
                logger.info("监听到【通用产品】支付消息队列" + JSON.toJSONString(payNoticeVO));
                String payWayIn = String.valueOf(payNoticeVO.getPayWay()) + "";
                if (payWayIn.length() == 0) {
                    logger.error("销售单号【{}】接收到的支付消息中，支付方式为空", payNoticeVO.getBusinessNo());
                    throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "销售单号【" + payNoticeVO.getBusinessNo() + "】接收到的支付消息中，支付方式为空");
                }
                int payStatus = 1;
                String payWay = payWayIn.substring(0, 1);
                if ("2".equals(payWay)) {
                    payStatus = EUnpConstant.PayStatus.BALANCE_PAIED.getKey();
                } else {
                    payStatus = EUnpConstant.PayStatus.PAIED.getKey();
                }
                Agent agent = payNoticeVO.getAgent();
                UnpOrderVo updateVo = new UnpOrderVo();
                UnpRefundVo updateRefundVo = new UnpRefundVo();
                UnpOrderQueryVo queryVo = new UnpOrderQueryVo();
                if (payNoticeVO.getBusinessType() == BusinessType.SALE_ORDER) {
                    queryVo.setSaleOrderNo(payNoticeVO.getBusinessNo());
                    UnpSale unpSale = unpOrderService.getSaleOrderInfo(queryVo);
                    if (unpSale == null) {
                        logger.error("根据销售单号【{}】未查询到销售单", payNoticeVO.getBusinessNo());
                        throw new GSSException(GoodsBigType.GENERAL.getValue(), "0", "根据销售单号【" + payNoticeVO.getBusinessNo() + "】未查询到销售单");
                    }
                    UnpSale unpUpdate = new UnpSale();
                    List<UnpSaleItem> itemsToUpdate = new ArrayList<>();
                    unpUpdate.setSaleOrderNo(payNoticeVO.getBusinessNo());
                    if (payNoticeVO.getIncomeExpenseType() == IncomeExpenseType.INCOME.getKey()) {
                        logger.info("监听到UNP销售支付消息:{}--￥{}", payNoticeVO.getBusinessNo(), payNoticeVO.getActualAmount());
                        //收款
                        if (unpSale.getPayStatus() <= EUnpConstant.PayStatus.PAYING.getKey()) {
                            //首次通知 首次修改
                            if ("2".equals(payWay)) {
                                unpUpdate.setPayStatus(EUnpConstant.PayStatus.BALANCE_PAIED.getKey());
                            } else {
                                unpUpdate.setPayStatus(EUnpConstant.PayStatus.PAIED.getKey());
                            }
                            unpUpdate.setPayTime(new Date());
                            unpUpdate.setModifier(agent.getAccount());
                            unpUpdate.setModifyTime(new Date());
                        }
                        unpUpdate.setStatus(EUnpConstant.OrderStatus.DONE.getKey());
                        unpUpdate.setActualAmount(payNoticeVO.getActualAmount());
                        unpSale.getSaleItems().forEach(item -> {
                            UnpSaleItem itemUpdate = new UnpSaleItem();
                            itemUpdate.setItemId(item.getItemId());
                            itemUpdate.setSaleOrderNo(item.getSaleOrderNo());
                            itemUpdate.setItemStatus(EUnpConstant.OrderStatus.DONE.getKey());
                            itemsToUpdate.add(itemUpdate);
                        });
                        unpUpdate.setSaleItems(itemsToUpdate);
                        updateVo.setUnpSale(unpUpdate);
                        unpOrderService.updateSale(agent, updateVo);
                    }
                }
                if (payNoticeVO.getBusinessType() == BusinessType.BUY_ORDER) {
                    if (payNoticeVO.getIncomeExpenseType() == IncomeExpenseType.EXPENSE.getKey()) {
                        //采购付款
                        logger.info("监听到UNP采购付款消息:{}--￥{}", payNoticeVO.getBusinessNo(), payNoticeVO.getActualAmount());
                        UnpBuy unpBuy = unpOrderService.getBuyOrderInfo(queryVo);
                        
                        UnpBuy unpBuyUpdate = new UnpBuy();
                        unpBuyUpdate.setBuyOrderNo(unpBuy.getBuyOrderNo());
                        if (unpBuy.getPayStatus() <= EUnpConstant.PayStatus.PAYING.getKey()) {
                            //首次成功通知
                            unpBuyUpdate.setPayStatus(payStatus);
                            unpBuyUpdate.setPayTime(new Date());
                            unpBuyUpdate.setModifier(agent.getAccount());
                            unpBuyUpdate.setModifyTime(new Date());
                        }
                        updateVo.setUnpBuy(unpBuy);
                        updateVo.setBuyItems(unpBuy.getBuyItems());
                        updateVo.setOperationType(EUnpConstant.Opertion.PAY.getKey());
                        unpOrderService.updateBuy(agent, updateVo);
                    }
                }
                if ((payNoticeVO.getBusinessType() == BusinessType.SALE_CHANGE_ORDER)) {
                    if (payNoticeVO.getIncomeExpenseType() == IncomeExpenseType.EXPENSE.getKey()) {
                        //销售退款  //退
                        logger.info("监听到UNP销售退款消息:{}--￥{}", payNoticeVO.getBusinessNo(), payNoticeVO.getActualAmount());
                        queryVo.setSaleChangeNo(payNoticeVO.getBusinessNo());
                        UnpSaleRefund unpSaleRefund = unpOrderService.querySaleOrderRefund(queryVo);
                        UnpSaleRefund saleRefundUpdate = new UnpSaleRefund();
                        if (unpSaleRefund.getPayStatus() < EUnpConstant.PayStatus.PAYING.getKey()) {
                            // 首次成功通知
                            saleRefundUpdate.setPayStatus(payStatus);
                            saleRefundUpdate.setPayTime(new Date());
                            saleRefundUpdate.setModifier(agent.getAccount());
                            saleRefundUpdate.setModifyTime(new Date());
                        }
                        saleRefundUpdate.setStatus(EUnpConstant.OrderStatus.DONE.getKey());
                        //处理明细单
                        List<UnpSaleRefundItem> refundItems = new ArrayList<>();
                        unpSaleRefund.getItems().forEach(item -> {
                            UnpSaleRefundItem itemUpdate = new UnpSaleRefundItem();
                            itemUpdate.setItemId(item.getItemId());
                            //验证要修改的item处于当前总单下
                            itemUpdate.setSaleRefundOrderNo(item.getSaleRefundOrderNo());
                            itemUpdate.setItemStatus(EUnpConstant.OrderStatus.DONE.getKey());
                            refundItems.add(itemUpdate);
                        });
                        saleRefundUpdate.setItems(refundItems);
                        updateRefundVo.setUnpSaleRefund(saleRefundUpdate);
                        updateRefundVo.setUnpSaleRefundItemList(saleRefundUpdate.getItems());
                        //调用退单的update
                        unpOrderService.updateSale(agent, updateRefundVo);
                    }
                }
                if ((payNoticeVO.getBusinessType() == BusinessType.BUY_CHANGE_ORDER)) {
                    if (payNoticeVO.getIncomeExpenseType() == IncomeExpenseType.INCOME.getKey()) {
                        //采购退款   //退
                        logger.info("监听到UNP采购退款消息:{}--￥{}", payNoticeVO.getBusinessNo(), payNoticeVO.getActualAmount());
                        queryVo.setBuyChangeNo(payNoticeVO.getBusinessNo());
                        UnpBuyRefund unpBuyRefund = unpOrderService.queryBuyOrderRefund(queryVo);
                        UnpBuyRefund buyRefundUpdate = new UnpBuyRefund();
                        if (unpBuyRefund.getPayStatus() < EUnpConstant.PayStatus.PAYING.getKey()) {
                            // 首次成功通知
                            buyRefundUpdate.setPayStatus(payStatus);
                            buyRefundUpdate.setPayTime(new Date());
                            buyRefundUpdate.setModifier(agent.getAccount());
                            buyRefundUpdate.setModifyTime(new Date());
                        }
                        buyRefundUpdate.setStatus(EUnpConstant.OrderStatus.DONE.getKey());
                        //处理明细单
                        List<UnpBuyRefundItem> refundItems = new ArrayList<>();
                        unpBuyRefund.getItems().forEach(item -> {
                            UnpBuyRefundItem itemUpdate = new UnpBuyRefundItem();
                            itemUpdate.setItemId(item.getItemId());
                            //验证要修改的item处于当前总单下
                            itemUpdate.setSaleRefundOrderNo(item.getSaleRefundOrderNo());
                            itemUpdate.setItemStatus(EUnpConstant.OrderStatus.DONE.getKey());
                            refundItems.add(itemUpdate);
                        });
                        buyRefundUpdate.setItems(refundItems);
                        updateRefundVo.setUnpBuyRefund(buyRefundUpdate);
                        updateRefundVo.setUnpBuyRefundItemList(buyRefundUpdate.getItems());
                        //调用退单的update
                        unpOrderService.updateSale(agent, updateRefundVo);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }
    }
    
}

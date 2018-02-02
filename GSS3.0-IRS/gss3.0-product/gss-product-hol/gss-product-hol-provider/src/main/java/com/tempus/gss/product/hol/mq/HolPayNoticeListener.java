/**
 * InsPayNoticeListener.java
 * com.tempus.gss.product.ins.mq
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2016年12月20日 		shuo.cheng
 * <p>
 * Copyright (c) 2016, TNT All Rights Reserved.
 */

package com.tempus.gss.product.hol.mq;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.order.entity.CreatePlanAmountVO;
import com.tempus.gss.order.entity.GoodsBigType;
import com.tempus.gss.order.entity.PayNoticeVO;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.product.hol.api.entity.response.HotelOrderDetail;
import com.tempus.gss.vo.Agent;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * ClassName:HolPayNoticeListener
 * Function: 酒店支付通知消息队列监听类
 *
 * @author fengjie.luo
 * @Date 2017年04月11日        上午10:53:15
 * @see
 * @since Ver 1.1
 */
//@Component
//@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "hol.payNoticeQue", durable = "true"), exchange = @Exchange(value = "ubp-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true"), key = "pay"))
public class HolPayNoticeListener {

    protected static final Logger logger = LoggerFactory.getLogger(HolPayNoticeListener.class);

   /* @Reference
    QTHotelOrderService qtHotelOrderService;*/

    @Reference
    IPlanAmountRecordService planAmountRecordService;

    @RabbitHandler
    public void onMessage(PayNoticeVO payNoticeVO) {
        try {
            logger.info("酒店监听到支付消息队列,入参{}" + JSON.toJSONString(payNoticeVO.toString()));
            // 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
            if (payNoticeVO.getGoodsType() == 4) {
                Agent agent = new Agent(payNoticeVO.getOwner(), "sys");
                if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0 && payNoticeVO.getIncomeExpenseType() == 1) {
                    long businessNo = payNoticeVO.getBusinessNo();
                    Long saleOrderNo = Long.valueOf(businessNo);
                    //调用酒店支付接口
                   /* qtHotelOrderService.hotelOrderPay(agent, saleOrderNo, "订单已支付");*/
                    /*if (result) {
                        HotelOrderDetail hotelOrderDetail = qtHotelOrderService.getHotelOrderDetail(agent, saleOrderNo);
                        if ("0".equals(hotelOrderDetail.getPayType())) {//预付产品需要创建应收/应付记录
                            //创建销售应收记录
                            CreatePlanAmountVO saleOrderPlanAmountVO = new CreatePlanAmountVO();
                            //销售金额=总价金额-立减金额
                            BigDecimal planAmount = new BigDecimal(hotelOrderDetail.getPrice()).subtract(new BigDecimal(hotelOrderDetail.getReduce()));
                            saleOrderPlanAmountVO.setPlanAmount(planAmount);// 销售应收金额
                            saleOrderPlanAmountVO.setGoodsType(GoodsBigType.GROGSHOP.getKey());//商品大类 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
                            saleOrderPlanAmountVO.setRecordNo(hotelOrderDetail.getSaleOrderNo());//记录编号   自动生成
                            saleOrderPlanAmountVO.setBusinessType(2);//业务类型 2，销售单，3，采购单，4 ，变更单（可以根据变更表设计情况将废退改分开）
                            saleOrderPlanAmountVO.setIncomeExpenseType(1);// 收支类型 1 收，2 为支
                            saleOrderPlanAmountVO.setRecordMovingType(1);// 记录变动类型 如 1 销售，2销售补单 3 补收，4销售废退， 5 销售改签 11 采购，12采购补单 13 补付 14 采购废退，15采购改签
                            planAmountRecordService.create(agent, saleOrderPlanAmountVO);
                        }
                    }*/
                }
            }

        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }

    }
}
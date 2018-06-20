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

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.order.entity.PayNoticeVO;
import com.tempus.gss.order.service.IPlanAmountRecordService;
import com.tempus.gss.product.hol.api.entity.response.HotelOrder;
import com.tempus.gss.product.hol.api.entity.response.tc.OwnerOrderStatus;
import com.tempus.gss.product.hol.api.entity.vo.bqy.request.OrderPayReq;
import com.tempus.gss.product.hol.api.entity.vo.bqy.response.OrderPayResult;
import com.tempus.gss.product.hol.api.service.IBQYHotelInterService;
import com.tempus.gss.product.hol.api.syn.ITCHotelOrderService;
import com.tempus.gss.product.hol.dao.HotelOrderMapper;
import com.tempus.gss.system.service.GSSException;

/**
 * ClassName:HolPayNoticeListener
 * Function: 酒店支付通知消息队列监听类
 *
 */
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "hol.payNoticeQue", durable = "true"), exchange = @Exchange(value = "gss-pay-exchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true"), key = "pay"))
public class HolPayNoticeListener {

    protected static final Logger logger = LoggerFactory.getLogger(HolPayNoticeListener.class);
    
    @Autowired
    ITCHotelOrderService hotelOrderService;
    
    @Autowired
    HotelOrderMapper hotelOrderMapper;

    @Reference
    IPlanAmountRecordService planAmountRecordService;
    
    @Autowired
    private IBQYHotelInterService bqyHotelInterService;

    @RabbitHandler
    public void onMessage(PayNoticeVO payNoticeVO) {
        try {
            logger.info("酒店监听到支付消息队列,入参{}" + JSON.toJSONString(payNoticeVO.toString()));
            // 商品类型 1 国内机票 2 国际机票 3 保险 4 酒店 5 机场服务 6 配送
            if (payNoticeVO.getGoodsType() == 4) {
               // Agent agent = new Agent(payNoticeVO.getOwner(), "sys");
                if (payNoticeVO.getBusinessType() == 2 && payNoticeVO.getChangeType() == 0 && payNoticeVO.getIncomeExpenseType() == 1) {
                    long businessNo = payNoticeVO.getBusinessNo();
                    Long saleOrderNo = Long.valueOf(businessNo);
                    //调用酒店支付接口
                    /*OrderPayReq orderPayReq = new OrderPayReq();
                    orderPayReq.setOrderNumber(saleOrderNo);
                    OrderPayResult orderPay = bqyHotelInterService.orderPay(orderPayReq);
                    if (!orderPay.getIsChangePrice()) {
                    	throw new GSSException("bqy酒店订单支付", "10009", "bqy酒店订单支付失败:酒店价格变动!" + orderPay.getMsg());
                    }
                    if (!orderPay.getReseult()) {
                    	throw new GSSException("bqy酒店订单支付", "10010", "bqy酒店订单支付失败:" + orderPay.getMsg());
                    }*/
                   
                    //更新酒店订单
                    HotelOrder hotelOrder = new HotelOrder();
                    hotelOrder.setSaleOrderNo(saleOrderNo);
    		        hotelOrder = hotelOrderMapper.selectOne(hotelOrder);
    		        if(StringUtil.isNotNullOrEmpty(hotelOrder.getHotelOrderNo())) {
    		        	if(payNoticeVO.getPayStatus().equals(1)) {
    		        		hotelOrder.setOrderStatus(OwnerOrderStatus.PAY_OK.getKey());
    		        		hotelOrder.setResultCode("3");
    		        		hotelOrder.setFactTotalPrice(payNoticeVO.getActualAmount());
    		        		hotelOrderMapper.updateById(hotelOrder);
    		        	}
    		        }
                }
            }

        } catch (Exception ex) {
            logger.error("消费队列异常 ：", ex);
        }

    }
}
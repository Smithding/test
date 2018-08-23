package com.tempus.gss.product.ins.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.dps.order.entity.sale.Sale;
import com.tempus.gss.dps.order.entity.vo.TicketMessage;
import com.tempus.gss.dps.order.service.SaleService;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.ActualAmountRecord;
import com.tempus.gss.order.entity.vo.ActualInfoSearchVO;
import com.tempus.gss.order.service.IActualAmountRecorService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ins.api.service.IOrderService;
import com.tempus.gss.security.AgentUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RabbitListener(bindings = {
        @QueueBinding(value = @Queue(value = "mss.listenerDpsTicketedQue", durable = "true"),
                exchange = @Exchange(value = "gss-dps-order-callback", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true")
                , key = "apiticketed"
        ) }
)
public class InsureTicketListenter {
        protected static final Logger logger = LogManager.getLogger(InsureTicketListenter.class);
        /**
         * 1:国内;2:国际
         */
        public static final String TICKET_TYPE = "1";
        @Reference
        private IOrderService orderService;
        @Reference
        IActualAmountRecorService actualAmountRecorService;
        @Reference
        SaleService saleService;
        @RabbitHandler
        public void processLogRecord(TicketMessage ticketMessage){
                logger.info("-------保险订单投保-----收到出票消息:"+ticketMessage.getOwner()+","+ticketMessage.getOrderNo()+","+ticketMessage.getTradeNo());
                boolean result  = false;
                try{
                        RequestWithActor<Long> requestWithActor = new RequestWithActor<Long>();
                        requestWithActor.setEntity(ticketMessage.getTradeNo());
                        requestWithActor.setAgent(AgentUtil.getAgent());
                        List<SaleOrderExt> saleOrderExtList =  orderService.querySaleOrderForTranSaction(requestWithActor);
                        if(saleOrderExtList==null){
                                logger.error("----------------查询保险订单出错！");
                        }
                        boolean ispay = false;
                        for(SaleOrderExt saleOrderExt:saleOrderExtList){
                                ActualInfoSearchVO actualInfoSearchVO = actualAmountRecorService.queryActualInfoByBusNo(AgentUtil.getAgent(),saleOrderExt.getSaleOrderNo(),2);
                                for(ActualAmountRecord actualAmountRecord:actualInfoSearchVO.getActualAmountRecordList()){
                                        if(actualAmountRecord.getIncomeExpenseType() == 1&&actualAmountRecord.getGoodsType() == 3&&actualAmountRecord.getActualStatus() == 1){
                                                ispay = true;
                                        }
                                }
                                if(ispay){
                                       // List<Sale> sales = saleService.getByTradeNo(AgentUtil.getAgent(),ticketMessage.getTradeNo());
                                        requestWithActor.setEntity(saleOrderExt.getSaleOrderNo());
                                        result = orderService.buyInsure(requestWithActor);
                                }else{
                                        logger.info("-------------------该订单没有进行付款无法投保！订单号为:"+saleOrderExt.getSaleOrderNo());
                                }

                                if(!result){
                                        logger.info("----------------保险销售单"+saleOrderExt.getSaleOrderNo()+"--机票已出票但保险订单投保出现异常！！");
                                }
                        }

                } catch (Exception ex) {
                        logger.error("----------------查询保险订单出错！ 交易单号:"+ticketMessage.getTradeNo());
                        throw new GSSException("已出票保险订单投保失败！","003","已出票保险订单投保失败！");
                }
        }
}

package com.tempus.gss.product.ins.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.dps.order.entity.vo.RefuseMessage;
import com.tempus.gss.mss.service.IMssReserveService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ins.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ins.api.entity.SaleOrderExt;
import com.tempus.gss.product.ins.api.entity.vo.OrderCancelVo;
import com.tempus.gss.product.ins.api.service.IOrderService;
import com.tempus.gss.product.ins.dao.OrderServiceDao;
import com.tempus.gss.product.ins.dao.SaleChangeExtDao;
import com.tempus.gss.product.ins.dao.SaleOrderDetailDao;
import com.tempus.gss.system.service.IParamService;
import com.tempus.gss.vo.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 保险拒单状态监听
 * @author yunxiang.yu
 */
@Component
@RabbitListener(bindings = {
		@QueueBinding(value = @Queue(value = "ins.listenerDpsJdTicketedQue", durable = "true"),
				exchange = @Exchange(value = "gss-dps-order-callback", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true", durable = "true")
				, key = "ticketRefused"
		) }
)
public class insJdTicketListenter {

	protected static final Logger logger = LogManager.getLogger(insJdTicketListenter.class);
	/**
	 * 1:国内;2:国际
	 */
	public static final String TICKET_TYPE = "1";
	@Reference
	IOrderService orderService;
	@Reference
	ISaleOrderService saleOrderService;
	@Reference
	private IParamService paramService;

	@RabbitHandler
	public void processLogRecord(RefuseMessage ticketMessage){
		logger.info("-------机票发生拒单退款保险也退款-----收到消息--交易单:"+ticketMessage.getTradeNo());
		Agent agent = new Agent(ticketMessage.getOwner(), "sys");
		RequestWithActor<Long> requestWithActor = new RequestWithActor<Long>();
		requestWithActor.setEntity(ticketMessage.getTradeNo());
		requestWithActor.setAgent(agent);
		List<SaleOrderExt> saleOrderExtList =  orderService.querySaleOrderForTranSaction(requestWithActor);
		//获取后台配置
		String isRefund = paramService.getValueByKey("refund_or_cancel");//配置为1时不发生退款操作
		//如果后台没有设置refund_or_cancel  默认退款
		if("1".equals(isRefund)){
			return ;
		}
		logger.info("---------------------------机票拒单保险开始退款----------");
		for(SaleOrderExt saleOrderExt:saleOrderExtList){
			for (SaleOrderDetail saleOrderDetail : saleOrderExt.getSaleOrderDetailList()) {
				try {
					logger.info("---------------------------机票拒单保险开退款-被保人编号:"+saleOrderDetail.getInsuredNo());
					orderService.refundForPersonDetail(saleOrderExt,saleOrderDetail,agent);
					logger.info("cancelInsure end!");
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
}

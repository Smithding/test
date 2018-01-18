/**
 * WarnOrderServiceImpl.java
 * com.tempus.gss.product.ift.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年9月5日 		shuo.cheng
 *
 * Copyright (c) 2017, TNT All Rights Reserved.
*/

package com.tempus.gss.product.ift.service;

import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.email.EmailSender;
import com.tempus.gss.product.ift.api.entity.setting.IFTConfigs;
import com.tempus.gss.product.ift.api.service.setting.IConfigsService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.WarnOrder;
import com.tempus.gss.product.ift.api.entity.vo.WarnOrderVO;
import com.tempus.gss.product.ift.api.service.IWarnOrderService;
import com.tempus.gss.product.ift.dao.WarnOrderDao;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * ClassName:WarnOrderServiceImpl
 *
 * @author   shuo.cheng
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年9月5日		下午3:00:12
 *
 * @see 	 
 *  
 */
@Service
@org.springframework.stereotype.Service
@EnableAutoConfiguration
public class WarnOrderServiceImpl implements IWarnOrderService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	WarnOrderDao warnOrderDao;
	@Reference
	private IConfigsService configsService;
	@Autowired
	EmailSender emailSender;

	@Override
	public Page<WarnOrder> listWarnOrder(Page<WarnOrder> page, RequestWithActor<WarnOrderVO> warnOrderRequest) {

		log.info("调整单提醒查询开始");
		log.info("调整单提醒查询参数" + JSON.toJSONString(warnOrderRequest));
		try {
			if (warnOrderRequest.getAgent() == null && warnOrderRequest.getAgent().getOwner() == 0) {
				throw new GSSException("查询调整单提醒异常", "0401", "传入参数为空");
			}

			long startTime = System.currentTimeMillis();
			log.info("查询调整单提醒接口开始=====");
			List<WarnOrder> warnOrderList = warnOrderDao.listWarnOrder(page,
					warnOrderRequest.getEntity());
			long endTime = System.currentTimeMillis();
			log.info("查询调整单提醒接口结束=====" + (endTime - startTime));
			page.setRecords(warnOrderList);

			log.info("查询调整单提醒结束");
		} catch (Exception e) {
			log.error("查询调整单提醒异常", e);
			throw new GSSException("查询调整单提醒", "0403", "查询调整单提醒异常");
		}
		return page;
	}

	@Override
	public int addWarnOrder(WarnOrder warnOrder) {

		return warnOrderDao.insertSelective(warnOrder);

	}

	@Override
	public List<WarnOrder> findWarnOrders(WarnOrder warnOrder) {

		return warnOrderDao.findWarnOrders(warnOrder);

	}

	@Override
	public int updateWarnOrder(WarnOrder warnOrder) {

		return warnOrderDao.updateByPrimaryKeySelective(warnOrder);

	}

	/**
	 * 差错订单当天未处理完成定时发送邮件任务
	 * 每天晚上23:55分任务执行
	 */
	@Scheduled(cron ="0 0 23 * * ?")
	public void sendEmailSchedule() {
		try {
			Long outTimeOrderNum = warnOrderDao.getOutTimeWarnOrders();
			if (outTimeOrderNum != null && outTimeOrderNum.longValue() > 0) {
				Agent agent = new Agent(8755);
				IFTConfigs configs = configsService.getConfigByChannelID(agent, 0L);
				if (configs != null) {
					StringBuilder sbd = new StringBuilder();
					sbd.append("距离系统提醒时间已超过1天，目前还有");
					sbd.append(outTimeOrderNum);
					sbd.append("笔订单仍未处理，请提醒员工尽快处理，谢谢。");
					String title = "差错订单未处理提醒";
					Map config = configs.getConfig();
					String emails = (String) config.get("emails");
					String[] arrays = emails.split(";");
					for (String email : arrays) {
						String content = sbd.toString();
						log.info("发送邮件给:"+email+"内容:"+content);
						emailSender.sendHtmlMail(email, title, content);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							log.error("睡眠时定时任务中断", e);
						}
					}
				}
			}else{
				log.info("未找到未处理的出错订单，不发送邮件");
			}
		} catch (Exception e) {
			log.error("定时任务异常", e);
		}
	}

}


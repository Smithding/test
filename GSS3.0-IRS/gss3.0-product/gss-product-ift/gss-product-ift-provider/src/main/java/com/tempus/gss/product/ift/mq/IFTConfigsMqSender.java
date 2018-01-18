
package com.tempus.gss.product.ift.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <pre>
 * <b>渠道配置变动消息发送器.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> lh
 * <b>Date:</b> 2016年11月16日 下午3:39:35
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年11月16日 下午3:39:35    lh
 *         new file.
 * </pre>
 */
@Service(value = "ift.ConfigsMqSender")
public class IFTConfigsMqSender extends IFTMqSender {

	/** 日志记录器. */
	protected static final Logger logger = LogManager.getLogger(IFTConfigsMqSender.class);

	/** 构造方法 */
	public IFTConfigsMqSender() {
		super();
	}

	@PostConstruct
	public void initialize() {
		logger.info(this.getClass().getName() + " init ...");
	}

	@Value("${mq.queue.configsQue}")
	public void setQueue(String queue) {
		this.queue = queue;
	}

}

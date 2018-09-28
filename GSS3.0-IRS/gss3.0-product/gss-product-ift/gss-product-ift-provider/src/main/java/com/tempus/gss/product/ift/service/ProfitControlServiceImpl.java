package com.tempus.gss.product.ift.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.service.IProfitControlService;
import com.tempus.gss.product.ift.dao.ProfitControlDao;

/**
 * 
 * <pre>
 * <b>国际机票匹配控润规则服务接口.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> cz
 * <b>Date:</b> 2018年9月17日 上午10:16:36
 * <b>Copyright:</b> Copyright ©2018 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月17日 上午10:16:36    cz
 *         new file.
 * </pre>
 */
@Service
public class ProfitControlServiceImpl implements IProfitControlService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	ProfitControlDao profitControlDao;


}

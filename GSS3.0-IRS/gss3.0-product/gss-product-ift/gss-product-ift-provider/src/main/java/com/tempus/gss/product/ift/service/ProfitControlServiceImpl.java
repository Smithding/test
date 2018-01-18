package com.tempus.gss.product.ift.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.service.IProfitControlService;
import com.tempus.gss.product.ift.dao.ProfitControlDao;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class ProfitControlServiceImpl implements IProfitControlService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	ProfitControlDao profitControlDao;


}

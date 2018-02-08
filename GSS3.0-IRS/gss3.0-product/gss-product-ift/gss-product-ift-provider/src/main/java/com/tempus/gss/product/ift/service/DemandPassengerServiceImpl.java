package com.tempus.gss.product.ift.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.ift.api.entity.DemandPassenger;
import com.tempus.gss.product.ift.api.service.IDemandPassengerService;
import com.tempus.gss.product.ift.dao.DemandPassengerDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class DemandPassengerServiceImpl implements IDemandPassengerService {

	private String moduleName = "修改需求明细模块";

	@Autowired
	DemandPassengerDao demandPassengerDao;



	@Override
	@Transactional
	public int updateValid(DemandPassenger record) {

		if (record == null) {
			throw new GSSException(moduleName, "0101", "传入参数为空");
		}
		int flag = demandPassengerDao.updateValid(record);
		if (flag == 0) {
			throw new GSSException(moduleName, "0002", "valid");
		}
		return flag;
	}

	@Override
	@Transactional
	public int updateStatus(DemandPassenger record) {

		if (record == null) {
			throw new GSSException(moduleName, "0201", "传入参数为空");
		}
		int flag = demandPassengerDao.updateStatus(record);
		if (flag == 0) {
			throw new GSSException(moduleName, "0202", "修改status失败");
		}
		return flag;
	}
}

package com.tempus.gss.product.ift.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.ift.api.entity.DemandDetail;
import com.tempus.gss.product.ift.api.service.IDemandDetailService;
import com.tempus.gss.product.ift.dao.DemandDetailDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class DemandDetailServiceImpl  implements IDemandDetailService {

	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	DemandDetailDao demandDetailDao;



	@Override
	@Transactional
	public int updateValid(DemandDetail record) {

		if (record == null) {
			throw new GSSException("修改需求乘客模块", "0101", "传入参数为空");
		}
		int flag = demandDetailDao.updateValid(record);
		if (flag == 0) {
			throw new GSSException("修改需求乘客模块", "0202", "valid修改失败");
		}
		return flag;
	}

	@Override
	@Transactional
	public int updateStatus(DemandDetail record) {

		if (record == null) {
			throw new GSSException("修改需求乘客模块", "0201", "传入参数为空");
		}
		int flag = demandDetailDao.updateStatus(record);
		if (flag == 0) {
			throw new GSSException("修改需求乘客模块", "0202", "status修改失败");
		}
		return flag;
	}
}

package com.tempus.gss.product.ift.service;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.SaleOrderDetail;
import com.tempus.gss.product.ift.api.service.ISaleOrderDetailService;
import com.tempus.gss.product.ift.dao.SaleOrderDetailDao;

@Service
@EnableAutoConfiguration
public class SaleOrderDetailServiceimpl implements ISaleOrderDetailService{
	protected final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SaleOrderDetailDao detailDao;
	
	@Reference
	private ILogService logService;

	
	@Override
	public List<SaleOrderDetail> selectBySaleOrderNo(Long saleOrderNo) {
		return detailDao.selectBySaleOrderNo(saleOrderNo);
	}

	@Override
	public SaleOrderDetail selectByPassengerAndLeg(Long passengerNo, Long legNo) {
		return null;
	}

	@Override
	@Transactional
	public int upateSaleOrder(RequestWithActor<SaleOrderDetail> saleOrderDetail) {
		int flag = detailDao.updateByPrimaryKeySelective(saleOrderDetail.getEntity());
		if (flag == 0) {
			throw new GSSException("修改订单明细模块", "0301", "修改订单明细失败");
		}
		/* 创建操作日志 */
		try {
			LogRecord logRecord = new LogRecord();
			logRecord.setAppCode("UBP");
			logRecord.setCreateTime(new Date());
			logRecord.setTitle("修改国际销售单");
			logRecord.setDesc(JSON.toJSONString(saleOrderDetail));
			logRecord.setOptLoginName(saleOrderDetail.getAgent().getAccount());
			logRecord.setRequestIp(saleOrderDetail.getAgent().getIp());
			logRecord.setBizCode("IFT-OrderServiceImpl-updateSaleOrderExt");
			logRecord.setBizNo(String.valueOf(saleOrderDetail.getEntity().getSaleOrderNo()));
//			logService.insert(logRecord);
		} catch (Exception e) {
			log.error("添加操作日志异常===" + e);
		}
		return flag;
	}
	
	
	@Override
	@Transactional
	public int batchUpdate(List<SaleOrderDetail> list) {
		int flag = detailDao.batchUpdate(list);
		if (flag == 0) {
			throw new GSSException("修改订单明细模块", "0301", "修改订单明细失败");
		}
		return flag;
	}

	@Override
	public int updateSaleOrderDetailStatus() {
		return detailDao.updateSaleOrderDetailStatus();
	}

	@Override
	public int updateSaleOrderDetailStatusByNo(Long saleOrderNo) {
		return detailDao.updateSaleOrderDetailStatusByNo(saleOrderNo);
	}

}

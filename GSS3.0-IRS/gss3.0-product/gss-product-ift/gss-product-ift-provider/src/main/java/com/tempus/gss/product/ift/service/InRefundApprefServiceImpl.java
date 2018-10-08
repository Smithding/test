package com.tempus.gss.product.ift.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.vo.InRefundApprefRequest;
import com.tempus.gss.product.ift.api.service.InRefundApprefService;
import com.tempus.gss.product.ift.dao.InRefundApprefDao;
import com.tempus.gss.util.JsonUtil;

@Service
@EnableAutoConfiguration
public class InRefundApprefServiceImpl implements InRefundApprefService {
    private final transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    InRefundApprefDao inRefundApprefDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateInRefundAppref(Map<String, Object> map) throws Exception {
        log.info("更新退款申请单开始");
        try {
            inRefundApprefDao.updateStatus(map);
        } catch (Exception e) {
            log.error("国际退款申请单更新失败", e);
            throw new GSSException("国际退款申请单查询模块", "0106", "国际退款申请单模块更新失败");
        }
        log.info("更新退款申请单结束");
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public InRefundApprefRequest getInRefundAppref(String orderid) throws Exception {
        InRefundApprefRequest InRefundApprefRequest = null;
        try {
            InRefundApprefRequest = inRefundApprefDao.selectInRefundApprefVo(orderid);
        } catch (Exception e) {
            log.error("国际退款申请单查询失败", e);
            throw new GSSException("国际退款申请单查询模块", "0107", "国际退款申请单模块查询失败");
        }
        return InRefundApprefRequest;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int create(InRefundApprefRequest inRefundAppref) throws Exception {
        log.info("创建创建国际退款申请单开始");
        try {
            int num = inRefundApprefDao.insertAndGetId(inRefundAppref);
            if (num == 0) {
                log.error("InRefundApprefRequest插入异常");
                throw new GSSException("创建退票退款申请模块", "0102", "退票退款插入失败");
            }
        } catch (Exception e) {
            log.error("国际退款申请单创建失败", e);
            throw new GSSException("创建国际退款申请单模块", "0107", "创建国际退款申请单模块失败");
        }
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateInRefundAppref(InRefundApprefRequest inRefundAppref) {
        log.info("更新退款申请单开始");
        try {
            int flag = inRefundApprefDao.updateByPrimaryKeySelective(inRefundAppref);
            if (flag == 0) {
                log.error("更新退款申请单");
                throw new GSSException("更新退款申请单模块", "0601", "退款申请单更新失败");
            }
        } catch (Exception e) {
            log.error("更新退款申请单", e);
            throw new GSSException("更新退款申请单模块", "0606", "退款申请单更新模块失败");
        }
        log.info("更新退款申请单结束");
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Page<InRefundApprefRequest> queryInRefApprefByVo(Page page, RequestWithActor<InRefundApprefRequest> requestWithActor) {
        log.info("查询国际退款单开始=======");
        try {
            if (requestWithActor.getEntity() == null) {
                log.error("国际退款单对象为空");
                throw new GSSException("国际退款单查询对象为空", "0001", "查询国际退款单失败");
            }
            List<InRefundApprefRequest> list = inRefundApprefDao.queryObjByKey(page, requestWithActor.getEntity());
            log.info(JsonUtil.toJson(list));
            page.setRecords(list);
        } catch (Exception e) {
            log.error("查询国际退款单", e);
        }
        log.info("查询国际退款单结束=======");
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public InRefundApprefRequest getInRefundApprefBySettlementId(Long settlementId) {
        InRefundApprefRequest InRefundApprefRequest = null;
        try {
            InRefundApprefRequest = inRefundApprefDao.selectInRefundApprefBySettlementId(settlementId);
        } catch (Exception e) {
            log.error("国际退款申请单查询失败", e);
            throw new GSSException("国际退款申请单查询模块", "0107", "国际退款申请单模块查询失败");
        }
        return InRefundApprefRequest;
    }

}

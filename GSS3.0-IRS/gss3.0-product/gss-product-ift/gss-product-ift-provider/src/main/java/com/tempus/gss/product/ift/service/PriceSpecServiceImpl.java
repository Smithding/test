package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PriceSpec;
import com.tempus.gss.product.ift.api.entity.vo.PriceSpecVo;
import com.tempus.gss.product.ift.api.service.IPriceSpecService;
import com.tempus.gss.product.ift.dao.PriceSpecDao;
import com.tempus.gss.system.service.IMaxNoService;
import com.tempus.gss.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 算价总则服务.
 */
@Service
@EnableAutoConfiguration
public class PriceSpecServiceImpl implements IPriceSpecService {
    
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    PriceSpecDao priceSpecDao;
    
    @Reference
    private IMaxNoService maxNoService;
    
    /**
     * 创建总则
     *
     * @param priceSpec
     * @return
     */
    @Override
    @Transactional
    @CachePut(value = "priceSpec", key = "#priceSpec.entity.airline")
    public PriceSpec createPriceSpec(RequestWithActor<PriceSpec> priceSpec) throws GSSException {
        
        log.info("创建总则开始==========");
        if (priceSpec.getEntity().getAirline() == null || priceSpec.getEntity().getNoAwardCabin() == null || priceSpec.getEntity().getNoAwardFareBase() == null || priceSpec.getEntity().getNoAwardFlightNo() == null || priceSpec.getEntity().getNoAwardTourCode() == null || priceSpec.getEntity().getNoAwardType() == null) {
            log.info("创建异常，关键字段不能为空==========");
            throw new GSSException("算价总则创建模块", "00101", "插入失败,关键字段不能为空");
        }
        // 添加或更新标志 true=添加 false=更新
        boolean saveOrUpdateFlag = true;
        if (priceSpec.getEntity().getId() != null) {// 更新
            saveOrUpdateFlag = false;
        } else {//添加
            priceSpec.getEntity().setPriceSpecNo(maxNoService.generateBizNo("IFT_PRICE_SPEC_NO", 34));
        }
        String json = JsonUtil.toJson(priceSpec.getEntity().getFormula());
        priceSpec.getEntity().setFormulaData(json);
        
        priceSpec.getEntity().setValid(true);
        
        int price = 0;
        if (!saveOrUpdateFlag) {// 更新
            PriceSpec ps = priceSpecDao.selectByPrimaryKey(priceSpec.getEntity().getPriceSpecNo());
            ps.setPriceSpecNo(IdWorker.getId());
            ps.setValid(false);
            priceSpecDao.insert(ps);
            price = priceSpecDao.updateByPrimaryKeySelective(priceSpec.getEntity());
        } else {
            price = priceSpecDao.insert(priceSpec.getEntity());
        }
        if (price == 0) {
            log.error("创建时异常========");
            throw new GSSException("算价总则创建模块", "00103", "创建发生异常,请检查");
        }
        log.info("创建总则结束");
        return priceSpec.getEntity();
    }
    
    /**
     * 更新总则，进行更新版本号操作
     *
     * @param priceSpec
     */
    @Override
    @Transactional
    @CacheEvict(value = "priceSpec", key = "#priceSpec.entity.airline")
    public PriceSpec updatePriceSpec(RequestWithActor<PriceSpec> priceSpec) throws GSSException {
        
        log.info("更新总则开始");
        PriceSpec pric = priceSpecDao.selectByPrimaryKey(priceSpec.getEntity().getPriceSpecNo());
        if (pric == null) {
            throw new GSSException("算价总则更新模块", "00201", "此编号不存在或为空-------");

        }
        priceSpecDao.updateByPrimaryKeySelective(priceSpec.getEntity());
        log.info("更新结束");
        return priceSpec.getEntity();
    }
    
    /**
     * 删除总则.
     *
     * @param priceSpec
     */
    @Override
    @Transactional
    @CacheEvict(value = "priceSpec", key = "#priceSpec.entity.airline")
    public void deletePriceSpec(RequestWithActor<PriceSpec> priceSpec) {
        
        log.info("删除总则开始");
        if (priceSpec.getEntity() == null) {
            log.error("删除产品参数条件为空");
            throw new GSSException("算价总则删除模块", "00301", "删除信息异常");
        }
        int price = 0;
        try {
            priceSpec.getEntity().setValid(false);
            priceSpecDao.deleteByPrimaryKeySelective(priceSpec.getEntity());
        } catch (Exception e) {
            log.error("priceSpec删除失败", e);
            throw new GSSException("删除总则模块", "00302", "删除信息异常");
        }
        log.info("删除总则结束");
    }
    
    /**
     * 分页查询总则.
     *
     * @param query
     */
    @Override
    public Page<PriceSpec> selectPriceSpec(Page<PriceSpec> page, RequestWithActor<PriceSpecVo> query) {
        
        log.info("查询总则开始");
        try {
            List<PriceSpec> priceSpecList = priceSpecDao.queryObjByKey(page, query.getEntity());
            page.setRecords(priceSpecList);
        } catch (Exception e) {
            log.error("供应商信息表中未查询出相应的结果集", e);
            throw new GSSException("查询供应商信息模块", "0022", "查询供应商信息失败");
        }
        log.info("查询总则结束");
        return page;
    }
    
    /**
     * 根据航司查询总则.
     *
     * @param airline
     * @return 返回航司总则，如果没有就返回*号的航司，都没有则返回空.
     */
    @Override
    @Cacheable(value = "priceSpec", key = "#airline.entity")
    public PriceSpec getPriceSpec(RequestWithActor<String> airline) {
        
        if (airline.getEntity() == null) {
            throw new GSSException("算价总则查询模块", "00501", "查询时，航司不能为空-------");
        } else {
            PriceSpec ps = priceSpecDao.selectAirLine(airline.getEntity());
            if (ps != null) {
                return ps;
            }
        }
        return null;
    }
    
    /**
     * 根据编号查询总则.
     *
     * @param priceSpecNo
     * @return 返回航司总则，如果没有就返回*号的航司，都没有则返回空.
     */
    public PriceSpec getPriceSpecByPriceSpecNo(Long priceSpecNo) {
        
        return priceSpecDao.selectByPrimaryKey(priceSpecNo);
    }
    
    /**
     * 根据传入航司判断航司是否存在
     */
    @Override
    public PriceSpec getExistPriceSpec(String airline) {
        
        return priceSpecDao.selectAirLine(airline);
    }
    
    @Override
    public boolean validate(RequestWithActor<Long> request, boolean isInvalid) throws GSSException {
        if (null != request && null != request.getEntity()) {
            PriceSpec priceSpec = getPriceSpecByPriceSpecNo(request.getEntity());
            String log = priceSpec.getLog();
            Map<String, String> map = new HashMap<>();
            map.put("MODIFIER", request.getAgent().getAccount());
            map.put("MODIFYTIME", DateUtil.getCurDate());
            if (isInvalid) {
                map.put("ACTION", "Invalid");
                priceSpec.setValid(false);
            } else {
                map.put("ACTION", "Valid");
                priceSpec.setValid(true);
            }
            log = JsonUtil.toJson(map,"{}");
            priceSpec.setLog(log);
            return priceSpecDao.validate(priceSpec) > 0;
        }
        return false;
    }
}

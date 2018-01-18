package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Pnr;
import com.tempus.gss.product.ift.api.exceptions.IFTException;
import com.tempus.gss.product.ift.api.service.IPnrManageService;
import com.tempus.gss.product.ift.dao.PnrDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Service
@EnableAutoConfiguration
public class PnrManageServiceimpl implements IPnrManageService {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    PnrDao pnrDao;
    
    /**
     * 通过pnr码，获取所有pnr，包括已取消的
     * selectByPnr方法最多只返回20条，按照创建时间倒序排
     *
     * @return
     */
    @Override
    public List<Pnr> getAllByPnr(RequestWithActor<String> actor) throws Exception {
        if (null == actor.getEntity()) {
            throw new IFTException("pnr查询", "不存在对应的记录", "getAllByPnr方法的参数为空", "0009");
        }
        return pnrDao.selectByPnr(actor.getEntity());
    }
    
    /**
     * 通过pnr码，获取所有pnr，包括已取消的
     * selectByPnr方法最多只返回20条，按照创建时间倒序排
     *
     * @return
     */
    @Override
    public Page<Pnr> getAllPnr(Page<Pnr> page, Pnr pnr) throws Exception {
        List<Pnr> reportOuts = pnrDao.queryPnr(page, pnr);
        page.setRecords(reportOuts);
        return page;
    }
    
    /**
     * 取消pnr，通过pnrNo，将pnr传到页面，用户进行选择后传回No，将status设为 3
     *
     * @return
     */
    @Override
    public String cancelPnr(RequestWithActor<Long> actor) throws Exception {
        try {
            if (null == actor.getEntity()) {
                throw new IFTException("pnr查询", "不存在对应的记录", "cancelPnr方法的参数为空", "0099");
            }
            Pnr pnr = new Pnr();
            pnr.setModifier(actor.getAgent().getAccount());
            pnr.setModifyTime(DateUtil.getDate(DateUtil.getCurDateTime()));
            pnr.setPnrNo(actor.getEntity());
            pnr.setStatus("3");
            return pnrDao.updateByPrimaryKeySelective(pnr) > 0 ? "取消成功！" : "取消Pnr失败！";
        } catch (Exception e) {
            log.error("cancelPnrServiceimpl ，出错！");
            log.error(e.getMessage());
            return e.getMessage();
        }
        
    }
    
    /**
     * 取消pnr，通过pnrNo，将pnr传到页面，用户进行选择后传回No，将status设为 3
     *
     * @return
     */
    @Override
    public List<Pnr> selectByOrderNo(RequestWithActor<Long> actor) throws Exception {
        try {
            if (null == actor.getEntity()) {
                throw new IFTException("pnr查询", "不存在对应的记录", "cancelPnr方法的参数为空", "0099");
            }
            return pnrDao.selectByOrderNo(actor.getEntity());
        } catch (Exception e) {
            log.error("cancelPnrServiceimpl ，出错！");
            log.error(e.getMessage());
            return null;
        }
        
    }
}

package com.tempus.gss.product.ift.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.ift.api.entity.Pnr;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PnrDao extends BaseDao<Pnr, Object> {
    
    /**
     * 根据编号查询Pnr信息
     */
    public List<Pnr> selectByOrderNo(Long sourceNo);
    
    /**
     * 查询的可用状态的pnr  valid=1
     * @param pnr
     * @return
     */
    public List<Pnr> queryByPnr(String pnr);
    
       /**
     * 查询的可用状态的pnr  valid=1
     * @return
     */
    public List<Pnr> queryPnr(Page<Pnr> page,Pnr pnr);
    
    /**
     * 查询所有pnr
     * @param pnr
     * @return
     */
    public List<Pnr> selectByPnr(String pnr);
}
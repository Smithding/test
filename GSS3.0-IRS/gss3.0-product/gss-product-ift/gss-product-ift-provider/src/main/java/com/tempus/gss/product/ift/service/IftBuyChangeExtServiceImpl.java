package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.BuyChangeExt;
import com.tempus.gss.product.ift.api.service.IftBuyChangeExtService;
import com.tempus.gss.product.ift.dao.BuyChangeExtDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 获取改签扩展记录服务实现
 *
 * @author li.zhi
 * @create 2018-07-22 21:50
 **/
@Service
public class IftBuyChangeExtServiceImpl implements IftBuyChangeExtService{

    public static Logger log = LoggerFactory.getLogger(IftBuyChangeExtServiceImpl.class);

    @Autowired
    private BuyChangeExtDao buyChangeExtDao;

    @Override
    public BuyChangeExt queryBuyChgeExtByNo(Long saleChangeNo) {
        BuyChangeExt buyChangeExt = null;
        buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);
        return  buyChangeExt;
    }

    @Override
    public void updateBuyChangeExt(BuyChangeExt buyChangeExt) {
        log.info("更新改签扩展记录信息:"+buyChangeExt.toString());
        buyChangeExtDao.updateByPrimaryKeySelective(buyChangeExt);
    }

    @Override
    public void updateBuyChangeByChangeNo(RequestWithActor<Long> requestWithActor, String status){
        Long saleChangeNo = requestWithActor.getEntity();
        BuyChangeExt buyChangeExt = buyChangeExtDao.selectBySaleChangeNoFindOne(saleChangeNo);

        if (buyChangeExt != null) {
            com.tempus.gss.vo.Agent agent = requestWithActor.getAgent();
            Date modifyTime = new Date();
            buyChangeExt.setAirLineRefundStatus(Integer.parseInt(status));//采购拒单后再次拒回给销售
            buyChangeExt.setModifyTime(modifyTime);
            buyChangeExt.setModifier(agent.getAccount());
            log.info("更新改签扩展记录信息:"+buyChangeExt.toString());
            buyChangeExtDao.updateByPrimaryKeySelective(buyChangeExt);
        }
    }
}

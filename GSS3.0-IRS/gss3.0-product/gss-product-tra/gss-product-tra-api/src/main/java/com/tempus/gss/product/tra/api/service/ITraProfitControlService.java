package com.tempus.gss.product.tra.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.tra.api.entity.*;
import com.tempus.gss.product.tra.api.entity.vo.TraProfitControlVo;


public interface ITraProfitControlService {
     
    /**
     * 创建控润信息
     * @param requestWithActor
     * @return
     */
    int createProfitControl(RequestWithActor<TraProfitControl> requestWithActor);

    /**
     * 删除
     *
     * @param requestWithActor
     * @return
     * @throws Exception
     */
     int deleteProfitControl(RequestWithActor<Long> requestWithActor);
     
     /**
      * 修改控润
      * @param requestWithActor
      * @return
      */
     int updateProfitControl(RequestWithActor<TraProfitControl> requestWithActor);

    /**
     * 根据订单编号查询订单
     *
     * @param requestWithActor
     * @return
     */
    public TraProfitControl queryProfitControl(RequestWithActor<Long> requestWithActor); 
    
    /**
     * 通过条件查询销售单集合
     * @param pageRequest
     * @return
     */
    public Page<TraProfitControl> queryPageProfitControlList(Page<TraProfitControl> page, RequestWithActor<TraProfitControlVo> pageRequest) ;

    public List<TraProfitControl> queryProfitControlList();
    
    /**
     *
     * cancelProfitControl:取消控润
     */
    boolean cancelProfitControl(RequestWithActor<Long> requestWithActor);

    /**
     * 获取控润
     */

     List<TraProfitControl> selectByTrainName (String name);

    
}

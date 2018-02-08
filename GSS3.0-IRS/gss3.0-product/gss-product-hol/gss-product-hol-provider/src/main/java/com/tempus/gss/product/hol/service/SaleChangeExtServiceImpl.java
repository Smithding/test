package com.tempus.gss.product.hol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.StringUtil;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.order.entity.SaleChange;
import com.tempus.gss.order.entity.SaleOrder;
import com.tempus.gss.order.service.ISaleChangeService;
import com.tempus.gss.order.service.ISaleOrderService;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.SaleChangeExt;
import com.tempus.gss.product.hol.api.entity.vo.SaleChangeExtVo;
import com.tempus.gss.product.hol.api.service.ISaleChangeExtService;
import com.tempus.gss.product.hol.dao.SaleChangeExtMapper;
import com.tempus.gss.vo.Agent;

/**
 * SaleChangeExt 表数据服务层接口实现类
 */
@Service
public class SaleChangeExtServiceImpl extends SuperServiceImpl<SaleChangeExtMapper, SaleChangeExt> implements ISaleChangeExtService {
    @Autowired
    SaleChangeExtMapper saleChangeExtMapper;

    @Reference
    ISaleChangeService saleChangeService;
    
    @Reference
    ISaleOrderService saleOrderService;


    /**
     * 根据条件查询退房订单列表
     *
     * @param page
     * @param pageRequest
     * @return
     */
    public Page<SaleChangeExt> querySaleChangeExtWithPage(Page<SaleChangeExt> page, RequestWithActor<SaleChangeExtVo> pageRequest) {
        logger.info("根据条件查询退房订单列表开始,入参:" + JSONObject.toJSONString(pageRequest));
        Agent agent = pageRequest.getAgent();
        if (agent == null) {
            logger.error("当前操作用户不能为空");
            throw new GSSException("查询酒店订单列表", "1010", "当前操作用户不能为空");
        }
        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
        if (agent.getType() != null && agent.getType() != 0L) { //不是运营平台账号
            if (pageRequest.getEntity() == null) {
                SaleChangeExtVo saleChangeExtVo = new SaleChangeExtVo();
                saleChangeExtVo.setOwner(pageRequest.getAgent().getOwner());
                saleChangeExtVo.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(saleChangeExtVo);
            } else {
                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
                pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
            }
        }
        List<SaleChangeExt> saleChangeExtList = saleChangeExtMapper.querySaleChangeExtList(page, pageRequest.getEntity());
        for (SaleChangeExt saleChangeExt : saleChangeExtList) {
            SaleChange saleChange = saleChangeService.getSaleChangeByNo(pageRequest.getAgent(), saleChangeExt.getSaleChangeNo());
            saleChangeExt.setSaleChange(saleChange);
            SaleOrder saleOrder=saleOrderService.getSOrderByNo(agent, saleChange.getSaleOrderNo());
            saleChangeExt.setSaleOrder(saleOrder);
        }
        page.setRecords(saleChangeExtList);
        return page;
    }

    /**
     * 确认退房
     * @param agent
     * @param saleChangeNo
     * @return
     */
    public Boolean confirmCancelRoom(Agent agent,Long saleChangeNo){
        logger.info("确认退房开始");
        if (StringUtil.isNullOrEmpty(agent)) {
            logger.error("当前操作用户不能为空");
            throw new GSSException("确认退房", "1010", "当前操作用户不能为空");
        }
        if(StringUtil.isNullOrEmpty(saleChangeNo)){
            logger.error("销售变更编号不能为空");
            throw new GSSException("确认退房", "1011", "销售变更编号不能为空");
        }
        SaleChangeExt saleChangeExt = new SaleChangeExt();
        saleChangeExt.setSaleChangeNo(saleChangeNo);
        saleChangeExt = saleChangeExtMapper.selectOne(saleChangeExt);
        if (StringUtil.isNullOrEmpty(saleChangeExt)){
            logger.error("退房变更记录不存在");
            throw new GSSException("确认退房", "1012", "退房变更记录不存在");
        }
       /* HotelOrderDetail hotelOrderDetail = qtHotelOrderService.getHotelOrderDetail(agent,saleChangeExt.getSaleOrderNo());
        try {
            CancelRoomReq cancelRoomReq = new CancelRoomReq();
            cancelRoomReq.setOrderNo(hotelOrderDetail.getOrderNumber());
            cancelRoomReq.setRemark(saleChangeExt.getRemark());
            cancelRoomReq.setCustomerCode(String.valueOf(agent.getOwner()));
            cancelRoomReq.setUserID(String.valueOf(agent.getNum()));

            QTCommonRequest qtCommonRequest = new QTCommonRequest();
            qtCommonRequest.setAction(ActionType.ApplyReturnOrder.toString());
            qtCommonRequest.setRequestJson(JSONObject.toJSONString(cancelRoomReq));
            String reqJson = JSONObject.toJSONString(qtCommonRequest);
            BaseResponse baseResponse = HttpClientUtil.doJsonPost(QianTaoConfig.ORDER_URL, reqJson, BaseResponse.class);
            if (baseResponse != null) {
                if (!baseResponse.getIsSuccess()) {//酒店申请退房请求失败
                    logger.error("酒店申请退房请求失败");
                    throw new GSSException("确认退房", "0113", "酒店申请退房请求失败:" + baseResponse.getErrorMessage());
                }
            } else {
                logger.error("酒店申请退房请求返回空值");
                throw new GSSException("确认退房", "0114", "酒店申请退房请求返回空值");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("酒店申请退房请求出错");
            throw new GSSException("确认退房", "0115", "酒店申请退房请求出错");
        }*/
        return true;
    }
}

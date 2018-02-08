package com.tempus.gss.product.tra.api.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.tra.api.entity.QueryTrainRequest;
import com.tempus.gss.product.tra.api.entity.TraSaleChangeExt;
import com.tempus.gss.product.tra.api.entity.TraSaleOrderDetail;
import com.tempus.gss.product.tra.api.entity.TraSaleOrderExt;
import com.tempus.gss.product.tra.api.entity.TrainResponse;
import com.tempus.gss.product.tra.api.entity.vo.OrderCreateVo;
import com.tempus.gss.product.tra.api.entity.vo.RefundTicketVo;
import com.tempus.gss.product.tra.api.entity.vo.TraSaleChangeExtVo;
import com.tempus.gss.product.tra.api.entity.vo.TraSaleOrderExtVo;
import com.tempus.gss.vo.Agent;

/**
 * Created by 杨威 on 2017/2/24.
 */
public interface ITrainService {
    /**
     * 方法描述:  查询火车信息
     * 作    者： Administrator
     * 日    期： 2016年7月9日-下午4:48:11
     * @param request
     * @return
     * 返回类型： TrainSearchResponse
     */
    TrainResponse queryTrain(QueryTrainRequest request);
    /**
     * 方法描述:  火车票预订
     * 作    者： Administrator
     * 日    期： 2016年7月11日-上午10:55:36
     * @param requestWithActor
     * @return
     * 返回类型： OrderTrainResponse
     */
    Long createOrder(RequestWithActor<OrderCreateVo> requestWithActor) throws Exception;

    /**
     *
     * buyInsure:出票
     *
     * @param @param
     *            requestBean
     * @param @return
     *            设定文件
     * @return boolean DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    boolean buyTrain(RequestWithActor<Long> requestWithActor) throws Exception;

    /**
     * 根据订单编号查询订单
     *
     * @param requestWithActor
     * @return
     */
    TraSaleOrderExt queryTraSaleOrder(RequestWithActor<Long> requestWithActor);
    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteSaleOrder(RequestWithActor<Long> id) throws Exception;

    /**
     *
     * cancelSaleOrder:退票
     *
     * @param @param
     *            cancel
     * @param @return
     *            设定文件
     * @return PolicyVo DOM对象
     * @throws @since
     *             CodingExample Ver 1.1
     */
    public boolean refundSaleOrderExt(RequestWithActor<TraSaleOrderExt> requestWithActor)  throws Exception;

    /**
     * 通过条件查询销售单集合
     * @param pageRequest
     * @return
     */
    public Page<TraSaleOrderExt> querySaleOrderList(Page<TraSaleOrderExt> page, RequestWithActor<TraSaleOrderExtVo> pageRequest) ;


    /**
     * 统计火车票订单数量
     * @param pageRequest
     * @return
     */
    public Integer countSaleOrderList(RequestWithActor<TraSaleOrderExtVo> pageRequest);

    /**
     * 更新保单号
     * @param requestWithActor
     * @return
     */
    int updateStatusDetail(RequestWithActor<TraSaleOrderDetail> requestWithActor);

    /**
     *
     * cancelSaleOrderExt:取消订单
     *
     * @param      saleOrderNo
     * @return void    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */
    boolean cancelSaleOrderExt(RequestWithActor<Long> saleOrderNo) throws Exception;


    public boolean updateRefund(RequestWithActor<Long> requestWithActor, RefundTicketVo refundTicketVo);
    
    /**
     * 通过buyOrderNo查询订单
     */
    public TraSaleOrderExt queryByBuyOrderNo(RequestWithActor<Long> buyOrderNo);

    /**
     * 通过条件查询查询火车票退票单
     */
    public Page<TraSaleChangeExt> querySaleChangeList(Page<TraSaleChangeExt> page, RequestWithActor<TraSaleChangeExtVo> pageRequest);
    
    /**
     * 根据saleOrderNo查询所有销售变更单
     * @param requestWithActor
     * @return
     */
    public List<TraSaleChangeExt> querySaleChangeListBySaleOrderNo(RequestWithActor<Long> requestWithActor);

    /**
     * 根据退票编号查询订单
     *
     * @param requestWithActor
     * @return
     */
    public TraSaleChangeExt querySaleChange(RequestWithActor<Long> requestWithActor);
    
    public TraSaleChangeExt queryByBuyChangeNo(RequestWithActor<Long> requestWithActor);
    
    /**
     * 查询详情
     */
    public TraSaleOrderDetail getSaleOrderDetail(RequestWithActor<Long> saleOrderDetailNo);

    /**
     * 对外api占座接口
     */
    public boolean bookSeat(Agent agent, String method, String param, String sign);
    
    /**
     * 对外api出票接口
     */
    public boolean issueOrder(Agent agent, String method, String param, String sign);
    
    /**
     * 对外api订单过期推送接口
     */
    public boolean notifyOvertimeOrder(Agent agent, String method, String param, String sign);

    /**
     * 对外api订单退票退款结果推送接口
     */
    public boolean refundTicket(Agent agent, String method, String param, String sign);
}



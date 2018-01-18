package com.tempus.gss.product.hol.service;

import java.util.List;

import com.tempus.gss.exception.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.hol.api.entity.response.HotelShowOrder;
import com.tempus.gss.product.hol.api.entity.vo.HotelOrderVo;
import com.tempus.gss.product.hol.api.service.ITCHotelOrderExportService;
import com.tempus.gss.product.hol.dao.HotelOrderManageMapper;
import com.tempus.gss.vo.Agent;

@Service
public class TCHotelOrderExportServiceImpl implements ITCHotelOrderExportService{
    @Autowired
    HotelOrderManageMapper hotelOrderManageMapper;
	protected final transient Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Page<HotelShowOrder> queryOrderListWithPage(Page<HotelShowOrder> page, RequestWithActor<HotelOrderVo> pageRequest) {
		logger.info("根据条件查询同程酒店订单列表开始,入参:" + JSONObject.toJSONString(pageRequest));
        Agent agent = pageRequest.getAgent();
        if (agent == null) {
            logger.error("当前操作用户不能为空");
            throw new GSSException("查询酒店订单列表", "1010", "当前操作用户不能为空");
        }
        //如果是运营平台账号，可查看全部订单，否则只能查看当前账号自己创建的订单
        if (agent.getType() != null && agent.getType() != 0L) { //不是运营平台账号
            if (pageRequest.getEntity() == null) {
                HotelOrderVo hotelOrderVo = new HotelOrderVo();
                hotelOrderVo.setOwner(pageRequest.getAgent().getOwner());
                hotelOrderVo.setCreator(pageRequest.getAgent().getAccount());
                pageRequest.setEntity(hotelOrderVo);
            } else {
                pageRequest.getEntity().setOwner(pageRequest.getAgent().getOwner());
                pageRequest.getEntity().setCreator(pageRequest.getAgent().getAccount());
            }
        }
        List<HotelShowOrder> hotelOrderList = hotelOrderManageMapper.queryOrderList(page, pageRequest.getEntity());
        
        /**
         * 根据saleOrderNo通过API接口去其他子系统去获取数据
         * 需要根据list的长度去执行获取数据的次数,此操作可能会存在性能问题
         */
        /*for (HotelOrder hotelOrder : hotelOrderList) {
            SaleOrder saleOrder = saleOrderService.getSOrderByNo(pageRequest.getAgent(), hotelOrder.getSaleOrderNo());
            hotelOrder.setSaleOrder(saleOrder);
        }*/
        page.setRecords(hotelOrderList);
        return page;
	}
}

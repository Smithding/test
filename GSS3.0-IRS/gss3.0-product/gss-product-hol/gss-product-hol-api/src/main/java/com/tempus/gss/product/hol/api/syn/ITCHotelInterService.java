package com.tempus.gss.product.hol.api.syn;

import java.util.List;

import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.hol.api.entity.FormDate;
import com.tempus.gss.product.hol.api.entity.request.tc.AllHotelListReq;
import com.tempus.gss.product.hol.api.entity.request.tc.AssignDateHotelReq;
import com.tempus.gss.product.hol.api.entity.request.tc.GetOrderLogInfoReq;
import com.tempus.gss.product.hol.api.entity.request.tc.IncrHotelList;
import com.tempus.gss.product.hol.api.entity.request.tc.SingleHotelDetailReq;
import com.tempus.gss.product.hol.api.entity.response.tc.AssignDateHotel;
import com.tempus.gss.product.hol.api.entity.response.tc.CancelReasonModel;
import com.tempus.gss.product.hol.api.entity.response.tc.OrderLogModel;
import com.tempus.gss.product.hol.api.entity.response.tc.ResBaseInfo;
import com.tempus.gss.product.hol.api.entity.response.tc.ResInfoList;
import com.tempus.gss.product.hol.api.entity.response.tc.ResourceProductIds;
import com.tempus.gss.product.hol.api.entity.response.tc.TCHotelDetailResult;
import com.tempus.gss.vo.Agent;

/**
 * 同程酒店接对接口
 * @author kai.yang
 *
 */
public interface ITCHotelInterService {
	/**
	 * 酒店全量同步接口
	 * @param allHotelListReq
	 * @return
	 */
	public List<ResInfoList> queryTCHotelList(AllHotelListReq allHotelListReq) throws GSSException;
	
	/**
	 * 酒店详情接口
	 * @param singleHotelDetailReq
	 * @return
	 */
	public TCHotelDetailResult queryTCHotelDetail(SingleHotelDetailReq singleHotelDetailReq) throws GSSException;
	
	
	/**
	 * 获取同程某一时间段内发生对应类型变更的酒店&及房型 id 列表
	 * @param incrHotelList
	 * @return
	 */
	public List<ResourceProductIds> queryIncrHotelList(IncrHotelList incrHotelList) throws GSSException;
	
	/**
	 * 获取同程某一时间段/某个月的酒店价格和库存信息
	 * @param assignDateHotelReq
	 * @return
	 */
	public AssignDateHotel queryAssignDateHotel(AssignDateHotelReq assignDateHotelReq) throws GSSException;
	
	/**
	 * 获取取消订单原因列表存入数据库
	 * @return
	 */
	public List<CancelReasonModel> getTcOrderCancelList() throws GSSException;
	
	/**
	 * 查询同程日志信息
	 * @param getOrderLogInfoReq
	 * @return
	 */
	public List<OrderLogModel> LogInfoOfOrderFromTc(Agent agent, GetOrderLogInfoReq getOrderLogInfoReq) throws GSSException;
	/**
	 * 单个酒店更新
	 * @return
	 */
	public Boolean updateSingleHotelDetail(Agent agent, Long resId) throws GSSException;
	/**
	 * 同步更新出库存价格外的酒店信息
	 * @param resId
	 */
	public void doIncrHotelDetail(Long resId) throws GSSException;
	/**
	 * 同步更新某一个酒店的库存价格信息
	 * @param resId
	 */
	public Boolean doIncrInventoryWithResId(Agent agent,Long resId);
	/**
	 * 同步更新某一个房间的库存价格信息
	 * @param resId
	 */
	public Boolean doIncrInventoryWithProductUnique(Agent agent, Long resId, Long productUniqueId);
	/**
	 * 实时获取同程单个酒店信息
	 * @return
	 */
	public ResBaseInfo updateSingleResDetail(Agent agent, String resId);
	/**
	 * 实时获取同程的价格库存展示在后台
	 */
	public List<FormDate> updateProductunique(Agent agent, Long resId, Long productUniqueId);
	
	
}

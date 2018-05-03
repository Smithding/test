package com.tempus.gss.product.ift.dao;

import com.tempus.gss.product.ift.api.entity.SaleChangeExt;
import com.tempus.gss.product.ift.api.entity.vo.SaleChangeExtVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleChangeExtDao extends BaseDao<SaleChangeExt, SaleChangeExtVo> {

	/*查询采购退票单未锁单的单*/
	List<SaleChangeExt> queryRefundBylocker(@Param("owner") String owner,@Param("locker")long locker);

	/*查询采购退票单被lockerId锁单的数量*/
	int queryRefundCountBylocker(@Param("owner") String owner,@Param("locker")long locker);

	/*查询改签支付过但未锁单的单*/
	List<SaleChangeExt> queryChangeBylocker(@Param("owner") String owner,@Param("locker")long locker);

	/*查询采购改签被lockerID锁单的数量*/
	int queryChangeCountBylocker(@Param("owner") String owner,@Param("locker")long locker);

	/*查询销售改签被lockerID锁单的数量*/
	int querySaleChangeCountBylocker(@Param("owner") String owner,@Param("locker")long locker);

	/*查询采购废退被lockerID锁单的数量*/
	int queryBuyRefundAndDelCountBylocker(@Param("owner") String owner,@Param("locker")long locker);
	/*查询销售废退被lockerID锁单的数量*/
	int querySaleRefundAndDelCountBylocker(@Param("owner") String owner,@Param("locker")long locker);



	/*根据销售单编号查询销售拓展单*/
	List<SaleChangeExt> queryBySaleOrderNo(Long saleOrderNo);
	/**
	 * 锁定、解锁
	 *
	 * @param record
	 * @return
	 */
	int updateLocker(SaleChangeExt record);

	/**
	 * 根据销售单订单编号修改数据
	 * @param saleChangeExt
	 * @return
	 */
	int updateByOrderNo(SaleChangeExt saleChangeExt);

	/**
	 * 根据锁定Id和订单业务类型查询数量
	 * @param lockerId
	 * @param changeType
	 * @return
	 */
	int queryCountByLockerAndType(@Param("lockerId") Long lockerId, @Param("changeType") int changeType);
}
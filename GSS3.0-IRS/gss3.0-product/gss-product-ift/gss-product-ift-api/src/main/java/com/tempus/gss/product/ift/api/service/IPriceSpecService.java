package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PriceSpec;
import com.tempus.gss.product.ift.api.entity.vo.PriceSpecVo;

/**
 * 总则服务接口.
 */
public interface IPriceSpecService {
    
    /**
     * 创建总则.
     *
     * @param priceSpec
     * @return
     */
    PriceSpec createPriceSpec(RequestWithActor<PriceSpec> priceSpec) throws GSSException;
    
    /**
     * 修改总则.
     *
     * @param priceSpec
     * @return
     */
    PriceSpec updatePriceSpec(RequestWithActor<PriceSpec> priceSpec) throws GSSException;
    
    /**
     * 删除总则.
     *
     * @param priceSpec
     * @return
     */
    void deletePriceSpec(RequestWithActor<PriceSpec> priceSpec) throws GSSException;
    
    /**
     * 根据航司查询总则，可以为空.
     *
     * @param query
     * @return
     */
    Page<PriceSpec> selectPriceSpec(Page<PriceSpec> page, RequestWithActor<PriceSpecVo> query);
    
    /**
     * 根据航司查询总则.
     *
     * @param airline
     * @return 返回航司总则，如果没有就返回*号的航司，都没有则返回空.
     */
    PriceSpec getPriceSpec(RequestWithActor<String> airline);
    
    /**
     * getPriceSpec(根据编号查找总则)
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param priceSpecNo
     * @return PriceSpec
     * @throws
     * @since 1.0.0
     */
    PriceSpec getPriceSpecByPriceSpecNo(Long priceSpecNo);
    
    /**
     * 航司不能重复，进行判断
     *
     * @param airline
     */
    PriceSpec getExistPriceSpec(String airline);
    
    /**
     * 挂起解挂
     */
    boolean validate(RequestWithActor<Long> priceSpecNo, boolean isInvalid) throws GSSException;
}

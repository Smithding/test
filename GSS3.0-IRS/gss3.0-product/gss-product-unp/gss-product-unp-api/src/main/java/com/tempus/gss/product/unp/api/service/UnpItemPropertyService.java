package com.tempus.gss.product.unp.api.service;

import com.tempus.gss.product.unp.api.entity.UnpItemProperty;
import com.tempus.gss.product.unp.api.entity.util.UnpResult;
import com.tempus.gss.product.unp.api.entity.vo.UnpItemPropertyVo;
import com.tempus.gss.vo.Agent;

import java.util.List;
import java.util.Map;

/**
 * @author ZhangBro
 */
public interface UnpItemPropertyService {
    /**
     * @param agent
     *         agent
     * @param param
     *         UnpItemProperty
     *         <p>
     *         批量修改properties
     * @return UnpResult
     */
    UnpResult<String> updateProperties(Agent agent, List<UnpItemProperty> param);
    
    /**
     * @param agent
     *         agent
     * @param param
     *         UnpItemProperty
     *         <p>
     *         批量添加properties
     * @return UnpResult
     */
    UnpResult<String> addProperties(Agent agent, List<UnpItemProperty> param);
    
    /**
     * @param param
     *         UnpItemProperty
     *         <p>
     *         根据参数查询properties
     * @return UnpResult
     */
    
    List<UnpItemProperty> getProperties(UnpItemPropertyVo param);
    
    /**
     * @param agent
     * @param params
     *         根据多个小类(大类）获取 要展示的字段
     * @return
     */
    UnpResult<Map<String, List<UnpItemProperty>>> getPropertiesByItems(Agent agent, List<UnpItemProperty> params);
}

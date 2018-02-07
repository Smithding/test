package com.tempus.gss.product.ift.policyRedis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.ift.api.entity.PolicyIndex;
import com.tempus.gss.product.ift.api.service.IPolicyRService;
import com.tempus.gss.product.ift.api.service.IPolicyRedisUtils;
import com.tempus.gss.util.JsonUtil;
import com.tempus.tbd.entity.Airport;
import com.tempus.tbd.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 杨威 on 2017/12/7.
 */
@Service
public class PolicyRService implements IPolicyRService {
    @Reference
    private IAirportService airportService;

    @Autowired
    IPolicyRedisUtils policyRedisUtils;

    /**
     * 根据Policy记录生成索引记录
     * policy
     */
    public void policyProduction(Policy policy) {
        policyRedisUtils.set(policy.getPolicyNo().toString(), JsonUtil.toJson(policy),0l);
    }

    public List<Policy> queryObjByODs(Policy policyVo) {
        Airport goDepAirport = airportService.queryAirportByCode(policyVo.getGoStart(), "I");
        if (null == goDepAirport) {
            goDepAirport = airportService.queryAirportByCode(policyVo.getGoStart(), "D");
        }
        Airport goArrAirport = airportService.queryAirportByCode(policyVo.getGoEnd(), "I");
        if (null == goArrAirport) {
            goArrAirport = airportService.queryAirportByCode(policyVo.getGoEnd(), "D");
        }
        List<Policy> goPolicyList = new ArrayList<Policy>();
        /**
         * 起始点机场，终点遍历所有情况
         */
        String dep = policyVo.getGoStart();
        String arr =policyVo.getGoEnd();
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        policyVo.setGoStart(dep);
        policyVo.setGoEnd(goArrAirport.getAreaCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart(dep );
        policyVo.setGoEnd( goArrAirport.getCityCode() );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( dep );
        policyVo.setGoEnd(goArrAirport.getCountryCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( dep );
        policyVo.setGoEnd("***" );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 起始点区域，终点遍历所有情况
         */

        policyVo.setGoStart(goDepAirport.getAreaCode());
        policyVo.setGoEnd(arr );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        policyVo.setGoStart(goDepAirport.getAreaCode());
        policyVo.setGoEnd(goArrAirport.getAreaCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart(goDepAirport.getAreaCode() );
        policyVo.setGoEnd( goArrAirport.getCityCode() );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getAreaCode() );
        policyVo.setGoEnd(goArrAirport.getCountryCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getAreaCode() );
        policyVo.setGoEnd("***" );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 起始点城市，终点遍历所有情况
         */

        policyVo.setGoStart(goDepAirport.getCityCode());
        policyVo.setGoEnd(arr );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        policyVo.setGoStart(goDepAirport.getCityCode());
        policyVo.setGoEnd(goArrAirport.getAreaCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart(goDepAirport.getCityCode() );
        policyVo.setGoEnd( goArrAirport.getCityCode() );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getCityCode());
        policyVo.setGoEnd(goArrAirport.getCountryCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getCityCode() );
        policyVo.setGoEnd("***" );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 起始点国家，终点遍历所有情况
         */

        policyVo.setGoStart(goDepAirport.getCountryCode());
        policyVo.setGoEnd(arr );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart(goDepAirport.getCountryCode());
        policyVo.setGoEnd(goArrAirport.getAreaCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart(goDepAirport.getCountryCode() );
        policyVo.setGoEnd( goArrAirport.getCityCode() );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getCountryCode());
        policyVo.setGoEnd(goArrAirport.getCountryCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( goDepAirport.getCountryCode() );
        policyVo.setGoEnd("***" );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 起始点***，终点遍历所有情况
         */
        policyVo.setGoStart("***");
        policyVo.setGoEnd(arr );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        policyVo.setGoStart("***");
        policyVo.setGoEnd(goArrAirport.getAreaCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart("***" );
        policyVo.setGoEnd( goArrAirport.getCityCode() );
        try {
            List<Policy> policys =queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart( "***");
        policyVo.setGoEnd(goArrAirport.getCountryCode() );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        policyVo.setGoStart("***");
        policyVo.setGoEnd("***" );
        try {
            List<Policy> policys = queryPolicyByOD(policyVo);
            if(null!=policys){
                goPolicyList.addAll(policys);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        goPolicyList =goPolicyList.stream().filter(e->e.getTravellerType().contains(policyVo.getTravellerType())).collect(Collectors.toList());
        return goPolicyList;
    }

    public List<Policy> queryPolicyByOD(Policy policyVo) {
        Integer flyTypeDid = policyVo.getTripType();
        String airline = policyVo.getAirline();
        Integer getOwner = policyVo.getOwner();
        String fromCityDcd = policyVo.getGoStart();
        String toCityDcd = policyVo.getGoEnd();
        StringBuffer keyBuffer = new StringBuffer(fromCityDcd);
        keyBuffer.append("-");
        keyBuffer.append(toCityDcd);
        keyBuffer.append("-").append(flyTypeDid).append("-").append(airline).append("-").append(getOwner);
        String policyIndexs = policyRedisUtils.getStr(keyBuffer.toString());
        PolicyIndex policyIndex = JsonUtil.toBean(policyIndexs, PolicyIndex.class);
        List<Policy> policys = null;
        if(null!=policyIndex&&null!=policyIndex.getPolicyIds()){
            policys=new ArrayList<Policy>();
            for (Long policyId : policyIndex.getPolicyIds()) {
                String spolicy = policyRedisUtils.getStr(policyId.toString());
                policys.add(JsonUtil.toBean(spolicy, Policy.class));
            }
        }
        return  policys;
    }
}

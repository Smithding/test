package com.tempus.gss.product.ift.policyRedis;

import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.product.ift.api.entity.Policy;
import com.tempus.gss.product.ift.api.entity.PolicyIndex;
import com.tempus.gss.product.ift.api.service.IPolicyIndexService;
import com.tempus.gss.product.ift.api.service.IPolicyRedisUtils;
import com.tempus.gss.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by 杨威 on 2017/12/5.
 * changeRecord zhi.li 2018-03-23
 */
@Service
public class PolicyIndexService implements IPolicyIndexService {
   // private static Logger logger = Logger.getLogger(IPolicyIndexService.class);
    @Autowired
    IPolicyRedisUtils policyRedisUtils;
    /**
     *
     *
     *根据Policy记录生成索引记录
     * 并保存redis 生命周期24小时
     * policy
     */
    public void  policyIndexsProduction(Policy policy) {
        if (policy == null)
            return;
        Integer flyTypeDid = policy.getTripType();
        if(2==policy.getTripType()||3==policy.getTripType()){
             flyTypeDid =1;
        }
        List<String> keyList=null;
        Long policyId=policy.getPolicyNo();
        Integer getOwner =policy.getOwner();
        String fromCityDcd = policy.getGoStart();
        String toCityDcd = policy.getGoEnd();
        for(String airline :policy.getAirline().split("/")){
            // 生产索引表的key值
            keyList = getPolicyTempKeyList(flyTypeDid, fromCityDcd,
                    toCityDcd,airline.trim(),getOwner);
            if(2==policy.getTripType()||3==policy.getTripType()){
                flyTypeDid =2;
                fromCityDcd = policy.getGoEnd();
                toCityDcd = policy.getBackEnd();
                keyList.addAll(getPolicyTempKeyList(flyTypeDid, fromCityDcd,
                        toCityDcd,airline,getOwner));
            }
        }
        for (String key : keyList) {
            String  policyIndexs = policyRedisUtils.getStr(key);
            PolicyIndex policyIndex = JsonUtil.toBean(policyIndexs,PolicyIndex.class);
            if (policyIndex != null&&null!=policyIndex.getPolicyIds()) {
                Set<Long> paramSet = policyIndex.getPolicyIds();
                paramSet.add(policyId);
                policyRedisUtils.set(key, JsonUtil.toJson(policyIndex),24*3600L);
            } else {
                // policyId 集合
                Set listPolicy = new HashSet();
                listPolicy.add(policyId);
                policyIndex =new PolicyIndex();
                policyIndex.setPolicyIds(listPolicy);
                policyIndex.setIndexPk(key);
                policyRedisUtils.set(key, JsonUtil.toJson(policyIndex),24*3600L);
            }
        }
    }

    /**
     * 根据fromCityDcdo，toCityDcd生产索引表的key
     * @param flyTypeDid
     * @param fromCityDcdo
     * @param toCityDcd
     * @param airline
     * @param getOwner
     * @return
     */
    private List<String> getPolicyTempKeyList(Integer flyTypeDid,
                                              String fromCityDcdo, String toCityDcd,String airline,Integer getOwner) {
        List<String> keyList = new ArrayList<String>();
        StringBuffer keySb;
        if (StringUtils.isNotEmpty(fromCityDcdo)) {
            if (fromCityDcdo.contains("/")) {
                String[] arrayFromCitys = fromCityDcdo.split("/");
                for (String fromCity : arrayFromCitys) {
                    keySb = new StringBuffer();
                    keySb.append(fromCity.trim()).append("-");
                    // 拆分ToCity
                    sliptToCityAppendKey(keySb, toCityDcd, flyTypeDid, airline, getOwner,keyList);
                }
            } else {
                keySb = new StringBuffer();
                keySb.append(fromCityDcdo.trim()).append("-");
                sliptToCityAppendKey(keySb, toCityDcd, flyTypeDid, airline, getOwner,keyList);
            }
        }
        return keyList;
    }


    /**
     * 拆分toCity字段
     * @param fromCityKey
     * @param toCityDid
     * @param flyTypeDid
     * @param airline
     * @param getOwner
     * @param keyList
     */
    private void sliptToCityAppendKey(StringBuffer fromCityKey,
                                      String toCityDid, Integer flyTypeDid,String airline,Integer getOwner, List<String> keyList) {
        if (StringUtils.isNotEmpty(toCityDid)) {

            if (toCityDid.contains("/")) {
                String[] arrayToCitys = toCityDid.split("/");
               /* for (String toCity : arrayToCitys) {
                    StringBuffer keyBuffer = new StringBuffer(fromCityKey);
                    keyBuffer.append(toCity);
                    keyBuffer.append("-").append(flyTypeDid).append("-").append(airline).append("-").append(getOwner);
                    String key = keyBuffer.toString();
                    keyList.add(key);
                }*/
                Arrays.stream(arrayToCitys).map(toCity -> buildKey(fromCityKey, toCity, flyTypeDid, airline, getOwner)).forEach(keyList::add);
            } else {
               /* StringBuffer keyBuffer = new StringBuffer(fromCityKey);
                keyBuffer.append(toCityDid);
                keyBuffer.append("-").append(flyTypeDid).append("-").append(airline).append("-").append(getOwner);
                String key = keyBuffer.toString();*/
                keyList.add(buildKey(fromCityKey,toCityDid,flyTypeDid,airline,getOwner));
            }
        }
    }

    private String buildKey(StringBuffer fromCityKey,String toCityDid, Integer flyTypeDid,String airline,Integer getOwner){
        StringBuffer keyBuffer = new StringBuffer(fromCityKey);
        keyBuffer.append(toCityDid);
        keyBuffer.append("-").append(flyTypeDid).append("-").append(airline).append("-").append(getOwner);
        String key = keyBuffer.toString();
        return  key;
    }

}

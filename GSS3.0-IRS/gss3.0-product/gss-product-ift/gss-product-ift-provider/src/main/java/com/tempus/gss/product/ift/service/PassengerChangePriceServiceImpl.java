package com.tempus.gss.product.ift.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.tempus.gss.exception.GSSException;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.PassengerChangePrice;
import com.tempus.gss.product.ift.api.entity.vo.ChangePriceRequest;
import com.tempus.gss.product.ift.api.entity.vo.ChangePriceVo;
import com.tempus.gss.product.ift.api.service.IPassengerChangePriceService;
import com.tempus.gss.product.ift.dao.PassengerChangePriceDao;
import com.tempus.gss.system.service.IMaxNoService;

//import com.tempus.gss.system.service.IMaxNoService;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
@Service
@EnableAutoConfiguration
public class PassengerChangePriceServiceImpl implements IPassengerChangePriceService {
    Logger log = LoggerFactory.getLogger(getClass());
    
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    PassengerChangePriceDao passengerChangePriceDao;
    
    @Reference
    private IMaxNoService maxNoService;
    
    @Override
    public int createPassengerChangePrice(RequestWithActor<ChangePriceRequest> requestWithActor) {
        if (requestWithActor.getAgent() == null) {
            throw new GSSException("当前用户不能为空", "0101", "当前操作用户为空");
        }
        if (requestWithActor == null || requestWithActor.getEntity() == null) {
            throw new GSSException("改签确认失败", "0102", "改签确认发生异常,请检查");
        }
        int flag = 0;
        try {
            List<ChangePriceVo> priceListVo = requestWithActor.getEntity().getBuyPriceList();
            for (ChangePriceVo priceVo : priceListVo) {
                PassengerChangePrice priceChange = new PassengerChangePrice();
                Long passengerChangePriceNo = maxNoService.generateBizNo("IFT_PASSENGER_CHANGE_PRICE_NO", 30);
                priceChange.setChangePriceNo(passengerChangePriceNo);
                priceChange.setSaleChangeNo(priceVo.getSaleChangeNo());
                priceChange.setPassengerNo(priceVo.getPassengerNo());
                priceChange.setSaleOrderNo(priceVo.getSaleOrderNo());
                priceChange.setSaleCountPrice(priceVo.getCountPrice());
                priceChange.setSalePrice(priceVo.getSalePrice());
                priceChange.setSaleRest(priceVo.getSaleRest());
                priceChange.setSaleTax(priceVo.getSaleTax());
                priceChange.setBuyAgencyFee(priceVo.getBuyAgencyFee());
                priceChange.setBuyRebate(priceVo.getBuyRebate());
                priceChange.setSaleAgencyFee(priceVo.getSaleAgencyFee());
                priceChange.setSaleRebate(priceVo.getSaleRebate());
                priceChange.setSaleBrokerage(priceVo.getSaleBrokerage());
                priceChange.setCreateTime(simple.parse(simple.format(new Date())));
                priceChange.setModifyTime(simple.parse(simple.format(new Date())));
                priceChange.setOwner(requestWithActor.getAgent().getOwner());
                priceChange.setValid((byte) 0);
                priceChange.setStatus("1");
                priceChange.setCreator(requestWithActor.getAgent().getAccount());
                priceChange.setModifier(requestWithActor.getAgent().getAccount());
                
                flag = passengerChangePriceDao.insert(priceChange);
                if (flag == 0) {
                    throw new GSSException("废退改创建失败", "0101", "创建发生异常,请检查");
                }
                flag += flag;
            }
        } catch (Exception e) {
            log.error("改签确认失败", e);
            throw new GSSException("改签确认失败", "0103", "改签确认异常,请检查");
        }
        return flag;
    }
    
    /**
     *
     */
    @Override
    public int updatePassengerChangePrice(RequestWithActor<PassengerChangePrice> passengerChangePrice) {
        int flag = 0;
        try {
            passengerChangePrice.getEntity().setModifier(passengerChangePrice.getAgent().getAccount());
            passengerChangePrice.getEntity().setModifyTime(simple.parse(simple.format(new Date())));
            flag = passengerChangePriceDao.updateByPrimaryKeySelective(passengerChangePrice.getEntity());
        } catch (Exception e) {
            throw new GSSException("废退改修改失败", "0301", "修改发生异常,请检查");
        }
        return flag;
    }
    
    public List<PassengerChangePrice> getChangePriceList(RequestWithActor<Long> ChangePriceNo) {
        List<PassengerChangePrice> priceList = new ArrayList<PassengerChangePrice>();
        try {
            priceList = passengerChangePriceDao.selectPricerByChangeNo(ChangePriceNo.getEntity().longValue());
        } catch (Exception e) {
            throw new GSSException("改签价格列表查询失败", "0101", "查询列表发生异常,请检查");
        }
        return priceList;
    }
    
}

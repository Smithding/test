package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tempus.gss.product.ift.api.entity.Passenger;
import com.tempus.gss.product.ift.api.entity.PassengerRefundPrice;
import com.tempus.gss.product.ift.api.entity.vo.OrderPriceVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleChangeExtVo;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ift.api.service.ISaleOrderExtVOService;
import com.tempus.gss.product.ift.dao.*;
import com.tempus.gss.sbs.service.IStateConserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@EnableAutoConfiguration
public class SaleOrderExtVOServiceimpl implements ISaleOrderExtVOService {
    @Autowired
    SaleOrderExtVoDao saleOrderExtVoDao;
    @Autowired
    SaleOrderDetailDao saleOrderDetailDao;
    @Autowired
    PassengerDao passengerDao;
    @Autowired
    PassengerRefundPriceDao passengerRefundPriceDao;
    @Autowired
    LegDao legDao;
    @Autowired
    DemandDao demandDao;
    @Reference
    private IStateConserveService stateConserveService;
    
    @Override
    public List<SaleOrderExtVo> selectByTraNo(Long transationOrderNo) throws Exception {
        List<SaleOrderExtVo> list=new ArrayList<>();
        List<String> saleNos = stateConserveService.getSaleNos(transationOrderNo);
        List<SaleOrderExtVo> saleOrderExtVo = saleOrderExtVoDao.selectByTraNo(transationOrderNo);
        for (SaleOrderExtVo saleOrderExtVo1 : saleOrderExtVo) {
            if (null != saleOrderExtVo1 && saleOrderExtVo1.getSaleOrderNo() != null && !"".equals(saleOrderExtVo1.getSaleOrderNo())) {
                if (!saleNos.contains(String.valueOf(saleOrderExtVo1.getSaleOrderNo()))) {
                    List<Passenger> passengers = passengerDao.selectPassengerBySaleOrderNo(saleOrderExtVo1.getSaleOrderNo());
                    List<OrderPriceVo> orderPriceVos = new ArrayList<>(200);
                    if (null != passengers) {
                        for (Passenger passenger : passengers) {
                            OrderPriceVo orderPriceVo = new OrderPriceVo();
                            orderPriceVo.setBuyAgencyFee(passenger.getBuyAgencyFee());
                            orderPriceVo.setBuyAwardPrice(passenger.getBuyAwardPrice());
                            orderPriceVo.setBuyBrokerage(passenger.getBuyBrokerage());
                            orderPriceVo.setBuyFare(passenger.getBuyFare());
                            orderPriceVo.setBuyPrice(passenger.getBuyPrice());
                            orderPriceVo.setBuyRebate(passenger.getBuyRebate());
                            orderPriceVo.setBuyTax(passenger.getBuyTax());
                            orderPriceVo.setOrderType("1");
                            orderPriceVo.setPassengerType(passenger.getPassengerType());
                            orderPriceVo.setSaleAgencyFee(passenger.getSaleAgencyFee());
                            orderPriceVo.setSaleAwardPrice(passenger.getSaleAwardPrice());
                            orderPriceVo.setSaleBrokerage(passenger.getSaleBrokerage());
                            orderPriceVo.setSaleFare(passenger.getSaleFare());
                            orderPriceVo.setSaleJgPrice(passenger.getSalePrice());
                            orderPriceVo.setSalePrice(passenger.getSalePrice());
                            orderPriceVo.setSaleRebate(passenger.getSaleRebate());
                            orderPriceVo.setSaleTax(passenger.getSaleTax());
                            orderPriceVo.setServiceCharge(passenger.getServiceCharge());
                            orderPriceVos.add(orderPriceVo);
                        }
                    }
                    saleOrderExtVo1.setOrderPriceVoList(orderPriceVos);
                    list.add(saleOrderExtVo1);
                }
            }
        }
        return list;
    }

    @Override
    public SaleOrderExtVo selectBySaleNo(Long saleOrderNo) throws Exception {
        SaleOrderExtVo saleOrderExtVo = saleOrderExtVoDao.selectBySaleNo(saleOrderNo);

            if (null != saleOrderExtVo && saleOrderExtVo.getSaleOrderNo() != null && !"".equals(saleOrderExtVo.getSaleOrderNo())) {
                List<Passenger> passengers = passengerDao.selectPassengerBySaleOrderNo(saleOrderExtVo.getSaleOrderNo());
                List<OrderPriceVo> orderPriceVos = new ArrayList<>(200);
                if (null != passengers) {
                    for (Passenger passenger : passengers) {
                        OrderPriceVo orderPriceVo = new OrderPriceVo();
                        orderPriceVo.setBuyAgencyFee(passenger.getBuyAgencyFee());
                        orderPriceVo.setBuyAwardPrice(passenger.getBuyAwardPrice());
                        orderPriceVo.setBuyBrokerage(passenger.getBuyBrokerage());
                        orderPriceVo.setBuyFare(passenger.getBuyFare());
                        orderPriceVo.setBuyPrice(passenger.getBuyPrice());
                        orderPriceVo.setBuyRebate(passenger.getBuyRebate());
                        orderPriceVo.setBuyTax(passenger.getBuyTax());
                        orderPriceVo.setOrderType("1");
                        orderPriceVo.setPassengerType(passenger.getPassengerType());
                        orderPriceVo.setSaleAgencyFee(passenger.getSaleAgencyFee());
                        orderPriceVo.setSaleAwardPrice(passenger.getSaleAwardPrice());
                        orderPriceVo.setSaleBrokerage(passenger.getSaleBrokerage());
                        orderPriceVo.setSaleFare(passenger.getSaleFare());
                        orderPriceVo.setSaleJgPrice(passenger.getSalePrice());
                        orderPriceVo.setSalePrice(passenger.getSalePrice());
                        orderPriceVo.setSaleRebate(passenger.getSaleRebate());
                        orderPriceVo.setSaleTax(passenger.getSaleTax());
                        orderPriceVo.setServiceCharge(passenger.getServiceCharge());
                        orderPriceVos.add(orderPriceVo);
                    }
                }
                saleOrderExtVo.setOrderPriceVoList(orderPriceVos);
            }

        return saleOrderExtVo;
    }


}

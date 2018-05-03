package com.tempus.gss.product.ift.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.bbp.util.DateUtil;
import com.tempus.gss.cps.entity.Supplier;
import com.tempus.gss.cps.service.ISupplierService;
import com.tempus.gss.order.entity.BuyOrder;
import com.tempus.gss.order.service.IBuyOrderService;
import com.tempus.gss.product.ift.api.entity.*;
import com.tempus.gss.product.ift.api.entity.vo.SaleOrderExtVo;
import com.tempus.gss.product.ift.api.service.IBuyOrderExtService;
import com.tempus.gss.security.AgentUtil;
import com.tempus.gss.vo.Agent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 国际机票控制层辅助类
 *
 * @author zhi.li
 * @create 2018-04-21 10:20
 **/
@Component
public class IFTControllerUtils {

    @Reference
    IBuyOrderService buyOrderService;

    @Reference
    ISupplierService supplierService;

    @Reference
    IBuyOrderExtService buyOrderExtService;


    public List<SaleOrderExtVo> orderVoList(Agent agent, Page<SaleOrderExt> tempPage) {
        List<SaleOrderExtVo> orderVoList = new ArrayList<>();
        try {
            if (tempPage.getRecords() != null) {
                for (SaleOrderExt order : tempPage.getRecords()) {
                    SaleOrderExtVo orderVo = new SaleOrderExtVo();
                    orderVo.setAgentId(AgentUtil.getAgent().getId());
                    orderVo.setId(order.getId());
                    orderVo.setLocker(order.getLocker());
                    orderVo.setSaleOrderNo(order.getSaleOrderNo());//订单编号
                    orderVo.setStatus(order.getStatus());//12（已挂起).13.(已解挂)
                    //订单状态
                    if (order.getSaleOrder() != null && order.getSaleOrder().getOrderChildStatus() != null) {
                        orderVo.setOrderStatus(order.getSaleOrder().getOrderChildStatus());
                    }
                    orderVo.setDemand(order.getDemand());
                    //订单来源
                    String sourceChannelNo = null;
                    if (order.getSaleOrder() != null) {
                        if (order.getSaleOrder().getSourceChannelNo() == null) {
                            order.getSaleOrder().setSourceChannelNo("");
                        }
                        if (order.getSaleOrder().getSourceChannelNo() != null || ("").equals(order.getSaleOrder().getSourceChannelNo())) {
                            sourceChannelNo = order.getSaleOrder().getSourceChannelNo();
                        } else {
                            sourceChannelNo = sourceChannelNo + "-" + order.getSaleOrder().getSourceChannelNo();
                        }
                    }
                    orderVo.setSourceChannelNo(sourceChannelNo);

                    //设置交易号
                    if (order.getSaleOrder() != null) {
                        orderVo.setTransationOrderNo(order.getSaleOrder().getTransationOrderNo());
                    }

                    orderVo.setCreateType(order.getCreateType());//创单类型
                    orderVo.setModifier(order.getModifier());
                    //PNR
                    String pnr = null;
                    if (order.getImportPnr() != null) {
                        if (order.getImportPnr().getPnr() == null) {
                            order.getImportPnr().setPnr("");
                        }
                        if (order.getImportPnr().getPnr() != null || !("").equals(order.getImportPnr().getPnr())) {
                            pnr = String.valueOf(order.getImportPnr().getPnr());
                        } else {
                            pnr = pnr + "," + order.getImportPnr().getPnr();
                        }
                        orderVo.setPnr(pnr);
                    }

                    String fightNo = null;
                    String fight = null;
                    if (order.getLegList() != null && order.getLegList().size() > 0) {
                        for (Leg leg : order.getLegList()) {
                            //拼装航程
                            if (leg.getDepAirport() == null) {
                                leg.setDepAirport("");
                            }
                            if (leg.getArrAirport() == null) {
                                leg.setAirline("");
                            }
                            if (fight == null || fight.equals("")) {
                                if (leg.getDepAirport() != null && leg.getArrAirport() != null) {
                                    fight = leg.getDepAirport() + "-" + leg.getArrAirport();
                                } else if (leg.getDepAirport() == null) {
                                    fight = leg.getArrAirport();
                                } else if (leg.getArrAirport() == null) {
                                    fight = leg.getDepAirport();
                                }
                            } else {
                                if (leg.getDepAirport() != null && leg.getArrAirport() != null) {
                                    fight = fight + "," + leg.getDepAirport() + "-" + leg.getArrAirport();
                                } else if (leg.getDepAirport() == null) {
                                    fight = fight + "," + leg.getArrAirport();
                                } else if (leg.getArrAirport() == null) {
                                    fight = fight + "," + leg.getDepAirport();
                                }
                            }
                            orderVo.setLeg(fight);//航程

                            //航班号
                            if (leg.getFlightNo() == null) {
                                leg.setFlightNo("");
                            }
                            if (fightNo == null || ("").equals(fightNo)) {
                                fightNo = leg.getAirline() + "" + leg.getFlightNo();
                            } else {
                                fightNo = fightNo + "-" + leg.getAirline() + "" + leg.getFlightNo();
                            }
                            orderVo.setFlightNo(fightNo);//航班号
                            //航司
                            //orderVo.setAirline(leg.getAirline());
                            //起飞时间
                            String depTime = null;
                            if (orderVo.getStartTime() != null || !("").equals(orderVo.getStartTime())) {
                                depTime = DateUtil.getFormatDate(leg.getDepTime(), "yyyy-MM-dd HH:mm:ss");
                            } else {
                                depTime = orderVo.getStartTime() + "," + DateUtil.getFormatDate(leg.getDepTime(), "yyyy-MM-dd HH:mm:ss");//起飞时间
                            }
                            orderVo.setStartTime(depTime);
                        }
                    }
                    //乘机人
                    String name = null;
                    for (Passenger passenger : order.getPassengerList()) {
                        if (passenger.getName() == null) {
                            passenger.setName("");
                        }
                        if (passenger.getSurname() == null) {
                            passenger.setSurname("");
                        }
                        if (name == null || ("").equals(name)) {
                            name = passenger.getSurname() + passenger.getName();
                        } else {
                            name = name + "," + passenger.getSurname() + passenger.getName();
                        }
                        //票面价
                        String saleFare = null;
                        if (passenger.getSaleFare() == null) {
                            passenger.setSaleFare(new BigDecimal(0));
                        }
                        if (saleFare == null || ("").equals(saleFare)) {
                            saleFare = String.valueOf(passenger.getSaleFare().intValue());
                        } else {
                            saleFare = saleFare + "," + String.valueOf(passenger.getSaleFare().intValue());
                        }
                        orderVo.setSaleFare(saleFare);
                        //税费
                        String saleTax = null;
                        if (passenger.getSaleTax() == null) {
                            passenger.setSaleTax(new BigDecimal(0));
                        }
                        if (saleTax == null || ("").equals(saleTax)) {
                            saleTax = String.valueOf(passenger.getSaleTax().intValue());
                        } else {
                            saleTax = saleTax + "," + String.valueOf(passenger.getSaleTax().intValue());
                        }
                        orderVo.setSaleTax(saleTax);
                    }
                    orderVo.setName(name);
                    if (order.getSaleOrder() != null) {
                        //orderVo.setOrderChildStatus(order.getSaleOrder().getOrderChildStatus());//订单子状态
                        //应收
                        orderVo.setReceivable(order.getSaleOrder().getReceivable());
                        //实收
                        orderVo.setReceived(order.getSaleOrder().getReceived());
                        orderVo.setPayStatus(order.getSaleOrder().getPayStatus());
                    }
                    //出票类型
                    String customerTypeNo = null;
                    String policyType = null;
                    List<BuyOrder> buyOrderList = buyOrderService.getBuyOrdersBySONo(AgentUtil.getAgent(), order.getSaleOrderNo());

                    if (buyOrderList != null) {
                        for (BuyOrder buyOrder : buyOrderList) {
                            if (buyOrder.getBusinessSignNo().equals(order.getSaleOrder().getBusinessSignNo())) {
                                BuyOrderExt buyOrderExt = buyOrderExtService.selectBySaleOrderNo(AgentUtil.getAgent(), order.getSaleOrderNo());
                                if (buyOrder.getSupplierNo() != null || buyOrder.getSupplierNo() != 0) {
                                    Supplier supplier = supplierService.getSupplierByNo(agent, buyOrder.getSupplierNo());
                                    orderVo.setPolicyType(supplier.getShortName());

                                }
                                if (buyOrderExt != null) {
                                    orderVo.setBuyStatus(buyOrderExt.getBuyOrder().getBuyChildStatus());//采购办理状态
                                    //出票航司
                                    if (buyOrderExt.getAirline() != null) {
                                        orderVo.setTicketAirline(buyOrderExt.getAirline());
                                    }
                                    //采购结算价
                                    orderVo.setBuyPrice(buyOrderExt.getBuyOrder().getPayable().toString());
                                }
                            }
                        }
                    }
                    if (order.getSaleOrder().getOrderChildStatus() != null) {
                        orderVo.setSaleStatus(order.getSaleOrder().getOrderChildStatus());//客户办理状态
                    }
                    //备注
                    if (order.getSaleRemark() != null) {
                        orderVo.setSaleRemark(order.getSaleRemark());
                    } //舱位
                    if (order.getDemand() != null && order.getDemand().getServiceClass() != null) {
                        orderVo.setServiceClass(order.getDemand().getServiceClass());
                    }
                    //销售结算价
                    orderVo.setSalePrice(order.getSaleOrder().getReceivable().toString());
                    //票号
                    String ticketNo = null;
                    if (order.getSaleOrderDetailList() != null) {
                        for (SaleOrderDetail saleOrderDetail : order.getSaleOrderDetailList()) {
                            //票号
                            if (saleOrderDetail.getTicketNo() == null) {
                                saleOrderDetail.setTicketNo("");
                            }
                            if (ticketNo == null || ticketNo.equals("")) {
                                ticketNo = saleOrderDetail.getTicketNo();
                            } else {
                                ticketNo = ticketNo + "," + saleOrderDetail.getTicketNo();
                            }
                        }
                        orderVo.setTicketNo(ticketNo);//票号
                    }
                    orderVo.setCreateTime(order.getCreateTime());
                    orderVo.setHandlers(order.getHandlers());
                    orderVoList.add(orderVo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderVoList;

    }
}

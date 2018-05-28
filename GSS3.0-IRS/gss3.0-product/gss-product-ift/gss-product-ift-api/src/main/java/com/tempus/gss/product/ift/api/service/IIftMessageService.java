package com.tempus.gss.product.ift.api.service;

public interface IIftMessageService {

    //销售核价订单消息处理
    public void sendMessage(String ownerCode,Long saleOrderNo,String type);

    //销售退废票消息处理
    public void sendRefuseMessage(Long saleOrderNo,String ownerCode,String type);

    //销售改签消息处理
    public void sendChangeMessage(Long saleOrderNo,String ownerCode,String type);
}

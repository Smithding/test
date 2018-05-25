package com.tempus.gss.product.ift.api.service;

public interface IIftMessageService {

    //采购订单消息处理
    public void sendMessage(String ownerCode,Long saleOrderNo,String type);

    //销售退票消息处理
    public void sendRefuseMessage(Long saleOrderNo,String ownerCode,String type);

    //改签消息处理
    public void sendChangeMessage(Long saleOrderNo,String ownerCode,String type);
}

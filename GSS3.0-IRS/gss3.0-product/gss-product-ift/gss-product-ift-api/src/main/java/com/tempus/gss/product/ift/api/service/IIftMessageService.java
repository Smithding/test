package com.tempus.gss.product.ift.api.service;

public interface IIftMessageService {

    //采购订单消息处理
    public void sendMessage(String ownerCode,Long saleOrderNo);

    //销售退票消息处理
    public void sendRefuseMessage(Long saleOrderNo,String ownerCode);
}

package com.tempus.gss.product.ift.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tempus.gss.product.common.entity.RequestWithActor;
import com.tempus.gss.product.ift.api.entity.Pnr;

import java.util.List;

public interface IPnrManageService {
    
    List<Pnr> getAllByPnr(RequestWithActor<String> actor) throws Exception;
    
    Page<Pnr> getAllPnr(Page<Pnr> page, Pnr pnr) throws Exception;
    
    String cancelPnr(RequestWithActor<Long> actor) throws Exception;
    
    List<Pnr> selectByOrderNo(RequestWithActor<Long> actor) throws Exception;
    
    Pnr selectByPnrNo(RequestWithActor<Long> actor) throws Exception;
}
